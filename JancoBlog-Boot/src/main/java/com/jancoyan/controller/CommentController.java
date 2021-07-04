package com.jancoyan.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jancoyan.pojo.Comment;
import com.jancoyan.pojo.User;
import com.jancoyan.service.CommentService;
import com.jancoyan.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * @author Jancoyan
 * @since 2021-06-28
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @RequestMapping(value = "/all")
    public Msg getAll(Integer page, Integer limit){
        IPage<Comment> iPage = commentService.selectAllByPageWithArticleName(page, limit);
        return Msg.success().add("pageInfo", iPage);
    }

    @RequestMapping(value = "/comment", method = RequestMethod.GET)
    public Msg getUserArticleComment(
            Integer page, Integer limit,
            HttpSession session
    ){
        User user = (User) session.getAttribute("user");
        Integer userId = user.getUserId();
        IPage<Comment> iPage = commentService.getUserArticleCommentByUserId(userId, page, limit);
        return Msg.success().add("pageInfo", iPage);
    }


    @RequestMapping(value = "/comment", method = RequestMethod.DELETE)
    public Msg deleteComment(
            @RequestParam("id") String commentIds
    ){
        // 多个tag的id会用 & 连起来，用 & 把不同的tagid分割
        String[] ids = {commentIds};
        if (commentIds.contains("&")){
            ids = commentIds.split("&");
        }
        Comment comment = new Comment();
        for (String id: ids) {
            comment.setCommentId(Integer.parseInt(id));
            comment.deleteById();
        }
        return Msg.success();
    }

    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Msg postComment(
            @RequestParam("id") String articleId,
            @RequestParam("nickname") String nickName,
            @RequestParam("email") String email,
            @RequestParam("content") String content,
            HttpServletRequest request,
            HttpSession session
    ){
        // 评论成功之后把评论传回去，进行回调
        Comment comment = new Comment();
        User user = (User) session.getAttribute("user");
        // 封装
        if(user != null){
            comment.setCommentAuthorId(user.getUserId());
            comment.setCommentAuthorEmail(user.getUserEmail());
            comment.setCommentAuthorNickname(user.getUserNickname());
        } else {
            comment.setCommentAuthorEmail(email);
            comment.setCommentAuthorNickname(nickName);
        }
        comment.setCommentId(commentService.getMaxCommentId() + 1);
        comment.setCommentArticleId(articleId);
        comment.setCommentContent(content);
        comment.setCommentDate(new Date());
        comment.setCommentAuthorIp(request.getRemoteAddr());
        // 插入
        comment.insert();
        return Msg.success().add("comment", comment);
    }

    @RequestMapping(value = "/article", method = RequestMethod.GET)
    public Msg getArticleComment(@RequestParam("id") String articleId){
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("comment_article_id", articleId);
        Comment comment = new Comment();
        List<Comment> commentList = comment.selectList(wrapper);
        return Msg.success().add("pageInfo", commentList);
    }


}

