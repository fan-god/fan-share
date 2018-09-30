<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="refresh" content="0.2;url=${basePath }home/showAllFile.do">
<title>中转页面</title>
</head>
<script type="text/javascript" language="javascript"> 
var bar=0;
var line="||"; 
var amount="||";
count() 
function count(){
bar=bar+2; 
amount =amount + line;
document.loading.chart.value=amount; 
document.loading.percent.value=bar+"%"; 
if (bar<99){
	setTimeout("count()",100);
}else {
	window.location = "${basePath }home/showAllFile.do";
	}
}
</script> 
<body>
<form name=loading> 
　<p align=center> <font color="#0066ff" size="2">正在跳转，请稍等</font><font color="#0066ff" size="2" face="Arial">...</font>
　　<input type=text name=chart size=46 style="font-family:Arial; font-weight:bolder; color:#0066ff; background-color:#fef4d9; padding:0px; border-style:none;"> 
　　<input type=text name=percent size=47 style="color:#0066ff; text-align:center; border-width:medium; border-style:none;"> 
　</p> 
</form> 
<p align="center"> 如果您的浏览器不支持跳转,<a style="text-decoration: none" href="${basePath }home/showAllFile.do">
	<font color="#FF0000">请点这里</font></a>.
</p>
</body>
</html>