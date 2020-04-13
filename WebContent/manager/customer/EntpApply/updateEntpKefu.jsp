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
<body style="height:2100px;">
<div class="mainbox mine">
  <jsp:include page="../_nav.jsp" flush="true"/>
  <%@ include file="/commons/pages/messages.jsp" %>
  <html-el:form action="/customer/EntpApply.do" enctype="multipart/form-data">
    <html-el:hidden property="queryString" />
    <html-el:hidden property="method" value="saveEntpKefu" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
      <tr>
        <th colspan="4">设置客服信息</th>
      </tr>
      
      <tr>
        <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>客服微信名片二维码：</td>
        <td colspan="3"><c:set var="img" value="${ctx}/commons/swfupload/style/images/no_image.jpg" />
          <c:if test="${not empty af.map.kefu_qr_code}">
            <c:set var="img" value=" ${ctx}/${af.map.kefu_qr_code}@s400x400" />
          </c:if>
          <img src="${img}" height="100" id="kefu_qr_code_img" />
          <html-el:hidden property="kefu_qr_code" styleId="kefu_qr_code" />
          <div class="files-warp" id="kefu_qr_code_warp">
            <div class="btn-files"> <span>添加附件</span>
              <input id="kefu_qr_code_file" type="file" name="kefu_qr_code_file"/>
            </div>
            <div class="progress"> <span class="bar"></span><span class="percent">0%</span > </div>
          </div>
          <span>说明：[图片比例]建议：1:1 [图片尺寸] 建议：<span style="color: red;">[280px * 280px]</span> ，图片大小不超过2M，图片格式支持：gif,jpg,jpeg,png,bmp,ico。</span></td>
      </tr>
      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>客服联系电话：</td>
        <td colspan="3"><html-el:text property="kefu_tel" maxlength="100" styleId="kefu_tel" style="width:400px" /></td>
      </tr>
      <tr>
        <td class="title_item">客服QQ：</td>
        <td colspan="3"><html-el:text property="qq" maxlength="40" styleId="qq" style="width:400px" /></td>
      </tr>
<!--       <tr> -->
<!--         <td class="title_item">公众号推送消息用户微信号码：</td> -->
<%--         <td colspan="3"><html-el:text property="customer_code" maxlength="40" styleId="customer_code" style="width:400px" /></td> --%>
<!--       </tr> -->
      
      <tr>
        <td colspan="4" style="text-align:center"><html-el:button property="" value="保存" styleClass="bgButton" styleId="btn_submit" />
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
<script type="text/javascript" src="${ctx}/scripts/citySelect/area.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/citySelect.js"></script>
<script type="text/javascript">//<![CDATA[

var f = document.forms[0];

$(document).ready(function(){
	
	
	var btn_name = "上传客服微信名片二维码";
	if ("" != "${af.map.kefu_qr_code}") {
		btn_name = "重新上传微信名片二维码";
	}
	upload("kefu_qr_code", "image", btn_name, "${ctx}");

	var regMobile = /^((\(\d{2,3}\))|(\d{3}\-))?((1[3-9]\d{9}))$/;
	var regPhone = /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/; 

	$("#kefu_qr_code").attr("dataType", "Filter" ).attr("msg", "请上传客服二维码").attr("accept", "bmp, gif, jpeg, jpg, png");

	$("#kefu_tel").attr("datatype","Require").attr("msg","客服电话必须填写");
	
	// 提交
	$("#btn_submit").click(function(){
		if(Validator.Validate(f, 1)){
			$("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
			 $("#btn_reset").attr("disabled", "true");
			 $("#btn_back").attr("disabled", "true");
			 f.submit();
		}
	});

});
                                          
//]]></script>
</body>
</html>
