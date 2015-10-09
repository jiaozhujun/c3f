<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>添加摄像头</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<script src="/js/jquery-1.4.2.min.js"></script>
</head>
<body>
<p><a href="/fsty/listWcl.do">摄像头列表</a> | 添加摄像头</p>
<div>
<form name="addWclForm" action="/fsty/addWcl.do" method="post">
标题：<input type="text" name="c.title" maxlength="60" size="60"/><br/>
链接地址：<input type="text" name="c.link" maxlength="256" value="http://" size="60"/><br/>
视频类型：<select name="c.videoType">
<option value="1" selected="selected">MJPEG</option>
<option value="2">RTSP/H.264</option>
<option value="3">RTSP/WMV</option>
<option value="4">HTTP/H.264</option>
</select><br/>
简介：<textarea name="c.info" cols="40" rows="5"></textarea><br/>
位置信息：<input type="text" name="c.navi" maxlength="90" size="60"/><br/>
国家：<select name="c.country">
<option value="1" selected="selected">中国</option>
<option value="2">美国</option>
<option value="3">英国</option>
<option value="4">加拿大</option>
</select><br/>
城市：<select name="c.city">
<option value="0" selected="selected">暂时无</option>
</select><br/>
分类：<select name="c.catid" id="cat"><option value="0">请选择分类...</option></select>
<script type="text/javascript">
$.getJSON("/_s/commons/Cat_wcl.json",
        function(data){
          $.each(data.cats, function(i,item){
              $('#cat').append('<option value="' + item.id + '">' + item.title + "</option>");
          });
        });
</script>
 | <a href="/fsty/listCat.do" target="_blank">管理分类</a><br/>
<input type="hidden" name="c.uid" value="1"/><br/>
<input type="submit" name="submit" value="添加摄像头"/><br/>
</form>
</div>
<div>
</div>
</body>
</html>