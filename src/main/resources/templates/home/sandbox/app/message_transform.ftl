<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <title>主页</title>
  <link rel="stylesheet" href="${ctx}/static/css/global.css">
  <link rel="stylesheet" href="${ctx}/static/lib/layui/css/layui.css">
  <style type="text/css">
  	
  	.g-body {
  		
  	}
  	
  	/*2列布局*/
  	.g-main-content {
  		margin:0 0 10px;
  		height: 100%;
  	}
  	.g-aside {
  		display: none;
  		position:relative;
  		/*
  		float:left;
  		width:190px;
  		margin-right:-190px;
  		*/
  		min-height: 730px;
  	}
  	
  	.g-main{
  		float:right;
  		width:100%;
  	}
  	
  	.g-mainc {
  		/*
  		margin-left:200px;
  		*/
  	}
  	/*  /2列布局 */
  	
  	
  	.g-environment {
  		padding-top: 10px;
  		background-color: #bae7ff;
  	}
  	
  	.g-device {
  		padding: 20px 0;
  	} 
  	
  	
  	.g-client-message {
  		margin-top: 5px;
  		border: 1px solid #eee;
  		padding: 20px; 
  		background-color: #eee;
  	}
  	
  	.layui-card-body {
  		min-height: 500px;
  	}
  	
  	.m-client-message .m-title {
  		position: relative;
  	}
  	
  	.m-client-message .m-title .u-title {
  		
  	}
  	
  	.m-client-message .m-title .u-option {
  		position: absolute;
  		right: 10px;
  		top: 10px;
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
    <div  clas="g-body  g-padding16">
    
    		<!-- 面包屑导航区域布局 -->
	    	<div class="g-breadcrumb  g-padding16 s-bgc-gray">
	    		<span class="layui-breadcrumb">
				  <a href="">首页</a>
				  <a href="">用户管理</a>
				  <a><cite>账号管理</cite></a>
				</span>
	    	</div>
	    	
	    	<!-- 内容区域 -->
	    	<div  class="g-main-content  f-cb">
	    		
	    		<!-- 侧边栏 -->
	    		<div  class="g-aside">
	    			
	    			<div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief">
					  <ul class="layui-tab-title">
					    <li class="layui-this">历史记录</li>
					    <li>项目</li>
					  </ul>
					  <div class="layui-tab-content" style="height: 100px;">
					    <div class="layui-tab-item layui-show">
					    	<ul>
					    		<li>1111</li>
					    		<li>1111</li>
					    		<li>1111</li>
					    		<li>1111</li>
					    	</ul>
					    </div>
					    <div class="layui-tab-item">
					    	
					    	<ul>
					    		<li>1111</li>
					    		<li>1111</li>
					    		<li>1111</li>
					    		<li>1111</li>
					    	</ul>
					    	
					    </div>
					  </div>
					</div> 
	    			
	    		</div>
	    		<!-- /侧边栏 -->
	    		
	    		<!-- 主区域 -->
	    		<div class="g-main">
	    			
	    			<div class="g-mainc">
	    			
			    		<!-- 设备区域 -->
			    		<div class="g-device">
			    			
			    			<div class="layui-form  f-cb" >
			    			
			    				<div class="layui-inline">
							      <label class="layui-form-label"  style="width: 100px;">选择设备</label>
							      <div class="layui-input-inline">
							        <select name="deviceClient"  class="deviceClient" >
							          <option value="">请选择设备</option>
							          
							        </select>
							      </div>
							    </div>
							    
							    <div class="layui-inline">
							      <label class="layui-form-label"  style="width: 150px;">app版本(加密版本)</label>
							      <div class="layui-input-inline">
							        <select name="appVersion" class="appVersion" >
							          <option value="">请选择设备</option>
							        </select>
							      </div>
							    </div>
							    
							    <div class="layui-inline">
							      <label class="layui-form-label"  style="width: 150px;">设备虚拟udid</label>
							      <div class="layui-input-inline  deviceUdid" name="deviceUdid"   >
							        <input  class="layui-input" type="text"   />
							      </div>
							    </div>
			    				
			    			</div>
			    			
			    		</div>
			    		<!-- 设备区域 -->
			    		
			    		
			    		<!-- 客户端消息转换 -->
			    		<div  class="g-client-message  layui-form">
			    			
			    			<div class="m-client-message">
			    			
			    				<div class="layui-row layui-col-space15">
			    					<div class="layui-col-md6">
			    						<div class="layui-card">
									        <div class="layui-card-header">
									        	<div>参数</div>
									        </div>
									        <div class="layui-card-body m-params ">
									        
									        	<div class="m-parameter-option">
													<div class="layui-btn-container">
									    				<button class="layui-btn layui-btn-sm layui-btn-normal"  style="border: none;"  onclick="addUrlParam();"  >
									    					添加参数
									    				</button>
									    				<div class="layui-input-inline" style="width: 60px;">
													        <button class="layui-btn layui-btn-sm  layui-btn-danger" onclick="deleteAllUrlParam();" >
													        	<i class="layui-icon layui-icon-delete"></i>
													        </button>
													    </div>
													    
													    <button class="layui-btn layui-btn-sm layui-btn-normal"  style="border: none;"  onclick="processMessageTransform()"  >
									    					执行转换
									    				</button>
									    				
									    			</div>
												</div>
									        
									        	<ul class="url-param" >
									        		
									        		
							    					
									        	</ul>
									        	
									        </div>
									    </div>
			    					</div>
			    					
			    					<div class="layui-col-md6">
			    						<div class="layui-card">
									        <div class="layui-card-header"  style="position: relative;">
									        	<span>请求消息加密</span>
									        </div>
									        <div class="layui-card-body ">
									        
									        	<div class="m-request-encode">
									        	
									        		<textarea placeholder="请输入内容"  rows="20" class="layui-textarea  requestBody"></textarea>
									        	
									        	</div>
									        
									        </div>
									    </div>
			    					</div>
			    					
			    				</div>
			    			</div>
			    			
			    			
			    		</div>
			    		
			    		<!--  /客户端消息转换 -->
			    		
			    		
			    		<!-- 消息结果转换 -->
			    		<div  class="g-resp-message  layui-form">
			    			
			    			<div class="m-client-message">
			    			
			    				<div class="layui-row layui-col-space15">
			    					<div class="layui-col-md6">
			    						<div class="layui-card">
									        <div class="layui-card-header  m-title">
									        	
									        	<div class="u-title" >请求结果填入</div>
									        	
									        	<div class="u-option">
									        		<button class="layui-btn layui-btn-sm layui-btn-normal"  style="border: none;"  onclick="processRespMessageTransform()"  >
									    					执行转换
									    			</button>
									        	</div>
									        	
									        </div>
									        <div class="layui-card-body">
									        	
									        	<!-- 请求消息模块 -->
									        	
									        	<div class="m-return-key">
									        	
									        		<textarea placeholder="请填入密钥key"  rows="5" class="layui-textarea  signatureKey"></textarea>
									        		
									        	</div>
									        	
									        	<div class="m-return-message">
									        	
									        		<textarea placeholder="请填入请求结果"  rows="14" class="layui-textarea  data"></textarea>
									        	
									        	</div>
									        	
									        	
									        	
									        	<!--  /请求消息模块 -->
									        	
									        </div>
									    </div>
			    					</div>
			    					
			    					<div class="layui-col-md6">
			    						<div class="layui-card">
									        <div class="layui-card-header"  style="position: relative;">
									        	<span>请求消息加密</span>
									        </div>
									        <div class="layui-card-body ">
									        
									        	<div class="m-decode-msg">
									        	
									        		<textarea placeholder="解密内容"  rows="20" class="layui-textarea  requestBody"></textarea>
									        	
									        	</div>
									        
									        </div>
									    </div>
			    					</div>
			    					
			    				</div>
			    			</div>
			    			
			    			
			    		</div>
			    		
			    		<!-- /消息结果转换 -->
			    		
			    		
			    		
			    		
	    			
	    			</div>
	    		</div>
	    		<!-- /主区域 -->
	    		
	    		
	    	</div>
    			
    </div>
    <!-- /内容主体区域 -->
  </div>
  
  <!-- 使用定义页脚的宏 -->
  <@components.footer  />
  
  
</div>
<script src="${ctx}/static/lib/fetch/fetch.umd.js"></script>
<script src="${ctx}/static/lib/layui/layui.js"></script>
<script type="text/javascript">
"use strict"

var element;
var form;
var upload;
var layer;

layui.use(['element', 'form', 'upload'], function(){
   element = layui.element;
   form = layui.form;
   upload = layui.upload;
   layer = layui.layer;
	
	// 动态加载APPVersion
	$.get(ctx + "/sandbox/app/appversion").then(function(resp){
		if(resp.errcode === "0") {
			var htm = "";
			$.each(resp.data, function(key, value){
				
				htm += '<option value="'+value+'" >'+key+'</option>';
				
			});
			$('.appVersion').append(htm);
			
			form.render('select');
		}
	});
	
	// 动态加载设备类型
	$.get(ctx + "/sandbox/app/deviceclient").then(function(resp){
		if(resp.errcode === "0") {
			var htm = "";
			$.each(resp.data, function(key, value){
				
				htm += '<option value="'+value+'" >'+key+'</option>';
				
			});
			$('.deviceClient').append(htm);
			
			form.render('select');
		}
	});
	
  
  
});

function  addUrlParam() {
	
	var htm = "";
	
	  htm += '<li class="url-param-item" >';
	  htm += '    <div class="layui-form-item">';
	  htm += '        <div class="layui-inline">';
	  htm += '           <div  class="layui-input-inline"  style="height: 38px; width: 60px;">';
	  htm += '                <input type="checkbox" name="isParamChecked" lay-skin="primary" lay-filter="isParamChecked" title="启用" checked=true>';
	  htm += '           </div>';
	  htm += '          <div class="layui-input-inline" style="width: 140px;" >';
	  htm += '            <input type="text" name="key" placeholder="key" autocomplete="off" class="layui-input  key">';
	  htm += '          </div>';
	  htm += '          <div class="layui-form-mid">=</div>';
	  htm += '          <div class="layui-input-inline"  style="width: 240px;" >';
	  htm += '            <input type="text" name="value" placeholder="value" autocomplete="off" class="layui-input  value">';
	  htm += '          </div>';
	  htm += '          <div class="layui-input-inline" style="width: 60px;">';
	  htm += '            <button class="layui-btn  layui-btn-danger"  onclick="deleteUrlParam(this)" ><i class="layui-icon layui-icon-delete"></i></button>';
	  htm += '          </div>';
	  htm += '        </div>';
	  htm += '    </div>';
	  htm += '</li>';
	  
	  
	$(".url-param").append(htm);
	
	form.render('checkbox');
	
}

function deleteAllUrlParam() {
	
	$(".url-param").html("");
	
}

function deleteUrlParam(ele) {
	$(ele).parents(".url-param-item").remove();
}


// 请求加密
function processMessageTransform() {
	
	var datas = {};
	
	var deviceClient = $(".deviceClient").val();
	var appVersion = $(".appVersion").val();
	var deviceUdid = $(".deviceUdid").val();
	
	if(deviceClient === "") {
		layer.msg("请求选择设备类型");
		return;
	}
	
	if(appVersion === "") {
		layer.msg("请选择app版本");
		return;
	}
	
	var params = {};
	var paramsFlag = true;
	// 获取请求参数
	$(".m-params  .url-param .url-param-item").each(function(){
		
		if($(this).find('input[name=isParamChecked]').is(":checked")) {
			
			var key = $(this).find('.key').val();
			var value = $(this).find('.value').val();
			
			if(key && value) {
				params[key] = value;
				paramsFlag = false;
			}
			
		}
		
	});
	
	if(paramsFlag) {
		layer.msg("参数不能为空");
		return;
	}
	
	
	datas['deviceClient'] = deviceClient;
	datas['appVersion'] = appVersion;
	datas['deviceUdid'] = deviceUdid;
	datas['params'] = params;
	
	
	$.ajax({
		url: ctx + "/sandbox/app/processMessageTransform",
		type: "post",
		data: datas,
		dataType: "json",
	}).success(function(resp){
		
		if(resp.errcode === "0") {
			
			console.log(resp);
			
			$(".m-request-encode .requestBody").val(JSON.stringify(resp.data));
			
		} else if(resp.errcode === "1") {
			layer.msg(resp.errmsg);
		} else if(resp.errcode === "-1") {
			
		}
		
	});
	
	
}


// 请求结果转换
function processRespMessageTransform() {
	
	var datas = {};
	
	var deviceClient = $(".deviceClient").val();
	var appVersion = $(".appVersion").val();
	var deviceUdid = $(".deviceUdid").val();
	
	
	var data = $(".m-return-message .data");
	var signatureKey = $(".m-return-key .signatureKey");
	
	if(deviceClient === "") {
		layer.msg("请求选择设备类型");
		return;
	}
	
	if(appVersion === "") {
		layer.msg("请选择app版本");
		return;
	}
	
	if(data === "") {
		layer.msg("数据不能为空");
		return;
	}
	
	if(signatureKey === "") {
		layer.msg("加密不能为空");
		return;
	}
	
	
	datas['deviceClient'] = deviceClient;
	datas['appVersion'] = appVersion;
	datas['deviceUdid'] = deviceUdid;
	datas['data'] = data;
	datas['signatureKey'] = signatureKey;
	
	$.ajax({
		url: ctx + "/sandbox/app/processRespMessageTransform",
		type: "post",
		data: datas,
		dataType: "json",
	}).success(function(resp){
		
		if(resp.errcode === "0") {
			
			console.log(resp.data);
			
			$(".m-decode-msg  .requestBody").val(JSON.stringify(resp.data));
			
		} else if(resp.errcode === "1") {
			layer.msg(resp.errmsg);
		} else if(resp.errcode === "-1") {
			
		}
		
	});
	
	
	
	
}





</script>
</body>
</html>