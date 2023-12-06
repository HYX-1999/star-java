package com.star.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.star.entity.Menu;
import com.star.model.dto.UserMenuDTO;

import java.util.List;

public interface MenuService extends IService<Menu> {

    List<UserMenuDTO> listUserMenus();

}
