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
<link href="${ctx}/styles/mui/css/mui.min.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx}/m/styles/village/css/style.css" />
<link rel="stylesheet" href="${ctx}/m/styles/village/css/content.css" />
<style type="text/css">
.mui-search .mui-placeholder{left:65px;line-height:60px;}
.mui-search.mui-active:before{left:65px;}
.search-input{width:67%!important;}
</style>
</head>
<body>
<body style="background-color: #fff;">
	<div id="app" v-cloak>
		<div style="background-color: #ebebeb;">
			<div class="mui-input-row mui-search c-hd" style="line-height:50px;margin:0 auto;">
				<section class="city" style="padding: 0.14rem 3.6rem 0 .1rem;"></section>
				<input type="search" id="servicecenter_name_like" name="servicecenter_name_like" class="search-input" style="margin: 0 .2rem;" placeholder="搜索" />
				<input type="submit" class="searchbtn" @click="search();" name="dosearch" style="margin:3.3% 1%;" value="搜索">
			</div>
		</div>
		<ul class="mui-table-view" id="OA_task_1" style="margin-bottom: 44px;">
			<li class="mui-table-view-cell" v-for="(service,index) in datas.entityList">
<!-- 				<div class="mui-slider-right mui-disabled"> -->
<!-- 					<a class="mui-btn mui-btn-grey" v-if="item.map.is_apply == 0">申请中</a> -->
<!-- 					<a class="mui-btn mui-btn-red"  v-else-if="item.map.is_apply == 1"  @click="quit(item,index);">退出</a> -->
<!-- 					<a class="mui-btn mui-btn-green" v-else @click="applyAdd(item.id,index);">加入</a> -->
<!-- 				</div> -->
				<div class="mui-slider-handle" @click="serviceIndex(service)">
					<img class="mui-media-object mui-pull-left" :src="ctx + service.logo" onerror="this.src='${ctx}/styles/imagesPublic/user_header.png'">
					<div class="mui-media-body">{{service.servicecenter_name}}</div>
				</div>
			</li>
		</ul>
	</div>
	<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script>
	<script type="text/javascript" src="${ctx}/styles/mui/mui.min.js"></script>
	<script type="text/javascript" src="${ctx}/styles/mui/common.js"></script>
	<script type="text/javascript" src="${ctx}/scripts/vue/vue.min.js"></script>
	<script type="text/javascript">
  var vm = new Vue({
    el: '#app',
    data: {
      ctx:Common.api,
      datas:"",
    },
    mounted: function() {
      this.$nextTick(function() {
    	  this.getAjaxData();
      });
    },
    updated: function() {
    	
    },
    methods: {
      getAjaxData: function() {
    	var p_index = localStorage.getItem('p_index_city_info');
     	var p_name = localStorage.getItem('p_name_city_info');
   		Common.getData({
   			route: 'm/MServiceCenterInfo.do?method=getAjaxDataList',
			data:{
				servicecenter_name_like:"${af.map.servicecenter_name_like}",
				p_index_like:p_index,
			},
   			success: function(data) {
   				if(data.code == 0) {
   				  mui.alert(data.msg);
   	              return false;
   	            } else if(data.code == 1) {
   	            	vm.datas = data.datas;
	   	           	if(null == p_index || p_index == '' || null == p_name || p_name == '') {
	   	           	   getLocationServiceInfo();
	   	           	}else{
  	           		  $("#city_name").text(p_name.substring(0,3));
  	           		  var servicecenter_name_like = "${af.map.servicecenter_name_like}";
  	           		  if(null != $("#servicecenter_name_like").val() && $("#servicecenter_name_like").val() != ''){
  	           			servicecenter_name_like = $("#servicecenter_name_like").val();
  	           		  }
  	           		  var p_index_like = "${af.map.p_index_like}";
  	           		  if(null != p_index && p_index != ""){
  	           			p_index = p_index_like;
  	           		  }
  	           	}
   	            }
   			},
   			error: function() {
   				mui.alert('好像出错了哦~');
   			}
   		});
      },
      serviceIndex: function(item) {
    	  localStorage.setItem('p_index_service_info',item.p_index);
    	  localStorage.setItem('p_name_service_info',item.servicecenter_name);
    	  var link_url = vm.ctx +"/VillageIndex.do?method=serviceIndex&p_index="+item.p_index;
		  window.parent.location.href=link_url;
	  },
	  search:function(){
		  var servicecenter_name_like = $("#servicecenter_name_like").val();
		  var p_index = localStorage.getItem('p_index_city_info');
		  Common.getData({
	   			route: '/m/MServiceCenterInfo.do?method=getAjaxDataList',
	   			data:{
	   				p_index_like:p_index,
	   				servicecenter_name_like:servicecenter_name_like,
 		   		},
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
    }
  });
</script>
	</body>
</html>