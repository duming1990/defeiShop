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
		<style type="text/css">
			.displayType0 {
				background: #e10e54;
				color: rgb(255, 255, 255);
				border-radius: 8px;
				font-family: Helvetica, sans-serif;
				margin: 12px;
			}
			.c-main {
				font-size: 23px;
				top: 10px;
				left: 10px;
				position: relative;
			}
			
			.c-sub-1 {
				font-size: 10px;
				margin-top: 17px;
				left: 10px;
				position: relative;
			}
			.c-sub-2 {
				font-size: 7px;
				left: 10px;
				position: relative;
			}
			.mui-flex{display:flex;height:90px;color: rgba(255, 255, 255, 1)}
			.mui-flex .tax-main{
				-webkit-box-flex: 3;
			    -webkit-flex: 3;
			    -ms-flex: 3;
			    flex: 3;
		    }
			.mui-flex .tax-split{
				-webkit-box-flex: 1;
			    -webkit-flex: 1;
			    -ms-flex:1;
			    flex: 1;
				border-right: 1px dotted #ffcdcd;
		    }
		    .mui-flex .tax-operator{
		        -webkit-box-flex: 1;
			    -webkit-flex:1;
			    -ms-flex:1;
			    flex: 1;
			    line-height: 90px;
			    text-align: center;
    			margin: 0px 10px;
		    }
		</style>
	</head>

	<body>
		<jsp:include page="../_header.jsp" flush="true" />
		<div id="app" v-cloak>
			<div class="mui-cover" id="a-decision-wrapper" style="margin-bottom: 55px;">
				<section id="s-decision" v-for="item1 in datas.yhqInfoSonlist">
					<div class="tax-box displayType0" v-for="item in item1.map.yhqInfo" v-bind:style="item1.yhq_state == 20 ? 'background:#696969':'background: #e10e54;'">
						<div class="mui-flex tax-item">
							<div class="tax-main cell" v-if="null != item">
								<div class="c-main">
									<i class="mui-price-rmb">¥</i><span class="rmb">{{item.yhq_money | formatMoney}}</span>
								</div>
								<div class="c-sub-1">{{item.yhq_name}}</div>
								<div class="c-sub-2">有效期{{item1.yhq_start_date | formatDateYmd}}至{{item1.yhq_end_date | formatDateYmd}}</div>
							</div>
							<div class="tax-split"></div>
							<div class="tax-operator cell" v-if="item1.yhq_state == 20">
								<div class="c-jf">已使用</div>
							</div>
							<div class="tax-operator cell" v-if="item1.yhq_state == -10">
								<div class="c-jf">已失效</div>
							</div>
							<div class="tax-operator cell" @click="useCoupons(item.id)" v-if="item1.yhq_state == 10">
								<div class="c-jf">去使用</div>
							</div>
						</div>
					</div>
				</section>
			</div>
		</div>
		<jsp:include page="../_footer.jsp" flush="true" />
		<script type="text/javascript">
			var vm = new Vue({
				el: '#app',
				data: {
					datas: "",
					ctx: Common.api,
					msg: "",
				},
				mounted: function() {
					this.$nextTick(function() {
						this.getAjaxData();
					});
				},
				updated: function() {},
				methods: {
					getAjaxData: function() {
						Common.getData({
							route: 'm/GetCoupons.do?method=getAjaxData',
							success: function(data) {
								if(data.code == 1) {
									vm.datas = data.datas;
								}
							},
							error: function() {
								mui.toast('好像出错了哦~');
							}
						});
					},

					useCoupons: function(id) {
						Common.getData({
							route: 'm/GetCoupons.do?method=useConpons&yhq_id=' + id,
							success: function(data) {
								if(data.code == 1) {
									location.href = app_path + "/m/MEntpInfo.do?method=index&entp_id=" + data.datas.id;
								}
							},
							error: function() {
								mui.alert('好像出错了哦~');
							}
						});
					},
					getcoupons: function(id) {
						Common.getData({
							route: 'm/GetCoupons.do?method=getYhqQrcode&',
							success: function(data) {
								if(data.code == 1) {
									mui.toast(data.msg);
								} else if(data.code == -1) {
									mui.toast(data.msg);
								} else if(data.code == -2) {
									mui.toast(data.msg);
								} else {
									Common.confirm("您还未登录，请先登录系统！", ["去登录", "再逛逛"],
										function() {
											location.href = app_path + "/m/MIndexLogin.do";
										},
										function() {});
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