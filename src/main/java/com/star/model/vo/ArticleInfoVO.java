package com.star.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 文章信息
 *
 * @author hyx
 */
@Data
@ApiModel(description = "文章信息")
public class ArticleInfoVO {

    /**
     * 文章id
     */
    @ApiModelProperty(value = "文章id")
    private Integer id;

    /**
     * 文章分类id
     */
    @ApiModelProperty(value = "文章分类ID")
    private Integer categoryId;

    /**
     * 文章缩略图
     */
    @ApiModelProperty(value = "文章缩略图")
    private String articleCover;

    /**
     * 文章标题
     */
    @ApiModelProperty(value = "文章标题")
    private String articleTitle;

    /**
     * 文章内容
     */
    @ApiModelProperty(value = "文章内容")
    private String articleContent;

    /**
     * 状态 (1公开 2私密 3草稿)
     */
    @ApiModelProperty(value = "状态 (1公开 2私密 3草稿)")
    private Integer status;

    /**
     * 文章标签
     */
    @ApiModelProperty(value = "文章标签")
    private List<Integer> tagIdList;
}
