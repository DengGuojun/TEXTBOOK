<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.lpmas.framework.bean.*"%>
<%@ page import="com.lpmas.admin.business.*"%>
<%@ page import="com.lpmas.textbook.textbook.config.*"%>
<% 
	AdminUserHelper adminUserHelper = (AdminUserHelper)request.getAttribute("AdminUserHelper");
%>
<%@ include file="../../include/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>专业选择</title>
	<link href="<%=STATIC_URL %>/css/main.css" type="text/css" rel="stylesheet" />
	<link href="<%=STATIC_URL%>/js/My97DatePicker/skin/WdatePicker.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="/js/ckeditor/ckeditor.js"></script>
	<script type='text/javascript'src="<%=STATIC_URL%>/js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL %>/js/jquery.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL %>/js/common.js"></script>
</head>
<body class="article_bg">
<p>
 <p class="p_top_border">
	      <em class="int_label">图书分类：</em>
	      <select class="form-control" name="TEXTBOOK_CLASS" id="TEXTBOOK_CLASS">
			<option value="">请选择</option>
			<%for(StatusBean<String,String> statusBean : TextbookInfoConfig.TXT_CLASS_LIST){ %>
			<option value="<%=statusBean.getStatus()%>"><%=statusBean.getValue() %></option>
			<%} %>
			</select>
	    </p>
 <div class="div_center">
	<input type="submit" name="button" id="button" class="modifysubbtn" value="选择" onclick="callbackTo()" />
 </div>

</body>
<script type="text/javascript">
function callbackTo(){
	var textbookClass = $("#TEXTBOOK_CLASS option:selected").val();
	self.parent.selectCatalog(textbookClass);
	try{ self.parent.jQuery.fancybox.close(); }catch(e){console.log(e);}
}
</script>
</html>