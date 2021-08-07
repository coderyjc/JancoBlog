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

    IPage<Article> selectAllWithAuthorNameByPage(Integer page, Integer limit, String search);

    IPage<Article> selectAllByPage(Integer pn, Integer limit);

    Article selectByPrimaryKeyWithAuthorName(String articleId);

    IPage<Article> selectUserArticleByPage(Integer page, Integer limit, String id);

    List<Article> getArticleRankByComment();
}
