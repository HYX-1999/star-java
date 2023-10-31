package com.star.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.star.entity.Article;
import com.star.model.dto.ArticleDTO;
import com.star.model.dto.ConditionDTO;
import com.star.model.vo.ArticleBackVO;
import com.star.model.vo.ArticleHomeVO;
import com.star.model.vo.ArticleInfoVO;
import com.star.model.vo.PageResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 文章业务接口
 *
 * @author hyx
 **/
public interface ArticleService extends IService<Article> {

    /**
     * 查看后台文章列表
     *
     * @param condition 条件
     * @return 后台文章列表
     */
    PageResult<ArticleBackVO> listArticleBackVO(ConditionDTO condition);

    /**
     * 添加文章
     *
     * @param article 文章
     */
    void addArticle(ArticleDTO article);

    /**
     * 修改文章
     *
     * @param article 文章
     */
    void updateArticle(ArticleDTO article);

    /**
     * 删除文章
     *
     * @param articleIdList 文章id
     */
    void deleteArticle(List<Integer> articleIdList);

    /**
     * 获取文章详情
     *
     * @param articleId 文章id
     * @return 文章
     */
    ArticleInfoVO getInfo(Integer articleId);

    /**
     * 上传文章图片
     *
     * @param file 文件
     * @return 文章图片地址
     */
    String saveArticleImages(MultipartFile file);

    /**
     * 查看首页文章列表
     *
     * @return 首页文章列表
     */
    PageResult<ArticleHomeVO> listArticleHomeVO();
}
