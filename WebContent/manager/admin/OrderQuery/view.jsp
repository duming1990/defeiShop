<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/scripts/jquery-ui/themes/base/jquery-ui.custom.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/scripts/colorbox/style3/colorbox.css" rel="stylesheet" type="text/css" />
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
      <td width="12%" nowrap="nowrap" class="title_item">用户名：</td>
      <td><c:out value="${af.map.add_user_name}"></c:out></td>
      <td nowrap="nowrap" class="title_item">商品所属企业：</td>
      <td><c:out value="${af.map.entp_name}"></c:out></td>
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
      <td>
      	第三方支付：<fmt:formatNumber value="${af.map.order_money}" pattern="0.00"/>元</br>
     	 应付款金额:<fmt:formatNumber pattern="0.00" value="${af.map.no_dis_money}" />元 </br>
     	<c:if test="${not empty af.map.yhq_tip_desc}">
	             ${af.map.yhq_tip_desc}
        </c:if>
            (运费:<fmt:formatNumber value="${af.map.matflow_price}" pattern="0.00"/>) 
            <c:if test="${af.map.money_bi gt 0}">
	          <br/>(余额抵扣:<fmt:formatNumber value="${af.map.money_bi-af.map.welfare_pay_money}" pattern="0.00"/>) 
		          <c:if test="${af.map.welfare_pay_money gt 0}">
		          	<br/>(福利金抵扣:<fmt:formatNumber value="${af.map.welfare_pay_money}" pattern="0.00"/>) 
	              </c:if> 
            </c:if> 
            <c:if test="${af.map.card_pay_money gt 0}">
	          <br/>(福利卡抵扣:<fmt:formatNumber value="${af.map.card_pay_money}" pattern="0.00"/>) 
            </c:if> 
	        <c:if test="${not empty af.map.map.huanhuo}">
            	<br/>(换货订单)
            </c:if>
            <c:if test="${not empty af.map.map.reMoney}">
          		<br/><span style="color:red">(退款:<fmt:formatNumber value="${af.map.map.reMoney}" pattern="0.00"/>)</span>
         	</c:if>
      </td>
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
    <c:if test="${not empty af.map.order_password}">
     <tr class="Tp">
      <td width="12%" nowrap="nowrap" class="title_item">自提密码：</td>
      <td><c:out value="${af.map.order_password}"></c:out>&nbsp;&nbsp;&nbsp;<input type="button" value="补发短信" class="bgButton" onclick="ziti(${af.map.id});" /></td>
    </tr>
    </c:if>
    <tr>
      <td colspan="4">
      <table width="100%" border="0"  cellpadding="0" cellspacing="0"  align="left"  class="tableClass">
          <tr>
	            <th colspan="7">订单明细信息</th>
          </tr>
          <tr>
            <th>商品名称</th>
            <th>商品数量</th>
            <th>商品单价</th>
            <th>商品总金额</th>
            <th>运费</th>
            <th>实际支付金额</th>
          </tr>
          <c:forEach var="cur" items="${orderInfoDetailList}" varStatus="vs">
            <tr>
              <td align="center" nowrap="nowrap">
                <c:if test="${cur.order_type eq 10 or cur.order_type eq 30  or cur.order_type eq 40 or cur.order_type eq 90}">
	               <c:url var="url" value="/entp/IndexEntpInfo.do?method=getCommInfo&id=${cur.comm_id}" />
                    <a href="${url}" target="_blank">${cur.comm_name}</a>
                    (${cur.comm_tczh_name})
			   </c:if>
			    <c:if test="${cur.order_type eq 50}">${cur.comm_name}</c:if>
               <c:if test="${cur.order_type eq 7 or cur.order_type eq 20}"> ${cur.comm_name} </c:if>    
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
				￥<fmt:formatNumber value="${cur.matflow_price}" pattern="0.##"/>              	
              </td>
               <td align="center" nowrap="nowrap">
              	<c:if test="${not empty cur.actual_money}">
                	￥<fmt:formatNumber   value="${cur.actual_money+cur.matflow_price}" pattern="0.00" /></br>
                </c:if>
                <c:if test="${empty cur.actual_money}">-</c:if>
                </td>
            </tr>
          </c:forEach>
        </table></td>
    </tr>
    <c:if test="${af.map.fp_state eq 1}">
    <tr>
      <td colspan="4">
      <table width="100%" border="0"  cellpadding="0" cellspacing="0"  align="left" class="tableClass">
          <tr>
            <th colspan="4">发票信息</th>
          </tr>
          <tr>
            <th>发票类型</th>
            <th>发票抬头</th>
            <th>发票税号</th>
          </tr>
          <tr>
            <td align="center" nowrap="nowrap">
             <c:if test="${af.map.invoice_type eq 1}">企业</c:if>
             <c:if test="${af.map.invoice_type eq 0}">个人</c:if>
            </td>
            <td align="center" nowrap="nowrap"><c:out value="${af.map.invoices_payable}" /></td>
            <td align="center" nowrap="nowrap"><c:out value="${af.map.invoices_no}"/></td>
          </tr>
        </table>
        </td>
    </tr>
    </c:if>
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
            <td align="center" nowrap="nowrap">
              <c:out value="${af.map.map.shippingAddress.map.full_name}" />
              <c:out value="${af.map.map.shippingAddress.rel_addr}" /></td>
          </tr>
        </table></td>
    </tr>
    </c:if>
    <!-- 线下补录订单需审核 -->
    <c:set var="isOffline" value="false"/>
    <c:if test="${af.map.order_type eq 60}">
    <c:set var="isOffline" value="true"/>
    	<tr>
	      <td colspan="4">
	      <html-el:form>
	      <html-el:hidden property="queryString" styleId="queryString" />
	      <html-el:hidden property="order_type" styleId="order_type" />
	      <table width="100%" border="0"  cellpadding="0" cellspacing="0"  align="left" class="tableClass">
	          <tr>
	            <th colspan="2">审核信息</th>
	          </tr>
	          <tr>
	            <th width="20%"><span style="color: #F00;">*</span>审核状态</th>
	            <td>
	            	<html-el:select property="audit_state" styleId="audit_state">
	            		<html-el:option value="0">待审核</html-el:option>
			            <html-el:option value="1">审核通过</html-el:option>
			            <html-el:option value="-1">审核不通过</html-el:option>
	            	</html-el:select>
	            </td>
	          </tr>
	          <tr>
	          	<th width="20%">审核意见</th>
	          	<td><html-el:textarea property="audit_desc" styleClass="webinput" styleId="audit_desc"  style="width:250px; height:80px;" ></html-el:textarea></td>
	          </tr>
	        </table>
	        </html-el:form></td>
	    </tr>
    </c:if>
    
    <tr id="btn">
      <td colspan="4" align="center">
      <c:if test="${isOffline eq true}">
      	<html-el:button property="" value="审 核" styleClass="bgButton" styleId="btn_audit" onclick="orderAudit('${af.map.id}');" />&nbsp;&nbsp;
      </c:if>
      <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" />
        </td>
    </tr>
  </table>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript" src="${ctx}/scripts/tip/jquery.quicktip.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/colorbox/jquery.colorbox.min.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script> 
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	
});

function windowReload(){
	window.location.reload();
}
function ziti(id){
	if(id){
		var submit = function (v, h, f) {
		    if (v == true) {
		    	$.jBox.tip("数据提交中...", 'loading');
		    	window.setTimeout(function () { 
		    	 $.post("?method=ziti",{id:id},function(data){
			    	 jBox.tip(data.msg, 'info');
			    	 window.setTimeout(function () {
			    	  if(data.ret == 1){
			    		window.location.reload();
			    	  }
			    	 }, 1000);
				 });
		    	}, 1000);
		    } 
		    return true;
		};
		// 自定义按钮
		$.jBox.confirm("您确定要补发自提密码吗?", "系统提示", submit, { buttons: { '确定': true, '取消': false} });
	}
}
function orderAudit(id){
	if(id){
		var audit_state = $("#audit_state").val();
		var audit_desc = $("#audit_desc").val();
		var order_type = $("#order_type").val();
		var queryString = $("#queryString").val();
		var submit = function (v, h, f) {
		    if (v == true) {
		    	$.jBox.tip("数据提交中...", 'loading');
		    	window.setTimeout(function () { 
		    	 $.post("?method=orderAudit",{id:id,audit_state:audit_state,audit_desc:audit_desc,order_type:order_type,queryString:queryString},function(data){
			    	 jBox.tip(data.msg, 'info');
			    	 window.setTimeout(function () {
			    	  if(data.ret == 1){
			    		window.location.href = "${ctx}/manager/admin/OrderQuery.do?method=list&order_type="+ data.order_type + "&" + data.queryString;
			    	  }
			    	 }, 1000);
				 });
		    	}, 1000);
		    } 
		    return true;
		};
		// 自定义按钮
		$.jBox.confirm("您确定要审核吗?", "系统提示", submit, { buttons: { '确定': true, '取消': false} });
	}
}


//]]></script>
<jsp:include page="../public_page.jsp" flush="true"/>
</body>
</html>
