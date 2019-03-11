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
  	
  	.m-is-enabled {
  		width: 550;
  		margin: 0 auto;
  	}
  	.m-is-enabled .layui-inline {
  		width: 160px;
  	}
  	.m-is-enabled  .layui-form-label {
  		width: 100px;
  	}
  	
  </style>
</head>
<body>

<!-- 注布局 -->
<div  class="g-body">
	<!-- 注内容区域 -->
	<div class="g-main  layui-form">
	
		<div  class="g-main-hide">
			<input type="hidden"  id="ucode" value="${ucode}" />
		</div>
		
		<div  class="g-form-row">
		
		    <div class="layui-inline  m-form-item  f-cb">
		      <label class="layui-form-label">昵称</label>
		      <div class="layui-input-inline">
		      	<input type="text" id="nickname" name="nickname"  autocomplete="off" class="layui-input" value="${sysUser.nickname!''}" >
		      </div>
		    </div>
			    
		    <div class="layui-inline  m-form-item  f-cb">
		      <label class="layui-form-label">电话号码</label>
		      <div class="layui-input-inline">
		      	<input type="text" id="telephone" name="telephone"  autocomplete="off" class="layui-input"  value="${sysUser.telephone!''}" >
		      </div>
		    </div>
		    
		</div>
		
		<!-- 
		<div  class="g-form-row">
		
		    <div class="layui-inline  m-form-item  f-cb">
		      <label class="layui-form-label">邮箱</label>
		      <div class="layui-input-inline">
		        <input type="email" id="email" name="email"  autocomplete="off" class="layui-input">
		      </div>
		    </div>
		    
		    <div class="layui-inline  m-form-item  f-cb">
		      <label class="layui-form-label">邮箱</label>
		      <div class="layui-input-inline">
		        <input type="email" id="email" name="email"  autocomplete="off" class="layui-input">
		      </div>
		    </div>
		    
		</div>
		 -->
		
		<div  class="g-form-row">
		
		    <div class="layui-inline  m-form-item  f-cb">
		      <label class="layui-form-label">性别</label>
		      <div class="layui-input-inline">
		        <select name="gender" id="gender" >
		        	<option value="0" <#if sysUser?? && sysUser.gender?string == "0">selected="true"</#if>>保密</option>
		        	<option value="1" <#if sysUser?? && sysUser.gender?string == "1">selected="true"</#if>>男</option>
		        	<option value="2" <#if sysUser?? && sysUser.gender?string == "2">selected="true"</#if>>女</option>
		        </select>
		      </div>
		    </div>
			    
		    <div class="layui-inline  m-form-item  f-cb">
		      <label class="layui-form-label">年龄</label>
		      <div class="layui-input-inline">
		        <input type="number"  id="age" name="age"  autocomplete="off" class="layui-input"  value="${sysUser.age!'0'}" >
		      </div>
		    </div>
		    
		</div>
		
		<div  class="g-form-row  m-is-enabled">
		
		    <div class="layui-inline   f-cb">
		      <label class="layui-form-label">是否锁定</label>
		      <div class="layui-input-inline">
		      	<input type="checkbox"  name="locked" id="locked" lay-skin="switch" lay-filter="locked" lay-text="是|否" <#if sysUser?? && sysUser.locked?string == "1">checked="true"</#if> />
		      </div>
		    </div>
			    
		    <div class="layui-inline   f-cb">
		      <label class="layui-form-label">是否激活</label>
		      <div class="layui-input-inline">
		      	<input type="checkbox" checked="" name="activated" id="activated" lay-skin="switch" lay-filter="activated" lay-text="是|否" <#if sysUser?? && sysUser.activated?string == "1">checked="true"</#if>  />
		      </div>
		    </div>
		    
		    <div class="layui-inline   f-cb">
		      <label class="layui-form-label">是否可用</label>
		      <div class="layui-input-inline">
		      	<input type="checkbox" checked="" name="enabled" id="enabled" lay-skin="switch" lay-filter="enabled" lay-text="是|否"  <#if sysUser?? && sysUser.enabled?string == "1">checked="true"</#if> />
		      </div>
		    </div>
		    
		</div>
		
		
		<div class="g-form-option">
			<#if sysUser?? >
				<button class="layui-btn layui-btn-lg  layui-btn-normal layui-btn-radius  u-submit">修改信息</button>
			<#else>
				<button class="layui-btn layui-btn-lg  layui-btn-normal layui-btn-radius  u-submit">提交信息</button>
			</#if>
		</div>
		
		
	</div>
</div>

<script src="${ctx}/static/lib/layui/layui.js"></script>
<script type="text/javascript">
"use strict"
layui.use(['element', 'table', 'layer', 'form'], function(){
  var element = layui.element;
  var table = layui.table;
  var layer = layui.layer;
  var form = layui.form;
  // if_exists 
  const isExist = '${(sysUser??)?string}';
  
  // 提交事件
  $(".g-form-option .u-submit").click(function() {
	  
	  var nickname = $("#nickname").val();
	  var telephone = $("#telephone").val();
	  var gender = $("#gender").val();
	  var age = $("#age").val();
	  var ucode = $("#ucode").val();
	  
	  
	  var locked = 0;
	  if($("#locked").is(":checked")) {
		  locked = 1;
	  }
	  var activated = 0;
	  if($("#activated").is(":checked")) {
		  activated = 1;
	  }
	  var enabled = 0;
	  if($("#enabled").is(":checked")) {
		  enabled = 1;
	  }
	  
	  var data = {};
	  
	  data['nickname'] = nickname;
	  data['telephone'] = telephone;
	  data['gender'] = gender;
	  data['age'] = age;
	  data['locked'] = locked;
	  data['activated'] = activated;
	  data['enabled'] = enabled;
	  data['ucode'] = ucode;
	  
	  $.post(ctx + "/sys/user/" + ucode, data).then(function(resp){
		  if(resp.errcode === '0') {
			  
			  var msg = '添加成功, 1秒后关闭';
			  if(isExist === 'true') {
				  msg = '修改成功, 1秒后关闭';
			  }
			  
			  layer.msg(msg, {
				  time: 1000
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
  
  
  // 开关选中
  //form.on('switch(locked)', function(data){
  //  layer.tips('账户确认锁定', data.othis)
  //});
  
  
});
</script>
</body>
</html>