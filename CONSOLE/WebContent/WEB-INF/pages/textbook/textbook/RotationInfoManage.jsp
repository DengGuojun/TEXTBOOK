<%@page import="com.lpmas.textbook.textbook.config.ArticleInfoConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.lpmas.admin.business.*"%>
<%@ page import="com.lpmas.admin.config.*"%>
<%@ page import="com.lpmas.textbook.textbook.bean.*"%>
<%@ page import="com.lpmas.textbook.console.config.*"%>
<% 
	AdminUserHelper adminUserHelper = (AdminUserHelper)request.getAttribute("AdminUserHelper");
	List<RotationInfoBean> list = (List<RotationInfoBean>)request.getAttribute("RotationInfoList");
	int rotationNum = 0;
%>
<%@ include file="../../include/header.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>轮播图管理</title>
	<link href="<%=STATIC_URL %>/css/main.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript" src="<%=STATIC_URL %>/js/jquery.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL %>/js/common.js"></script>
</head>
<body class="article_bg">
	<p class="article_tit">轮播图管理</p>
	<form id="formData" name="formData" method="post" action="RotationInfoManage.do" onsubmit="javascript:return checkForm('formData');">
		<%
	 	for(int i = 0; i < list.size(); i++){
	 		RotationInfoBean bean = list.get(i);
	 		rotationNum += 1;
	 	%>
		  <input type="hidden" name="rotationId<%=i %>" id="rotationId<%=i %>" value="<%=bean==null? 0: bean.getRotationId() %>"/>
		  <div class="modify_form">
		    <p>
		      <em class="int_label"><span>*</span>标题：</em>
		      <input type="text" name="imageTitle<%=i %>" id="imageTitle<%=i %>" size="30" maxlength="20" value="<%=bean==null? "": bean.getImageTitle() %>" />
		    </p>
		    <p class="p_top_border">
		      <em class="int_label"><span>*</span>图片：</em>
		      <img alt="" src='<%=bean==null? "": TextbookConsoleConfig.RELATIVE_ROTATION_PATH  + bean.getImageUrl() %>'/>
		      <input type="hidden"  name="imageUrl<%=i %>" id="imageUrl<%=i %>" size="30" maxlength="50" value="<%=bean==null? "": bean.getImageUrl() %>" />
		      <input type="file" id="file<%=i %>" name="file<%=i %>" class="form-control int_normal" onchange="fileUpLoad($('#file<%=i %>'));" >
		    </p>
		    <p class="p_top_border">
		      <em class="int_label">链接：</em>
		      <input type="text"  name="originalUrl<%=i %>" id="originalUrl<%=i %>" size="100" maxlength="200" value="<%=bean==null? "": bean.getOriginalUrl() %>" />
		    </p>
		    <p class="p_top_border">
		      <em class="int_label"><span>*</span>排序：</em>
		      <input type="text"  name="priority<%=i %>" id="priority<%=i %>" size="5" maxlength="5" value="<%=bean==null? 0: bean.getPriority() %>" />
		    </p>
		  </div>
	    <%} %>
	  	
	  	<%for(; rotationNum < 5; rotationNum++){ %>
		  <div class="modify_form">
		    <p>
		      <em class="int_label"><span>*</span>标题：</em>
		      <input type="text" name="imageTitle<%=rotationNum %>" id="imageTitle<%=rotationNum %>" size="30" maxlength="20" />
		    </p>
		    <p class="p_top_border">
		      <em class="int_label"><span>*</span>图片：</em>
		      <img alt="" src=''/>
		      <input type="hidden"  name="imageUrl<%=rotationNum %>" id="imageUrl<%=rotationNum %>" size="30" maxlength="50" />
		      <input type="file" id="file<%=rotationNum %>" name="file<%=rotationNum %>" class="form-control int_normal" onchange="fileUpLoad($('#file<%=rotationNum %>'));" >
		    </p>
		    <p class="p_top_border">
		      <em class="int_label">链接：</em>
		      <input type="text"  name="originalUrl<%=rotationNum %>" id="originalUrl<%=rotationNum %>" size="30" maxlength="50" />
		    </p>
		    <p class="p_top_border">
		      <em class="int_label"><span>*</span>排序：</em>
		      <input type="text"  name="priority<%=rotationNum %>" id="priority<%=rotationNum %>" size="5" maxlength="5" />
		    </p>
		  </div>
	  	<% } %>
	  <div class="div_center">
	  <input type="submit" name="submit" id="submit" class="modifysubbtn" value="提交" />
	  <input type="button" name="cancel" id="cancel" value="取消" onclick="javascript:history.back()">
	  </div>
	</form>
	
	<script type="text/javascript">
		function fileUpLoad(obj) {
		    var file_data = obj[0].files; 
		    for (var i = 0; i < file_data.length; i++) {
		        var fd = new FormData();
		        fd.append('file', file_data[i]);
		        fd.append('uploadType', 'rotation');
		        $.ajax({
		        		url: '/textbook/RotationMediaManage.do',
			            data:fd,
			            contentType: false,
			            processData: false,
			            type: 'POST',
			            success: function (data) {
			            	var dataObj=eval("("+data.message+")");
			                if(data.code == '1'){
			                		obj.siblings('img').attr('src', dataObj.pathName);
			                		obj.siblings('input[type=hidden]').val(dataObj.fileName);
			                }else{
			                		alert("上传失败");
			                }
			            },
			            error: function (XMLHttpRequest, textStatus, errorThrown) {
			                console.log(errorThrown);
			            }
		        });  
		    }
		}
	</script>
	
</body>
</html>