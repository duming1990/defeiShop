<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="${fn:escapeXml(fnx:abbreviate(af.map.content, 2 * 50, ''))}" />
<meta name="keywords" content="${fn:escapeXml(af.map.title)}" />
<title>${fn:escapeXml(af.map.title)} - ${fn:escapeXml(mod_name)} - ${app_name}</title>
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
  <div class="neone"><a href="${url}">首页</a> &gt; <a href="${urlnews}">社区新闻</a> &gt; <strong>详细</strong> </div>
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
      <div class="stop5">${fn:escapeXml(af.map.title)}</div>
      <div class="stop6"> 发布时间:
        <fmt:formatDate value="${af.map.pub_time}" pattern="yyyy-MM-dd"/>
        &nbsp;&nbsp; &nbsp;&nbsp; 作者: ${af.map.author eq null ? '匿名': fn:escapeXml(af.map.author)}&nbsp;&nbsp; &nbsp;&nbsp; 点击次数：${fn:escapeXml(af.map.view_count)}</div>
      <div class="stop7"></div>
      <div class="sintegright2" style="min-height: 400px;">
        <c:if test="${not empty af.map.image_path}">
          <div class="sintegrul22 " align="center">
            <c:set var="imgurl" value="${ctx}/${af.map.image_path}"/>
            <p><img src="${imgurl}" alt="${fn:escapeXml(af.map.title)}"/></p>
          </div>
        </c:if>
        <div class="sintegrul22">${af.map.content}</div>
        <c:if test="${not empty (attachmentList)}">
          <fieldset style="margin:5px; padding:10px; border:1px solid #ccc;">
            <legend style="font-size:12px;">附件下载</legend>
            <c:forEach var="cur" items="${attachmentList}" varStatus="vs"><a href="${ctx}/${cur.save_path}">${cur.file_name}</a><br />
            </c:forEach>
          </fieldset>
        </c:if>
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
	
});
//]]>
</script>
</body>
</html>
