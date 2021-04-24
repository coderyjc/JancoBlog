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
    <title>搜索结果</title>
    <base href="<%=basePath%>"/>
</head>
<script>
    console.log(${articles});
</script>
<body>



</body>
</html>