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
<link href="${ctx}/styles/mui/poppicker/mui.picker.min.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/mui/poppicker/mui.poppicker.css" rel="stylesheet" type="text/css" />
</head>
<body>
<jsp:include page="../_header.jsp" flush="true" />
<div class="content">
  <!--article-->
  <form action="/m/MMyAccount" enctype="multipart/form-data" method="post" class="ajaxForm0">
    <input type="hidden" name="id" id="id" />
    <input type="hidden" name="method" value="save" />
    <input type="hidden" name="mod_id" id="mod_id" value="${af.map.mod_id}"/>
    <input type="hidden" name="queryString" id="queryString" />
    <input type="hidden" name="p_index" id="p_index" value="${p_index}"/>
    <input type="hidden" name="is_secret" id="is_secret" value="${af.map.is_secret}" />
    <div class="set-site" id="city_div">
      <ul>
        <li> <span class="grey-name">登录名：</span>
          <input style="width: 70%" name="user_name" id="user_name" type="text" autocomplete="off" maxlength="38" value="${af.map.user_name}" placeholder="登录名" readonly="true" class="buy_input">
        </li>
        <li> <span class="grey-name">真实姓名：</span>
          <input style="width: 70%" name="real_name" id="real_name" type="text" autocomplete="off" maxlength="38" value="${af.map.real_name}" placeholder="真实姓名" class="buy_input">
        </li>
        <li> <span class="grey-name">个人签名：</span>
          <input style="width: 70%" name="autograph" id="autograph" type="text" autocomplete="off" maxlength="120" value="${af.map.autograph}" placeholder="个人签名" class="buy_input">
        </li>
        <li> <span class="grey-name">微信id：</span>
          ${af.map.appid_weixin}
        </li>
        <li>
          <section class="files-info">
            <!-- 会员头像、姓名、等级 -->
            <c:set var="user_logo" value="${ctx}/m/styles/img/my/myls_avatar_v2.png" />
            <c:if test="${not empty af.map.user_logo}">
              <c:set var="user_logo" value=" ${ctx}/${af.map.user_logo}@s400x400" />
            </c:if>
            <c:if test="${fn:contains(af.map.user_logo, 'http://')}">
              <c:set var="user_logo" value="${af.map.user_logo}"/>
            </c:if>
            <span class="files-info_img_view"><img src="${user_logo}" id="user_logo_img"></span>
            <input type="hidden" name="user_logo" id="user_logo" value="${af.map.user_logo}"/>
            <div class="btnfiles app_logout files-warp" id="user_logo_warp">
              <div class="btn-files"> <span>添加附件</span>
                <input id="user_logo_file" type="file" name="user_logo_file" accept="image/*"  />
               </div>
              <div class="progress progress_m" style=""> <span class="bar bar_m"></span><span class="percent">0%</span > </div>
            </div>
            <div class="tipfiles tip-default">说明：头像大小不能超过2M!</div>
          </section>
        </li>
        <li> <span class="grey-name">地区：</span>
          <input style="width: 70%" name="showCity" id="showCity" type="text" maxlength="38" value="${af.map.full_name}" placeholder="所在地区" readonly="true" class="buy_input" />
        </li>
        <li class="select">
          <select name="sex" id="sex" value="${af.map.sex}">
            <option value='1' <c:if test="${af.map.sex eq 1}">selected</c:if>>女</option>
            <option value='0' <c:if test="${af.map.sex eq 0}">selected</c:if>>男</option>
          </select>
        </li>
        <li>
          <fmt:formatDate value="${af.map.birthday}" pattern="yyyy-MM-dd" var="_add_birthday" />
          <input name="birthday" type="text" id="birthday" readonly="readonly" autocomplete="off" maxlength="38" value="${_add_birthday}" placeholder="生日" class="buy_input">
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
<script type="text/javascript" src="${ctx}/m/js/date/app1/js/date.js"></script>
<script type="text/javascript" src="${ctx}/styles/mui/poppicker/mui.picker.min.js"></script>
<script type="text/javascript" src="${ctx}/styles/mui/poppicker/mui.poppicker.js"></script>
<script type="text/javascript" src="${ctx}/styles/mui/city/city.data-3.js"></script>
<c:url var="urlhome" value="/m/MMyHome.do" />
<script type="text/javascript">//<![CDATA[
$(document).ready(function() {
	
	var cityPicker3 = new mui.PopPicker({
		layer: 3
	});
	cityPicker3.setData(cityData3);
	document.getElementById('showCity').addEventListener('tap', function(event) {
		cityPicker3.show(function(items) {
			$("#p_index").val(items[2].value);
			$("#showCity").val(items[0].text  +","+ items[1].text  +","+ items[2].text);
		});
	}, false);
	
	
	var d_year = ${d_year};
	 $('#birthday').datePicker({
         beginyear: 1920,
         endyear: d_year,
         theme: 'date',
     });
	
	var btn_name = "上传头像";
	if ("" != "${af.map.user_logo}") {
		btn_name = "重新上传";
	}
	upload("user_logo", "image", btn_name, "${ctx}");
	
	$("#real_name").attr("dataType", "Require").attr("msg", "真实姓名不能为空");
	$("#repeat").attr("datatype", "Repeat").attr("to", "new_password").attr("msg", "两次输入的密码不一致");

	$("#sex").attr("datatype","Require").attr("msg","性别不能为空！");
	$("#birthday").attr("datatype","Require").attr("msg","生日不能为空！");
	$("#id_card").attr("datatype","IdCard").attr("Require","false").attr("msg","请输入正确的身份证号！");
	
	var f0 = $(".ajaxForm0").get(0);
	$("#btn_submit0").click(function(){
		if (Validator.Validate(f0, 1)) {
			Common.loading();
				$.ajax({
					type: "POST",
					url: "?method=saveUserInfo",
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

	$("#id_card_").change(function(){
		var thisVal = $(this).val();
		if(null != thisVal && '' != thisVal){
			$("#id_card").val(thisVal);
		}
	});

	$("#userul").find("li").click(function(){
		$(this).siblings().removeClass("xur");
		var id = "form" + $(this).attr("data_id");
		$(".backTable").hide();
		$(this).addClass("xur");
		$("#" + id).fadeIn();
		
	});
	
});
//]]></script>
</body>
</html>
