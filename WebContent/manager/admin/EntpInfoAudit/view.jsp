<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
</head>
<body>
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
    <tr>
      <th colspan="4">商家基本信息</th>
    </tr>
    <tr>
      <td width="15%" class="title_item">店铺名称：</td>
      <td colspan="3">${fn:escapeXml(af.map.entp_name)}</td>
    </tr>
    <c:if test="${not empty af.map.entp_no}">
       <tr>
         <td width="14%" nowrap="nowrap" class="title_item">商家编号：</td>
            <td colspan="3">${af.map.entp_no}</td>
       </tr>
    </c:if>
    <tr>
      <td class="title_item">门头照片：</td>
      <td colspan="3">
      <c:if test="${not empty (af.map.entp_logo)}"> <a href="${ctx}/${af.map.entp_logo}" target="_blank">
      <img src="${ctx}/${af.map.entp_logo}@s400x400" height="100" /></a></c:if>
      </td>
    </tr>
    <tr>
      <td class="title_item">商家简介：</td>
      <td colspan="3">${fn:escapeXml(af.map.entp_desc)}</td>
    </tr>
    <tr>
      <td class="title_item">商家详细信息：</td>
      <td colspan="3">${af.map.entp_content}</td>
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
       <td class="title_item">商家地理位置：</td>
       <td colspan="3">${af.map.entp_latlng}
       &nbsp;
       <a onclick="viewMap('${af.map.entp_latlng}');">查看</a>
       </td>
      </tr>
      <tr>
        <td class="title_item" nowrap="nowrap">有无营业执照：</td>
        <td nowrap="nowrap" colspan="3"><c:choose>
            <c:when test="${af.map.is_has_yinye_no eq 0}">没有</c:when>
            <c:when test="${af.map.is_has_yinye_no eq 1}">有</c:when>
          </c:choose>
        </td>
      </tr>
      <c:if test="${af.map.is_has_yinye_no eq 0}">
        <tr>
          <td class="title_item" nowrap="nowrap">身份证号：</td>
          <td nowrap="nowrap" colspan="3">${fn:escapeXml(af.map.id_card_no)}</td>
        </tr>
        <tr>
          <td class="title_item" nowrap="nowrap">身份证正面：</td>
          <td nowrap="nowrap" colspan="3"><c:if test="${not empty af.map.img_id_card_zm}"> <a href="${ctx}/${af.map.img_id_card_zm}" title="身份证正面" class="cb"> <img height="100" src="${ctx}/${af.map.img_id_card_zm}" /></a> </c:if></td>
        </tr>
        <tr>
          <td class="title_item" nowrap="nowrap">身份证背面：</td>
          <td nowrap="nowrap" colspan="3"><c:if test="${not empty af.map.img_id_card_fm}"> <a href="${ctx}/${af.map.img_id_card_fm}" title="身份证背面" class="cb"> <img height="100" src="${ctx}/${af.map.img_id_card_fm}" /></a> </c:if></td>
        </tr>
      </c:if>
      <c:if test="${af.map.is_has_yinye_no eq 1}">
       <tr>
          <td class="title_item" nowrap="nowrap">营业执照号码：</td>
          <td nowrap="nowrap" colspan="3">${fn:escapeXml(af.map.entp_licence)}</td>
        </tr>
        <tr>
          <td class="title_item" nowrap="nowrap">商家法人营业执照：</td>
          <td nowrap="nowrap" colspan="3">
            <c:if test="${not empty af.map.entp_licence_img}"> <a href="${ctx}/${af.map.entp_licence_img}" title="商家法人营业执照" class="cb"> <img height="100" src="${ctx}/${af.map.entp_licence_img}" /></a></c:if>
        </tr>
      </c:if>
      <tr>
         <th colspan="4">其他信息</th>
      </tr>
       <tr>
         <td class="title_item">营业时间：</td>
         <td colspan="3">${fn:escapeXml(af.map.yy_sj_between)}</td>
       </tr>
       <tr>
         <td class="title_item" nowrap="nowrap">税务登记证<br/>
           /组织机构代码<br />
           /生产许可证</td>
         <td nowrap="nowrap" colspan="3"><c:if test="${not empty af.map.tax_reg_certificate}"> <a href="${ctx}/${af.map.tax_reg_certificate}"  class="cb"> <img height="100" src="${ctx}/${af.map.tax_reg_certificate}" /></a></c:if>
           <c:if test="${not empty af.map.org_code}"> <a href="${ctx}/${af.map.org_code}"  class="cb"> <img height="100" src="${ctx}/${af.map.org_code}" /></a></c:if>
           <c:if test="${not empty af.map.production_license}"> <a href="${ctx}/${af.map.production_license}"  class="cb"> <img height="100" src="${ctx}/${af.map.production_license}" /></a></c:if>
       </tr>
       <tr>
         <td class="title_item" nowrap="nowrap">食品流通特许经营证<br/>
           /代理授权证明<br />
           /商标注册证</td>
         <td nowrap="nowrap" colspan="3"><c:if test="${not empty af.map.food_liutong_texu_license}"> <a href="${ctx}/${af.map.food_liutong_texu_license}"  class="cb"> <img height="100" src="${ctx}/${af.map.food_liutong_texu_license}" /></a></c:if>
           <c:if test="${not empty af.map.proxy_author_certificate}"> <a href="${ctx}/${af.map.proxy_author_certificate}"  class="cb"> <img height="100" src="${ctx}/${af.map.proxy_author_certificate}" /></a></c:if>
           <c:if test="${not empty af.map.brand_reg_license}"> <a href="${ctx}/${af.map.brand_reg_license}"  class="cb"> <img height="100" src="${ctx}/${af.map.brand_reg_license}" /></a></c:if>
       </tr>
    <tr>
      <th colspan="4">联系方式</th>
    </tr>
    <tr>
      <td class="title_item">商家联系人：</td>
      <td colspan="3">${fn:escapeXml(af.map.entp_linkman)}</td>
    </tr>
    <tr>
      <td class="title_item">联系电话：</td>
      <td colspan="3">${fn:escapeXml(af.map.entp_tel)}</td>
    </tr>
    <tr>
      <td class="title_item">联系QQ：</td>
      <td colspan="3">${fn:escapeXml(af.map.qq)}</td>
    </tr>
    <tr>
      <td class="title_item">营业时间：</td>
      <td colspan="3">${fn:escapeXml(af.map.yy_sj_between)}</td>
    </tr>

    <tr>
      <th colspan="4">审核信息</th>
    </tr>
    <tr>
      <td nowrap="nowrap" class="title_item">审核状态：</td>
      <td nowrap="nowrap" colspan="3">
      <c:choose>
          <c:when test="${af.map.audit_state eq -2}"><span class="label label-danger">审核不通过</span></c:when>
          <c:when test="${af.map.audit_state eq 0}"><span class="label label-default">待审核</span></c:when>
          <c:when test="${af.map.audit_state eq 2}"><span class="label label-success">审核通过</span></c:when>
        </c:choose></td>
    </tr>
        <tr>
          <td nowrap="nowrap" class="title_item">审核说明：</td>
          <td colspan="3"><c:out value="${af.map.audit_desc_two}" /></td>
        </tr>
    <tr>
      <td colspan="4" style="text-align:center">
      <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
    </tr>
  </table>
</div>
 
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript">//<![CDATA[
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
