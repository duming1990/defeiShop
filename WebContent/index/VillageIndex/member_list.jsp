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
.mui-table-view .mui-media-body{line-height:21px;}
</style>
</head>
<body style="font-size:17px!important;">
<div id="app" v-cloak>
	<div style="background-color:#ebebeb;">
		<div class="mui-input-row mui-search" style="height:50px;line-height:50px;width:95%;margin:0 auto;">
			<input type="search" v-model="search" class="mui-input-clear" style="text-align:left;margin-bottom:0;background-color:#fff;width:100%;" placeholder="搜索" />
		</div>
	</div>
	<input type="hidden" v-model="startPage"/>
	<div id="pullrefresh" class="mui-content mui-scroll-wrapper" style="top:100px;">
	<div class="mui-scroll">
    <ul class="mui-table-view mui-table-view-chevron">
		<li class="mui-media" v-for="item in datas.entityList" style="cursor: pointer;">
			<a class="mui-navigate-right" :attr-id="item.user_id" @click="myIndex(item)">
				<img class="mui-media-object mui-pull-left" v-if="null != item.map.user_logo" :src="ctx + item.map.user_logo + '@s60x60'">
				<img class="mui-media-object mui-pull-left" v-else src="${ctx}/styles/images/user.png">
				<div class="mui-media-body">
					{{item.user_name}}
					<p class="mui-ellipsis">{{item.mobile}}</p>
				</div>
			</a>
		</li>
	</ul>
   </div>
  </div>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script>
<script type="text/javascript" src="${ctx}/styles/mui/mui.min.js"></script>
<script type="text/javascript" src="${ctx}/styles/mui/common.js"></script>
<script type="text/javascript" src="${ctx}/scripts/vue/vue.min.js"></script>
<script type="text/javascript">
  var vm = new Vue({
    el: '#app',
    data: {
      datas:"",
      ctx:Common.api,
      startPage:0,
      search:""
    },
    mounted: function() {
      this.$nextTick(function() {
    	  this.getAjaxData();
      });
    },
    updated: function() {
    	
    	mui.init({
			pullRefresh: {
				container: '#pullrefresh',
				down: {
				},
				up: {
					contentrefresh: '正在加载...',
					callback: pullupRefresh
				}
			}
		});
    	
    	mui('.mui-table-view').on('tap', 'a', function(){
	   		 var link_url = vm.ctx+"m/MUserCenter.do?method=index&user_id=" + $(this).attr("attr-id");
	  		 goUrl(link_url);
    	});
    	
		var count = 0;
		function pullupRefresh() {
			setTimeout(function() {
				Common.getData({
		   			route: '/m/MVillageMember.do?method=getAjaxData&village_id=${af.map.village_id}',
		   			data: {
						startPage:vm.startPage
					},		
		   			success: function(data) {
		   				if(data.code == 0) {
		   				   mui.toast(data.msg);
		   	               return false;
		   	            } else if(data.code == 1) {
		   	            	vm.datas.entityList = vm.datas.entityList.concat(data.datas.entityList);
		   	            	vm.startPage = vm.startPage + 1;
		   	            	mui('#pullrefresh').pullRefresh().endPullupToRefresh((data.datas.entityList.length < 10));
		   	            }
		   			},
		   			error: function() {
		   				mui.toast('好像出错了哦~');
		   			}
		   		});
			}, 1500);
		}
    },
    watch: {
    	search: function(val, oldval) {
			if(val != "") {
				document.onkeydown = function(event) {
					var e = event || window.event || arguments.callee.caller.arguments[0];
					if(e && e.keyCode == 13) {//enter 键
						Common.getData({
				   			route: '/m/MVillageMember.do?method=getAjaxData&village_id=${af.map.village_id}&user_name_like=' + val,
				   			success: function(data) {
				   				if(data.code == 0) {
				   				   mui.toast(data.msg);
				   	            } else if(data.code == 1) {
				   	            	vm.datas = data.datas;
				   	            }
				   			},
				   			error: function() {
				   				mui.toast('好像出错了哦~');
				   			}
				   		});
					}
				};
			}
		},
	},
    methods: {
      getAjaxData: function() {
   		Common.getData({
   			route: '/m/MVillageMember.do?method=getAjaxData&village_id=${af.map.village_id}',
   			data: {
				startPage:vm.startPage
			},		
   			success: function(data) {
   				if(data.code == 0) {
   				   mui.toast(data.msg);
   	               return false;
   	            } else if(data.code == 1) {
   	            	vm.datas = data.datas;
   	            	vm.startPage = vm.startPage + 1;
   	            }
   			},
   			error: function() {
   				mui.toast('好像出错了哦~');
   			}
   		});
      },
      myIndex: function(item) {
    	  var village_id="${af.map.village_id}";
    	  var link_url = vm.ctx +"/VillageIndex.do?method=myIndex&member_id="+item.user_id+"&village_id="+village_id;
		  window.parent.location.href=link_url;
	  },
      
      
    }
  });
</script>
</body>
</html>