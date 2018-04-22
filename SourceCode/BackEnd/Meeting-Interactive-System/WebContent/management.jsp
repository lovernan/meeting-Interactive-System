<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <title>管理系统</title>

    <style media="screen">
        .radio-button {
            display: inline-block;
            padding: 10px;
            cursor: pointer;
        }
        .gua-hide {
            display: none;
        }
    </style>

    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="font-awesome/css/font-awesome.css" rel="stylesheet">

    <!-- Toastr style -->
    <link href="css/plugins/toastr/toastr.min.css" rel="stylesheet">

    <!-- Gritter -->
    <link href="js/plugins/gritter/jquery.gritter.css" rel="stylesheet">

    <link href="css/animate.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
    <script src="js/jquery-2.1.1.js"></script>
    <script>
    	$(document).ready(function(){
   			$.get("admin-data?dataName=socketCount",function(data){
    			$("#data-socket-count").html(data);
    		});
   			$.get("admin-data?dataName=userCount",function(data){
  		      	$("#data-user-count").html(data);
  			});
   			$.get("admin-data?dataName=memory",function(data){
  		      	$("#data-memory").html(data);
  			});
   			$.get("admin-data?dataName=meetingCount",function(data){
  		      	$("#data-meeting-count").html(data);
  			});
   			$.get("admin-data?dataName=meetingNotStart",function(data){
  		      	$("#data-meeting-not-start").html(data);
  			});
   			$.get("admin-data?dataName=meetingInProcess",function(data){
  		      	$("#data-meeting-in-process").html(data);
  			});
   			$.get("admin-data?dataName=meetingHasFinished",function(data){
  		      	$("#data-meeting-has-finished").html(data);
  			});
   			$.get("admin-data?dataName=blacklist" ,function(data){
   				var list = document.getElementById("blacklist");
   				var json = JSON.parse(data);
   				var length = json.length;
   				var idList = json.userId;
   				var timeList = json.startTime;
   				for (var i = 0; i < length; i++) {
   					// <p class="m-b-xs"><strong>zhang</strong></p>
   					var item = document.createElement("p");
   					item.class = "m-b-xs";
   					var nest = document.createElement("strong");
   					nest.innerHTML = idList[i] + " - " + timeList[i];
   					item.appendChild(nest);
   					list.appendChild(item);
   				}
   			});
		});
    	
    	function doTest() {
    		document.getElementById("time-test").innerHTML = "正在测试..."
    		var startTime;
    		var endTime;
    		var socket = new WebSocket("wss://meeting.maphical.cn/Meeting-Interactive-System/endpoint");
    		socket.onopen = function(evt) {
        		startTime = (new Date()).getTime();
        		socket.send("{\"operation\":\"echo\"}");
    		}
    		socket.onerror = function(evt) {
    			document.getElementById("time-test").innerHTML = "Websocket错误" + evt.data;
    		}
    		socket.onmessage = function(evt) {
    			endTime = (new Date()).getTime();
    			var timeSpan = endTime - startTime;
    			document.getElementById("time-test").innerHTML = timeSpan + "ms";
    		}
    	}
    	
    	function doRegister() {
    		var username = document.getElementById("register-username").value;
    		var password = document.getElementById("register-password").value;
    		var mail = document.getElementById("register-mail").value;
    		$.post("admin-register", {
    			"username":username,
    			"password":password,
    			"mail":mail
    		}, function(data){
    			alert(data);
    		});
    	}
    </script>
</head>
<%
if (session.getAttribute("username") == null) {		// 若用户未登录
	
	// 重定向到登录页面
	response.sendRedirect("login.jsp");
}
%>
<%
String username = (String)session.getAttribute("username");
%>
<body>
    <div id="wrapper">
        <nav class="navbar-default navbar-static-side" role="navigation">
            <div class="sidebar-collapse">
                <ul class="nav metismenu" id="side-menu">
                    <li class="nav-header">
                        <div class="dropdown profile-element">
                            <span>
                                <img alt="image" class="img-circle" src="img/profile_small.jpg" />
                            </span>
                            <a data-toggle="dropdown" class="dropdown-toggle" href="index.html#">
                                <span class="clear">
                                    <span class="block m-t-xs"> <strong class="font-bold"><%= username %></strong></span>
                                    <!-- <span class="text-muted text-xs block">xxx<b class="caret"></b></span> -->
                                </span>
                            </a>
                        </div>
                    </li>
                    <li class="active">
                        <a><i class="fa fa-th-large"></i><span class="nav-label radio-button gua-tab" data-page='todo-list'>系统状态</span></a>
                    </li>
                    <li class="">
                        <a><i class="fa fa-diamond"></i><span class="nav-label radio-button gua-tab" data-page='todo-edit'>会议状态</span></a>
                    </li>
                    <li class="">
                        <a><i class="fa fa-bar-chart-o"></i><span class="nav-label radio-button gua-tab" data-page='todo-check'>系统设置参数</span></a>
                    </li>
                </ul>

            </div>
        </nav>

        <div id="page-wrapper" class="gray-bg dashbard-1">
            <div class="row border-bottom">
                <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
                    <ul class="nav navbar-top-links navbar-right">
                        <li>
                            <span class="m-r-sm text-muted welcome-message">系统管理系统</span>
                        </li>
                        <li>
                            <a href="admin-logout">
                                <i class="fa fa-sign-out"></i> 注销
                            </a>
                        </li>
                    </ul>
                </nav>
            </div>
            <div class="row gua-page todo-list border-bottom white-bg dashboard-header">
                <div class="col-sm-3">
                    <h2>系统状态</h2>
                    <small>......此处省略六个字</small>
                    <ul class="list-group clear-list m-t">
                        <li class="list-group-item fist-item">
                            <span class="pull-right" id="data-socket-count">
                            </span>
                            <span class="label label-success">1</span>   WebSocket连接数
                        </li>
                        <li class="list-group-item">
                            <span class="pull-right" id="data-user-count">
                            </span>
                            <span class="label label-info">2</span>   有效用户连接数
                        </li>
                        <li class="list-group-item">
                            <span class="pull-right" id="data-memory">
                            </span>
                            <span class="label label-primary">3</span>   内存使用率
                        </li>
                        <li class="list-group-item">
                            <span class="pull-right" id="time-test">
                            </span>
                            <span class="label label-default">4</span>
                            <button type="button" class="btn btn-outline btn-link" onclick="doTest()">服务器链接时间</button>
                   		</li>
                    </ul>
                </div>
            </div>
            <div class="row gua-page todo-edit border-bottom white-bg dashboard-header">
                <div class="col-sm-3">
                    <h2>会议状态</h2>
                    <small>......此处省略六个字</small>
                    <ul class="list-group clear-list m-t">
                        <li class="list-group-item fist-item">
                            <span class="pull-right" id="data-meeting-count">
                            </span>
                            <span class="label label-success">1</span> 当前会议注册个数
                        </li>
                        <li class="list-group-item">
                            <span class="pull-right" id="data-meeting-not-start">
                            </span>
                            <span class="label label-info">2</span> 未开始会议个数
                        </li>
                        <li class="list-group-item">
                            <span class="pull-right" id="data-meeting-in-process">
                            </span>
                            <span class="label label-primary">3</span> 进行的会议个数
                        </li>
                        <li class="list-group-item">
                            <span class="pull-right" id="data-meeting-has-finished">
                            </span>
                            <span class="label label-default">4</span> 结束的会议个数
                        </li>
                    </ul>
                </div>
            </div>
            <div class="row gua-page todo-check border-bottom white-bg dashboard-header">
                <div class="col-lg-6">
                    <div class="ibox-content">
                        <h2>系统设置参数</h2>
                        <small>This is example of task list</small>
                        <ul class="todo-list m-t">
                            <li>
                                <input type="checkbox" value="" name="" class="i-checks"/>
                                <span class="m-l-xs">是否允许新用户注册</span>
                            </li>
                            <li>
                                <input type="checkbox" value="" name="" class="i-checks" checked/>
                                <span class="m-l-xs">是否允许用户登录</span>
                            </li>
                        </ul>
                        <br/>
                         <button type="button" class="btn btn-block btn-outline btn-primary">确认修改</button>
                    </div>
                </div>
                <div class="col-lg-6">
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <h5>禁止登录的用户名单</h5>
                            <div class="ibox-tools">
                                <a class="collapse-link">
                                    <i class="fa fa-chevron-up"></i>
                                </a>
                            </div>
                        </div>

                        <div class="ibox-content inspinia-timeline">

                            <div class="timeline-item">
                                <div class="row">
                                    <div class="col-xs-3 date">
                                        1
                                    </div>
                                    <div class="col-xs-7 content no-top-border" id="blacklist">
                                        <p class="m-b-xs"><strong>zhang</strong></p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="input-group"><input type="text" class="form-control" placeholder="向禁止登录名单中加入新用户"> <span class="input-group-btn"> <button type="button" class="btn btn-primary">Go!
                        </button> </span>
                    </div>
                </div>
                <div class="col-sm-3">
                    <h2>管理员注册</h2>
                    	<ul>
                    	<li><div>用户名</div><input type="text" name="username" id="register-username" /></li>
                    	<li><div>密码</div><input type="text" name="password" id="register-password" /></li>
                    	<li><div>密码</div><input type="text" name="amil" id="register-mail" /></li>
                    	<li><input type="button" value="注册" onclick="doRegister()"></li>
                    	</ul>
                </div>
            </div>
            </div>
           
    </div>

    <script src='gua.js'></script>
    <!-- Mainly scripts -->
    <script src="js/bootstrap.min.js"></script>
    <script src="js/plugins/metisMenu/jquery.metisMenu.js"></script>
    <script src="js/plugins/slimscroll/jquery.slimscroll.min.js"></script>

    <!-- Flot -->
    <script src="js/plugins/flot/jquery.flot.js"></script>
    <script src="js/plugins/flot/jquery.flot.tooltip.min.js"></script>
    <script src="js/plugins/flot/jquery.flot.spline.js"></script>
    <script src="js/plugins/flot/jquery.flot.resize.js"></script>
    <script src="js/plugins/flot/jquery.flot.pie.js"></script>

    <!-- Peity -->
    <script src="js/plugins/peity/jquery.peity.min.js"></script>
    <script src="js/demo/peity-demo.js"></script>

    <!-- Custom and plugin javascript -->
    <script src="js/inspinia.js"></script>
    <script src="js/plugins/pace/pace.min.js"></script>

    <!-- jQuery UI -->
    <script src="js/plugins/jquery-ui/jquery-ui.min.js"></script>

    <!-- GITTER -->
    <script src="js/plugins/gritter/jquery.gritter.min.js"></script>

    <!-- Sparkline -->
    <script src="js/plugins/sparkline/jquery.sparkline.min.js"></script>

    <!-- Sparkline demo data  -->
    <script src="js/demo/sparkline-demo.js"></script>

    <!-- ChartJS-->
    <script src="js/plugins/chartJs/Chart.min.js"></script>

    <!-- Toastr -->
    <script src="js/plugins/toastr/toastr.min.js"></script>

    <script>
        var buttons = document.querySelectorAll('.radio-button')

        for (var i = 0; i < buttons.length; i++) {
            var button = buttons[i]
            button.addEventListener('click', function(event){
                var self = event.target
                clearActive()
                self.parentNode.parentNode.classList.add('active')
            })
        }

        var clearActive = function() {
            var active = document.querySelector('.active')
            if (active != null) {
                active.classList.remove("active")
            }
        }

        //SPA部分
        var bindEvents = function() {
            bindAll('.gua-tab', 'click', function(event) {
                var button = event.target
                var page = button.dataset.page
                log(page)
                showPage(page)
                pushState(page)
            })

            window.addEventListener('popstate', function(e) {
                //window函数参数中的e是什么鬼。。。
                var state = e.state
                var pageName = state.page
                //查的参数和class不一样
                showPage(pageName)
            })
        }

        //写一个可以删除一个指定class全部标签的函数
        //e被覆盖掉了,e有问题用find,用es改函数加上All，部分用e部分用es
        //for中可以用let
        var pushState = function(className) {
            var pageName = className.split('-')[1]
            var url = 'management.jsp?page=' + pageName
            var state = {
                page: className,
            }
            history.pushState(state, 'title', url)
            document.title = pageName
        }
        //push与show的矛盾

        var showPage = function(className) {
            var pages = es('.gua-page')
            for (var i = 0; i < pages.length; i++) {
                let page = pages[i]
                page.classList.add('gua-hide')
            }
            //上面的可以写成一个函数
            var selector = '.' + className
            var todonewDiv = e(selector)
            todonewDiv.classList.remove('gua-hide')
            if (className == 'todo-list') {
                //相当于刷新一遍
                // showtodoList()
            }
        }

        var initApp = function() {
            //在没有该函数的时候，默认的search是什么
            var query = location.search
            var [k, v] = query.slice(1).split('=')
            var page = 'list'
            var validPages = ['list', 'new']
            if (k == 'page') {
                if (validPages.includes(v)) {
                    page = v
                }
            }
            var pageName = 'todo-' + page
            showPage(pageName)
        }
        var __main = function() {
            bindEvents()
            // showtodoList()
            initApp()
        }

        __main()
    </script>
</body>
</html>
