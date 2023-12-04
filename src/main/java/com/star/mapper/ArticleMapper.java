package com.star.mapper;

import com.star.model.dto.ArticleAdminDTO;
import com.star.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.star.model.vo.ConditionVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleMapper extends BaseMapper<Article> {

    List<ArticleAdminDTO> listArticlesAdmin(@Param("current") Long current, @Param("size") Long size, @Param("conditionVO") ConditionVO conditionVO);

    Integer countArticleAdmins(@Param("conditionVO") ConditionVO conditionVO);

}
