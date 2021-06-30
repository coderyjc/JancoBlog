package com.jancoyan.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.jancoyan.pojo.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Jancoyan
 * @since 2021-06-28
 */
public interface ArticleMapper extends BaseMapper<Article> {

    List<Article> selectOrderByArticleViewCount();
    List<Article> selectOrderByArticleLikeCount();
    List<Article> selectOrderByArticleCommentCount();

}
