<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="./taglib.jsp" %>

<c:if test="${not empty param['decorator']}">
	<c:set var="decorator" value="&decorator=${param['decorator']}"/>
</c:if>
<!-- 페이지 -->
<ul class="pagination sm">
	<c:if test="${pager.currentPage > pager.pageLinkCnt}">
		<li><a href="${request.requestURI}${pager.queryString}&currentPage=${pager.prevPage}&dataPerPage=${pager.dataPerPage}${decorator}"
			data-page_navigation_page="${pager.prevPage}"
			data-page_navigation_data_per_page="${pager.dataPerPage}"
			class="prev">&laquo;</a></li>
	</c:if>
	<c:forEach var="counter" items="${pager.pageList}">
		<c:if test="${pager.currentPage != counter}">
			<li><a href="${request.requestURI}${pager.queryString}&currentPage=${counter}&dataPerPage=${pager.dataPerPage}${decorator}"
				data-page_navigation_page="${counter}"
				data-page_navigation_data_per_page="${pager.dataPerPage}"
			><c:out
				value="${counter}" /></a></li>
		</c:if>
		<c:if test="${pager.currentPage == counter}">
			<li class="active"><a href="#"><c:out value="${counter}" /></a></li>
		</c:if>
	</c:forEach>
	<c:if test="${pager.totalPageLinkCnt > pager.endPage}">
		<li><a href="${request.requestURI}${pager.queryString}&currentPage=${pager.nextPage}&dataPerPage=${pager.dataPerPage}${decorator}"
			data-page_navigation_page="${pager.nextPage}"
			data-page_navigation_data_per_page="${pager.dataPerPage}"
		   class="next">&raquo;</a></li>
	</c:if>
</ul>

