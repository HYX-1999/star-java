package com.star.service;

import com.star.model.dto.LoginDTO;
import com.star.model.dto.RegisterDTO;

/**
 * 登录业务接口
 **/
public interface LoginService {

    /**
     * 用户登录
     *
     * @param login 登录参数
     * @return token
     */
    String login(LoginDTO login);

    /**
     * 用户注册
     *
     * @param register 注册信息
     */
    void register(RegisterDTO register);
}
