package com.star.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.star.entity.BlogFile;
import com.star.mapper.BlogFileMapper;
import com.star.service.BlogFileService;
import com.star.strategy.UploadStrategy;
import com.star.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 文件业务接口实现类
 */
@Service
public class BlogFileServiceImpl extends ServiceImpl<BlogFileMapper, BlogFile> implements BlogFileService {

    @Autowired
    private BlogFileMapper blogFileMapper;

    @Autowired
    private UploadStrategy uploadStrategy;

    @Override
    public void uploadFile(MultipartFile file, String path) {
        try {
            // 上传文件
            String url = uploadStrategy.uploadFile(file, path);
            // 获取文件md5值
            String md5 = FileUtils.getMd5(file.getInputStream());
            // 获取文件扩展名
            String extName = FileUtils.getExtension(file);
            BlogFile existFile = blogFileMapper.selectOne(new LambdaQueryWrapper<BlogFile>()
                    .select(BlogFile::getId)
                    .eq(BlogFile::getFileName, md5)
                    .eq(BlogFile::getFilePath, path));
            Assert.isNull(existFile, "文件已存在");
            // 保存文件信息
            BlogFile newFile = BlogFile.builder()
                                       .fileUrl(url)
                                       .fileName(md5)
                                       .filePath("/" + path)
                                       .extendName(extName)
                                       .fileSize((int) file.getSize())
                                       .isDir(0)
                                       .build();
            blogFileMapper.insert(newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
