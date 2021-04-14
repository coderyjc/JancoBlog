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

	<link rel="stylesheet" href="./static/js/editor.md/css/editormd.min.css" />
	<script src="./static/js/editor.md/editormd.min.js"></script>
	<script type="text/javascript">
		$(function() {
			navAddClass('read_write');
			md_edit = editormd("blog_mdedit", { //注意1：这里的就是上面的DIV的id属性值
				placeholder : '  欢迎使用'+'${title}' +" 博客",
				width : "90%",
				height : 400,
				syncScrolling : "single",
				emoji : true,
				path : "${proPath }/js/md/lib/", //注意2：你的路径
				saveHTMLToTextarea : true,
				tocm : true, // Using [TOCM]
				tex : true, // 开启科学公式TeX语言支持，默认关闭
				flowChart : true, // 开启流程图支持，默认关闭
				/* 上传图片配置 */
				imageUpload : true,
				imageFormats : [ "jpg", "jpeg", "gif", "png", "bmp", "webp" ],
				imageUploadURL : "${proPath }/base/blog/upFile", //注意你后端的上传图片服务地址
			});
		});
	</script>
</head>
<body>
<!--模拟表单传输数据到后台-->
<form method="post" enctype="multipart/form-data" accept-charset="UTF-8">
	<div id="my-editormd">
		<!--Editor.md可以自动附加<textarea>标签-->
		<textarea id="my-editormd-markdown-doc" name="my-editormd-markdown-doc" style="display:none;"></textarea>
		<!-- 注意：name属性的值-->
		<!--textarea中name属性值，应该跟着div的ID"my-editormd"值来定，即(div的id值+)-markdown-doc-->
	</div>
	<input type="submit" value="  Send the artcile !" />
	</form>
	<!--需要引入的js文件和js配置-->
	<script type="text/javascript">
		$(function() {
			var editor = editormd("my-editormd", {//上面div的id值
				width: "90%",
				height: 540,
				theme : "dark", //主题
				path: "editor.md-master/lib/", // 这里我的html文件和editor.md-master文件位置如下图
				saveHTMLToTextarea : true//这个配置，方便post提交html源码表单，保存 HTML 到 Textarea它关乎后端是否可以获取到值
			});
		});
		//更多的editormd配置请参考下面或官网
	</script>
</body>
</html>