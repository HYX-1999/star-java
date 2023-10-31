package com.star.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.star.annotation.VisitLogger;
import com.star.model.dto.CategoryDTO;
import com.star.model.dto.ConditionDTO;
import com.star.model.vo.CategoryBackVO;
import com.star.model.vo.CategoryVO;
import com.star.model.vo.PageResult;
import com.star.model.vo.Result;
import com.star.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类控制器
 **/
@Api(tags = "分类模块")
@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 查看后台分类列表
     *
     * @param condition 查询条件
     * @return {@link CategoryBackVO} 后台分类
     */
    @ApiOperation(value = "查看后台分类列表")
    @SaCheckPermission("blog:category:page")
    @GetMapping("/admin/category/page")
    public Result<PageResult<CategoryBackVO>> pageCategoryBackVO(ConditionDTO condition) {
        return Result.success(categoryService.pageCategoryBackVO(condition));
    }

    /**
     * 添加分类
     *
     * @param category 分类信息
     * @return {@link Result<>}
     */
    @ApiOperation(value = "添加分类")
    @SaCheckPermission("blog:category:add")
    @PostMapping("/admin/category/add")
    public Result<?> addCategory(@Validated @RequestBody CategoryDTO category) {
        categoryService.addCategory(category);
        return Result.success();
    }

    /**
     * 删除分类
     *
     * @param categoryIdList 分类id集合
     * @return {@link Result<>}
     */
    @ApiOperation(value = "删除分类")
    @SaCheckPermission("blog:category:delete")
    @DeleteMapping("/admin/category/delete")
    public Result<?> deleteCategory(@RequestBody List<Integer> categoryIdList) {
        categoryService.deleteCategory(categoryIdList);
        return Result.success();
    }

    @ApiOperation(value = "修改分类")
    @SaCheckPermission("blog:category:update")
    @PutMapping("/admin/category/update")
    public Result<?> updateCategory(@Validated @RequestBody CategoryDTO category) {
        categoryService.updateCategory(category);
        return Result.success();
    }

    /**
     * 查看分类列表
     *
     * @return {@link Result<CategoryVO>} 分类列表
     */
    @VisitLogger(value = "文章分类")
    @ApiOperation(value = "查看分类列表")
    @GetMapping("/category/list")
    public Result<List<CategoryVO>> listCategoryVO() {
        return Result.success(categoryService.listCategoryVO());
    }
}
