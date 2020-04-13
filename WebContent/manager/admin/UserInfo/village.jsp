<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="mainbox mine">
 <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
      <tr>
        <th colspan="4">基本信息</th>
      </tr>
       <tr>
         <td width="14%" nowrap="nowrap" class="title_item">申请区域：</td>
            <td colspan="3">
             <c:if test="${not empty full_name}">${fn:escapeXml(full_name)}</c:if>
            </td>
       </tr>
       <tr>
	       <td width="14%" nowrap="nowrap" class="title_item">地理位置：</td>
	       <td colspan="3">${af.map.position_latlng}
	       &nbsp;
	       <a onclick="viewMap('${af.map.position_latlng}');">查看</a>
	       </td>
       </tr>
       <tr>
         <td width="14%" nowrap="nowrap" class="title_item">站主名称：</td>
         <td>${fn:escapeXml(af.map.owner_name)}</td>
         <td width="14%" nowrap="nowrap" class="title_item">电话：</td>
         <td>${fn:escapeXml(af.map.village_mobile)}</td>
       </tr>
       <tr>
         <td width="14%" nowrap="nowrap" class="title_item">驿站上线时间：</td>
         <td><fmt:formatDate value="${af.map.service_online_date}" pattern="yyyy-MM-dd" /></td>
         <td width="14%" nowrap="nowrap" class="title_item">驿站运营时间：</td>
         <td>
          <fmt:formatDate value="${af.map.service_operation_date}" pattern="yyyy-MM-dd"/>-
          <fmt:formatDate value="${af.map.service_operation_date_end}" pattern="yyyy-MM-dd"/>
         </td>
       </tr>
       <tr>
         <td width="14%" nowrap="nowrap" class="title_item">店铺名称：</td>
         <td>${fn:escapeXml(af.map.village_name)}</td>
         <td width="14%" nowrap="nowrap" class="title_item">统一信用代码：</td>
         <td>${fn:escapeXml(af.map.shop_faith_code)}&nbsp;
         </td>
       </tr>
       <tr>
      	  <td width="14%" nowrap="nowrap" class="title_item">店铺营业执照号：</td>
          <td>${fn:escapeXml(af.map.shop_licence)}</td>
          <td width="14%" class="title_item" nowrap="nowrap">店铺营业执照图片：</td>
          <td><c:if test="${not empty af.map.shop_licence_img}"> <a href="${ctx}/${af.map.shop_licence_img}" title="身份证正面" class="cb"> <img height="100" src="${ctx}/${af.map.shop_licence_img}@s400x400" /></a> </c:if></td>
        </tr>
        <tr>
      	  <td width="14%" nowrap="nowrap" class="title_item">食品经营许可证号：</td>
          <td>${fn:escapeXml(af.map.food_licence)}</td>
          <td width="14%" class="title_item" nowrap="nowrap">食品经营许可证图片：</td>
          <td><c:if test="${not empty af.map.food_licence_img}"> <a href="${ctx}/${af.map.food_licence_img}" title="身份证正面" class="cb"> <img height="100" src="${ctx}/${af.map.food_licence_img}@s400x400" /></a> </c:if></td>
        </tr>
      <tr>
        <th colspan="4">个人信息</th>
      </tr>
      <tr>
        <td class="title_item">联系QQ：</td>
        <td>${fn:escapeXml(userinfo.appid_qq)}</td>
        <td class="title_item">性别：</td>
        <td><c:out value="${af.map.sex eq 0 ? '男' : '女'}"/></td>
      </tr>
       <tr>
        <td class="title_item">出生日期：</td>
        <td><fmt:formatDate value="${userinfo.birthday}" pattern="yyyy-MM-dd"/></td>
        <td class="title_item">电子邮箱：</td>
        <td><c:out value="${userinfo.email}"/></td>
      </tr>
      <tr>
         <td class="title_item">身份证号码：</td>
        <td colspan="4">${fn:escapeXml(userinfo.id_card)}</td>
      </tr>
       <tr>
          <td class="title_item" nowrap="nowrap">身份证正面：</td>
          <td nowrap="nowrap"><c:if test="${not empty userinfo.img_id_card_zm}"> <a href="${ctx}/${userinfo.img_id_card_zm}" title="身份证正面" class="cb"> 
          <img height="100" src="${ctx}/${userinfo.img_id_card_zm}@s400x400" /></a> </c:if></td>
          <td class="title_item" nowrap="nowrap">身份证背面：</td>
          <td nowrap="nowrap"><c:if test="${not empty userinfo.img_id_card_fm}"> <a href="${ctx}/${userinfo.img_id_card_fm}" title="身份证背面" class="cb"> 
          <img height="100" src="${ctx}/${userinfo.img_id_card_fm}@s400x400" /></a> </c:if></td>
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
      <tr id="btn">
    </tr>
    </table>
</div>
</body>
</html>
