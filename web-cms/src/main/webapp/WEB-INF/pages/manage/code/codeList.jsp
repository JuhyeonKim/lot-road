<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../../include/taglib.jsp" %>
<html>
<head>
	<title>공통코드 관리</title>
	<link rel="stylesheet" href="${ctx}/assets/js/bootstrap3.0.3/treeview/bootstrap-treeview.min.css">
</head>

<body>
<section class="content-header">
    <h1>
        코드 관리
        <small>Control panel</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="${ctx}/main"><i class="fa fa-dashboard"></i> Home</a></li>
        <li class="active">코드관리</li>
    </ol>
</section>
<!-- 좌측 메뉴 -->
<section class="content">
		<div class="row">
			<!-- 좌측 코드 트리 -->
			<div class="col-md-5">
				<div class="box box-primary">
					<div class="box-header">
						<i class="fa fa-code"></i>

						<h3 class="box-title">코드목록</h3>
						<button id="addNewCode" class="btn btn-sm btn-primary pull-right" style="margin:7px 10px 0;">
							신규등록
						</button>
					</div>
					<div class="box-body">
						<div id="codeTree"></div>
					</div>
				</div>
			</div>
			<div class="col-md-6">
				<div id="codeFormContainer" class="box box-success">
					<div class="box-header">
						<i class="fa fa-code"></i>

						<h3 id="formTitle" class="box-title">코드등록</h3>

						<button id="addNewSubCode" class="btn btn-sm btn-default disabled pull-right"
						        style="margin:7px 10px 0;">하위등록
						</button>
					</div>
					<div class="box-body">
						<form:form id="addCodeForm" commandName="code" method="POST" cssClass="form-horizontal">
							<!-- ===================================================== error message -->
							<spring:bind path="code.*">
								<div id="errorContainer"
								     class="alert alert-danger alert-dismissable fade in <c:if test="${empty status.errorMessages}">hide</c:if>">
									<i class="fa fa-ban"></i>
									<button type="button" class="close" data-dismiss="alert" aria-hidden="true">x</button>
									<span>
										<c:forEach var="error" items="${status.errorMessages}">
											<c:out value="${error}" escapeXml="false" /><br />
										</c:forEach>
									</span>
								</div>
							</spring:bind>
							<div class="form-group">
								<label for="parentCodeName" class="control-label col-md-3">상위코드</label>
								<form:hidden path="parent.code" id="parentCode" />

								<div class="col-md-7">
									<c:choose>
										<c:when test="${code.parent != null}">
											<form:input path="parent.name" id="parentCodeName"
											            cssClass="form-control input-sm" disabled="true" />
										</c:when>
										<c:otherwise>
											<form:input path="parent.name" id="parentCodeName"
											            cssClass="form-control input-sm" disabled="true" />
										</c:otherwise>
									</c:choose>
								</div>
							</div>
							<div class="form-group">
								<label for="codeName" class="control-label col-md-3">코드명</label>

								<div class="col-md-7">
									<form:input path="name" id="codeName" cssClass="form-control input-sm"
									            maxlength="200" />
								</div>
							</div>
							<div class="form-group">
								<label for="codeAlias" class="control-label col-md-3">별칭</label>

								<div class="col-md-7">
									<form:input path="alias" id="codeAlias" cssClass="form-control input-sm"
									            maxlength="100" />
								</div>
							</div>
							<div class="form-group">
								<label for="sortOrder" class="control-label col-md-3">정렬순서</label>

								<div class="col-md-7">
									<form:input path="sortOrder" id="sortOrder" cssClass="form-control input-sm"
									            maxlength="100" disabled="true" />
								</div>
							</div>
							<div class="form-group">
								<label for="useYNY" class="control-label col-md-3">사용여부</label>

								<div class="col-md-7">
									<label>
										<form:radiobutton path="useYN" id="useYNY" value="Y" />사용
									</label>
									<label>
										<form:radiobutton path="useYN" id="useYNN" value="N" />사용안함
									</label>
								</div>
							</div>
							<div class="form-group">
								<label for="description" class="control-label col-md-3">설명</label>

								<div class="col-md-7">
									<form:textarea path="description" id="description" cssClass="form-control input-sm"
									               rows="4" />
								</div>
							</div>
						</form:form>
					</div>
					<div class="box-footer clearfix">
						<div class="pull-right">
							<button id="saveCodeButton" class="btn btn-primary btn-sm">등록</button>
							<button id="cancelCodeButton" class="btn btn-default btn-sm">취소</button>
						</div>
					</div>
				</div>
			</div>
		</div>
</section><!-- /.content -->
<div id="ajaxModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="alertModalLabel"
     aria-hidden="true">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span>
					<span class="sr-only">닫기</span>
				</button>
				<h4 class="modal-title">
					<span class="glyphicon glyphicon-bell" style="color:red"></span>
					알림
				</h4>
			</div>
			<div class="modal-body">
				<p>삭제하시겠습니까?</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary">확인</button>
			</div>
		</div>
	</div>
</div>

<c:set var="scripts" scope="request">
	<script type="text/javascript" src="${ctx}/assets/js/bootstrap3.0.3/treeview/bootstrap-treeview.min.js"></script>
	<script type="text/javascript" src="${ctx}/assets/js/external/ext_code.js"></script>
</c:set>
</body>
</html>