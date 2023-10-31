package com.star.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.star.entity.Category;
import com.star.model.dto.CategoryDTO;
import com.star.model.dto.ConditionDTO;
import com.star.model.vo.CategoryBackVO;
import com.star.model.vo.CategoryVO;
import com.star.model.vo.PageResult;

import java.util.List;

/**
 * 分类业务接口
 **/
public interface CategoryService extends IService<Category> {

    /**
     * 查看后台分类列表
     *
     * @param condition 查询条件
     * @return 后台分类列表
     */
    PageResult<CategoryBackVO> pageCategoryBackVO(ConditionDTO condition);

    /**
     * 添加分类
     *
     * @param category 分类
     */
    void addCategory(CategoryDTO category);

    /**
     * 删除分类
     *
     * @param categoryIdList 分类id集合
     */
    void deleteCategory(List<Integer> categoryIdList);

    /**
     * 修改分类
     *
     * @param category 分类
     */
    void updateCategory(CategoryDTO category);

    /**
     * 查看分类列表
     *
     * @return 分类列表
     */
    List<CategoryVO> listCategoryVO();
}
