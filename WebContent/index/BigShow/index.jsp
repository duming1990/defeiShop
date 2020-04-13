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
.mui-toast-container {bottom: 7% !important;}
.mui-toast-message {background: url(/app/themes/default/images/toast.png) no-repeat center 10px #000; opacity: 0.6; color: #fff; width: 180px; padding: auto;}</style>
</head>
<body>
<div class="allpage" id="bigShow" v-cloak>
    <div class="top">
		${bigShow.file_name}
    </div>
    <div class="body">
        <div class="bleft">
            <div class="left_top">
                <div class="left_top_title">
                    <div class="lin"></div>
                    <div class="title_t">实时销售版</div>
                </div>
                <div class="letf_list">
                    <table>
                        <thead>
                        <tr>
                            <th style="width:80px">贫困户</th>
                            <th style="width:120px">所在村</th>
                            <th style="width:90px">产品</th>
                            <th style="width:80px">销售额</th>
                            <th style="width:90px">时间</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr v-for="(item,index) in poorSalesRealtimeList" :style="index%2 != 1 ? 'background:#0a5cc5;' : 'background:#2580da;'">
                            <td><div class="tdDiv" :style="item.map.isNew==1 ? 'color:#00ff5a;width:80px':'color:#ffffff;width:80px'">{{item.map.real_name}}</div></td>
                            <td><div class="tdDiv" :style="item.map.isNew==1 ? 'color:#00ff5a;width:120px':'color:#ffffff;width:120px'">{{item.map.village_name}}</div></td>
                            <td><div class="tdDiv" :style="item.map.isNew==1 ? 'color:#00ff5a;width:90px':'color:#ffffff;width:90px'">{{item.map.comm_name}}</div></td>
                            <td><div class="tdDiv" :style="item.map.isNew==1 ? 'color:#00ff5a;width:80px':'color:#ffffff;width:80px'">{{item.map.good_price}}</div></td>
                            <td><div class="tdDiv" :style="item.map.isNew==1 ? 'color:#00ff5a;width:90px':'color:#ffffff;width:90px'">{{item.map.add_date | formatDateYmd}}</div></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="left_bottm">
                <div class="left_top_title">
                    <div class="lin"></div>
                    <div class="title_t">排名榜</div>
                </div>
                <div class="left_bottm_li">
                    <div class="left_bottm_li1">
                        <div class="left_bottm_list" v-for="(item,index) in poorSaleRankList">
						<img :src="ctx + item.map.user_logo +'@s200x200'" onerror="this.src='${ctx}/styles/images/user.png'">
                            <div class="left_bottm_num" >
                               {{index+1}}
                            </div>
                            <div class="left_bottm_name">
                                <span>{{item.map.village_name}}</span>
                            </div>
                            <div class="left_bottm_name">
                                <span>{{item.map.real_name}}</span>
                            </div>
                            <div class="left_bottm_name">{{item.map.good_price}}元</div>
                        </div>
                    </div>
            </div>
        </div>
        </div>
        <div class="bcenter"  style="background: url(${ctx}/${bigShow.save_path}) no-repeat">
            <div class="left_top_title">
            </div>
        </div>
        <div class="bright">
            <div class="bright_top">

                <div class="left_top_title">
                    <div class="lin"></div>
                    <div class="title_t">企业帮扶动态</div>
                </div>
                <div class="bright_list" style="height: 397px;width: 470px;margin-left: 9px;overflow: hidden;">
                    <table>
                        <thead>
                        <tr>
                            <th style="width:80px">企业</th>
                            <th style="width:120px">贫困户</th>
                            <th style="width:90px">产品</th>
                            <th style="width:80px">帮扶金</th>
                            <th style="width:90px">时间</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr v-for="(item,index) in corporateHelpDynamicList" :style="index%2 != 1 ? 'background:#0a5cc5;' : 'background:#2580da;'">
                            <td><div class="tdDiv" :style="item.map.isNew==1 ? 'color:#00ff5a;width:80px':'color:#ffffff;width:80px'">{{item.map.entp_name}}</div></td>
                            <td><div class="tdDiv" :style="item.map.isNew==1 ? 'color:#00ff5a;width:120px':'color:#ffffff;width:120px'">{{item.map.real_name}}</div></td>
                            <td><div class="tdDiv" :style="item.map.isNew==1 ? 'color:#00ff5a;width:90px':'color:#ffffff;width:90px'">{{item.map.comm_name}}</div></td>
                            <td><div class="tdDiv" :style="item.map.isNew==1 ? 'color:#00ff5a;width:80px':'color:#ffffff;width:80px'">{{item.map.bi_no}}</div></td>
                            <td><div class="tdDiv" :style="item.map.isNew==1 ? 'color:#00ff5a;width:90px':'color:#ffffff;width:90px'">{{item.map.add_date | formatDateYmd}}</div></td>
                        </tr>
                        </tbody>
                    </table>
                </div>

            </div>
            <div class="bright_bottm">
                <div class="left_top_title">
                    <div class="lin"></div>
                    <div class="title_t">企业帮扶榜</div>
                </div>
                <div class="bright_bottm_list">
                    <table>
                        <thead>
                        <tr>
                            <th class="">序号</th>
                            <th class="">企业</th>
                            <th class="">扶贫总额</th>
                        </tr>
                        </thead >
                        <tbody>
                        <tr v-for="(item,index) in corporateHelpList" :style="index%2 != 1 ? 'background:#0a5cc5;' : 'background:#2580da;'">
                            <td>
                                <div class="bright_bottm_list_num" >
                                    {{index+1}}
                                </div>
                            </td>
                            <td>{{item.map.entp_name}}</td>
                            <td>{{item.map.sum_aid_money}}元</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <div style="display:none;">
    <audio src=" ${ctx}/scripts/jwplayer/Windows.wav" controls="controls" id="video_path_div">
	</audio>
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
		poorSaleRankList:new Array(),
		corporateHelpList:new Array(),
		poorSalesRealtimeList:new Array(),
		corporateHelpDynamicList:new Array(),
	},
	mounted: function() {
		this.$nextTick(function() {
			Common.loading();
			this.getAjaxData();
			this.getAjaxDataCorporateHelp();
			this.getAjaxDataPoorSalesRealtime();
			this.getAjaxDataCorporateHelpDynamic();
		});
	},
	updated: function() {
	},
	methods: {
		getAjaxData: function() {//排名榜
			Common.getData({
				route: 'BigShow.do?method=getAjaxDataPoorSalesRanking',
				data: {
					id: "${af.map.id}",
				},
				success: function(data) {
					if(data.code == 0) {
						mui.toast(data.msg);
						return false;
					}else if(data.code == 1) {
						Common.hide();
						vm.poorSaleRankList = data.datas.poorSaleRankList;
					}
				},
				error: function() {
					mui.toast('好像出错了哦~');
				}
			});
		},
		getAjaxDataCorporateHelp: function() {//企业帮扶帮
			Common.getData({
				route: 'BigShow.do?method=getAjaxDataCorporateHelp',
				data:{
					id: "${af.map.id}",
				},
				success: function(data) {
					if(data.code == 0) {
						mui.toast(data.msg);
						return false;
					}else if(data.code == 1) {
						Common.hide();
						vm.corporateHelpList = data.datas.corporateHelpList;
					}
				},
				error: function() {
					mui.toast('好像出错了哦~');
				}
			});
		},
		getAjaxDataPoorSalesRealtime: function(getMarqueeData) {//实时销售榜
			var data = {
					id: "${af.map.id}"
			};
			if(getMarqueeData && vm.poorSalesRealtimeList.length > 0){
				data = {
						id: "${af.map.id}",
						last_id: vm.poorSalesRealtimeList[0].map.id
				};
			}
			Common.getData({
				route: 'BigShow.do?method=getAjaxDataPoorSalesRealtime',
				data: data,
				success: function(data) {
					if(data.code == 0) {
						mui.toast(data.msg);
						return false;
					}else if(data.code == 1) {
						Common.hide();
						if(getMarqueeData){
							if(data.datas.poorSalesRealtimeList!=''){
								var list = vm.poorSalesRealtimeList;
								for(var i = 0;i <list.length;i++){
									Vue.set(list[i].map, 'isNew', 0);
								}
								var list = data.datas.poorSalesRealtimeList;
								for(var i = list.length-1 ; i >= 0 ; i--){
									Vue.set(list[i].map, 'isNew', 1);
									vm.poorSalesRealtimeList.splice(0,0,list[i]);
								}
							}
						}else{
						   vm.poorSalesRealtimeList = data.datas.poorSalesRealtimeList;
						}
					}
				},
				error: function() {
					mui.toast('好像出错了哦~');
				}
			});
		},
		getAjaxDataCorporateHelpDynamic: function(getCorporateHelpDynamicData) {//企业帮扶动态
			var data = {
					id: "${af.map.id}"
			};
			if(getCorporateHelpDynamicData && vm.corporateHelpDynamicList.length > 0){
				data = {
						id: "${af.map.id}",
						last_id: vm.corporateHelpDynamicList[0].map.id
				};
			}
			Common.getData({
				route: 'BigShow.do?method=getAjaxDataCorporateHelpDynamic',
				data: data,
				success: function(data) {
					if(data.code == 0) {
						mui.toast(data.msg);
						return false;
					}else if(data.code == 1) {
						Common.hide();
						if(getCorporateHelpDynamicData){
							if(data.datas.corporateHelpDynamicList!=''){
								var list = vm.corporateHelpDynamicList;
								for(var i = 0;i <list.length;i++){//将前面的样式去掉
									Vue.set(list[i].map, 'isNew', 0);
								}
								var list = data.datas.corporateHelpDynamicList;
								for(var i = list.length-1;i >=0;i--){
									Vue.set(list[i].map, 'isNew', 1);//新数据的样式
									vm.corporateHelpDynamicList.splice(0,0,list[i]);
								}
							}
						}else{
							vm.corporateHelpDynamicList = data.datas.corporateHelpDynamicList;
						}
					}
				},
				error: function() {
					mui.toast('好像出错了哦~');
				}
			});
		},
	}
});

var thePlayer;
var ctx = "${ctx}";
$(document).ready(function(){
	$('body').everyTime('5s',function(){
		var data = {
				id: "${af.map.id}"
		};
		if(vm.poorSalesRealtimeList.length > 0){
			data = {
					id: "${af.map.id}",
					last_id: vm.poorSalesRealtimeList[0].map.id
			};
		}
		$.ajax({
			type: "POST",
			url: ctx + "/BigShow.do?method=getHasNewOrder",
			data:data,
			dataType: "json",
			error: function(request, settings) {},
			success: function(data) {
				if(data.ret == 1){
					autoPlayer();
					mui.toast('有一个新的订单！',{ duration:'short', type:'div' }) 
				}
			}
		});	
	 });
	$('body').everyTime('6s',function(){
		var data = {
				id: "${af.map.id}"
		};
		if(vm.corporateHelpDynamicList.length > 0){
			data = {
					id: "${af.map.id}",
					last_id: vm.corporateHelpDynamicList[0].map.id
			};
		}
		$.ajax({
			type: "POST",
			url: ctx + "/BigShow.do?method=getEntpHasNewOrder",
			data:data,
			dataType: "json",
			error: function(request, settings) {},
			success: function(data) {
				if(data.ret == 1){
					autoPlayer();
					mui.toast('有一笔新的扶贫金！',{ duration:'short', type:'div' }) 
				}
			}
		});	
	 });
});

function autoPlayer(){
	document.getElementById("video_path_div").play();
} 

//每5秒钟去查询一次
setInterval("getBalanceDeskInfo()",5000);
function getBalanceDeskInfo(){
  vm.getAjaxData(true); 
  vm.getAjaxDataPoorSalesRealtime(true);
  vm.getAjaxDataCorporateHelp(true);
  vm.getAjaxDataCorporateHelpDynamic(true);
}
</script>
</html>