package com.jancoyan.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jancoyan.pojo.Comment;
import com.jancoyan.pojo.Tag;
import com.jancoyan.pojo.User;
import com.jancoyan.service.CommentService;
import com.jancoyan.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import javax.servlet.http.HttpSession;

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


}

