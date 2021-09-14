package com.jancoyan.jancoblog.service.impl;

import com.jancoyan.jancoblog.pojo.Comment;
import com.jancoyan.jancoblog.mapper.CommentMapper;
import com.jancoyan.jancoblog.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jancoyan
 * @since 2021-09-14
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
