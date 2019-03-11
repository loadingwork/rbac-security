<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <title>用户账号</title>
  <link rel="stylesheet" href="${ctx}/static/css/global.css">
  <link rel="stylesheet" href="${ctx}/static/lib/layui/css/layui.css">
  <style type="text/css">
  
  .g-content {
  }
  .g-content .layui-card {
  	height: 600px;
  }
  
  .g-dict-search {
  	padding: 10px;
  	border: 1px solid #eee;
  }
  .g-dict-search .layui-form-label {
  	width: 120px;
  }
  .g-dict-search .layui-input-block {
   margin-left: 140px;
  }
  
  .g-dict-option {
  	margin-top: 10px;
  }
  
  .m-dict-item {
  	position: relative;
  }
  .m-dict-item > .title {
  	position: absolute; 
  	right: 10px; 
  	display: inline-block; 
  	line-height: 38px; 
  	height: 38px;
  	color: red;
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
			  <a><cite>数据字典管理</cite></a>
			</span>
    	</div>
    	
    	<!-- 搜索区域 -->
    	<div  class="g-search">
    	
    	</div>
    	
    	<div class="g-content">
    		<div style="padding: 20px; background-color: #F2F2F2;">
			  <div class="layui-row layui-col-space15">
			  
			    <div class="layui-col-md6">
			      <div class="layui-card">
			        <div class="layui-card-header">数据字典类型</div>
			        <div class="layui-card-body">
			        	
			        	<div class="g-dict-search">
			        		<div class="layui-inline">
					            <label class="layui-form-label">名称</label>
					            <div class="layui-input-block">
					              <input type="text" name="loginname" placeholder="请输入字典名称/编码" autocomplete="off" class="layui-input">
					            </div>
					       </div>
					       <div class="layui-inline">
					            <button class="layui-btn">
					              <i class="layui-icon layui-icon-search"></i>
					            </button>
					       </div>
			        	</div>
			         	
			         	<div class="g-dict-option dict-type">
			         		<button class="layui-btn layui-btn-normal"  data-type='add' >增加</button>
			         		<button class="layui-btn"  data-type='dictTypeRefresh' >刷新</button>
			         	</div>
			         	
			         	<table id="main-table"  lay-filter="main-table" class="layui-table"  lay-size="sm" lay-data="{cellMinWidth: 80, page: true}" ></table>
			         	
			        </div>
			      </div>
			    </div>
			    
			    <div class="layui-col-md6">
			      <div class="layui-card">
			        <div class="layui-card-header">数据字典项</div>
			        <div class="layui-card-body">
			        
			        	<!-- 类型编码 -->
			        	<input type="hidden"  name="typeCode"  id="typeCode" value="" >
			        	
			        	<div class="g-dict-option dict-item m-dict-item" >
			         		<button class="layui-btn layui-btn-normal"  data-type='dictItemAdd' >增加</button>
			         		<button class="layui-btn"  data-type='dictItemRefresh' >刷新</button>
			         		
			         		<div class="title"></div>
			         	</div>
			         	
			         	<table id="dict-item-table"  lay-filter="dict-item-table" class="layui-table"  lay-size="sm" lay-data="{cellMinWidth: 80, page: true}" ></table>
			        	
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

<script type="text/html" id="barOption">
  <a class="layui-btn layui-btn-xs" lay-event="dictType">字典项</a>
  <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
  <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delete">删除</a>
</script>

<script type="text/html" id="barOptionDictItem">
  <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
  <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delete">删除</a>
</script>

<script src="${ctx}/static/lib/layui/layui.js"></script>
<script type="text/javascript">
"use strict"

var element;
var table;
var layer;

layui.use(['element', 'table', 'layer'], function(){
	   element = layui.element;
	   table = layui.table;
	   layer = layui.layer;
  
  		
	
	   
	   // 初始表格
	   initDictType();
  
	   
	   // 定义函数对象
	   var active = {
			   add: function(){
			        layer.open({
			          type: 2
			          ,title: '添加数据字典类型'
			          ,content: ctx + '/dict/type/add.do'
			          ,area: ['460px', '480px']
			          ,btn: ['确定', '取消']
			          ,yes: function(index, layero){
			        	 
			            var iframeWindow = window['layui-layer-iframe'+ index], 
			            submitID = 'form-dict-type-submit', 
			            submit = layero.find('iframe').contents().find('#'+ submitID);
	
			            //监听提交
			            // 注: 需要 lay-filter="form-dict-type-submit"
			            iframeWindow.layui.form.on('submit('+ submitID +')', function(data){
			            	
			              var field = data.field; //获取提交的字段
			              
			              $.ajax({
			            	  url: ctx + '/dict/type/',
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
			   dictTypeRefresh: function() {
				  
				   table.reload('main-table');
				   
			  },
			  dictItemAdd: function() {
				  
				  // 点击
				  var typeCode = $('#typeCode').val();
				  
				  if(typeCode == undefined || typeCode == '') {
					  layer.msg('请选择数据字典类型');
					  return;
				  }
				  
				  layer.open({
			          type: 2
			          ,title: '添加字典项'
			          ,content: ctx + '/dict/item/add.do?typeCode='+typeCode
			          ,area: ['460px', '420px']
			          ,btn: ['确定', '取消']
			          ,yes: function(index, layero){
			        	 
			            var iframeWindow = window['layui-layer-iframe'+ index], 
			            submitID = 'form-dict-item-submit', 
			            submit = layero.find('iframe').contents().find('#'+ submitID);
	
			            //监听提交
			            // 注: 需要 lay-filter="form-dict-type-submit"
			            iframeWindow.layui.form.on('submit('+ submitID +')', function(data){
			            	
			              var field = data.field; //获取提交的字段
			              
			              // 设置类型编码
			              field['typeCode'] = typeCode;
			              
			              $.ajax({
			            	  url: ctx + '/dict/item/',
			            	  data: field,
			            	  type: 'POST',
			            	  dataType: 'json',
			            	  success: function(resp){
			            		  if(resp.errcode === '0') {
			            			  layer.msg(resp.errmsg);
			            			  table.reload('dict-item-table');
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
			  dictItemRefresh: function() {
				  // 刷新表格
				  table.reload('dict-item-table');
			  }
	   }
	   
	   // 点击事件
	   $('.g-dict-option.dict-type button').on('click', function(){
		      var type = $(this).data('type');
		      active[type] ? active[type].call(this) : '';
	   });
	   
	   // 点击事件
	   $('.g-dict-option.dict-item button').on('click', function(){
		      var type = $(this).data('type');
		      active[type] ? active[type].call(this) : '';
	   });
	   
});

// 初始表格
function initDictType() {
	
	const baseUrl = '/dict/type';
	
	table.render({
	    elem: '#main-table'
	    ,url: ctx + baseUrl + '/search'
	    ,height: 400
	    ,page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
	      layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'] //自定义分页布局
	      //,curr: 5 //设定初始在第 5 页
	      ,groups: 1 //只显示 1 个连续页码
	      ,first: false //不显示首页
	      ,last: false //不显示尾页
	      
	    },
	    cols: [[
	      {type:'checkbox'},
	      {field:'dictCategoryCode', width: 100, title: '分类编码'},
	      {field:'typeCode', width: 100, title: '字典类型编码'},
	      {field:'text', width: 180, title: '名称'},
	      {field:'status', width: 60, title: '状态'},
	      {field:'gmtCreate', width: 160, title: '创建时间'},
	      {title: '操作', width:180, align:'center', fixed: 'right', toolbar: '#barOption'}
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
	   	  } 
	  });
	
	initDictTypeTool();
	
}


// 初始化数据字典项
function initDictItem(typeCode) {
	
	const baseUrl = '/dict/item';
	
	table.render({
	    elem: '#dict-item-table'
	    ,url: ctx + baseUrl + '/listDictItemPoByTypeCode?typeCode=' + typeCode
	    ,height: 400
	    ,page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
	      layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'] //自定义分页布局
	      //,curr: 5 //设定初始在第 5 页
	      ,groups: 1 //只显示 1 个连续页码
	      ,first: false //不显示首页
	      ,last: false //不显示尾页
	      
	    },
	    cols: [[
	      {type:'checkbox'},
	      {field:'typeCode', width: 100, title: '类型编码'},
	      {field:'dictCode', width: 100, title: '字典项编码'},
	      {field:'text', width: 180, title: '名称'},
	      {field:'enabled', width: 100, title: '是否使用'},
	      {field:'gmtCreate', width: 160, title: '创建时间'},
	      {title: '操作', width:140, align:'center', fixed: 'right', toolbar: '#barOptionDictItem'}
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
	   	  } 
	  });
	
	initDictItemTool();
	
}

// 数据字典类型
function initDictTypeTool() {
	
	//监听bar事件  为table lay-filter="main-table"
 	table.on('tool(main-table)', function(obj){
 		
 	  switch(obj.event){
 	    case 'detail':
 	      layer.msg('添加');
 	    break;
 	    case 'delete':
 	    	layer.confirm("确定删除账号?", {icon: 3, title:'提示'}, function(index){
 	    		// 删除
 	 	    	deleteDictType(obj.data.id);
	    		// 关闭页面
	    	    layer.close(index);
	    	 });
 	    break;
 	    
 	    case 'edit':
 	    	// 打开编辑页面
 	    	editDictType(obj.data.id);
 	    break;
 	    
 	    case 'dictType':
 	    	
 	    	// 设置选中颜色
 	    	$(obj.tr).css('background-color', '#eee').siblings().css('background-color', '#fff');
 	    	$('.m-dict-item > .title').html(obj.data.text);
 	    	$('#typeCode').val(obj.data.typeCode);
 	    	
 	    	dictTypeLook(obj.data.typeCode);
 	    break;	
 	  };
 	});
	
}


// 数据字典项类型
function initDictItemTool() {
	
	//监听bar事件  为table lay-filter="main-table"
 	table.on('tool(dict-item-table)', function(obj){
 	  
 	  switch(obj.event){
 	    case 'detail':
 	      layer.msg('添加');
 	    break;
 	    case 'delete':
 	    	layer.confirm("确定删除账号?", {icon: 3, title:'提示'}, function(index){
 	    		// 删除
 	 	    	deleteDictType(obj.data.id);
	    		// 关闭页面
	    	    layer.close(index);
	    	 });
 	    break;
 	    case 'edit':
 	    	// 打开编辑页面
 	    	editDictItem(obj.data.id);
 	    break;
 	  };
 	});
	
}

// 查看字典项
function dictTypeLook(dictType) {
	
	initDictItem(dictType);
}

// table刷新
function refreshDictTypeTable() {
	
	const baseUrl = '/dict/type';
	
	table.reload('main-table', {
	  url: ctx + baseUrl + '/search'
	  ,where: {} //设定异步数据接口的额外参数
	  //,height: 300
	});
		    
}

// 删除
function deleteDictType(id) {
	
	// 使用post请求模拟delete请求
	$.ajax({
		url: ctx + '/dict/type/' + id,
		data: {'_method': 'delete'},
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

// 编辑数据字典
function editDictItem(id) {
	
	layer.open({
        type: 2
        ,title: '添加字典项'
        ,content: ctx + '/dict/item/edit.do?id='+id
        ,area: ['460px', '420px']
        ,btn: ['确定', '取消']
        ,yes: function(index, layero){
      	 
          var iframeWindow = window['layui-layer-iframe'+ index], 
          submitID = 'form-dict-item-edit', 
          submit = layero.find('iframe').contents().find('#'+ submitID);

          //监听提交
          // 注: 需要 lay-filter="form-dict-item-edit"
          iframeWindow.layui.form.on('submit('+ submitID +')', function(data){
          	
            var field = data.field; //获取提交的字段
            
            field['_method'] = 'PUT';
            
            $.ajax({
          	  url: ctx + '/dict/item/' + id,
          	  data: field,
          	  type: 'POST',
          	  dataType: 'json',
          	  success: function(resp){
          		  if(resp.errcode === '0') {
          			  layer.msg(resp.errmsg);
          			  table.reload('dict-item-table');
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
	
}


// 编辑数据字典
function editDictType(id) {
	
	layer.open({
        type: 2
        ,title: '添加字典项'
        ,content: ctx + '/dict/type/edit.do?id='+id
        ,area: ['460px', '420px']
        ,btn: ['确定', '取消']
        ,yes: function(index, layero){
      	 
          var iframeWindow = window['layui-layer-iframe'+ index], 
          submitID = 'form-dict-type-edit', 
          submit = layero.find('iframe').contents().find('#'+ submitID);

          //监听提交
          // 注: 需要 lay-filter="form-dict-type-edit"
          iframeWindow.layui.form.on('submit('+ submitID +')', function(data){
          	
            var field = data.field; //获取提交的字段
            
            field['_method'] = 'PUT';
            
            $.ajax({
          	  url: ctx + '/dict/type/' + id,
          	  data: field,
          	  type: 'POST',
          	  dataType: 'json',
          	  success: function(resp){
          		  if(resp.errcode === '0') {
          			  layer.msg(resp.errmsg);
          			  table.reload('dict-item-table');
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
	
}


</script>
</body>
</html>