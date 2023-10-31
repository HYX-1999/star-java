package com.star.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 文章DTO
 **/
@Data
@ApiModel(description = "文章DTO")
public class ArticleDTO {

    /**
     * 文章id
     */
    @ApiModelProperty(value = "文章id")
    private Integer id;

    /**
     * 文章缩略图
     */
    @ApiModelProperty(value = "文章缩略图")
    private String articleCover;

    /**
     * 文章标题
     */
    @NotBlank(message = "文章标题不能为空")
    @ApiModelProperty(value = "文章标题")
    private String articleTitle;

    /**
     * 文章内容
     */
    @NotBlank(message = "文章内容不能为空")
    @ApiModelProperty(value = "文章内容")
    private String articleContent;

    /**
     * 分类名
     */
    @NotNull(message = "文章分类不能为空")
    @ApiModelProperty(value = "分类ID")
    private Integer categoryId;

    /**
     * 标签名
     */
    @ApiModelProperty(value = "标签名")
    private List<Integer> tagIdList;

    /**
     * 状态 (1公开 2私密 3草稿)
     */
    @ApiModelProperty(value = "状态 (1公开 2私密 3草稿)")
    private Integer status;
}
