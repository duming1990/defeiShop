<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="mainbox mine">
  <html-el:form action="/customer/MySecurityCenter.do" styleClass="ajaxForm">
  
    <html-el:hidden property="method" value="modifyBank" />
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="par_id" />
    <c:set var="is_add" value="true"/>
    <c:set var="readonly" value="false"/>
    <c:if test="${not empty af.map.bank_account}">
      <c:set var="readonly" value="true"/>
      <c:set var="is_add" value="false"/>
    </c:if>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
      <tr>
        <th colspan="4">银行账号</th>
      </tr>
    <tr>
      <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>开户地：</td>
      <td id="city_div" colspan="3"><html-el:select property="province" styleId="province" styleClass="pi_prov" style="width:120px;" disabled="disabled">
          <html-el:option value="">请选择省...</html-el:option>
        </html-el:select>
        &nbsp;
        <html-el:select property="city" styleId="city" styleClass="pi_city" style="width:120px;">
          <html-el:option value="">请选择市...</html-el:option>
        </html-el:select>
        <html-el:hidden property="bank_pindex" styleId="bank_pindex" /></td>
    </tr>
      <tr>
        <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>开户银行：</td>
        <td colspan="3">
        <html-el:select property="bank_name" styleId="bank_name"></html-el:select> - 
        <input type="text" id="bank_branch_name" name="bank_branch_name" value="${fn:escapeXml(af.map.bank_branch_name)}" placeholder="XX分行XX支行" class="webinput" title="支行" autocomplete="off" style="width: 200px;" />
        </td>
      </tr>
      <tr>
        <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>开户账号：</td>
        <td colspan="3">
        <c:set var="display_BankAccount" value="" />
        <c:set var="display_BankAccount_span" value="none" />
        <c:if test="${not is_add}">
          <c:set var="display_BankAccount" value="none" />
          <c:set var="display_BankAccount_span" value="" />
        </c:if>
        <html-el:text property="bank_account" onkeyup="formatBankNo(this)" onkeydown="formatBankNo(this)"  maxlength="50" styleClass="webinput" styleId="bank_account" style="width:200px;display:${display_BankAccount}" value=" ${af.map.bank_account}"/>
        <span id="bank_account_span" style="display:${display_BankAccount_span}">${af.map.encryptBankAccount}</span>
        </td>
      </tr>
      <tr>
        <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>开户名：</td>
        <td colspan="3"><html-el:text property="bank_account_name" maxlength="50" styleClass="webinput hide_element" styleId="bank_account_name" style="width:200px" readonly="${readonly}"/>
        </td>
      </tr>
      <tr class="hide_tr">
        <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>验证码：</td>
        <td><html-el:text property="verificationCode" styleId="verificationCode" maxlength="4" style="width:100px" styleClass="webinput" />
          &nbsp;<img height="22" width="72" class="signup-captcha-img" id="veri_img" style="vertical-align: bottom;"/> <a tabindex="-1" class="captcha-refresh inline-link" href="javascript:updateVerCode();">看不清楚？换一张</a> </td>
      </tr>
      <tr class="hide_tr">
        <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>手机号：</td>
        <td><html-el:text property="mobile" styleId="mobile" maxlength="11" style="width:100px" styleClass="webinput" readonly="true" />
          &nbsp;
          <button class="bgButtonFontAwesome" type="button" onclick="sendSms()" id="sendMobileCode"><i class="fa fa-send"></i>发送验证码</button></td>
      </tr>
      <tr class="hide_tr">
        <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>手机动态码：</td>
        <td><html-el:text property="ver_code" styleId="ver_code" maxlength="6" style="width:100px" styleClass="webinput" /></td>
      </tr>
      <tr>
        <td width="14%" nowrap="nowrap">&nbsp;</td>
        <td colspan="3" style="text-align:left">
          <button class="bgButtonFontAwesome" type="button" id="btn_edit" onclick="edit()"><i class="fa fa-edit"></i>修 改</button>
          <button class="bgButtonFontAwesome" type="button" id="btn_submit"><i class="fa fa-save"></i>保 存</button></td>
      </tr>
    </table>
  </html-el:form>
</div>
<c:set var="bank_account" value="${af.map.bank_account }"/>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
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

	$("#veri_img").attr("src", "${ctx}/images/VerificationCode.jpg?t=" + new Date().getTime());$("#veri_img").attr("src", "${ctx}/images/VerificationCode.jpg?t=" + new Date().getTime());
	
	$("#bank_name").attr("datatype","Require").attr("msg","请选择开户银行！");
	$("#bank_branch_name").attr("datatype","Require").attr("msg","请填写支行名称！");
	$("#bank_account").attr("datatype","Require").attr("msg","请填写开户账号！");
	$("#bank_account_name").attr("datatype","Chinese").attr("msg","请填写开户名,且只能是中文");
	
	
	$("#city_div").citySelect({
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
	var bank_branch_name=$("#bank_branch_name").val();
	var $form  = $(".ajaxForm");
	var f = $form.get(0);
	$("#btn_submit").click(function(){

		if(Validator.Validate(f, 3)){
			$.jBox.tip("数据提交中...", 'loading');
			$("#btn_submit").attr("disabled", "true");
			window.setTimeout(function () { 
				$.ajax({
					type: "POST",
					url: "MySecurityCenter.do?method=modifyBank",
					data: $form.serialize(),
					dataType: "json",
					error: function(request, settings) {$.jBox.tip("数据请求失败", "error");},
					success: function(data) {
						$("#btn_submit").removeAttr("disabled");
						if(data.ret == "0"){
							$.jBox.tip(data.msg, "info");
						} else {
							$.jBox.tip(data.msg, "success");
							init();
							var bank = $("#bank_account").val();
							if (bank.length >= 16) {
								var encryptIdCard = bank.substring(0,4)+"***********"+bank.substring(15);
								$("#bank_account_span").text(encryptIdCard);
							}else{
								$("#bank_account_span").text(bank);
							}
							$('#bank_account').hide();
							$('#bank_account_span').show();
							alert("保存成功！");
							returnTo();
						}
					}
				});	
	    	}, 1000);
		
		} 
		
	});
	
	
});
function returnTo(){
	var api = frameElement.api, W = api.opener;
	W.refreshPage();
	api.close();
	
}
function formatBankNo (BankNo){
    if (BankNo.value == "") return;
    var account = new String (BankNo.value);
    //account = account.substring(0,23); /*帐号的总数, 包括空格在内 */
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
        account = " " + account.substring (1,5) + " " + account.substring (6,10) + " " + account.substring (14,18) + "-" + account.substring(18);
    }
    if (account != BankNo.value) BankNo.value = account;
}

var state = 0;        

function sendSms(){
	var mobile = $("#mobile").val();
	if ("" == mobile) {
		alert("手机号为空，请先绑定手机号！");
		$("#mobile").focus();
		return false;
	}
	if("" == $("#verificationCode").val()){
		alert("请先验证码！");
		$("#veri_code").focus();
		return false;
	}
	
	if (state == 0) {
		state = "1"; 
		i = 120; 
		$("#mobile").attr("readonly", "true");
		$.ajax({
			type: "POST" , 
			url: "${ctx}/CsAjax.do" , 
			data:"method=sendMobileMessage&mobile=" + mobile + "&veri_code=" + $("#verificationCode").val() + "&type=3&bdType=2" + "&t=" + new Date(),
			dataType: "json" , 
	        async: true, 
	        error: function (request, settings) {alert(" 数据加载请求失败！ "); },
	        success: function (result) {
	        	state = "0"; 
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
	$("#sendMobileCode").html("<i class='fa fa-send'></i>" + i + "秒后获取验证码</button>"); 
	if(i > 0) {
		setTimeout("clock();", 1000);
	} else {
		state = "0"; 
		$("#sendMobileCode").html("获取短信验证码");
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
