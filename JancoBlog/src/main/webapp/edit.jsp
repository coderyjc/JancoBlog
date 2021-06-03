<%@ page import="com.Jancoyan.domain.User" %>
<%@ page import="com.Jancoyan.domain.Article" %><%--
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
	<script src="./static/js/editor.md/editormd.js"></script>
	<script>
		<%
			Article article = (Article) session.getAttribute("article");
			String content = (String) session.getAttribute("content");
			String title = "";
			if(article != null) title = article.getArticleTitle();
		%>

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
				imageUpload: true,
				imageUploadURL:"uploadarticlepicture",
				value: '<%=content == null ? "" : content%>'
			});

			// 直接发送请求拿到文章类型
			$.ajax({
				url: "types",
				type: "get",
				success: function (result) {
					var types = result.extend.types;
					// 目前的想法是把所有的子类型都存在一个list-div中，点击不同的父类型的时候显示出来相应的子类型
					// 这个是存放所有子类型的list
					var subTypeList = $("<div></div>").addClass("sub-type-list");

					//为每一个拿到的数据加上一个select-id。用来进行数据的选择
					$.each(types, function (index, item){
						var currTypeA = $("<a></a>")
								.attr("attr","javascript:;")
								.attr("select-id", item.typeId)
								.append(item.typeName);
						// 添加点击事件
						currTypeA.click(function (){
							// 将这个元素的状态变为active,其他的元素的active标签去掉
							$(".super-type>a").removeClass("active");
							$(this).addClass("active");
							//	把所有的子标签的选择状态置为0
							$(".sub-type>a").removeClass("active");
							// 显示出来对应子标签的所有选项, 隐藏其他的标签集的选型
							$(".sub-type").css("display", "none");
							var queryStr = ".sub-type[select-status=" + item.typeId + "]";
							$(queryStr).css("display", "block");
						});
					//	父标签添加
						currTypeA.appendTo(".super-type");
					//	遍历所有子标签
						// 把所有子标签添加到list中, 默认不显示
						var currSubTypes = $("<div></div>")
								.addClass("sub-type")
								.attr("select-status", item.typeId)
								.css("display", "none");
						// 遍历每一个子类型
						$.each(item.subTypes, function (index, item) {
							var currSubTypeA =
									$("<a></a>")
											.attr("href", "javascript:;")
											.attr("select-id", item.typeId)
											.append(item.typeName);
							// 添加点击事件
							currSubTypeA.click(function (){
							//	点击的元素显示active，其他都不显示
								$(".sub-type>a").removeClass("active");
								$(this).addClass("active");
							});
						//	添加到子列表中
							currSubTypeA.appendTo(currSubTypes);
						});
						// 添加到子列表的列表中
						currSubTypes.appendTo(".sub-type-list");
					});
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
			<input id="title_input_text" type="text" placeholder="请输入文章标题" value="<%=title ==
			null ? "" : title%>">
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
			<div class="super-type"></div>
			<div class="sub-type-list"></div>
		</div>
	</div>
	<script>

		// 绑定点击事件
		$(document).on("click", "#submit_btn", function () {
			// 文章标题
			var articleTitle = $("#title_input_text").val();
			var articleContent = $(".editormd-preview")[0].innerHTML;
			var mdContent = $('.editormd-markdown-textarea').val();
			var idxNumber = $(".active").length;
			var typeId;
			// 这个事件已经绑定好了，找时间把这个上传文字和图片的功能给做了。
			if("" == articleTitle){
				alert("标题不能为空");
				return;
			}
			if("" == articleContent){
				alert("内容不能为空");
				return;
			}
			if(0 == idxNumber){
				alert("请选择文章类型");
				return;
			} else if (1 == idxNumber){
				typeId = $(".active").attr("select-id");
			} else {
				typeId = $(".active")[1].attributes[1].value;
			}
			<%
				User user = (User) session.getAttribute("user");
				if(article == null){
			%>
			$.ajax({
				url:"article",
				type:"PUT",
				data: {
					'innerHTML': articleContent,
					'innerMD' : mdContent,
					'userId': <%=user.getUserId()%>,
					'userNickname': '<%=user.getUserNickname()%>',
					'title': $("#title_input_text").val(),
					'type': typeId, // 先用101试试
				},
				success: function (result) {
					alert("发布成功!");
					// 跳转到首页
					window.location.href="./index.jsp";
				}
			});
			<%
			} else {
			%>
<%--如果null， 就是要提交修改这个文章--%>
			$.ajax({
				url: "article/update",
				type:"POST",
				data: {
					'innerHTML': articleContent,
					'innerMD' : mdContent,
					'userNickname': '<%=user.getUserNickname()%>',
					'title': $("#title_input_text").val(),
					'type': typeId, // 先用101试试
				},
				success: function (result) {
					alert("修改成功!");
					// 跳转到首页
					window.location.href="./index.jsp";
				}
			})
			<%
			}
			%>

		});
	</script>
	</body>
</html>