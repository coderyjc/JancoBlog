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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.UnsupportedEncodingException;
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
     *  -GET 修改发布的文章
     *  -DELETE 删除文章
     *  -POST 获取要修改的文章的信息
     *
     *  /article/update
     *  -POST 提交修改后的文章
     *
     *  /redirect/article
     *  -POST 转发到edit.jsp
     *
     *  /articles
     *  -GET 获取索引页的初始文章
     *  -PUT 通过主键查找文章
     *
     *  /articles/{id}
     *  - GET 获取用户id的所有文章
     *
     *  /rank
     *  - GET
     *  - POST
     *  - PUT
     *
     *
     *  /article/view
     *  - POST 增加文章的浏览量
     *  /article/like
     *  - POST 文章点赞功能
     *  /article/comment
     *  - POST 文章评论数量的增加
     */


    /**
     * 文章点赞功能的实现
     * @param id 文章id
     * @return 消息状态
     */
    @ResponseBody
    @RequestMapping(value = "/article/like", method = RequestMethod.POST)
    public Msg addLikeCount(String id, String count, HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        // 组装新的article对象
        Article article = new Article();
        article.setArticleId(id);
        // 拿到数据之后+1, 然后在前端再+1
        Integer cnt = Integer.valueOf(count);
        cnt += 1;
        article.setArticleLikeCount(cnt);
        articleService.updateByPrimaryKeySelective(article);
        return Msg.success().add("count", cnt);
    }

    /**
     * 根据主键查找文章
     * @param id 文章id
     * @return 文章查找消息
     */
    @ResponseBody
    @RequestMapping(value = "/articles", method = RequestMethod.PUT)
    public Msg getArticleByPrimaryKey(String id, HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        Article article = articleService.selectByPrimaryKey(id);
        return Msg.success().add("article", article);
    }

    /**
     * 更新文章
     * @param innerHTML 内部HTML代码
     * @param innerMD 完整的md
     * @param userNickname 昵称
     * @param title 标题
     * @param type 类型
     * @param session session
     * @return 返回的消息
     */
    @ResponseBody
    @RequestMapping(value = "/article/update", method = RequestMethod.POST)
    public Msg updateArticle(
            String innerHTML,
            String innerMD,
            String userNickname,
            String title,
            Integer type,
            HttpSession session,
            HttpServletRequest request
    ) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        Article article = (Article) session.getAttribute("article");

        Article newArticle = new Article();
        // 构造html页面
        String content = ArticleUtils.createArticle(title, userNickname, innerHTML);
        // 写入HTML
        String fileName = article.getArticleId();
        String path = ConstUtils.CURRENTPATH + File.separator +
                "static" + File.separator +
                "p" + File.separator + fileName + ".html";
        // 先删除这个文章
        FileIo.deleteFileIfExists(path);
        // 再写入这个文章
        FileIo.writeFile(path, content);

        //将md文件直接写入文件
        FileIo.createDirectoryIfNotExists(ConstUtils.CURRENTPATH + File.separator + "static" + File.separator + "md");
        String mdPath =
                ConstUtils.CURRENTPATH + File.separator + "static" +
                        File.separator + "md" + File.separator
                        + fileName + ".md";
        FileIo.writeFile(mdPath, innerMD);

        // 设置主键
        newArticle.setArticleId(article.getArticleId());
        // 类型
        newArticle.setArticleType(type);
        // 摘要
        newArticle.setArticleAbstract(ArticleUtils.getArticleAbstract(innerHTML));
        // 标题
        newArticle.setArticleTitle(title);
        // 修改时间
        newArticle.setArticleEditTime(new Date());

        articleService.updateByPrimaryKeySelective(newArticle);

        //修改完之后移除掉session中的文章
        session.removeAttribute("article");
        session.removeAttribute("content");

        return Msg.success();
    }

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
            String innerMD,
            String userId,
            String userNickname,
            String title,
            String type,
            HttpServletRequest request
    ) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        // 构造html页面
        String content = ArticleUtils.createArticle(title, userNickname, innerHTML);

        //将html页面写入文件
        String fileName = userId + TimeUtils.getCurrentTimestamp();
        FileIo.createDirectoryIfNotExists(ConstUtils.CURRENTPATH + File.separator +
                "static"+File.separator +"p");
        String path = ConstUtils.HTMLPATH + fileName + ".html";
        FileIo.writeFile(path, content);

        //将md文件直接写入文件
        FileIo.createDirectoryIfNotExists(ConstUtils.CURRENTPATH + File.separator + "static"
                + File.separator + "md");
        String mdPath = ConstUtils.MDPATH + fileName + ".md";
        FileIo.writeFile(mdPath, innerMD);

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
        Date now = new Date();
        // 文章的修改时间
        article.setArticleEditTime(now);
        // 文章的创建时间
        article.setArticlePostDate(now);
        articleService.submitArticle(article);
        return Msg.success();
    }

    /**
     * 修改文章的转发操作
     * @param id 文章id
     * @param session 暂时用session保存数据吧
     */
    @ResponseBody
    @RequestMapping(value = "/redirect/article", method = RequestMethod.POST)
    public void updateArticle(String id, HttpSession session, HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        // 获取文章对象
        Article article = articleService.getArticleByPrimaryKey(id);
        // 获取md文件的内容
        String path = ConstUtils.MDPATH + id + ".md";
        String articleContent = FileIo.readFile(path);
        // 把数据放在session中，编辑提交完页面之后再删掉
        session.setAttribute("article", article);
        // 传入过滤之后的文本
        session.setAttribute("content", ArticleUtils.NextLineToText(articleContent));
    }

    /**
     * 删除文章
     * @param id 文章id
     * @return 删除成功还是失败
     */
    @ResponseBody
    @RequestMapping(value = "/article/{id}", method = RequestMethod.DELETE)
    public Msg deleteArticle(
            @PathVariable("id") String id,
            HttpServletRequest request
    ) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        articleService.deleteByPrimaryKey(id);
        String path = ConstUtils.HTMLPATH + id + ".html";
        String mdPath = ConstUtils.MDPATH + id + ".md";
        boolean success =
                FileIo.deleteImagesInHtmlFile(path) &&
                FileIo.deleteFile(path) &&
                FileIo.deleteFile(mdPath);
        return Msg.success().add("success", success);
    }

    /**
     * 获取索引页的初始文章
     * @param pn 要请求的页码
     * @return 搜索结果消息
     */
    @ResponseBody
    @RequestMapping(value = "/articles", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public Msg getIndexArticles(Integer pn, HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        PageHelper.startPage(pn, 10);
        List<Article> articles = articleService.getAll();
        PageInfo<Article> pageInfo = new PageInfo<>(articles, 5);
        return Msg.success().add("pageInfo", pageInfo);
    }

    /**
     * 根据用户id获取用户的所有文章
     * @param page
     * @param limit
     * @param id
     */
    @ResponseBody
    @RequestMapping(value = "/articles/{id}", method = RequestMethod.GET)
    public Msg getArticlesById(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit,
            @PathVariable("id") Integer id,
            HttpServletRequest request
    ) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        PageHelper.startPage(page, limit);
        List<Article> articles = articleService.getArticlesByUserId(id);
        PageInfo<Article> pageInfo = new PageInfo<>(articles, 5);
        return Msg.success().add("pageInfo", pageInfo);
    }


    /**
     * 根据文章的类型和页码获取文章
     * @param id 类型id
     * @param pn 第n页
     * @return 包含文章的消息
     */
    @ResponseBody
    @RequestMapping(value = "/type/{id}", method = RequestMethod.GET)
    public Msg getArticlesByType(
        @PathVariable("id") Integer id,
        @RequestParam(value = "pn", defaultValue = "1") Integer pn,
        HttpServletRequest request
    ) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        PageHelper.startPage(pn, 5);
        List<Article> articles;
        // 加一步判断，如果id小于100，也就是父类别，就获取其下面所有子类的文章
        if(id < 100){
            articles = articleService.getArticlesBySuperId(id);
        }else{
            articles = articleService.getArticlesByTypeId(id);
        }
        PageInfo<Article> pageInfo = new PageInfo<>(articles, 5);
        return Msg.success().add("pageInfo", pageInfo);
    }

    /**
     * 全局搜索文章
     * @param name 查找模式
     * @return 文章列表
     */
    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public Msg searchArticle(
            @RequestParam(value = "keyword") String name,
            @RequestParam(value = "pn", defaultValue = "1") Integer pn,
            HttpServletRequest request
    ) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        PageHelper.startPage(pn, 10);
        List<Article> articles = articleService.getArticlesLikeName(name);
        PageInfo<Article> pageInfo = new PageInfo<>(articles, 5);
        return Msg.success().add("pageInfo", pageInfo);
    }

    /**
     * 文章浏览量的增加
     * @param id 文章id
     * @return 成功
     */
    @ResponseBody
    @RequestMapping(value = "/article/view", method = RequestMethod.POST)
    public Msg addViewCount(String id, HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        // 组装新的article
        Article article = new Article();
        article.setArticleId(id);
        // 这一步从数据库中拿到数据，把选中的字段+1，而不是直接使用前端传来的数据
        article.setArticleViewTime(articleService.selectByPrimaryKey(id).getArticleViewTime() + 1);
        articleService.updateByPrimaryKeySelective(article);
        return Msg.success();
    }

    /**
     * 文章评论数量的增加
     * @param id 文章id
     * @return 成功
     */
    @ResponseBody
    @RequestMapping(value = "/article/comment", method = RequestMethod.POST)
    public Msg addCommentCount(String id, HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        Article article = new Article();
        article.setArticleId(id);
        // 从数据库中拿到数据之后把选中的字段+1
        article.setArticleCommentCount(articleService.selectByPrimaryKey(id).getArticleCommentCount() + 1);
        articleService.updateByPrimaryKeySelective(article);
        return Msg.success();
    }


    /**
     * 获取最热门的10篇文章
     * @return 带有文章列表的消息
     */
    @ResponseBody
    @RequestMapping(value = "/rank", method = RequestMethod.GET)
    public Msg getHotArticles(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        List<Article> articles = articleService.getArticlesLimit("article_view_time");
        return Msg.success().add("viewRank", articles);
    }

    /**
     * 获取点赞量最高的10篇文章
     * @param request 设置编码格式
     * @return 消息
     */
    @ResponseBody
    @RequestMapping(value = "/rank", method = RequestMethod.POST)
    public Msg getLikeRank(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        List<Article> articles = articleService.getArticlesLimit("article_like_count");
        return Msg.success().add("likeRank", articles);
    }

    /**
     * 获取评论数量最多的10篇文章
     * @param request 用来设置编码格式
     * @return 消息
     */
    @ResponseBody
    @RequestMapping(value = "/rank", method = RequestMethod.PUT)
    public Msg getCommentRank(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        List<Article> articles = articleService.getArticlesLimit("article_comment_count");
        return Msg.success().add("commentRank", articles);
    }

}
