<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>九个挑夫</title>
		<link href="${ctx}/styles/mui/css/mui.min.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/countryvillageMyIndex/css/global.css" />
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/countryvillageMyIndex/css/font-awesome.min.css" />
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/countryvillageMyIndex/css/common.css" />
		<link rel="stylesheet" type="text/css" href="${ctx}/m/scripts/lightGallery/css/lightgallery.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/common.css"  />
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/category-list.css"  />
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/category-nav.css?v=20180427"  />
		<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script>
		<style>
		.categorys {width: -webkit-fill-available;height: 38px;background-color: #f4f4f4;}
		#searchbtn{border: none;color: #fff;background: #ec5051;}
		.commentbox{padding-left: 90px;}
		</style>
	</head>
<style>
.cart{float: right;cursor: pointer;width: 30px;display: block;margin-left: 20px;}
</style>
	<body class="page-color">
	<jsp:include page="../../_header.jsp" flush="true" />
		<div id="countryIndex" v-cloak>
				<!-- 背景图 -->
				<div class="xian-header" v-bind:style="{backgroundImage:'url(' + ctx+banner + ')'}">
					<div class="black-layer"></div>
					<div class="mycity-wrap">
						<div class="left-group">
							<div class="top-pic">
								<a href="javascript:void(0)">
									<img v-if="logo == ''" src="${ctx}/styles/images/user.png">
									<img v-else :src="ctx + logo +'@s200x200'">
								</a>
							</div>
							<div class="group-text">
								<h3><a href="javascript:void(0)">{{p_name}}</a><!--<span class="auth"></span>--></h3>
								<div class="subtext">村站数量：<span>{{datas.list_count}}</span> 昨日新增：<span>+{{datas.yesterdayAddCount}}</span></div>
							</div>
						</div>
						<div class="right-group">
							<div  class="my-qr">
								<i v-if="service_qrcode == ''" class="fa fa-qrcode"></i>
								<!-- 二维码 -->
								<div v-else class="show-qr" v-bind:style="{'display':service_qrcode != '' ? 'block':'none'}">
									<img :src="ctx + service_qrcode+'@s400x400'" />
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="mycity-wrap mianbox">
					<div class="mianleftbox">
						<div class="comm-box">
							<div class="xian-infobox">
								<div class="fl">
									<div class="top-pic">
										<a :href="'VillageIndex.do?method=myIndex&member_id='+manageUser.id">
										<img v-if="manageUserLogo.indexOf('http://') == -1" :src="ctx + manageUserLogo"/>
										<img v-else :src="manageUserLogo"/>
										</a>
									</div>
									<div class="group-text">
										<h3><a href="javascript:void(0)">{{p_name}}</a></h3>
										<div class="subtext">{{manageUser.user_name}}</div>
									</div>
								</div>
								<div class="fr">
									<ul class="into-item-link">
										<li>
											<a @tap="xianQing();"><i class="fa fa-flag-o"></i>
												<p>县情</p>
											</a>
										</li>
										<li>
											<a @tap="photoList();"><i class="fa fa-map-marker"></i>
												<p>县貌</p>
											</a>
										</li>
										<li>
											<a @tap="villageList();"><i class="fa fa-list-ul"></i>
												<p>行政村</p>
											</a>
										</li>
									</ul>
								</div>
							</div>
							<!--热点 -->
							<div class="hotnews">
								<div class="title">热点</div>
								<div class="sli-textbox">
									<ul class="slidemain">
										<li v-for="(item,index) in datas.newsInfoList" v-if='index<=5'>
											<a :href="'IndexNewsInfo.do?uuid='+item.uuid">{{item.title}}</a>
										</li>
									</ul>
									<div class="hd">
										<ul></ul>
									</div>
								</div>
							</div>
							<div class="news-pic-box">
								<h3 class="comm-title"><span class="more"><a :href="'IndexNewsInfo.do?method=list&mod_code='+datas.mod_code+'&par_code='+datas.par_code+'&link_id='+datas.entity.id">更多 ></a></span><span class="ht cur">县资讯</span></h3>
								<div class="xc-newslist">
									<ul>
										<li v-for="(item,index) in datas.newsInfoList" v-if='index<=3'>
											<a target="_blank" :href="'IndexNewsInfo.do?uuid='+item.uuid"><strong>{{item.title}}</strong>
												<p>{{item.summary}}</p>
											</a>
										</li>
									</ul>
								</div>
								<div class="xc-newspicbox">
									<ul class="slidemain">
										<li v-for="(item,index) in datas.newsImgList" v-if='index<=3'>
											<a target="_blank" :href="'IndexNewsInfo.do?uuid='+item.uuid">
											<img :src="ctx+item.image_path +'@s330x255'" alt=""></a>
										</li>
									</ul>
									<div class="hd">
										<a href="javascript:void(0);" class="prev"><i class="fa fa-angle-left"></i></a>
										<a href="javascript:void(0);" class="next"><i class="fa fa-angle-right"></i></a>
										<ul>
											<li v-for="(item,index) in datas.newsImgList" v-if='index<=3'>
												<a href="javascript:void(0);"></a>
											</li>
										</ul>
									</div>
								</div>
							</div>

							<div class="menu-tool">
								<ul>
									<li>
										<a href="${ctx}/Search.do"><img src="${ctx}/m/styles/village/img/images/cun1.png" style="width: 90px;" />
											<p>商品分类</p>
										</a>
									</li>
									<li>
										<a href="${ctx}/Search.do?is_aid=1"><img src="${ctx}/m/styles/village/img/images/cun2.png" style="width: 90px;" />
											<p>扶贫商品</p>
										</a>
									</li>
									<li>
										<a href="${ctx}/Search.do?htype=1&p_index=${af.map.p_index}"><img src="${ctx}/m/styles/village/img/images/cun3.png" style="width: 90px;" />
											<p>县域企业</p>
										</a>
									</li>
									<li>
										<a href="#"><img src="${ctx}/m/styles/village/img/images/cun4.png" style="width: 90px;" />
											<p>乡村旅游</p>
										</a>
									</li>
								</ul>
							</div>

							<div class="special-pdc">
								<h3 class="comm-title"><span class="ht">本县特产</span></h3>
								<ul class="prd-tg" >
									<li v-for="(item,index) in teChanInfoList" class="ti">
										<a :href="item.link_url"><img :src="ctx + item.image_path" style="width: 314px;height: 158px;"/></a>
									</li>
								</ul>
							</div>
							<div class="list-more">
								<a @tap="teChanListMore">...更多...</a>
							</div>

							<div class="tj-box">
								<h3 class="comm-title"><span class="ht cur">热点推荐</span></h3>

								<div class="user-datbox" v-for="(Dynamic,index) in DynamicList" v-if="Dynamic.type == 2">
									<div class="user-group" style="width: 100%;">
										<div class="uerhead" style="left: 20px;width: 50px;height: 50px;">
											<a :href="'VillageIndex.do?method=myIndex&village_id='+Dynamic.village_id+'&member_id='+Dynamic.add_user_id">
											<img v-if="null == Dynamic.map.user_logo" src="${ctx}/styles/images/user64.png"/>
											<img v-if="null != Dynamic.map.user_logo && Dynamic.map.user_logo.indexOf('http://') == -1" :src="ctx + Dynamic.map.user_logo+'@s96x96'"/>
											<img v-if="null != Dynamic.map.user_logo && Dynamic.map.user_logo.indexOf('http://') != -1" :src="Dynamic.map.user_logo"/>
											</a>
										</div>
										<div class="datinfo">
											<div class="userinfo-box">
												<div class="pricebox">
													<a href="#">￥{{Dynamic.map.commInfoTczh.comm_price}} 元</a>
													<img  v-if="Dynamic.map.commInfoTczh.inventory > 0 && Dynamic.map.commInfoTczh != null" @tap="buy(Dynamic.map.commInfoTczh)" class="cart" src="${ctx}/m/styles/village/img/cart.png"/>
													<img  v-else @tap="noInventory" class="cart"  src="${ctx}/m/styles/village/img/no_cart.png" title="该商品已售罄"/>
												</div>
												<div class="username">{{Dynamic.map.real_name}}
													<p class="send-time"><i class="fa fa-clock-o"></i> {{Dynamic.add_date | formatDate}}</p>
												</div>
											</div>
											<div class="send-cont">
												{{Dynamic.content}}
												<div class="send-img lightgallery">
													<a :href="ctx+DynamicImg.file_path+'@compress'" v-for="DynamicImg in Dynamic.map.imgList">
														<img :src="ctx + DynamicImg.file_path+'@s70x70'"/>
													</a>
												</div>
											</div>
										</div>
									</div>
<!-- 									<div class="user-bycun"> -->
<%-- 										<img  v-if="null != Dynamic.map.village_logo" :src="ctx + Dynamic.map.village_logo" onerror="this.src='${ctx}/styles/imagesPublic/user_header.png'"/> --%>
<%-- 										<img  v-else src="${ctx}/styles/imagesPublic/user_header.png" /> --%>
<!-- 										<p class="cun-name">来自于{{Dynamic.village_name}}</p> -->
<!-- 										<p class="goin-cun"> -->
<!-- 											<a :href="'VillageIndex.do?method=villageIndex&village_id='+Dynamic.village_id">进入该村</a> -->
<!-- 										</p> -->
<!-- 									</div> -->
									<div class="clearfix"></div>
									<!--回复-->
									<div class="commentbox">
										<div class="comment-tool">
											<div class="zan-user"><i class="fa fa-heart-o"></i>赞 <span>{{Dynamic.map.zanNameList}}</span>等{{Dynamic.map.zan_count}}人</div>
											<a :href="'VillageIndex.do?method=villageIndex&village_id='+Dynamic.village_id">
											<img src="${ctx}/m/styles/village/img/dingwei.png" style="margin-top: 6px; width: 17px; float: left;">
											<span style="color: #777;font-size: 15px;">{{Dynamic.village_name}}</span></a> 
											<a v-if="datas.user!=''" @tap="goDianZan(Dynamic,index)" v-bind:title="Dynamic.map.is_zan ==1 ?'取消点赞':'点赞'">
											<i class="fa fa-heart-o" v-bind:style="{'color':Dynamic.map.is_zan ==1 ? '#e8601b':'#333'}"></i></a> 
											<a v-else @tap="loginTip();" title="点赞"><i class="fa fa-heart-o"></i></a> 
											
											<a v-if="datas.user!=''"  href="javascript:void(0)" title="评论" @tap="showInput(index);"><i class="fa fa-comment-o"></i></a>
											<a v-else  title="评论" @tap="loginTip();"><i class="fa fa-comment-o"></i></a>
											
										</div>
									    <form>
									    	<div class="comment-inputbox" v-bind:style="{'display':index === inputNum ? 'block':'none'}">
									    		<input type="text" placeholder="内容不超过200字" class="comm-input" v-model="Dynamic.comm_content"/>
									    		<input  @tap="commentSubmit(Dynamic,index)" type="button" value="评论" class="comm-sub"/>
									    	</div>
									    </form>
									    <div class="comment-n" v-for="DynamicComment in Dynamic.map.commentList">
									    	<div class="user-commt"><span class="name"><a href="#">{{DynamicComment.map.real_name}}</a></span>：<span class="comt">@{{DynamicComment.link_user_name}}:{{DynamicComment.content}}</span></div>
									    	<div class="comment-tool"><span class="time"><i class="fa fa-clock-o"></i> {{DynamicComment.add_date | formatDate}}</span></div>
									    <form>
									    	<div class="comment-inputbox">
									    		<input type="text" placeholder="内容不超过200字" class="comm-input" />
									    		<input type="submit" value="回复" class="comm-sub"  />
									    	</div>
									    </form>
									    </div>
									</div>
									<!--回复结束-->
								</div>
								<div class="list-more">
								<a @tap="dynamicMore">...更多...</a>
								</div>
							</div>
						</div>
					</div>
					<div class="siderightbox">
						<div class="comm-box">
							<h3 class="comm-title"><span class="ht cur">我加入的村</span></h3>
							<ul class="text-img-list">
								<li v-for="(item,index) in myJoinVillageList" v-if='index<=9'>
									<a :href="'VillageIndex.do?method=villageIndex&village_id='+item.village_id">
										<img  :src="ctx+item.map.village_logo">
										<p title="item.map.village_name">{{item.map.village_name}}</p>
									</a>
								</li>
							</ul>
							<div class="list-more">
								<a @tap="myJoinVillageMore">...更多...</a>
							</div>
						</div>
						<div class="comm-box mt15">
							<h3 class="comm-title"><span class="ht cur">爱心扶贫</span></h3>
							<ul class="text-img-list headerlist">
								<li v-for="(item,index) in poorInfoList" v-if='index<=6'>
									<a :href="'VillageIndex.do?method=myIndex&village_id='+item.village_id+'&member_id='+item.user_id"><img :src="ctx+item.head_logo">
										<p title="item.real_name">{{item.real_name}}</p>
									</a>
								</li>
							</ul>
							<div class="list-more">
								<a @tap="poorListMore">...更多...</a>
							</div>
						</div>
						<div class="comm-box mt15">
							<h3 class="comm-title"><span class="ht cur">销售排行</span></h3>
							<ul class="text-img-list rankinglist">
								<li v-for="(item,index) in datas.saleRankList" v-if='index<=6'>
									<a :href="'VillageIndex.do?method=myIndex&member_id='+item.map.id">
									<img v-if="null == item.map.user_logo" src="${ctx}/styles/images/user64.png"/>
									<img v-if="null != item.map.user_logo && item.map.user_logo.indexOf('http://') == -1" :src="ctx + item.map.user_logo+'@s96x96'"/>
									<img v-if="null != item.map.user_logo && item.map.user_logo.indexOf('http://') != -1" :src="item.map.user_logo"/>
									<i>{{index+1}}</i><p title="item.map.user_name">{{item.map.user_name}}</p>
									</a>
								</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<script type="text/javascript" src="${ctx}/styles/mui/mui.min.js"></script>
			<script type="text/javascript" src="${ctx}/styles/mui/common.js"></script>
			<script type="text/javascript" src="${ctx}/scripts/vue/vue.min.js"></script>
			<script type="text/javascript" src="${ctx}/m/scripts/lightGallery/js/lightgallery-all.min.js?20180530"></script>
			<script type="text/javascript" src="${ctx}/styles/countryvillageMyIndex/js/jquery.SuperSlide.2.1.1.js"></script>
			<script type="text/javascript" src="${ctx}/styles/countryvillageMyIndex/js/global.js"></script>
			<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
			<script>
			Vue.filter('formatDate', function(value) {
				return Common.formatDate(value, 'yyyy-MM-dd HH:mm');
			});
			var p_index = localStorage.getItem('p_index_service_info');
			var add_user_id = localStorage.getItem('add_user_id_service_info');
				var vm = new Vue({
					el: '#countryIndex',
					data: {
						datas: "",
						DynamicList:"",
						ctx: Common.api,
						myJoinVillageList:new Array(),
						poorInfoList:new Array(),
						teChanInfoList:new Array(),
						search: "",
						logo: "styles/images/user.png",
						manageUserLogo: "styles/images/user.png",
						manageUser: "",
						banner: "styles/countryvillageMyIndex/images/gg.jpg",
						service_qrcode:"",
						p_index_list: "",
						startPage: 1,
						p_name: "",
						inputNum: "",
						dynamicStartPage:0,
						myJoinVillageStartPage:0,
						poorListStartPage:0,
						teChanListStartPage:0,
						ui:"",
					},
					mounted: function() {
						this.$nextTick(function() {
							this.getAjaxData();
						});
					},
					updated: function() {
						$('.lightgallery').lightGallery({download:false});
						jQuery(".sli-textbox").slide({
							titCell: ".hd ul",
							mainCell: ".slidemain",
							autoPage: true,
							effect: "top",
							autoPlay: true,
							vis: 1
						});
						jQuery(".xc-newspicbox").slide({
							mainCell: ".slidemain",
							autoPlay: true
						});
					},
					methods: {
						
						getAjaxData: function() {
							vm.p_name = localStorage.getItem('p_name_service_info');
							//获取县基本信息
							Common.getData({
								route: '/VillageIndex.do?method=getServiceInfo',
								data: {
									p_index: p_index,
								},
								success: function(data) {
									if(data.code == 0) {
										mui.toast(data.msg);
										return false;
									} else if(data.code == 1) {
										vm.datas = data.datas;
										
										if(null != data.datas.manageUser) {
											vm.manageUser = data.datas.manageUser;
										}
										if(null != data.datas.manageUser.user_logo) {
											vm.manageUserLogo=data.datas.manageUser.user_logo+"@s96x96";
										}
										if(null != data.datas.manageUser.p_index) {
											vm.p_index = data.datas.manageUser.p_index;				
										}
										if(null != data.datas.newsInfoList) {
											vm.newsInfoList = data.datas.newsInfoList;
										}
										if(null != data.datas.banner) {
											vm.banner = data.datas.banner;
										}
										if(null != data.datas.p_index_list) {
											vm.p_index_list = data.datas.p_index_list;
										}
										if(null != data.datas.user) {
											vm.ui=data.datas.user;
										}
										if(null != data.datas.entity.qrcode) {
											vm.service_qrcode = data.datas.entity.qrcode;
										}
										if(null != data.datas.entity.logo) {
											vm.logo = data.datas.entity.logo;
										}
									}
								},
								error: function() {
									mui.toast('好像出错了哦~');
								}
							});
							
							//获取动态信息
							Common.getData({
								route: '/VillageIndex.do?method=getServiceDynamicList',
								data: {
									p_index: p_index,
								},
								success: function(data) {
									if(data.code == 0) {
										mui.toast(data.msg);
										return false;
									} else if(data.code == 1) {
										vm.DynamicList = data.villageDynamicList;
									}
								},
								error: function() {
									mui.toast('好像出错了哦~');
								}
							});
							//活动本县商品
							this.getTeChanList(vm.teChanListStartPage);
							//获取动态信息
							this.getDynamicListData(vm.dynamicStartPage);
							//我加入的村
							this.getMyJoinVillageList(vm.myJoinVillageStartPage);
							//爱心扶贫
							this.getPoorList(vm.poorListStartPage);
						},
						
						dynamicMore:function() {
							this.getDynamicListData(vm.dynamicStartPage+1);
							vm.dynamicStartPage = vm.dynamicStartPage+1;
						},
						
						myJoinVillageMore:function() {
							this.getMyJoinVillageList(vm.myJoinVillageStartPage+1);
							vm.myJoinVillageStartPage = vm.myJoinVillageStartPage+1;
						},
						
						poorListMore:function() {
							this.getPoorList(vm.poorListStartPage+1);
							vm.poorListStartPage = vm.poorListStartPage+1;
						},
						teChanListMore:function() {
							this.getTeChanList(vm.teChanListStartPage+1);
							vm.teChanListStartPage = vm.teChanListStartPage+1;
						},
						buy: function(item) {
							if(vm.ui == null || vm.ui == ""){
								this.loginTip();
							}else{
								//加入购物车
								//判断是否支设置支付密码
								Common.getData({
									route: 'm/MVillage.do?method=isSetUpPasswordPay',
									success: function(data) {
										if(data.code == "1") {
											var url= vm.ctx + "/IndexShoppingNoCar.do?comm_id=" + item.comm_id + "&pd_count=" + 1 + "&comm_tczh_id=" + item.id + "&showAddress=true";
											window.location.href=url;
										}
										else if(data.code == "-1") {
											Common.confirm(data.msg, ["取消", "去设置"], function() {}, function() {
												var url = vm.ctx + "/manager/customer/MySecurityCenter.do?par_id=1100620000&mod_id=1100620100";
												window.location.href=url;
											});

										}
										else {
											mui.toast(data.msg);
										}

									},
									error: function() {
										mui.alert('好像出错了哦~');
									}
								});
							}
						},
						//获取动态
						getDynamicListData: function(startPage){
							Common.getData({
								route: '/VillageIndex.do?method=getServiceDynamicList',
								data: {
									p_index: p_index,
									startPage: startPage,
								},
								success: function(data) {
									if(data.villageDynamicList != null && data.villageDynamicList !=""){
										vm.DynamicList = vm.DynamicList.concat(data.villageDynamicList);
									}else{
										mui.toast('已全部加载！');
										return false;
									}
								},
								error: function() {
									mui.toast('好像出错了哦~');
								}
							});
						},
						
						//我加入的村
						getMyJoinVillageList: function(startPage){
							Common.getData({
								route: '/VillageIndex.do?method=myJoinVillageList',
								data: {
									startPage: startPage,
								},
								success: function(data) {
									if(data.myJoinVillageList != null && data.myJoinVillageList !=""){
										vm.myJoinVillageList = vm.myJoinVillageList.concat(data.myJoinVillageList);
									}else{
										mui.toast('已全部加载！');
										return false;
									}
								},
								error: function() {
									mui.toast('好像出错了哦~');
								}
							});
						},
						
						//爱心扶贫
						getPoorList: function(startPage){
							Common.getData({
								route: '/VillageIndex.do?method=poorList',
								data: {
									startPage: startPage,
									p_index: p_index,
								},
								success: function(data) {
									if(data.poorInfoList != null && data.poorInfoList !=""){
										vm.poorInfoList = vm.poorInfoList.concat(data.poorInfoList);
									}else{
										mui.toast('已全部加载！');
										return false;
									}
								},
								error: function() {
									mui.toast('好像出错了哦~');
								}
							});
						},
						//活动特产
						getTeChanList: function(startPage){
							Common.getData({
								route: '/VillageIndex.do?method=teChanList',
								data: {
									startPage: startPage,
									p_index: p_index,
									add_user_id: add_user_id,
								},
								success: function(data) {
									if(data.teChanInfoList != null && data.teChanInfoList !=""){
										vm.teChanInfoList = vm.teChanInfoList.concat(data.teChanInfoList);
									}else{
										mui.toast('已全部加载！');
										return false;
									}
								},
								error: function() {
									mui.toast('好像出错了哦~');
								}
							});
						},
						
						showInput: function(index) {
							vm.inputNum = index;
						},
						loginTip: function() {
							mui.toast('请先登录！');
// 							this.login();
						},
						login:function(){
							window.setTimeout(function () { 
								window.location.href=vm.ctx+"login.shtml?returnUrl="+window.location.href;
							}, 1500);
						},
						noInventory:function() {
							mui.toast('该商品已售罄！');
						},
						//评论
						commentSubmit: function(Dynamic,index) {
							vm.inputNum = "";
							Common.getData({
								route: 'm/MVillageDynamic.do?method=saveComment',
								data: {
									village_id: Dynamic.village_id,
									type: 1,//评论
									content: Dynamic.comm_content,
									id: Dynamic.id,
								},
								success: function(data) {
									if(data.code == 0) {
										mui.toast('请先登录！');
									}
									if(data.code == 1) {
										mui.toast('评论成功！');
										vm.DynamicList[index].map.commentList=vm.DynamicList[index].map.commentList.concat(data.insertComment);
										vm.DynamicList[index].map.commentList.map.real_name=vm.DynamicList[index].map.commentList.map.real_name.concat(data.insertComment.map.real_name);
									}
								},
								error: function() {
									mui.toast('好像出错了哦~');
								}
							});
						},
						//点赞	
						goDianZan: function(dynamic, index) {
							Common.getData({
								route: 'm/MVillageDynamic.do?method=saveComment&type=3&id=' + dynamic.id,
								data: {
									village_id: dynamic.village_id,
								},
								success: function(data) {
									if(data.code == 0) {
										mui.toast('请先登录！');
// 										this.login();
									}
									if(data.code == 1) {//点赞
										var names = vm.DynamicList[index].map.zanNameList.concat("、"+data.insertComment.map.real_name);
										vm.DynamicList[index].map.zanNameList = names;
										vm.DynamicList[index].map.is_zan=1;
										var zan_count = vm.DynamicList[index].map.zan_count+1;
										vm.DynamicList[index].map.zan_count = zan_count;
									}
									if(data.code == -2) {//取消点赞
										var array2= vm.DynamicList[index].map.zanNameList.split("、"); 
										array2.splice(data.cur_user_zanName_index,1);
										vm.DynamicList[index].map.zanNameList = array2.join("、");
										vm.DynamicList[index].map.is_zan=0;
										var zan_count = vm.DynamicList[index].map.zan_count-1;
										vm.DynamicList[index].map.zan_count = zan_count;
									}
									if(data.code == -1) {
										mui.toast(data.msg);
									}
								},
								error: function() {
									mui.alert('好像出错了哦~');
								}
							});
						},
						villageList:function(id){
							$.dialog({
								title:  "村站",
								width:  500,
								height: 800,
						        lock:true ,
								content:"url:${ctx}/VillageIndex.do?method=villageList"
							});
						},
						
						photoList:function(){
							$.dialog({
								title:  "县貌",
								width:  1200,
								height: 800,
						        lock:true ,
								content:"url:${ctx}/VillageIndex.do?method=servicePhoto&id="+vm.datas.entity.id
							});
						},
						
						xianQing:function(){
							$.dialog({
								title:  "县情",
								width:  500,
								height: 800,
						        lock:true ,
								content:"url:${ctx}/VillageIndex.do?method=xianQing&id="+vm.datas.entity.id
							});
						},
					}
				});
			</script>
	</body>

</html>