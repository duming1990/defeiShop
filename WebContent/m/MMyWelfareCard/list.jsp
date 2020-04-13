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
<link href="${ctx}/m/styles/erweima/card.css" rel="stylesheet" type="text/css" />
<style type="text/css">
p{margin-bottom:0px!important;}
.c-hd section.back a{box-sizing: unset;}
.items{
	 height: auto;
	 border-bottom: 1px solid #eee;
	 padding: 1px 0;
	 margin: 9px 4px;
	 padding-bottom: 11px;
}
.card .right p{
	line-height: 30px;
	font-size: 0.3rem;
	color: #fff;
	margin-right: 15px;
}
.card .middle{
	text-align: center;
}
.card .middle .msg1{
	line-height: 50px;
	font-size: 0.4rem;
	color: #fff;
}
.card .middle .msg2{
	line-height: 20px;
	font-size: 0.25rem;
	color: #fff;
}
.active{
font-size: 14px;
    width: 100%;
    height: 26px;
    margin: 15px 0 0;
    padding: 0 5px;
    border: 1px solid rgba(0,0,0,.3);
    border-radius: 0;
    background: #fff;
}
</style>
</head>
<body>
	<jsp:include page="../_header.jsp" flush="true" />
	 <div class="content" id="app" v-cloak>
		<div class="card" style="background-image: url(${ctx}/m/styles/card_banner.png);" v-for="card in cardList">
			<div class="left"><p class="msg1">福利卡</p></div>
			<div class="right" @tap="openUrl(ctx+'/m/MWelfareShop.do?card_id='+card.id)"><p> 去使用</p></div>
			<div class="middle">
				<p class="msg1">额度：{{card.card_amount}}元</p>
				<p class="msg2">已消费：{{(card.card_amount - card.card_cash) | formatMoney}}元 &nbsp;&nbsp;&nbsp;&nbsp;剩余：{{card.card_cash | formatMoney}}元</p>
			</div>
			<div class="bottom">
				<p class="msg1">截止使用时间：{{card.end_date | formatDate}}</p>
			</div>
		</div>
		<div class="items" style="height: auto;padding-bottom: 16px;padding-top: 5px;">
			<button @tap="activeCard" type="button" class="mui-btn mui-btn-primary mui-btn-outlined" style="color: rgb(0, 0, 0);border:1px solid;border-color: rgb(0, 0, 0) !important;margin-left: 28%;padding: 6px 50px;">激活福利卡</button>
		</div>
	 </div>
	 <jsp:include page="../_footer.jsp" flush="true" />
<script type="text/javascript" src="${ctx}/styles/mui/layer.js"></script>
<script type="text/javascript">
var vm = new Vue({
	el: '#app',
	data: {
		ctx: Common.api,
		cardList:new Array(),
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
				route: 'm/MMyWelfareCard.do?method=getCardInfo',
				success: function(data) {
					if(data.code == 0) {
						mui.toast(data.msg);
						return false;
					} else if(data.code == 1) {
						vm.cardList = data.cardList;
						console.info(vm.cardList);
					}
				},
				error: function() {
					mui.toast('好像出错了哦~'); 
				}
			});
		},
		activeCard:function(){
			mui.confirm('<input class="active" type="text" autocomplete="off" id="card_no" placeholder="请输入卡号"/>'
					   +'<input class="active" type="password" autocomplete="new-password" id="card_pwd" placeholder="请输入密码"/>', '激活福利卡', null, function(event) {  
			    var index = event.index;  
			    if(index === 1) {  
			        var card_no = document.getElementById('card_no').value;  
			        var card_pwd = document.getElementById('card_pwd').value;  
			        Common.getData({
						route: 'm/MMyWelfareCard.do?method=activeCard&card_no='+card_no+'&card_pwd='+card_pwd,
						success: function(data) {
							if(data.code == 0) {
								mui.toast(data.msg);
								return false;
							} else if(data.code == 1) {
								vm.cardList = vm.cardList.concat(data.cardInfo);
								mui.toast(data.msg);
							}
						},
						error: function() {
							mui.toast('好像出错了哦~'); 
						}
					});
			    }else{
			    	 console.log("取消");
			    }  
			},'div');  
		},
	    openUrl: function(url) {
			goUrl(url);
	    },
	}
});
</script>
</body>
</html>