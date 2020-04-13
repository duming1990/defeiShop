<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="${fn:escapeXml(fnx:abbreviate(af.map.content, 2 * 50, ''))}" />
<meta name="keywords" content="${fn:escapeXml(af.map.title)}" />
<title>${fn:escapeXml(af.map.title)} - ${fn:escapeXml(mod_name)} - ${app_name}</title>
<jsp:include page="../../_public_header.jsp" flush="true" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/newscontent.css"  />
<style type="">
#content img{max-width: 100% !important;height: auto !important;}
</style>
<style type="">
#bd{
    width: 1200px;
    top:0px;
}
</style>
</head>
<body class="pg-deal pg-deal-default pg-deal-detail" id="deal-default" style="position: static;">
<jsp:include page="../../_header.jsp" flush="true" />
<div class="bdw">
 <div id="bd" class="cf">
<div class="newnavdown" style="padding-bottom:10px;">
  <c:url var="url" value="/index.do"></c:url>
  <c:url var="urlnews" value="/IndexWebsiteIntroduction.do?method=list&mod_id=${fn:escapeXml(af.map.mod_id)}" />
  <div class="neone"><a href="${url}">首页</a> &gt; <a href="${urlnews}">${fn:escapeXml(mod_name)}</a> &gt; <strong>列表</strong> </div>
</div>
   <!----内容开始---->
<div id="content">
   <div class="mainbox">
     <ul class="nav-tabs--small cf">
     </ul>
     <h1 class="headline"><span class="headline__content">${fn:escapeXml(mod_name)}</span></h1>
     <ul class="newslist">
     <c:forEach var="cur" items="${newsInfoList}" varStatus="vs">
      <li>
      <a href="IndexWebsiteIntroduction.do?method=viewById&id=${cur.id}&mod_id=${cur.mod_id}"><span><fmt:formatDate value="${cur.pub_time}" pattern="yyyy-MM-dd"></fmt:formatDate></span>${fn:escapeXml(cur.title)}</a>
       <p>${fn:escapeXml(cur.title)}</p>
      </li>
      </c:forEach>
     </ul>
   </div>
   <div style="text-align:center" class="black">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="${ctx}/IndexWebsiteIntroduction.do">
      <table width="98%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td height="10"><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
				var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
		        pager.addHiddenInputs("method", "list");
				pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
		        document.write(pager.toString());
            	</script></td>
        </tr>
      </table>
    </form>
  </div>
  </div>
   <!----内容结束----->
<!---------侧边开始------------>
<jsp:include page="left.jsp" flush="true"/>
<!---------侧边结束------------>
 </div>
 </div>
<jsp:include page="../../_footer.jsp" flush="true" />
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
});
//]]>
</script>
</body>
</html>
