<!-- 定义常量 使用为 components.ctx ctx使用  constant.ftl文件中的变量  -->
<#assign ctx = springMacroRequestContext.getContextPath() >

<#-- 定义一些常用的宏 -->
<#macro header  authjQuery=false >
<!-- 头部区域 -->
  <div class="layui-header">
    <div class="layui-logo">平台后台</div>
    <!-- 头部区域（可配合layui已有的水平导航） -->
    <ul class="layui-nav layui-layout-left">
      <li class="layui-nav-item  layui-nav-itemed"><a href="">控制台</a></li>
      
      <!-- 
      <li class="layui-nav-item"><a href="">商品管理</a></li>
      <li class="layui-nav-item"><a href="">用户</a></li>
      <li class="layui-nav-item">
        <a href="javascript:;">其它系统</a>
        <dl class="layui-nav-child">
          <dd><a href="">邮件管理</a></dd>
          <dd><a href="">消息管理</a></dd>
          <dd><a href="">授权管理</a></dd>
        </dl>
      </li>
       -->
      
    </ul>
    
    <ul class="layui-nav layui-layout-right">
      <li class="layui-nav-item">
        <a href="javascript:;">
          <!-- 
          <img src="http://t.cn/RCzsdCq" class="layui-nav-img">
           -->
          	用户
        </a>
        <dl class="layui-nav-child">
          <dd><a href="">基本资料</a></dd>
          <!-- 
          <dd><a href="">安全设置</a></dd>
           -->
        </dl>
      </li>
      <li class="layui-nav-item">
      	<a href="${ctx}/u_logout.do">退出</a>
      </li>
    </ul>
    
    
  </div>
  <!-- /头部区域 -->
  
  <#if authjQuery >
  <script src="${ctx}/static/lib/jquery/jquery-1.12.4/jquery.min.js"></script>
  </#if>
  
</#macro>

<#macro menu_aside >

<!-- 侧边菜单区域 -->
  <div class="layui-side layui-bg-black">
    <div class="layui-side-scroll  g-side-scroll">
      <!-- 内容区域 -->
      <ul class="layui-nav layui-nav-tree"  lay-filter="test">
      	<!-- layui-nav-itemed -->
        
        <li class="layui-nav-item">
          <a class="" href="javascript:;">用户管理</a>
          <dl class="layui-nav-child">
            <dd><a href="/user/account/index.do">账号管理</a></dd>
            <dd><a href="/sys/user/index.do">账户管理</a></dd>
          </dl>
        </li>
        
        <li class="layui-nav-item">
          <a class="" href="javascript:;">权限管理</a>
          <dl class="layui-nav-child">
            <dd><a href="/sys/role/index.do">角色管理</a></dd>
            <dd><a href="/sys/permission/index.do">权限管理</a></dd>
            <dd><a href="/sys/menu/index.do">菜单管理</a></dd>
            <dd><a href="/sys/operation/index.do">操作管理</a></dd>
          </dl>
        </li>
        
        <li class="layui-nav-item">
          <a class="" href="javascript:;">系统管理</a>
          <dl class="layui-nav-child">
            <dd><a href="${ctx}/dict/category/index.do">数据字典分类</a></dd>
            <dd><a href="${ctx}/dict/type/index.do">数据字典管理</a></dd>
            <dd><a href="${ctx}/file/storage/index.do">文件管理</a></dd>
          </dl>
        </li>
        
        <li class="layui-nav-item">
          <a class="" href="javascript:;">系统监控</a>
          <dl class="layui-nav-child">
            <dd><a href="${ctx}/home/druid.do">数据库状态</a></dd>
            <dd><a href="${ctx}/home/swagger.do">接口文档</a></dd>
          </dl>
        </li>
        
        <li class="layui-nav-item">
          <a href="javascript:;">系统维护</a>
          <dl class="layui-nav-child">
            <dd><a href="${ctx}/sys/loginlog/index.do">系统登录log</a></dd>
            <dd><a href="${ctx}/sys/errorlog/index.do">系统错误log</a></dd>
          </dl>
        </li>
        
        <!-- 
        <li class="layui-nav-item">
          <a href="javascript:;">解决方案</a>
          <dl class="layui-nav-child">
            <dd><a href="javascript:;">列表一</a></dd>
            <dd><a href="javascript:;">列表二</a></dd>
            <dd><a href="">超链接</a></dd>
          </dl>
        </li>
        <li class="layui-nav-item"><a href="">云市场</a></li>
        <li class="layui-nav-item"><a href="">发布商品</a></li>
         -->
         
      </ul>
      <!-- 内容区域 -->
      
    </div>
  </div>
  <!-- /侧边菜单区域 -->

</#macro>

<#macro footer  myDomain="iveiv.com"  >

  <!-- 底部固定区域 -->
  <div class="layui-footer">
    © ${myDomain} - 底部提示
  </div>
  <!-- /底部固定区域 -->
  <!-- 
  <#nested>
   -->

</#macro>

<#-- js 常量区 -->
<script type="text/javascript"  >
	const c11tx = "11111";
</script>