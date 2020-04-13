<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${app_name}</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Expires" content="-1">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Pragma" content="no-cache">
<jsp:include page="../_public_in_head.jsp" flush="true" />
<link href="${ctx}/m/styles/css/my/my-v1.css" rel="stylesheet" type="text/css" />
</head>
<body >
<jsp:include page="../_header.jsp" flush="true" />	
<div class="content my-center">
	<c:forEach var="cur" items="${sonSysModuleList}" varStatus="vs">
	<div class="myls-listbox">
    	  <c:url var="url" value="${cur.mod_url}" />
		  <a class="name sel" onclick="goUrl('${url}')">${cur.mod_name}</a>
	</div>
	</c:forEach>
	<c:if test="${userInfo.is_entp eq 1}">
	  <div class="myls-listbox">
        <c:url var="url" value="/m/MTiXianHuoKuanBi.do?method=add&&par_id=1300100000&mod_id=1300300400" />
	    <a class="name sel" onclick="goUrl('${url}')">货款提现</a>
	  </div>
	</c:if>
</div>
<jsp:include page="../_footer.jsp" flush="true" />			
</body>
</html>