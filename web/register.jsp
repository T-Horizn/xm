<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	//获取项目名
	String path = request.getContextPath();
	//获取tomcat 协议+地址+端口号+ 获取项目名 http://localhost:80/xm/
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	// 把数据存储到页面域对象中,可以通过EL取出结果
	pageContext.setAttribute("basePath", basePath);
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>注册页面</title>
    <link rel="stylesheet" href="css/index.css">
<%--	引入jQuery的代码,只有这样才能使用jQuer有的语法--%>
	<script src="js/jquery.1.11.1.min.js"></script>
</head>
<body>
<div class="sign_background">
    <div class="sign_background_in">
        <div class="sign_background_no1">
            <a href="index.html"><img src="img/logo.jpg" alt=""></a>
        </div>
        <div class="sign_background_no2">注册小米帐号</div>
        <div class="sign_background_no3">
            <span id="msg1" style="color: red">${msg}</span>
            <div class="sign_background_no5">
<%--        action:表单数据提交服务器路径     	图片上传: method一定是post请求, enctype="multipart/form-data" 才能实现文件上传--%>

             	<form id="myForm"  action="${basePath}user" method="post" enctype="multipart/form-data">
<%--             		设置一个隐藏域, 用来表示表单提交过去要做的事情--%>
					<input type="hidden" name="method" value="register">
             		<table style="width: 500px;" border="0" cellspacing="0">
             			<tr>
             				<td width="25%" class="_left">姓名：</td>
             				<td>
								<input id="name" type="text" name="uname">
								<span id="nameMsg"></span>
							</td>
             			</tr>
             			<tr>
             				<td width="25%" class="_left">性别：</td>
             				<td>
             					男<input type="radio" value="1" name="gender" checked>
             				 	女<input type="radio" value="0" name="gender">
							</td>
             			</tr>
             			<tr>
             				<td width="25%" class="_left">电话号码：</td>
             				<td>
								<input id="phone" type="text" name="phone">
								<span id="phoneMsg"></span>
							</td>
             			</tr>
             			<tr>
             				<td width="25%" class="_left">所在地区：</td>
             				<td>
								<input id="area" type="text" name="area">
								<span id="areaMsg"></span>
							</td>
             			</tr>
             			<tr>
             				<td width="25%" class="_left">账号：</td>
             				<td>
								<input id="username" type="text" name="username">
								<span id="usernameMsg"></span>
							</td>
             			</tr>
             			<tr>
             				<td width="25%" class="_left">密码：</td>
             				<td>
								<input id="password" type="password" name="password">
								<span id="passwordMsg"></span>
							</td>
             			</tr>
             			<tr>
             				<td width="25%" class="_left">上传头像：</td>
             				<td><input type="file" name="photo"></td>
             			</tr>
             		</table>
             		<div class="sign_background_no6" id="btn" >立即注册</div>
             	</form>
             	 
            </div>
        </div>
        <div class="sign_background_no7">注册帐号即表示您同意并愿意遵守小米 <span>用户协议</span>和<span>隐私政策</span> </div>
    </div>
    <div class="sign_background_no8"><img src="img/sign01.jpg" alt=""></div>
</div>
<script>
	var isName = false;
	// 姓名的非空校验  失去焦点时间触发回调函数
	$("#name").blur(function (){
		var name = $("#name").val();
		if (name == null || name == "") {
			$("#nameMsg").text("姓名不能为空").css("color", "red");
			isName = false;
		}else{
			$("#nameMsg").text("姓名OK").css("color", "green");
			isName = true;
		}
	})
    var isArea = false;
	// 地区的非空校验
	$("#area").blur(function (){
		var area = $("#area").val();
		if (area == null || area == "") {
			$("#areaMsg").text("地区不能为空").css("color", "red");
			isArea = false;
		}else{
			$("#areaMsg").text("地区OK").css("color", "green");
			isArea = true;
		}
	})
	var isPassword = false;
	// 密码的非空校验
	$("#password").blur(function (){
		var password = $("#password").val();
		if (password == null || password == "") {
			$("#passwordMsg").text("密码不能为空").css("color", "red");
			isPassword =false;
		}else{
			$("#passwordMsg").text("密码OK").css("color", "green");
			isPassword = true;
		}
	})

	var isPhone = false;
	// 手机号的校验
	$("#phone").blur(function (){
		var phone = $("#phone").val();
		// 非空校验
		if (phone == null || phone == "") {
			$("#phoneMsg").text("手机号不能为空").css("color", "red");
			isPhone = false;
		}else{
			// 手机号是否合法
			var reg = /^1[3-9][0-9]{9}$/;
			if (!reg.test(phone)){
				$("#phoneMsg").text("手机号格式不对").css("color", "red");
				isPhone = false;
			}else{
				// 发送AJAX请求,校验手机号是否可用
				$.ajax({
					url:"${basePath}user", // ajax请求要发送的请求的路径 // http://localhost:80/xm/user
					data:{"method":"checkPhone", "phone":phone}, // 发送请求时要携带到后台的数据
					dataType:"json", // 告诉后台我需要的响应结果的数据类型
					type:"post", // ajax请求发送请求的方式: get或post
					success:function (respData){ // ajax请求响应的成功时回调的函数
						// respData存储就是响应的结果
						if (respData) {
							$("#phoneMsg").text("手机号未被注册").css("color", "green");
							isPhone = true;
						}else{
							$("#phoneMsg").text("手机号已被注册").css("color", "red");
							isPhone = false;
						}
					}
				})
			}
		}
	})
	var isUsername = false;
	// 账号的校验
	$("#username").blur(function (){
		var username = $("#username").val();
		// 非空校验
		if (username == null || username == "") {
			$("#usernameMsg").text("账号不能为空").css("color", "red");
			isUsername = false;
		}else{
			// 做唯一校验, 要去后台查
			$.ajax({
				url:"${basePath}user",
				data:{"method":"checkUsername", "username":username},
				type: "post",
				dataType: "json",
				success:function (respData) {
					if (respData){
						$("#usernameMsg").text("账号可用").css("color", "green");
						isUsername = true;
					}else{
						$("#usernameMsg").text("账号已被注册").css("color", "red");
						isUsername =false;
					}
				}
			})
		}
	})
	// 注册按钮
	$("#btn").click(function (){
		if (isName && isArea && isPhone && isPassword && isUsername) {
			// 获取表单对象提交数据
			$("#myForm").submit();
		}else{
			alert("请检查输入的注册数据")
		}
	})

	$("input").focus(function (){
		$("#msg1").text("");
	})
</script>
</body>
</html>