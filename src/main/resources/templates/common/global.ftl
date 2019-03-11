<#-- freemarker 常量区 -->
<#assign ctx = springMacroRequestContext.getContextPath() >

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
