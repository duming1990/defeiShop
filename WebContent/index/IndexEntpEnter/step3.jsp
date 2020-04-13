<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我要开店 - ${app_name}</title>
<jsp:include page="../../_public_header.jsp" flush="true" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/login_reg.css"  />
</head>
<body class="pg-unitive-signup theme--www">
<jsp:include page="../../_header.jsp" flush="true" />
<div class="merSteps">
  <div class="w1200">
    <c:url var="url" value="/IndexEntpEnter.do?method=step4" />
    <form id="stepForm" action="${url}" method="post" name="stepForm">
      <div class="panel">
        <div class="panel-nav">
          <div class="progress-item passed">
            <div class="number">1</div>
            <div class="progress-desc">入驻须知</div>
            <div class="arrow-background"></div>
            <div class="arrow-foreground"></div>
          </div>
          <div class="progress-item ongoing">
            <div class="number">2</div>
            <div class="progress-desc">公司信息认证</div>
            <div class="arrow-background"></div>
            <div class="arrow-foreground"></div>
          </div>
          <div class="progress-item tobe">
            <div class="number">3</div>
            <div class="progress-desc">店铺信息认证</div>
            <div class="arrow-background"></div>
            <div class="arrow-foreground"></div>
          </div>
          <div class="progress-item tobe">
            <div class="number">4</div>
            <div class="progress-desc">等待审核</div>
          </div>
        </div>
        <div class="panel-content">
          <div class="bg-top"></div>
          <div class="bg-warp">
            <div class="title"> <span>公司信息提交</span> </div>
            <div class="panel-body">
              <div class="list">
                <div class="item">
                  <div class="label"> <em>*</em> <span>申请人姓名：</span> </div>
                  <div class="value">
                    <input class="text" type="text" size="20"  maxlength="20" name="entp_sname" id="entp_sname" value="${af.map.entp_sname}"/>
                  </div>
                </div>
                <div class="item">
                  <div class="label"> <em>*</em> <span>有无营业执照：</span> </div>
                  <div class="value">
                    <select name="is_has_yinye_no" id="is_has_yinye_no">
                      <option value="1">有</option>
                      <option value="0">没有</option>
                    </select>
                  </div>
                </div>
                <div class="item" id="notHasYinYeNo" style="display:none;">
                  <div class="label"> <em>*</em> <span>身份证正面/反面：</span> </div>
                  <div class="value">
                    <div>
                      <input class="text" type="text" size="20" value="${af.map.id_card_no}" placeholder="请填写身份证号" maxlength="18" name="id_card_no" id="id_card_no" />
                    </div>
                    <table border="0" width="100%">
                    <tr>
                    <td><div>
                      <c:set var="img" value="${ctx}/commons/swfupload/style/images/no_image.jpg" />
                      <c:set var="img_max" value="${ctx}/commons/swfupload/style/images/no_image.jpg" />
                      <c:if test="${not empty af.map.img_id_card_zm}">
                        <c:set var="img" value="${ctx}/${af.map.img_id_card_zm}@s400x400" />
                        <c:set var="img_max" value="${ctx}/${af.map.img_id_card_zm}" />
                      </c:if>
                      <a href="${img_max}" id="img_id_card_zm_a" target="_blank"><img src="${img}" height="100" id="img_id_card_zm_img" /></a>
                      <input type="hidden" name="img_id_card_zm" id="img_id_card_zm" value="${af.map.img_id_card_zm}" />
                      <div class="files-warp" id="img_id_card_zm_warp">
                        <div class="btn-files"> <span>添加附件</span>
                          <input id="img_id_card_zm_file" type="file" name="img_id_card_zm_file"/>
                        </div>
                        <div class="progress"> <span class="bar"></span><span class="percent">0%</span > </div>
                      </div>
                    </div></td>
                    <td><div>
                      <c:set var="img" value="${ctx}/commons/swfupload/style/images/no_image.jpg" />
                      <c:set var="img_max" value="${ctx}/commons/swfupload/style/images/no_image.jpg" />
                      <c:if test="${not empty af.map.img_id_card_fm}">
                        <c:set var="img" value="${ctx}/${af.map.img_id_card_fm}@s400x400" />
                        <c:set var="img_max" value="${ctx}/${af.map.img_id_card_fm}" />
                      </c:if>
                      <a href="${img_max}" id="img_id_card_fm_a" target="_blank"><img src="${img}" height="100" id="img_id_card_fm_img" /></a>
                      <input type="hidden" name="img_id_card_fm" id="img_id_card_fm" value="${af.map.img_id_card_fm}" />
                      <div class="files-warp" id="img_id_card_fm_warp">
                        <div class="btn-files"> <span>添加附件</span>
                          <input id="img_id_card_fm_file" type="file" name="img_id_card_fm_file"/>
                        </div>
                        <div class="progress"> <span class="bar"></span><span class="percent">0%</span > </div>
                      </div>
                    </div></td>
                    </tr>
                    </table>
                    
                    
                    <span style="color: #F00;float:left">&nbsp;店铺所属人(法人)身份证正面图片，大小2M以内，文字须清晰可见。</span> </div>
                </div>
                <div class="item" id="hasYinYeNo">
                  <div class="label"> <em>*</em> <span>商家法人营业执照：</span> </div>
                  <div class="value">
                    <input class="text" placeholder="请填写营业执照编号" type="text" value="${af.map.entp_licence}" name="entp_licence" maxlength="100" id="entp_licence" />
                    <br />
                    <c:set var="img" value="${ctx}/commons/swfupload/style/images/no_image.jpg" />
                    <c:if test="${not empty af.map.entp_licence_img}">
                      <c:set var="img" value=" ${ctx}/${af.map.entp_licence_img}@s400x400" />
                    </c:if>
                    <img src="${img}" height="100" id="entp_licence_img_img" />
                    <input type="hidden" name="entp_licence_img" id="entp_licence_img" value="${af.map.entp_licence_img}" />
                    <div class="files-warp" id="entp_licence_img_warp">
                      <div class="btn-files"> <span>添加附件</span>
                        <input id="entp_licence_img_file" type="file" name="entp_licence_img_file"/>
                      </div>
                      <div class="progress"> <span class="bar"></span><span class="percent">0%</span > </div>
                    </div>
                    <span>说明：营业执照扫描件大小不能超过2M!图片格式支持：gif,jpg,jpeg,png,bmp,ico。</span> </div>
                </div>
                <div class="item">
                  <div class="label"> <em>*</em> <span>店铺所在地区：</span> </div>
                  <div class="value"  id="city_div">
                    <select name="province" id="province" style="width:120px;" class="pi_prov" datatype="Require" msg="请选择省份">
                    </select>
                    &nbsp;
                    <select name="city" id="city" style="width:120px;" class="pi_city" datatype="Require" msg="请选择市">
                    </select>
                    &nbsp;
                    <select name="country" id="country" style="width:120px;" class="pi_dist" datatype="Require" msg="请选择区/县">
                    </select>
                  </div>
                </div>
                <div class="item">
                  <div class="label"> <em>*</em><span>地理坐标：</span> </div>
                  <div class="value">
                    <input class="text" type="text" name="entp_latlng" maxlength="100" style="width:200px" value="${af.map.entp_latlng}" id="entp_latlng" readonly="true"/>
                    &nbsp;
          			<input type="button" value="维护坐标" onclick="getLatlng('entp_latlng')" class="btns btns-danger" />
                  </div>
                </div>
                <div class="item" id="s_tr" style="display: none;">
                  <div class="label"> <em>*</em> <span>运营中心名称：</span> </div>
                  <div class="value"> <span id="s_name"></span>&nbsp;，电话：<span id="s_mobile" class="tip-warning"></span> </div>
                </div>
                <div class="item">
                  <div class="label"> <em>*</em> <span>店铺具体地址：</span> </div>
                  <div class="value">
                    <input class="text" type="text" name="entp_addr" maxlength="100" style="width: 600px" value="${af.map.entp_addr}" id="entp_addr" />
                  </div>
                </div>
              </div>
            </div>
            <div class="btn-group mt0">
              <input type="hidden" name="entp_id" value="${af.map.id}" />
              <input type="hidden" name="p_index" value="${af.map.p_index}" id="p_index" />
              <input type="hidden" name="p_index_pro" value="${af.map.p_index_pro}" id="p_index_pro" />
              <c:url var="url" value="/IndexEntpEnter.do?method=step2" />
              <input class="btn btn-w" id="js-pre-step" type="button" value="上一步" onclick="location.href='${url}'"  />
              <input class="btn" id="nextStepBtn" type="submit" value="下一步，完善店铺信息" />
            </div>
          </div>
          <div class="bg-bottom"></div>
        </div>
      </div>
    </form>
  </div>
</div>
<jsp:include page="../../_footer.jsp" flush="true" />
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.form.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/area.js?v=20170307"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/citySelect.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript">//<![CDATA[
var f_step = $("#stepForm").get(0);                             
$(document).ready(function(){
	
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
	
	
	$("#city_div").citySelect({
        data:getAreaDic(),
        province:"${af.map.province}",
        city:"${af.map.city}",
        country:"${af.map.country}",
        province_required:true,
        city_required:true,
        country_required:true,
    });
	
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
	
	
	//地市选择
	$("#province").change(function(){
		if (this.value.length != 0) {
			this.form.p_index.value = this.value;
			this.form.p_index_pro.value = this.value;
			$("#city").val("");
			$("#country").val("");
		}
	});
	$("#city").change(function(){
		if (this.value.length != 0) {
			this.form.p_index.value = this.value;
			$("#country").val("");
		} else {
			this.form.p_index.value = this.form.province.value;
		}
	});
	$("#country").change(function(){
		var thisVal = $(this).val();
		//查询该地区是否有运营中心
		if(null != thisVal && '' != thisVal){
		if(thisVal.length != 0) {
			$("#p_index").val(thisVal);
		} else {
			$("#p_index").val($("#city").val());
		}
		}
	});
	
	
});	

f_step.onsubmit = function () {

	if(Validator.Validate(this,1)){
		$("#addZdy_tbody").remove();
        $("#nextStepBtn").attr("value", "正在提交...").attr("disabled", "true");
		return true;
	}
	return false;
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
