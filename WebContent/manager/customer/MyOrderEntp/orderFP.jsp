<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="mainbox mine">
<html-el:form action="/customer/MyOrderEntp.do" styleClass="ajaxForm">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
      <tr>
        <td nowrap="nowrap" class="title_item" width="14%"><span style="color: #F00" id="span_main_pic">*</span>发票凭证：</td>
        <td colspan="2"><c:set var="img" value="${ctx}/styles/imagesPublic/user_header.png" />
          <c:if test="${not empty af.map.file_path}">
            <c:set var="img" value=" ${ctx}/${af.map.file_path}@s400x400" />
          </c:if>
          <img src="${img}" height="80" id="file_path_img" style="height: 300px;"/>
          <html-el:hidden property="file_path" styleId="file_path" />
          <div class="file-warp" id="file_path_warp">
            <div class="btn-files"> <span>上传凭证</span>
              <input id="file_path_file" type="file" name="file_path_file" />
            </div>
            <div class="progress"> <span class="bar"></span><span class="percent">0%</span></div>
          </div>
      </tr>
       <tr>
        <td style="text-align:center" colspan="2">
          <html-el:submit property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
       </td>
      </tr>
    </table>
</html-el:form>
</div>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script> 
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.form.min.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	var btn_name = "上传主图";
	if ("" != "${af.map.file_path}") {
		btn_name = "重新上传";
	}
	upload("file_path", "image", btn_name, "${ctx}");
	
	$("#file_path").attr("dataType", "Filter" ).attr("msg", "请上传格式为（bmp, gif, jpeg, jpg, png）的主图地址！").attr("require", "true").attr("accept", "bmp, gif, jpeg, jpg, png");
	
	var f0 = $(".ajaxForm").get(0);
	f0.onsubmit = function(){
		if (Validator.Validate(f0, 3)) {
			$.jBox.tip("数据提交中...", 'loading');
			$("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
			window.setTimeout(function () {
				$.ajax({
					type: "POST",
					url: "?method=updateFp&order_id=${af.map.order_id}",
					data: $(f0).serialize(),
					dataType: "json",
					error: function(request, settings) {},
					success: function(data) {
						if(data.ret == "1"){
							$.jBox.tip(data.msg, "success",{timeout:1000});
							window.setTimeout(function () {
								returnTo();
							}, 1500);
						} else {
							$.jBox.tip(data.msg, "info",{timeout:1000});
							$("#btn_submit").attr("value", "保 存").removeAttr("disabled");
						}
					}
				});	
			}, 1000);
			return false;
		}
		return false;
	}
});

function returnTo(){
	var api = frameElement.api, W = api.opener;
	W.refreshPage();
	api.close();
}
//]]></script>
</body>
</html>
