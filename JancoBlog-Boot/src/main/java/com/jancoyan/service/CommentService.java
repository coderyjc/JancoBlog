package com.jancoyan.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jancoyan.pojo.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Jancoyan
 * @since 2021-06-28
 */
public interface CommentService extends IService<Comment> {

    IPage<Comment> selectAllByPageWithArticleName(Integer page, Integer limit);

    IPage<Comment> getUserArticleCommentByUserId(Integer userId, Integer page, Integer limit);
}
