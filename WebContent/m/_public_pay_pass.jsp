<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<link href="${ctx}/styles/pay_pass/css/pass.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${ctx}/commons/font-awesome/css/font-awesome.min.css"  />
<div class="ftc_wzsf">
  <div class="srzfmm_box">
  
   <div class="qsrzfmm_bt clear_wl"> 
    <img src="${ctx}/styles/pay_pass/css/images/xx_03.jpg" class="tx close fl" /> 
   <span class="fl">请输入支付密码</span> </div>
   
   <div class="zfmmxx_shop">
      <div class="mz">${app_name}</div>
      <div class="wxzf_price"><span id="pay_tip_price"><fmt:formatNumber value="${order_money}" pattern="0.00"/></span></div>
    </div>
   
    <ul class="mm_box">
      <li></li>
      <li></li>
      <li></li>
      <li></li>
      <li></li>
      <li></li>
    </ul>
  </div>
  <div class="numb_box">
    <div class="xiaq_tb"> <img src="${ctx}/styles/pay_pass/css/images/jftc_14.jpg" height="10"> </div>
    <ul class="nub_ggg">
      <li><a>1</a></li>
      <li><a class="zj_x">2</a></li>
      <li><a>3</a></li>
      <li><a>4</a></li>
      <li><a class="zj_x">5</a></li>
      <li><a>6</a></li>
      <li><a>7</a></li>
      <li><a class="zj_x">8</a></li>
      <li><a>9</a></li>
      <li><span></span></li>
      <li><a class="zj_x">0</a></li>
      <li><span class="del" > <img src="${ctx}/styles/pay_pass/css/images/jftc_18.jpg"></span></li>
    </ul>
  </div>
  <div class="hbbj"></div>
</div>
<script type="text/javascript" src="${ctx}/m/scripts/fastclick.js"></script>
<script type="text/javascript">//<![CDATA[
var m = new Array(6);
var i = 0;          
$(document).ready(function(){
	
	FastClick && FastClick.attach(document.body);
	
    //关闭浮动
    $(".close").click(function(){
	  $(".ftc_wzsf").hide();
	  initPass();
	});
	//数字显示隐藏
	$(".xiaq_tb").click(function(){
	  $(".numb_box").slideUp(500);
	});
	$(".mm_box").click(function(){
	  $(".numb_box").slideDown(500);
	});
	
	//----
	$(".nub_ggg li a").click(function(){
		var value = $(this).text();
		m[i] = value;
		i++;
		if(i<6){
			$(".mm_box li").eq(i-1).addClass("mmdd");
		}else if(i = 6){
				$(".mm_box li").eq(i-1).addClass("mmdd"); 
				setTimeout(function(){
				  $("#pay_password").val(m.join().replace(/,/g,''));
				  submitThisForm();
				});
		 } else{
			   mui.toast("请点击6位数密码！",2000);
			   $(".mm_box li").removeClass("mmdd");
            	m = new Array(6);
            	i=0;
            	return false;
		 }
	});
	
	$(".nub_ggg li .del").click(function(){
		if(i>0){
		 i--
		 $(".mm_box li").eq(i).removeClass("mmdd");
		 i==0;
		}
	});
});

function initPass(){
	m = new Array(6);
	i = 0;
	$(".mm_box li").each(function(){
		$(this).removeClass("mmdd");
	});
	$("#pay_password").val("");
}

//]]></script>
