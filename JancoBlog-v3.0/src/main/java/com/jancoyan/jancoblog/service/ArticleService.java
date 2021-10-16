package com.jancoyan.jancoblog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jancoyan.jancoblog.pojo.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jancoyan.jancoblog.pojo.PageArticle;

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
     * @param userId 用户id，可选
     * @param pn 页码
     * @param limit 容量
     * @param condition 条件
     * @return
     */
    IPage<Article> getManageList(Integer userId, Integer pn, Integer limit, String condition);

    /**
     * 获取一篇文章
     * @param articleId 文章id
     * @return
     */
    Article getSingleArticle(String articleId);

    /**
     * 获取所有删除的文章
     * @param userName 用户名，可选
     * @param pn 页码
     * @param limit 容量
     * @param condition 条件
     * @return
     */
    IPage<Article> getDeletedList(Integer userId, Integer pn, Integer limit, String condition);

    /**
     * 彻底删除已经删除了的文章
     * @param ids id
     * @return
     */
    boolean deleteCompletely(String ids);


    /**
     * 批量恢复指定的文章
     * @param ids ids
     * @return
     */
    boolean batchRecoverDeletedArticle(String ids);

    /**
     * 获区用户最近发表的文章
     * @param id 用户id
     * @param pn 页码
     * @param limit 容量
     * @return
     */
    IPage<PageArticle> getArticleByUserRecently(String id, Integer pn, Integer limit);
}
