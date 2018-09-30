<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>展示文件</title>
</head>
<body>
<c:forEach items="${files}" var="file">
	<h1><a href="${basePath}home/fileDownload.do?fileName=${file.fnna}" title="点击下载">文件名：${file.fnna}</a></h1>
	<h1>原文件名：${file.ofna}</h1>
	<h1>上传日期：${file.updt}</h1>
	<h1>上传者：${file.scrm}</h1>
	<h1>文件状态：${file.flag}</h1>
	<hr>
</c:forEach>
</body>
</html>