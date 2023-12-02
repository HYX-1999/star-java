package com.star.handler;

import com.alibaba.fastjson.JSON;
import com.star.constant.CommonConstant;
import com.star.model.dto.UserDetailsDTO;
import com.star.model.dto.UserInfoDTO;
import com.star.entity.UserAuth;
import com.star.mapper.UserAuthMapper;
import com.star.service.TokenService;
import com.star.utils.BeanCopyUtils;
import com.star.utils.UserUtils;
import com.star.model.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;


@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    @Autowired
    private UserAuthMapper userAuthMapper;

    @Autowired
    private TokenService tokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserInfoDTO userLoginDTO = BeanCopyUtils.copyObject(UserUtils.getUserDetailsDTO(), UserInfoDTO.class);
        if (Objects.nonNull(authentication)) {
            UserDetailsDTO userDetailsDTO = (UserDetailsDTO) authentication.getPrincipal();
            String token = tokenService.createToken(userDetailsDTO);
            userLoginDTO.setToken(token);
        }
        response.setContentType(CommonConstant.APPLICATION_JSON);
        response.getWriter().write(JSON.toJSONString(ResultVO.ok(userLoginDTO)));
        updateUserInfo();
    }

    @Async
    public void updateUserInfo() {
        UserAuth userAuth = UserAuth.builder()
                .id(UserUtils.getUserDetailsDTO().getId())
                .ipAddress(UserUtils.getUserDetailsDTO().getIpAddress())
                .ipSource(UserUtils.getUserDetailsDTO().getIpSource())
                .lastLoginTime(UserUtils.getUserDetailsDTO().getLastLoginTime())
                .build();
        userAuthMapper.updateById(userAuth);
    }
}
