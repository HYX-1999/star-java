package com.star.controller;

import com.star.model.dto.LoginDTO;
import com.star.model.dto.RegisterDTO;
import com.star.model.vo.Result;
import com.star.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录控制器
 */
@Api(tags = "登录模块")
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * 用户登录
     *
     * @param login 登录参数
     * @return {@link String} Token
     */
    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public Result<String> login(@Validated @RequestBody LoginDTO login) {
        return Result.success(loginService.login(login));
    }

    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    public Result<?> register(@Validated @RequestBody RegisterDTO register) {
        loginService.register(register);
        return Result.success();
    }
}
