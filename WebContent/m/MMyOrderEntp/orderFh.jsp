<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${app_name}</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Expires" content="-1">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Pragma" content="no-cache">
<jsp:include page="../_public_in_head.jsp" flush="true" />
</head>
<body>
<div class="content">
  <html-el:form action="/MyOrderEntp.do" enctype="multipart/form-data" styleClass="ajaxForm0">
    <div class="set-site">
      <ul class="formUl">
      <c:if test="${orderInfo.order_type ne 10}">
         <li><span class="grey-name">快递公司：</span>
<%--           <html-el:select property="wl_comp_id" styleId="wl_comp_id" style="width:70%;"> --%>
<%-- 	         <html-el:option value="">请选择</html-el:option> --%>
<%-- 	         <c:forEach items="${wlCompInfoList}" var="cur"> --%>
<%--                 <html-el:option value="${cur.id}">${cur.wl_comp_name}</html-el:option> --%>
<%--              </c:forEach> --%>
<%--           </html-el:select> --%>
          <html-el:hidden property="wl_comp_id" styleId="wl_comp_id"/>
          <input type="text" name="waybill_name" placeholder="请选择快递公司" id="waybill_name" maxlength="50" readonly="readonly" onclick="getWlCompInfoList();"/>
        </li>
        <li><span class="grey-name">运单号：</span>
        <input type="text" name="waybill_no" placeholder="请输入运单号" id="waybill_no" maxlength="50" />
        </li>
        </c:if>
        <li><span class="grey-name">发货备注：</span>
        <input type="text" name="fahuo_remark" placeholder="请输入发货备注" id="fahuo_remark" maxlength="120" />
        </li>
      </ul>
    </div>
    <div class="box submit-btn">
    <input type="button" class="com-btn" id="btn_submit" value="保存" /></div>
  </html-el:form>
</div>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script> 
<script type="text/javascript" src="${ctx}/commons/scripts/validator.m.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jquery.nav.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function() {
	<c:if test="${orderInfo.order_type ne 10}">
	$("#wl_comp_id").attr("datatype", "Require").attr("msg", "请选择快递公司！");
	$("#waybill_name").attr("datatype", "Require").attr("msg", "请选择快递公司！");
	$("#waybill_no").attr("datatype", "Require").attr("msg", "请填写运单号！");
	</c:if>
	
	var f0 = $(".ajaxForm0").get(0);
	$("#btn_submit").click(function(){
		if(Validator.Validate(f0, 1)){
			 $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
			 $.ajax({
					type: "POST",
					url: "?method=updateState&id=${af.map.order_id}&state=20",
					data: $(f0).serialize(),
					dataType: "json",
					error: function(request, settings) {},
					success: function(data) {
						if(data.ret == "1"){
							window.setTimeout(function () {
								returnTo();
							}, 500);
						}else {
							mui.toast(data.msg);
							$("#btn_submit").attr("value", "保 存").removeAttr("disabled");
						}
					}
				});	
			return true;
		}
	});
});


function getWlCompInfoList(){
	var url = "${ctx}/CsAjax.do?method=getWlCompInfoList";
	$.dialog({
		title:  "选择快递公司",
		width:"100%",
		height: "100%",
		padding: 0,
		max: true,
        min: false,
        fixed: true,
        lock: true,
        zIndex:999,
		content:"url:"+ encodeURI(url)
	});
}

function returnTo(){
	var api = frameElement.api, W = api.opener;
	W.refreshPage();
	api.close();
}
//]]></script>
</body>
</html>
