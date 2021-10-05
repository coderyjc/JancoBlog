package com.jancoyan.jancoblog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jancoyan.jancoblog.pojo.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

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
     * @param condition 条件
     * @return
     */
    IPage<Comment> getAll(String userId, Integer pn, Integer limit, String condition);

    /**
     * 获取某个文章的所有评论
     * @param id 文章id
     * @param pn 页码
     * @param limit 容量
     * @return
     */
    IPage<Comment> getCommentByArticle(String id, Integer pn, Integer limit);
}
