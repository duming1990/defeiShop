<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>九个挑夫</title>
		<link href="${ctx}/styles/mui/css/mui.min.css" rel="stylesheet" type="text/css"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/countryvillageMyIndex/css/global.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/countryvillageMyIndex/css/font-awesome.min.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/countryvillageMyIndex/css/common.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/customer.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/common.css?v20161116"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/filter-deallist.css?20180613"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/m/scripts/lightGallery/css/lightgallery.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/common.css"  />
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/category-list.css"  />
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/category-nav.css?v=20180427"  />
		<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script>
	</head>
	<style>
	.comm-input.my{margin-bottom: 12px;height: 40px;width: auto;}
	.cart{float: right;cursor: pointer;width: 30px;display: block;margin-left: 20px;}
	.categorys {width: -webkit-fill-available;height: 38px;background-color: #f4f4f4;}
	#searchbtn{border: none;color: #fff;background: #ec5051;}
	.commentbox{padding-left: 90px;}
	</style>
	<body class="page-color">
	<jsp:include page="../../_header.jsp" flush="true" />
		<!-- 村背景图 -->
		<div id="app" v-cloak>
			<div class="xian-header" v-bind:style="{backgroundImage:'url(' + ctx+banner + ')'}">
				<div class="black-layer"></div>
				<div class="mycity-wrap">
					<div class="left-group">
						<!--个人头像 -->
						<div class="top-pic">
							<a :href="'VillageIndex.do?method=myIndex&village_id='+village_id+'&member_id='+user.id">
								<img v-if="user_logo.indexOf('http://') == -1" :src="ctx + user_logo"/>
								<img v-else :src="user_logo"/>
							</a>
						</div>
						<!-- 基本个人信息 -->
						<div class="group-text">
						    <h3><a href="#">{{user.real_name}}</a> <span v-if="user.is_poor == 1" class="fp"><img src="${ctx}/styles/countryvillageMyIndex/images/fp-icon.png"></span></h3>
							<div class="subtext w480">{{user.autograph}}<span class="stinfo" v-if="user.is_poor == 1"><a @tap="getPoor(user.poor_id)">查看详情</a></span></div>
						</div>
						<div class="clearfix"></div>
						<a v-if="follow_name == '+关注'" class="follow" @click="follow">{{follow_name}}</a>
						<a v-else @click="noFollow();" class="follow">{{follow_name}}</a>
					</div>
					<div class="right-group">
						<ul class="my-correlation">
							<li><a href="#">{{datas.zan_count}} <p style="color: #fff;">赞</p></a></li>
							<li><a href="#">{{datas.guanzhu_count}} <p style="color: #fff;">关注</p></a></li>
							<li><a href="#">{{datas.fensi_count}}<p style="color: #fff;">粉丝</p></a></li>
						</ul>
					</div>
				</div>
			</div>
			<div class="mycity-wrap mianbox">
				<div class="mianleftbox">
					<div class="comm-box">
						<div class="tj-box">
							<h3 class="comm-title">
								<a href="javascript:void(0)"><span :class="comm_or_dynamic_show == 1? 'ht cur': 'ht'" @tap="dcShow(1)">动态</span></a>
								<a href="javascript:void(0)"><span :class="comm_or_dynamic_show == 2? 'ht cur':'ht'" @tap="dcShow(2)">商品</span></a>
								<a v-if="user.is_poor == 1" href="javascript:void(0)"><span :class="comm_or_dynamic_show == 3? 'ht cur':'ht'" @tap="dcShow(3)">扶贫商品</span></a>
							</h3>
							<div class="user-datbox" v-for="(Dynamic,index) in DynamicList" v-if="Dynamic.type == comm_or_dynamic_show">
								<div class="user-group nowth">
									<div class="uerhead" style="left: 20px;width: 50px;height: 50px;">
										<a :href="'VillageIndex.do?method=myIndex&village_id='+Dynamic.village_id+'&member_id='+Dynamic.add_user_id">
											<img v-if="null == Dynamic.map.user_logo" src="${ctx}/styles/images/user64.png"/>
											<img v-if="null != Dynamic.map.user_logo && Dynamic.map.user_logo.indexOf('http://') == -1" :src="ctx + Dynamic.map.user_logo+'@s96x96'"/>
											<img v-if="null != Dynamic.map.user_logo && Dynamic.map.user_logo.indexOf('http://') != -1" :src="Dynamic.map.user_logo"/>
										</a>
									</div>
									<div class="datinfo">
										<div class="userinfo-box">
											<div class="pricebox" v-if="Dynamic.type==2">
												<a href="javascript:void(0)">￥{{Dynamic.map.commInfoTczh.comm_price}} 元</a>
												<img  v-if="Dynamic.map.commInfoTczh.inventory > 0 && Dynamic.map.commInfoTczh != null" @tap="buy(Dynamic.map.commInfoTczh)" class="cart" src="${ctx}/m/styles/village/img/cart.png"/>
												<img  v-else @tap="noInventory" class="cart"  src="${ctx}/m/styles/village/img/no_cart.png" title="该商品已售罄"/>
											</div>
											<div class="username">{{Dynamic.map.real_name}}
												<p class="send-time"><i class="fa fa-clock-o"></i> {{Dynamic.add_date | formatDate}}</p>
											</div>
										</div>
										<div class="send-cont">
											{{Dynamic.content}}
											<div class="send-img lightgallery">
												<a :href="ctx+DynamicImg.file_path"  v-for="DynamicImg in Dynamic.map.imgList">
													<img :src="ctx + DynamicImg.file_path+'@s70x70'"/>
												</a>
											</div>
										</div>
									</div>
								</div>
								<div class="clearfix"></div>
								<!--回复-->
								<div class="commentbox">
									<div class="comment-tool">
										<div class="zan-user"><i class="fa fa-heart-o"></i>赞 <span>{{Dynamic.map.zanNameList}}</span>等{{Dynamic.map.zan_count}}人</div>
										<a v-if="datas.user!=''" @tap="goDianZan(Dynamic,index)" v-bind:title="Dynamic.map.is_zan ==1 ?'取消点赞':'点赞'">
											<i class="fa fa-heart-o" v-bind:style="{'color':Dynamic.map.is_zan ==1 ? '#e8601b':'#333'}"></i></a>
										<a v-else @tap="loginTip();" title="点赞"><i class="fa fa-heart-o"></i></a>

										<a v-if="datas.user!=''" href="javascript:void(0)" title="评论" @tap="showInput(index);"><i class="fa fa-comment-o"></i></a>
										<a v-else title="评论" @tap="loginTip();"><i class="fa fa-comment-o"></i></a>
										<a v-if="datas.user!=''&& ui!=null && datas.user.id==ui.id" href="javascript:void(0)" title="删除" @tap="delDynamic(Dynamic.id);">
										<img src="${ctx}/styles/countryvillageMyIndex/images/delete.png" style="margin-top: -17px;width: 20px;"/>
										</a>

									</div>
									<form>
										<div class="comment-inputbox" v-bind:style="{'display':index === inputNum ? 'block':'none'}">
											<input type="text" placeholder="内容不超过200字" class="comm-input" v-model="Dynamic.comm_content" />
											<input @tap="commentSubmit(Dynamic,index)" type="button" value="评论" class="comm-sub" />
										</div>
									</form>
									<div class="comment-n" v-for="DynamicComment in Dynamic.map.commentList">
										<div class="user-commt"><span class="name"><a href="#">{{DynamicComment.map.real_name}}</a></span>：<span class="comt">@{{DynamicComment.link_user_name}}:{{DynamicComment.content}}</span></div>
										<div class="comment-tool"><span class="time"><i class="fa fa-clock-o"></i> {{DynamicComment.add_date | formatDate}}</span></div>
										<form>
											<div class="comment-inputbox">
												<input type="text" placeholder="内容不超过200字" class="comm-input" />
												<input type="submit" value="回复" class="comm-sub" />
											</div>
										</form>
									</div>
								</div>
								<!--回复结束-->
							</div>
							<div class="list-more" v-if="3 != comm_or_dynamic_show">
								<a @tap="dynamicMore">...更多...</a>
							</div>
							<!--扶贫商品 -->
							<div class="user-datbox"  v-if="comm_or_dynamic_show == 3">
								<div class="J-scrollloader cf J-hub">
								 <div class="component-deal-list mt-component--booted">
								  <div class="deal-tile" style="width: 48%;margin: 5px;" v-for="(poorComm,index) in poorCommList">
								      <a :href="'entp/IndexEntpInfo.do\?method=getCommInfo&id='+poorComm.comm_id" class="deal-tile__cover" target="_blank">
								        <img class="J-webp" style="width: 100%;" height="290" :alt="poorComm.map.comm_name" :src="ctx+poorComm.map.main_pic+'@compress'">
								      </a>
								      <h3 class="deal-tile__title">
								       <a :href="'entp/IndexEntpInfo.do\?method=getCommInfo&id='+poorComm.comm_id" class="w-link" :title="poorComm.map.comm_name" target="_blank" style="height: auto;">
								          <span class="xtitle">{{poorComm.map.comm_name}}</span>
								         </a>
								      </h3>
								      <p class="deal-tile__detail"><span class="price">¥<strong>{{poorComm.map.sale_price}}</strong></span></p>
								   </div>
								   </div>
								 </div>
								<div class="list-more">
									<a @tap="poorCommMore">...更多...</a>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="siderightbox" style="width: 331px;">
					<div class="comm-box" v-if="village_id!=''">
						<div class="inbox">
							<a id="pub_dynamic">发布动态</a>
							<a id="pub_comm">发布商品</a>
						</div>
					</div>
					<!--我加入的村 -->
					<div :class="village_id!=''?'comm-box mt15':'comm-box'">
						<h3 class="comm-title"><span class="ht cur">我加入的村</span></h3>
						<ul class="text-img-list">
							<li v-for="(item,index) in myJoinVillageList" v-if='index<=9'>
								<a :href="'VillageIndex.do?method=villageIndex&village_id='+item.village_id">
									<img  :src="ctx+item.map.village_logo">
									<p title="item.map.village_name">{{item.map.village_name}}</p>
								</a>
							</li>
						</ul>
						<div class="list-more">
							<a @tap="myJoinVillageMore">...更多...</a>
						</div>
					</div>
					<!--与我相关 -->
					<div :class="village_id!=''?'comm-box':'comm-box mt15'">
						<h3 class="comm-title"><span class="ht cur">与我相关</span></h3>
						<ul class="text-img-list state">
							<li v-for="(item,index) in villageDynamicRecordList">
								<a v-if="item.village_id == null" :href="'VillageIndex.do?method=myIndex&village_id='+village_id+'&member_id='+item.add_user_id">
								<a v-else :href="'VillageIndex.do?method=myIndex&village_id='+item.village_id+'&member_id='+item.add_user_id">
								<img v-if="null == item.map.user_logo" src="${ctx}/styles/images/user64.png"/>
								<img v-if="null != item.map.user_logo && item.map.user_logo.indexOf('http://') == -1" :src="ctx + item.map.user_logo+'@s96x96'"/>
								<img v-if="null != item.map.user_logo && item.map.user_logo.indexOf('http://') != -1" :src="item.map.user_logo"/>
								<p title="">{{item.map.real_name}}<span class="time">{{item.add_date | formatDate}}</span></p>
								</a>
								<div class="commstate">{{item.remark}}</div>
							</li>
						</ul>
						<div class="list-more">
							<a @tap="aboutMeMore">...更多...</a>
						</div>
					</div>

				</div>
			</div>
			<div class="panel-dt">
				<div class="black-layer"></div>
				<div class="blankbox">
					<a href="javascript:void(0)" class="close-panel-dt">x</a>
					<form action="/m/MVillageDynamic" enctype="multipart/form-data" method="post" class="ajaxForm0">
					<input type="hidden" name="method" value="save" />
					<input type="hidden" name="village_id" id="village_id" value="${af.map.village_id}"/>
					<input type="hidden" name="type" id="type"/>
						<div class="inputwrap" v-show="!showPub">
							<input type="text" name="comm_name" id="comm_name" class="comm-input" placeholder="标题 品牌品类都是买家喜欢搜索的">
						</div>
						<div class="inputwrap">
							<input type="text" :placeholder="showPub?'分享新鲜事...':'描述一下宝贝的细节或故事'" class="comm-input" id="content" name="content" style="margin-top: 10px;width: 100%;padding: 6px;">
						</div>
						<div class="inputwrap">
							<div class="img-up">
								<ul class="img-list">
									<li id="photoadd">
										<span class="input-file">
											<img @click="clickFiles();" src="${ctx}/styles/countryvillageMyIndex/images/input-file-icon.png" alt="上传图片" />
											<input type="file" style="display: none;" name="files" id="files" accept="image/*" />
										</span>
									</li>
								</ul>
							</div>
						</div>
						<div class="inputwrap" v-show="showPub">是否公开:
							<select name="is_public" id="is_public" style="width: 50px;margin-left: 20px;" class="comm-input my">
					           <option value="1">是</option>
					           <option value="0">否</option>
					        </select>
						</div>
						<div class="inputwrap" v-show="!showPub">分类：
							<select name="cls_id" id="cls_id" style="width: 80px;margin-top: 5px;" class="comm-input my">     
					           <option v-for="commClass in commClassList" :value="commClass.value">{{commClass.text}}</option>
					        </select>
						</div>
						<div class="inputwrap" style="float: left;width: 35%;margin-left: 100px;" v-show="!showPub">价格：
							<input type="text" id="comm_price" name="comm_price" class="comm-input my" placeholder="商品价格/元" style="text-align: center;">
						</div>
						<div class="inputwrap" style="width: 36%;" v-show="!showPub">库存：
							<input type="text" id="inventory" name="inventory" class="comm-input my" placeholder="库存" style="text-align: center;">
						</div>
						<div class="inputwrap" style="float: left;width: 35%;margin-left: 100px;" v-show="!showPub">上架时间：
						    <fmt:formatDate value="${af.map.up_date}" pattern="yyyy-MM-dd" var="_up_date" />
                            <input class="comm-input my" name="up_date" id="up_date" size="12" maxlength="20"  readonly="true" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:DayFunc})" style="cursor:pointer;text-align: center;width: 130px;" value="${_up_date}"/>
						</div>
						<div class="inputwrap" style="width: 36%;" v-show="!showPub">下架时间：
							<fmt:formatDate value="${af.map.down_date}" pattern="yyyy-MM-dd" var="_down_date" />
							<input class="comm-input my" name="down_date" id="down_date" size="12" maxlength="20"  readonly="true" onclick="WdatePicker()" style="cursor:pointer;text-align: center;width: 130px;" value="${_down_date}"/>
						</div>
						
						<div class="inputwrap">
							<input @click="btn();" type="button" value="提交" class="inpusub">
						</div>
					</form>
				</div>
			</div>
		</div>
		<script type="text/javascript" src="${ctx}/styles/mui/mui.min.js"></script>
		<script type="text/javascript" src="${ctx}/styles/mui/common.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/vue/vue.min.js"></script>
		<script type="text/javascript" src="${ctx}/m/scripts/lightGallery/js/lightgallery-all.min.js?20180530"></script>
		<script type="text/javascript" src="${ctx}/styles/countryvillageMyIndex/js/jquery.SuperSlide.2.1.1.js"></script>
		<script type="text/javascript" src="${ctx}/styles/countryvillageMyIndex/js/global.js"></script>
		<script type="text/javascript" src="${ctx}/commons/scripts/jquery.form.min.js"></script>
		<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
		<script type="text/javascript" src="${ctx}/commons/scripts/validator.m.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
		<script>
			jQuery(".sli-textbox").slide({
				titCell: ".hd ul",
				mainCell: ".slidemain",
				autoPage: true,
				effect: "top",
				autoPlay: true,
				vis: 1
			});
			jQuery(".xc-newspicbox").slide({
				mainCell: ".slidemain",
				autoPlay: true
			});

			Vue.filter('formatDate', function(value) {
				return Common.formatDate(value, 'yyyy-MM-dd HH:mm');
			});
			
			var member_id = "${af.map.member_id}";
			var village_id = "${af.map.village_id}";
			
			var vm = new Vue({
				el: '#app',
				data: {
					ctx: Common.api,
					banner: "styles/countryvillageMyIndex/images/myggx.jpg",
					user_logo:"styles/images/user.png",
					user: "",
					follow_name:"+关注",
					DynamicList:new Array(),
					villageDynamicRecordList:new Array(),
					myJoinVillageList:new Array(),
					commClassList:"",
					poorCommList:new Array(),
					datas: "",
					ui: "",
					village_id:"",
					inputNum: "",
					showPub:false,
					comm_or_dynamic_show:1,
					startPage:0,
					dynamicStartPage:0,
					AboutMeStartPage:0,
					myJoinVillageStartPage:0,
				},
				mounted: function() {
					this.$nextTick(function() {
						this.getAjaxData();
					});
				},
				updated: function() {
					$('.lightgallery').lightGallery({download:false});
				},
				methods: {
					getAjaxData: function() {
						 Common.getData({
		                        route: '/VillageIndex.do?method=getMemberInfo&user_id='+member_id+'&village_id='+village_id,
		                        success: function(data) {
		                            if (data.code == -1) {
		                                mui.alert(data.msg);
		                                return false;
		                            } else if (data.code == 1) {
		                                vm.datas = data.datas;
		                                vm.user = data.datas.user;
		                                if(null != data.datas.user.user_logo){
			                                vm.user_logo = data.datas.user.user_logo;
		                                }
		                                if (null != data.datas.is_guanzhu_cout && data.datas.is_guanzhu_cout > 0) {
		                                    vm.follow_name = "已关注";
		                                }
		                                if (null != data.datas.is_huguan && data.datas.is_huguan > 0) {
		                                    vm.follow_name = "互相关注";
		                                }
		                                if(null != data.datas.poorInfo){
		                                	vm.poorInfo = data.datas.poorInfo;
		                                }
		                                if(null != data.datas.ui){
											vm.ui = data.datas.ui;
										}
		                            }
		                        },
		                        error: function() {
		                            mui.alert('好像出错了哦~');
		                        }
		                    });
						
						//获取动态信息
						this.getDynamicListData(member_id,vm.dynamicStartPage);
						//我加入的村
						this.getMyJoinVillageList(vm.myJoinVillageStartPage);
						//与我相关
						this.getAboutMeData(vm.AboutMeStartPage);
						//获取扶贫商品
						this.getPoorCommData(member_id,vm.startPage);
						//商品类型
						if(village_id!=null&&village_id!=""){
							vm.village_id=village_id;
							Common.getData({
								route: '/m/MVillageDynamic.do?method=getAjaxDataList',
								data:{
				       				village_id:village_id,
				       				type:2,
				       			},
								success: function(data) {
									if(data.code == 0) {
										mui.toast(data.msg);
										return false;
									} else if(data.code == 1) {
										vm.commClassList = data.datas.commClassList;
									}
								},
								error: function() {
									mui.alert('好像出错了哦~');
								}
							});
						}
					},
					poorCommMore:function() {
						this.getPoorCommData(member_id,vm.startPage+1);
						vm.startPage = vm.startPage+1;
					},
					dynamicMore:function() {
						this.getDynamicListData(member_id,vm.dynamicStartPage+1);
						vm.dynamicStartPage = vm.dynamicStartPage+1;
					},
					aboutMeMore:function() {
						this.getAboutMeData(vm.AboutMeStartPage+1)
						vm.AboutMeStartPage = vm.AboutMeStartPage+1;
					},
					showInput: function(index) {
						vm.inputNum = index;
					},
					myJoinVillageMore:function() {
						this.getMyJoinVillageList(vm.myJoinVillageStartPage+1);
						vm.myJoinVillageStartPage = vm.myJoinVillageStartPage+1;
					},
					loginTip: function() {
						mui.toast('请先登录！');
// 						this.login();
					},
					
					login:function(){
						window.setTimeout(function () { 
							window.location.href=vm.ctx+"login.shtml?returnUrl=VillageIndex.do";
						}, 1500);
					},
					noInventory:function() {
						mui.toast('该商品已售罄！');
					},
					
					clickFiles:function(){
			    		$("#files").trigger("click");
			    	},
			    	
			    	getPoor:function(poor_id){
						$.dialog({
							title:  "贫困信息",
							width:  600,
							height: 800,
					        lock:true ,
							content:"url:${ctx}/m/MUserCenter.do?method=viewPoorDetails&poor_id="+poor_id
						});
					},
			    	dcShow:function(type){
			    		vm.comm_or_dynamic_show = type;
			    	},
			    	btn:function(){
			    		if($("#type").val() == 1){
			    			this.pubDynamic();
			    		}else{
			    			this.pubComm();
			    		}
			    	},
			    	pubDynamic:function(){
			    		var f0 = $(".ajaxForm0").get(0);
			    		$.ajax({
							type: "POST",
							url: "m/MVillageDynamic.do?method=save",
							data: $(f0).serialize(),
							dataType: "json",
							error: function(request, settings) {},
							success: function(data) {
								mui.toast(data.msg);
								if(data.code == 1){
									setTimeout(function() {
										window.parent.location.reload();
									}, 500);
								}
							}
						});
			    	},
					pubComm: function() {
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
											url: "m/MVillageDynamic.do?method=save",
											data: $(f0).serialize(),
											dataType: "json",
											error: function(request, settings) {},
											success: function(data) {
												mui.toast(data.msg);
												if(data.code == 1) {
													setTimeout(function() {
														window.parent.location.reload();
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
					//获取动态
					getDynamicListData: function(member_id,startPage){
						Common.getData({
							route: '/VillageIndex.do?method=getMemberDynamicList',
							data: {
								member_id: member_id,
								startPage: startPage,
							},
							success: function(data) {
								if(data.code == 0) {
									mui.toast(data.msg);
									return false;
								} else if(data.code == 1) {
									if(data.villageDynamicList != null && data.villageDynamicList !=""){
										vm.DynamicList = vm.DynamicList.concat(data.villageDynamicList);
									}else{
										mui.toast('已全部加载！');
										return false;
									}
								}
							},
							error: function() {
								mui.toast('好像出错了哦~');
							}
						});						
					},
					//获取扶贫商品
					getPoorCommData: function(member_id,startPage){
						Common.getData({
						route: '/VillageIndex.do?method=getPoorCommList',
						data: {
							member_id: member_id,
							startPage: startPage,
						},
						success: function(data) {
						   if(data.poorCommList != null && data.poorCommList !=""){
							   vm.poorCommList = vm.poorCommList.concat(data.poorCommList);
							}else{
								mui.toast('已全部加载！');
								return false;
							}
						},
						error: function() {
							mui.toast('好像出错了哦~');
						}
						});
					},
					//我加入的村
					getMyJoinVillageList: function(startPage){
						Common.getData({
							route: '/VillageIndex.do?method=myJoinVillageList',
							data: {
								startPage: startPage,
							},
							success: function(data) {
								if(data.myJoinVillageList != null && data.myJoinVillageList !=""){
									vm.myJoinVillageList = vm.myJoinVillageList.concat(data.myJoinVillageList);
								}else{
									mui.toast('已全部加载！');
									return false;
								}
							},
							error: function() {
								mui.toast('好像出错了哦~');
							}
						});
					},
					//与我相关
					getAboutMeData: function(startPage){
						Common.getData({
							route: '/VillageIndex.do?method=aboutMe&id='+member_id,
							data: {
								startPage: startPage,
							},
							success: function(data) {
								if(null != data.datas.villageDynamicRecordList && data.datas.villageDynamicRecordList != "") {
									vm.villageDynamicRecordList = vm.villageDynamicRecordList.concat(data.datas.villageDynamicRecordList);
								}else{
									mui.toast('已全部加载！');
									return false;
								}
							},
							error: function() {
								mui.alert('好像出错了哦~')
							}
						});
					},	
					//评论
					commentSubmit: function(Dynamic,index) {
						vm.inputNum = "";
						Common.getData({
							route: 'm/MVillageDynamic.do?method=saveComment',
							data: {
								village_id: Dynamic.village_id,
								type: 1,//评论
								content: Dynamic.comm_content,
								id: Dynamic.id,
							},
							success: function(data) {
								if(data.code == 0) {
									mui.toast('请先登录！');
								}
								if(data.code == 1) {
									mui.toast('评论成功！');
									vm.DynamicList[index].map.commentList=vm.DynamicList[index].map.commentList.concat(data.insertComment);
									vm.DynamicList[index].map.commentList.map.real_name=vm.DynamicList[index].map.commentList.map.real_name.concat(data.insertComment.map.real_name);
								}
							},
							error: function() {
								mui.toast('好像出错了哦~');
							}
						});
					},
					//点赞	
					goDianZan: function(dynamic, index) {
						Common.getData({
							route: 'm/MVillageDynamic.do?method=saveComment&type=3&id=' + dynamic.id,
							data: {
								village_id: dynamic.village_id,
							},
							success: function(data) {
								if(data.code == 0) {
									mui.toast('请先登录！');
// 									this.login();
								}
								if(data.code == 1) {//点赞
									var names = vm.DynamicList[index].map.zanNameList.concat("、"+data.insertComment.map.real_name);
									vm.DynamicList[index].map.zanNameList = names;
									vm.DynamicList[index].map.is_zan=1;
									var zan_count = vm.DynamicList[index].map.zan_count+1;
									vm.DynamicList[index].map.zan_count = zan_count;
								}
								if(data.code == -2) {//取消点赞
									var array2= vm.DynamicList[index].map.zanNameList.split("、"); 
									array2.splice(data.cur_user_zanName_index,1);
									vm.DynamicList[index].map.zanNameList = array2.join("、");
									vm.DynamicList[index].map.is_zan=0;
									var zan_count = vm.DynamicList[index].map.zan_count-1;
									vm.DynamicList[index].map.zan_count = zan_count;
								}
								if(data.code == -1) {
									mui.toast(data.msg);
								}
							},
							error: function() {
								mui.alert('好像出错了哦~');
							}
						});
					},
					delDynamic: function(id) {
						Common.confirm("确定删除该动态吗？", ["取消", "确定"], function() {}, function() {
							Common.getData({
								route: 'VillageIndex.do?method=delDynamic&id='+id,
								success: function(data) {
									if(data.code == "1") {
										mui.toast(data.msg);
									    setTimeout("window.location.reload();", 1500 )
										
									}else {
										mui.toast(data.msg);
									}

								},
								error: function() {
									mui.alert('好像出错了哦~');
								}
							});
						});
						
					},
					buy: function(item) {
						if(vm.ui == null || vm.ui == ""){
							this.loginTip();
						}else{
							//加入购物车
							//判断是否支设置支付密码
							Common.getData({
								route: 'm/MVillage.do?method=isSetUpPasswordPay',
								success: function(data) {
									if(data.code == "1") {
										var url= vm.ctx + "/IndexShoppingNoCar.do?comm_id=" + item.comm_id + "&pd_count=" + 1 + "&comm_tczh_id=" + item.id + "&showAddress=true";
										window.location.href=url;
									}
									else if(data.code == "-1") {
										Common.confirm(data.msg, ["取消", "去设置"], function() {}, function() {
											var url = vm.ctx + "/manager/customer/MySecurityCenter.do?par_id=1100620000&mod_id=1100620100";
											window.location.href=url;
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
						}
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
	                                	window.location.href=vm.ctx+"login.shtml?returnUrl=VillageIndex.do";
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
	                                vm.follow_name = "+关注";
	                            }
	                            if(data.code == -1) {
	                                mui.toast(data.msg);
	                                return false;
	                            }
	                            if(data.code == 0){
	                                Common.confirm(data.msg,["取消","去登陆"],function(){
	                                },function(){
	                                	window.location.href=vm.ctx+"login.shtml?returnUrl=VillageIndex.do";
	                                });
	                            }
	                        },
	                        error: function() {
	                            mui.alert('好像出错了哦~');
	                        }
	                    });
	                },
				}
			});
			
			$("#comm_name").attr("dataType", "Require").attr("msg", "请填写商品名称！");
			$("#content").attr("dataType", "Require").attr("msg", "请描述一下宝贝的细节或故事");
			$("#comm_price").attr("dataType", "Require").attr("msg", "请填写商品价格");
			$("#inventory").attr("dataType", "Require").attr("msg", "请填写商品库存");
			$("#up_date").attr("dataType", "Require").attr("msg", "请填写上架时间");
			$("#down_date").attr("dataType", "Require").attr("msg", "请填写下架时间");
			$("#cls_id").attr("dataType", "Require").attr("msg", "请选择商品分类");
			
			var btn_name = "上传";
			uploadPc("files", "image", btn_name, "${ctx}");
			
			function DayFunc(){
				var c = $dp.cal;
				var todate = new Date(c.getP('y'),c.getP('M')-1,c.getP('d'));
				todate.setDate(todate.getDate()+365);
				$("#down_date").val(todate.format("yyyy-MM-dd")); 
			}
		</script>
	</body>

</html>