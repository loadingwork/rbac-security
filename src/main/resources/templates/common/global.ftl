<#-- freemarker 常量区 -->
<#-- springboot 暴露变量 springMacroRequestContext  -->
<#assign ctx = springMacroRequestContext.getContextPath() >
<#assign locale = springMacroRequestContext.locale >

<!-- 
<#setting datetime_format="yyyy-MM-dd HH:mm:ss"/>
 -->
 <#-- 
 在页面中使用${prop??}进行判断，或使用${prop?""}，若为空则显示
 TemplateExceptionHandler   template_exception_handler=xxxx.
FreeMarkerExceptionHandler
  -->

<!-- 全局引入jq -->
<script src="${ctx}/static/lib/jquery/jquery-1.12.4/jquery.min.js"></script>
<#-- js 常量区 -->
<script type="text/javascript"  >
	const ctx = "${ctx}";
</script>


<#-- 定义宏 -->
<!-- 引入js -->
<#macro script src="" pattern=true>
	<#if pattern==true>
		<script type="text/javascript" src="${springMacroRequestContext.getContextUrl('/static${src}')}"></script>
		<#else>
		<script type="text/javascript" src="${springMacroRequestContext.getContextUrl(src)!''}"></script>
	</#if>
</#macro>

<!-- 引入css -->
<#macro link href="" pattern=true>
	<#if pattern==true>
		<link rel="stylesheet" type="text/css" href="${springMacroRequestContext.getContextUrl('/static${href}')}" />
		<#else>
		<link rel="stylesheet" type="text/css" href="${springMacroRequestContext.getContextUrl(href)!''}" />
	</#if>
</#macro>


