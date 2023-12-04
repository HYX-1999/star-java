package com.star.service.impl;

import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.star.constant.CommonConstant;
import com.star.entity.UserAuth;
import com.star.entity.UserInfo;
import com.star.entity.UserRole;
import com.star.enums.LoginTypeEnum;
import com.star.enums.RoleEnum;
import com.star.exception.BizException;
import com.star.mapper.UserAuthMapper;
import com.star.mapper.UserInfoMapper;
import com.star.mapper.UserRoleMapper;
import com.star.model.vo.UserVO;
import com.star.service.RedisService;
import com.star.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.star.constant.RedisConstant.USER_CODE_KEY;

@Service
public class UserAuthServiceImpl implements UserAuthService {

    @Autowired
    private UserAuthMapper userAuthMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RedisService redisService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(UserVO userVO) {
//        if (!checkEmail(userVO.getUsername())) {
//            throw new BizException("邮箱格式不对！");
//        }
//        if (checkUser(userVO)) {
//            throw new BizException("邮箱已被注册！");
//        }
        UserInfo userInfo = UserInfo.builder().email(userVO.getUsername()).nickname(CommonConstant.DEFAULT_NICKNAME + IdWorker.getId()).build();
        userInfoMapper.insert(userInfo);
        UserRole userRole = UserRole.builder().userId(userInfo.getId()).roleId(RoleEnum.ADMIN.getRoleId()).build();
        userRoleMapper.insert(userRole);
        UserAuth userAuth = UserAuth.builder().userInfoId(userInfo.getId()).username(userVO.getUsername()).password(BCrypt.hashpw(userVO.getPassword(), BCrypt.gensalt())).loginType(LoginTypeEnum.EMAIL.getType()).build();
        userAuthMapper.insert(userAuth);
    }

    private Boolean checkUser(UserVO user) {
        if (!user.getClass().equals(redisService.get(USER_CODE_KEY + user.getUsername()))) {
            throw new BizException("验证码错误！");
        }
        UserAuth userAuth = userAuthMapper.selectOne(new LambdaQueryWrapper<UserAuth>().select(UserAuth::getUsername).eq(UserAuth::getUsername, user.getUsername()));
        return Objects.nonNull(userAuth);
    }
}
