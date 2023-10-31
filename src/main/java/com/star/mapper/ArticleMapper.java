package com.star.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.star.entity.Article;
import com.star.model.dto.ConditionDTO;
import com.star.model.vo.ArticleBackVO;
import com.star.model.vo.ArticleConditionVO;
import com.star.model.vo.ArticleHomeVO;
import com.star.model.vo.ArticleInfoVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 文章 Mapper
 *
 * @author hyx
 **/
@Repository
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 查询后台文章数量
     *
     * @param condition 条件
     * @return 文章数量
     */
    Long countArticleBackVO(@Param("condition") ConditionDTO condition);

    /**
     * 查询后台文章列表
     *
     * @param limit     页码
     * @param size      大小
     * @param condition 条件
     * @return 后台文章列表
     */
    List<ArticleBackVO> selectArticleBackVO(@Param("limit") Long limit, @Param("size") Long size, @Param("condition") ConditionDTO condition);

    /**
     * 根据id查询文章信息
     *
     * @param articleId 文章id
     * @return 文章信息
     */
    ArticleInfoVO selectArticleInfoById(@Param("articleId") Integer articleId);

    /**
     * 查询首页文章
     *
     * @param limit 页码
     * @param size  大小
     * @return 首页文章
     */
    List<ArticleHomeVO> selectArticleHomeList(@Param("limit") Long limit, @Param("size") Long size);

    /**
     * 根据条件查询文章
     *
     * @param limit     页码
     * @param size      大小
     * @param condition 条件
     * @return 文章列表
     */
    List<ArticleConditionVO> listArticleByCondition(@Param("limit") Long limit, @Param("size") Long size, @Param("condition") ConditionDTO condition);
}
