package com.jancoyan.jancoblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jancoyan.jancoblog.pojo.Article;
import com.jancoyan.jancoblog.pojo.Comment;
import com.jancoyan.jancoblog.mapper.CommentMapper;
import com.jancoyan.jancoblog.pojo.DeletedComment;
import com.jancoyan.jancoblog.pojo.PageComment;
import com.jancoyan.jancoblog.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.lettuce.core.dynamic.annotation.CommandNaming;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public IPage<Comment> listAll(String userId, Integer pn, Integer limit, String condition) {

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
    public IPage<Comment> listCommentByArticle(String id, Integer pn, Integer limit) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        IPage<Comment> page = new Page<>(pn, limit);
        wrapper.eq("comment_article_id", id);
        wrapper.orderByDesc("comment_date");
        return baseMapper.getCommentByArticle(page, wrapper);
    }

    @Override
    public IPage<Comment> listCommentByUserPosted(String id, Integer pn, Integer limit, String condition) {

        // 分页查询
        IPage<Comment> iPage = new Page<>(pn, limit);
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();

        if(id != null) {
            wrapper.eq("comment_author_id", id);
        }

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
    public IPage<PageComment> listCommentByUserRecently(String authorId) {
        IPage<PageComment> iPage = new Page<>(1, 10);
        QueryWrapper<PageComment> wrapper = new QueryWrapper<>();
        wrapper.eq("article_author", authorId);
        wrapper.orderByDesc("comment_date");
        return baseMapper.getCommentByUserRecently(iPage, wrapper);

    }

    @Override
    public void deleteCommentByArticle(String ids) {
        // 先查出来评论，再依次删除
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        Comment comment = new Comment();
        Article article = new Article();
        if(!ids.contains("&")){
            // 将删除文章的评论挪到另一张表中
            wrapper.eq("comment_article_id", ids);
            List<Comment> list = comment.selectList(wrapper);
            if(!list.isEmpty()){
                for (Comment item : list){
                    baseMapper.deleteComment(item.getCommentId());
                }
            }
        } else {
            String[] id = ids.split("&");
            for (String articleId : id) {
                wrapper.eq("comment_article_id", articleId);
                List<Comment> list = comment.selectList(wrapper);
                if(!list.isEmpty()){
                    for (Comment item : list){
                        baseMapper.deleteComment(item.getCommentId());
                    }
                }
            }
        }

    }

    @Override
    public void recoverCommentByArticle(String ids) {
        QueryWrapper<DeletedComment> wrapper = new QueryWrapper<>();
        DeletedComment comment = new DeletedComment();
        // 先查出来评论，再依次恢复
        if(!ids.contains("&")){
            wrapper.eq("comment_article_id", ids);
            List<DeletedComment> list = comment.selectList(wrapper);
            if(!list.isEmpty()){
                for (DeletedComment item : list){
                    baseMapper.recoverComment(item.getCommentId());
                }
            }
        } else {
            String[] id = ids.split("&");
            for (String article : id) {
                wrapper.eq("comment_article_id", article);
                List<DeletedComment> list = comment.selectList(wrapper);
                if(!list.isEmpty()){
                    for (DeletedComment item : list){
                        baseMapper.recoverComment(item.getCommentId());
                    }
                }
            }
        }
    }

    @Override
    public boolean batchDeleteComment(String ids) {
        Comment comment = new Comment();
        boolean suc = false;
        if(!ids.contains("&")){
            comment.setCommentId(Integer.parseInt(ids));
            suc = comment.deleteById();
        } else {
            String[] id = ids.split("&");
            for (String item : id) {
                comment.setCommentId(Integer.parseInt(item));
                suc = comment.deleteById();
            }
        }
        return suc;
    }

    @Override
    public void likeComment(Integer id) {
        Comment comment = new Comment();
        comment.setCommentId(id);
        comment = comment.selectById();
        comment.setCommentLikeCount(comment.getCommentLikeCount() + 1);
        comment.updateById();
    }
}
