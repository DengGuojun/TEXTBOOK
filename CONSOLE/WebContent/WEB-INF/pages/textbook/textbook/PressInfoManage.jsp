<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.lpmas.framework.config.*"%>
<%@ page import="com.lpmas.admin.business.*"%>
<%@ page import="com.lpmas.admin.config.*"%>
<%@ page import="com.lpmas.textbook.textbook.bean.*"%>
<%@ page import="com.lpmas.textbook.console.textbook.config.*"%>
<% 
	PressInfoBean bean = (PressInfoBean)request.getAttribute("PressInfo");
	AdminUserHelper adminUserHelper = (AdminUserHelper)request.getAttribute("AdminUserHelper");
%>
<%@ include file="../../include/header.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>出版社管理</title>
	<link href="<%=STATIC_URL %>/css/main.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript" src="<%=STATIC_URL %>/js/jquery.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL %>/js/common.js"></script>
</head>
<body class="article_bg">
<div class="article_tit">
		<a href="javascript:history.back()" ><img src="<%=STATIC_URL %>/images/back_forward.jpg"/></a> 
		<ul class="art_nav">
			<li><a href="PressInfoList.do">出版社列表</a>&nbsp;>&nbsp;</li>
			<% if(bean.getPressId() > 0) {%>
				<li>修改出版社</li>
			<%}else{ %>
				<li>新建出版社</li>
			<%}%>
		</ul>
	</div>
	<form id="formData" name="formData" method="post" action="PressInfoManage.do" >
	  <input type="hidden" name="pressId" id="pressId" value="<%=bean.getPressId() %>"/>
	  <input type="hidden" name="status" id="status" value="<%=bean.getStatus() %>"/>
	  <div class="modify_form">
	    <p>
	      <em class="int_label"><span>*</span>出版社名称：</em>
	      <input type="text" name="pressName" id="pressName" size="30" maxlength="20" value="<%=bean.getPressName() %>" checkStr="出版社名称;txt;true;;100"/>
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
	<% if((bean.getPressId() > 0) && adminUserHelper.checkPermission(TextbookResource.TEXTBOOK_PRESS, OperationConfig.REMOVE)) {%>
	<!-- 删除表单 -->
	<form id="formData" name="formData" method="post" action="PressInfoManage.do">
	<div style="text-align:right">
		<input type="hidden" name="pressId" id="pressId" value="<%=bean.getPressId() %>"/>
	  	<input type="hidden" name="status" id="status" value="<%=Constants.STATUS_NOT_VALID %>"/>
	  	<input type="submit" name="remove" id="remove" class="modifysubbtn" value="删除" />
	</div>
	</form>
	<%} %>

</body>
</html>