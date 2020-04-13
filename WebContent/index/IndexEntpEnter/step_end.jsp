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
          <div class="progress-item passed">
            <div class="number">2</div>
            <div class="progress-desc">公司信息认证</div>
            <div class="arrow-background"></div>
            <div class="arrow-foreground"></div>
          </div>
          <div class="progress-item passed">
            <div class="number">3</div>
            <div class="progress-desc">店铺信息认证</div>
            <div class="arrow-background"></div>
            <div class="arrow-foreground"></div>
          </div>
          <div class="progress-item ongoing">
            <div class="number">4</div>
            <div class="progress-desc">等待审核</div>
          </div>
        </div>
        <div class="panel-content">
          <div class="bg-top"></div>
          <div class="bg-warp pannel_end">
            <div class="settled-state"> <span>
              <c:choose>
                <c:when test="${af.map.audit_state eq -2}"><span class="tip-danger">管理员审核不通过</span></c:when>
                <c:when test="${af.map.audit_state eq -1}"><span class="tip-danger">运营中心审核不通过</span></c:when>
                <c:when test="${af.map.audit_state eq 0}"><span class="tip-default">正在审核中...</span></c:when>
                <c:when test="${af.map.audit_state eq 1}"><span class="tip-success">运营中心审核通过</span>&nbsp;<span class="tip-danger">等待管理员审核</span></c:when>
                <c:when test="${af.map.audit_state eq 2}"><span class="tip-success">管理员审核通过</span></c:when>
              </c:choose>
              </span> </div>
            <c:if test="${not empty af.map.audit_desc_one}">
              <div>运营中心审核说明：
                <c:out value="${af.map.audit_desc_one}" />
              </div>
            </c:if>
            <c:if test="${not empty af.map.audit_desc_two}">
              <div>管理员审核说明：
                <c:out value="${af.map.audit_desc_two}" />
              </div>
            </c:if>
            <h3 class="ordertitle">感谢您在${app_name}申请商家入驻！</h3>
            <div class="item">
              <div class="">店铺名称：${fn:escapeXml(af.map.entp_name)}</div>
            </div>
            <div class="item">
              <div class="">店铺简介：${fn:escapeXml(af.map.entp_desc)}</div>
            </div>
            <c:url var="url" value="/index.do" />
            <c:url var="urlredo" value="/IndexEntpEnter.do?method=step2&redo=true" />
            <c:url var="urlmy" value="/manager/customer/index.do" />
            <div class="setted-footer"><a href="${url}">返回首页</a><a href="${urlredo}">重新提交信息</a><a href="${urlmy}">用户中心</a><c:if test="${af.map.audit_state eq 2}">
            <a onclick="goCustomerUrl('1300500100')">请完善商品信息</a>
            </c:if></div>
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
<script type="text/javascript" src="${ctx}/scripts/citySelect/area.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/citySelect.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script> 
<script type="text/javascript">//<![CDATA[
var f_step = $("#stepForm").get(0);                             
$(document).ready(function(){
	
	$("#entp_tel").attr("datatype","Mobile").attr("msg","请正确填写联系人手机");
	$("#qq").attr("datatype","QQ").attr("msg","请正确填写联系人QQ");
	$("#entp_email").attr("datatype","Email").attr("msg","请正确填写联系人邮箱");
	$("#entp_addr").attr("datatype","Require").attr("msg","请正确填店铺具体地址");
	
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
	
	<c:if test="${af.map.audit_state eq 2}">
	showTip();
	</c:if>
	
});	

f_step.onsubmit = function () {

	if(Validator.Validate(this,1)){
		$("#addZdy_tbody").remove();
        $("#nextStepBtn").attr("value", "正在提交...").attr("disabled", "true");
		return true;
	}
	return false;
}

function goCustomerUrl(mod_id){
    $.post("${ctx}/CsAjax.do?method=getUrlLinkModId",{mod_id:mod_id},function(data){
		if(data.ret == 1){
			var parId_cookie = data.par_id + "," + data.data_url;
			if ($.isFunction($.cookie)) $.cookie("parId_cookie", parId_cookie, { path: '/' });
			location.href= "${ctx}/manager/customer/index.shtml";
		}
	});

}

function showTip() {
	
	var submit2 = function(v, h, f) {
		if (v == "ok") {
			goCustomerUrl('1300500100');
		}
		return true
	};
	var tip = "恭喜您管理员已审核通过，点击确定完善商品信息!";
	$.jBox.confirm(tip, "审核通知", submit2);

}

//]]></script>
</body>
</html>
