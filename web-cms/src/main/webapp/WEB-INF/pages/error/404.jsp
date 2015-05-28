<%--
  Created by IntelliJ IDEA.
  User: JoonHo Son(NageSoft Corp.)
  Date: 2014. 1. 9.
  Time: 오전 11:34
--%>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/taglib.jsp"%>
<page:applyDecorator name="default" />
<html>
<head>
	<title>페이지를 찾을 수 없습니다.</title>
</head>

<body>
<div id="wrapper404" style="text-align: center;">
	<a href="http://shiningkey.tistory.com/m/post/view/id/115" target="_blank">
	<img src="${ctx}/assets/img/404.jpg" alt="Oops!"><br>
	이미지 출처 : 재미있는 404 페이지(Entertaining 404 Error Pages)</a>
</div>

<script type="text/javascript">
	$(document).ready(function () {
		$('#wrapper404').popupLayer(
			{
				isClone: false,
				backgroundColor: '#fff'
			}
		);
	});
</script>
</body>
</html>