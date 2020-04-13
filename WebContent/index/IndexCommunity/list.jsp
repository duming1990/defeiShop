<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${mod_name} - ${app_name}</title>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<link href="${ctx}/styles/indexv3/css/top.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/indexv3/css/global.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/indexv3/css/fonts.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/indexv2/css/nmnetwork.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/indexv2/css/netlist.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/indexv3/css/bottom.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/indexv2/css/left_sort.css" rel="stylesheet" type="text/css" />
</head>
<body>
<jsp:include page="../../_header.jsp" flush="true" />
<div class="newnavdown">
  <c:url var="url" value="/index.do"></c:url>
  <c:url var="urlnews" value="/IndexCommunity.do?method=list&mod_code=1003005000" />
  <div class="neone"><a href="${url}">首页</a> &gt; <a href="${urlnews}">社区新闻</a> &gt; <strong>列表</strong> </div>
</div>
<!-- list start -->
<div class="listcont"> 
  <!-- left -->
  <div class="listleft">
    <div class="listab1">
      <div class="listit2">热门快报</div>
      <ul class="listul2">
        <jsp:include page="../../public/_index_tjzx.jsp" flush="true" />
      </ul>
    </div>
    <div class="listbot2"></div>
    <div class="listab1 martop8">
      <div class="listit2">品牌推荐</div>
      <ul class="rp1">
        <jsp:include page="../../public/_index_qytgfw.jsp" flush="true" />
      </ul>
    </div>
    <div class="listbot2"></div>
  </div>
  <!-- right -->
  <div class="listright">
    <div class="picone"><img src="${ctx}/styles/indexv2/images/banner.jpg" height="85" /></div>
    <div style="padding: 5px;"></div>
    <div class="listnews">
      <ul>
        <c:forEach var="cur" items="${newsInfoList}" varStatus="vs">
          <c:url var="url" value="/IndexCommunity.do?uuid=${cur.uuid}"></c:url>
          <li><span>
            <fmt:formatDate value="${cur.pub_time}" pattern="yyyy-MM-dd"></fmt:formatDate>
            </span><a href="${url}" target="_blank" title="${cur.title}">${fn:escapeXml(fnx:abbreviate(cur.title, 2 * 42, "..."))}</a></li>
          <c:if test="${vs.last eq true}">
            <c:set var="i" value="${vs.count}" />
          </c:if>
        </c:forEach>
      </ul>
      <c:forEach begin="${i}" end="26"> <br/>
      </c:forEach>
      <div style="line-height:40px; text-align:center; margin-top:10px;">
        <c:url var="url" value="/IndexCommunity.do"/>
        <form id="bottomPageForm" name="bottomPageForm" method="post" action="${url}">
          <table width="98%" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td height="10"><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script> 
                <script type="text/javascript">
						var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
						pager.addHiddenInputs("method", "list");
						pager.addHiddenInputs("mod_code", "${af.map.mod_code}");
						pager.addHiddenInputs("title_like", "${fn:escapeXml(af.map.title_like)}");
						document.write(pager.toString());
		            	</script></td>
            </tr>
          </table>
        </form>
      </div>
    </div>
  </div>
</div>
<div class="clear"></div>
<!-- list end -->
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
