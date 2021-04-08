<%--
  Created by Jancoyan.
  User: Jancoyan
  Date: 2021/3/16
  Time: 16:47
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
    <title>登录博客</title>
    <link rel="stylesheet" href="./static/css/login.css">
    <base href="<%=basePath%>" />
</head>
<body>
<!-- 页面最上面的导航栏 -->
<div class="nav"><a href="index.jsp">JancoBlog</a><span class="nav-author">@Jancoyan</span></div>
<!-- 登录面板 -->
<div class="login">
    <!-- "登录"字样 -->
    <div class="login-text">登录</div>
    <hr>
    <!-- 用户名和密码表单 -->
    <form action="user/login.do" method="post">
        <input type="text" name="uname" id="uname" class="login-item" placeholder="用户名"> <br>
        <input type="password" name="upwd" id="pwd" class="login-item" placeholder="密码"> <br>
        <input type="submit" name="uname" value="登 录" id="submit" class="login-item">
    </form>
</div>
</body>
</html>