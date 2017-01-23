<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.lpmas.framework.util.*"%>
<%@ page import="com.lpmas.framework.web.*"%>
<%@ page import="com.lpmas.framework.page.*"%>
<%@ page import="com.lpmas.admin.business.*"%>
<%@ page import="com.lpmas.admin.config.*"%>
<%@ page import="com.lpmas.textbook.textbook.bean.*"%>
<%@ page import="com.lpmas.textbook.textbook.config.*"%>
<%@ page import="com.lpmas.textbook.console.textbook.config.*"%>
<%
	AdminUserHelper adminUserHelper = (AdminUserHelper)request.getAttribute("AdminUserHelper");
	List<ArticleInfoBean> list = (List<ArticleInfoBean>)request.getAttribute("ArticleInfoList");
	PageBean PAGE_BEAN = (PageBean)request.getAttribute("PageResult");
	List<String[]> COND_LIST = (List<String[]>)request.getAttribute("CondList");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>文章管理</title>
<%@ include file="../../include/header.jsp"%>
</head>
<body class="article_bg">
	<p class="article_tit">文章列表</p>
	<form name="formSearch" method="post" action="/textbook/ArticleInfoList.do">
		<div class="search_form">
		<%if(adminUserHelper.hasPermission(TextbookResource.ARTICLE_INFO, OperationConfig.SEARCH)){ %>
			<em class="em1">标题：</em>
			<input type="text" name=articleTitle id="articleTitle" value="<%=ParamKit.getParameter(request, "articleTitle", "")%>" size="20"/>
			<input name="" type="submit" class="search_btn_sub" value="查询"/>
		<%} %>
		</div>
		
	 <table width="100%" border="0" cellpadding="0" class="table_style">
	 	<tr>
	 		<th>标题</th>
	 		<th>状态</th>
	 		<th>新增时间</th>
	 		<th>操作</th>
	 	</tr>
	 	<%
	 	for(ArticleInfoBean bean : list){
	 	%>
	 		<tr>
	 			<td><%=bean.getArticleTitle() %></td>
	 			<td><%=MapKit.getValueFromMap(bean.getPublishStatus(), ArticleInfoConfig.PUBLISH_STATUS_MAP) %></td>
	 			<td><%=DateKit.formatDate(bean.getCreateTime(), DateKit.DEFAULT_DATE_FORMAT)%></td>
	 			<td align="center">
	 				<%if(adminUserHelper.hasPermission(TextbookResource.ARTICLE_INFO, OperationConfig.SEARCH)){ %>
	 					<a href="/textbook/ArticleInfoManage.do?ArticleId=<%=bean.getArticleId()%>">编辑</a>
	 					<a href="/textbook/ArticleInfoManage.do?ArticleId=<%=bean.getArticleId()%>&DELETE=1">删除</a>
	 				<%} %>
	 			</td>
	 		</tr>
	 	<%} %>
	 </table>
	</form>
	
<ul class="page_info">
<li class="page_left_btn">
	<%if(adminUserHelper.hasPermission(TextbookResource.ARTICLE_INFO, OperationConfig.CREATE)){ %>
  	<input type="button" name="button" id="button" value="新建" onclick="javascript:location.href='ArticleInfoManage.do'">
  	<%} %>
</li>
<%@ include file="../../include/page.jsp" %>
</ul>

</body>
</html>