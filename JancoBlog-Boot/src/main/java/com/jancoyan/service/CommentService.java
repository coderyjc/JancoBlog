package com.jancoyan.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jancoyan.pojo.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jancoyan
 * @since 2021-06-28
 */
public interface CommentService extends IService<Comment> {

    IPage<Comment> selectAllByPageWithArticleName(Integer page, Integer limit);

}
