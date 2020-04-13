<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${mod_name} - ${app_name}</title>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<jsp:include page="../../_public_header.jsp" flush="true" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/newscontent.css"  />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/pages.css"  />
<style type="">
#bd{
    width: 1200px;
    top:0px;
}
</style>
</head>
<body>
<jsp:include page="../../_header.jsp" flush="true" />
<div class="bdw">
 <div id="bd" class="cf">
<!----内容开始---->
  <div id="content">
   <div class="mainbox">
     <h1 class="headline"><span class="headline__content">${fn:escapeXml(mod_name)}</span></h1>
     <ul class="newslist">
     <c:forEach var="cur" items="${entityList}" varStatus="vs">
      <li>
      <c:url var="url" value="/IndexHelpInfo.do?method=view&h_mod_id=${cur.h_mod_id}&id=${cur.id}"></c:url>
      <a href="${url}" target="_blank" title="${fn:escapeXml(cur.title)}">
      <span><fmt:formatDate value="${cur.pub_date}" pattern="yyyy/MM/dd"></fmt:formatDate></span>
      ${fn:escapeXml(fnx:abbreviate(cur.title, 2 * 40, "..."))}</a>
      </li>
      </c:forEach>
     </ul>
    <div class="pages">
    <c:url var="url" value="/IndexHelpInfo.do"/>
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="${url}">
    <ul id="pagination" class="pagination"></ul>
     <script type="text/javascript" src="${ctx}/commons/pager/pagination.home.js">;</script>
       <script type="text/javascript">
          $.fn.pagination.addHiddenInputs("method", "list");
          $.fn.pagination.addHiddenInputs("h_mod_id", "${af.map.h_mod_id}");
      	  $("#pagination").pagination({
      		pageForm : document.bottomPageForm,
      		recordCount : parseFloat("${af.map.pager.recordCount}"),
      		pageSize : parseFloat("${af.map.pager.pageSize}"),
      		currentPage : parseFloat("${af.map.pager.currentPage - 1}")
      	  });
       </script>
    </form>
  </div>
 </div>
</div>
<!----内容结束----->
 </div>
</div>
<jsp:include page="../../_footer.jsp" flush="true" />
<script type="text/javascript" src="${ctx}/scripts/tabs/tabs.switch.min.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	var f = document.forms[1];// 第二个form,第一个为header页面中的登录

	$("#sousuo_submit").click(function(){
		f.submit();
	});
	
});
//]]>
</script>
</body>
</html>
