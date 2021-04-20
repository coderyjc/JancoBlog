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
</head>
<body>
<!-- 导航条 -->
<div class="nav">
    <!-- 回主页的"图标" -->
    <a href="#" class="back-to-index">JancoBlog</a>
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
                    <input type="text" name="search" placeholder="博文标题" id="search-text">
                    <input type="submit" name="submit" value="搜索" id="search-btn">
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
    var currentPage = 1;

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
            $("<li></li>").addClass("article")
                .append(img)
                .append(articleBox)
                .appendTo(".articles>ul");
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
    //
    // //为搜索按钮绑定搜索事件
    // $("#search-btn").click(function () {
    //     var searchKey = $("#search-text").val();
    //     if("" == searchKey){
    //         alert("搜索内容不能为空！");
    //         return;
    //     }
    //
    //     //发送ajax请求，分页返回到view中的searchlist页面
    //     $.ajax({
    //         url: "searcharticles/" + $("#search-text").val(),
    //         type: "GET",
    //         success: function (result) {
    //          //   build_article_list(result);
    //         }
    //     });
    //
    //
    // });

    // function get_articles_by_typeId(typeId, pn) {
    //     // ajax请求获取typeId类型的所有文章返回到view
    //     $.ajax({
    //         url: "/articles/type/" + typeId,
    //         type: "GET",
    //         data: pn,
    //         success: function (result) {
    //             // 清空原有的文章列表
    //             $(".articles>ul").empty();
    //         //    添加执行类型的文章
    //             build_article_list(result);
    //         }
    //     });
    // }

</script>


</body>
</html>