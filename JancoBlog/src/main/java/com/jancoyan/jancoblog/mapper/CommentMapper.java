package com.jancoyan.jancoblog.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jancoyan.jancoblog.model.domain.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jancoyan.jancoblog.model.domain.PageComment;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Jancoyan
 * @since 2021-09-14
 */
public interface CommentMapper extends BaseMapper<Comment> {

    IPage<Comment> getAll(IPage<Comment> iPage, Wrapper ew);

    IPage<Comment> getCommentByArticle(IPage<Comment> page, QueryWrapper<Comment> ew);

    IPage<Comment> getCommentByUserPosted(IPage<Comment> iPage, Wrapper ew);

    IPage<PageComment> getCommentByUserRecently(IPage<PageComment> iPage, Wrapper ew);

    void deleteComment(Integer commentId);

    void recoverComment(Integer commentId);
}
