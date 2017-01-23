<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.lpmas.framework.config.*"%>
<%@ page import="com.lpmas.admin.business.*"%>
<%@ page import="com.lpmas.admin.config.*"%>
<%@ page import="com.lpmas.textbook.textbook.bean.*"%>
<%@ page import="com.lpmas.textbook.console.textbook.config.*"%>
<% 
	KeywordInfoBean bean = (KeywordInfoBean)request.getAttribute("keywordInfo");
	AdminUserHelper adminUserHelper = (AdminUserHelper)request.getAttribute("AdminUserHelper");
%>
<%@ include file="../../include/header.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>热搜词管理</title>
	<link href="<%=STATIC_URL %>/css/main.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript" src="<%=STATIC_URL %>/js/jquery.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL %>/js/common.js"></script>
</head>
<body class="article_bg">
<div class="article_tit">
		<a href="javascript:history.back()" ><img src="<%=STATIC_URL %>/images/back_forward.jpg"/></a> 
		<ul class="art_nav">
			<li><a href="KeywordInfoList.do">热搜词管理</a>&nbsp;>&nbsp;</li>
			<% if(bean.getKeywordId() > 0) {%>
				<li>修改</li>
			<%}else{ %>
				<li>新建</li>
			<%}%>
		</ul>
	</div>
	<form id="formData" name="formData" method="post" action="KeywordInfoManage.do" onsubmit="javascript:return checkForm('formData');">
	  <input type="hidden" name="keywordId" id="keywordId" value="<%=bean.getKeywordId() %>"/>
		<input type="hidden" name="status" id="status" value="<%=bean.getStatus() %>"/>
	  <div class="modify_form">
	    <p>
	      <em class="int_label"><span>*</span>名称：</em>
	      <input type="text" name="keyword" id="keyword" size="30" maxlength="20" value="<%=bean.getKeyword() %>" checkStr="名称;txt;true;;100"/>
	    </p>
	    <p class="p_top_border">
	      <em class="int_label">排序：</em>
	      <input type="text"  name="priority" id="priority" size="5" maxlength="5" value="<%=bean.getPriority() %>" checkStr="排序;num;true;;10"/>
	    </p>
	  </div>
	  <div class="div_center">
	  <input type="submit" name="submit" id="submit" class="modifysubbtn" value="提交" />
	  <input type="button" name="cancel" id="cancel" value="取消" onclick="javascript:history.back()">
	  </div>
	</form>
	<% if((bean.getKeywordId() > 0) && adminUserHelper.checkPermission(TextbookResource.TEXTBOOK_KEYWORD, OperationConfig.REMOVE)) {%>
	<!-- 删除表单 -->
	<form id="formData" name="formData" method="post" action="KeywordInfoManage.do">
	<div style="text-align:right">
		<input type="hidden" name="keywordId" id="keywordId" value="<%=bean.getKeywordId() %>"/>
	  	<input type="hidden" name="status" id="status" value="<%=Constants.STATUS_NOT_VALID %>"/>
	  	<input type="submit" name="remove" id="remove" class="modifysubbtn" value="删除" />
	</div>
	</form>
	<%} %>

</body>
</html>