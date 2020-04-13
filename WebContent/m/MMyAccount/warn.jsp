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
  <form action="/m/MMyAccount" enctype="multipart/form-data" method="post" class="ajaxForm0">
    <input type="hidden" name="id" id="id" value="${af.map.id}"/>
    <input type="hidden" name="method" value="saveWarn" />
    <input type="hidden" name="mod_id" id="mod_id" value="${af.map.mod_id}"/>
    <input type="hidden" name="queryString" id="queryString" />
    <div class="set-site" id="city_div">
      <ul>
        <li class="select">
          <select name="is_start" id="is_start" value="${af.map.is_start}">
            <option value='1' <c:if test="${af.map.is_start eq 1}">selected</c:if>>开启</option>
            <option value='0' <c:if test="${af.map.is_start eq 0}">selected</c:if>>关闭</option>
          </select>
        </li>
      </ul>
    </div>
    <div class="box submit-btn"> <a class="com-btn" id="btn_submit0">保存</a> </div>
  </form>
</div>
<jsp:include page="../_footer.jsp" flush="true" />
<script type="text/javascript" src="${ctx}/commons/scripts/validator.m.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.form.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/iscroll/iscroll-5.1.2.min.js"></script>
<script type="text/javascript" src="${ctx}/m/js/date/app1/js/date.js?v20160406"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/area.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/citySelect.js"></script>
<c:url var="urlhome" value="/m/MMyHome.do" />
<script type="text/javascript">//<![CDATA[
$(document).ready(function() {

	$("#is_start").attr("datatype","Require").attr("msg","设置不能为空！");
	
	var f0 = $(".ajaxForm0").get(0);
	$("#btn_submit0").click(function(){
		if (Validator.Validate(f0, 1)) {
			Common.loading();
				$.ajax({
					type: "POST",
					url: "?method=saveWarn",
					data: $(f0).serialize(),
					dataType: "json",
					error: function(request, settings) {},
					success: function(data) {
						Common.hide();
						if (data.code == "1") {
							mui.toast(data.msg);
							window.setTimeout(function () {
								goUrl('${urlhome}',0);
							},2000);
						} else {
							mui.toast(data.msg);
						}
					}
				});	
			return true;
		}
		return false;
	});

});
//]]></script>
</body>
</html>
