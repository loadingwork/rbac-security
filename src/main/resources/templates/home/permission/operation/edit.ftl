<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <title>编辑操作权限</title>
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
		
		<div style="display: none;">
			<input name="id"  value="${model.id}" />
		</div>
	    
	    <div class="layui-form-item">
	      <label class="layui-form-label">名称</label>
	      <div class="layui-input-inline">
	        <input type="text" name="name" lay-verify="required" placeholder="数据字典名称" autocomplete="off" class="layui-input" value="${model.name}" >
	      </div>
	    </div>
	    
	    <div class="layui-form-item">
	      <label class="layui-form-label">权限编码</label>
	      <div class="layui-input-inline">
	        <input type="text" name="operationCode"   autocomplete="off" class="layui-input" value="${model.operationCode}" readonly="readonly" />
	      </div>
	    </div>
	    
	    <div class="layui-form-item">
	      <label class="layui-form-label">匹配类型</label>
	      <div class="layui-input-inline">
	        <select name="matchType" >
	        	<option value="none" <#if model.matchType?string=="none" >selected="selected"</#if> >没有</option>
	        	<option value="ant"  <#if model.matchType?string=="ant" >selected="selected"</#if> >ant</option>
		        <option value="pattern" <#if model.matchType?string=="pattern" >selected="selected"</#if> >pattern</option>
	        </select>
	      </div>
	    </div>
	    
	    <div class="layui-form-item">
	      <label class="layui-form-label">匹配表达式</label>
	      <div class="layui-input-inline">
	        <input type="text" name="matchPattern" value="${model.matchPattern}"  placeholder="匹配表达式" autocomplete="off" class="layui-input">
	      </div>
	    </div>
	    
	    <div class="layui-form-item">
	      <label class="layui-form-label">状态</label>
	      <div class="layui-input-inline">
	        <select name="enabled" >
	        	<option value="1" <#if model.enabled?string=='true' >selected="selected"</#if> >启用</option>
		        <option value="0" <#if model.enabled?string=='false' >selected="selected"</#if> >停用</option>
	        </select>
	      </div>
	    </div>
	    
	    <div class="layui-form-item">
	      <label class="layui-form-label">备注</label>
	      <div class="layui-input-inline">
	        <textarea name="remarks"  class="layui-textarea" placeholder="备注" >${model.remarks}</textarea>
	      </div>
	    </div>
	    
	    <div class="layui-form-item layui-hide">
	      <input type="button" lay-submit lay-filter="form-operation-edit-submit"  id="form-operation-edit-submit" value="确认"  />
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
	  operationCode: [
	    /^[a-z]{1}[a-z1-9_]{1,255}$/, 
	    '必须以字母开头, 剩余由字母, 数字,  下划线组成, 长度不大于256位'
	  ] 
	}); 

  
});


</script>
</body>
</html>