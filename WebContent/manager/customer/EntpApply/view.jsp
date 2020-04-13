<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/scripts/colorbox/style3/colorbox.css" rel="stylesheet" type="text/css" />
</head>
<body style="height:850px">
<div class="mainbox mine">
  <jsp:include page="../_nav.jsp" flush="true"/>
  <html-el:form action="/customer/AuditEntp.do" enctype="multipart/form-data">
    <html-el:hidden property="queryString" styleId="queryString" />
    <html-el:hidden property="method" styleId="method" value="save" />
    <html-el:hidden property="mod_id" styleId="mod_id" />
    <html-el:hidden property="par_id" styleId="par_id" />
    <html-el:hidden property="id" styleId="id" />
    <html-el:hidden property="is_audit" styleId="is_audit" value="true"/>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
      <tr>
        <th colspan="4">联系方式&nbsp;<html-el:button property="" value="修改店铺信息" styleClass="bgButton" onclick="confirmUpdate(null, 'EntpApply.do', 'id=${af.map.id}&' + $('#bottomPageForm').serialize())" /></th>
      </tr>
      <tr>
        <td class="title_item">联系人姓名：</td>
        <td colspan="3">${fn:escapeXml(af.map.entp_linkman)}</td>
      </tr>
      <tr>
        <td class="title_item">联系人手机：</td>
        <td colspan="3">${fn:escapeXml(af.map.entp_tel)}</td>
      </tr>
      <tr>
        <td class="title_item">联系人邮箱：</td>
        <td colspan="3">${fn:escapeXml(af.map.entp_email)}</td>
      </tr>
      <tr>
        <td class="title_item">联系人QQ：</td>
        <td colspan="3">${fn:escapeXml(af.map.qq)}</td>
      </tr>
      <tr>
        <th colspan="4">公司信息</th>
      </tr>
      <tr>
        <td class="title_item">所在地区：</td>
        <td colspan="3"><c:if test="${not empty af.map.map.full_name}">${fn:escapeXml(af.map.map.full_name)}</c:if></td>
      </tr>
      <tr>
        <td class="title_item">详细地址：</td>
        <td colspan="3">${fn:escapeXml(af.map.entp_addr)}</td>
      </tr>
      <tr>
        <td class="title_item">${type_name}地理位置：</td>
        <td colspan="3">${af.map.entp_latlng}
          &nbsp; <a onclick="viewMap('${af.map.entp_latlng}');">查看</a> </td>
      </tr>
      <tr>
        <th colspan="4">店铺信息</th>
      </tr>
      <c:if test="${not empty af.map.entp_no}">
        <tr>
          <td width="14%" nowrap="nowrap" class="title_item">店铺编号：</td>
          <td colspan="3">${af.map.entp_no}</td>
        </tr>
      </c:if>
      <tr>
        <td width="15%" class="title_item">店铺名称：</td>
        <td colspan="3">${fn:escapeXml(af.map.entp_name)}</td>
      </tr>
      <tr>
        <td class="title_item">门头照片：</td>
        <td colspan="3"><c:if test="${not empty (af.map.entp_logo)}"> <a href="${ctx}/${af.map.entp_logo}" title="门头照片" class="cb" target="_blank"> <img src="${ctx}/${af.map.entp_logo}@s400x400" height="100" /></a></c:if>
        </td>
      </tr>
      <tr>
        <td class="title_item">店铺简介：</td>
        <td colspan="3">${fn:escapeXml(af.map.entp_desc)}</td>
      </tr>
      <tr>
        <td class="title_item">店铺详细信息：</td>
        <td colspan="3"><html-el:button property="" value="编辑店铺详细" styleClass="bgButton" onclick="editContent('${af.map.id}');"/></td>
      </tr>
  
      <tbody style="display: none">
        <tr>
          <th colspan="4">其他信息</th>
        </tr>
        <tr>
          <td class="title_item">营业时间：</td>
          <td colspan="3">${fn:escapeXml(af.map.yy_sj_between)}</td>
        </tr>
      
      </tbody>
      <tr>
        <th colspan="4">审核信息</th>
      </tr>
      <c:if test="${not empty af.map.audit_desc_two}">
        <tr>
          <td nowrap="nowrap" class="title_item">当前审核状态：</td>
          <td nowrap="nowrap" colspan="3"><c:choose>
              <c:when test="${af.map.audit_state eq -2}"><span class="label label-danger">审核不通过</span></c:when>
              <c:when test="${af.map.audit_state eq 0}"><span class="label label-default">待审核</span></c:when>
              <c:when test="${af.map.audit_state eq 2}"><span class="label label-success">审核通过</span></c:when>
            </c:choose></td>
        </tr>
      
        <tr>
          <td nowrap="nowrap" class="title_item">管理员审核说明：</td>
          <td colspan="3"><c:out value="${af.map.audit_desc_two}" /></td>
        </tr>
      </c:if>
        <tr>
          <td colspan="4" style="text-align:center">
           <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" />
          </td>
      </tr>
    </table>
  </html-el:form>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/scripts/colorbox/jquery.colorbox.min.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/pager.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	$("#audit_state").attr("dataType", "Require").attr("msg", "请选择审核状态！");
	 <c:if test="${userInfoTemp.is_fuwu eq 1}">
	 </c:if>
	 <c:if test="${userInfoTemp.is_daqu eq 1}">
	 </c:if>
	$("#audit_desc_one").attr("dataType", "Require").attr("msg", "请填写审核意见！");
	
	$(".cb").colorbox({rel:'cb'});
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
function windowReload(){
	window.location.reload();
}
function editContent(id){
	var url = "EntpApply.do?method=editContent&id=" + id;
	$.dialog({
		title:  "编辑店铺详细信息",
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
//]]></script>
</body>
</html>
