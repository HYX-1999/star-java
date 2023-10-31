package com.star.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.star.model.vo.Result;
import com.star.service.BlogFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件控制器
 **/
@Api(tags = "文件模块")
@RestController
public class BlogFileController {

    @Autowired
    private BlogFileService fileService;

    /**
     * 上传文件
     *
     * @param file 文件
     * @return {@link Result<>}
     */
    @ApiOperation(value = "上传文件")
    @ApiImplicitParam(name = "file", value = "图片", required = true, dataType = "MultipartFile")
    @SaCheckPermission("system:file:upload")
    @PostMapping("/admin/file/upload")
    public Result<?> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("path") String path) {
        fileService.uploadFile(file, path);
        return Result.success();
    }
}
