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
  	
  	.m-form-full-item {
  	} 
  	.m-form-full-item label {
  		width: 140px;
  	} 
  	.m-form-full-item .u-input-content {
  		margin-left: 140px; 
  		min-height: 36px;
  	} 
  	
  </style>
</head>
<body>

<!-- 注布局 -->
<div  class="g-body">
	<!-- 注内容区域 -->
	<div class="g-main  layui-form">
		
		
		<div  class="g-form-row">
		
		    <div class="layui-inline  m-form-item  f-cb">
		      <label class="layui-form-label">角色编码</label>
		      <div class="layui-input-inline">
		        <input type="text" id="roleCode" name="roleCode"  autocomplete="off" class="layui-input" placeholder="创建后不允许修改" >
		      </div>
		    </div>
		    
		</div>
		
		<div  class="g-form-row">
		
		    <div class="layui-inline  m-form-item  f-cb">
		      <label class="layui-form-label">角色名称</label>
		      <div class="layui-input-inline">
		        <input type="text" id="name" name="name"  autocomplete="off" class="layui-input">
		      </div>
		    </div>
		    
		</div>
		
		<div  class="g-form-row">
		
		    <div class="layui-form-item layui-form-text  m-form-full-item">
		      <label class="layui-form-label"  >角色描述</label>
		      <div class="u-input-content" >
			      <textarea placeholder="请输入内容" class="layui-textarea" name="describtion" id="describtion" ></textarea>
			  </div>
		    </div>
		    
		</div>
		
		<div  class="g-form-row">
		
		    <div class="layui-form-item layui-form-text  m-form-full-item">
		      <label class="layui-form-label"  >后台备注</label>
		      <div class="u-input-content" >
			      <textarea placeholder="请输入内容" class="layui-textarea" name="remarks" id="remarks" ></textarea>
			  </div>
		    </div>
		    
		</div>
		
		
		<div  class="g-form-row">
		
		    <div class="layui-form-item layui-form-text  m-form-full-item">
		      <label class="layui-form-label"  >是否启用</label>
		      <div class="u-input-content" >
			      <input type="checkbox" checked="" name="enabled" id="enabled" lay-skin="switch" lay-filter="enabled" lay-text="启用|关闭">
			  </div>
		    </div>
		    
		</div>
		
		
		
		<div class="g-form-option">
			<button class="layui-btn layui-btn-lg  layui-btn-normal layui-btn-radius  u-submit">提交信息</button>
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
  
  $(".g-form-option .u-submit").click(function() {
	  
	  var roleCode = $("#roleCode").val();
	  var name = $("#name").val();
	  var describtion = $("#describtion").val();
	  var remarks = $("#remarks").val();
	  
	  var enabled = 0;
	  if($("#enabled").is(":checked")) {
		  enabled = 1;
	  }
	  
	  if(roleCode === '') {
		  layer.msg('请填写角色编码');
		  return;
	  }
	  
	  if(name === '') {
		  layer.msg('请填写角色名称');
		  return;
	  }
	  
	  
	  var data = {};
	  
	  data['roleCode'] = roleCode;
	  data['name'] = name;
	  data['describtion'] = describtion;
	  data['remarks'] = remarks;
	  data['enabled'] = enabled;
	  
	  $.post(ctx + "/sys/role/", data).then(function(resp){
		  if(resp.errcode === '0') {
			  layer.msg('添加成功, 1秒后关闭', {
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
  
  
});
</script>
</body>
</html>