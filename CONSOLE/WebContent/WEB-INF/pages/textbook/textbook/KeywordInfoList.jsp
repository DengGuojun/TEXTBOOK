<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.lpmas.framework.web.*"%>
<%@ page import="com.lpmas.framework.page.*"%>
<%@ page import="com.lpmas.admin.business.*"%>
<%@ page import="com.lpmas.admin.config.*"%>
<%@ page import="com.lpmas.textbook.textbook.bean.*"%>
<%@ page import="com.lpmas.textbook.console.textbook.config.*"%>

<%
	AdminUserHelper adminUserHelper = (AdminUserHelper)request.getAttribute("AdminUserHelper");
	List<KeywordInfoBean> list = (List<KeywordInfoBean>)request.getAttribute("keywordInfoList");
	PageBean PAGE_BEAN = (PageBean)request.getAttribute("PageResult");
	List<String[]> COND_LIST = (List<String[]>)request.getAttribute("CondList");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>热搜词管理</title>
<%@ include file="../../include/header.jsp"%>
</head>
<body class="article_bg">
	<p class="article_tit">热搜词列表</p>
	<form name="formSearch" method="post" action="/textbook/KeywordInfoList.do">
		<div class="search_form">
		<%if(adminUserHelper.hasPermission(TextbookResource.TEXTBOOK_KEYWORD, OperationConfig.SEARCH)){ %>
			<em class="em1">名称：</em>
			<input type="text" name="keyword" id="keyword" value="<%=ParamKit.getParameter(request, "keyword", "")%>" size="20"/>
			<input name="" type="submit" class="search_btn_sub" value="查询"/>
		<%} %>
		</div>
		
	 <table width="100%" border="0" cellpadding="0" class="table_style">
	 	<tr>
	 		<th>序号</th>
	 		<th>名称</th>
	 		<th>排序</th>
	 		<th>操作</th>
	 	</tr>
	 	<%
	 	for(KeywordInfoBean bean : list){
	 	%>
	 		<tr>
	 			<td><%=bean.getKeywordId() %></td>
	 			<td><%=bean.getKeyword() %></td>
	 			<td><%=bean.getPriority() %></td>
	 			<td align="center">
	 				<%if(adminUserHelper.hasPermission(TextbookResource.TEXTBOOK_PRESS, OperationConfig.SEARCH)){ %>
	 					<a href="/textbook/KeywordInfoManage.do?keywordId=<%=bean.getKeywordId()%>">编辑</a>
	 				<%} %>
	 			</td>
	 		</tr>
	 		
	 	<%} %>
	 
	 </table>
	</form>
	
<ul class="page_info">
<li class="page_left_btn">
	<%if(adminUserHelper.hasPermission(TextbookResource.TEXTBOOK_KEYWORD, OperationConfig.CREATE)){ %>
  	<input type="button" name="button" id="button" value="新建" onclick="javascript:location.href='KeywordInfoManage.do'">
  	<%} %>
</li>
<%@ include file="../../include/page.jsp" %>
</ul>

</body>
</html>