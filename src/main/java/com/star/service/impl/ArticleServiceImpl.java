package com.star.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.star.entity.Article;
import com.star.entity.ArticleTag;
import com.star.entity.Category;
import com.star.entity.Tag;
import com.star.mapper.ArticleMapper;
import com.star.mapper.ArticleTagMapper;
import com.star.mapper.CategoryMapper;
import com.star.model.dto.ArticleAdminDTO;
import com.star.model.dto.PageResultDTO;
import com.star.model.vo.ArticleVO;
import com.star.model.vo.ConditionVO;
import com.star.service.ArticleService;
import com.star.service.ArticleTagService;
import com.star.service.RedisService;
import com.star.service.TagService;
import com.star.utils.BeanCopyUtils;
import com.star.utils.PageUtils;
import com.star.utils.UserUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.star.constant.RedisConstant.ARTICLE_VIEWS_COUNT;
import static com.star.enums.ArticleStatusEnum.DRAFT;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private TagService tagService;

    @Autowired
    private ArticleTagService articleTagService;

    @Autowired
    private RedisService redisService;

    @SneakyThrows
    @Override
    public PageResultDTO<ArticleAdminDTO> listArticlesAdmin(ConditionVO conditionVO) {
        CompletableFuture<Integer> asyncCount = CompletableFuture.supplyAsync(() -> articleMapper.countArticleAdmins(conditionVO));
        List<ArticleAdminDTO> articleAdminDTOS = articleMapper.listArticlesAdmin(PageUtils.getLimitCurrent(), PageUtils.getSize(), conditionVO);
        Map<Object, Double> viewsCountMap = redisService.zAllScore(ARTICLE_VIEWS_COUNT);
        articleAdminDTOS.forEach(item -> {
            Double viewsCount = viewsCountMap.get(item.getId());
            if (Objects.nonNull(viewsCount)) {
                item.setViewsCount(viewsCount.intValue());
            }
        });
        return new PageResultDTO<>(articleAdminDTOS, asyncCount.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdateArticle(ArticleVO articleVO) {
        Category category = saveArticleCategory(articleVO);
        Article article = BeanCopyUtils.copyObject(articleVO, Article.class);
        if (Objects.nonNull(category)) {
            article.setCategoryId(category.getId());
        }
        article.setUserId(UserUtils.getUserDetailsDTO().getUserInfoId());
        this.saveOrUpdate(article);
        saveArticleTag(articleVO, article.getId());
    }

    private Category saveArticleCategory(ArticleVO articleVO) {
        Category category = categoryMapper.selectOne(new LambdaQueryWrapper<Category>().eq(Category::getCategoryName, articleVO.getCategoryName()));
        if (Objects.isNull(category) && !articleVO.getStatus().equals(DRAFT.getStatus())) {
            category = Category.builder().categoryName(articleVO.getCategoryName()).build();
            categoryMapper.insert(category);
        }
        return category;
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveArticleTag(ArticleVO articleVO, Integer articleId) {
        if (Objects.nonNull(articleVO.getId())) {
            articleTagMapper.delete(new LambdaQueryWrapper<ArticleTag>().eq(ArticleTag::getArticleId, articleVO.getId()));
        }
        List<String> tagNames = articleVO.getTagNames();
        if (CollectionUtils.isNotEmpty(tagNames)) {
            List<Tag> existTags = tagService.list(new LambdaQueryWrapper<Tag>().in(Tag::getTagName, tagNames));
            List<String> existTagNames = existTags.stream().map(Tag::getTagName).collect(Collectors.toList());
            List<Integer> existTagIds = existTags.stream().map(Tag::getId).collect(Collectors.toList());
            tagNames.removeAll(existTagNames);
            if (CollectionUtils.isNotEmpty(tagNames)) {
                List<Tag> tags = tagNames.stream().map(item -> Tag.builder().tagName(item).build()).collect(Collectors.toList());
                tagService.saveBatch(tags);
                List<Integer> tagIds = tags.stream().map(Tag::getId).collect(Collectors.toList());
                existTagIds.addAll(tagIds);
            }
            List<ArticleTag> articleTags = existTagIds.stream().map(item -> ArticleTag.builder().articleId(articleId).tagId(item).build()).collect(Collectors.toList());
            articleTagService.saveBatch(articleTags);
        }
    }

}
