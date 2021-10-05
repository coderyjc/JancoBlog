package com.jancoyan.jancoblog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jancoyan.jancoblog.pojo.Article;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jancoyan
 * @since 2021-09-14
 */
public interface ArticleService extends IService<Article> {

    /**
     * 获取首页的文章列表
     * @param pn 页码
     * @param limit 容量
     * @param condition 条件
     * @return
     */
    IPage<Article> getIndexList(Integer pn, Integer limit, String condition);

    /**
     * 文章管理的时候获得用于管理的文章列表
     * @param userName 用户名，可选
     * @param pn 页码
     * @param limit 容量
     * @param condition 条件
     * @return
     */
    IPage<Article> getManageList(String userName, Integer pn, Integer limit, String condition);

    /**
     * 获取一篇文章
     * @param articleId 文章id
     * @return
     */
    Article getSingleArticle(String articleId);
}
