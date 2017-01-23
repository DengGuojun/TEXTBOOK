<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	if (bean != null && bean.getCatalogId() > 0) {
%>
<div>
	<p class="info_nav">
		销售目录ID：<%=bean.getCatalogId()%>
	</p>
	<p class="info_nav">
		销售目录名称：<%=bean.getCatalogName()%>
	</p>
</div>
<%
	}
%>
<p class="tab">
	<a href="CatalogInfoManage.do?catalogId=<%=bean.getCatalogId()%>">专业</a> <a href="CatalogTemplateManage.do?catalogId=<%=bean.getCatalogId()%>">目录模版</a> 
</p>
<script language="javascript">
	tabChange('.tab', 'a');
</script>
