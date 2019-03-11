<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <title>数据字典分类管理</title>
  <link rel="stylesheet" href="${ctx}/static/css/global.css">
  <link rel="stylesheet" href="${ctx}/static/lib/layui/css/layui.css">
  <!-- ztree库 -->
  <link rel="stylesheet" href="${ctx}/static/lib/ztreev3/css/zTreeStyle/zTreeStyle.css">
  
  
  <style type="text/css">
  		.g-content {
  		 }
		.g-main-side { 
		  	position:relative;
		  	float:left;
		  	width:400px;
		  	margin-right:-400px;
		  	min-height: 600px;
		  	}
		.g-main {
			float:right;
			width:100%;
			}
		.g-main > .g-main-c {
		 	margin-left:420px;
		 	min-height: 600px;
		 	padding-top: 10px;
		 	border: 1px solid #ddd;
		   }
		.g-tree {
			padding-top: 26px;
		}
		.g-main-c .layui-form {
			padding: 10px;
		}
		
		.g-main-c  .layui-form-item > .layui-form-label {
			width: 160px;
		}
		.g-main-c  .layui-form-item > .layui-input-block {
			margin-left: 180px;
		}
		.g-main-c .g-main-op {
			margin-top: 50px;
			margin-left: 180px;
		}
		.g-main-c .g-main-op button {
			display: none;
		}
  </style>
</head>
<body class="layui-layout-body">


<div class="layui-layout layui-layout-admin">

  <!-- 头部区域 -->
  <@components.header />
  
  <!-- 侧面菜单区域 -->
  <@components.menu_aside />
  
  <div class="layui-body">
    <!-- 内容主体区域 -->
    <div  class="g-body">
    	
    	<!-- 面包屑导航区域布局 -->
    	<div class="g-breadcrumb  g-padding16 s-bgc-gray">
    		<span class="layui-breadcrumb">
			  <a href="">首页</a>
			  <a href="">系统管理</a>
			  <a><cite>数据字典分类管理</cite></a>
			</span>
    	</div>
    	
    	<!-- 搜索区域 -->
    	<div  class="g-search">
    	
    	</div>
    	
   	   <div class="g-content f-cb g-padding16">
		    <div class="g-main-side">
		    	
		    	<div class="g-menu-o">
		    		<button class="layui-btn layui-btn-normal" onclick="treeNodeAdd()" >添加</button>
		    		<button class="layui-btn layui-btn-warm" onclick="treeNodeEdit()" >编辑</button>
		    		<button class="layui-btn layui-btn-danger" onclick="treeNodeDelete()" >删除</button>
		    	</div>
		    	
		        <ul id="g-tree" class="ztree g-tree" ></ul>
		        
		    </div>
		    <div class="g-main">
		        <div class="g-main-c">
		        	<div class="layui-form">
		        		
		        		<div class="layui-form-item">
					    	<label class="layui-form-label">父级编码</label>
					    	<div class="layui-input-block">
					      		<input type="text" name="pcode"  id="pcode"  placeholder="父级编码"  class="layui-input"  readonly="readonly">
					    	</div>
					    </div>
					    
					    <div class="layui-form-item">
					    	<label class="layui-form-label">分类编码</label>
					    	<div class="layui-input-block">
					      		<input type="text" name="code"  id="code"   placeholder="分类编码"  class="layui-input"  readonly="readonly">
					    	</div>
					    </div>
					    
					    <div class="layui-form-item">
					    	<label class="layui-form-label">字典分类名称</label>
					    	<div class="layui-input-block">
					      		<input type="text" name="categoryName" id="categoryName"  value=""   placeholder="字典分类名称"  class="layui-input" >
					    	</div>
					    </div>
					    
					    <div class="layui-form-item">
					    	<label class="layui-form-label">是否启用</label>
					    	<div class="layui-input-block">
					      		<input type="checkbox" checked="" name="enabled"  id="enabled" lay-skin="switch" lay-filter="enabled" lay-text="启用|禁用">
					    	</div>
					    </div>
					    
					    <div class="layui-form-item">
					    	<div  class="g-main-op">
					    		<button class="layui-btn layui-btn-normal update" onclick="update()" >更新</button>
					    		<button class="layui-btn layui-btn-normal save" onclick="save()" >保存</button>
		    					<button class="layui-btn layui-btn-primary cancel" onclick="optionCancel()" >取消</button>
					    	</div>
					    </div>
					    
		        	</div>
		        </div>
		    </div>
		</div>
    	
    </div>
    <!-- /内容主体区域 -->
  </div>
  
  <!-- 使用定义页脚的宏 -->
  <@components.footer  />
  
  
</div>


<script src="${ctx}/static/lib/layui/layui.js"></script>
<script src="${ctx}/static/lib/ztreev3/js/jquery.ztree.core.min.js"></script>

<script type="text/javascript">
"use strict"

var element;
var table;
var layer;
var form;
var loading;

var loadingOption = {
		imgSrc: ctx + '/static/lib/layui_exts/loading/images/loading/loading-7.gif',
		background: '#000',
		opacity: 0.3,
		text: '请稍等...'
}


// ztree
var setting = {
		callback: {
			onClick: zTreeOnClick,
			
			beforeRemove : zTreeBeforeRemove,
            onRemove : zTreeOnRemove,
            onRename : zTreeOnRename
		}
};
var zNodes =[];
var treeObj; 
var selectTreeNode;

// 二级地址
const baseUrl = "/dict/category";

layui.config({
	  base: ctx + '/static/lib/layui_exts/'
	}).extend({
	  loading: 'loading/loading'
	}).use(['element', 'table', 'layer', 'form', 'loading'], function(){
	   element = layui.element;
	   table = layui.table;
	   layer = layui.layer;
	   form = layui.form;
	   
	   form.on('switch(enabled)', function(data){
		   console.log(data);
	   });
  
	   // 初始化tree
	   initTree();
});


// 初始化
function initTree() {
	
	$.get(ctx + baseUrl + "/tnodes").success(function(resp){
		if(resp.errcode === '0'){
			zNodes = resp.data;
			
			if(zNodes.length === 0) {
				$("#g-tree").html('暂无数据');
			} else {
				treeObj = $.fn.zTree.init($("#g-tree"), setting, zNodes);
			}
			
		}
	});
}

// 增加treeNode
function treeNodeAdd() {
	changeOption('add');
	$.get(ctx + baseUrl + "/checkFirstAdd").success(function(resp){
		if(resp.errcode === '0'){
			$("#pcode").val('PROOT');
			$("#code").val('root');
		} else {
			$("#pcode").val(selectTreeNode.id);
			$("#code").val('后台随机给');
			$("#categoryName").val('');
			$('#enabled').prop("checked", true);
			form.render(); //更新全部
		}
	});
	
}

// 编辑
function treeNodeEdit() {
	if($('#pcode').val() !== ''){
		changeOption('update');
	}
}

// 删除操作
function treeNodeDelete() {
	
	// var nodes = treeObj.getSelectedNodes();
	if(selectTreeNode == undefined) {
		layer.msg('请选择节点删除');
	} else {
		treeObj.removeNode(selectTreeNode,  true);
	}
}


var globalType = 'see';
// 切换操作
function changeOption(type) {
	if(type === 'add') {
		$('.g-main-op button').hide();
		$('.g-main-op .save').show();
		$('.g-main-op .cancel').show();
		if(globalType === 'update') {
			
		}
		globalType = 'add';
	} else if(type === 'update') {
		if(globalType === 'add') {
			layer.msg('重新选择');
			$('.g-main-op button').hide();
			return;
		}
		$('.g-main-op button').hide();
		$('.g-main-op .update').show();
		$('.g-main-op .cancel').show();
		globalType = 'add';
	} else if(type === 'see') {
		$('.g-main-op button').hide();
		
		globalType = 'see';
	}
}

// 操作取消
function optionCancel() {
	changeOption('see');
}


// 保存
function save() {
	
	var loading;
	
	var pcode = $("#pcode").val();
	var categoryName = $("#categoryName").val();
	var enabled = 1;
	
	if(!$("#enabled").is(':checked')) {
		enabled = 0;
	}
	
	if(categoryName === '') {
		layer.msg('请填写分类名称');
		return;
	}
	
	var params={};
	// 设值
	params['pcode'] = pcode;
	params['categoryName'] = categoryName;
	params['enabled'] = enabled;
	
	const URL = ctx + baseUrl + '/'
	console.log(params)
	// 提交
	$.ajax({
		url: URL,
		data: params,
		type: 'post',
		dataType: 'json',
		beforeSend: function(XHR){
			loading = $('.g-main-c').loading('show', loadingOption);
		},
		complete: function(XHR, TS){
			loading.hide();
		},
		success: function(resp){
			if(resp.errcode==='0') {
				layer.msg(resp.errmsg);
				
				
				
				// 初始化树
				initTree();
			} else if(resp.errcode==='1') {
				layer.msg(resp.errmsg);
			}
		}
	});
	
}

//修改
function update() {
	
var loading;
	
	var pcode = $("#pcode").val();
	var code = $("#code").val();
	var categoryName = $("#categoryName").val();
	var enabled = 1;
	
	if(!$("#enabled").is(':checked')) {
		enabled = 0;
	}
	
	if(categoryName === '') {
		layer.msg('请填写分类名称');
		return;
	}
	
	var params={};
	// 设值
	params['pcode'] = pcode;
	params['categoryName'] = categoryName;
	params['enabled'] = enabled;
	
	const URL = ctx + baseUrl + '/code/' + code;
	
	// 修改
	params['_method'] = 'PUT';
	// 提交
	$.ajax({
		url: URL,
		data: params,
		type: 'post',
		dataType: 'json',
		beforeSend: function(XHR){
			loading = $('.g-main-c').loading('show', loadingOption);
		},
		complete: function(XHR, TS){
			loading.hide();
		},
		success: function(resp){
			if(resp.errcode==='0') {
				layer.msg(resp.errmsg);
				// 初始化树
				initTree();
			} else if(resp.errcode==='1') {
				layer.msg(resp.errmsg);
			}
		}
	});
	
}


//单击事件，向后台发起请求
function zTreeOnClick(event, treeId, treeNode) {
	
	// 选择treeNode
	selectTreeNode = treeNode;
    
    $.get(ctx + baseUrl + "/code/" + treeNode.id).success(function(resp){
    	if(resp.errcode==='0') {
			layer.msg(resp.errmsg);
			changeOption('see');
			
			var data = resp.data;
			
			$('#pcode').val(data.pcode);
			$('#code').val(data.code);
			$('#categoryName').val(data.categoryName);
			
			if(data.enabled === true){
				$('#enabled').prop("checked",true);
			} else {
				$('#enabled').prop("checked",false);
			}
			
			form.render(); //更新全部
			
		} else if(resp.errcode==='1') {
			layer.msg(resp.errmsg);
		}
    });
    
    
}

function zTreeBeforeRemove(treeId, treeNode) {
    if (confirm("是否确认删除: "+ treeNode.name)) {
    	
    	var flag = false;
    	// 执行删除操作
    	$.ajax({
    		url: ctx + baseUrl + "/code/" + treeNode.id,
    		type: 'post',
    		data: {'_method': 'DELETE'},
    		dataType: 'json',
    		async: false,
    		success: function(resp) {
    			if(resp.errcode==='0') {
    				layer.msg(resp.errmsg);
    				flag = true;
    			} else if(resp.errcode==='1') {
    				layer.msg(resp.errmsg);
    			}
    		}
    	});
    	
    	return flag;
    }
    return false;
}

function zTreeOnRemove(event, treeId, treeNode) {
	
	
	
}

function zTreeOnRename(event, treeId, treeNode) {
    $.ajax({
        url : basePath + "/design/detain/updateName",
        data : {
            id : treeNode.id,
            name : treeNode.name
        },
        type : "POST",
        success : function(data) {
        }
    });
}




</script>
</body>
</html>