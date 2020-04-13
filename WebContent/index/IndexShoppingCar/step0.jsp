<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="../../commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="MSThemeCompatible" content="no" />
<meta name="MSSmartTagsPreventParsing" content="true" />
<meta name="Description" content="${app_name}" />
<meta name="Keywords" content="${app_name}" />
<title>查看购物车 - ${app_name}</title>
<jsp:include page="../../_public_header.jsp" flush="true" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/buy.css"  />
<style type="">
#bd{
    width: 1200px;
    top:0px;
}
</style>
</head>
<body class="pg-buy pg-buy-process pg-buy pg-cart pg-buy-process" id="deal-buy">
<jsp:include page="../../_header_order.jsp" flush="true" />
<div class="component-system-message mt-component--booted" style="display:none;" id="msg_error">
  <div class="sysmsgw common-tip">
    <div class="sysmsg"> <span class="J-msg-content"><span class="J-tip-status tip-status tip-status--error"></span> </span> <span id="msg_error-tip">抱歉，数量有限，您最多只能购买 1件</span> <span class="close common-close" onclick="closedErrorMsg();">关闭</span> </div>
  </div>
</div>
<div class="bdw">
  <c:if test="${not empty cartInfoList}">
  <div id="bd" class="cf cart_not_empty" style="top:0px;">
    <c:url var="url" value="IndexShoppingCar.do" />
    <html-el:form action="${url}" styleClass="common-form form J-wwwtracker-form mt-component--booted formOrder" method="post" >
    <html-el:hidden property="method" value="step1"/>
    <div class="cart-head cf">
      <div class="cart-status"> <i class="cart-status-icon status-1"></i><span class="cart-title">我的购物车</span> </div>
    </div>
    <div class="table-section summary-table">
    <table class="buy-table">
      <tr class="order-table-head-row">
        <th width="60"> 
        <input type="checkbox" id="cart-selectall" class="ui-checkbox" checked="checked" onclick="chkSelectAll(this);" />
          <label for="cart-selectall" class="cart-select-all">全选</label>
        </th>
        <th class="desc">项目</th>
        <th class="unit-price">单价</th>
        <th class="amount">数量</th>
        <th class="col-total">小计</th>
        <th width="80" style="border-right: 1px dotted #E5E5E5;">操作</th>
      </tr>
      <tbody id="cartContentTbody">
        <c:forEach items="${cartInfoList}" var="cart">
        <tr id="cartTr${cart.id}">
          <td width="60" rowspan="1" class="select-cartItem">
          <input type="checkbox" class="ui-checkbox" name="cart_ids" checked="checked" id="cart-select${cart.id}" onclick="chkSelect(this,'${cart.id}');" value="${cart.id}"/>
            <label for="cart-select${cart.id}">&nbsp;</label>
          </td>
          <td class="desc"><c:url var="url" value="/entp/IndexEntpInfo.do?method=getCommInfo&id=${cart.comm_id}" />
            <a href="${url}" target="_blank">${fn:escapeXml(cart.entp_name)}:${fn:escapeXml(cart.comm_name)}</a>[${cart.comm_tczh_name}]</td>
          <td class="money J-deal-buy-price">¥<span id="deal-buy-price">
            <fmt:formatNumber value="${cart.pd_price}" pattern="0.##" />
            </span></td>
          <td class="deal-component-quantity">
          <div class="component-dealbuy-quantity mt-component--booted">
              <div class="dealbuy-quantity">
                <button class="minus" type="button" onclick="calcCartMoney($('#${cart.id}pd_count'),${cart.pd_price},${cart.id}, -1);">-</button><input type="text" class="f-text J-quantity J-cart-quantity" maxlength="4" id="${cart.id}pd_count" value="${cart.pd_count}" onkeyup="calcCartMoney($('#${cart.id}pd_count'),${cart.pd_price},${cart.id},null);" onblur="calcCartMoney($('#${cart.id}pd_count'),${cart.pd_price},${cart.id},null,true);" /><button class="item plus" type="button"  onclick="calcCartMoney($('#${cart.id}pd_count'),${cart.pd_price},${cart.id},1);">+</button>
                <c:set var="color" value="" />
                <c:if test="${cart.pd_count > cart.map.pd_max_count}"><c:set var="color" value="color:red" /></c:if>
                <div id="msg${cart.id}" style="${color}">${cart.map.inventoryTip}</div>
              <input type="hidden" name="pd_max_count" value="${cart.map.pd_max_count}" id="max${cart.id}"/>
              <input type="hidden" class="minSumPrice" data-id="${cart.id}" id="${cart.id}minSumPrice" value="${(cart.pd_count * cart.pd_price)}"/>
            </div>
      </div>
      </td>
      <td class="money total rightpadding col-total">¥<span id="J-deal-buy-total${cart.id}" class="thisTrCheck">${(cart.pd_count * cart.pd_price)}</span> </td>
        <td width="80" class="op list-delete" style="border-right: 1px dotted #E5E5E5;"><a class="delete" onclick="delCart(this,${cart.id});">删除</a> </td>
      </tr>
      </c:forEach>
      </tbody>
      <tr>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td colspan="5" class="extra-fee total-fee"><strong>商品总额</strong>： <span class="inline-block money">¥<strong id="J-cart-total">
          <fmt:formatNumber value="${totalMoney}" pattern="0.##"/>
          </strong> </span> </td>
      </tr>
    </table>
  </div>
  <div class="form-submit">
    <c:if test="${lack_inventorty}">
      <input type="button" class="btn btn-large btn-buy" id="form_sbt" name="buy" value="去结算" onclick="sbt();" />
    </c:if>
    <c:if test="${!lack_inventorty}">
      <input type="button" class="btn btn-large btn-disabled" id="form_sbt" name="buy" value="去结算" />
    </c:if>
  </div>
  
  </html-el:form>
  <div>
 </div>
</div>
</c:if>
<div id="bd" class="cf cart_empty" style="display:none;">
  <div id="content">
    <div class="mainbox">
      <div class="cart-empty">
        <div class="cart-empty-tips cf"> <i class="cart-empty-icon"></i>
          <h3>您的购物车还是空的</h3>
          <p>点击购物车可以快速添加，您可以：</p>
          <c:url var="url" value="/index.do" />
          <p class="suggestion">去<a href="${url}">首页</a>购买喜欢的产品</p>
        </div>
      </div>
      <div> </div>
    </div>
  </div>
</div>
</div>
<jsp:include page="../../_footer.jsp" flush="true" />
<c:url var="url_my_order" value="/manager/customer/MyOrder.do?par_id=1100500000&mod_id=1100500100"></c:url>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/scripts/jquery.timers.js"></script>
<script type="text/javascript">//<![CDATA[
 
                                          
 var lack_inventorty = "${lack_inventorty}";
 $(document).ready(function(){
	 <c:if test="${empty cartInfoList}">
	 	$(".cart_empty").show();
	 </c:if>
 });
 
 function delCart(thisobj, cart_id) {
	if (null != cart_id || undefined != cart_id) {
		 var submit = function (v, h, f) {
			    if (v == true) {
			    	$.post("CsAjax.do?method=delCart",{cart_id : cart_id},function(data){
						if (data.result) {
							$("#cartTr" + cart_id).remove();
							calTotalMoney();
							jugdeButton();
						}
					    if($("tr[id*=cartTr]").size() <= 0) {
					    	$(".cart_not_empty").empty();
					    	$(".cart_empty").show();
					    }
					});
			    } 
			    return true;
			};
		confirmDel("您确定要删除该订单吗？",submit);
	} 
}
 
function confirmDel(tip, submit){ 
		$.jBox.confirm(tip, "${app_name}", submit, { buttons: { '确定': true, '取消': false} });
}
 
 function calcCartMoney(JqObj, pd_price, cart_id, addNum, isBlur) {
		if (null == addNum || undefined == addNum) {addNum = 0;}
		if (null == isBlur || undefined == isBlur) {isBlur = false;}
		var ajax = true;
		var _regInt = /^[1-9]\d{0,2}$/g;;
		var _regCurr = /^[\+]?\d*?\.?\d*?$/;
		var $this = JqObj;
		var count = parseFloat($this.val());
		
		if (!_regInt.test(count)) {
			ErrorMsgAndClosed("输入的数量有误,应为[1-999]");
			$this.val(1);
			count = 1;
			ajax = true;
		}
		count += parseFloat(addNum); 
		
		var pd_max_count = $("#max" + cart_id).val();
		if(isNaN(pd_max_count)) pd_max_count = 0;
		//alert("count:"+count+"  pd_max_count:"+pd_max_count)
		if ((count > pd_max_count)) {
			ErrorMsgAndClosed("很抱歉，该产品库存不足，货到后我们将第一时间通知您！");	
			$("#msg"+ cart_id).html("库存不足");
			$("#msg"+ cart_id).css("color","red");
			$("#form_sbt").removeClass("btn-buy").addClass("btn-disabled");
			$("#form_sbt").removeAttr("onclick");
			ajax = false;
		} else{
			$("#msg"+ cart_id).empty();
		}
		
		if(count<=0){count=1;ajax = true;}
		$this.val(count);
		var price = parseFloat(pd_price);
		if(isNaN(price)){ ajax = false;return false;}
		
		$("#J-deal-buy-total" + cart_id).text((count * price).toFixed(2));
		
		jugdeButton();
		calTotalMoney();
		
		//改变当前可使用红包价格
		//找同级别的商品类别，如果是红包商品，进行计算
		var comm_type = $("#comm_type_"+cart_id).val();
		if(null != comm_type && comm_type == 6){
			var red_scale = $("#red_scale_"+cart_id).val();
			var red_money = price * parseFloat(red_scale) * count;
			$("#can_use_red_money_"+cart_id).text(red_money);
			$("#max_use_red_money_"+cart_id).val(red_money);
			$("#red_money_"+cart_id).val(red_money);
			
		}
		var matflow_price = 0;
		var cur_v = $("#" + cart_id + "matflow_price").val();
		if (!(null == cur_v || undefined == cur_v)) {
			var cur_v_array = cur_v.split(",");
		    matflow_price = parseFloat(cur_v_array[1]); ;
			if (null == matflow_price || undefined == matflow_price) {matflow_price =0;}
		}
		$("#" + cart_id + "minSumPrice").val((count * price) + parseFloat(matflow_price));
		
		var total_money = 0;
		$("input[class*='minSumPrice']").each(function(){
			var cart_id = $(this).attr("data-id");
			if($("#cart-select" + cart_id).is(":checked")){
				total_money = total_money + parseFloat($(this).val());
			}
		});
		
		$("#J-cart-total").text(total_money)
		 var flag = true;
		 $("#cartContentTbody").find("input[name*='cart_ids']").map(function(){
		 	if($(this).is(':checked')){
		 		flag=false;
		 	}
		 });
		 if(flag){
			 $("#cart-selectall").removeAttr("checked");
			 $("#form_sbt").removeClass("btn-buy").addClass("btn-disabled");
			 $("#form_sbt").removeAttr("onclick");
		 }
		
		
		if(ajax && !isBlur||parseFloat(addNum)<0){
			$.post("CsAjax.do?method=updateCartInfo",{id : cart_id,pd_count:count},function(data){
	    		if (!data.result) {
	    			alert("\u5f88\u9057\u61be\uff0c\u7531\u4e8e\u7cfb\u7edf\u7e41\u5fd9\uff0c\u8bf7\u7a0d\u540e\u518d\u8bd5\uff01");
	        	}
			});
		}
	}
 
 function jugdeButton(){
	 
	 var flag = true;
	 $("#cartContentTbody").find("div[class='dealbuy-quantity']").map(function(){
	 	var pd_max_count = $(this).find("input[id*='max']").val();
	 	var input_count = $(this).find("input[id*='pd_count']").val();
	 	if(parseInt(input_count) > parseFloat(pd_max_count)){
	 		flag = false;
	 		return false;
	 	}
	 });
	 if(flag){
		 $("#form_sbt").removeClass("btn-disabled").addClass("btn-buy");
		 $("#form_sbt").attr("onclick","sbt()");
	 }else{
		 $("#form_sbt").removeClass("btn-buy").addClass("btn-disabled");
		 $("#form_sbt").removeAttr("onclick");
	 }
	 
 }
 
 function calTotalMoney(){
	 var total_money = 0;
	 $("#cartContentTbody").find("span[class='thisTrCheck']").map(function(){
			total_money += parseFloat($(this).text());
	});
	$("#J-cart-total").text(total_money.toFixed(2));
 }
 
 function ErrorMsgAndClosed(msg){
		$("#msg_error-tip").text(msg);
		$("#msg_error").show();
		setTimeout(closedErrorMsg, 2000);
 }
 
 
 function closedErrorMsg(){
	 $("#msg_error").hide();
 }
 
 function sbt(){
	$(".formOrder").get(0).action="?method=step1"; 
	$(".formOrder").get(0).submit();
 }
 
function chkSelectAll(obj){
	if(obj.checked){
		$("input[name*='cart_ids']").attr("checked",true); 
		 $("#cartContentTbody").find("span[id*='J-deal-buy-total']").map(function(){
			 $(this).addClass("thisTrCheck");
		 });
		 if(lack_inventorty){
		     $("#form_sbt").removeClass("btn-disabled").addClass("btn-buy");
		     $("#form_sbt").attr("onclick","sbt()");
		 }else{
			 $("#form_sbt").removeClass("btn-buy").addClass("btn-disabled");
			 $("#form_sbt").removeAttr("onclick");
		 }
	}else{
		$("input[name*='cart_ids']").removeAttr("checked"); 
		 $("#cartContentTbody").find("span[id*='J-deal-buy-total']").map(function(){
			 $(this).removeClass("thisTrCheck");
		 });
		 $("#form_sbt").removeClass("btn-buy").addClass("btn-disabled");
		 $("#form_sbt").removeAttr("onclick");
	}
	calTotalMoney();
} 
function chkSelect(obj,cart_id){
	if(obj.checked){
		 $(obj).attr("checked",true); 
		 $("#cart-selectall").attr("checked",true);
		 if(lack_inventorty){
			 $("#form_sbt").removeClass("btn-disabled").addClass("btn-buy");
			 $("#form_sbt").attr("onclick","sbt()");
			 $("#J-deal-buy-total" + cart_id).addClass("thisTrCheck");
		 }else{
			 $("#form_sbt").removeClass("btn-buy").addClass("btn-disabled");
			 $("#form_sbt").removeAttr("onclick");
		 }
	}else{
		$(obj).removeAttr("checked"); 
		 var flag = true;
		 $("#cartContentTbody").find("input[name*='cart_ids']").map(function(){
		 	if($(this).is(':checked')){
		 		flag=false;
		 	}
		 });
		 if(flag){
			 $("#cart-selectall").removeAttr("checked");
			 $("#form_sbt").removeClass("btn-buy").addClass("btn-disabled");
			 $("#form_sbt").removeAttr("onclick");
		 }
		 $("#J-deal-buy-total" + cart_id).removeClass("thisTrCheck");
	}
	calTotalMoney();
} 

$('body').everyTime('1s',function(){
	$.ajax({
		type: "POST",
		url: "${ctx}/CsAjax.do?method=getCartInfoState",
		dataType: "json",
		error: function(request, settings) {},
		success: function(data) {
			if(data.ret == 1){
			   $.jBox.tip("页面跳转中，正在进入我的订单...", 'loading');
	           window.setTimeout(function () { 
					$(".cart_not_empty").empty();
					$(".cart_empty").show();
					// $.jBox.tip("购物车已清空", "success");
				
					$('body').stopTime();
				    $.post("${ctx}/CsAjax.do?method=getUrlLinkModId",{mod_id:1100500100},function(data){
						if(data.ret == 1){
							var parId_cookie = data.par_id + "," + data.data_url;
							if ($.isFunction($.cookie)) $.cookie("parId_cookie", parId_cookie, { path: '/' });
							location.href= "${ctx}/manager/customer/index.shtml";
						}
					});
				}, 2000);
			
			}
		}
	});	
});

//]]></script>
</body>
</html>
