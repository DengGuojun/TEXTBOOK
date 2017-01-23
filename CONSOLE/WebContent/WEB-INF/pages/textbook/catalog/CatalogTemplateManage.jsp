<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.lpmas.framework.config.*"%>
<%@ page import="com.lpmas.admin.bean.*"%>
<%@ page import="com.lpmas.admin.business.*"%>
<%@ page import="com.lpmas.admin.config.*"%>
<%@ page import="com.lpmas.system.bean.*"%>
<%@	page import="com.lpmas.framework.bean.*"%>
<%@	page import="com.lpmas.framework.util.*"%>
<%@	page import="com.lpmas.constant.language.LanguageConfig"%>
<%@ page import="com.lpmas.textbook.catalog.bean.*"%>
<%@ page import="com.lpmas.textbook.console.catalog.config.*"%>
<%@ page import="com.lpmas.textbook.catalog.config.*"%>
<%
	AdminUserHelper adminHelper = (AdminUserHelper) request.getAttribute("AdminUserHelper");
	CatalogInfoBean bean = (CatalogInfoBean)request.getAttribute("CatalogInfo");
	Map<String,Integer> sectionTemplateMap = (Map<String,Integer>)request.getAttribute("CatalogTemplateListMap");
	Map<String,String> templateNameMap = (Map<String,String>)request.getAttribute("TemplateNameMap");
	
	int catalogId = bean.getCatalogId();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>目录模版管理</title>
<%@ include file="../../include/header.jsp"%>
<link rel="stylesheet"	href="<%=STATIC_URL%>/js/fancyBox/jquery.fancybox.css" type="text/css" media="screen" />
<script language="javascript"  src="<%=STATIC_URL%>/js/fancyBox/jquery.fancybox.js"></script>
<script type='text/javascript'>	
document.domain = "<%=DOMAIN %>";
function openTemplateWindow(fromInput) {
	jQuery.fancybox.open({
		href : '<%=TEMPLATE_URL%>/template/TemplateInfoList.do?fromTag=modal&callbackFun=callbackFun&fromInput='+fromInput+'&groupId='+$("#groupId").val(),
		type : 'iframe',
		width : 800,
		minHeight : 500,
		autoScale : false,
		scrolling : true
	});
}

function callbackFun(templateId, templateName, fromInput) {
	jQuery("#"+fromInput+"_templateId").val(templateId);
	jQuery("#"+fromInput+"_templateName").val(templateName);
}
	
function clearTemplate(templateName,templateId) {
	jQuery(templateId).val("0");
	jQuery(templateName).val("");
}
	
function pagePreview(templateId,templateType){
	window.open('/catalog/CatalogTemplatePreview.shtml?catalogId='+$('#catalogId').val()+'&templateId='+$(templateId).val()+'&language='+$('#language').val()+'&templateType='+$(templateType).val());
}	
</script>
</head>
<body class="article_bg">
<%@ include file="CatalogInfoNav.jsp" %>
	<p class="article_tit">目录模版管理</p>
	<form id="formData" name="formData" method="post" action="CatalogTemplateManage.do"	onsubmit="javascript:return checkForm('formData');">
		<input type="hidden" name="catalogId" id="catalogId" value="<%=bean.getCatalogId()%>" />
		<div class="search_form">
		</div>
		<div class="modify_form">
			<%
				for (StatusBean<String, String> typeBean : CatalogConfig.TEMPLATE_TYPE_LIST) {
					String templateType = typeBean.getStatus();
			%>
			<p>
				<em class="int_label"><%=typeBean.getValue()%>：</em> 
				<input type="hidden" name="<%=templateType %>_templateType" id="<%=typeBean.getStatus() %>_templateType" value="<%=templateType %>"/>
				<input type="text"	name="<%=templateType %>_templateName" id="<%=typeBean.getStatus() %>_templateName" size="50" readonly checkStr="模版;txt;false;;100" value="<%=MapKit.getValueFromMap(templateType, templateNameMap)%>"/> 
				<input type="hidden" name="<%=templateType %>_templateId" id="<%=typeBean.getStatus() %>_templateId" checkStr="模版;txt;false;;100" value="<%=MapKit.getValueFromMap(typeBean.getStatus(), sectionTemplateMap)%>" /> 
				<input id="<%=templateType %>" type="button" class="search_btn_group" value="浏览..."  onclick="javascript:openTemplateWindow('<%=typeBean.getStatus() %>')"/>   
				<input type="button" class="search_btn_group" value="清空" onclick="javascript:clearTemplate('#<%=templateType %>_templateName','#<%=templateType %>_templateId');" />
				
			</p>
			<%} %>
		</div>
		<div class="div_center">
			<%
				if (adminHelper.hasPermission(CatalogConsoleResource.CATALOG_TEMPLATE, OperationConfig.UPDATE)
						|| adminHelper.hasPermission(CatalogConsoleResource.CATALOG_TEMPLATE, OperationConfig.CREATE)) {
			%>
			<input type="submit" name="button" id="button" class="modifysubbtn" value="提交" /> 			
			<%} %>
			<input type="button" name="btnback" id="btnback" class="modifysubbtn" value="返回" onclick="window.history.back()" />
		</div>
	</form>
</body>
</html>