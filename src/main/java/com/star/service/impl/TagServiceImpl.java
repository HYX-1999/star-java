package com.star.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.star.entity.Tag;
import com.star.mapper.TagMapper;
import com.star.service.TagService;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
}
