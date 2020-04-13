<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
</head>
<c:set var="type_name" value="商家"/>
<body>
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/admin/ServiceInfoAudit.do" enctype="multipart/form-data">
    <html-el:hidden property="queryString" styleId="queryString" />
    <html-el:hidden property="method" styleId="method" value="save" />
    <html-el:hidden property="mod_id" styleId="mod_id" />
    <html-el:hidden property="id" styleId="id" />
    <html-el:hidden property="is_audit" styleId="is_audit" value="true"/>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th colspan="4">基本信息</th>
      </tr>
      <tr>
         <td width="14%" nowrap="nowrap" class="title_item">合伙人类型：</td>
            <td colspan="3">
            <c:forEach var="curserviceLevel" items="${serviceLevelList}">
            <c:if test="${curserviceLevel.index eq af.map.servicecenter_level}">${curserviceLevel.name}</c:if>
            </c:forEach>
            </td>
       </tr>
       <tr>
         <td width="14%" nowrap="nowrap" class="title_item">用户：</td>
         <td colspan="3">${fn:escapeXml(af.map.add_user_name)}</td>
       </tr>
        <c:if test="${not empty af.map.servicecenter_no}">
       <tr>
         <td width="14%" nowrap="nowrap" class="title_item">服务编号：</td>
            <td colspan="3">${af.map.servicecenter_no}</td>
       </tr>
       </c:if>
       <tr>
         <td width="14%" nowrap="nowrap" class="title_item">申请区域：</td>
            <td colspan="3">
             <c:if test="${not empty af.map.map.full_name}">${fn:escapeXml(af.map.map.full_name)}</c:if>
            </td>
       </tr>
       <tr>
         <td width="14%" nowrap="nowrap" class="title_item">驿站数量限制：</td>
            <td colspan="3">
             ${fn:escapeXml(af.map.village_count_limit)}&nbsp;家
            </td>
       </tr>
       <tr>
         <td width="14%" nowrap="nowrap" class="title_item">合伙人名称：</td>
         <td>${fn:escapeXml(af.map.servicecenter_name)}</td>
         <td width="14%" nowrap="nowrap" class="title_item">注册地址：</td>
         <td colspan="3">${fn:escapeXml(af.map.servicecenter_addr)}</td>
       </tr>
       <tr>
	       <td width="14%" nowrap="nowrap" class="title_item">地理位置：</td>
	       <td colspan="3">${af.map.position_latlng}
	       &nbsp;
	       <a onclick="viewMap('${af.map.position_latlng}');">查看</a>
	       </td>
       </tr>
       <tr>
         <td width="14%" nowrap="nowrap" class="title_item">公司/商户法人：</td>
         <td>${fn:escapeXml(af.map.servicecenter_corporation)}</td>
         <td width="14%" nowrap="nowrap" class="title_item">公司性质：</td>
         <td>
            <c:forEach var="cur" items="${baseData3100List}" varStatus="vs">
             <c:if test="${cur.id eq af.map.servicecenter_type}">${cur.type_name}</c:if>
            </c:forEach>
         </td>
       </tr>
       <tr>
         <td width="14%" nowrap="nowrap" class="title_item">注册时间：</td>
         <td><fmt:formatDate value="${af.map.servicecenter_build_date}" pattern="yyyy-MM-dd" /></td>
         <td width="14%" nowrap="nowrap" class="title_item">注册资金：</td>
         <td>${fn:escapeXml(af.map.servicecenter_reg_money)}&nbsp;万元
         </td>
       </tr>
       <tr>
         <td width="14%" nowrap="nowrap" class="title_item">员工人数：</td>
         <td>${fn:escapeXml(af.map.servicecenter_persons)}</td>
         <td width="14%" nowrap="nowrap" class="title_item">经营面积：</td>
         <td>${fn:escapeXml(af.map.servicecenter_area)}&nbsp;平方
         </td>
       </tr>
       <tr>
         <td width="14%" nowrap="nowrap" class="title_item">经营状况：</td>
         <td colspan="3">${fn:escapeXml(af.map.servicecenter_jy_desc)}</td>
       </tr>
      <tr>
        <th colspan="4">个人信息</th>
      </tr>
      <tr>
        <td class="title_item">真实姓名：</td>
        <td>${fn:escapeXml(af.map.servicecenter_linkman)}</td>
        <td class="title_item">籍贯：</td>
        <td>${fn:escapeXml(af.map.servicecenter_linkman_jg)}</td>
      </tr>
      
      
      <tr>
         <td class="title_item">身份证号码：</td>
        <td colspan="4">${fn:escapeXml(af.map.id_card)}</td>
      </tr>
       <tr>
          <td class="title_item" nowrap="nowrap">身份证正面：</td>
          <td nowrap="nowrap" colspan="3"><c:if test="${not empty img_id_card_zm}"> <a href="${ctx}/${img_id_card_zm}" title="身份证正面" class="cb"> <img height="100" src="${ctx}/${img_id_card_zm}@s400x400" /></a> </c:if></td>
        </tr>
        <tr>
          <td class="title_item" nowrap="nowrap">身份证背面：</td>
          <td nowrap="nowrap" colspan="3"><c:if test="${not empty img_id_card_fm}"> <a href="${ctx}/${img_id_card_fm}" title="身份证背面" class="cb"> <img height="100" src="${ctx}/${img_id_card_fm}@s400x400" /></a> </c:if></td>
        </tr>
        
      <tr>
        <td class="title_item">联络地址：</td>
        <td>${fn:escapeXml(af.map.servicecenter_linkman_addr)}</td>
        <td class="title_item">联络手机：</td>
        <td>${fn:escapeXml(af.map.servicecenter_linkman_tel)}</td>
      </tr>
      <tr>
        <td class="title_item">微信号：</td>
        <td>${fn:escapeXml(af.map.servicecenter_linkman_wixin_nu)}</td>
        <td class="title_item">联系QQ：</td>
        <td>${fn:escapeXml(af.map.servicecenter_linkman_qq)}</td>
      </tr>
      <tr>
        <td class="title_item">紧急联系人：</td>
        <td>${fn:escapeXml(af.map.servicecenter_help_linkman)}</td>
        <td class="title_item">紧急联系电话：</td>
        <td>${fn:escapeXml(af.map.servicecenter_help_linkman_tel)}</td>
      </tr>
      
    
      <tr>
        <th colspan="4">审核信息</th>
      </tr>
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
	$("#order_value").attr("datatype","Number").attr("msg","排序值必须在0~9999之间的正整数");
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
//]]></script>
<jsp:include page="../public_page.jsp" flush="true"/>
</body>
</html>