package com.Jancoyan.controller;

import com.Jancoyan.domain.Article;
import com.Jancoyan.service.ArticleService;
import com.Jancoyan.service.TypeService;
import com.Jancoyan.utils.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author Jancoyan
 */
@Controller
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @Autowired
    TypeService typeService;

    /**
     * 约定:
     * /article:
     *  -PUT 提交发布文章
     *  -DELETE 删除文章
     *  -POST 修改文章
     */


    /**
     * 提交文章
     * @param innerHTML 从前端传过来的带有标签的html
     * @param userId 用户id
     * @param userNickname 用户昵称
     * @param title 文章的名称
     * @param type 文章类型
     * @return 操作成功
     */
    @ResponseBody
    @RequestMapping(value = "/article", method = RequestMethod.PUT)
    public Msg submitArticle(
            String innerHTML,
            String userId,
            String userNickname,
            String title,
            String type
    ){
        // 构造html页面
        String content = ArticleUtils.createArticle(title, userNickname, innerHTML);
        //将html页面写入文件
        String fileName = userId + TimeUtils.getCurrentTimestamp();
        String path = ConstUtils.CURRENTPATH + "\\static\\p\\" + fileName + ".html";
        FileIo.writeHTMLFile(path, content);

        //创建Article对象
        Article article = new Article();
        // 文章编号
        article.setArticleId(fileName);
        // 文章作者的id
        article.setArticleAuthorId(Integer.parseInt(userId));
        // 文章的摘要
        article.setArticleAbstract(ArticleUtils.getArticleAbstract(innerHTML));
        // 文章标题
        article.setArticleTitle(title);
        // 文章类型
        article.setArticleType(Integer.parseInt(type));
        // 文章的点赞数
        article.setArticleLikeCount(0);
        // 文章的浏览量
        article.setArticleViewTime(0);
        // 文章的评论数
        article.setArticleCommentCount(0);
        // 文章的修改时间
        article.setArticleEditTime(new Date());
        // 文章的创建时间
        article.setArticlePostDate(new Date());

        articleService.submitArticle(article);

        return Msg.success();
    }


    /**
     * 删除文章
     * @param id 文章id
     * @return 删除成功还是失败
     */
    @ResponseBody
    @RequestMapping(value = "/article", method = RequestMethod.DELETE)
    public Msg deleteArticle(String id){
        articleService.deleteByPrimaryKey(id);
        String path = ConstUtils.CURRENTPATH + "\\static\\p\\" + id + ".html";
        boolean success = FileIo.deleteFile(path);
        return Msg.success().add("success", success);
    }

    @ResponseBody
    @RequestMapping(value = "/article", method = RequestMethod.POST)
    public Msg updateArticle(){

        return Msg.success();
    }



    /**
     * 获取索引页的初始文章
     * @param pn 要请求的页码
     * @return 搜索结果消息
     */
    @ResponseBody
    @RequestMapping(value = "/articles", method = RequestMethod.GET)
    public Msg getIndexArticles(
            @RequestParam(value = "pn", defaultValue = "1") Integer pn){
        PageHelper.startPage(pn, 5);
        List<Article> articles = articleService.getAll();
        PageInfo pageInfo = new PageInfo(articles, 5);
        return Msg.success().add("pageInfo", pageInfo);
    }

    /**
     * 根据用户id获取用户的所有文章
     * @param pn 第几页
     * @param id 用户的id是多少
     * @return 结果消息
     */
    @ResponseBody
    @RequestMapping(value = "/articles/{id}", method = RequestMethod.GET)
    public Msg getArticlesById(
            @RequestParam(value = "pn", defaultValue = "1") Integer pn,
            @PathVariable("id") Integer id
    ){
        PageHelper.startPage(pn, 11);
        List<Article> articles = articleService.getArticlesByUserId(id);
        PageInfo pageInfo = new PageInfo(articles, 5);
        return Msg.success().add("pageInfo", pageInfo);
    }

//    /**
//     * 根据文章的类型和页码获取文章
//     * @param id
//     * @param pn
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping(value = "/articles/type/{id}", method = RequestMethod.GET)
//    public Msg getArticlesByType(
//        @PathVariable("id") Integer id,
//        @RequestParam(value = "pn", defaultValue = "1") Integer pn
//    ){
//        PageHelper.startPage(pn, 10);
//        List<Article> articles = articleService.getArticlesByTypeId(id);
//        PageInfo pageInfo = new PageInfo(articles, 5);
//        return Msg.success().add("pageInfo", pageInfo);
//    }

    /**
     *
     * @param name 查找模式
     * @return 文章列表
     */
    @ResponseBody
    @RequestMapping(value = "/search/{name}", method = RequestMethod.GET)
    public Msg searchArticle(
            @PathVariable("name") String name,
            @RequestParam(value = "pn", defaultValue = "1") Integer pn
    ){
        PageHelper.startPage(pn, 10);
        List<Article> articles = articleService.getArticlesLikeName(name);
        PageInfo pageInfo = new PageInfo(articles, 5);
        return Msg.success().add("articles", pageInfo);
    }

    /**
     * @param id 文章id
     * @param viewCount 文章浏览量
     * @return 成功
     */
    @ResponseBody
    @RequestMapping(value = "/article/view", method = RequestMethod.POST)
    public Msg addViewCount(String id, Integer viewCount){
        // 组装新的article
        Article article = new Article();
        article.setArticleId(id);
        // 这一步从数据库中拿到数据，把选中的字段+1，而不是直接使用前端传来的数据
        article.setArticleViewTime(articleService.selectByPrimaryKey(id).getArticleViewTime() + 1);
        articleService.updateByPrimaryKeySelective(article);
        return Msg.success();
    }

}
