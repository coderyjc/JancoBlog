package com.jancoyan.jancoblog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jancoyan.jancoblog.model.domain.DeletedComment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jancoyan
 * @since 2021-10-17
 */
public interface DeletedCommentService extends IService<DeletedComment> {


    IPage<DeletedComment> getCommentByArticle(String id, Integer pn, Integer limit);
}
