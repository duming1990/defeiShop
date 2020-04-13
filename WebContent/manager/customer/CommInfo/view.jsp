<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员中心 - ${app_name}</title>
<meta content="${app_name}会员中心关键字" name="keywords" />
<meta content="${app_name}会员中心介绍" name="description" />
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
</head>
<body>
<div class="mainbox mine">
  <jsp:include page="../_nav.jsp" flush="true"/>
  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable" align="left">
    <tr>
      <td width="14%" nowrap="nowrap" class="title_item">商品名称：</td>
      <td colspan="2">${fn:escapeXml(af.map.comm_name)}</td>
    </tr>
    <tr>
      <td nowrap="nowrap" class="title_item">产品类别：</td>
      <td colspan="2">${fn:escapeXml(af.map.cls_name)}</td>
    </tr>
    <tr>
      <td nowrap="nowrap" class="title_item">商品编号：</td>
      <td colspan="2">${fn:escapeXml(af.map.comm_no)}</td>
    </tr>
    <tr>
      <td nowrap="nowrap" class="title_item">所属店铺：</td>
      <td colspan="2">${fn:escapeXml(af.map.map.entp_name)}</td>
    </tr>
    <tr>
      <td nowrap="nowrap" class="title_item">主图地址：</td>
      <td colspan="2"><c:if test="${not empty fn:substringBefore(af.map.main_pic, '.')}"> <img src="${ctx}/${af.map.main_pic}@s400x400" height="100" /> </c:if>
        <c:if test="${empty fn:substringBefore(af.map.main_pic, '.')}"> <img src="${ctx}/images/no_img.gif" /> </c:if></td>
    </tr>
    <tr>
      <td nowrap="nowrap" class="title_item">销售价格：</td>
      <td colspan="2">${fn:escapeXml(af.map.sale_price)}&nbsp;<span>元</span></td>
    </tr>
    <tr>
      <td nowrap="nowrap" class="title_item">是否上架：</td>
      <td colspan="2"><c:if test="${af.map.is_sell eq 0}">
          <c:out value="否"/>
        </c:if>
        <c:if test="${af.map.is_sell eq 1}">
          <c:out value="是"/>
        </c:if></td>
    </tr>
    <c:if test="${af.map.is_sell eq 1}">
      <tr>
        <td nowrap="nowrap" class="title_item">上架时间：</td>
        <td  colspan="2" height="24"><fmt:formatDate value="${af.map.up_date}" pattern="yyyy-MM-dd" var="_up_date" />
          <c:out value="${_up_date}"/></td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item">下架时间：</td>
        <td  colspan="2" height="24"><fmt:formatDate value="${af.map.down_date}" pattern="yyyy-MM-dd" var="_down_date" />
          <c:out value="${_down_date}"/></td>
      </tr>
    </c:if>
    <tr>
      <td nowrap="nowrap" class="title_item">商品详细内容：</td>
      <td colspan="2">${af.map.comm_content}</td>
    </tr>
    <tr>
        <th colspan="3">套餐信息</th>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item">套餐信息：</td>
        <td colspan="2" width="88%"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable" align="left" >
            <tr>
              <td align="center" width="34%">套餐名称</td>
              <td align="center" width="33%">套餐价格</td>
              <td align="center" width="33%">套餐库存</td>
            </tr>
            <tbody>
              <c:forEach var="cur" items="${list_CommTczhPrice}" varStatus="vs" >
                <tr>
                  <td align="center" id="edit_son" width="34%">
                   <c:out value="${cur.tczh_name}"></c:out>
                  </td>
                  <td align="center" width="33%">${cur.comm_price}</td>
                  <td align="center" width="33%">${cur.inventory}</td>
                </tr>
              </c:forEach>
            </tbody>
          </table></td>
      </tr>
    <tr>
      <td nowrap="nowrap" class="title_item">审核状态：</td>
      <td colspan="2">
          <c:if test="${af.map.audit_state eq -1}"> <span style="color:#F00;">
              <c:out value="审核不通过"/>
              </span> </c:if>
            <c:if test="${af.map.audit_state eq 0}">
              <c:out value="待审核"/>
            </c:if>
            <c:if test="${af.map.audit_state eq 1}"> <span style="color:#060;">
              <c:out value="审核通过"/>
              </span> </c:if>
      </td>
    </tr>
        <c:if test="${(not empty af.map.audit_desc)}">
          <tr>
            <td nowrap="nowrap" class="title_item">审核说明：</td>
            <td colspan="2">${fn:escapeXml(af.map.audit_desc)}</td>
          </tr>
        </c:if>
    <tr>
      <td colspan="3" style="text-align:center"><html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
    </tr>
  </table>
  <div class="clear"></div>
</div>
<!-- main end -->
</body>
</html>
