<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/scripts/colorbox/style3/colorbox.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div align="center" class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <div style="padding-bottom:5px;"> </div>
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
    <tr>
      <th nowrap="nowrap">所属企业名称</th>
      <th width="14%">企业联系方式</th>
      <th width="8%">银联订单号</th>
      <th width="8%">${app_name_min}订单号</th>
      <th width="4%">订单产品数量</th>
      <th width="8%">订单金额</th>
      <th width="6%">订单佣金</th>
      <th width="8%">收货人姓名</th>
      <th width="8%">收货人电话</th>
      <th width="7%">支付方式</th>
      <th width="7%">订单状态</th>
      <th width="8%">订单日期</th>
      <th width="7%">操作</th>
    </tr>
      <tr align="center">
        <td align="center"> ${fn:escapeXml(entity.entp_name)}</td>
        <td align="left">企业联系人：${fn:escapeXml(entity.map.entp_linkman)}<br />
          联系电话：${fn:escapeXml(entity.map.entp_tel)}<br />
          用户手机：${fn:escapeXml(entity.map.mobile)}</td>
        <td align="center">
         <c:if test="${entity.pay_type eq 0}">
          	  货到付款不存在银联订单号
            </c:if>
            <c:if test="${entity.pay_type eq 2}">
           <c:if test="${empty (entity.trade_merger_index)}">----</c:if>
        	<c:if test="${not empty (entity.trade_merger_index)}">${entity.trade_merger_index}</c:if>
            </c:if>
        </td>
        <td align="center"> ${entity.trade_index}</td>
        <td nowrap="nowrap"> ${entity.order_num}</td>
        <td align="center" nowrap="nowrap">￥
          <fmt:formatNumber value="${entity.order_money}" pattern="0.00" />
          		<c:if test="${not empty entity.yhq_id}">
                 	 <br/>${entity.yhq_tip_desc} 
                </c:if>
                <c:if test="${not empty entity.jfdh_money}">
                 	 <br/>积分兑换：${entity.jfdh_money}
                </c:if>
                <c:if test="${not empty entity.qdyh_id}">
                 	<br/>${entity.qdyh_tip_desc}
                  <c:if test="${not empty entity.matflow_price}">
                  	<br/>(运费:<fmt:formatNumber value="${entity.matflow_price}" pattern="0.00"/>)
                  </c:if>
                </c:if>
                <c:if test="${empty entity.qdyh_id}">
                	<br/>(运费:<fmt:formatNumber value="${entity.matflow_price}" pattern="0.00"/>) 
                </c:if>
          </td>
        <td nowrap="nowrap"> 
        	<c:if test="${empty (entity.map.pay_yj)}">----</c:if>
        	<c:if test="${not empty (entity.map.pay_yj)}">￥${entity.map.pay_yj}</c:if>
        	</td>
        <td nowrap="nowrap"> ${entity.rel_name}</td>
        <td nowrap="nowrap"> ${entity.rel_phone}</td>
         <td align="center">
         <c:if test="${entity.pay_type eq 0}">货到付款</c:if>
        <c:if test="${entity.pay_type eq 2}">银联支付</c:if>
        <c:if test="${entity.pay_type eq 3}">微信支付</c:if>
        <c:if test="${entity.pay_type eq 1}">支付宝支付</c:if>
        </td>
        <td align="center">
        <c:choose>
             <c:when test="${entity.order_state eq -10}">
               <c:out value="已取消" />
             </c:when>
             <c:when test="${entity.order_state eq 0}">
               <c:out value="已预订" />
             </c:when>
             <c:when test="${entity.order_state eq 10}">
               <c:if test="${entity.pay_type eq 0}">
                 <c:out value="等待发货" />
               </c:if>
               <c:if test="${entity.pay_type eq 2 or entity.pay_type eq 3 or entity.pay_type eq 1}">
                 <c:out value="已付款" />
               </c:if>
             </c:when>
             <c:when test="${entity.order_state eq 20}">
               <c:out value="已发货" />
             </c:when>
             <c:when test="${entity.order_state eq 30}">
               <c:out value="已到货" />
             </c:when>
             <c:when test="${entity.order_state eq 40}"> 已收货<br />
               (<span style="color: green;">交易成功</span>) </c:when>
             <c:when test="${entity.order_state eq 90}">
               <c:out value="已关闭" />
             </c:when>
          </c:choose>
          </td>
        <td><fmt:formatDate value="${entity.order_date}" pattern="yyyy-MM-dd" /></td>
        <td>
        <a title="查看" href="OrderDelete.do?method=view&amp;mod_id=${af.map.mod_id}&amp;id=${entity.id}"><img src="${ctx}/styles/images/chakan.gif" width="55" height="18" /></a>
        <a title="删除" onclick="confirmDelete(null, 'OrderDelete.do', 'id=${entity.id}&amp;mod_id=${af.map.mod_id}&' + $('#bottomPageForm').serialize())"><img src="${ctx}/styles/images/shanchu.gif" width="55" height="18" /></a>
        </td>
      </tr>
  </table>
</div>
 
<script type="text/javascript" src="${ctx}/commons/scripts/pager.js"></script> 
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
})
//]]>
</script>
</body>
</html>
