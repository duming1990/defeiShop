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
<link rel="stylesheet" href="${ctx}/m/styles/village/css/style.css?20180312" />
<link rel="stylesheet" href="${ctx}/m/styles/village/css/content.css" />
<link rel="stylesheet" href="${ctx}/styles/mui/poppicker/mui.picker.min.css" />
<link rel="stylesheet" href="${ctx}/styles/mui/poppicker/mui.poppicker.css" />
</head>
<body>
<body style="background-color: #fff;">
	<div id="app" v-cloak>
		<div style="background-color: #ebebeb;">
			<div class="mui-input-row mui-search village-list-search-div">
				<input type="search" id="village_name_like" name="village_name_like" class="search-input" style="" placeholder="搜索" />
				<input type="submit" class="searchbtn" @click="search();" name="dosearch" style="margin: 8px 4px;" value="搜索">
			</div>
		</div>
		<ul v-if="datas.villageInfoList!=''" class="mui-table-view" id="OA_task_1" style="margin-bottom: 44px;">
			<li class="mui-table-view-cell" v-for="(item,index) in datas.villageInfoList" style="cursor: pointer;">
<!-- 				<div class="mui-slider-right mui-disabled"> -->
<!-- 					<a class="mui-btn mui-btn-grey" v-if="item.map.is_apply == 0">申请中</a> -->
<!-- 					<a class="mui-btn mui-btn-red"  v-else-if="item.map.is_apply == 1"  @click="quit(item,index);">退出</a> -->
<!-- 					<a class="mui-btn mui-btn-green" v-else @click="applyAdd(item.id,index);">加入</a> -->
<!-- 				</div> -->
				<div class="mui-slider-handle" @click="villageIndex(item)">
					<img class="mui-media-object mui-pull-left" :src="ctx + item.village_logo" onerror="this.src='${ctx}/styles/imagesPublic/user_header.png'">
					<div class="mui-media-body">{{item.village_name}}</div>
				</div>
			</li>
		</ul>
		<ul v-else class="mui-table-view" id="OA_task_1" style="margin-bottom: 44px;">
			<li class="mui-table-view-cell" style="text-align: center;">
				暂无行政村~~
			</li>
		</ul>
	</div>
	<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script>
	<script type="text/javascript" src="${ctx}/styles/mui/mui.min.js"></script>
	<script type="text/javascript" src="${ctx}/styles/mui/common.js"></script>
	<script type="text/javascript" src="${ctx}/scripts/vue/vue.min.js"></script>
	<script type="text/javascript" src="${ctx}/styles/mui/poppicker/mui.picker.min.js"></script>
	<script type="text/javascript" src="${ctx}/styles/mui/poppicker/mui.poppicker.js"></script>
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
      });
    },
    updated: function() {
    },
    methods: {
      getAjaxData: function() {
    	 var p_index=localStorage.getItem('p_index_service_info');
   		Common.getData({
   			route: '/m/MVillage.do?method=getAjaxDataList',
   			data:{
   				p_index:p_index,
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
      villageIndex: function(item) {
    	  localStorage.setItem('p_index_village_info',item.p_index);
    	  localStorage.setItem('p_name_village_info',item.village_name);
    	  var link_url = vm.ctx +"/VillageIndex.do?method=villageIndex&village_id="+item.id;
		  window.parent.location.href=link_url;
	  },
	  search:function(){
		  var village_name_like = $("#village_name_like").val();
		  Common.getData({
	   			route: '/m/MVillage.do?method=getAjaxDataList&village_name_like='+village_name_like,
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