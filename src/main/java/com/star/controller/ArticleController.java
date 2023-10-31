package com.star.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.star.annotation.VisitLogger;
import com.star.model.dto.ArticleDTO;
import com.star.model.dto.ConditionDTO;
import com.star.model.vo.*;
import com.star.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文章控制器
 *
 * @author hyx
 **/
@Api(tags = "文章模块")
@RestController
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 查看后台文章列表
     *
     * @param condition 条件
     * @return {@link Result<ArticleBackVO>} 后台文章列表
     */
    @ApiOperation(value = "查看后台文章列表")
    @SaCheckPermission("blog:article:list")
    @GetMapping("/admin/article/list")
    public Result<PageResult<ArticleBackVO>> listArticleBackVO(ConditionDTO condition) {
        return Result.success(articleService.listArticleBackVO(condition));
    }

    /**
     * 添加文章
     *
     * @param article 文章信息
     * @return {@link Result<>}
     */
    @ApiOperation(value = "添加文章")
    @SaCheckPermission("blog:article:add")
    @PostMapping("/admin/article/add")
    public Result<?> addArticle(@Validated @RequestBody ArticleDTO article) {
        articleService.addArticle(article);
        return Result.success();
    }

    /**
     * 修改文章
     *
     * @param article 文章信息
     * @return {@link Result<>}
     */
    @ApiOperation(value = "修改文章")
    @SaCheckPermission("blog:article:update")
    @PutMapping("/admin/article/update")
    public Result<?> updateArticle(@Validated @RequestBody ArticleDTO article) {
        articleService.updateArticle(article);
        return Result.success();
    }

    /**
     * 获取文章详情
     *
     * @param articleId 文章id
     * @return {@link Result<ArticleInfoVO>} 后台文章
     */
    @ApiOperation(value = "获取文章详情")
    @SaCheckPermission("blog:article:getInfo")
    @GetMapping("/admin/article/getInfo/{articleId}")
    public Result<ArticleInfoVO> getInfo(@PathVariable("articleId") Integer articleId) {
        return Result.success(articleService.getInfo(articleId));
    }

    /**
     * 上传文章图片
     *
     * @param file 文件
     * @return {@link Result<String>} 文章图片地址
     */
    @ApiOperation(value = "上传文章图片")
    @ApiImplicitParam(name = "file", value = "文章图片", required = true, dataType = "MultipartFile")
    @SaCheckPermission("blog:article:upload")
    @PostMapping("/admin/article/upload")
    public Result<String> saveArticleImages(@RequestParam("file") MultipartFile file) {
        return Result.success(articleService.saveArticleImages(file));
    }

    /**
     * 查看首页文章列表
     *
     * @return {@link Result<ArticleHomeVO>}
     */
    @VisitLogger(value = "首页")
    @ApiOperation(value = "查看首页文章列表")
    @GetMapping("/article/list")
    public Result<PageResult<ArticleHomeVO>> listArticleHomeVO() {
        return Result.success(articleService.listArticleHomeVO());
    }
}
