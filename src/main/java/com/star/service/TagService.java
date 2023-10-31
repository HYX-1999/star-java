package com.star.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.star.entity.Tag;
import com.star.model.dto.ConditionDTO;
import com.star.model.dto.TagDTO;
import com.star.model.vo.*;

import java.util.List;

/**
 * 标签业务接口
 *
 * @author hyx
 **/
public interface TagService extends IService<Tag> {

    /**
     * 查看后台标签列表
     *
     * @param condition 条件
     * @return 后台标签列表
     */
    PageResult<TagBackVO> listTagBackVO(ConditionDTO condition);

    /**
     * 添加标签
     *
     * @param tag 标签
     */
    void addTag(TagDTO tag);

    /**
     * 删除标签
     *
     * @param tagIdList 标签id集合
     */
    void deleteTag(List<Integer> tagIdList);

    /**
     * 修改标签
     *
     * @param tag 标签
     */
    void updateTag(TagDTO tag);

    /**
     * 搜索文章标签
     *
     * @return 标签列表
     */
    List<TagOptionVO> listTagOption();

    /**
     * 查看标签列表
     *
     * @return 标签列表
     */
    List<TagVO> listTagVO();

    /**
     * 查看标签下的文章
     * @param condition 条件
     * @return 文章列表
     */
    ArticleListVO listArticleTag(ConditionDTO condition);
}
