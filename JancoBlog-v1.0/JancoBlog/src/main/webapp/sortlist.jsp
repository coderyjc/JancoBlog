<%@ page import="com.Jancoyan.domain.User" %>
<%--
  Created by Jancoyan.
  User: Jancoyan
  Date: 2021/4/17
  Time: 22:33
 --%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" +
            request.getServerName() + ":" + request.getServerPort() +
            request.getContextPath() + "/";
%>

<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>"/>
    <meta charset="utf-8">
    <title>文章分类</title>
    <script type="text/javascript" src="./static/js/jquery-1.12.js"></script>
    <script src="./static/lib/layui/layui.js"></script>
    <link rel="stylesheet" href="./static/lib/layui/css/layui.css">
    <link rel="stylesheet" href="./static/css/sortlist.css">
</head>
<script type="text/javascript">

    var typeid = <%=request.getParameter("type")%>;
    var currentPage = 1;
    var totalPage = 1;

    $(function(){
        // 获得这个类型第一页的文章
        get_articles(typeid, currentPage);
    });

</script>
<body>

<!-- 顶部导航栏 -->
<nav class="clearfix">
    <ul id="top-nav-bar" class="layui-nav layui-bg-black" layui-filter="top-nav">
        <li class="layui-nav-item"><a href="./index.jsp">首页</a></li>
        <li class="layui-nav-item"><a href="javascript:;">AI</a><dl class="layui-nav-child"><dd><a href="sortlist.jsp?type=201">图像识别</a></dd></dl></li>
        <li class="layui-nav-item"><a href="./sortlist.jsp?type=1">计算机基础<i class="layui-icon layui-icon-down layui-nav-more"></i></a><dl class="layui-nav-child"><dd><a href="sortlist.jsp?type=101">数据结构与算法</a></dd><dd><a href="sortlist.jsp?type=102">计算机网络</a></dd><dd><a href="sortlist.jsp?type=103">计算机组成原理</a></dd><dd><a href="sortlist.jsp?type=104">操作系统</a></dd></dl></li>
        <li class="layui-nav-item"><a href="./sortlist.jsp?type=3">前端<i class="layui-icon layui-icon-down layui-nav-more"></i></a><dl class="layui-nav-child"><dd><a href="sortlist.jsp?type=301">HTML</a></dd><dd><a href="sortlist.jsp?type=302">CSS</a></dd><dd><a href="sortlist.jsp?type=303">JavaScript</a></dd><dd><a href="sortlist.jsp?type=304">JQuery</a></dd><dd><a href="sortlist.jsp?type=305">Ajax</a></dd><dd><a href="sortlist.jsp?type=306">Java图形界面</a></dd></dl></li>
        <li class="layui-nav-item"><a href="./sortlist.jsp?type=4">Java后端<i class="layui-icon layui-icon-down layui-nav-more"></i></a><dl class="layui-nav-child"><dd><a href="sortlist.jsp?type=401">Spring</a></dd><dd><a href="sortlist.jsp?type=402">SpringMVC</a></dd><dd><a href="sortlist.jsp?type=403">MyBatis</a></dd><dd><a href="sortlist.jsp?type=404">JDBC</a></dd><dd><a href="sortlist.jsp?type=406">SSM整合</a></dd></dl></li>
        <li class="layui-nav-item"><a href="./sortlist.jsp?type=5">Python<i class="layui-icon layui-icon-down layui-nav-more"></i></a><dl class="layui-nav-child"><dd><a href="sortlist.jsp?type=501">Python基础知识</a></dd><dd><a href="sortlist.jsp?type=502">PythonWeb</a></dd><dd><a href="sortlist.jsp?type=503">PyQt</a></dd></dl></li>
        <li class="layui-nav-item"><a href="./sortlist.jsp?type=6">数据库<i class="layui-icon layui-icon-down layui-nav-more"></i></a><dl class="layui-nav-child"><dd><a href="sortlist.jsp?type=601">MySQL</a></dd></dl></li>
        <li class="layui-nav-item"><a href="./sortlist.jsp?type=7">大数据<i class="layui-icon layui-icon-down layui-nav-more"></i></a><dl class="layui-nav-child"><dd><a href="sortlist.jsp?type=701">HDFS</a></dd></dl></li>
        <%
            String aTag = "";
            String aHref = "profile.png";
            User user = (User) session.getAttribute("user");
            String profileClick = "javascript:;";
            if(user == null){
                aHref = "login-profile.png";
                profileClick = "./login.jsp";
            } else {
                aTag = "<dl class=\"layui-nav-child\"><dd><a href=\"./edit.jsp\">写博客</a></dd><dd><a href=\"./workbench/\">后台管理</a></dd><hr><dd style=\"text-align: center;\"><a href=\"\">退出</a></dd></dl>\n";
            }
        %>
        <li id="user-profile-picture" class="layui-nav-item" lay-unselect="">
            <a href="<%=profileClick%>"><img src="./static/img/<%=aHref%>" class="layui-nav-img"></a>
            <%=aTag%>
        </li>
    </ul>
</nav>

<h2 id="search-title"></h2>

<div id="main" class="layui-bg-gray" style="padding: 30px;">
    <div id="article-list" class="layui-row layui-col-space15">
    </div>
</div>

<script>

    // 页面加载完毕之后执行，完成一些初始化工作
    function get_articles(typeid, page) {
        $.ajax({
            url: "type/" + typeid,
            type: "GET",
            data: "pn=" + page,
            success: function (result){
                // 判断结果是不是空
                if (0 === result.extend.pageInfo.total){
                    layer.msg("没有相关类型的文章，请重新选择");
                    $("#search-title").text("暂时没有相关文章...");
                    return;
                }
                // 拿到结果建立文章列表
                build_article_list(result);
            }
        });
    }

    // 传入result，在不清空页面的情况下进行文章的追加
    function build_article_list(result) {
        var articleList = result.extend.pageInfo.list;

        // 显示 ”找到多少条“ 此类文字
        $("#search-title").text("共有相关文章 " + result.extend.pageInfo.total + " 篇");

        totalPage = result.extend.pageInfo.pages;
        $.each(articleList, function (index, item) {
            // 卡片的父级col列表
            var articleCol = $("<div></div>").addClass("layui-col-md6");
            // 卡片， 里面有标题、内容和底部信息
            var articleCard = $("<li></li>").addClass("layui-card");
            // 标题中的链接
            var articlePath = "./static/p/" + item.articleId + ".html";
            // 文章标题
            var aTitle = $("<div></div>").addClass("layui-card-header").append(
                $("<a></a>").attr("href", articlePath).append(item.articleTitle)
            );
            // 绑定增加浏览量的函数
            aTitle.click(function () {
                add_article_view(item.articleId);
            });

            // 内容
            var articleBody = $("<div></div>").addClass("layui-card-body")
                .append(item.articleAbstract);

            // 点赞的数量, 图标 和 数量
            var likeArticles = $("<div></div>").addClass("article-info-like")
                .append("<i class='layui-icon layui-icon-praise' style='color: red'></i>")
                .append($("<span class='like-count'></span>").append(item.articleLikeCount));
            // 绑定点击事件
            likeArticles.click(function(){
                add_like_count(this.childNodes[1], item.articleId);
            });

            // 发布时间和浏览量
            var articleInfo = $("<div></div>")
                .addClass("article-info")
                .append($("<div></div>").addClass("article-info-date").append("发布日期："+dateFormat(item.articlePostDate)))
                .append($("<div></div>").addClass("article-info-view").append("浏览量："+item.articleViewTime))
                .append(likeArticles);

            //向盒子中添加元素
            articleCard.append(aTitle)
                .append(articleBody)
                .append($("<div class='segment-line'></div>"))
                .append(articleInfo);

            // 向一列中中添加元素
            articleCol.append(articleCard).appendTo("#article-list");
        })
    }

    // 点击文章的时候增加文章阅读量
    function add_article_view(articleId) {
        $.ajax({
            url: "article/view",
            type: "POST",
            data: {
                "id": articleId
            }
        });
    }

    // 点赞功能的实现
    function add_like_count(element, id){
        // 当前点赞的数量
        var nowCount = element.innerHTML;
        // 请求成功之后应该顺便把这个数量+1
        $.ajax({
            url: "article/like",
            type: "post",
            data: {
                "count": nowCount,
                "id" : id
            },
            success: function (result) {
                var nowCount = result.extend.count;
                element.innerHTML = nowCount;
            }
        });
    }

    /**
     * 将时间戳转化为标准时间
     * @param date 时间戳
     * @returns {string} 标准时间字符串
     */
    function dateFormat(date) {
        var s = new Date(date)
        var y = s.getFullYear()
        var m = (s.getMonth() + 1) < 10 ? '0' + (s.getMonth() + 1) : (s.getMonth() + 1)
        var dd = s.getDate() < 10 ? '0' + s.getDate() : s.getDate()
        var hh = s.getHours() < 10 ? '0' + s.getHours() : s.getHours()
        var mm = s.getMinutes() < 10 ? '0' + s.getMinutes() : s.getMinutes()
        var ss = s.getSeconds() < 10 ? '0' + s.getSeconds() : s.getSeconds()
        var enddate = y + '-' + m + '-' + dd + ' ' + hh + ':' + mm + ":" + ss
        return enddate
    }

</script>
</body>
</html>
