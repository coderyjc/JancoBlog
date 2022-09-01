package com.Jancoyan.controller;

import com.Jancoyan.domain.ArticleComment;
import com.Jancoyan.service.CommentService;
import com.Jancoyan.utils.Msg;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

/**
 * /comments
 *  - GET 获取某个文章的所有评论
 *  - DELETE 一次性删除多条评论
 *
 * /comment
 *  - POST 添加评论
 *  - GET 获取所有评论（分页）
 *  - DELETE 删除一条评论
 *
 * /getcomment
 *  - GET 从主键获取一条完整评论
 *
 */
@Controller
public class CommentController {

    @Autowired
    CommentService commentService;

    /**
     * 获取所有评论
     * @param page 页数
     * @param limit 每页的上限
     */
    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.GET)
    public Msg getAll(@RequestParam(value = "page", defaultValue = "1") Integer page,
                      @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                      HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        PageHelper.startPage(page, limit);
        List<ArticleComment> comments = commentService.getAll();
        PageInfo<ArticleComment> pageInfo = new PageInfo<>(comments, 10);
        return Msg.success().add("pageInfo", pageInfo);
    }

    /**
     * 从主键删除
     * @param commentId 主键
     * @return 成功
     */
    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.DELETE)
    public Msg deleteComment(Integer commentId){
        commentService.deleteArticleById(commentId);
        return Msg.success();
    }

    // 添加评论的功能，目前还没打算开放注册功能，就先和以前一样写吧。

    /**
     * 添加评论
     * @param articleId 文章id
     * @param nickName 昵称
     * @param email 邮箱（作为主键）
     * @param content 内容
     */
    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Msg addComment(@RequestParam(value = "id") String articleId,
                          String nickName,
                          String email,
                          String content,
                          HttpServletRequest request){
        ArticleComment articleComment = new ArticleComment();
        articleComment.setCommentContent(content);
        articleComment.setArticleId(articleId);
        articleComment.setCommentDate(new Date());
        articleComment.setAuthorEmail(email);
        articleComment.setAuthorNickname(nickName);
        articleComment.setAuthorIp(request.getRemoteAddr());
        commentService.insert(articleComment);
        return Msg.success().add("comment", articleComment);
    }

    /**
     * 获取带有内容的完整的评论记录
     * @param commentId 主键
     * @return 记录
     */
    @ResponseBody
    @RequestMapping(value = "/getcomment", method = RequestMethod.GET)
    public Msg getCommentById(Integer commentId){
        ArticleComment comment = commentService.getCommentById(commentId);
        return Msg.success().add("comment", comment);
    }


    /**
     * 获取同一个文章下的评论
     * @param articleId 文章id
     */
    @ResponseBody
    @RequestMapping(value = "/comments", method = RequestMethod.GET)
    public Msg getCommentByArticleId(
            @RequestParam(value = "id") String articleId){
        List<ArticleComment> list = commentService.getCommentByArticleId(articleId);
        return Msg.success().add("comments", list);
    }

}

