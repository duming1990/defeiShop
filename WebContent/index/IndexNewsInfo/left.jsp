<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>

<div id="sidebar" class="site-sidebar" >
	<div class="side-menu">
			<h3><a href="#">网站资讯</a></h3>
				<ul>
				<c:forEach items="${sModuleList}" var="cur" varStatus="vs">
					<c:url var="url" value="/IndexNewsInfo.do?method=list&mod_code=${cur.mod_id}&par_code=${cur.par_id}" />
					<li><a href="${url}">${cur.mod_name}</a></li>
				</c:forEach>
				</ul>
	</div>
</div>
