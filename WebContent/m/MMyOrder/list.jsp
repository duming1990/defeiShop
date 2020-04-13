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
<link href="${ctx}/m/styles/css/my/order-details.css" rel="stylesheet" type="text/css" />
</head>
<body>
<jsp:include page="../_header.jsp" flush="true" />
<div class="content mui-scroll"> 
  <div class="myorder-ors">
  	<a href="${ctx}/m/MMyOrder.do?method=list&order_type=${af.map.order_type}&mod_id=${af.map.mod_id}" id="ors_">全部</a>
	<a href="${ctx}/m/MMyOrder.do?method=list&order_type=${af.map.order_type}&mod_id=${af.map.mod_id}&order_state=0" id="ors_0">
	待支付<c:if test="${dai_fukuan_num ne 0}">(${dai_fukuan_num})</c:if></a>
	 <a href="${ctx}/m/MMyOrder.do?method=list&order_type=${af.map.order_type}&mod_id=${af.map.mod_id}&order_state=10" id="ors_10">
	 待发货<c:if test="${dai_fahuo_num ne 0}">(${dai_fahuo_num})</c:if></a>
	 <a href="${ctx}/m/MMyOrder.do?method=list&order_type=${af.map.order_type}&mod_id=${af.map.mod_id}&order_state=20" id="ors_20">
	 待收货<c:if test="${dai_shouhuo_num ne 0}">(${dai_shouhuo_num})</c:if></a>
	 <a href="${ctx}/m/MMyOrder.do?method=list&order_type=${af.map.order_type}&mod_id=${af.map.mod_id}&order_state=40&is_comment=0" id="ors_40">
	 待评价<c:if test="${dai_pingjia_num ne 0}">(${dai_pingjia_num})</c:if></a>
	<a href="${ctx}/m/MMyOrder.do?method=list&order_type=${af.map.order_type}&mod_id=${af.map.mod_id}&order_state=50" id="ors_50">已完成</a>
  </div>
  
  <div id="div_orderlist">
    <div id="allOrders" style="position:relative">
    
    <c:if test="${empty entityList}">
	    <div id="no_data" style="background:#fff; padding:15px; margin-bottom:10px;">暂无订单~</div>
	</c:if>
    
    <c:forEach items="${entityList}" var="cur">
     <div class="m item border-1px mar9">
        <div class="mt bdb-1px">
            ${cur.trade_index}
            <span class="i-info">
            <script type="text/javascript">showOrderState(${cur.order_state},${cur.pay_type},${cur.order_type})</script>
            <c:if test="${cur.order_state eq -10}">
              <a class="i-complete" onclick="orderDel('${cur.id}')"></a>
            </c:if>
            </span>
        </div>
       <div class="mc">
        <c:url var="url" value="/m/MMyOrderDetail.do?method=view&order_id=${cur.id}&from=user" />
		  <a onclick="goUrl('${url}');">
		     <div class="imc-con bdb-1px">
		     
		     <!--多个商品-->
		     <c:if test="${fn:length(cur.orderInfoDetailsList) > 1}">
		      <div class="c-type-wrap carousel" id="carousel0">
		       <ul class="step-tab -type">
		      <c:forEach items="${cur.orderInfoDetailsList}" var="curSon">   
		      <li>
		        <div class="liimg">
		         <c:set var="imgSrc" value="${ctx}/styles/imagesPublic/user_header.png" />
		         <c:if test="${not empty curSon.map.commInfo.main_pic}">
		         <c:set var="imgSrc" value="${ctx}/${curSon.map.commInfo.main_pic}@s400x400" />
		         </c:if>
			     <img src="${imgSrc}" width="120" height="120"/>
				</div>
		      </li>
		      </c:forEach>
		      </ul>
		    </div>
		    </c:if>
		    
		    <!--一单一个商品显示图片和信息-->
		    <c:if test="${fn:length(cur.orderInfoDetailsList) == 1}">
		    <c:forEach items="${cur.orderInfoDetailsList}" var="curSon">  
		    <div class="imc-one">
			  <div class="imco-l">
			    <div class="imco-l-img-box">
			        <div class="imco-l-img">
				     <c:set var="imgSrc" value="${ctx}/styles/imagesPublic/user_header.png" />
			         <c:if test="${not empty curSon.map.commInfo.main_pic}">
			         <c:set var="imgSrc" value="${ctx}/${curSon.map.commInfo.main_pic}@s400x400" />
			         </c:if>
			         <img src="${imgSrc}" width="120" height="120"/>
			        </div>
			    </div>
			    </div>
			    <div class="imco-r-content">
			        <div class="imco-r">
			        <c:if test="${not empty curSon.map.commInfo}">
			        	<c:out value="${fnx:abbreviate(curSon.map.commInfo.comm_name,50, '...')}" escapeXml="true" />
			        </c:if>
			        <c:if test="${not empty curSon.comm_tczh_name}">&nbsp;[${curSon.comm_tczh_name}]</c:if>
			        <c:if test="${empty curSon.map.commInfo}">
			        	<c:out value="${fnx:abbreviate(curSon.comm_name,15, '...')}" escapeXml="true" />
			        </c:if>
				      <c:if test="${not empty cur.map.wlOrderInfo}"> 
			            <p>快递公司名称：${cur.map.wlOrderInfo.wl_comp_name}</p>
			            <p>运单号：${cur.map.wlOrderInfo.waybill_no}</p>
			          </c:if>
			        
			        </div>
			    </div>
			 </div>
			 </c:forEach>
		    </c:if>
		    
		   </div>
		  </a>
		</div>
		<div class="mb cf">
			<span class="true-payed">实付款:</span>
  		 	<span class="imb-num" style="margin-right: 10px;">¥<fmt:formatNumber value="${cur.order_money}" pattern="0.##"/></span>
	  		 <c:if test="${cur.money_bi gt 0}">
		  		 <span class="true-payed">余额抵扣:</span>
		  		 <span class="imb-num" style="margin-right: 10px;">¥<fmt:formatNumber value="${cur.money_bi - cur.welfare_pay_money}" pattern="0.##"/></span>
		  		 
		  		 <c:if test="${cur.welfare_pay_money gt 0}">
			  		 <span class="true-payed">福利金抵扣:</span>
			  		 <span class="imb-num">¥<fmt:formatNumber value="${cur.welfare_pay_money}" pattern="0.##"/></span>
		  		 </c:if>
	  		 </c:if>
	  		 
	  		 <c:if test="${cur.card_pay_money gt 0}">
		  		 <span class="true-payed">福利卡抵扣:</span>
		  		 <span class="imb-num" style="margin-right: 10px;">¥<fmt:formatNumber value="${cur.card_pay_money}" pattern="0.##"/></span>
	  		 </c:if>
		</div>
		
  		 <div class="mb cf" id="order_state_${cur.id}" date-isshixiao="${cur.is_shixiao}" data-order-type="${cur.order_type}">
  		 	
	  		 <c:choose>
	  		  <c:when test="${cur.order_state eq 0}">
		    		 <c:url var="url" value="" />
		    		  <c:url var="payUrl" value="MMyCartInfo.do?method=selectPayType&trade_index=${cur.trade_index}&pay_type=${cur.pay_type}" />
		    		 <c:if test="${(cur.order_type eq 70)}">
		    		 	<c:url var="payUrl" value="MWelfareCartInfo.do?method=selectPayType&trade_index=${cur.trade_index}&pay_type=${cur.pay_type}" />
		    		 </c:if>
		    		 <div class="imb-btn-box">
		    		   <a class="imb-btn" href="javascript:goUrl('${payUrl}');">去支付</a>
		    		 </div>
		    		 <div class="imb-btn-box">
		    			<a class="imb-btn" onclick="updateState('MMyOrder.do', 'updateState', ${cur.id}, -10, this);">取消订单</a>
		    		 </div>
	  		  </c:when>
	         <c:when test="${cur.order_state eq 10}">
	           <c:if test="${(cur.order_type eq 10)}">
	           	<c:if test="${fn:length(cur.orderInfoDetailsList) > 1}">
	             <div class="imb-btn-box"><a class="imb-btn" onclick="tuihuo(${cur.id})">去退款</a></div>
	             </c:if>
	             <c:if test="${fn:length(cur.orderInfoDetailsList) == 1}">
	             <div class="imb-btn-box"><a class="imb-btn" href="javascript:updateState('MMyOrder.do', 'updateState', '${cur.id}', 15, this);">去退款</a></div>
	             </c:if>
	           </c:if>
	           <c:if test="${(cur.order_type eq 80)}">
	           	<c:if test="${fn:length(cur.orderInfoDetailsList) > 1}">
	             <div class="imb-btn-box"><a class="imb-btn" onclick="tuihuo(${cur.id})">去退款</a></div>
	             </c:if>
	             <c:if test="${fn:length(cur.orderInfoDetailsList) == 1}">
	             <div class="imb-btn-box"><a class="imb-btn" href="javascript:updateState('MMyOrder.do', 'updateState', '${cur.id}', 15, this);">去退款</a></div>
	             </c:if>
	           </c:if>
	         </c:when>
	  		 <c:when test="${cur.order_state ge 20}">
	<%--   		   <c:if test="${cur.delay_shouhuo ne 1}"> --%>
	<%-- 	          <div class="imb-btn-box"><a class="imb-btn" onclick="delayShouhuo('MMyOrder.do', ${cur.id}, this);">延迟收货</a></div>  --%>
	<%-- 	       </c:if> --%>
				<c:if test="${(cur.is_ziti ne 1) and (not empty cur.map.wlOrderInfo) and (cur.order_type ne 7)}">
	                <div class="imb-btn-box"><a class="imb-btn" onclick="kdcx(${cur.id})">物流</a></div>
	          	</c:if>
	          	<c:if test="${cur.order_state eq 20}">
	          		<div class="imb-btn-box"><a class="imb-btn" onclick="updateState('MMyOrder.do', 'updateState', '${cur.id}', 40, this);">确认收货</a></div>
	         	</c:if>
	          
		     </c:when>
	  		</c:choose>
	  		<c:if test="${cur.order_state eq 10}">
	           <c:if test="${(cur.order_type eq 100)}">
	           	 <c:if test="${cur.is_leader eq 1 }">
	           	 <c:set var="ptDetailUrl" value="${ctx}/m/MEntpInfo.do?method=getPtDetails&leaderOrderId=${cur.leader_order_id}&isLeader=true" />
	             	<div class="imb-btn-box" onclick="goUrl('${ptDetailUrl}')"><a class="imb-btn" onclick="">去分享</a></div>
	             </c:if>
	             <c:set var="ptDetailUrl" value="${ctx}/m/MEntpInfo.do?method=getPtDetails&leaderOrderId=${cur.leader_order_id}" />
	             <div class="imb-btn-box"><a class="imb-btn" onclick="goUrl('${ptDetailUrl}')">拼团详情</a></div>
	             
	           </c:if>
	        </c:if>
	  		<c:if test="${cur.order_state gt 15 && ((cur.order_type eq 10) or (cur.order_type eq 30))}">
	          	<c:url var="returnUrl" value="MMyOrderReturn.do?method=list&id=${cur.id}" />
	         	 <div class="imb-btn-box"><a id="" href="javascript:goUrl('${returnUrl}');" class="imb-btn">售后</a></div>
	  		</c:if>
	  		<c:if test="${(cur.order_state eq 40 or cur.order_state eq 50) && (cur.order_type eq 10 or cur.order_type eq 7 or cur.order_type eq 30)}">
	  			<div class="imb-btn-box"><a class="imb-btn" href="#" onclick="orderInfoAddCart('${cur.id}')">购买</a></div>
	  			<c:url var="returnUrl" value="MMyComment.do?method=chooseList&id=${cur.id}" />
	  			<c:if test="${cur.is_comment eq 0}">
	  			<div class="imb-btn-box"><a class="imb-btn" href="javascript:goUrl('${returnUrl}');">评价</a></div>
	  			</c:if>
	  		</c:if>
<%--   		<c:if test="${cur.order_state ge 20 }"> --%>
<%--   		 <c:url var="url" value="/m/MMyOrderDetail.do?method=view&order_id=${cur.id}&from=user" /> --%>
<%--   		 	<div class="imb-btn-box"><a class="imb-btn" onclick="goUrl('${url}');">发货备注</a></div> --%>
<%--   		 </c:if> --%>
  	   </div>
      </div> 
     </c:forEach> 
    </div>
  </div>
  
  <!-- 延迟加载交互提示 -->
  <div class="list-other-more">
    <c:set var="display" value="none" />
    <c:if test="${appendMore eq 1}">
    <c:set var="display" value="block" />
    </c:if>
    <div class="load-more btnpage" style="display: ${display};margin-top:10px;" id="appendMore" onclick="appendMore()" data-pages="1">查看更多</div>
  </div>
  <form id="bottomPageForm" name="bottomPageForm" method="post" action="${ctx}/m/MMyOrder.do">
  	<input type="hidden" name="method" value="list" />
  	<input type="hidden" name="order_type" value="${af.map.order_type}" />
  	<input type="hidden" name="order_state" value="${af.map.order_state}" />
  	<input type="hidden" name="mod_id" value="${af.map.mod_id}" />
  </form>
  <!-- ——page --> 
</div>
<jsp:include page="../_footer.jsp" flush="true" />
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.js"></script>
<script type="text/javascript">//<![CDATA[
var ctx = "${ctx}";


<c:if test="${not empty af.map.order_state}">
  $("#ors_" + ${af.map.order_state}).addClass("cur").siblings().removeClass();
</c:if>

<c:if test="${empty af.map.order_state}">
  $("#ors_").addClass("cur").siblings().removeClass();
</c:if>

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
					
					html += '<div class="m item border-1px mar9">';
					html += '<div class="mt bdb-1px">';
					html +=  data.trade_index;
					html += '<span class="i-info">'+data.order_state_name+'';
					if(data.order_state == -10){
						html += '<a class="i-complete" onclick="orderDel(\''+ data.id +'\')"></a>';
					}
					html += '</span></div>';
					html += '<div class="mc">';
					var url = '${ctx}/m/MMyOrderDetail.do?method=view&order_id='+data.id+'&from=user';
					html += '<a onclick="goUrl(\''+ url +'\');">';
					html += '<div class="imc-con bdb-1px">';
					
					
					//判断是多个商品 还是单个商品
					
					var orderDetailList = data.orderInfoDetailsList;
					
					if(data.detailSize > 1){
						html += '<div class="c-type-wrap carousel" id="carousel0">';
						html += '<ul class="step-tab -type">';
										
						$.each(orderDetailList, function(j,dataDetails){ 
							html += '<li>';
							html += '<div class="liimg">';
							html += '<img src="/' +  dataDetails.map.commInfo.main_pic + '" width="120" height="120">';
							html += '</div>';
							html += '</li>';
						});
						html += '</ul></div>';
						html += '</div>';
					}else if(data.detailSize == 1){
						$.each(orderDetailList, function(j,dataDetails){ 
							html += '<div class="imc-one">';
							html += '<div class="imco-l">';
							html += '<div class="imco-l-img-box">';
							html += '<div class="imco-l-img">';
							html += '<img src="/' +  dataDetails.map.commInfo.main_pic + '" width="120" height="120">';
							html += '</div></div></div>';
							html += '<div class="imco-r-content">';
							html += '<div class="imco-r">' + dataDetails.map.commInfo.comm_name + '</div>';
							html += '</div></div>';
						});
					}
					
					
					html += '</a>';
					html += '</div>';
					html += '<div class="mb cf" id="order_state_'+data.id+'" date-isshixiao="'+data.is_shixiao+'" data-order-type="'+data.order_type+'">';
					html += '<span class="true-payed">实付款:</span><span class="imb-num">¥'+data.order_money+'</span>';
					if(data.money_bi > 0 ){
						html += '<span class="true-payed">余额抵扣:</span>';
						html += '<span class="imb-num">¥'+data.money_bi+'</span>';
					}
					html += '</div>'
					
					html += '<div class="mb cf" id="order_state_'+data.id+'" date-isshixiao="'+data.is_shixiao+'" data-order-type="'+data.order_type+'">';
					
					if(data.order_state == 0){
						var payUrl = "${ctx}/m/MMyCartInfo.do?method=selectPayType&trade_index="+ data.trade_index +"&pay_type="+ data.pay_type;
						html += '<div class="imb-btn-box"><a class="imb-btn" href="javascript:goUrl(\''+ payUrl +'\');">去支付</a></div>';
						html += '<div class="imb-btn-box"><a class="imb-btn" href="javascript:updateState(\'MMyOrder.do\', \'updateState\', '+data.id+', -10, this);">取消订单</a></div>';
					} else if(data.order_state == 20){
						html += '<div class="imb-btn-box"><a class="imb-btn" href="javascript:updateState(\'MMyOrder.do\', \'updateState\', '+data.id+', 40, this);">确认收货</a></div>';
						if(data.delay_shouhuo != 1) {
							html += '<div class="imb-btn-box"><a class="imb-btn" onclick="delayShouhuo(\'MMyOrder.do\','+data.id+', this);">延迟收货</a></div>';
						}
					} else if(data.order_state == 10){
						if(data.order_type == 10){
							html += '<div class="imb-btn-box"><a class="imb-btn" href="javascript:updateState(\'MMyOrder.do\', \'updateState\', '+data.id+', -20, this);">去退款</a></div>';
						}
					}
					if(data.order_state > 15 && (data.order_type == 10 || data.order_type == 30)){
						
						var url = 'MMyOrderReturn.do?method=list&id='+data.id;
						html += '<div class="imb-btn-box"><a id="" href="javascript:goUrl('+url+');" class="imb-btn">售后</a></div>';
												
					}
					html += '</div>';
					html += '</div> ';
				});
				page += 1;
				$("#appendMore").attr('data-pages',page);
				if (datas.appendMore == 1) {
					$("#appendMore").show();
				} else {
					mui.toast("全部加载完成");
				}
				$("#allOrders").append(html);
			} else {
				mui.toast(datas.msg);
			}
			if (datas.ret == 2) {
				html = "<li>"+datas.msg+"</li>";
			}
		
		}
	});	
}
function kdcx(order_id){
	$.ajax({
		type: "POST",
		url: "${ctx}/CsAjax.do?method=getDeliveryInfo",
		data:"order_id="+order_id,
		dataType: "json",
		error: function(request, settings) {alert("系统错误，请联系管理员！");},
		success: function(result) {
			if(result.ret == 1){
// 				mui.toast(result.msg);
				$.dialog({
					title:  "物流信息查询",
					width:  560,
					height: 280,
			        left: '100%',
			        top: '100%',
			        drag: false,
			        resize: false,
			        max: false,
			        min: false,
					content:"url:" + result.msg
				});
			}else{
				$.jBox.alert(result.msg, '提示');
			}
			
		}
	});	
}

function orderDel(id){
	Common.confirm("你确定要删除该订单吗？",["确定","取消"],function(){
		$.post("MMyOrder.do?method=updateState",{id:id,state:90},function(datas){
			if(datas.ret == 1){
				Common.loading();
				window.setTimeout(function () {
					window.location.reload();
				}, 1000);
			}else{
				mui.toast(data.msg);
			}
		});
	},function(){});
}
function tuihuo(id){
	$.post("MMyOrder.do?method=getOrderReturnInfoCount",{order_id:id},function(datas){
		if(datas.ret == 1){
			if(datas.returnInfoCount > 0){
				
				Common.loading();
				window.location.href='${ctx}/m/MMyOrderReturn.do?method=list&id='+id;
				
			}else{
				Common.confirm("该订单拥有多个商品是否全部退货退款？",["全退","退单个"],function(){
					//全退
					$.post("MMyOrder.do?method=updateState",{id:id,state:15},function(datas){
						if(datas.ret == 1){
							Common.loading();
							window.setTimeout(function () {
								window.location.reload();
							}, 1000);
						}else{
							mui.toast(data.msg);
						}
					});
				},function(){
					//单个
					Common.loading();
					window.location.href='${ctx}/m/MMyOrderReturn.do?method=list&id='+id;
					
				});
			}
		}else{
			mui.toast(data.msg);
		}
	});
	
	
}


//]]></script>
</body>
</html>