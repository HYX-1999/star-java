package com.star.service;

import com.star.common.ResponseResult;
import com.star.model.dto.Captcha;
import com.star.model.dto.LoginDTO;

public interface LoginService {

    ResponseResult login(LoginDTO loginDTO);

}
