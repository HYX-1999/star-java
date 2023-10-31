package com.star.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.star.entity.User;
import com.star.mapper.UserMapper;
import com.star.model.dto.LoginDTO;
import com.star.model.dto.RegisterDTO;
import com.star.service.LoginService;
import com.star.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 登录业务接口实现类
 *
 * @author hyx
 **/
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public String login(LoginDTO login) {
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().select(User::getId)
                                                                       .eq(User::getUsername, login.getUsername())
                                                                       .eq(User::getPassword, SecurityUtils.sha256Encrypt(login.getPassword())));
        Assert.notNull(user, "用户名不存在或密码错误");
        StpUtil.checkDisable(user.getId());
        StpUtil.login(user.getId());
        return StpUtil.getTokenValue();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void register(RegisterDTO register) {
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().select(User::getUsername)
                                                                       .eq(User::getUsername, register.getUsername()));
        Assert.isNull(user, "用户名已注册");
        User newUser = User.builder()
                           .username(register.getUsername())
                           .role(register.getRole())
                           .password(SecurityUtils.sha256Encrypt(register.getPassword()))
                .isDisable(0)
                           .build();
        userMapper.insert(newUser);
    }
}
