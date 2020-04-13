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
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/user-privilege.css"  />
<link rel="stylesheet" type="text/css" href="${ctx}/commons/font-awesome/css/font-awesome.min.css"  />
<link rel="stylesheet" type="text/css" href="${ctx}/m/styles/themes/css/icon.css?v=20180319" />
<style type="">
.info-box .user img{width:50px;} 
.aui-icon{
    width: 14px!important;
    height: 14px!important;
    background-size: 14px!important;
}
</style>
</head>
<body class="has-order-nav" id="growth" style="position: static;min-height: 800px">
<div id="content">
<div class="component-account-info mt-component--booted">
  <div class="account-info">
    <c:set var="user_logo" value="${ctx}/styles/imagesPublic/user_header.png" />
    <c:if test="${not empty userInfo.user_logo}">
      <c:set var="user_logo" value=" ${ctx}/${userInfo.user_logo}@s400x400" />
    </c:if>
    <c:if test="${fn:contains(userInfo.user_logo, 'http://')}">
      <c:set var="user_logo" value="${userInfo.user_logo}"/>
    </c:if>
    <c:url var="url" value="/manager/customer/MyAccount.do?par_id=1100600000&mod_id=1100600200" />
    <div class="avatar-box"> <a onclick="goUrl('${url}',1100600000)" class="J-user item user"><img src="${user_logo}" width="76" height="76" /></a> </div>
    <div class="info-box">
      <div class="user"> <span class="user__name">${userInfo.real_name}</span></div>
    </div>
  </div>
</div>
<div class="mainbox mine">
  <div class="user-right">
    <div class="user-counts">
      <ul>
        <li class="user-count1">
          <c:url var="url" value="/manager/customer/MyOrder.do?method=list&order_type=10&order_state=0&mod_id=1100500100&par_id=1100500000" />
          <div class="count-item"> <a href="${url}"><i class="count-icon"></i><span>待付款 <em>(&nbsp;${count_daifukuan}&nbsp;)</em></span></a> </div>
        </li>
        <li class="user-count2">
          <c:url var="url" value="/manager/customer/MyOrder.do?method=list&order_type=10&order_state=20&mod_id=1100500100&par_id=1100500000" />
          <div class="count-item"> <a href="${url}"><i class="count-icon"></i><span>待确认收货 <em>(&nbsp;${count_daiquerenshouhuo}&nbsp;)</em></span></a> </div>
        </li>
        <li class="user-count4">
          <c:url var="url" value="/manager/customer/MyOrder.do?method=list&order_type=10&order_state=40&mod_id=1100500100&par_id=1100500000" />
          <div class="count-item"> <a href="${url}"><i class="count-icon"></i><span>已收货 <em>(&nbsp;${count_yiwancheng}&nbsp;)</em></span></a> </div>
        </li>
      </ul>
    </div>
  </div>
  <c:url var="url" value="/manager/customer/MyOrder.do?method=list&order_type=10&mod_id=1100500100&par_id=1100500000" />
  <h3 id="about-growth-rule" class="growth-title">近期订单 <a href="${url}" class="user-more">查看所有订单 &gt;</a></h3>
  <div class="component-growth-rule mt-component--booted">
    <table class="table growth-rule-table user-product-list">
      <thead>
        <tr>
          <th>订单编号</th>
          <th>状态</th>
          <th>总金额</th>
          <th>收货人</th>
          <th>下单日期</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
        <c:if test="${empty orderInfoZuijinList}">
          <tr>
            <td colspan="6" style="text-align: center;">没有数据</td>
          </tr>
        </c:if>
        <c:forEach items="${orderInfoZuijinList}" var="cur">
          <tr>
            <c:url var="url" value="/manager/customer/MyOrderDetail.do?order_id=${cur.id}&mod_id=1100500100&par_id=1100500000" />
            <td class="td"><a href="${url}" class="ftx-01">${cur.trade_index}</a></td>
            <td  id="order_state_${cur.id}"><fmt:formatDate value="${cur.end_date}" pattern="yyyy-MM-dd HH:mm"  var="end_date"/>
              <script type="text/javascript">showOrderState(${cur.order_state},${cur.pay_type},${cur.order_type},"")</script>
            </td>
            <td><span class="ftx-01"><em>¥</em>
              <fmt:formatNumber value="${cur.order_money}" pattern="0.##"/>
              </span></td>
            <td><span class="ftx-06">${cur.rel_name}</span></td>
            <td><span class="ftx-03">
              <fmt:formatDate value="${cur.order_date}" pattern="yyyy-MM-dd HH:mm:ss" />
              </span></td>
            <td><a href="${url}" class="ftx-07">订单详情</a></td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </div>
  </div>
</div>
<c:url var="urlMySecurityCenter" value="/manager/customer/MySecurityCenter.do?par_id=1100620000&mod_id=1100620100"/>
<c:url var="urlUserCenter" value="/manager/customer/MyAccount.do?par_id=1100600000&mod_id=1100600200" />
<script type="text/javascript" src="${ctx}/scripts/tip/jquery.quicktip.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/area.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/citySelect.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
$("#city_div").citySelect({
        data:getAreaDic(),
        province:"${af.map.province}",
        city:"${af.map.city}",
        country:"${af.map.country}",
        province_required:true,
        city_required:true,
        country_required:false,
        callback:function(selectValue,selectText){
        	if(null != selectValue && "" != selectValue){
        		var p_indexs = selectValue.split(",");
        		if(null != p_indexs && p_indexs.length > 0){
        			$("#p_index").val(p_indexs[p_indexs.length - 1]);
        		}
        	}
        }
    });
    
<c:if test="${not empty msg}">
var submit = function (v, h, f) {
	 if (v == true) {
		var url = "${ctx}/entpenter.html?method=stepend";
		window.open(url); 
	 }
	 return true;
};
$.jBox.confirm("${msg.msg_content}","${msg.msg_title}", submit, { buttons: { '前往查看': true} });
</c:if>
  $(".quicktip").quicktip();
});

function toUpLevel(){
	$.jBox.confirm("付费会员将缴费${upLevelNeedPayMoney}元,你确定要升级成为付费会员吗？", "${app_name}", submit2, { buttons: { '确定': true, '取消': false} });
}

var submit2 = function (v, h, f) {
	 if (v == true) {
		 var url = "${ctx}/IndexPayment.do?method=PayForUpLevel";
			window.open(url); 
	 }
	 return true;
};

function myConfirm(tip, submit){ 
	$.jBox.confirm(tip, "${app_name}", submit, { buttons: { '确定': true, '取消': false} });
}
function openNewWindow(){ 
	window.open('${pay_type_0_url}','_blank');
}

function refreshPage(){
	window.location.reload();
}
//]]></script>
</body>
</html>
