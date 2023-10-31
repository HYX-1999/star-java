package com.star.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 文章条件列表VO
 *
 * @author hyx
 **/
@Data
@Builder
@ApiModel(description = "文章条件列表VO")
public class ArticleListVO {

    /**
     * 文章列表
     */
    @ApiModelProperty(value = "文章列表")
    private List<ArticleConditionVO> articleConditionVOList;

    /**
     * 条件名
     */
    @ApiModelProperty(value = "条件名")
    private String name;
}
