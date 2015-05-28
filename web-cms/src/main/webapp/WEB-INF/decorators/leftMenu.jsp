<%--
  Created by IntelliJ IDEA.
  User: Kimjuhyeon
  Date: 15. 1. 20.
  Time: 오후 8:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../pages/include/taglib.jsp" %>
<html>
<head>
    <title></title>
</head>
<body>

<aside class="left-side sidebar-offcanvas">
  <!-- sidebar: style can be found in sidebar.less -->
  <section class="sidebar">
    <!-- Sidebar user panel -->
    <div class="user-panel">
      <%--<div class="pull-left image">--%>
        <%--<img src="${ctx}/assets/images/avatar5.png" class="img-circle" alt="User Image" />--%>
      <%--</div>--%>
      <div class="pull-left info">
        <p>안녕하세요, ${user.name}님</p>

        <%--<a href="#"><i class="fa fa-circle text-success"></i> Online</a>--%>
      </div>
    </div>
    <!-- search form -->
    <form action="#" method="get" class="sidebar-form">
      <div class="input-group">
        <input type="text" name="q" class="form-control" placeholder="Search..."/>
                            <span class="input-group-btn">
                                <button type='submit' name='seach' id='search-btn' class="btn btn-flat"><i class="fa fa-search"></i></button>
                            </span>
      </div>
    </form>
    <!-- /.search form -->
    <!-- sidebar menu: : style can be found in sidebar.less -->
    <ul class="sidebar-menu">
      <li class="active">
        <a href="${ctx}/main">
          <i class="fa fa-dashboard"></i> <span>Dashboard</span>
        </a>
      </li>
      <li>
        <a href="${ctx}/transcription/form">
          <i class="glyphicon glyphicon-pencil"></i> <span>필사</span> <small class="badge pull-right bg-green">new</small>
        </a>
      </li>
      <li class="treeview">
        <a href="#">
          <i class="glyphicon glyphicon-user"></i> <span>마이페이지</span>
          <i class="fa fa-angle-left pull-right"></i>
        </a>
        <ul class="treeview-menu">
          <li><a href="${ctx}/mypage/form"><i class="fa fa-angle-double-right"></i> 정보수정</a></li>
          <li><a href="${ctx}/mypage/password"><i class="fa fa-angle-double-right"></i> 비밀번호변경</a></li>
        </ul>
      </li>
      <li class="treeview">
        <a href="#">
          <i class="fa fa-laptop"></i> <span>관리</span>
          <i class="fa fa-angle-left pull-right"></i>
        </a>
        <ul class="treeview-menu">
          <li><a href="${ctx}/manage/member"><i class="fa fa-angle-double-right"></i> 회원 관리</a></li>
          <li><a href="${ctx}/manage/spring-mvc"><i class="fa fa-angle-double-right"></i> 성경 관리</a></li>
          <li><a href="${ctx}/manage/statistics"><i class="fa fa-angle-double-right"></i> 통계</a></li>
          <li><a href="${ctx}/code"><i class="fa fa-angle-double-right"></i> 코드 관리</a></li>
        </ul>
      </li>
    </ul>
  </section>
  <!-- /.sidebar -->
</aside>

</body>
</html>
