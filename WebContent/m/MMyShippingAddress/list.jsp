<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>触屏版-${app_name}</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Expires" content="-1">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Pragma" content="no-cache">
<jsp:include page="../_public_in_head.jsp" flush="true" />
<style type="text/css">
p{margin-bottom: 0px;font-size:12px;}
</style>
</head>
<body id="body" style="background: #eee;height:100%;">
<jsp:include page="../_header.jsp" flush="true" />
<div class="content">
  <section class="aui-myOrder-content">
       <c:forEach items="${shippingAddressList}" var="cur">
		<div class="aui-Address-box">
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
					<p><input type="checkbox" onclick="setDefault('${cur.id}');" class="check goods-check goodsCheck" <c:if test="${cur.is_default eq 1}">checked </c:if> style="background-size: 17px 17px;"> <em style="padding-left:24px;">默认地址</em></p>
				</div>
				<div class="aui-Address-box-item-ft" style="margin-right: 5px;">
				    <c:url var="url" value="/m/MMyShippingAddress.do?method=edit&id=${cur.id}" />
					<p onclick="goUrl('${url}');">修改</p>
				</div>
				<div class="aui-Address-box-item-ft">
					<p onclick="delClick('${cur.id}');">删除</p>
				</div>
			</div>
		</div>
		</c:forEach>
	</section>
</div>
<jsp:include page="../_footer.jsp" flush="true" />
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	 var topBtnUrl = "${ctx}/m/MMyShippingAddress.do?method=add&mod_id=${af.map.mod_id}&par_id=${af.map.par_id}";
	 setTopBtnUrl(topBtnUrl);
});
function delClick(id){
	Common.confirm("是否确认删除?",["确定","取消"],function(){
		$.post("?method=delete&id="+id, 
				function(datas) {
				if (datas.ret != 0) {
					mui.toast('恭喜删除成功！',2000);
					setTimeout(function(){
						window.location.reload();
					},1000); 
				} else {
					mui.toast('对不起，删除失败！');
				}
		});
	},function(){
	});
}

function setDefault(id){
	$.post("?method=updateDefault&id="+id, 
			function(datas) {
			if (datas.ret != 0) {
				mui.toast('设置成功');
				setTimeout(function(){
					window.location.reload();
				},1000); 
			} else {
				mui.toast('对不起，操作失败，请稍后再试！');
			}
	});
}
//]]></script>
</body>
</html>
