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
    <link rel="stylesheet" href="./static/css/articlelist.css">
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
                aTag = "<a href='./workbench'>后台</a>";
            }
        %>
        <%=aTag%>
    </div>
</div>
<!-- 除去导航条和footer的部分是主体main -->
<div class="main clearfix">
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
                <input type="text" name="search" placeholder="博文标题" id="search-text" />
                <button id="search-btn">搜索</button>
            </div>
        </ul>
    </div>

    <!-- 文章列表和标签导航 -->
    <!-- 列表和分页 -->
    <div class="index-content clearfix">
        <!-- 文章列表 -->
        <div class="left-content clearfix">
            <ul class="clearfix"></ul>
        </div>
        <!-- 文章热榜和标签分类 -->
        <div class="right-content">
            <!-- 文章热榜 -->
            <div class="article-rank-list">
                <div class="article-rank-title">
                    热门文章
                </div>
                <div class="segment-line"></div>
                <ul class="hot-articles"></ul>
            </div>

        </div>
    </div>
</div>


<script>

    // 页面加载完毕立即执行
    $(function(){
    //    从数据库拿到数据建立导航栏
        build_nav_bar();
    //   获取数据库中最新的10条记录
        get_article_page(1);
        // 加载文章热榜，也就是按照浏览量排序的前十名
        build_hot_rank();
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

            var articleBoxLi = $("<li></li>").addClass("article-box");
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
                .addClass("article-info")
                .append($("<div></div>").addClass("view-count").append("浏览量："+item.articleViewTime))
                .append($("<div></div>").addClass("post-time").append("发布日期："+dateFormat(item.articlePostDate)));
            // 向盒子中添加元素
            articleBoxLi.append(aTitle)
                .append(segmentLine)
                .append(articleAbstract)
                .append(articleInfo)
                .appendTo(".left-content ul");
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
                    var currTypeName =
                        $("<a target='_blank' ></a>").attr("href",
                            "./sortlist.jsp?type="+item.typeId).text(item.typeName);
                    var currSuperTypeDrop = $("<div></div>").addClass("drop-content");
                    //创建第二级分类列表
                    var subTypes = item.subTypes;
                    $.each(subTypes, function (index, item1) {
                        currSuperTypeDrop
                            .append($("<a target='_blank'></a>")
                                .attr("href", "sortlist.jsp?type=" + item1.typeId)
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

    // 页面加载完毕之后加载文章热榜
    function build_hot_rank() {
        $.ajax({
            url:"articles",
            type :"POST",
            success: function (result) {
                var hotArticles = result.extend.hotArticles;
                $.each(hotArticles, function (index, item) {
                    var hotArticleLi = $("<li></li>").addClass("hot-article");
                    var orderDiv = $("<div></div>").addClass("hot-order").append(index + 1);
                    var articlePath = "./static/p/" + item.articleId + ".html";
                    var titleA = $("<a></a>").attr("href", articlePath).append(item.articleTitle);
                    // 点击的时候增加阅读量
                    titleA.click(function (){
                        add_article_view(item.articleId);
                    })
                    var viewTime =
                        $("<div></div>").addClass("hot-article-views").append(item.articleViewTime);
                    hotArticleLi.append(orderDiv)
                    .append(titleA)
                    .append(viewTime)
                    .appendTo(".hot-articles");
                });
            }
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

    // 监听搜索按钮，搜索字段为空的时候
    $("#search-btn").click(function (){
       var keyWord = $("#search-text").val();
       if(keyWord == ""){
           alert("搜索字段不能为空");
           return;
       } else {
           window.location.href = "./search.jsp?keyword=" + keyWord;
       }
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
    });

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