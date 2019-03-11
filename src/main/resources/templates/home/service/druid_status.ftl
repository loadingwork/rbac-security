<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <title>数据库连接池</title>
  <link rel="stylesheet" href="${ctx}/static/lib/layui/css/layui.css">
  <style type="text/css">
  
  .g-main {
  	width: 100%; 
  }
  
  #main-frame {
  	width: 100%; 
  	height: 100%;
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
    <div  clas="g-main" >
    
    	<iframe id="main-frame" src="${ctx}/druid/index.html" frameborder="0" ></iframe>
    
    </div>
    <!-- /内容主体区域 -->
  </div>
  
  <!-- 使用定义页脚的宏 -->
  <@components.footer  />
  
  
</div>
<script src="${ctx}/static/lib/layui/layui.js"></script>
<script type="text/javascript">
"use strict"
layui.use('element', function(){
  var element = layui.element;
  
});
</script>
</body>
</html>