<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>规格库选择</title>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="mainbox mine">

 <html-el:form action="/admin/CommInfo" styleClass="searchForm">
    <html-el:hidden property="method" value="selectHasAttr" />
    <html-el:hidden property="cls_id" styleId="cls_id"/>
    <html-el:hidden property="comm_id" styleId="comm_id"/>
    <html-el:hidden property="ids" styleId="ids"/>
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tableClassSearch">
      <tr>
        <td>规格名称：
            <html-el:text property="attr_name_like" styleClass="webinput" maxlength="10" style="width:80px;"/>
            &nbsp;
            <button class="bgButtonFontAwesome" type="submit"><i class="fa fa-search"></i>查 询</button>
             &nbsp;
            <button class="bgButtonFontAwesome" type="button" onclick="submitHasCheck();"><i class="fa fa-plus-square"></i>确定所选</button>
          </td>
      </tr>
    </table>
  </html-el:form>


 <html-el:form action="/admin/CommInfo" enctype="multipart/form-data" styleClass="selectAttrForm">
  <html-el:hidden property="method" value="saveHasSelectAttr" />
  <html-el:hidden property="comm_id" styleId="comm_id"/>
  <html-el:hidden property="mod_id" />
  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tableClass" align="left">
    <tr>
      <td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="tableClass" align="left">
          <tr>
            <th width="10%" nowrap="nowrap"><input name="chkAll" type="checkbox" id="chkAll" value="-1" onclick="checkAll(this);" /></th>
            <th nowrap="nowrap" width="20%">套餐主规格名称 </th>
            <th nowrap="nowrap" width="25%">套餐子规格 </th>
          </tr>
          <c:forEach var="cur" items="${entityList}" varStatus="vs">
            <tr>
            <td align="center" nowrap="nowrap"><input name="pks" type="checkbox" id="pks_${cur.id}" value="${cur.id}" /></td>
              <td align="left" nowrap="nowrap">${fn:escapeXml(cur.attr_name)}</td>
              <td align="left">
              <c:forEach items="${cur.map.listBaseAttributeSon}" var="son">
                <c:out value="${son.attr_show_name}"></c:out>&nbsp; 
              </c:forEach></td>
            </tr>
          </c:forEach>
        </table></td>
    </tr>
    <tr>
      <td colspan="2" align="center">
          <button class="bgButtonFontAwesome" type="button" onclick="submitHasCheck();"><i class="fa fa-plus-square"></i>确定所选</button>
      </td>
    </tr>
  </table>
  </html-el:form>
</div>
<!-- main end -->
<script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script> 
<script type="text/javascript">//<![CDATA[
var api = frameElement.api, W = api.opener;
var form = document.forms[1];
$(document).ready(function(){
});

function submitHasCheck(){
	var checkedCount = 0;
	if (!form.pks) {
		return;
	}
	if(!form.pks.length) {
		if (form.pks.checked == true) {
			checkedCount = 1;
		}
	}
	for (var i = 0; i < form.pks.length; i++) {
		if (form.pks[i].checked == true) {
			checkedCount++;
		}
	}
	if (checkedCount == 0) {
		alert("你还未选择！");
	} else {
		$.ajax({
			type: "POST",
			url: "CommInfo.do?method=saveHasSelectAttr",
			data: $(".selectAttrForm").serialize(),
			dataType: "json",
			error: function(request, settings) {alert("调用接口失败！");},
			success: function(data) {
				if(data.code == 1){
					W.parentGoUrl('${ctx}/manager/admin/CommInfo.do?method=listattr&comm_id=${af.map.comm_id}');
					api.close();  
				}else{
					alert(data.msg);
				}
			}
		});
	}
}

//]]></script>
</body>
</html>
