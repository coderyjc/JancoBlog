<%@ page import="com.Jancoyan.domain.User" %>
<%@ page import="com.Jancoyan.utils.TimeUtils" %>
<%--
  Created by Jancoyan.
  User: Jancoyan
  Date: 2021/4/11
  Time: 15:25
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
    <title>Title</title>
    <base href="<%=basePath%>"/>
    <script src="./static/js/jquery-1.12.js"></script>
    <link rel="stylesheet" href="./static/css/workbench.css">
</head>
<%--获取用户对象--%>
<%
    User user = (User) session.getAttribute("user");
%>
<body>    <!-- 导航条 -->
<div class="nav">
    <!-- 回主页的"图标" -->
    <a href="./index.jsp" class="back-to-index">JancoBlog</a>
</div>
<!-- 后台管理标题 -->
<h1>
    后台管理
</h1>

<!-- 主体 -->
<div class="main clearfix">
    <!-- 功能选择栏 -->
    <div class="function-list">
        <ul class="operate-list">
            <li><a href="./edit.jsp" id="write-eassy">写博文</a></li>
            <br>
            <li><a href="javascript:;" id="personal-info-item">个人信息</a></li>
            <li><a href="javascript:;" id="article-manage-item">文章管理</a></li>
        </ul>
    </div>
    <!-- 功能实现栏  -->
    <!--个人信息管理-->
    <div class="personal-info">
        <table>
            <tr>
                <td>用户名</td>
                <td><input type="text" disabled="disabled" name="name" id="user-name" value="<%=user.getUserNickname()%>"/></td>
            </tr>
            <tr>
                <td>邮箱</td>
                <td><input type="text" disabled="disabled" name="name" id="user-email"
                           value="<%=user.getUserEmail()%>" /></td>
            </tr>
            <tr>
                <td>性别</td>
                <td>
                    <div class="user-sex">
                        <div class="male <%=user.getUserSex() == 1 ? "choose" : ""%>">男</div>
                        <div class="famale <%=user.getUserSex() == 0 ? "choose" : ""%>">女</div>
                        <div class="none <%=user.getUserSex() == -1 ? "choose" : ""%>">未知</div>
                    </div>
                </td>
            </tr>
            <tr>
                <td>生日</td>
                <td><input type="text" disabled="disabled" name="name" id="user-create-date"
                           value="<%=TimeUtils.castDateTypeToDateString(user.getUserCreateDate())%>" placeholder="格式：2001-12-1"/></td>
            </tr>
        </table>
        <button id="edit-personal-info">编辑</button>
        <button id="save-personal-info">保存</button>
        <button id="cancel-personal-info">取消</button>
    </div>

    <!-- 文章管理 -->
    <div class="manage-articles">
        <table id="article-list">
            <thead>
                <tr>
                    <th>#</th>
                    <th>文章标题</th>
                    <th>发布时间</th>
                    <th>修改时间</th>
                    <th>浏览量</th>
                    <th>评论数</th>
                    <th>点赞数</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
        <div id="page-nav-bar">
        </div>
    </div>
</div>

<!-- 页脚 -->
<footer>

</footer>

<script type="text/javascript">
    // 点击相应的功能之后隐藏显示对应的功能，显示其他的功能
    $(function () {
        to_page(1);
    });

    // 点击“文章管理”按钮之后，隐藏其他卡片，显示文章管理的卡片，发送ajax请求，拿到分页数据
    $("#article-manage-item").click(function () {
        // 显示和隐藏卡片
        $(".personal-info").css("display","none");
        $(".manage-articles").css("display","block");
        // 直接去第 pn 页
        to_page(1);
    });

    // 点击”个人信息“按钮之后，隐藏其他卡片，显示个人信息卡片
    $("#personal-info-item").click(function () {
       // 显示和隐藏卡片
        $(".personal-info").css("display","block");
        $(".manage-articles").css("display","none");
    });

    // 点击“写博文”按钮之后，删除当前session中的article和content
    $("#write-eassy").click(function () {
        <%
        if(session.getAttribute("content") != null){
            session.removeAttribute("content");
        }
        if(session.getAttribute("article") != null){
            session.removeAttribute("article");
        }
        %>
    });

    //绑定修改个人信息点击事件
    $("#edit-personal-info").click(function () {
        // 显示保存和取消按钮
        $("#save-personal-info").css("display", "inline");
        $("#cancel-personal-info").css("display", "inline");
        $("#edit-personal-info").css("display", "none");
        // 将input标签变为可修改的
        $(".personal-info input").removeAttr("disabled");
        // 添加性别的选择click
        $.each($(".user-sex>div"), function (index, item) {
            item.onclick = function(){
                $(".user-sex>div").removeClass("choose");
                item.classList.add("choose");
            }
        });
    });

    // 目前还没有对用户名或邮箱进行查重
    // 保存并更新用户信息，也就是要更新user信息并重新跳转到这个页面
    $("#save-personal-info").click(function () {
        // 完成赋值
        var userName =  $("#user-name").val();
        var email = $("#user-email").val();
        var creat = $("#user-create-date").val();
        var sex = -1;
        if($(".male").hasClass("choose")){
            sex = 1;
        } else if($(".famale").hasClass("choose")) {
            sex = 0;
        }

        // 进行正则表达式格式判断
        var nameReg = /^[\u4E00-\u9FA5A-Za-z0-9]{2,20}$/;
        if(!nameReg.test(userName)){
            alert("用户名格式不对");
            return;
        }
        var emailReg = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
        if(!emailReg.test(email)){
            alert("用户邮箱格式不对")
            return;
        }
        var birthdayReg =
            /^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))$/;
        if(!birthdayReg.test(creat)){
            alert("用户生日日期不对");
            return;
        }

        $.ajax({
            url: "user",
            type: "POST",
            data: {
                "userNickName": userName,
                "userEmail" : email,
                "userSex" : sex,
                "userBirthdayDay" : creat
            },
            success : function (result) {
                alert("更新成功");
            //    变更当前的页面的信息，并加上disabled
            //    更新成功意味着修改之后的值全都符合规范，所以直接锁上输入文本框就行了
            //    锁上输入框的文本
                $("#user-name").attr("disabled", "disabled");
                $("#user-email").attr("disabled", "disabled");
                $("#user-create-date").attr("disabled", "disabled");
            //    移除性别按钮的点击事件
                $(".user-sex>div").removeAttr("click");
            //    隐藏“保存” 和“取消” 按钮
                $("#save-personal-info").css("display", "none");
                $("#cancel-personal-info").css("display", "none");
                $("#edit-personal-info").css("display", "block");
            }
        })
    });

    // 取消保存用户信息，也就是直接返回
    $("#cancel-personal-info").click(function () {
        // 填充input的信息
        $("#user-name").val("<%=user.getUserNickname()%>").attr("disabled", "disabled");
        $("#user-email").val("<%=user.getUserEmail()%>").attr("disabled", "disabled");
        $("#user-create-date").val("<%=TimeUtils.castDateTypeToDateString(user.getUserCreateDate())%>").attr("disabled", "disabled");
        //先把性别的选择去掉
        $(".user-sex>div").removeClass("active");
        //然后把应该选择的选上
        if(1 == <%=user.getUserSex()%>){
            $(".male").addClass("choose");
        }else if(0 == <%=user.getUserSex()%>){
            $(".famale").addClass("choose");
        }else {
            $(".none").addClass("choose");
        }
        $("#edit-personal-info").css("display","block");
        $("#cancel-personal-info").css("display", "none");
        $("#save-personal-info").css("display", "none");
    });

    //修改按钮，应该打开写文章的页面并填充上相应的文章
    //发送请求直接返回到edit页面
    $(document).on("click", ".edit-btn", function () {
        var id = $(this).attr("edit-id");
        $.ajax({
            url: "redirect/article",
            type: "POST",
            data: "id=" + id,
            success : function (){
                window.location.href = "./edit.jsp";
            }
        });
    });

    //删除按钮，点击按钮之后，从数据库中删除这一条记录并在服务端删除文件
    $(document).on("click", ".delete-btn", function () {
        var articleTitle = $(this).parents("tr").find("td:eq(1)").text();
        var articleId = $(this).attr("delete-id");

        if(confirm("确认删除" + articleTitle + "吗？")){
            $.ajax({
                url: "article/" + articleId,
                type:"DELETE",
                success: function (result) {
                    alert("删除成功");
                    to_page(currentPage);
                }
            });
        }
    });

    //去第 pn 页
    function to_page(pn) {
        $.ajax({
            url:"articles/" + "<%=user.getUserId()%>",
            type:"GET",
            data: "pn=" + pn,
            success: function (result) {
                currentPage = result.extend.pageInfo.pageNum;
                // 建立文章表格
                build_article_table(result);
                // 建立导航栏
                build_nav_bar(result);
            }
        });
    }
    
    //传入ajax请求拿到的结果，建立新的分页显示页面
    function build_article_table(result) {
        //清空表格原有信息
        $("#article-list tbody").empty();
        //建立新表格
        var articles = result.extend.pageInfo.list;
        $.each(articles, function (index, item) {
            var no = $("<td></td>").append((currentPage - 1) * 10 + index + 1);
            var titleTd = $("<td></td>").append(item.articleTitle);
            var createTd = $("<td></td>").append(dateFormat(item.articlePostDate));
            var updateTd = $("<td></td>").append(dateFormat(item.articleEditTime));
            var viewtimeTd = $("<td></td>").append(item.articleViewTime);
            var commentTd = $("<td></td>").append(item.articleCommentCount);
            var likeTd = $("<td></td>").append(item.articleLikeCount);
            var editButton = $("<button></button>").addClass("edit-btn").append("编辑");
            editButton.attr("edit-id", item.articleId);
            var deleteButton = $("<button></button>").addClass("delete-btn").append("删除");
            deleteButton.attr("delete-id", item.articleId)
            var operateTd = $("<td></td>").append(editButton).append(deleteButton);
            $("<tr></tr>").append(no)
            .append(titleTd)
            .append(createTd)
            .append(updateTd)
            .append(viewtimeTd)
            .append(commentTd)
            .append(likeTd)
            .append(operateTd)
            .appendTo("#article-list tbody")
        });
    }

    //建立分页导航栏
    function build_nav_bar(result) {
        $("#page-nav-bar").empty();
        var ul = $("<ul></ul>").addClass("pagination");
        var pageInfo = result.extend.pageInfo;
        //构建元素
        var firstPageLi =
            $("<li></li>").append($("<a></a>").append("首页").attr("href","javascript:;"));
        var prePageLi = $("<li></li>").append($("<a></a>").append("&laquo;"));
        if(pageInfo.hasPreviousPage == false){
            firstPageLi.addClass("disabled");
            prePageLi.addClass("disabled");
        }else{
            //为元素添加点击翻页的事件
            firstPageLi.click(function(){
                to_page(1);
            });
            prePageLi.click(function(){
                to_page(pageInfo.pageNum - 1);
            });
        }
        var nextPageLi = $("<li></li>").append($("<a></a>").append("&raquo;").attr("href", "javascript:;"));
        var lastPageLi = $("<li></li>").append($("<a></a>").append("末页").attr("href","javascript:;"));
        if(pageInfo.hasNextPage == false){
            nextPageLi.addClass("disabled");
            lastPageLi.addClass("disabled");
        }else{
            nextPageLi.click(function(){
                to_page(pageInfo.pageNum + 1);
            });
            lastPageLi.click(function(){
                to_page(pageInfo.pages);
            });
        }

        //添加首页和前一页 的提示
        ul.append(firstPageLi).append(prePageLi);
        //1,2，3遍历给ul中添加页码提示
        $.each(pageInfo.navigatepageNums,function(index,item){
            var numLi = $("<li></li>").append($("<a></a>").attr("href", "javascript:;").append(item));
            if(pageInfo.pageNum == item){
                numLi.addClass("active");
            }
            numLi.click(function(){
                to_page(item);
            });
            ul.append(numLi);
        });
        //添加下一页和末页 的提示
        ul.append(nextPageLi).append(lastPageLi);

        //把ul加入到nav
        var navEle = $("<nav></nav>").append(ul);
        navEle.appendTo("#page-nav-bar");
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