package com.star.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.star.entity.ArticleTag;
import com.star.mapper.ArticleTagMapper;
import com.star.service.ArticleTagService;
import org.springframework.stereotype.Service;

@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {
}
