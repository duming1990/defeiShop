<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base target="_self" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${ctx}/commons/styles/icons/icons.css" rel="stylesheet" type="text/css" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<title>
<c:if test="${not empty wl_comp_info}">
选择合作物流
</c:if>
</title>
<style type="text/css">
.ul_selpd {
}
.ul_selpd li {
	width: 130px;
	height: 18px;
	overflow: hidden;
	float: left;
	padding: 10px;
}
.ul_selpd li b {
	float: right;
	font-weight: normal;
	padding-left: 5px;
}
.inputText {
	border:1px solid #ccc;
	height:16px;
	line-height:16px;
	text-align:center;
}
table.TabTitle {
	border:1px solid #ccc;
	border-bottom:none;
	border-left:none;
}
table.TabTitle td {
	border-bottom:1px solid #ccc;
	border-left:1px solid #ccc;
	border-collapse:collapse;
	background:#F1F7FC;
	padding:6px 6px;
}
table.Tab {
	border:none;
}
table.Tab td {
	border-collapse:collapse;
	padding:5px 6px;
}
table.Tab td.next {
	background:#DBE9F7;
}
table.Tab td.page {
	text-align:right;
	background:#fff;
	padding-right:20px;
}
tr.alteven td {
	background: #ecf6fc;
}
tr.altodd td {
	background: #DBE9F7;
}
tr.over td {
	background: #BDD3E8;
}
table {
	table-layout: fixed;
}
</style>
</head>
<body>
<div align="center">
  <html-el:form action="/admin/CommInfo.do">
    <html-el:hidden property="method" value="chooseEntpInfo" />
    <html-el:hidden property="_entp_type" styleId="_entp_type"/>
    <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
		<th align="left" nowrap="nowrap"> 
         	物流/快递公司名称：
          <html-el:text property="wl_name_like" styleClass="webinput" maxlength="100"/>
          &nbsp;
          <input name="submit" type="submit" style="cursor:pointer;" class="bgButton" value="查 询" /></th>
      </tr>
    </table>
  </html-el:form>
<div style="padding: 5px;"></div>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
          <tr class="tite2">
            <th align="center" nowrap="nowrap">企业名称</th>
            <th width="20%" align="center" nowrap="nowrap">企业类型</th>
            <th width="20%" align="center" nowrap="nowrap">操作</th>
          </tr>
          <c:if test="${not empty wlCompInfoList}">
          <c:forEach var="cur" items="${wlCompInfoList}" varStatus="vs">
            <tr align="center">
              <td>${fn:escapeXml(cur.wl_comp_name)} </td>
              <td align="left">
              	<c:if test="${cur.comp_type eq 0}">快递</c:if>
            	<c:if test="${cur.comp_type eq 1}">物流</c:if>
			  </td>
              <td><a class="butbase" href="#" onclick="returnEntpInfo('${cur.id}','${cur.wl_comp_name}');" ><span class="icon-ok">选择</span></a></td>
            </tr>

          </c:forEach>
          </c:if>
        </table>
  
  <form id="bottomPageForm" name="bottomPageForm" method="post" action="CommInfo.do">
    <table width="750" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td height="40" align="center"><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script> 
          <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
            pager.addHiddenInputs("method", "chooseEntpInfo");
            pager.addHiddenInputs("wl_name_like", "${fn:escapeXml(af.map.wl_name_like)}");
            pager.addHiddenInputs("_entp_type", "${af.map._entp_type}");
            pager.addHiddenInputs("own_sys", "${af.map.own_sys}");
            document.write(pager.toString());
            </script></td>
      </tr>
    </table>
  </form>
</div>
 
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	$(".Tab tr").mouseover(function(){  
		$(this).addClass("over");
	}).mouseout(function(){
		$(this).removeClass("over");
	})
	$(".Tab tr:even").addClass("alteven");
	$(".Tab tr:odd").addClass("altodd");
});

function returnEntpInfo(val0,val1){
	var api = frameElement.api, W = api.opener;
	
	W.document.getElementById("own_entp_id").value = val0;
	W.document.getElementById("entp_name").value = val1;
	
	api.close();
}
//]]></script>
<jsp:include page="../../../_public_page.jsp" flush="true"/>
</body>
</html>