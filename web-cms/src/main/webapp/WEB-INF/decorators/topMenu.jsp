<%--
  Created by IntelliJ IDEA.
  User: JoonHo Son(NageSoft Corp.)
  Date: 2014. 1. 9.
  Time: 오후 5:01
--%>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../pages/include/taglib.jsp"%>
<html>
<head>
	<title>Simple jsp page</title>
</head>

<body>
<decorator:usePage id="p" />
<%-- 현재 메뉴 확인 --%>
<c:set var="currentMenu" value="${pageContext.request.requestURI}" />

<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
	<div class="navbar-header">
		<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#topNavigation"></button>
		<a class="navbar-brand" href="${ctx}/">nagesoft</a>
	</div>

	<div class="collapse navbar-collapse" id="topNavigation">
		<!-- 왼쪽 메뉴 -->
		<ul class="nav navbar-nav">
			<li><a href="${ctx}/spring-mvc/menu1">메뉴1</a></li>
			<li class="dropdown">
				<a href="${ctx}/" class="dropdown-toggle" data-toggle="dropdown">메뉴2 <b class="caret"></b></a>
				<ul class="dropdown-menu">
					<li><a href="#">서브메뉴1</a></li>
					<li><a href="#">서브메뉴2</a></li>
					<li class="divider"></li>
					<li><a href="#">서브메뉴3</a></li>
				</ul>
			</li>
		</ul>

		<!-- 검색 -->
		<form class="navbar-form navbar-left" action="${ctx}/">
			<div class="form-group">
				<input type="text" class="form-control" placeholder="검색어를 입력하세요.">
			</div>
			<button type="submit" class="btn btn-default">찾기</button>
		</form>

		<!-- 오른쪽 메뉴 -->
		<ul class="nav navbar-nav navbar-right">
			<li><a href="#">메뉴3</a></li>
			<li class="dropdown">
				<a href="#" class="dropdown-toggle" data-toggle="dropdown">메뉴4 <b class="caret"></b></a>
				<ul class="dropdown-menu">
					<li><a href="#">서브메뉴3-1</a></li>
					<li><a href="#">서브메뉴3-2</a></li>
					<li class="divider"></li>
					<li><a href="#">서브메뉴3-3</a></li>
					<li><a href="#">서브메뉴3-4</a></li>
					<li class="divider"></li>
					<li><a href="#">서브메뉴3-5</a></li>
				</ul>
			</li>
		</ul>
	</div>
</nav>
</body>
</html>