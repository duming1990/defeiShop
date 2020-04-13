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
<jsp:include page="../_public_in_head.jsp" flush="true" />
<link rel="stylesheet" href="${ctx}/m/styles/village/css/style.css" />
<link rel="stylesheet" href="${ctx}/m/styles/village/css/content.css" />
<style type="text/css">
.mui-table-view .mui-media-body{line-height:21px;}
</style>
</head>
<body style="font-size:17px!important;">
<jsp:include page="../_header.jsp" flush="true" />
<div id="app" v-cloak>
<!-- 	<div style="background-color:#ebebeb;"> -->
<!-- 		<div class="mui-input-row mui-search" style="height:50px;line-height:50px;width:95%;margin:0 auto;"> -->
<!-- 			<input type="search" v-model="search" class="mui-input-clear" style="text-align:left;margin-bottom:0;background-color:#fff;width:100%;" placeholder="搜索" /> -->
<!-- 		</div> -->
<!-- 	</div> -->
	<input type="hidden" v-model="startPage"/>
	<div id="pullrefresh" class="mui-content mui-scroll-wrapper" style="top:45px;">
	<div class="mui-scroll mui-content">
		<ul id="OA_task_2" class="mui-table-view">
				<li class="mui-table-view-cell" v-for="(item,index) in datas.entityList">
					<div class="mui-slider-left mui-disabled" v-if="userInfo != '' && ui.id == userInfo.id">
						<a class="mui-btn mui-btn-yellow beizhu" :attr-id="item.id" :attr-index="index" v-if="type == 1" >备注</a>
						<a class="mui-btn mui-btn-grey yichu":attr-id="item.contact_user_" :attr-index="index">移除</a>
					</div>
					<div class="mui-slider-right mui-disabled" v-if="userInfo != '' && ui.id == userInfo.id">
						<a class="mui-btn mui-btn-yellow beizhu" :attr-id="item.id" :attr-index="index" v-if="type == 1">备注</a>
						<a class="mui-btn mui-btn-grey yichu" :attr-id="item.add_user_id" :attr-index="index">移除</a>
					</div>
					<div class="mui-slider-handle">
						<a class="mui-navigate-right" :attr-id="item.contact_user_id" v-if="datas.type == 1">
							<img class="mui-media-object mui-pull-left" v-if="datas.type == 1 && null != item.map.contact_user_logo" :src="ctx + item.map.contact_user_logo + '@s60x60'" onerror="this.src='${ctx}/styles/images/user.png'" />
							<img class="mui-media-object mui-pull-left" v-else-if="datas.type == 2 && null != item.map.add_user_logo" :src="ctx + item.map.add_user_logo + '@s60x60'" onerror="this.src='${ctx}/styles/images/user.png'" />
							<img class="mui-media-object mui-pull-left" v-else src="${ctx}/styles/images/user.png">
							<div class="mui-media-body" >
								{{item.contact_user_name}}
								<p class="mui-ellipsis" v-if="userInfo != '' && ui.id == userInfo.id">{{item.nick_name}}</p>
							</div>
						</a>
						<a class="mui-navigate-right" :attr-id="item.add_user_id" v-if="datas.type == 2">
						<img class="mui-media-object mui-pull-left" v-if="datas.type == 1 && null != item.map.contact_user_logo" :src="ctx + item.map.contact_user_logo + '@s60x60'" onerror="this.src='${ctx}/styles/images/user.png'" />
						<img class="mui-media-object mui-pull-left" v-else-if="datas.type == 2 && null != item.map.add_user_logo" :src="ctx + item.map.add_user_logo + '@s60x60'" onerror="this.src='${ctx}/styles/images/user.png'" />
						<img class="mui-media-object mui-pull-left" v-else src="${ctx}/styles/images/user.png">
						<div class="mui-media-body">
							{{item.add_user_name}}
							<p class="mui-ellipsis" v-if="ui.id == item.contact_user_id"></p>
						</div>
					</a>
					</div>
				</li>
			</ul>
   </div>
  </div>
</div>
<jsp:include page="../_footer.jsp" flush="true" />
<script type="text/javascript">

  var vm = new Vue({
    el: '#app',
    data: {
      datas:"",
      ctx:Common.api,
      startPage:0,
      ui:'',
      userInfo:'',
      search:"",
      type:'',
    },
    mounted: function() {
      this.$nextTick(function() {
    	  this.getAjaxData();
      });
    },
    updated: function() {
    	
    	mui('.mui-slider-handle').on('tap', 'a', function(){
	   		 var link_url = vm.ctx+"m/MUserCenter.do?method=index&user_id=" + $(this).attr("attr-id");
	  		 goUrl(link_url);
   	    });
    	
    	mui('.mui-table-view-cell').on('tap', '.beizhu', function(event){
    		var id = $(this).attr("attr-id");
    		var index = $(this).attr("attr-index");
    		event.stopPropagation();
			var elem = event.target.parentNode.parentNode;
			var btnArray = ['取消', '确定'];
			mui.prompt('', '请输入备注', '修改备注', btnArray, function(e) {
				if(e.index == 1) {
					mui.swipeoutClose(elem);
					if(null != e.value && "" != e.value) {
						Common.getData({
							route: 'm/MMyContact.do?method=updateNickName&id=' + id + "&nick_name=" + e.value,
							success: function(data) {
								mui.toast(data.msg);
								if(data.code == 1) {
									Vue.set(vm.datas.entityList[index], 'nick_name', e.value);
								}
							},
							error: function() {
								mui.toast('好像出错了哦~');
							}
						});
					}
				} else {
					mui.swipeoutClose(elem);
				}
			})
  	    });
    	mui('.mui-table-view-cell').on('tap', '.yichu', function(event){
    		var id = $(this).attr("attr-id");
    		var index = $(this).attr("attr-index");
			Common.getData({
				route: 'm/MVillage.do?method=noFollow&id=' + id,
				success: function(data) {
					mui.toast(data.msg);
					if(data.code == 1) {
// 						Vue.set(vm.datas.entityList[index], 'nick_name', e.value);
						vm.datas.entityList.splice(index,1)
					}
				},
				error: function() {
					mui.toast('好像出错了哦~');
				}
			});
  	    });
    	
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
		var count = 0;
		function pullupRefresh() {
			setTimeout(function() {
				Common.getData({
		   			route: '/m/MVillageMember.do?method=getAjaxDataFollow&user_id=${af.map.user_id}&type=${af.map.type}',
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
				   			route: '/m/MVillageMember.do?method=getAjaxData&user_id=${af.map.user_id}&type=${af.map.type}&user_name_like=' + val,
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
   			route: '/m/MVillageMember.do?method=getAjaxDataFollow&user_id=${af.map.user_id}&type=${af.map.type}',
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
   	            	vm.ui = data.datas.ui;
   	            	if(null != data.datas.userInfo){
	   	            	vm.userInfo = data.datas.userInfo;
   	            	}
   	            	vm.type = '${af.map.type}';
   	            }
   			},
   			error: function() {
   				mui.toast('好像出错了哦~');
   			}
   		});
      },
    }
  });
</script>
</body>
</html>