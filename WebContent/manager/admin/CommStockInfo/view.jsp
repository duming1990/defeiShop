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
<link href="${ctx}/styles/entp/css/css.css" rel="stylesheet" type="text/css" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
</head>
<body>
<div class="divContent">
 <div class="subtitle">
      <h3>${naviString}</h3>
  </div>
       <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
        <tr>
          <td width="14%" nowrap="nowrap" class="title_item">商品名称：</td>
          <td colspan="2">${fn:escapeXml(af.map.comm_name)}</td>
        </tr>
        <tr>
          <td nowrap="nowrap" class="title_item">用户名：</td>
          <td colspan="2">${fn:escapeXml(userInfo.user_name)}</td>
        </tr> 
         <tr>
          <td nowrap="nowrap" class="title_item">姓名：</td>
          <td colspan="2">${fn:escapeXml(userInfo.real_name)}</td>
        </tr> 
         <tr>
          <td nowrap="nowrap" class="title_item">联系电话：</td>
          <td colspan="2">${fn:escapeXml(userInfo.mobile)}</td>
        </tr> 
        <c:if test="${not empty af.map.brand_name}">
        <tr>
          <td nowrap="nowrap" class="title_item">所属品牌：</td>
          <td colspan="2">${fn:escapeXml(af.map.brand_name)}</td>
        </tr> 
        </c:if>
        <tr>
          <td nowrap="nowrap" class="title_item">商品条形码：</td>
          <td colspan="2">${fn:escapeXml(af.map.comm_barcode)}</td>
        </tr>
        <tr>
          <td nowrap="nowrap" class="title_item">商品类型：</td>
          <td colspan="2">
             <c:forEach var="curCommType" items="${commTypeList}">	
                <c:if test="${curCommType.index eq af.map.comm_type}">${curCommType.name}</c:if>
             </c:forEach>
          </td>
        </tr>
        <tr>
          <td nowrap="nowrap" class="title_item">所属企业：</td>
          <td colspan="2">${fn:escapeXml(af.map.map.entp_name)}</td>
        </tr>
        <tr>
          <td nowrap="nowrap" class="title_item">销售价格：</td>
          <td colspan="2">${fn:escapeXml(af.map.sale_price)}&nbsp;<span>元</span></td>
        </tr>
        <tr>
          <td nowrap="nowrap" class="title_item">商品详细内容：</td>
          <td colspan="2">${af.map.comm_content}</td>
        </tr>
        <tr>
          <td nowrap="nowrap" class="title_item">排序值：</td>
          <td colspan="2">${fn:escapeXml(af.map.order_value)}</td>
        </tr>
        <tr>
      <td nowrap="nowrap" class="title_item">审核状态：</td>
      <td colspan="2">
          <c:if test="${af.map.audit_state eq -2}"> <span style="color:#F00;">
              <c:out value="合伙人审核不通过"/>
              </span> </c:if>
          <c:if test="${af.map.audit_state eq -1}"> <span style="color:#F00;">
              <c:out value="管理员审核不通过"/>
              </span> </c:if>
            <c:if test="${af.map.audit_state eq 0}">
              <c:out value="待审核"/>
            </c:if>
            <c:if test="${af.map.audit_state eq 1}"> <span style="color:#060;">
              <c:out value="管理员审核通过"/>
              </span> </c:if>
            <c:if test="${af.map.audit_state eq 2}"> <span style="color:#060;">
              <c:out value="合伙人审核通过"/>
              </span> </c:if>  
      </td>
    </tr>
    <c:if test="${(not empty af.map.audit_service_desc)}">
          <tr>
            <td nowrap="nowrap" class="title_item">合伙人审核说明：</td>
            <td colspan="2">${fn:escapeXml(af.map.audit_service_desc)}</td>
          </tr>
        </c:if>
        <c:if test="${(not empty af.map.audit_desc)}">
          <tr>
            <td nowrap="nowrap" class="title_item">管理员审核说明：</td>
            <td colspan="2">${fn:escapeXml(af.map.audit_desc)}</td>
          </tr>
        </c:if>
        <tr>
      <td colspan="3" style="text-align:center">
      <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
    </tr>
      </table>
    <div class="clear"></div>
</div>
<!-- main end -->
<jsp:include page="../../../_public_page.jsp" flush="true" />
</body>
</html>