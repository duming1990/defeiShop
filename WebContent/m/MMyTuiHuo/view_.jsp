<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${app_name}</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Expires" content="-1">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Pragma" content="no-cache">
<jsp:include page="../_public_in_head.jsp" flush="true" />
</head>
<body >
<header class="index app_hide" >
  <div class="c-hd">
    <section class="back"> <a href="javascript:history.go(-1);"><i></i></a> </section>
    <section class="hd-title">订单详情</section>
    <section class="side"></section>
    <!-- <section class="side"><a href="javascript:;" class="arrowside-fun"><span class="del 1" id="order-del-btn">删除</span></a>
	</section> -->
  </div>
</header>
<div class="content">
	<!-- 订单信息 -->
    <div class="section-detailbox">
        <section class="title">
            <h2><i class="t5"></i>售后订单信息</h2>
        </section>
        <section class="info-details">
            <span class="item-label">退款状态：</span>
            <span class="item-content">
            <span class="price"><c:choose>
                  <c:when test="${af.map.tk_status eq 0}">未退款</c:when>
                  <c:when test="${af.map.tk_status eq 1}">已退款</c:when>
                </c:choose></span></span>
        </section>
        <section class="info-details">
            <span class="item-label">退货成功确认：</span>
            <span class="item-content">
            <span class="price"><c:choose>
                  <c:when test="${af.map.is_confirm eq 0}">未确认</c:when>
                  <c:when test="${af.map.is_confirm eq 1}">已确认</c:when>
                </c:choose></span></span>
        </section>
        <section class="info-details">
            <span class="item-label">订单编号：</span>
            <span class="item-content">${trade_index}</span>
        </section>
        <section class="info-details">
            <span class="item-label">申请售后时间：</span>
            <span class="item-content"><fmt:formatDate value="${af.map.add_date}" pattern="yyyy-MM-dd HH:mm:ss" /></span>
        </section>
        <section class="info-details">
            <span class="item-label">退换货原因：</span>
            <span class="item-content">${returnTypeName}</span>
        </section>
        <section class="info-details">
            <span class="item-label">买家期望处理方式：</span>
            <span class="item-content"><c:choose>
                <c:when test="${af.map.expect_return_way eq 1}">退货退款</c:when>
                <c:when test="${af.map.expect_return_way eq 2}">换货</c:when>
                <c:when test="${af.map.expect_return_way eq 3}">仅退款</c:when>
              </c:choose></span>
        </section>
        <c:if test="${not empty af.map.return_way}">
        <section class="info-details">
            <span class="item-label">实际退货方式：</span>
            <span class="item-content"><c:choose>
                  <c:when test="${af.map.return_way eq 1}">退货退款</c:when>
                  <c:when test="${af.map.return_way eq 2}">换货</c:when>
                  <c:when test="${af.map.return_way eq 3}">仅退款</c:when>
                </c:choose></span>
        </section>
        </c:if>
        <c:if test="${af.map.expect_return_way eq 1 or af.map.expect_return_way eq 2}">
        <section class="info-details">
            <span class="item-label">买家是否承担物流费用：</span>
            <span class="item-content"><c:choose>
                  <c:when test="${af.map.fh_fee eq 0}">不承担</c:when>
                  <c:when test="${af.map.fh_fee eq 1}">承担</c:when>
                </c:choose></span>
        </section>
        </c:if>
        <section class="info-details">
            <span class="item-label">应退金额：</span>
            <span class="item-content"><span class="price">¥<fmt:formatNumber value="${af.map.tk_money}" pattern="0.##"/></span>元</span>
        </section>
        <section class="info-details">
            <span class="item-label">手续费：</span>
            <span class="item-content"><span class="price">¥<fmt:formatNumber value="${af.map.tk_fee}" pattern="0.##"/></span>元</span>
        </section>
        <c:if test="${not empty af.map.end_date}">
        <section class="info-details">
            <span class="item-label">订单有效期：</span>
            <span class="item-content"><fmt:formatDate value="${af.map.end_date}" pattern="yyyy-MM-dd HH:mm:ss" /></span>
        </section>
        </c:if>
        <c:if test="${not empty af.map.pay_date}">
        <section class="info-details">
            <span class="item-label">订单有效期：</span>
            <span class="item-content"><fmt:formatDate value="${af.map.pay_date}" pattern="yyyy-MM-dd HH:mm:ss" /></span>
        </section>
        </c:if>
        <c:if test="${not empty af.map.fahuo_remark}">
        <section class="info-details">
            <span class="item-label">发货备注：</span>
            <span class="item-content">${af.map.fahuo_remark}</span>
        </section>
        </c:if>
    </div>
    <div class="section-detailbox">
    <section class="title goods">
       <c:if test="${not empty imgsList}">
        <dl class="goods-txt" id="_assessItem">
          <dd>
            <div class="txt">
<!--               <p class="img"> -->
               <c:forEach items="${imgsList}" var="cur" varStatus="vs">
                <c:if test="${not empty cur.file_path}">
                 	<a href="${ctx}/${cur1.file_path}" data-lightbox="image-${vs.count}">
                  <img src="${ctx}/${cur.file_path}@s400x400" height="70" width="70"></a>
                </c:if>
<!--                </p> -->
              </div>
              </dd>
          </c:forEach>
        </dl>
       </c:if>
      </section>
      </div>
    <!-- 商品信息 -->
    <div class="section-detailbox">
    	<section class="title">
            <h2><i class="t5"></i>商品信息</h2>
        </section>
        <section class="info-details">
            <span class="item-label">商品名称：</span>
            <span class="item-content">${af.map.comm_name}</span>
        </section>
        <section class="info-details">
            <span class="item-label">总金额：</span>
            <span class="item-content">
            <span class="price">¥<fmt:formatNumber pattern="0.##" value="${af.map.price}" /></span>
            </span>
        </section>
<!--         <section class="info-details"> -->
<!--             <span class="item-label">数量：</span> -->
<%--             <span class="item-content">${cur.good_count}</span> --%>
<!--         </section> -->
<!--         <section class="info-details"> -->
<!--             <span class="item-label">支付金额：</span> -->
<%--             <span class="item-content"><c:if test="${not empty cur.good_sum_price}"> --%>
<%--             <span class="price">¥<fmt:formatNumber pattern="0.##" value="${cur.good_sum_price}" /></span></c:if> --%>
<%--             <c:if test="${empty cur.good_sum_price}">-</c:if> --%>
<!--             </span> -->
<!--         </section> -->
    </div>
    <!-- 购买人信息 -->
    <div class="section-detailbox">
        <section class="title">
            <h2><i class="t5"></i>物流信息</h2>
        </section>
        <section class="info-details">
            <span class="item-label">物流公司名称：</span>
            <span class="item-content">${af.map.th_wl_company}</span>
        </section>
        <section class="info-details">
            <span class="item-label">购买人手机号码：</span>
            <span class="item-content">${af.map.th_wl_no}</span>
        </section>
        <section class="info-details">
            <span class="item-label">物流费用：</span>
            <span class="item-content"><span class="price">¥<fmt:formatNumber pattern="0.##" value="${af.map.th_wl_fee}" /></span></span>
        </section>
    </div>
    <div class="section-detailbox">
        <section class="title">
            <h2><i class="t5"></i>审核信息</h2>
        </section>
        <section class="info-details">
            <span class="item-label">审核状态：</span>
            <span class="item-content"><c:choose>
                <c:when test="${af.map.audit_state eq 2}"><span class="label label-success">商家审核通过</span></c:when>
                <c:when test="${af.map.audit_state eq 1}"><span class="label label-success">平台审核通过</span></c:when>
                <c:when test="${af.map.audit_state eq 0}"><span class="label label-default">未审核</span></c:when>
                <c:when test="${af.map.audit_state eq 3}"><span class="label label-default">待平台审核</span></c:when>
                <c:when test="${af.map.audit_state eq -1}"><span class="label label-danger">平台审核不通过</span></c:when>
                <c:when test="${af.map.audit_state eq -2}"><span class="label label-danger">商家审核不通过</span></c:when>
              </c:choose></span>
        </section>
        <section class="info-details">
            <span class="item-label">审核说明：</span>
            <span class="item-content">${remark}</span>
        </section>
        <section class="info-details">
            <span class="item-label">审核时间：</span>
            <span class="item-content"><fmt:formatDate value="${af.map.audit_date}" pattern="yyyy-MM-dd HH:mm:ss" /></span>
        </section>
    </div>
</div>
<jsp:include page="../_footer.jsp" flush="true" />
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/order/order.js"></script>
</body>
</html>