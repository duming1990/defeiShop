<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
    <tr class="Tp">
      <th colspan="4">订单基本信息</th>
    </tr>
    <tr class="Tp">
      <td width="12%" nowrap="nowrap" class="title_item">真实姓名：</td>
      <td><c:out value="${showUserInfo.real_name}"></c:out></td>
      <td nowrap="nowrap" class="title_item">产品所属企业：</td>
      <td><c:out value="${af.map.map.orderInfo.entp_name}"></c:out></td>
    </tr>
    <tr class="Tp">
      <td width="12%" nowrap="nowrap" class="title_item">交易流水号：</td>
      <td ><c:out value="${af.map.map.orderInfo.trade_index}"></c:out></td>
      <td nowrap="nowrap" class="title_item">订单状态：</td>
      <td><c:choose>
          <c:when test="${af.map.map.orderInfo.order_state eq -10}">
            <c:out value="已取消" />
          </c:when>
          <c:when test="${af.map.map.orderInfo.order_state eq 0}">
            <c:out value="已预订" />
          </c:when>
          <c:when test="${af.map.map.orderInfo.order_state eq 10}">
            <c:out value="已付款" />
          </c:when>
          <c:when test="${af.map.map.orderInfo.order_state eq 20}">
            <c:out value="已发货" />
          </c:when>
          <c:when test="${af.map.map.orderInfo.order_state eq 30}">
            <c:out value="已到货" />
          </c:when>
          <c:when test="${af.map.map.orderInfo.order_state eq 40}"> 已收货<br />
            (<span style="color: green;">交易成功</span>) </c:when>
          <c:when test="${af.map.map.orderInfo.order_state eq 90}">
            <c:out value="已关闭" />
          </c:when>
          <c:otherwise></c:otherwise>
        </c:choose></td>
    </tr>
    <tr class="Tp">
      <td width="12%" nowrap="nowrap" class="title_item">订单产品数量：</td>
      <td><c:out value="${af.map.map.orderInfo.order_num}"></c:out></td>
      <td width="12%" nowrap="nowrap" class="title_item">订单金额详情：</td>
      <td>
        &nbsp;商品总金额：<fmt:formatNumber pattern="0.00" value="${good_sum_price}" />元 <br/>
     	<c:if test="${not empty af.map.map.orderInfo.jfdh_money}">
                 	积分兑换：${af.map.map.orderInfo.jfdh_money} <br/>
       </c:if>
      <c:if test="${empty af.map.map.orderInfo.qdyh_id}">&nbsp;运费：
        <fmt:formatNumber pattern="￥0.00" value="${af.map.map.orderInfo.matflow_price}" /><br/>
        <c:set var="matflow_price" value="${af.map.map.orderInfo.matflow_price}"/>
      </c:if>
       <c:if test="${not empty af.map.map.orderInfo.yhq_id}">
       	${af.map.map.orderInfo.yhq_tip_desc}<br/>
      </c:if>
      <c:if test="${not empty af.map.map.orderInfo.qdyh_id}">
       	${af.map.map.orderInfo.qdyh_tip_desc}
          <c:if test="${not empty af.map.map.orderInfo.matflow_price}">
          <br/>(运费:
           <fmt:formatNumber value="${af.map.map.orderInfo.matflow_price}" pattern="0.00"/>
          ) 
      </c:if>
      <br/>
      </c:if>
    	&nbsp;应付款金额：<fmt:formatNumber pattern="0.00" value="${af.map.map.orderInfo.order_money - af.map.map.yhq_money}" />元 
        
        </td>
    </tr>
    <tr class="Tp">
      <td nowrap="nowrap" class="title_item">下单时间：</td>
      <td><fmt:formatDate value="${af.map.map.orderInfo.order_date}" pattern="yyyy-MM-dd" /></td>
      <td nowrap="nowrap" class="title_item">送货时间：</td>
      <td><c:out value="${af.map.map.orderInfo.delivery_time}"/></td>
    </tr>
    <tr class="Tp">
      <td nowrap="nowrap" class="title_item">发票抬头：</td>
      <td colspan="1"><c:out value="${af.map.map.orderInfo.invoices_payable}"></c:out></td>
      <td nowrap="nowrap" class="title_item">发票类型：</td>
      <td colspan="1"><c:choose>
          <c:when test="${af.map.map.orderInfo.invoice_type eq 1}">普通发票</c:when>
          <c:when test="${af.map.map.orderInfo.invoice_type eq 2}">增值税发票</c:when>
        </c:choose></td>
    </tr>
    <tr class="Tp">
      <td width="12%" nowrap="nowrap" class="title_item">用户名：</td>
      <td colspan="3"><c:out value="${showUserInfo.user_name}"></c:out></td>
    </tr>
    <tr>
      <td colspan="4"><table width="100%" border="0"  cellpadding="0" cellspacing="0"  align="left"  class="tableClass">
          <tr>
            <th colspan="6">订单产品明细信息</th>
          </tr>
          <tr>
            <th>订单类型</th>
            <th>类别名称</th>
            <th>商品名称</th>
            <th>商品数量</th>
            <th>商品单价</th>
            <th>商品总金额</th>
          </tr>
          <c:forEach var="cur" items="${af.map.map.oidsList}" varStatus="vs">
            <tr>
              <td align="center" nowrap="nowrap"><c:choose>
                  <c:when test="${cur.order_type eq 10}">
                    <c:out value="普通产品" />
                  </c:when>
                  <c:when test="${cur.order_type eq 20}">
                    <c:out value="早餐产品" />
                  </c:when>
                  <c:when test="${cur.order_type eq 30}">
                    <c:out value="酒店菜品" />
                  </c:when>
                  <c:when test="${cur.order_type eq 40}">
                    <c:out value="酒店预定" />
                  </c:when>
                  <c:when test="${cur.order_type eq 50}">
                    <c:out value="团购" />
                  </c:when>
                  <c:when test="${cur.order_type eq 60}">
                    <c:out value="房间预订" />
                  </c:when>
                  <c:when test="${cur.order_type eq 70}">
                    <c:out value="旅游线路" />
                  </c:when>
                  <c:when test="${cur.order_type eq 80}">
                    <c:out value="景区门票" />
                  </c:when>
                  <c:when test="${cur.order_type eq 100}">
                    <c:out value="便民服务" />
                  </c:when>
                </c:choose></td>
              <td align="center" nowrap="nowrap"><c:out value="${fn:escapeXml(fnx:abbreviate(cur.cls_name, 45, '...'))}" /></td>
              <td align="center" nowrap="nowrap"><c:choose>
                  <c:when test="${cur.order_type eq 10}"> <a href="${ctx}/item-${cur.comm_id}.shtml" target="_blank">
                    <c:out value="${fn:escapeXml(fnx:abbreviate(cur.comm_name, 45, '...'))}" />
                    </a> </c:when>
                  <c:when test="${cur.order_type eq 30}"> <a href="${ctx}/item-${cur.comm_id}.shtml" target="_blank">
                    <c:out value="${fn:escapeXml(fnx:abbreviate(cur.comm_name, 45, '...'))}" />
                    </a> </c:when>
                  <c:when test="${cur.order_type eq 50}"> <a href="${ctx}/group-${cur.comm_id}.shtml" target="_blank">
                    <c:out value="${fn:escapeXml(fnx:abbreviate(cur.comm_name, 45, '...'))}" />
                    </a> </c:when>
                </c:choose></td>
              <td align="center" nowrap="nowrap"><c:out value="${cur.good_count }" /></td>
              <td align="center" nowrap="nowrap">￥
                <fmt:formatNumber value="${cur.good_price}" pattern="0.00" /></td>
              <td align="center" nowrap="nowrap">￥
                <fmt:formatNumber value="${cur.good_sum_price}" pattern="0.00" /></td>
            </tr>
          </c:forEach>
        </table></td>
    </tr>
    <tr>
      <td colspan="4"><table width="100%" border="0"  cellpadding="0" cellspacing="0"  align="left"  class="tableClass">
          <tr>
            <th colspan="3">收货人信息</th>
          </tr>
          <tr>
            <th>收货人姓名</th>
            <th>收货人手机号码</th>
            <th>详细地址</th>
          </tr>
          <tr>
            <td align="center" nowrap="nowrap"><c:out value="${shippingAddress.rel_name}" /></td>
            <td align="center" nowrap="nowrap"><c:out value="${shippingAddress.rel_phone }" /></td>
            <td align="center" nowrap="nowrap"><c:out value="${shippingAddress.map.full_name }" />
              <c:out value="${af.map.map.shippingAddress.rel_addr }" /></td>
          </tr>
        </table></td>
    </tr>
  </table>
  
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass" style="margin-top:5px;">
      <tr>
        <td width="15%" nowrap="nowrap" class="title_item">优惠券名称:</td>
        <td>${fn:escapeXml(af.map.map.yhqCfInfo.yhq_name)}</td>
      </tr>
      <tr>
        <td width="15%" nowrap="nowrap" class="title_item">优惠券金额:</td>
        <td><fmt:formatNumber value="${af.map.yhq_money}" pattern="#,##0.00" /></td>
      </tr>
      <tr>
        <td width="15%" nowrap="nowrap" class="title_item">银联订单号:</td>
        <td>
         <c:if test="${af.map.map.orderInfo.pay_type eq 0}">
          	  货到付款不存在银联订单号
         </c:if>
        <c:if test="${af.map.map.orderInfo.pay_type eq 2}">
        ${af.map.map.orderInfo.trade_merger_index}
        </c:if>
        </td>
      </tr>
      <tr>
        <td width="15%" nowrap="nowrap" class="title_item">${app_name_min}订单号:</td>
        <td>${af.map.map.orderInfo.trade_index}</td>
      </tr>
      <tr>
        <td width="15%" nowrap="nowrap" class="title_item">订单金额:</td>
        <td><fmt:formatNumber value="${af.map.map.orderInfo.order_money}" pattern="#,##0.00" /></td>
      </tr>
      <tr>
        <td width="15%" nowrap="nowrap" class="title_item">支付方式:</td>
        <td>
          <c:if test="${af.map.map.orderInfo.pay_type eq 0}">货到付款</c:if>
        <c:if test="${af.map.map.orderInfo.pay_type eq 2}">银联支付</c:if>
       </td>
      </tr>
      <tr>
        <td width="15%" nowrap="nowrap" class="title_item">订单日期:</td>
        <td>
          <fmt:formatDate value="${af.map.map.orderInfo.order_date}" pattern="yyyy-MM-dd" />
       </td>
      </tr>
      <tr>
        <td colspan="2" align="center">
          <input  type="button" class="bgButton"  onclick="history.back();"  value="返回" /></td>
      </tr>
    </table>
</div>
 
<script type="text/javascript">//<![CDATA[
                                          

//]]></script>
<jsp:include page="../public_page.jsp" flush="true"/>
</body>
</html>