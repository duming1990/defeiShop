<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no" charset="utf-8" />
<link href="${ctx}/styles/public.css" rel="stylesheet" type="text/css" />
<jsp:include page="../_public_in_head.jsp" flush="true" />
<link rel="stylesheet" href="${ctx}/styles/mui/css/mui.min.css" />
<style>
img{
	max-width: 100%;
}
</style>
</head>
<body>
    <jsp:include page="../_header.jsp" flush="true" />
	<c:if test="${ not empty entpBaseLink.image_path}">
		<img src="${ctx}/${entpBaseLink.image_path}"/>
	</c:if>
	<c:forEach var="cur" items="${entpBaseLink1}" varStatus="vs">
		   <div class="mui-content" style="background-color:#fff">
				<div class="mui-card">
					<ul class="mui-table-view">
						<li class="mui-table-view-cell mui-collapse">
							<a class="mui-navigate-right" href="">${cur.title}</a>
							<div class="mui-collapse-content">
								<c:forEach var="soncur" items="${cur.map.sonBaseLink}" varStatus="vs">
									<c:if test="${ not empty soncur.content}">
											${soncur.content}
									</c:if>
									<c:if test="${ not empty soncur.image_path}">
											<img src="${ctx}/${soncur.image_path}"/>
								    </c:if>
								</c:forEach>
							</div>
						</li>
					</ul>
				</div>
		  </div>
	    </c:forEach>
<script type="text/javascript">//<![CDATA[
	var f = document.forms[0];
		//]]></script>
</body>
</html>
