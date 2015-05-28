<%--
  Created by IntelliJ IDEA.
  User: Kimjuhyeon
  Date: 15. 1. 20.
  Time: 오후 8:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/pages/include/taglib.jsp" %>
<html>
<head>
    <title>성경 필사</title>
</head>
<body>
<!-- START ACCORDION & CAROUSEL-->
<h2 class="page-header">성경 필사</h2>

<div class="row">
    <div class="col-md-12">
        <div class="box box-solid">
            <div class="box-header">
                <h3 class="box-title">마태복음</h3>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
                <div class="box-group" id="accordion">
                    <!-- we are adding the .panel class so bootstrap.js collapse plugin detects it -->
                    <div class="panel box box-primary">
                        <div class="box-header">
                            <h4 class="box-title">
                                <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
                                    1장
                                </a>
                            </h4>
                        </div>
                        <div id="collapseOne" class="panel-collapse collapse in">
                            <div class="box-body">
                                <c:forEach items="${bible1stList}" var="bible">
                                    <h6 >${bible.chapter}:${bible.verse}</h6>
                                    <h6 >${bible.contents}</h6>
                                </c:forEach>
                                <textarea name="write" class="form-control"></textarea>
                            </div>
                        </div>
                    </div>
                    <div class="panel box box-danger">
                        <div class="box-header">
                            <h4 class="box-title">
                                <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">
                                    2장
                                </a>
                            </h4>
                        </div>
                        <div id="collapseTwo" class="panel-collapse collapse">
                            <div class="box-body ">
                                <c:forEach items="${bible2ndList}" var="bible">
                                    <h6 >${bible.chapter}:${bible.verse}</h6>
                                    <h6 >${bible.contents}</h6>
                                </c:forEach>
                                <textarea name="write" class="form-control"></textarea>
                            </div>
                        </div>
                    </div>
                    <div class="panel box box-success">
                        <div class="box-header">
                            <h4 class="box-title">
                                <a data-toggle="collapse" data-parent="#accordion" href="#collapseThree">
                                    3장
                                </a>
                            </h4>
                        </div>
                        <div id="collapseThree" class="panel-collapse collapse">
                            <div class="box-body">
                                <c:forEach items="${bible3rdList}" var="bible">
                                    <h6 >${bible.chapter}:${bible.verse}</h6>
                                    <h6 >${bible.contents}</h6>
                                </c:forEach>
                                <textarea name="write" class="form-control"></textarea>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- /.box-body -->
        </div>
        <!-- /.box -->
    </div>
    <!-- /.col -->
</div>
<!-- /.row -->
<!-- END ACCORDION & CAROUSEL-->

<c:set var="scripts" scope="request">
    <script type="text/javascript">

        $(document).ready(function(){

            $("body").on("dragstart",function(){
               return false;
            });

            $("body").on("drag",function(){
                return false;
            });

            $("body").on("selectstart",function(){
                return false;
            });
            $("body").on("contextmenu",function(){
                return false;
            });

        });

    </script>
</c:set>

</body>
</html>
