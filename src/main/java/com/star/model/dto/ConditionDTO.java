package com.star.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 查询条件
 **/
@Data
@ApiModel(description = "查询条件")
public class ConditionDTO {

    /**
     * 页码
     */
    @ApiModelProperty(value = "页码")
    private Long current;

    /**
     * 条数
     */
    @ApiModelProperty(value = "条数")
    private Long size;

    /**
     * 搜索内容
     */
    @ApiModelProperty(value = "搜索内容")
    private String keyword;

    /**
     * 是否禁用 (0否 1是)
     */
    @ApiModelProperty(value = "是否禁用 (0否 1是)")
    private Integer isDisable;

    /**
     * 分类id
     */
    @ApiModelProperty(value = "分类id")
    private Integer categoryId;

    /**
     * 标签id
     */
    @ApiModelProperty(value = "标签id")
    private Integer tagId;

    /**
     * 用户类型
     */
    @ApiModelProperty(value = "用户类型")
    private Integer userType;

    /**
     * 是否删除 (0否 1是)
     */
    @ApiModelProperty(value = "是否删除 (0否 1是)")
    private Integer isDelete;

    /**
     * 文章状态 (1公开 2私密 3草稿)
     * 任务状态 (0运行 1暂停)
     */
    @ApiModelProperty(value = "状态")
    private Integer status;

    /**
     * 文件路径
     */
    @ApiModelProperty(value = "文件路径")
    private String filePath;
}
