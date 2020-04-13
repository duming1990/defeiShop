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
		<jsp:include page="../_public_in_head.jsp" flush="true" />
		<link rel="stylesheet" href="${ctx}/m/styles/village/css/style.css" />
		<link rel="stylesheet" href="${ctx}/m/styles/village/css/content.css" />
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/mui/poppicker/mui.picker.min.css" />
		<style type="text/css">
			.mui-table-view .mui-media-body {
				line-height: 21px;
			}
			
			#info {
				padding: 20px 10px;
			}
			
			.des {
				margin: .5em 0;
			}
			
			.des>li {
				font-size: 14px;
				color: #8f8f94;
			}
			.mui-popover.mui-popover-action {
				display: block;
				opacity: 1;
				-webkit-transform: translate3d(0, 0, 0);
				transform: translate3d(0, 0, 0);
				-webkit-transition: none;
				transition: none;
			}
			
			.mui-actionsheet-enter {
				opacity: 1;
			}
			
			.mui-actionsheet-leave-to {
				opacity: 0;
			}
			
			.mui-actionsheet-enter-active,
			.mui-actionsheet-leave-active,
			.mui-actionsheet-enter-active .mui-popover.mui-popover-action,
			.mui-actionsheet-leave-active .mui-popover.mui-popover-action {
				-webkit-transition: -webkit-transform .3s, opacity .3s;
				transition: transform .3s, opacity .3s;
			}
			
			.mui-actionsheet-enter .mui-popover.mui-popover-action,
			.mui-actionsheet-leave-to .mui-popover.mui-popover-action {
				-webkit-transform: translate3d(0, 100%, 0);
				transform: translate3d(0, 100%, 0);
			}
		</style>
	</head>
	<body style="font-size:17px!important;">
		<jsp:include page="../_header.jsp" flush="true" />
		<div id="app" v-cloak>
			<ul class="mui-table-view">
				<li class="mui-table-view-cell mui-collapse" v-for="item,index in datas.entityList" v-longtap="{fn:vuetap,item:item,index:index}" >
					<a href="#"><i class="mui-navigate-left"></i>{{item.group_name}}</a>
					<span class="mui-badges">{{item.map.fzCount}}</span>
					<div class="mui-collapse-content">
						<ul class="mui-input-group">
							<li class="mui-media mui-table-view-cell mui-transitioning" v-for="item,index2 in item.map.villageContactListList">
								<div class="mui-slider-right mui-disabled">
									<a class="mui-btn mui-btn-grey" @click="updateNickName($event,item)">备注</a>
									<a class="mui-btn mui-btn-yellow" @click="toggleActionSheet('fenzu',index,index2,$event,item)">分组</a>
								</div>
								<a @click="openUrl(item)" class="mui-slider-handle">
									<img class="mui-media-object mui-pull-left" :src="ctx + item.map.contact_user_logo + '@s60x60'">
									<div class="mui-media-body">
										{{item.contact_user_name}}
										<p class="mui-ellipsis">{{item.nick_name}}</p>
									</div>
								</a>
							</li>
						</ul>
					</div>
				</li>
			</ul>
			<action-sheet ref="fenzu" :items="fenzuItems" v-on:onitemclick="handleItemClick"></action-sheet>
		</div>

	<jsp:include page="../_footer.jsp" flush="true" />
		<script id="actionSheetTpl" type="text/html">
			<transition name="mui-actionsheet">
				<div class="mui-backdrop" v-show="isOpen" v-on:click="hide">
					<div class="mui-popover mui-popover-action mui-popover-bottom">
						<ul class="mui-table-view">
							<li v-for="(item,index) in items" v-on:click="handleItemClick(index)" class="mui-table-view-cell">
								<a>{{item.group_name}}</a>
							</li>
						</ul>
						<ul class="mui-table-view">
							<li v-on:click="hide" class="mui-table-view-cell">
								<a><b>取消</b></a>
							</li>
						</ul>
					</div>
				</div>
			</transition>
		</script>

		<script src="${ctx}/styles/mui/poppicker/mui.picker.min.js"></script>
		<script src="${ctx}/scripts/vue/vue-touch.min.js"></script>
		<script type="text/javascript">
			Vue.component('action-sheet', {
				props: ['items', 'shown'],
				data: function() {
					return {
						isOpen: !!this.shown
					}
				},
				watch: {
					shown: function() {
						this.isOpen = !!this.shown;
					}
				},
				methods: {
					show: function() {
						this.isOpen = true; //显示actionsheet
					},
					hide: function() {
						this.isOpen = false; //关闭actionsheet
					},
					toggle: function() {
						this.isOpen = !this.isOpen; //切换显示状态
					},
					handleItemClick: function(index) {
						this.$emit('onitemclick',index);
					}
				},
				template: document.getElementById("actionSheetTpl").innerText,
			});

			var vm = new Vue({
				el: '#app',
				data: {
					datas: "",
					ctx: Common.api,
					search: "",
					fenzuItems: "",
					parIndex: "",
					sonIndex: "",
					event: "",
					item: ""
				},
				mounted: function() {
					this.$nextTick(function() {
						this.getAjaxData();
					});
				},
				updated: function() {
					$("#titleSideName").click(function(){
						var elem = event.target.parentNode.parentNode;
						var btnArray = ['取消', '确定'];
						mui.prompt('', '请输入分组名称', '添加分组', btnArray, function(e) {
							if(e.index == 1) {
								mui.swipeoutClose(elem);
								if(null != e.value && "" != e.value) {
									Common.getData({
										route: 'm/MMyContact.do?method=addGroup&group_name=' +e.value,
										success: function(data) {
											mui.toast(data.msg);
											if(data.code == 1) {
												vm.datas.entityList = vm.datas.entityList.concat(data.datas.entity);
												vm.fenzuItems = vm.datas.entityList;
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
				},
				methods: {
					getAjaxData: function() {
						Common.getData({
							route: 'm/MMyContact.do?method=getAjaxData&user_id=${userInfo.id}',
							success: function(data) {
								if(data.code == 0) {
									mui.toast(data.msg);
									return false;
								} else if(data.code == 1) {
									vm.datas = data.datas;
									vm.fenzuItems = data.datas.entityList;
								}
							},
							error: function() {
								mui.toast('好像出错了哦~');
							}
						});
					},
					openUrl: function(item) {
						var link_url = vm.ctx + "m/MUserCenter.do?method=index&user_id=" + item.contact_user_id;
						goUrl(link_url);
					},
					toggleActionSheet: function(actionsheet,parIndex,sonIndex,event,item) {
						vm.parIndex = parIndex;
						vm.sonIndex = sonIndex;
						vm.event = event;
						vm.item = item;
						this.$refs[actionsheet]['toggle']();
					},
					handleItemClick: function(index) {
						if(index != vm.parIndex){
							var parEntity = vm.datas.entityList[vm.parIndex];
							var currentyEntity = vm.datas.entityList[index];
							
							Common.getData({
								route: 'm/MMyContact.do?method=fenzu&id=' + parEntity.map.villageContactListList[vm.sonIndex].id + "&groupId=" + currentyEntity.id,
								success: function(data) {
									mui.toast(data.msg);
									if(data.code == 1) {
										parEntity.map.fzCount = parEntity.map.fzCount - 1;
										currentyEntity.map.fzCount = currentyEntity.map.fzCount + 1;
										
										currentyEntity.map.villageContactListList.push(parEntity.map.villageContactListList[vm.sonIndex]);
										parEntity.map.villageContactListList.splice(vm.sonIndex,1);
									}
								},
								error: function() {
									mui.toast('好像出错了哦~');
								}
							});
							
						}
					},
					updateNickName: function(event, item) {
						event.stopPropagation();
						var elem = event.target.parentNode.parentNode;
						var btnArray = ['取消', '确定'];
						mui.prompt('', '请输入昵称', '修改昵称', btnArray, function(e) {
							if(e.index == 1) {
								mui.swipeoutClose(elem);
								if(null != e.value && "" != e.value) {
									Common.getData({
										route: 'm/MMyContact.do?method=updateNickName&id=' + item.id + "&nick_name=" + e.value,
										success: function(data) {
											mui.toast(data.msg);
											if(data.code == 1) {
												item.nick_name = e.value;
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
					},
					vuetap:function(obj){
						if(obj.item.id != 0){//未分组不能删除
							Common.confirm("你确定要删除该分组吗？",["确定","取消"],function(){
								Common.getData({
									route: 'm/MMyContact.do?method=deleteGroup&id=' + obj.item.id,
									success: function(data) {
										mui.toast(data.msg);
										if(data.code == 1) {
											vm.datas.entityList.splice(obj.index,1);
											vm.fenzuItems = vm.datas.entityList;
											Vue.set(vm.datas.entityList[vm.datas.entityList.length-1].map, 'villageContactListList', data.datas.villageContactListList);
											Vue.set(vm.datas.entityList[vm.datas.entityList.length-1].map, 'fzCount', data.datas.villageContactListList.length);
										}
									},
									error: function() {
										mui.toast('好像出错了哦~');
									}
								});
							},function(){
							});
						}
			        }
				}
			});
		</script>
	</body>

</html>