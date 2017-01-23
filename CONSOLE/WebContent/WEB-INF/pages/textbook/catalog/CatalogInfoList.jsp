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
	AdminUserHelper adminUserHelper = (AdminUserHelper)request.getAttribute("AdminUserHelper");
	List<CatalogInfoBean> list = (List<CatalogInfoBean>)request.getAttribute("CatalogInfoList");
	PageBean PAGE_BEAN = (PageBean)request.getAttribute("PageResult");
	List<String[]> COND_LIST = (List<String[]>)request.getAttribute("CondList");
	int parentCatalogId = (int)request.getAttribute("parentCatalogId");
	if(parentCatalogId == 0)parentCatalogId = 1;
	HashMap<Integer, String> parentNameMap = (HashMap<Integer,String>)request.getAttribute("parentNameMap");
	List<CatalogInfoBean> navigationList = (List<CatalogInfoBean>)request.getAttribute("navigationBar");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>出版社管理</title>
<%@ include file="../../include/header.jsp"%>
</head>
<body class="article_bg">
	<div class="article_tit">
		<ul class="art_nav">
			<li><a href="/catalog/CatalogInfoList.do"><p class="article_tit" style="border-bottom:#e0e0e0 ;">专业分类列表</p></a></li>
			<li>
			<%for(CatalogInfoBean typeBean : navigationList) {%>
			&nbsp;>&nbsp;<a href="/catalog/CatalogInfoList.do?catalogId=<%=typeBean.getCatalogId() %>"><%=typeBean.getCatalogName() %></a>
			<%} %>
			</li>
		</ul>
	</div>
	<form name="formSearch" method="post" action="/catalog/CatalogInfoList.do">
		<div class="search_form">
		<%if(adminUserHelper.hasPermission(CatalogConsoleResource.CATALOG_INFO, OperationConfig.SEARCH)){ %>
			<em class="em1">名称：</em>
			<input type="text" name="catalogName" id="catalogName" value="<%=ParamKit.getParameter(request, "catalogName", "")%>" size="20"/>
			<select id="parentCatalogId" name="parentCatalogId">
    		<option value=""></option>
    		<option value="">全局</option>
    		<option value="<%=(list.size()>0)?list.get(0).getParentCatalogId():""%>">当前父类</option>
    		</select>
			<input name="" type="submit" class="search_btn_sub" value="查询"/>
		<%} %>
		</div>
		
	  <table width="100%" border="0"  cellpadding="0" class="table_style">
    <tr>
      <th>序号</th>
      <th>名称</th>
      <th>排序</th>
      <th>父类</th>
      <th>说明</th>
      <th>更新时间</th>
      <th>操作</th>
    </tr>
     <%
    for(CatalogInfoBean bean:list){%> 
    <tr>
      <td><%=bean.getCatalogId() %></td>
      <td><%=bean.getCatalogName() %></td>
      <td><%=bean.getPriority() %></td>
      <td><%=parentNameMap.get(bean.getParentCatalogId())  %></td>
      <td><%=bean.getMemo()%></td>
      <td><%=bean.getModifyTime() !=null ? DateKit.formatTimestamp(bean.getModifyTime(), DateKit.DEFAULT_DATE_TIME_FORMAT) : DateKit.formatTimestamp(bean.getCreateTime(), DateKit.DEFAULT_DATE_TIME_FORMAT) %></td>
      <td align="center">
      	<%if(adminUserHelper.hasPermission(CatalogConsoleResource.CATALOG_INFO, OperationConfig.UPDATE)){ %>
      	<a href="/catalog/CatalogInfoManage.do?catalogId=<%=bean.getCatalogId()%>">编辑</a>
      	<%} %>
      	<%if((navigationList.size()<2)&&("".equals(ParamKit.getParameter(request, "catalogName", ""))||StringKit.isValid(ParamKit.getParameter(request, "parentCatalogId", "")))) {%>
      	<a href="/catalog/CatalogInfoList.do?catalogId=<%=bean.getCatalogId()%>">查看子分类</a>
      	
      	<% }%>
      </td>
    </tr>	
    <%} %>
    
   </table>
	</form>
	
<ul class="page_info">
<li class="page_left_btn">
	<%if(adminUserHelper.hasPermission(CatalogConsoleResource.CATALOG_INFO, OperationConfig.CREATE)&&("".equals(ParamKit.getParameter(request, "catalogName", "")))){ %>
  	<input type="button" name="button" id="button" value="新建" onclick=javascript:location.href='CatalogInfoManage.do'+'?parentCatalogId='+<%=parentCatalogId %>+''>
  	<%} %>
</li>
<%@ include file="../../include/page.jsp" %>
</ul>

</body>
</html>