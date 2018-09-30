<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

</head>

<body>
	<h1>注册</h1>
	<form action="<%= basePath %>homePage/register.do" method="post" onsubmit="checksubmit()">
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td>姓名</td>
				<td><input type="text" name="username"/></td>
			</tr>
			<tr>
				<td>密码</td>
				<td><input type="text" name="password"/></td>
			</tr>
			<tr>
				<td>
					男<input type="radio" name="sex" checked="checked"/>
					女<input type="radio" name="sex"/>
				</td>
			</tr>
			<tr>
				<td>地址</td>
				<td><input type="text" name="address"/></td>
			</tr>
			<!-- <tr>
				<td>电话</td>
				<td><input type="text" name="phone"/></td>
			</tr> -->
			<tr>
				<td>邮箱</td>
				<td><input type="text" name="gwf2"/></td>
			</tr>
			<tr>
				<td><input type="submit" value="提交"/></td>
			</tr>
		</table>
	</form>
</body>
</html>