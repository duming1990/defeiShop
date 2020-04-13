<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
</head>
<body>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar.js"></script>
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/admin/UserAudit.do" enctype="multipart/form-data">
    <html-el:hidden property="queryString" />
    <html-el:hidden property="method" value="save" />
    <html-el:hidden property="id" styleId="id"/>
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="link_id" styleId="link_id"/>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th colspan="4">基本信息</th>
      </tr>
     <c:if test="${is_add}">
      <tr>
         <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>选择用户：</td>
          <td colspan="3">
            <html-el:hidden property="user_id" styleId="user_id" />
            <html-el:text property="opt_note" maxlength="20" styleClass="webinput" styleId="user_name" style="width:200px" readonly="true"/>
             &nbsp;
             <a class="butbase" onclick="getUserInfo()" ><span class="icon-search">选择</span></a>
          </td>
       </tr>
      </c:if>
      <c:if test="${!is_add}">
       <tr>
         <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>真实姓名：</td>
            <td colspan="3"><html-el:text property="opt_note" maxlength="50" styleClass="webinput" styleId="opt_note" style="width:300px"/></td>
       </tr>
       </c:if>
       <tr>
         <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>身份证号码：</td>
            <td colspan="3"><html-el:text property="id_card" maxlength="50" styleClass="webinput" styleId="id_card" style="width:300px"/></td>
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
        <td colspan="4" style="text-align:center">
          <html-el:button property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
          &nbsp;
          <html-el:button property="" value="重 填" styleClass="bgButton" styleId="btn_reset" onclick="this.form.reset();" />
          &nbsp;
          <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
      </tr>
    </table>
  </html-el:form>
</div>
 
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script> 
<script type="text/javascript" src="${ctx}/commons/kindeditor/kindeditor.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script> 
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.form.min.js"></script>
<script type="text/javascript">//<![CDATA[

var f = document.forms[0];

$(document).ready(function(){
	
	$("#real_name").attr("datatype","Require").attr("msg","请填写姓名！");
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
	
	
	// 提交
	$("#btn_submit").click(function(){
		if(Validator.Validate(f, 3)){
			 $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
			 $("#btn_reset").attr("disabled", "true");
			 $("#btn_back").attr("disabled", "true");
			 f.submit();
		}
	});
});     

function valIsHasAudit(user_id){
	if(null != user_id && "" != user_id){
		$("#link_id").val(user_id);
		 $.ajax({
			   type: "POST",
			   url: "${ctx}/manager/admin/UserAudit.do?method=valIsHasAudit",
			   data: "user_id="+user_id,
			   success: function(data){
			     if(data.ret != 1){
			    	 $.jBox.tip(data.msg, "error");
			    	 $("#btn_submit").attr("disabled", "true");
			    	 if(data.ret == -2 && null != data.id){
			    		 window.setTimeout(function () { 
			    			 location.href= '${ctx}/manager/admin/UserAudit.do?method=edit&id=' + data.id + "&mod_id=${af.map.mod_id}";
			    		 }, 1000);
			    	 }
			     }else{
			    	 $("#btn_submit").removeAttr("disabled");
			     }
			   }
		 });
	}
}


function getUserInfo(){
	var url = "${ctx}/BaseCsAjax.do?method=getUserInfo&getIdCard=true";
	$.dialog({
		title:  "选择用户",
		width:  770,
		height: 550,
        lock:true ,
		content:"url:"+url
	});
}

//]]></script>
<jsp:include page="../public_page.jsp" flush="true" />
</body>
</html>
