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
  		width: 400px;
  		margin: 0px auto;
  		padding: 20px 0;
  		position: relative;
  	}
  	
  	.g-main .layui-form-item >  .layui-input-inline {
  		width: 280px;
  	}
  	
  	.g-main .layui-form-item >  .layui-form-label {
  	   width: 86px;
  	}
  	
  	
  </style>
</head>
<body>

<!-- 注布局 -->
<div  class="g-body">
	<!-- 注内容区域 -->
	<div class="g-main">
		
		<div class="layui-form"  style="padding: 20px 0 0 0;">
		
	    
	    <div class="layui-form-item">
	      <label class="layui-form-label">名称</label>
	      <div class="layui-input-inline">
	        <input type="text" name="text" lay-verify="required" placeholder="数据字典名称" autocomplete="off" class="layui-input">
	      </div>
	    </div>
	    
	    <div class="layui-form-item">
	      <label class="layui-form-label">业务代码</label>
	      <div class="layui-input-inline">
	        <input type="text" name="dictCode" lay-verify="required|dictCode" placeholder="必须以字母开头, 剩余由字母, 数字,  下划线组成, 长度不大于4位" autocomplete="off" class="layui-input">
	      </div>
	    </div>
	    
	    <div class="layui-form-item">
	      <label class="layui-form-label">状态</label>
	      <div class="layui-input-inline">
	        <select name="enabled" >
	        	<option value="1">启用</option>
		        <option value="0">停用</option>
	        </select>
	      </div>
	    </div>
	    
	    <div class="layui-form-item">
	      <label class="layui-form-label">备注</label>
	      <div class="layui-input-inline">
	        <textarea name="remark"  class="layui-textarea" placeholder="备注使用过的数据库表" ></textarea>
	      </div>
	    </div>
	    
	    <div class="layui-form-item layui-hide">
	      <input type="button" lay-submit lay-filter="form-dict-item-submit"  id="form-dict-item-submit" value="确认">
	    </div>
	    
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
  
  form.verify({
	  dictCode: [
	    /^[a-z]{1}[a-z1-9_]{1,3}$/, 
	    '必须以字母开头, 剩余由字母, 数字,  下划线组成, 长度不大于4位'
	  ] 
	}); 

  
});


</script>
</body>
</html>