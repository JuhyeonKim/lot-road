<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/pages/include/taglib.jsp" %>
<!DOCTYPE html>
<html class="bg-black">
<head>
	<meta charset="UTF-8">
	<title>AdminLTE | Log in</title>
	<meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
	<link href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
	<!-- Theme style -->
	<![endif]-->
</head>
<body class="bg-black">
	<div class="col-md-12">
		<button type="button" id="roadBtn" class="btn btn-sm btn-default" data-toggle="modal" data-target="#roadModal">주소 검색</button>
	</div>

	<div class="modal fade" id="roadModal">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title">도로명 주소 우편번호 찾기</h4>
				</div>
				<div class="modal-body">
					<fieldset>
						<form action="/postcode/listRoad" id="submitForm">
							<div class="form-group">
								<label for="sidoInput" >시도</label>
								<select id="sidoInput" name="q.sidoName">
									<option value="">선택</option>
								</select>
								<label for="sigunguInput" >시군구</label>
								<select id="sigunguInput" name="q.sigunguName">
									<option value="">선택</option>
								</select>
								<label for="txt">도로명 또는 동이름</label>
								<input id="txt" type="text" name="q.keyword" />
								<button type="submit" id="submitRoadBtn" class="btn btn-sm btn-primary" >도로명주소로 검색</button>
								<button type="button" id="submitLotBtn" class="btn btn-sm btn-primary" >지번주소로 검색</button>
							</div>

						</form>
					</fieldset>
					<table class="table table-responsive table" id="listTable">
						<colgroup>
							<col width="10%"/>
							<col width="15%"/>
							<col width="15%"/>
							<col width="30%"/>
							<col width="30%"/>
						</colgroup>
						<thead>
							<tr>
								<th>번호</th>
								<th>우편번호</th>
								<th>새우편번호</th>
								<th>도로명</th>
								<th>지번주소</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td colspan="5" class="text-center">검색하세요.</td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary">Save changes</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->


	<script type="text/javascript" src="/assets/js/jquery-1.10.2.min.js" ></script>
	<script type="text/javascript" src="/assets/js/bootstrap/js/bootstrap.min.js" ></script>
	<script type="text/javascript" src="/assets/js/bootstrap/js/bootstrap-growl.min.js" ></script>
	<script type="text/javascript">
		$(document).ready(function(){

			$("#submitRoadBtn").bind("click", function () {
				$("#submitForm").attr("action", "/postcode/listRoad");
				$("#submitForm").submit();

			});

			$("#submitLotBtn").bind("click", function () {
				$("#submitForm").attr("action", "/postcode/listLot");
				$("#submitForm").submit();

			});


			$.ajax({
				url:"/postcode/listSido"
				,dataType:"json"
				,type:"get"
			}).done(function(data){
				if(data.length > 0){

					for(var i=0;i<data.length;i++) {
						$("#sidoInput").append($("<option value='"+data[i]+"'>"+data[i]+"</option>"));

					}
				}

				console.log(data);
			}).fail(function(data){
				console.log(data)
			});

			$("#sidoInput").bind("change", function () {
				$.ajax({
					url:"/postcode/listSigungu"
					,dataType:"json"
					,type:"get"
					,data:{"q.sidoName":$(this).val()}
				}).done(function(data){

					$("#sigunguInput").html("");
					$("#sigunguInput").append($("<option value=''>선택</option>"));

					if(data.length > 0){

						for(var i=0;i<data.length;i++) {
							$("#sigunguInput").append($("<option value='"+data[i].sigunguName+"'>"+data[i].sigunguName+"</option>"));

						}
					}

					console.log(data);
				}).fail(function(data){
					console.log(data)
				});
			});

			$("#submitForm").bind("submit", function () {
				$.ajax({
					url:$(this).attr("action")
					,dataType:"json"
					,type:"get"
					,data:$(this).serialize()
				}).done(function(data){
					if(data.length > 0) {
						$("#listTable tbody").html("");
						for(var i=0;i<data.length;i++) {
							var tr = $("<tr />");
							var td1 = $("<td>"+(i+1)+"</td>");
							var td2 = $("<td>"+data[i].postCode+"</td>");
							var td3 = $("<td>"+data[i].baseAreaNumber+"</td>");
							var td4 = $("<td>"+data[i].address+"</td>");
							var td5 = $("<td>"+data[i].lotAddress+"</td>");
							tr.append(td1);
							tr.append(td2);
							tr.append(td3);
							tr.append(td4);
							tr.append(td5);
							tr.appendTo("#listTable tbody")
						}
					}else{
						$("#listTable tbody").html("<tr><td colspan='5'>검색 결과가 없습니다.</td></tr>");
					}
					console.log(data);
				}).fail(function(data){
					console.log(data);
				});

				return false;
			});
		});
	</script>
</body>
</html>