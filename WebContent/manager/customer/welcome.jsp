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
<jsp:include page="./_public_head_back.jsp" flush="true" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/user-privilege.css"  />
<link rel="stylesheet" type="text/css" href="${ctx}/commons/font-awesome/css/font-awesome.min.css"  />
<link rel="stylesheet" type="text/css" href="${ctx}/m/styles/themes/css/icon.css?v=20180319" />
<style type="">
.has-order-nav #content{width: 98%;padding: 0px 10px;}
.account-info{width:auto;}
.table tr td {
    border-left: 1px dotted #e5e5e5;
}
.aui-icon{
    width: 14px!important;
    height: 14px!important;
    background-size: 14px!important;
}
.growth-rule-table tr th{text-align:center!important;}
</style>
</head>
<body class="has-order-nav" id="growth" style="position: static;min-height: 1600px">
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
      <div class="user"> <span class="user__name">${userInfo.real_name}</span>
        <c:url var="url" value="/manager/customer/MyScore.do?par_id=1100610000&mod_id=1100610300" />
        <fmt:formatNumber var="score_cur" value="${userInfo.cur_score}" pattern="0.########"/>
        <i onclick="goUrl('${url}',1100600000)" class="sp-growth-icons level-icon level-icon-${user_level} quicktip" title="总积分：${score_cur}，点击查看详情"></i>
        <c:if test="${userInfo.is_entp eq 1}">
          <c:set var="account_logo" value="${ctx}/styles/index/images/shang.gif"/>
          <c:url var="url" value="/manager/customer/EntpApply.do?method=list&par_id=1300510000&mod_id=1300510130" />
          <i onclick="goUrl('${url}',1300510130)" style="cursor:pointer;"><img src="${account_logo}" width="29"/></i>
        </c:if>
        <c:if test="${userInfo.is_fuwu eq 1}">
          <c:set var="account_logo" value="${ctx}/styles/index/images/fuwu.gif" />
          <i><img src="${account_logo}" width="50"/></i>
        </c:if>
       	 <c:if test="${userInfo.user_level eq 201}">
			<i onclick="toUpLevel();" class="aui-icon aui-icon-fufei-disabled" style="width：50px"></i>
		 </c:if>
		 <c:if test="${userInfo.user_level != 201}">
			<i class="aui-icon aui-icon-fufei" style="width：50px"></i>
		 </c:if>
      </div>
    </div>
    <div class="account-box">
    <c:if test="${not empty is_open_bi_dian}">
        <div class="item item__point"> <span>余额</span>
        <c:url var="url" value="/manager/customer/MyBiDianZi.do?par_id=1100400000&mod_id=1100400200" />
        <fmt:formatNumber var="bi_src" value="${userInfo.bi_dianzi}" pattern="0.########"/>
        <fmt:formatNumber var="bi" value="${userInfo.bi_dianzi}" pattern="0.##"/>
        <fmt:formatNumber var="bi1" value="${userInfo.bi_dianzi}" pattern="0"/>
        <c:if test="${bi1 gt 10000}">
          <fmt:formatNumber var="bi" value="${bi/10000}" pattern="#.##万"/>
        </c:if>
        <a href="javascript:;" class="quicktip" onclick="goUrl('${url}',1100400000)" title="您共有：${bi_src}余额，点击查看余额明细">${bi}</a>
        <c:url var="url" value="/manager/customer/TiXianDianZiBi.do?par_id=1100400000&mod_id=1100400100" />
        <button class="btns btns-warning" onclick="goUrl('${url}',1100400000)">提现</button>
      </div>
     </c:if>
      <div class="item"> <span>货款</span>
        <c:url var="url" value="/manager/customer/MyBiHuoKuan.do?par_id=1300100000&mod_id=1300300300" />
        <fmt:formatNumber var="bi_src" value="${userInfo.bi_huokuan}" pattern="0.########"/>
        <fmt:formatNumber var="bi" value="${userInfo.bi_huokuan}" pattern="0.##"/>
        <fmt:formatNumber var="bi1" value="${userInfo.bi_huokuan}" pattern="0"/>
        <c:if test="${bi1 gt 10000}">
          <fmt:formatNumber var="bi" value="${bi/10000}" pattern="#.##万"/>
        </c:if>
        <a href="javascript:;" class="quicktip" onclick="goUrl('${url}',1300100000)" title="您共有：${bi_src}货款，点击查看货款明细"> ${bi} </a>
        <c:url var="url" value="/manager/customer/MerchantCheck.do?par_id=1300501000&mod_id=1300510140" />
        <button class="btns btns-warning" onclick="goUrl('${url}',1300510140)">结算</button>
      </div>
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
          <c:url var="url" value="/manager/customer/MyOrder.do?method=list&order_type=10&order_state=50&mod_id=1100500100&par_id=1100500000" />
          <div class="count-item"> <a href="${url}"><i class="count-icon"></i><span>已完成 <em>(&nbsp;${count_yiwancheng}&nbsp;)</em></span></a> </div>
        </li>
      </ul>
    </div>
  </div>
  
  <!-- 驿站/村 -->
  <c:if test="${userInfo.is_village eq 1}">
  <h3 id="about-growth-rule" class="growth-title">总体统计</h3>
  <div class="component-growth-rule mt-component--booted">
    <table class="table growth-rule-table user-product-list">
      <tr>
        <td width="" rowspan="2" align="center" class="font40">村民</td>
        <td width="" class="bg_tip">入驻村民总数共为 <span title="点击查看" class="tip-danger" style="cursor: pointer;font-weight:bold;" onclick="doNeedMethod(null, 'CircleMemberManager.do', 'list','mod_id=1500301010&amp;audit_state=1')">${member_count}</span> 个，</td>
        <td width="" rowspan="2" align="center" class="font40">订单</td>
        <td width="" class="bg_tip" style="text-align:left;">订单总数共为 <span title="点击查看" class="tip-danger" style="cursor: pointer;font-weight:bold;" onclick="doNeedMethod(null, 'VillageOrderManager.do', 'list','mod_id=1500301040')">${orderInfoCount}</span> 个，</td>
      	<td width="" rowspan="2" align="center" class="font40">贫困户</td>
        <td width="" class="bg_tip" style="text-align:left;">贫困户总数共为 <span title="点击查看" class="tip-danger" style="cursor: pointer;font-weight:bold;" onclick="doNeedMethod(null, 'PoorManager.do', 'list','mod_id=1500301020')">${poorInfoCount}</span> 个，</td>
      </tr>
      <tr>
        <td style="padding-left: 10px;">待审核入驻村民总数为 <strong><span title="点击查看" class="tip-danger" style="cursor: pointer;" onclick="doNeedMethod(null, 'CircleMemberManager.do', 'list','mod_id=1500301010&amp;audit_state=0')">${dsh_member_count}</span></strong> 个。</td>
        <td style="text-align:left;">待发货订单总数为 <strong><span title="点击查看" class="tip-danger" style="cursor: pointer;" onclick="doNeedMethod(null, 'VillageOrderManager.do', 'list','mod_id=1500301040&order_state=10')">${orderInfoDfhCount}</span></strong> 个。</td>
        <td style="text-align:left;">待审核贫困户总数为 <strong><span title="点击查看" class="tip-danger" style="cursor: pointer;" onclick="doNeedMethod(null, 'PoorManager.do', 'list','mod_id=1500301020&audit_state=0')">${poorInfoDshCount}</span></strong> 个。</td>
      </tr>
    </table>
  </div> 
  </c:if>
  <!-- 合伙人 -->
  <c:if test="${userInfo.is_fuwu eq 1}">
  <h3 id="about-growth-rule" class="growth-title">总体统计</h3>
  <div class="component-growth-rule mt-component--booted">
    <table class="table growth-rule-table user-product-list">
      <tr>
        <td width="" rowspan="2" align="center" class="font40">驿站</td>
        <td width="" class="bg_tip">入驻驿站总数共为 <span title="点击查看" class="tip-danger" style="cursor: pointer;font-weight:bold;" onclick="doNeedMethod(null, 'VillageAudit.do', 'list','mod_id=1500401010&amp;audit_state=1')">${village_count}</span> 个，</td>
        <td width="" rowspan="2" align="center" class="font40">商品</td>
        <td width="" class="bg_tip">商品总数共为 <span title="点击查看" class="tip-danger" style="cursor: pointer;font-weight:bold;" onclick="doNeedMethod(null, 'CommInfo.do', 'list','mod_id=1500102010')">${commInfoCount}</span> 个，</td>
        <td width="" rowspan="2" align="center" class="font40">订单</td>
        <td width="" class="bg_tip" style="text-align:left;">订单总数共为 <span title="点击查看" class="tip-danger" style="cursor: pointer;font-weight:bold;" onclick="doNeedMethod(null, 'MyOrderEntp.do', 'list','mod_id=1500104001')">${orderInfoCount}</span> 个，</td>
      	<td width="" rowspan="2" align="center" class="font40">扶贫</td>
        <td width="" class="bg_tip" style="text-align:left;">扶贫商品总数共为 <span title="点击查看" class="tip-danger" style="cursor: pointer;" onclick="doNeedMethod(null, 'CommInfo.do', 'list','mod_id=1300500100&audit_state=1&is_aid=1')">${commInfoFpCount}</span> 个，</td>
      </tr>
      <tr>
        <td style="padding-left: 10px;">待审核入驻驿站总数为 <strong><span title="点击查看" class="tip-danger" style="cursor: pointer;" onclick="doNeedMethod(null, 'VillageAudit.do', 'list','mod_id=1500401010&amp;audit_state=0')">${ds_village_count}</span></strong> 个。</td>
        <td>待审核商品总数为 <strong><span title="点击查看" class="tip-danger" style="cursor: pointer;" onclick="doNeedMethod(null, 'CommInfo.do', 'list','mod_id=1500102010&audit_state=0')">${commInfoDshCount}</span></strong> 个。</td>
        <td style="text-align:left;">待发货订单总数为 <strong><span title="点击查看" class="tip-danger" style="cursor: pointer;" onclick="doNeedMethod(null, 'MyOrderEntp.do', 'list','mod_id=1500104001&order_state=10')">${orderInfoDfhCount}</span></strong> 个。</td>
        <td style="text-align:left;">扶贫对象总数为 <strong><span title="点击查看" class="tip-danger" style="cursor: pointer;" onclick="doNeedMethod(null, 'PoorManager.do', 'listAidMoney','mod_id=1500301020')">${entpPoorCount}</span></strong> 个。</td>
      </tr>
    </table>
  </div> 
  </c:if>
  <!-- 企业 -->
  <c:if test="${userInfo.is_entp eq 1}">
  <h3 id="about-growth-rule" class="growth-title">总体统计</h3>
  <div class="component-growth-rule mt-component--booted">
    <table class="table growth-rule-table user-product-list">
      <tr>
        <td width="" rowspan="2" align="center" class="font40">商品</td>
        <td width="" class="bg_tip">商品总数共为 <span title="点击查看" class="tip-danger" style="cursor: pointer;" onclick="doNeedMethod(null, 'CommInfo.do', 'list','mod_id=1300500100')">${commInfoCount}</span> 个，</td>
        <td width="" rowspan="2" align="center" class="font40">订单</td>
        <td width="" class="bg_tip" style="text-align:left;">订单总数共为 <span title="点击查看" class="tip-danger" style="cursor: pointer;" onclick="doNeedMethod(null, 'MyOrderEntp.do', 'list','mod_id=1300300100')">${orderInfoCount}</span> 个，</td>
      	<td width="" rowspan="2" align="center" class="font40">扶贫</td>
        <td width="" class="bg_tip" style="text-align:left;">扶贫商品总数共为 <span title="点击查看" class="tip-danger" style="cursor: pointer;" onclick="doNeedMethod(null, 'CommInfo.do', 'list','mod_id=1300500100&audit_state=1&is_aid=1')">${commInfoFpCount}</span> 个，</td>
      </tr>
      <tr>
        <td style="padding-left: 10px;">待审核商品总数为 <strong><span title="点击查看" class="tip-danger" style="cursor: pointer;" onclick="doNeedMethod(null, 'CommInfo.do', 'list','mod_id=1300500100&audit_state=0')">${commInfoDshCount}</span></strong> 个。</td>
        <td style="text-align:left;">待发货订单总数为 <strong><span title="点击查看" class="tip-danger" style="cursor: pointer;" onclick="doNeedMethod(null, 'MyOrderEntp.do', 'list','mod_id=1300300100&order_state=10')">${orderInfoDfhCount}</span></strong> 个。</td>
        <td style="text-align:left;">扶贫对象总数为 <strong><span title="点击查看" class="tip-danger" style="cursor: pointer;" onclick="doNeedMethod(null, 'PoorManager.do', 'listAidMoney','mod_id=1500301020')">${entpPoorCount}</span></strong> 个。</td>
      </tr>
    </table>
  </div> 
  </c:if>
  
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
            <td  id="order_state_${cur.id}" date-isshixiao="${cur.is_shixiao}" data-order-type="${cur.order_type}"><fmt:formatDate value="${cur.end_date}" pattern="yyyy-MM-dd HH:mm"  var="end_date"/>
              <c:if test="${(cur.order_state ge 10) and (cur.order_state ne 40) and (cur.order_type eq 10)}">
                <c:if test="${cur.is_shixiao eq 0 and (cur.order_state ne -20)}">
                  <div class="tip-success quicktip" title="请尽快消费，${cur.map.day_tip}，失效时间:${end_date}"><i class="fa fa-clock-o"></i> ${cur.map.day_tip_min}</div>
                </c:if>
                <c:if test="${(cur.is_shixiao eq 1)}">
                  <div class="tip-danger quicktip" title="失效时间:${end_date}"><i class="fa fa-clock-o"></i> 已失效</div>
                </c:if>
              </c:if>
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
  <c:if test="${userInfo.is_fuwu eq 1}">
  <h3 id="about-growth-rule" class="growth-title">村统计</h3>
  <div class="component-growth-rule mt-component--booted">
	  <table class="table growth-rule-table user-product-list">
	      <thead>
	        <tr>
	          <th width="25%">村总数</th>
	          <th width="25%">今日新增村数</th>
	          <th width="25%">村成员总数</th>
	          <th width="25%">今日新增村成员数</th>
	        </tr>
	      </thead>
	      <tbody>
	      	<tr>
	      		<td align="center"><span class="ftx-01">${village_count}</span></td>
	      		<td align="center"><span class="ftx-01">${today_village_count}</span></td>
	      		<td align="center"><span class="ftx-01">${member_count}</span></td>
	      		<td style="text-align:center!important;"><span class="ftx-01">${today_member_count}</span></td>
	      	</tr>
	      </tbody>
	  </table>
  </div>
  </c:if>
  <c:if test="${userInfo.is_village eq 1}">
  <h3 id="about-growth-rule" class="growth-title">村统计</h3>
  <div class="component-growth-rule mt-component--booted">
	  <table class="table growth-rule-table user-product-list">
	      <thead>
	        <tr>
	          <th width="50%" style="text-align:center;">村成员总数</th>
	          <th width="50%" style="text-align:center;">今日新增村成员数</th>
	        </tr>
	      </thead>
	      <tbody>
	      	<tr>
	      		<td style="text-align:center;"><span class="ftx-01">${member_count}</span></td>
	      		<td style="text-align:center!important;"><span class="ftx-01">${today_member_count}</span></td>
	      	</tr>
	      </tbody>
	  </table>
  </div>
  </c:if>
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
<script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script> 
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){

var browser=navigator.appName
var b_version=navigator.appVersion
var version=parseFloat(b_version)
if(browser == 'Microsoft Internet Explorer' && version <= 6){
	alert("您的浏览器版本过低，为了系统的正常使用，请升级到最新版本或使用360浏览器！");
}	
	
	
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
