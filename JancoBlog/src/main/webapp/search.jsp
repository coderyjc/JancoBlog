<%--
  Created by Jancoyan.
  User: Jancoyan
  Date: 2021/4/11
  Time: 9:30
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
        <meta charset="utf-8">
        <title>Result</title>
        <link rel="stylesheet" type="text/css" href="./static/css/articlelist.css"/>
        <base href="<%=basePath%>"/>
    </head>
    <body>
        <!-- 导航条 -->
        <div class="nav">
            <!-- 回主页的"图标" -->
            <a href="#" class="back-to-index">JancoBlog</a>
        </div>
        <!-- 搜索结果的主体 -->
        <div class="main clearfix">
            <!-- 找到结果的条数 -->
            <h2 class="search-title">找到关于“Maven”的结果3条</h2>
            <!-- 找到的结果 -->
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
                </ul>
            </div>

        </div>
    </body>
</html>
