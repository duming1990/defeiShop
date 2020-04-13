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
	    <header-item header_title="选择合伙人" canback="true"></header-item>
	    <div class="mui-content">
		<div style="background-color:#ebebeb;">
			<div class="mui-input-row mui-search c-hd" style="line-height:50px;margin:0 auto;">
			    <c:url var="url" value="/m/MChangeCity.do" />
				<section class="city"><a onclick="goUrl('${url}')" id="city_name">${fn:substring(quanguo_p_name, 0, 3)}</a></section>
				<input type="search" id="servicecenter_name_like" name="servicecenter_name_like" class="search-input" style="margin: 0 .2rem;" placeholder="搜索" />
				<input type="submit" class="searchbtn" @click="search();" name="dosearch" style="margin:3.3% 1%;" value="搜索">
			</div>
		</div>
		<ul class="mui-table-view" id="OA_task_1" style="margin-bottom: 44px;">
			<li class="mui-table-view-cell" v-for="(item,index) in datas.entityList">
				<div class="mui-slider-handle" @click="openUrl(item,index)">
					<img class="mui-media-object mui-pull-left" :src="ctx + item.logo" onerror="this.src='${ctx}/styles/imagesPublic/user_header.png'">
					<div class="mui-media-body">{{item.servicecenter_name}}</div>
				</div>
			</li>
		</ul>
		</div>
	</div>
	<script type="text/javascript" src="https://api.map.baidu.com/api?v=2.0&ak=${map_baidu_key}"></script>
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
   		Common.getData({
   			route: 'm/MServiceCenterInfo.do?method=getAjaxDataList',
			data:{
				servicecenter_name_like:"${af.map.servicecenter_name_like}",
				p_index_like:"${af.map.p_index_like}",
			},
   			success: function(data) {
   				if(data.code == 0) {
   				  mui.alert(data.msg);
   	              return false;
   	            } else if(data.code == 1) {
   	            	vm.datas = data.datas;
   	            	var p_index = localStorage.getItem('p_index_service_info');
	   	           	var p_name = localStorage.getItem('p_name_service_info');
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
  	           		  Common.getData({
  	       		   			route: 'm/MServiceCenterInfo.do?method=getAjaxDataList',
  	       		   			data:{
  	       		   				p_index_like:p_index,
  	       		   				servicecenter_name_like:servicecenter_name_like,
  	       		   			},
  	       		   			success: function(data) {
  	       		   				if(data.code == 1){
  	       		   					Vue.set(vm.datas, 'entityList', data.datas.entityList);
  	       		   				}
  	       		   			},
  	       		   			error: function() {
  	       		   				mui.alert('好像出错了哦~');
  	       		   			}
  	       		   	});
  	           	}
   	            }
   			},
   			error: function() {
   				mui.alert('好像出错了哦~');
   			}
   		});
      },
      openUrl: function(item) {
    	  var link_url = vm.ctx +"m/MServiceCenterInfo.do?method=index&id="+item.id;
			location.href=link_url;
	  },
	  search:function(){
		  var servicecenter_name_like = $("#servicecenter_name_like").val();
		  var p_index = localStorage.getItem('p_index_service_info');
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