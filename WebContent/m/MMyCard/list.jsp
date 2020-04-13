<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${app_name_min}触屏版</title>
<meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0" name="viewport" />
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<jsp:include page="../_public_in_head.jsp" flush="true" />
<link href="${ctx}/m/styles/erweima/card.css?v=20180319" rel="stylesheet" type="text/css" />
<style type="text/css">
p{margin-bottom:0px!important;}
.c-hd section.back a{box-sizing: unset;}
</style>
</head>
<body>
	<jsp:include page="../_header.jsp" flush="true" />
	 <div class="content" id="app" v-cloak>
		<div class="card">
			<div class="left">
			 <img class="img" :src="user_logo" v-if="user_logo != ''"/> 
			 <img class="img" src="${ctx}/styles/images/user.png" width="50" v-else/> 
			 <p class="msg1">{{userInfo.user_name}}</p>
			 <p class="msg2">{{userInfo.real_name}}</p>
			</div>
			<div class="right">
				<img src="${ctx}/m/styles/erweima/code1.png"  @click="openUrl(ctx + 'm/MMyQrCode.do?method=myRegisterCode')"/>
			</div>
			<div class="bottom">
				<p class="msg1">{{userInfo.mobile}}</p>
				<p class="msg2" v-if="null != userInfo.card_end_date">{{userInfo.card_end_date | formatDateYmd}}</p>
			</div>
		</div>
		<div class="items">
			<div class="item" @click="openUrl(ctx + myScore)">
				<p class="p1">积分</p>
				<p class="p2">{{userInfo.cur_score}}</p>
			</div>
			<div class="item" @click="openUrl(ctx + 'm/MMyContact.do?method=list')">
				<p class="p1">关注</p>
				<p class="p2">{{datas.guanzhu_count}}</p>
			</div>
			<div class="item" @click="openUrl(ctx + qianBao)">
				<p class="p1">余额</p>
				<p class="p2">{{userInfo.bi_dianzi}}</p>
			</div>
		</div>
			<div class="lists">
			<div class="list" @click ="openUrl(ctx + 'm/MMyCard.do?method=userRight')">
				<div class="left">会员权益</div>
				<div class="right" >查看</div>
			</div>
		</div>
			<div class="lists">
			<div class="list" @click ="xufei('m/MMyCard.do')">
				<div class="left">会员卡续费</div>
				   <div class="right" v-if="userInfo.user_level===201">激活</div>
    				<div class="right"v-else-if="userInfo.user_level===202"> 续费</div>
			</div>
		</div>
	
			<div class="lists">
			<div class="list"  @click="openUrl(ctx + 'weixin/WeixinLogin.do?method=subscribe')">
				<div class="left">${app_name}公众号</div>
				<div class="right">关注</div>
			</div>
		</div>
	 </div>
  <jsp:include page="../_footer.jsp" flush="true" />
  <script type="text/javascript" src="${ctx}/styles/mui/layer.js"></script>
 <script type="text/javascript">
		 var vm = new Vue({
			el: '#app',
			data: {
				datas: "",
				ctx: Common.api,
				user_logo:'',
				userInfo: "",
				myScore:'m/MMyScore.do',
				qianBao:'m/MMyQianBao.do',
				upLevelNeedPayMoney:'',
			},
			mounted: function() {
				this.$nextTick(function() {
					this.getAjaxData();
				});
			},
			updated: function() {},
			methods: {
				getAjaxData: function() {
					Common.getData({
						route: 'm/MMyCard.do?method=getAjaxData&user_id=${userInfo.id}',
						success: function(data) {
							if(data.code == 0) {
								mui.toast(data.msg);
								return false;
							} else if(data.code == 1) {
								vm.datas = data.datas;
								vm.userInfo = data.datas.userInfo;
								vm.upLevelNeedPayMoney = data.datas.upLevelNeedPayMoney;
							 	if(null != data.datas.userInfo.user_logo){
					            	vm.user_logo = data.datas.userInfo.user_logo;
					            	if(null !=vm.user_logo && "" != vm.user_logo && vm.user_logo.indexOf("http://") == -1){
					            		vm.user_logo = vm.ctx + vm.user_logo + '@s100x100';
					            	}
				            	}
							}
						},
						error: function() {
							mui.toast('好像出错了哦~'); 
						}
					});
				},
			    openUrl: function(url) {
					goUrl(url);
			    },
			    xufei: function(url) {
			    	if( null == vm.userInfo.mobile || "" ==vm.userInfo.mobile || vm.userInfo.mobile.length == 0){
			    		mui.toast('请先绑定手机号！'); 
			     		var url = vm.ctx + "m/MMySecurityCenter.do?method=setMobile";
			 			goUrl(url);
			     	}else{
					    Common.confirm("续费会员将缴费"+ vm.upLevelNeedPayMoney +"元,你确定要续费会员吗？",["确定","取消"],function(){
				    		var url = vm.ctx + "m/MIndexPayment.do?method=PayForUpLevel";
							goUrl(url);
						},function(){
						});
				  }
			    },
			}
		});
  </script>
</body>
</html>