<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <title>用户账号</title>
  <link rel="stylesheet" href="${ctx}/static/css/global.css">
  <link rel="stylesheet" href="${ctx}/static/lib/layui/css/layui.css">
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
			  <a href="">用户管理</a>
			  <a><cite>账号管理</cite></a>
			</span>
    	</div>
    	
    	<!-- 搜索区域 -->
    	<div  class="g-search">
    	
    	</div>
    	
    	<!-- crud区域  -->
   		<div class="g-crud g-padding16">
   				<!-- 操作区域 -->
   				<div class="m-options">
   					<button class="layui-btn layui-btn-danger  batchDelete">删除</button>
   					<button class="layui-btn layui-btn-normal  add">添加</button>
   				</div>
   				<!-- 表格区域 -->
   				<table id="main-table"  lay-filter="main-table" class="layui-table"  lay-size="sm" lay-data="{cellMinWidth: 80, page: true}" ></table>
   		</div>
   		<!-- /crud区域  -->
    	
    </div>
    <!-- /内容主体区域 -->
  </div>
  
  <!-- 使用定义页脚的宏 -->
  <@components.footer  />
  
  
</div>

<script type="text/html" id="barOption">
  <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看账户</a>
  <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
<a class="layui-btn layui-btn-xs" lay-event="sysEdit">sys编辑</a>
  <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delete">删除</a>
</script>

<script src="${ctx}/static/lib/layui/layui.js"></script>
<script type="text/javascript">
"use strict"

var element;
var table;
var layer;

//二级地址
const baseUrl = "/user/account";

layui.use(['element', 'table', 'layer'], function(){
	   element = layui.element;
	   table = layui.table;
	   layer = layui.layer;
  
  
  table.render({
    elem: '#main-table'
    ,url: ctx + baseUrl + '/search'
    ,page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
      layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'] //自定义分页布局
      //,curr: 5 //设定初始在第 5 页
      ,groups: 1 //只显示 1 个连续页码
      ,first: false //不显示首页
      ,last: false //不显示尾页
      
    },
    cols: [[
      {type:'checkbox'},
      {field:'id', width:280, title: 'ID(主键)'},
      {field:'ucode', width:300, title: '账号编码'},
      {field:'username', width:180, title: '用户名'},
      {field:'phone', width:180, title: '手机号'},
      {field:'email', width:180, title: '邮箱'},
      {fixed: 'right', width:300, align:'center', toolbar: '#barOption'}
    ]],
    request: {
       pageName: 'pageNum', //页码的参数名称，默认：page
       limitName: 'pageSize' //每页数据量的参数名，默认：limit
     },
    response: {
   	    statusName: 'errcode' //规定数据状态的字段名称，默认：code
   	    ,statusCode: 0 //规定成功的状态码，默认：0
   	    ,msgName: 'errmsg' //规定状态信息的字段名称，默认：msg
   	    ,countName: 'totalElements' //规定数据总数的字段名称，默认：count
   	    ,dataName: 'data' //规定数据列表的字段名称，默认：data
   	  } ,
   	/*
    parseData: function(res) {
    	return {
   	      "code": res.errcode, //解析接口状态
   	      "msg": res.errmsg, //解析提示文本
   	      "count": res.totalElements, //解析数据长度
   	      "data": res.data //解析数据列表
   	    };
    }
   	  */
    
  });
  
  
/*执行重载
  table.reload('main-table', {
    page: {
      curr: 1 //重新从第 1 页开始
    }
  }); */
  
	//监听bar事件  为table lay-filter="main-table"
 	table.on('tool(main-table)', function(obj){
 	  
 	  switch(obj.event){
 	    case 'detail':
 	      layer.msg('添加');
 	    break;
 	    case 'delete':
 	    	layer.confirm("确定删除账号?", {icon: 3, title:'提示'}, function(index){
 	    		// 删除
 	 	    	deleteUser(obj.data.id);
	    		// 关闭页面
	    	    layer.close(index);
	    	 });
 	    break;
 	    case 'edit':
 	    	// 打开编辑页面
 	    	editUser(obj.data.id);
 	    break;
 	    case 'sysEdit': 
 	    	// 打开sysUser编辑页面
 	    	editSysUser(obj.data.id);
 	    	break;	
 	  };
 	});
  
	
	// 增加
	$(".m-options>.add").click(function() {
		
		// 打开页面
		layer.open({
		  title: '添加账号',
		  type: 2,
		  area: ['900px', '600px'],
		  fixed: false, //不固定
		  maxmin: true,
		  content: ctx + baseUrl + "/add.do",
		});
		
	});
	
	
	// 删除
	$(".m-options>.batchDelete").click(function() {
		
	});
  
});

// table刷新
function refreshTable() {
	table.reload('main-table', {
	  url: ctx + baseUrl + '/search'
	  ,where: {} //设定异步数据接口的额外参数
	  //,height: 300
	});
		    
}


// 删除账号
function  deleteUser(id) {
	if(id == '') {
		layer.msg('id不能为空');
		return;
	}
	
	var data = {};
	data['_method'] = 'DELETE';
	
	$.ajax({
		type: 'post',
		data: data,
		url: ctx + baseUrl + "/" + id,
		dataType: 'json'
	}).then(function(resp){
		if(resp.errcode === '0') {
			  layer.msg('删除成功', function(){
				  // 刷新table
				  refreshTable();
			  });
		  } else if(resp.errcode === '1') {
			  // 显示提示信息
			  layer.msg(resp.errmsg);
		  }
	});
	
}


// 编辑账号
function editUser(id) {
	// 打开页面
	layer.open({
	  title: '添加账号',
	  type: 2,
	  area: ['900px', '600px'],
	  fixed: false, //不固定
	  maxmin: true,
	  content: ctx + baseUrl + "/edit.do?id="+id,
	});
}


// 编辑sys账户
function editSysUser(id) {
	// 打开页面
	layer.open({
	  title: '添加账号',
	  type: 2,
	  area: ['900px', '600px'],
	  fixed: false, //不固定
	  maxmin: true,
	  content: ctx + baseUrl + "/sysedit.do?id="+id,
	});
}





</script>
</body>
</html>