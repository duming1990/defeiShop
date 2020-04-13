<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
</head>
<body>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/admin/UserInfo100.do">
    <html-el:hidden property="queryString" />
    <html-el:hidden property="method" value="saveRoleUser" />
    <html-el:hidden property="user_id" styleId="user_id" />
     <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th colspan="2">用户角色分配</th>
      </tr>
      <tr>
        <td width="15%" class="title_item">已分配角色：</td>
        <td width="85%">
        <c:forEach items="${roleHasSetList}" var="cur" varStatus="vs">
        ${cur.role_name} &nbsp;<a onclick="cancleRoleUser(${cur.id},${af.map.user_id})" class="tip-danger">取消</a><c:if test="${not vs.last}">、</c:if>
        </c:forEach>
        </td>
      </tr>
      <tr>
        <td class="title_item">分配角色：</td>
        <td>
              <c:forEach items="${roleNotSetList}" var="cur">
              <html-el:checkbox property="role_ids" value="${cur.id}" styleId="role_ids_${cur.id}"></html-el:checkbox>
              <label for="role_ids_${cur.id}">${cur.role_name}</label>&nbsp;
        </c:forEach>
        <c:if test="${empty  roleNotSetList}">
        您还没有添加后台管理用户角色，请到“<span class="label label-danger">角色授权管理</span>”中添加
<%--         <c:url var="url" value="/manager/admin/Role.do?method=add&mod_id=1002002000" /> --%>
<%--         <a href="${url}" class="label label-danger"></a> --%>
        </c:if>
        </td>
      </tr>
      <tr>
        <td colspan="2" style="text-align: center"><html-el:button property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
          &nbsp;
          <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
      </tr>
    </table>
  </html-el:form>
</div>

<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script> 
<script type="text/javascript">//<![CDATA[
var f = document.forms[0];
$(document).ready(function(){   
	$("#btn_submit").click(function() {
		if (Validator.Validate(f, 3)) {
			$("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
			$("#btn_reset").attr("disabled", "true");
			$("#btn_back").attr("disabled", "true");
			f.submit();
		}
	});
});

function cancleRoleUser(role_id, user_id) {
	
	var submit2 = function(v, h, f) {
		if (v == "ok") {
			$.jBox.tip("加载中...", "loading");
			$.post("?method=cancleRoleUser",{role_id : role_id, user_id : user_id},function(data){
		     if(data.code == 1){
		    	 $.jBox.tip(data.msg, "success");
		    	 window.setTimeout(function () {location.reload();}, 1000);
		     }else{
		    	 $.jBox.tip(data.msg, "error");
		     }
			});
		}
		return true
	};
	var tip = "确定取消角色吗？";
	$.jBox.confirm(tip, "确定提示", submit2)

}
//]]>
</script>
<jsp:include page="../public_page.jsp" flush="true" />
</body>
</html>
