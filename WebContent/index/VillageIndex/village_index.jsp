<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>九个挑夫</title>
		<link href="${ctx}/styles/mui/css/mui.min.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/countryvillageMyIndex/css/global.css" />
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/countryvillageMyIndex/css/font-awesome.min.css" />
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/countryvillageMyIndex/css/common.css" />
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
						<!--村头像 -->
						<div class="top-pic">
							<a href="javascript:void(0)">
								<img :src="ctx + village_logo">
							</a>
						</div>
						<!-- 基本村信息 -->
						<div class="group-text">
							<h3>
								<a href="javascript:void(0)">{{villageInfo.village_name}}</a>
								<span class="auth" v-if="villageInfo.is_ren_zheng==1"><i class="fa fa-shield"></i> 村品牌认证</span>
							</h3>
							<div class="subtext">村民数量：<span>{{datas.villageMemberCount}}</span> 昨日新增：<span>+{{datas.yesterdayAddCount}}</span>
								<span class="add-grp" style="cursor: pointer;"><a @tap="applyAddVillage"><i class="fa fa-check-circle-o"></i>{{applyAdd}}</a></span></div>
						</div>
					</div>
					<div class="right-group">
						<div class="my-qr">
							<i v-if="village_qrcode == ''" class="fa fa-qrcode"></i>
							<!-- 村二维码 -->
							<div v-else class="show-qr" v-bind:style="{'display':village_qrcode != '' ? 'block':'none'}">
								<img :src="ctx + village_qrcode + '@s400x400'" />
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="mycity-wrap mianbox">
				<div class="mianleftbox">
					<div class="comm-box">
						<div class="xian-infobox">
							<div class="fl">
								<div class="top-pic">
									<a :href="'VillageIndex.do?method=myIndex&village_id='+villageInfo.id+'&member_id='+villageManageUser.id">
										<img v-if="villageManageUserLogo.indexOf('http://') == -1" :src="ctx + villageManageUserLogo"/>
										<img v-else :src="villageManageUserLogo"/>
									</a>
								</div>
								<div class="group-text">
									<h3><a href="javascript:void(0)">{{villageInfo.village_name}}</a></h3>
									<div class="subtext">{{villageManageUser.user_name}}</div>
								</div>
							</div>
							<div class="fr">
								<ul class="into-item-link">
									<li>
										<a @tap="cunQing"><i class="fa fa-flag-o"></i>
											<p>村情</p>
										</a>
									</li>
									<li>
										<a @tap="photoList"><i class="fa fa-map-marker"></i>
											<p>村貌</p>
										</a>
									</li>
									<li>
										<a @tap="memberList(villageInfo.id)"><i class="fa fa-list-ul"></i>
											<p>通讯录</p>
										</a>
									</li>
								</ul>
							</div>
						</div>
						<!--热点 -->
						<div class="hotnews">
							<div class="title">热点</div>
							<div class="sli-textbox">
								<ul class="slidemain">
									<li v-for="(item,index) in datas.newsInfoList" v-if='index<=5'>
										<a :href="'IndexNewsInfo.do?uuid='+item.uuid">{{item.title}}</a>
									</li>
								</ul>
								<div class="hd">
									<ul></ul>
								</div>
							</div>
						</div>
						<!--村资讯 -->
						<div class="news-pic-box">
							<h3 class="comm-title"><span class="more"><a :href="'IndexNewsInfo.do?method=list&mod_code='+datas.mod_code+'&par_code='+datas.par_code+'&link_id='+villageInfo.id">更多 ></a></span><span class="ht cur">村资讯</span></h3>
							<div class="xc-newslist">
								<ul>
									<li v-for="(item,index) in datas.newsInfoList" v-if='index<=3'>
										<a :href="'IndexNewsInfo.do?uuid='+item.uuid"><strong>{{item.title}}</strong>
											<p>{{item.summary}}</p>
										</a>
									</li>
								</ul>
							</div>
							<div class="xc-newspicbox">
								<ul class="slidemain">
									<li v-for="(item,index) in datas.newsImgList" v-if='index<=3'>
										<a :href="'IndexNewsInfo.do?uuid='+item.uuid"><img :src="ctx+item.image_path" alt=""></a>
									</li>
								</ul>
								<div class="hd">
									<a href="javascript:void(0);" class="prev"><i class="fa fa-angle-left"></i></a>
									<a href="javascript:void(0);" class="next"><i class="fa fa-angle-right"></i></a>
									<ul>
										<li v-for="(item,index) in datas.newsImgList" v-if='index<=3'>
											<a href="javascript:void(0);"></a>
										</li>
									</ul>
								</div>
							</div>
						</div>

						<div class="tj-box">
							<h3 class="comm-title">
								<a href="javascript:void(0)"><span :class="comm_or_dynamic_show == 1? 'ht cur': 'ht'" @tap="dcShow(1)">村民动态</span></a>
								<a href="javascript:void(0)"><span :class="comm_or_dynamic_show == 2? 'ht cur':'ht'" @tap="dcShow(2)">村民商品</span></a>
							</h3>
							<div class="user-datbox" v-for="(Dynamic,index) in DynamicList" v-if="Dynamic.type==comm_or_dynamic_show">
								<div class="user-group" style="width: 100%;">
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
												<a :href="ctx+DynamicImg.file_path+'@compress'" v-for="DynamicImg in Dynamic.map.imgList">
													<img :src="ctx + DynamicImg.file_path+'@s70x70'"/>
												</a>
											</div>
										</div>
									</div>
								</div>
<!-- 								<div class="user-bycun"> -->
<%-- 									<img v-if="null != Dynamic.map.village_logo" :src="ctx + Dynamic.map.village_logo" onerror="this.src='${ctx}/styles/imagesPublic/user_header.png'" /> --%>
<%-- 									<img v-else src="${ctx}/styles/imagesPublic/user_header.png" /> --%>
<!-- 									<p class="cun-name">来自于{{Dynamic.village_name}}</p> -->
<!-- 									<p class="goin-cun"> -->
<!-- 										<a :href="'VillageIndex.do?method=villageIndex&village_id='+Dynamic.village_id">进入该村</a> -->
<!-- 									</p> -->
<!-- 								</div> -->
								<div class="clearfix"></div>
								<!--回复-->
								<div class="commentbox">
									<div class="comment-tool">
										<div class="zan-user"><i class="fa fa-heart-o"></i>赞 <span>{{Dynamic.map.zanNameList}}</span>等{{Dynamic.map.zan_count}}人</div>
										<a :href="'VillageIndex.do?method=villageIndex&village_id='+Dynamic.village_id">
											<img src="${ctx}/m/styles/village/img/dingwei.png" style="margin-top: 6px; width: 17px; float: left;">
											<span style="color: #777;font-size: 15px;">{{Dynamic.village_name}}</span></a>
										<a v-if="datas.user!=''" @tap="goDianZan(Dynamic,index)" v-bind:title="Dynamic.map.is_zan ==1 ?'取消点赞':'点赞'">
											<i class="fa fa-heart-o" v-bind:style="{'color':Dynamic.map.is_zan ==1 ? '#e8601b':'#333'}"></i></a>
										<a v-else @tap="loginTip();" title="点赞"><i class="fa fa-heart-o"></i></a>

										<a v-if="datas.user!=''" href="javascript:void(0)" title="评论" @tap="showInput(index);"><i class="fa fa-comment-o"></i></a>
										<a v-else title="评论" @tap="loginTip();"><i class="fa fa-comment-o"></i></a>

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

						</div>
					</div>
				</div>
				<div class="siderightbox">
					<div class="comm-box">
						<div class="inbox">
							<a id="pub_dynamic">发布动态</a>
							<a id="pub_comm">发布商品</a>
						</div>
					</div>
					<!--我加入的村 -->
					<div class="comm-box mt15">
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
					<!--爱心扶贫 -->
					<div class="comm-box mt15">
						<h3 class="comm-title"><span class="ht cur">爱心扶贫</span></h3>
						<ul class="text-img-list headerlist">
							<li v-for="(item,index) in poorInfoList" v-if='index<=6'>
								<a :href="'VillageIndex.do?method=myIndex&village_id='+item.village_id+'&member_id='+item.user_id"><img :src="ctx+item.head_logo">
									<p title="item.real_name">{{item.real_name}}</p>
								</a>
							</li>
						</ul>
						<div class="list-more">
							<a @tap="poorListMore">...更多...</a>
						</div>
					</div>
					<!--与我相关 -->
					<div class="comm-box mt15">
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
							<input type="text" :placeholder="showPub?'分享新鲜事...':'描述一下宝贝的细节或故事'" class="comm-input" id="content" name="content" style="margin-top: 10px;padding: 6px;">
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
						<div class="inputwrap" style="float: left;width: 37%;margin-left: 100px;" v-show="!showPub">价格：
							<input type="text" id="comm_price" name="comm_price" class="comm-input my" placeholder="商品价格/元" style="text-align: center;">
						</div>
						<div class="inputwrap" style="width: 36%;" v-show="!showPub">库存：
							<input type="text" id="inventory" name="inventory" class="comm-input my" placeholder="库存" style="text-align: center;">
						</div>
						<div class="inputwrap" style="float: left;width: 37%;margin-left: 100px;" v-show="!showPub">上架时间：
						    <fmt:formatDate value="${af.map.up_date}" pattern="yyyy-MM-dd" var="_up_date" />
                            <input class="comm-input my" name="up_date" id="up_date" size="12" maxlength="20"  readonly="true" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:DayFunc})" style="cursor:pointer;text-align: center;width: 130px;" value="${_up_date}"/>
						</div>
						<div class="inputwrap" style="width: 36%;" v-show="!showPub">下架时间：
							<fmt:formatDate value="${af.map.down_date}" pattern="yyyy-MM-dd" var="_down_date" />
							<input class="comm-input my" name="down_date" id="down_date" size="12" maxlength="20"  readonly="true" onclick="WdatePicker()" style="cursor:pointer;text-align: center;width: 130px;" value="${_down_date}"/>
						</div>
						
						<div class="inputwrap">
							<input @click="btn" type="button" value="提交" class="inpusub">
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
			
			var village_id = "${af.map.village_id}";
			var vm = new Vue({
				el: '#app',
				data: {
					ctx: Common.api,
					DynamicList:new Array(),
					villageDynamicRecordList:new Array(),
					myJoinVillageList:new Array(),
					poorInfoList:new Array(),
					commClassList:"",
					banner: "styles/countryvillageMyIndex/images/ggx.jpg",
					villageInfo: '',
					applyVillageInfo: '',
					follow: '+关注',
					applyAdd: '申请加入',
					village_logo: 'styles/images/user.png',
					villageManageUser: '',
					villageManageUserLogo: 'styles/images/user.png',
					village_banner: "",
					village_qrcode: "",
					renzheng_logo: "m/styles/village/img/village_logo.png",
					datas: "",
					ui: "",
					inputNum: "",
					showPub:false,
					comm_or_dynamic_show:1,
					dynamicStartPage:0,
					AboutMeStartPage:0,
					myJoinVillageStartPage:0,
					poorListStartPage:0,
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
							route: '/VillageIndex.do?method=getVillageInfo&id=' + village_id,
							success: function(data) {
								if(data.code == 0) {
									mui.alert(data.msg);
									return false;
								} else if(data.code == 1) {
									vm.datas = data.datas;
									vm.villageInfo = data.datas.villageInfo;
									if(null != data.datas.villageInfo.village_logo) {
										vm.village_logo = data.datas.villageInfo.village_logo+'@s96x96';
									}
									if(null != data.datas.villageInfo.village_banner) {
										vm.banner = data.datas.villageInfo.village_banner;
									}
									if(null != data.datas.villageInfo.village_qrcode) {
										vm.village_qrcode = data.datas.villageInfo.village_qrcode;
									}
									vm.villageManageUser = data.datas.villageManageUser;
									if(null != data.datas.villageManageUser.user_logo) {
										vm.villageManageUserLogo = data.datas.villageManageUser.user_logo+'@s96x96';
									}
									if(null != data.datas.guanzhu_count && data.datas.guanzhu_count > 0) {
										vm.follow = "已关注";
									}
									if(null != data.datas.applyVillageInfo) {
										vm.applyVillageInfo = data.datas.applyVillageInfo;
										if(data.datas.applyVillageInfo.audit_state == 0) {
											vm.applyAdd = "待审核";
										} else if(data.datas.applyVillageInfo.audit_state == 1) {
											vm.applyAdd = "已加入";
										} else {
											vm.applyAdd = "重新申请";
										}
									}
									if(null != data.datas.user) {
										vm.ui = data.datas.user;
									}

								} else if(data.code == -2) {
									mui.toast(data.msg);
									setTimeout(function() {
										Common.goBack();
									}, 1000);

								}
							},
							error: function() {
								mui.alert('好像出错了哦~');
							}
						});
						
						//获取动态信息
						this.getDynamicListData(vm.dynamicStartPage);
						//我加入的村
						this.getMyJoinVillageList(vm.myJoinVillageStartPage);
						//爱心扶贫
						this.getPoorList(vm.poorListStartPage);
						//与我相关
						if(village_id!=null){
							this.getAboutMeData(vm.AboutMeStartPage)
						}
						
						//商品类型
						Common.getData({
							route: '/m/MVillageDynamic.do?method=getAjaxDataList',
							data:{
			       				village_id:'${af.map.village_id}',
			       				type:2,
			       			},
							success: function(data) {
								if(data.code == 0) {
									mui.toast(data.msg);
									return false;
								} else if(data.code == 1) {
									vm.commClassList = data.datas.commClassList;
									console.info(vm.commClassList)
								}
							},
							error: function() {
								mui.alert('好像出错了哦~');
							}
						});
					},
					
					dynamicMore:function() {
						this.getDynamicListData(vm.dynamicStartPage+1);
						vm.dynamicStartPage = vm.dynamicStartPage+1;
					},
					
					aboutMeMore:function() {
						this.getAboutMeData(vm.AboutMeStartPage+1)
						vm.AboutMeStartPage = vm.AboutMeStartPage+1;
					},
					myJoinVillageMore:function() {
						this.getMyJoinVillageList(vm.myJoinVillageStartPage+1);
						vm.myJoinVillageStartPage = vm.myJoinVillageStartPage+1;
					},
					
					poorListMore:function() {
						this.getPoorList(vm.poorListStartPage+1);
						vm.poorListStartPage = vm.poorListStartPage+1;
					},
					showInput: function(index) {
						vm.inputNum = index;
					},
					noInventory:function() {
						mui.toast('该商品已售罄！');
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
					clickFiles:function(){
			    		$("#files").trigger("click");
			    	},
			    	dcShow:function(type){
			    		vm.comm_or_dynamic_show = type;
			    	},
			    	memberList:function(id){
						$.dialog({
							title:  "村站",
							width:  500,
							height: 800,
					        lock:true ,
							content:"url:${ctx}/VillageIndex.do?method=memberList&village_id="+id
						});
					},
					applyAddVillage: function() {
						if(vm.ui == null || vm.ui == ""){
							this.loginTip();
						}else{
						Common.getData({
							route: '/m/MVillage.do?method=applyAdd&id=' + vm.villageInfo.id,
							success: function(data) {
								if(data.code == -3) {
									var url = vm.ctx + "register.shtml";
									window.location.href = url;
								}
								else if(data.code == -2) {
									Common.confirm(data.msg, ["取消", "去认证"], function() {}, function() {
										var url = vm.ctx + "/manager/customer/MySecurityCenter.do?par_id=1100620000&mod_id=1100620100";
										window.location.href=url;
									});
								}
								else if(data.code == 1) {
									mui.toast(data.msg);
									vm.applyAdd = "待审核";
								}
								else if(data.code == 0) {
									mui.toast(data.msg);
								}
							},
							error: function() {
								mui.alert('好像出错了哦~');
							}
						});
						}
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
					//获取动态信息
					getDynamicListData: function(startPage){
						Common.getData({
							route: '/VillageIndex.do?method=getVillageDynamicList',
							data: {
								village_id: village_id,
								startPage:startPage,
								comment_show_count:5,
							},
							success: function(data) {
								if(data.villageDynamicList != null && data.villageDynamicList !=""){
									vm.DynamicList = vm.DynamicList.concat(data.villageDynamicList);
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
					//爱心扶贫
					getPoorList: function(startPage){
						Common.getData({
							route: '/VillageIndex.do?method=villagePoorList',
							data: {
								startPage: startPage,
								village_id: village_id,
							},
							success: function(data) {
								if(data.poorInfoList != null && data.poorInfoList !=""){
									vm.poorInfoList = vm.poorInfoList.concat(data.poorInfoList);
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
							route: 'm/MVillage.do?method=getAjaxDataRecord&id='+village_id,
							data: {
								startPage: startPage,
								newPageSize:5
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
					photoList:function(){
						$.dialog({
							title:  "村貌",
							width:  1200,
							height: 800,
					        lock:true ,
							content:"url:${ctx}/VillageIndex.do?method=villagePhoto&village_id="+vm.villageInfo.id
						});
					},
					
					cunQing:function(){
						$.dialog({
							title:  "村情",
							width:  500,
							height: 800,
					        lock:true ,
							content:"url:${ctx}/VillageIndex.do?method=cunQing&village_id="+vm.villageInfo.id
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