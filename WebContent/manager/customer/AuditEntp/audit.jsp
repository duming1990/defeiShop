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
<body>
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
        <th colspan="4">联系方式</th>
      </tr>
      <tr>
        <td class="title_item">性别：</td>
        <td colspan="3"><c:out value="${entpUser.sex eq 0 ? '男' : '女'}" /></td>
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
        <td class="title_item">申请人姓名：</td>
        <td colspan="3">${fn:escapeXml(af.map.entp_sname)}</td>
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
          <td nowrap="nowrap" colspan="3"><c:if test="${not empty af.map.img_id_card_zm}"> <a href="${ctx}/${af.map.img_id_card_zm}" title="身份证正面" class="cb"> <img height="100" src="${ctx}/${af.map.img_id_card_zm}@s400x400" /></a> </c:if></td>
        </tr>
        <tr>
          <td class="title_item" nowrap="nowrap">身份证背面：</td>
          <td nowrap="nowrap" colspan="3"><c:if test="${not empty af.map.img_id_card_fm}"> <a href="${ctx}/${af.map.img_id_card_fm}" title="身份证背面" class="cb"> <img height="100" src="${ctx}/${af.map.img_id_card_fm}@s400x400" /></a> </c:if></td>
        </tr>
      </c:if>
      <c:if test="${af.map.is_has_yinye_no eq 1}">
        <tr>
          <td class="title_item" nowrap="nowrap">商家法人营业执照：</td>
          <td nowrap="nowrap" colspan="3">${fn:escapeXml(af.map.entp_licence)}<br/>
            <c:if test="${not empty af.map.entp_licence_img}"> <a href="${ctx}/${af.map.entp_licence_img}" title="商家法人营业执照" class="cb"> <img height="100" src="${ctx}/${af.map.entp_licence_img}@s400x400" /></a></c:if>
        </tr>
      </c:if>
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
          <td width="14%" nowrap="nowrap" class="title_item">商家编号：</td>
          <td colspan="3">${af.map.entp_no}</td>
        </tr>
      </c:if>
      <tr>
        <td width="15%" class="title_item">店铺名称：</td>
        <td colspan="3">${fn:escapeXml(af.map.entp_name)}</td>
      </tr>
      <tr>
        <td class="title_item">店铺类型：</td>
        <td colspan="3"><c:forEach var="cur" items="${shopTypes}" varStatus="vs">
            <c:if test="${cur.index eq af.map.is_nx_entp}">${cur.name}</c:if>
          </c:forEach>
        </td>
      </tr>
      <tr>
        <td class="title_item">所属行业：</td>
        <td colspan="3"><c:forEach var="cur" items="${baseHyClassList}" varStatus="vs">
            <c:if test="${cur.cls_id eq af.map.hy_cls_id}">${cur.cls_name}</c:if>
          </c:forEach>
        </td>
      </tr>
      <tr>
        <td class="title_item">经营范围：</td>
        <td colspan="3">${af.map.main_pd_class_names}</td>
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
        <td class="title_item">商家详细信息：</td>
        <td colspan="3">${af.map.entp_content}</td>
      </tr>
      <tr>
        <td class="title_item">折扣规则：</td>
        <td colspan="3"><c:forEach var="cur" items="${baseData700List}">
            <c:if test="${cur.id eq af.map.fanxian_rule}">${cur.type_name}</c:if>
          </c:forEach>
        </td>
      </tr>
      <tbody style="display: none">
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
          <td nowrap="nowrap" colspan="3"><c:if test="${not empty af.map.tax_reg_certificate}"> <a href="${ctx}/${af.map.tax_reg_certificate}"  class="cb"> <img height="100" src="${ctx}/${af.map.tax_reg_certificate}@s400x400" /></a></c:if>
            <c:if test="${not empty af.map.org_code}"> <a href="${ctx}/${af.map.org_code}"  class="cb"> <img height="100" src="${ctx}/${af.map.org_code}@s400x400" /></a></c:if>
            <c:if test="${not empty af.map.production_license}"> <a href="${ctx}/${af.map.production_license}"  class="cb"> <img height="100" src="${ctx}/${af.map.production_license}@s400x400" /></a></c:if>
        </tr>
        <tr>
          <td class="title_item" nowrap="nowrap">食品流通特许经营证<br/>
            /代理授权证明<br />
            /商标注册证</td>
          <td nowrap="nowrap" colspan="3"><c:if test="${not empty af.map.food_liutong_texu_license}"> <a href="${ctx}/${af.map.food_liutong_texu_license}"  class="cb"> <img height="100" src="${ctx}/${af.map.food_liutong_texu_license}@s400x400" /></a></c:if>
            <c:if test="${not empty af.map.proxy_author_certificate}"> <a href="${ctx}/${af.map.proxy_author_certificate}"  class="cb"> <img height="100" src="${ctx}/${af.map.proxy_author_certificate}@s400x400" /></a></c:if>
            <c:if test="${not empty af.map.brand_reg_license}"> <a href="${ctx}/${af.map.brand_reg_license}"  class="cb"> <img height="100" src="${ctx}/${af.map.brand_reg_license}@s400x400" /></a></c:if>
        </tr>
      </tbody>
      <c:if test="${not empty userInfoList}">
        <tr>
          <th colspan="4">登陆用户信息</th>
        </tr>
        <tr>
          <td class="title_item">登录名：</td>
          <td width="35%"><c:forEach var="cur" items="${userInfoList}" varStatus="vs"> ${fn:escapeXml(cur.user_name)}
              <c:if test="${vs.count < list_size}">,</c:if>
            </c:forEach>
          </td>
          <td class="title_item">手机：</td>
          <td width="35%"><c:forEach var="cur" items="${userInfoList}" varStatus="vs"> ${fn:escapeXml(cur.mobile)}
              <c:if test="${vs.count < list_size}">,</c:if>
            </c:forEach>
          </td>
        </tr>
      </c:if>
      <tr>
        <th colspan="4">审核信息</th>
      </tr>
      <c:if test="${not empty af.map.audit_desc_two}">
        <tr>
          <td nowrap="nowrap" class="title_item">当前审核状态：</td>
          <td nowrap="nowrap" colspan="3"><c:choose>
              <c:when test="${af.map.audit_state eq -2}"><span class="label label-danger">管理员审核不通过</span></c:when>
              <c:when test="${af.map.audit_state eq -1}"><span class="label label-danger">合伙人审核不通过</span></c:when>
              <c:when test="${af.map.audit_state eq 0}"><span class="label label-default">待审核</span></c:when>
              <c:when test="${af.map.audit_state eq 1}"><span class="label label-success">合伙人审核通过</span>&nbsp;<span class="label label-danger">等待管理员审核</span></c:when>
              <c:when test="${af.map.audit_state eq 2}"><span class="label label-success">管理员审核通过</span></c:when>
            </c:choose></td>
        </tr>
        <tr>
          <td nowrap="nowrap" class="title_item">管理员审核说明：</td>
          <td colspan="3"><c:out value="${af.map.audit_desc_two}" /></td>
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
        <td colspan="3"><html-el:text property="audit_desc_one" styleId="audit_desc_one" maxlength="125" style="width:480px" styleClass="webinput" /></td>
      </tr>
      <tr>
        <td colspan="4" align="center"><html-el:button property="" value="审 核" styleClass="bgButton" styleId="btn_submit" />
          &nbsp;
          <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
      </tr>
    </table>
  </html-el:form>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/scripts/colorbox/jquery.colorbox.min.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	$("#audit_state").attr("dataType", "Require").attr("msg", "请选择审核状态！");
	 <c:if test="${userInfoTemp.is_fuwu eq 1}">
	 </c:if>
	 <c:if test="${userInfoTemp.is_daqu eq 1}">
	 </c:if>
	$("#audit_desc_one").attr("dataType", "Require").attr("msg", "请填写审核意见！");
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
//]]></script>
</body>
</html>
