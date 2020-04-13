<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${app_name}</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Expires" content="-1">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Pragma" content="no-cache">
<jsp:include page="../_public_in_head_new.jsp" flush="true" />
<style type="text/css">
.aui-slide-box .aui-slide-item-list .aui-slide-item-item{width: 31%!important;margin-left: 1%;margin-right:0px;float:left;}
.aui-slide-box .aui-slide-item-list .aui-slide-item-item .v-img{margin: 0 auto}
</style>
</head>
<body>
<div id="app" v-cloak>
	<header class="aui-header-default aui-header-fixed aui-header-clear-bg " style="background:none; border-bottom:0">
		<a href="#" class="aui-header-item">
			<div id="scrollSearchDiv">
				<img src="${ctx}/m/styles/themes/img/user/head-2.jpg" alt="">
			</div>
		</a>
		<div class="aui-header-center aui-header-center-clear">
			<div class=""></div>
		</div>
		<a @click="openUrl(ctx + mMyAccount)" class="aui-header-item-icon">
			<i class="aui-icon aui-icon-Sets"></i>
		</a>
	</header>
	<section class="aui-me-content" style="padding-top:0;">
		<div class="aui-me-content-box">
			<div class="aui-me-content-list" style="padding-top:35px;">
				<div class="aui-me-content-item">
					<div class="aui-me-content-item-head">
						<div class="aui-me-content-item-img" v-if="user_logo != ''">
							<img :src="user_logo" @click="openUrl(ctx + MUserCenter + userInfo.id)"/>
						</div>
						<div class="aui-me-content-item-img" v-else>
							<img src="${ctx}/styles/images/user.png" @click="openUrl(ctx + MUserCenter + userInfo.id)"/>
						</div>
					</div>
					<div class="aui-me-content-item-head" style="margin-left: 4rem;">
						<div class="aui-me-content-item-title">
						{{user_name}}
						<span v-if="userInfo.user_level != 201" @click="openUrl(ctx + mMyCard)"><i class="aui-icon aui-icon-fufei" style="vertical-align: bottom;"></i></span>
						<span v-if="userInfo.user_level == 201" @click="toUpLevel"><i class="aui-icon aui-icon-fufei-disabled" style="vertical-align: bottom;"></i></span>
						<span v-if="userInfo.is_renzheng == 1" @click="renzhen"><i class="aui-icon aui-icon-renzheng" style="vertical-align: bottom;"></i></span>
						<span v-if="userInfo.is_renzheng == 0" @click="renzhen"><i class="aui-icon aui-icon-renzheng-disabled" style="vertical-align: bottom;"></i></span>
						<span v-if="userInfo.is_poor == 1"><i class="aui-icon aui-icon-pinkunhu" style="vertical-align: bottom;"></i></span>
						</div>
						<div class="aui-me-content-item-title" style="margin-top: 15px;overflow-x: hidden;text-overflow: ellipsis;white-space: nowrap;width: 250px;font-weight: normal;">
						 {{userInfo.autograph}}
						</div>
					</div>
					<div class="aui-me-content-item-text">
						<a @click="openUrl(ctx + shoucang_count)">
							<span>{{datas.sc_count}}</span>
							<span>收藏</span>
						</a>
						<a @click="openUrl(ctx + 'm/MMyContact.do?method=list')">
							<span>{{datas.guanzhu_count}}</span>
							<span>关注</span>
						</a>
						<a @click="openUrl(ctx + 'm/MVillageMember.do?method=guanzhu&user_id='+ userInfo.id +'&type=2')">
							<span>{{datas.fensi_count}}</span>
							<span>粉丝</span>
						</a>
						<a @click="showJoinVillage">
							<span>{{datas.myJoinVillageCount}}</span>
							<span>社区</span>
						</a>
					</div>
				</div>
			</div>
		</div>
		<div class="aui-me-content-order">
			<a @click="openUrl(ctx + order_all)" class="aui-well aui-fl-arrow">
				<div class="aui-well-bd">我的订单</div>
				<div class="aui-well-ft">查看全部</div>
			</a>
		</div>
		<section class="aui-grid-content">
			<div class="aui-grid-row">
				<c:url var="url" value="" />
				<a @click="openUrl(ctx + daifukuan)" class="aui-grid-row-item">
				    <span class="mui-badge mui-badge-danger" style="position:absolute;top:0.05rem;right:1rem;" v-show="dai_fukuan_num > 0">{{dai_fukuan_num}}</span>
					<i class="aui-icon-large aui-icon-large-sm aui-icon-wallet"></i>
					<p class="aui-grid-row-label">待付款</p>
				</a>
				<a @click="openUrl(ctx + daifahuo)" class="aui-grid-row-item">
				    <span class="mui-badge mui-badge-danger" style="position:absolute;top:0.05rem;right:1rem;" v-show="dai_fahuo_num > 0">{{dai_fahuo_num}}</span>
					<i class="aui-icon-large aui-icon-large-sm aui-icon-goods"></i>
					<p class="aui-grid-row-label">待发货</p>
				</a>
				<a @click="openUrl(ctx + daishouhuo)" class="aui-grid-row-item">
				    <span class="mui-badge mui-badge-danger" style="position:absolute;top:0.05rem;right:1rem;" v-show="dai_shouhuo_num > 0">{{dai_shouhuo_num}}</span>
					<i class="aui-icon-large aui-icon-large-sm aui-icon-receipt"></i>
					<p class="aui-grid-row-label">待收货</p>
				</a>
				<a @click="openUrl(ctx + daipingjia)" class="aui-grid-row-item">
				    <span class="mui-badge mui-badge-danger" style="position:absolute;top:0.05rem;right:1rem;" v-show="dai_pingjia_num > 0">{{dai_pingjia_num}}</span>
					<i class="aui-icon-large aui-icon-large-sm aui-icon-evaluate"></i>
					<p class="aui-grid-row-label">待评价</p>
				</a>
				<a @click="openUrl(ctx + daituihuotuikuan)" class="aui-grid-row-item">
				    <span class="mui-badge mui-badge-danger" style="position:absolute;top:0.05rem;right:1rem;" v-show="tuihuo_num > 0">{{tuihuo_num}}</span>
					<i class="aui-icon-large aui-icon-large-sm aui-icon-refund"></i>
					<p class="aui-grid-row-label">退货退款</p>
				</a>
			</div>
			<div class="aui-dri"></div>
			<div class="aui-grid-row">
			   <c:if test="${(userInfo.is_entp eq 0) and (userInfo.is_fuwu eq 0) and (userInfo.user_type eq 2)}">
				<a @click="openUrl(ctx + 'm/MMyQrCode.do?method=myRegisterCode')" class="aui-grid-row-item -mob-share-open">
					<i class="aui-icon-large aui-icon-large-sm aui-icon-invitation"></i>
					<p class="aui-grid-row-label">我的邀请码</p>
				</a>
			   </c:if>	
				<a @click="openUrl(ctx + 'm/MMyQianBao.do')" class="aui-grid-row-item">
					<i class="aui-icon-large aui-icon-large-sm aui-icon-coupon"></i>
					<p class="aui-grid-row-label">我的钱包</p>
				</a>
				<a @click="openUrl(ctx + 'm/MHelpDocument.do?method=index')" class="aui-grid-row-item">
					<i class="aui-icon-large aui-icon-large-sm aui-icon-help"></i>
					<p class="aui-grid-row-label">帮助中心</p>
				</a>
				<a @click="openUrl(ctx + 'm/MMyComment.do?method=getCommentList&add_user_id=${userInfo.id}&tip=1')" class="aui-grid-row-item">
					<i class="aui-icon-large aui-icon-large-sm aui-icon-evaluates"></i>
					<p class="aui-grid-row-label">我的评价</p>
				</a>
				<a @click="openUrl(ctx + 'm/MMyLowerLevel.do?par_id=1100700000&par_level=1&mod_id=1100700100')" class="aui-grid-row-item">
					<i class="aui-icon-large aui-icon-large-sm aui-icon-shares"></i>
					<p class="aui-grid-row-label">我的团队</p>
				</a>
				<a @click="openUrl(ctx + 'm/MMyContact.do?method=list')" class="aui-grid-row-item">
					<i class="aui-icon-large aui-icon-large-sm aui-icon-fight"></i>
					<p class="aui-grid-row-label">我的通讯录</p>
				</a>
				
				<a @click="openUrl(ctx + 'm/MMySecurityCenter.do?method=setRenzheng')" class="aui-grid-row-item">
					<i class="aui-icon-large aui-icon-large-sm aui-icon-renzheng"></i>
					<p class="aui-grid-row-label">实名认证</p>
				</a>
				<a @click="openUrl(ctx + 'm/MMyMsg.do?mod_id=1100630100')" class="aui-grid-row-item">
					<span class="mui-badge mui-badge-danger" style="position:absolute;top:0.05rem;right:1rem;" v-show="msg_count > 0">{{msg_count}}</span>
					<i class="aui-icon-large aui-icon-large-sm aui-icon-xiaoxi"></i>
					<p class="aui-grid-row-label">我的消息</p>
				</a>
				<a @click="openUrl(ctx + 'm/GetCoupons.do?')" class="aui-grid-row-item">
					<i class="aui-icon-large aui-icon-large-sm aui-icon-coupon"></i>
					<p class="aui-grid-row-label">我的优惠券</p>
				</a>
				<a @click="openUrl(ctx + 'm/MMyWelfareCard.do?')" class="aui-grid-row-item">
					<i class="aui-icon-large aui-icon-large-sm aui-icon-coupon"></i>
					<p class="aui-grid-row-label">我的福利卡</p>
				</a>
				<a @click="openUrl(ctx + pintuan)" class="aui-grid-row-item">
					<i class="aui-icon-large aui-icon-large-sm aui-icon-xiaoxi"></i>
					<p class="aui-grid-row-label">我的拼团</p>
				</a>
				
			</div>
		</section>
	</section>
	<div id="JoinVillage" style="display:none;">
	<div class="aui-slide-box">
	 <div class="aui-slide-list swiper-container-comm">
		<ul class="aui-slide-item-list swiper-wrapper">
			<li class="aui-slide-item-item swiper-slide join-village-li" v-for="item in datas.myJoinVillageList">
				<a :href="ctx + 'm/MVillage.do?method=index&id='+item.village_id" class="v-link">
					<img :src="ctx + item.map.village_logo +'@s200x200'" class="v-img" style="border-radius:50%;">
					<p class="aui-slide-item-title aui-slide-item-f-els">{{item.map.village_name}}</p>
				</a>
			</li>
		</ul>
	</div>
   </div>
   </div>
</div>
<jsp:include page="../_footer.jsp" flush="true" />
<script type="text/javascript" src="${ctx}/scripts/swiper/swiper.min.js"></script>
<script type="text/javascript" src="${ctx}/styles/mui/layer.js"></script>
<script type="text/javascript">

var vm = new Vue({
  el: '#app',
  data: {
    totalMoney: 0,
    ctx:Common.api,
    MUserCenter:'m/MUserCenter.do?method=index&user_id=',
    mMyAccount:'m/MMyAccount.do',
    mMyCard:'m/MMyCard.do',
    order_all:'m/MMyOrder.do?method=list',
    daifukuan:'m/MMyOrder.do?method=list&order_type=&order_state=0',
    daifahuo:'m/MMyOrder.do?method=list&order_type=&order_state=10',
    daishouhuo:'m/MMyOrder.do?method=list&order_type=&order_state=20',
    daipingjia:'m/MMyOrder.do?method=list&order_type=&order_state=40&is_comment=0',
    daituihuotuikuan:'m/MMyTuiHuo.do?method=list',
    shoucang_count:'m/MMyFav.do',
    myScore:'m/MMyScore.do',
    qianBao:'m/MMyQianBao.do',
    user_logo:'',
    upLevelNeedPayMoney:'',
    userInfo:'',
    user_name:'',
    datas:"",
    dai_fukuan_num: '',
    dai_fahuo_num: '',
    dai_shouhuo_num: '',
    dai_pingjia_num: '',
    tuihuo_num: '',
    msg_count: '',
    pintuan:'m/MMyOrder.do?method=list&order_type=100&order_state=10',
  },
  mounted: function() {
    this.$nextTick(function() {
    	vm.getAjaxData();
    });
  },
  methods: {
  	getAjaxData: function() {
  		Common.getData({
			route: 'm/MMyHome.do?method=getAjaxData',
			success: function(data) {
				if(data.code == 0) {
				  mui.alert(data.msg);
	              return false;
	            } else if(data.code == 1) {
	            	vm.datas = data.datas;
	            	vm.userInfo = data.datas.userInfo;
	            	if(null != data.datas.userInfo.user_logo){
		            	vm.user_logo = data.datas.userInfo.user_logo;
		            	if(null !=vm.user_logo && "" != vm.user_logo && vm.user_logo.indexOf("http://") == -1){
		            		vm.user_logo = vm.ctx + vm.user_logo + '@s100x100';
		            	}
		            	vm.user_name = data.datas.userInfo.user_name;
		            	if(null != data.datas.userInfo.real_name){
		            		vm.user_name = data.datas.userInfo.real_name;
		            	}
		         
	            	}
	            	vm.upLevelNeedPayMoney = data.datas.upLevelNeedPayMoney;
	            	vm.dai_fukuan_num = data.datas.dai_fukuan_num;
	            	vm.dai_fahuo_num = data.datas.dai_fahuo_num;
	            	vm.dai_shouhuo_num = data.datas.dai_shouhuo_num;
	            	vm.dai_pingjia_num = data.datas.dai_pingjia_num;
	            	vm.tuihuo_num = data.datas.tuihuo_num;
	            	vm.msg_count = data.datas.msg_count;
	            }
			},
			error: function() {
				mui.alert('好像出错了哦~');
			}
  		});
    },
    openUrl: function(url) {
		goUrl(url);
    },
    toUpLevel: function() {
    	if( null == vm.userInfo.mobile || "" ==vm.userInfo.mobile || vm.userInfo.mobile.length == 0){
    		mui.toast('请先绑定手机号！'); 
    		var url = vm.ctx + "m/MMySecurityCenter.do?method=setMobile";
			goUrl(url);
    	}else{
    	   	//去选择支付方式
        	Common.confirm("付费会员将缴费"+ vm.upLevelNeedPayMoney +"元,你确定要升级成为付费会员吗？",["确定","取消"],function(){
        		var url = vm.ctx + "m/MIndexPayment.do?method=PayForUpLevel";
    			goUrl(url);
    		},function(){
    		});
    	}
    },
    showJoinVillage: function() {
    	var html = $("#JoinVillage").html();
    	Common.showLayUi(html,true);
    },
    renzhen:function(){
    	var url = vm.ctx + "m/MMySecurityCenter.do?method=setRenzheng";
		goUrl(url);
    }
  }
});

</script>
</body>
</html>
