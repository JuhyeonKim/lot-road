<%--
  Created by IntelliJ IDEA.
  User: JoonHo Son(NageSoft Corp.)
  Date: 2014. 1. 9.
  Time: 오후 1:51
--%>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/taglib.jsp"%>
<html>
<head>
	<title>오류가 발생하였습니다.</title>
</head>

<body>
<div id="wrapper404" style="text-align: center;">
	<a href="http://www.flickr.com/photos/laughingsquid/5722628966/" target="_blank">
	<img src="${ctx}/assets/img/500.jpg" alt="Oops!"><br>
	이미지 출처 : Flickr</a>
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