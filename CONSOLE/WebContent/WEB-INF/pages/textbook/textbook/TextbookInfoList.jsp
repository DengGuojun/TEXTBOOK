<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.lpmas.framework.util.*"%>
<%@ page import="com.lpmas.framework.bean.*"%>
<%@ page import="com.lpmas.framework.web.*"%>
<%@ page import="com.lpmas.framework.page.*"%>
<%@ page import="com.lpmas.admin.config.*"%>
<%@ page import="com.lpmas.admin.business.*"%>
<%@ page import="com.lpmas.region.bean.*"%>
<%@ page import="com.lpmas.textbook.textbook.bean.*"%>
<%@ page import="com.lpmas.textbook.textbook.config.*"%>
<%@ page import="com.lpmas.textbook.console.config.*"%>
<%@ page import="com.lpmas.textbook.console.textbook.config.*"%>
<%
	AdminUserHelper adminUserHelper = (AdminUserHelper)request.getAttribute("AdminUserHelper");
	List<TextbookIndexBean> list = (List<TextbookIndexBean>)request.getAttribute("textbookInfoList");
	PageBean PAGE_BEAN = (PageBean)request.getAttribute("PageResult");
	List<String[]> COND_LIST = (List<String[]>)request.getAttribute("CondList");
	
	String serverPath = TextbookConsoleConfig.PORTAL_SERVER + "/t/";
	
	List<PressInfoBean> pressList = (ArrayList<PressInfoBean>) request.getAttribute("pressInfoList");
	List<ProvinceInfoBean> provinceList = (List<ProvinceInfoBean>)request.getAttribute("provinceList");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>图书管理</title>
	<%@ include file="../../include/header.jsp"%>
	<link rel="stylesheet" href="<%=STATIC_URL%>/js/fancyBox/jquery.fancybox.css" type="text/css" media="screen" />
	<link href="<%=STATIC_URL%>/js/My97DatePicker/skin/WdatePicker.css" rel="stylesheet" type="text/css">
	<script type='text/javascript' src="<%=STATIC_URL%>/js/fancyBox/jquery.fancybox.js"></script>
	<script type='text/javascript'src="<%=STATIC_URL%>/js/My97DatePicker/WdatePicker.js"></script>
</head>
<body class="article_bg">
	<p class="article_tit">图书管理</p>
	<form id="formSearch" name="formSearch" method="post" action="/textbook/TextbookInfoList.do" onsubmit="javascript:return checkSubmit();">
		<div class="search_form">
		<%if(adminUserHelper.hasPermission(TextbookResource.TEXTBOOK_INFO, OperationConfig.SEARCH)){ %>
			<em class="int_label">图书分类：</em>
	        <select class="form-control" name="textbookClass" id="textbookClass">
				<option value="">请选择</option>
				<%for(StatusBean<String,String> statusBean : TextbookInfoConfig.TXT_CLASS_LIST){ %>
				<option value="<%=statusBean.getStatus()%>" <%=(statusBean.getStatus().equals(ParamKit.getParameter(request, "textbookClass", ""))) ? "selected" : ""%>><%=statusBean.getValue() %></option>
				<%} %>
			</select>
			<em class="em1">图书名称：</em>
			<input type="text" name="textbookName" id="textbookName" value="<%=ParamKit.getParameter(request, "textbookName", "")%>" size="20"/>
			
			<em class="int_label">年份：</em>
			<input type="text" name="year" id="year" onclick="WdatePicker({dateFmt:'yyyy',minDate:'2010',maxDate:'2030'})" size="20" value="<%=ParamKit.getParameter(request, "year", "")%>" />
			
			<em class="int_label">出版社：</em>
	      	<select id="press" name="press" > 
				<option value="">请选择</option>
				<% for(PressInfoBean bean1 : pressList ){%>
				<option value="<%=bean1.getPressId()%>" <%=bean1.getPressId()==ParamKit.getIntParameter(request, "press", -1) ? "selected" : ""%>><%=bean1.getPressName()%></option> 
				<% } %> 
		  	</select>
		  	
		  	<em class="int_label">省份：</em>
	      	<select class="form-control" name="province" id="province"  style="width:120px">
		      	<option value="">请选择</option>
		      	<option value="0" <%=0==ParamKit.getIntParameter(request, "province", -1) ? "selected" : ""%>>中央农广校</option>
		      	<%for(ProvinceInfoBean tempProBean : provinceList) {%>
		      	<option value="<%=tempProBean.getProvinceId() %>" <%=tempProBean.getProvinceId()==ParamKit.getIntParameter(request, "province", -1) ? "selected" : ""%>><%=tempProBean.getProvinceName() %></option>
		      	<%} %>
	      	</select>
		  	
			<button onclick="querySolrData()" class="search_btn_sub">查询</button>
			
			<script type="text/javascript">
				function querySolrData(){
					$("#textbookClass").val($("#textbookClass").val()==""?"*":$("#textbookClass").val());
					$("#textbookName").val($("#textbookName").val()==""?"*":$("#textbookName").val());
					$("#year").val($("#year").val()==""?"*":$("#year").val());
					$("#press").val($("#press").val()==""?"*":$("#press").val());
					$("#province").val($("#province").val()==""?"*":$("#province").val());
					
					formSearch.submit();
				}
			</script>
		<%} %>
		</div>
		
	 <table width="100%" border="0" cellpadding="0" class="table_style">
	 	<tr>
	 		<th>
				<input type="button" id="toggleButton" name="toggleButton" value="全选">
			</th>
	 		<th>图书名</th>
	 		<th>状态</th>
	 		<th>创建时间</th>
	 		<th>排序</th>
	 		<th>操作</th>
	 	</tr>
	 	<%
	 	for(TextbookIndexBean bean : list){
	 	%>
	 		<tr>
	 			<td><input type="checkbox" id="<%=bean.getId()%>" name="approveCheck" /></td>
	 			<td><%=bean.getTextbookName() %></td>
	 			<td><%=bean.getSellingStatus()%></td>
	 			<td><%=DateKit.formatDate(bean.getCreateTime(), DateKit.DEFAULT_DATE_TIME_FORMAT)%></td>
	 			<td><%=bean.getPriority() %></td>
	 			<td align="center">
	 				<%if(adminUserHelper.hasPermission(TextbookResource.TEXTBOOK_INFO, OperationConfig.SEARCH)){ %>
	 					<a href="/textbook/TextbookInfoManage.do?textbookId=<%=bean.getId()%>">编辑</a>
	 				<%} %>
	 				<a href="<%=serverPath+bean.getId() + ".shtml"%>">链接</a>
	 			</td>
	 		</tr>
	 	<%} %>
	 </table>
	</form>
	
<ul class="page_info">
<li class="page_left_btn">
	<%if(adminUserHelper.hasPermission(TextbookResource.TEXTBOOK_INFO, OperationConfig.CREATE)){ %>
  	<input type="button" name="button" id="button" value="新建" onclick="javascript:location.href='TextbookInfoManage.do'">
  	<%} %>
  	<%if(adminUserHelper.hasPermission(TextbookResource.TEXTBOOK_INFO, OperationConfig.REMOVE)){ %>
  	<input type="button" name="" id="outBookButton" value="下架" >
  	<%} %>
  	<%if(adminUserHelper.hasPermission(TextbookResource.TEXTBOOK_INFO, OperationConfig.UPDATE)){ %>
  	<input type="button" name="" id="onBookButton" value="上架" >
  	<%} %>
  	<%if(adminUserHelper.hasPermission(TextbookResource.TEXTBOOK_INFO, OperationConfig.UPDATE)){ %>
  	<input type="button" name="" id="Selectbutton" value="设置分类" >
  	<%} %>
</li>
<%@ include file="../../include/page.jsp" %>
</ul>

</body>
<script type="text/javascript">
//设置toggleButton触发事件
$("#toggleButton").toggle(
	function(){$("#formSearch input:checkbox").attr("checked", "checked"); } ,
	function(){$("#formSearch input:checkbox").removeAttr("checked"); }	
) ;
//图书下架
$("#outBookButton").click(function(){
	var waitForOutId = "" ;  //待审核的Id
	$("input:checkbox[name=approveCheck]:checked").each(function(){
	var textbookId = $(this).attr("id") ;  //审核对象的id
	if(textbookId > 0){
		if(waitForOutId.length <= 0){
			waitForOutId += textbookId ;								
		}else{
			 waitForOutId += "-"+textbookId ;
		}  
	 }
	})//id串生成结束
	
	if(waitForOutId.length <= 0){
		alert("未选中选项");
	}
	if(waitForOutId !=null && waitForOutId.length > 0){
		$.ajax({
			 type : "GET",
			 url : "/textbook/TextbookInfoStatusModify.do",
			 data : {waitForOutId : waitForOutId,actionType:0},
			 success : function(data) {
				 if(data!= null){
					 str = "[" +  data + "]\n下架成功\n" ; 
				 }
				 if(data.length > 0) 
					 alert(str) ;
				 location.reload();
			 },
			 error : function() { 
				 alert("下架失败") ;
			}
		}); //ajax方法结束  
	}
});	
	//图书上架
	$("#onBookButton").click(function(){
		var waitForOutId = "" ;  //待审核的Id
		$("input:checkbox[name=approveCheck]:checked").each(function(){
		var textbookId = $(this).attr("id") ;  //审核对象的id
		if(textbookId > 0){
			if(waitForOutId.length <= 0){
				waitForOutId += textbookId ;								
			}else{
				 waitForOutId += "-"+textbookId ;
			}  
		 }
		})//id串生成结束
		if(waitForOutId.length <= 0){
			alert("未选中选项");
		}
		if(waitForOutId !=null && waitForOutId.length > 0){
			$.ajax({
				 type : "GET",
				 url : "/textbook/TextbookInfoStatusModify.do",
				 data : {waitForOutId : waitForOutId,actionType:1},
				 success : function(data) {
					 if(data!= null){
						 str = "[" +  data + "]\n上架成功\n" ; 
					 }
					 if(data.length > 0) 
						 alert(str) ;
					 location.reload();
				 },
				 error : function() { 
					 alert("上架失败") ;
				}
			}); //ajax方法结束  
		}
	});

</script>
<script type='text/javascript'>
$(document).ready(function() {
	$("#Selectbutton").click(
		function() {
			$.fancybox.open({
				href : 'TextbookSelectClass.do?callbackFun=selectCatalogId',
				type : 'iframe',
				width : 560,
				minHeight : 150
		});
	});
});
function selectCatalog(textbookClass) {
	var waitForOutId = "" ;  //待
	$("input:checkbox[name=approveCheck]:checked").each(function(){
	var textbookId = $(this).attr("id") ;  //审核对象的id
	if(textbookId > 0){
		if(waitForOutId.length <= 0){
			waitForOutId += textbookId ;								
		}else{
			 waitForOutId += "-"+textbookId ;
		}  
	 }
	})//id串生成结束
	if(waitForOutId.length <= 0){
		alert("未选中选项");
		return;
	}
	if(waitForOutId !=null && waitForOutId.length > 0){
		$.ajax({
			 type : "GET",
			 url : "/textbook/TextbookInfoStatusModify.do",
			 data : {waitForOutId : waitForOutId,actionType:2,textbookClass:textbookClass},
			 success : function(data) {
				 if(data!= null){
					 str = "[" +  data + "]\n更改成功\n" ; 
				 }
				 if(data.length > 0) 
					 alert(str) ;
				 location.reload();
			 },
			 error : function() { 
				 alert("更改失败") ;
			}
		}); //ajax方法结束  
	}
}
</script>
</html>