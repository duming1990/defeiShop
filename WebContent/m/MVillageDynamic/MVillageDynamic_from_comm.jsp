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
		<link rel="stylesheet" href="${ctx}/m/styles/village/css/release.css" />
		<link rel="stylesheet" href="${ctx}/styles/mui/poppicker/mui.picker.min.css" />
		<link rel="stylesheet" href="${ctx}/styles/mui/poppicker/mui.poppicker.css" />
		<style type="text/css">
			 img {
				display: -webkit-inline-box;
				margin: 0;
				padding: 0;
			 }
			.mui-btn {
				font-size: 16px;
				padding: 8px;
				margin: 3px;
			}
			.sell-btn{
			    background-color: #fff;
			    float: left!important;
			    width: 56%!important;
			}
			.mui-img a{
				position: relative;
			    display: inline-block;
			}
			.mui-img a .aui-icon{
				position: absolute;
			    right: -3px;
    			top: -10px;
			}
</style>
</head>
	<body>
		<div id="app" v-cloak>
			<header class="mui-bar mui-bar-nav mui-bars">
				<i class="mui-icon mui-icon-closeempty" onclick="Common.goBack();"></i>
				<h1 class="mui-title">发布宝贝</h1>
			</header>
			<form action="/m/MVillageDynamic" enctype="multipart/form-data" method="post" class="ajaxForm0">
				<input type="hidden" name="method" value="save" />
				<input type="hidden" name="village_id" id="village_id" value="${af.map.village_id}" />
				<input type="hidden" name="is_public" id="is_public" value="1" />
				<input type="hidden" name="comm_no" id="comm_no" />
				<input type="hidden" name="type" id="type" value="${af.map.type}" />
				<div class="over_height">
					<div class="color_bjt" style="margin-bottom: 10px;">
						<div class="mui-input-row mui-img" style="padding-left: 25px;">
							<input type="text" name="comm_name" id="comm_name" class="pub_inpuit" placeholder="标题 品牌品类都是买家喜欢搜索的">
						</div>
						<div class="shuru">
							<textarea rows="6" name="content" id="content" style="width: 100%" placeholder="描述一下宝贝的细节或故事"></textarea>
							<div class="mui-img" style="padding: 0 0 20px;position: relative;">
								<img class="photo photoadd" @click="clickFiles();" src="${ctx}/m/styles/village/img/imgcama.png" alt="" />
								<input type="file" style="display: none;" name="files" id="files" accept="image/*" />
							</div>
						</div>
					</div>
					<div class="color_bjt">
						<ul class="mui-table-view" style="color: #101010;font-size: 14px;font-weight: 400;">
							<li class="mui-table-view-cell" style="padding: 10px 15px 10px 25px;">
								<div class="mui-input-row">
									<label style="padding-left: 0px;">价格</label>
									<input type="text" id="comm_price" name="comm_price" placeholder="0.0" style="text-align: right;">
								</div>
							</li>
							<li class="mui-table-view-cell" style="padding: 10px 15px 10px 25px;">
								<div class="mui-input-row">
									<label style="padding-left: 0px;">分类</label>
<!-- 									<button id='showUserPicker' class="mui-btn mui-btn-block" type='button'>选择分类</button> -->
									<div id='userResult' class="ui-alert"></div>
									<input type="button" @click="btn_class($event)" id="_cls_name" class="btn mui-btn mui-btn-block sell-btn" value="选择分类" />
									<input type="hidden" name="cls_id" id="cls_id">
									<input type="hidden" name="cls_name" id="cls_name">
								</div>
							</li>
							<li class="mui-table-view-cell" style="padding: 10px 15px 10px 25px;">
								<div class="mui-input-row">
									<label style="padding-left: 0px;">库存</label>
									<input type="text" name="inventory" id="inventory" placeholder="0" style="text-align: right;">
								</div>
							</li>
							<li class="mui-table-view-cell" style="padding: 10px 15px 10px 25px;">
								<div class="mui-input-row">
									<label style="padding-left: 0px;">上架时间</label>
									<input type="button" @click="btn_date($event);" data-options='{"type":"date"}' id="_up_date" class="btn mui-btn mui-btn-block sell-btn" value="选择日期" />
									<input type="hidden" name="up_date" id="up_date">
								</div>
							</li>
							<li class="mui-table-view-cell" style="padding: 10px 15px 10px 25px;">
								<div class="mui-input-row">
									<label style="padding-left: 0px;">下架时间</label>
									<input type="button" @click="btn_date($event);" data-options='{"type":"date"}' id="_down_date" class="btn mui-btn mui-btn-block sell-btn" value="选择日期" />
									<input type="hidden" name="down_date" id="down_date">
								</div>
							</li>
						</ul>
					</div>
				</div>
			</form>
			<div style="position:fixed;left:0;bottom:0;width: 100%;background-color: #fff;height: 50px;" class="footers">
				<button type="button" @click="btn" class="mui-btn mui-btn-danger mui-btn-block">确定发布</button>
			</div>
		</div>
		<script type="text/javascript" src="${ctx}/commons/scripts/jquery.form.min.js"></script>
		<script type="text/javascript" src="${ctx}/styles/mui/poppicker/mui.poppicker.js"></script>
		<script type="text/javascript" src="${ctx}/styles/mui/poppicker/mui.picker.min.js"></script>
		<script type="text/javascript" src="${ctx}/commons/scripts/validator.m.js"></script>
		<script type="text/javascript">
			var vm = new Vue({
				el: '#app',
				data: {
					ctx: Common.api,
					datas: "",
					commClassList:"",
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
							route: '/m/MVillageDynamic.do?method=getAjaxDataList',
							data:{
			       				village_id:'${af.map.village_id}',
			       				type:'${af.map.type}',
			       			},
							success: function(data) {
								if(data.code == 0) {
									mui.alert(data.msg);
									return false;
								} else if(data.code == 1) {
									vm.datas = data.datas;
									vm.commClassList = data.datas.commClassList;
								}
							},
							error: function() {
								mui.alert('好像出错了哦~');
							}
						});
					},
					clickFiles: function() {
						$("#files").trigger("click");
					},
					btn: function() {
						var f0 = $(".ajaxForm0").get(0);
						Common.loading();
						$.ajax({
							type: "POST",
							url: "CsAjax.do?method=getCommNo&cls_id=20045",
							dataType: "json",
							error: function(request, settings) {},
							success: function(data) {
								if(null != data && null != data.comm_no) {
									$("#comm_no").val(data.comm_no);
									if(Validator.Validate(f0, 1)) {
										$.ajax({
											type: "POST",
											url: "?method=save",
											data: $(f0).serialize(),
											dataType: "json",
											error: function(request, settings) {},
											success: function(data) {
												mui.toast(data.msg);
												if(data.code == 1) {
													var url = "MVillage.do?method=index&id=${af.map.village_id}";
													setTimeout(function() {
														goUrl(url);
													}, 500);
												}
											}
										});
									}
									Common.hide();
								}
							}
						});
					},
					btn_date:function (e){
						var _self = e;
						if(_self.picker) {
							_self.picker.show(function (rs) {
								$(e.target).val(rs.text);
								_self.picker.dispose();
								_self.picker = null;
							});
						} else {
							var optionsJson = $(e.target).attr('data-options') || '{}';
							var options = JSON.parse(optionsJson);
							var id = $(e.target).attr('id');
							_self.picker = new mui.DtPicker(options);
							_self.picker.show(function(rs) {
								$(e.target).val(rs.text);
								var input_id = id.replace("_","");
								$("#"+input_id).val(rs.text);
								_self.picker.dispose();
								_self.picker = null;
							});
						}
					},
					btn_class:function (e){
						var _cls_name = document.getElementById('_cls_name');
						var cls_name = document.getElementById('cls_name');
						var cls_id = document.getElementById('cls_id');
						var userPicker = new mui.PopPicker();
						userPicker.setData(vm.commClassList);
						userPicker.show(function(items) {
							cls_name.value = items[0].text;
							cls_id.value = items[0].value;
							_cls_name.value=items[0].text;
						});
					}
				}
			});
			$(".over_height").css("height", window.innerHeight - $("header").height() - $(".footers").height());
			$("#comm_name").attr("dataType", "Require").attr("msg", "请填写商品名称！");
			$("#content").attr("dataType", "Require").attr("msg", "请描述一下宝贝的细节或故事");
			$("#comm_price").attr("dataType", "Currency").attr("msg", "请填写商品价格");
			$("#inventory").attr("dataType", "Require").attr("msg", "请填写商品库存");
			$("#up_date").attr("dataType", "Require").attr("msg", "请填写上架时间");
			$("#down_date").attr("dataType", "Require").attr("msg", "请填写下架时间");
			var btn_name = "上传";
			uploadNew("files", "image", btn_name, "${ctx}");
			
			
		</script>
	</body>

</html>