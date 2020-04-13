<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>大屏显示 - ${app_name}</title>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<meta content="telephone=no" name="format-detection" />
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script>
<script type="text/javascript" src="${ctx}/scripts/vue/vue.min.js"></script>
<script type="text/javascript" src="${ctx}/styles/mui/mui.min.js"></script>
<script type="text/javascript" src="${ctx}/m/scripts/public.js?v02026"></script>
<script type="text/javascript" src="${ctx}/styles/mui/common.js?v02026"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script> 
<script type="text/javascript" src="${ctx}/scripts/jquery.timers.js"></script>

<link href="${ctx}/styles/mui/css/mui.min.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/bigShow/css/nine.css" rel="stylesheet" type="text/css" />
<style type="">
.countAndMoney{
	height:25px;
	margin-top:30px;
	margin-right:50px;
	float:right;
}
.countAndMoney span{
	font-size:20px;
	font-weight: bold;
}
.mui-toast-container {bottom: 7% !important;}
tr td{
line-height: 100px;
}
.font{
	white-space:nowrap;
    text-overflow:ellipsis;
    -o-text-overflow:ellipsis;
    overflow: hidden;
}
</style>
</head>
<body>

 
<div style="background:url('${ctx}/styles/bigShow/img/background_2.jpg') no-repeat;background-size:100%;height:768px;" id="bigShow" v-cloak>
    <div class="top">
		<div class="title_t" style="text-align:center"> ${af.map.activity.title }</div>
    </div>
    <hr></hr>
    <div>
        <div>
                <div  style="width: 100%;overflow: hidden;height:500px;">
                    <table align="center">
	                        <tr>
								<th width="5%">序号</th>
								<th width="10%">商家名称</th>
								<th width="10%">产品名称</th>
								<th width="10%">规格</th>
								<th width="10%">数量</th>
								<th width="10%">订单号</th>
								<th width="10%">交易额</th>
								<th width="10%">交易时间</th>
							</tr>
                        <!-- <table align="center">
							<tr align="center" v-for="(item,index) in orderInfoList" :style="index%2 != 1 ? 'background:#0a5cc5;' : 'background:#2580da;'">
								<td>{{index+1}}</td>
								<td><div :style="item.map.isNew==1 ? 'color:#00ff5a;':'color:#ffffff;'">{{item.entp_name}}</div></td>
								<td><table id="tableInside" width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr align="center" v-for="oids in item.orderInfoDetailsList">
										<td><div :style="item.map.isNew==1 ? 'color:#00ff5a;':'color:#ffffff;'">{{oids.comm_name}}</div></td>
										</tr>
								</table></td>
								<td><table id="tableInside" width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr align="center" v-for="oids in item.orderInfoDetailsList">
										<td><div :style="item.map.isNew==1 ? 'color:#00ff5a;':'color:#ffffff;'">{{oids.comm_tczh_name}}</div></td>
										</tr>
								</table></td>
								<td><table id="tableInside" width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr align="center" v-for="oids in item.orderInfoDetailsList">
											<td><div  :style="item.map.isNew==1 ? 'color:#00ff5a;':'color:#ffffff;'">{{oids.good_count}}</div></td>
										</tr>
								</table></td>
								<td><div :style="item.map.isNew==1 ? 'color:#00ff5a;':'color:#ffffff;'">{{item.trade_index}}</div></td>
								<td><div :style="item.map.isNew==1 ? 'color:#00ff5a;':'color:#ffffff;'">{{item.order_money + item.money_bi}}</div></td>
								<td><div :style="item.map.isNew==1 ? 'color:#00ff5a;':'color:#ffffff;'">{{item.pay_date | formatDateYmdHms}}</div></td>
							</tr>
                    	</table> -->
					<table align="center" bgcolor="#cccccc" border="0" cellpadding="0"
						cellspacing="0" width="100%">
						<tbody>
							<tr>
								<td>
									<div id="demohq" width="100%"
										style="overflow: hidden; height:500px;background:#0a5cc5">
										<div id="demohq1" align="center">
											<table align="center">
							<tr align="center" v-for="(item,index) in orderInfoList" :style="index%2 != 1 ? 'background:#0a5cc5;' : 'background:#2580da;'">
								<td width="5%">{{index+1}}</td>
								<td width="10%"><div :style="item.map.isNew==1 ? 'color:#00ff5a;':'color:#ffffff;'" style="width:190px;" class="font">{{item.entp_name}}</div></td>
								<td width="10%"><table id="tableInside" width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr align="center" v-for="oids in item.orderInfoDetailsList">
										<td><div :style="item.map.isNew==1 ? 'color:#00ff5a;':'color:#ffffff;'" class="font" style="width:190px;">{{oids.comm_name}}</div></td>
										</tr>
								</table></td>
								<td width="10%"><table id="tableInside" width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr align="center" v-for="oids in item.orderInfoDetailsList">
										<td><div :style="item.map.isNew==1 ? 'color:#00ff5a;':'color:#ffffff;'" class="font" style="width:190px;">{{oids.comm_tczh_name}}</div></td>
										</tr>
								</table></td>
								<td width="10%"><table id="tableInside" width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr align="center" v-for="oids in item.orderInfoDetailsList">
											<td><div  :style="item.map.isNew==1 ? 'color:#00ff5a;':'color:#ffffff;'">{{oids.good_count}}</div></td>
										</tr>
								</table></td>
								<td width="10%"><div :style="item.map.isNew==1 ? 'color:#00ff5a;':'color:#ffffff;'">{{item.trade_index}}</div></td>
								<td width="10%"><div :style="item.map.isNew==1 ? 'color:#00ff5a;':'color:#ffffff;'">{{item.order_money + item.money_bi}}</div></td>
								<td width="10%"><div :style="item.map.isNew==1 ? 'color:#00ff5a;':'color:#ffffff;'">{{item.pay_date | formatDateYmdHms}}</div></td>
							</tr>
                    	</table>
										</div>
										<div id="demohq2" align="center">
											<table align="center"><tbody>
							<tr align="center" v-for="(item,index) in orderInfoList" :style="index%2 != 1 ? 'background:#0a5cc5;' : 'background:#2580da;'">
								<td width="5%">{{index+1}}</td>
								<td width="10%"><div :style="item.map.isNew==1 ? 'color:#00ff5a;':'color:#ffffff;'" class="font" style="width:190px;">{{item.entp_name}}</div></td>
								<td width="10%"><table id="tableInside" width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr align="center" v-for="oids in item.orderInfoDetailsList">
										<td><div :style="item.map.isNew==1 ? 'color:#00ff5a;':'color:#ffffff;'" class="font" style="width:190px;">{{oids.comm_name}}</div></td>
										</tr>
								</table></td>
								<td width="10%"><table id="tableInside" width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr align="center" v-for="oids in item.orderInfoDetailsList">
										<td><div :style="item.map.isNew==1 ? 'color:#00ff5a;':'color:#ffffff;'" class="font" style="width:190px;">{{oids.comm_tczh_name}}</div></td>
										</tr>
								</table></td>
								<td width="10%"><table id="tableInside" width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr align="center" v-for="oids in item.orderInfoDetailsList">
											<td><div  :style="item.map.isNew==1 ? 'color:#00ff5a;':'color:#ffffff;'">{{oids.good_count}}</div></td>
										</tr>
								</table></td>
								<td width="10%"><div :style="item.map.isNew==1 ? 'color:#00ff5a;':'color:#ffffff;'">{{item.trade_index}}</div></td>
								<td width="10%"><div :style="item.map.isNew==1 ? 'color:#00ff5a;':'color:#ffffff;'">{{item.order_money + item.money_bi}}</div></td>
								<td width="10%"><div :style="item.map.isNew==1 ? 'color:#00ff5a;':'color:#ffffff;'">{{item.pay_date | formatDateYmdHms}}</div></td>
							</tr>
                    	</tbody></table>
										</div>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
					</table>
                </div>

    </div>
					<hr></hr>
    <div class="countAndMoney">
			<span>今日总订单数：{{orderCount}}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<span>今日总交易额：{{orderSumMoney}}</span>
			</div>
</div>
</body>
<script type="text/javascript" src="${ctx}/styles/mui/mui.pullToRefresh.js"></script>
<script type="text/javascript" src="${ctx}/styles/mui/mui.pullToRefresh.material.js"></script>
<script type="text/javascript" src="${ctx}/styles/mui/poppicker/mui.picker.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/swiper/swiper.min.js"></script>
<script type="text/javascript" src="${ctx}/styles/mui/layer.js"></script>
<script type="text/javascript" src="${ctx}/m/scripts/lightGallery/js/lightgallery-all.min.js?20180530"></script> 
<script type="text/javascript">

var vm = new Vue({
	el: '#bigShow',
	data: {
		datas: "",
		ctx: Common.api,
		p_index: '',
		orderInfoList:new Array(),
		orderCount:0,
		orderSumMoney:0,
	},
	mounted: function() {
		this.$nextTick(function() {
			Common.loading();
			this.getAjaxData();
		});
	},
	updated: function() {
	},
	methods: {
		getAjaxData: function(getAjaxData) {
			 var data = {
					id: "${af.map.id}",
					pageSize:"${af.map.pageSize}",
					orderDate:"${af.map.orderDate}"
			};
			if(getAjaxData && vm.orderInfoList.length > 0){
				data = {
						id: "${af.map.id}",
						pageSize:"${af.map.pageSize}",
						last_pay_date: vm.orderInfoList[0].pay_date,
						orderDate:"${af.map.orderDate}"
				};
			} 
			Common.getData({
				route: '/BigShow.do?method=getAjaxDataBigShow',
				data: data,
				success: function(data) {
					if(data.code == 0) {
						mui.toast(data.msg);
						return false;
					}else if(data.code == 1) {
						Common.hide();
						if(getAjaxData){
							if(data.datas.orderInfoList!=''){
								var list = vm.orderInfoList;
								for(var i = 0;i <list.length;i++){//将前面的样式去掉
									Vue.set(list[i].map, 'isNew', 0);
								}
								var oldLength = list.length; 
								
								var list = data.datas.orderInfoList;
								var newLength = list.length;
								
								for(var i = list.length-1;i >=0;i--){
									Vue.set(list[i].map, 'isNew', 1);//新数据的样式
									vm.orderInfoList.splice(0,0,list[i]);
								}
							}
								if(vm.orderInfoList.length<=5){
									$("#demohq2").hide()
								}else{
									$("#demohq2").show()
								}
							vm.orderSumMoney = data.datas.orderSumMoney;
							vm.orderCount = data.datas.orderCount;
						}else{
							vm.orderInfoList = data.datas.orderInfoList;
							vm.orderSumMoney = data.datas.orderSumMoney;
							vm.orderCount = data.datas.orderCount;
							if(vm.orderInfoList.length<=5){
								$("#demohq2").hide()
							}else{
								$("#demohq2").show()
							}
						}
					}
				},
				error: function() {
					mui.toast('好像出错了哦~');
				}
			});
		}
	},
});
	

//每5秒钟去查询一次
setInterval("getBalanceDeskInfo()",2000);
function getBalanceDeskInfo(){
  vm.getAjaxData(true); 
}

$(document).ready(function(){
	var speedhq = 60;
	function Marqueehq() {
		
		if (demohq.scrollTop == demohq1.offsetHeight) {
			demohq.scrollTop = 0;
		} else {
			demohq.scrollTop++;
		}
	}
	var MyMarhq = setInterval(Marqueehq, 60)
	demohq.onmouseover = function() {
		clearInterval(MyMarhq)
	}
	demohq.onmouseout = function() {
		MyMarhq = setInterval(Marqueehq, speedhq)
	}
	})
</script>
</html>