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
<p>摄像头列表 | <a href="/fsty/addWcl.do">添加摄像头</a> | <a href="/fsty/listCat.do" target="_blank">管理分类</a> | <a href="/fsty/listUser.do">用户列表</a></p>
<div>
<table>
<thead><tr><td>id</td><td>title</td><td>link</td><td>type</td><td>info</td><td>navi</td><td>country</td><td>city</td><td>catid</td><td>uid</td><td>edit</td><td>audit</td><td>publish</td><td>action</td></tr></thead>
<tbody>
<s:iterator value="contentWclList" status="rowstatus">
<tr>
	<td><img src="/images/fsty/<s:property value="id"/>.jpg" id="img_<s:property value="id"/>" width="100" height="75"/></td>
	<td><s:property value="title"/></td>
	<td><s:property value="link"/></td>
	<td><s:property value="videoType"/></td>
	<td><s:property value="info"/></td>
	<td><s:property value="navi"/></td>
	<td><s:property value="country"/></td>
	<td><s:property value="city"/></td>
	<td><s:property value="catid"/></td>
	<td><s:property value="uid"/></td>
	<td><s:property value="edit"/></td>
	<td><s:property value="audit"/></td>
	<td><s:if test="publish==1">已发布</s:if><s:else>未发布</s:else></td>
	<td><a href="/fsty/grabImage.do?c.id=<s:property value="id"/>">截图</a> | <a href="/fsty/editWcl.do?c.id=<s:property value="id"/>">修改</a> | <a href="/fsty/delWcl.do?c.id=<s:property value="id"/>">删除</a> | <a href="/fsty/pubWcl.do?c.id=<s:property value="id"/>&c.publish=<s:if test="publish==1">0">取消发布</s:if><s:else>1">发布</s:else></a></td>
</tr>
</s:iterator>
</tbody></table>
</div>
</body>
</html>