<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/10/22/0022
  Time: 16:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    %>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Title</title>
</head>
<body>
Welcome！<shiro:principal/>
<br/>
<shiro:hasAnyRoles name="user">
    <a href="<%=basePath%>user.jsp">User Page</a>
</shiro:hasAnyRoles>
<br/>
<shiro:hasAnyRoles name="admin">
    <a href="<%=basePath%>admin.jsp">Admin Page</a>
</shiro:hasAnyRoles>
<br/>
<a href="<%=basePath%>web/user/v1.0/logout">Logout</a>
</body>
</html>
