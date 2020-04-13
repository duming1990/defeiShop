<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>九个挑夫 ${fn:escapeXml(commInfo.comm_name)}</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Expires" content="-1">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Pragma" content="no-cache">
<jsp:include page="../_public_in_head_new.jsp" flush="true" />
<link rel="stylesheet" type="text/css" href="${ctx}/m/styles/css/my/comm-details.css?v=20180320" />
<link rel="stylesheet" type="text/css" href="${ctx}/m/styles/themes/css/index.css" />
<style type="text/css">
.aui-slide-box .aui-slide-list {
	margin: 10px;
}

.aui-slide-box .aui-slide-item-list .aui-slide-item-item {
	margin-right: 0.6rem !important;
}

.aui-real-price {
	padding: 5px 10px 10px 10px;
}

.fixed-contact_pic{position: fixed;z-index: 1002;max-width:640px;width:100%;height:100%; top: 10px}
.fixed-contact_pic .fixed-wechat_png{position: relative;text-align: center;top: 50%;margin-top: -50%;}
.fixed-contact_pic .fixed-wechat_png .close{display: block;width: 20px;height: 20px;position: absolute;background: transparent;top: 5px;right: 50%;margin-right: -135px;}
#contact_pic_mask{background-color: rgba(0,0,0,0.5);position: fixed;width: 100%;height: 100%;max-width:640px; z-index: 1001; top: 0}
.top_back_container .title-con_link{height:44px;line-height:44px;position: relative;}
.fixed-contact_pic .fixed-wechat_png img{
    width: 80%;
}
/* @media screen and (max-width:330px) { */
/*   .fixed-contact_pic .fixed-wechat_png img{ */
/*       height: 350px; */
/*       width: 220px; */
/*   } */
/*   .fixed-contact_pic .fixed-wechat_png { */
/*       margin-top:-220px; */
/*   } */
/*   .fixed-contact_pic .fixed-wechat_png .close{ */
/*       margin-right: -108px; */
/*       top: 3px; */
/*   } */
/* } */
.erweima{
    position: absolute;
    width: 40%!important;
    left: 30%!important;
    top: 10%!important;
}
.tel{
    position: fixed;
    width: 40%!important;
    left: 30%!important;
    top: 57%!important;
    font-size: 16px;
    color: white;
    border: 1px solid white;
    border-radius: 10px;
    text-align: center;
    height: 4vh;
    line-height: 4vh;
}
.fixed-contact_pic span{
	color: white;
}
.lianxi{
	position: fixed;
    left: 39%!important;
    top: 52%!important;
}
.dianhua_tip{
	position: fixed;
    left: 39%!important;
    top: 62%!important;
}
.city{
    margin-top: 45px;
}
.m-topSearchIpt{
    margin-top: -1.88rem;
    width: 88%;
    height: 25px;
    left: 27px;
    position: absolute;
    border-radius: 1.2438rem;
}
.city2{
	position: absolute;
    right: 1px;
    top: 49px;
}
.aui-product-boutique{
background: white;
}
#app{ 
 background: white; 
}
.line{
padding: 4px 0px 2px 0px;
}
.box{
	width:500px;
	height:100px;
	background-image: -moz-linear-gradient(top , #45B5DA, #0382AD);
	background-image: -webkit-gradient(linear, 50% 0%, 50% 100%, from(#45B5DA), to(#0382AD));
	background-image: -o-linear-gradient(top , #45B5DA, #0382AD);}
.pindan{
	width:100%;
	height: 30px;
	background: linear-gradient(90deg, #f94c00 20%, #fb8b2a 50%, #fcc04e 80%);
	border-radius:5px;
	padding-top:5px;
}
.join_button{
	height: 48px;
	width:100%;
    border: 0;
    font-size: 15px;
    color: #fff;
    background: #DD2727;
    line-height: 50px;/*让黄色div中的文字内容垂直居中*/
    text-align: center;/*让文字水平居中*/
    margin-bottom:10px;
}
.font{
	 font-size: 15px;
	 text-align: center;
	 line-height: 50px;
	 color: #666;
	 width:250px;
}
#shareit {
  -webkit-user-select: none;
  position: absolute;
  width: 100%;
  height: 100%;
  background: rgba(0,0,0,0.85);
  text-align: center;
  top: 0;
  left: 0;
  z-index: 105;
}
#shareit img {
  max-width: 100%;
}
#share-text {
  margin-top: 50px;
}
</style>
</head>
<body id="body">

<!-- <div class="mui-fullscreen sharebox" v-if="sharebox" @click="{{sharebox=false}}" style="background:green;z-index: 2;">{{sharebox}}</div> -->
	<div id="app" v-cloak v-if="datas.commInfo">
	
	<%-- <div class="fixed-contact_pic" v-show="is_kefu">   
	    <div class="fixed-wechat_png">
	        <img src="${ctx}/m/styles/img/kefu2.jpg" style="display: unset;" />
	        <img v-if="null != datas.entpInfo.kefu_qr_code" :src="ctx+datas.entpInfo.kefu_qr_code" class="erweima" style="display: unset;" />
	        <span class="lianxi">有问题联系我哦~</span>
	        <a :href="'tel:'+datas.entpInfo.kefu_tel" v-show="!isApp"><span class="tel">{{datas.entpInfo.kefu_tel}}</span></a>
	        <a @tap="callUser(datas.entpInfo.kefu_tel)" v-show="isApp"><span class="tel">{{datas.entpInfo.kefu_tel}}</span></a>
	        <span class="dianhua_tip">点击拨打客服电话</span>
	        <a class="sback" @click="showKefu" style="position: absolute; top:-5%; right: 10%;"><i class="aui-icon aui-icon-close2"></i></a>
	    </div>
	</div>  --%>
		<header class="aui-header-default aui-header-fixed" id="s-header">
			<a class="aui-header-item mui-action-back"><i class="aui-icon aui-icon-back"></i></a>
			<div class="aui-header-center aui-header-center-clear">
				<div class="aui-header-center-logo">
					<div id="scrollSearchPro">
						
						<span class="current">拼团详情</span>
						
					</div>
				</div>
			</div>
			<a href="javascript:;" class="aui-header-item-icon select" style="min-width: 0;">
			<i class="aui-icon aui-footer-icon-home" @click="openIndex"></i></a>
		</header>
		<div id="content1">
			<div class="aui-banner-content aui-fixed-top swiper-container">
				<div class="aui-banner-wrapper swiper-wrapper">
					<div class="aui-banner-wrapper-item swiper-slide" v-for="item in commImgsList">
						<img :src="ctx + item.file_path + '@s800x800'" />
					</div>
				</div>
				<div class="aui-banner-pagination swiper-pagination"></div>
			</div>
			<div class="aui-product-content">
				<div class="aui-product-title" style="padding: 1px 15px 0px 15px;">
					<h2 v-if="datas.commInfo">
						<span class="commInfo_from">{{datas.commInfo.map.commZyName}}</span>
						{{datas.commInfo.comm_name}}
					</h2>
				</div>
				<div class="aui-dri"></div>
				<div class="pindan" v-show="datas.commInfo.comm_type == 20">
					<span v-if="datas.commInfo" style="font-size: 14px; margin-right: 0;">{{datas.commInfo.group_count}}人拼团 ￥{{datas.commInfo.map.commTczhPriceList[0].group_price | formatMoney}} </span>
					&nbsp;&nbsp;&nbsp;
					<span v-if="datas.commInfo" style="font-size: 10px; margin-left: 0;text-decoration:line-through;">原价 ￥{{datas.commInfo.sale_price | formatMoney}} </span>
				</div>
				<div class="aui-product-title" style="padding: 1px 15px 0px 15px;" v-show="datas.commInfo.comm_type == 20">
					<h2 v-if="datas.commInfo.group_type == 2">
						 老带新拼团，由拼团发起者邀请新用户注册后，新用户可以加入拼团 
					</h2>
					<h2 v-if="datas.commInfo.group_type == 1">
						 普通拼团，所有用户都可以参加 
					</h2>
				</div>
				<div class="aui-dri"></div>
				<div class="aui-product-coupon">
					<div class="aui-address-cell aui-fl-arrow-clear">
						<div class="aui-address-cell-bd">团长：</div>
						<div class="font">{{leaderOrderInfo.add_user_name}}</div>
					</div>
				</div>
				<div class="aui-dri"></div>
				<div class="aui-product-coupon">
					<div class="aui-address-cell aui-fl-arrow-clear">
						<div class="aui-address-cell-bd">成员：</div>
						<div class="font"  v-for="child in leaderOrderInfo.map.childOrderInfoList">{{child.add_user_name}}</div>
						<div class="font" v-show="leaderOrderInfo.map.hasChild != true">尚未有人加入</div>
					</div>
				</div>
				<div class="aui-dri"></div>
				<div class="aui-product-coupon">
					<div class="aui-address-cell aui-fl-arrow-clear">
						<div class="aui-address-cell-bd">剩余人数：</div>
						<div v-show="leaderOrderInfo.map.leftCount > 0" class="font">还差{{leaderOrderInfo.map.leftCount}}人，完成拼团</div>
						<div v-show="isClose == 1" class="font">拼团已完成</div>
					</div>
				</div>
				<div class="aui-dri"></div>
				<div class="aui-product-coupon">
					<div class="aui-address-cell aui-fl-arrow-clear">
						<div class="aui-address-cell-bd">剩余时间：</div>
						<div v-show="isClose == 0" class="font" id="time"></div>
						<div v-show="isClose == 1" class="font">拼团已完成</div>
					</div>
				</div>
				<div class="aui-dri"></div>
				
			</div>
		</div>
		<button class="join_button" id="buyBtn" v-show="isLogin" @click="goBuy()">马上参团</button>
		<c:url var="url" value="/m/MIndexLogin.do?comm_id=${af.map.id}" />
		<button class="join_button" v-show="!isLogin" @click="toLogin('${url}');">马上参团</button>
	</div>
	<c:if test="${isLeader eq true}">
		<div id="shareit" onclick="closeTip()">
	 		<img class="arrow" src="${ctx}/images/shareBackground.png"> 
			  <%-- <a href="#" id="follow">
			    <img id="share-text" src="${ctx}/m/styles/img/kefu2.jpg">
			  </a> --%>
		</div>
	</c:if>
	<script type="text/javascript"src="${ctx}/scripts/swiper/swiper.min.js"></script>
	<script type="text/javascript"src="${ctx}/scripts/jquery.infinitescroll.js"></script>
	<script type="text/javascript"src="${ctx}/scripts/cart/mCartCommInfo.js"></script>
	<script src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
	<script type="text/javascript">
	
		var vm = new Vue({
				el : '#app',
				data : {
					sharebox:true,
					ctx : Common.api,
					datas : "",
					isLogin : false,
					hasFavCount : 0,
					pd_count : 1,
					comm_tczh_id : "",
					cart_type : 100,
					commImgsList : "",
					upLevelNeedPayMoney : '',
					pd_stock : '',
					is_kefu:false,
					userInfo:"",
				    isWeixin:false,
				    isApp:false,
				    isPtFlag:1,
				    leaderOrderInfo : "",
				    leaderOrderId:"",
				    isLeader:0,
				    isClose:0,
					},
					mounted : function() {
						this.$nextTick(function() {
							var inData = {
									appId : '${appid}',
									timestamp : '${timestamp}',
									nonceStr : '${nonceStr}',
									signature : '${signature}'
								};
							var shareData = {
								title : '${app_name}',
								desc : '${commInfo.comm_name}',
								link : '${share_url}',
								imgUrl : '${share_img}',
							};
							Common.weixinConfig(inData, shareData);
						});  
						this.getAjaxData();
						this.getLeaderOrderInfo();
						
					},
					updated : function() {
						this.countTime();
						if(vm.isClose == 1){
							$("#buyBtn").attr("disabled",true);
						}
					},
					methods : {
						getLeaderOrderInfo : function(){
							Common.getData({
								route : 'm/MGroupBuy.do?method=getLeaderOrderInfo&leaderOrderId=${af.map.leaderOrderId}&comm_id=${commInfo.id}',
								success : function(data) {
									if (data.code == 0) {
										mui.toast(data.msg);
										return false;
									} else if (data.code == 1) {
										vm.leaderOrderInfo = data.datas.leaderOrderInfo;
										if(vm.leaderOrderInfo.map.leftCount == 0){
											vm.isClose = 1;
										}
									}
								},
								error : function() {
									mui.alert('好像出错了哦~');
								}
							});
						},
						getAjaxData : function() {
							Common.getData({
										route : 'm/MEntpInfo.do?method=getCommInfoData&comm_id=${commInfo.id}',
										success : function(data) {
											if (data.code == 0) {
												mui.toast(data.msg);
												return false;
											} else if (data.code == 1) {
												vm.datas = data.datas;
												vm.isLogin = data.datas.isLogin;
												vm.hasFavCount = data.datas.hasFavCount;
												vm.comm_tczh_id = data.datas.commInfo.map.commTczhPriceList[0].id;
												vm.commImgsList = data.datas.commInfo.commImgsList;
												vm.upLevelNeedPayMoney = data.datas.upLevelNeedPayMoney;
												vm.pd_stock = data.datas.commInfo.map.commTczhPriceList[0].inventory;
												vm.userInfo = data.datas.userInfo;
												vm.isWeixin = data.datas.isWeixin;
												vm.isApp = data.datas.isApp;
											}
										},
										error : function() {
											mui.alert('好像出错了哦~');
										}
									});
						},
						openIndex : function() {
							var url = vm.ctx + "m/index.shtml";
							goUrl(url);
						},
						goBuy :function(){
							var _this = this;
							Common.getData({
								route : 'm/MGroupBuy.do?method=checkLeader&leaderOrderId=${af.map.leaderOrderId}&comm_id=${commInfo.id}',
								success : function(data) {
									if (data.code == 1) {
										mui.toast(data.msg);
										return false;
									} else {
										_this.addCartAndBuy();
									}
								},
								error : function() {
									mui.alert('好像出错了哦~');
								}
							});
						},
						addCartAndBuy : function(){
							Common.getData({
								route : "CsAjax.do?method=saveCartInfo&comm_id="+ vm.datas.commInfo.id
										+ "&gm_pd_count="+ vm.pd_count+ "&comm_tczh_id="+ vm.comm_tczh_id
										+ "&cart_type="+ vm.cart_type+ "&entp_id="+ vm.datas.entpInfo.id+"&isPt="+vm.isPtFlag,
								success : function(data) {
												location.href = app_path+ "/m/MMyCartInfo.do?"+ "&cart_type="+ vm.cart_type
												+ "&comm_tczh_id="+ vm.comm_tczh_id+ "&pd_count="+ vm.pd_count+"&comm_id="+ vm.datas.commInfo.id
												+ "&isPt=" + vm.isPtFlag+"&isLeader="+vm.isLeader+"&leaderOrderId=${af.map.leaderOrderId}"+"&cart_id="+data.result;
								},
								error : function() {
									mui.alert('好像出错了哦~');
								}
							});
						},
						countTime : function() {  
				            //获取当前时间  
				            var date = new Date();  
				            var now = date.getTime();  
				            //设置截止时间  
				            var end = vm.leaderOrderInfo.end_date;  
				            
				            //时间差  
				            var leftTime = end-now; 
				            if(leftTime >0){
					            //定义变量 d,h,m,s保存倒计时的时间  
					            var d,h,m,s;  
					            if (leftTime>=0) {  
					                d = Math.floor(leftTime/1000/60/60/24);  
					                h = Math.floor(leftTime/1000/60/60%24);  
					                m = Math.floor(leftTime/1000/60%60);  
					                s = Math.floor(leftTime/1000%60);                     
					            }  
					            //将倒计时赋值到div中  
					            if(d!=0){
						            $("#time").text(d+"天"+h+"小时"+m+"分"+s+"秒")
					            }else{
						            $("#time").text(h+"小时"+m+"分"+s+"秒")
					            }
					            setInterval(this.countTime,1000);  
				            }else{
				            	vm.isClose = 1;
				            	$("#time").text("拼团已结束")
				            }
				        },
				        closeTip : function(){
				        	console.log("sssssss")
				        	$("#shareit").attr("style","display:none;")
				        },
					}
				});
		function toLogin(url){
			 mui.toast('很抱歉！您还没有登录，请先登录！');
			 setTimeout(function() {
					 location.href = url +'&returnUrl=' + escape(location.href)},2000);
			 return true;
		}
		$("#share_btn").on("click", function() {
		      $("#shareit").show();
		  });
		   
		   
		  $("#shareit").on("click", function(){
		    $("#shareit").hide(); 
		  });
	</script>
</body>
</html>