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
  <html-el:form action="/customer/PoorManager.do" enctype="multipart/form-data">
    <html-el:hidden property="queryString" styleId="queryString" />
    <html-el:hidden property="method" styleId="method" value="save" />
    <html-el:hidden property="mod_id" styleId="mod_id" />
    <html-el:hidden property="id" styleId="id" />
    <html-el:hidden property="is_audit" styleId="is_audit" value="true"/>
     <html-el:hidden property="person_user_id" styleId="person_user_id"/>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
      <tr>
        <th colspan="4">基本信息</th>
      </tr>
       <tr>
         <td width="14%"  class="title_item">申请区域：</td>
            <td colspan="3">
             <c:if test="${not empty full_name}">${fn:escapeXml(full_name)}</c:if>
            </td>
       </tr>
       <tr>
         <td width="14%"  class="title_item">昵称：</td>
         <td>${fn:escapeXml(af.map.pet_name)}</td>
         <td width="14%"  class="title_item">民族：</td>
         <td>${fn:escapeXml(af.map.nation)}</td>
       </tr>
       <tr>
         <td width="14%"  class="title_item">入乡时间：</td>
         <td><fmt:formatDate value="${af.map.residence_start_time}" pattern="yyyy-MM-dd" /></td>
          <td width="14%"  class="title_item">离乡时间：</td>
         <td><fmt:formatDate value="${af.map.residence_end_time}" pattern="yyyy-MM-dd" /></td>
       </tr>
       <tr>
         <td width="14%"  class="title_item">学历：</td>
         <td>${fn:escapeXml(af.map.education)}</td>
         <td width="14%"  class="title_item">毕业学校：</td>
         <td>${fn:escapeXml(af.map.graduate_school)}&nbsp;
         </td>
       </tr>
       <tr>
         <td width="14%"  class="title_item">个人简介：</td>
         <td>${fn:escapeXml(af.map.person_introduce)}</td>
       </tr>
      <tr>
        <th colspan="4">个人信息</th>
      </tr>
      <tr>
        <td class="title_item">真实姓名：</td>
        <td>${fn:escapeXml(af.map.real_name)}</td>
        <td class="title_item">头像：</td>
         <td><c:if test="${not empty af.map.head_logo}"> <a href="${ctx}/${af.map.head_logo}" title="头像" class="cb"> 
          <img height="100" src="${ctx}/${af.map.head_logo}@s400x400" /></a> </c:if></td>
      </tr>
       <tr>
        <td class="title_item">电话：</td>
        <td>${fn:escapeXml(af.map.mobile)}</td>
        <td class="title_item">性别：</td>
        <td><c:out value="${af.map.sex eq 0 ? '男' : '女'}"/></td>
      </tr>
       <tr>
        <td class="title_item">出生日期：</td>
        <td><fmt:formatDate value="${af.map.birthday}" pattern="yyyy-MM-dd"/></td>
        <td class="title_item">电子邮箱：</td>
        <td><c:out value="${af.map.email}"/></td>
      </tr>
      <tr>
         <td class="title_item">身份证号码：</td>
        <td colspan="3">${fn:escapeXml(af.map.id_card)}</td>
      </tr>
       <tr>
          <td class="title_item" >身份证正面：</td>
          <td><c:if test="${not empty af.map.img_id_card_zm}"> <a href="${ctx}/${af.map.img_id_card_zm}" title="身份证正面" class="cb"> 
          <img height="100" src="${ctx}/${af.map.img_id_card_zm}@s400x400" /></a> </c:if></td>
          <td class="title_item" >身份证背面：</td>
          <td><c:if test="${not empty af.map.img_id_card_fm}"> <a href="${ctx}/${af.map.img_id_card_fm}" title="身份证背面" class="cb"> 
          <img height="100" src="${ctx}/${af.map.img_id_card_fm}@s400x400" /></a> </c:if></td>
        </tr>
    
      <tr>
        <th colspan="4">审核信息</th>
      </tr>
      <tr>
        <td class="title_item">审核状态：</td>
        <td colspan="3">
	        <c:if test="${af.map.audit_state eq 0}">待审核</c:if>
	        <c:if test="${af.map.audit_state eq -1}">审核不通过</c:if>
	        <c:if test="${af.map.audit_state eq 1}">审核通过</c:if>
		</td>
      </tr>
      <tr>
        <td class="title_item">审核意见:</td>
        <td colspan="3">${fn:escapeXml(af.map.audit_desc)}</td>
      </tr>
      <tr>
      	<td colspan="4" align="center"><input type="button" value="返 回" class="bgButton" onclick="history.back();" /></td>
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