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
<link href="${ctx}/styles/mui/poppicker/mui.picker.min.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/mui/poppicker/mui.poppicker.css" rel="stylesheet" type="text/css" />
</head>
<body id="body" style="background: #eee;height:100%;">
<jsp:include page="../_header.jsp" flush="true" />
<form method="post" action="/m/MMyShippingAddress" class="ajaxForm0">
<input type="hidden" name="id" id="id" value="${af.map.id}"/>
<input type="hidden" name="rel_province" id="rel_province" value="${af.map.rel_province}"/>
<input type="hidden" name="rel_city" id="rel_city" value="${af.map.rel_city}"/>
<input type="hidden" name="rel_region" id="rel_region" value="${af.map.rel_region}"/>
<input type="hidden" name="rel_region_four" id="rel_region_four" value="${af.map.rel_region_four}"/>
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
 <div class="box submit-btn"> <a class="j_submit" id="btn_submit0">保存</a> </div>
</form>
<jsp:include page="../_footer.jsp" flush="true" /></div>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.m.js"></script>
<script type="text/javascript" src="${ctx}/styles/mui/poppicker/mui.picker.min.js"></script>
<script type="text/javascript" src="${ctx}/styles/mui/poppicker/mui.poppicker.js"></script>
<script type="text/javascript" src="${ctx}/styles/mui/city/city.data-4.js?v=21090306lzx"></script>
<c:url var="urlhome" value="/m/MMyShippingAddress.do?mod_id=1100600500" />
<script type="text/javascript">//<![CDATA[
                                           
$(document).ready(function() {
	
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
			$("#showCity").val(items[0].text  +","+ items[1].text  +","+ items[2].text+","+ items[3].text);
		});
	}, false);
	
	$("#rel_name").attr("dataType", "Require").attr("msg", "真实姓名必须填写！");
	$("#rel_addr").attr("dataType", "Require").attr("msg", "详细地址必须填写！");
	$("#rel_phone").attr("datatype","Mobile").attr("Require","true").attr("msg","请输入正确的手机号！");
	$("#rel_region").attr("dataType", "Require").attr("msg", "请选择所在地区！");
	$("#rel_region_four").attr("dataType", "Require").attr("msg", "请选择所在地区！");

	var f0 = $(".ajaxForm0").get(0);
	$("#btn_submit0").click(function(){
		if(Validator.Validate(f0, 1)){
			Common.loading();
				$.ajax({
					type: "POST",
					url: "?method=save",
					data: $(f0).serialize(),
					dataType: "json",
					error: function(request, settings) {},
					success: function(data) {
						Common.hide();
						if (data.code == "1") {
							mui.toast(data.msg);
							window.setTimeout(function () {
								goUrl('${urlhome}');
							},2000);
						} else {
							mui.toast(data.msg);
						}
					}
				});	
			return true;
		}
		return false;
	});
});
//]]></script>
</body>
</html>
