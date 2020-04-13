<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body style="height:1000px;">
<div class="mainbox mine">
  <jsp:include page="../_nav.jsp" flush="true"/> 
  <html-el:form action="/customer/ServiceCenterInfo.do" enctype="multipart/form-data">
    <html-el:hidden property="queryString" />
    <html-el:hidden property="method" value="save" />
    <html-el:hidden property="id" styleId="id"/>
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="par_id" />
    <html-el:hidden property="p_index" styleId="p_index" />
    <html-el:hidden property="p_index_pro" styleId="p_index_pro" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
      <tr>
        <th colspan="4">基本信息</th>
      </tr>
       <tr>
         <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>申请区域：</td>
            <td colspan="3" id="city_div">
               <html-el:select property="province" styleId="province" styleClass="pi_prov" style="width:120px;" disabled="disabled">
	            <html-el:option value="">请选择...</html-el:option>
	          </html-el:select>
	          &nbsp;
	          <html-el:select property="city" styleId="city" styleClass="pi_city" style="width:120px;">
	            <html-el:option value="">请选择...</html-el:option>
	          </html-el:select>
	          &nbsp;
	          <html-el:select property="country" styleId="country" styleClass="pi_dist" style="width:120px;">
	            <html-el:option value="">请选择...</html-el:option>
	          </html-el:select>
	           &nbsp;
	          <html-el:select property="town" styleId="town" styleClass="pi_town" style="width:120px;">
	            <html-el:option value="">请选择...</html-el:option>
	          </html-el:select>
	           &nbsp;
	          <html-el:select property="village" styleId="village" styleClass="pi_village" style="width:120px;">
	            <html-el:option value="">请选择...</html-el:option>
	          </html-el:select>
            </td>
       </tr>
      
       <tr>
        <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>村站名称：</td>
         <td><html-el:text property="village_name" maxlength="50" styleId="village_name" /></td>
        
        <td width="14%" nowrap="nowrap" class="title_item">地理位置：</td>
        <td><html-el:text property="position_latlng" readonly="true" maxlength="128" style="width:250px;" styleId="position_latlng" />
          &nbsp;
          <input type="button" value="维护坐标" onclick="getLatlng('position_latlng')" class="bgButton" />
        </td>
       </tr>
       <tr>
        <td width="14%" nowrap="nowrap" class="title_item">站主名称：</td>
        <td><html-el:text property="owner_name" maxlength="50" styleId="owner_name" /></td>
         <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>电话：</td>
         <td><html-el:text property="village_mobile" maxlength="50" styleId="village_mobile" style="width:100px"/>&nbsp;
         </td>
       </tr>
       <tr>
         <td width="14%" nowrap="nowrap" class="title_item">服务站上线时间：</td>
         <td>
          <fmt:formatDate value="${af.map.service_online_date}" pattern="yyyy-MM-dd" var="service_online_date" />
          <html-el:text  property="service_online_date" styleId="service_online_date" size="10" maxlength="20" styleClass="webinput" readonly="true" onclick="WdatePicker()" style="cursor:pointer;" value="${_servicecenter_build_date}" />
         </td>
         <td width="14%" nowrap="nowrap" class="title_item">服务站运营时间：</td>
         <td>
          <fmt:formatDate value="${af.map.service_operation_date}" pattern="yyyy-MM-dd" var="service_operation_date" />
          <html-el:text property="service_operation_date" styleId="service_operation_date" size="10" maxlength="20" styleClass="webinput" readonly="true" onclick="WdatePicker()" style="cursor:pointer;" value="${service_operation_date}" />
          	至<fmt:formatDate value="${af.map.service_operation_date_end}" pattern="yyyy-MM-dd" var="service_operation_date_end" />
          <html-el:text property="service_operation_date_end" styleId="service_operation_date_end" size="10" maxlength="20" styleClass="webinput" readonly="true" onclick="WdatePicker()" style="cursor:pointer;" value="${service_operation_date_end}" />
         </td>
       </tr>
        <tr>
         <td width="14%" nowrap="nowrap" class="title_item">店铺名称：</td>
         <td><html-el:text property="shop_name" maxlength="120" styleId="shop_name"/></td>
         <td width="14%" nowrap="nowrap" class="title_item">统一信用代码：</td>
         <td><html-el:text property="shop_faith_code" maxlength="50" styleId="shop_faith_code" style="width:200px;" />&nbsp;
         </td>
       </tr>
       <tr>
         <td width="14%" nowrap="nowrap" class="title_item">店铺营业执照号：</td>
         <td><html-el:text property="shop_licence" styleId="shop_licence" style="width:200px"/></td>
         <td width="14%" nowrap="nowrap" class="title_item">营业执照图片：</td>
       	 <td>
       	 <div style="float:left;">
              <c:set var="img" value="${ctx}/commons/swfupload/style/images/no_image.jpg" />
              <c:set var="img_max" value="${ctx}/commons/swfupload/style/images/no_image.jpg" />
              <c:if test="${not empty af.map.shop_licence_img}">
                <c:set var="img" value="${ctx}/${af.map.shop_licence_img}@s400x400" />
                <c:set var="img_max" value="${ctx}/${af.map.shop_licence_img}" />
              </c:if>
              <a href="${img_max}" id="shop_licence_img_a" target="_blank"><img src="${img}" height="50"  id="shop_licence_img_img" /></a>
              <html-el:hidden property="shop_licence_img" styleId="shop_licence_img" value="${af.map.shop_licence_img}"/>
              <div class="files-warp" id="shop_licence_img_warp"> <span>营业执照:</span><br />
                <div class="btn-files"> <span>添加附件</span>
                  <input id="shop_licence_img_file" type="file" name="shop_licence_img_file"/>
                </div>
                <div class="progress"> <span class="bar"></span><span class="percent">0%</span > </div>
              </div>
          </div>
          </td>
       </tr>
        <tr>
         <td width="14%" nowrap="nowrap" class="title_item">食品经营许可证号：</td>
         <td><html-el:text property="food_license" styleId="food_license" style="width:200px"/></td>
         <td width="14%" nowrap="nowrap" class="title_item">食品经营许可证图片：</td>
       	 <td>
       	 <div style="float:left;">
              <c:set var="img" value="${ctx}/commons/swfupload/style/images/no_image.jpg" />
              <c:set var="img_max" value="${ctx}/commons/swfupload/style/images/no_image.jpg" />
              <c:if test="${not empty af.map.food_licence_img}">
                <c:set var="img" value="${ctx}/${af.map.food_licence_img}@s400x400" />
                <c:set var="img_max" value="${ctx}/${af.map.food_licence_img}" />
              </c:if>
              <a href="${img_max}" id="food_licence_img_a" target="_blank"><img src="${img}" height="50"  id="food_licence_img_img" /></a>
              <html-el:hidden property="food_licence_img" styleId="food_licence_img" value="${af.map.food_licence_img}"/>
              <div class="files-warp" id="food_licence_img_warp"> <span>食品经营许可证:</span><br />
                <div class="btn-files"> <span>添加附件</span>
                  <input id="food_licence_img_file" type="file" name="food_licence_img_file"/>
                </div>
                <div class="progress"> <span class="bar"></span><span class="percent">0%</span > </div>
              </div>
          </div>
          </td>
       </tr>
      
      <tr>
        <th colspan="4">个人信息</th>
      </tr>
      <tr>
       <td class="title_item">联系QQ：</td>
        <td><html-el:text property="appid_qq" maxlength="15" styleId="appid_qq" style="width:200px" /></td>
         <td class="title_item">性别：</td>
        <td><html-el:select property="sex" styleId="sex">
            <html-el:option value="0">男</html-el:option>
            <html-el:option value="1">女</html-el:option>
          </html-el:select></td>
      </tr>
       <tr>
       <td class="title_item">出生日期：</td>
        <td><fmt:formatDate value="${af.map.birthday}" pattern="yyyy-MM-dd" var="_add_birthday" />
          <html-el:text property="birthday" size="10" maxlength="10" readonly="true" onclick="WdatePicker();" value="${_add_birthday}" style="cursor:pointer;text-align:center;" title="点击选择日期" /></td>
      	<td class="title_item">电子邮箱：</td>
        <td><html-el:text property="email" styleClass="webinput" styleId="email" maxlength="50" />
          &nbsp;</td>
      </tr>
        <tr>
         <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>身份证号码：</td>
         <td colspan="3"><html-el:text property="id_card" maxlength="120" styleId="id_card" style="width:200px"/></td>
         </td>
       </tr>
        <tr>
          <td class="title_item" nowrap="nowrap"><span style="color: #F00;">*</span>身份证正面/反面：</td>
          <td colspan="3">
             <div style="float:left;width:50%;">
              <c:set var="img" value="${ctx}/commons/swfupload/style/images/no_image.jpg" />
              <c:set var="img_max" value="${ctx}/commons/swfupload/style/images/no_image.jpg" />
              <c:if test="${not empty af.map.img_id_card_zm}">
                <c:set var="img" value="${ctx}/${af.map.img_id_card_zm}@s400x400" />
                <c:set var="img_max" value="${ctx}/${af.map.img_id_card_zm}" />
              </c:if>
              <a href="${img_max}" id="img_id_card_zm_a" target="_blank"><img src="${img}" height="50"  id="img_id_card_zm_img" /></a>
              <html-el:hidden property="img_id_card_zm" styleId="img_id_card_zm" value="${af.map.img_id_card_zm}"/>
              <div class="files-warp" id="img_id_card_zm_warp"> <span>身份证正面扫描件:</span><br />
                <div class="btn-files"> <span>添加附件</span>
                  <input id="img_id_card_zm_file" type="file" name="img_id_card_zm_file"/>
                </div>
                <div class="progress"> <span class="bar"></span><span class="percent">0%</span > </div>
              </div>
            </div>
            <div style="float:left;width:50%;">
              <c:set var="img" value="${ctx}/commons/swfupload/style/images/no_image.jpg" />
              <c:set var="img_max" value="${ctx}/commons/swfupload/style/images/no_image.jpg" />
              <c:if test="${not empty af.map.img_id_card_fm}">
                <c:set var="img" value="${ctx}/${af.map.img_id_card_fm}@s400x400" />
                <c:set var="img_max" value="${ctx}/${af.map.img_id_card_fm}" />
              </c:if>
              <a href="${img_max}" id="img_id_card_fm_a" target="_blank"><img src="${img}" height="50" id="img_id_card_fm_img" /></a>
              <html-el:hidden property="img_id_card_fm" styleId="img_id_card_fm" value="${af.map.img_id_card_fm}"/>
              <div class="files-warp" id="img_id_card_fm_warp"> <span>身份证反面扫描件:</span><br />
                <div class="btn-files"> <span>添加附件</span>
                  <input id="img_id_card_fm_file" type="file" name="img_id_card_fm_file"/>
                </div>
                <div class="progress"> <span class="bar"></span><span class="percent">0%</span > </div>
              </div>
            </div>
            <span style="color: #F00;float:left">&nbsp;用户身份证正面图片，大小500K以内，文字须清晰可见。</span></td>
        </tr>
      <tr>
        <td colspan="4" style="text-align:center">
          <html-el:button property="" value="保存" styleClass="bgButton" styleId="btn_submit" />
          &nbsp;
          <html-el:button property="" value="重 填" styleClass="bgButton" styleId="btn_reset" onclick="this.form.reset();" />
          &nbsp;
          <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
      </tr>
    </table>
  </html-el:form>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.form.min.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.cs.js"></script>
<script type="text/javascript">//<![CDATA[

var f = document.forms[0];

$(document).ready(function(){
	$("#province").attr({"subElement": "city", "defaultText": "请选择...", "defaultValue": "", "selectedValue": "${af.map.province}","datatype": "Require", "msg": "请选择省份"});
	$("#city").attr({"subElement": "country", "defaultText": "请选择...", "defaultValue": "", "selectedValue": "${af.map.city}","datatype": "Require", "msg": "请选择市"});
	$("#country").attr({"subElement": "town", "defaultText": "请选择...", "defaultValue": "", "selectedValue": "${af.map.country}","datatype": "Require", "msg": "请选择县"});
	$("#town").attr({"subElement": "village", "defaultText": "请选择...", "defaultValue": "", "selectedValue": "${af.map.town}","datatype": "Require", "msg": "请选择乡/镇"});
	$("#village" ).attr({"defaultText": "请选择...", "defaultValue": "", "selectedValue": "${af.map.village}","datatype": "Require", "msg": "请选择村"});
	$("#province").cs("${ctx}/BaseCsAjax.do?method=getBaseProvinceList", "p_index", "0", false);
	
	$("#province").change(function(){
		$('#city').val("");
		$('#country').val("");
		$('#town').val("");
		$('#village').val("");
	});
	$("#city").change(function(){
		$('#country').val("");
		$('#town').val("");
		$('#village').val("");
	});
	$("#country").change(function(){
		$('#town').val("");
		$('#village').val("");
	});
	$("#town").change(function(){
		$('#village').val("");
	});
	
	$("#province").click(function(){
        alert("只可选择该县的地区，不能更改省市县！");
        return false;
    }); 
	$("#city").click(function(){
        alert("只可选择该县的地区，不能更改省市县！");
        return false;
    });   
	$("#country").click(function(){
        alert("只可选择该县的地区，不能更改省市县！");
        return false;
    });   
	
	$("#village").change(function(){
		var village=$("#village").val();
		$.ajax({
			type: "POST" , 
			url: "${ctx}/CsAjax.do" , 
			data:"method=validateVillageIsExist&p_index=" + village,
			dataType: "json", 
	        async: true, 
	        error: function (request, settings) {alert(" 数据加载请求失败！ ");	$("#btn_submit").attr("disabled", "true");}, 
	        success: function (data) {
				if (data.ret == 1) {
					alert(data.msg, '提示');
					$("#btn_submit").attr("disabled", "true");
					return false;
				} 
	        }
		});
	});
	$("#village_name").attr("datatype", "Require").attr("msg", "请填写村站名称");
	$("#village_mobile").attr("dataType","Mobile").attr("msg", "电话号码为空或格式不正确");
	$("#id_card").attr("datatype","IdCard").attr("msg","请填写正确格式的身份证号码！");
	$("#img_id_card_zm").attr("dataType", "Filter" ).attr("msg", "身份证正面扫描件必须上传且格式必须是(bmp,gif,jpeg,jpg,png,png)").attr("accept", "bmp, gif, jpeg, jpg, png");
	$("#img_id_card_fm").attr("dataType", "Filter" ).attr("msg", "身份证反面扫描件必须上传且格式必须是(bmp,gif,jpeg,jpg,png,png)").attr("accept", "bmp, gif, jpeg, jpg, png");
	
	var btn_name = "上传身份证正面";
	if ("" != "${img_id_card_zm}") {
		btn_name = "重新上传身份证正面";
	}
	upload("img_id_card_zm", "image", btn_name, "${ctx}");
	
	var btn_name = "上传身份证背面";
	if ("" != "${img_id_card_fm}") {
		btn_name = "重新上传身份证背面";
	}
	upload("img_id_card_fm", "image", btn_name, "${ctx}");
	
	var btn_name = "上传营业执照";
	if ("" != "${af.map.shop_licence_img}") {
		btn_name = "重新上传营业执照";
	}
	upload("shop_licence_img", "image", btn_name, "${ctx}");
	
	var btn_name = "上传食品经营许可证图片";
	if ("" != "${af.map.food_licence_img}") {
		btn_name = "重新上传食品经营许可证图片";
	}
	upload("food_licence_img", "image", btn_name, "${ctx}");
});     

// 提交
$("#btn_submit").click(function(){
	if(Validator.Validate(f, 3)){
		
		 if($("#service_operation_date").val()>=$("#service_operation_date_end").val()){
			alert("村站站运营开始时间必须大于结束时间");
			return false;
		 }
		
		 $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
		 $("#btn_reset").attr("disabled", "true");
		 $("#btn_back").attr("disabled", "true");
		 f.submit();
	}
});

function validMobile(mobile){
	if ("" != mobile && $("#mobile").attr("readonly") != "readonly") {
		var reg = /^((\(\d{2,3}\))|(\d{3}\-))?((1[3-9]\d{9}))$/;
		if (mobile.match(reg)) {
			$.ajax({
				type: "POST" , 
				url: "${ctx}/CsAjax.do" , 
				data:"method=validateMobile&mobile=" + mobile + "&t=" + new Date(),
				dataType: "json", 
		        async: true, 
		        error: function (request, settings) {alert(" 数据加载请求失败！ ");	$("#btn_submit").attr("disabled", "true");}, 
		        success: function (result) {
					if (result == 0) {
						alert('参数丢失！', '提示');
						$("#btn_submit").attr("disabled", "true");
						return false;
					} else if (result == 1) {
						$("#btn_submit").removeAttr("disabled");
					} else if (result == 2) {
						alert('该手机号码已被注册！', '提示');
						$("#btn_submit").attr("disabled", "true");
						return false;
					}
		        }
			});
		} else {
			alert('手机格式不正确！', '提示');
			$("#btn_submit").attr("disabled", "true");
			return false;
		}
	}
}		
function getLatlng(obj){
	var url = "${ctx}/CsAjax.do?method=getBMap&result_id=" + obj;
	$.dialog({
		title:  "选择坐标",
		width:  900,
		height: 520,
		padding: 0,
		max: false,
        min: false,
        fixed: true,
        lock: true,
		content:"url:"+ encodeURI(url)
	});
}

//]]></script>
</body>
</html>
