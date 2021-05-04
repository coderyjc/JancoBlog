package com.Jancoyan.controller;

import com.Jancoyan.domain.ArticleComment;
import com.Jancoyan.domain.ArticleCommentKey;
import com.Jancoyan.service.CommentService;
import com.Jancoyan.utils.Msg;
import com.Jancoyan.utils.TimeUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
     *  -GET 获取所有评论（分页）
     *  -PUT 删除一条评论
     *
     * /getcomment
     *  -GET从主键获取一条评论
     */

    /**
     * 由主键查询
     * @param articleId 文章id
     * @param email 用户邮箱
     * @param commentDate 评论日期
     * @return 查到的评论
     */
    @ResponseBody
    @RequestMapping(value = "/getcomment", method = RequestMethod.GET)
    public Msg getCommentByPrimaryKey(
            String articleId,
            String email,
            String commentDate
    ){
        // 进行封装
        ArticleCommentKey articleCommentKey = new ArticleCommentKey();
        articleCommentKey.setAuthorEmail(email);
        articleCommentKey.setArticleId(articleId);
        articleCommentKey.setCommentDate(TimeUtils.caseDateStringToDateTypeYMDHMS(commentDate));
        // 进行传输
        ArticleComment articleComment = commentService.getCommentByPrimaryKey(articleCommentKey);
        return Msg.success().add("comment", articleComment);
    }


    /**
     * 删除评论
     * @param articleId 文章id
     * @param email 评论人邮箱
     * @param commentDate 评论日期
     * @return 成功msg
     */
    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.PUT)
    public Msg deleteComment(
        String articleId,
        String email,
        String commentDate
    ){
        // 构造主键
        ArticleCommentKey articleCommentKey = new ArticleCommentKey();
        articleCommentKey.setArticleId(articleId);
        articleCommentKey.setAuthorEmail(email);
        articleCommentKey.setCommentDate(TimeUtils.caseDateStringToDateTypeYMDHMS(commentDate));

        // 由主键删除
        commentService.deleteByPrimaryKey(articleCommentKey);
        return Msg.success();
    }


    /**
     * 获取所有评论（分页）, 按照文章排序
     * @param pn 页码
     * @return 包含分页信息的消息
     */
    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.GET)
    public Msg getAll(
            Integer pn
    ){
        PageHelper.startPage(pn, 15);
        List<ArticleComment> comments = commentService.getAll();
        PageInfo<ArticleComment> pageInfo = new PageInfo<>(comments, 5);
        return Msg.success().add("pageInfo", pageInfo);
    }


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
