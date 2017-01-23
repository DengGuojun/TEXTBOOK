<%@page import="com.lpmas.framework.web.ParamKit"%>
<%@page import="com.lpmas.textbook.catalog.bean.CatalogInfoBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.lpmas.framework.util.*"%>
<%@ page import="com.lpmas.framework.bean.*"%>
<%@ page import="com.lpmas.framework.config.*"%>
<%@ page import="com.lpmas.admin.config.*"%>
<%@ page import="com.lpmas.admin.business.*"%>
<%@ page import="com.lpmas.region.bean.*"%>
<%@ page import="com.lpmas.textbook.textbook.bean.*"%>
<%@ page import="com.lpmas.textbook.textbook.config.*"%>
<%@ page import="com.lpmas.textbook.console.catalog.config.*"%>
<%@ page import="com.lpmas.textbook.console.textbook.config.*"%>
<% 
	TextbookInfoBean bean = (TextbookInfoBean)request.getAttribute("TextbookInfo");
	AdminUserHelper adminUserHelper = (AdminUserHelper)request.getAttribute("AdminUserHelper");
	List<PressInfoBean> pressList = (ArrayList<PressInfoBean>) request.getAttribute("pressInfoList");
	
	List<CatalogInfoBean> directory1List = (List<CatalogInfoBean>) request.getAttribute("directory1List");
	List<CatalogInfoBean> directory2List = (List<CatalogInfoBean>) request.getAttribute("directory2List");
		
	List<ProvinceInfoBean> provinceList = (List<ProvinceInfoBean>)request.getAttribute("provinceList");
	List<TextbookMediaBean> coverImgList = (List<TextbookMediaBean>)request.getAttribute("coverImgList");
	
	HashMap<Object,TextbookDescriptionBean> descMap = (HashMap<Object,TextbookDescriptionBean>)request.getAttribute("descMap");
	TextbookDescriptionBean priceBean = descMap.get(TextbookInfoConfig.TXT_DESC_PRICE);
	TextbookDescriptionBean overclassBean = descMap.get(TextbookInfoConfig.TXT_DESC_OVERALL_CLASSIFICATION);
	TextbookDescriptionBean yearBean = descMap.get(TextbookInfoConfig.TXT_DESC_YEAR);
	TextbookDescriptionBean provinceBean = descMap.get(TextbookInfoConfig.TXT_DESC_PROVINCE);
	TextbookDescriptionBean classBean = descMap.get(TextbookInfoConfig.TXT_DESC_TEXTBOOK_CLASS);
	TextbookDescriptionBean typeBean = descMap.get(TextbookInfoConfig.TXT_DESC_TEXTBOOK_TYPE);
	TextbookDescriptionBean groupBean = descMap.get(TextbookInfoConfig.TXT_DESC_GROUP_EDIT);
	TextbookDescriptionBean mainBean = descMap.get(TextbookInfoConfig.TXT_DESC_MAIN_EDIT);
	TextbookDescriptionBean teacherBean = descMap.get(TextbookInfoConfig.TXT_DESC_GUIDE_TEACHER);
	TextbookDescriptionBean pressBean = descMap.get(TextbookInfoConfig.TXT_DESC_PRESS);
	TextbookDescriptionBean dateBean = descMap.get(TextbookInfoConfig.TXT_DESC_PUBLICATION_DATE);
	TextbookDescriptionBean formatBean = descMap.get(TextbookInfoConfig.TXT_DESC_BOOK_FORMAT);
	TextbookDescriptionBean introBean = descMap.get(TextbookInfoConfig.TXT_DESC_INTRODUCTION);
	TextbookDescriptionBean descBean = descMap.get(TextbookInfoConfig.TXT_DESC_WRITE_DESCRIPTION);
	TextbookDescriptionBean contentsBean = descMap.get(TextbookInfoConfig.TXT_DESC_CONTENTS);
	TextbookDescriptionBean chapterBean = descMap.get(TextbookInfoConfig.TXT_DESC_FIRST_CHAPTER);
	
	int directory1  = ParamKit.getIntAttribute(request, "directory1", -1);
	int directory2  = ParamKit.getIntAttribute(request, "directory2", -1);
%>
<%@ include file="../../include/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>图书管理</title>
	<link href="<%=STATIC_URL %>/css/main.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript" src="/js/ckeditor-ming/ckeditor.js"></script>
	<script type="text/javascript" src="/js/ckeditor-ming/js/app.js"></script>
	<script type='text/javascript'src="<%=STATIC_URL%>/js/My97DatePicker/WdatePicker.js"></script>
	<link href="<%=STATIC_URL%>/js/My97DatePicker/skin/WdatePicker.css" rel="stylesheet" type="text/css">
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
			<li><a href="TextbookInfoList.do">图书列表</a>&nbsp;>&nbsp;</li>
			<% if(bean.getTextbookId() > 0) {%>
			<li>修改</li>
			<%}else{ %>
			<li>新建</li>
			<%}%>
		</ul>
	</div>
	<form id="formData" name="formData" method="post" action="TextbookInfoManage.do" onsubmit="javascript:return myCheckForm();">
	  <input type="hidden" name="textbookId" id="textbookId" value="<%=bean.getTextbookId() %>"/>
	  <input type="hidden" name="status" id="status" value="<%=bean.getStatus() %>"/>
	   <input type="hidden" name="sellingStatus" id="sellingStatus" value="<%=bean.getSellingStatus() %>"/>
	  <div class="modify_form">
	    <p>
	      <em class="int_label"><span>*</span>图书名：</em>
	      <input type="text" name="textbookName" id="textbookName" size="30" maxlength="20" value="<%=bean.getTextbookName() %>" checkStr="名称;txt;true;;100"/>
	    </p>
	    <p class="p_top_border">
	      <em class="int_label">排序：</em>
	      <input type="text"  name="priority" id="priority" size="30" maxlength="5" value="<%=bean.getPriority() %>" checkStr="排序;num;true;;100"/>
	    </p>
	    <p class="p_top_border">
	      <em class="int_label"><span>*</span>价格：</em>
	      <input type="text"  name="PRICE" id="PRICE" size="30" maxlength="10" value="<%=priceBean != null ?priceBean.getDescValue():""%>" checkStr="排序;num;true;;100"/>
	    </p>
	    <p class="p_top_border">
	    
         <em class="int_label">封面图片：</em>                                                   
         <input type="file" id="file" name="file" multiple="multiple" class="form-control int_normal" onchange="up();" ><span>支持jpg、png格式图片，最多十张</span>          
      	 
        </p>
       <p class="p_top_border">
	       <div id="showImage">
	       <% for(TextbookMediaBean mediaBean : coverImgList) {%>
		       <div class='imageBox js-imageBox'><div class='close js-close-btn'></div>
		       		<img id="img0" height=150  width=150 src="<%=mediaBean.getMediaUrl() %>" name="img0">
		       </div>
	       <% } %>
	       </div>
	   	   <input type="hidden" id="imgCol" name="imgCol"/>
       </p>
	    <p class="p_top_border">
	      <em class="int_label"><span>*</span>总体分类：</em>
	      <select class="form-control" name="OVERALL_CLASSIFICATION" id="OVERALL_CLASSIFICATION">
			<option value="">请选择</option>
			<% String classification = (overclassBean != null ?overclassBean.getDescValue():""); %>	
			<%for(StatusBean<String,String> statusBean : TextbookInfoConfig.TXT_CLASSIFICATION_LIST){ %>
			<option value="<%=statusBean.getStatus()%>" <%=(statusBean.getStatus().equals(classification)) ? "selected" : ""%>><%=statusBean.getValue() %></option>
			<%} %>
		  </select>
	    </p>
	    
	    <p class="p_top_border">
	      <em class="int_label"><span>*</span>年份：</em>
	      <input type="text" name="YEAR" id="YEAR" onclick="WdatePicker({dateFmt:'yyyy',minDate:'2010',maxDate:'2030'})" value="<%=yearBean!=null?yearBean.getDescValue():"" %>" size="20" >
	    </p>
	     <p class="p_top_border">
	      <em class="int_label">省份：</em>
	      <select class="form-control" name="PROVINCE" id="PROVINCE"  style="width:120px">
	      	<option value="">请选择</option>
	      	<option value="0">中央农广校</option>
	      	<%for(ProvinceInfoBean tempProBean : provinceList) {%>
	      	<option value="<%=tempProBean.getProvinceId() %>" <%if((tempProBean.getProvinceId()+"").equals(provinceBean!=null?provinceBean.getDescValue():"")) {%>selected<%} %>><%=tempProBean.getProvinceName() %></option>
	      	<%} %>
	      </select>
	    </p>
	    <%-- <p class="p_top_border">
	      <em class="int_label"><span>*</span>图书分类：</em>
	      <select class="form-control" name="TEXTBOOK_CLASS" id="TEXTBOOK_CLASS">
			<option value="">请选择</option>
			<% String txtclass = (classBean != null ?classBean.getDescValue():""); %>	
			<%for(StatusBean<String,String> statusBean : TextbookInfoConfig.TXT_CLASS_LIST){ %>
			<option value="<%=statusBean.getStatus()%>" <%=(statusBean.getStatus().equals(txtclass)) ? "selected" : ""%>><%=statusBean.getValue() %></option>
			<%} %>
			</select>
	    </p> --%>
	    <select class="form-control" name="TEXTBOOK_CLASS" id="TEXTBOOK_CLASS" style="display:none">
			<option value="AQUACULTURE" selected>种植业</option>
		</select>
		<p class="p_top_border">
			<em class="int_label"><span>*</span>专业：</em>
			<select id="directory1"	name="directory1">
				<option></option>
				<%
					if (directory1List != null) {
						for (CatalogInfoBean catalogBean : directory1List) {
				%>
				<option value="<%=catalogBean.getCatalogId()%>"	<%if (catalogBean.getCatalogId() == directory1) {%> selected <%}%>><%=catalogBean.getCatalogName()%></option>
				<%
						}
					}
				%>
			</select> 
			<select id="directory2" name="directory2">
				<option></option>
				<%
					if (directory2List != null) {
						for (CatalogInfoBean catalogBean : directory2List) {
				%>
				<option value="<%=catalogBean.getCatalogId()%>"	<%if (catalogBean.getCatalogId() == directory2) {%> selected <%}%>><%=catalogBean.getCatalogName()%></option>
				<%
						}
					}
				%>
			</select>
			<input type="hidden" id="CATALOGID" name="CATALOGID" value="">
		</p>
		<p class="p_top_border">
        	<em class="int_label"><span>*</span>图书类型：</em>
        	<select class="form-control" name="TEXTBOOK_TYPE" id="TEXTBOOK_TYPE">
				<option value="">请选择</option>
				<% String type = (typeBean != null ?typeBean.getDescValue():""); %>	
				<%for(StatusBean<String,String> statusBean : TextbookInfoConfig.TXT_TYPE_LIST){ %>
					<option value="<%=statusBean.getStatus()%>" <%=(statusBean.getStatus().equals(type)) ? "selected" : ""%>><%=statusBean.getValue() %></option>
				<%} %>
			</select>
	    </p>
	    
	    <p class="p_top_border">
	      <em class="int_label"><span>*</span>组编：</em>
	      <input type="text"  name="GROUP_EDIT" id="GROUP_EDIT" size="30" maxlength="30" value="<%=groupBean != null ?groupBean.getDescValue():""%>" checkStr="组编;num;true;;100"/>
	    </p>
	    <p class="p_top_border">
	      <em class="int_label"><span>*</span>主编：</em>
	      <input type="text"  name="MAIN_EDIT" id="MAIN_EDIT" size="30" maxlength="30" value="<%=mainBean != null ?mainBean.getDescValue():""%>" checkStr="主编;num;true;;100"/>
	    </p>
	    <p class="p_top_border">
	      <em class="int_label">指导老师：</em>
	      <input type="text"  name="GUIDE_TEACHER" id="GUIDE_TEACHER" size="30" maxlength="30" value="<%=teacherBean != null ?teacherBean.getDescValue():""%>" checkStr="指导老师;num;true;;100"/>
	    </p>
	    <p class="p_top_border">
	      <em class="int_label"><span>*</span>出版社：</em>
	      <select id="PRESS" name="PRESS" value="<%=pressBean != null?pressBean.getDescValue() : 0 %>" > 
			<option value="">请选择</option>
			<% for(PressInfoBean bean1 : pressList ){%>
			<option value="<%=bean1.getPressId()%>" <%if ((bean1.getPressId()+"").equals(pressBean != null?pressBean.getDescValue() : 0)) {%> selected <%}%>><%=bean1.getPressName()%></option> <% } %> 
		  </select>
	    </p>
	     <p class="p_top_border">
	      <em class="int_label"><span>*</span>出版日期：</em>
	      <input type="text" name="PUBLICATION_DATE" id="PUBLICATION_DATE" onclick="WdatePicker({dateFmt:'yyyy-MM',minDate:'1990-01',maxDate:'2030-12'})" value="<%=dateBean!=null?dateBean.getDescValue():"" %>" size="20" >
	    </p>
	     <p class="p_top_border">
	      <em class="int_label">开本：</em>
	      <input type="text"  name="BOOK_FORMAT" id="BOOK_FORMAT" size="30" maxlength="30" value="<%=formatBean != null ?formatBean.getDescValue():""%>" checkStr="开本;num;true;;100"/>
	    </p>
	    <p class="p_top_border">
				<em class="int_label">内容介绍：</em>
				<span class="inline_block">
					<textarea name="INTRODUCTION" id="INTRODUCTION"><%=introBean != null?introBean.getDescValue():""%></textarea>
					<script>
						CKEDITOR.replace('INTRODUCTION', {
							language : 'zh-cn',
							uiColor : '#ffffff',
							allowedContent : true
						});
					</script>
				</span>
		</p>
		<p class="p_top_border">
				<em class="int_label">编写说明：</em>
				<span class="inline_block">
					<textarea name="WRITE_DESCRIPTION" id="WRITE_DESCRIPTION"><%=descBean !=null?descBean.getDescValue():""%></textarea>
					<script>
						CKEDITOR.replace('WRITE_DESCRIPTION', {
							language : 'zh-cn',
							uiColor : '#ffffff',
							allowedContent : true
						});
					</script>
				</span>
		</p>
		<p class="p_top_border">
				<em class="int_label">目录：</em>
				<span class="inline_block">
					<textarea name="CONTENTS" id="CONTENTS"><%=contentsBean != null?contentsBean.getDescValue():""%></textarea>
					<script>
						CKEDITOR.replace('CONTENTS', {
							language : 'zh-cn',
							uiColor : '#ffffff',
							allowedContent : true
						});
					</script>
				</span>
		</p>
		<p class="p_top_border">
				<em class="int_label">订购方式：</em>
				<span class="inline_block">
					<textarea name="FIRST_CHAPTER" id="FIRST_CHAPTER"><%=chapterBean != null?chapterBean.getDescValue():""%></textarea>
					<script>
						CKEDITOR.replace('FIRST_CHAPTER', {
							language : 'zh-cn',
							uiColor : '#ffffff',
							allowedContent : true
						});
					</script>
				</span>
		</p>
	  </div>
	  <div class="div_center">
	  <input type="submit" name="submit" id="submit" class="modifysubbtn" value="上架" />
	  <input type="button" name="cancel" id="cancel" value="取消" onclick="javascript:history.back()">
	  </div>
	</form>
	<% if((bean.getTextbookId() > 0) && adminUserHelper.checkPermission(TextbookResource.TEXTBOOK_INFO, OperationConfig.REMOVE)) {%>
	<!-- 删除表单 -->
	<form id="formData" name="formData" method="post" action="TextbookInfoManage.do">
	<div style="text-align:right">
		<input type="hidden" name="textbookId" id="textbookId" value="<%=bean.getTextbookId() %>"/>
	  	<input type="hidden" name="status" id="status" value="<%=Constants.STATUS_NOT_VALID %>"/>
	  	<input type="submit" name="remove" id="remove" class="modifysubbtn" value="删除" />
	</div>
	</form>
	<%} %>
</body>

<script type="text/javascript">
	var imgCol = "" ;
	function up() {
	    var file_data = $('#file')[0].files; // for multiple files
	    var images = $('#showImage img');
	    var count = images.length;
	    for (var i = 0; i < file_data.length; i++) {
	    	count++;
	        if(count > 10){
				if(count == 11)alert("封面图片超过十张");
	        	continue;
	        }
	        var fd = new FormData();
	        fd.append('file', file_data[i]);
	        $.ajax({
	        		url: '/textbook/TextbookMediaManage.do',
	            data: fd,
	            contentType: false,
	            processData: false,
	            type: 'POST',
	            success: function (data) {
	                console.log(data);
	                if(data.code == '1'){
	                		var dataObj=eval("("+data.message+")");
	                		var child = "";
	                		child +="<div class='imageBox js-imageBox'><div class='close js-close-btn'></div><img id='img0' name='coverImg' height=150  width=150 src='"+dataObj+"'/></div>"
	                		imgCol += dataObj + ",";
	                       
	                		$("#showImage").append(child);
	                		$('.js-close-btn').click(function(){
	                			$(this).parents('.js-imageBox').remove(); //删除图片
	                		})
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
	function makeImgCol(){
		var resImg = "";
		//img串拼接
		$("[id=img0]").each(function(){
			resImg += $(this).attr("src") + ",";
		})
		document.getElementById("imgCol").value = resImg;
	}
	$('.js-close-btn').click(function(){
		$(this).parents('.js-imageBox').remove(); //删除图片
	})
</script>
<script type="text/javascript">
	$(document).ready(function() {
		//top
		$("#directory1").on("change",function() {
			var catalogId = $(this).find("option:selected").val();
			//清空二级目录
			$("#directory2").html("");
			seletCate_top(catalogId, $(this));
		});
		$("#directory2").on("change",function() {
			$("#CATALOGID").val($(this).find("option:selected").val());
		});
		function seletCate_top(catalogId, element) {
			if (catalogId == 0)
				return;
			$.ajax({
				type : 'get',
				url : "/catalog/CatalogInfoSelect.do?catalogId=" + catalogId,
				dataType : 'json',
				success : function(data) {
					console.log(data);
					var catalogInfo = data.result;
					child = "<option></option>";
					for (var j = 0; j < catalogInfo.length; j++) {
						var originalId = $("#CATALOGID").val();
						child += "<option value='" + catalogInfo[j].catalogId  +"'>" + catalogInfo[j].catalogName + "</option>";
					}
					$("#directory2").html(child);
				}
			});
		}
	});
</script>
<script type="text/javascript">
	function checkSubmit() {
		var select1 = $("#directory1 option:selected").val();
		var select2 = $("#directory2 option:selected").val();
		//判断取哪个父类id
		if (select1 > 0 && select2 > 0) {
			document.getElementById("CATALOGID").value = select2;
			return true;
		} else {
			alert("请选择二级专业");
			return false;
		}
	}
	function myCheckForm() {
		if (checkSubmit())
			return true;
		else
			return false;
	}
</script>
</html>