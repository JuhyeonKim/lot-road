<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/pages/include/taglib.jsp" %>

<c:set var="method" value="delete" />
<c:set var="action" value="${ctx}/admin/deleteSelectedRows" />

<head>
	<title>관리자 리스트</title>
</head>
<body>

<div class="page-header">
	<h1>
		<span class="glyphicon glyphicon-user"></span>
		<small></small>
		관리자 리스트
	</h1>
</div>

<div class="panel panel-default">
	<div class="panel-heading">
		<h3 class="panel-title">검색</h3>
	</div>

	<div class="panel-body">
		<form action="${ctx}/admin" id="searchForm" class="form-horizontal">
			<div class="form-group">
				<label for="searchKeyword" class="col-sm-3 control-label">관리자 검색</label>

				<div class="col-sm-6">
					<input name="searchKeyword" id="searchKeyword" class="form-control" value="${param.searchKeyword}"
					       placeholder="이름을 입력해 주세요." />
				</div>
				<button type="submit" class="btn btn-default" id="btnSearch"><span
					class="glyphicon glyphicon-search"></span>&nbsp;검색
				</button>
			</div>
		</form>
	</div>

	<div class="panel-footer text-left">
		&nbsp;
	</div>

</div>

<div class="panel panel-default">
	<div class="panel-heading">
		<h3 class="panel-title">총 ${pager.totalCnt} 명</h3>
	</div>
	<div class="panel-body">
		<form:form class="form-horizontal" role="form" id="deleteForm" name="deleteForm" action="${action}"
		           method="${method}">
			<table class="table table-hover">
				<colgroup>
					<col width="5%">
					<col width="10%">
					<col width="25%">
					<col width="20%">
					<col width="20%">
					<col width="20%">
				</colgroup>
				<thead>
					<tr> <!-- tr class = "info,success,danger,warning,active" -->
						<th class="text-center"><input type="checkbox" id="checkAll"></th>
						<th class="text-center">NO</th>
						<th class="text-center">구분</th>
						<th class="text-center">이름</th>
						<th class="text-center">연락처</th>
						<th class="text-center">등록일</th>
					</tr>
				</thead>
				<tbody id="contentRow">
					<c:if test="${empty model }">
						<tr>
							<td colspan="7" class="text-center">데이터가 없습니다.</td>
						</tr>
					</c:if>
					<c:forEach items="${model}" var="item">
						<tr>
							<td class="text-center"><input type="checkbox" value="${item.id }" name="idx"></td>
							<td class="text-center">${item.positionIdx }</td>
							<td class="text-center">${item.division }</td>
							<td class="text-center"><a href="${ctx }/admin/${item.id}/form">${item.name }</a></td>
							<td class="text-center">${item.phone}</td>
							<td class="text-center">
								<fmt:formatDate value="${item.regDate }" pattern="${datePattern }" />
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<hr>
		</form:form>
		<div class="text-center">
			<%@ include file="/WEB-INF/pages/include/pageNavi.jsp" %>
		</div>
	</div>

	<div class="panel-footer text-center">
		&nbsp;
	</div>
</div>

<div class="form-group">
	<div class="col-sm-2 text-left">
		<button type="button" class="btn btn-default" id="btnToList" style="width:100px;height:40px;">전체목록</button>
	</div>
	<div class="col-sm-7"></div>
	<div class="col-sm-3 text-right">
		<button type="button" class="btn btn-primary" id="btnInsert" style="width:80px;height:40px;"> 등록</button>
		<button type="button" class="btn btn-warning disabled" id="btnDelete" style="width:80px;height:40px;"> 삭제</button>
	</div>
</div>

<div style="height:50px;">
</div>

<c:set var="scripts" scope="request">
	<script type="text/javascript">
		var totalCount = ${pager.totalCnt};
		var currentTotal = $('#contentRow').find('tr').length;

		$(document).ready(function () {
			$("#btnInsert").click(function (event) {
				location.href = "${ctx}/admin/form";
				if (event.preventDefault) {
					event.preventDefault();
				} else {
					event.returnValue = false;
				}
			});

			$("#btnToList").click(function () {
				document.location.assign("${ctx}/admin");
				if (event.preventDefault) {
					event.preventDefault();
				} else {
					event.returnValue = false;
				}
			});

			$("#btnDelete").click(function (event) {
				if ($("input[name=idx]:checked").length == 0) {
					alert("선택된 관리자가 없습니다.");
				} else if (currentTotal == 1 || $("input[name=idx]:checked").length == totalCount) {
					alert('관리자 계정은 최소 하나 이상 존재하여야 합니다.');
				} else {
					if (confirm("삭제하시겠습니까?")) {
						var param = [];
						$("input[name=idx]:checked").each(function () {
							param.push($(this).val());
							$("#deleteForm").submit();
							event.preventDefault();
						});
					} else {
						event.preventDefault();
					}
				}
			});

			$("#checkAll").click(function () {
				if ($(this).is(':checked')) {
					$("input[type=checkbox]").prop("checked", true);
					$('#btnDelete').removeClass('disabled');
				} else {
					$("input[type=checkbox]").prop("checked", false);
					$('#btnDelete').addClass('disabled');
				}
			});

			$("input[name='idx']").each(function() {
				$(this).on('click', function() {
					var checkedLength = $("input[name='idx']:checked").length;

					if ($(this).prop('checked')) {
						$('#btnDelete').removeClass('disabled');

						$('#checkAll').prop('checked', checkedLength == currentTotal);
					} else {
						$('#checkAll').prop('checked', checkedLength == currentTotal);

						if (checkedLength == 0) {
							$('#btnDelete').addClass('disabled');
						}
					}
				});
			});
		});
	</script>
</c:set>

</body>
