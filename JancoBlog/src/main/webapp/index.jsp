<%@ page import="com.Jancoyan.domain.User" %><%--
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
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Welcome</title>
    <script src="./static/js/jquery-1.12.js"></script>
    <link rel="stylesheet" href="./static/css/index.css">
    <script>
        var totalPage = 0;
        var currentPage = 1;
    </script>
</head>
<body>
<!-- 导航条 -->
<div class="nav">
    <!-- 回主页的"图标" -->
    <a href="./index.jsp" class="back-to-index">JancoBlog</a>
    <!-- 登录和注册 -->
    <div class="signin">
        <%
            String aTag = null;
            User user = (User) session.getAttribute("user");
            if(user == null){
                aTag = "<a href='login.jsp'>登录</a>";
            } else {
                aTag = "<a href='./workbench'>后台管理</a>";
            }
        %>
        <%=aTag%>
    </div>
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
        <ul class="types clearfix">
            <!-- 搜索功能 -->
            <div class="search">
                <form action="#" method="POST" class="search-form">
                    <input type="text" name="search" placeholder="博文标题" id="search-text" />
                    <input type="submit" name="submit" value="搜索" id="search-btn" />
                </form>
            </div>
        </ul>
    </div>

    <!-- 文章列表和标签导航 -->
    <!-- 列表和分页 -->
    <div class="article-list clearfix">
        <!-- 文章列表 -->
        <div class="articles clearfix">
            <ul class="clearfix"></ul>
        </div>
        <div class="load-more">
            <span>下滑加载更多...</span>
        </div>
    </div>
</div>
<!-- 页脚 -->
<footer>
    <ul>
        <li><a href="#">关于</a></li>
    </ul>
</footer>

<script>

    // 页面加载之前执行
    $(function(){
    //    从数据库拿到数据建立导航栏
        build_nav_bar();
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
                // 调用建立文章的函数
                build_article_list(result);
            }
        });
    }

    // 传入result，建立文章列表
    function build_article_list(result) {
        var articleList = result.extend.pageInfo.list;
        totalPage = result.extend.pageInfo.pages;
        $.each(articleList, function (index, item) {
            // 博文的图片
            var img = $("<div></div>").addClass("image").append($("<img />").attr({
                "src" : "./static/image/test.png",
                "alt" : "这是图片",
                "height" : "180px",
                "width" : "280px"
            }));

            var articlePath = "./static/p/" + item.articleId + ".html";

            var title = $("<div></div>").addClass("title").append($("<a target='_blank'></a>").append(item.articleTitle).attr("href", articlePath));
            var discription = $("<div></div>").addClass("discription").append(item.articleAbstract);
            var time = $("<div></div>").addClass("datetime").append(new Date(item.articlePostDate));
            var articleBox = $("<div></div>").addClass("article-box").append(title).append(discription).append(time);
            var articleLi = $("<li></li>");
            articleLi.addClass("article")
                .append(img)
                .append(articleBox)
                .attr("click-id", item.articleId)
                .appendTo(".articles>ul");
            articleLi.click(function (){
                add_article_view(item.articleId, item.articleViewTime);
            });
        })
    }

    // 创建导航条，从数据库中拿到数据来创建
    function build_nav_bar() {
        $.ajax({
            url: "types",
            type: "get",
            success: function (result) {
                var superType = result.extend.types;
                $.each(superType, function (index, item) {
                    //第一级分类列表
                    var currTypeName = $("<a></a>").attr("href", "#").text(item.typeName);
                    var currSuperTypeDrop = $("<div></div>").addClass("drop-content");
                    //创建第二级分类列表
                    var subTypes = item.subTypes;
                    $.each(subTypes, function (index, item1) {
                        currSuperTypeDrop
                            .append($("<a></a>")
                                .attr("href", "#")
                                .append(item1.typeName)
                            );
                    });
                    $("<li></li>").addClass("drop-down clearfix")
                        .append(currTypeName)
                        .append(currSuperTypeDrop)
                        .prependTo(".types");
                });
            }
        });
    }

    // 点击文章的时候增加文章阅读量
    function add_article_view(articleId, viewCount) {
        $.ajax({
            url: "article/view",
            type: "POST",
            data: {
                "id": articleId,
                "viewCount": viewCount
            }
        });
    }

    // 搜索按钮
    $(document).on("click", "#search-btn", function () {
        var searchKey = $("#search-text").val();
        if("" == searchKey){
            alert("搜索内容不能为空！");
            return;
        }
        $.ajax({
            url: "search/" + searchKey,
            type: "GET"
        });
    });

    // 监听页面到底部的事件，拉取更多的文章
    $(window).scroll(function(){
        var scrollTop = $(this).scrollTop();
        var scrollHeight = $(document).height();
        var windowHeight = $(this).height();
        if(scrollTop + windowHeight == scrollHeight){
            currentPage += 1;
            get_article_page(currentPage);
        }
        // 如果文章拉取完了，就在最下面显示到底了
        if(currentPage == totalPage){
            $(".load-more span").text("已经到底了...");
        }
    });

</script>


</body>
</html>