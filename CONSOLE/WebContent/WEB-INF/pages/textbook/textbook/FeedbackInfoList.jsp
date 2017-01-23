<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.lpmas.framework.util.*"%>
<%@ page import="com.lpmas.framework.page.*"%>
<%@ page import="com.lpmas.admin.business.*"%>
<%@ page import="com.lpmas.textbook.textbook.bean.*"%>
<%
	AdminUserHelper adminUserHelper = (AdminUserHelper)request.getAttribute("AdminUserHelper");
	List<FeedbackInfoBean> list = (List<FeedbackInfoBean>)request.getAttribute("FeedbackInfoList");
	PageBean PAGE_BEAN = (PageBean)request.getAttribute("PageResult");
	List<String[]> COND_LIST = (List<String[]>)request.getAttribute("CondList");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>反馈图书</title>
<%@ include file="../../include/header.jsp"%>
</head>
<body class="article_bg">
	<p class="article_tit">反馈图书</p>
	 <table width="100%" border="0" cellpadding="0" class="table_style">
	 	<tr>
	 		<th>图书名</th>
	 		<th>联系方式</th>
	 		<th>时间</th>
	 	</tr>
	 	<%
	 	for(FeedbackInfoBean bean : list){
	 	%>
	 		<tr>
	 			<td><%=bean.getTextbookName() %></td>
	 			<td><%=bean.getContactInfo() %></td>
	 			<td><%=DateKit.formatDate(bean.getCreateTime(), DateKit.DEFAULT_DATE_FORMAT)%></td>
	 		</tr>
	 	<%} %>
	 </table>
	
<ul class="page_info">
<%@ include file="../../include/page.jsp" %>
</ul>

</body>
</html>