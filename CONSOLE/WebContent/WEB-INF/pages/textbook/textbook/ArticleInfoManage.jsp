<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.lpmas.framework.bean.*"%>
<%@ page import="com.lpmas.admin.business.*"%>
<%@ page import="com.lpmas.textbook.textbook.bean.*"%>
<%@ page import="com.lpmas.textbook.textbook.config.*"%>
<% 
	ArticleInfoBean bean = (ArticleInfoBean)request.getAttribute("ArticleInfo");
	AdminUserHelper adminUserHelper = (AdminUserHelper)request.getAttribute("AdminUserHelper");
%>
<%@ include file="../../include/header.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>文章管理</title>
	<link href="<%=STATIC_URL %>/css/main.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript" src="<%=STATIC_URL %>/js/jquery.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL %>/js/common.js"></script>
	<script type="text/javascript" src="/js/ckeditor-ming/ckeditor.js"></script>
	<script type="text/javascript" src="/js/ckeditor-ming/js/app.js"></script>
	<script type='text/javascript'src="<%=STATIC_URL%>/js/My97DatePicker/WdatePicker.js"></script>
	<style type="text/css">
		.editor{ width: 800px; height: 200px; }
		.cke_reset_all .dn{ display: none !important; }
		.cke_reset_all .clearfix:after{ content: ''; height: 0; clear: both; display: block; }
		.cke_reset_all .scrollBox{ width: 615px; height: 400px; overflow-y: auto;  }
		.cke_reset_all .fl{ float: left; }
		.cke_reset_all .imgBox{ width: 80px; height: 100px; margin: 10px; position: relative; }
		.cke_reset_all .delbtn{ position: absolute; top: -7px; right: -7px; width: 16px; height: 16px; background: url(../images/delete.png); z-index: 10;  }
		.cke_reset_all .imgBox .checked{ position: absolute; top: 0; left: 0; width: 100%; height: 100%; background: url(../images/check.png) no-repeat #000; opacity: .4; filter: alpha(opacity = 40); color: #fff; line-height: 180px; font-size: 40px; text-align: center; background-position: 50% 50%; }
		.cke_reset_all .imgBox .imgSrc{ display: block; width: 80px; height: 80px; }
		.cke_reset_all .imgBox .imgName{ line-height: 20px; font-size: 14px; color: #333; text-align: center; }
		.cke_reset_all .search-int{ margin-left: 10px; border: 1px solid #ccc; padding: 3px; outline: none }
		.cke_reset_all .search-btn,.cke_reset_all .turn-btn{ border: 1px solid #ccc; padding: 3px; background: #f2f2f2; border-left-width: 0; }
		.cke_reset_all .pageNum{ text-align: center; padding: 5px 0; }
		.cke_reset_all .pageNum span{ margin: 0 2px; }
		.cke_reset_all .pageNum .intNum{ border: 1px solid #ccc; width: 40px; }
		.cke_reset_all .turn-btn{ border: 1px solid #ccc; padding: 1px 3px; margin-left: 2px; }
		.cke_reset_all .hander{ cursor: pointer; }
		
		
		.imageBox{ position: relative; width: 150px; height:150px; margin-bottom: 18px; margin-right: 18px; float: left; }
		.close{ width: 16px; height: 16px; position: absolute; top: -8px; right: -8px; background: url(../images/close.png) no-repeat; }
	</style>
</head>
<body class="article_bg">
<div class="article_tit">
		<a href="javascript:history.back()" ><img src="<%=STATIC_URL %>/images/back_forward.jpg"/></a> 
		<ul class="art_nav">
			<li><a href="ArticleInfoList.do">文章管理</a>&nbsp;>&nbsp;</li>
			<% if(bean.getArticleId() > 0) {%>
			<li>修改</li>
			<%}else{ %>
			<li>新建</li>
			<%}%>
		</ul>
	</div>
	<form id="formData" name="formData" method="post" action="ArticleInfoManage.do" onsubmit="javascript:return checkForm('formData');">
	  <input type="hidden" name="articleId" id="articleId" value="<%=bean.getArticleId() %>"/>
	  <input type="hidden" name="status" id="status" value="<%=bean.getStatus() %>"/>
	  <div class="modify_form">
	    <p>
	      <em class="int_label"><span>*</span>文章标题：</em>
	      <input type="text" name="articleTitle" id="articleTitle" size="50" maxlength="50" value="<%=bean.getArticleTitle() %>" checkStr="文章标题;txt;true;;100"/>
	    </p>
	    <p>
	      <em class="int_label"><span></span>作者：</em>
	      <input type="text" name="author" id="author" size="30" maxlength="20" value="<%=bean.getAuthor() %>" checkStr="作者;txt;false;;100"/>
	    </p>
	    <p>
	      <em class="int_label"><span>*</span>栏目类型：</em>
	       <select class="form-control" name="sectionType" id="sectionType" checkStr="栏目类型;txt;true;;100">
			<option value="">请选择</option>
			<% String sectionType = (bean!=null ? bean.getSectionType() : ""); %>	
			<%for(StatusBean<String,String> statusBean : ArticleInfoConfig.SECTION_TYPE_LIST){ %>
			<option value="<%=statusBean.getStatus()%>" <%=(statusBean.getStatus().equals(sectionType)) ? "selected" : ""%>><%=statusBean.getValue() %></option>
			<%} %>
			</select>
	    </p>
	    <p>
	      <em class="int_label"><span>*</span>状态：</em>
	       <select class="form-control" name="publishStatus" id="publishStatus" checkStr="状态;num;true;;10">
			<option value="">请选择</option>
			<% int status = (bean!=null ? bean.getPublishStatus() : 0); %>	
			<%for(StatusBean<Integer,String> statusBean : ArticleInfoConfig.PUBLISH_STATUS_LIST){ %>
			<option value="<%=statusBean.getStatus()%>" <%=(statusBean.getStatus()==status) ? "selected" : ""%>><%=statusBean.getValue() %></option>
			<%} %>
			</select>
	    </p>
	    <p>
	      <em class="int_label"><span>*</span>文章内容：</em>
	      <span class="inline_block">
					<textarea name="articleContent" id="articleContent"><%=bean!=null ? bean.getArticleContent() : ""%></textarea>
					<script>
						CKEDITOR.replace('articleContent', {
							language : 'zh-cn',
							uiColor : '#ffffff',
							allowedContent : true
						});
					</script>
		  </span>
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
</body>
</html>