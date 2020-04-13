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
<body>
<jsp:include page="../_header.jsp" flush="true" />	
<div class="content"> 
  <!--article-->
  <div class="list-view">
    <ul class="list-ul" id="ul_data">
      <!-- 未付款、已付款、待评价、物流单、退款单  start -->
      <c:forEach items="${orderInfoDetailsList}" var="cur">
      	<li data-id="${cur.id}"  id="order_state_${cur.id}">
      		<div class="list-item list-room">
	          <div class="info">
	            <h2 class="title">${cur.comm_name}</h2>
	            <div class="ordermain">
	              <p>总价：<em>¥<fmt:formatNumber value="${cur.good_sum_price}" pattern="0.##"/></em></p>
	              <p>数量：<em>${cur.good_count}</em></p>
	            </div>
	            
           		<div class="orderedit"> <span></span> 
           		<span> 
           			<c:url var="url" value="MMyOrderReturn.do?id=${cur.id}" />
           			<c:url var="txt" value="选择" />
           			<c:if test="${cur.is_tuihuo eq 0}">
           				<c:if test="${not empty cur.map.order_return_id}">
<%--            					<c:url var="url" value="MMyOrderReturn.do?id=${cur.id}&order_return_id=${cur.map.order_return_id}" /> --%>
							<c:url var="url" value="/m/MMyTuiHuo.do?method=view&id=${cur.map.order_return_id}" />
           					<c:url var="txt" value="已申请" />
           				</c:if>
           				<a href="javascript:goUrl('${url}');" class="buy">${txt}</a>
           			</c:if>
           			<c:url var="url" value="/m/MMyTuiHuo.do?method=view&id=${cur.map.order_return_id}" />
           			<c:if test="${cur.is_tuihuo eq 1}">
           			<a href="javascript:goUrl('${url}');" class="buy">已退换货(查看)</a>
           			</c:if>
           		</span> </div>
	          </div>
	        </div>
      	</li>
      </c:forEach>
    </ul>
  </div>
  
  <!-- 延迟加载交互提示 -->
  <div class="list-other-more">
    <c:set var="display" value="none" />
    <c:if test="${appendMore eq 1}">
    <c:set var="display" value="block" />
    </c:if>
    <div class="load-more btnpage" style="display: ${display};" id="appendMore" onclick="appendMore()" data-pages="1">查看更多</div>
  </div>
  <form id="bottomPageForm" name="bottomPageForm" method="post" action="${ctx}/m/MMyOrder.do">
  	<input type="hidden" name="method" value="list" />
<%--   	<input type="hidden" name="order_type" value="${af.map.order_type}" /> --%>
<%--   	<input type="hidden" name="order_state" value="${af.map.order_state}" /> --%>
<%--   	<input type="hidden" name="mod_id" value="${af.map.mod_id}" /> --%>
  </form>
  <!-- ——page --> 
</div>
<jsp:include page="../_footer.jsp" flush="true" />
<script type="text/javascript">//<![CDATA[
var ctx = "${ctx}";

function appendMore() {
	getData();
}
function getData() {
	Common.loading();
	var page = $("#appendMore").attr('data-pages');
	page = Number(page);
	$.ajax({
		type: "POST",
		url: ctx + "/m/MMyOrder.do?method=getOrderListJson",
		data: 'startPage=' + page +'&' + $("#bottomPageForm").serialize(),
		dataType: "json",
		error: function(request, settings) {},
		success: function(datas) {
			var html = "";
			$("#appendMore").hide();
			Common.hide();
			if (datas.ret == 1) {
				var dataList = eval(datas.dataList);
				$.each(dataList, function(i,data){  
					var url = ctx + "/m/MEntpInfo.do?id=" + data.id;
					var order_logo = ctx + "/styles/imagesPublic/user_header.png";
					html += '<li class="li_"' + data.trade_index +' data-id="'+ data.id +'" data-trade="'+ data.trade_index +'" id="order_state_'+data.id+'" date-isshixiao="'+data.is_shixiao+'">';
					html += '<div class="list-item list-room">';
					html += '<div class="info">';
					html += '<h2 class="title">'+ data.comm_name +'<i class="checkbox check-icon-toggle" data-id="'+ data.trade_index +'"></i> </h2>';
					html += '<div class="ordermain">';
					html += '<p>下单时间：<em>'+data.order_date+'</em></p>';
					html += '</div>';
					html += '<div class="ordermain">';
					html += '<p>总价：<em>¥'+data.order_money+'</em></p>';
					html += '<p>状态：<em>'+data.order_state_name+'</em></p>';
					html += '</div>';
					if(data.order_state == 0){
						html += '<div class="orderedit"> <span></span> <span> <a href="" class="buy">付款</a> </span> &nbsp;&nbsp; <span> <a href="javascript:updateState(\'MMyOrder.do\', \'updateState\', '+data.id+', -10, this);" class="buy">取消</a> </span></div>';
					}else if(data.order_state == 10){
						if(data.order_type == 10 || data.order_type == 50){
							html += '<div class="orderedit"> <span></span> <span> <a href="javascript:updateState(\'MMyOrder.do\', \'updateState\', '+data.id+', -20, this);" class="buy">退款</a> </span> </div>';
						}
					}else if(data.order_state == 20){
						html += '<div class="orderedit"> <span></span> <span> <a href="javascript:updateState(\'MMyOrder.do\', \'updateState\', '+data.id+', 40, this);" class="buy">确认收货</a> </span> </div>';
					}else if(data.order_state == -10){
						html += '<div class="orderedit"> <span></span> <span> <a href="javascript:confirmDelete(null,\'MMyOrder.do\', \'id=${cur.id}&order_type=${af.map.order_type}&order_state=${af.map.order_state}\');" class="buy">删除</a> </span> </div>';
					}
					html += '</div>';
					html += '</div>';
					html += '<a href="'+ctx+'/m/MMyOrderDetail.do?mehod=view&order_id='+data.id+'&from=user" class="btn-combg"></a> ';
					html += '</li>';
					
				});
				page += 1;
				$("#appendMore").attr('data-pages',page);
				if (datas.appendMore == 1) {
					$("#appendMore").show();
				} else {
					mui.toast("全部加载完成");
				}
				$("#ul_data").append(html);
			} else {
				mui.toast(datas.msg);
			}
			if (datas.ret == 2) {
				html = "<li>"+datas.msg+"</li>";
			}
		
		}
	});	
}

//]]></script>
</body>
</html>