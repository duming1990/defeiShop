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
<jsp:include page="../_public_in_head_new.jsp" flush="true" />
<jsp:include page="../_public_css.jsp" flush="true" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/mui/poppicker/mui.picker.min.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/m/scripts/lightGallery/css/lightgallery.css"/>
<style type="text/css">

.mui-popover .mui-table-view{
  max-height: 80%!important;
}

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
	display: block!important;
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

.center-lists {
	display: none;
}

.aui-slide-box .aui-slide-item-list .aui-slide-item-item .v-img {
	border-radius: 50%;
}

.aui-slide-box .aui-slide-list {
	margin: 10px 10px 0 10px;
	overflow: hidden;
	height: 8rem;
}

.aui-slide-box .aui-slide-item-list {
	width: auto;
	white-space: nowrap;
	height: 7.9rem;
	font-size: 0;
	-webkit-overflow-scrolling: touch;
}

.swiper-wrapper li {
	text-align: center;
	margin-right: 0px!important;
}

.swiper-wrapper li .v-img {
	width: 5rem!important;
	height: 5rem!important;
}
.rank-img{
position: relative;
width: 1.5rem !important;
height: 2rem !important;
bottom: -8px;
right: 25px;
}
.mui-off-canvas-left, .mui-off-canvas-right{
    width: 30vw;
}
.commInfo_from{
margin-left: 5px;
font-size: 16px;
font-weight:bold;
padding:3px;
}

</style>
</head>

<body>
<!-- 侧滑导航根容器 -->
<div id="app" v-cloak>
	<header-item :header_title="servicecenter_name" canback="true"></header-item>
	<div class="mui-content">
		<header id="header" class="mui-bar" style="background-color:#f03e3d!important;">
		 <h1 class="mui-title" style="left:76px;">
		  <input type="search" @tap="openUrl(ctx+'m/MSearch.do')" class="search-input" style="width:100%!important;"/>
		 </h1>
		 <a class="mui-icon mui-icon-bars mui-pull-right" href="#showPickerRight" style="color:#fff;"></a>
		 <a href="#showPicker">
		 <div class="mui-btn mui-btn-blue mui-btn-link mui-btn-nav mui-pull-left" id="city_name" style="color:#fff;margin-right:0px;">
		   
		 </div>
		 </a>
		 <i class="mui-icon mui-icon-arrowdown" style="color:white!important;margin: 0px;padding-left:0px;"></i>
		</header>
		<div id="offCanvasContentScroll" style="top:88px;" class="mui-content mui-scroll-wrapper">
			<div class="mui-scroll">
				<div>
					<div class="cunhead">
						<img class="bg" v-if="banner == ''" src="${ctx}/m/styles/village/img/cunbg.jpg" />
						<img class="bg" v-else :src="ctx + banner+'@s414x207'" onerror="this.src='${ctx}/m/styles/village/img/cunbg.jpg'" />
						<img class="cunlogo" v-if="logo == ''" src="${ctx}/styles/imagesPublic/user_header.png" />
						<img class="cunlogo" v-if="logo != ''" :src="ctx + logo+'@s200x200'" onerror="this.src='${ctx}/styles/imagesPublic/user_header.png'" />
						<h3 style="padding-top:3px;">{{servicecenter_name}}...
						<c:if test="${link_area eq 1 }">
						<span class="commInfo_from" @tap="delHomePage()" id="delHomePage">取消主页</span>
						<span class="commInfo_from" style="display: none" @tap="setHomePage(entity.id)" id="setHomePage">设为主页</span>
						</c:if>
						<c:if test="${link_area eq 0 || link_area eq null}">
						<span class="commInfo_from" style="display: none" @tap="delHomePage()" id="delHomePage">取消主页</span>
						<span class="commInfo_from"  @tap="setHomePage(entity.id)" id="setHomePage">设为主页</span>
						</c:if>
						
						
						</h3>
						<p class="cuncount" style="font-size: 16px;">村民数量：{{datas.villageMemberCount}} &nbsp;&nbsp;昨日新增：+{{datas.yesterdayAddCount}}</p>
						<div class="lightgallery" v-if="qrcode != ''">
							<a :href="ctx + qrcode"><i class="icon_qrcode"></i></a>
						</div>
					</div>
					<ul class="mui-table-view">
						<li class="mui-media cunzhu">
							<img class="mui-media-object mui-pull-left radius50" @tap="openUrl(ctx+'m/MUserCenter.do?method=index&user_id='+manageUser.id)" v-if="manageUserLogo == ''" src="${ctx}/styles/images/user.png">
							<img class="mui-media-object mui-pull-left radius50" @tap="openUrl(ctx+'m/MUserCenter.do?method=index&user_id='+manageUser.id)" v-else :src="ctx + manageUserLogo +'@s200x200'" onerror="this.src='${ctx}/styles/images/user.png'">
							<div class="mui-media-body">
								<h4 class="name" @tap="openUrl(ctx+'m/MUserCenter.do?method=index&user_id='+manageUser.id)">{{manageUser.real_name}}</h4>
								<p class="ismhz">门户主 </p>
								<div class="cunlod mui-table-view-test">
									<span class="radius50" @tap="openUrl(ctx+'m/MVillage.do?village_id='+ entity.id+'&p_index='+ entity.p_index)">行政村 </span>
									<span class="radius50" @tap="openUrl(ctx+'m/MServiceCenterInfo.do?method=photo&id='+ entity.id)">县貌</span>
									<span class="radius50" @tap="openUrl(ctx+'m/MServiceCenterInfo.do?method=xianqing&id='+ entity.id)">县情</span>
								</div>
							</div>
						</li>
					</ul>

					<div class="toutiao" v-if="newsInfoList != ''">
						<i class="tticon"></i>
						<span @tap="openUrl(ctx+'m/MNewsInfo.do?link_id='+ entity.id+'&mod_index=1500501030')" v-if="index == 0" v-for="(item,index) in newsInfoList">{{item.title}}</span>
					</div>
					<div class="center-list-flex">
						<div class="center-list-flex center-list-par" style="margin-top: 10px;">
							<div class="center-list-content">
								<img src="${ctx}/m/styles/village/img/images/cun1.png" @tap="openUrl(ctx+'m/MCategory.do')" />
								<div>商品分类</div>
							</div>
							<div class="center-list-content">
								<img src="${ctx}/m/styles/village/img/images/cun2.png" @tap="openUrl(ctx+'m/MSearch.do?method=listPd&is_aid=1&id='+entity.id)" />
								<div>扶贫商品</div>
							</div>
							<div class="center-list-content">
								<img src="${ctx}/m/styles/village/img/images/cun1.png" @tap="openUrl(ctx+'m/MSearch.do?method=listEntp&p_index='+p_index)" />
								<div>县域企业</div>
							</div>
							<div class="center-list-content">
								<img src="${ctx}/m/styles/village/img/images/cun4.png" />
								<div>乡村旅游</div>
							</div>
						</div>
					</div>
					<div class="center-list" style="margin-top: 10px;" v-if="datas.myJoinVillageList != ''&&datas.myJoinVillageList !=null">
						<div class="cuncate" style="border-bottom: 0;">
							<div class="title">我加入的村</div>
						</div>
						<div class="fen-ge-fu"></div>
						<div class="aui-slide-box">
							<div class="aui-slide-list swiper-container-comm">
								<ul class="aui-slide-item-list swiper-wrapper">
									<li class="aui-slide-item-item swiper-slide join-village-li" v-for="item in datas.myJoinVillageList">
										<a @tap="openUrl(ctx+'m/MVillage.do?method=index&id='+item.village_id)" class="v-link">
											<img :src="ctx + item.map.village_logo +'@s200x200'" class="v-img">
											<p class="aui-slide-item-title aui-slide-item-f-els">{{item.map.village_name}}</p>
										</a>
									</li>
								</ul>
							</div>
						</div>
					</div>
					<div class="center-list" v-if="mBaseLinkList != ''">
						<div class="cuncate" style="border-bottom: 0;">
							<div class="title">本县特产</div>
						</div>
						<div class="fen-ge-fu"></div>
						<ul class="mui-table-view mui-grid-view ">
							<li class="mui-table-view-cell mui-media mui-col-xs-6 mBaseClass" v-for="item in datas.mBaseLinkList">
								<a href="#">
									<img class="mui-media-object" @tap="openUrl(item.link_url)" :src="ctx + item.image_path+'@s465x243'" style="height: 95px;">
								</a>
							</li>
						</ul>
					</div>
					<div class="center-list" style="margin-top: 10px;" v-if="datas.poorInfoList != ''">
						<div class="cuncate" style="border-bottom: 0;">
							<div class="title">爱心助贫</div>
						</div>
						<div class="fen-ge-fu"></div>
						<div class="aui-slide-box">
							<div class="aui-slide-list swiper-container-comm">
								<ul class="aui-slide-item-list swiper-wrapper">
									<li class="aui-slide-item-item swiper-slide join-village-li" v-for="item in datas.poorInfoList">
										<a @tap="openUrl(ctx+'m/MUserCenter.do?method=index&user_id='+item.user_id)" class="v-link">
											<img :src="ctx + item.head_logo +'@s200x200'" onerror="this.src='${ctx}/styles/images/user.png'" class="v-img">
											<p class="aui-slide-item-title aui-slide-item-f-els">{{item.real_name}}</p>
										</a>
									</li>
								</ul>
							</div>
						</div>
					</div>
					<div class="center-list" style="margin-top: 10px;" v-if="datas.saleRankList != ''">
						<div class="cuncate" style="border-bottom: 0;">
							<div class="title">销售排行</div>
						</div>
						<div class="fen-ge-fu"></div>
						<div class="aui-slide-box">
							<div class="aui-slide-list swiper-container-comm">
								<ul class="aui-slide-item-list swiper-wrapper">
									<li class="aui-slide-item-item swiper-slide join-village-li" v-for="(item,index) in datas.saleRankList">
										<a @tap="openUrl(ctx+'m/MUserCenter.do?method=index&user_id='+item.map.id)" class="v-link">
											<img :src="ctx + item.map.user_logo +'@s200x200'" onerror="this.src='${ctx}/styles/images/user.png'" class="v-img">
											<img :src="ctx + 'm/styles/village/img/rank/'+ (index + 1) +'.png'" class="rank-img" />
											<p class="aui-slide-item-title aui-slide-item-f-els">{{item.map.real_name}}</p>
										</a>
									</li>
								</ul>
							</div>
						</div>
					</div>
					<div class="center-list">
						<div class="cuncate">
							<div class="title">热点商品</div>
						</div>
						<div class="fen-ge-fu"></div>
						<div class="center-content">
							<div class="center-content2" v-for="(item,index) in datas.villageDynamicList">
								<div class="center-content21">
									<div class="center-content211" @tap="openUrl(ctx+'m/MUserCenter.do?method=index&user_id='+item.add_user_id)">
										<img v-if="null != item.map.user_logo && item.map.user_logo.indexOf('http://') == -1" :src="ctx + item.map.user_logo+'@s64x64'" />
										<img v-else-if="null != item.map.user_logo && item.map.user_logo.indexOf('http://') != -1" :src="item.map.user_logo" />
										<img v-else src="${ctx}/styles/images/user64.png" />
									</div>
									<div class="center-content212" @tap="openUrl(ctx+'m/MUserCenter.do?method=index&user_id='+item.add_user_id)">
										<p class="center-content2121">{{item.map.real_name}}</p>
										<p class="center-content2121 pub_date">{{item.add_date | formatDate}}</p>
									</div>
									<div class="comm-div" v-if="item.type == 2" style="float: right;" @click="addCart(item.map.commInfoTczh);">
										<span class="money" v-if="item.map.commInfoTczh != null ">￥{{item.map.commInfoTczh.comm_price}}</span>
									</div>
								</div>
								<div class="center-content22">
								    <p class="center-content223 title">{{item.comm_name}}</p>
									<p class="center-content223">{{item.content}}</p>
									<div class="lightgallery">
										<a :href="ctx + item3.file_path+'@compress'" v-for="item3 in item.map.imgList">
											<img v-if="item.map.imgList && item.map.imgList.length == 1" class="center-content221 pop-img" :src="ctx + item3.file_path+'@compress'" style="height:auto;width:40%!important;"/>
										    <img v-else class="center-content221 pop-img" :style="item.map.imgList && item.map.imgList.length % 2 == 0 && item.map.imgList.length < 5   ? 'width:49%!important;height: auto;':''" :src="ctx + item3.file_path+'@s400x400'"/>
										</a>
									</div>
								</div>
								<div class="div_cuns" @tap="openUrl(ctx+'m/MVillage.do?method=index&id='+ item.village_id)">
								<img src="${ctx}/m/styles/village/img/dingwei.png" style="margin-top: 6px; width: 17px;float: left;">
									<div class="div_pcun">
										<p>来自于{{item.village_name}}</p>
									</div>
								</div>
								<div class="line" v-show="item.map.commentList.length > 0"></div>
								<dynamic-comment-item :item="item" :is_show_huifu="0" :ctx="ctx" showindex="3"></dynamic-comment-item>
								<div class="line_all"></div>
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
<script type="text/javascript" src="${ctx}/styles/mui/mui.pullToRefresh.js"></script>
<script type="text/javascript" src="${ctx}/styles/mui/mui.pullToRefresh.material.js"></script>
<script type="text/javascript" src="${ctx}/styles/mui/poppicker/mui.picker.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/swiper/swiper.min.js"></script>
<script type="text/javascript" src="${ctx}/m/scripts/lightGallery/js/lightgallery-all.min.js"></script> 
<script>
var vm = new Vue({
	el: '#app',
	data: {
		datas: "",
		ctx: Common.api,
		search: "",
		logo: "",
		banner: "",
		qrcode: "",
		entity: "",
		manageUserLogo: "",
		manageUser: "",
		newsInfoList: new Array(),
		p_index_list: new Array(),
		nextPIndexList: new Array(),
		startPage: 1,
		p_index: '',
		mBaseLinkList: new Array(),
		servicecenter_name:"",
		poorStartPage: 0,
		is_get_poor_list: true
		
	},
	mounted: function() {
		this.$nextTick(function() {
			Common.loading();
			this.getAjaxData();
		});
		
	},
	updated: function() {
		$('.lightgallery').lightGallery({download:false});
		mui('.lightgallery').on('tap', 'a', function(){
	   		$(this).click();
   	    });
		
		new Swiper('.swiper-container-comm', {
			slidesPerView: 'auto',
			paginationClickable: true,
			spaceBetween: 20
		});
		
		new Swiper('.swiper-container-poor', {
			slidesPerView: 'auto',
			paginationClickable: true,
			spaceBetween: 20,
			on: {
				 touchEnd: function(event){
					 if(vm.is_get_poor_list){
// 						 vm.getPoorAjaxData();
					 }
				 },
			  },
		});

		mui.init({
			pullRefresh: {
				container: '#offCanvasContentScroll',
				down: {},
				up: {
					contentrefresh: '正在加载...',
					callback: pullupRefresh
				}
			}
		});
		var pageSize = 10;

		function pullupRefresh() {
			Common.loading();
			Common.getData({
				route: '/m/MServiceCenterInfo.do?method=getAjaxDataPage',
				data: {
					id: "${af.map.id}",
					pageSize: pageSize,
					startPage: vm.startPage
				},
				success: function(data) {
					if(data.code == 0) {
						mui.toast(data.msg);
						return false;
					}
					else if(data.code == 1) {
						vm.datas.villageDynamicList = vm.datas.villageDynamicList.concat(data.datas.villageDynamicList);
						vm.startPage = vm.startPage + 1;
						mui('#offCanvasContentScroll').pullRefresh().endPullupToRefresh((data.datas.villageDynamicList.length < 10));
						Common.hide();
					}
				},
				error: function() {
					mui.toast('好像出错了哦~');
				}
			});
		}

		mui.init({
			swipeBack: false,
		});
		mui(".mui-scroll-wrapper").scroll();
		mui(".mui-off-canvas-wrap").on('tap', 'h2', function() {
			$(this).parent().hide();
		});
		//主界面和侧滑菜单界面均支持区域滚动；
		mui('#offCanvasSideScroll').scroll({
			indicators: false
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
				}
				else {
					$(".center-lists").addClass("shows").siblings().removeClass("shows-active");
				}
			});
		});
		
		Common.showPov('showPicker', vm.p_index_list, function($this) {
			
			Common.getData({
				route: "m/MServiceCenterInfo.do?method=queryOtherCountry&p_index=" + $this.getAttribute("data-value"),
				data: {},
				success: function(data) {
					if(data.code == 0) {
						mui.toast(data.msg);
						return false;
					}else if(data.code == 1) {
						var url = vm.ctx  + "m/MServiceCenterInfo.do?method=index&id=" + data.datas.entity.id;
						goUrl(url);
					}
				},
				error: function() {
					mui.toast('好像出错了哦~');
				}
			});
		});
		
	},
	methods: {
		getAjaxData: function() {

			Common.getData({
				route: '/m/MServiceCenterInfo.do?method=getAjaxData',
				data: {
					id: "${af.map.id}",
					pageSize: 4,
					startPage: vm.startPage,
				},
				success: function(data) {
					if(data.code == 0) {
						mui.toast(data.msg);
						return false;
					}else if(data.code == 1) {
						Common.hide();
						vm.datas = data.datas;
						vm.entity = data.datas.entity;
						vm.startPage = vm.startPage + 1;
						if(null != data.datas.entity.logo) {
							vm.logo = data.datas.entity.logo;
						}
						if(null != data.datas.entity.banner) {
							vm.banner = data.datas.entity.banner;
						}
						if(null != data.datas.entity.qrcode) {
							vm.qrcode = data.datas.entity.qrcode;
						}
						if(null != data.datas.managerUser) {
							vm.manageUser = data.datas.managerUser;
						}
						if(null != data.datas.managerUser.user_logo) {
							vm.manageUserLogo = data.datas.managerUser.user_logo;
						}
						if(null != data.datas.newsInfoList) {
							vm.newsInfoList = data.datas.newsInfoList;
						}
						if(null != data.datas.mBaseLinkList) {
							vm.mBaseLinkList = data.datas.mBaseLinkList;
						}
						if(null != data.datas.p_index_list) {
							vm.p_index_list = data.datas.p_index_list;
						}
						if(null != data.datas.nextPIndexList) {
							vm.nextPIndexList = data.datas.nextPIndexList;
							Common.showPovScroll('showPickerRight', vm.nextPIndexList, function($this) {
								vm.openTown($this.getAttribute("data-value"));
							});
						}
						if(null != data.datas.entity.servicecenter_name){
							vm.servicecenter_name = data.datas.entity.servicecenter_name.substring(0,3);
							
						}
						$("#city_name").text(data.datas.p_name);
					}
				},
				error: function() {
					mui.toast('好像出错了哦~');
				}
			});
		},
		getPoorAjaxData:function(){
			console.info("vm.poorStartPage:"+vm.poorStartPage)
			Common.getData({
				route: '/m/MServiceCenterInfo.do?method=getPoorAjaxData',
				data: {
					id: "${af.map.id}",
					pageSize: 4,
					poorStartPage: vm.poorStartPage+1,
				},
				success: function(data) {
					if(data.code == 0) {
						mui.toast(data.msg);
						return false;
					}else if(data.code == 1) {
// 						Common.hide();
						
						vm.datas.poorInfoList = vm.datas.poorInfoList.concat(data.datas.poorInfoList);
						vm.poorStartPage = vm.poorStartPage + 1;
						if(data.datas.poorInfoList.length < 6){
							vm.is_get_poor_list = false;
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
		openTown: function(p_index) {
			var url = vm.ctx + "m/MVillage.do?method=list&p_index="+p_index;
			goUrl(url);
		},
		login:function(){
			window.setTimeout(function () { 
				window.location.href=vm.ctx+"login.shtml?returnUrl="+window.location.href;
			}, 1500);
		},
		setHomePage:function(id){
			Common.getData({
				route: '/m/MCityCenter.do?method=setHomePage',
				data: {
					link_area: '/MServiceCenterInfo.do?method=index&id='+id,
				},
				success: function(data) {
					if(data.code=="-1"){
						login();
					}else{
						mui.toast(data.msg);
						$("#setHomePage").hide();
						$("#delHomePage").show();
					}
				},
				error: function() {
					mui.toast('好像出错了哦~');
				}
			});
		},
		delHomePage:function (){
			Common.getData({
				route: '/m/MCityCenter.do?method=setHomePage',
				data: {
					link_area: '/m/MCityCenter.do',
				},
				success: function(data) {
					if(data.code=="-1"){
						login();
					}else{
						mui.toast(data.msg);
						$("#setHomePage").show();
						$("#delHomePage").hide();
					}
					
				},
				error: function() {
					mui.toast('好像出错了哦~');
				}
			});
		},
	}
});
$(document).ready(function() {
	$(".over_height").css("height", window.innerHeight - $("header").height());
	$(".center-lists").css("width", $(".scrolls").width() - $(".heights").width());
	$(".div_pcun").css("width", $(".div_cuns").width() - $(".div_img").width() - $(".div_jinru").width() - 40);
	$(".heights").css("height", $(".scrolls").height());
});

</script>
</body>

</html>