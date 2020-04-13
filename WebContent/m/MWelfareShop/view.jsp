<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
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
</style>
</head>
<body id="body">
	<div id="app" v-cloak v-if="datas.commInfo">
	
	<div class="fixed-contact_pic" v-show="is_kefu">   
	    <div class="fixed-wechat_png">
	        <img src="${ctx}/m/styles/img/kefu2.jpg" style="display: unset;" />
	        <img v-if="null != datas.entpInfo.kefu_qr_code" :src="ctx+datas.entpInfo.kefu_qr_code" class="erweima" style="display: unset;" />
	        <span class="lianxi">有问题联系我哦~</span>
	        <a :href="'tel:'+datas.entpInfo.kefu_tel" v-show="!isApp"><span class="tel">{{datas.entpInfo.kefu_tel}}</span></a>
	        <a @tap="callUser(datas.entpInfo.kefu_tel)" v-show="isApp"><span class="tel">{{datas.entpInfo.kefu_tel}}</span></a>
	        <span class="dianhua_tip">点击拨打客服电话</span>
	        <a class="sback" @click="showKefu" style="position: absolute; top:-5%; right: 10%;"><i class="aui-icon aui-icon-close2"></i></a>
	    </div>
	</div>
	<div id="contact_pic_mask" v-show="is_kefu"></div>
		<header class="aui-header-default aui-header-fixed" id="s-header">
			<a class="aui-header-item mui-action-back"><i class="aui-icon aui-icon-back"></i></a>
			<div class="aui-header-center aui-header-center-clear">
				<div class="aui-header-center-logo">
					<div id="scrollSearchPro">
						<c:url var="url" value="/m/MEntpInfo.do?id=${af.map.id}" />
						<span class="current" data-url="${url}">商品</span>
						<c:url var="url"
							value="/m/MEntpInfo.do?method=viewDetails&id=${af.map.id}" />
						<span data-url="${url}">详情</span>
						<c:url var="url"
							value="/m/MEntpInfo.do?method=getCommentList&comm_id=${af.map.id}" />
						<span data-url="${url}">评价</span>
					</div>
				</div>
			</div>
			<a href="javascript:;" class="aui-header-item-icon select" style="min-width: 0;">
			<i class="aui-icon aui-footer-icon-home" @click="openIndex"></i></a>
		</header>
		<!-- <div class="line c-hd" v-show="isWeixin">
			<section class="city" id="scanQRCode"><span class="aui-icon aui-icon-scan"></span></section>
			<div class="m-topSearchIpt ipt" @click="btnSearch()"><i class="icon"></i><span class="placeholder">搜索商品</span></div>
			<section class="city2" @click="openMyCard('m/MMyCard.do')"><span class="aui-icon aui-icon-mycard"></span></section>
		</div> -->
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
				<div class="aui-real-price clearfix">
					<span v-if="datas.commInfo"> ￥{{datas.commInfo.sale_price | formatMoney}} </span>
						<span v-if="datas.commInfo && datas.commInfo.is_rebate == 1" style="float: right;"> 
							<span class="aui-product-tag-text" style="font-size: 16px; margin-right: 0;" @click="toUpLevel" v-if="(isLogin && datas.userInfo.user_level == 201) || (!isLogin)">
							挑夫会员：立减{{(datas.commInfo.rebate_scale * datas.commInfo.sale_price * datas.reBate1001/10000) | formatMoney }}元</span>
							 <span class="aui-product-tag-text" style="font-size: 16px; margin-right: 0;" v-else="(isLogin && datas.userInfo.user_level == 201) || (!isLogin)">
							 挑夫会员：立减{{(datas.commInfo.rebate_scale * datas.commInfo.sale_price * datas.reBate1001/10000) |formatMoney }}元</span>
					</span>
				</div>
				<div class="aui-product-boutique clearfix" v-if="datas.commInfo && datas.commInfo.is_zingying == 1" style="margin: 0 5px 5px 5px;">
					<div style="margin-bottom: 0.2rem;">
						<i class="aui-icon aui-icon-fahuo"></i> 
						<span class="aui-product-tag-text" style="float: none;">由京东发货并提供售后</span>
					</div>
					<div>
						<i class="aui-icon aui-icon-jingdong"></i>
						<span class="aui-product-tag-text">京东商品不支持七天无理由退货</span>
					</div>
				</div>
				<div class="aui-dri"></div>
				<div class="aui-product-coupon" v-if="datas.commInfo &&  datas.commInfo.is_rebate == 1">
					<a @click="showRebate" class="aui-address-cell aui-fl-arrow aui-fl-arrow-clear">
						<div class="aui-address-cell-bd">代言</div>
						<div class="aui-address-cell-ft">查询代言奖励</div>
					</a>
				</div>
				<div class="aui-dri" v-if="datas.commInfo &&  datas.commInfo.is_rebate == 1"></div>
				<div class="aui-product-coupon" v-if="datas.commInfo &&  datas.commInfo.is_aid == 1">
					<a @click="showCommPoor" class="aui-address-cell aui-fl-arrow aui-fl-arrow-clear">
						<div class="aui-address-cell-bd">扶贫</div>
						<div class="aui-address-cell-ft">{{datas.commInfo.poorsList.length}}人</div>
					</a>
				</div>
				<div class="aui-dri" v-if="datas.commInfo && datas.commInfo.is_aid == 1"></div>
				<div class="aui-product-coupon">
					<a @click="showBuyTip(0);" class="aui-address-cell aui-fl-arrow aui-fl-arrow-clear">
						<div class="aui-address-cell-bd">选择</div>
						<div class="aui-address-cell-ft">套餐选择</div>
					</a>
				</div>
				<div class="aui-dri"></div>
			</div>
		</div>
		<div id="content2" style="display: none; padding-top: 2.8rem; margin-bottom: 50px;"></div>
		<div id="content3" style="display: none; padding-top: 2.8rem; margin-bottom: 50px;"></div>
		<footer class="aui-footer-product" id="s-actionBar-container">
			<div class="aui-footer-product-fixed">
				<div class="aui-footer-product-concern-cart" v-if="datas.entpInfo">
					<c:set var="kefu_url" value="http://wpa.qq.com/msgrd?v=3&uin=${entpInfo.qq}&site=qq&menu=yes" />
					<c:if test="${isApp}">
						<c:set var="kefu_url" value="mqqwpa://im/chat?chat_type=wpa&uin=${entpInfo.qq}&version=1&src_type=web&web_src=oicqzone.com" />
					</c:if>
					<a href="#" @click="showKefu();" style="width: 50%;"> 
						<span class="aui-f-p-icon">
							<img src="${ctx}/m/styles/themes/img/icon/icon-kf.png" alt=""></span>
								<span class="aui-f-p-focus-info">客服</span></a> 	 
					<a href="#" style="width: 50%;">
						<span class="aui-f-p-icon" v-show="hasFavCount == 0">
							<img src="${ctx}/m/styles/themes/img/icon/icon-sc.png" alt=""></span>
						<span class="aui-f-p-icon" v-show="hasFavCount > 0">
							<img src="${ctx}/m/styles/themes/img/icon/icon-sc2.png" alt=""></span>
						<span class="aui-f-p-focus-info" v-show="isLogin" @click="addFav">收藏</span>
							<c:url var="url" value="/m/MIndexLogin.do?comm_id=${af.map.id}" />
						<span class="aui-f-p-focus-info" v-show="!isLogin" onclick="toLogin('${url}');">收藏</span>
					</a> 
<!-- 					<a :href="ctx + 'm/MEntpInfo.do?method=index&entp_id=' + datas.entpInfo.id"> -->
<!-- 						<span class="aui-f-p-icon"> -->
<%-- 							<img src="${ctx}/m/styles/themes/img/icon/icon-dp.png" alt=""></span> --%>
<!-- 						<span class="aui-f-p-focus-info">店铺</span> -->
<!-- 					</a> -->
				</div>
				<div class="aui-footer-product-action-list">
					<!-- 				    <input type="hidden" name="pd_stock" id="pd_stock" v-model="datas.commInfo.inventory"/> -->
					<input type="hidden" name="comm_tczh_id" id="comm_tczh_id" v-model="comm_tczh_id" />
					 <a class="yellow-color" v-show="isLogin" @click="showBuyTip(0);">加入购物车</a>
					 <a class="red-color" v-show="isLogin" @click="showBuyTip(1);">立即购买</a>
					 <c:url var="url" value="/m/MIndexLogin.do?comm_id=${af.map.id}" />
					 <a class="yellow-color" v-show="!isLogin" onclick="toLogin('${url}');">加入购物车</a>
					 <a class="red-color" v-show="!isLogin" onclick="toLogin('${url}');">立即购买</a>
				</div>
			</div>
		</footer>
		<div class="mui-cover" id="s-decision-wrapper">
		   <div class="body">
			 <section id="s-decision">
				<div class="address-control" style="display: none"></div>
				  <div class="sku-control">
					<ul class="mui-sku">
					  <li class="J_SkuGroup mui-sku-group">
						<h2>套餐</h2>
						  <div class="items">
						    <label v-for="item,index in datas.commInfo.map.commTczhPriceList" id="item" :class="{'checked': index === 0}"
								@click="chooseType(item,$event)">{{item.tczh_name}}</label></div>
							</li>
						</ul>
					</div>
			<div class="number">
			  <h2>数量</h2>
				<div class="content">
				  <div class="mui-numbox">
					<button class="mui-btn mui-numbox-btn-minus" type="button" @click="calcCartMoney(-1);">-</button>
					  <input class="mui-numbox-input" type="number" value="1" min="1" max="2" id="pd_count" v-model="pd_count" @change="calcCartMoney()" />
						<button class="mui-btn mui-numbox-btn-plus" type="button" @click="calcCartMoney(1);">+</button>
						</div>
					</div>
				</div>
			</section>
			</div>
			<div class="summary">
				<div class="img">
					<img :src="ctx + datas.commInfo.main_pic  + '@s100x100'" width="100" height="100" /></div>
				<div class="main">
					<div class="priceContainer">
						<span class="price" id="sale_price">{{datas.commInfo.sale_price | formatMoney}}</span>
					</div>
					<div class="stock-control">
						<span class="stock"><label class="label">库存:</label>
						<span id="curr_stock">{{datas.commInfo.map.commTczhPriceList[0].inventory}}</span>件</span>
					</div>
					<div class="sku-dtips">已选择:
						<span id="hasSelectTc">{{datas.commInfo.map.commTczhPriceList[0].tczh_name}}</span>
					</div>
				</div>
				<a class="sback" @click="hideWrapper"><i class="aui-icon aui-icon-close"></i></a>
			</div>
			<div class="option mui-flex">
				<c:url var="url" value="/m/MIndexLogin.do?comm_id=${af.map.id}" />
				<button class="ok cell" v-show="!isLogin" onclick="toLogin('${url}');">确定</button>
				<button class="ok cell" v-show="isLogin" @click="addCartOrBuy(false);">确定</button>
			</div>
		</div>
		<div class="mui-cover" id="a-decision-wrapper"
			style="top: 43%; background-color: #fdfafa;overflow-y: scroll;">
			<div>
				<section id="s-decision"  style="position: absolute; bottom: 0px; top: 0; left: 0;right: 0;overflow: auto;">
					<div class="sku-control">
						<ul class="mui-sku">
						  <li class="J_SkuGroup mui-sku-group">
						  <h3 class="tax-title" style="text-align: center; font-size: 18px;height: 28px; margin: 10px;">店铺优惠券</h3>
							<div class="tax-box displayType0" style="background: #FFF1F1; color: #FF615E; border-radius: 8px; font-family: Helvetica, sans-serif; margin: 12px;" v-for="item in datas.couponslist">
								<div class="mui-flex tax-item" style="color: #f24b4b" v-if = "null !=item.map.yhqInfo">
									<div class="tax-main cell">
										<div class="c-main" style="font-size: 23px; margin-top: 10px; left: 10px; position: relative;">
											<i class="mui-price-rmb">¥</i><span class="rmb">{{item.map.yhqInfo.yhq_money | formatMoney}}</span></div>
												<div class="c-sub" style="font-size: 10px; margin-top: 7px; left: 10px; position: relative;">{{item.map.yhqInfo.yhq_name}}</div>
													<div class="c-sub" style="font-size: 7px; left: 10px; position: relative;">
													有效期{{item.map.yhqInfo.yhq_start_date | formatDateYmd}}至{{item.map.yhqInfo.yhq_end_date | formatDateYmd}}
													</div>
											</div>
									<div class="tax-split " style="position: relative; height: 90px; right: -67px; border-right: 1px dotted #FFCDCD;">
										<div class="line" style="border-color: #FFCDCD; opacity: 1;"></div>
									</div>
									<div class="tax-operator cell" @click="GetCoupons(item.map.yhqInfo.id)">
										<div class="c-jf" style="margin-top: 32px; font-size: 15px; position: absolute; right: 23px;">立即领取</div>
										</div>
									</div>
								</div>
							</li>
						</ul>
					</div>
				</section>
			</div>
			<a class="sback" @click="hideWrapper" style="position: absolute; top: 0px; right: 0px;">
			<i class="aui-icon aui-icon-close"></i></a>
		</div>
		<div class="mui-cover" id="s-poor-wrapper">
			<h4>扶贫商品</h4>
			<div class="aui-product-boutique clearfix" v-if="datas.commInfo.is_aid == 1">
				<span v-if="datas.commInfo.is_aid == 1"> <i class="aui-icon aui-icon-fupin"></i>
				 <span class="aui-product-tag-text">
				 本商品扶贫金：{{(datas.commInfo.aid_scale * datas.commInfo.sale_price/100) | formatMoney}}元</span></span>
			</div>
			<div class="cover-content">扶贫金是指平台商户为贫困户设定的资助金，本商品在订单成交后，扶贫金会自动转入扶贫对象的账户，没有中间方。</div>
			<div class="aui-slide-box">
				<div class="aui-slide-list" style="height: auto;">
					<ul class="aui-slide-item-list" style="height: auto;">
						<li class="aui-slide-item-item" style="width: 30%; float: left;" v-for="item in datas.commInfo.poorsList">
							<a class="v-link" @click="openPoorUrl(item)">
							 <img class="v-img" v-if="item.map.head_logo == null" :src="ctx + 'styles/images/user.png'" />
							 	 <img class="v-img" v-if="item.map.head_logo != null" :src="ctx + item.map.head_logo+'@s80x80'" />
								 <p class="aui-slide-item-title aui-slide-item-f-els">{{item.map.real_name}}</p>
							</a>
						</li>
					</ul>
				</div>
			</div>
			<a class="sback" @click="hideWrapper"><i class="aui-icon aui-icon-close"></i></a>
		</div>
		<div class="mui-cover" id="s-poor-rebate">
			<h4>代言奖励</h4>
			<div class="aui-product-boutique clearfix" v-if="datas.commInfo.is_rebate == 1">
			   <span v-if="datas.commInfo.is_rebate == 1"><i class="aui-icon aui-icon-fanxian"></i> 
				  <span class="aui-product-tag-text">
					代言奖励：{{(datas.commInfo.rebate_scale * datas.commInfo.sale_price * datas.reBate1002/10000) |formatMoney }}元</span>
			</div>
			<div class="cover-content">代言奖励是指九个挑夫平台会员通过代言分享，让朋友也注册成为会员，当该会员在平台消费后，平台会给予代言人一定的分享奖励，该奖励可用于代言人在平台的消费抵扣，或提现到银行卡。</div>
			<a class="sback" @click="hideWrapper"><i class="aui-icon aui-icon-close"></i></a>
		</div>
		<div class="cover-decision" id="s-decision-wrapper-cover" @click="hideWrapper" style="display: none;"></div>
    </div>
	<script type="text/javascript"src="${ctx}/scripts/swiper/swiper.min.js"></script>
	<script type="text/javascript"src="${ctx}/scripts/jquery.infinitescroll.js"></script>
	<script type="text/javascript"src="${ctx}/scripts/cart/mCartCommInfo.js"></script>
	<script src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
	<script type="text/javascript">
		var vm = new Vue({
				el : '#app',
				data : {
					ctx : Common.api,
					datas : "",
					isLogin : false,
					hasFavCount : 0,
					pd_count : 1,
					comm_tczh_id : "",
					addCartOrBuyFlag : 0,
					cart_type : 20,
					commImgsList : "",
					upLevelNeedPayMoney : '',
					pd_stock : '',
					is_kefu:false,
					userInfo:"",
				    isWeixin:false,
				    isApp:false,
					},
				mounted : function() {
					 
					Common.loading();
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
						this.getAjaxData();
					});
				},
				updated : function() {
					Common.hide();
					$("#scrollSearchPro span").each(
						function(index) {
							$(this).click(function() {
									$(this).addClass("current").siblings().removeClass("current");
									var contentHtml = $("#content"+ (index + 1)).html();
									$("#content"+ (index + 1)).show().siblings().hide();
									$("#s-actionBar-container").show();
									$("#s-decision-wrapper").hide();
									$("#s-poor-wrapper").hide();
									$("#s-poor-rebate").hide();
									$("#s-decision-wrapper-cover").hide();
									$("#s-header").show();
									if (!contentHtml) {
										var url = $(this).attr("data-url");
										$.post(url,function(data) {
											$("#content"+ (index + 1)).html(data);
										});
									}
								});
							});
						new Swiper('.swiper-container',{
									autoplay : true,//等同于以下设置
									pagination : {
										el : '.swiper-pagination',
										bulletClass : 'slider-pagination-item',
										bulletActiveClass : 'slider-pagination-item-active',
									},
								});
						new Swiper('.swiper-container-comm', {
							slidesPerView : 'auto',
							paginationClickable : true,
							spaceBetween : 20
						});

					},
					methods : {
						getAjaxData : function() {
							Common.getData({
										route : 'm/MEntpInfo.do?method=getCommInfoData&comm_id=${af.map.id}',
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
						toUpLevel : function() {
							//去选择支付方式
					 	if( null == vm.userInfo.mobile || "" ==vm.userInfo.mobile || vm.userInfo.mobile.length == 0){
				    		mui.toast('请先绑定手机号！'); 
				     		var url = vm.ctx + "m/MMySecurityCenter.do?method=setMobile";
				 			goUrl(url);
				     	}else{
						Common.confirm("付费会员将缴费" + vm.upLevelNeedPayMoney+ "元,你确定要升级成为付费会员吗？",[ "确定", "取消" ],function() {
										var url = vm.ctx+ "m/MIndexPayment.do?method=PayForUpLevel";
										goUrl(url);},function() {});
					     	}
						},
						openPoorUrl : function(item) {
							var url = vm.ctx+ "m/MUserCenter.do?method=index&user_id="+ item.map.user_id;
							goUrl(url);
						},
						openIndex : function() {
							var url = vm.ctx + "m/index.shtml";
							goUrl(url);
						},
						showCommPoor : function() {
							$("#s-poor-wrapper").show().addClass("show");
							$("#s-decision-wrapper-cover").show();
						},
						showRebate : function() {
							$("#s-poor-rebate").show().addClass("show");
							$("#s-decision-wrapper-cover").show();
						},
						chooseType : function(item, e) {
							$(e.target).siblings().removeClass("checked");
							$(e.target).addClass("checked");
							vm.setPrice(item);
							vm.pd_stock = item.inventory;
						},
						addFav : function() {
							Common.getData({
								route : 'entp/CsAjax.do?method=addFav&sc_type=2&link_user_id='+ vm.datas.userInfo.id+ "&link_id=${af.map.id}",
								success : function(oper) {
									var html;
									if (oper.result == 1) {
										html = "收藏成功";
										vm.hasFavCount = 1;
									} else {
										html = "取消成功";
										vm.hasFavCount = 0;
									}
									mui.toast(html);
								},
								error : function() {
									mui.alert('好像出错了哦~');
								}
							});
						},
						hideWrapper : function() {
							$("#s-decision-wrapper").removeClass("show");
							$("#s-poor-wrapper").removeClass("show");
							$("#s-poor-rebate").removeClass("show");
							$("#a-decision-wrapper").removeClass("show");
							$("#s-decision-wrapper-cover").hide();
							$("#a-decision-wrapper-cover").hide();
						},
						showBuyTip : function(flag) {
							$("#s-decision-wrapper").show().addClass("show");
							$("#a-decision-wrapper").removeClass("show");
							$("#a-decision-wrapper-cover").hide();
							$("#s-decision-wrapper-cover").show();
							vm.addCartOrBuyFlag = flag;
						},
						showTip : function(flag) {
							$("#a-decision-wrapper").show().addClass("show");
							$("#a-decision-wrapper-cover").show();
							$("#s-decision-wrapper").removeClass("show");
							$("#s-decision-wrapper-cover").hide();
							vm.addCartOrBuyFlag = flag;
						},
						callUser:function(tel){
							mui.plusReady(function() {
								plus.device.dial(tel,false);
							});
						},
						calcCartMoney : function(addNum) {
							var pd_max_count = vm.pd_stock;
							console.info(pd_max_count)
							if (null == addNum || undefined == addNum) {
								addNum = 0;
							}
							var count = vm.pd_count;
							count =parseFloat(count)+ parseFloat(addNum);
							if (null != pd_max_count
									&& undefined != pd_max_count
									&& "" != pd_max_count) {
								if ((count > pd_max_count)) {
									mui.toast('很抱歉，购买数量大于该产品当前库存，请调整购买数量！');
									count = pd_max_count;
								}
							}
							if (count <= 0) {
								count = 1;
							}
							vm.pd_count = count;
						},
						calcCount : function(){
							var count = vm.pd_count;
					 		if(!(count%1 === 0)){
					 			mui.toast("商品数量只能是整数");
					 			vm.pd_count=1;
					 			return false;
					 		}
					 		var pd_max_count = vm.pd_stock;
					 		if (null != pd_max_count
									&& undefined != pd_max_count
									&& "" != pd_max_count) {
								if ((count > pd_max_count)) {
									mui.toast('很抱歉，购买数量大于该产品当前库存，请调整购买数量！');
									count = pd_max_count;
								}
							}
							if (count <= 0) {
								count = 1;
							}
							vm.pd_count = count;
						},
						setPrice : function(item) {
							$("#hasSelectTc").text(item.tczh_name);
							Common.getData({
										route : 'entp/CsAjax.do?method=getTcPriceByTcId&tczh_id='+ item.id,
										success : function(data) {
											if (data.code == 1) {
												$("#hasSelectTc").text(data.datas.commTczhPrice.tczh_name);
												$("#sale_price").text(parseFloat(data.datas.commTczhPrice.comm_price).toFixed(2));
												vm.comm_tczh_id = item.id;
											} else {
												mui.toast(data.msg);
											}
										},
										error : function() {
											mui.alert('好像出错了哦~');
										}
									});
						},
						GetCoupons : function(id) {
							Common.getData({
										route : 'm/GetCoupons.do?method=getYhq&yhq_id='+id,
										success : function(data) {
											if (data.code == 1) {
												mui.toast(data.msg);
											}else if (data.code == -1){
												mui.toast(data.msg);
											}else{
												Common.confirm("您还未登录，请先登录系统！",[ "去登录", "再逛逛" ],
														function() {
															location.href = app_path+ "/m/MIndexLogin.do";
														}, function() {
														});
											}
										},
										error : function() {
											mui.alert('好像出错了哦~');
										}
									});
						},
					   openMyCard: function(url) {
						<c:if test="${(empty userInfo) or ((not empty userInfo))}">
							   goUrl(vm.ctx + url);	
							 </c:if>
						  },
					   btnSearch:function(){
								 var url = vm.ctx + "m/MSearch.do";
								 goUrl(url);
							  },
						addCartOrBuy : function(noCart) {
							Common.getData({
										route : "CsAjax.do?method=saveCartInfo&comm_id="+ vm.datas.commInfo.id
												+ "&gm_pd_count="+ vm.pd_count+ "&comm_tczh_id="+ vm.comm_tczh_id
												+ "&cart_type="+ vm.cart_type+ "&entp_id="+ vm.datas.entpInfo.id,
										success : function(data) {
											if (vm.addCartOrBuyFlag == 1) {
												if (noCart) {
													mui.alert('商品有误，该系统没有虚拟商品');
// 													location.href = app_path+ "/m/MMyNoCartInfo.do?comm_id="+ vm.datas.commInfo.id
// 																	+ "&comm_tczh_id="+ vm.comm_tczh_id+ "&pd_count="+ vm.pd_count;
												} else {
													location.href = app_path+ "/m/MWelfareCartInfo.do";
												}
											} else {
												$("#s-decision-wrapper").removeClass("show");
												$("#s-poor-wrapper").removeClass("show");
												$("#s-poor-rebate").removeClass("show");
												$("#s-decision-wrapper-cover").hide();
												Common.confirm("加入购物车成功！",[ "去购物车", "再逛逛" ],
													function() {
														location.href = app_path+ "/m/MWelfareCartInfo.do";
													}, function() {
												});
											}
										},
										error : function() {
											mui.alert('好像出错了哦~');
										}
									});
						},
						showKefu:function(){
							vm.is_kefu = !vm.is_kefu;
						},
					}
				});
		function toLogin(url){
			 mui.toast('很抱歉！您还没有登录，请先登录！');
			 setTimeout(function() {
					 location.href = url +'&returnUrl=' + escape(location.href)},2000);
			 return true;
		}
	</script>
</body>
</html>