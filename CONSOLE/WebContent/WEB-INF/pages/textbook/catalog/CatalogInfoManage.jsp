<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.lpmas.framework.util.*"%>
<%@ page import="com.lpmas.framework.config.*"%>
<%@ page import="com.lpmas.framework.bean.*"%>
<%@ page import="com.lpmas.framework.web.*"%>
<%@ page import="com.lpmas.framework.page.*"%>
<%@ page import="com.lpmas.admin.business.*"%>
<%@ page import="com.lpmas.admin.config.*"%>
<%@ page import="com.lpmas.system.bean.*"%>
<%@ page import="com.lpmas.textbook.catalog.bean.*"%>
<%@ page import="com.lpmas.textbook.console.catalog.config.*"%>

<%
	AdminUserHelper adminUserHelper = (AdminUserHelper) request.getAttribute("AdminUserHelper");
	CatalogInfoBean bean = (CatalogInfoBean) request.getAttribute("catalogInfo");
	CatalogInfoBean firstParentBean = (CatalogInfoBean) request.getAttribute("firstParentBean");
	CatalogInfoBean secondParentBean = (CatalogInfoBean) request.getAttribute("secondParentBean");
%>
<%@ include file="../../include/header.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>节目用途</title>
	<link href="<%=STATIC_URL %>/css/main.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript" src="<%=STATIC_URL %>/js/jquery.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL %>/js/common.js"></script>
</head>
<body class="article_bg">
<div class="article_tit">
		<ul class="art_nav">
			<li><a href="CatalogInfoList.do">专业分类</a>&nbsp;>&nbsp;</li>
			<%
				if (bean.getCatalogId() > 0) {
			%>
			<li>修改节目分类</li>
			<%
				} else {
			%>
			<li>添加节目分类</li>
			<%
				}
			%>
		</ul>
	</div>
	<form id="formData" name="formData" method="post" action="CatalogInfoManage.do" onsubmit="javascript:return checkSubmit();">
		<input type="hidden" name="catalogId" id="catalogId" value="<%=bean.getCatalogId()%>" /> 
		<input type="hidden" name="status" id="status" value="<%=bean.getStatus()%>" />
		<div class="modify_form">
			<p>
				<em class="int_label"><span>*</span>分类名称：</em> 
				<input type="text" name="catalogName" id="catalogName" size="30" maxlength="20" value="<%=bean.getCatalogName()%>" checkStr="节目分类名称;txt;true;;100" />
			</p>
			<p>
				<em class="int_label"><span>*</span>父类：</em>

				<input value="<%=firstParentBean.getCatalogName()%>"disabled="disabled" size="14">
				<input id="secondParent" type="text" value="<%=secondParentBean.getCatalogName() %>" disabled="disabled" size="13">
						

			<input type="hidden" id="parentCatalogId" name="parentCatalogId" value="<%=bean.getParentCatalogId()%>">
			<p class="p_top_border">
				<em class="int_label">说明：</em>
				<textarea name="memo" id="memo" cols="29" rows="3"><%=bean.getMemo()%></textarea>
			</p>

			<p class="p_top_border">
				<em class="int_label">排序：</em> <input type="text" name="priority"
					id="priority" size="5" maxlength="5"
					value="<%=bean.getPriority()%>" checkStr="排序;num;true;;10" />
			</p>
		</div>
		<div class="div_center">
			<input type="submit" name="submit" id="program_type_save_btn" class="modifysubbtn" value="提交" /> 
			<input type="button" name="cancel" id="cancel" value="取消" onclick="javascript:history.back()">
		</div>
	</form>
	<%
		if ((bean.getCatalogId() > 0) && adminUserHelper.checkPermission(CatalogConsoleResource.CATALOG_INFO, OperationConfig.REMOVE)) {
	%>
	<!-- 删除表单 -->
	<form id="formData2" name="formData2" method="post"
		action="catalogInfoManage.do">
		<div style="text-align: right">
			<input type="hidden" name="catalogId" id="catalogId" value="<%=bean.getCatalogId()%>" />
			 <input type="hidden" name="status" id="status" value="<%=Constants.STATUS_NOT_VALID%>" /> 
			 <input type="hidden" name="parentCatalogId" value="<%=bean.getParentCatalogId()%>">
			 <input type="submit" name="remove" id="remove" class="modifysubbtn" value="删除" />
		</div>
	</form>
	<%
		}
	%>
</body>
<script type="text/javascript">

	function checkSubmit(){
		var firstParentId = <%=firstParentBean.getCatalogId() %>
		var secondParentId = <%=secondParentBean.getCatalogId() %>
		//判断取哪个父类id
		if (secondParentTypeId != 0) {
			document.getElementById("parentCatalogId").value = secondParentId;
		} else {
			document.getElementById("parentCatalogId").value = firstParentId;
		}
		return true;
	}
</script>
</html>