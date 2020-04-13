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
<link rel="stylesheet" href="${ctx}/m/styles/village/css/style.css?20180312" />
<link rel="stylesheet" href="${ctx}/m/styles/village/css/content.css" />
<link rel="stylesheet" href="${ctx}/styles/mui/poppicker/mui.picker.min.css" />
<link rel="stylesheet" href="${ctx}/styles/mui/poppicker/mui.poppicker.css" />
<link rel="stylesheet" href="${ctx}/m/scripts/dropload/css/dropload.css" />
</head>
<body>
<body style="background-color: #fff;">
	<div id="app" v-cloak>
	    <header-item header_title="选择街道" canback="true"></header-item>
		<div class="mui-content">
		<div style="background-color: #ebebeb;">
			<div class="mui-input-row mui-search village-list-search-div">
				<input type="search" id="village_name_like" name="village_name_like" class="search-input" style="" placeholder="搜索" />
				<input type="submit" class="searchbtn" @click="search();" name="dosearch" style="margin: 8px 4px;" value="搜索">
			</div>
		</div>
		<ul class="mui-table-view" style="margin-bottom: 44px;" id="itemcontent" flag="1">
		  <div>
			<li class="mui-table-view-cell" v-for="(item,index) in datas.villageInfoList">
<!-- 				<div class="mui-slider-right mui-disabled"> -->
<!-- 					<a class="mui-btn mui-btn-grey" v-if="item.map.is_apply == 0">申请中</a> -->
<!-- 					<a class="mui-btn mui-btn-red"  v-else-if="item.map.is_apply == 1"  @click="quit(item,index);">退出</a> -->
<!-- 					<a class="mui-btn mui-btn-green" v-else @click="applyAdd(item.id,index);">加入</a> -->
<!-- 				</div> -->
				<div class="mui-slider-handle" @click="openVillage(item,index)">
					<img class="mui-media-object mui-pull-left" :src="ctx + item.village_logo" onerror="this.src='${ctx}/styles/imagesPublic/user_header.png'">
					<div class="mui-media-body">{{item.village_name}}</div>
				</div>
			</li>
		  </div>	
		</ul>
		</div>
	</div>
	<script type="text/javascript" src="${ctx}/styles/mui/poppicker/mui.picker.min.js"></script>
	<script type="text/javascript" src="${ctx}/styles/mui/poppicker/mui.poppicker.js"></script>
	<script type="text/javascript" src="${ctx}/m/scripts/dropload/js/dropload.min.js?v20160725"></script>
	<script type="text/javascript">
  var vm = new Vue({
    el: '#app',
    data: {
      totalMoney: 0,
      ctx:Common.api,
      datas:"",
    },
    mounted: function() {
      this.$nextTick(function() {
    	  this.getAjaxData();
    	  
    	  $('#itemcontent').dropload({
		        scrollArea : window,
		        autoLoad : false,     
		        loadDownFn : function(me){
		        	var flag = $("#itemcontent").attr('flag');
		        	flag = Number(flag);
					Common.getData({
						route: 'm/MVillage.do?method=getAjaxDataList',
						data: {
							startPage: flag,
							p_index:"${af.map.p_index}",
			   				village_name_like:"${af.map.village_name_like}",
						},
						success: function(data) {
							if(null != data.datas.villageInfoList) {
								vm.datas.villageInfoList = vm.datas.villageInfoList.concat(data.datas.villageInfoList);
								setTimeout(function(){
			                        me.resetload();
			                    },500); 
							}
							flag += 1;
							$("#itemcontent").attr("flag", flag);
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
    },
    methods: {
      getAjaxData: function() {
   		Common.getData({
   			route: '/m/MVillage.do?method=getAjaxDataList',
   			data:{
   				p_index:"${af.map.p_index}",
   				village_name_like:"${af.map.village_name_like}",
   			},
   			success: function(data) {
   				if(data.code == 0) {
   				  mui.alert(data.msg);
   	              return false;
   	            } else if(data.code == 1) {
   	            	vm.datas = data.datas;
   	            }
   			},
   			error: function() {
   				mui.alert('好像出错了哦~');
   			}
   		});
      },
      openUrl: function(item) {
    	  var link_url = vm.ctx +"m/MVillage.do?method=index&id="+item.id;
			location.href=link_url;
	  },
	  openVillage: function(item,index) {
		  Common.getData({
	   			route: '/m/MVillage.do?method=isApplyVillage&id='+item.id,
	   			success: function(data) {
	   				if(data.code == -3) {
	   					mui.toast(data.msg);
	   					return false;
	   	            }
	   				if(data.code == -2) {
	   					Common.confirm(data.msg,["取消","加入"],function(){
		   					},function(){
		   						vm.applyAdd(item.id,index);
		   					});
	   					return false;
	   	            }
	   				
	   				if(data.code == -1) {
	   					Common.confirm(data.msg,["取消","去登陆"],function(){
	   					},function(){
	   						var url = vm.ctx + "m/MIndexLogin.do";
	   					  	goUrl(url);
	   					});
		   	        } 
	   				var link_url = vm.ctx +"m/MVillage.do?method=index&id="+item.id;
	   				location.href=link_url;
	   			},
	   			error: function() {
	   				mui.alert('好像出错了哦~');
	   			}
	   		});
    	  
	  },
	  search:function(){
		  var village_name_like = $("#village_name_like").val();
		  Common.getData({
	   			route: '/m/MVillage.do?method=getAjaxDataList&p_index=${af.map.p_index}&village_name_like='+village_name_like,
	   			success: function(data) {
	   				if(data.code == 0) {
	   				  mui.alert(data.msg);
	   	              return false;
	   	            } else if(data.code == 1) {
	   	            	vm.datas = data.datas;
	   	            	vm.datas.villageInfoList = data.datas.villageInfoList;
	   	            }
	   			},
	   			error: function() {
	   				mui.alert('好像出错了哦~');
	   			}
	   		});
	  },
	  applyAdd:function(id,index){
		  Common.getData({
	   			route: '/m/MVillage.do?method=applyAdd&id='+id,
	   			success: function(data) {
	   				if(data.code == -1){
	   					Common.confirm(data.msg,["取消","去登陆"],function(){
	   					},function(){
	   						var url = vm.ctx + "m/MIndexLogin.do";
	   					  	goUrl(url);
	   					});
	   				}else{
	   					mui.toast(data.msg);
	   					Vue.set(vm.datas.villageInfoList[index].map, 'is_apply', 0);
	   				}
	   			},
	   			error: function() {
	   				mui.alert('好像出错了哦~');
	   			}
	   		});
	  },
	  quit:function(item,index){
		  Common.getData({
	   			route: '/m/MVillage.do?method=quit&id='+item.id,
	   			success: function(data) {
	   				if(data.code == -1){
	   					Common.confirm(data.msg,["取消","去登陆"],function(){
	   					},function(){
	   						var url = vm.ctx + "m/MIndexLogin.do";
	   					  	goUrl(url);
	   					});
	   				}else{
	   					Vue.set(vm.datas.villageInfoList[index].map, 'is_apply', -1);
	   				}
	   					mui.toast(data.msg);
	   				
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