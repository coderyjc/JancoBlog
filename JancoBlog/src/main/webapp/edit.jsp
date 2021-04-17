<%--
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
    <title>编辑</title>
    <base href="<%=basePath%>"/>

	<link rel="stylesheet" href="./static/js/editor.md/css/editormd.min.css">
	<link rel="stylesheet" href="./static/css/edit_other.css">
	<script src="./static/js/jquery-1.12.js"></script>
	<script src="./static/js/editor.md/editormd.min.js"></script>
	<script type="text/javascript">
		$(function(){
			// 进行输入区的相关绑定
			var testEditor;
			testEditor = editormd("test-editormd", {
				palceholder:'欢迎',
				htmlDecode:"style, script, iframe",
				width: "100%",
				height:700,
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

			get_super_type();
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
			<button id="submit_btn">发布文章</button>
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
		// 直接获取父类型
		function get_super_type() {
			$.ajax({
				url: "types",
				type: "get",
				success: function (result) {
					var selectList = $("<select></select>");
					var superType = result.extend.types;
					$.each(superType, function (index, item) {
						var currGroup = $("<optgroup></optgroup>").attr("label", item.typeName);
						console.log(item);
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
		}
		
	</script>
	</body>
</html>