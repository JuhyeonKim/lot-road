<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/pages/include/taglib.jsp" %>

<c:set var="method" value="delete" />
<c:set var="action" value="${ctx}/estimate/deleteSelectedRows" />

<head>
	<title>견적문의현황</title>
</head>
<body>

<div class="page-header">
	<h1>
		<span class="glyphicon glyphicon-retweet"></span>
		<small></small>
		견적문의현황
	</h1>
</div>

<div class="panel panel-default">
	<div class="panel-heading">
		<h3 class="panel-title">검색</h3>
	</div>

	<div class="panel-body">

		<form action="${ctx}/estimate" id="searchForm" class="form-horizontal">

			<!-- 상태 검색 구간 -->
			<div class="form-group" style="height:34px;">
				<label for="id" class="col-sm-2 control-label">상태</label>

				<div class="col-sm-9">
					<div class="controls form-group row">
						<label class="checkbox-inline">
							<input type="radio" name="q.status" value=""
							       <c:if test="${param['q.status'] eq '' }">checked="checked"</c:if>
								/> &nbsp;전체
						</label>
						<label class="checkbox-inline">
							<input type="radio" id="statusN" name="q.status" value="N"
							       <c:if test="${param['q.status'] eq 'N' }">checked="checked"</c:if>
								/> &nbsp;처리중
						</label>
						<label class="checkbox-inline">
							<input type="radio" id="statusY" name="q.status" value="Y"
							       <c:if test="${param['q.status'] eq 'Y' }">checked="checked"</c:if>
								/> &nbsp;완료
						</label>
					</div>
				</div>
			</div>

			<!-- 기간 검색 구간 -->
			<div class="form-group">
				<label for="id" class="col-sm-2 control-label text-left">기간</label>

				<div class="input-group col-sm-4" >
						<input name="q.startDate" id="startDate" class="form-control" value="${param['q.startDate']}"
						       placeholder="시작일" />
					<div class="input-group-addon" style="border:0px;background-color:#FFF;">-</div>
						<input name="q.endDate" id="endDate" class="form-control" value="${param['q.endDate']}"
						       placeholder="종료일" />
				</div>
			</div>

			<!-- 지역 검색 구간 -->
			<div class="form-group">
				<label for="id" class="col-sm-2 control-label">시공지역</label>

				<div class="col-sm-2">
					<select class="form-control" name="q.area" id="area">
						<option value="">전체</option>
                       	<c:forEach items="${areaCodeList }" var="item">
							<option value="${item.code }" <c:if test="${param['q.area'] eq item.code}">selected="selected"</c:if>>${item.name }</option>
                       	</c:forEach>
					</select>
				</div>
			</div>

			<!-- 공간 검색 구간 -->
			<div class="form-group">
				<label for="id" class="col-sm-2 control-label">시공의뢰공간</label>

				<div class="col-sm-2">
					<select class="form-control" name="q.space" id="space" >
						<option value="">선택</option>
                       	<c:forEach items="${spaceCodeList }" var="item">
							<option <c:if test="${param['q.space'] eq item.code}">selected="selected"</c:if> value="${item.code }"> ${item.name }</option>
                       	</c:forEach>
					</select>
				</div>
				<div class="col-sm-2" id="hiddenSelect"  >
					<select class="form-control" name="q.secondSpace" id="secondSpace">
					</select>
				</div>
			</div>

			<!-- 직접검색 구간 -->
			<div class="form-group">
				<label for="id" class="col-sm-2 control-label">직접 검색 </label>

				<div class="col-sm-8">
					<input name="q.searchKeyword" id="searchKeyword" class="form-control" value="${param['q.searchKeyword']}"
					       placeholder="이름 또는 제목을 입력해 주세요." />
				</div>
				<button type="submit" class="btn btn-default" id="btnSearch"><span
					class="glyphicon glyphicon-search"></span> &nbsp; 검색
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
		<h3 class="panel-title">총 ${pager.totalCnt} 건</h3>
	</div>
	<div class="panel-body">
		<form:form class="form-horizontal" role="form" id="deleteForm" name="deleteForm" action="${action}"
		           method="${method}">
			<table class="table table-hover">
				<colgroup>
					<col>
					<col>
					<col>
					<col>
					<col>
					<col>
					<col>
					<col>
					<col>
				</colgroup>
				<thead>
					<tr> <!-- tr class = "info,success,danger,warning,active" -->
						<th class="text-center"><input type="checkbox" id="checkAll"></th>
						<th class="text-center">NO</th>
						<th class="text-center">제목</th>
						<th class="text-center">시공지역</th>
						<th class="text-center">시공의뢰 공간</th>
						<th class="text-center">작성자</th>
						<th class="text-center">상태</th>
						<th class="text-center">등록일</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${empty model }">
						<tr>
							<td colspan="7" class="text-center"> 등록된 견적문의가 없습니다.</td>
						</tr>
					</c:if>
					<c:forEach items="${model}" var="item">
						<tr>
							<td class="text-center"><input type="checkbox" value="${item.id}" id="idx" name="idx"></td>
							<td class="text-center">${item.positionIdx }</td>
							<td><a href="${ctx}/estimate/${item.id}/form"><nt:cutString limit="30" target="${item.title }"/></a></td>
							<td class="text-center">${item.area.name}</td>
							<td class="text-center">${item.space.name}<c:if test="${item.secondSpace != null}">(${item.secondSpace.name})</c:if></td>
							<td class="text-center">${item.name }</td>
							<td class="text-center">
								<c:if test="${item.status eq 'Y' }">완료</c:if>
								<c:if test="${item.status eq 'N' }">처리중</c:if>
							</td>
							<td class="text-center"><fmt:formatDate value="${item.regDate }"
							                                        pattern="${datePattern }" /></td>
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

	<div class="panel-footer text-right">
		&nbsp;
	</div>

</div>

<div class="form-group">
	<div class="col-sm-2 text-left">
		<button type="button" class="btn btn-default" id="btnToList" style="width:100px;height:40px;">전체목록</button>
	</div>
	<div class="col-sm-8"></div>
	<div class="col-sm-2 text-right">
		<button type="button" class="btn btn-warning" id="btnDelete" style="width:80px;height:40px;">삭제</button>
	</div>
</div>

<div style="height:50px;">
</div>

<c:set var="scripts" scope="request">
	<script type="text/javascript">

	var param_secondSpace = "${param['q.secondSpace']}";

	function changeSpace(obj){
		$("#secondSpace > option").remove();

	    $.ajax(
	            {
	                type: 'get',
	                url: ctx + '/code/'+$(obj).val()+'?ajax=true',
	                dataType: 'json',
	                success: function (d) {
	                    if (d.result === 'y') {
	                        var list = d.list;
	                        var length = list.length;

	                        if(length == 0){
//		                        	$("#hiddenSelect").css("display","none");

	                        }else{
								var option = $("<option />");
								option.prop("value","");
								option.text("전체");
								option.appendTo("#secondSpace");
		                        for (var i = 0; i < length; i++) {
		                            var data = list[i];
									var option = $("<option />");
									option.prop("value",data.code);
									option.text(data.name);
									option.appendTo("#secondSpace");
									if(param_secondSpace == data.code){
										option.prop("selected",true);
									}
		                        }
		                        $("#hiddenSelect").css("display","");
	                        }
	                    }
	                }
	            }
	        );


	}
		$(document).ready(function () {

			changeSpace($("#space"));

			$("#space").change(function(){
				changeSpace($(this));
			});


			//검색 후 시공 공간이 상업공간일때 공간선택2번 노출
			if ("${param.space}" == "상업공간") {
				$("#hiddenSelect").show();
			}

			//목록으로
			$("#btnToList").click(function () {
				document.location.assign("${ctx}/estimate");
				if (event.preventDefault) {
					event.preventDefault();
				} else {
					event.returnValue = false;
				}
			});

			//선택된 문의 삭제
			$("#btnDelete").click(function (event) {
				if ($("input[name=idx]:checked").length == 0) {
					alert("선택된 견적문의가 없습니다.");
					return;
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

			// 전체 선택 / 해제
			$("#checkAll").click(function (event) {
				if ($(this).is(':checked')) {
					$("input[type=checkbox]").prop("checked", true);
				} else {
					$("input[type=checkbox]").prop("checked", false);
				}
			});

			// 데이트피커(날짜선택)
			$("#startDate,#endDate").datepicker(
				{
					dateFormat: 'yy-mm-dd'
				}
			);

		});

		// 시공 공간을 상업공간으로 선택 시 공간선택2번 노출
		function fnShowHiddenSelect() {
			if ($("#space").val() == "상업공간") {
				$("#hiddenSelect").show(350);
			} else {
				$("#hiddenSelect").hide(350);
			}
		}
	</script>
</c:set>

</body>

