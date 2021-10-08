package com.jancoyan.jancoblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jancoyan.jancoblog.pojo.Article;
import com.jancoyan.jancoblog.pojo.Comment;
import com.jancoyan.jancoblog.mapper.CommentMapper;
import com.jancoyan.jancoblog.pojo.PageComment;
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

    @Override
    public IPage<Comment> getAll(String userId, Integer pn, Integer limit, String condition) {

        // 分页查询
        IPage<Comment> iPage = new Page<>(pn, limit);
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();

        if(userId != null) wrapper.eq("user_id", userId);

        String[] split = condition.split("--");
        for (String item : split) {
            String[] split2 = item.split("=");
            if(split2.length < 2){
                continue;
            }

            if("comment_author_name".equals(split2[0])){
                wrapper.like("comment_author_name", split2[1]);
            }else if("article_title".equals(split2[0])){
                wrapper.like("article_title", split2[1]);
            }else if("start".equals(split2[0])){
                wrapper.gt("comment_date", split2[1]);
            }else if("end".equals(split2[0])){
                wrapper.lt("comment_date", split2[1]);
            }else if("rank_like".equals(split2[0])){
                if ("1".equals(split2[1])) {
                    wrapper.orderByAsc("comment_like_count");
                } else {
                    wrapper.orderByDesc("comment_like_count");
                }
            }
        }
        return baseMapper.getAll(iPage, wrapper);
    }

    @Override
    public IPage<Comment> getCommentByArticle(String id, Integer pn, Integer limit) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        IPage<Comment> page = new Page<>(pn, limit);
        wrapper.eq("comment_article_id", id);
        wrapper.orderByDesc("comment_date");
        return baseMapper.getCommentByArticle(page, wrapper);
    }

    @Override
    public IPage<Comment> getCommentByUserPosted(String id, Integer pn, Integer limit, String condition) {

        // 分页查询
        IPage<Comment> iPage = new Page<>(pn, limit);
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();

        if(id != null) wrapper.eq("comment_author_id", id);

        String[] split = condition.split("--");
        for (String item : split) {
            String[] split2 = item.split("=");
            if(split2.length < 2){
                continue;
            }

            if("comment_author_name".equals(split2[0])){
                wrapper.like("user_id", split2[1]);
            }else if("article_title".equals(split2[0])){
                wrapper.like("article_title", split2[1]);
            }else if("start".equals(split2[0])){
                wrapper.gt("comment_date", split2[1]);
            }else if("end".equals(split2[0])){
                wrapper.lt("comment_date", split2[1]);
            }else if("rank_like".equals(split2[0])){
                if ("1".equals(split2[1])) {
                    wrapper.orderByAsc("comment_like_count");
                } else {
                    wrapper.orderByDesc("comment_like_count");
                }
            }
        }
        return baseMapper.getCommentByUserPosted(iPage, wrapper);
    }

    @Override
    public IPage<PageComment> getCommentByUserRecently(String authorId) {
        IPage<PageComment> iPage = new Page<>(1, 10);
        QueryWrapper<PageComment> wrapper = new QueryWrapper<>();
        wrapper.eq("article_author", authorId);
        wrapper.orderByDesc("comment_date");
        return baseMapper.getCommentByUserRecently(iPage, wrapper);

    }


}
