package com.jancoyan.jancoblog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jancoyan.jancoblog.model.domain.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jancoyan.jancoblog.model.domain.PageComment;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jancoyan
 * @since 2021-09-14
 */
public interface CommentService extends IService<Comment> {

    /**
     * 评论管理的时候获取所有评论，用户是获得用户的所有评论
     * @param userId 用户id
     * @param pn 页码
     * @param limit 容量
     * @return
     */
    IPage<Comment> listAll(String userId, Integer pn, Integer limit, String articleTitle,
                           String commentAuthorName, String rankLike, String start, String end);

    /**
     * 获取某个文章的所有评论
     * @param id 文章id
     * @param pn 页码
     * @param limit 容量
     * @return
     */
    IPage<Comment> listCommentByArticle(String id, Integer pn, Integer limit);

    /**
     * 评论管理，获取用户发表的所有评论
     * @param id 用户id
     * @param pn 页码
     * @param limit 容量
     * @return
     */
    IPage<Comment> listCommentByUserPosted(String id, Integer pn, Integer limit,
                                           String articleTitle, String commentAuthorName,
                                           String rankLike, String start, String end);

    /**
     * 获取用户最近获取的评论
     * @param authorId 作者id
     * @return
     */
    IPage<PageComment> listCommentByUserRecently(String authorId);

    /**
     * 按照文章“删除”评论
     * @param articleId
     */
    void deleteCommentByArticle(String articleId);

    /**
     * 按照文章“恢复”评论
     * @param ids 多个id
     */
    void recoverCommentByArticle(String ids);

    /**
     * 批量删除评论
     * @param ids
     * @return
     */
    boolean batchDeleteComment(String ids);

    /**
     * 给评论点赞
     * @param id
     */
    void likeComment(Integer id);
}
