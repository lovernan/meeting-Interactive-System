<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>INSPINIA | Login</title>

    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="font-awesome/css/font-awesome.css" rel="stylesheet">

    <link href="css/animate.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
    <script>
    	function changeCaptcha() {
    		var parentElement = document.getElementById("captcha-parent");
    		var oldImg = document.getElementById("captcha-img");
    		var newImg = document.createElement("input");
    		newImg.id = "captcha-img";
    		newImg.src = "get-captcha-img";
    		newImg.type = "image";
    		parentElement.replaceChild(newImg, oldImg);
    	}
    	function login() {
    		var username = document.getElementById("username").value;
    		var password = document.getElementById("password").value;
    		var captcha = document.getElementById("captcha").value;
    		$.post("admin-login",  {
    			"username": username,
    			"password": password,
    			"captcha" : captcha
    		},function(data) {
    			if (data == "Success!") {
    				alert(data);
    				window.location.href = "management.jsp";
    			} else {
    				alert(data);
    			}
    		});
    	}
    </script>

</head>
<% 
// 判断用户是否登录
if (session.getAttribute("username") != null) {		// 用户已登录
	
	// 跳转到管理主页面
	response.sendRedirect("management.jsp");
}
%>
<body class="gray-bg">

    <div class="middle-box text-center loginscreen animated fadeInDown">
        <div>
            <div>

                <h1 class="logo-name">MIS</h1>

            </div>
            <h3>系统管理登录</h3>
            <p>
                	移动会议实时互动系统管理页面
            </p>
            <p>登录</p>
            <form class="m-t" role="form" method="post" action="admin-login">
                <div class="form-group">
                    <input id="username" type="text" class="form-control" placeholder="用户名" required="">
                </div>
                <div class="form-group">
                    <input id="password" type="password" class="form-control" placeholder="密码" required="">
                </div>
                <div class="form-group" id="captcha-parent">
                	<input type="image" src="get-captcha-img" id="captcha-img">
                    <button type="button" class="btn btn-outline btn-link" onclick="changeCaptcha()">换一张</button>
                	<input id="captcha" type="text" class="form-control" placeholder="验证码" required="">
                </div>
                <button type="button" onclick="login()" class="btn btn-primary block full-width m-b">登录</button>
            </form>
            <p class="m-t"> <small>Inspinia we app framework base on Bootstrap 3 &copy; 2018</small> </p>
        </div>
    </div>

    <!-- Mainly scripts -->
    <script src="js/jquery-2.1.1.js"></script>
    <script src="http://libs.baidu.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>

</body>

</html>
