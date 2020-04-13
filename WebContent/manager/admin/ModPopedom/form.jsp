<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/scripts/colorbox/style3/colorbox.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/admin/ModPopedom.do">
    <html-el:hidden property="method" value="save" />
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="role_id" />
    <html-el:hidden property="user_id" />
    <html-el:hidden property="url" />
    <html-el:hidden property="isShouQuanUserTpye23" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th width="5%">层级</th>
        <th width="20%">栏目授权</th>
        <th>权限设置</th>
        <th width="10%">选择</th>
      </tr>
      <c:forEach var="cur" items="${sysModuleList}">
        <c:set var="isChecked" value="${false}" />
        <c:set var="parModelStyle" value=""></c:set>
        <c:if var="urlIsNull" test="${cur.mod_url eq null or empty cur.mod_url}">
        <c:set var="parModelStyle" value="font-weight: bold;"></c:set>
        </c:if>
        <tr>
          <td style="text-align:center;">${cur.mod_level}</td>
          <td style="text-align:left;" nowrap="nowrap"><c:forEach begin="0" end="${cur.mod_level}">&nbsp;</c:forEach>
            <span style="${parModelStyle}">${cur.mod_name}</span>
            </td>
          <td style="text-align:left;"><div style="padding-left:5px;">
              <c:forEach var="basePopedom" items="${cur.basePopedomList}">
                <logic-el:present name="mod_popedom_${cur.mod_id}">
                  <c:set var="isChecked" value="${false}" />
                  <logic-el:iterate id="selectedModPopedom" name="mod_popedom_${cur.mod_id}">
                    <c:if var="isChecked" test="${(selectedModPopedom eq basePopedom.ppdm_code) or isChecked}" />
                  </logic-el:iterate>
                </logic-el:present>
                <c:if test="${isChecked}">
                  <input type="checkbox" id="checkbox_${cur.mod_id}_${basePopedom.ppdm_code}" name="checkbox_${cur.mod_id}" value="${basePopedom.ppdm_code}" onclick="checkFirst('checkbox_${cur.mod_id}', this);" checked="checked"/>
                </c:if>
                <c:if test="${not isChecked}">
                  <input type="checkbox" id="checkbox_${cur.mod_id}_${basePopedom.ppdm_code}" name="checkbox_${cur.mod_id}" value="${basePopedom.ppdm_code}" onclick="checkFirst('checkbox_${cur.mod_id}', this);" />
                </c:if>
                <label for="checkbox_${cur.mod_id}_${basePopedom.ppdm_code}">
                  <c:out value="${basePopedom.ppdm_desc}" />
                </label>
              </c:forEach>
            </div></td>
          <td style="text-align:center;"><input type="checkbox" id="checkbox_${cur.mod_id}" onclick="checkRow('checkbox_${cur.mod_id}', this);" />
            <label for="checkbox_${cur.mod_id}"> 行选择 </label></td>
        </tr>
      </c:forEach>
      <tr>
        <td colspan="4" align="center">
          <html-el:checkbox property="all" onclick="checkAll(this)"> 选择全部 </html-el:checkbox>
          <html-el:submit value="确 定" styleClass="bgButton" />&nbsp;
          <html-el:button property="back" styleClass="bgButton" value="返 回" onclick="history.back();" />
          </td>
      </tr>
    </table>
  </html-el:form>
</div>

<script type="text/javascript" src="${ctx}/scripts/colorbox/jquery.colorbox.min.js"></script> 
<script type="text/javascript">//<![CDATA[
$("a.beautybgButton").colorbox({width:"90%", height:"80%", iframe:true});
                                          
function checkAll(e) {
	for (var i = 01; i < e.form.elements.length; i++) {
		if (e.form.elements[i].type == "checkbox") {
			e.form.elements[i].checked = e.checked;
		}
	}
}

function checkRow(name, cb) {
	var e = document.getElementsByName(name);
	for(var i = 0; i < e.length; i++) {
		e[i].checked = cb.checked;
	}
}

function checkFirst(name, cb) {
	return;
//	var e = document.getElementsByName(name);
//	for(var i = 0; i < e.length; i++) {
//		if (e[i].checked == true) {
//			e[0].checked = true;
//			break;
//		}
//	}
}

$("#btn_submit").click(function(){
     $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
     this.form.submit();
});
//]]></script>
<jsp:include page="../public_page.jsp" flush="true" />
</body>
</html>
