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
    <meta charset="UTF-8">
    <title>Welcome</title>
    <script src="./static/js/jquery-1.12.js"></script>
    <base href="<%=basePath%>" />
    <link rel="stylesheet" href="./static/css/index.css"/>
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

            </li>
            <li class="drop-down"><a href="#">Linux</a>

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
            <ul>
                <li class="article">
                    <div class="image">
                        <img src="./static/image/test.png" alt="这是图片" height="180px" width="280px">
                    </div>
                    <div class="article-box">
                        <div class="title"><a href="#">这是标题这是标题这是标题这是标题这是标题这是标题</a></div>
                        <div class="discription">这是简介这是简介这是简介这是简介这是简介这是简介这是简介这是简介这是简介这是简介这是简介这是简介这是简介这是简介这是简介这是简介这是简介这是简介这是简介这是简介这是简介这是简介这是简介这...</div>
                        <div class="datetime">2021-3-14</div>
                    </div>
                </li>
                <li class="article">
                    <div class="image">
                        <img src="./static/image/test.png" alt="这是图片" height="180px" width="280px">
                    </div>
                    <div class="article-box">
                        <div class="title"><a href="#">这是标题这是标题这是标题这是标题这是标题这是标题</a></div>
                        <div class="discription">这是简介这是简介这是简介这是简介这是简介这是简介这是简介这是简介这是简介这是简介这是简介这是简介这是简介这是简介这是简介这是简介这是简介这是简介这是简介这是简介这是简介这是简介这是简介这...</div>
                        <div class="datetime">2021-3-14</div>
                    </div>
                </li>
            </ul>
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





</script>


</body>
</html>