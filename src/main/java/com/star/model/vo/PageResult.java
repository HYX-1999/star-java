package com.star.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页返回类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "分页返回类")
public class PageResult<T> {

    /**
     * 分页结果
     */
    @ApiModelProperty(value = "分页结果")
    private List<T> records;

    /**
     * 总数
     */
    @ApiModelProperty(value = "总数", dataType = "long")
    private Long total;

    /**
     * 总数
     */
    @ApiModelProperty(value = "页数", dataType = "long")
    private Long current;

    /**
     * 总数
     */
    @ApiModelProperty(value = "条数", dataType = "long")
    private Long size;
}
