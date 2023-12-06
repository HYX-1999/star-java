package com.star.controller;

import com.star.model.dto.UserMenuDTO;
import com.star.model.vo.ResultVO;
import com.star.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "菜单模块")
@RestController
public class MenuController {

    @Autowired
    private MenuService menuService;

    @ApiOperation(value = "查看当前用户菜单")
    @GetMapping("/admin/user/menus")
    public ResultVO<List<UserMenuDTO>> listUserMenus() {
        return ResultVO.ok(menuService.listUserMenus());
    }
}
