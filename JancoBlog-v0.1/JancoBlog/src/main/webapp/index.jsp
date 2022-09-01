<%@ page import="com.Jancoyan.domain.User" %>
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
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Welcome</title>
    <link rel="stylesheet" href="./static/css/index.css">
    <link rel="stylesheet" href="./static/lib/layui/css/layui.css">
    <script src="./static/lib/layui/layui.js"></script>
    <script src="./static/js/jquery-1.12.js"></script>
    <script>
        var totalPage = 0;
        var currentPage = 1;
    </script>
</head>
<body>
<!-- 顶部导航栏 -->
<nav class="clearfix">
    <ul id="top-nav-bar" class="layui-nav layui-bg-black" layui-filter="top-nav">
        <li class="layui-nav-item layui-this"><a href="">首页</a></li>
        <li class="layui-nav-item"><a href="javascript:;">AI</a><dl class="layui-nav-child"><dd><a target="_blank" href="sortlist.jsp?type=201">图像识别</a></dd></dl></li>
        <li class="layui-nav-item"><a target="_blank" href="./sortlist.jsp?type=1">计算机基础<i class="layui-icon layui-icon-down layui-nav-more"></i></a><dl class="layui-nav-child"><dd><a target="_blank" href="sortlist.jsp?type=101">数据结构与算法</a></dd><dd><a target="_blank" href="sortlist.jsp?type=102">计算机网络</a></dd><dd><a target="_blank" href="sortlist.jsp?type=103">计算机组成原理</a></dd><dd><a target="_blank" href="sortlist.jsp?type=104">操作系统</a></dd></dl></li>
        <li class="layui-nav-item"><a target="_blank" href="./sortlist.jsp?type=3">前端<i class="layui-icon layui-icon-down layui-nav-more"></i></a><dl class="layui-nav-child"><dd><a target="_blank" href="sortlist.jsp?type=301">HTML</a></dd><dd><a target="_blank" href="sortlist.jsp?type=302">CSS</a></dd><dd><a target="_blank" href="sortlist.jsp?type=303">JavaScript</a></dd><dd><a target="_blank" href="sortlist.jsp?type=304">JQuery</a></dd><dd><a target="_blank" href="sortlist.jsp?type=305">Ajax</a></dd><dd><a target="_blank" href="sortlist.jsp?type=306">Java图形界面</a></dd></dl></li>
        <li class="layui-nav-item"><a target="_blank" href="./sortlist.jsp?type=4">Java后端<i class="layui-icon layui-icon-down layui-nav-more"></i></a><dl class="layui-nav-child"><dd><a target="_blank" href="sortlist.jsp?type=401">Spring</a></dd><dd><a target="_blank" href="sortlist.jsp?type=402">SpringMVC</a></dd><dd><a target="_blank" href="sortlist.jsp?type=403">MyBatis</a></dd><dd><a target="_blank" href="sortlist.jsp?type=404">JDBC</a></dd><dd><a target="_blank" href="sortlist.jsp?type=406">SSM整合</a></dd></dl></li>
        <li class="layui-nav-item"><a target="_blank" href="./sortlist.jsp?type=5">Python<i class="layui-icon layui-icon-down layui-nav-more"></i></a><dl class="layui-nav-child"><dd><a target="_blank" href="sortlist.jsp?type=501">Python基础知识</a></dd><dd><a target="_blank" href="sortlist.jsp?type=502">PythonWeb</a></dd><dd><a target="_blank" href="sortlist.jsp?type=503">PyQt</a></dd></dl></li>
        <li class="layui-nav-item"><a target="_blank" href="./sortlist.jsp?type=6">数据库<i class="layui-icon layui-icon-down layui-nav-more"></i></a><dl class="layui-nav-child"><dd><a target="_blank" href="sortlist.jsp?type=601">MySQL</a></dd></dl></li>
        <li class="layui-nav-item"><a target="_blank" href="./sortlist.jsp?type=7">大数据<i class="layui-icon layui-icon-down layui-nav-more"></i></a><dl class="layui-nav-child"><dd><a target="_blank" href="sortlist.jsp?type=701">HDFS</a></dd></dl></li>
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

<!-- 欢迎页面,需要向下滑动 -->
<div class="welcome-page">
    <h1 style="margin-top: 60px; padding-top: 9%;">JANCOYAN</h1>
    <div class="quote">十年饮冰，难凉热血。</div>
</div>

<script type="text/javascript">
    $(function(){
        var window_h = $(window).height();
        var window_w = $(window).width();
        $(".welcome-page").height(window_h - 60);
        $(".welcome-page").width(window_w);
    })

</script>

<!-- 向下滑动打开文章列表 -->
<div class="main clearfix">

    <!-- 左右布局 -->
    <!-- 左边是文章列表 -->
    <div class="left-content">
        <div class="layui-bg-gray" style="padding: 30px;">
            <div id="article-list" class="layui-row layui-col-space15">
<%--               文章列表在这里--%>
            </div>
        </div>
    </div>

    <!-- 右边是热门文章 -->
    <div class="right-content">

        <!-- 搜索按钮放在这里 -->
        <div class="search">
            <input type="text" name="title" id="search-text" placeholder="搜索文章	" autocomplete="off"
                   class="layui-input">
            <button id="search-btn" class="layui-btn layui-btn-primary layui-border"><i
                    class=" layui-icon layui-icon-search"></i></button>
        </div>

        <div class="layui-collapse" lay-accordion="">
            <div class="layui-colla-item">
                <h2 class="layui-colla-title">浏览量</h2>
                <div class="layui-colla-content layui-show">
                    <ul id="rank-by-view"></ul>
                </div>
            </div>
            <div class="layui-colla-item">
                <h2 class="layui-colla-title">点赞</h2>
                <div class="layui-colla-content">
                    <ul id="rank-by-like"></ul>
                </div>
            </div>
            <div class="layui-colla-item">
                <h2 class="layui-colla-title">评论</h2>
                <div class="layui-colla-content">
                    <ul id="rank-by-comment"></ul>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 折叠面板的内容用流加载加载出来-->


<!-- 页脚的内容 -->
<div class="footer">
    <a href="javascript:;">联系我</a>
</div>


<script>

    layui.use('element', function(){
        var element = layui.element;
    });

    // 页面加载完毕立即执行
    $(function(){
    //    从数据库拿到数据建立导航栏
    //     build_nav_bar();
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
            // 卡片的父级col列表
            var articleCol = $("<div></div>").addClass("layui-col-md");
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
            articleCol.append(articleCard).appendTo(".left-content #article-list");
        })
    }

    // 页面加载完毕之后加载文章热榜
    function build_hot_rank() {
        // 获取阅读量排行
        $.ajax({
            url:"rank",
            type :"GET",
            success: function (result) {
                var articles = result.extend.viewRank;
                build_rank("#rank-by-view", articles, 1);
            }
        });
        // 获取点赞量排行
        $.ajax({
            url:"rank",
            type :"POST",
            success: function (result) {
                var articles = result.extend.likeRank;
                build_rank("#rank-by-like", articles, 2);
            }
        })
        // 获取评论量排行
        $.ajax({
            url:"rank",
            type :"PUT",
            success: function (result) {
                var articles = result.extend.commentRank;
                build_rank("#rank-by-comment", articles, 3);
            }
        })
    }

    /**
     * hot_rank 是查询到的jq对象
     * list 是文章列表
     * type
     *  - 1  view
     *  - 2  like
     *  - 3  comment
     */
    function build_rank(hot_rank, list, type) {
        $.each(list, function (index, item) {
            let path = "./static/p/" + item.articleId + ".html";
            let idxDiv = $("<div class='rank-index'></div>").append(index + 1);
            let titleDiv =
                $("<div'></div>").append($("<a></a>").attr("href", path).append(item.articleTitle));
            let count = $("<div class='rank-count'></div>").append(
                type == 1 ? item.articleViewTime :
                    type == 2 ? item.articleLikeCount :
                        item.articleCommentCount);
            $("<li></li>").append(idxDiv).append(titleDiv).append(count).appendTo(hot_rank);
        })
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

    // 点击文章的时候增加文章阅读量
    function add_article_view(articleId) {
        $.ajax({
            url: "article/view",
            type: "POST",
            data: {
                "id": articleId,
            },
            success: function () {
            }
        });
    }

    // 监听搜索按钮，搜索字段为空的时候
    $("#search-btn").click(function (){
       var keyWord = $("#search-text").val();
       if(keyWord == ""){
           layer.alert("搜索字段不能为空");
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
        if(scrollTop + windowHeight == scrollHeight && currentPage < totalPage){
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