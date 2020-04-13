<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<meta name="format-detection" content="telephone=no">
		<meta http-equiv="Expires" content="-1">
		<meta http-equiv="Cache-Control" content="no-cache">
		<meta http-equiv="Pragma" content="no-cache">
		<jsp:include page="../_public_in_head_new.jsp" flush="true" />
		<link rel="stylesheet" type="text/css" href="${ctx}/m/styles/css/my/comm-details.css?v=20180320" />
		<link rel="stylesheet" type="text/css" href="${ctx}/m/scripts/lightGallery/css/lightgallery.css"/>
		<link rel="stylesheet" href="${ctx}/m/styles/village/css/style.css?v=20180528" />
		<style type="text/css">
		 .aui-slide-box .aui-slide-list{margin:10px;}
		 .aui-slide-box .aui-slide-item-list .aui-slide-item-item{margin-right:0.6rem!important;}
		 .aui-real-price{padding: 5px 10px 10px 10px;}
		 .current{border-bottom:none!important;}
		 .aui-header-center-clear{margin:0!important;}
		 .aui-footer-product-action-list{width:100%!important;}
		 .aui-fl-arrow:after{background-image: none!important;}
		 .aui-banner-wrapper{padding:0px 10px;padding-top: 15px;}
		 .aui-address-cell{border-bottom: 1px solid #e6e6e6;margin-bottom: 5px;}
		 .aui-product-shop-text{margin-top:5px;}
		 .aui-product-shop-img img{border-radius: 50%;}
		 .aui-product-shop-img{width:50px;}
		 .aui-header-center-logo div{width:100%!important;}
		 .gz_btn{padding: 0px 4px 0px 4px;border: 1px solid #333;border-radius: 3px;display: block;margin-top: 10px;text-align: center;width: 57px;}
/* 		 .center-content25{background-color: #ececec;border-radius: 5px;margin-top: 2px;padding: 0 10px;} */
/* 		.center-content2511 img {width: 7vw;height: 7vw;border-radius: 50%;overflow: hidden;} */
		</style>
	</head>
	<body id="body">
	    <div id="app" v-cloak>
		<header-item :header_title="item.comm_name" canback="true"></header-item>
		<div id="content1" class="aui-fixed-top">
		<div class="aui-product-evaluate">
			<a class="aui-address-cell aui-fl-arrow aui-fl-arrow-clear">
				<div class="aui-address-cell-bd">
					<div class="clearfix" >
						<div class="aui-product-shop-img" @tap="openUser(item.add_user_id);">
							<img v-if="null != user.user_logo && user.user_logo.indexOf('http://') == -1" :src="ctx + user.user_logo+'@s100x100'"/>
							<img v-else-if="null != user.user_logo && user.user_logo.indexOf('http://') != -1" :src="user.user_logo"/>
							<img v-else src="${ctx}/styles/images/user.png" />
						</div>
						<div class="aui-product-shop-text" style="width: auto;" @tap="openUser(item.add_user_id);">
							<h4>{{item.add_user_name}}</h4>
							<span style="display:block;margin-top: 10px;">{{item.add_date | formatDate}}</span>
						</div>
						<div class="aui-product-shop-text" style="width: auto;position: absolute;right: 10%;">
							<span v-if="follow_name == '关注'" @tap="follow" class="gz_btn">{{follow_name}}</span>
							<span v-else @tap="noFollow();" class="gz_btn">{{follow_name}}</span>
						</div>
					</div>
					<div class="aui-real-price clearfix">
					 <span v-if="null != item.map && null != item.map.commInfoTczh">
						 ￥{{item.map.commInfoTczh.comm_price | formatMoney}}
					 </span>
					</div>
				</div>
			</a>
		</div>
		<div class="aui-banner-content lightgallery">
			<a :href="ctx + item.file_path+'@compress'" v-for="item in commImgsList">
			<div class="aui-banner-wrapper" :style="img_style">
				<div class="aui-banner-wrapper-item">
					<img :src="ctx + item.file_path + '@compress'" />
				</div>
			</div>
			</a>
		</div>
		<div class="aui-product-content">
			<div class="aui-product-title" style="padding: 1px 15px 0px 15px;">
				<h2>
				{{item.comm_name}}</h2>
			</div>
			<div class="aui-dri"></div>
			<div class="aui-slide-box">
				<div class="aui-slide-list swiper-container-comm">
					<ul class="aui-slide-item-list swiper-wrapper">
						<li class="aui-slide-item-item swiper-slide" v-for="item2 in villageDynamicTjList">
							<a :href="ctx + 'm/MUserCenter.do?method=MUserCommInfo&id=' + item2.id" class="v-link">
								<img class="v-img" v-if="index == 0" :src="ctx + item3.file_path + '@s200x200'" v-for="(item3,index) in item2.map.imgList"/>
								<p class="aui-slide-item-title aui-slide-item-f-els" style="height: auto;">{{item2.comm_name}}</p>
								<p class="aui-slide-item-info">
									<span class="aui-slide-item-price" v-if="null != item2.map && null != item2.map.commInfoTczh">¥{{item2.map.commInfoTczh.comm_price | formatMoney}}</span>
								</p>
							</a>
						</li>
					</ul>
				</div>
			</div>
			<div class="center-content">
			<p style="clear: both;">
				<span class="ping-lun"><span style="color: pink;">+{{item.map.commentList.length}}</span></span>
				<span class="dian-zan1"><span style="color: pink;">+{{item.map.zanList.length}}</span></span>
			</p>
			<div class="line" v-show="null !=item.map.zanList && item.map.zanList.length > 0"></div>
			<div class="center-content24" v-show="null !=item.map.zanList && item.map.zanList.length > 0">
				<p class="center-content241">
					<img src="${ctx}/m/styles/village/img/redxin.png" style="width: 18px;">
					<span><span>{{item.map.zanNameList}}</span></span>
				</p>
			</div>
			<div class="line" v-show="null != item.map.commentList && item.map.commentList.length > 0"></div>
			<div class="center-content25">
				<div class="center-content251 center-content251-a" v-for="itemComment in item.map.commentList">
					<div class="center-content2511" @click="openUser(itemComment.add_user_id);">
						<img class="center-content2512" v-if="null != itemComment.map.user_logo && itemComment.map.user_logo.indexOf('http://') == -1" :src="ctx + itemComment.map.user_logo">
						<img class="center-content2512" v-else-if="null != itemComment.map.user_logo && itemComment.map.user_logo.indexOf('http://') != -1" :src="itemComment.map.user_logo">
						<img class="center-content2512" v-else src="${ctx}/styles/images/user.png" />
					</div>
					<div class="center-content2512">
						<p class="center-content25121" @click="openUser(itemComment.add_user_id);">{{itemComment.map.real_name}}</p>
						<p class="center-content25123">@{{itemComment.replyc_target_user_name}}:{{itemComment.content}}</p>
						<p>
							<span>{{itemComment.add_date | formatDate}}</span>
							<span class="huifu" :id="'liuyan_comment'+itemComment.id" @click="goLiuYan(itemComment.id,2)"><span></span></span>
						</p>
					</div>
				</div>
			</div>
			<div class="replaymsg">
				<div class="dialogdiv-hide" @click="closeComment();"></div>
				<div class="dialogdiv">
					<textarea placeholder="这里填入回复的内容"></textarea>
					<div @click="comment();"  class="bt">发送</div>
					<input type="hidden" id="comment_dyna_id">
					<input type="hidden" id="comment_dyna_type">
				</div>
			</div>
			</div>
		 </div>
		</div>
		<footer class="aui-footer-product" id="s-actionBar-container">
			<div class="aui-footer-product-fixed">
				<div class="aui-footer-product-action-list">
					<a class="red-color" @tap="goDianZan(item)" style="font-size: 10px;background: #fff;color: black;width: 20%;">
						<img  :src="vm.zan_img" style="width: 25px;vertical-align: middle;">点赞
					</a>
					<a class="red-color" @tap="goLiuYan(item.id,1)" style="font-size: 10px;background: #fff;color: black;width: 20%;">
						<img src="${ctx}/m/styles/village/img/pinlun_detail.png" style="width: 25px;vertical-align: middle;">评论
					</a>
					<a class="red-color" @tap="addCart(item);" style="font-size: 10px;background: #fff;color: black;width: 20%;">
						<img src="${ctx}/m/styles/village/img/shoucang_detail.png" style="width: 25px;vertical-align: middle;">收藏
					</a>
					<a class="red-color" @tap="addCart(item);" style="width: 40%">
						<img alt="" src="">立即购买
					</a>
				</div>
			</div>
		</footer>
	</div>
<script type="text/javascript" src="${ctx}/scripts/swiper/swiper.min.js"></script>
<script type="text/javascript" src="${ctx}/m/scripts/lightGallery/js/lightgallery-all.min.js?20180530"></script> 
<script src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
<script type="text/javascript">
  var vm = new Vue({
    el: '#app',
    data: {
      ctx:Common.api,
      datas:"",
      commImgsList:"",
      follow_name: '关注',
      item:"",
      user:"",
      villageDynamicTjList:"",
      img_style:'',
      zan_img:"${ctx}/m/styles/village/img/zan_detail.png",
    },
    mounted: function() {
      Common.loading();		
      this.$nextTick(function() {
     	  var inData = { 
     			  	appId: '${appid}',
         	      	timestamp: '${timestamp}',
         	      	nonceStr: '${nonceStr}',
         	      	signature: '${signature}'
          };
      	  var shareData = { 
      		  		title : '${entity.comm_name}',
    			 	desc : '${entity.content}',
    				link : '${share_url}',
    				imgUrl : '${share_img}',
          };
      	  Common.weixinConfig(inData,shareData);	  
      	  this.getAjaxData();
      });
    },
    updated: function() {
    	$('.lightgallery').lightGallery({download:false});
		mui('.lightgallery').on('tap', 'a', function(){
	   		$(this).click();
   	    });
    	Common.hide();
    	
     	new Swiper('.swiper-container-comm', {
			slidesPerView: 'auto',
			paginationClickable: true,
			spaceBetween: 20
		});
     	
    },
    methods: {
      getAjaxData: function() {
   		Common.getData({
   			route: '/m/MUserCenter.do?method=getCommInfoData&id=${af.map.id}',
   			success: function(data) {
   				if(data.code == 0) {
   				  mui.toast(data.msg);
   	              return false;
   	            } else if(data.code == 1) {
   	            	vm.datas = data.datas;
   	            	vm.commImgsList = data.datas.entity.map.imgList;
   	            	vm.item = data.datas.entity;
   	            	if(data.datas.entity.map.zan_count > 0){
   	            		vm.zan_img="${ctx}/m/styles/village/img/yizan_detail.png";
   	            	}
   	            	vm.user = data.datas.user;
   	            	vm.villageDynamicTjList = data.datas.entity.map.villageDynamicTjList;
   	            	if (null != data.datas.is_guanzhu_cout && data.datas.is_guanzhu_cout > 0) {
                        vm.follow_name = "已关注";
                    }
   	            	
                    if (null != data.datas.is_huguan && data.datas.is_huguan > 0) {
                        vm.follow_name = "互相关注";
                    }
   	            }
   			},
   			error: function() {
   				mui.alert('好像出错了哦~');
   			}
   		});
      },
      openUser:function(id){
          var link_url = vm.ctx+"m/MUserCenter.do?method=index&user_id="+id;
          location.href=link_url;
      },
      addCart: function(item) {
			//判断是否支设置支付密码
			Common.getData({
				route: 'm/MVillage.do?method=isSetUpPasswordPay',
				success: function(data) {
					if(data.code == "1") {
						var url = vm.ctx + "m/MVillageNoCartInfo.do?comm_id=" + item.map.commInfoTczh.comm_id + "&comm_tczh_id=" + item.map.commInfoTczh.id + "&village_id=" + item.village_id;
						goUrl(url);
					}
					else if(data.code == "-1") {
						Common.confirm(data.msg, ["取消", "去设置"], function() {}, function() {
							var url = vm.ctx + "m/MMySecurityCenter.do?method=setPasswordPay";
							goUrl(url);
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
         goLiuYan:function(id,type){
             $(".replaymsg").show();
             $(".replaymsg textarea").focus();
             $(".replaymsg textarea").attr("name","comment"+id);
             $(".replaymsg textarea").attr("id","comment"+id);
             $("#comment_dyna_id").val(id);
             $("#comment_dyna_type").val(type);
         },
         closeComment: function() {
				$(".replaymsg").hide();
		 },
         goDianZan:function(item){
             Common.getData({
                 route: 'm/MVillageDynamic.do?method=saveComment&type=3&id='+item.id,
                 success: function(data) {
                     if(data.code == 0){
                     	login();
                     }
                     if(data.code == 1){//赞
                         var names = vm.item.map.zanNameList;
                         if(names.length>0){
                             names += "、"+vm.datas.ui.real_name;
                         }else{
                             names += +vm.datas.ui.real_name;
                         }
                         vm.item.map.zanNameList = names;
                         vm.zan_img="${ctx}/m/styles/village/img/yizan_detail.png"
                     }
                     if(data.code == -2){//取消赞
                    	 var names = vm.item.map.zanNameList;
                    	 var last_index = names.lastIndexOf("、");
                         names = names.substring(0,last_index);
                         vm.item.map.zanNameList = names;
                         vm.zan_img="${ctx}/m/styles/village/img/zan_detail.png"
                     }
                     if(data.code == -1){
                         mui.toast(data.msg);
                     }
                 },
                 error: function() {
                     mui.alert('好像出错了哦~');
                 }
             });
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
			 comment:function(){
                 Common.loading();
                 var id =  $("#comment_dyna_id").val();
                 var type =  $("#comment_dyna_type").val();
                 var content = $("#comment"+id).val();
                 Common.getData({
                     route: 'm/MVillageDynamic.do?method=saveComment&type='+type+'&content='+content+'&id='+id,
                     success: function(data) {
                         Common.hide();
                         mui.toast(data.msg);
                         if(data.code == 0){
                         	login();
                         	$(".replaymsg").hide();
                         }
                         if(data.code == 1){
                             $(".replaymsg").hide();
                         }
                     },
                     error: function() {
                         mui.alert('好像出错了哦~');
                     }
                 });
             },
    }
  });
</script>	
</body>
</html>