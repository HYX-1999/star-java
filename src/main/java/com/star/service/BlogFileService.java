package com.star.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.star.entity.BlogFile;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件业务接口
 */
public interface BlogFileService extends IService<BlogFile> {

    /**
     * 上传文件
     *
     * @param file 文件
     * @param path 文件路径
     */
    void uploadFile(MultipartFile file, String path);
}
