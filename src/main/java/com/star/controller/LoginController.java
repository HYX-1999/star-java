package com.star.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.star.common.ResponseResult;
import com.star.model.dto.LoginDTO;
import com.star.service.LoginService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "登录接口")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("login")
    public ResponseResult login(@Validated @RequestBody LoginDTO loginDTO) {
        return loginService.login(loginDTO);
    }

    @SaCheckLogin
    @GetMapping("logout")
    public ResponseResult logout() {
        StpUtil.logout();
        return ResponseResult.success("退出成功");
    }
}
