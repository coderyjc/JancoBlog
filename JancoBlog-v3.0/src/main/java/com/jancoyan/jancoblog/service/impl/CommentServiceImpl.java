package com.jancoyan.jancoblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jancoyan.jancoblog.pojo.Article;
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

    @Override
    public IPage<Comment> getAll(Integer pn, Integer limit, String condition) {

        // 分页查询
        IPage<Comment> iPage = new Page<>(pn, limit);
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();

        String[] split = condition.split("--");
        for (String item : split) {
            String[] split2 = item.split("=");
            if(split2.length < 2){
                continue;
            }

            if(split2[0].equals("comment_author_name")){
                wrapper.like("comment_author_name", split2[1]);
            }else if(split2[0].equals("article_title")){
                wrapper.like("article_title", split2[1]);
            }else if(split2[0].equals("start")){
                wrapper.gt("comment_date", split2[1]);
            }else if(split2[0].equals("end")){
                wrapper.lt("comment_date", split2[1]);
            }else if(split2[0].equals("rank_like")){
                if (split2[1].equals("1")) {
                    wrapper.orderByAsc("comment_like_count");
                } else {
                    wrapper.orderByDesc("comment_like_count");
                }
            }
        }
        return baseMapper.getAll(iPage, wrapper);
    }
}
