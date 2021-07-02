package com.jancoyan.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jancoyan.pojo.Article;
import com.jancoyan.pojo.ArticleContent;
import com.jancoyan.pojo.ArticleTag;
import com.jancoyan.pojo.User;
import com.jancoyan.service.ArticleService;
import com.jancoyan.service.ArticleContentService;
import com.jancoyan.service.ArticleTagService;
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

    @Autowired
    ArticleTagService articleTagService;


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


    @RequestMapping(value = "/submit", method = RequestMethod.PUT)
    public Msg postBlog(
        @RequestParam("innerHTML") String innerHTML,
        @RequestParam("innerMD") String innerMD,
        @RequestParam("title") String title,
        @RequestParam("types") String types,
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

        // 向ArticleTag表中插入数据
        String[] type = types.split("&");
        ArticleTag articleTag = new ArticleTag();
        articleTag.setArticleId(articleId);
        for (String i : type) {
            if (!i.equals("")){
                articleTag.setTagId(Integer.parseInt(i));
                articleTag.insert();
            }
        }

        return Msg.success();
    }

    @RequestMapping(value = "/submit", method = RequestMethod.DELETE)
    public Msg deleteBlog(
            @RequestParam("id") String articleId){
        Article article = new Article();
        article.setArticleId(articleId);
        boolean b = article.deleteById();
        return Msg.success().add("success", b);
    }


    /**
     * 保存修改文章
     * @param innerHTML
     * @param innerMD
     * @param title
     * @param types
     * @param session
     * @return
     */
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public Msg updateBlog(
            @RequestParam("innerHTML") String innerHTML,
            @RequestParam("innerMD") String innerMD,
            @RequestParam("title") String title,
            @RequestParam("types") String types,
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

        // 向ArticleTag表中插入数据
        String[] type = types.split("&");
        ArticleTag articleTag = new ArticleTag();
        articleTagService.deleteByArticleId(article.getArticleId());
        articleTag.setArticleId(article.getArticleId());
        for (String i : type) {
            if (!i.equals("")){
                articleTag.setTagId(Integer.parseInt(i));
                articleTag.insert();
            }
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


    @RequestMapping(value = "/redirect", method = RequestMethod.GET)
    public Msg redirectToUpdateBlog(
            @RequestParam("id") String articleId,
            HttpSession session){
        ArticleContent content = new ArticleContent();
        content.setArticleId(articleId);
        content = content.selectById();
        session.setAttribute("content", content);
        return Msg.success();
    }

    @RequestMapping(value = "/articles", method = RequestMethod.GET)
    public Msg selectArticleById(
            @RequestParam(value = "pn")Integer pn,
            HttpSession session
    ){
        session.setAttribute("content", new ArticleContent());
        IPage<Article> page = articleService.selectAllByPage(pn, 10);
        return Msg.success().add("pageInfo", page);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Msg getAll(Integer page, Integer limit){
        IPage<Article> iPage = articleService.selectAllByPage(page, limit);
        return Msg.success().add("pageInfo", iPage);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Msg getUserAll(
            Integer page, Integer limit,
            HttpSession session
    ){
        User currUser = (User)session.getAttribute("user");
        IPage<Article> iPage = articleService.selectUserArticleByPage(page, limit, String.valueOf(currUser.getUserId()));
        return Msg.success().add("pageInfo", iPage);
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public Msg searchArticles(
            @RequestParam("keyword") String keyword
    ){
        Article article = new Article();
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("article_title", keyword);
        List<Article> list = article.selectList(queryWrapper);
        return Msg.success().add("pageInfo", list);
    }

    @RequestMapping(value = "/rank", method = RequestMethod.GET)
    public Msg getArticleRankByView(){
        List<Article> list = articleService.getArticleRankByView();
        return Msg.success().add("viewRank", list);
    }

    @RequestMapping(value = "/rank", method = RequestMethod.POST)
    public Msg getArticleRankByLike(){
        List<Article> list = articleService.getArticleRankByLike();
        return Msg.success().add("likeRank", list);
    }

    @RequestMapping(value = "/rank", method = RequestMethod.PUT)
    public Msg getArticleRankByComment(){
        List<Article> list = articleService.getArticleRankByComment();
        return Msg.success().add("commentRank", list);
    }

}
