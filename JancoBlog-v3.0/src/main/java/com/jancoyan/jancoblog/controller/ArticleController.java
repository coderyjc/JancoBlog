package com.jancoyan.jancoblog.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jancoyan.jancoblog.pojo.Article;
import com.jancoyan.jancoblog.pojo.User;
import com.jancoyan.jancoblog.service.ArticleService;
import com.jancoyan.jancoblog.utils.ArticleUtils;
import com.jancoyan.jancoblog.utils.Msg;
import com.jancoyan.jancoblog.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
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
    RedisUtil redisUtil;

    /**
     * 获取首页的文章，带有搜索功能
     * @param pn 第几页
     * @param limit 每一页的大小
     * @param condition 条件
     * @return 标准的pageInfo
     */
    @RequestMapping(value = "/all")
    public Msg getAll(
            @RequestParam(value = "pn")String pn,
            @RequestParam(value = "limit", defaultValue = "10")String limit,
            @RequestParam(value = "condition", defaultValue = "")String condition
    ){
        IPage<Article> iPage = service.getIndexList(Integer.parseInt(pn),
                Integer.parseInt(limit),
                condition);
        return Msg.success().add("pageInfo", iPage);
    }

    /**
     * 获取文章管理列表的文章
     * @param pn 第几页
     * @param limit 容量
     * @param condition 条件
     * @return 成功
     */
    @RequestMapping(value = "/manage")
    public Msg getManageAll(
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
        IPage<Article> iPage = service.getManageList(null,
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
    public Msg getArticleByUser(
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
        IPage<Article> iPage = service.getManageList(
                user.getUserName(),
                Integer.parseInt(pn),
                Integer.parseInt(limit),
                condition);
        return Msg.success().add("pageInfo", iPage);
    }

    /**
     * 批量删除文章
     * @param ids 文章id，以 & 连接
     * @return 成功/失败
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Msg batchDeleteArticle(
            String ids,
            HttpServletRequest request){
        String token = request.getHeader("token");
        if(null == token){
            // 用户登录信息过期了
            return Msg.expire();
        }
        Article article = new Article();
        boolean suc = false;
        if(!ids.contains("&")){
            article.setArticleId(ids);
            suc = article.deleteById();
        } else {
            String[] id = ids.split("&");
            for (String item : id) {
                article.setArticleId(item);
                suc = article.deleteById();
            }
        }
        return Msg.success().add("suc", suc ? "success" : "fail");
    }

    /**
     * 查看文章的时候获取单个文章
     * @param articleId 文章ID
     * @return 成功
     */
    @RequestMapping(value = "/single", method = RequestMethod.GET)
    public Msg getSingleArticle(
            @RequestParam(value = "id") String articleId
    ){
        Article article = service.getSingleArticle(articleId);
        return Msg.success().add("article", article);
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
    public Msg postArticle(
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
        User user = new User();
        if(null == token){
            // 用户登录信息过期了
            return Msg.expire();
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
                .setArticleHtml(html)
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

        boolean suc = article.insert();

        return Msg.success().add("suc", suc).add("id", article.getArticleId());
    }

    /**
     * 点赞
     * @param id 点赞的文章
     * @return 成功
     */
    @RequestMapping(value = "/like", method = RequestMethod.POST)
    public Msg addLikeCount(@RequestParam(value = "id")String id){
        Article article = new Article();
        article.setArticleId(id);
        article = article.selectById();
        article.setArticleLikeCount(article.getArticleLikeCount() + 1);
        article.updateById();
        return Msg.success();
    }

    /**
     * 浏览
     * @param id 点开的文章的id
     * @return 成功
     */
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public Msg addViewCount(@RequestParam(value = "id")String id){
        Article article = new Article();
        article.setArticleId(id);
        article = article.selectById();
        article.setArticleViewCount(article.getArticleViewCount() + 1);
        article.updateById();
        return Msg.success();
    }

    /**
     * 改变当前评论的状态
     * @param id 评论的id
     * @return
     */
    @RequestMapping(value = "/toggle/comment", method = RequestMethod.POST)
    public Msg toggleIsComment(
            @RequestParam(value = "id")String id,
            HttpServletRequest request
    ){
        String token = request.getHeader("token");
        if(null == token){
            // 用户登录信息过期了
            return Msg.expire();
        }
        // 改变评论的状态
        Article article = new Article();
        article.setArticleId(id);
        article = article.selectById();
        article.setArticleIsComment(1 - article.getArticleIsComment());
        boolean suc = article.updateById();

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
    public Msg stickArticleToTop(
            @RequestParam(value = "id")String id,
            HttpServletRequest request
    ){
        String token = request.getHeader("token");
        if(null == token){
            // 用户登录信息过期了
            return Msg.expire();
        }
        // 改变置顶的状态
        Article article = new Article();
        article.setArticleId(id);
        article = article.selectById();
        article.setArticleRank(1 - article.getArticleRank());
        boolean suc = article.updateById();
        if(suc) {
            return Msg.success();
        } else {
            return Msg.fail();
        }
    }

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

        String savePath = new File(".").getCanonicalPath() + "\\target\\classes\\static\\p\\";

        System.out.println(savePath);

        File savePathFile = new File(savePath);
        if (!savePathFile.exists()) {
            //若不存在该目录，则创建目录
            savePathFile.mkdir();
        }

        //通过UUID生成唯一文件名
        String filename = UUID.randomUUID().toString().replaceAll("-","") + "." + suffix;

        System.out.println(filename);

        try {
            //将文件保存指定目录
            file.transferTo(new File(savePath + filename));
        } catch (Exception e) {
            e.printStackTrace();
            return Msg.fail().add("msg", "保存文件异常");
        }
        String url = "http://localhost:8080/p/" + filename;

        //返回文件名称
        return Msg.success().add("url", url);
    }


}

