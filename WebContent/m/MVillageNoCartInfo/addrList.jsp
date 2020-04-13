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
<link href="${ctx}/m/styles/css/step1.css" rel="stylesheet" type="text/css" />
<style type="text/css">
p{margin-bottom: 0px;font-size:12px;}
</style>
</head>
<body id="body" style="background: #eee;height:100%;">
<div id="wrap">
<jsp:include page="../_header.jsp" flush="true" />
<div>
	<section class="aui-myOrder-content" id="chooseAddr" style="margin-bottom: 65px;background: #eee;">
       <c:forEach items="${shippingAddressList}" var="cur">
		<div class="aui-Address-box" data-id="${cur.id}">
			<div class="aui-Address-box-item">
				<div class="aui-Address-box-item-bd">
					<p>${cur.rel_name}</p>
				</div>
				<div class="aui-Address-box-item-ft">
					<p>${cur.rel_phone}</p>
				</div>
			</div>
			<div class="aui-Address-box-item">
				<div class="aui-Address-box-item-bd">
					<p>${cur.map.full_name}${cur.rel_addr}</p>
				</div>
			</div>
			<div class="aui-Address-box-item">
				<div class="aui-Address-box-item-bd">
				      <c:set var="shippingSelect" value="" />
			          <c:if test="${cur.id eq shipping_address_id}">
			  			<c:set var="shippingSelect" value="checked" />
					  </c:if>
					<p><input type="checkbox" class="check goods-check goodsCheck" ${shippingSelect} style="background-size: 17px 17px;"> <em style="padding-left:24px;">默认地址</em></p>
				</div>
			</div>
		</div>
		</c:forEach>
		<div class="common-item" style="padding:.3rem 0;width:80%;margin:0 auto;">
			<c:url var="url" value="/m/MMyCartInfo.do?method=addAddr&village_id=${af.map.village_id}&comm_id=${af.map.comm_id}&comm_tczh_id=${af.map.comm_tczh_id}"/>
			<input type="button" class="j_submit" value="增加收货地址"  name="sub" onclick="goUrl('${url}');">
		</div>
	</section>
 </div>
 
<jsp:include page="../_footer.jsp" flush="true" />	
</div>
<script type="text/javascript" src="${ctx}/scripts/cart/cart.sourceMobile.js"></script>
<script type="text/javascript">//<![CDATA[
$("#chooseAddr .aui-Address-box").each(function(index){
	$(this).click(function(){
		var cart_ids = "${cart_ids}";
		var shipping_address_id = $(this).attr("data-id");
		var delivery_p_index = "${p_index}";
		var village_id = ${af.map.village_id};
		var comm_tczh_id = ${af.map.comm_tczh_id};
		var comm_id =  ${af.map.comm_id};
		Common.loading();
		window.setTimeout(function () {
			location.href="${ctx}/m/MVillageNoCartInfo.do?shipping_address_id=" + shipping_address_id+"&village_id="+village_id+"&comm_id="+comm_id+"&comm_tczh_id="+comm_tczh_id;
		}, 500);
	});
});
//]]></script>
</body>
</html>