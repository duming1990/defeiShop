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
  <form action="/m/MMySecurityCenter" enctype="multipart/form-data" method="post" class="ajaxForm">
  <html-el:hidden property="audit_state_old" styleId="audit_state_old" value="${baseAuditRecord.audit_state}"/>
    <div class="set-site">
     <ul>
      <c:if test="${(not empty baseAuditRecord) and (baseAuditRecord.audit_state eq  0)}">
      <li>
        <span class="label label-danger">正在审核中，请耐心等待。。。</span>
      </li>
      </c:if>
      <c:if test="${(not empty baseAuditRecord) and (baseAuditRecord.audit_state eq  -1)}">
      <li>
        <span class="label label-danger">审核不通过：${baseAuditRecord.audit_note}</span>
      </li>
      </c:if>
      <c:if test="${(not empty baseAuditRecord) and (baseAuditRecord.audit_state eq  1)}">
      <li>
        <span class="label label-success">审核通过</span>
      </li>
      </c:if>
      <li>
        <span style="width: 40%" class="grey-name"><span style="color: #F00;">*</span>姓名：</span>
        <input style="width: 60%" name="real_name" id="real_name" value="${real_name}" type="text" autocomplete="off" maxlength="38" class="buy_input" />
      </li>
      <li>
         <span style="width: 40%" class="grey-name"><span style="color: #F00;">*</span>身份证号码：</span>
         <input style="width: 60%" name="id_card" id="id_card"  value="${user.id_card}" autocomplete="off" maxlength="38" class="buy_input" type="text" />
      </li>
        <li>
          <span style="width: 100%" class="grey-name">
          <div style="float:left;width:50%;text-align: center;">
              <c:set var="img" value="${ctx}/commons/swfupload/style/images/no_image.jpg" />
              <c:set var="img_max" value="${ctx}/commons/swfupload/style/images/no_image.jpg" />
              <c:if test="${not empty img_id_card_zm}">
                <c:set var="img" value="${ctx}/${img_id_card_zm}@s400x400" />
                <c:set var="img_max" value="${ctx}/${img_id_card_zm}" />
              </c:if>
              <a href="${img_max}" id="img_id_card_zm_a" target="_blank"><img src="${img}" height="50"  id="img_id_card_zm_img" /></a>
              <html-el:hidden property="img_id_card_zm" styleId="img_id_card_zm" value="${img_id_card_zm}"/>
              <div class="files-warp" id="img_id_card_zm_warp"> <span>身份证正面扫描件:</span><br />
                <div class="btn-files"> <span>添加附件</span>
                  <input id="img_id_card_zm_file" type="file" name="img_id_card_zm_file" accept="image/*"  />
                </div>
                <div class="progress"> <span class="bar"></span><span class="percent">0%</span > </div>
              </div>
          </div>
            <div style="float:left;width:50%;text-align: center;">
              <c:set var="img" value="${ctx}/commons/swfupload/style/images/no_image.jpg" />
              <c:set var="img_max" value="${ctx}/commons/swfupload/style/images/no_image.jpg" />
              <c:if test="${not empty img_id_card_fm}">
                <c:set var="img" value="${ctx}/${img_id_card_fm}@s400x400" />
                <c:set var="img_max" value="${ctx}/${img_id_card_fm}" />
              </c:if>
              <a href="${img_max}" id="img_id_card_fm_a" target="_blank"><img src="${img}" height="50" id="img_id_card_fm_img" /></a>
              <html-el:hidden property="img_id_card_fm" styleId="img_id_card_fm" value="${img_id_card_fm}"/>
              <div class="files-warp" id="img_id_card_fm_warp"> <span>身份证反面扫描件:</span><br />
                <div class="btn-files"> <span>添加附件</span>
                  <input id="img_id_card_fm_file" type="file"  name="img_id_card_fm_file" accept="image/*" />
                </div>
                <div class="progress"> <span class="bar"></span><span class="percent">0%</span > </div>
              </div>
            </div>
            <span style="color: #F00;float:left">&nbsp;用户身份证正面图片，大小5M以内，文字须清晰可见。</span></span>
        </li>
</ul>
    </div>
    <div class="box submit-btn"> <a class="com-btn" id="btn_submit">保存</a> </div>
  </form>
</div>
</div>
<jsp:include page="../_footer.jsp" flush="true" />
<c:set var="bank_account" value="${af.map.bank_account}"/>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.m.js"></script>
<script type="text/javascript" src="${ctx}/scripts/bank/bank.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.form.min.js"></script>
<script type="text/javascript">//<![CDATA[
var $form  = $(".ajaxForm");
var f = $form.get(0);
$(document).ready(function(){
	$("#real_name").attr("dataType", "Require").attr("msg", "请填写姓名");
	$("#id_card").attr("dataType", "IdCard" ).attr("msg", "请正确填写身份证").attr("require", "true");
	
	$("#btn_submit").click(function(){
		var img_id_card_zm = $("#img_id_card_zm").val();
		var img_id_card_fm = $("#img_id_card_fm").val();
		var real_name = $("#real_name").val();
		var id_card = $("#id_card").val();
		
		if(null == img_id_card_zm || "" == img_id_card_zm){
			mui.toast("请上传身份证正面");
			return false;
		}
		if(null == img_id_card_fm || "" == img_id_card_fm){
			mui.toast("请上传身份证反面");
			return false;
		}
		
		if(Validator.Validate(f, 3)){
			
			if($("#audit_state_old").val() == 1){
			  Common.confirm("修改之后，需要管理员重新审核，你确定要修改吗?",["确定修改","取消修改"],function(){
				 subForm();
				 },function(){
			    });
			}else{
				subForm();
			}
		} 
		
	});
	
	var btn_name = "上传正面";
	if ("" != "${img_id_card_zm}") {
		btn_name = "重新上传";
	}
	upload("img_id_card_zm", "image", btn_name, "${ctx}");
	
	var btn_name = "上传背面";
	if ("" != "${img_id_card_fm}") {
		btn_name = "重新上传";
	}
	upload("img_id_card_fm", "image", btn_name, "${ctx}");
	
	
	function subForm(){
		Common.loading();
		$("#btn_submit").attr("disabled", "true");
		window.setTimeout(function () { 
			$.ajax({
				type: "POST",
				url: "?method=modifyRenzheng",
				data: $form.serialize(),
				dataType: "json",
				error: function(request, settings) {mui.toast("数据请求失败");},
				success: function(data) {
					$("#btn_submit").removeAttr("disabled");
					if(data.ret == "0"){
						mui.toast(data.msg);
						Common.hide();
					} else {
						mui.toast(data.msg);
						window.setTimeout(function () { 
						 location.href="MMySecurityCenter.do?mod_id=1100620100";
						}, 1000);
					}
				}
			});	
    	}, 1000);
	}
	
});
//]]></script>
</body>
</html>
