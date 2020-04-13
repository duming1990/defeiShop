<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>

<div id="sidebar" class="site-sidebar" >
	<div class="side-menu">
			<h3><a href="#">公司介绍</a></h3>
				<ul>
				<c:forEach items="${sModuleList}" var="cur" varStatus="vs">
					<c:url var="url" value="/IndexWebsiteIntroduction.do?method=view&mod_code=${cur.mod_id}" />
					<li><a href="${url}">${cur.mod_name}</a></li>
				</c:forEach>
				</ul>
	</div>
</div>
