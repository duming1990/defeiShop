<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>${app_name}</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Description" content="${app_name}后触屏版找回密码" />
<meta name="Keywords" content="${app_name}后触屏版找回密码" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"/>
<meta name="apple-mobile-web-app-capable" content="yes"/>
<meta name="apple-mobile-web-app-status-bar-style" content="black"/>
<meta name="format-detection" content="telephone=no"/>
<jsp:include page="../_public_in_head.jsp" flush="true" />
</head>
<body id="body">
<jsp:include page="../_header.jsp" flush="true" />
<div class="content bg-white">
		<div class="entry" style="display:none;"></div>
		<!--article-->
		<div class="login-box">
		 <html-el:form action="/MGetPwBack.do" method="post" styleClass="form_getPwBack">
			<html-el:hidden property="method" value="stepOne" />
       		<html-el:hidden property="returnurl" />
			<ul class="codebox">
				<li class="name">
					<input id="mobile" type="tel" name="mobile" class="codebox-input zhaoinput" placeholder="请输入手机号" />
				</li>
				<li class="box regcode">
					<input type="number" name="veri_code" id="veri_code" class="box-flex codebox-input" placeholder="请输入图片验证码" />
					<span class="password-code">
						<img src="${ctx}/images/VerificationCode.jpg" style="width:1.4rem;" alt="验证码" id="veri_img" />
						<a style="cursor:pointer;color:#7B7B79; line-height:.5rem" onclick="updateVerCode();">换一张</a>
					</span>
				</li>				
			</ul>
			<div class="deal-btn">
				<input type="button" class="j_submit" id="btn_submit" name="sub" value="获取短信验证码" />
			</div>
		  </html-el:form>
		</div>
		<div class="password-serve"><span>找回密码如遇到问题，请拨打${app_name_min}客服<br>客服热线:${app_tel}</span></div>
</div>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	
	var f = document.forms[0];
	
	 $("#btn_submit").click(function(){
		var mobile = $("#mobile").val();
		var veri_code = $("#veri_code").val();
		if(null == mobile || '' == mobile){
			mui.toast("请输入手机号",1000);
			return false;
		}else if(null == veri_code || '' == veri_code){
			mui.toast("请输入验证码",1000);
			return false;
		}
		var reg = /^((\(\d{2,3}\))|(\d{3}\-))?((1[3-9]\d{9}))$/;
		if (!mobile.match(reg)) {
			mui.toast('手机格式不正确！',1000);
			return false;
		}
		$("#mobile").attr("readonly", "true");
		$.post("${ctx}/Register.do?method=sendMobileMessage",{"mobile":mobile,"veri_code":$("#veri_code").val()},function(datas){
			if (datas.ret == 1) {
				$("#btn_submit").attr("disabled", "true").addClass("j_submit_disabled");
				f.submit();
			}else{
				mui.toast(datas.msg);
			}
		});
		
	});
});
//]]></script>
</body>
</html>
