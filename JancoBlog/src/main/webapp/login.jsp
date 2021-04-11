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
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>登录博客</title>
    <link rel="stylesheet" href="./static/css/login.css">
    <script src="./static/js/jquery-1.12.js"></script>
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
    <form>
        <input type="text" name="userName" id="userName" class="login-item" placeholder="用户名"> <br>
        <input type="password" name="userPwd" id="userPwd" class="login-item" placeholder="密码"> <br>
    </form>
    <button id="login-btn" class="login-item">登录</button>
</div>
<script>

<%--&lt;%&ndash;按下回车键进行登录&ndash;%&gt;--%>
<%--    $("#userPwd").keydown(function (event) {--%>
<%--        if(event.which == 13 && $("#userPwd").val() != "" && $("#userName").val() != ""){--%>
<%--            login();--%>
<%--        }--%>
<%--    });--%>

<%--点击登录按钮进行登录--%>
    $("#login-btn").click(login());

    //登录函数
    function login() {
        $.ajax({
            url:"login",
            type:"post",
            data: $("form").serialize(),
            success: function (result) {
                if(result.extend.userName != null){
                    alert("登录成功");
                    window.location.href="./index.jsp";
                }else{
                    alert("登录失败");
                    $("input").val("");
                }
            }
        });
    }
</script>
</body>
</html>