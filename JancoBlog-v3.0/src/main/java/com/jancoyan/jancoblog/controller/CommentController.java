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
    ){




        return Msg.success();
    }


    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public Msg postComment(){



        return Msg.success();
    }


}

