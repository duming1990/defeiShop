<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base target="_self" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
<title>选择帮助节点</title>
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
  
  <div>
  <table width="100%" border="0" cellpadding="1" cellspacing="1" class="backTable">
          <tr class="tite2">
            <th align="center" nowrap="nowrap">节点名称</th>
            <th width="20%" align="center" nowrap="nowrap">操作</th>
          </tr>
          <c:forEach var="cur" items="${entityList}" varStatus="vs">
            <tr>
              <td align="left">${fn:escapeXml(cur.mod_name)} </td>
              <td align="center">
              <span class="bgButtonFontAwesome" onclick="returnInfo('${cur.h_mod_id}','${cur.mod_name}');">
                <a><i class="fa fa-check-square"></i>选择</a>
              </span>
              </td>
            </tr>
          </c:forEach>
        </table>
  </div>
  <form id="bottomPageForm" name="bottomPageForm" method="post" action="BaseCsAjax.do">
    <table width="750" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td height="40" align="center"><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script> 
          <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
            pager.addHiddenInputs("method", "selectHelpInfo");
            document.write(pager.toString());
            </script></td>
      </tr>
    </table>
  </form>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script> 
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



function returnInfo(val0,val1){
	var api = frameElement.api, W = api.opener;
	W.document.getElementById("h_mod_id").value = val0;
	W.document.getElementById("title").value = val1;
	api.close();
}
//]]></script>

</body>
</html>