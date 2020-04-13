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
<link href="${ctx}/m/styles/css/my/my-v1.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/index/css/btns.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/m/js/date/app1/css/date.css" rel="stylesheet" type="text/css" />
</head>
<body>
<jsp:include page="../_header.jsp" flush="true" />
<div class="content">
  <!--article-->
  <form action="/m/MEntpEnter" enctype="multipart/form-data" method="post" class="ajaxForm0">
    <input type="hidden" name="id" id="id" />
    <input type="hidden" name="method" value="step3" />
    <input type="hidden" name="mod_id" id="mod_id" value="${af.map.mod_id}"/>
    <input type="hidden" name="queryString" id="queryString" />
    <input type="hidden" name="entp_id" id="entp_id" value="${af.map.id}" />
    <div class="set-site" id="city_div">
      <ul>
      	<li>
      		<span class="grey-name">申请人姓名：</span>
          <input style="width: 70%" name="entp_sname" id="entp_sname" type="text" autocomplete="off" maxlength="38" value="${af.map.entp_sname}" class="buy_input">
        </li>
        <li class="select"><span class="grey-name">有无营业执照：</span>
           <select name="is_has_yinye_no" id="is_has_yinye_no" style="width:70%;">
                <option value="1">有</option>
                <option value="0">没有</option>
           </select>
        </li>
        
       <div class="item" id="hasYinYeNo" style="border-bottom:1px solid #C8C8C8;">
        <li>
      		<span class="grey-name"  style="width: 40%">商家法人营业执照：</span>
          <input style="width: 60%" name="entp_licence" id="entp_licence" type="text" autocomplete="off" maxlength="38" value="${af.map.entp_licence}" class="buy_input">
        </li>
        <li style="text-align: center;">
        	<span style="width: 80%;padding-left: 60px;padding-top: 20px;">
		        <div style="float:left;width:50%;">
		        <c:set var="img" value="${ctx}/commons/swfupload/style/images/no_image.jpg" />
		        <c:if test="${not empty af.map.entp_licence_img}">
                   <c:set var="img" value=" ${ctx}/${af.map.entp_licence_img}@s400x400" />
                </c:if>
		        <img src="${img}" width="100" height="100" id="entp_licence_img_img" />
		        <input type="hidden" name="entp_licence_img" id="entp_licence_img" value="${af.map.entp_licence_img}" />
		        <div class="files-warp" id="entp_licence_img_warp">
		          <div class="btn-files"> <span>添加附件</span>
		            <input id="entp_licence_img_file" type="file" name="entp_licence_img_file"  accept="image/*" />
		          </div>
		          <div class="progress progress_m" style=""> <span class="bar bar_m"></span><span class="percent">0%</span > </div>
		        </div>
		        </div>
		  	</span>
        </li>
        </div>
        
       <div class="item" id="notHasYinYeNo" style="display:none;border-bottom: 1px solid #C8C8C8;">
        <li>
      		<span class="grey-name" style="width: 30%">填写身份证号：</span>
            <input style="width: 70%" name="id_card_no" id="id_card_no" type="text" autocomplete="off" maxlength="38" value="${af.map.id_card_no}" class="buy_input">
        </li>
        <li style="text-align: center;">
        	<div style="width: 80%;padding-top: 20px;">
		        <div style="float:left;width:50%;">
		        <c:set var="img" value="${ctx}/commons/swfupload/style/images/no_image.jpg" />
		        <c:if test="${not empty af.map.img_id_card_zm}">
                   <c:set var="img" value=" ${ctx}/${af.map.img_id_card_zm}@s400x400" />
                </c:if>
		        <img src="${img}" width="100" height="100" id="img_id_card_zm_img" />
		        <input type="hidden" name="img_id_card_zm" id="img_id_card_zm" value="${af.map.img_id_card_zm}" />
		        <div class="files-warp" id="img_id_card_zm_warp">
		          <div class="btn-files"> <span>添加附件</span>
		            <input id="img_id_card_zm_file" type="file" name="img_id_card_zm_file"  accept="image/*" />
		          </div>
		          <div class="progress progress_m" style=""> <span class="bar bar_m"></span><span class="percent">0%</span > </div>
		        </div>
		        </div>
		        <div style="float:left;width:50%;">
		        <c:set var="img" value="${ctx}/commons/swfupload/style/images/no_image.jpg" />
		        <c:if test="${not empty af.map.img_id_card_fm}">
                   <c:set var="img" value=" ${ctx}/${af.map.img_id_card_fm}@s400x400" />
                </c:if>
		        <img src="${img}" width="100" height="100" id="img_id_card_fm_img" />
		        <input type="hidden" name="img_id_card_fm" id="img_id_card_fm" value="${af.map.img_id_card_fm}"  accept="image/*"  />
		        <div class="files-warp" id="img_id_card_fm_warp">
		          <div class="btn-files"> <span>添加附件</span>
		            <input id="img_id_card_fm_file" type="file" name="img_id_card_fm_file"/>
		          </div>
		          <div class="progress progress_m" style=""> <span class="bar bar_m"></span><span class="percent">0%</span > </div>
		        </div>
		        </div>
		     </div>
         </li>
        </div>
        
        <li><span class="grey-name" style="width: 30%">店铺所在地区：</span></li>
        <li class="select">
          <select name="province" id="province" >
            <option value="">------选择省份------</option>
          </select>
        </li>
        <li class="select">
          <select name="city" id="city">
            <option value="">--城市--</option>
          </select>
        </li>
        <li class="select">
          <select name="country" id="country">
            <option value="">--县区--</option>
          </select>
        </li>
        <li>
      		<span class="grey-name">地理坐标：</span>
          <input style="width: 50%" name="entp_latlng" id="entp_latlng" type="text" readonly="readonly" autocomplete="off" maxlength="38" value="${af.map.entp_latlng}" class="buy_input">
        	<div class="files-warp">
		          <div class="btn-files"> <span>维护坐标</span>
		            <input onclick="getLatlng('entp_latlng')" type="button"/>
		          </div>
		        </div>
        </li>
        <li>
      		<span class="grey-name">店铺具体地址：</span>
          <input style="width: 70%" name="entp_addr" id="entp_addr" type="text"  autocomplete="off" maxlength="38" value="${af.map.entp_addr}" class="buy_input">
        </li>
      </ul>
    </div>
    <input type="hidden" name="p_index" value="${af.map.p_index}" id="p_index" />
    <input type="hidden" name="p_index_pro" value="${af.map.p_index_pro}" id="p_index_pro" />
    <div class="box submit-btn"> <a class="com-btn" id="btn_submit0">下一步</a> </div>
  </form>
</div>
<jsp:include page="../_footer.jsp" flush="true" />
<script type="text/javascript" src="${ctx}/commons/scripts/validator.m.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.form.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/iscroll/iscroll-5.1.2.min.js"></script>
<script type="text/javascript" src="${ctx}/m/js/date/app1/js/date.js?v20160406"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/area.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/citySelect.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<c:url var="urlhome" value="/m/MMyHome.do" />
<c:url var="toStep3" value="/m/MEntpEnter.do?method=toStep3" />
<script type="text/javascript">//<![CDATA[

$(document).ready(function() {
	
	var btn_name = "上传营业执照扫描件";
	if ("" != "${af.map.entp_licence_img}") {
		btn_name = "重新上传营业执照扫描件";
	}
	upload("entp_licence_img", "image", btn_name, "${ctx}");
	
	var btn_name = "上传身份证正面";
	if ("" != "${af.map.img_id_card_zm}") {
		btn_name = "重新上传身份证正面";
	}
	upload("img_id_card_zm", "image", btn_name, "${ctx}");
	
	var btn_name = "上传身份证背面";
	if ("" != "${af.map.img_id_card_fm}") {
		btn_name = "重新上传身份证背面";
	}
	upload("img_id_card_fm", "image", btn_name, "${ctx}");
	
	$("#country").attr("datatype","Require").attr("msg","区域不可为空！");
	$("#entp_sname").attr("datatype","Require").attr("msg","请正确填写申请人姓名");
	$("#entp_tel").attr("datatype","Mobile").attr("msg","请正确填写联系人手机");
	$("#qq").attr("datatype","QQ").attr("msg","请正确填写联系人QQ");
	$("#entp_email").attr("datatype","Email").attr("msg","请正确填写联系人邮箱");
	$("#entp_addr").attr("datatype","Require").attr("msg","请正确填店铺具体地址");
	$("#entp_latlng").attr("datatype","Require").attr("msg","请选择正确的地理坐标");
	
	$("#sex-${sex}").attr("checked", true);
	$("#is_has_yinye_no").val("${af.map.is_has_yinye_no}");
	
	$("#is_has_yinye_no").change(function(){
		var thisValue = $(this).val();
		if(thisValue == 1){
			$("#hasYinYeNo").show();
			$("#notHasYinYeNo").hide();
			$("#entp_licence").attr("datatype","Require").attr("msg","营业执照编码必须填写");
			$("#entp_licence_img").attr("dataType", "Filter" ).attr("msg", "营业执照扫描件必须上传且格式必须是(bmp,gif,jpeg,jpg,png,png)").attr("accept", "bmp, gif, jpeg, jpg, png");
			$("#img_id_card_zm").removeAttr("datatype");
			$("#img_id_card_fm").removeAttr("datatype");
			$("#id_card_no").removeAttr("datatype");
		}else{
			$("#notHasYinYeNo").show();
			$("#hasYinYeNo").hide();
			$("#id_card_no").attr("datatype","Require").attr("msg","身份证号必须填写");
			$("#img_id_card_zm").attr("dataType", "Filter" ).attr("msg", "身份证正面扫描件必须上传且格式必须是(bmp,gif,jpeg,jpg,png,png)").attr("accept", "bmp, gif, jpeg, jpg, png");
			$("#img_id_card_fm").attr("dataType", "Filter" ).attr("msg", "身份证反面扫描件必须上传且格式必须是(bmp,gif,jpeg,jpg,png,png)").attr("accept", "bmp, gif, jpeg, jpg, png");
			$("#entp_licence").removeAttr("datatype");
			$("#entp_licence_img").removeAttr("datatype");
		}
	});
	
	$("#is_has_yinye_no").change();
	
	<c:if test="${not empty (af.map.entp_licence_img)}">
	$("#entp_licence_img").removeAttr("datatype");
    </c:if>	
	<c:if test="${not empty (af.map.img_id_card_zm)}">
	$("#img_id_card_zm").removeAttr("datatype");
	</c:if>	
	<c:if test="${not empty (af.map.img_id_card_fm)}">
	$("#img_id_card_fm").removeAttr("datatype");
	</c:if>	
	
// 	省市县选择
	$("#city_div").citySelect({
        data:getAreaDic(),
        province:"${af.map.province}",
        city:"${af.map.city}",
        country:"${af.map.country}",
        
        province_required:true,
        city_required:true,
        country_required:false,
        callback:function(selectValue,selectText){
        	if(null != selectValue && "" != selectValue){
        		var p_indexs = selectValue.split(",");
        		if(null != p_indexs && p_indexs.length > 0){
        			$("#p_index").val(p_indexs[p_indexs.length - 1]);
        		}
        	}
        	
        }
    });
	
	var f0 = $(".ajaxForm0").get(0);
	$("#btn_submit0").click(function(){
		if (Validator.Validate(f0, 1)) {
			Common.loading();
				$.ajax({
					type: "POST",
					url: "?method=step3",
					data: $(f0).serialize(),
					dataType: "json",
					error: function(request, settings) {},
					success: function(data) {
						Common.hide();
						if (data.code == 1) {
							mui.toast(data.msg);
							window.setTimeout(function () {
								goUrl('${toStep3}',0);
							},1000);
						} else {
							mui.toast(data.msg);
						}
					}
				});	
			return true;
		}
		return false;
	});

	$("#id_card_").change(function(){
		var thisVal = $(this).val();
		if(null != thisVal && '' != thisVal){
			$("#id_card").val(thisVal);
		}
	});
});

function getLatlng(obj){
	var url = "${ctx}/CsAjax.do?method=getBMap&result_id=" + obj + "&vieType=m";
	$.dialog({
		title:  "选择坐标",
		width:"100%",
		height: "100%",
		padding: 0,
		max: true,
        min: false,
        fixed: true,
        lock: true,
		content:"url:"+ encodeURI(url)
	});
}

//]]></script>
</body>
</html>
