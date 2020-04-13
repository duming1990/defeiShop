<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %><!DOCTYPE html>
<html><head lang="en"><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta content="telephone=no" name="format-detection">
    <meta name="wap-font-scale" content="no">
    <title>${app_name}</title>
    <jsp:include page="../_public_in_head.jsp" flush="true" />
   </head>
<body>
		<header class="mui-bar mui-bar-nav">
			<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
			<h1 class="mui-title">新闻详情</h1>
		</header>
		<div class="mui-content">
			<div class="mui-card" style="margin-top: 0px;margin:0px;padding: 10px;">
				<div class="mui-card-header mui-card-media">
					<div class="mui-media-body" style="margin-left:auto;text-align: center;">
						${newsInfo.title}
						<p>发表于 <fmt:formatDate value="${newsInfo.add_time}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
					</div>
				</div>
				<div class="mui-card-content" >
					${newsInfo.map.content}
					<img src="../images/yuantiao.jpg" alt="" width="100%"/>
				</div>
			</div>
		</div>
		<jsp:include page="../_footer.jsp" flush="true" />
	</body>
</html>