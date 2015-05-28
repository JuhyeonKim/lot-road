<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/pages/include/taglib.jsp" %>

<c:choose>
	<c:when test="${not empty model.id}">
		<c:set var="method" value="put" />
		<c:set var="action" value="${ctx}/admin/update/${model.id}" />
		<c:set var="isInsert" value="false"/>
	</c:when>
	<c:when test="${empty model.id}">
		<c:set var="method" value="post" />
		<c:set var="action" value="${ctx}/admin/insert" />
		<c:set var="isInsert" value="true"/>
	</c:when>
</c:choose>

<head>
<title>관리자 관리</title>
</head>
<body>

<div class="page-header">
	<h1>
		<span class="glyphicon glyphicon-user"></span> <small></small>
		관리자 관리
	</h1>
</div>

<form:form class="form-horizontal" role="form" action="${action}" method="${method}" id="saveForm">
	<input type="hidden" name="checkAccount" id="checkAccount" value="false">
	<div class="panel panel-default">
		<div class="panel-heading">
			<h2 class="panel-title"><span class="glyphicon glyphicon-edit"></span>
			<c:if test="${!isInsert}">수정</c:if>
			<c:if test="${isInsert}">등록</c:if>
			</h2>
		</div>
		<div class="panel-body">
			<div class="form-group">
				<label class="col-md-3 control-label" for="title">구분
					<c:if test="${isInsert}"><span style="color:red;">*</span></c:if>
				</label>
				<div class="col-md-7">
					<div class="form-group row">
						<div class="col-md-10">
							<input type="text" class="form-control" id="division"
								name="division" maxlength="20"
								placeholder="구분" value="${model.division}" />
						</div>
					</div>
				</div>
				<label class="col-md-3 control-label" for="title">이름
					<c:if test="${isInsert}"><span style="color:red;">*</span></c:if>
				</label>
				<div class="col-md-7">
					<div class="form-group row">
						<div class="col-md-10">
							<input type="text" class="form-control" id="name"
								name="name" maxlength="20"
								<c:if test="${!isInsert}">readonly</c:if>
								placeholder="이름" value="${model.name}" />
						</div>
					</div>
				</div>
				<label class="col-md-3 control-label" for="title">아이디
					<c:if test="${isInsert}"><span style="color:red;">*</span></c:if>
				</label>
				<div class="col-md-7">

					<c:if test="${isInsert}">
						<div class="form-group row">
							<div class="col-md-8">
								<input type="text" class="form-control" id="account"
									name="account" maxlength="20"
									<c:if test="${!isInsert}">readonly</c:if>
									placeholder="아이디" value="${model.account}" />
							</div>
							<div class="col-md-3">
								<button type="button" class="btn btn-default" id="duplicationCheck">중복확인</button>
							</div>
						</div>
					</c:if>

					<c:if test="${!isInsert}">
						<div class="form-group row">
							<div class="col-md-10">
								<input type="text" class="form-control" id="account"
									name="account" maxlength="20"
									<c:if test="${!isInsert}">readonly</c:if>
									placeholder="아이디" value="${model.account}" />
							</div>
						</div>
					</c:if>

				</div>
				<label class="col-md-3 control-label" for="title">비밀번호
					<c:if test="${isInsert}"><span style="color:red;">*</span></c:if>
				</label>
				<div class="col-md-7">
					<div class="form-group row">
						<div class="col-md-10">
							<input type="password" class="form-control" id="password"
								name="password" maxlength="20"
								<c:if test="${!isInsert}">readonly</c:if>
								placeholder="비밀번호" value="${model.password}" />
						</div>
					</div>
				</div>
				<label class="col-md-3 control-label" for="title">연락처
					<c:if test="${isInsert}"><span style="color:red;">*</span></c:if>
				</label>
				<div class="col-md-7">
					<div class="form-group row">
						<div class="col-md-10">
							<input type="text" class="form-control" id="phone"
								name="phone" maxlength="20"
								placeholder="010-1234-5678" value="${model.phone}" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="form-group">
		<div class="col-sm-2 text-left">
			<button type="button" class="btn btn-default" id="btnToList" style="width:80px;height:40px;">목록</button>
		</div>
		<div class="col-sm-7"></div>
			<div class="col-sm-3 text-right">
				<button type="submit" class="btn btn-primary" id="btnSave" style="width:80px;height:40px;">확인</button>
				<c:if test="${not empty model.id}">
					<button type="button" class="btn btn-warning" id="btnDelete" style="width:80px;height:40px;">삭제</button>
				</c:if>
		</div>
	</div>
</form:form>


<c:set var="scripts" scope="request">
<script type="text/javascript">
	$(document).ready(function(){

		$("input[name=account]").change(function(){
			$("#checkAccount").val("false");
		});

		//영문 정규식
		var engPattern 	   = new RegExp(/^[a-z0-9_]+$/);
		//전화번호 정규식
		var phonePattern = new RegExp(/^(\d{3})-?(\d{1,2})\d{2}-?\d(\d{3})$/);

		/*
		* 저장 ( 유효성 검사 및 아이디 중복 검사 )
		*/
		$("#saveForm").submit(function(){

			// 등록 유효성검사
			if("${isInsert}" == 'true'){
				if($("#division").val()== ""){
					alert("구분을 입력해 주세요.");
					$("#division").focus();
					return false;
				}
				if($("#name").val()== ""){
					alert("이름을 입력해 주세요.");
					$("#name").focus();
					return false;
				}
				if($("#account").val()== ""){
					alert("아이디를 입력해 주세요.");
					$("#account").focus();
					return false;
				}
				if($("#account").val().length<4){
					alert("아이디는 4자 이상 입력해 주세요.");
					$("#account").focus();
					return false;
				}
				if($("#password").val()== ""){
					alert("비밀번호를 입력해 주세요.");
					$("#password").focus();
					return false;
				}
				if($("#password").val().length<4){
					alert("비밀번호는 4자 이상 입력해 주세요.");
					$("#password").focus();
					return false;
				}
				if($("#phone").val()== ""){
					alert("연락처를 입력해 주세요.");
					$("#phone").focus();
					return false;
				}

			// 정규식 검사
				if(!engPattern.test($("#account").val())){
					alert("아이디는 영문 또는 숫자로 입력해 주세요.");
					$("#account").focus();
					return false;
				}
			// 정규식 검사
				if(!phonePattern.test($("#phone").val())){
					alert(" 전화번호를 다시 입력해 주세요. \n\n ex) 010-1234-5678");
					$("#phone").focus();
					return false;
				}

			// 중복검사 여부 검사
				if($("#checkAccount").val()=='false'){
					alert("아이디 중복검사를 실행하여 주세요.");
					$("input[name=account]").focus();
					return false;
				}

			// 수정 유효성 검사
			}else{
				$("#checkAccount").val('true');
				if($("#division").val()== ""){
					alert("구분을 입력해 주세요.");
					$("#division").focus();
					return false;
				}
				if($("#phone").val()== ""){
					alert("연락처를 입력해 주세요.");
					$("#phone").focus();
					return false;
				}
			}

			var conf = confirm("저장하시겠습니까?");
			return conf;

		});

	// 초기 목록으로
		$("#btnToList").click(function(){
			document.location.assign("${ctx}/admin");
		});

	// 단건 삭제
		$("#btnDelete").click(function(event){
			var conf = confirm("정말 삭제 하시겠습니까?");
			return conf;
// 			if(!confirm("정말 삭제 하시겠습니까?")){
// 				if(event.preventDefault){ event.preventDefault();}else{event.returnValue = false;}
// 				return;
// 			}
			$("#saveForm").find("[name=_method]").val("delete");
			$("#saveForm").attr("action","${ctx}/admin/delete/${model.id}");
			$("#saveForm").submit();
		});

	// 중복 검사
		$("#duplicationCheck").click(function(event){
			$.get("${ctx}/admin/duplicationCheck/"+$('#account').val()+"?ajax=true",function(data,status){
			   if(data==true){
				   $("#checkAccount").val(true);
				   alert("사용 가능한 ID 입니다.");
			   }else{
				   $("#checkAccount").val(false);
				   alert("사용 불가능한 ID 입니다. 다시 입력해 주세요.");
			   }
		   });
		});


	});
</script>
</c:set>
</body>
