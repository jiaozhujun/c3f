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
</head>
<body>
<div
	style="background: #bbbbbb; position: fixed; width: 1024; height: 768;">
<div
	style="background: #bbbbbb; width: 980; height: 740; float: left; margin: 20 20 20 20"><s:iterator
	value="brands">
	<div
		style="width: 490; height: 185; margin: 0px 0px 0px 0px; float: left">
	<table bgcolor="#CCCCCC">
		<tr align="left">
			<td rowspan="3" width="140" height="185">
			<div><a
				href="<%=request.getContextPath()%>/newsPaper/newspaper.do?uid=<s:property value="#session.uid"/>&id=<s:property value="id"/>"><img
				width="140" height="185" src="<s:property value="coverUrl"/>" /></a></div>
			</td>
			<td width="350" height="20"><s:property value="name" /></td>
		</tr>
		<tr>
			<td width="350" height="110" valign="top"><span> <s:property
				value="desc" escape="false" /> </span></td>
		</tr>
		<tr>
			<td width="350" height="20" valign="top" align="right"><s:if
				test="issubed == 1">
				<input id="<s:property value="id"/>" type="submit" value=" 取消订阅 " name="del"
					onclick="ajax(<s:property value="#session.uid"/>, <s:property value="id"/>, this.name)" />
			</s:if> <s:else>
				<input id="<s:property value="id"/>" type="submit" value=" 订阅 " name="add"
					onclick="ajax(<s:property value="#session.uid"/>, <s:property value="id"/>, this.name)" />
			</s:else></td>
		</tr>
	</table>
	</div>
</s:iterator></div>
</div>

</body>
</html>