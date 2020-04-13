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
<link rel="stylesheet" href="${ctx}/m/styles/village/css/style-centent.css" />
<script type="text/javascript" src="${ctx}/styles/mui/mui.min.js"></script>
<link href="${ctx}/scripts/colorbox/style3/colorbox.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/public.css" rel="stylesheet" type="text/css" />
<style type="text/css">
	.pub_btn {
		float: left;
		margin-left: 1rem;
		width: 45%;
	}
	
	.mui-pull-bottom-tips {
		text-align: center;
		font-size: 12px;
		line-height: 40px;
		color: #777;
	}
	.mui-table-view:before {
				position: absolute;
				right: 0;
				left: 0;
				height: 1px;
				content: '';
				-webkit-transform: scaleY(.5);
				transform: scaleY(.5);
				background-color: white;
				top: -1px;
			}
			
			.mui-table-view:after {
				position: absolute;
				right: 0;
				bottom: 0;
				left: 0;
				height: 1px;
				content: '';
				-webkit-transform: scaleY(.5);
				transform: scaleY(.5);
				background-color: white;
			}
			
			.mui-table-views.mui-grid-views {
				font-size: 0;
				display: block;
				width: 100%;
				padding: 0;
				white-space: normal;
			}
			
			.mui-table-views {
				position: relative;
				margin-top: 0;
				margin-bottom: 0;
				padding-left: 0;
				list-style: none;
				background-color: #fff;
			}
			
			.mui-table-views.mui-grid-views .mui-table-view-cell {
				font-size: 17px;
				display: inline-block;
				margin-right: 0;
				padding: 0 0 0 10px;
				text-align: center;
				vertical-align: middle;
				background: 0 0;
			}
			
			.mui-table-views.mui-grid-views .mui-table-view-cell>a:not(.mui-btn) {
				margin: 0;
				padding: 0;
			}
			
			.mui-input-row .mui-input-clear~.mui-icon-clear {
				font-size: 20px;
				position: absolute;
				z-index: 1;
				top: 7px;
				right: 0;
				width: 38px;
				height: 38px;
				text-align: center;
				color: #999;
				line-height: 38px;
			}
			
			.mui-search .mui-placeholder {
				font-size: 16px;
				/*line-height: 50px;*/
				position: absolute;
				z-index: 1;
				top: 0;
				right: auto;
				bottom: 0;
				left: 5px;
				display: inline-block;
				/*height: 50px;*/
				text-align: center;
				color: #999;
				border: 0;
				border-radius: 6px;
				background: 0 0;
			}
			
			.mui-table-view-chevron .mui-table-view-cells {
				position: relative;
				overflow: hidden;
				padding: 11px 0;
				-webkit-touch-callout: none;
				text-align: center;
			}
			
			.mui-table-view-cells.mui-collapse.mui-active {
				margin-top: 0;
			}
			
			.mui-table-view-cells.mui-active {
				background-color: #eee;
			}
			
			.mui-table-view-chevron .mui-table-view-cells>a:not(.mui-btn) {
				margin: -11px 0;
				color: #000000;
			}
			
			.mui-table-view-chevron .mui-table-view-cells:after {
				position: absolute;
				right: 0;
				bottom: 0;
				left: 0;
				height: 1px;
				content: '';
				-webkit-transform: scaleY(.5);
				transform: scaleY(.5);
				background-color: #c8c7cc;
			}
			
			.shows {
				display: none;
			}
			
			.shows-active {
				display: block;
			}
			
			.mui-table-view-chevron .mui-table-view-cells.mui-actives>a:not(.mui-btn) {
				color: #bd1c1c;
			}
			
			.mui-actives {
				background-color: #eee;
			}
			
			.over_height {
				overflow-y: scroll !important;
			}
</style>
</head>

<body style="margin: 0 auto;">

		<!-- 侧滑导航根容器 -->
		<div id="offCanvasWrapper" class="mui-off-canvas-wrap mui-draggable mui-slide-in" s>
			<!-- 菜单容器 -->
			<aside id="offCanvasSide" class="mui-off-canvas-right" style="background-color: white;top: 44px;transform: translate3d(0, 0px, 0px);">
				<div id="offCanvasSideScroll" class="mui-scroll-wrapper">
					<div class="mui-scroll scrolls" style="height: 100%;">
						<ul class="mui-table-view mui-table-view-chevron heights" style="width: 23vw;float: right;border-left: 1px solid #eee;">
							<li class="mui-table-view-cells mui-collapse mui-actives" data-id="1">
								<a class="" href="#">推荐区</a>
							</li>
							<li class="mui-table-view-cells mui-collapse" data-id="2">
								<a class="" href="#">个性专区</a>
							</li>
							<li class="mui-table-view-cells mui-collapse" data-id="3">
								<a class="" href="#">冬季专区</a>
							</li>
							<li class="mui-table-view-cells mui-collapse" data-id="4">
								<a class="" href="#">居家</a>
							</li>
							<li class="mui-table-view-cells mui-collapse" data-id="5">
								<a class="" href="#">配件</a>
							</li>
							<li class="mui-table-view-cells mui-collapse" data-id="6">
								<a class="" href="#">服装</a>
							</li>
							<li class="mui-table-view-cells mui-collapse" data-id="7">
								<a class="" href="#">电器</a>
							</li>
						</ul>
						<div class="center-lists shows-active" data-id="1">
							<div style="text-align: center;display: inline-block;width: 22vw;margin: 15px 0;">
								<img src="${ctx}/styles/images/user.png" style="width: 12vw;height: 12vw;border-radius: 50%;overflow: hidden;margin-bottom: 10px;" />
								<div>情侣装</div>
							</div>
							<div style="text-align: center;display: inline-block;width: 22vw;margin: 15px 0;">
								<img src="${ctx}/styles/images/user.png" style="width: 12vw;height: 12vw;border-radius: 50%;overflow: hidden;margin-bottom: 10px;" />
								<div>亲子装</div>
							</div>
							<div style="text-align: center;display: inline-block;width: 22vw;margin: 15px 0;">
								<img src="${ctx}/styles/images/user.png" style="width: 12vw;height: 12vw;border-radius: 50%;overflow: hidden;margin-bottom: 10px;" />
								<div>妆达人</div>
							</div>
							<div style="text-align: center;display: inline-block;width: 22vw;margin: 15px 0;">
								<img src="${ctx}/styles/images/user.png" style="width: 12vw;height: 12vw;border-radius: 50%;overflow: hidden;margin-bottom: 10px;" />
								<div>旅行者</div>
							</div>
						</div>

						<div class="center-lists shows" data-id="2">
							<div style="text-align: center;display: inline-block;width: 22vw;margin: 15px 0;">
								<img src="${ctx}/styles/images/user.png" style="width: 12vw;height: 12vw;border-radius: 50%;overflow: hidden;margin-bottom: 10px;" />
								<div>情侣22装</div>
							</div>
							<div style="text-align: center;display: inline-block;width: 22vw;margin: 15px 0;">
								<img src="${ctx}/styles/images/user.png" style="width: 12vw;height: 12vw;border-radius: 50%;overflow: hidden;margin-bottom: 10px;" />
								<div>亲子22装</div>
							</div>
							<div style="text-align: center;display: inline-block;width: 22vw;margin: 15px 0;">
								<img src="${ctx}/styles/images/user.png" style="width: 12vw;height: 12vw;border-radius: 50%;overflow: hidden;margin-bottom: 10px;" />
								<div>妆达22人</div>
							</div>
							<div style="text-align: center;display: inline-block;width: 22vw;margin: 15px 0;">
								<img src="${ctx}/styles/images/user.png" style="width: 12vw;height: 12vw;border-radius: 50%;overflow: hidden;margin-bottom: 10px;" />
								<div>旅行22者</div>
							</div>
						</div>
					</div>
				</div>
			</aside>
			<!-- 主页面容器 -->
			<div class="mui-inner-wrap mui-transitioning" style="transform: translate3d(0px, 0px, 0px);">
				<header id="header" class="mui-bar mui-bar-nav" style="position: fixed !important;background-color: red!important;z-index: 200000;">
					<div class="dizhi" style="display: inline-block;color: #fff;">
						安庆.太湖县<i class="mui-icon mui-icon-arrowdown"></i>
					</div>
					<div class="mui-search" style="display: inline-block;margin:0 auto;">
						<input type="search" class="mui-input-clear" style="text-align: left;margin-bottom: 0;background-color: #fff;height: 31px;" placeholder="搜索" data-input-clear="1" data-input-search="1">
						<span class="mui-icon mui-icon-clear mui-hidden"></span>
						<span class="mui-placeholder"><span class="mui-icon mui-icon-search" style="padding-top: 11px;"></span></span>
					</div>
					<a href="#offCanvasSide" class="mui-icon mui-icon-bars mui-pull-right" style="padding: 0 10px;font-size: 26px;color: white;">
						<div style="font-size: 14px;">分类</div>
					</a>
				</header>
				<div id="offCanvasContentScroll" class="mui-content mui-scroll-wrapper">
					<div class="mui-scroll" style="transform: translate3d(0px, -67px, 0px) translateZ(0px); transition-duration: 0ms; transition-timing-function: cubic-bezier(0.165, 0.84, 0.44, 1);">
						<div class="cunhead">
						<div class="areasSmall"><a href="${ctx}/manager/admin/MBaseLink.do?mod_id=${af.map.mod_id}&link_type=10" class="beautybg">编辑背景图</a></div>
							<img class="bg" src="${ctx}/m/styles/village/img/cunbg.jpg" />
							<img class="cunlogo" src="${ctx}/styles/imagesPublic/user_header.png" />
							<h3>太湖县电商扶贫馆</h3>
							<p class="cuncount">村名数量：40 &nbsp;&nbsp;昨日新增：+5</p>
							<i class="icon_qrcode"></i>
						</div>
						<ul class="mui-table-view">
							<li class="mui-table-view-cell mui-media cunzhu">
								<a href="javascript:;" class="">
									<img class="mui-media-object mui-pull-left radius50" src="${ctx}/styles/images/user.png">
									<div class="mui-media-body">
										<h4 class="name">软宝宝</h4>
										<p class="ismhz">门户主 <span class="join">+关注</span></p>
										<div class="cunlod">
											<span class="radius50">县情</span>
											<span class="radius50">县貌</span>
											<span class="radius50">行政村</span>
										</div>
									</div>
								</a>
							</li>
						</ul>
						<div class="toutiao">
							<i class="tticon"></i>
							<span>县政协程浩对天华村彭季团进行慰问</span>
						</div>
						<div class="center-list">
							<div class="center-list" style="margin-top: 10px;">
								<div style="text-align: center;display: inline-block;width: 23vw;margin: 20px 0;">
									<img src="${ctx}/m/styles/village/img/images/cunquangg (2).png" style="width: 12vw;height: 12vw;border-radius: 50%;overflow: hidden;margin-bottom: 10px;" />
									<div>商品分类</div>
								</div>
								<div style="text-align: center;display: inline-block;width: 23vw;margin: 20px 0;">
									<img src="${ctx}/m/styles/village/img/images/cunquangg (3).png" style="width: 12vw;height: 12vw;border-radius: 50%;overflow: hidden;margin-bottom: 10px;" />
									<div>我买到的</div>
								</div>
								<div style="text-align: center;display: inline-block;width: 23vw;margin: 20px 0;">
									<img src="${ctx}/m/styles/village/img/images/cunquangg (4).png" style="width: 12vw;height: 12vw;border-radius: 50%;overflow: hidden;margin-bottom: 10px;" />
									<div>卖出去的</div>
								</div>
								<div style="text-align: center;display: inline-block;width: 23vw;margin: 20px 0;">
									<img src="${ctx}/m/styles/village/img/images/cunquangg (5).png" style="width: 12vw;height: 12vw;border-radius: 50%;overflow: hidden;margin-bottom: 10px;" />
									<div>交易排行</div>
								</div>
							</div>
						</div>
						<div class="center-list">
							<div class="cuncate" style="border-bottom: 0;">
								<div style="padding: 18px 0 5px 7%;line-height: normal;font-weight: bolder;">农家特产</div>
							</div>
							<ul class="mui-table-view mui-grid-view">
								<li class="mui-table-view-cell mui-media mui-col-xs-6">
									<a href="#">
										<img class="mui-media-object" src="${ctx}/m/styles/village/img/images/cunquangg (6).png" style="height: 95px;">
									</a>
								</li>
								<li class="mui-table-view-cell mui-media mui-col-xs-6">
									<a href="#">
										<img class="mui-media-object" src="${ctx}/m/styles/village/img/images/cunquangg (7).png" style="height: 95px;">
									</a>
								</li>
								<li class="mui-table-view-cell mui-media mui-col-xs-6">
									<a href="#">
										<img class="mui-media-object" src="${ctx}/m/styles/village/img/images/cunquangg (8).png" style="height: 95px;">
									</a>
								</li>
								<li class="mui-table-view-cell mui-media mui-col-xs-6" style="padding: 10px 0 0 4px;">
									<ul class="mui-table-views mui-grid-views">
										<li class="mui-table-view-cell mui-media mui-col-xs-6">
											<a href="#">
												<img class="mui-media-object" src="${ctx}/m/styles/village/img/images/cunquangg (9).png">
											</a>
										</li>
										<li class="mui-table-view-cell mui-media mui-col-xs-6">
											<a href="#">
												<img class="mui-media-object" src="${ctx}/m/styles/village/img/images/cunquangg (1).png" style="height: 95px;">
											</a>
										</li>
									</ul>
								</li>
							</ul>
						</div>
						<div class="center-list">
							<div class="cuncate">
								<div style="padding: 18px 0 5px 7%;line-height: normal;font-weight: bolder;">热点推荐</div>
							</div>
							<div class="center-content">
								<div class="center-content-c2">
									<div class="item c1 active">
										<div class="center-content2">
											<div class="center-content21">
												<div class="center-content211">
													<img src="${ctx}/styles/images/user.png" />
												</div>
												<div class="center-content212">
													<p class="center-content2121">丁静<span class="center-content2122"><img src="img/shiming.png"/></span></p>
													<p class="center-content2123"><img src="img/jt_s.png"><span>1小时前</span></p>
												</div>
												<p style="float: right;font-size: 14px;font-weight: bold;color: red;">￥98</p>
											</div>
											<div class="center-content22">
												<img class="center-content221" src="${ctx}/styles/imagesPublic/user_header.png" />
												<img class="center-content221" src="${ctx}/styles/imagesPublic/user_header.png" />
												<img class="center-content221" src="${ctx}/styles/imagesPublic/user_header.png" />
											</div>
											<div class="line"></div>
											<div class="center-content23">
												<p class="center-content231"><span>留言</span></p>
												<p class="center-content232"><span>赞</span></p>
											</div>
											<div class="div_cuns" style="border: 1px solid #efeff4;margin: 20px 0;padding: 8px;height: 88px;">
												<div class="div_img" style="display: inline-block;float: left;">
													<img class="" src="${ctx}/m/styles/village/img/images/cunquangg (1).png" style="display: block;" width="70" height="70" />
												</div>
												<div class="div_pcun" style="display: inline-block;float: left;line-height: 25px;margin-left: 5px;">
													<p style="overflow: hidden;text-overflow: ellipsis;white-space: nowrap;color: #000;">来自于天华镇天华村</p>
													<p>感兴趣的村</p>
												</div>
												<div class="div_jinru" style="display: inline-block;float: right;border: 2px solid #000;padding: 6px 14px;position: relative;top: 50%;transform: translateY(-50%);-webkit-transform: translateY(-50%);">
													<p style="margin: 0;"><a href="#" style="color: #000;">进入该村</a></p>
												</div>
											</div>
											<div class="leaving">
												<input type="text" placeholder="写下您想说的话" />
												<span class="bt">回复</span>
											</div>
										</div>

										<div class="center-content2">
											<div class="center-content21">
												<div class="center-content211">
													<img src="${ctx}/styles/images/user.png" />
												</div>
												<div class="center-content212">
													<p class="center-content2121">老王<span class="center-content2122"><img src="img/shiming.png"/></span></p>
													<p class="center-content2123"><img src="img/jt_x.png"><span>两天前</span></p>
												</div>
												<p style="float: right;font-size: 14px;font-weight: bold;color: red;">￥98</p>
											</div>
											<div class="center-content22">
												<img class="center-content222" src="${ctx}/styles/imagesPublic/user_header.png" />
												<p class="center-content223">天华镇天华村两年脱贫207户，建档立卡贫困户....</p>
											</div>
											<div class="line"></div>
											<div class="center-content23">
												<p class="center-content231"><span>留言</span></p>
												<p class="center-content232"><span>赞</span></p>
											</div>
											<div class="div_cuns" style="border: 1px solid #efeff4;margin: 20px 0;padding: 8px;height: 88px;">
												<div class="div_img" style="display: inline-block;float: left;">
													<img class="" src="${ctx}/m/styles/village/img/images/cunquangg (1).png" style="display: block;" width="70" height="70" />
												</div>
												<div class="div_pcun" style="display: inline-block;float: left;line-height: 25px;margin-left: 5px;">
													<p style="overflow: hidden;text-overflow: ellipsis;white-space: nowrap;color: #000;">来自于天华镇天华村</p>
													<p>感兴趣的村</p>
												</div>
												<div class="div_jinru" style="display: inline-block;float: right;border: 2px solid #000;padding: 6px 14px;position: relative;top: 50%;transform: translateY(-50%);-webkit-transform: translateY(-50%);">
													<p style="margin: 0;"><a href="#" style="color: #000;">进入该村</a></p>
												</div>
											</div>
											<div class="leaving">
												<input type="text" placeholder="写下您想说的话" />
												<span class="bt">回复</span>
											</div>
											<div class="center-content24">
												<p class="center-content241">
													<img src="${ctx}/m/styles/village/img/redxin.png" /><span>玉玉、李自成、老张、老王、老李、张三丰觉得很赞</span>
												</p>
											</div>
											<div class="line"></div>
											<div class="center-content25">
												<div class="center-content251 center-content251-a">
													<div class="center-content2511">
														<img class="center-content2512" src="${ctx}/styles/images/user.png" />
													</div>
													<div class="center-content2512">
														<p class="center-content25121">王文<span class="center-content25122"><img src="img/shiming.png"/></span></p>
														<p class="center-content25123">党和人民紧紧缠绕在一起，团结在一起！</p>
													</div>
												</div>
												<div class="center-content251 center-content251-b">
													<div class="center-content2511">
														<img class="center-content2512" src="${ctx}/styles/images/user.png" />
													</div>
													<div class="center-content2512">
														<p class="center-content25121">张三<span class="center-content25122"><img src="img/shiming.png"/></span></p>
														<p class="center-content25123">@王文：现在我们的生活直奔小康</p>
													</div>
												</div>
												<div class="center-content251 center-content251-b">
													<div class="center-content2511">
														<img class="center-content2512" src="${ctx}/styles/images/user.png" />
													</div>
													<div class="center-content2512">
														<p class="center-content25121">张三<span class="center-content25122"><img src="img/shiming.png"/></span></p>
														<p class="center-content25123">@王文：现在我们的生活直奔小康</p>
													</div>
												</div>
												<div class="line"></div>
												<div class="center-content251 center-content251-a">
													<div class="center-content2511">
														<img class="center-content2512" src="${ctx}/styles/images/user.png" />
													</div>
													<div class="center-content2512">
														<p class="center-content25121">胡华<span class="center-content25122"><img src="img/shiming.png"/></span></p>
														<p class="center-content25123">现在人民生活好了，我们感谢党的帮助！</p>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="replaymsg">
					<div class="dialogdiv">
						<textarea placeholder="这里填入回复的内容"></textarea>
						<div class="bt">发送</div>
					</div>
				</div>
				<div class="mui-off-canvas-backdrop"></div>
			</div>
		</div>

	</body>
	<script>
		mui.init({
			swipeBack: false,
		});
		//侧滑容器父节点
		var offCanvasWrapper = mui('#offCanvasWrapper');
		//主界面容器
		var offCanvasInner = offCanvasWrapper[0].querySelector('.mui-inner-wrap');
		//菜单容器
		var offCanvasSide = document.getElementById("offCanvasSide");
		//移动效果是否为整体移动
		var moveTogether = false;
		//侧滑容器的class列表，增加.mui-slide-in即可实现菜单移动、主界面不动的效果；
		var classList = offCanvasWrapper[0].classList;
		//变换侧滑动画移动效果；
		mui('.mui-input-group').on('change', 'input', function() {
			if(this.checked) {
				offCanvasSide.classList.remove('mui-transitioning');
				offCanvasSide.setAttribute('style', '');
				classList.remove('mui-slide-in');
				classList.remove('mui-scalable');
				switch(this.value) {
					case 'main-move':
						if(moveTogether) {
							//仅主内容滑动时，侧滑菜单在off-canvas-wrap内，和主界面并列
							offCanvasWrapper[0].insertBefore(offCanvasSide, offCanvasWrapper[0].firstElementChild);
							moveTogether = false;
						}
						break;
					case 'main-move-scalable':
						if(moveTogether) {
							//仅主内容滑动时，侧滑菜单在off-canvas-wrap内，和主界面并列
							offCanvasWrapper[0].insertBefore(offCanvasSide, offCanvasWrapper[0].firstElementChild);
						}
						classList.add('mui-scalable');
						break;
					case 'menu-move':
						classList.add('mui-slide-in');
						break;
					case 'all-move':
						moveTogether = true;
						//整体滑动时，侧滑菜单在inner-wrap内
						offCanvasInner.insertBefore(offCanvasSide, offCanvasInner.firstElementChild);
						break;
				}
				offCanvasWrapper.offCanvas().refresh();
			}
		});
		//主界面和侧滑菜单界面均支持区域滚动；
		mui('#offCanvasSideScroll').scroll();
		mui('#offCanvasContentScroll').scroll();
		//实现ios平台的侧滑关闭页面；
		if(mui.os.plus && mui.os.ios) {
			offCanvasWrapper[0].addEventListener('shown', function(e) { //菜单显示完成事件
				plus.webview.currentWebview().setStyle({
					'popGesture': 'none'
				});
			});
			offCanvasWrapper[0].addEventListener('hidden', function(e) { //菜单关闭完成事件
				plus.webview.currentWebview().setStyle({
					'popGesture': 'close'
				});
			});
		}
	</script>
	<script>
		$(document).ready(function() {
			$(".over_height").css("height", window.innerHeight - $("header").height());
			$(".mui-search").css("width", $("header").width() - $(".dizhi").width() - $(".mui-icon").width() - 28);
			$(".center-lists").css("width", $(".scrolls").width() - $(".heights").width());
			$(".div_pcun").css("width", $(".div_cuns").width() - $(".div_img").width() - $(".div_jinru").width() - 40);
			$(".heights").css("height", $(".scrolls").height());
		});
		$(".center-content231,.center-content331").click(function() {
			$(".replaymsg").show();
			$(".replaymsg textarea").focus();
		});
		mui('.mui-off-canvas-right').on('tap', '.mui-collapse', function(e) {
			var id = this.getAttribute("data-id");
			$(".mui-collapse").siblings().removeClass("mui-actives");
			this.classList.add("mui-actives");
			$(".center-lists").each(function() {
				var sid = $(this).data("id");
				if(id == sid) {
					$(".center-lists").addClass("shows").siblings().removeClass("shows-active");
					$(this).removeClass("shows").addClass("shows-active");
					return false;
				} else {
					$(".center-lists").addClass("shows").siblings().removeClass("shows-active");
				}
			});
		});
	</script>
</body>

</html>