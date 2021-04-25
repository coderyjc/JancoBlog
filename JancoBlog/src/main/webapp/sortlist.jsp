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
    <link rel="stylesheet" href="./static/css/sortlist.css">
    <script type="text/javascript" src="./static/js/jquery-1.12.js"></script>
</head>
<script type="text/javascript">

    var typeid = 0;
    typeid = <%=request.getParameter("type")%>;
    var currentPage = 1;

    $(function(){
        // 建立导航栏
        build_nav_bar();
        // 获得这个类型第一页的文章
        get_articles(typeid, 1);

        $(document).on("click", ".nav-title", function () {
            // 给所有菜单项的主标题添加事件
            // 点击主标题,子标题展示出来
            // 获取当前主标题下的子标题内容去
            var navContent = $(this).parent(".nav-menu").children(".nav-content");
            // 判断这个对象的display属性值是否为none, none:隐藏的, block:显示的
            if(navContent.css("display") != "none"){
                // 隐藏
                navContent.hide(200);
            }else{
                // 展示的时候,其他的应该关掉
                var sibling = navContent.parents(".nav-menu").siblings();
                sibling.each(function(){
                    $(this).children(".nav-content").hide(200);
                })
                // 展示
                navContent.show(200);
            }
        })



    });

</script>
<body>
<!-- 导航条 -->
<div class="nav">
    <!-- 回主页的"图标" -->
    <a href="./index.jsp" class="back-to-index">JancoBlog</a>
</div>

<!-- 主要容器，包括左边导航栏和右边文章主体 -->
<div class="main">
    <!-- 左边的文章类别导航栏 -->
    <div class="side-nav-bar">
        <div class="nav-bar-title"><a href="">分类查看</a></div>
        <div class="segment-line"></div>
        <!-- 菜单栏导航 -->
        <div class="nav-list"></div>
    </div>

    <!-- 右边的文章列表 -->
    <div class="article-sort-list">
        <ul>
            <li class="article-box">
                <a href="">这是标题</a>
                <div class="segment-line"></div>
                <div class="article-abstract">这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要</div>
                <div class="article-info">
                    <div class="view-count">浏览量:63</div>
                    <div class="post-time">发布日期:2021-4-12</div>
                </div>
            </li>
            <li class="article-box">
                <a href="">这是标题</a>
                <div class="segment-line"></div>
                <div class="article-abstract">这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要</div>
                <div class="article-info">
                    <div class="view-count">浏览量:63</div>
                    <div class="post-time">发布日期:2021-4-12</div>
                </div>
            </li>

        </ul>
    </div>

</div>

<script>

    // 建立悬浮的导航栏
    function build_nav_bar() {
        $.ajax({
            url: "types",
            type: "get",
            success: function (result) {
                var superType = result.extend.types;
                console.log(superType);
                $.each(superType, function (index, item) {
                    //第一级分类列表
                    var navMenu = $("<div></div>").addClass("nav-menu");
                    navMenu.append($("<div></div>").addClass("nav-title").append(item.typeName));
                    var navContent =
                        $("<div></div>").addClass("nav-content").css("display","none");
                    //创建第二级分类列表
                    var subTypes = item.subTypes;
                    $.each(subTypes, function (index, item1) {
                        navContent
                            .append($("<a></a>")
                                .attr("href", "#")
                                .append(item1.typeName)
                            );
                    });
                    navMenu.append(navContent).appendTo(".nav-list");
                });
            }
        });
    }

    // 发送ajax请求获取文章
    function get_articles(typeid, pn) {
        $.ajax({
            url: "type/" + typeid,
            type: "GET",
            data:"pn=" + pn,
            success: function (result) {
                console.log(result);
                build_article_list(result);
            }
        });
    }

    // 根据结果建立文章列表
    function build_article_list(result) {
        // 文章列表
        var articleList = result.extend.pageInfo.list;
        // 建立文章列表
        $.each(articleList, function (index, item){
            var articleBoxLi = $("<li><\li>").addClass("article-box");
            var articlePath = "./static/p/" + item.articleId + ".html";
            // box内部的a标签
            var aTitle = $("<a></a>").attr("href", articlePath).append(item.articleTitle);
            // 绑定增加浏览量的函数
            aTitle.click(function () {
                add_article_view(item.articleId);
            });
            //分割线
            var segmentLine = $('<div class="segment-line"></div>');
            // 摘要
            var articleAbstract = $("<div></div>").addClass("article-abstract")
            .append(item.articleAbstract);
            // 发布时间和浏览量
            var articleInfo = $("<div></div>")
                .addClass("article--info")
            .append($("<div></div>").addClass("view-count").append("浏览量："+item.articleViewTime))
            .append($("<div></div>").addClass("post-time").append("发布日期："+dateFormat(item.articlePostDate)));
            // 向盒子中添加元素
            articleBoxLi.append(aTitle)
            .append(segmentLine)
            .append(articleAbstract)
            .append(articleInfo)
            .appendTo(".article-sort-list ul");
        });
    }


    // 点击文章的时候增加文章阅读量
    function add_article_view(articleId) {
        $.ajax({
            url: "article/view",
            type: "POST",
            data: {
                "id": articleId,
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
