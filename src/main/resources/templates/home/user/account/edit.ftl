<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <title>用户账号</title>
  <link rel="stylesheet" href="${ctx}/static/css/global.css">
  <link rel="stylesheet" href="${ctx}/static/lib/layui/css/layui.css">
  <style type="text/css">
  
  	.g-body, .g-main {
  		height: 100%;
  	}
  
  	.g-main {
  		width: 800px;
  		margin: 0px auto;
  		padding: 20px 0;
  		position: relative;
  	}
  	
  	.g-form-row {
  		margin-bottom: 20px;
  	}
  	
  	.m-form-item {
	  	width: 396px;
	  	display: inline-block;
  	}
  	
  	.m-form-item > label {
  		width: 140px;
  	}
  	
  	.m-form-item > .layui-input-inline {
  		width: 256px;
  	}
  	
  	.g-form-option {
  		text-align: center;
  		padding-top: 20px;
  		width: 100%;
  		position: absolute;
  		bottom: 10px;
  	}
  	
  </style>
</head>
<body>

<!-- 注布局 -->
<div  class="g-body">
	<!-- 注内容区域 -->
	<div class="g-main">
		<input type="hidden"  id="primaryKey"  value="${model.id}"  />
		
		<div  class="g-form-row">
		
		    <div class="layui-inline  m-form-item  f-cb">
		      <label class="layui-form-label">用户名</label>
		      <div class="layui-input-inline">
		        <input type="text" id="username" name="username"  autocomplete="off" class="layui-input"  value="${model.username!''}" disabled />
		      </div>
		    </div>
			    
		    <div class="layui-inline  m-form-item  f-cb">
		      <label class="layui-form-label">手机号码</label>
		      <div class="layui-input-inline">
		        <input type="phone" id="phone" name="phone"  autocomplete="off" class="layui-input"  value="${model.phone!''}" >
		      </div>
		    </div>
		    
		</div>
		
		<div  class="g-form-row">
		
		    <div class="layui-inline  m-form-item  f-cb">
		      <label class="layui-form-label">邮箱</label>
		      <div class="layui-input-inline">
		        <input type="email" id="email" name="email"  autocomplete="off" class="layui-input" value="${model.email!''}" >
		      </div>
		    </div>
		    
		</div>
		
		<div  class="g-form-row">
		
		    <div class="layui-inline  m-form-item  f-cb">
		      <label class="layui-form-label">密码</label>
		      <div class="layui-input-inline">
		        <input type="password"  id="password" name="password"  autocomplete="off" class="layui-input">
		      </div>
		    </div>
			    
		    <div class="layui-inline  m-form-item  f-cb">
		      <label class="layui-form-label">重复密码</label>
		      <div class="layui-input-inline">
		        <input type="password"  id="repassword" name="repassword"  autocomplete="off" class="layui-input">
		      </div>
		    </div>
		    
		</div>
		
		
		<div class="g-form-option">
			<button class="layui-btn layui-btn-lg  layui-btn-normal layui-btn-radius  u-submit">修改信息</button>
		</div>
		
		
	</div>
</div>

<script src="${ctx}/static/lib/layui/layui.js"></script>
<script type="text/javascript">
"use strict"
layui.use(['element', 'table', 'layer'], function(){
  var element = layui.element;
  var table = layui.table;
  var layer = layui.layer;
  
  $(".g-form-option .u-submit").click(function() {
	  
	  var username = $("#username").val();
	  var phone = $("#phone").val();
	  var email = $("#email").val();
	  var password = $("#password").val();
	  var repassword = $("#repassword").val();
	  
	  var id = $("#primaryKey").val();
	  
	  if(username == '' && phone == '' && email == '') {
		  layer.msg('用户名/手机号/邮箱必填其一');
		  return;
	  }
	  
	  if(username) {
		  // TODO 用户名校验
		  
	  }
	  
	  // 密码
	  if(password != '') {
		  if(password.length < 6) {
			  layer.msg('密码长度不能少于6位');
			  return;
		  }
		  if(password != repassword) {
			  layer.msg('密码与重复密码不一致');
			  return;
		  }
	  }
	  
	  
	  var data = {};
	  
	  data['username'] = username;
	  data['phone'] = phone;
	  data['email'] = email;
	  data['password'] = password;
	  data['id'] = id;
	  
	  
	  data['_method'] = "PUT";
	  
	  $.ajax({
		url: ctx + "/user/account/"+id,
		type: "post",
		data: data
	  }).then(function(resp){
		  if(resp.errcode === '0') {
			  layer.msg('修改成功, 2秒后关闭',{
				  time: 2000
			  }, function(){
				// 刷新父页面
				// 也可以调用父级页面方法刷新table
				  top.location.reload();
			  });
			  // 关闭子页面
		  } else if(resp.errcode === '1') {
			  // 显示提示信息
			  layer.msg(resp.errmsg);
		  }
	  });
	  
	  
  });
  
  
});
</script>
</body>
</html>