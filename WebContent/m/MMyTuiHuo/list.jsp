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
		<a href="${ctx}/m/MMyTuiHuo.do?method=list&mod_id=${af.map.mod_id}" id="ors_">全部</a>
		<a href="${ctx}/m/MMyTuiHuo.do?method=list&mod_id=${af.map.mod_id}&audit_state=0" id="ors_0">待审核</a>
		<a href="${ctx}/m/MMyTuiHuo.do?method=list&mod_id=${af.map.mod_id}&audit_state=2" id="ors_2">审核通过</a>
		<a href="${ctx}/m/MMyTuiHuo.do?method=list&mod_id=${af.map.mod_id}&audit_state=-2" id="ors_-2">审核不通过</a>
	</div>

	<div id="div_orderlist">
		<div id="allOrders" style="position:relative">

			<c:if test="${empty entityList}">
				<div id="no_data" style="background:#fff; padding:15px; margin-bottom:10px;">暂时订单~</div>
			</c:if>

			<c:forEach items="${entityList}" var="cur">
				<div class="m item border-1px mar9">
					<div class="mt bdb-1px">
						${cur.map.trade_index}
						<span class="i-info">
            <c:choose>
			              <c:when test="${cur.audit_state eq -1}">平台审核不通过</c:when>
			              <c:when test="${cur.audit_state eq -2}">商家审核不通过</c:when>
			              <c:when test="${cur.audit_state eq 0}">待审核</c:when>
			              <c:when test="${cur.audit_state eq 2}">商家审核通过</c:when>
			              <c:when test="${cur.audit_state eq 1}">平台审核通过</c:when>
            			</c:choose>
            </span>
					</div>
					<div class="mc">
						<c:url var="view" value="/m/MMyOrderDetail.do?method=view&order_id=${cur.order_id}&from=user&tuikuan_id=${cur.id}" />
						<a onclick="goUrl('${url}');">
							<div class="imc-con bdb-1px">
								<div class="c-type-wrap" id="carousel0">
									<ul class="step-tab -type">
										<li class="tuihuo_li">申请售后时间：
											<fmt:formatDate value="${cur.add_date}" pattern="yyyy-MM-dd HH:mm:ss" />
										</li>
										<c:if test="${cur.audit_state eq 2 }">
											<li class="tuihuo_li">审核时间：
												<fmt:formatDate value="${cur.audit_date}" pattern="yyyy-MM-dd HH:mm:ss" />
											</li>
										</c:if>
										<li class="tuihuo_li">期望处理方式：
											<script type="text/javascript">
											showTuiHuoCause('${cur.expect_return_way}');
											</script>
										</li>

										<li class="tuihuo_li">退货原因：
											<script type="text/javascript">showTuiHuoReasone('${cur.return_type}');</script>
										</li>
<%-- 										<c:if test="${(cur.expect_return_way eq 1)}"> --%>
											<li class="tuihuo_li">退款金额：¥
												<fmt:formatNumber value="${cur.price}" pattern="0.##" />
											</li>
<%-- 										</c:if> --%>

									</ul>
								</div>
							</div>
						</a>
					</div>
					<div class="mb cf">
						<c:url var="url" value="" />
						<c:url var="view" value="/m/MMyOrderDetail.do?method=view&order_id=${cur.order_id}&from=user&tuikuan_id=${cur.id}" />
						<div class="imb-btn-box">
							<a class="imb-btn" href="javascript:goUrl('${view}');">查看</a>
						</div>
						<c:if test="${cur.audit_state eq -2 or cur.audit_state eq -1}">
							<div class="imb-btn-box">
								<c:url var="url" value="MMyTuiHuo.do?method=fankui&id=${cur.id }" />
								<a class="imb-btn" href="javascript:goUrl('${url}');">申诉</a>
							</div>
						</c:if>
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
	<form id="bottomPageForm" name="bottomPageForm" method="post" action="${ctx}/m/MMyTuiHuo.do">
		<input type="hidden" name="method" value="list" />
		<input type="hidden" name="order_type" value="${af.map.order_type}" />
		<input type="hidden" name="order_state" value="${af.map.order_state}" />
		<input type="hidden" name="mod_id" value="${af.map.mod_id}" />
	</form>
	<!-- ——page -->
</div>
<jsp:include page="../_footer.jsp" flush="true" />
<script type="text/javascript">//<![CDATA[
var ctx = "${ctx}";

<c:if test="${not empty af.map.audit_state}">
  $("#ors_" + ${af.map.audit_state}).addClass("cur").siblings().removeClass();
</c:if>

<c:if test="${empty af.map.audit_state}">
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
		url: ctx + "/m/MMyTuiHuo.do?method=getTuiHuoListJson",
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
					html += '<span class="i-info">';
					html +=  data.audit_state_name;
					html += '</span></div>'
					html += '<div class="mc">';
					var url = '${ctx}/m/MMyTuiHuo.do?method=view&id=${cur.id }';
					html += '<a onclick="goUrl(\''+ url +'\');">';
					html += '<div class="imc-con bdb-1px">';
					html += '<div class="c-type-wrap" id="carousel0">';
					html += '<ul class="step-tab -type">';
					html += '<li class="tuihuo_li">申请售后时间：'+ data.add_date +'</li>';
					if(data.audit_state == 2){
						html += '<li class="tuihuo_li">审核时间：'+ data.audit_date +'</li>'
					}
					html += '<li class="tuihuo_li">期望退货方式：'+ data.expect_return_way +'</li>';
					html += '<li class="tuihuo_li">退货原因：'+ data.returnTypeName +'</li>';
					html += '</ul></div>';
					html += '</div>';
					html += '</a>';
					html += '</div>';
					html += '<div class="mb cf"><span class="true-payed">退款金额:</span><span class="imb-num">¥'+data.price+'</span>';
					var view = '${ctx}/m/MMyTuiHuo.do?method=view&id=${cur.id}';
					html += '<div class="imb-btn-box"><a class="imb-btn" href="javascript:goUrl(\''+ view +'\');">查看</a></div>';
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

function orderDel(id){
	Common.confirm("你确定要删除该订单吗？",["确定","取消"],function(){
		$.post("MMyTuiHuo.do?method=updateState",{id:id,state:90},function(datas){
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