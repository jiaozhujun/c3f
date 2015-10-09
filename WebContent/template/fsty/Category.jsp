<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>摄像头内容分类列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
</head>
<body>
<p>管理摄像头内容分类</p>
<div>
<form name="addCategory" action="/fsty/addCat.do" method="post">
分类名称：<input type="text" name="cat.title" maxlength="60" size="60"/>
<input type="submit" name="submit" value="添加分类"/><br/>
</form>
<br/>
<table>
<thead><tr><td>id</td><td>title</td><td>ord</td><td>操作</td></tr></thead>
<tbody>
<s:iterator value="categoryList" status="rowstatus">
<tr>
	<td><s:property value="id"/></td>
	<td><s:property value="title"/></td>
	<td><s:property value="ord"/></td>
	<td><a href="/fsty/delCat.do?cat.id=<s:property value="id"/>">删除</a> | <a href="/fsty/modCat.do?cat.id=<s:property value="id"/>">修改</a></td>
</tr>
</s:iterator>
</tbody></table>
</div>
<div>
</div>
</body>
</html>