package com.jancoyan.jancoblog.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jancoyan.jancoblog.pojo.Article;
import com.jancoyan.jancoblog.pojo.Comment;
import com.jancoyan.jancoblog.pojo.User;
import com.jancoyan.jancoblog.service.CommentService;
import com.jancoyan.jancoblog.utils.Msg;
import com.jancoyan.jancoblog.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jancoyan
 * @since 2021-09-14
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService service;

    @Autowired
    RedisUtil redisUtil;


    @RequestMapping(value = "/all")
    public Msg getAll(
            @RequestParam(value = "pn")String pn,
            @RequestParam(value = "limit", defaultValue = "10")String limit,
            @RequestParam(value = "condition", defaultValue = "")String condition
    ){
        IPage<Comment> iPage = service.getAll(null, Integer.parseInt(pn),
                Integer.parseInt(limit),
                condition);
        return Msg.success().add("pageInfo", iPage);
    }


    @RequestMapping(value = "/receive")
    public Msg getCommentByUserReceive(
            @RequestParam(value = "pn")String pn,
            @RequestParam(value = "limit", defaultValue = "10")String limit,
            @RequestParam(value = "condition", defaultValue = "")String condition,
            HttpServletRequest request
    ){
        // 从token中拿到用户
        String token = request.getHeader("token");
        if(null == token){
            return Msg.expire();
        }
        User user = (User) redisUtil.get(token);
        IPage<Comment> iPage = service.getAll(
                String.valueOf(user.getUserId()),
                Integer.parseInt(pn),
                Integer.parseInt(limit),
                condition);
        return Msg.success().add("pageInfo", iPage);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Msg deleteComment(
            @RequestParam(value = "ids") String ids
    ){
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
        return Msg.success().add("suc", suc ? "success" : "fail");
    }


    @RequestMapping(value = "/article")
    public Msg getCommentByArticle(
            @RequestParam(value = "id") String id,
            @RequestParam(value = "pn")Integer pn,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit
    ){
        IPage<Comment> iPage = service.getCommentByArticle(id, pn, limit);
        return Msg.success().add("pageInfo", iPage);
    }


    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public Msg postComment(
            @RequestParam(value = "articleId")String articleId,
            @RequestParam(value = "name", defaultValue = "") String userName,
            @RequestParam(value = "email", defaultValue = "")String email,
            @RequestParam(value = "content")String content,
            HttpServletRequest request
    ){
//      先判断是不是已经登陆了
        Comment comment = new Comment();
        String token = request.getHeader("token");
        User user = (User) redisUtil.get(token);
        // 用户信息已经过期了
        if(null == user && null != token) {
            return Msg.expire();
        }

        // 如果 token 是 null
        if(null == token || "".equals(token)){
            comment.setCommentAuthorEmail(email);
            comment.setCommentAuthorName(userName);
        } else {
            comment.setCommentAuthorName(user.getUserName());
            comment.setCommentAuthorId(user.getUserId());
            comment.setCommentAuthorEmail(user.getUserEmail());
        }
        comment.setCommentArticleId(articleId);
        comment.setCommentDate(new Date());
        comment.setCommentContent(content);
        comment.setCommentAuthorIp(request.getScheme());

        comment.insert();
        return Msg.success();
    }

    @RequestMapping(value = "/like", method = RequestMethod.POST)
    public Msg likeComment(
            @RequestParam(value = "id") Integer id
    ){
        Comment comment = new Comment();
        comment.setCommentId(id);
        comment = comment.selectById();
        comment.setCommentLikeCount(comment.getCommentLikeCount() + 1);
        comment.updateById();
        return Msg.success();
    }



}

