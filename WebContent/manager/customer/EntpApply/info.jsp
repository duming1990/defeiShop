<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>店铺入驻电子协议 - ${app_name}</title>
<jsp:include page="../../../_public_header.jsp" flush="true" />
</head>
<body class="pg-unitive-signup theme--www">
<button class="bgButton" id="export">导出word</button>
<div style="padding:10px;" class="text">${newsInfo.map.content}</div>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script>
<script type="text/javascript" src="${ctx}/scripts/FileSaver.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jquery-wordexport.js"></script>

<script type="text/javascript">
$(function(){
    $("#export").click(function(event) {
        $(".text").wordExport('商城服务协议');
    });
})
//<![CDATA[
//]]></script>
</body>
</html>
