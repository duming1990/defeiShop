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
<link href="${ctx}/styles/mui/poppicker/mui.picker.min.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/mui/poppicker/mui.poppicker.css" rel="stylesheet" type="text/css" />
</head>
<body style="background: #eee;height:100%;">
<div id="wrap">
<jsp:include page="../_header.jsp" flush="true" />
<div class="content">
   <c:url var="url" value="MMyCartInfo.do" />
   <html-el:form action="${url}" styleClass="formAddr" method="post">
   <html-el:hidden property="method" value="saveAddr"/>
   <input type="hidden" name="id" id="id" value="${af.map.id}"/>
   <input type="hidden" name="rel_province" id="rel_province" value="${af.map.rel_province}"/>
   <input type="hidden" name="rel_city" id="rel_city" value="${af.map.rel_city}"/>
   <input type="hidden" name="rel_region" id="rel_region" value="${af.map.rel_region}"/>
   <input type="hidden" name="rel_region_four" id="rel_region_four" value="${af.map.rel_region_four}"/>
   <input type="hidden" name="comm_id" id="comm_id" value="${af.map.comm_id}" />
   <input type="hidden" name="village_id" id="village_id" value="${af.map.village_id}"/>
   <input type="hidden" name="comm_tczh_id" id="comm_tczh_id" value="${af.map.comm_tczh_id}" />
	<div class="aui-Address-box">
		<p>
			<input class="aui-Address-box-input" id="rel_name" name="rel_name" maxlength="38" value="${af.map.rel_name}" type="text" placeholder="收货人姓名"/>
		</p>
		<p>
			<input class="aui-Address-box-input" type="text" value="${af.map.rel_phone}" name="rel_phone" id="rel_phone" placeholder="手机号码" />
		</p>
		<p>
			<input class="aui-Address-box-input"  type="text" readonly="" id="showCity" value="${af.map.full_name}" placeholder="所在地区" />
		</p>
		<p>
			<textarea class="aui-Address-box-text" id="rel_addr" name="rel_addr" placeholder="街道， 小区门牌等详细地址" rows="3">${af.map.rel_addr}</textarea>
		</p>
	 </div>
     <div class="submit-btn">
		<input type="button" class="j_submit" value="确定" name="sub" onclick="saveAddr(this)"/>
     </div>
   </html-el:form>
 <jsp:include page="../_footer.jsp" flush="true" />	
</div>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.m.js"></script> 
<script type="text/javascript" src="${ctx}/styles/mui/poppicker/mui.picker.min.js"></script>
<script type="text/javascript" src="${ctx}/styles/mui/poppicker/mui.poppicker.js"></script>
<script type="text/javascript" src="${ctx}/styles/mui/city/city.data-4.js?v=21090306lzx"></script>
<script type="text/javascript">//<![CDATA[
var f = document.forms[0];      
$(function(){
	
	var cityPicker4 = new mui.PopPicker({
		layer: 4
	});
	cityPicker4.setData(cityData4);
	document.getElementById('showCity').addEventListener('tap', function(event) {
		cityPicker4.show(function(items) {
			$("#rel_province").val(items[0].value);
			$("#rel_city").val(items[1].value);
			$("#rel_region").val(items[2].value);
			$("#rel_region_four").val(items[3].value);
			$("#showCity").val(items[0].text  +","+ items[1].text  +","+ items[2].text +","+ items[3].text);
		});
	}, false);
	
	
	 $("#rel_name").attr("dataType", "Require").attr("msg", "请填写收货人姓名");
	 $("#rel_addr").attr("dataType", "Require").attr("msg", "请填写地址");
	 $("#rel_phone").attr("dataType", "Mobile").attr("msg", "手机号码格式不正确！");
	 $("#rel_region").attr("dataType", "Require").attr("msg", "请选择所在地区！");
	 $("#rel_region_four").attr("dataType", "Require").attr("msg", "请选择所在地区！");
	 
	 $("#setDefault").click(function(){
		 if($(this).hasClass("current")){
			 $(this).removeClass("current");
			 $("#is_default").val(0);
		 }else{
			 $(this).addClass("current");
			 $("#is_default").val(1);
		 }
	 });
});

function saveAddr(obj){
	if(Validator.Validate(f, 1)){
		/* var str = $('.formAddr').serialize()
		var s = decodeURIComponent($('.formAddr').serialize(),true);
		alert(s); */
		var cart_ids = "${af.map.cart_ids}";
		var isYs = "${af.map.isYs}";
		$(obj).removeAttr("onclick");
		$.post("${ctx}/CsAjax.do?method=saveShippingAddress&" + $('.formAddr').serialize(),{},function(data){
			if (data.result) {
				Common.loading();
				window.setTimeout(function () {
					var village_id = $("#village_id").val();
					var comm_id = "${comm_id}";
					var comm_tczh_id = "${comm_tczh_id}";
					if(null != village_id && "" != village_id){
						location.href="${ctx}/m/MVillageNoCartInfo.do?shipping_address_id=" + data.result+"&village_id="+village_id+"&comm_id="+comm_id+"&comm_tczh_id="+comm_tczh_id + "&isYs=" + isYs;
					}else{
						location.href="${ctx}/m/MMyNoCartInfo.do?method=step1&shipping_address_id=" + data.result+"&comm_id="+comm_id+"&comm_tczh_id="+comm_tczh_id;
					}
				}, 1000);
			}
		}); 
	}
}

//]]></script>
</body>
</html>