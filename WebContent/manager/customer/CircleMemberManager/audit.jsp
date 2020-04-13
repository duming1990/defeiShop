<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />  
<link href="${ctx}/commons/styles/icons/icons.css" rel="stylesheet" type="text/css" />   
</head>
<c:set var="type_name" value="商家"/>
<body>
<div class="divContent">
 <jsp:include page="../_nav.jsp" flush="true"/>
  <html-el:form action="/customer/CircleMemberManager.do" enctype="multipart/form-data">
    <html-el:hidden property="queryString" styleId="queryString" />
    <html-el:hidden property="method" styleId="method" value="save" />
    <html-el:hidden property="mod_id" styleId="mod_id" />
    <html-el:hidden property="id" styleId="id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
      <tr>
        <th colspan="4">个人信息</th>
      </tr>
       <tr>
        <td width="14%" class="title_item">用户名：</td>
        <td><c:out value="${af.map.user_name}"/></td>
        <td width="14%" class="title_item">电话：</td>
        <td><c:out value="${af.map.mobile}"/></td>
      </tr>
        <tr>
        <td width="14%" class="title_item">真实姓名：</td>
        <td colspan="3"><c:out value="${af.map.real_name}"/></td>
      </tr>
      <tr>
        <td width="14%" class="title_item">联系QQ：</td>
        <td>${fn:escapeXml(af.map.appid_qq)}</td>
        <td width="14%" class="title_item">性别：</td>
        <td><c:out value="${af.map.sex eq 0 ? '男' : '女'}"/></td>
      </tr>
       <tr>
        <td width="14%" class="title_item">出生日期：</td>
        <td><fmt:formatDate value="${af.map.birthday}" pattern="yyyy-MM-dd"/></td>
        <td width="14%" class="title_item">电子邮箱：</td>
        <td><c:out value="${af.map.email}"/></td>
      </tr>
      <tr>
        <td width="14%" class="title_item">所属区域：</td>
        <td colspan="3">${full_name}</td>
      </tr>
      <tr>
        <th colspan="4">审核信息</th>
      </tr>
       <c:if test="${not empty choose_person_user}">
       <tr>
          <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>用户：</td>
          <td colspan="2" width="88%"><html-el:text property="user_name" styleId="user_name" maxlength="125" style="width:280px" styleClass="webinput" readonly="true"/>
              &nbsp;<a class="butbase" onclick="openUser();"><span class="icon-search">选择</span></a>
          </td>
       </tr>
      </c:if>
      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>审核状态：</td>
        <td colspan="3"><html-el:select property="audit_state" styleId="audit_state">
            <html-el:option value="">请选择...</html-el:option>
            <html-el:option value="0">待审核</html-el:option>
            <html-el:option value="-1">审核不通过</html-el:option>
            <html-el:option value="1">审核通过</html-el:option>
          </html-el:select></td>
      </tr>
      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>审核意见:</td>
        <td colspan="3">
        <html-el:text property="audit_desc" styleId="audit_desc" maxlength="125" style="width:480px" styleClass="webinput" /></td>
      </tr>
      <tr>
        <td colspan="4" align="center">
        <html-el:button property="" value="审 核" styleClass="bgButton" styleId="btn_submit" />
          &nbsp;
          <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
      </tr>
    </table>
  </html-el:form>
</div>
 
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/jquery-ui/external/jquery.bgiframe.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/jquery-ui/ui/minified/jquery-ui.custom.min.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	$("#audit_state").attr("dataType", "Require").attr("msg", "请选择审核状态！");
	$("#audit_desc").attr("dataType", "Require").attr("msg", "请填写审核意见！");
	var f = document.forms[0];

	$("#btn_submit").click(function(){
		if(Validator.Validate(f, 3)){
	            $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
	            $("#btn_reset").attr("disabled", "true");
	            $("#btn_back").attr("disabled", "true");
				f.submit();
		}
		return false;
	});
});

function viewMap(latlng){
	var url = "${ctx}/CsAjax.do?method=viewBMap&latlng=" + latlng;
	$.dialog({
		title:  "查看坐标",
		width:  900,
		height: 520,
		padding: 0,
		max: false,
        min: false,
        fixed: true,
        lock: true,
		content:"url:"+ encodeURI(url)
	});
}
function openUser(){
	
	var url = "${ctx}/BaseCsAjax.do?method=chooseUserInfo&dir=admin";
	$.dialog({
		title:  "选择用户",
		width:  770,
		height: 550,
        lock:true ,
		content:"url:"+url
	});
}
//]]></script>
</body>
</html>