package com.star.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

/**
 * 用户注册信息
 *
 * @author hyx
 */
@Data
@ApiModel(description = "用户注册信息")
public class RegisterDTO {

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, message = "密码不能少于6位")
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 角色
     */
    @Positive(message = "角色不能为空")
    @ApiModelProperty(value = "角色")
    private Integer role;
}
