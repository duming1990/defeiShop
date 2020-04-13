<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="../../commons/pages/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>${app_name}</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="Description" content="${app_name}后触屏版找回密码" />
<meta name="Keywords" content="${app_name}后触屏版找回密码" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"/>
<meta name="apple-mobile-web-app-capable" content="yes"/>
<meta name="apple-mobile-web-app-status-bar-style" content="black"/>
<meta name="format-detection" content="telephone=no"/>
<jsp:include page="../_public_in_head.jsp" flush="true" />
<link href="${ctx}/m/styles/css/cp_style_v15.11.min.css" rel="stylesheet" type="text/css"/> 
</head>
<body>
<jsp:include page="../_header.jsp" flush="true" />
<div class="content">
    <!--article-->
    <div class="login-box" style="padding:0px;">
        <div class="zhao-codebox">
                <p>密码修改成功，3s后自动跳转至
                <c:url var="url" value="/m/MIndexLogin.do" />
                <a href="${url}">登录</a></p>
        </div>
    </div>
</div>
<jsp:include page="../_footer.jsp" flush="true" />
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	setTimeout(function(){window.location= "${ctx}/m/MIndexLogin.do";},3000);
});
//]]></script>
</body>
</html>