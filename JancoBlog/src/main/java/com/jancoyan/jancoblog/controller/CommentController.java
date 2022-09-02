package com.jancoyan.jancoblog.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jancoyan.jancoblog.model.domain.Article;
import com.jancoyan.jancoblog.model.domain.Comment;
import com.jancoyan.jancoblog.model.domain.PageComment;
import com.jancoyan.jancoblog.model.domain.User;
import com.jancoyan.jancoblog.service.CommentService;
import com.jancoyan.jancoblog.utils.Msg;
import com.jancoyan.jancoblog.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

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


    /**
     * 管理员进行评论管理的时候，获取所有评论
     * @param pn 页码
     * @param limit 容量
     * @param condition 条件
     * @return 成功
     */
    @RequestMapping(value = "/all")
    public Msg listAll(
            @RequestParam(value = "pn")String pn,
            @RequestParam(value = "limit", defaultValue = "10")String limit,
            @RequestParam(value = "condition", defaultValue = "")String condition
    ){
        IPage<Comment> iPage = service.listAll(null, Integer.parseInt(pn),
                Integer.parseInt(limit),
                condition);
        return Msg.success().add("pageInfo", iPage);
    }


    /**
     * 获取用户收到的所有评论
     * @param pn 页码
     * @param limit 容量
     * @param condition 条件
     * @param request request
     * @return 消息
     */
    @RequestMapping(value = "/receive")
    public Msg listCommentByUserReceive(
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
        IPage<Comment> iPage = service.listAll(
                String.valueOf(user.getUserId()),
                Integer.parseInt(pn),
                Integer.parseInt(limit),
                condition);
        return Msg.success().add("pageInfo", iPage);
    }

    /**
     * 获取当前登录的用户所发表的所有评论
     * @param pn 页码
     * @param limit 容量
     * @param condition 条件
     * @param request
     * @return
     */
    @RequestMapping(value = "/posted")
    public Msg listCommentByUserPosted(
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
        if(null == user){
            return Msg.fail().add("msg", "获取失败");
        }
        IPage<Comment> iPage = service.listCommentByUserPosted(
                String.valueOf(user.getUserId()),
                Integer.parseInt(pn),
                Integer.parseInt(limit),
                condition);
        return Msg.success().add("pageInfo", iPage);
    }

    /**
     * 批量删除评论
     * @param ids 评论id
     * @return 消息
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Msg deleteComment(
            @RequestParam(value = "ids") String ids
    ){
        boolean suc = service.batchDeleteComment(ids);
        return Msg.success().add("suc", suc ? "success" : "fail");
    }

    /**
     * 获取某个文章的分页评论
     * @param id 文章id
     * @param pn 页码
     * @param limit 容量，默认为 7
     * @return 消息
     */
    @RequestMapping(value = "/article")
    public Msg listCommentByArticle(
            @RequestParam(value = "id") String id,
            @RequestParam(value = "pn")Integer pn,
            @RequestParam(value = "limit", defaultValue = "7") Integer limit
    ){
        IPage<Comment> iPage = service.listCommentByArticle(id, pn, limit);
        return Msg.success().add("pageInfo", iPage);
    }

    /**
     * 发表评论
     * @param articleId 目标文章
     * @param userName 用户名，游客有效
     * @param email 邮箱，游客有效
     * @param content 内容
     * @param request request，用来获取ip
     * @return 消息
     */
    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public Msg insertComment(
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
            // 游客发表的评论
            comment.setCommentAuthorEmail(email);
            comment.setCommentAuthorName(userName);
        } else {
            // 已经登录的用户发表的评论
            comment.setCommentAuthorName(user.getUserName());
            comment.setCommentAuthorId(user.getUserId());
        }
        comment.setCommentArticleId(articleId);
        comment.setCommentDate(new Date());
        comment.setCommentContent(content);
        comment.setCommentAuthorIp(request.getScheme());

        Article article = new Article();
        article.setArticleId(articleId);
        article = article.selectById();
        article.setArticleCommentCount(article.getArticleCommentCount() + 1);
        article.updateById();

        comment.insert();
        return Msg.success();
    }

    /**
     * 给评论点赞
     * @param id 评论id
     * @return 消息
     */
    @RequestMapping(value = "/like", method = RequestMethod.POST)
    public Msg likeComment(
            @RequestParam(value = "id") Integer id
    ){
        service.likeComment(id);
        return Msg.success();
    }

    /**
     * 获取指定用户最近收到的评论
     * id为-1的时候获取所有评论
     * @param authorId 用户id
     * @return
     */
    @RequestMapping(value = "/recent", method = RequestMethod.GET)
    public Msg listCommentByUserRecentlyReceive(
            @RequestParam(value = "id", defaultValue = "-1") String authorId,
            @RequestParam(value = "pn", defaultValue = "1") String pn,
            @RequestParam(value = "limit", defaultValue = "10") String limit
    ){
        IPage<PageComment> iPage  = service.listCommentByUserRecently(authorId);
        return Msg.success().add("pageInfo", iPage);
    }


}

