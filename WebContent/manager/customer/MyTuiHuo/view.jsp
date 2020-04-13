<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>订单详情- ${app_name}</title>
<meta content="${app_name}订单管理" name="keywords" />
<meta content="${app_name}订单" name="description" />
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/scripts/colorbox/style3/colorbox.css" rel="stylesheet" type="text/css" />
</head>
<body id="order-detail" >
<div class="mainbox mine">
  <jsp:include page="../_nav.jsp" flush="true"/>
  <div id="content" style="width:100%;">
    <div class="mainbox mine" style="min-height: 1300px;">
      <h2>退货审核<span class="op-area"><a href="javascript:void(0);" onclick="history.back();">返回</a></span></h2>
      <dl class="bunch-section J-coupon">
        <dt class="bunch-section__label">订单状态</dt>
        <dd class="bunch-section__content">
          <div class="coupon-field">
            <ul>
                <c:if test="${af.map.return_way eq 1 or af.map.return_way eq 2}">
              <li class="invalid">退货成功确认：<span>
                <c:choose>
                  <c:when test="${af.map.is_confirm eq 0}">未确认</c:when>
                  <c:when test="${af.map.is_confirm eq 1}">已确认</c:when>
                  <c:when test="${af.map.is_confirm eq 2}">已换货</c:when>
                </c:choose>
                </span></li>
                </c:if>
            </ul>
          </div>
        </dd>
        <dt class="bunch-section__label">售后订单信息</dt>
        <dd class="bunch-section__content">
          <ul class="flow-list">
            <li>订单编号：${orderInfo.trade_index}</li>
            <li>申请售后时间：
              <fmt:formatDate value="${af.map.add_date}" pattern="yyyy-MM-dd HH:mm:ss" />
            </li>
            <li>退换货原因：<script type="text/javascript">showTuiHuoCause(${af.map.return_way})</script></li>
          </ul>
        </dd>
        <dt class="bunch-section__label">商品信息</dt>
        <dd class="bunch-section__content">
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable">
            <tr> 
              <th class="left" width="100">商品名称</th>
              <th class="left" width="100">价格</th>
              <th class="left" width="100">数量</th>
            </tr>
             <c:forEach var="oidsList" varStatus="vs1" items="${orderInfo.orderInfoDetailsList}">
            <tr>
              <td class="left">
                     ${oidsList.comm_name}<c:if test="${not empty oidsList.comm_tczh_name}"> &nbsp;[${oidsList.comm_tczh_name}] </c:if>
              </td>
              <td width="20%" align="center"><c:if test="${not empty oidsList.good_price}"> ￥
                      <fmt:formatNumber value="${oidsList.good_price}" pattern="0.##" />
                    </c:if>
                    <c:if test="${empty oidsList.good_price}">-</c:if>
                  </td>
                  <td width="12%" align="center">${oidsList.good_count}</td>
            </tr>
              </c:forEach>
          </table>
        </dd>
        <c:if test="${not empty imgsList}">
          <dt class="bunch-section__label">用户上传图片</dt>
          <dd class="bunch-section__content">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable">
              <tr id="trFile">
                <td colspan="2">
                	<c:forEach var="cur" items="${imgsList}" varStatus="vs"> <span style="float:left;">
                	<a href="${ctx}/${cur.file_path}@s400x400" class="viewImgMain">
                		<img src="${ctx}/${cur.file_path}@s400x400"  height="130" width="130"/>
                	</a>
                    <c:if test="${vs.count%4 eq 0}"><br/>
                    </c:if>
                    <c:if test="${vs.count%4 ne 0}">&nbsp;&nbsp;</c:if>
                    </span></c:forEach></td>
              </tr>
            </table>
          </dd>
        </c:if>
        <c:if test="${not empty af.map.hh_wl_no and af.map.expect_return_way ne 3}">
          <dt class="bunch-section__label">物流信息</dt>
          <dd class="bunch-section__content">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable">
              <tr>
                <th>物流公司名称</th>
                <th>物流单号</th>
                  </th>
              </tr>
              <tr>
                <td align="center" width="15%"><c:out value="${af.map.th_wl_company}" /></td>
                <td align="center"><c:out value="${af.map.th_wl_no}" /></td>
              </tr>
            </table>
          </dd>
        </c:if>
        <dt class="bunch-section__label">审核信息</dt>
        <dd class="bunch-section__content">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable">
        	<tr>
        		<td class="title_item" width="20%">审核状态：</td>
                <td colspan="3"><c:choose>
                <c:when test="${af.map.audit_state eq 2}"><span class="label label-success">商家审核通过</span></c:when>
                <c:when test="${af.map.audit_state eq 1}"><span class="label label-success">平台审核通过</span></c:when>
                <c:when test="${af.map.audit_state eq 0}"><span class="label label-default">未审核</span></c:when>
                <c:when test="${af.map.audit_state eq 3}"><span class="label label-default">待平台审核</span></c:when>
                <c:when test="${af.map.audit_state eq -1}"><span class="label label-danger">平台审核不通过</span></c:when>
                <c:when test="${af.map.audit_state eq -2}"><span class="label label-danger">商家审核不通过</span></c:when>
              </c:choose></td>
        	</tr>
        	<tr>
        		<td class="title_item" width="20%">审核说明：</td>
                <td colspan="3">${remark}</td>
        	</tr>
        	<tr>
        		<td class="title_item" width="20%">审核时间：</td>
                <td colspan="3"><fmt:formatDate value="${af.map.audit_date}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
        	</tr>
        	
            </table>
		</dd>        
        <dd class="last"> </dd>
        <dd class="bunch-section__content">
        </dd>
      </dl>
    </div>
  </div>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script> 
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/colorbox/jquery.colorbox.min.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	
	$("a.viewImgMain").colorbox();
	
});
//]]></script>
</body>
</html>
