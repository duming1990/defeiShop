<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>请选择用户</title>
<base target="_self" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx}/commons/bootstrap/default/bootstrap.min.css" />
<style type="">
.backTable td{
    padding: 5px 5px;
}
</style>
</head>
<body>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery-1.11.1.min.js"></script>
<div class="divContent" style="margin: 5px;"> 
  <html-el:form action="/CsAjax">
    <html-el:hidden property="method" value="getUserInfoForMobile" />
    <html-el:hidden property="user_type" />
    <table width="99%" border="0" cellpadding="1" cellspacing="1" class="tableClassSearch">
      <tr>
        <td>用户姓名:
          <html-el:text property="user_name_like" maxlength="20" styleId="nurse_name_like" style="width:80px;" styleClass="webinput" /> 
            &nbsp;
          <html-el:submit value="查 询" styleClass="bgButton" />
        </td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" >
    <table width="99%" border="0" cellpadding="1" cellspacing="1" class="backTable">
      <tr>
        <th nowrap="nowrap">用户姓名</th>
        <th width="12%" nowrap="nowrap">选择</th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr>
          <td align="left" nowrap="nowrap"><span>${fn:escapeXml(cur.real_name)}</span></td>
          <td align="center"><a class="butbase" href="javascript:void(0);"><span class="icon-ok" onclick="selectUser(${cur.id},'${fn:escapeXml(cur.real_name)}');">选择</span></a></td>
        </tr>
      </c:forEach>
    </table>
  </form>
  
  <div class="dataTables_paginate paging_bootstrap" align="center">
            <form id="bottomPageForm" name="bottomPageForm" method="post" action="CsAjax.do">
            <ul id="pagination" class="pagination"></ul>
            <script type="text/javascript" src="${ctx}/commons/pager/pagination.home.js">;</script> 
            <script type="text/javascript">
	    	$.fn.pagination.addHiddenInputs("method", "getUserInfoForMobile");
	    	$.fn.pagination.addHiddenInputs("user_name_like", "${fn:escapeXml(af.map.user_name_like)}");
	    	$.fn.pagination.addHiddenInputs("user_type", "${fn:escapeXml(af.map.user_type)}");
	    	$.fn.pagination.addHiddenInputs("mod_id", "${fn:escapeXml(mod_id)}");
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
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript">//<![CDATA[
function selectUser(add_uid, add_uname) {
	var api = frameElement.api, W = api.opener;
	W.document.getElementById("user_id").value = add_uid;
	W.document.getElementById("user_name").value = add_uname;
	api.close();
}

//]]></script>
</body>
</html>
