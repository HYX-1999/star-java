package com.star.service.impl;

import com.star.entity.UserRole;
import com.star.mapper.UserRoleMapper;
import com.star.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
