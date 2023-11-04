package com.star.service.impl;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import com.star.common.Constants;
import com.star.common.ResponseResult;
import com.star.common.ResultCode;
import com.star.entity.User;
import com.star.exception.BusinessException;
import com.star.mapper.UserMapper;
import com.star.model.dto.LoginDTO;
import com.star.service.LoginService;
import com.star.utils.AesEncryptUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LoginServiceImpl implements LoginService {

    private final UserMapper userMapper;

    @Override
    public ResponseResult login(LoginDTO loginDTO) {

        User user = userMapper.selectNameAndPassword(loginDTO.getUsername(), AesEncryptUtils.aesEncrypt(loginDTO.getPassword()));
        if (user == null) {
            throw new BusinessException(ResultCode.ERROR_PASSWORD.desc);
        }

        if (loginDTO.getRememberMe()) {
            StpUtil.login(user.getId(), new SaLoginModel().setTimeout(60*60*24*7));
        } else {
            StpUtil.login(user.getId(), "system");
        }
        StpUtil.getSession().set(Constants.CURRENT_USER, userMapper.getById(user.getId()));
        return ResponseResult.success(StpUtil.getTokenValue());
    }

}
