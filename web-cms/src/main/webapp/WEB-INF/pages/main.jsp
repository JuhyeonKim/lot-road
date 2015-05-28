<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/pages/include/taglib.jsp" %>
<html>
<head>
	<title>DashBoard</title>
</head>
<body>

<section class="content-header">
	<h1>
		Dashboard
		<small>Control panel</small>
	</h1>
	<ol class="breadcrumb">
		<li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
		<li class="active">Dashboard</li>
	</ol>
</section>
<!-- Main content -->
<section class="content">

	<!-- Small boxes (Stat box) -->
	<div class="row">
		<div class="col-lg-3 col-xs-6">
			<!-- small box -->
			<div class="small-box bg-aqua">
				<div class="inner">
					<h3>
						150
					</h3>
					<p>
						내가 다 쓴 장 수
					</p>
				</div>
				<div class="icon">
					<i class="ion ion-bag"></i>
				</div>
				<a href="#" class="small-box-footer">
					More info <i class="fa fa-arrow-circle-right"></i>
				</a>
			</div>
		</div><!-- ./col -->
		<div class="col-lg-3 col-xs-6">
			<!-- small box -->
			<div class="small-box bg-green">
				<div class="inner">
					<h3>
						53<sup style="font-size: 20px">%</sup>
					</h3>
					<p>
						내가 다 쓴 책의 %
					</p>
				</div>
				<div class="icon">
					<i class="ion ion-stats-bars"></i>
				</div>
				<a href="#" class="small-box-footer">
					More info <i class="fa fa-arrow-circle-right"></i>
				</a>
			</div>
		</div><!-- ./col -->
		<div class="col-lg-3 col-xs-6">
			<!-- small box -->
			<div class="small-box bg-yellow">
				<div class="inner">
					<h3>
						44
					</h3>
					<p>
						팀 내 순위
					</p>
				</div>
				<div class="icon">
					<i class="ion ion-person-add"></i>
				</div>
				<a href="#" class="small-box-footer">
					More info <i class="fa fa-arrow-circle-right"></i>
				</a>
			</div>
		</div><!-- ./col -->
		<div class="col-lg-3 col-xs-6">
			<!-- small box -->
			<div class="small-box bg-red">
				<div class="inner">
					<h3>
						65
					</h3>
					<p>
						전체 순위
					</p>
				</div>
				<div class="icon">
					<i class="ion ion-pie-graph"></i>
				</div>
				<a href="#" class="small-box-footer">
					More info <i class="fa fa-arrow-circle-right"></i>
				</a>
			</div>
		</div><!-- ./col -->
	</div><!-- /.row -->

	<!-- Main row -->
	<div class="row">
		<!-- Left col -->
		<section class="col-lg-7 connectedSortable">


			<!-- Custom tabs (Charts with tabs)-->
			<div class="nav-tabs-custom">
				<!-- Tabs within a box -->
				<ul class="nav nav-tabs pull-right">
					<li class="active"><a href="#bar-chart" data-toggle="tab">Area</a></li>
					<li class="pull-left header"><i class="fa fa-inbox"></i> 전체 1~10 순위</li>
				</ul>
				<div class="tab-content no-padding chart-responsive">
					<!-- Morris chart - Sales -->
					<div class="chart" id="bar-chart" style="height: 300px;"></div>
				</div>
			</div><!-- /.nav-tabs-custom -->

			<!-- TO DO List -->
			<div class="box box-primary">
				<div class="box-header">
					<i class="ion ion-clipboard"></i>
					<h3 class="box-title">다음에 써야하는 장</h3>
					<div class="box-tools pull-right">
						<ul class="pagination pagination-sm inline">
							<li><a href="#">&laquo;</a></li>
							<li><a href="#">1</a></li>
							<li><a href="#">2</a></li>
							<li><a href="#">3</a></li>
							<li><a href="#">&raquo;</a></li>
						</ul>
					</div>
				</div><!-- /.box-header -->
				<div class="box-body">
					<ul class="todo-list">
						<li>
							<!-- drag handle -->
                                            <span class="handle">
                                                <i class="fa fa-ellipsis-v"></i>
                                                <i class="fa fa-ellipsis-v"></i>
                                            </span>
							<!-- checkbox -->
							<input type="checkbox" value="" name=""/>
							<!-- todo text -->
							<span class="text">Design a nice theme</span>
							<!-- Emphasis label -->
							<small class="label label-danger"><i class="fa fa-clock-o"></i> 2 mins</small>
							<!-- General tools such as edit or delete-->
							<div class="tools">
								<i class="fa fa-edit"></i>
								<i class="fa fa-trash-o"></i>
							</div>
						</li>
						<li>
                                            <span class="handle">
                                                <i class="fa fa-ellipsis-v"></i>
                                                <i class="fa fa-ellipsis-v"></i>
                                            </span>
							<input type="checkbox" value="" name=""/>
							<span class="text">Make the theme responsive</span>
							<small class="label label-info"><i class="fa fa-clock-o"></i> 4 hours</small>
							<div class="tools">
								<i class="fa fa-edit"></i>
								<i class="fa fa-trash-o"></i>
							</div>
						</li>
						<li>
                                            <span class="handle">
                                                <i class="fa fa-ellipsis-v"></i>
                                                <i class="fa fa-ellipsis-v"></i>
                                            </span>
							<input type="checkbox" value="" name=""/>
							<span class="text">Let theme shine like a star</span>
							<small class="label label-warning"><i class="fa fa-clock-o"></i> 1 day</small>
							<div class="tools">
								<i class="fa fa-edit"></i>
								<i class="fa fa-trash-o"></i>
							</div>
						</li>
						<li>
                                            <span class="handle">
                                                <i class="fa fa-ellipsis-v"></i>
                                                <i class="fa fa-ellipsis-v"></i>
                                            </span>
							<input type="checkbox" value="" name=""/>
							<span class="text">Let theme shine like a star</span>
							<small class="label label-success"><i class="fa fa-clock-o"></i> 3 days</small>
							<div class="tools">
								<i class="fa fa-edit"></i>
								<i class="fa fa-trash-o"></i>
							</div>
						</li>
						<li>
                                            <span class="handle">
                                                <i class="fa fa-ellipsis-v"></i>
                                                <i class="fa fa-ellipsis-v"></i>
                                            </span>
							<input type="checkbox" value="" name=""/>
							<span class="text">Check your messages and notifications</span>
							<small class="label label-primary"><i class="fa fa-clock-o"></i> 1 week</small>
							<div class="tools">
								<i class="fa fa-edit"></i>
								<i class="fa fa-trash-o"></i>
							</div>
						</li>
						<li>
                                            <span class="handle">
                                                <i class="fa fa-ellipsis-v"></i>
                                                <i class="fa fa-ellipsis-v"></i>
                                            </span>
							<input type="checkbox" value="" name=""/>
							<span class="text">Let theme shine like a star</span>
							<small class="label label-default"><i class="fa fa-clock-o"></i> 1 month</small>
							<div class="tools">
								<i class="fa fa-edit"></i>
								<i class="fa fa-trash-o"></i>
							</div>
						</li>
					</ul>
				</div><!-- /.box-body -->
				<div class="box-footer clearfix no-border">
					<button class="btn btn-default pull-right"><i class="fa fa-plus"></i> Add item</button>
				</div>
			</div><!-- /.box -->

		</section><!-- /.Left col -->
		<!-- right col (We are only adding the ID to make the widgets sortable)-->
		<section class="col-lg-5 connectedSortable">

			<!-- Calendar -->
			<div class="box box-solid bg-green-gradient">
				<div class="box-header">
					<i class="fa fa-calendar"></i>
					<h3 class="box-title">Calendar<!--(일 별 다 쓴내역 표시)--></h3>
					<!-- tools box -->
					<div class="pull-right box-tools">
						<!-- button with a dropdown -->
						<div class="btn-group">
							<button class="btn btn-success btn-sm dropdown-toggle" data-toggle="dropdown"><i class="fa fa-bars"></i></button>
							<ul class="dropdown-menu pull-right" role="menu">
								<li><a href="#">Add new event</a></li>
								<li><a href="#">Clear events</a></li>
								<li class="divider"></li>
								<li><a href="#">View calendar</a></li>
							</ul>
						</div>
						<button class="btn btn-success btn-sm" data-widget="collapse"><i class="fa fa-minus"></i></button>
						<button class="btn btn-success btn-sm" data-widget="remove"><i class="fa fa-times"></i></button>
					</div><!-- /. tools -->
				</div><!-- /.box-header -->
				<div class="box-body no-padding">
					<!--The calendar -->
					<div id="calendar" style="width: 100%"></div>
				</div><!-- /.box-body -->
				<div class="box-footer text-black">
					<div class="row">
						<div class="col-sm-6">
							<!-- Progress bars -->
							<div class="clearfix">
								<span class="pull-left">Task #1</span>
								<small class="pull-right">90%</small>
							</div>
							<div class="progress xs">
								<div class="progress-bar progress-bar-green" style="width: 90%;"></div>
							</div>

							<div class="clearfix">
								<span class="pull-left">Task #2</span>
								<small class="pull-right">70%</small>
							</div>
							<div class="progress xs">
								<div class="progress-bar progress-bar-green" style="width: 70%;"></div>
							</div>
						</div><!-- /.col -->
						<div class="col-sm-6">
							<div class="clearfix">
								<span class="pull-left">Task #3</span>
								<small class="pull-right">60%</small>
							</div>
							<div class="progress xs">
								<div class="progress-bar progress-bar-green" style="width: 60%;"></div>
							</div>

							<div class="clearfix">
								<span class="pull-left">Task #4</span>
								<small class="pull-right">40%</small>
							</div>
							<div class="progress xs">
								<div class="progress-bar progress-bar-green" style="width: 40%;"></div>
							</div>
						</div><!-- /.col -->
					</div><!-- /.row -->
				</div>
			</div><!-- /.box -->

			<!-- Chat box -->
			<div class="box box-success">
				<div class="box-header">
					<i class="fa fa-comments-o"></i>
					<h3 class="box-title">Chat(필사 각오 예정)</h3>
					<div class="box-tools pull-right" data-toggle="tooltip" title="Status">
						<div class="btn-group" data-toggle="btn-toggle" >
							<button type="button" class="btn btn-default btn-sm active"><i class="fa fa-square text-green"></i></button>
							<button type="button" class="btn btn-default btn-sm"><i class="fa fa-square text-red"></i></button>
						</div>
					</div>
				</div>
				<div class="box-body chat" id="chat-box">
					<!-- chat item -->
					<div class="item">
						<img src="${ctx}/assets/images/avatar.png" alt="user image" class="online"/>
						<p class="message">
							<a href="#" class="name">
								<small class="text-muted pull-right"><i class="fa fa-clock-o"></i> 2:15</small>
								Mike Doe
							</a>
							I would like to meet you to discuss the latest news about
							the arrival of the new theme. They say it is going to be one the
							best themes on the market
						</p>
						<div class="attachment">
							<h4>Attachments:</h4>
							<p class="filename">
								Theme-thumbnail-image.jpg
							</p>
							<div class="pull-right">
								<button class="btn btn-primary btn-sm btn-flat">Open</button>
							</div>
						</div><!-- /.attachment -->
					</div><!-- /.item -->
					<!-- chat item -->
					<div class="item">
						<img src="${ctx}/assets/images/avatar2.png" alt="user image" class="offline"/>
						<p class="message">
							<a href="#" class="name">
								<small class="text-muted pull-right"><i class="fa fa-clock-o"></i> 5:15</small>
								Jane Doe
							</a>
							I would like to meet you to discuss the latest news about
							the arrival of the new theme. They say it is going to be one the
							best themes on the market
						</p>
					</div><!-- /.item -->
					<!-- chat item -->
					<div class="item">
						<img src="${ctx}/assets/images/avatar3.png" alt="user image" class="offline"/>
						<p class="message">
							<a href="#" class="name">
								<small class="text-muted pull-right"><i class="fa fa-clock-o"></i> 5:30</small>
								Susan Doe
							</a>
							I would like to meet you to discuss the latest news about
							the arrival of the new theme. They say it is going to be one the
							best themes on the market
						</p>
					</div><!-- /.item -->
				</div><!-- /.chat -->
				<div class="box-footer">
					<div class="input-group">
						<input class="form-control" placeholder="Type message..."/>
						<div class="input-group-btn">
							<button class="btn btn-success"><i class="fa fa-plus"></i></button>
						</div>
					</div>
				</div>
			</div><!-- /.box (chat box) -->

		</section><!-- right col -->
	</div><!-- /.row (main row) -->

</section><!-- /.content -->
<c:set var="scripts" scope="request">
	<!-- page script -->
	<script type="text/javascript">
		$(document).ready(function(){
			//BAR CHART
			var bar = new Morris.Bar({
				element: 'bar-chart',
				resize: true,
				data: [
					{y: '김OO', a: 100, b: 90},
					{y: '강OO', a: 75, b: 65},
					{y: '구OO', a: 50, b: 40},
					{y: '이OO', a: 45, b: 40},
					{y: '최OO', a: 43, b: 44},
					{y: '진OO', a: 40, b: 41},
					{y: '김XX', a: 21, b: 30}
				],
				barColors: ['#7a92a3', '#cb4b4b'],
				xkey: 'y',
				ykeys: ['a', 'b'],
				labels: ['신약', '구약'],
				hideHover: 'auto'
			});
		});
	</script>
</c:set>

</body>
</html>

