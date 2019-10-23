<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>title</title>
</head>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<body>
<h2>Hello World!</h2>
<form action="<%=basePath%>web/user/v1.0/login" method="post">
    username:<input type="text" name="username"/></br>
    password:<input type="text" name="password"/></br>
    <input type="submit" value="submit"/>
</form>
</body>
</html>
