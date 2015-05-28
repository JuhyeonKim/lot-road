<%--
  Created by IntelliJ IDEA.
  User: JoonHo Son(NageSoft Corp.)
  Date: 2014. 1. 9.
  Time: 오전 11:50
--%>

<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%--<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>--%>
<%--<%@ taglib uri="http://www.springmodules.org/tags/commons-validator" prefix="v" %>--%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%--<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>--%>
<%--<%@ taglib uri="http://struts-menu.sf.net/tag-el" prefix="menu" %>--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>--%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>
<%@ taglib prefix="nt" uri="http://www.nagesoft.com/taglib" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="datePattern" value="yyyy-MM-dd" />
<% pageContext.setAttribute("newLineChar", "\r\n"); %>
<c:set var="user" value="${pageContext['request'].userPrincipal.principal}" />