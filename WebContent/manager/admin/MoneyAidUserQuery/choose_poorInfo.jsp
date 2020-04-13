<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base target="_self" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
<title>
选择用户
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
  <html-el:form>
    <html-el:hidden property="method" value="poorlist" />
    <html-el:hidden property="dir"/>
    <table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="tableClassSearch">
      <tr>
        <th>行政区划：&nbsp;<html-el:select property="province" styleId="province" styleClass="pi_prov" style="width:120px;">
	            <html-el:option value="">请选择...</html-el:option>
	          </html-el:select>
	          &nbsp;
	          <html-el:select property="city" styleId="city" styleClass="pi_city" style="width:120px;">
	            <html-el:option value="">请选择...</html-el:option>
	          </html-el:select>
	          &nbsp;
	          <html-el:select property="country" styleId="country" styleClass="pi_dist" style="width:120px;">
	            <html-el:option value="">请选择...</html-el:option>
	          </html-el:select>
	           &nbsp;
	          <html-el:select property="town" styleId="town" styleClass="pi_town" style="width:120px;">
	            <html-el:option value="">请选择...</html-el:option>
	          </html-el:select>
	           &nbsp;
	          <html-el:select property="village" styleId="village" styleClass="pi_village" style="width:120px;">
	            <html-el:option value="">请选择...</html-el:option>
	          </html-el:select>
	     </th>
      </tr>
      <tr>
		<th align="left" nowrap="nowrap"> 
          	用户名：
          <html-el:text property="user_name_like" styleClass="webinput" maxlength="100"/>
          &nbsp;待发扶贫金大于
          <html-el:text property="bi_aid_gt" styleClass="webinput" maxlength="100"/>&nbsp;元
          &nbsp;
          <input name="submit" type="submit" style="cursor:pointer;" class="bgButton" value="查 询" /></th>
      </tr>
    </table>
  </html-el:form>
<div style="padding: 5px;"></div>
    <table width="100%" border="0" cellpadding="1" cellspacing="1" class="backTable">
        <tr class="tite2">
          <th align="center" nowrap="nowrap">所在村</th>
          <th align="center" nowrap="nowrap">姓名</th>
          <th align="center" nowrap="nowrap">手机</th>
          <th align="center" nowrap="nowrap">待发扶贫金</th>
          <th align="center" nowrap="nowrap">已发扶贫金</th>
          <th width="20%" align="center" nowrap="nowrap">操作</th>
        </tr>
        <c:if test="${not empty entityList}">
        <c:forEach var="cur" items="${entityList}" varStatus="vs">
          <tr>
          	<td align="center">${fn:escapeXml(cur.addr)} </td>
            <td align="center">${fn:escapeXml(cur.real_name)} </td>
            <td align="center">${fn:escapeXml(cur.mobile)} </td>
            <td align="center"><fmt:formatNumber value="${cur.map.bi_aid}" pattern="#.##"></fmt:formatNumber>元</td>
            <td align="center"><fmt:formatNumber value="${cur.map.bi_aid_sended}" pattern="#.##"></fmt:formatNumber>元</td>
            <td align="center">
             <span class="bgButtonFontAwesome" onclick="returnUserInfo('${cur.map.user.id}','${cur.real_name}');">
              <a><i class="fa fa-check-square"></i>选择</a>
            </span>
            </td>
          </tr>
        </c:forEach>
        </c:if>
      </table>
  <form id="bottomPageForm" name="bottomPageForm" method="post" action="MoneyAidUserQuery.do">
    <table width="750" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td height="40" align="center"><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script> 
          <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
            pager.addHiddenInputs("method", "poorlist");
            pager.addHiddenInputs("dir", "${fn:escapeXml(af.map.dir)}");
            pager.addHiddenInputs("user_name_like", "${fn:escapeXml(af.map.user_name_like)}");
            pager.addHiddenInputs("province", "${fn:escapeXml(af.map.province)}");
            pager.addHiddenInputs("city", "${fn:escapeXml(af.map.city)}");
            pager.addHiddenInputs("country", "${fn:escapeXml(af.map.country)}");
            pager.addHiddenInputs("town", "${fn:escapeXml(af.map.town)}");
            pager.addHiddenInputs("village", "${fn:escapeXml(af.map.village)}");
            pager.addHiddenInputs("bi_aid_gt", "${fn:escapeXml(af.map.bi_aid_gt)}");
            document.write(pager.toString());
            </script></td>
      </tr>
    </table>
  </form>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script> 
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.cs.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	$("#province").attr({"subElement": "city", "defaultText": "请选择...", "defaultValue": "", "selectedValue": "${af.map.province}","datatype": "Require", "msg": "请选择省份"});
	$("#city").attr({"subElement": "country", "defaultText": "请选择...", "defaultValue": "", "selectedValue": "${af.map.city}","datatype": "Require", "msg": "请选择市"});
	$("#country").attr({"subElement": "town", "defaultText": "请选择...", "defaultValue": "", "selectedValue": "${af.map.country}","datatype": "Require", "msg": "请选择县"});
	$("#town").attr({"subElement": "village", "defaultText": "请选择...", "defaultValue": "", "selectedValue": "${af.map.town}","datatype": "Require", "msg": "请选择乡/镇"});
	$("#village" ).attr({"defaultText": "请选择...", "defaultValue": "", "selectedValue": "${af.map.village}","datatype": "Require", "msg": "请选择村"});
	$("#province").cs("${ctx}/BaseCsAjax.do?method=getBaseProvinceList", "p_index", "0", false);
	
	$(".Tab tr").mouseover(function(){  
		$(this).addClass("over");
	}).mouseout(function(){
		$(this).removeClass("over");
	})
	$(".Tab tr:even").addClass("alteven");
	$(".Tab tr:odd").addClass("altodd");
});

function returnUserInfo(val0,val1){
	var api = frameElement.api, W = api.opener;
	
	W.document.getElementById("user_id").value = val0;
	W.document.getElementById("real_name").value = val1;
	api.close();
}
//]]></script>
</body>
</html>