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
  <div class="myorder-ors">
  	<c:if test="${userInfo.is_entp eq 1}">
  		<a href="${ctx}/m/MMyComment.do?method=getCommentList&is_entp=true" id="ors_">店铺评论</a>
  	</c:if>
	<a href="${ctx}/m/MMyComment.do?method=getCommentList&add_user_id=${userInfo.id}&tip=1" id="ors_1">发出的评论</a>
	<a href="${ctx}/m/MMyComment.do?method=getCommentInfoSonList&add_user_id=${userInfo.id}&tip=2" id="ors_2">发出的回复</a>
	<a href="${ctx}/m/MMyComment.do?method=getCommentInfoSonList&to_user_id=${userInfo.id}&tip=3" id="ors_3">收到的回复</a>
  </div>
  
  <div id="div_orderlist">
    <div id="allOrders" style="position:relative">
    
    <c:if test="${(empty entityList) and (empty commentInfoSonList)}">
	    <div id="no_data" style="background:#fff; padding:15px; margin-bottom:10px;">暂时无评论~</div>
	</c:if>
    <c:if test="${not empty entityList}">
    <c:forEach items="${entityList}" var="cur">
     <div class="m item border-1px mar9">
        <div class="mt bdb-1px">
            ${cur.map.ods.comm_name}
            <span class="i-info">
            	${cur.comm_uname}
<%--               删除评论<a class="i-complete" onclick="orderDel('${cur.id}')"></a> --%>
            </span>
        </div>
       <div class="mc">
        <c:url var="url" value="/m/MMyComment.do?method=view&id=${cur.id}" />
		  <a onclick="goUrl('${url}');">
		     <div class="imc-con bdb-1px">
		    <div class="imc-one">
		    <c:if test="${not empty cur.map.baseFilesList}">
			  <div class="imco-l" style="width: 100%;">
			    <div class="imco-l-img-box" style="width: 100%;border-radius: 0px;">
			        <div class="imco-l-img" style="width: 100%;-webkit-transform: none;border: 0px solid #e1e1e1;border-radius: 0px;">
				     <c:forEach items="${cur.map.baseFilesList}" var="curSon">  
				     <c:set var="imgSrc" value="${ctx}/styles/imagesPublic/user_header.png" />
			         <c:if test="${not empty curSon.save_path}">
			         <c:set var="imgSrc" value="${ctx}/${curSon.save_path}@s400x400" />
			         <img src="${imgSrc}" width="60" height="60" style="width: 60px;border-radius: 6px;"/>
			         </c:if>
			         </c:forEach>
			        </div>
			    </div>
			    </div>
		    </c:if>
			    <c:if test="${not empty cur.comm_experience}">
			    <div class="imco-r-content" style="margin-top: 0.1rem;height: initial;width: 100%">
			        <div class="imco-r">
			        	<span style="color: brown;">${cur.comm_uname}:</span>${cur.comm_experience}
			        </div>
			    </div>
			    </c:if>
			 </div>
		    
		   </div>
		  </a>
		</div>
  		 <div class="mb cf"><span class="true-payed">评论时间:</span><span class="imb-num"><fmt:formatDate value="${cur.comm_time}" pattern="yyyy-MM-dd" /></span>
  		 	<c:if test="${cur.comm_uid eq userInfo.id }">
  		 		<c:url var="url" value="MMyComment.do?method=edit&id=${cur.id}" />
  				<div class="imb-btn-box"><a class="imb-btn" href="#" onclick="goUrl('${url}')">修改</a></div>
  			</c:if>
  			<c:if test="${cur.comm_uid ne userInfo.id }">
  			<c:url var="url" value="MMyComment.do?method=huifu&id=${cur.id}" />
  			<div class="imb-btn-box"><a class="imb-btn" href="#" onclick="goUrl('${url}')">回复</a></div>
  			</c:if>
  	   </div>
      </div> 
     </c:forEach> 
    </c:if>
    
    <c:if test="${not empty commentInfoSonList}">
    <c:forEach items="${commentInfoSonList}" var="cur">
     <div class="m item border-1px mar9">
        <div class="mt bdb-1px">
            ${cur.map.ods.comm_name}
            <span class="i-info">
            	${cur.map.commentInfo.comm_uname}
<%--               删除评论<a class="i-complete" onclick="orderDel('${cur.id}')"></a> --%>
            </span>
        </div>
       <div class="mc">
        <c:url var="url" value="/m/MMyComment.do?method=view&id=${cur.par_id}" />
		  <a onclick="goUrl('${url}');">
		     <div class="imc-con bdb-1px">
		    <div class="imc-one">
		    <c:if test="${not empty cur.map.baseFilesList}">
			  <div class="imco-l" style="width: 100%;">
			    <div class="imco-l-img-box" style="width: 100%;border-radius: 0px;">
			        <div class="imco-l-img" style="width: 100%;-webkit-transform: none;border: 0px solid #e1e1e1;border-radius: 0px;">
				     <c:forEach items="${cur.map.baseFilesList}" var="curSon">  
				     <c:set var="imgSrc" value="${ctx}/styles/imagesPublic/user_header.png" />
			         <c:if test="${not empty curSon.save_path}">
			         <c:set var="imgSrc" value="${ctx}/${curSon.save_path}@s400x400" />
			         <img src="${imgSrc}" width="60" height="60" style="width: 60px;border-radius: 6px;"/>
			         </c:if>
			         </c:forEach>
			        </div>
			    </div>
			    </div>
			    </c:if>
			    <c:if test="${not empty cur.map.commentInfo.comm_experience}">
			    <div class="imco-r-content" style="margin-top: 0.1rem;height: initial;width: 100%">
			        <div class="imco-r">
			        	<span style="color: brown;">${cur.add_user_name}:</span>${cur.content}
			        </div>
			    </div>
			    </c:if>
			 </div>
		    
		   </div>
		  </a>
		</div>
  		 <div class="mb cf"><span class="true-payed">评论时间:</span><span class="imb-num"><fmt:formatDate value="${cur.add_date}" pattern="yyyy-MM-dd" /></span>
  		 	<c:if test="${cur.add_user_id eq userInfo.id }">
	  		 	<c:url var="url" value="MMyComment.do?method=editHuifu&id=${cur.id}" />
	  			<div class="imb-btn-box"><a class="imb-btn" href="#" onclick="goUrl('${url}')">修改</a></div>
  			</c:if>
  			<c:url var="url" value="MMyComment.do?method=huifu&id=${cur.map.commentInfo.id}" />
  			<div class="imb-btn-box"><a class="imb-btn" href="#" onclick="goUrl('${url}')">回复</a></div>
  	   </div>
      </div> 
     </c:forEach> 
    </c:if>   
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
<%--   	<input type="hidden" name="order_type" value="${af.map.order_type}" /> --%>
<%--   	<input type="hidden" name="order_state" value="${af.map.order_state}" /> --%>
  	<input type="hidden" name="mod_id" value="${af.map.mod_id}" />
  </form>
  <!-- ——page --> 
</div>
<jsp:include page="../_footer.jsp" flush="true" />
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.js"></script>
<script type="text/javascript">//<![CDATA[
var ctx = "${ctx}";

<c:if test="${not empty af.map.tip}">
  $("#ors_" + ${af.map.tip}).addClass("cur").siblings().removeClass();
</c:if>

<c:if test="${empty af.map.tip}">
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
					if(data.order_type == 10 || data.order_type ==  11){
			            html +=  '<img src="${ctx}/m/styles/img/order_'+ data.order_type +'.png" width="25"/>';
					}
					html +=  data.trade_index;
					if(data.order_state == 50){
						html +=  '&nbsp;消费总额：'+ (data.xiadan_user_sum/100) +'';
					}
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
							html += '<img src="' +  dataDetails.map.commInfo.main_pic + '" width="120" height="120">';
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
					html += '<div class="mb cf" id="order_state_'+data.id+'" date-isshixiao="'+data.is_shixiao+'" data-order-type="'+data.order_type+'"><span class="true-payed">实付款:</span><span class="imb-num">¥'+data.order_money+'</span>';
					
					if(data.order_state == 0){
						var payUrl = "${ctx}/m/MMyCartInfo.do?method=selectPayType&trade_index="+ data.trade_index +"&pay_type="+ data.pay_type;
						html += '<div class="imb-btn-box"><a class="imb-btn" href="javascript:goUrl(\''+ payUrl +'\');">去支付</a></div>';
					} else if(data.order_state == 20){
						html += '<div class="imb-btn-box"><a class="imb-btn" href="javascript:updateState(\'MMyOrder.do\', \'updateState\', '+data.id+', 40, this);">确认收货</a></div>';
						if(data.delay_shouhuo != 1) {
							html += '<div class="imb-btn-box"><a class="imb-btn" onclick="delayShouhuo(\'MMyOrder.do\','+data.id+', this);">延迟收货</a></div>';
						}
					} else if(data.order_state == 10){
						if(data.order_type == 10 || data.order_type == 11){
							html += '<div class="imb-btn-box"><a class="imb-btn" href="javascript:updateState(\'MMyOrder.do\', \'updateState\', '+data.id+', -20, this);">去退款</a></div>';
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
function kdcx(order_id){
	$.ajax({
		type: "POST",
		url: "${ctx}/CsAjax.do?method=getDeliveryInfo",
		data:"order_id="+order_id,
		dataType: "json",
		error: function(request, settings) {alert("系统错误，请联系管理员！");},
		success: function(result) {
			if(result.ret == 1){
				mui.toast(result.msg);
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
//]]></script>
</body>
</html>