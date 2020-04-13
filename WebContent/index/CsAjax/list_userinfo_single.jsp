<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>请选择用户</title>
<base target="_self" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="divContent" style="margin: 5px;"> 
  <html-el:form action="/CsAjax">
    <html-el:hidden property="method" value="getUserInfo" />
    <html-el:hidden property="user_type" />
    <html-el:hidden property="getIdCard" />
    <table width="99%" border="0" cellpadding="1" cellspacing="1" class="tableClassSearch">
      <tr>
        <td>用户姓名:
          <html-el:text property="user_name_like" maxlength="20" styleId="nurse_name_like" style="width:80px;" styleClass="webinput" /> 
           &nbsp;手机号码:
          <html-el:text property="mobile_like" maxlength="20" styleId="mobile_like" style="width:80px;" styleClass="webinput" /> 
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
        <th width="5%" nowrap="nowrap">序号</th>
        <th nowrap="nowrap">用户姓名</th>
        <th nowrap="nowrap">手机号码</th>
        <th width="12%" nowrap="nowrap">选择</th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr>
          <td align="center" nowrap="nowrap">${vs.count}</td>
          <td align="left" nowrap="nowrap"><span>${fn:escapeXml(cur.user_name)}</span></td>
          <td align="center" nowrap="nowrap"><span>${fn:escapeXml(cur.mobile)}</span></td>
          <td align="center"><a class="butbase" href="javascript:void(0);"><span class="icon-ok" onclick="selectUser(${cur.id},'${fn:escapeXml(cur.user_name)}','${fn:escapeXml(cur.id_card)}');">选择</span></a></td>
        </tr>
      </c:forEach>
    </table>
  </form>
  <div class="pageClass">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="CsAjax.do">
      <table width="98%" border="0" cellpadding="0" cellspacing="0">
        <tr align="center">
          <td><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script> 
            <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
            pager.addHiddenInputs("method", "getUserInfo");
            pager.addHiddenInputs("user_name_like", "${fn:escapeXml(af.map.user_name_like)}");
			pager.addHiddenInputs("is_del", "${af.map.is_del}"); 
			pager.addHiddenInputs("mobile_like", "${af.map.mobile_like}"); 
			pager.addHiddenInputs("sex", "${af.map.sex}");
			pager.addHiddenInputs("user_type", "${af.map.user_type}");
			pager.addHiddenInputs("getIdCard", "${af.map.getIdCard}");
            document.write(pager.toString());
            </script></td>
        </tr>
      </table>
    </form>
  </div>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script> 
<script type="text/javascript">//<![CDATA[
function selectUser(add_uid, add_uname,id_card) {
	var api = frameElement.api, W = api.opener;
	W.document.getElementById("user_id").value = add_uid;
	W.document.getElementById("user_name").value = add_uname;
	<c:if test="${not empty af.map.getIdCard}">
	 W.document.getElementById("id_card").value = id_card;
	 W.valIsHasAudit(add_uid);
	</c:if>
	api.close();
}

//]]></script>
</body>
</html>
