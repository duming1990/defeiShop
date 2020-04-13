<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${app_name_min}触屏版</title>
<meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0" name="viewport" />
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<jsp:include page="../_public_in_head.jsp" flush="true" />
<link href="${ctx}/m/styles/erweima/card.css?v=20180319" rel="stylesheet" type="text/css" />
<style type="text/css">
p{margin-bottom:0px!important;}
.c-hd section.back a{box-sizing: unset;}
</style>
</head>
<body>
	<jsp:include page="../_header.jsp" flush="true" />
	<img src="${ctx}/images/user_right.jpg" style="max-width:100%;padding-bottom: 30px;"/>
  <jsp:include page="../_footer.jsp" flush="true" />
  <script type="text/javascript" src="${ctx}/styles/mui/layer.js"></script>
 <script type="text/javascript">
  </script>
</body>
</html>