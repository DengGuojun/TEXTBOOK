<%@page import="com.lpmas.textbook.textbook.config.ArticleInfoConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.lpmas.admin.business.*"%>
<%
	AdminUserHelper adminUserHelper = (AdminUserHelper) request.getAttribute("AdminUserHelper");
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>全国新型职业农民培育教材信息平台</title>
	<!--[if lt IE9]> 
	<script> 
	   (function() {
	     if (! 
	     /*@cc_on!@*/
	     0) return;
	     var e = "abbr, article, aside, audio, canvas, datalist, details, dialog, eventsource, figure, footer, header, hgroup, mark, menu, meter, nav, output, progress, section, time, video".split(', ');
	     var i= e.length;
	     while (i--){
	         document.createElement(e[i])
	     } 
	})() 
	</script>
	<![endif]-->
	<link rel="stylesheet" type="text/css" href="/css/base.css">
</head>
<body>
	<nav class="g-nav clearfix">
		<div class="nav-content">
			<div class="slide-left">
				<h1 class="nav-title"><a href="/index.shtml">新型职业农民培育图书</a></h1>
			</div>
			<div class="slide-right">
				<div class="fr collect-box">
					<a class="item" rel="sidebar" onclick="AddFavorite();">收藏</a>
					<i class="u-line"></i>
					<a class="item" href="http://1.202.195.201:9090/wasdemo/search?searchword=&channelid=84836&searchword=&SendHead=%E6%90%9C%E7%B4%A2">帮助</a>
				</div>
				<div class="search-box fr">
					<input type="text" class="search-input" name="fullTextSearch" id="fullTextSearch" placeholder="搜索教材名称…">
					<i class="icon-lib icon-search" onclick="search();"></i>
				</div>
			</div>
		</div>
		
	</nav>
	<div class="g-container">
		<div class="rel">
			<div class="cont-section ml-0 mt-16">
				<div class="suggest-box">
					<div class="suggest-content">
						<h2 class="suggest-title">找不到想要的书？<br/>请反馈给我们，我们将尽快为您查找</h2>
						<form class="suggest-formSubmit" method="post" action="FeedbackSubmit.do">
							<p>
								<label class="form-label">书名</label><input placeholder="必填" class="form-input" type="text" name="textbookName">
							</p>
							<p>
								<label class="form-label">你的联系方式</label><input placeholder="必填" class="form-input" type="text" name="contactInfo">
							</p>
							<div class="tc">
								<div class="btn submitBtn"><a>提交</a></div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<!-- sina jquery -->
<script type="text/javascript" src="http://lib.sinaapp.com/js/jquery/1.8.3/jquery.min.js"></script>
<!-- common -->
<script type="text/javascript" src="/js/app.js"></script>

<script type="text/javascript">
	$(".submitBtn").click(function(){
		$("form").submit();
	});
</script>
</html>