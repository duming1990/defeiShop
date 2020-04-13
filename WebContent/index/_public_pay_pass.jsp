<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<link href="${ctx}/styles/pay_pass/css/pass_pc.css" rel="stylesheet" type="text/css" />
<div id="payPassword_container" class="alieditContainer clearfix" data-busy="0">
	<label for="i_payPassword" class="i-block">支付密码：</label>
	<div class="i-block" data-error="i_error">
		<div class="i-block six-password">
			<input class="i-text sixDigitPassword" id="pay_password" type="password" autocomplete="off" name="pay_password"  maxlength="6" />
			<div tabindex="0" class="sixDigitPassword-box" style="width: 180px;">
				<i style="width: 29px;border-left: none;"><b></b></i>
				<i style="width: 29px;"><b></b></i>
				<i style="width: 29px;"><b></b></i>
				<i style="width: 29px;"><b></b></i>
				<i style="width: 29px;"><b></b></i>
				<i style="width: 29px;"><b></b></i>
				<span style="width: 29px; left:0px; visibility: hidden;" id="cardwrap"></span>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	var payPassword = $("#payPassword_container"),
    _this = payPassword.find('i'),	
	k=0,j=0,
	password = '' ,
	_cardwrap = $('#cardwrap');
	//点击隐藏的input密码框,在6个显示的密码框的第一个框显示光标
	payPassword.on('focus',"input[name='pay_password']",function(){
	
		var _this = payPassword.find('i');
		if(payPassword.attr('data-busy') === '0'){ 
		//在第一个密码框中添加光标样式
		   _this.eq(k).addClass("active");
		   _cardwrap.css('visibility','visible');
		   payPassword.attr('data-busy','1');
		}
		
	});	
	//change时去除输入框的高亮，用户再次输入密码时需再次点击
	payPassword.on('change',"input[name='pay_password']",function(){
		_cardwrap.css('visibility','hidden');
		_this.eq(k).removeClass("active");
		payPassword.attr('data-busy','0');
	}).on('blur',"input[name='pay_password']",function(){
		
		_cardwrap.css('visibility','hidden');
		_this.eq(k).removeClass("active");					
		payPassword.attr('data-busy','0');
		
	});
	
	//使用keyup事件，绑定键盘上的数字按键和backspace按键
	payPassword.on('keyup',"input[name='pay_password']",function(e){
	
	var  e = (e) ? e : window.event;
	var _val = this.value;
	//键盘上的数字键按下才可以输入
	if(e.keyCode == 8 || (e.keyCode >= 48 && e.keyCode <= 57) || (e.keyCode >= 96 && e.keyCode <= 105)){
			k = this.value.length;//输入框里面的密码长度
			l = _this.size();//6
			
			for(;l--;){
			
			//输入到第几个密码框，第几个密码框就显示高亮和光标（在输入框内有2个数字密码，第三个密码框要显示高亮和光标，之前的显示黑点后面的显示空白，输入和删除都一样）
				if(l === k){
					_this.eq(l).addClass("active");
					_this.eq(l).find('b').css('visibility','hidden');
					
				}else{
					_this.eq(l).removeClass("active");
					_this.eq(l).find('b').css('visibility', l < k ? 'visible' : 'hidden');
					
				}				
			if(k === 6){
				j = 5;
			}else{
				j = k;
			}
			 $('#cardwrap').css('left',j*30+'px');
			}
			$("#pay_password").val(_val);	
		}else{
		//输入其他字符，直接清空
			var _val = this.value;
			this.value = _val.replace(/\D/g,'');
		}
	});	
 
});

//]]></script>
