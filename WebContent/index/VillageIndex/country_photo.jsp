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
<link href="${ctx}/styles/mui/css/mui.min.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx}/m/styles/village/css/style.css" />
<link rel="stylesheet" href="${ctx}/m/styles/village/css/content.css" />
<link href="${ctx}/scripts/lightbox/css/lightbox.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/scripts/colorbox/style3/colorbox.css" rel="stylesheet" type="text/css" />
<style type="text/css">
	.viewImgMain{
		width: 20%;
    	float: left;
    	padding: 0 1.5%;
        margin: 5px 0;
	}
	img {
		width: 100%;
		border-radius: 5px;
	}
	.mui-media-body{
	    height: 16px;
    line-height: 16px;
    text-align: center;
    width: 100%;
    overflow: hidden;
    margin-top: 1vh;}
</style>
</head>

<body style="font-size:17px!important;">

<div id="app" v-cloak>
	<input type="hidden" v-model="startPage" />
	<div id="pullrefresh" class="mui-content mui-scroll-wrapper" style="background-color: #fff;padding-bottom: 54px;padding-top: 44px;">
		<div class="mui-scroll">
			<div>
				<div>
					<a class="viewImgMain" :rel="'view' + 1" v-for="(item,index) in datas.entityList" :href="ctx + item.save_path" :title="item.file_desc">
						<img class="mui-media-object pop-img" :src="ctx + item.save_path+'@s400x400'">
						<div class="mui-media-body" v-if="'' != item.file_desc">{{item.file_desc}}</div>
					</a>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script>
<script type="text/javascript" src="${ctx}/styles/mui/mui.min.js"></script>
<script type="text/javascript" src="${ctx}/styles/mui/common.js"></script>
<script type="text/javascript" src="${ctx}/scripts/vue/vue.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/colorbox/jquery.colorbox.min.js"></script>
<script type="text/javascript">
	var vm = new Vue({
		el: '#app',
		data: {
			datas: "",
			ctx: Common.api,
			startPage: 0,
		},
		mounted: function() {
			this.$nextTick(function() {
				this.getAjaxData();
			});
		},
		updated: function() {
			$("a.viewImgMain").colorbox({transition:"none", width:"800",height:"600"});
			
			mui.init({
				pullRefresh: {
					container: '#pullrefresh',
					down: {},
					up: {
						contentrefresh: '正在加载...',
						callback: pullupRefresh
					}
				}
			});
			var count = 0;

			function pullupRefresh() {
				setTimeout(function() {
					Common.getData({
						route: '/m/MCityCenter.do?method=getAjaxDataPhoto&id=${af.map.id}',
						data: {
							startPage: vm.startPage
						},
						success: function(data) {
							if(data.code == 0) {
								mui.toast(data.msg);
								return false;
							} else if(data.code == 1) {
								vm.datas.entityList = vm.datas.entityList.concat(data.datas.entityList);
								vm.startPage = vm.startPage + 1;
								mui('#pullrefresh').pullRefresh().endPullupToRefresh((data.datas.entityList.length < 10));
							}
						},
						error: function() {
							mui.toast('好像出错了哦~');
						}
					});
				}, 1500);
			}
		},
		methods: {
			getAjaxData: function() {
				Common.getData({
					route: '/m/MCityCenter.do?method=getAjaxDataPhoto&id=${af.map.id}',
					data: {
						startPage: vm.startPage
					},
					success: function(data) {
						if(data.code == 0) {
							mui.toast(data.msg);
							return false;
						} else if(data.code == 1) {
							vm.datas = data.datas;
							vm.startPage = vm.startPage + 1;
						}
					},
					error: function() {
						mui.toast('好像出错了哦~');
					}
				});
			},
			openUrl: function(item) {
				var link_url = vm.ctx + "m/MUserCenter.do?method=index&user_id=" + item.user_id;
				goUrl(link_url);
			},
		}
	});
</script>
</body>

</html>