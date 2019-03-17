<!doctype html>
<html  class="x-admin-sm">
<head>
	<meta charset="UTF-8">
	<title>后台登录-adminV3</title>
	<meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" >
    <meta name="viewport" content="width=device-width, initial-scale=1.0" >
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    
    <@link href="/css/font.css"  />
    <@link href="/css/xadmin.css"  />
</head>
<body class="login-bg">
    
    <div class="login layui-anim layui-anim-up">
        <div class="message">管理员登录</div>
        <div id="darkbannerwrap"></div>
        
        <form method="post" class="layui-form"  action="${ctx}/j_login.do" >
            <input name="username"   type="text"  lay-verify="required" class="layui-input" placeholder="用户名" />
            <hr class="hr15">
            <input name="password"   type="password"  lay-verify="required"   class="layui-input" placeholder="密码" />
            <hr class="hr15">
            <input value="登录" lay-submit lay-filter="login" style="width:100%;" type="submit" />
            <hr class="hr20" >
        </form>
    </div>

	<@script src="/lib/layui/layui.js" />
	<@script src="/js/cookie.js" />
	<@script src="/js/xadmin.js" />
    <script>
    
    	var errmsg = '${errmsg}' || '';
    
        $(function  () {
            layui.use(['form', 'layer'], function(){
              var form = layui.form;
              var layer = layui.layer;
              
              if(errmsg != '') {
            	  layer.msg(errmsg, {
            		  time: 3000
            	  });
              }
              
            });
        });

        
    </script>
    <!-- 底部结束 -->
</body>
</html>