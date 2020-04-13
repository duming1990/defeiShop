<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-

transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base target="_self" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
<title>选择商品名称</title>
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
  <html-el:form action="/CsAjax">
    <html-el:hidden property="method" value="getBaseCommCodeList" />
    <html-el:hidden property="dir" styleId="dir"/>
    <html-el:hidden property="num" styleId="num"/>
    <html-el:hidden property="ajax"/>
    <html-el:hidden property="p_indexs"/>
    <html-el:hidden property="comm_type"/>
    <html-el:hidden property="own_entp_id"/>
    <html-el:hidden property="noNeedState"/>
    <table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="tableClassSearch">
      <tr>
        <th align="left" nowrap="nowrap">
          	商品名称：
          <html-el:text property="comm_name_like" styleClass="webinput" maxlength="50"/>
          	商品名称：
          <html-el:text property="comm_code_like" styleClass="webinput" maxlength="50"/>
          &nbsp;
          <input name="submit" type="submit" style="cursor:pointer;" class="bgButton" value="查 询" /></th>
      </tr>
    </table>
  </html-el:form>
  
  <div>
  <table width="100%" border="0" cellpadding="1" cellspacing="1" class="backTable">
          <tr class="tite2">
            <th width="50%" align="left" nowrap="nowrap">商品名称</th>
            <th align="center" nowrap="nowrap">条形码</th>
            <th width="20%" align="center" nowrap="nowrap">操作</th>
          </tr>
          <c:forEach var="cur" items="${entityList}" varStatus="vs">
            <tr>
              <td align="left">${fn:escapeXml(cur.comm_name)} </td>
              <td align="center" nowrap="nowrap"><span>${fn:escapeXml(cur.comm_code)}</span></td>
              <td align="center">
              <span class="bgButtonFontAwesome" onclick="selectCommCode('${cur.comm_name}','${cur.comm_code}','${cur.id}');">
                <a><i class="fa fa-check-square"></i>选择</a>
              </span>
              </td>
            </tr>
          </c:forEach>
        </table>
  </div>
  <form id="bottomPageForm" name="bottomPageForm" method="post" action="CsAjax.do">
    <table width="750" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td height="40" align="center"><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script> 
          <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
            pager.addHiddenInputs("method", "getBaseCommCodeList");
            pager.addHiddenInputs("dir", "${fn:escapeXml(af.map.dir)}");
            pager.addHiddenInputs("comm_name_like", "${fn:escapeXml(af.map.comm_name_like)}");
            pager.addHiddenInputs("comm_code_like", "${fn:escapeXml(af.map.comm_code_like)}");
            pager.addHiddenInputs("num", "${fn:escapeXml(af.map.num)}");
            pager.addHiddenInputs("ajax", "${fn:escapeXml(af.map.ajax)}");
            pager.addHiddenInputs("p_indexs", "${fn:escapeXml(af.map.p_indexs)}");
            pager.addHiddenInputs("noNeedState", "${fn:escapeXml(af.map.noNeedState)}");
            pager.addHiddenInputs("own_entp_id", "${fn:escapeXml(af.map.own_entp_id)}");
            document.write(pager.toString());
            </script></td>
      </tr>
    </table>
  </form>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script> 
<script type="text/javascript">//<![CDATA[
function selectCommCode(comm_name, comm_code, id) {
	var api = frameElement.api, W = api.opener;
	W.document.getElementById("comm_name").value = comm_name;
	W.document.getElementById("comm_code").value = comm_code;
	W.document.getElementById("comm_code_id").value = id;
	W.setDefaultCommAttr(id);
	api.close();
}

//]]></script>
</body>
</html>
