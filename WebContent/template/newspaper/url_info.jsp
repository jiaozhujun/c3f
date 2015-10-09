<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/ajax.js"></script>
<style type="text/css">
.cont {
	padding-left: 50px;
}

#back {
	background-color: #999;
	height: 30px;
	width: 100%;
	line-height: 30px;
	right: 20px;
}
</style>
</head>
<body>
<div
	style="background: #bbbbbb; position: relative; width: 1024; height: 768;">
<div
	style="background: #bbbbbb; width: 980; height: 740; margin: 20 20 20 20">
<table bgcolor="#CCCCCC" width="100%">
	<tr>
		<td colspan="2" align="right">
		<div id='back'><a
			href='<%=request.getContextPath()%>/newsPaper/newspapers.do?uid=<s:property value="#session.uid"/>'>返回</a></div>
		</td>
	</tr>
	<tr>
		<td height="185" width="140">
		<div><img src="<s:property value="brand.coverUrl"/>"
			height="185" width="140"></div>
		</td>
		<td valign="top" class="cont">
		<div>
		<h1><s:property value="brand.name" /></h1>
		</div>
		<div><s:property value="brand.desc" escape="false" /></div>
		</td>
	</tr>
	<tr>
		<td height="20"><s:if test="brand.issubed == 1">
			<input id="<s:property value="brand.id"/>" type="submit"
				value=" 取消订阅 " name="del"
				onclick="ajax(<s:property value="#session.uid"/>, <s:property value="brand.id"/>, this.name)" />
		</s:if> <s:else>
			<input id="<s:property value="brand.id"/>" type="submit" value=" 订阅 "
				name="add"
				onclick="ajax(<s:property value="#session.uid"/>, <s:property value="brand.id"/>, this.name)" />
		</s:else></td>
		<td></td>
	</tr>
</table>
<hr>
<div
	style="background: #bbbbbb; width: 980; height: 30; margin: 20 20 20 20; text-align: left"><span>www.youcan.com.cn
北京远宽</span></div>
</div>
</div>
</body>
</html>