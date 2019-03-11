<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <title>用户账号</title>
  <link rel="stylesheet" href="${ctx}/static/css/global.css">
  <link rel="stylesheet" href="${ctx}/static/lib/ztreev3/css/zTreeStyle/zTreeStyle.css">
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
  	
  	.g-ztree {
  		width:280px; 
  		height: 180px;
  		overflow-y: scroll;
  		overflow-x: auto;
  		background: #fff;
  		border: 1px solid #eee;
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
	      <label class="layui-form-label">分类</label>
	      <div class="layui-input-inline">
	        <!-- 
	        <input type="text" name="dictCategoryCode" lay-verify="required" placeholder="数据字典分类类型" autocomplete="off" class="layui-input">
	         -->
	        <input id="dictCategoryName" type="text"  class="layui-input"  readonly value="" lay-verify="required" onclick="showMenu();" />
	        <input id="dictCategoryCode" type="hidden" name="dictCategoryCode" class="layui-input"  readonly value=""  />
	      </div>
	    </div>
	    
	    <div class="layui-form-item">
	      <label class="layui-form-label">名称</label>
	      <div class="layui-input-inline">
	        <input type="text" name="text" lay-verify="required" placeholder="数据字典名称" autocomplete="off" class="layui-input">
	      </div>
	    </div>
	    
	    <div class="layui-form-item">
	      <label class="layui-form-label">状态</label>
	      <div class="layui-input-inline">
	        <select name="status" >
	        	<option value="ok">启用</option>
		        <option value="disable">停用</option>
	        </select>
	      </div>
	    </div>
	    
	    <div class="layui-form-item">
	      <label class="layui-form-label">备注</label>
	      <div class="layui-input-inline">
	        <textarea name="tableCol"  class="layui-textarea" placeholder="备注使用过的数据库表" ></textarea>
	      </div>
	    </div>
	    
	    <div class="layui-form-item layui-hide">
	      <input type="button" lay-submit lay-filter="form-dict-type-submit"  id="form-dict-type-submit" value="确认">
	    </div>
	    
	  </div>
	  
		
	</div>
</div>

<!-- 数据字典分类选择 -->
<div id="menuContent" class="menuContent" style="display:none; position: absolute;">
		<ul id="dictTree" class="ztree g-ztree" ></ul>
</div>

<script src="${ctx}/static/lib/layui/layui.js"></script>
<script src="${ctx}/static/lib/ztreev3/js/jquery.ztree.core.min.js"></script>
<script src="${ctx}/static/lib/ztreev3/js/jquery.ztree.excheck.min.js"></script>
<script type="text/javascript">
"use strict"


var setting = {
		check: {
			enable: true,
			chkStyle: "radio",
			radioType: "all"
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			onClick: onClick,
			onCheck: onCheck
		},
		view: {
            dblClickExpand: false,
            showLine: true
        }
	};

var zNodes =[];



layui.use(['element', 'table', 'layer', 'form'], function(){
  var element = layui.element;
  var table = layui.table;
  var layer = layui.layer;
  var form = layui.form;

  
  // 初始化列表
  initDictCategoryTree();
});

function initDictCategoryTree() {
	
	// 获取分类
	$.get(ctx + "/dict/category/tnodes").success(function(resp){
		if(resp.errcode === '0'){
			zNodes = resp.data;
			if(zNodes.length === 0) {
				$("#dictTree").html('暂无数据');
			} else {
				$.fn.zTree.init($("#dictTree"), setting, zNodes);
			}
			
		}
	});
}

// 点击事件
function onClick(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("dictTree");
	zTree.checkNode(treeNode, !treeNode.checked, null, true);
	return false;
}

// 选中事件
function onCheck(e, treeId, treeNode) {
	$("#dictCategoryName").val(treeNode.name);
	$("#dictCategoryCode").val(treeNode.id);
}

// 打开菜单
function showMenu() {
	var dictCategoryNameObj = $("#dictCategoryName");
	var dictCategoryNameOffset = $("#dictCategoryName").offset();
	
	$("#menuContent").css({ 
		left: dictCategoryNameOffset.left + "px", 
		top: (dictCategoryNameOffset.top + dictCategoryNameObj.outerHeight()) + "px" 
		}).slideDown("fast");

	$("body").bind("mousedown", onBodyDown);
}
// 隐藏菜单
function hideMenu() {
	$("#menuContent").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDown);
}

function onBodyDown(event) {
	if (!(event.target.id == "menuBtn" || event.target.id == "dictCategoryName" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
		hideMenu();
	}
}


</script>
</body>
</html>