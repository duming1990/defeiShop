<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>请选择类别</title>
<base target="_self" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
</head>
<body>
<div class="divContent" style="margin: 5px;"> 
  <html-el:form action="/admin/BaseLink">
    <html-el:hidden property="method" value="listPdClass" />
    <table width="99%" border="0" cellpadding="0" cellspacing="0" class="tableClassSearch">
      <tr>
        <td>类别名称:
          <html-el:text property="cls_name_like" maxlength="20" styleId="nurse_name_like" style="width:80px;" styleClass="webinput" /> 
            &nbsp;
            <html-el:submit value="查 询" styleClass="bgButton" />
        </td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" >
    <table width="99%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th width="5%" nowrap="nowrap">序号</th>
        <th nowrap="nowrap">类别名称</th>
        <th width="12%" nowrap="nowrap">选择</th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr>
          <td align="center" nowrap="nowrap">${vs.count}</td>
          <td align="left" nowrap="nowrap"><span>${fn:escapeXml(cur.cls_name)}</span></td>
          <td align="center"><a class="butbase" href="javascript:void(0);"><span class="icon-ok" onclick="selectCls(${cur.cls_id},'${fn:escapeXml(cur.cls_name)}');">选择</span></a></td>
        </tr>
      </c:forEach>
    </table>
  </form>
  <div class="pageClass">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="BaseLink.do">
      <table width="98%" border="0" cellpadding="0" cellspacing="0">
        <tr align="center">
          <td><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script> 
            <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
            pager.addHiddenInputs("method", "listPdClass");
            pager.addHiddenInputs("cls_name_like", "${fn:escapeXml(af.map.cls_name_like)}");
            document.write(pager.toString());
            </script></td>
        </tr>
      </table>
    </form>
  </div>
</div>
 
<script type="text/javascript">//<![CDATA[
function selectCls(cls_id, cls_name) {
	var api = frameElement.api, W = api.opener;
	W.document.getElementById("cls_id").value = cls_id;
	W.document.getElementById("cls_name").value = cls_name;
	if(W.document.getElementById("title")){
		W.document.getElementById("title").value = cls_name;
	}
	<c:if test="${af.map.link_type eq 20}">
		W.document.getElementById("link_url").value ="http://"+ window.location.host+ "/search-"+ cls_id +".shtml"; 
	</c:if>
	api.close();
}

//]]></script>
</body>
</html>
