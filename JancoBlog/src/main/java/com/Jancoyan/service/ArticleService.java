package com.Jancoyan.service;


import com.Jancoyan.dao.ArticleMapper;
import com.Jancoyan.domain.Article;
import com.Jancoyan.domain.ArticleExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jancoyan
 */
@Service
public class ArticleService {

    @Autowired
    ArticleMapper articleMapper;

    /**
     * 获取所有文章
     * 按照发表日期降序排序
     * @return
     */
    public List<Article> getAll() {
        ArticleExample example = new ArticleExample();
        example.setOrderByClause("article_post_date DESC");
        return articleMapper.selectByExample(example);
    }


    /**
     * 根据作者的id筛选文章
     * @param id 作者的id
     * @return 文章列表
     */
    public List<Article> getArticlesByUserId(Integer id) {
        ArticleExample example = new ArticleExample();
        ArticleExample.Criteria criteria = example.createCriteria();
        criteria.andArticleAuthorIdEqualTo(id);
        List<Article> articles = articleMapper.selectByExample(example);
        return articles;
    }

    /**
     * 根据文章的类型筛选文章
     * @param id 类型id
     * @return 文章列表
     */
    public List<Article> getArticlesByTypeId(Integer id) {
        ArticleExample example = new ArticleExample();
        ArticleExample.Criteria criteria = example.createCriteria();
        criteria.andArticleTypeEqualTo(id);
        List<Article> articles = articleMapper.selectByExample(example);
        return articles;
    }

    /**
     * 根据文章名称查找文章 like
     * @param articleName
     * @return
     */
    public List<Article> getArticlesLikeName(String articleName) {
        ArticleExample articleExample = new ArticleExample();
        ArticleExample.Criteria criteria = articleExample.createCriteria();
        criteria.andArticleTitleLike(articleName);
        List<Article> articles = articleMapper.selectByExample(articleExample);
        return articles;
    }

    /**
     * 直接插入文章
     * @param article
     */
    public void submitArticle(Article article) {
        articleMapper.insert(article);
    }
}
