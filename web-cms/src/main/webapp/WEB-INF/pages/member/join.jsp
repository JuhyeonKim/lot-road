<%--
  Created by IntelliJ IDEA.
  User: Kimjuhyeon
  Date: 15. 1. 19.
  Time: 오후 4:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/pages/include/taglib.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html class="bg-black">
<head>
  <meta charset="UTF-8">
  <title>AdminLTE | Registration Page</title>
  <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
  <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
  <link href="//cdnjs.cloudflare.com/ajax/libs/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
  <link rel="stylesheet" type="text/css" media="screen" href="${ctx}/assets/css/font/webfont.css" />
  <!-- Theme style -->
  <link href="${ctx}/assets/css/AdminLTE.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="${ctx}/assets/css/custom-theme/jquery-ui-1.10.3.custom.css">

  <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
  <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
  <![endif]-->
</head>
<body class="bg-black">

<div class="form-box" id="login-box">
  <div class="header">회원가입</div>
  <form:form commandName="user" action="${ctx}/member/join" method="post">
    <div class="body bg-gray">
      <div class="form-group">
        <form:input path="name" class="form-control" placeholder="이름"/>
      </div>
      <div class="form-group">
        <form:input path="id" class="form-control" placeholder="사용자 ID"/>
      </div>
      <div class="form-group">
        <form:password path="password" class="form-control" placeholder="비밀번호"/>
      </div>
      <div class="form-group">
        <form:password path="confirmPassword" class="form-control" placeholder="비밀번호 확인"/>
      </div>
      <div class="form-group">
        <form:input path="birthday" class="form-control" placeholder="생년월일" readonly="readonly" />
      </div>
      <div class="form-group">
        <form:input path="tel" class="form-control" placeholder="연락처" />
      </div>
      <div class="form-group">
          <form:select path="group" class="form-control">
              <form:option value="" >소속 팀 선택</form:option>
              <c:forEach items="${groupCodeList}" var="groupCode">
                  <form:option value="${groupCode.code}">${groupCode.name}</form:option>
              </c:forEach>
          </form:select>
      </div>
      <div class="form-group">
        <form:input path="church" class="form-control" placeholder="소속 교회" />
      </div>
      <div class="form-group">
        <form:textarea path="compaction" class="form-control" placeholder="성경필사 다짐" ></form:textarea>
      </div>
    </div>
    <div class="footer">

      <button type="submit" class="btn bg-olive btn-block">가입하기</button>

      <a href="${ctx}/login" class="text-center">이미 회원으로 가입되어있다면 로그인하세요.</a>
    </div>
  </form:form>

  <div class="margin text-center">
    <%--<span>Register using social networks</span>--%>
    <%--<br/>--%>
    <%--<button class="btn bg-light-blue btn-circle"><i class="fa fa-facebook"></i></button>--%>
    <%--<button class="btn bg-aqua btn-circle"><i class="fa fa-twitter"></i></button>--%>
    <%--<button class="btn bg-red btn-circle"><i class="fa fa-google-plus"></i></button>--%>

  </div>
</div>

<script src="${ctx}/assets/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${ctx}/assets/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="${ctx}/assets/js/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctx}/assets/js/bootstrap/js/bootstrap-growl.min.js"></script>
<script type="text/javascript" src="${ctx}/assets/js/ext_growlMessage.min.js"></script>

<c:if test="${not empty savedMessage}">
    <script type="text/javascript">
        var growlMessages = "";

        <c:forEach var="message" items="${savedMessage}" varStatus="status">
        growlMessages += "${message}<br />";
        </c:forEach>

        showGrowlMessage(growlMessages);
    </script>
    <c:remove var="savedMessage" scope="session" />
</c:if>
<script type="text/javascript">
    $(document).ready(function(){
        $("#birthday").datepicker({
            dateFormat: 'yy-mm-dd'
            ,defaultDate: new Date(1990, 1 - 1, 1)
        });
    });
</script>

</body>
</html>