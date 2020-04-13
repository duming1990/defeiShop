<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<script type="text/javascript">//<![CDATA[
window.onload = function () {
	alert("您当前登录的账号于 ${loginDate} 在IP为 ${loginIp} 的地方登录，您被迫下线！");
	top.location.href = "${ctx}/";
}
//]]></script>