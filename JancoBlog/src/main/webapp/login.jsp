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
    <link rel="stylesheet" href="./static/lib/layui/css/layui.css">
    <script src="./static/lib/layui/layui.js"></script>
    <script src="./static/js/jquery-1.12.js"></script>

    <base href="<%=basePath%>" />
</head>
<body>
<!-- 登录面板 -->
<div class="login-panel">
    <fieldset id="login-title" class="layui-elem-field layui-field-title" style="margin-top: 20px;">
        <legend>登录</legend>
    </fieldset>
    <form id="login-form" method="post">
    <div class="layui-form-item login-item">
        <label class="layui-form-label">用户名</label>
        <div class="layui-input-block">
            <input type="text" name="userName" lay-verify="required" lay-reqtext="用户名是必填项，岂能为空？"
                   placeholder="用户名" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item login-item">
        <label class="layui-form-label">密码</label>
        <div class="layui-input-block">
            <input type="password" name="userPwd" lay-verify="required" lay-reqtext="密码都没输,就想登录？" placeholder="密码" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item login-item">
        <div class="layui-input-block">
            <button id="login-btn" type="submit" class="layui-btn">登录</button>
            <button id="signup-btn" type="submit" class="layui-btn layui-btn-normal">注册</button>
            <button id="return-to-index" class="layui-btn layui-btn-primary">返回</button>
        </div>
    </div>
    </form>
</div>
<!-- 注册模态框 -->
<div id="myModal" class="modal">
    <div class="modal-content">
        <fieldset id="login-title" class="layui-elem-field layui-field-title" style="margin-top: 20px;">
            <legend>注册</legend>
        </fieldset>
        <div class="modal-body">
            <div class="layui-form-item login-item">
                <label class="layui-form-label">用户名</label>
                <div class="layui-input-block">
                    <input type="text" name="userName" lay-verify="required"
                           lay-reqtext="用户名是必填项，岂能为空？" placeholder="用户名" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item login-item">
                <label class="layui-form-label">密码</label>
                <div class="layui-input-block">
                    <input type="password" name="userPwd" lay-verify="required" lay-reqtext="密码都没输,就想登录？" placeholder="密码" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item login-item">
                <label class="layui-form-label">确认密码</label>
                <div class="layui-input-block">
                    <input type="password" name="userPwd" lay-verify="required" lay-reqtext="密码都没输,就想登录？" placeholder="密码" autocomplete="off" class="layui-input">
                </div>
            </div>
        </div>
        <div class="modal-btn">
            <button type="button" class="layui-btn">提交</button>
            <button id="modal-return" type="button" class="layui-btn layui-btn-primary">返回</button>
        </div>
    </div>
</div>

<script>

    // 注册按钮,绑定弹出注册模态框
    $("#signup-btn").click(function(){
        $("#myModal").css("display", "block");
    });
    // 关闭注册模态框
    $("#modal-return").click(function(){
        $("#myModal").css("display", "none");
    });
    // 返回到 index
    $("#return-to-index").click(function() {
        window.location.href="./index.jsp";
    });
    <%--点击登录按钮进行登录--%>
    $("#login-btn").click( function() {
        //登录校验
        $.ajax({
            url:"login",
            type:"post",
            data: $("#login-form").serialize(),
            success: function (result) {
                console.log(result);
                if(result.extend.user != null){
                    alert("登录成功");
                    window.location.href="./index.jsp";
                }else{
                    alert("登录失败");
                    $("input").val("");
                }
            }
        });
    });

</script>
</body>
</html>