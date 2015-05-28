<%@ page language="java" contentType="text/html;charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/pages/include/taglib.jsp" %>

<c:set var="method" value="put" />
<c:set var="action" value="${ctx}/estimate/update/${model.id}" />

<head>
	<title>견적문의</title>
</head>
<body>

<div class="page-header">
	<h1>
		<span class="glyphicon glyphicon-retweet"></span>
		<small></small>
		견적문의현황
	</h1>
</div>

<form:form commandName="model" class="form-horizontal" role="form"
           action="${action}"
           method="${method}" id="saveForm">
	<div class="panel panel-default">
		<div class="panel-heading">
			<h2 class="panel-title"><span
				class="glyphicon glyphicon-edit"></span>
				견적문의 내역
			</h2>
		</div>
		<div class="panel-body">
			<div class="form-group">
				<div class="control-group">
					<label class="col-md-3 control-label"
					       for="statusN">상태</label>

					<div class="col-md-7">
						<div class="controls form-group row">
							<label class="checkbox-inline">
								<input type="radio" id="statusN" name="status"
								       value="N"
								       <c:if test="${model.status eq 'N' }">checked="checked"</c:if>
									/> &nbsp;처리중
							</label>
							<label class="checkbox-inline">
								<input type="radio" id="statusY" name="status"
								       value="Y"
								       <c:if test="${model.status eq 'Y' }">checked="checked"</c:if>
									/> &nbsp;완료
							</label>
						</div>
					</div>
				</div>
			</div>

			<div class="col-md-12" style="margin-top:5px;" >
				<strong class="col-md-3 text-right" >이름</strong>
				<div class="col-md-7">
					${model.name}
				</div>
			</div>

			<div class="col-md-12" style="margin-top:5px;" >
				<strong class="col-md-3 text-right" >연락처</strong>
				<div class="col-md-7">
					${model.phone}
				</div>
			</div>
			<div class="col-md-12" style="margin-top:5px;" >
				<strong class="col-md-3 text-right" >E-mail</strong>
				<div class="col-md-7">
					${model.email}
				</div>
			</div>
			<div class="col-md-12" style="margin-top:5px;" >
				<strong class="col-md-3 text-right" >주소</strong>
				<div class="col-md-7">
					${model.address}
				</div>
			</div>

			<div class="col-md-12" style="margin-top:5px;" >
				<strong class="col-md-3 text-right" >제목</strong>
				<div class="col-md-7">
					${model.title}
				</div>
			</div>
			<div class="col-md-12" style="margin-top:5px;" >
				<strong class="col-md-3 text-right" >시공지역</strong>
				<div class="col-md-7">
					${model.area.name}
				</div>
			</div>

			<div class="col-md-12" style="margin-top:5px;" >
				<strong class="col-md-3 text-right" >시공 의뢰</strong>
				<div class="col-md-3">
					${model.space.name}
						<c:if test="${not empty  model.secondSpace}">
							(${model.secondSpace.name})
						</c:if>
				</div>
			</div>

			<div class="col-md-12" style="margin-top:5px;" >
				<strong class="col-md-3 text-right" >시공예산</strong>
				<div class="col-md-3">
					${model.budget.name}
				</div>
				<strong class="col-md-2 text-right" >시공희망일</strong>
				<div class="col-md-2">
					<fmt:formatDate value="${model.hopeDate }" pattern="${datePattern }" />
				</div>
			</div>

			<div class="col-md-12" style="margin-top:5px;" >
				<strong class="col-md-3 text-right" >상세내역</strong>
				<div class="col-md-7">
					${fn:replace(model.comment, newLineChar, '<br />')}
				</div>
			</div>
		</div>
	</div>
	<div class="form-group">
		<div class="col-sm-2 text-left">
			<button type="button" class="btn btn-default" id="btnToList"
			        style="width:80px;height:40px;">목록
			</button>
		</div>
		<div class="col-sm-7"></div>
		<div class="col-sm-3 text-right">
			<button type="submit" class="btn btn-primary" id="btnSave"
			        style="width:80px;height:40px;">확인
			</button>
			<c:if test="${not empty model.id}">
				<button type="button" class="btn btn-warning" id="btnDelete"
				        style="width:80px;height:40px;">삭제
				</button>
			</c:if>
		</div>
	</div>
</form:form>

<c:set var="scripts" scope="request">
	<script type="text/javascript">
		$(document).ready(function () {

			// 저장
			$("#btnSave").click(function () {
				if (confirm("저장하시겠습니까?")) {
					$("#saveForm").submit();
				} else {
					if (event.preventDefault) {
						event.preventDefault();
					} else {
						event.returnValue = false;
					}
				}
			});

			//목록으로
			$("#btnToList").click(function () {
				document.location.assign("${ctx}/estimate");
				if (event.preventDefault) {
					event.preventDefault();
				} else {
					event.returnValue = false;
				}
			});

			// 단건 삭제
			$("#btnDelete").click(function (event) {
				if (!confirm("정말 삭제 하시겠습니까?")) {
					if (event.preventDefault) {
						event.preventDefault();
					} else {
						event.returnValue = false;
					}
					return;
				}
				$("#saveForm").find("[name=_method]").val("delete");
				$("#saveForm").attr("action",
				                    "${ctx}/estimate/delete/${model.id}");
				$("#saveForm").submit();
			});

		});
	</script>
</c:set>
</body>
