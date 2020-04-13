<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${app_name}</title>
<meta content="${app_name}会员中心关键字" name="keywords" />
<meta content="${app_name}会员中心介绍" name="description" />
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/scripts/colorbox/style3/colorbox.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="divContent">
 <div class="subtitle">
      <h3>${naviString}</h3>
  </div>
  <html-el:form action="/admin/GroupCommInfo.do"  enctype="multipart/form-data">
        <html-el:hidden property="queryString" styleId="queryString" />
        <html-el:hidden property="method" styleId="method" value="save" />
        <html-el:hidden property="mod_id" styleId="mod_id" />
        <html-el:hidden property="id" styleId="id" />
        <html-el:hidden property="is_audit" styleId="is_audit" value="true" />
       <table width="100%" border="0" align="left" cellpadding="0" cellspacing="0" class="tableClass">
         <tr>
      <td width="14%" nowrap="nowrap" class="title_item">商品名称：</td>
       <td colspan="2" width="88%">${af.map.comm_name}</td>
    </tr>
    <tr>
      <td width="14%" nowrap="nowrap" class="title_item">商品套餐名称：</td>
       <td colspan="2" width="88%">${af.map.comm_tczh_name}</td>
    </tr>
    <tr>
      <td width="14%" nowrap="nowrap" class="title_item">活动名称：</td>
      <td colspan="2" width="88%">${af.map.comm_title}</td>
    </tr>
    <tr>
      <td width="14%" nowrap="nowrap" class="title_item">活动副标题：</td>
      <td colspan="2" width="88%">${af.map.comm_sub_title}</td>
    </tr>
    <tr>
      <td width="14%" nowrap="nowrap" class="title_item">原价格：</td>
      <td colspan="2" width="88%">
      <fmt:formatNumber pattern="#,##0.00" value="${af.map.no_dist_price}"/>&nbsp;元
      </td>
    </tr>
    <tr>
      <td width="14%" nowrap="nowrap" class="title_item">活动价格：</td>
      <td colspan="2" width="88%">
      <fmt:formatNumber pattern="#,##0.00" value="${af.map.prom_price}"/>&nbsp;元</td>
    </tr>
    <tr>
      <td nowrap="nowrap" class="title_item">开始时间：</td>
      <td  colspan="2"><fmt:formatDate value="${af.map.start_time}" pattern="yyyy-MM-dd HH:mm:ss"  /></td>
    </tr>
    <tr>
      <td nowrap="nowrap" class="title_item">结束时间：</td>
      <td  colspan="2"><fmt:formatDate value="${af.map.end_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
    </tr>
    <tr>
      <td nowrap="nowrap" class="title_item">是否开启限购：</td>
      <td  colspan="2">
        <c:if test="${af.map.is_buyer_limit eq 0}"><span class="label label-default">未开启</span></c:if>
        <c:if test="${af.map.is_buyer_limit eq 1}"><span class="label label-success">已开启</span></c:if>
      </td>
    </tr>
    <tr>
      <td nowrap="nowrap" class="title_item">每人限购数量：</td>
      <td  colspan="2">
          <c:if test="${af.map.is_buyer_limit eq 0}">不限制数量</c:if>
          <c:if test="${af.map.is_buyer_limit eq 1}">${af.map.buyer_limit_num}&nbsp;个</c:if>
      </td>
    </tr>
    <tr>
      <td nowrap="nowrap" class="title_item">活动数量：</td>
      <td  colspan="2">${af.map.prom_inventory}&nbsp;个
      </td>
    </tr>
    <tr>
      <td nowrap="nowrap" class="title_item">已售数量：</td>
      <td  colspan="2">${af.map.prom_sale_acount}&nbsp;个
      </td>
    </tr>
      <tr>
        <th colspan="3">审核信息</th>
      </tr>
        <tr>
          <td nowrap="nowrap" class="title_item">当前审核状态：</td>
          <td nowrap="nowrap" colspan="3"><c:choose>
              <c:when test="${af.map.audit_state eq -1}"><span class="tip-danger">审核不通过</span></c:when>
              <c:when test="${af.map.audit_state eq 0}"><span class="tip-default">待审核</span></c:when>
              <c:when test="${af.map.audit_state eq 1}"><span class="tip-success">审核通过</span></c:when>
            </c:choose></td>
        </tr>
         <tr>
        <td width="12%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>审核状态：</td>
        <td colspan="2" width="88%"><html-el:select property="audit_state" styleId="audit_state">
            <html-el:option value="">请选择...</html-el:option>
            <html-el:option value="0">待审核</html-el:option>
            <html-el:option value="1">审核通过</html-el:option>
            <html-el:option value="-1">审核不通过</html-el:option>
          </html-el:select></td>
      </tr>
      <tr>
      	<td width="12%" nowrap="nowrap" class="title_item">审核说明：</td>
      	<td colspan="2">
      		<html-el:textarea property="audit_desc" styleClass="webinput" styleId="audit_desc"  style="width:500px; height:80px;" ></html-el:textarea>
      	</td>
      </tr>
       <tr>
        <td colspan="3" align="center"><html-el:button property="" value="审 核" styleClass="bgButton" styleId="btn_submit" />
            &nbsp;
            <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
      </tr>
      </table>
      </html-el:form>
    <div class="clear"></div>
</div>

<script type="text/javascript" src="${ctx}/scripts/colorbox/jquery.colorbox.min.js"></script> 
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	
	$("#audit_state").attr("dataType", "Require").attr("msg", "请选择审核状态！");
	$("#audit_desc").attr("datatype","Limit").attr("min","0").attr("max","125").attr("msg","审核说明在125个汉字之内");
	
	var f = document.forms[0];
	$("#btn_submit").click(function(){
		if(Validator.Validate(f, 3)){
            $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
            $("#btn_reset").attr("disabled", "true");
            $("#btn_back").attr("disabled", "true");
			f.submit();
		}else{
			return false;
		}
	});
});
//]]></script>
<jsp:include page="../../../_public_page.jsp" flush="true" />
</body>
</html>