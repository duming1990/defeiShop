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
  <form action="/m/MMySecurityCenter" enctype="multipart/form-data" method="post" class="ajaxForm">
  <c:set var="is_add" value="true"/>
    <c:set var="readonly" value="false"/>
    <c:if test="${not empty af.map.bank_account}">
      <c:set var="readonly" value="true"/>
      <c:set var="is_add" value="false"/>
    </c:if>
    <div class="set-site">
      <ul>
            <li class="select">
          <select name="province" id="province" >
            <option value="">--开户地省份--</option>
          </select>
        </li>
        <li class="select">
          <select name="city" id="city">
            <option value="">--开户地城市--</option>
          </select>
          <input type="hidden" name="bank_pindex" id="bank_pindex" value="${af.map.bank_pindex}">
        </li>
        <li> <span style="width: 30%" class="grey-name">开户银行：</span>
          <select style="width: 70%" name="bank_name" id="bank_name" value="${af.map.bank_name}">
          </select>        </li>
        <li> <span style="width: 30%" class="grey-name">开户支银行：</span>
          <input style="width:70%" type="text" id="bank_branch_name" name="bank_branch_name" value="${fn:escapeXml(af.map.bank_branch_name)}" placeholder="XX分行XX支行" class="webinput" title="支行" autocomplete="off" style="width: 200px;" />   </li>
        <li> <span style="width: 30%" class="grey-name">开户账号：</span>
        <c:set var="display_BankAccount" value="" />
        <c:set var="display_BankAccount_span" value="none" />
        <c:if test="${not is_add}">
          <c:set var="display_BankAccount" value="none" />
          <c:set var="display_BankAccount_span" value="" />
        </c:if>
          <input style="width: 70%;display:${display_BankAccount}" name="bank_account" id="bank_account" onkeyup="formatBankNo(this)" onkeydown="formatBankNo(this)" value="${af.map.bank_account}" type="text" autocomplete="off" maxlength="38" class="buy_input" />
        <span id="bank_account_span" style="display:${display_BankAccount_span}">${af.map.encryptBankAccount}</span>
        </li>
        <li> <span style="width:30%" class="grey-name">开户名：</span>
          <input style="width:70%" name="bank_account_name" id="bank_account_name" type="text" autocomplete="off" maxlength="38" class="buy_input" value="${ af.map.bank_account_name}"/>
        </li>
        <li class="hide_tr">
	      <div class="box pop-vali">
	      <input style="width:40%;" type="number" name="verificationCode" id="verificationCode" class="box-flex vali-input" placeholder="请输入图片验证码" />
	      <img src="${ctx}/images/VerificationCode.jpg" alt="验证码" id="veri_img" /> <a class="switch" onClick="updateVerCode();"><span>换一张</span></a> </div>
	      <div class="tips" style="display:none;">请输入图片验证码</div>
        </li>
        <li class="hide_tr"> 
         <span style="width: 20%" class="grey-name">手机号：</span>
         <input style="width: 30%" name="mobile" id="mobile" type="text" autocomplete="off" maxlength="38" class="buy_input" value="${af.map.mobile}"/>
         <input style="width: 40%; margin: .12rem .2rem;" type="button" class="btnside zhaosubmit" id="sendMobileCode" onclick="sendSms()" value="获取验证码" />
        </li>
         <li class="hide_tr"> 
          <span style="width: 30%" class="grey-name">动态码：</span>
          <input style="width: 70%" name="ver_code" id="ver_code" type="text" autocomplete="off" maxlength="38" class="buy_input" />
        </li>
      </ul>
    </div>
    <div class="box submit-btn"> <a class="com-btn" id="btn_edit" onclick="edit()">修改</a> </div>
    <div class="box submit-btn"> <a class="com-btn" id="btn_submit">保存</a> </div>
  </form>
</div>
<jsp:include page="../_footer.jsp" flush="true" />
<c:set var="bank_account" value="${af.map.bank_account}"/>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.m.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript" src="${ctx}/scripts/bank/bank.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/area.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/citySelect.js"></script>
<script type="text/javascript">//<![CDATA[
function edit(){
	$(".hide_tr").show();
	$("#btn_submit").show();
	$("#btn_edit").hide();
	$(".hide_element").removeAttr("readonly");
	$('#bank_account').show();$('#bank_account_span').hide();
	$("#mobile").attr("dataType", "Mobile").attr("msg", "请正确填写手机号码");
	$("#ver_code").attr("dataType", "Require").attr("msg", "请填写手机动态码");
	$("#verificationCode").attr("dataType", "Require").attr("msg", "请填写验证码！");
	$("#captcha").attr("datatype","Require").attr("msg","请填写验证码！");
}
function init(){
	$(".hide_tr").hide();
	$("#btn_submit").hide();
	$("#btn_edit").show();
	$(".hide_element").attr("readonly","true");
}
$(document).ready(function(){
	init();
	<c:if test="${is_add}">
	$(".hide_tr").hide();
	$("#btn_submit").show();
	$("#btn_edit").hide();
	$(".hide_element").removeAttr("readonly");
	</c:if>
	
	var banks = datas.banks;
	for (var i = 0, len = banks.length; i < len; i++) {
		var name = banks[i]["name"];
		$('#bank_name').append('<option value="' + name + '">' + name + '</option>');
	}
	
	$('#bank_name').val("${af.map.bank_name}");

	$("#province").citySelect({
        data:getAreaDic(),
        province:"${af.map.province}",
        city:"${af.map.city}",
        province_required:true,
        city_required:true,
        callback:function(selectValue,selectText){
        	if(null != selectValue && "" != selectValue){
        		var p_indexs = selectValue.split(",");
        		if(null != p_indexs && p_indexs.length > 0){
        			$("#bank_pindex").val(p_indexs[p_indexs.length - 1]);
        		}
        	}
        	
        }
    });
	
	
	$("#veri_img").attr("src", "${ctx}/images/VerificationCode.jpg?t=" + new Date().getTime());$("#veri_img").attr("src", "${ctx}/images/VerificationCode.jpg?t=" + new Date().getTime());
	
	$("#bank_name").attr("datatype","Require").attr("msg","请选择开户银行！");
	$("#bank_account").attr("datatype","Require").attr("msg","请填写开户账号！");
	$("#bank_account_name").attr("datatype","Chinese").attr("msg","请填写开户名,且只能是中文");
	
	var $form  = $(".ajaxForm");
	var f = $form.get(0);
	$("#btn_submit").click(function(){

		if(Validator.Validate(f, 1)){
			Common.loading();
			$("#btn_submit").attr("disabled", "true");
			window.setTimeout(function () { 
				$.ajax({
					type: "POST",
					url: "MMySecurityCenter.do?method=modifyBank",
					data: $form.serialize(),
					dataType: "json",
					error: function(request, settings) {mui.toast("数据请求失败");},
					success: function(data) {
						Common.hide();
						$("#btn_submit").removeAttr("disabled");
						if(data.ret == "0"){
							mui.toast(data.msg);
						} else {
							mui.toast(data.msg);
							init();
							var bank = $("#bank_account").val();
							if (bank.length >= 16) {
								var encryptIdCard = bank.substring(0,4)+"***********"+bank.substring(15);
								$("#bank_account_span").text(encryptIdCard);
							}
							$('#bank_account').hide();
							$('#bank_account_span').show();
						}
					}
				});	
	    	}, 1000);
		} 
		
	});
	
	
});
function formatBankNo (BankNo){
    if (BankNo.value == "") return;
    var account = new String (BankNo.value);
    account = account.substring(0,23); /*帐号的总数, 包括空格在内 */
    if (account.match (".[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{7}") == null){
        /* 对照格式 */
        if (account.match (".[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{7}|" + ".[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{7}|" +
        ".[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{7}|" + ".[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{7}") == null){
            var accountNumeric = accountChar = "", i;
            for (i=0;i<account.length;i++){
                accountChar = account.substr (i,1);
                if (!isNaN (accountChar) && (accountChar != " ")) accountNumeric = accountNumeric + accountChar;
            }
            account = "";
            for (i=0;i<accountNumeric.length;i++){    /* 可将以下空格改为-,效果也不错 */
                if (i == 4) account = account + " "; /* 帐号第四位数后加空格 */
                if (i == 8) account = account + " "; /* 帐号第八位数后加空格 */
                if (i == 12) account = account + " ";/* 帐号第十二位后数后加空格 */
                if (i == 16) account = account + " ";/* 帐号第16位后数后加空格 */
                account = account + accountNumeric.substr (i,1);
            }
        }
    }
    else
    {
        account = " " + account.substring (1,5) + " " + account.substring (6,10) + " " + account.substring (14,18) + "-" + account.substring(18,25);
    }
    if (account != BankNo.value) BankNo.value = account;
}

var state = 0;        

function sendSms(){
	if("" == $("#verificationCode").val()){
		mui.toast("请先验证码！");
		$("#veri_code").focus();
		return false;
	}
	var mobile  = $("#mobile").val();
	if("" == mobile){
		mui.toast("手机号为空，请先绑定手机号！");
		$("#veri_code").focus();
		return false;
	}
	var isBdOrSet = 0;
	<c:if test="${is_add}">
		isBdOrSet = 1;
	</c:if>
	
	if (state == 0) {
		state = "1"; 
		i = 120; 
		$("#mobile").attr("readonly", "true");
		$.ajax({
			type: "POST" , 
			url: "${ctx}/Register.do" , 
			data:"method=sendMobileMessage&mobile=" + mobile + "&veri_code=" + $("#verificationCode").val() + "&type=3&bdType=2&isBdOrSet=" + isBdOrSet + "&t=" + new Date(),
			dataType: "json" , 
	        async: true, 
	        error: function (request, settings) {mui.toast(" 数据加载请求失败！ ");},
	        success: function (result) {
	        	if (result.ret == 1) {
	        		$("#sendMobileCode").removeAttr("onclick");
					clock();
				}else{
					alert("发送短信失败，请联系管理员！");
				}
	        } 
		});
	}
}

function clock() {
	i--;
	$("#sendMobileCode").val(i + "秒后获取验证码");
	if(i > 0) {
		setTimeout("clock();", 1000);
	} else {
		state = "0"; 
		$("#sendMobileCode").val("获取短信验证码");
		$("#sendMobileCode").attr("onclick","sendSms();");
		$("#mobile").removeAttr("readonly");
	}
}

function updateVerCode(){
	$("#veri_img").attr("src", "${ctx}/images/VerificationCode.jpg?t=" + new Date().getTime());
}


//]]></script>
</body>
</html>
