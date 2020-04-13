<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base target="_self" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
<title>
选择企业
</title>
<style type="text/css">
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
  <html-el:form action="BaseCsAjax.do">
    <html-el:hidden property="method" value="chooseEntpInfo2" />
    <html-el:hidden property="dir"/>
    <table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="tableClassSearch">
      <tr>
		<th align="left" nowrap="nowrap"> 
          	企业名称：
          <html-el:text property="entp_name_like" styleClass="webinput" maxlength="100"/>
          	用户名：
          <html-el:text property="user_name_like" styleClass="webinput" maxlength="100"/>
          &nbsp;
          <input name="submit" type="submit" style="cursor:pointer;" class="bgButton" value="查 询" /></th>
      </tr>
    </table>
  </html-el:form>
<div style="padding: 5px;"></div>
    <table width="100%" border="0" cellpadding="1" cellspacing="1" class="backTable">
          <tr class="tite2">
            <th align="center" nowrap="nowrap">企业名称</th>
            <th align="center" nowrap="nowrap">用户名</th>
            <th width="20%" align="center" nowrap="nowrap">操作</th>
          </tr>
          <c:if test="${not empty entityList}">
          <c:forEach var="cur" items="${entityList}" varStatus="vs">
            <tr>
              <td align="left">${fn:escapeXml(cur.entp_name)} </td>
              <td align="left">${fn:escapeXml(cur.add_user_name)} </td>
              <td align="center">
               <span class="bgButtonFontAwesome" onclick="returnEntpInfo('${cur.id}','${cur.entp_name}','${cur.add_user_name}');">
                <a><i class="fa fa-check-square"></i>选择</a>
              </span>
              </td>
            </tr>

          </c:forEach>
          </c:if>
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
  
  
  
  
  <form id="bottomPageForm" name="bottomPageForm" method="post" action="BaseCsAjax.do?method=chooseEntpInfo2&dir=customer">
    <table width="750" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td height="40" align="center"><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script> 
          <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
            pager.addHiddenInputs("method", "chooseEntpInfo");
            pager.addHiddenInputs("dir", "${fn:escapeXml(af.map.dir)}");
            pager.addHiddenInputs("entp_name_like", "${fn:escapeXml(af.map.entp_name_like)}");
            pager.addHiddenInputs("user_name_like", "${fn:escapeXml(af.map.user_name_like)}");
            pager.addHiddenInputs("entp_type", "${af.map.entp_type}");
            pager.addHiddenInputs("own_sys", "${af.map.own_sys}");
            document.write(pager.toString());
            </script></td>
      </tr>
    </table>
  </form>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script> 
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

function returnEntpInfo(val0,val1,val2){
	var api = frameElement.api, W = api.opener;
	
	W.document.getElementById("own_entp_id").value = val0;
	<c:if test="${empty af.map.isParEntp}">
	if(null != W.document.getElementById("entp_name")){
		W.document.getElementById("entp_name").value = val1;
		W.document.getElementById("own_entp_id").value = val0;
	}
	if(null != W.document.getElementById("add_user_name_span")){
		W.document.getElementById("add_user_name_span").innerHTML = "用户名："+val2;
	}
	</c:if>
	if(null != W.document.getElementById("par_entp_name")){
		W.document.getElementById("par_entp_name").value = val1;
	}
	
	//$(W.document.getElementById("view_a")).attr("href","${ctx}/manager/all/CommInfo.do?method=view&pd_id=" + val0).show();
	api.close();
}
//]]></script>
</body>
</html>