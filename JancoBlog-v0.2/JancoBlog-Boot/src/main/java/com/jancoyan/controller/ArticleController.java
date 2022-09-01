package com.jancoyan.controller;

import ch.qos.logback.core.rolling.helper.IntegerTokenConverter;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jancoyan.pojo.*;
import com.jancoyan.service.ArticleService;
import com.jancoyan.service.ArticleContentService;
import com.jancoyan.utils.ArticleUtils;
import com.jancoyan.utils.FileIo;
import com.jancoyan.utils.Msg;
import com.jancoyan.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

/**
 * @author Jancoyan
 * @since 2021-06-28
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @Autowired
    ArticleContentService articleContentService;


    /**
     * load index article
     * @param pn page number
     * @param session session
     * @return message
     */
    @RequestMapping(value = "/articles", method = RequestMethod.GET)
    public Msg indexArticle(
            @RequestParam(value = "pn")Integer pn,
            @RequestParam(value = "search", defaultValue = "") String search,
            HttpSession session
    ){
        System.out.println(search);
        session.setAttribute("content", new ArticleContent());
        IPage<Article> page = articleService.selectAllWithAuthorNameByPage(pn, 10, search);
        return Msg.success().add("pageInfo", page);
    }



    /**
     * receive picture from editor.md
     * @param multipartFile picture segment
     * @param request request
     * @param session session
     * @return data in json that editor.md required
     * @throws UnsupportedEncodingException set encoding
     */
    @RequestMapping(value = "/pictureupload", method = RequestMethod.POST)
    public JSONObject uploadArticlePicture(@RequestParam(value = "editormd-image-file",
            required = true) MultipartFile multipartFile,
                                           HttpServletRequest request,
                                           HttpSession session) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        // 获取用户id
        User user = (User) session.getAttribute("user");
        String userId = String.valueOf(user.getUserId());
        //
        String trueFileName = multipartFile.getOriginalFilename();
        // 文件后缀名
        String suffix = trueFileName.substring(trueFileName.lastIndexOf("."));
        // 图片文件命名规范：
        // 时间-用户名.后缀
        String now = TimeUtils.getCurrentTimeString();
        // 图片的全名
        String fileName = now.replaceAll("[:-]","").replace(' ', '-') + userId + suffix;
        // 目录，每月创建一个目录
        // 获取当前月份
        String directory = now.substring(0, 7);
        // 获取图片地址
        String path =
                request.getSession().getServletContext().getRealPath(
                        "/articleimages/" + directory
                );

        // 调用工具类进行图片的写入
        FileIo.uploadPicture(multipartFile, path, fileName);

        String backUrl = request.getScheme() + "://" +
                request.getServerName() + ":" + request.getServerPort() +
                request.getContextPath() +
                "/articleimages/" + directory + "/" + fileName;

        JSONObject rst = new JSONObject();
        rst.put("url", backUrl);
        rst.put("success", 1);
        rst.put("message", "success");

        return rst;
    }


    /**
     * post article
     * @param innerHTML 文章预览HTML
     * @param innerMD 文章md内容
     * @param title 标题
     * @param type 类型
     * @param type 类型
     * @param session session
     * @return 消息
     */
    @RequestMapping(value = "/submit", method = RequestMethod.PUT)
    public Msg postBlog(
        @RequestParam("innerHTML") String innerHTML,
        @RequestParam("innerMD") String innerMD,
        @RequestParam("title") String title,
        @RequestParam("types") String type,
        HttpSession session
    ){
        User user = (User) session.getAttribute("user");
        Date date = new Date();
        // 文章id
        String articleId = user.getUserId().toString() + date.getTime();

        // 向Article表中插入数据
        Article article = new Article();
        article.setArticleId(articleId);
        article.setArticleTitle(title);
        article.setArticleAuthorId(user.getUserId());
        article.setArticleSummary(ArticleUtils.getArticleAbstract(innerHTML));
        article.setArticleEditTime(date);
        article.setArticlePostTime(date);
        article.insert();

        // 向ArticleContent表中插入数据
        ArticleContent content = new ArticleContent(articleId, innerHTML,
                ArticleUtils.NextLineToText(innerMD));
        content.insert();

        // 向ArticleType表中插入数据
        ArticleType articleType = new ArticleType();
        articleType.setArticleId(articleId);
        articleType.setTypeId(Integer.parseInt(type));
        articleType.insert();

        return Msg.success();
    }

    /**
     * update article
     * @param innerHTML HTML type to show its content
     * @param innerMD md content
     * @param title new title
     * @param type article types
     * @param session session
     * @return message
     */
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public Msg updateBlog(
            @RequestParam("innerHTML") String innerHTML,
            @RequestParam("innerMD") String innerMD,
            @RequestParam("title") String title,
            @RequestParam("types") String type,
            HttpSession session
    ){
        ArticleContent content = (ArticleContent) session.getAttribute("content");
        Article article = new Article();
        // Active Record 查询要修改的对象
        article.setArticleId(content.getArticleId());
        article = article.selectById();

        Date date = new Date();
        // 向Article表中插入数据
        article.setArticleTitle(title);
        article.setArticleSummary(ArticleUtils.getArticleAbstract(innerHTML));
        article.setArticleEditTime(date);
        article.updateById();

        // 向ArticleContent表中插入数据
        ArticleContent articleContent = new ArticleContent(article.getArticleId(), innerHTML,
                ArticleUtils.NextLineToText(innerMD));
        articleContent.updateById();

        // 修改类型表中的数据
        ArticleType articleType = new ArticleType();
        articleType.setArticleId(article.getArticleId());
        articleType.setTypeId(Integer.parseInt(type));
        articleType.updateById();

        return Msg.success();
    }


    /**
     * delete article (can be multiple)
     * @param articleId article id
     * @return message
     */
    @RequestMapping(value = "/submit", method = RequestMethod.DELETE)
    public Msg deleteBlog(
            @RequestParam("id") String articleId){
        // 多个tag的id会用 & 连起来，用 & 把不同的tagid分割
        String[] ids = {articleId};
        if (articleId.contains("&")){
            ids = articleId.split("&");
        }
        Article article = new Article();
        for (String id: ids) {
            article.setArticleId(id);
            article.deleteById();
            System.out.println(id);
        }
        return Msg.success();
    }



    @RequestMapping(value = "/submit", method = RequestMethod.GET)
    public Msg cancelupdateBlog(
            HttpSession session
    ){
        // 去掉原本存在Session中的content对象
        session.setAttribute("content", new ArticleContent());
        return Msg.success();
    }

    /**
     * 修改文章的重定向
     * @param articleId 文章id
     * @param session 会话传值
     * @return 连接状态信息
     */
    @RequestMapping(value = "/redirect", method = RequestMethod.GET)
    public Msg redirectToUpdateBlog(
            @RequestParam("id") String articleId,
            HttpSession session){
        // 使用ActiveRecord进行数据查询
        ArticleContent content = new ArticleContent();
        content.setArticleId(articleId);
        content = content.selectById();
        session.setAttribute("content", content);
        return Msg.success();
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Msg getAll(Integer page, Integer limit){
        IPage<Article> iPage = articleService.selectAllByPage(page, limit);
        return Msg.success().add("pageInfo", iPage);
    }

    /**
     * get user's all article
     * @param page page
     * @param limit limit
     * @param session session
     * @return message
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Msg getUserAll(
            Integer page, Integer limit,
            HttpSession session
    ){
        User currUser = (User)session.getAttribute("user");
        IPage<Article> iPage = articleService.selectUserArticleByPage(page, limit, String.valueOf(currUser.getUserId()));
        return Msg.success().add("pageInfo", iPage);
    }

    /**
     * get article rank by comment count
     * @return message
     */
    @RequestMapping(value = "/rank", method = RequestMethod.PUT)
    public Msg getArticleRankByComment(){
        List<Article> list = articleService.getArticleRankByComment();
        return Msg.success().add("commentRank", list);
    }

    /**
     * add article view count
     * @param articleId article id
     * @return message
     */
    @RequestMapping(value = "/view", method = RequestMethod.POST)
    public Msg addViewCount(
            @RequestParam("id") String articleId
    ){
        Article article = new Article();
        article.setArticleId(articleId);
        article = article.selectById();
        article.setArticleViewCount(article.getArticleViewCount() + 1);
        article.updateById();
        return Msg.success();
    }

    /**
     * add article like count
     * @param articleId article id
     * @return message
     */
    @RequestMapping(value = "/like", method = RequestMethod.POST)
    public Msg addLikeCount(
            @RequestParam("id") String articleId
    ){
        Article article = new Article();
        article.setArticleId(articleId);
        article = article.selectById();
        System.out.println(article);
        article.setArticleLikeCount(article.getArticleLikeCount() + 1);
        article.updateById();
        return Msg.success();
    }

}
