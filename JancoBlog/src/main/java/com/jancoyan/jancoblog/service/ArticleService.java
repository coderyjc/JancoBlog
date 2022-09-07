package com.jancoyan.jancoblog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jancoyan.jancoblog.model.domain.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jancoyan.jancoblog.model.domain.PageArticle;

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
     * 首页文章
     * @param pn
     * @param limit
     * @param userName
     * @param articleTitle
     * @param articleType
     * @param start
     * @param end
     * @param articleViewCount
     * @param articleLikeCount
     * @param articleCommentCount
     * @return
     */
    IPage<Article> listArticleIndex(Integer pn, Integer limit, String userName,
                                    String articleTitle, String articleType, String start,
                                    String end, String articleViewCount,
                                    String articleLikeCount, String articleCommentCount);

    /**
     * 文章管理的时候获得用于管理的文章列表
     * @param userId 用户id，可选
     * @param pn 页码
     * @param limit 容量
     * @return
     */
    IPage<Article> listArticleManage(Integer userId, Integer pn, Integer limit, String userName,
                                     String articleTitle, String articleType, String start,
                                     String end, String articleViewCount,
                                     String articleLikeCount, String articleCommentCount);

    /**
     * 获取所有删除的文章
     * @param userId 用户名，可选
     * @param pn 页码
     * @param limit 容量
     * @return
     */
    IPage<Article> listDeleted(Integer userId, Integer pn, Integer limit, String userName,
                               String articleTitle, String articleType, String start,
                               String end, String articleViewCount,
                               String articleLikeCount, String articleCommentCount);

    /**
     * 获取一篇文章
     * @param articleId 文章id
     * @return
     */
    Article getArticleSingle(String articleId);

    /**
     * 获取单个已经删除的文章
     * @param articleId
     * @return
     */
    Article getArticleSingleDeleted(String articleId);


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
     * 批量删除指定文章
     * @param ids
     * @return
     */
    boolean batchDeleteArticle(String ids);

    /**
     * 获区用户最近发表的文章
     * @param id 用户id
     * @param pn 页码
     * @param limit 容量
     * @return
     */
    IPage<PageArticle> listArticleUserRecently(String id, Integer pn, Integer limit);

    /**
     * 给文章点赞
     * @param id 文章id
     */
    void addLikeCount(String id);

    /**
     * 取消文章的点赞
     * @param id 文章id
     */
    void subLikeCount(String id);

    /**
     * 增加文章的浏览量
     * @param id
     */
    void addViewCount(String id);

    /**
     * 改变文章是否评论的状态
     * @param id 文章id
     * @return
     */
    boolean updateIsComment(String id);

    /**
     * 改变文章的置顶状态
     * @return
     */
    boolean updateIsTop(String id);

    /**
     * 获取文章用于修改
     * @return
     */
    Article getArticleEdit(String id);
}
