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
  	
  	.g-request .m-url-request {
  		position: relative;
  		margin-top: 20px;
  		margin-bottom: 40px;
  	}
  	
  	.g-request .m-url-request .on-method {
  		z-index: 100px;
  		width: 200px;
  	}
  	.g-request .m-url-request .on-input {
  		width: 100%;
  		position: absolute;
  		top: 0px;
  		padding-right: 220px;
  	}
  	.g-request .m-url-request .onsubmit {
  		position: absolute;
  		right: 10px;
  		top: 0px;
  	}
  	.g-request .m-parameter  {
  		margin-top: 5px;
  	}
  	
  	.g-request .m-parameter .m-parameter-option  {
  		margin: 10px;
  	}
  	
  	.g-request .m-parameter ul.url-param {
  		width: 1200px;
  		margin-left: 20px;
  	}
  	
  	.g-request .m-parameter ul.url-param li {
  		display: inline-block;
  		width: 598px;
  	}
  	
  	
  	.m-header-and-body {
  		margin-top: 5px;
  		min-height: 300px;
  		border: 1px solid #eee;
  		padding: 20px; 
  		background-color: #eee;
  	}
  	
  	.m-header-and-body  .m-body-type {
  		padding: 10px 0;
  	}
  	
  	.m-header-and-body   .m-body-type > a {
  		cursor: pointer;
  		padding: 10px;
  	}
  	
  	.m-header-and-body   .m-body-type > a:hover {
  		background-color: #d4b106;
  		color: #fff;
  	}
  	
  	.m-header-and-body   .m-body-type > a.active {
  		color: #a8071a;
  		background-color: #fff1f0;
  	}
  	
  	.layui-card-body {
  		min-height: 500px;
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
	    			
	    				<!-- 环境区域 -->
			    		<div  class="g-environment  f-cb">
			    		
			    			<div class="layui-btn-container  f-fr">
			    				<button class="layui-btn layui-btn-normal layui-btn-radius"  style="border: none;"><i class="layui-icon layui-icon-add-1"></i>添加环境</button>
			    			</div>
			    			
			    		</div>
			    		<!-- /环境区域 -->
			    		
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
			    		
			    		<!-- 请求区域 -->
			    		<div  class="g-request  layui-form">
			    			
			    			<div  class="m-url-request">
			    				
			    				<div class="on-method layui-inline">
			    					<select name="reqMethod" lay-filter="reqMethod" class="reqMethod" >
									  <option value="">请求方式</option>
									  <option value="get">GET</option>
									  <option value="post">POST</option>
									  <!-- 
									  <option value="put">PUT</option>
									  <option value="delete">DELETE</option>
									   -->
									</select>
			    				</div>  
			    				<div class="on-input layui-inline">
			    						<input id="request-url" type="text" name="title"  placeholder="SCHEME :// HOST [ ':' PORT ] [ PATH [ '?' QUERY ]]" autocomplete="off" class="layui-input">
			    				</div>
			    				<div class="onsubmit  layui-inline">
			    					<button type="button" class="layui-btn  layui-btn-normal"  onclick="requestAction()" >发送</button>
			    				</div>
			    				
			    			</div>
			    			
			    			
			    			
			    			<!-- url参数查询 -->
			    			<div class="m-parameter">
			    			
			    				<div style="font-size: 18px;margin-left: 20px;">查询参数</div>
			    				
			    				<div class="m-parameter-option">
									<div class="layui-btn-container">
					    				<button class="layui-btn layui-btn-sm layui-btn-normal layui-btn-radius"  style="border: none;"   onclick="addUrlParam()" >
					    					<i class="layui-icon layui-icon-add-1"></i>添加参数
					    				</button>
					    				<button class="layui-btn layui-btn-sm layui-btn-primary"  onclick="deleteAllUrlParam()" >
					    					<i class="layui-icon layui-icon-delete"></i>
					    				</button>
					    			</div>
								</div>
			    				
			    				<ul class="url-param" >
			    					
			    					<!-- 
			    					<li class="url-param-item" >
			    						<div class="layui-form-item">
										    <div class="layui-inline">
											   <div  class="layui-input-inline"  style="height: 38px; width: 60px;">
						    						<input type="checkbox" name="isParamChecked" lay-skin="primary" lay-filter="isParamChecked" title="启用" checked=true>
						    				   </div>
										      <div class="layui-input-inline" style="width: 140px;" >
										        <input type="text" name="key" placeholder="key" autocomplete="off" class="layui-input">
										      </div>
										      <div class="layui-form-mid">=</div>
										      <div class="layui-input-inline"  style="width: 240px;" >
										        <input type="text" name="value" placeholder="value" autocomplete="off" class="layui-input">
										      </div>
										      <div class="layui-input-inline" style="width: 60px;">
										        <button class="layui-btn  layui-btn-danger"><i class="layui-icon layui-icon-delete"></i></button>
										      </div>
										    </div>
										</div>
			    					</li>
			    					 -->
			    					
			    				</ul>
			    				
			    			</div>
			    			<!-- /url参数查询 -->
			    			
			    			<div class="m-header-and-body">
			    				<div class="layui-row layui-col-space15">
			    					<div class="layui-col-md6">
			    						<div class="layui-card">
									        <div class="layui-card-header">
									        	<div>请求头</div>
									        </div>
									        <div class="layui-card-body m-header ">
									        
									        	<div class="m-parameter-option">
													<div class="layui-btn-container">
									    				<button class="layui-btn layui-btn-sm layui-btn-normal"  style="border: none;"  onclick="addHeaders();"  >
									    					<i class="layui-icon layui-icon-add-1"></i>添加参数
									    				</button>
									    				<div class="layui-input-inline" style="width: 60px;">
													        <button class="layui-btn layui-btn-sm  layui-btn-danger" onclick="deleteAllHeaders();" >
													        	<i class="layui-icon layui-icon-delete"></i>
													        </button>
													    </div>
									    			</div>
												</div>
									        
									        	<ul class="req-headers" >
									        		
									        		<!-- 
									        		<li  class="req-headers-item">
							    						<div class="layui-form-item">
														    <div class="layui-inline">
															   <div  class="layui-input-inline"  style="height: 38px; width: 30px;">
										    						<input type="checkbox" name="isHeadersChecked" lay-skin="primary"  lay-filter="isHeadersChecked"  checked=true>
										    				   </div>
														      <div class="layui-input-inline" style="width: 140px;" >
														        <input type="text" name="price_min" placeholder="key" autocomplete="off" class="layui-input">
														      </div>
														      <div class="layui-form-mid">=</div>
														      <div class="layui-input-inline"  style="width: 240px;" >
														        <input type="text" name="price_max" placeholder="value" autocomplete="off" class="layui-input">
														      </div>
														      <div class="layui-input-inline" style="width: 60px;">
														        <button class="layui-btn  layui-btn-danger"><i class="layui-icon layui-icon-delete"></i></button>
														      </div>
														    </div>
														</div>
							    					</li>
									        		 -->
							    					
									        	</ul>
									        	
									        </div>
									    </div>
			    					</div>
			    					<div class="layui-col-md6">
			    						<div class="layui-card">
									        <div class="layui-card-header"  style="position: relative;">
									        	<span>请求body</span>
									        	<div class="layui-inline"  style="position: absolute;right: 10px; top: 5px; display: none;">
									        		<select  lay-filter="requestBodyType" class="requestBodyType"  >
													  <option value="text">text</option>
													  <option value="file">file</option>
													  <option value="form">form</option>
													</select>  
									        	</div>
									        </div>
									        <div class="layui-card-body ">
									        
									        	<div class="m-request-body-content">
									        	
									        		<div class="text"  >
										        		<textarea placeholder="请输入内容"  rows="20" class="layui-textarea  requestBody"></textarea>
										        		<div class="m-body-type  bodyContentType">
										        				<a href="javascript:;" class="active" >text</a>
											        			<a href="javascript:;" >json</a>
											        			<a href="javascript:;" >xml</a>
											        			<a href="javascript:;" >html</a>
										        		</div>
										        	</div>
										        	
										        	<div  class="file  f-dn" id="upload-file" >
										        		<div class="layui-upload-drag">
														  <i class="layui-icon layui-icon-upload"></i>
														  <p>点击上传，或将文件拖拽到此处</p>
														</div>
										        	</div>
										        	
										        	<div class="form  f-dn" >
										        		<div>表单数据</div>
										        		
										        		<!-- 请求body操作区域 -->
										        		<div class="m-parameter-option">
															<div class="layui-btn-container">
											    				<button class="layui-btn layui-btn-sm layui-btn-normal"  style="border: none;"  onclick="addRequestBody();"  >
											    					<i class="layui-icon layui-icon-add-1"></i>添加参数
											    				</button>
											    				<div class="layui-input-inline" style="width: 60px;">
															        <button class="layui-btn layui-btn-sm  layui-btn-danger" onclick="deleteAllRequestBody();" >
															        	<i class="layui-icon layui-icon-delete"></i>
															        </button>
															    </div>
											    			</div>
														</div>
										        		
										        		<ul class="request-body" >
										        		
										        			<!-- 
											        		<li class="request-body-item" >
									    						<div class="layui-form-item">
																    <div class="layui-inline">
																	   <div  class="layui-input-inline"  style="height: 38px; width: 30px;">
												    						<input type="checkbox" name="isRequestBodyChecked" lay-filter="isRequestBodyChecked" lay-skin="primary"  checked="true">
												    				   </div>
																      <div class="layui-input-inline" style="width: 140px;" >
																        <input type="text" name="key" placeholder="key" autocomplete="off" class="layui-input">
																      </div>
																      <div class="layui-form-mid">=</div>
																      <div class="layui-input-inline"  style="width: 240px;" >
																        <input type="text" name="value" placeholder="value" autocomplete="off" class="layui-input">
																      </div>
																      <div class="layui-input-inline" style="width: 60px;">
																        <button class="layui-btn  layui-btn-danger">
																        	<i class="layui-icon layui-icon-delete"></i>
																        </button>
																      </div>
																    </div>
																</div>
									    					</li>
										        			 -->
									    					
											        	</ul>
											        	
											        	
										        	</div>
									        	
									        	
									        	</div>
									        
									        </div>
									    </div>
			    					</div>
			    				</div>
			    			</div>
			    			
			    			
			    		</div>
			    		<!-- /请求区域 -->
			    		
			    		<!-- 响应区域 -->
			    		<div  class="g-response">
			    		
			    			<div  class="layui-form" style="padding: 20px; background-color: #F2F2F2;">
			    			
							  <div class="layui-row layui-col-space15">
							    <div class="layui-col-md6">
							      <div class="layui-card">
							        <div class="layui-card-header">响应头</div>
							        <div class="layui-card-body">
							        
								         <div class="m-response-headers">
					    						
					    						<ul>
					    							<li>key=value</li>
					    						</ul>
					    						
					    				</div>
							          
							        </div>
							      </div>
							    </div>
							    
							    <div class="layui-col-md6">
							      <div class="layui-card">
							        <div class="layui-card-header">
							        		<span>响应body</span>
								        	<div class="layui-inline"  style="position: absolute;right: 10px; top: 5px;display: none;">
								        		<select  lay-filter="respBodyType" >
												  <option value="text">原始内容</option>
												  <option value="file">解码内容</option>
												</select>  
								        	</div>
							        </div>
							        <div class="layui-card-body">
							          
							          <div class="m-response-body">
							          		
							          		<div>
						    					<textarea  placeholder="显示内容" rows="20" class="layui-textarea  response-body-area"></textarea>
							          		</div>
						    				
						    		  </div>
							          
							        </div>
							      </div>
							    </div>
							    
							  </div>
							</div> 
			    			
			    		</div>
			    		<!-- /响应区域 -->
	    			
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
  
   // 请求类型选择
  form.on('select(requestBodyType)', function(data){
	 $(".m-request-body-content").find("."+data.value).show().siblings().hide();
  });
   
   // 响应内容
  form.on('select(respBodyType)', function(data){
	  
  });
   
  // 请求方式
  form.on('select(reqMethod)', function(data){
	  
	  if(data.value === "get") {
		  $('.m-request-body-content').hide();
	  } else if(data.value === "post") {
		  $('.m-request-body-content').show();
	  } else if(data.value === "put") {
		  $('.m-request-body-content').show();
	  } else if(data.value === "delete") {
		  $('.m-request-body-content').hide();
	  }
	  
  });
   
   
   
  
  
	//选完文件后不自动上传
	upload.render({
	    elem: '#upload-file'
	    ,auto: false
	    ,accept: 'file' //普通文件
	    ,done: function(res){
	      console.log(res)
	    },
	    choose:function(obj) {
	    	console.log(obj);
	    }
	  });
	
	
	// 绑定点击事件 
	$(".bodyContentType a").click(function() {
		
		var type = $(this).html();
		
		// 选项控制
		if(!$(this).hasClass('active')) {
			$(this).addClass('active').siblings().removeClass('active');
		}
		
		// 添加指定头
		bodyChangeHeaders(type);
		
	});
	
	
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


// 请求头控制
function addHeaders() {
	var htm = "";
	
	htm += '<li  class="req-headers-item">';
	htm += '    <div class="layui-form-item">';
	htm += '      <div class="layui-inline">';
	htm += '       <div  class="layui-input-inline"  style="height: 38px; width: 30px;">';
	htm += '          <input type="checkbox" name="isHeadersChecked" lay-skin="primary"  lay-filter="isHeadersChecked"  checked=true>';
	htm += '         </div>';
	htm += '        <div class="layui-input-inline" style="width: 140px;" >';
	htm += '          <input type="text" name="key" placeholder="key" autocomplete="off" class="layui-input  key">';
	htm += '        </div>';
	htm += '        <div class="layui-form-mid">=</div>';
	htm += '        <div class="layui-input-inline"  style="width: 240px;" >';
	htm += '          <input type="text" name="value" placeholder="value" autocomplete="off" class="layui-input  value">';
	htm += '        </div>';
	htm += '        <div class="layui-input-inline" style="width: 60px;">';
	htm += '          <button class="layui-btn  layui-btn-danger"  onclick="deleteHeaders(this)" ><i class="layui-icon layui-icon-delete"></i></button>';
	htm += '        </div>';
	htm += '      </div>';
	htm += '  </div>';
	htm += '</li>';
	
	$(".req-headers").append(htm);
	
	
	form.render('checkbox');
}

function bodyChangeHeaders(type) {
	
	var contentType = "Content-Type";
	
	var contentTypeValue = "text/plain";
	
	if(type === "text") {
		contentTypeValue="text/plain";
	} else if(type === "json") {
		contentTypeValue="application/json";
	} else if(type === "xml") {
		contentTypeValue="application/xml";
	} else if(type === "html") {
		contentTypeValue="text/html";
	}
	
	// 查询req-headers中是否有第一项
	if($('.req-headers-item') && $('.req-headers-item').length > 0) {
		// 修改第一项中数据
		var one = $('.req-headers-item')[0];
		
		$(one).find('input[name=key]').val(contentType);
		
		$(one).find('input[name=value]').val(contentTypeValue);
		
		
	} else {
		// 添加数据
		
		var htm = "";
		
		htm += '<li  class="req-headers-item">';
		htm += '    <div class="layui-form-item">';
		htm += '      <div class="layui-inline">';
		htm += '       <div  class="layui-input-inline"  style="height: 38px; width: 30px;">';
		htm += '          <input type="checkbox" name="isHeadersChecked" lay-skin="primary"  lay-filter="isHeadersChecked"  checked=true>';
		htm += '         </div>';
		htm += '        <div class="layui-input-inline" style="width: 140px;" >';
		htm += '          <input type="text" name="key" placeholder="key" autocomplete="off" class="layui-input  key" value="'+contentType+'"  >';
		htm += '        </div>';
		htm += '        <div class="layui-form-mid">=</div>';
		htm += '        <div class="layui-input-inline"  style="width: 240px;" >';
		htm += '          <input type="text" name="value" placeholder="value" autocomplete="off" class="layui-input  value"  value="'+contentTypeValue+'" >';
		htm += '        </div>';
		htm += '        <div class="layui-input-inline" style="width: 60px;">';
		htm += '          <button class="layui-btn  layui-btn-danger"  onclick="deleteHeaders(this)" ><i class="layui-icon layui-icon-delete"></i></button>';
		htm += '        </div>';
		htm += '      </div>';
		htm += '  </div>';
		htm += '</li>';
		
		$(".req-headers").append(htm);
		
		form.render('checkbox');
		
	}
}

function deleteAllHeaders() {
	$(".req-headers").html("");
}

function deleteHeaders(ele) {
	$(ele).parents(".req-headers-item").remove();
}


// 请求body操作
function addRequestBody() {
	var htm = "";
	
	htm += '<li class="request-body-item" >';
	htm += '    <div class="layui-form-item">';
	htm += '      <div class="layui-inline">';
	htm += '       <div  class="layui-input-inline"  style="height: 38px; width: 30px;">';
	htm += '          <input type="checkbox" name="isRequestBodyChecked" lay-filter="isRequestBodyChecked" lay-skin="primary"  checked="true">';
	htm += '         </div>';
	htm += '        <div class="layui-input-inline" style="width: 140px;" >';
	htm += '          <input type="text" name="key" placeholder="key" autocomplete="off" class="layui-input  key">';
	htm += '        </div>';
	htm += '        <div class="layui-form-mid">=</div>';
	htm += '        <div class="layui-input-inline"  style="width: 240px;" >';
	htm += '          <input type="text" name="value" placeholder="value" autocomplete="off" class="layui-input  value">';
	htm += '        </div>';
	htm += '        <div class="layui-input-inline" style="width: 60px;">';
	htm += '          <button class="layui-btn  layui-btn-danger"  onclick="deleteRequestBody(this)" >';
	htm += '            <i class="layui-icon layui-icon-delete"></i>';
	htm += '          </button>';
	htm += '        </div>';
	htm += '      </div>';
	htm += '  </div>';
	htm += '</li>';
	
	$(".request-body").append(htm);
	
	form.render('checkbox');
}

function deleteAllRequestBody() {
	$(".request-body").html("");
}

function deleteRequestBody(ele) {
	$(ele).parents(".request-body-item").remove();
}


function checkStatus(response) {
	  if (response.status >= 200 && response.status < 300) {
	    return response
	  } else {
	    var error = new Error(response.statusText)
	    error.response = response
	    throw error
	  }
}

function parseJSON(response) {
  return response.json()
}



// 发送请求
function requestAction() {
	
	var datas = {};
	
	// 开始判断
	var deviceClient = $(".deviceClient").val();
	var requestUrl = $("#request-url").val();
	var appVersion = $(".appVersion").val();
	var deviceUdid = $(".deviceUdid").val();
	var reqMethod = $(".reqMethod").val();
	var requestBody = "";
	
	var requestBodyType = $(".requestBodyType").val();
	
	if(requestBodyType === "text") {
		// 获取内容
		requestBody = $(".m-request-body-content > .text > .requestBody").val();
	} else if(requestBodyType === "file") {
		// 暂时不处理
	} else if(requestBodyType === "form") {
		// 暂时不处理
	} 
	
	var params = {};
	// 获取请求参数
	$(".m-parameter  .url-param .url-param-item").each(function(){
		
		if($(this).find('input[name=isParamChecked]').is(":checked")) {
			
			var key = $(this).find('.key').val();
			var value = $(this).find('.value').val();
			
			if(key && value) {
				params[key] = value;
			}
			
		}
		
	});
	
	var headers = {};
	
	// 获取请求头
	$(".m-header-and-body .req-headers .req-headers-item").each(function(){
		if($(this).find('input[name=isHeadersChecked]').is(":checked")) {
			
			var key = $(this).find('.key').val();
			var value = $(this).find('.value').val();
			
			if(key && value) {
				headers[key] = value;
			}
			
		}
	});
	
	if(deviceClient === "") {
		layer.msg("请求选择设备类型");
		return;
	}
	
	if(appVersion === "") {
		layer.msg("请选择app版本");
		return;
	}
	
	
	// 设置参数
	datas['method'] = reqMethod;
	datas['requestURL'] = requestUrl;
	datas['headers'] = headers;
	datas['params'] = params;
	datas['deviceUdid'] = deviceUdid;
	datas['deviceClient'] = deviceClient;
	datas['appVersion'] = appVersion;
	datas['requestBody'] = requestBody;
	
	
	$.ajax({
		url: ctx + "/sandbox/app/request",
		type: "post",
		data: datas,
		dataType: "json",
	}).success(function(resp){
		
		if(resp.errcode === "0") {
			
			$(".response-body-area").val(resp.data.decodeResult);
			
		} else if(resp.errcode === "1") {
			layer.msg(resp.errmsg);
		} else if(resp.errcode === "-1") {
			
		}
		
	});
	
	
	
	
}





</script>
</body>
</html>