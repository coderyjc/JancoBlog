package com.jancoyan.mapper;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

import com.jancoyan.pojo.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author Jancoyan
 * @since 2021-06-28
 */
public interface ArticleMapper extends BaseMapper<Article> {

    List<Article> selectOrderByArticleViewCount();
    List<Article> selectOrderByArticleLikeCount();
    List<Article> selectOrderByArticleCommentCount();
    List<Article> selectArticleByType(String typeId);
    List<Article> selectArticleByTagId(String tagId);
    Article selectByIdWithAuthorName(String articleId);
    IPage<Article> selectAllWithAuthorNameByPage(IPage<Article> iPage, Wrapper ew);
}
