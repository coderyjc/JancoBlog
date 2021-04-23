<%@ page import="com.Jancoyan.domain.User" %><%--
  Created by Jancoyan.
  User: Jancoyan
  Date: 2021/4/12
  Time: 19:36
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
    <title>编辑博文</title>
    <base href="<%=basePath%>"/>

	<link rel="stylesheet" href="./static/js/editor.md/css/editormd.min.css">
	<link rel="stylesheet" href="./static/css/edit_other.css">
	<script src="./static/js/jquery-1.12.js"></script>
	<script src="./static/js/editor.md/editormd.min.js"></script>
	<script>
		$(function(){
			// 进行输入区的相关绑定
			var testEditor;
			testEditor = editormd("test-editormd", {
				palceholder:'欢迎',
				htmlDecode:"style, script, iframe",
				width: "100%",
				height: 700,
				syncScrilling:"single",
				emoji: true,
				path: "./static/js/editor.md/lib/",
				saceHTMLToTextarea: true,
				tocm: true,
				tex: true,
				flowChart: true,
				imageFormats: ["jpg", "gif", "png", "jpeg"],
				imageUploadURL:"",
			});

			// 直接发送请求拿到文章类型
			$.ajax({
				url: "types",
				type: "get",
				success: function (result) {
					var selectList = $("<select></select>");
					var superType = result.extend.types;
					$.each(superType, function (index, item) {
						var currGroup = $("<optgroup></optgroup>").attr("label", item.typeName);
						$.each(item.subTypes, function (index, item) {
							currGroup.append($("<option></option>")
									.append(item.typeName)
									.attr("value", item.typeId)
							);
						});
						selectList.append(currGroup);
					});
					selectList.appendTo("#type-select");
				}
			});

		});


	</script>
</head>
<body>
	<div class="nav">
		<!-- 回主页的"图标" -->
		<a href="./index.jsp" class="back-to-index">JancoBlog</a>
	</div>

	<div class="main">
		<!-- 标题框 -->
		<div id="title">
			<input id="title_input_text" type="text" placeholder="请输入文章标题">
			<input type="button" id="submit_btn" value="发布文章">
		</div>
		<!-- 文章输入框 -->
		<div class="clearfix">
			<div style="float: left;width:80%; border: 0px solid red; padding: 30px 10%; ">
				<div id="test-editormd">
					<textarea name="message" id="message"></textarea>
				</div>
			</div>
		</div>
		<!-- 选择文章类型 -->
		<div id="type-select">
			<span>文章类型：</span>
		</div>

		<!-- 选择文章标签 -->
	</div>
	<script>

		// 绑定点击事件
		$(document).on("click", "#submit_btn", function () {
			// 文章标题
			var articleTitle = $("#title_input_text").val();
			var articleContent = $(".editormd-preview")[0].innerHTML;
			// 这个事件已经绑定好了，找时间把这个上传文字和图片的功能给做了。
			if("" == articleTitle){
				alert("标题不能为空");
				return;
			}
			if("" == articleContent){
				alert("内容不能为空");
				return;
			}
			<%
				User user = (User) session.getAttribute("user");
			%>
			$.ajax({
				url:"article",
				type:"PUT",
				data: {
					'innerHTML': articleContent,
					'userId': <%=user.getUserId()%>,
					'userNickname': <%=user.getUserNickname()%>,
					'title': $("#title_input_text").val(),
					'type': 101 // 先用101试试
				},
				success: function (result) {
					alert("发布成功!");
					// 跳转到首页
					window.location.href="./index.jsp";
				}
			});
		});
	</script>
	</body>
</html>