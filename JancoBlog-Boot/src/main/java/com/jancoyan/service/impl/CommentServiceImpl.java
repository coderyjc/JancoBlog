package com.jancoyan.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jancoyan.pojo.Comment;
import com.jancoyan.mapper.CommentMapper;
import com.jancoyan.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jancoyan
 * @since 2021-06-28
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Override
    public IPage<Comment> selectAllByPageWithArticleName(Integer page, Integer limit) {
        IPage<Comment> iPage = new Page<>(page, limit);
        return baseMapper.selectAllByPageWithArticleName(iPage, null);
    }
}
