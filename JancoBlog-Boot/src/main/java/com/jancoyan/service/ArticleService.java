package com.jancoyan.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jancoyan.mapper.ArticleMapper;
import com.jancoyan.pojo.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Jancoyan
 * @since 2021-06-28
 */
public interface ArticleService extends IService<Article> {

    IPage<Article> selectAllByPage(Integer pn, Integer limit);

    List<Article> getArticleRankByView();

    List<Article> getArticleRankByLike();

    List<Article> getArticleRankByComment();

    IPage<Article> selectUserArticleByPage(Integer page, Integer limit, String id);

    Article selectByPrimaryKeyWithAuthorName(String articleId);

    List<Article> selectArticleByType(String typeId);

    List<Article> selectArticleByTagId(String tagId);
}
