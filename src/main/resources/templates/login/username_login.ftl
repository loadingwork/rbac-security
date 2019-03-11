<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>用户登录</title>
	<link rel="stylesheet" href="${ctx}/static/css/global.css">
    <link rel="stylesheet" href="${ctx}/static/css/login/username_login.css">
</head>
<body>

	<div class="g-body">
		
		<!-- 
		http://localhost:8001/u_login.do
		 -->
		<!-- form 表单 -->
		<form id="form-login" action="${ctx}/u_login.do"  method="POST"  >
		<!-- 登录界面 -->
		<div class="g-login">

			<div class="m-login-header">
				<div class="u-title">平台后台</div>
				<div class="u-subtitle">欢迎登录</div>
			</div>

			<div class="m-login-body">

				<div class="u-sign-row u-errorInfo active">
					<div class="u-fm-error">
						<!-- <i class="fa fa-exclamation-circle"></i> -->
						<span>
						${errmsg!""}
						</span>
					</div>
				</div>

				<div class="u-sign-row u-account">
					<input class="username" name="username" type="text" autocomplete="off"
						placeholder="请输入账号">
				</div>

				<div class="u-sign-row u--password">
					<input class="password" name="password" type="password" autocomplete="off"
						placeholder="请输入密码">
				</div>
				
				<#--
				验证码模块关闭
				<div class="u-sign-row u-verifyCode  f-cb">
					<input class="verifyCode" name="verifyCode" type="text"
						placeholder="请输入验证码" />
					<div class="u-verifyCode-img">
						<img
							src="http://via.placeholder.com/80x30/eeeeee/verifyCode.png?text=12x5"
							alt="">
					</div>
				</div>
				
				-->
				
				<div class="u-sign-row u-memberpass">
					<input class="remember" id="remember" name="remember-me"
						type="checkbox"> <label for="remember">自动登录</label> <a
						class="u-forgetpasswd" href="javascript:alert('暂时, 木有实现')">忘记密码?</a>
				</div>
				

				<div class="u-sign-row u-login-options">
					<a class="u-login-confirm"  href="javascript:;" onclick="submitForm()" >进入平台</a>
				</div>


			</div>


			<div class="m-login-footer">
				<div class="u-login-other">
					<div>
						<a href="${ctx}/login/qrcode.do">其他登录方式</a>
					</div>
				</div>
			</div>


		</div>
		<!-- /登录界面 -->
		</form>
		<!-- /form 表单 -->

	</div>


	<!-- cdn -->
	<script src="${ctx}/static/lib/jquery/jquery-1.12.4/jquery.min.js"></script>
	<script type="text/javascript">
	"use strict"
	
	// form提交
	function submitForm() {
		// 检查参数
		
		//$(".u-fm-error span").html("");
		
		if($(".username").val() === '') {
			$(".u-fm-error span").html("用户名不能为空");
			return;
		}
		if($(".password").val() === '') {
			$(".u-fm-error span").html("密码不能为空");
			return;
		}
		
		$("#form-login").submit();
	}
	
	</script>

</body>
</html>