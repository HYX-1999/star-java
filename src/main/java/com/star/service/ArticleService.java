package com.star.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.star.entity.Article;
import com.star.model.dto.ArticleAdminDTO;
import com.star.model.dto.PageResultDTO;
import com.star.model.vo.*;

public interface ArticleService extends IService<Article> {

    PageResultDTO<ArticleAdminDTO> listArticlesAdmin(ConditionVO conditionVO);

    void saveOrUpdateArticle(ArticleVO articleVO);

}
