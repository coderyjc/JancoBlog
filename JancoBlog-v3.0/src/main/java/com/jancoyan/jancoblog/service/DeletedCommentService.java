package com.jancoyan.jancoblog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jancoyan.jancoblog.pojo.Comment;
import com.jancoyan.jancoblog.pojo.DeletedComment;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Delete;

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
