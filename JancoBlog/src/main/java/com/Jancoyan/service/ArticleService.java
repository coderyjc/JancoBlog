package com.Jancoyan.service;


import com.Jancoyan.dao.ArticleMapper;
import com.Jancoyan.domain.Article;
import com.Jancoyan.domain.ArticleExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
     * @return alllist
     */
    public List<Article> getAll() {
        ArticleExample example = new ArticleExample();
        example.setOrderByClause("article_post_date DESC");
        return articleMapper.selectByExample(example);
    }

    /**
     * 根据主键查找
     * @param id 主键 - 文章编号
     * @return 查询到的结果
     */
    public Article selectByPrimaryKey(String id){
        return articleMapper.selectByPrimaryKey(id);
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
     * 根据文章名称查找文章 like
     * @param articleName 文章的名字
     * @return 相似文章列表
     */
    public List<Article> getArticlesLikeName(String articleName) {
        ArticleExample articleExample = new ArticleExample();
        ArticleExample.Criteria criteria = articleExample.createCriteria();
        String pattern = "%" + articleName + "%";
        criteria.andArticleTitleLike(pattern);
        List<Article> articles = articleMapper.selectByExample(articleExample);
        return articles;
    }

    /**
     * 直接插入文章
     * @param article 文章实体
     */
    public void submitArticle(Article article) {
        articleMapper.insert(article);
    }

    /**
     * 根据主键删除文章
     * @param id 文章id
     */
    public void deleteByPrimaryKey(String id) {
        articleMapper.deleteByPrimaryKey(id);
    }

    /**
     * 更新不为空的字段
     * @param article 文章id
     */
    public void updateByPrimaryKeySelective(Article article) {
        articleMapper.updateByPrimaryKeySelective(article);
    }

    /**
     * 根据主键查找
     * @param id
     * @return
     */
    public Article getArticleByPrimaryKey(String id) {
        return articleMapper.selectByPrimaryKey(id);
    }

    /**
     * 获取前10名最热的文章
     * @return 文章列表
     */
    public List<Article> getArticlesLimit() {
        ArticleExample example = new ArticleExample();
        example.setOrderByClause("article_view_time desc limit 10");
        List<Article> articles = articleMapper.selectByExample(example);
        return articles;
    }

    /**
     * 获取父类id下面的所有子类型的文章
     * @param id 父类型
     * @return 所有文章
     */
    public List<Article> getArticlesBySuperId(Integer id) {
        ArticleExample example = new ArticleExample();
        ArticleExample.Criteria criteria = example.createCriteria();
        List<Integer> ids = new ArrayList<>(100);
        ids.add(id);
        for(int i = id * 100 + 1; i < 100*(id + 1); i++)
            ids.add(i);
        criteria.andArticleTypeIn(ids);
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

}
