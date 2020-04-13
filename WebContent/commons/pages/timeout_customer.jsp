<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<c:url var="url" value="/login.do" />
<script type="text/javascript">//<![CDATA[
window.onload = function () {
	alert("尊敬的会员，很抱歉！您没有登录或已超时，请重新登录！");//\n如果您已经选择了记住密码，系统将自动重新登录。
	top.location.href = "${url}";
	//var ss = "${url}";
};
//]]></script>