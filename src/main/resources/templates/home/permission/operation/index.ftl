<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <title>操作管理</title>
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
			  <a href="">权限管理</a>
			  <a><cite>操作管理</cite></a>
			</span>
    	</div>
    	
    	<!-- 搜索区域 -->
    	<div  class="g-search">
    	
    	</div>
    	
    	<!-- crud区域  -->
   		<div class="g-crud g-padding16">
   				<!-- 操作区域 -->
   				<div class="m-options">
   					<button class="layui-btn layui-btn-danger"  data-type="batchDelete" >删除</button>
   					<button class="layui-btn layui-btn-normal"  data-type="add" >添加</button>
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
  <!--
  <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看账户</a>
  -->
  <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
  <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delete">删除</a>
</script>

<script src="${ctx}/static/lib/layui/layui.js"></script>
<script type="text/javascript">
"use strict"

// 二级地址
const baseUrl = "/sys/operation";

layui.use(['element', 'table', 'layer'], function(){
	var element = layui.element;
	var table = layui.table;
	var layer = layui.layer;
  
  
  
	var active = {
			add: function() {
				
				layer.open({
			          type: 2
			          ,title: '添加操作权限'
			          ,content: ctx + baseUrl + '/add.do'
			          ,area: ['460px', '580px']
			          ,btn: ['确定', '取消']
			          ,yes: function(index, layero){
			        	 
			            var iframeWindow = window['layui-layer-iframe'+ index], 
			            submitID = 'form-operation-add-submit', 
			            submit = layero.find('iframe').contents().find('#'+ submitID);
	
			            //监听提交
			            // 注: 需要 lay-filter="form-dict-type-submit"
			            iframeWindow.layui.form.on('submit('+ submitID +')', function(data){
			            	
			              var field = data.field; //获取提交的字段
			              
			              // 执行保存操作
			              $.ajax({
			            	  url: ctx + baseUrl + '/',
			            	  data: field,
			            	  type: 'post',
			            	  dataType: 'json',
			            	  success: function(resp){
			            		  if(resp.errcode === '0') {
			            			  layer.msg(resp.errmsg);
			            			  table.reload('main-table');
			            			  layer.close(index); //关闭弹层
			            		  } else {
			            			  layer.msg(resp.errmsg);
			            		  }
			            	  }
			              });
			              
			            });  
			            
			            // 出发提交
			            // 注: lay-submit
			            submit.trigger('click');
			          }
			        });
				
			},
			edit: function (id) {
				
				layer.open({
			          type: 2
			          ,title: '编辑操作'
			          ,content: ctx + baseUrl + '/edit.do?id=' + id
			          ,area: ['460px', '580px']
			          ,btn: ['确定', '取消']
			          ,yes: function(index, layero){
			        	 
			            var iframeWindow = window['layui-layer-iframe'+ index], 
			            submitID = 'form-operation-edit-submit', 
			            submit = layero.find('iframe').contents().find('#'+ submitID);
	
			            //监听提交
			            // 注: 需要 lay-filter="form-dict-type-submit"
			            iframeWindow.layui.form.on('submit('+ submitID +')', function(data){
			            	
			              var field = data.field; //获取提交的字段
			              
			              field['_method'] = 'PUT';
			              
			              // 执行保存操作
			              $.ajax({
			            	  url: ctx + baseUrl + '/'+id,
			            	  data: field,
			            	  type: 'POST',
			            	  dataType: 'json',
			            	  success: function(resp){
			            		  if(resp.errcode === '0') {
			            			  layer.msg(resp.errmsg);
			            			  table.reload('main-table');
			            			  layer.close(index); //关闭弹层
			            		  } else {
			            			  layer.msg(resp.errmsg);
			            		  }
			            	  }
			              });
			              
			            });  
			            
			            // 出发提交
			            // 注: lay-submit
			            submit.trigger('click');
			          }
			        });
				
			},
			deleteModel: function(id) {
				
				var params = {};
				
				params['_method'] = 'DELETE';
				
				// 执行保存操作
	              $.ajax({
	            	  url: ctx + baseUrl + '/'+id,
	            	  data: params,
	            	  type: 'POST',
	            	  dataType: 'json',
	            	  success: function(resp){
	            		  if(resp.errcode === '0') {
	            			  layer.msg(resp.errmsg);
	            			  table.reload('main-table');
	            		  } else {
	            			  layer.msg(resp.errmsg);
	            		  }
	            	  }
	             });
				
			}
	}
	
	// 初始化
	initTable(table, active);
	
	// 初始化时间
	initEvent(layer, active);
	
});


// 初始化事件
function initEvent(layer, active) {
	
	// 点击事件
   $('.m-options button').on('click', function(){
	   	  // 获取data-type
	      var type = $(this).data('type');
	      active[type] ? active[type].call(this) : '';
   });
		
	// 删除
   $(".m-options>.batchDelete").click(function() {
		
   });
	
}


// 初始化表单
function initTable(table, active) {
	
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
	      {field:'id', width:100, title: '内码'},
	      {field:'operationCode', width:180, title: '操作编码'},
	      {field:'name', width:180, title: '名称'},
	      {field:'gmtCreate', width:180, title: '创建时间'},
	      {field:'gmtModified', width:180, title: '修改时间'},
	      {fixed: 'right', width:200, align:'center', toolbar: '#barOption'}
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
  
  
		//监听bar事件  为table lay-filter="main-table"
	 table.on('tool(main-table)', function(obj){
	 	  
	 	  switch(obj.event){
	 	    case 'detail':
	 	      layer.msg('添加');
	 	    break;
	 	    case 'delete':
	 	    	layer.confirm("确定删除?", {icon: 3, title:'提示'}, function(index){
	 	    		// 删除
	 	 	    	active.deleteModel(obj.data.id);
		    		// 关闭页面
		    	    layer.close(index);
		    	 });
	 	    break;
	 	    case 'edit':
	 	    	// 打开编辑页面
	 	    	active.edit(obj.data.id);
	 	    break;
	 	  };
	 });
	
	
}








</script>
</body>
</html>