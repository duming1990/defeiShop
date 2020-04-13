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
<div class="content"> 
  <div id="div_orderlist">
    <div id="allOrders" style="position:relative">
    
    <c:if test="${empty entityList}">
	    <div id="no_data" style="background:#fff; padding:15px; margin-bottom:10px;">暂时订单~</div>
	</c:if>
    
    <c:forEach items="${entityList}" var="cur">
     <div class="m item border-1px mar9">
        <div class="mt bdb-1px">
            <c:if test="${cur.order_type eq 10 or cur.order_type eq 11}">
              <img src="${ctx}/m/styles/img/order_${cur.order_type}.png" width="25"/>
            </c:if>
            ${cur.trade_index}
             <c:if test="${cur.order_state ge 50}">
                 &nbsp;消费总额：<fmt:formatNumber value="${cur.entp_user_balance/100}" pattern="0.########"/>
             </c:if>
            <span class="i-info">
            <script type="text/javascript">showOrderState(${cur.order_state},${cur.pay_type},${cur.order_type})</script>
            <c:if test="${cur.order_state eq -10}">
              <a class="i-complete" onclick="orderDel('${cur.id}')"></a>
            </c:if>
            </span>
        </div>
       <div class="mc">
        <c:url var="url" value="/m/MMyOrderDetail.do?method=view&order_id=${cur.id}&from=entp" />
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
			        <div class="imco-r">${curSon.map.commInfo.comm_name} <c:if test="${not empty curSon.comm_tczh_name}">&nbsp;[${curSon.comm_tczh_name}]</c:if></div>
			        
			        
			    </div>
			 </div>
			 </c:forEach>
		    </c:if>
		    
		   </div>
		  </a>
		</div>
  		 <div class="mb cf"><span class="true-payed">实付款:</span><span class="imb-num">¥<fmt:formatNumber value="${cur.order_money}" pattern="0.##"/></span>
  		 <c:choose>
         <c:when test="${cur.order_state eq 0}">
           <c:if test="${(cur.order_type eq 10) or (cur.order_type eq 11)}">
             <div class="imb-btn-box"><a class="imb-btn"  onclick="orderModifyPrice('${cur.id}');">修改价格</a></div>
           </c:if>
         </c:when>
  		</c:choose>
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
  <form id="bottomPageForm" name="bottomPageForm" method="post" action="${ctx}/m/MMyOrderModify.do">
  	<input type="hidden" name="method" value="list" />
  	<input type="hidden" name="order_type" value="${af.map.order_type}" />
  	<input type="hidden" name="order_state" value="0" />
  	<input type="hidden" name="mod_id" value="${af.map.mod_id}" />
  </form>
  <!-- ——page --> 
</div>
<jsp:include page="../_footer.jsp" flush="true" />
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
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
		url: ctx + "/m/MMyOrderEntp.do?method=getOrderListJson",
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
					if(data.order_type == 10 || data.order_type ==  11){
			            html +=  '<img src="${ctx}/m/styles/img/order_'+ data.order_type +'.png" width="25"/>';
					}
					html +=  data.trade_index;
					if(data.order_state == 50){
						html +=  '&nbsp;消费总额：'+ (data.entp_user_balance/100) +'';
					}
					
					html += '<span class="i-info">'+data.order_state_name+'';
					
					html += '</span></div>';
					html += '<div class="mc">';
					var url = '${ctx}/m/MMyOrderDetail.do?method=view&order_id='+data.id+'&from=entp';
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
							html += '<img src="' +  dataDetails.map.commInfo.main_pic + '" width="120" height="120" />';
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
							html += '<img src="' +  dataDetails.map.commInfo.main_pic + '" width="120" height="120">';
							html += '</div></div></div>';
							html += '<div class="imco-r-content">';
							html += '<div class="imco-r">' + dataDetails.map.commInfo.comm_name + '</div>';
							html += '</div></div>';
						});
					}
					
					
					html += '</a>';
					html += '</div>';
					html += '<div class="mb cf"><span class="true-payed">实付款:</span><span class="imb-num">¥'+data.order_money+'</span>';
					
					if(data.order_state == 10){
						if(data.order_type == 10){
							html += '<div class="imb-btn-box"><a class="imb-btn"  onclick="orderConfirm('+data.id+');">订单确认</a></div>';
						}
						if(data.order_type == 11){
							html += '<div class="imb-btn-box"><a class="imb-btn" onclick="orderFh('+data.id+');">订单发货</a></div>';
						}
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

function refreshPage(){
	window.location.reload();
}


//]]></script>
</body>
</html>