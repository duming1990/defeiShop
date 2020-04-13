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
<link href="${ctx}/m/styles/css/mui-checkbox.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/m/styles/css/my/my-v1.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/index/css/btns.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/m/js/date/app1/css/date.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/m/styles/css/iconfont.css" rel="stylesheet" type="text/css" />
</head>
<body>
<jsp:include page="../_header.jsp" flush="true" />
<div class="content">
  <!--article-->
   <html-el:form action="/MEntpEnter.do" enctype="multipart/form-data" styleClass="ajaxForm0">
    <html-el:hidden  property="id" styleId="id" />
    <html-el:hidden  property="method" value="step2" />
    <html-el:hidden  property="mod_id" styleId="mod_id" value="${af.map.mod_id}"/>
    <html-el:hidden  property="queryString" styleId="queryString" />
    <html-el:hidden  property="entp_id" styleId="entp_id" value="${af.map.id}" />
    <div class="set-site" id="city_div">
      <ul>
      <li class="select"><span class="grey-name">性别：</span>
          <html-el:select property="sex" styleId="sex" value="${sex}" style="width:70%;">
          	<html-el:option value="1">女</html-el:option>
          	<html-el:option value="0">男</html-el:option>
          </html-el:select>
        </li>
        <li> <span class="grey-name">联系人姓名：</span>
          <input style="width: 70%" name="entp_linkman" id="entp_linkman" type="text" autocomplete="off" maxlength="38" value="${af.map.entp_linkman}" class="buy_input">
        </li>
        <li> <span class="grey-name">联系人手机：</span>
          <input style="width: 70%" name="entp_tel" id="entp_tel" type="text" autocomplete="off" maxlength="38" value="${af.map.entp_tel}" class="buy_input">
        </li>
        <li> <span class="grey-name">联系人邮箱：</span>
          <input style="width: 70%" name="entp_email" id="entp_email" type="text" autocomplete="off" maxlength="38" value="${af.map.entp_email}"  class="buy_input">
        </li>
        <li> <span class="grey-name">客服QQ：</span>
          <input style="width: 70%" name="qq" id="qq" type="text" autocomplete="off" maxlength="38" value="${af.map.qq}" class="buy_input">
        </li>
        <li> 
  			<div style="text-align: center;width: 100%;">
  			<label class="mui-label" style="display: -webkit-inline-box;"><input name="pks"  id="pks" class="mui-checkbox checkbox-s" type="checkbox"></label>
<!--   			<input checked="checked" id="pks" name="pks" style="width: 20px;display: -webkit-inline-box;" type="checkbox"> -->
  			<a onclick="getDialog();">《入驻协议》</a>
  			</div>
        </li>
      </ul>
    </div>
    <div class="box submit-btn"> <a class="com-btn" id="btn_submit0">下一步</a> </div>
  </html-el:form>
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
<c:url var="step2Url" value="/m/MEntpEnter.do?method=step2" />
<script type="text/javascript">//<![CDATA[


$(document).ready(function() {
	
	$("#entp_linkman").attr("datatype","Require").attr("msg","请正确填写联系人姓名");
	$("#entp_tel").attr("datatype","Mobile").attr("msg","请正确填写联系人手机");
	
	var f0 = $(".ajaxForm0").get(0);
	$("#btn_submit0").click(function(){
		if (Validator.Validate(f0, 1)) {
			Common.loading();
			 f0.submit();
			return true;
				
		}
		return false;
	});

});
function getDialog(){
	$.dialog({
		lock: true,
		zIndex:9999,
		title:"入住须知",
		content: "url:?method=getDialog",
		button: [{
			name: '知道了',
			focus: true
		}]
	}).max();
}
//]]></script>
</body>
</html>
