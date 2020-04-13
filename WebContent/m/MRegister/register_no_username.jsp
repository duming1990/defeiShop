<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>${app_name}</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Description" content="${app_name}后触屏版登陆" />
<meta name="Keywords" content="${app_name}后触屏版登陆" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"/>
<meta name="apple-mobile-web-app-capable" content="yes"/>
<meta name="apple-mobile-web-app-status-bar-style" content="black"/>
<meta name="format-detection" content="telephone=no"/>
<jsp:include page="../_public_in_head.jsp" flush="true" />
<link rel="stylesheet" href="${ctx}/m/styles/css/pop-ver.css?v=20161214" />
<link rel="stylesheet" type="text/css" href="${ctx}/m/styles/css/my/comm-details.css?v=20180320" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/common.css?v20161116"  />
</head>
<body>
<jsp:include page="../_header.jsp" flush="true" />
<!--content-->
<div class="content bg-white">
  <div class="entry" style="display:none;"> </div>
  <!--article-->
  <div class="login-box">
    <c:set var="bind_name" value="" />
    <c:if test="${not empty af.map.appid_qq}">
      <c:set var="bind_name" value=" - 绑定QQ" />
    </c:if>
    <c:if test="${not empty af.map.appid_weibo}">
      <c:set var="bind_name" value=" - 绑定微博" />
    </c:if>
    <c:set var="url" value="/MRegister.do" />
    <c:if test="${not empty is_mobile}">
      <c:set var="url" value="/Register.do" />
    </c:if>
    <html-el:form action="${url}" method="post" styleClass="register_form">
      <input type="hidden" name="method" value="registerNoUserName" />
      <html-el:hidden property="appid_qq" styleId="appid_qq" />
      <html-el:hidden property="appid_weibo" styleId="appid_weibo" />
      <html-el:hidden property="user_logo" />
      <html-el:hidden property="user_id" styleId="user_id"/>
      <ul class="codebox">
        <c:if test="${not empty bind_name}">
          <li class="box name">
            <input id="real_name" type="text" name="real_name" value="${fn:escapeXml(af.map.real_name)}" class="codebox-input zhaoinput" maxlength="20" placeholder="请输入昵称 "/>
          </li>
        </c:if>
        <li class="box name">
          <input id="mobile" type="tel" name="mobile" class="box-flex codebox-input zhaoinput width75" maxlength="11" placeholder="请输入您的手机号" />
          <input type="button" class="btnside zhaosubmit" id="sendMobileCode" onClick="getMessage($('#mobile').val());" value="获取验证码" />
        </li>
        <li class="pwd">
          <input id="verifycode" type="number" name="verifycode" class="codebox-input zhaoinput" placeholder="请输入短信验证码" maxlength="6">
        </li>
        <li class="box pwd">
          <input type="password" name="password_hide" id="password_hide" class="box-flex codebox-input input-1-password" placeholder="登录密码" autocomplete="off" maxlength="32" />
          <input type="text" name="password" id="password_show" class="box-flex codebox-input input-1-text" placeholder="登录密码" autocomplete="off" maxlength="32" style="display:none;" />
          <input type="button" class="pwd-mode toggle-pwd" id="changePassShowType" />
        </li>
        <c:set var="readonly" value=""/>
        <c:if test="${not empty af.map.readolny_ymid}">
          <c:set var="readonly" value="readonly='true'"/>
        </c:if>
        <li class="pwd">
          <input id="ymid" type="text" ${readonly}  value="${af.map.ymid}" name="ymid"  class="codebox-input input-2-password" maxlength="20" autocomplete="off" placeholder="邀请人的用户名/认证手机号" />
        </li>
      </ul>
      <div class="reg-pwdtips">登录密码为6位以上数字和字母的组合，区分大小写 </div>
      <div class="reg-pwdtips">用户名4-16字符的字母或数字或下划线组合 </div>
      <input name="agreement" class="ui-radio" id="agreement" type="radio" value="1"/>
                        <label for="agreement" class="ui-radio-label">同意</label><a href="javascript:getUserXyInfo();">《${app_name_min}网用户协议》</a> 
      <div class="deal-btn" style="padding-top:.2rem;">
        <input type="button"  id="btn_submit" class="j_submit" name="doregister" value="注册">
      </div>
      <c:url var="url" value="/m/MIndexLogin.do"/>
      <div class="deal-btn" style="padding-top:.2rem;">
        <input type="button" id="btn_login" class="j_submit" name="doregister" value="已注册请点击登录"  style="background: #19be6b!important;" onclick="goUrl('${url}')" />
      </div>
<!--       <div class="deal-btn">  -->
<%--   <a href="javascript:void(0);" onclick="getUserXyInfo();">《${app_name_min}网用户协议》</a> </div> --%>
      <%-- <div class="login-find">
      <a href="${url}">已有账号，直接登录</a></div> --%>
    </html-el:form>
  </div>
</div>

<div class="pop-warp sms-captcha" style="display:none;" id="showSelectCode">
  <div class="pop-innr"> <i class="close" onClick="$(this).parents('.sms-captcha').hide();"></i>
    <div class="title">请输入图片验证码</div>
     <div class="box pop-vali">
      <input type="number" name="verificationCode" id="veri_code" class="vali-input" placeholder="请输入图片验证码">
     </div>
     <div class="box pop-vali">
       <img src="${ctx}/images/VerificationCode.jpg" alt="验证码" id="veri_img" onClick="updateVerCode();"/>
       <a class="switch" onclick="updateVerCode();"><span>换一张</span></a>
      </div>
    <div class="tips" style="display:none;">请输入图片验证码</div>
    <div class="box pop-bottom"> <a href="javascript:sendMessage();" class="box-flex next-step">下一步</a> </div>
  </div>
</div>

<div class="pop-warp sms-captcha" style="display:none;" id="showExistUser">
  <div class="pop-innr"> <i class="close" onclick="closeExistUser(0);"></i>
   <div class="title">是否绑定已存在用户</div>
   <div style="text-align:center;padding-bottom:0.1rem;"><span id="showUserName"></span></div>
    <div class="box pop-vali">
      <input type="password" name="hasUserPassword" id="hasUserPassword" class="box-flex vali-input" placeholder="请输入用户密码进行绑定" />
    </div>
    <div class="box pop-bottom" style="padding-bottom:0.1rem;"> <a href="javascript:bindExistUser();" class="box-flex next-step">确定绑定</a> </div>
    <div class="box pop-bottom" style="padding-top:0;"> <a href="javascript:closeExistUser(1);" class="box-flex next-step">取消绑定</a> </div>
  </div>
</div>
<div class="mui-cover" id="s-poor-rebate" style="overflow: scroll;width: 100%;height: 550px;top: 25%;">
	<h1 style="text-align: center;font-size: 25px;margin-top: 12px;" id="newsTitle"></h1>
	<div class="cover-content" id="newsContent" style="margin-bottom: 193px;"></div>
	<a class="sback" onClick="hideWrapper()" style="top: -45px;right: 0.3rem;font-size: 1.45rem;"><i class="aui-icon aui-icon-close" ></i></a>
</div>
<div class="cover-decision" id="s-decision-wrapper-cover" onClick="hideWrapper()" style="display: none;"></div>
<jsp:include page="../_footer.jsp" flush="true" />
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/scripts/jquery.timers.js" ></script>
	<script type="text/javascript"src="${ctx}/scripts/swiper/swiper.min.js"></script>
	<script type="text/javascript"src="${ctx}/scripts/jquery.infinitescroll.js"></script>
	<script type="text/javascript"src="${ctx}/scripts/cart/mCartCommInfo.js"></script>
<script type="text/javascript">//<![CDATA[
var f_r = $(".register_form").get(0);   
var hasUserFlag = 0;
var agreeFlag = false;
$(document).ready(function(){
	
	$("#password_show").change(function(){
		var thisVal = $(this).val();
			$("#password_hide").val(thisVal);
	 });
	 $("#password_hide").change(function(){
		var thisVal = $(this).val();
		$("#password_show").val(thisVal);
	 });
	 
	 $("#changePassShowType").click(function(){
		if($(this).hasClass("pwd-mode-open")){
			$(this).removeClass("pwd-mode-open");
			$("#password_hide").show();
			$("#password_show").hide();
		}else{
			$(this).addClass("pwd-mode-open");
			$("#password_show").show();
			$("#password_hide").hide();
		}
	 });
	 
	 
	// 提交
	$("#btn_submit").click(function(){
		var agreement = $("#agreement").attr("checked");
		if(agreement != 'checked'){
			mui.toast('请阅读并同意九个挑夫网用户协议！');
			return false;
		}
		if(registerSubValNoUserName() && check_password_regx("password_hide")){
		
		var ym_id_value = $("#ymid").val();
		if(null != ym_id_value){
			$.ajax({
				type: "POST",
				url: "${ctx}/Register.do",
				data: "method=validateYmId&ymid=" + ym_id_value,
				dataType: "json",
				error: function(request, settings) {},
				success: function(oper) {
					if (oper == 1) {
						mui.toast('未查询到此推荐人，请确认填写是否正确');
						return false;
					}else if (oper == 2) {
						mui.toast('该用户不能推荐人');
						return false;
					}else{
						Common.loading();
						$("#btn_submit").attr("disabled", "true").addClass("j_submit_disabled");
						f_r.submit();
					}
				}
			});
		}else{
			Common.loading();
			$("#btn_submit").attr("disabled", "true").addClass("j_submit_disabled");
			f_r.submit();
		}
	  }
	});
});

function submitForm(){
    var t = $.trim($("#re_mobile").val()),
    e = $.trim($("#re_mobile_code").val()),
    i = $.trim($("#pwd").val()),
    o = $.trim($("#pwd2").val());
}


	 var state = 0;                                         
	//验证手机号
	function validMobile(mobile){
		if ("" != mobile && $("#mobile").attr("readonly") != "readonly") {
			var reg = /^((\(\d{2,3}\))|(\d{3}\-))?((1[3-9]\d{9}))$/;
			if (mobile.match(reg)) {
				$.ajax({
					type: "POST" , 
					url: "${ctx}/Register.do" , 
					data:"method=validateMobileForWeiXin&mobile=" + mobile + "&t=" + new Date(),
					dataType: "json", 
			        async: true, 
			        error: function (request, settings) {alert(" 数据加载请求失败！ ");	$("#btn_submit").attr("disabled", "true").addClass("j_submit_disabled"); }, 
			        success: function (data) {
						if (data.ret == 0) {
							mui.toast('参数丢失！');
							state = "-1";
							return false;
						} else if (data.ret == 1) {
							state = "0";
							$("#showSelectCode").show();
						} else if (data.ret == 2) {
							mui.toast('该手机号码已被注册！');
							var appid_qq = $("#appid_qq").val();
							var appid_weibo = $("#appid_weibo").val();
							if("" != appid_qq || "" != appid_weibo){
								$("#user_id").val(data.user_id);
								$("#showUserName").text("手机号：" + mobile);
								$("#showExistUser").show();
							}
							state = "-2";
							return false;
						}
			        }
				});
			} else {
				mui.toast('手机格式不正确！');
				state = "-3";
				return false;
			}
		}
	}

	function getMessage(mobile){
		if ("" == mobile) {
			mui.toast('请先输入手机号！');
			$("#mobile").focus();
			return false;
		}
		validMobile(mobile);
	}
	
	function sendMessage(){
		if("" == $("#veri_code").val()){
			mui.toast('请先验证码！');
			$("#veri_code").focus();
			return false;
		}
		var mobile = $("#mobile").val();
		if ("" == mobile) {
			mui.toast('请先输入手机号！');
			$("#mobile").focus();
			return false;
		}
		i = 120; 
		$("#mobile").attr("readonly", "true");
		$.post("${ctx}/Register.do?method=sendMobileMessage",{"mobile":mobile,"veri_code":$("#veri_code").val()},function(datas){
				if (datas.ret == 1) {
					$("#showSelectCode").hide();
	        		$("#sendMobileCode").removeAttr("onclick");
					clock();
				}else{
					mui.toast(datas.msg);
				}
		});
	}

	function clock() {
		i--;
		$("#sendMobileCode").val(i + "秒后获取验证码"); 
		if(i > 0) {
			setTimeout("clock();", 1000);
		} else {
			state = "0"; 
			$("#sendMobileCode").val("获取短信验证码");
			$("#sendMobileCode").attr("onclick","getMessage($('#mobile').val());");
			$("#mobile").removeAttr("readonly");
		}
	}
	
	function bindExistUser(){
		
		var hasUserPassword = $("#hasUserPassword").val();
		if ("" == hasUserPassword) {
			mui.toast('请先用户密码！');
			$("#hasUserPassword").focus();
			return false;
		}
		var userName = $("#user_name").val();
		
		$.ajax({
			type: "POST",
			url: "${ctx}/weixin/WeixinLogin.do",
			data: "method=bindExistUser&hasUserPassword="+ hasUserPassword +"&" + $(".register_form").serialize(),
			dataType: "json",
			error: function(request, settings) {},
			success: function(data) {
				mui.toast(data.msg);
				if(data.ret == 0){
				}else{
					var url = "${ctx}/m/MMyHome.do";
					goUrl(url);
				}
			}
		});
	}

	function closeExistUser(flag){
		$('#showExistUser').hide();
		$('#user_name').focus();
		if(flag == 1){
			hasUserFlag = flag;
		}
	}
	function getUserXyInfo(){
		$.ajax({
			type: "POST",
			url: "${ctx}/Register.do?method=getUserXyInfoAjax",
			dataType: "json",
			success: function(data) {
				$("#newsContent").html(data.datas.newsInfo.map.content);
				$("#newsTitle").text(data.datas.newsInfo.title);
			}
		})
		$("#s-poor-rebate").show().addClass("show");
	}
	function hideWrapper() {
		$("#s-decision-wrapper").removeClass("show");
		$("#s-poor-rebate").removeClass("show");
		$("#s-decision-wrapper-cover").hide();
	}
//]]></script>
</body>
</html>
