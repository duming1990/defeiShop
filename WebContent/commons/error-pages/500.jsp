<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" session="false" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta name="format-detection" content="telephone=no" />
<title>禁止访问</title>
<link href="${ctx}/commons/error-pages/css/style.css" rel="stylesheet" type="text/css" />
</head>
  <body>
		<div class="main-agileits">
			<h1>系统繁忙<br/>请稍后重试</h1>
			<div class="mainw3-agileinfo form">
				<div class="agileits-subscribe">
					<div class="subscribe-w3lsbottom">
						<h2>500</h2>
						<c:url var="url" value="/index.do" />
						<a class="play-icon popup-with-zoom-anim" href="${url}">进入首页</a>
					</div>
					<div class="clear"></div>
				</div>
			</div>
		</div>
  </body>
</html>
