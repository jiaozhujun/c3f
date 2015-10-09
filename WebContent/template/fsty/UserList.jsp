<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>NMS - 测试</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
</head>
<body>
<p>用户列表 | <a href="/fsty/listWcl.do">摄像头列表</a></p>
<div>
<table>
<thead><tr><td>id</td><td>userName</td><td>userGroup</td><td>userKey</td><td>mdn</td><td>deviceid</td><td>deviceType</td><td>userType</td><td>regTime</td><td>lastLoginTime</td><td>lastLoginPlace</td><td>state</td><td>score</td><td>action</td></tr></thead>
<tbody>
<s:bean name="com.youcan.core.util.Text" var="textUtil"/>
<s:iterator value="userList" status="rowstatus" var="user">
<tr>
	<td><s:property value="id"/></td>
	<td><s:property value="userName"/></td>
	<td><s:property value="userGroup"/></td>
	<td><s:property value="userKey"/></td>
	<td><s:property value="mdn"/></td>
	<td><s:property value="deviceid"/></td>
	<td><s:property value="deviceType"/></td>
	<td><s:property value="userType"/></td>
	<td><s:property value="regTime"/></td>
	<td><s:property value="lastLoginTime"/></td>
	<td><s:property value="lastLoginPlace"/></td>
	<td><s:property value="state"/></td>
	<td><s:property value="score"/></td>
	<td><a href="/fsty/delUser.do?u.id=<s:property value="id"/>">删除</a> | <a href="/fsty/activeUser.do?u.id=<s:property value="id"/>&u.state=<s:if test="state==1">0">冻结</s:if><s:else>1">激活</s:else></a></td>
</tr>
</s:iterator>
</tbody></table>
</div>
</body>
</html>