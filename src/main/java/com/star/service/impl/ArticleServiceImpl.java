package com.star.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.star.entity.Article;
import com.star.entity.ArticleTag;
import com.star.entity.BlogFile;
import com.star.mapper.ArticleMapper;
import com.star.mapper.ArticleTagMapper;
import com.star.mapper.BlogFileMapper;
import com.star.mapper.TagMapper;
import com.star.model.dto.ArticleDTO;
import com.star.model.dto.ConditionDTO;
import com.star.model.vo.ArticleBackVO;
import com.star.model.vo.ArticleHomeVO;
import com.star.model.vo.ArticleInfoVO;
import com.star.model.vo.PageResult;
import com.star.service.ArticleService;
import com.star.strategy.UploadStrategy;
import com.star.utils.BeanCopyUtils;
import com.star.utils.FileUtils;
import com.star.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.star.enums.FilePathEnum.ARTICLE;

/**
 * 文章业务接口实现类
 *
 * @author hyx
 **/
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private UploadStrategy uploadStrategy;

    @Autowired
    private BlogFileMapper blogFileMapper;

    @Override
    public PageResult<ArticleBackVO> listArticleBackVO(ConditionDTO condition) {
        Long total = articleMapper.countArticleBackVO(condition);
        if (total == 0) {
            return new PageResult<>(new ArrayList<>(), total, null, null);
        }
        Long limit = PageUtils.getLimit();
        List<ArticleBackVO> articleBackVOList = articleMapper.selectArticleBackVO(PageUtils.getLimit(), PageUtils.getSize(), condition);
        return new PageResult<>(articleBackVOList, total, PageUtils.getCurrent(), PageUtils.getSize());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addArticle(ArticleDTO article) {
        Article newArticle = Article.builder()
                                    .categoryId(article.getCategoryId())
                                    .articleContent(article.getArticleContent())
                                    .articleCover(article.getArticleCover())
                                    .articleTitle(article.getArticleTitle()).status(article.getStatus()).build();
        baseMapper.insert(newArticle);
        // 保存文章标签
        articleTagMapper.saveBatchArticleTag(newArticle.getId(), article.getTagIdList());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateArticle(ArticleDTO article) {
        Article newArticle = BeanCopyUtils.copyBean(article, Article.class);
        baseMapper.updateById(newArticle);
        // 保存文章标签
        articleTagMapper.saveBatchArticleTag(newArticle.getId(), article.getTagIdList());
    }

    @Override
    public void deleteArticle(List<Integer> articleIdList) {
        // 删除文章标签
        articleTagMapper.delete(new LambdaQueryWrapper<ArticleTag>().in(ArticleTag::getArticleId, articleIdList));
        // 删除文章
        articleMapper.deleteBatchIds(articleIdList);
    }

    @Override
    public ArticleInfoVO getInfo(Integer articleId) {
        ArticleInfoVO articleInfoVO = articleMapper.selectArticleInfoById(articleId);
        Assert.notNull(articleInfoVO, "没有该文章");
        // 查询文章标签名称
        List<Integer> tagIdList = tagMapper.selectTagIdByArticleId(articleId);
        articleInfoVO.setTagIdList(tagIdList);
        return articleInfoVO;
    }

    @Override
    public String saveArticleImages(MultipartFile file) {
        String url = uploadStrategy.uploadFile(file, ARTICLE.getPath());
        try {
            // 获取文件md5值
            String md5 = FileUtils.getMd5(file.getInputStream());
            // 获取文件扩展名
            String extName = FileUtils.getExtension(file);
            BlogFile existFile = blogFileMapper.selectOne(new LambdaQueryWrapper<BlogFile>()
                    .select(BlogFile::getId)
                    .eq(BlogFile::getFileName, md5)
                    .eq(BlogFile::getFilePath, ARTICLE.getFilePath()));
            if (Objects.isNull(existFile)) {
                // 保存文件信息
                BlogFile newFile = BlogFile.builder()
                                           .fileUrl(url)
                                           .fileName(md5)
                                           .filePath(ARTICLE.getFilePath())
                                           .extendName(extName)
                                           .fileSize((int) file.getSize())
                                           .isDir(0)
                                           .build();
                blogFileMapper.insert(newFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return url;
    }

    @Override
    public PageResult<ArticleHomeVO> listArticleHomeVO() {
        // 查询文章数量
        Long total = articleMapper.selectCount(new LambdaQueryWrapper<Article>().eq(Article::getIsDelete, 0).eq(Article::getStatus, 1));
        if (total == 0) {
            return new PageResult<>(new ArrayList<>(), total, null, null);
        }
        // 查询首页文章
        List<ArticleHomeVO> articleHomeVOList = articleMapper.selectArticleHomeList(PageUtils.getLimit(), PageUtils.getSize());
        return new PageResult<>(articleHomeVOList, total, PageUtils.getCurrent(), PageUtils.getSize());
    }
}
