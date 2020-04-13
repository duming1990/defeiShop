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
<link rel="stylesheet" href="${ctx}/m/styles/village/css/style.css" />
<link rel="stylesheet" href="${ctx}/m/styles/village/css/content.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/m/scripts/lightGallery/css/lightgallery.css"/>
<style type="text/css">
	.lightgallery a {
		width: 47%;
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
<jsp:include page="../_header.jsp" flush="true" />

<div id="app" v-cloak>
	<input type="hidden" v-model="startPage" />
	<div id="pullrefresh" class="mui-content mui-scroll-wrapper" style="background-color: #fff;padding-bottom: 54px;padding-top: 44px;">
		<div class="mui-scroll">
			<div>
				<div class="lightgallery">
					<a class="mui-control-item" v-for="(item,index) in datas.entityList" :href="ctx + item.save_path+'@compress'" :title="item.file_desc">
						<img class="mui-media-object pop-img" :src="ctx + item.save_path+'@s400x400'">
						<div class="mui-media-body" v-if="'' != item.file_desc" style="height: 56px;">{{item.file_desc}}</div>
					</a>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript" src="${ctx}/m/scripts/lightGallery/js/lightgallery-all.min.js"></script> 
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
							route: '/m/MServiceCenterInfo.do?method=getAjaxDataPhoto&id=${af.map.id}',
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
				
			});
		},
		updated: function() {
			
			$('.lightgallery').lightGallery({download:false});
			mui('.lightgallery').on('tap', 'a', function(){
		   		$(this).click();
	   	    });
			
		},
		methods: {
			getAjaxData: function() {
				Common.getData({
					route: '/m/MServiceCenterInfo.do?method=getAjaxDataPhoto&id=${af.map.id}',
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