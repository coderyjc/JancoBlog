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
    <base href="<%=basePath%>" />
</head>
<body>
<!-- 登录面板 -->
<div class="login-panel">
    <fieldset id="login-title" class="layui-elem-field layui-field-title" style="margin-top: 20px;">
        <legend>登录</legend>
    </fieldset>
    <form class="layui-form" action="" method="post">
        <div class="layui-form-item">
            <label class="layui-form-label">用户名</label>
            <div class="layui-input-block">
                <input id="userName" type="text" name="userName" autocomplete="off"
                       class="layui-input" lay-verify="required">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">密码</label>
            <div class="layui-input-block">
                <input id="userPwd" type="password" name="userPwd" autocomplete="off"
                       class="layui-input" lay-verify="required">
            </div>
        </div>

        <div class="layui-form-item">
            <div id="modal-buttons" class="layui-input-block">
                <button id="login-btn" type="submit" class="layui-btn" lay-submit
                        lay-filter="login">登录
                </button>
                <button id="register-btn" type="submit" class="layui-btn">注册</button>
                <button type="button" id="return-to-index" class="layui-btn layui-btn-primary">返回
                </button>
            </div>
        </div>
    </form>
</div>
<!-- 注册模态框 -->

<!-- 添加、编辑、查看的模态框 -->
<div id="myModal" class="modal">
    <div class="modal-content">
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
            <legend>注册</legend>
        </fieldset>
        <form class="layui-form" action="" lay-filter="information" method="post">
            <div class="layui-form-item">
                <label class="layui-form-label">用户名</label>
                <div class="layui-input-block">
                    <input id="modal-student-id" type="text" name="id" autocomplete="off"
                           class="layui-input" lay-verify="required">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">密码</label>
                <div class="layui-input-block">
                    <input id="modal-student-pwd" type="password" name="passwd" autocomplete="off"
                           class="layui-input" lay-verify="required">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">确认密码</label>
                <div class="layui-input-block">
                    <input id="modal-student-repeatpwd" type="password" name="passwd" autocomplete="off"
                           class="layui-input" lay-verify="required">
                </div>
            </div>

            <div class="layui-form-item">
                <div id="modal-buttons" class="layui-input-block">
                    <button id="insert-btn" type="submit" class="layui-btn" lay-submit
                            lay-filter="register">注册
                    </button>
                    <button type="button" id="hide-modal"
                            class="layui-btn layui-btn-primary">返回</button>
                </div>
            </div>
        </form>
    </div>
</div>

<script>
    var form = layui.form;
    var $ = layui.$;

    // 返回主界面
    $("#return-to-index").click(function () {
        window.location.href = "./index.jsp";
    })

    // 注册按钮
    $("#register-btn").click(function () {
        // 展示模态框
        $("#myModal").css("display", "block");
    });

    // 从注册界面返回按钮
    $("#hide-modal").click(function () {
        // 隐藏模态框
        $("#myModal").css("display", "none");
    });

    // 登录
    form.on('submit(login)', function(data){
        // 获取数据
        var post = data.field;
        // 发送请求
        console.log(post) //当前容器的全部表单字段，名值对形式：{name: value}
        $.ajax({
            url: "login",
            type: "POST",
            data: post,
            success: function (result) {
                if (result.extend.user != null){
                    layer.msg("登录成功");
                    setTimeout(function () {
                        window.location.href = "./index.jsp";
                    }, 1500);
                } else {
                    layer.alert("登陆失败");
                }
            }
        });
        return false;
    });

    // 注册
    form.on('submit(register)', function(data){
        // 获取数据
        var post = data.field;
        // console.log(post) //当前容器的全部表单字段，名值对形式：{name: value}
        $.ajax({
            url: "user",
            type: "PUT",
            data: post,
            success: function () {
                layer.alert("这是注册按钮");
            }
        })

    });

</script>
</body>
</html>