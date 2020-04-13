<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>${app_name_min}触屏版</title>
	<meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0" name="viewport"/>
	<meta content="yes" name="apple-mobile-web-app-capable"/>
	<meta content="black" name="apple-mobile-web-app-status-bar-style"/>
	<meta content="telephone=no" name="format-detection"/>
	<jsp:include page="../_public_in_head_new.jsp" flush="true" />
	<jsp:include page="../_public_css.jsp" flush="true" />
	<link rel="stylesheet" href="${ctx}/m/scripts/dropload/css/dropload.css" />
	<link rel="stylesheet" type="text/css" href="${ctx}/m/scripts/lightGallery/css/lightgallery.css"/>
	<style type="text/css">
		.pub_btn{
			float: left;margin-left: 1rem;width: 45%;
		}
		.center-list{margin-bottom:0px;}
		.mui-pull-bottom-tips {
			text-align: center;
			font-size: 12px;
			line-height: 40px;
			color: #777;
		}
		.center-content .center-content2{
			
		}
		.center-info .name .center-name p{height: 6.5vw;line-height: 6.5vw;padding-top: 0.2rem;}
		.center-set .center-set1 .center-set11{margin-bottom:10px!important;}
		.center-set .center-set1 .center-set12{width:40%!important;}
		.center-content .item{border:none!important;}
 		.lightgallery{overflow:auto;zoom:1;} 
 		#fp_comm a{width: 32% !important; 
     height: 32vw; 
     padding: 0; 
     display: block; 
     float: left; 
     margin-right: 1%; 
     margin-bottom: 5px; 
    }
	</style>
</head>
<body>
<div id="app" v-cloak style="background-color: #efeff4;">
   <header-item :header_title="user.real_name" canback="true"></header-item>
	<div class="mui-content">
		<div class="centerhead">
			<img class="bg" src="${ctx}/m/styles/village/img/personal_center_bg.jpg"/>
			<img class="headimg" v-if="user_logo != '' && user_logo.indexOf('http://') == -1" :src="ctx + user_logo + '@s100x100'"/>
			<img class="headimg" v-else-if="user_logo != '' && user_logo.indexOf('http://') != -1" :src="user_logo"/>
			<img class="headimg" v-else src="${ctx}/styles/images/user.png"/>
		</div>
		<div class="lightgallery" v-if="null != poorInfo.poor_qrcode">
			<a :href="ctx + poorInfo.poor_qrcode+'@compress'"><i class="icon_qrcode pop-img" style="background-size: 40px 40px;width: 40px;height: 40px;left: 8%;top: 12.5%;"></i></a>
		</div>
		<div class="center-info">
			<div class="name" style="height: 19.5vh;">
				<div class="center-name">
					<p>{{user.real_name}}<span v-if="user.is_poor == 1" style="vertical-align:text-top;padding-left: .2rem;"><img alt="" src="${ctx}/m/styles/village/img/pinkunhu.png" width="20"></span>
					</br>
					<span class="autograph" v-if="null != user.autograph" style="max-height: 13vw;display: block;overflow: hidden;">{{user.autograph}}</span>
					</p>
				</div>
				<div class="follow" style="margin-top: 6.5vw;">
				
					<div class="follow-left">
						<p class="p-font">{{datas.zan_count}}</p>
						<p>赞</p>
					</div>
					<div class="follow-left" @click="guanzhu(1)">
						<p class="p-font" >{{datas.guanzhu_count}}</p>
						<p>关注</p>
					</div>
					<div class="follow-left" @click="guanzhu(2)">
						<p class="p-font" >{{datas.fensi_count}}</p>
						<p>粉丝</p>
					</div>
					<div class="follow-right">
						<p>
							<a v-if="follow_name == '关注'" @click="follow" style="text-decoration:none;out-line: none;color: #000;">{{follow_name}}</a>
							<a v-else @click="noFollow();" style="text-decoration:none;out-line: none;color: #000;">{{follow_name}}</a>
						</p>
					</div>
				</div>
			</div>
		</div>
		<div class="center-set">
			<div class="center-set1 sm" v-show="user.is_renzheng  == 1">
				<p class="center-set11 p-font">实名认证</p>
				<p class="center-set12"><span>已认证</span></p>
			</div>
			<div class="center-set1 poor" v-if="'' != poorInfo">
				<p class="center-set11 p-font">贫困认证</p>
				<p class="center-set12" @click="openUrl('MUserCenter.do?method=viewPoorDetails&poor_id='+poorInfo.id);"><span>查看详情</span></p>
			</div>
			<div class="center-set1 mc">
				<p class="center-set11 p-font">我卖出去的</p>
				<p class="center-set12"><span>约{{orderInfoTodaySumOut}}元</span></p>
			</div>
			<div class="center-set1 md">
				<p class="center-set11 p-font">我买到的</p>
				<p class="center-set12"><span>约{{orderInfoTodaySumBuy}}元</span></p>
			</div>
		</div>
		
		<div class="center-list mui-slider" id="pullrefresh">
			<div class="inner mui-slider-indicator mui-segmented-control mui-segmented-control-inverted">
				<div class="cuncate swiper-wrapper" id="cuncate" >
				<div style="width: 23%;display: inline-block;">
					<a href="#itemcontent1" class="item mui-control-item mui-active" flag="1" style="display: inline-table;">动态</a>
				</div>
				<div style="width: 23%;display: inline-block;">
					<a href="#itemcontent2" class="item mui-control-item" flag="2" style="display: inline-table;">商品</a>
				</div>
				<div style="width: 23%;display: inline-block;" v-if="user.is_poor == 1">
					<a href="#itemcontent3" class="item mui-control-item" flag="3"  style="display: inline-table;">扶贫商品</a>
				</div>
				<div style="width: 23%;display: inline-block;">
					<a href="#itemcontent4" class="item mui-control-item" flag="4" style="display: inline-table;">TA</a>
				</div>
				</div>
			</div>
			<div class="center-content mui-slider-group center-content-c2">
				<!-- 动态 -->
				<div class="item mui-slider-item mui-control-content mui-active mui-scroll" flag="0" id="itemcontent1">
					<div>
					<div class="center-content2" v-for="(item,index) in villageDynamicList">
						<div class="center-content21">
							<div class="center-content211" @click="openUser(item.add_user_id);">
								<img v-if="null != item.map.user_logo && item.map.user_logo.indexOf('http://') == -1" :src="ctx + item.map.user_logo+'@s100x100'" />
								<img v-else-if="null != item.map.user_logo && item.map.user_logo.indexOf('http://') != -1" :src="item.map.user_logo"/>
								<img v-else src="${ctx}/styles/images/user.png" />
							</div>
							<div class="center-content212" @click="openUser(item.add_user_id);">
								<p class="center-content2121">{{item.map.real_name}}</p>
								<span style="color: #8f8f94;">{{item.add_date | formatDate}}</span>
							</div>
						</div>
						<div class="center-content22">
							<p class="center-content223">{{item.content}}</p>
							<div class="lightgallery">
								<a :href="ctx + item2.file_path+'@compress'" v-for="item2 in item.map.imgList">
									<img v-if="item.map.imgList && item.map.imgList.length == 1" class="center-content221 pop-img" :src="ctx + item2.file_path+'@compress'" style="height:auto;width:40%!important;"/>
									<img v-else class="center-content221 pop-img" :style="item.map.imgList && item.map.imgList.length % 2 == 0 && item.map.imgList.length < 5   ? 'width:49%!important;height: auto;':''" :src="ctx + item2.file_path+'@s400x400'"/>
								</a>
							</div>
						</div>
						<p style="clear: both;">
							<span><img src="${ctx}/m/styles/village/img/dingwei.png" style="margin-bottom: -2px;width: 17px;">{{item.map.village_name}}</span>
							<span class="del-village" :id="'del'+item.id" v-if="ui.id == item.add_user_id" @click="del(item.id,index,1)"><span></span></span>
							<span class="ping-lun" :id="'liuyan'+item.id" @click="goLiuYan(item.id,1,index,1)"><span></span></span>
							<span v-bind:class="item.map.zan_count > 0 ? 'dian-zan1':'dian-zan'" :id="'dianzan'+item.id" @click="goDianZan(item,index,1)"><span></span></span>
						</p>
						<div class="line" v-show="null !=item.map.zanList && item.map.zanList.length > 0"></div>
						<div class="center-content24" :style="'display:'+item.map.zanList.length > 0 ? 'block':'none'">
							<p class="center-content241">
								<img src="${ctx}/m/styles/village/img/redxin.png">
								<span><span>{{item.map.zanNameList}}</span></span>
							</p>
						</div>
						
						<div class="line" v-show="null != item.map.commentList && item.map.commentList.length > 0"></div>
						
<!-- 						<dynamic-comment-item :item="item" :is_show_huifu="1" :ctx="ctx"></dynamic-comment-item> -->
						
						<dynamic-comment-item :item="item" :is_show_huifu="1" :ctx="ctx" showindex="3"></dynamic-comment-item>
						
						
					</div>
				</div>
				</div>
				<!-- 商品 -->
				<div class="item mui-slider-item mui-control-content mui-scroll" id="itemcontent2"  flag="0" style="background-color: #efeff4;">
					<div>
					<div class="center-content2" v-for="(item,index) in villageDynamicCommList">
						<div class="center-content21">
							<div class="center-content211" @click="openUser(item.add_user_id);">
								<img v-if="null != item.map.user_logo && item.map.user_logo.indexOf('http://') == -1" :src="ctx + item.map.user_logo+'@s100x100'" />
								<img v-else-if="null != item.map.user_logo && item.map.user_logo.indexOf('http://') != -1" :src="item.map.user_logo" />
								<img v-else src="${ctx}/styles/images/user.png" />
							</div>
							<div class="center-content212" @click="openUser(item.add_user_id);">
								<p class="center-content2121">{{item.map.real_name}}</p>
								<span style="color: #8f8f94;">{{item.add_date | formatDate}}</span>
							</div>
							<div class="comm-div" style="float: right;" @click="addCart(item);">
								<span class="money">￥{{item.map.commInfoTczh.comm_price}}</span>
								<img class="addtocart"  src="${ctx}/m/styles/village/img/cart_v2.png" />
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
						<p style="clear: both;">
							<span><img src="${ctx}/m/styles/village/img/dingwei.png" style="margin-bottom: -2px;width: 17px;">{{item.map.village_name}}</span>
							<span class="del-village" :id="'del'+item.id" v-if="ui.id == item.add_user_id" @click="del(item.id,index,2)"><span></span></span>
							<span class="ping-lun" :id="'liuyan'+item.id" @click="goLiuYan(item.id,1,index,2)"><span></span></span>
							<span v-bind:class="item.map.zan_count > 0 ? 'dian-zan1':'dian-zan'" :id="'dianzan'+item.id" @click="goDianZan(item,index,2)"><span></span></span>
						</p>
						<div class="line" v-show="null !=item.map.zanList && item.map.zanList.length > 0"></div>
						<div class="center-content24" :style="'display:'+item.map.zanList.length > 0 ? 'block':'none'">
							<p class="center-content241">
								<img src="${ctx}/m/styles/village/img/redxin.png">
								<span><span>{{item.map.zanNameList}}</span></span>
							</p>
						</div>
						<div class="line" v-show="null != item.map.commentList && item.map.commentList.length > 0"></div>
						
						
						<dynamic-comment-item :item="item" :is_show_huifu="1" :ctx="ctx" showindex="3"></dynamic-comment-item>
						
						<div class="line_all"></div>
					</div>
					</div>
				</div>
				<!-- 扶贫商品 -->
				<div class="item mui-slider-item mui-control-content mui-scroll" id="itemcontent3"  flag="0">
					<div>
					<div class="center-content2" v-for="(item,index) in commInfoPoorsList" style="clear: both;margin-bottom: 10px;">
						<div class="center-content21">
							<div class="center-content211" @click="openUser(item.add_user_id);">
								<img v-if="null != item.map.entp_logo" :src="ctx + item.map.entp_logo+'@s200x200'" />
								<img v-else src="${ctx}/styles/images/user.png" />
							</div>
							<div class="center-content212" @click="openUser(item.add_user_id);">
								<p class="center-content2121">{{item.map.entp_name}}</p>
								<span style="color: #8f8f94;">{{item.add_date | formatDate}}</span>
							</div>
							<div class="comm-div" style="float: right;" @click="openUrl('MEntpInfo.do?id='+item.comm_id);">
								<span class="money">￥{{item.map.sale_price}}</span>
								<img class="addtocart"  src="${ctx}/m/styles/village/img/cart_v2.png" />
								<span class="money" style="color: rgb(143, 143, 148);font-size: 10px;right: -2vw;top: 4.3vh;width: 110px;">扶贫金：￥{{(item.map.aid_scale * item.map.sale_price/100) | formatMoney}}</span>
							</div>
						</div>
						<div class="center-content21" style="height: 3vh;clear: both;overflow: hidden;">
							<div class="center-content212" @click="openUser(item.add_user_id);" style="width: 100%;">
								<p class="center-content2121" style="color: #222;font-weight: 400;">{{item.map.comm_name}}</p>
							</div>
						</div>
						<div class="center-content22">
							<div class="lightgallery" id="fp_comm">
<!-- 								<a class="mui-control-item" :href="ctx + item.map.main_pic+'@compress'" :title="item.map.comm_name" > -->
<!-- 									<img class="center-content221 pop-img" v-if="null != item.map.main_pic" :src="ctx + item.map.main_pic+'@s400x400'"/> -->
<!-- 								</a> -->
								<a class="mui-control-item" v-for="item2 in item.commImgsList" :href="ctx + item2.file_path+'@compress'">
									<img v-if="item.map.imgList && item.map.imgList.length == 1" class=" pop-img" :src="ctx + item2.file_path+'@compress'" style="height:auto;"/>
									<img v-else class=" pop-img" :style="item.commImgsList && item.commImgsList.length % 2 == 0 && item.commImgsList.length < 5  ? 'height: auto;':''" :src="ctx + item2.file_path+'@s400x400'"/>
								</a>
							</div>
						</div>
						
					</div>
					<div class="line_all"></div>
					</div>
				</div>
				<!-- TA -->
				<div class="item mui-slider-item mui-control-content mui-scroll" id="itemcontent4" flag="0">
					<div>
					<div class="center-content2">
					<div class="center-content25" v-for="(item,index) in villageDynamicRecordList">
						<div class="center-content251 center-content251-a">
							<div class="center-content2511" @click="openUser(item.add_user_id);">
								<img class="center-content2512" v-if="null != item.map.user_logo && item.map.user_logo.indexOf('http://') == -1" :src="ctx + item.map.user_logo">
								<img class="center-content2512" v-else-if="null != item.map.user_logo && item.map.user_logo.indexOf('http://') != -1" :src="item.map.user_logo">
								<img class="center-content2512" v-else src="${ctx}/styles/images/user.png" />
							</div>
							<div class="center-content2512">
								<p class="center-content25121" @click="openUser(item.add_user_id);">{{item.map.real_name}}</p>
								<p class="center-content25123">{{item.add_user_name}}
								<span v-if="item.record_type == 1">关注了</span>
								<span v-if="item.record_type == 3">评论了</span>
								<span v-if="item.record_type == 4">评论了</span>
								<span v-if="item.record_type == 5">赞了</span>
								<span v-if="item.record_type == 6">发布了动态</span>
								<span v-if="item.record_type == 7">发布了商品</span>
								{{item.to_user_name}}
								</p>
								<p>
								    <span>{{item.add_date | formatDate}}</span>
							    </p>
							</div>
						</div>
						
					</div>
					<div class="line_all"></div>
				</div>
				</div>
				</div>
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
	</div>
	<script type="text/javascript" src="${ctx}/styles/mui/mui.pullToRefresh.js"></script>
	<script type="text/javascript" src="${ctx}/styles/mui/mui.pullToRefresh.material.js"></script>
	<script type="text/javascript" src="${ctx}/m/scripts/lightGallery/js/lightgallery-all.min.js?20180530"></script> 
	<script type="text/javascript" src="${ctx}/m/scripts/dropload/js/dropload.min.js?v20160725"></script>
	
	<script type="text/javascript">

        var vm = new Vue({
            el: '#app',
            data: {
                ctx:Common.api,
                user: '',
                user_logo: '',
                follow_name: '关注',
                poorInfo: '',
                datas:"",
                villageDynamicRecordList:new Array(),
                villageDynamicList:new Array(),
                villageDynamicCommList:new Array(),
                commInfoPoorsList:new Array(),
                ui:"",
                orderInfoTodaySumBuy:0,
                orderInfoTodaySumOut:0,
                isApp:false
            },
            mounted: function() {
                this.$nextTick(function() {
                	Common.loading();
                    this.getAjaxData();
                    
                    mui('.mui-slider').slider().setStopped(true);
                    
                	mui('.cuncate').on('tap', 'a', function(){
                		var flag = this.getAttribute("flag");
                		Common.loading();
                		vm.villageDynamicRecordList = new Array();
                        vm.villageDynamicList = new Array();
                        vm.villageDynamicCommList = new Array();
                        vm.commInfoPoorsList = new Array();
                		if(flag == 1){
                            Common.getData({
                                route: '/m/MUserCenter.do?method=getAjaxData&user_id=${af.map.user_id}&type=1',
                                data: {
                                    startPage:0
                                },
                                success: function(data) {
                                	Common.hide();
                                    if(null != data.datas.villageDynamicList){
                                        vm.villageDynamicList = vm.villageDynamicList.concat(data.datas.villageDynamicList);
                                        $("#itemcontent1").attr("flag", 1);
                                    }
                                },
                                error: function() {
                                    mui.alert('好像出错了哦~')
                                }
                            });
            		     }
                		if(flag == 2){
                                Common.getData({
                                    route: '/m/MUserCenter.do?method=getAjaxData&user_id=${af.map.user_id}&type=2',
                                    data: {
                                        startPage:0
                                    },
                                    success: function(data) {
                                    	 Common.hide();
                                        if(null != data.datas.villageDynamicCommList){
                                            vm.villageDynamicCommList = vm.villageDynamicCommList.concat(data.datas.villageDynamicCommList);
                                            $("#itemcontent2").attr("flag", 1);
                                        }
                                    },
                                    error: function() {
                                        mui.alert('好像出错了哦~')
                                    }
                                });
                		}
                		if(flag == 3){
                			Common.getData({
                                route: '/m/MUserCenter.do?method=getAjaxData&user_id=${af.map.user_id}&type=3',
                                data: {
                                    startPage:0
                                },
                                success: function(data) {
                                	 Common.hide();
                                    if(null != data.datas.commInfoPoorsList){
                                        vm.commInfoPoorsList = vm.commInfoPoorsList.concat(data.datas.commInfoPoorsList);
                                        $("#itemcontent3").attr("flag", 1);
                                    }
                                },
                                error: function() {
                                    mui.alert('好像出错了哦~')
                                }
                            });
                		}
                		if(flag == 4){
                			 Common.getData({
                                 route: '/m/MUserCenter.do?method=getAjaxData&user_id=${af.map.user_id}&type=4',
                                 data: {
                                     startPage:0
                                 },
                                 success: function(data) {
                                	 Common.hide();
                                     if(null != data.datas.villageDynamicRecordList){
                                         vm.villageDynamicRecordList = vm.villageDynamicRecordList.concat(data.datas.villageDynamicRecordList);
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
                                 route: 'm/MUserCenter.do?method=getAjaxData&user_id=${af.map.user_id}&type=1',
                                 data: {
                                     startPage:flag
                                 },
                                 success: function(data) {
                                     if(null != data.datas.villageDynamicList){
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
                             	route: 'm/MUserCenter.do?method=getAjaxData&user_id=${af.map.user_id}&type=2',
                                 data: {
                                     startPage:flag
                                 },
                                 success: function(data) {
                                     if(null != data.datas.villageDynamicCommList){
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
 		    	  
 		    	  $('#itemcontent3').dropload({
 				        scrollArea : window,
 				        autoLoad : true,     
 				        loadDownFn : function(me){
 				        	var flag = $("#itemcontent3").attr('flag');
 				        	flag = Number(flag);
 				        	
 				        	Common.getData({
                             	route: 'm/MUserCenter.do?method=getAjaxData&user_id=${af.map.user_id}&type=3',
                                 data: {
                                     startPage:flag
                                 },
                                 success: function(data) {
                                     if(null != data.datas.commInfoPoorsList){
                                         vm.commInfoPoorsList = vm.commInfoPoorsList.concat(data.datas.commInfoPoorsList);
                                         setTimeout(function(){
  					                        me.resetload();
  					                     },500);
                                     }
                                     flag += 1;
  									 $("#itemcontent3").attr("flag", flag);
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
				        	var pageSize = 10;
				        	var flag = $("#itemcontent4").attr('flag');
				        	flag = Number(flag);
				        	
				        	Common.getData({
                            	route: '/m/MUserCenter.do?method=getAjaxData&user_id=${af.map.user_id}&type=4',
                                data: {
                                	pageSize:pageSize,
                                    startPage:flag
                                },
                                success: function(data) {
                                    if(null != data.datas.villageDynamicRecordList){
                                        vm.villageDynamicRecordList = vm.villageDynamicRecordList.concat(data.datas.villageDynamicRecordList);
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
            },
            methods: {
                getAjaxData: function() {
                    Common.getData({
                        route: '/m/MUserCenter.do?method=getAjaxData&user_id=${af.map.user_id}&type=1',
                        success: function(data) {
                        	Common.hide();
                            if (data.code == 0) {
                                mui.alert(data.msg);
                                return false;
                            }
                           	  vm.datas = data.datas;
                              vm.user = data.datas.user;
                              vm.isApp = data.datas.isApp;
                              vm.orderInfoTodaySumBuy = data.datas.orderInfoTodaySumBuy;
                              vm.orderInfoTodaySumOut = data.datas.orderInfoTodaySumOut;
                              if(null != data.datas.user.user_logo){
                               vm.user_logo = data.datas.user.user_logo;
                              }
                              if (null != data.datas.is_guanzhu_cout && data.datas.is_guanzhu_cout > 0) {
                                  vm.follow_name = "已关注";
                              }
                              if (null != data.datas.is_huguan && data.datas.is_huguan > 0) {
                                  vm.follow_name = "互相关注";
                              }
                              if(null != data.datas.poorInfo){
                              	vm.poorInfo = data.datas.poorInfo;
                              }
                              if(null != data.datas.ui){
							    vm.ui = data.datas.ui;
							  }
                        },
                        error: function() {
                            mui.alert('好像出错了哦~');
                        }
                    });
                },
                openUrl: function(url) {
                    var link_url = url;
                    location.href=link_url;
                },
                openUser:function(id){
                    var link_url = vm.ctx+"m/MUserCenter.do?method=index&user_id="+id;
                    location.href=link_url;
                },
                showPinDao: function() {
                    var html = $("#pinDaoContent").html();
                    setTimeout(function() {
                        Common.showLayUi(html,false,true);
                    }, 100);
                },
                btnSearch:function(){
                    var keyword = $("#search-content").val();
                    var url = vm.ctx + "m/MSearch.do?keyword="+keyword;
                    goUrl(url);
                },
                getTag:function(tag_id){
                    var url = vm.ctx + "m/MSearch.do";
                    goUrl(url);
                },
                applyAdd:function(){
                    Common.getData({
                        route: '/m/MVillage.do?method=applyAdd&id='+vm.villageInfo.id,
                        success: function(data) {
                            if(data.code == 0){
                                Common.confirm(data.msg,["取消","去登陆"],function(){
                                },function(){
                                    var url = vm.ctx + "m/MIndexLogin.do";
                                    goUrl(url);
                                });
                            }else{
                                mui.toast(data.msg);
                            }
                        },
                        error: function() {
                            mui.alert('好像出错了哦~');
                        }
                    });
                },
                follow:function(){
                    Common.getData({
                        route: 'm/MVillage.do?method=follow&id='+vm.user.id,
                        success: function(data) {
                            if(data.code == 1) {
                                mui.toast(data.msg);
                                vm.follow_name = "已关注";
                            }
                            if(data.code == -1) {
                                mui.toast(data.msg);
                                return false;
                            }
                            if(data.code == 0){
                                Common.confirm(data.msg,["取消","去登陆"],function(){
                                },function(){
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
                noFollow:function(){
                    Common.getData({
                        route: 'm/MVillage.do?method=noFollow&id='+vm.user.id,
                        success: function(data) {
                            if(data.code == 1) {
                                mui.toast("取消关注成功");
                                vm.follow_name = "关注";
                            }
                            if(data.code == -1) {
                                mui.toast(data.msg);
                                return false;
                            }
                            if(data.code == 0){
                                Common.confirm(data.msg,["取消","去登陆"],function(){
                                },function(){
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
                pub:function (type){
                    var url = vm.ctx + "m/MVillageDynamic.do?village_id="+vm.villageInfo.id+"&type="+type;
                    goUrl(url);
                },
                openNews:function(item){
                    var url = vm.ctx + "m/MNewsInfo.do";
                    goUrl(url);
                },
                comment: function() {
    				Common.loading();
    				var id = $("#comment_dyna_id").val();
    				var index = $("#comment_dyna_index").val();
    				var type = $("#comment_dyna_type").val();
    				var content = $("#comment" + id).val();
    				var flag = $("#comment_dyna_flag").val();
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
    							
    							if(flag == 1){
    								vm.villageDynamicList[index].map.commentList = vm.villageDynamicList[index].map.commentList.concat(data.insertComment);
    							}
    							if(flag == 2){
    								vm.villageDynamicCommList[index].map.commentList = vm.villageDynamicCommList[index].map.commentList.concat(data.insertComment);
    							}
    							
    							$("#comment" + id).val("");
    							$(".replaymsg").hide();
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
//     						village_id: vm.villageInfo.id,
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
    							
//     							var zan_count = vm.villageDynamicList[index].map.zan_count - 1;
//     							vm.DynamicList[index].map.zan_count = zan_count;
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
    				// 		  $("#leaving"+id).show();
    			},
                openMVillageMember:function(){
                    var url = vm.ctx + "m/MVillageMember.do?village_id="+vm.villageInfo.id;
                    goUrl(url);
                },
                guanzhu:function(type){
                	 var url = vm.ctx + "m/MVillageMember.do?method=guanzhu&user_id="+vm.user.id+"&type="+type;
                     goUrl(url);	
                },
                del:function(id,index,flag){
					Common.confirm("确认删除该条动态吗？", ["取消", "确认"], function() {}, function() {
						Common.getData({
							route: 'm/MVillageDynamic.do?method=del',
							data:{
								village_id:id
							},
							success: function(data) {
								if(data.code == "1") {
									mui.toast(data.msg);
									if(flag == 1){
										vm.villageDynamicList.splice(index,1)
									}else if(flag == 2){
										vm.villageDynamicCommList.splice(index,1)
									}
								} else {
									mui.toast(data.msg);
								}
							},
							error: function() {
								mui.alert('好像出错了哦~');
							}
						});
					});
				},
				addCart: function(item) {
					//判断是否支设置支付密码
					var url = vm.ctx + "m/MUserCenter.do?method=MUserCommInfo&id=" + item.id;
					goUrl(url);
				},
				closeComment: function() {
					$(".replaymsg").hide();
				},
            }
        });
	</script>
</body>
</html>