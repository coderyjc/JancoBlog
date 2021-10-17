package com.jancoyan.jancoblog.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jancoyan.jancoblog.pojo.DeletedComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Jancoyan
 * @since 2021-10-17
 */
public interface DeletedCommentMapper extends BaseMapper<DeletedComment> {

    IPage<DeletedComment> getCommentByArticle(IPage<DeletedComment> iPage, Wrapper ew);
}
