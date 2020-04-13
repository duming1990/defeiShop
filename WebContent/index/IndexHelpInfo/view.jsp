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
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/newscontent.css?v20160918"  />
<style type="">
#content img{max-width: 100% !important;height: auto !important;}
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
      <c:url var="urlnews" value="/IndexHelpInfo.do?method=view&h_mod_id=${af.map.h_mod_id}" />
      <div class="neone"><a href="${url}">首页</a> &gt; <a href="${urlnews}">${fn:escapeXml(mod_name)}</a> &gt; <strong>详细</strong> </div>
    </div>
    <!----内容开始---->
    <div id="content">
      <div class="mainbox">
        <h1 class="headline"><span class="headline__content">${fn:escapeXml(mod_name)}</span></h1>
        <div class="pg-commitment">
          <h1 style="text-align:center;margin-bottom:15px;">${fn:escapeXml(af.map.title)}</h1>
          <c:if test="${not empty af.map.image_path}">
            <div align="center">
              <c:set var="imgurl" value="${ctx}/${af.map.image_path}"/>
              <img src="${imgurl}" alt="${fn:escapeXml(af.map.title)}"/> </div>
          </c:if>
          <div class="conditions"> ${af.map.content} </div>
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
    <!----内容结束----->
    <!---------侧边开始------------>
    <jsp:include page="left.jsp" flush="true"/>
    <!---------侧边结束------------>
  </div>
</div>
<jsp:include page="../../_footer.jsp" flush="true" />
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	$("#content").find("img").each(function(){
		$(this).click(function(){
		var imgSrc = $(this).attr("src");
		$.dialog({
			 	lock:true,
		        max: false,
		        min: false,
		        zIndex:9999,
		        content: "<img src='"+ imgSrc +"'/>"
		       }).max();
		});
	});
});
//]]>
</script>
</body>
</html>
