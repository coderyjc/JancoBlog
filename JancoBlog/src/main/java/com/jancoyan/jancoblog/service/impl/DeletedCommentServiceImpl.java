package com.jancoyan.jancoblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jancoyan.jancoblog.model.domain.DeletedComment;
import com.jancoyan.jancoblog.mapper.DeletedCommentMapper;
import com.jancoyan.jancoblog.service.DeletedCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jancoyan
 * @since 2021-10-17
 */
@Service
public class DeletedCommentServiceImpl extends ServiceImpl<DeletedCommentMapper, DeletedComment> implements DeletedCommentService {

    @Override
    public IPage<DeletedComment> getCommentByArticle(String id, Integer pn, Integer limit) {
        QueryWrapper<DeletedComment> wrapper = new QueryWrapper<>();
        IPage<DeletedComment> page = new Page<>(pn, limit);
        wrapper.eq("comment_article_id", id);
        wrapper.orderByDesc("comment_date");
        return baseMapper.getCommentByArticle(page, wrapper);
    }



}
