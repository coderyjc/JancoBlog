<%--
  Created by Jancoyan.
  User: Jancoyan
  Date: 2021/3/16
  Time: 16:33
 --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" +
            request.getServerName() + ":" + request.getServerPort() +
            request.getContextPath() + "/";
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Welcome</title>
    <script src="./static/js/jquery-1.12.js"></script>
    <link rel="stylesheet" href="./static/css/index.css"/>
    <base href="<%=basePath%>" />
</head>
<body>
<!-- 导航条 -->
<div class="nav">
    <!-- 回主页的"图标" -->
    <a href="javascript:;" class="back-to-index">JancoBlog</a>
    <!-- 登录和注册 -->
    <div class="signin"><a href="login.jsp">登录/注册</a></div>
</div>
<!-- 除去导航条和footer的部分是主体main -->
<div class="main">
    <!-- 博客标题和签名 -->
    <div class="title-and-signature">
        <h1>JANCOYAN</h1> <br>
        <div class="quote">十 年 饮 冰, 难 凉 热 血</div>
    </div>
    <!-- 分类导航和搜索功能 -->
    <div class="sort-and-search">
        <!-- 文章分类导航 -->
        <ul class="types">
            <li class="drop-down"><a href="#">计算机基础</a>
                <div class="drop-content">
                    <a href="#">数据结构与算法</a>
                    <a href="#">计算机组成原理</a>
                    <a href="#">计算机网络</a>
                    <a href="#">操作系统</a>
                </div>
            </li>
            <li class="drop-down"><a href="#">大数据</a>

            </li>
            <li class="drop-down"><a href="#">前端</a>
                <div class="drop-content">
                    <a href="#">HTML</a>
                    <a href="#">CSS</a>
                    <a href="#">JavaScript</a>
                </div>
            </li>
            <li class="drop-down"><a href="#">JavaWeb</a>
                <div class="drop-content">
                    <a href="#">Ajax</a>
                    <a href="#">JDBC</a>
                    <a href="#">Mybatis</a>
                </div>
            </li>
            <!-- 搜索功能 -->
            <div class="search">
                <form action="#" method="POST" class="search-form">
                    <input type="text" name="search" placeholder="搜索博文" class="search-text">
                    <input type="submit" name="submit" value="搜索" class="search-btn">
                </form>
            </div>
        </ul>
    </div>

    <!-- 文章列表和标签导航 -->
    <!-- 列表和分页 -->
    <div class="article-list">
        <!-- 文章列表 -->
        <div class="articles">
            <ul></ul>
        </div>
        <div class="load-more">
            <span>下滑加载更多...</span>
        </div>
    </div>
</div>
<!-- 页脚 -->

<div class="footer">
    <ul>
        <li><a href="#">关于</a></li>
    </ul>
</div>



<script>
    // 页面加载之前执行
    $(function(){
    //    获取数据库中最新的10条记录
        get_article_page(1);
    });


    // 获取文章，然后添加到主页的末尾
    function get_article_page(pn) {
        $.ajax({
            url: "articles",
            data : "pn=" + pn,
            type : "GET",
            success: function (result) {
                var articleList = result.extend.PageInfo.list;
                $.each(articleList, function (index, item) {
                    // 博文的图片
                    var img = $("<div class='image'></div>").append($("<img />").attr({
                        "src" : "./static/image/test.png",
                        "alt" : "这是图片",
                        "height" : "180px",
                        "width" : "280px"
                    }));

                    var articlePath = "./static/p/" + item.articleId + ".html";

                    var title = $("<div class='title'></div>").append($("<a target='_blank'></a>").append(item.articleTitle).attr("href", articlePath));
                    var discription = $("<div class='discription'></div>").append(item.articleAbstract);
                    var time = $("<div class='datetime'></div>").append(new Date(item.articlePostDate));
                    var articleBox = $("<div class='article-box'></div>").append(title).append(discription).append(time);
                    $("<li class='article'></li>")
                        .append(img)
                        .append(articleBox)
                        .appendTo(".articles>ul");
                })
            }
        });
    }





</script>


</body>
</html>