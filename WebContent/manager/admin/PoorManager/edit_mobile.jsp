<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
</head>
<body>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/admin/PoorManager.do">
    <html-el:hidden property="queryString" />
    <html-el:hidden property="method" value="saveMobile" />
    <html-el:hidden property="id" styleId="id" />
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="p_index" styleId="p_index" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th colspan="2">用户基本信息</th>
      </tr>
      <tr>
        <td width="15%" class="title_item">登录名：</td>
        <td width="85%">${af.map.user_name }</td>
      </tr>
      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>真实姓名：</td>
        <td>${af.map.real_name}</td>
      </tr>
      <tr id="tr-mobile">
        <td class="title_item"><span style="color: #F00;">*</span>手机：</td>
        <td>
        <c:set var="mobile" value="${poorInfo.mobile}" />
        <c:if test="${not empty af.map.mobile }">
        	<c:set var="mobile" value="${af.map.mobile}" />
        </c:if>
        <html-el:text property="mobile" maxlength="80" styleClass="webinput" value="${mobile}" styleId="mobile" onblur="validMobile($(this).val());" style="width:200px;" /></td>
      </tr>
      <tr>
        <td colspan="2" style="text-align: center"><html-el:button property="" value="保 存" styleClass="bgButton" styleId="btn_submit" /></td>
      </tr>
    </table>
  </html-el:form>
</div>

<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/area.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/citySelect.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script> 
<script type="text/javascript">//<![CDATA[

var api = frameElement.api, W = api.opener;
		var f = document.forms[0];
		// 提交
		$("#btn_submit").click(function() {
			var mobile = $("#mobile").val();
			if(null != mobile && mobile != '' && mobile != 19){
				$("#mobile").attr("datatype", "Mobile").attr("msg", "请正确填写手机号");
			}
			
			if (Validator.Validate(f, 3)) {
				$.ajax({
					type: "POST" , 
					url: "${ctx}/manager/admin/PoorManager.do?method=saveMobile" , 
					data:{
						id:"${af.map.id}",
						mobile:mobile
					},
					dataType: "json", 
			        async: true, 
			        error: function(request, settings) {},
			        success: function (data) {
			        	alert(data.msg);
						if (data.ret == "-1") {
							$("#btn_submit").removeAttr("disabled");
							return false;
						}else{
							W.location.reload()
							api.close();
						}
			        }
				});
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
						$.jBox.alert("参数丢失","提示");
						$("#btn_submit").attr("disabled", "true");
						return false;
					} else if (result == 1) {
						$("#btn_submit").removeAttr("disabled");
					} else if (result == 2) {
						$.jBox.alert("该手机号码已被注册！","提示");
						$("#btn_submit").attr("disabled", "true");
						return false;
					}
		        }
			});
		} else {
			$.jBox.alert("手机格式不正确！","提示");
			$("#btn_submit").attr("disabled", "true");
			return false;
		}
	}
}		
//]]>
</script>
<jsp:include page="../public_page.jsp" flush="true" />
</body>
</html>
