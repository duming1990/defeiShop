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
<link rel="stylesheet" type="text/css" href="${ctx}/m/scripts/lightGallery/css/lightgallery.css"/>
<link rel="stylesheet" href="${ctx}/m/scripts/dropload/css/dropload.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/mui/poppicker/mui.picker.min.css" />
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
.aui-slide-box .aui-slide-list{
	margin: 0px;
    overflow: hidden;
   height: auto;
}
.aui-slide-box .aui-slide-item-list{
	width: auto;
    white-space: nowrap;
    height: auto;
    font-size: 0;
}
.aui-slide-box .aui-slide-item-list .aui-slide-item-item{
    display: inline-block;
/*     width: 6rem; */
    margin-right: 12px;
    vertical-align: top;
    text-align: center;
}
.aui-slide-box .aui-slide-item-list .aui-slide-item-item .v-img{
    display: block;
    width: 3.5rem;
    height: 3.5rem;
    background-size: 62px;
    border-radius: 30px;
}
.aui-slide-box .aui-slide-item-list .aui-slide-item-item .aui-slide-item-title{
    text-align: center;
    line-height: 1rem;
    word-break: break-word;
    height: 2rem;
    white-space: normal;
    /* margin: 6px 0 4px; */
    font-size: 12px;
    color: #333;
}
.comm-class{
	text-align: center;
	color: #8f8f94;
	padding: 0.1rem .3rem;
	
}
.comm-class-choose{
    color: darkorange;
}
.comm-class-ul{
	line-height: 2rem;
    height: 2rem;
    display: flex;}
.commInfo_from{
margin-left: 5px;
font-size: 16px;
font-weight:bold;
padding:3px;
}
</style>
</head>
<body>
<div id="app" v-cloak>
    <header-item :header_title="village_name" canback="true" v-show="isApp"></header-item>
	<div :class="{'mui-content':isApp}">
		<div class="cunhead">
			<img class="bg" v-if="village_banner == ''" src="${ctx}/m/styles/village/img/cunbg.jpg" />
			<img class="bg" v-else :src="ctx +village_banner+'@s414x207'" onerror="this.src='${ctx}/m/styles/village/img/cunbg.jpg'" />
			<img class="cunlogo" v-if="village_logo == ''" src="${ctx}/styles/imagesPublic/user_header.png" />
			<img class="cunlogo" v-if="village_logo != ''" :src="ctx + village_logo+'@s200x200'" onerror="this.src='${ctx}/styles/imagesPublic/user_header.png'" />
			<div class="lightgallery" v-if="villageInfo.is_ren_zheng==1">
				<a :href="ctx + '/m/styles/img/cunrenzheng.jpg'">
					<img class="renzhen-logo pop-img" :src="ctx + renzheng_logo"/>
				</a>
			</div>
			<h3  style="padding-top:3px;">{{village_name}}...
				<c:if test="${link_area eq 1}">
				<span class="commInfo_from" @tap="delHomePage()" id="delHomePage">取消主页</span>
				<span class="commInfo_from" style=" display: none" @tap="setHomePage(villageInfo.id)" id="setHomePage">设为主页</span>
				</c:if>
				<c:if test="${link_area eq 0 || link_area eq null}">
				<span class="commInfo_from" style="display: none" @tap="delHomePage()" id="delHomePage">取消主页</span>
				<span class="commInfo_from"  @tap="setHomePage(villageInfo.id)" id="setHomePage">设为主页</span>
				</c:if>
			</h3>
			<p class="cuncount"><span @click="openMVillageMember">村民数量：{{datas.villageMemberCount}}</span> &nbsp;&nbsp;<span>昨日新增：+{{datas.yesterdayAddCount}}</span></p>
			<div class="join" @click="applyAddVillage">{{applyAdd}}</div>
			<div class="lightgallery">
				<a :href="ctx + village_qrcode+'@compress'"><i class="icon_qrcode pop-img"></i></a>
			</div>
			<a href="#showPicker"><i class="aui-icon aui-icon-back2 villageBack"></i></a>
		</div>
		<ul class="mui-table-view">
			<li class="mui-media cunzhu">
				<img class="mui-media-object mui-pull-left radius50" @click="openUser(villageManageUser.id);" v-if="villageManageUserLogo == ''" src="${ctx}/styles/images/user.png">
				<img class="mui-media-object mui-pull-left radius50" @click="openUser(villageManageUser.id);" v-else :src="ctx + villageManageUser.user_logo+'@s200x200'">
				<div class="mui-media-body">
					<h4 class="name" @click="openUser(villageManageUser.id);">{{villageManageUser.real_name}}</h4>
					<p class="ismhz">门户主</p>
					<div class="cunlod">
						<span class="radius50" @click="openMVillageMember">通讯录</span>
						<span class="radius50" @click="openMVillagePhoto">村貌</span>
						<span class="radius50" @click="openMVillageCunQing">村情</span>
					</div>
				</div>
			</li>
		</ul>
		<div class="toutiao" v-show="datas.newsInfoList != ''">
			<i class="tticon"></i>
			<span @click="openNews(item);" v-for="(item,index) in datas.newsInfoList" v-if="index == 0">{{item.title}}</span>
		</div>
		
		<div class="center-list mui-slider" id="pullrefresh" :style="{marginBottom:pullrefreshCss + 'px'}">
			<div class="inner mui-slider-indicator mui-segmented-control mui-segmented-control-inverted">
				<div class="cuncate village_cuncate swiper-wrapper">
					<a href="#itemcontent1" class="item mui-control-item mui-active" flag="1">村民动态</a>
					<a href="#itemcontent2" class="item mui-control-item" flag="2">村民商品</a>
<!-- 					<a href="#itemcontent3" class="item mui-control-item" flag="3">与我相关</a> -->
					<a href="#itemcontent4" class="item mui-control-item" flag="4">扶贫商品</a>
				</div>
			</div>
			<div class="center-content mui-slider-group center-content-c2">
				<div class="item mui-slider-item mui-control-content mui-active mui-scroll" flag="0" id="itemcontent1">
					<div>
						<div class="center-content2" v-for="(item,index) in villageDynamicList">
							<div class="center-content21">
								<div class="center-content211" @click="openUser(item.add_user_id);">
									<img v-if="null != item.map.user_logo && item.map.user_logo.indexOf('http://') == -1" :src="ctx + item.map.user_logo+'@s128x128'" onerror="this.src='${ctx}/styles/images/user.png'" />
									<img v-else-if="null != item.map.user_logo && item.map.user_logo.indexOf('http://') != -1" :src="item.map.user_logo" onerror="this.src='${ctx}/styles/images/user.png'" />
									<img v-else src="${ctx}/styles/images/user64.png" />
								</div>
								<div class="center-content212" @click="openUser(item.add_user_id);">
									<p class="center-content2121">{{item.map.real_name}}</p>
									<p class="center-content2121 pub_date">{{item.add_date | formatDate}}</p>
								</div>
							</div>
							<div class="center-content22">
							    <p class="center-content223 title">{{item.comm_name}}</p>
								<p class="center-content223">{{item.content}}</p>
								<div class="lightgallery">
									<a :href="ctx + item2.file_path +'@compress'" v-for="item2 in item.map.imgList">
										<img v-if="item.map.imgList && item.map.imgList.length == 1" class="center-content221 pop-img" :src="ctx + item2.file_path+'@compress'"  style="height:auto;width:40%!important;"/>
										<img v-else class="center-content221 pop-img" :style="item.map.imgList && item.map.imgList.length % 2 == 0 && item.map.imgList.length < 5   ? 'width:49%!important;height: auto;':''" :src="ctx + item2.file_path+'@s400x400'"/>
									</a>
								</div>
							</div>
							<p style="clear: both;height: 20px;">
								<span class="del-village" :id="'del'+item.id" v-if="ui.id == item.add_user_id" @click="del(item.id,index,1)"><span></span></span>
								<span class="ping-lun" :id="'liuyan'+item.id" @click="goLiuYan(item.id,1,index,1)"><span></span></span>
								<span v-bind:class="item.map.zan_count > 0 ? 'dian-zan1':'dian-zan'" :id="'dianzan'+item.id" @click="goDianZan(item,index,1)"><span></span></span>
							</p>
							<div class="line" style="margin-top: 5px;"></div>
							<div class="center-content24" :style="'display:'+item.map.zanList.length > 0 ? 'block':'none'">
								<p class="center-content241">
									<img src="${ctx}/m/styles/village/img/redxin.png">
									<span><span>{{item.map.zanNameList}}</span></span>
								</p>
							</div>
							<div class="line" v-show="null != item.map.zanList && item.map.zanList.length > 0"></div>
							
								<dynamic-comment-item :item="item" :is_show_huifu="1" :ctx="ctx" showindex="3"></dynamic-comment-item>
							
						</div>
					</div>
				</div>
				<div class="item mui-slider-item mui-control-content mui-scroll" id="itemcontent2" flag="0" style="background-color: #efeff4;">
					<div>
						<div class="swiper-container-comm" style="background: white;">
							<ul class="aui-slide-item-list swiper-wrapper comm-class-ul" style="display: -webkit-inline-box;">
							
							<li style="flex: 1;" @click="chooseClass(0)" id="cls_name0" :class="0 == cls_id ? 'aui-slide-item-item swiper-slide comm-class comm-class-choose' : 'aui-slide-item-item swiper-slide comm-class'">
								全部
							</li>
							<li style="flex: 1;" @click="chooseClass(item.id)" :class="item.id == cls_id ? 'aui-slide-item-item swiper-slide comm-class comm-class-choose' : 'aui-slide-item-item swiper-slide comm-class'" v-for="(item,index) in commClassDataList">
								{{item.type_name}}
							</li>
							</ul>
						</div>
						<div class="line"></div>
						
						<div class="center-content2" v-if="item.map.commInfoTczh != null" v-for="(item,index) in villageDynamicCommList">
							<div class="center-content21">
								<div class="center-content211" @click="openUser(item.add_user_id);">
									<img v-if="null != item.map.user_logo && item.map.user_logo.indexOf('http://') == -1" :src="ctx + item.map.user_logo+'@s128x128'" onerror="this.src='${ctx}/styles/images/user64.png'" />
									<img v-else-if="null != item.map.user_logo && item.map.user_logo.indexOf('http://') != -1" :src="item.map.user_logo" onerror="this.src='${ctx}/styles/images/user64.png'" />
									<img v-else src="${ctx}/styles/images/user64.png" />
								</div>
								<div class="center-content212" @click="openUser(item.add_user_id);">
									<p class="center-content2121">{{item.map.real_name}}</p>
									<p class="center-content2121 pub_date">{{item.add_date | formatDate}}</p>
								</div>
								<div class="comm-div" style="float: right;" @click="addCart(item);">
									<span class="money" v-if="item.map.commInfoTczh != null ">￥{{item.map.commInfoTczh.comm_price}}</span>
									<img class="addtocart" v-if="item.map.commInfoTczh != null" src="${ctx}/m/styles/village/img/cart_v2.png" />
								</div>
							</div>
							<div class="center-content22">
								<p class="center-content223 title">{{item.comm_name}}</p>
								<p class="center-content223">{{item.content}}</p>
								<div class="lightgallery">
									<a :href="ctx + item2.file_path+'@compress'" v-for="item2 in item.map.imgList">
										<img v-if="item.map.imgList && item.map.imgList.length == 1" class="center-content221 pop-img" :src="ctx + item2.file_path+'@compress'" style="height:auto;width:40%!important;"/>
										<img v-else class="center-content221 pop-img" :style="item.map.imgList && item.map.imgList.length % 2 == 0 && item.map.imgList.length < 5   ? 'width:49%!important;height: auto;':''" :src="ctx + item2.file_path+'@s400x400'"/>
									</a>
								</div>
							</div>
							<p style="clear: both;height: 20px;">
								<span v-if="item.map.commInfoTczh != null">库存：{{item.map.commInfoTczh.inventory}}</span>
								<span>
								<span class="del-village" :id="'del'+item.id" v-if="ui.id == item.add_user_id" @click="del(item.id,index,2)"><span></span></span>
								<span class="ping-lun" :id="'liuyan'+item.id" @click="goLiuYan(item.id,1,index,2)"><span></span></span>
								
								<span  v-bind:class="item.map.zan_count > 0 ? 'dian-zan1':'dian-zan'" :id="'dianzan'+item.id" @click="goDianZan(item,index,2)"><span></span></span>
<!-- 								<span v-else class="dian-zan" :id="'dianzan'+item.id" @click="goDianZan(item,index,)"><span></span></span> -->
								</span>
							</p>
							<div class="line" style="margin-top: 5px;"></div>
							<div class="center-content24" :style="'display:'+item.map.zanList.length > 0 ? 'block':'none'">
								<p class="center-content241">
									<img src="${ctx}/m/styles/village/img/redxin.png">
									<span><span>{{item.map.zanNameList}}</span></span>
								</p>
							</div>
							<div class="line" v-show="null != item.map.zanList && item.map.zanList.length > 0"></div>
							
							<dynamic-comment-item :item="item" :is_show_huifu="1" :ctx="ctx" showindex="3"></dynamic-comment-item>
							
						</div>
					</div>
				</div>
				<div class="item mui-slider-item mui-control-content mui-scroll" id="itemcontent4" flag="0" style="background-color: #efeff4;">
					<div>
						<div class="center-content2" v-if="item.map.commInfoTczh != null" v-for="(item,index) in commInfoList">
							<div class="center-content21">
								<div class="center-content211" @click="openUrl('MEntpInfo.do?method=index&entp_id='+item.own_entp_id);">
									<img v-if="null != item.map.entp_logo && item.map.entp_logo.indexOf('http://') == -1" :src="ctx + item.map.entp_logo+'@s128x128'" onerror="this.src='${ctx}/styles/images/user64.png'" /> --%>
									<img v-else-if="null != item.map.entp_logo && item.map.entp_logo.indexOf('http://') != -1" :src="item.map.entp_logo" onerror="this.src='${ctx}/styles/images/user64.png'" />
									<img v-else src="${ctx}/styles/images/user64.png" />
								</div>
								<div class="center-content212" @click="openUrl('MEntpInfo.do?method=index&entp_id='+item.own_entp_id);">
									<p class="center-content2121">{{item.map.entp_name}}</p>
									<p class="center-content2121 pub_date">{{item.add_date | formatDate}}</p>
								</div>
								<div class="comm-div" style="float: right;" @click="openUrl('MEntpInfo.do?id='+item.id);">
								<span class="money">￥{{item.map.commInfoTczh.comm_price}}</span>
								<img class="addtocart"  src="${ctx}/m/styles/village/img/cart_v2.png" />
								<span class="money" style="color: rgb(143, 143, 148);font-size: 10px;right: -2vw;top: 4.3vh;width: 110px;">扶贫金：￥{{(item.aid_scale * item.sale_price/100) | formatMoney}}</span>
							</div>
<!-- 								<div class="comm-div" style="float: right;" @click="viewComm(item);"> -->
<!-- 									<span class="money" v-if="item.map.commInfoTczh != null ">￥{{item.map.commInfoTczh.comm_price}}</span> -->
<%-- 									<img class="addtocart" v-if="item.map.commInfoTczh != null" src="${ctx}/m/styles/village/img/cart.png" /> --%>
<!-- 								</div> -->
							</div>
							<div class="center-content22">
								<p class="center-content223 title">{{item.comm_name}}</p>
								<p class="center-content223" onclick="showOrHideFuPin(this);">展开/隐藏扶贫对象</p>
								<div class="aui-slide-box" style="display:none;">
									<div class="aui-slide-list swiper-container-comm">
										<ul class="aui-slide-item-list swiper-wrapper">
											<li style="width:4.5rem!important;" @click="openUser(item2.map.user_id)" class="aui-slide-item-item swiper-slide join-village-li" v-for="(item2,index) in item.map.poorList">
												<a class="v-link">
													<img :src="ctx + item2.map.head_logo+'@s128x128'" class="v-img">
													<p class="aui-slide-item-title aui-slide-item-f-els">{{item2.map.real_name}}</p>
												</a>
											</li>
										</ul>
									</div>
								</div>
<!-- 								<div v-for="(item2,index) in item.map.poorList"> -->
<!-- 									<div style="float: left;"> -->
<!-- 										<img :src="ctx + item2.map.head_logo+'@s128x128'" style="width: 60px;"    /> -->
<!-- 										<span>{{item2.map.real_name}}</span> -->
<!-- 									</div> -->
								
<!-- 								</div> -->
								<div class="line"></div>
								
								<div class="lightgallery">
									<a :href="ctx + item2.file_path+'@compress'" v-for="item2 in item.commImgsList">
										<img v-if="item.commImgsList && item.commImgsList.length == 1" class="center-content221 pop-img" :src="ctx + item2.file_path+'@compress'" style="height:auto;width:40%!important;"/>
										<img v-else class="center-content221 pop-img" :style="item.commImgsList && item.commImgsList.length % 2 == 0 && item.commImgsList.length < 5   ? 'width:49%!important;height: auto;':''" :src="ctx + item2.file_path+'@s400x400'"/>
									</a>
								</div>
								
							</div>
<!-- 							<p style="clear: both;height: 20px;"> -->
<!-- 								<span v-if="item.map.commInfoTczh != null">库存：{{item.map.commInfoTczh.inventory}}</span> -->
<!-- 								<span> -->
<!-- 									<span class="del-village" :id="'del'+item.id" v-if="ui.id == item.add_user_id" @click="del(item.id,index,2)"><span></span></span> -->
<!-- 								<span class="ping-lun" :id="'liuyan'+item.id" @click="goLiuYan(item.id,1)"><span></span></span> -->
<!-- 								<span  v-if="item.map.zan_count > 0" class="dian-zan1" :id="'dianzan'+item.id" @click="goDianZan(item,index)"><span></span></span> -->
<!-- 								<span v-else class="dian-zan" :id="'dianzan'+item.id" @click="goDianZan(item,index)"><span></span></span> -->
<!-- 								</span> -->
<!-- 							</p> -->
							<div class="line" style="margin-top: 5px;"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="footers bottom-div" v-show="applyVillageInfo.audit_state == 1" style="z-index:100;">
			<button type="button" @click="pub(1);" class="mui-btn mui-btn-danger mui-btn-block pub_btn">发布动态</button>
			<button type="button" @click="pub(2);" class="mui-btn mui-btn-danger mui-btn-block pub_btn">发布商品</button>
		</div>
		<div class="footers bottom-div" v-show="applyVillageInfo.audit_state == 0" style="z-index:100;">
			<button type="button" @click="applyAddVillage" class="mui-btn mui-btn-danger mui-btn-block pub_btn" style="width: 90%;">申请加入</button>
		</div>
		<div class="replaymsg">
			<div class="dialogdiv-hide" @click="closeComment();"></div>
			<div class="dialogdiv">
				<textarea placeholder="这里填入回复的内容"></textarea>
				<div @click="comment();" class="bt">发送</div>
				<input type="hidden" id="comment_dyna_id">
				<input type="hidden" id="comment_dyna_type">
				<input type="hidden" id="comment_dyna_index">
				<input type="hidden" id="comment_dyna_flag">
			</div>
		</div>
	</div>
	<script type="text/javascript" src="${ctx}/m/scripts/lightGallery/js/lightgallery-all.min.js?20180530"></script> 
	<script src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
	<script type="text/javascript" src="${ctx}/m/scripts/dropload/js/dropload.min.js?v20160725"></script>
	<script type="text/javascript" src="${ctx}/styles/mui/poppicker/mui.picker.min.js"></script>
	<script type="text/javascript" src="${ctx}/scripts/swiper/swiper.min.js"></script>
	<script type="text/javascript">
	var vm = new Vue({
		el: '#app',
		data: {
			ctx: Common.api,
			villageInfo: '',
			village_logo: '',
			villageManageUser: '',
			villageManageUserLogo: '',
			applyVillageInfo: '',
			follow: '+关注',
			applyAdd: '申请加入',
			village_banner: "",
			village_qrcode: "",
			pullrefreshCss:0,
			villageDynamicList:new Array(),
			villageDynamicCommList:new Array(),
			villageDynamicRecordList:new Array(),
			commInfoList:new Array(),
			renzheng_logo: "m/styles/village/img/village_logo.png",
			showPickerList:[{value:1,text:"返回市级别"},{value:2,text:"返回县级别"},{value:3,text:"正常返回"}],
			datas: "",
			ui: "",
			commClassDataList:"",
			cls_id:"",
			village_name:"",
			isApp:false
		},
		mounted: function() {
			this.$nextTick(function() {
				Common.loading();
				this.getAjaxData();
				
				var inData = { 
		   			  	appId: '${appid}',
		       	      	timestamp: '${timestamp}',
		       	      	nonceStr: '${nonceStr}',
		       	      	signature: '${signature}'
		          };
		    	  var shareData = { 
		    		  	title : '${app_name}',
		  			 	desc : '${ShareVillagePortalDesc}',
		  				link : Common.api + 'm/MVillage.do?method=index&id=${af.map.id}',
		  				imgUrl : Common.api + '${logo}',
		          };
		    	  Common.weixinConfig(inData,shareData);
		    	  
		    	  mui('.mui-slider').slider().setStopped(true);
		    	  
		    	  Common.showPov('showPicker', vm.showPickerList, function($this) {
						var index = $this.getAttribute("data-value");
						if(index == 1){
							var url = vm.ctx + 'm/MCityCenter.do?index='+1;
							goUrl(url);
						}else if (index == 2){
							var url = vm.ctx + 'm/MServiceCenterInfo.do?method=index&id=' + vm.datas.serviceCenterInfo.id;
							goUrl(url);
						}else{
							history.go(-1);
						}
				  });
		    	  
		    	  mui('.cuncate').on('tap', 'a', function() {
						var flag = this.getAttribute("flag");
						Common.loading();
						vm.villageDynamicList = new Array();
						vm.villageDynamicCommList = new Array();
						vm.villageDynamicRecordList = new Array();
						if(flag == 1) {
							Common.getData({
								route: 'm/MVillage.do?method=getAjaxDataPage&id=${af.map.id}',
								data: {
									startPage: 0
								},
								success: function(data) {
									Common.hide();
									if(null != data.datas.villageDynamicList) {
										vm.villageDynamicList = vm.villageDynamicList.concat(data.datas.villageDynamicList);
										$("#itemcontent1").attr("flag", 1);
									}
								},
								error: function() {
									mui.alert('好像出错了哦~')
								}
							});
						}
						if(flag == 2) {
							Common.getData({
								route: 'm/MVillage.do?method=getAjaxDataComm&id=${af.map.id}',
								data: {
									startPage: 0,
									cls_id:vm.cls_id
								},
								success: function(data) {
									Common.hide();
									if(null != data.datas.villageDynamicCommList) {
										vm.villageDynamicCommList = vm.villageDynamicCommList.concat(data.datas.villageDynamicCommList);
										vm.commClassDataList = data.datas.commClassDataList;
										$("#itemcontent2").attr("flag", 1);
									}
								},
								error: function() {
									mui.alert('好像出错了哦~');
								}
							});
						}
// 						if(flag == 3) {
// 							Common.getData({
// 								route: 'm/MVillage.do?method=getAjaxDataRecord&id=${af.map.id}',
// 								data: {
// 									startPage: 0
// 								},
// 								success: function(data) {
// 									Common.hide();
// 									if(null != data.datas.villageDynamicRecordList) {
// 										vm.villageDynamicRecordList = vm.villageDynamicRecordList.concat(data.datas.villageDynamicRecordList);
// 										$("#itemcontent3").attr("flag", 1);
// 									}
// 								},
// 								error: function() {
// 									mui.alert('好像出错了哦~')
// 								}
// 							});
// 						}
						if(flag == 4) {
							Common.getData({
								route: 'm/MVillage.do?method=getAjaxDataFuPinComm&id=${af.map.id}',
								data: {
									startPage: 1
								},
								success: function(data) {
									Common.hide();
									if(null != data.datas.commInfoList) {
										vm.commInfoList = vm.commInfoList.concat(data.datas.commInfoList);
										$("#itemcontent4").attr("flag", 1);
									}
								},
								error: function() {
									mui.alert('好像出错了哦~')
								}
							});
						}
					});
		    	  $('#itemcontent1').dropload({
				        scrollArea : window,
				        autoLoad : true,
				        loadDownFn : function(me){
				        	var flag = $("#itemcontent1").attr('flag');
				        	flag = Number(flag);
							Common.getData({
								route: 'm/MVillage.do?method=getAjaxDataPage&id=${af.map.id}',
								data: {
									startPage: flag
								},
								success: function(data) {
									if(null != data.datas.villageDynamicList) {
										vm.villageDynamicList = vm.villageDynamicList.concat(data.datas.villageDynamicList);
										setTimeout(function(){
					                        me.resetload();
					                    },500); 
									}
									flag += 1;
									$("#itemcontent1").attr("flag", flag);
									if (data.code == 2) {
				                         me.lock();// 锁定
				                         me.noData(); // 无数据
				   					}
								},
								error: function() {
									mui.alert('好像出错了哦~')
								}
							});
				        },
				        domUp : {// 上方DOM                                                       
				            domClass   : 'dropload-up',
				            domRefresh : '<div class="dropload-refresh"><i class="fa fa-long-arrow-down"></i>&nbsp;下拉刷新</div>',
				            domUpdate  : '<div class="dropload-update"><i class="fa fa-long-arrow-up"></i>&nbsp;释放更新</div>',
				            domLoad    : '<div class="dropload-load"><span class="loading"></span>刷新中...</div>'
				        },
				        threshold : 50
				    });
		    	  
		    	  $('#itemcontent2').dropload({
				        scrollArea : window,
				        autoLoad : true,     
				        loadDownFn : function(me){
				        	var flag = $("#itemcontent2").attr('flag');
				        	flag = Number(flag);
							Common.getData({
								route: 'm/MVillage.do?method=getAjaxDataComm&id=${af.map.id}',
								data: {
									startPage: flag,
									cls_id:vm.cls_id
								},
								success: function(data) {
									if(null != data.datas.villageDynamicCommList) {
										vm.villageDynamicCommList = vm.villageDynamicCommList.concat(data.datas.villageDynamicCommList);
										setTimeout(function(){
					                        me.resetload();
					                    },500); 
									}
									flag += 1;
									$("#itemcontent2").attr("flag", flag);
									if (data.code == 2) {
				                         me.lock();// 锁定
				                         me.noData(); // 无数据
				   					} 
								},
								error: function() {
									mui.alert('好像出错了哦~')
								}
							});
				        },
				        domUp : {// 上方DOM                                                       
				            domClass   : 'dropload-up',
				            domRefresh : '<div class="dropload-refresh"><i class="fa fa-long-arrow-down"></i>&nbsp;下拉刷新</div>',
				            domUpdate  : '<div class="dropload-update"><i class="fa fa-long-arrow-up"></i>&nbsp;释放更新</div>',
				            domLoad    : '<div class="dropload-load"><span class="loading"></span>刷新中...</div>'
				        },
				        threshold : 50
				    });
		    	  
		    	  $('#itemcontent4').dropload({
				        scrollArea : window,
				        autoLoad : true,     
				        loadDownFn : function(me){
				        	var flag = $("#itemcontent4").attr('flag');
				        	flag = Number(flag);
							Common.getData({
								route: 'm/MVillage.do?method=getAjaxDataFuPinComm&id=${af.map.id}',
								data: {
									startPage: flag
								},
								success: function(data) {
									if(null != data.datas.commInfoList) {
										vm.commInfoList = vm.commInfoList.concat(data.datas.commInfoList);
										setTimeout(function(){
					                        me.resetload();
					                    },500); 
									}
									flag += 1;
									$("#itemcontent4").attr("flag", flag);
									if (data.code == 2) {
				                         me.lock();// 锁定
				                         me.noData(); // 无数据
				   					} 
								},
								error: function() {
									mui.alert('好像出错了哦~')
								}
							});
				        },
				        domUp : {// 上方DOM                                                       
				            domClass   : 'dropload-up',
				            domRefresh : '<div class="dropload-refresh"><i class="fa fa-long-arrow-down"></i>&nbsp;下拉刷新</div>',
				            domUpdate  : '<div class="dropload-update"><i class="fa fa-long-arrow-up"></i>&nbsp;释放更新</div>',
				            domLoad    : '<div class="dropload-load"><span class="loading"></span>刷新中...</div>'
				        },
				        threshold : 50
				    });
		    	  
		    	  
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
				spaceBetween: 10
			});
		},
		methods: {
			getAjaxData: function() {
				Common.getData({
					route: 'm/MVillage.do?method=getAjaxData&id=${af.map.id}',
					success: function(data) {
						if(data.code == 0) {
							mui.alert(data.msg);
							return false;
						}else if(data.code == 1) {
							Common.hide();
							vm.datas = data.datas;
							vm.villageInfo = data.datas.villageInfo;
							vm.isApp = data.datas.isApp;
							if(null != data.datas.villageInfo.village_logo) {
								vm.village_logo = data.datas.villageInfo.village_logo;
							}
							if(null != data.datas.villageInfo.village_banner) {
								vm.village_banner = data.datas.villageInfo.village_banner;
							}
							if(null != data.datas.villageInfo.village_qrcode) {
								vm.village_qrcode = data.datas.villageInfo.village_qrcode;
							}
							if(null != data.datas.villageInfo.village_name){
								vm.village_name = data.datas.villageInfo.village_name.substring(0,3);
								
							}
							
							vm.villageManageUser = data.datas.villageManageUser;
							if(null != data.datas.villageManageUser.user_logo) {
								vm.villageManageUserLogo = data.datas.villageManageUser.user_logo;
							}
							if(null != data.datas.guanzhu_count && data.datas.guanzhu_count > 0) {
								vm.follow = "已关注";
							}
							if(null != data.datas.applyVillageInfo) {
								vm.applyVillageInfo = data.datas.applyVillageInfo;
								if(data.datas.applyVillageInfo.audit_state == 0) {
									vm.applyAdd = "待审核";
								}
								else if(data.datas.applyVillageInfo.audit_state == 1) {
									vm.applyAdd = "已加入";
									vm.pullrefreshCss = 65;
								}
								else {
									vm.applyAdd = "重新申请";
								}
							}
							if(null != data.datas.ui) {
								vm.ui = data.datas.ui;
							}
							

						}else if(data.code == -2) {
							mui.toast(data.msg);
							setTimeout(function() {
								Common.goBack();
							}, 1000);

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
			openTeam: function(url) {
				if(vm.applyVillageInfo == '' || vm.applyVillageInfo.audit_state != 1) {
					mui.toast("未加入村子，无法查看");
					return false;
				}
				var link_url = vm.ctx + url;
				location.href = link_url;
			},
			openUser: function(id) {
				var link_url = vm.ctx + "m/MUserCenter.do?method=index&user_id=" + id;
				location.href = link_url;
			},
			showPinDao: function() {
				var html = $("#pinDaoContent").html();
				setTimeout(function() {
					Common.showLayUi(html, false, true);
				}, 100);
			},
			btnSearch: function() {
				var keyword = $("#search-content").val();
				var url = vm.ctx + "m/MSearch.do?keyword=" + keyword;
				goUrl(url);
			},
			getTag: function(tag_id) {
				var url = vm.ctx + "m/MSearch.do";
				goUrl(url);
			},
			chooseClass:function(cls_id,event){
				Common.getData({
					route: 'm/MVillage.do?method=getAjaxDataComm',
					data: {
						id:vm.villageInfo.id,
						startPage: 0,
						cls_id:cls_id
					},
					success: function(data) {
						Common.hide();
						vm.cls_id = cls_id;
						if(null != data.datas.villageDynamicCommList) {
							vm.villageDynamicCommList = new Array();
							vm.villageDynamicCommList = vm.villageDynamicCommList.concat(data.datas.villageDynamicCommList);
							$("#itemcontent2").attr("flag", 1);
						}
					},
					error: function() {
						mui.alert('好像出错了哦~');
					}
				});
			},
			applyAddVillage: function() {
				Common.getData({
					route: '/m/MVillage.do?method=applyAdd&id=' + vm.villageInfo.id,
					success: function(data) {
						if(data.code == -3) {
							var url = vm.ctx + "m/MRegister.do?from=share&suid=" + vm.villageManageUser.id
							goUrl(url);
						}
						else if(data.code == -2) {
							Common.confirm(data.msg, ["取消", "去认证"], function() {}, function() {
								var url = vm.ctx + "m/MMySecurityCenter.do?method=setRenzheng";
								goUrl(url);
							});
						}
						else if(data.code == 1) {
							mui.toast(data.msg);
							vm.applyAdd = "待审核";
						}
						else {
							mui.toast(data.msg);
						}
					},
					error: function() {
						mui.alert('好像出错了哦~');
					}
				});
			},
			followUser: function() {
				Common.getData({
					route: 'm/MVillage.do?method=follow&id=' + vm.villageManageUser.id,
					success: function(data) {
						if(data.code == 1) {
							
							mui.toast(data.msg);
							vm.follow = "已关注";
						}
						if(data.code == -1) {
							mui.toast(data.msg);
							return false;
						}
						if(data.code == 0) {
							Common.confirm(data.msg, ["取消", "去登陆"], function() {}, function() {
								var url = vm.ctx + "m/MIndexLogin.do";
								goUrl(url);
							});
						}
					},
					error: function() {
						mui.alert('好像出错了哦~');
					}
				});
			},
			pub: function(type) {
				var url = vm.ctx + "m/MVillageDynamic.do?village_id=" + vm.villageInfo.id + "&type=" + type;
				goUrl(url);
			},
			openNews: function(item) {
				var url = vm.ctx + "m/MNewsInfo.do?link_id=" + vm.villageInfo.id + "&mod_id=1500301060";
				goUrl(url);
			},
			viewComm: function(item) {
				var url = vm.ctx + "m/MEntpInfo.do?id=" + item.id;
				goUrl(url);
			},
			comment: function() {
				Common.loading();
				var id = $("#comment_dyna_id").val();
				var index = $("#comment_dyna_index").val();
				var flag = $("#comment_dyna_flag").val();
				var type = $("#comment_dyna_type").val();
				var content = $("#comment" + id).val();
				Common.getData({
					route: 'm/MVillageDynamic.do?method=saveComment',
					data: {
						village_id: '${af.map.id}',
						type: type,
						content: content,
						id: id,
					},
					success: function(data) {
						Common.hide();
						mui.toast(data.msg);
						if(data.code == 0) {
							login();
							$(".replaymsg").hide();
						}
						if(data.code == 1) {
							$("#comment" + id).val("");
							$(".replaymsg").hide();
							if(flag == 1){
								vm.villageDynamicList[index].map.commentList = vm.villageDynamicList[index].map.commentList.concat(data.insertComment);
							}
							if(flag == 2){
								vm.villageDynamicCommList[index].map.commentList = vm.villageDynamicCommList[index].map.commentList.concat(data.insertComment);
							}
						}
					},
					error: function() {
						mui.alert('好像出错了哦~');
					}
				});
			},
			goDianZan: function(item, index,flag) {
				Common.getData({
					route: 'm/MVillageDynamic.do?method=saveComment&type=3&id=' + item.id,
					data: {
						village_id: vm.villageInfo.id,
					},
					success: function(data) {
						if(data.code == 0) {
							login();
						}
						//赞
						if(data.code == 1) {
							var names = "";
							if(flag == 1){
								if(null != vm.villageDynamicList[index]){
									names = vm.villageDynamicList[index].map.zanNameList;
								}
							}else{
								if(null != vm.villageDynamicCommList[index]){
									names = vm.villageDynamicCommList[index].map.zanNameList;
								}
							}
							if($("#dianzan" + item.id).attr("class") == "dian-zan") {
								$("#dianzan" + item.id).attr("class", "dian-zan1");
								if(names.length > 0) {
									names += "、"+vm.ui.real_name;
								}else {
									names += vm.ui.real_name;
								}
							}else {
								$("#dianzan" + item.id).attr("class", "dian-zan");
								var last_index = names.lastIndexOf("、");
								names = names.substring(0, last_index);
							}
							if(flag == "1"){
								vm.villageDynamicList[index].map.zanNameList = names;
								vm.villageDynamicList[index].map.zan_count = item.map.zan_count + 1;
							}else{
								vm.villageDynamicCommList[index].map.zanNameList = names;
								vm.villageDynamicCommList[index].map.zan_count = item.map.zan_count + 1;
							}
							
						}
						if(data.code == -2) {
							if(flag == 1){
								var array2 = vm.villageDynamicList[index].map.zanNameList.split("、"); 
								array2.splice(data.cur_user_zanName_index,1);
								vm.villageDynamicList[index].map.zanNameList = array2.join("、");
								vm.villageDynamicList[index].map.zan_count = 0;
							}else{
								var array2 = vm.villageDynamicCommList[index].map.zanNameList.split("、"); 
								array2.splice(data.cur_user_zanName_index,1);
								vm.villageDynamicCommList[index].map.zanNameList = array2.join("、");
								vm.villageDynamicCommList[index].map.zan_count = 0;
							}
							
// 							var zan_count = vm.villageDynamicList[index].map.zan_count - 1;
// 							vm.DynamicList[index].map.zan_count = zan_count;
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
			goLiuYan: function(id, type,index,flag) {
				$(".replaymsg").show();
				$(".replaymsg textarea").focus();
				$(".replaymsg textarea").attr("name", "comment" + id);
				$(".replaymsg textarea").attr("id", "comment" + id);
				$("#comment_dyna_id").val(id);
				$("#comment_dyna_type").val(type);
				$("#comment_dyna_index").val(index);
				$("#comment_dyna_flag").val(flag);
			},
			openMVillageMember: function() {
				if(vm.applyVillageInfo == '' || vm.applyVillageInfo.audit_state != 1) {
					mui.toast("未加入村子，无法查看");
					return false;
				}

				var url = vm.ctx + "m/MVillageMember.do?village_id=" + vm.villageInfo.id;
				goUrl(url);
			},
			addCart: function(item) {
				//判断是否支设置支付密码
				var url = vm.ctx + "m/MUserCenter.do?method=MUserCommInfo&id=" + item.id;
				goUrl(url);
			},
			closeComment: function() {
				$(".replaymsg").hide();
			},
			openMVillagePhoto: function() {
				var url = vm.ctx + "m/MVillage.do?method=photo&village_id=${af.map.id}";
				goUrl(url);
			},
			openMVillageCunQing: function() {
				var url = vm.ctx + "m/MVillage.do?method=cunqing&village_id=${af.map.id}";
				goUrl(url);
			},
			del: function(id, index, flag) {
				Common.confirm("确认删除该条动态吗？", ["取消", "确认"], function() {}, function() {
					Common.getData({
						route: 'm/MVillageDynamic.do?method=del',
						data: {
							village_id: id
						},
						success: function(data) {
							if(data.code == "1") {
								mui.toast(data.msg);
								if(flag == 1) {
									vm.villageDynamicList.splice(index, 1)
								}
								else if(flag == 2) {
									vm.villageDynamicCommList.splice(index, 1)
								}
							}
							else {
								mui.toast(data.msg);
							}
						},
						error: function() {
							mui.alert('好像出错了哦~');
						}
					});
				});
			}
		}
	});
	function setHomePage(id){
		Common.getData({
			route: '/m/MCityCenter.do?method=setHomePage',
			data: {
				link_area:'/MVillage.do?method=index&id='+id
			},
			success: function(data) {
				mui.toast(data.msg);
				$("#setHomePage").hide();
				$("#delHomePage").show();
			},
			error: function() {
				mui.toast('好像出错了哦~');
			}
		});
	}
	function delHomePage(){
		Common.getData({
			route: '/m/MCityCenter.do?method=setHomePage',
			data: {
				link_area: '/m/MCityCenter.do',
			},	
			success: function(data) {
				mui.toast(data.msg);
				$("#setHomePage").show();
				$("#delHomePage").hide();
			},
			error: function() {
				mui.toast('好像出错了哦~');
			}
		});
	}
	
	function showOrHideFuPin(obj){
		if($(obj).parent().find(".aui-slide-box").is(":hidden")){
			$(obj).parent().find(".aui-slide-box").show();
		}else {
			$(obj).parent().find(".aui-slide-box").hide();
		}
	}
	</script>
</body>

</html>