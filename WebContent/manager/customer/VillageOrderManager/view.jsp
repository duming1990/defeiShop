<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/scripts/jquery-ui/themes/base/jquery-ui.custom.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
    <tr class="Tp">
      <th colspan="4">订单基本信息</th>
    </tr>
    <tr class="Tp">
      <td width="12%" nowrap="nowrap" class="title_item">用户名：</td>
      <td><c:out value="${af.map.add_user_name}"></c:out></td>
      <td nowrap="nowrap" class="title_item">商品所属人：</td>
      <td><c:out value="${maijia_user.user_name}"></c:out></td>
    </tr>
    <tr class="Tp">
      <td width="12%" nowrap="nowrap" class="title_item">交易流水号：</td>
      <td ><c:out value="${af.map.trade_index}"></c:out></td>
      <td nowrap="nowrap" class="title_item">订单状态：</td>
      <td>
      <c:forEach var="curOrderState" items="${orderStateList}">
       <c:if test="${curOrderState.index eq af.map.order_state}">
         <c:set var="orderStateName" value="${curOrderState.name}" />
         <c:if test="${af.map.order_type eq 10 and orderStateName eq '等待发货'}">
           <c:set var="orderStateName" value="等待消费" />
         </c:if>
         <c:if test="${af.map.order_type eq 30 and orderStateName eq '等待发货'}">
             <c:set var="orderStateName" value="充值成功" />
           </c:if>
         ${orderStateName} </c:if>
       </c:forEach>
      </td>
    </tr>
    <tr class="Tp">
      <td width="12%" nowrap="nowrap" class="title_item">订单产品数量：</td>
      <td><c:out value="${af.map.order_num}"></c:out></td>
      <td width="12%" nowrap="nowrap" class="title_item">订单金额详情：</td>
      <td>应付款金额：<fmt:formatNumber pattern="0.00" value="${af.map.order_money}" />元 </td>
    </tr>
    <tr class="Tp">
      <td width="12%" nowrap="nowrap" class="title_item">支付方式：</td>
      <td colspan="3">
          <c:forEach var="curPayType" items="${payTypeList}">
              <c:if test="${curPayType.index eq af.map.pay_type}">
                <c:if test="${af.map.order_type eq 30}">
                  <c:if test="${af.map.pay_type ne 0}">${curPayType.name}</c:if>
                  <c:if test="${af.map.pay_type eq 0}">-</c:if>
                </c:if>
                <c:if test="${af.map.order_type ne 30}">${curPayType.name}</c:if>
              </c:if>
            </c:forEach>
      </td>
    </tr>
    <tr>
      <td width="12%" nowrap="nowrap" class="title_item">订单时间：</td>
      <td colspan="3">
      <div><fmt:formatDate value="${af.map.order_date}" pattern="yyyy-MM-dd HH:mm:ss"  var="od"/>下单时间：${od}</div>
      <div><fmt:formatDate value="${af.map.pay_date}" pattern="yyyy-MM-dd HH:mm:ss"  var="od"/>支付时间：${od}</div>
      <div><fmt:formatDate value="${af.map.fahuo_date}" pattern="yyyy-MM-dd HH:mm:ss"  var="od"/>发货时间：${od}</div>
      <div><fmt:formatDate value="${af.map.qrsh_date}" pattern="yyyy-MM-dd HH:mm:ss"  var="od"/>收货时间：${od}</div>
		</td>
    </tr>
    <tr>
      <td colspan="4">
      <table width="100%" border="0"  cellpadding="0" cellspacing="0"  align="left"  class="tableClass">
          <tr>
            <th colspan="5">订单明细信息</th>
          </tr>
          <tr>
            <th>商品名称</th>
            <th>商品数量</th>
            <th>商品单价</th>
            <th>商品总金额</th>
            <th>实际支付金额</th>
          </tr>
          <c:forEach var="cur" items="${orderInfoDetailList}" varStatus="vs">
            <tr>
              <td align="center" nowrap="nowrap">
                    ${cur.comm_name}(${cur.comm_tczh_name})
               </td>
              <td align="center" nowrap="nowrap"><c:out value="${cur.good_count}" /></td>
              <td align="center" nowrap="nowrap">
              <c:if test="${not empty cur.good_price}">￥<fmt:formatNumber value="${cur.good_price}" pattern="0.00" /></c:if>
              <c:if test="${empty cur.good_price}">-</c:if>
              </td>
              <td align="center" nowrap="nowrap">
              	<c:if test="${not empty cur.good_sum_price}">￥<fmt:formatNumber value="${cur.good_sum_price}" pattern="0.00" /></c:if>
                <c:if test="${empty cur.good_sum_price}">-</c:if>
                </td>
               <td align="center" nowrap="nowrap">
               
              	<c:if test="${not empty cur.good_sum_price}">
                 <c:if test="${not empty cur.sum_red_money}">
                	￥<fmt:formatNumber   value="${cur.good_sum_price - cur.sum_red_money}" pattern="0.00" />
                  </c:if>
                  <c:if test="${empty cur.sum_red_money}">
                		￥<fmt:formatNumber   value="${cur.good_sum_price}" pattern="0.00" />
                  </c:if>
                </c:if>
                </td>
            </tr>
          </c:forEach>
        </table></td>
    </tr>
   <c:if test="${(not empty af.map.map.shippingAddress)}">
    <tr>
      <td colspan="4">
      <table width="100%" border="0"  cellpadding="0" cellspacing="0"  align="left" class="tableClass">
          <tr>
            <th colspan="4">收货人信息</th>
          </tr>
          <tr>
            <th>收货人姓名</th>
            <th>收货人手机号码</th>
            <th>详细地址</th>
          </tr>
          <tr>
            <td align="center" nowrap="nowrap">
            <c:out value="${af.map.map.shippingAddress.rel_name}" />
            </td>
            <td align="center" nowrap="nowrap"><c:out value="${af.map.map.shippingAddress.rel_phone}" /></td>
            <td align="center" nowrap="nowrap"><c:out value="${af.map.map.shippingAddress.map.full_name}" />
              <c:out value="${af.map.map.shippingAddress.rel_addr}" /></td>
          </tr>
        </table></td>
    </tr>
    </c:if>
    <tr id="btn">
      <td colspan="4" align="center">
      <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" />
        </td>
    </tr>
  </table>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>

<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	
});

function windowReload(){
	window.location.reload();
}

//]]></script>
</body>
</html>
