<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>${app_name}</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Description" content="${app_name}后触屏版登陆" />
<meta name="Keywords" content="${app_name}后触屏版登陆" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"/>
<meta name="apple-mobile-web-app-capable" content="yes"/>
<meta name="apple-mobile-web-app-status-bar-style" content="black"/>
<meta name="format-detection" content="telephone=no"/>
<jsp:include page="../_public_in_head.jsp" flush="true" />
<style type="text/css">
.deal-btn {
	margin: 0.2rem 0.2rem 0 0.2rem;
	;
}
</style>
</head>
<body id="body" style="background-color: #f5f5f9;">
<div class="content">
  <div class="loginapp-page"> <i class="loginappimg"></i>
    <p> ${msg1} </p>
    <p> ${msg2} </p>
  </div>
  <c:if test="${is_success}">
  <div class="deal-btn">
    <input type="button" class="j_submit" name="loginapp" id="loginapp" value="确认登录" />
  </div>
  </c:if>
  <div class="deal-btn">
    <input type="button" class="j_submit j_submit_cancel" name="cancel" onclick="closewebview()" id="cancel" value="${btn_cancel}" />
  </div>
</div>
<script type="text/javascript">//<![CDATA[
                                          
function closewebview(){
	window.location.href="appclosewebview://";
}

$(document).ready(function(){
	
	 $("#loginapp").click(function(){
		Common.loading();
		$.ajax({
			dataType:"json",
			url: "${ctx}/CsAjax.do?method=applogin",
			success: function(data){
				Common.hide();
				if(data.code == 1){
					closewebview();
					return false;
				} else {
					mui.toast(data.msg);
				}
			}
		});
	 });
	 
});

//]]></script>
</body>
</html>
