package com.Jancoyan.controller;

import com.Jancoyan.domain.ArticleComment;
import com.Jancoyan.service.CommentService;
import com.Jancoyan.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    CommentService commentService;

    /**
     * REST风格
     * /comments
     *  -GET 获取某个文章的所有评论
     *
     * /comment
     *  -POST 添加评论
     */

    /**
     * 插入评论
     * @param id 文章id
     * @param nickName 昵称
     * @param email 邮箱
     * @param content 内容
     * @param request 请求，用来获取ip
     * @return 成功
     */
    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Msg insertComment(
            String id,
            String nickName,
            String email,
            String content,
            HttpServletRequest request){

        // 设置其中的属性
        ArticleComment articleComment = new ArticleComment();
        articleComment.setCommentContent(content);
        articleComment.setArticleId(id);
        articleComment.setCommentDate(new Date());
        articleComment.setAuthorEmail(email);
        articleComment.setAuthorNickname(nickName);
        articleComment.setAuthorIp(request.getRemoteAddr());

        // 进行添加
        commentService.insertComment(articleComment);
        return Msg.success().add("comment", articleComment);
    }

    /**
     * 获取某个文章的所有评论（时间降序）
     * @param id 文章id
     * @return 成功消息
     */
    @ResponseBody
    @RequestMapping(value = "/comments", method = RequestMethod.GET)
    public Msg getCommentByArticleId(String id){
        List<ArticleComment> articleComments =
                commentService.getCommmentByArticleId(id);
        return Msg.success().add("comments", articleComments);
    }

}
