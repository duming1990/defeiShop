<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!-- nav start -->
<c:set var="ulStyle" value="" />
<c:if test="${(userInfo.is_entp eq 0) and (userInfo.is_fuwu eq 0) and (userInfo.is_village eq 0)}">
<c:set var="ulStyle" value="display:block" />
</c:if>
<ul class="filter cf" id="nav_ul_top" style="${ulStyle}">
  <c:forEach var="cur" items="${sonSysModuleList}" varStatus="vs">
    <c:url var="url" value="${cur.mod_url}" />
    <li data-type="${cur.mod_id}" >
      <a href="javascript:void(0)" onclick="updateCookiesUrl('${url}');"><span>${cur.mod_name}</span></a> </li>
  </c:forEach>
</ul>
<script type="text/javascript">//<![CDATA[
var thePlayer;                                           
$(document).ready(function(){
	
	$("#nav_ul_top li").each(function(){//导航回显
		
		var mod_id = "${af.map.mod_id}";
		if(null != mod_id && '' != mod_id){
			if($(this).attr("data-type") ==  mod_id){
				$(this).addClass("current");
				return false;
			}
		}
	}); 
});

function updateCookiesUrl(url){
	var parId_cookie = $.cookie("parId_cookie");
 	var parId;
 	if(parId_cookie){
 		var spl = parId_cookie.split(",");
		parId = spl[0];
 	}
 	if(parId){
		var parId_cookie_new = parId + "," + url;
 		if ($.isFunction($.cookie)) $.cookie("parId_cookie", parId_cookie_new, { path: '/' });
		location.href = url;
	}
}

//]]></script>
<!-- nav end -->
