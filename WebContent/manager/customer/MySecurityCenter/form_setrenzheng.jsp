<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="mainbox mine">
  <html-el:form action="/customer/MySecurityCenter.do" styleClass="ajaxForm">
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="par_id" />
    <html-el:hidden property="audit_state_old" styleId="audit_state_old" value="${baseAuditRecord.audit_state}"/>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
      <tr>
        <th colspan="4">实名认证</th>
      </tr>
      <c:if test="${(not empty baseAuditRecord) and (baseAuditRecord.audit_state ne 1)}">
      <tr>
        <td colspan="4" align="center">
        <span class="label label-danger">正在审核中，请耐心等待。。。</span>
        </td>
      </tr>
      </c:if>
      <tr>
        <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>姓名：</td>
        <td colspan="3"><html-el:text property="real_name" maxlength="50" styleClass="webinput" styleId="real_name" style="width:200px"/></td>
      </tr>
      <tr>
        <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>身份证号码：</td>
        <td colspan="3"><html-el:text property="id_card" maxlength="50" styleClass="webinput" styleId="id_card" style="width:200px" /></td>
      </tr>
        <tr>
          <td class="title_item" nowrap="nowrap"><span style="color: #F00;">*</span>身份证正面/反面：</td>
          <td colspan="3"><div style="float:left;width:50%;">
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
                  <input id="img_id_card_zm_file" type="file" name="img_id_card_zm_file"/>
                </div>
                <div class="progress"> <span class="bar"></span><span class="percent">0%</span > </div>
              </div>
            </div>
            <div style="float:left;width:50%;">
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
                  <input id="img_id_card_fm_file" type="file" name="img_id_card_fm_file"/>
                </div>
                <div class="progress"> <span class="bar"></span><span class="percent">0%</span > </div>
              </div>
            </div>
            <span style="color: #F00;float:left">&nbsp;用户身份证正面图片，大小500K以内，文字须清晰可见。</span></td>
        </tr>
      <tr>
        <td width="14%" nowrap="nowrap">&nbsp;</td>
        <td colspan="3" style="text-align:left">
<%--         <c:if test="${af.map.is_renzheng eq 1}"> --%>
<!--         	<button class="bgButtonFontAwesome" type="button" id="btn_submit"><i class="fa fa-save"></i>修改</button> -->
<%--         </c:if> --%>
<%--         <c:if test="${af.map.is_renzheng eq 0}"> --%>
          <button class="bgButtonFontAwesome" type="button" id="btn_submit"><i class="fa fa-save"></i>保 存</button>
<%--            </c:if> --%>
           </td>
      </tr>
    </table>
  </html-el:form>
</div>
<c:set var="bank_account" value="${af.map.bank_account}"/>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript" src="${ctx}/scripts/bank/bank.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.form.min.js"></script>
<script type="text/javascript">//<![CDATA[
                                          
var $form  = $(".ajaxForm");
var f = $form.get(0);
$(document).ready(function(){
// 	var id_card_zm = ${img_id_card_zm};
// 	alert(id_card_zm)
// 	$("#img_id_card_zm_file").val(id_card_zm);
	$("#real_name").attr("datatype","Require").attr("msg","请填写姓名！");
	$("#id_card").attr("datatype","IdCard").attr("msg","请填写正确格式的身份证号码！");
	$("#img_id_card_zm").attr("dataType", "Filter" ).attr("msg", "身份证正面扫描件必须上传且格式必须是(bmp,gif,jpeg,jpg,png,png)").attr("accept", "bmp, gif, jpeg, jpg, png");
	$("#img_id_card_fm").attr("dataType", "Filter" ).attr("msg", "身份证反面扫描件必须上传且格式必须是(bmp,gif,jpeg,jpg,png,png)").attr("accept", "bmp, gif, jpeg, jpg, png");
	
	$("#btn_submit").click(function(){
		
		var img_id_card_zm = $("#img_id_card_zm").val();
		var img_id_card_fm = $("#img_id_card_fm").val();
		var real_name = $("#real_name").val();
		var id_card = $("#id_card").val();
		if(Validator.Validate(f, 3)){
			
			if($("#audit_state_old").val() == 1){
				 var submit2 = function(v, h, f) {
					if (v == "ok") {
						subForm();
					}
				};
				$.jBox.confirm("修改之后，需要管理员重新审核，你确定要修改吗?", "确定提示", submit2);
			}else{
				subForm();
			}
		} 
		
	});
	
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
	
});

function subForm(){
	$.jBox.tip("数据提交中...", 'loading');
	$("#btn_submit").attr("disabled", "true");
	window.setTimeout(function () { 
		$.ajax({
			type: "POST",
			url: "MySecurityCenter.do?method=modifyRenzheng",
			data: $form.serialize(),
			dataType: "json",
			error: function(request, settings) {$.jBox.tip("数据请求失败", "error");},
			success: function(data) {
				$("#btn_submit").removeAttr("disabled");
				if(data.ret == "0"){
					$.jBox.tip(data.msg, "info");
				} else {
					$.jBox.tip(data.msg, "success");
					window.setTimeout(function () {
						returnTo();
					}, 1000);
				}
			}
		});	
	}, 1000);
}


function returnTo(){
	var api = frameElement.api, W = api.opener;
	W.refreshPage();
	api.close();
}


//]]></script>
</body>
</html>
