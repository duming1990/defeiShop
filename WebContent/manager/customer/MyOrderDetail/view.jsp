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
<style type="text/css">
.line-limit-length {
	max-width: 250px;
	
	overflow: hidden;
	
	text-overflow: ellipsis;
	
	white-space: nowrap; //文本不换行，这样超出一行的部分被截取，显示...
}

#copyValue {
	position: absolute;
	top: 0;
	left: 0;
	opacity: 0;
	z-index: -10;
}
</style>
</head>
<body id="order-detail">
<div class="mainbox mine">
  <jsp:include page="../_nav.jsp" flush="true"/>
  <div id="content" style="width:100%;">
    <div class="mainbox mine">
      <h2>订单详情<span class="op-area"><a href="javascript:void(0);" onclick="history.back();">返回我的订单</a></span></h2>
      <dl class="info-section primary-info J-primary-info">
        <dt> <span class="info-section--title">当前订单状态：</span> <em class="info-section--text">
          <c:forEach var="curOrderState" items="${orderStateList}">
            <c:if test="${curOrderState.index eq af.map.order_state}">
              <c:set var="orderStateName" value="${curOrderState.name}" />
              ${orderStateName}</c:if>
          </c:forEach>
         	 ：<span class="money">¥</span>
          <fmt:formatNumber value="${af.map.order_money}" pattern="0.##" />
          
           <c:forEach var="curType" items="${orderTypeShowList}">
              <c:if test="${af.map.order_type eq curType.index}">
               &nbsp;&nbsp;${curType.name}
              </c:if>
           </c:forEach>
          </em> 
          </dt>
        <dd class="last"></dd>
      </dl>
     <c:if test="${(af.map.is_ziti eq 1) and (af.map.from eq 'user')}">
      <dl class="info-section primary-info J-primary-info">
        <dt> <span class="info-section--title">${app_name_min}订单密码：</span> <em class="info-section--text">
          ${af.map.order_password} 
          </em> </dt>
        <dd class="last"> </dd>
      </dl>
      </c:if>
      <dl class="bunch-section J-coupon">
        <dt class="bunch-section__label">${app_name_min}订单</dt>
        <dd class="bunch-section__content">
          <div class="coupon-field"> 
              <ul>
                  <li class="invalid">订单状态：<span>
                    <c:forEach var="curOrderState" items="${orderStateList}">
                      <c:if test="${curOrderState.index eq af.map.order_state}">
                        <c:set var="orderStateName" value="${curOrderState.name}" />
                        ${orderStateName}</c:if>
                    </c:forEach>
                    </span></li>
              </ul>
          </div>
        </dd>
        
        <dt class="bunch-section__label">商家信息</dt>
        <dd class="bunch-section__content">
          <ul class="flow-list">
            <li>店铺名称：${entpInfo.entp_name}</li>
            <li>商家地址：${entpInfo.entp_addr}</li>
            <li>联系方式：${entpInfo.entp_tel}</li>
          </ul>
        </dd>
        <dt class="bunch-section__label">订单信息</dt>
        <dd class="bunch-section__content">
          <ul class="flow-list">
            <li>订单编号：${af.map.trade_index}</li>
            <li>下单时间：
              <fmt:formatDate value="${af.map.order_date}" pattern="yyyy-MM-dd HH:mm:ss" />
            </li>
            <c:if test="${not empty af.map.end_date}">
	            <li>订单有效期：
	              <fmt:formatDate value="${af.map.end_date}" pattern="yyyy-MM-dd HH:mm:ss" />
	            </li>
            </c:if>
            <li> 付款方式：
              <c:forEach var="curPayType" items="${payTypeList}">
                <c:if test="${curPayType.index eq af.map.pay_type}">
                  ${curPayType.name}
                </c:if>
              </c:forEach>
            </li>
            <c:if test="${not empty af.map.pay_date}">
              <li>付款时间：
                <fmt:formatDate value="${af.map.pay_date}" pattern="yyyy-MM-dd HH:mm:ss" />
              </li>
            </c:if>
            <c:if test="${not empty af.map.remark}">
              <li>买家留言：
                ${fn:escapeXml(af.map.remark)}
              </li>
            </c:if>
            <c:if test="${not empty af.map.fahuo_remark}">
              <li>发货备注：
                ${af.map.fahuo_remark}
              </li>
            </c:if>
            <br/>
            <li>第三方支付：<fmt:formatNumber value="${af.map.order_money}" pattern="0.00"/>元</li>
            <li> 应付款金额: <fmt:formatNumber pattern="0.00" value="${af.map.no_dis_money}" />元</li>
            <li>运费：<fmt:formatNumber value="${af.map.matflow_price}" pattern="0.00"/> </li>
            <c:if test="${af.map.money_bi gt 0}">
	          <li>余额抵扣: <fmt:formatNumber value="${af.map.money_bi-af.map.welfare_pay_money}" pattern="0.00"/></li> 
		          <c:if test="${af.map.welfare_pay_money gt 0}">
		          	<li>福利金抵扣:<fmt:formatNumber value="${af.map.welfare_pay_money}" pattern="0.00"/></li>  
	              </c:if> 
            </c:if> 
            <c:if test="${af.map.card_pay_money gt 0}">
	          <li>福利卡抵扣: <fmt:formatNumber value="${af.map.card_pay_money}" pattern="0.00"/></li> 
            </c:if> 
            <c:if test="${not empty af.map.map.reMoney}">
          		<li><span style="color:red">(退款: <fmt:formatNumber value="${af.map.map.reMoney}" pattern="0.00"/>)</span></li>
         	</c:if>
          </ul>
        </dd>
         <c:if test="${af.map.fp_state eq 1}">
        <dt class="bunch-section__label">发票信息</dt>
        <dd class="bunch-section__content">
          <ul class="flow-list">
            <li>发票类型：
            <c:if test="${af.map.invoice_type eq 1}">企业</c:if>
            <c:if test="${af.map.invoice_type eq 0}">个人</c:if>
            </li>
            <li>发票抬头：${af.map.invoices_payable}</li>
            <li>发票税号：${af.map.invoices_no}</li>
          </ul>
        </dd>
        </c:if>
        <dt class="bunch-section__label">商品信息</dt>
        <dd class="bunch-section__content">
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable">
            <tr>
              <th width="10%">序号</th>
              <th width="20%">商品主图</th>
              <th nowrap="nowrap">商品名称</th>
              <th width="8%">单价</th>
              <th width="8%">数量</th>
              <th width="8%">总价</th>
              <th width="8%">支付金额</th>
            </tr>
            <c:forEach items="${orderInfoDetailList}" var="cur" varStatus="vs">
              <tr>
                <td align="center" width="5%">${vs.count}</td>
                <td align="center"><img src="${ctx}/${cur.map.comm.main_pic}" style="width: 100px;"></img></td>
                <td align="center">
                	<textarea  id="copyValue"></textarea>
                	<div title="${cur.comm_name}&nbsp;${cur.comm_tczh_name}">
                	<p id="copyText" class="line-limit-length">
	                 ${cur.comm_name}
	                 <c:if test="${not empty cur.comm_tczh_name}">
	                    &nbsp;[${cur.comm_tczh_name}]
	                 </c:if> </p></div></br>
                 <button class="bgButton" onclick="copyText()" >复制文本</button>
                </td>
                <td width="20%" align="center">
                <c:if test="${not empty cur.good_price}"> <span class="money">¥</span>
                    <fmt:formatNumber pattern="0.##" value="${cur.good_price}" />
                  </c:if>
                  <c:if test="${empty cur.good_price}">-</c:if>
                </td>
                <td width="20%" align="center">${cur.good_count}</td>
                <td width="20%"  align="center">
              	<c:if test="${not empty cur.good_sum_price}"><span class="money">¥</span>
              		<fmt:formatNumber value="${cur.good_sum_price}" pattern="0.##" /></c:if>
                <c:if test="${empty cur.good_sum_price}">-</c:if>
                </td>
                <td width="20%" align="center" class="total">
               	  <c:if test="${not empty cur.actual_money}"> <span class="money">¥</span>
                    <fmt:formatNumber pattern="0.##" value="${cur.actual_money}" />
                  </c:if>
                  <c:if test="${empty cur.actual_money}">-</c:if>
                </td>
              </tr>
            </c:forEach>
          </table>
        </dd>
        <c:if test="${(not empty af.map.map.shippingAddress)}">
        <dt class="bunch-section__label">购买人信息</dt>
        <dd class="bunch-section__content">
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable">
            <tr>
              <th>购买人姓名</th>
              <th>购买人手机号码</th>
              <th>详细地址</th>
              <th>
              	物流信息
               <c:if test="${(userInfo.own_entp_id eq af.map.entp_id) and (userInfo.is_entp eq 1)}">
	              <c:if test="${af.map.order_state ge 20 and (not empty wlOrderInfo)}">
	               	<button class="bgButton" onclick="updateWlOrderInfo('${af.map.id}');">修改</button>
	              </c:if>
               </c:if>
              </th>
            </tr>
              <tr>
                <td align="center" width="15%"><c:out value="${af.map.map.shippingAddress.rel_name}" /></td>
                <td align="center"><c:out value="${af.map.map.shippingAddress.rel_phone}" /></td>
                <td width="40%" align="center"><c:out value="${af.map.map.shippingAddress.map.full_name}" /><c:out value="${af.map.map.shippingAddress.rel_addr}" /></td>
                <td width="25%" align="center">
                <!-- 自提订单 -->
                <c:if test="${af.map.is_ziti eq 1}" var="isziti">用户自提</c:if>
                <c:if test="${not isziti}">
                <c:if test="${af.map.order_state ge 20 and (not empty wlOrderInfo)}">
                <span class="label label-info">${wlOrderInfo.waybill_no}</span><br/>
                <button class="bgButton" onclick="kdcx(this)" style="margin-top:10px;">物流信息查询</button></c:if>
                <c:if test="${af.map.order_state lt 20 or (empty wlOrderInfo)}">-</c:if>
                </c:if>
                </td>
              </tr>
              <tr id="btn">
			      <td colspan="4" align="center">
			      <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" />
			        </td>
			  </tr>
          </table>
        </dd>
        </c:if>
       
      </dl>
    </div>
  </div>
</div>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script> 
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
});

function copyText() {
    var text = document.getElementById("copyText").innerText;
    var input = document.getElementById("copyValue");
    input.value = text; // 修改文本框的内容
    input.select(); // 选中文本
    document.execCommand("copy"); // 执行浏览器复制命令
    jBox.tip("复制成功", 'info');
  }
  
function windowReload(){
	window.location.reload();
}

function updateWlOrderInfo(order_id){
	$.dialog({
		title:  "修改物流信息",
		width:  500,
		height: 400,
        lock:true ,
		content:"url:${ctx}/manager/customer/MyOrderDetail.do?method=updateWlOrderInfo&order_id=" +order_id
	});
}

function kdcx(){
	$.ajax({
		type: "POST",
		url: "${ctx}/CsAjax.do?method=getDeliveryInfo",
		data:"order_id=${af.map.id}",
		dataType: "json",
		error: function(request, settings) {alert("系统错误，请联系管理员！");},
		success: function(result) {
			if(result.ret == 1){
				$.dialog({
					title:  "物流信息查询",
					width:  960,
					height: 600,
			        //left: '100%',
			        //top: '100%',
			        //drag: false,
			        //resize: false,
			        //max: false,
			        min: false,
			        lock:true ,
					content:"url:" + result.msg
				});
			}else{
				$.jBox.alert(result.msg, '提示');
			}
			
		}
	});	
}

//]]></script>
</body>
</html>
