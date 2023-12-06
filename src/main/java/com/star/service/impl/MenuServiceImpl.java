package com.star.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.star.entity.Menu;
import com.star.mapper.MenuMapper;
import com.star.model.dto.UserMenuDTO;
import com.star.service.MenuService;
import com.star.utils.BeanCopyUtils;
import com.star.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.star.constant.CommonConstant.COMPONENT;
import static com.star.constant.CommonConstant.TRUE;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<UserMenuDTO> listUserMenus() {
        List<Menu> menus = menuMapper.listMenusByUserInfoId(UserUtils.getUserDetailsDTO().getUserInfoId());
        List<Menu> catalogs = listCatalogs(menus);
        Map<Integer, List<Menu>> childrenMap = getMenuMap(menus);
        return convertUserMenuList(catalogs, childrenMap);
    }

    private List<Menu> listCatalogs(List<Menu> menus) {
        return menus.stream().filter(item -> Objects.isNull(item.getParentId())).sorted(Comparator.comparing(Menu::getOrderNum)).collect(Collectors.toList());
    }

    private Map<Integer, List<Menu>> getMenuMap(List<Menu> menus) {
        return menus.stream().filter(item -> Objects.nonNull(item.getParentId())).collect(Collectors.groupingBy(Menu::getParentId));
    }

    private List<UserMenuDTO> convertUserMenuList(List<Menu> catalogList, Map<Integer, List<Menu>> childrenMap) {
        return catalogList.stream().map(item -> {
            UserMenuDTO userMenuDTO = new UserMenuDTO();
            List<UserMenuDTO> list = new ArrayList<>();
            List<Menu> children = childrenMap.get(item.getId());
            if (CollectionUtils.isNotEmpty(children)) {
                userMenuDTO = BeanCopyUtils.copyObject(item, UserMenuDTO.class);
                list = children.stream()
                        .sorted(Comparator.comparing(Menu::getOrderNum))
                        .map(menu -> {
                            UserMenuDTO dto = BeanCopyUtils.copyObject(menu, UserMenuDTO.class);
                            dto.setHidden(menu.getIsHidden().equals(TRUE));
                            return dto;
                        })
                        .collect(Collectors.toList());
            } else {
                userMenuDTO.setPath(item.getPath());
                userMenuDTO.setComponent(COMPONENT);
                list.add(UserMenuDTO.builder()
                        .path("")
                        .name(item.getName())
                        .icon(item.getIcon())
                        .component(item.getComponent())
                        .build());
            }
            userMenuDTO.setHidden(item.getIsHidden().equals(TRUE));
            userMenuDTO.setChildren(list);
            return userMenuDTO;
        }).collect(Collectors.toList());
    }
}
