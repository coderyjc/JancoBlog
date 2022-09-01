package com.jancoyan.jancoblog.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jancoyan.jancoblog.pojo.*;
import com.jancoyan.jancoblog.service.ArticleService;
import com.jancoyan.jancoblog.service.CommentService;
import com.jancoyan.jancoblog.service.LikeRecordService;
import com.jancoyan.jancoblog.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jancoyan
 * @since 2021-09-14
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    ArticleService service;

    @Autowired
    CommentService  commentService;

    @Autowired
    LikeRecordService likeRecordService;

    @Autowired
    RedisUtil redisUtil;

    /**
     * 获取首页的文章，带有搜索功能
     * @param pn 第几页
     * @param limit 每一页的大小
     * @param condition 条件
     * @return 标准的pageInfo
     */
    @RequestMapping(value = "/all")
    public Msg listArticleIndex(
            @RequestParam(value = "pn")String pn,
            @RequestParam(value = "limit", defaultValue = "10")String limit,
            @RequestParam(value = "condition", defaultValue = "")String condition
    ){
        IPage<Article> iPage = service.listArticleIndex(Integer.parseInt(pn),
                Integer.parseInt(limit),
                condition);
        return Msg.success().add("pageInfo", iPage);
    }

    /**
     * 获取管理的文章管理列表的文章
     * @param pn 第几页
     * @param limit 容量
     * @param condition 条件
     * @return 成功
     */
    @RequestMapping(value = "/manage")
    public Msg listArticleManageAll(
            @RequestParam(value = "pn")String pn,
            @RequestParam(value = "limit", defaultValue = "10")String limit,
            @RequestParam(value = "condition", defaultValue = "")String condition,
            HttpServletRequest request
    ){
        String token = request.getHeader("token");
        if(null == token){
            // 用户登录信息过期了
            return Msg.expire();
        }
        IPage<Article> iPage = service.listArticleManage(null,
                Integer.parseInt(pn),
                Integer.parseInt(limit),
                condition);
        return Msg.success().add("pageInfo", iPage);
    }

    /**
     * 获取当前登录的用户发表的所有文章
     * @param pn 页码
     * @param limit 容量
     * @param condition 条件
     * @param request 获取token
     * @return 成功
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Msg listArticleManageUser(
            @RequestParam(value = "pn")String pn,
            @RequestParam(value = "limit", defaultValue = "10")String limit,
            @RequestParam(value = "condition", defaultValue = "")String condition,
            HttpServletRequest request){
        // 从token中拿到用户
        String token = request.getHeader("token");
        if(null == token){
            // 用户信息已经过期了
            return Msg.expire();
        }
        User user = (User) redisUtil.get(token);
        if(null == user){
            return Msg.fail();
        }
        IPage<Article> iPage = service.listArticleManage(
                user.getUserId(),
                Integer.parseInt(pn),
                Integer.parseInt(limit),
                condition);
        return Msg.success().add("pageInfo", iPage);
    }

    /**
     * 获取全站所有删除的文章
     * @param pn 页码
     * @param limit 容量
     * @param condition 条件
     * @param request 获取token
     * @return
     */
    @RequestMapping(value = "/deleted/all", method = RequestMethod.GET)
    public Msg listDeletedAll(
            @RequestParam(value = "pn")String pn,
            @RequestParam(value = "limit", defaultValue = "10")String limit,
            @RequestParam(value = "condition", defaultValue = "")String condition,
            HttpServletRequest request
    ){
        String token = request.getHeader("token");
        if(null == token){
            // 用户登录信息过期了
            return Msg.expire();
        }
        IPage<Article> iPage = service.listDeleted(
                null,
                Integer.parseInt(pn),
                Integer.parseInt(limit),
                condition);
        return Msg.success().add("pageInfo", iPage);
    }

    /**
     * 获取用户删除的文章
     * @param pn 页码
     * @param limit 容量
     * @param condition 条件
     * @param request 获取token
     * @return
     */
    @RequestMapping(value = "/deleted/user", method = RequestMethod.GET)
    public Msg listDeletedUser(
            @RequestParam(value = "pn")String pn,
            @RequestParam(value = "limit", defaultValue = "10")String limit,
            @RequestParam(value = "condition", defaultValue = "")String condition,
            HttpServletRequest request
    ){
        // 从token中拿到用户
        String token = request.getHeader("token");
        if(null == token){
            // 用户信息已经过期了
            return Msg.fail();
        }
        User user = (User) redisUtil.get(token);
        if(null == user){
            return Msg.expire();
        }
        IPage<Article> iPage = service.listDeleted(
                user.getUserId(),
                Integer.parseInt(pn),
                Integer.parseInt(limit),
                condition);
        return Msg.success().add("pageInfo", iPage);
    }

    /**
     * 彻底删除已经删除了的文章
     * @param ids id
     * @param request
     * @return
     */
    @RequestMapping(value = "/deleted/delete", method = RequestMethod.POST)
    public Msg deleteArticleDeleted(
            String ids,
            HttpServletRequest request
    ){
        String token = request.getHeader("token");
        if(null == token){
            // 未登录
            return Msg.fail();
        }
        boolean suc = service.deleteCompletely(ids);
        return Msg.success().add("suc", suc);
    }

    /**
     * 批量恢复用户已经删除的文章
     * @param ids id
     * @param request
     * @return
     */
    @RequestMapping(value = "/deleted/recover", method = RequestMethod.POST)
    public Msg recoverArticle(
            String ids,
            HttpServletRequest request
    ){
        // 验证用户登录
        String token = request.getHeader("token");
        if(null == token){
            return Msg.fail();
        }
        // 恢复删除的文章
        boolean suc = service.batchRecoverDeletedArticle(ids);
        // 恢复删除的评论
        commentService.recoverCommentByArticle(ids);
        return Msg.success().add("suc", suc);
    }

    /**
     * 批量删除文章
     * @param ids 文章id，以 & 连接
     * @return 成功/失败
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Msg deleteArticle(
            String ids,
            HttpServletRequest request
    ){
        String token = request.getHeader("token");
        if(null == token){
            // 用户未登录
            return Msg.fail();
        }
        // 批量删除文章
        boolean suc = service.batchDeleteArticle(ids);
        commentService.deleteCommentByArticle(ids);
        return Msg.success().add("suc", suc);
    }


    /**
     * 获取文章用于编辑
     * @param id 文章id
     * @param request
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public Msg getArticleEdit(
            @RequestParam(value = "id") String id,
            HttpServletRequest request){
        String token = request.getHeader("token");
        if(null == token){
            // 用户未登录
            return Msg.fail();
        }
        Article article = service.getArticleEdit(id);
        return Msg.success().add("article", article);
    }

    /**
     * 查看文章的时候获取单个文章
     * @param articleId 文章ID
     * @return 成功
     */
    @RequestMapping(value = "/single", method = RequestMethod.GET)
    public Msg getArticleSingle(
            @RequestParam(value = "id") String articleId
    ){
        Article article = service.getArticleSingle(articleId);
        return Msg.success().add("article", article);
    }

    /**
     * 查看被删除的文章的时候获取单个文章
     * @param articleId 文章ID
     * @return 成功
     */
    @RequestMapping(value = "/single/deleted", method = RequestMethod.GET)
    public Msg getArticleSingleDeleted(
            @RequestParam(value = "id") String articleId
    ){
        Article article = service.getArticleSingleDeleted(articleId);
        return Msg.success().add("article", article);
    }

    /**
     * 获取用户最近发布的文章 10 个
     * @param id 用户id
     * @param pn 页码
     * @param limit 容量
     * @return
     */
    @RequestMapping(value = "/recent", method = RequestMethod.GET)
    public Msg listArticleUserRecently(
            @RequestParam(value = "id") String id,
            @RequestParam(value = "pn", defaultValue = "1")Integer pn,
            @RequestParam(value = "limit" ,defaultValue = "10")Integer limit
    ){
        IPage<PageArticle> iPage = service.listArticleUserRecently(id, pn, limit);
        return Msg.success().add("pageInfo", iPage);
    }


    /**
     * 点赞, 游客不能点赞
     * @param id 点赞的文章
     * @return 成功
     */
    @RequestMapping(value = "/like", method = RequestMethod.POST)
    public Msg addLikeCount(
            @RequestParam(value = "id")String id,
            HttpServletRequest request
    ){
        //        登录认证
        String token = request.getHeader("token");
        if(null == token){
            // 未登录，说明是游客
            return Msg.loginNeeded();
        }
        User user = (User) redisUtil.get(token);
        if(null == user){
            return Msg.expire();
        }

        service.addLikeCount(id);
        likeRecordService.insertRecord(user.getUserId(), id);

        return Msg.success();
    }

    /**
     * 取消点赞
     * @param id 点赞的文章
     * @return 成功
     */
    @RequestMapping(value = "/dislike", method = RequestMethod.POST)
    public Msg subLikeCount(
            @RequestParam(value = "id")String id,
            HttpServletRequest request
    ){
        //        登录认证
        String token = request.getHeader("token");
        if(null == token){
            // 未登录，说明是游客
            return Msg.loginNeeded();
        }
        User user = (User) redisUtil.get(token);

        service.subLikeCount(id);
        likeRecordService.deleteRecord(user.getUserId(), id);

        return Msg.success();
    }

    /**
     * 浏览
     * @param id 点开的文章的id
     * @return 成功
     */
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public Msg addViewCount(@RequestParam(value = "id")String id){
        service.addViewCount(id);
        return Msg.success();
    }

    /**
     * 改变当前评论的状态
     * @param id 评论的id
     * @return
     */
    @RequestMapping(value = "/toggle/comment", method = RequestMethod.POST)
    public Msg updateIsComment(
            @RequestParam(value = "id")String id,
            HttpServletRequest request
    ){
        String token = request.getHeader("token");
        if(null == token){
            // 用户未登录
            return Msg.fail();
        }

        boolean suc = service.updateIsComment(id);

        if(suc) {
            return Msg.success();
        } else {
            return Msg.fail();
        }
    }

    /**
     * 置顶指定的博文
     * @param id 博文的id
     * @return
     */
    @RequestMapping(value = "/toggle/top", method = RequestMethod.POST)
    public Msg updateArticleTop(
            @RequestParam(value = "id")String id,
            HttpServletRequest request
    ){
        String token = request.getHeader("token");
        if(null == token){
            // 用户未登录
            return Msg.fail();
        }

        boolean suc = service.updateIsTop(id);
        if(suc) {
            return Msg.success();
        } else {
            return Msg.fail();
        }
    }

    /**
     * 文章中图片上传
     * @param file 图片
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/picture", method = RequestMethod.POST)
    public Msg uploadPicture(
            @RequestParam(value = "file") MultipartFile file,
            HttpServletRequest request
    ) throws IOException {
        if (file == null) {
            return Msg.fail().add("msg", "请选择要上传的图片");
        }
        if (file.getSize() > 1024 * 1024 * 10) {
            return Msg.fail().add("msg", "文件大小不能大于10M");
        }
        //获取文件后缀
        String suffix = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        if (!"jpg,jpeg,gif,png".toUpperCase().contains(suffix.toUpperCase())) {
            return Msg.fail().add("msg", "请选择jpg,jpeg,gif,png格式的图片");
        }

        // 获取当前年-月， 如 2021-01
        String nowMonth = TimeUtils.getCurrentTimeString().substring(0, 7);
        String savePath = ConstantUtil.STATIC_RESOURCES + "/p/" + nowMonth + "/";

        File savePathFile = new File(savePath);
        if (!savePathFile.exists()) {
            //若不存在该目录，则创建目录
            savePathFile.mkdir();
        }

        //通过UUID生成唯一文件名
        String filename = UUID.randomUUID().toString().replaceAll("-","") + "." + suffix;

        try {
            //将文件保存指定目录
            file.transferTo(new File(savePath + filename));
        } catch (Exception e) {
            e.printStackTrace();
            return Msg.fail().add("msg", "保存文件异常");
        }

        String url =  ConstantUtil.STATIC_URL + "/p/" + nowMonth + "/" + filename;

        //返回文件名称
        return Msg.success().add("url", url);
    }



    /**
     * 发表文章
     * @param title 标题
     * @param type 类型
     * @param summary 摘要
     * @param comment 是否允许评论
     * @param md md格式的内容
     * @param html 不加修饰的html格式的内容
     * @param request request
     * @return 消息
     * @throws UnsupportedEncodingException 设置编码格式
     */
    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public Msg insertArticle(
            @RequestParam(value = "title") String title,
            @RequestParam(value = "type") String type,
            @RequestParam(value = "summary") String summary,
            @RequestParam(value = "comment") String comment,
            @RequestParam(value = "md") String md,
            @RequestParam(value = "html") String html,
            HttpServletRequest request
    ) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        // 判断用户登录状态
        String token = request.getHeader("token");
        User user;
        if(null == token){
            // 用户未登录
            return Msg.fail();
        }else{
            user = (User) redisUtil.get(token);
            if(null == user) {
                return Msg.expire();
            }
        }
        // 此时User已经拿到了。组装文章
        Article article = new Article();
        article.setArticleTitle(title)
                .setArticleAuthor(user.getUserId())
                .setArticleType(Integer.parseInt(type))
                .setArticleHtml(ArticleUtils.simplifyImages(html))
                .setArticleMd(ArticleUtils.replaceSingleSlash(md))
                .setArticleIsComment("true".equals(comment) ? 1 : 0)
                .setArticleRank(0);

        // 统一发布时间
        long now = System.currentTimeMillis();
        article.setArticleId(ArticleUtils.getArticleId(user.getUserId(), now))
                .setArticlePostTime(new Date(now))
                .setArticleEditTime(new Date(now));

        // 填充文章摘要
        if(!"".equals(summary)){
            article.setArticleSummary(summary);
        }else{
            article.setArticleSummary(ArticleUtils.getArticleDefaultSummary(html));
        }

        // 向文章——图片表中插入记录
        List<String> images = ArticleUtils.getPicturesInArticle(html);
        // 向 file-image 表中插入文章图片记录
        ArticleImage articleImage = new ArticleImage();
        for (String image : images) {
            articleImage.setInsertDate(new Date(now));
            articleImage.setArticleId(article.getArticleId());
            articleImage.setFilename(image);
            articleImage.insert();
        }

        boolean suc = article.insert();

        return Msg.success().add("suc", suc).add("id", article.getArticleId());
    }

    /**
     * 修改文章
     * @param id 修改的文章的id
     * @param title 标题
     * @param type 类型
     * @param summary 摘要
     * @param comment 是否允许评论
     * @param md md格式的内容
     * @param html 不加修饰的html格式的内容
     * @param request request
     * @return 消息
     * @throws UnsupportedEncodingException 设置编码格式
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Msg updateArticle(
            @RequestParam(value = "id") String id,
            @RequestParam(value = "title") String title,
            @RequestParam(value = "type") String type,
            @RequestParam(value = "summary") String summary,
            @RequestParam(value = "comment") String comment,
            @RequestParam(value = "md") String md,
            @RequestParam(value = "html") String html,
            HttpServletRequest request
    ) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        // 判断用户登录状态
        String token = request.getHeader("token");
        User user;
        if(null == token){
            // 用户未登录
            return Msg.fail();
        }else{
            user = (User) redisUtil.get(token);
            if(null == user) {
                return Msg.expire();
            }
        }
        // 此时User已经拿到了。组装文章
        Article article = new Article();

        article.setArticleId(id)
                .setArticleTitle(title)
                .setArticleAuthor(user.getUserId())
                .setArticleType(Integer.parseInt(type))
                .setArticleHtml(ArticleUtils.simplifyImages(html))
                .setArticleMd(ArticleUtils.replaceSingleSlash(md))
                .setArticleIsComment("true".equals(comment) ? 1 : 0)
                .setArticleRank(0);

        // 统一发布时间
        long now = System.currentTimeMillis();
        article.setArticleEditTime(new Date(now));

        // 填充文章摘要
        if(!"".equals(summary)){
            article.setArticleSummary(summary);
        }else{
            article.setArticleSummary(ArticleUtils.getArticleDefaultSummary(html));
        }

        // 向文章——图片表中插入记录
        List<String> images = ArticleUtils.getPicturesInArticle(html);
        // 向 file-image 表中插入文章图片记录
        ArticleImage articleImage = new ArticleImage();
        for (String image : images) {
            articleImage.setInsertDate(new Date(now));
            articleImage.setArticleId(article.getArticleId());
            articleImage.setFilename(image);
            try {
                articleImage.insert();
            }catch (Exception e){
                // 略过
            }
        }

        boolean suc = article.updateById();

        return Msg.success().add("suc", suc).add("id", article.getArticleId());
    }


}

