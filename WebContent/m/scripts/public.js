
Vue.filter('formatMoney', function(value) {
	if(null != value){
		return parseFloat(value).toFixed(2);
	}else{
		return 0;
	}
	
});
Vue.filter('formatMoneyChina', function(value) {
	if(null != value){
		return "¥ " + parseFloat(value).toFixed(2) +" 元";
	}else{
		return "¥0元";
	}
});
Vue.filter('formatDate', function(value) {
	return Common.formatDate(value, 'yyyy-MM-dd HH:mm');
});
Vue.filter('formatDateYmd', function(value) {
	return Common.formatDate(value, 'yyyy-MM-dd');
});
Vue.filter('formatDateYmdHms', function(value) {
	return Common.formatDate(value, 'yyyy-MM-dd HH:mm:ss');
});
function setCookieByPname(p_name){
	  $.post("CsAjax.do?method=getPIdexFromPname&p_name="+p_name,{},function(datas){
			if(datas.ret == "1"){
				var city_name = datas.p_name;
				if(city_name){
					city_name = city_name.replace(/市/,"");
					$("#city_name").text(city_name);
				}
			}
		});
}

function getCookiePindex(){
	  $.post("CsAjax.do?method=getCookiePindex",{},function(datas){
			if(datas.ret == "1"){
				var city_name = datas.p_name;
				if(city_name){
					city_name = city_name.replace(/市/,"");
					$("#city_name").text(city_name);
					$("#city_name_footer").text(city_name);
				}
			}
		});
}

function canSearch(){
	if($("#searchContent").is(":hidden")){
		$("#searchContent").show();
		$('.pop-shade').show().height($('.pop-shade').height() + $('body').height());
	}else{
		$("#searchContent").hide();
		$('.pop-shade').hide().css('height', '100%');
	}
}


function login(msg){
	if(null == msg){
		msg = "您还未登录，请先登录系统！";
	}
	Common.confirm(msg,["取消","去登陆"],function(){
	},function(){
		var url = vm.ctx + "m/MIndexLogin.do";
	  	goUrl(url);
	});
}


function canRefresh(refreshContent){
	
	if(null == refreshContent || "" == refreshContent){
		refreshContent = "body";
	}
	// dropload
    $('' + refreshContent).dropload({
        scrollArea : window,
        autoLoad : false,     
        domUp : {// 上方DOM                                                       
            domClass   : 'dropload-up',
            domRefresh : '<div class="dropload-refresh"><i class="fa fa-long-arrow-down"></i>&nbsp;下拉刷新</div>',
            domUpdate  : '<div class="dropload-update"><i class="fa fa-long-arrow-up"></i>&nbsp;释放更新</div>',
            domLoad    : '<div class="dropload-load"><span class="loading"></span>刷新中...</div>'
        },
        loadUpFn : function(me){
           // 为了测试，延迟1秒加载
           setTimeout(function(){
               me.resetload();
               me.unlock();
               me.noData(false);
               Common.loading();
	       		window.setTimeout(function () {
	       			window.location.reload();
	       		}, 1000);
           },500);
        },
        loadDownFn:"",
        threshold : 50
    });
}


function goUrl(url,falg){	
	if(url){
		Common.loading();
		window.setTimeout(function () {
			Common.hide();
			location.href = url;
		}, 1000);
	}
}

function setTopBtnUrl(url){	
	$("#titleSideName").click(function(){
		Common.loading();
		window.setTimeout(function () {
			Common.hide();
			location.href = url;
		}, 1000);
	});
}

function submitForm(f){
	if(f){
		Common.loading();
		window.setTimeout(function () {
			f.submit();
		}, 1000);
	}
}
function updateCookiesUrl(url){
	var parId_cookie = $.cookie("parId_cookie");
 	var parId;
 	if(parId_cookie){
 		var spl = parId_cookie.split(",");
		parId = spl[0];
 	}
 	if(parId){
		var parId_cookie_new = parId + "," + url;
 		if ($.isFunction($.cookie)) $.cookie("parId_cookie", parId_cookie_new, { path: '/' });
		location.href = url;
	}
}

function updateVerCode(){
	$("#veri_img").attr("src", app_path +  "/images/VerificationCode.jpg?t=" + new Date().getTime());
}

function pageBack() {
    var a = window.location.href;
	window.history.back();
	window.location.load(window.location.href);
}

function toLogin(url){
	 mui.toast('很抱歉！您还没有登录，请先登录！');
	 setTimeout(function() {
			 location.href = url +'?returnUrl=' + escape(location.href)},2000);
	 return true;
}

function toTop(){	
	$("html,body").animate({scrollTop :0}, 800);	
	return false;
}

var testEmail = /^[A-Za-z0-9](([_\.\+\-]?[a-zA-Z0-9]+)*)@([A-Za-z0-9]+)(([\.\-]?[a-zA-Z0-9]+)*)\.([A-Za-z]{2,})$/;
var testMobile = /^(13[0-9]|14[0-9]|15[0|1|2|3|5|6|7|8|9]|17[0-9]|18[0-9])\d{8}$/;
var testQQ = /^[1-9]\d{4,11}$/;
var testUserName = /^\w{4,16}$/;
function checkEmail(e) {
    if (!testEmail.test(e)) {
        return false
    }
    return true
}
function checkMobile(e) {
    if (!testMobile.test(e)) {
        return false
    }
    return true
}
function checkQQ(e) {
    if (!testQQ.test(e)) {
        return false
    }
    return true
}
function checkUserName(e) {
	if (!testUserName.test(e)) {
		return false
	}
	return true
}

function checkStrong(t) {
    for (Modes = 0, i = 0; i < t.length; i++) Modes |= CharMode(t.charCodeAt(i));
    return bitTotal(Modes)
}
function  CharMode(t) {
    return t >= 48 && 57 >= t ? 1 : t >= 65 && 90 >= t ? 2 : t >= 97 && 122 >= t ? 4 : 8
}
function bitTotal(t) {
    for (modes = 0, i = 0; i < 4; i++) 1 & t && modes++,
    t >>= 1;
    return modes
}

function registerSubVal(){
  var t = $("#mobile").val(),
  e = $.trim($("#verifycode").val()),
  i = $.trim($("#password_hide").val()),
  o = $.trim($("#_password").val());
  u = $.trim($("#user_name").val());
  if("" == u){
	  mui.toast("请输入用户名");
	  return false;
  }
  if(!checkUserName(u)){
	  mui.toast("用户名格式错误");
	  return false;
  }
  var flag= "" == t ? (mui.toast("请输入手机号码"), !1) : checkMobile(t) ? "" == e ? (mui.toast("手机验证码不能为空"), !1) : "" == i ? (mui.toast("密码不能为空"), !1) : i.length < 6 ? (mui.toast("密码长度不能小于6位"), !1) :  "" == o ? (mui.toast("请填写确认登录密码"), !1) : o != i ? (mui.toast("两次输入密码不一致，请确认"), !1) : void 0 : (mui.toast("手机号码格式错误"), !1);
  if(typeof(flag) == "undefined"){
	  flag = true;
  }
  return flag;		  
}
function registerSubValNoUserName(){
	var t = $("#mobile").val(),
	e = $.trim($("#verifycode").val()),
	i = $.trim($("#password_hide").val());
	//o = $.trim($("#_password").val());
	var flag= "" == t ? (mui.toast("请输入手机号码"), !1) : checkMobile(t) ? "" == e ? (mui.toast("手机验证码不能为空"), !1) : "" == i ? (mui.toast("密码不能为空"), !1) : i.length < 6 ? (mui.toast("密码长度不能小于6位"), !1) : void 0 : (mui.toast("手机号码格式错误"), !1);
	if(typeof(flag) == "undefined"){
		flag = true;
	}
	return flag;		  
}


function error_msg_ani(e) {
    $("." + e).addClass("entryamt");
    setTimeout("$('." + e + "').removeClass('entryamt')", 500)
}

//拉手网的弹出框样式 两种样式 必须配合css 有点鸡肋
// msg_tit_common("请输入手机号码", "entry", 1, 1, 1);
// msg_tit_common("请输入手机号码", "entry", 1, 1);
function msg_tit_common(e, t, r, n, a) {
    if (!a) {
        if (e) {
            $("." + t).html(e);
            $("." + t).show();
            if (n) {
                error_msg_ani(t)
            }
            if (r) {
                $("body,html").animate({
                    scrollTop: 0
                },
                500)
            }
        } else {
            $("." + t).html("");
            $("." + t).hide()
        }
    } else {
        if (e) {
            var i = '<div class="tips-prompt"><span class="name"></span><span class="bg"></span></div>';
            if ($("div.tips-prompt").length == 0) {
                $("body").append(i)
            }
            var o = $("div.tips-prompt");
            o.find("span.name").text(e);
            $(".tips-prompt").fadeIn();
            setTimeout(function() {
                $(".tips-prompt").fadeOut()
            },
            2e3)
        }
    }
}


function confirmDelete(msg, page, queryString) {
	msg  = msg  || "\u786e\u5b9a\u5220\u9664\u8fd9\u6761\u4fe1\u606f\u5417\uff1f";
	page = page || "?";
	page = page.indexOf("?") != -1 ? page : (page + "?");
	Common.confirm(msg,["确定","取消"],function(){
		Common.loading();
		location.href = page + "method=delete&" + queryString;
	 },function(){
	});	
}

function updateState(action, mtd, id, state, obj) {
	var tip = "确定执行该操作吗？";
	var tip1 = "";
	var isshixiao = $("#order_state_" + id).attr("date-isshixiao");
	var order_type = $("#order_state_" + id).attr("data-order-type");
	if(isshixiao == 1 && order_type == 10){// 虚拟订单才扣手续费
		tip1 = "<br/>订单已失效，将扣除3%的手续费！";
	}
	if(state == -10){
		tip = "确定要取消订单吗？" + tip1;
	}
	if(state == -20){
		tip = "确定要退款吗？退款金额将返还到您的账户余额中！" + tip1;
	}
	if(state == 50){
		tip = "确认收货7天后发放订单奖励，且确认后不能退货只能换货！确定要确认收货吗？" + tip1;
	}
	Common.confirm(tip,["确定","取消"],function(){
		Common.loading();
		$.ajax({
			type : "POST",
			cache : false,
			url : action,
			data : "method=" + mtd + "&id=" + id + "&state=" + state,
			dataType : "json",
			error : function(request, settings) {
				Common.hide();
				mui.toast("系统繁忙，请稍后重试！");
				 window.setTimeout(function () {
					 window.location.reload();
				 }, 1000);
			},
			success : function(data) {
				if (data.ret == "1") {
					 Common.hide();
					
					
					 //确认收货后，提示是否去评价，取消则刷新页面
					 if(state == 50){
						 window.setTimeout(function(){
							 
							 Common.confirm(data.msg+"是否立即评价?",["确定","取消"],function(){
								 Common.loading();
								location.href=app_path + "/m/MMyComment.do?method=chooseList&id="+id;
								},function(){
									//系统默认好评
									$.ajax({
										type: "POST",
										url: app_path + "/m/MMyOrder.do?method=DefaultComment",
										data: 'id=' + id,
										dataType: "json",
										error: function(request, settings) {},
										success: function(datas) {
											if(datas.ret == 1){
												Common.loading();
												window.setTimeout(function () {
													window.location.reload();
												}, 1000);
											}else{
												 Common.hide();
												mui.toast(data.msg);
											}
										}
									});
//									 window.location.reload();
									
								});
							},1000); 
						
					 };
					 
					 if(state != 50){
						 mui.toast(data.msg);
						 window.setTimeout(function () {
							 window.location.reload();
						 }, 1500);
					 }
				} else {
					 Common.hide();
					mui.toast(data.msg);
					 window.setTimeout(function () {
						 window.location.reload();
					 }, 1000);
				}
			}
		});
	 },function(){
	});	
}

function delayShouhuo(action,id,obj) {
	
	var tip = "延迟收货将再延后3天，确定延迟收货吗？";
	Common.confirm(tip,["确定","取消"],function(){
		Common.loading();
		$.ajax({
			type : "POST",
			cache : false,
			url : action,
			data : "method=delayShouhuo&id=" + id,
			dataType : "json",
			error : function(request, settings) {
				mui.toast("系统繁忙，请稍后重试！");
			},
			success : function(data) {
				Common.hide();
				if (data.ret == "1") {
					mui.toast(data.msg);
					 window.setTimeout(function () {
						 window.location.reload();
					 }, 1500);
				} else {
					mui.toast(data.msg);
				}
			}
		});
	 },function(){
	});	
}


function jugdeIsApp(){
	var is_app = $.cookie("is_app");
 	if(is_app){
 		$("#footer").hide();
 	}else{
 		$("#footer").show();
 	}
 
}

function getLocationServiceInfo() {
	Common.loading("定位中");
	$("#city_name").text("定位中");
	var geolocation = new BMap.Geolocation();  
    geolocation.getCurrentPosition(function (r) {  
        if (this.getStatus() == BMAP_STATUS_SUCCESS) {  
            var mk = new BMap.Marker(r.point);  
            currentLat = r.point.lat;  
            currentLon = r.point.lng;  
            var pt = new BMap.Point(currentLon, currentLat);  
            var geoc = new BMap.Geocoder();  
            geoc.getLocation(pt, function (rs) {  
                var addComp = rs.addressComponents;
                if(addComp.city){
    				var p_name = addComp.city;  
    				$.post("CsAjax.do?method=ajaxGetPindex",{p_name:p_name},function(datas){
    					Common.hide();
    					if(datas.ret == "1"){
    						var p_name = datas.p_name;
    						var p_index = datas.p_index;
    						$("#city_name").text(p_name);
    						localStorage.setItem('p_index_service_info', p_index);
    						localStorage.setItem('p_name_service_info',p_name);
    						//这个地方需要去改变
    						Common.getData({
    				   			route: 'm/MServiceCenterInfo.do?method=getAjaxDataList&p_index_like=' + p_index,
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
    				});
    			}
            });
        }  
    });
}
function getLocationCityService() {
	Common.loading("定位中");
	var geolocation = new BMap.Geolocation();  
    geolocation.getCurrentPosition(function (r) {
        if (this.getStatus() == BMAP_STATUS_SUCCESS) {  
            var mk = new BMap.Marker(r.point);  
            currentLat = r.point.lat;  
            currentLon = r.point.lng;  
            var pt = new BMap.Point(currentLon, currentLat);  
            var geoc = new BMap.Geocoder();  
            geoc.getLocation(pt, function (rs) {  
                var addComp = rs.addressComponents;
                if(addComp.city){
    				var p_name = addComp.city;  
    				$.post("CsAjax.do?method=ajaxGetPindex",{p_name:p_name},function(datas){
    					Common.hide();
    					if(datas.ret == "1"){
    						var p_name = datas.p_name;
    						var p_index = datas.p_index;
    						$("#city_name").text(p_name);
    						localStorage.setItem('p_index_service_info', p_index);
    						localStorage.setItem('p_name_service_info',p_name);
    						//这个地方需要去改变
    						Common.getData({
    				   			route: 'm/MCityCenter.do?method=getAjaxData&p_index=' + p_index,
    				   			success: function(data) {
    				   				if(data.code == 1){
    				   					vm.datas = data.datas;
    				   					if(null != data.datas.manageUser) {
    										vm.manageUser = data.datas.manageUser;
    										vm.p_index = data.datas.manageUser.p_index;
    									}
    									if(null != data.datas.newsInfoList) {
    										vm.newsInfoList = data.datas.newsInfoList;
    									}
    									if(null != data.datas.banner){
    										vm.banner = data.datas.banner;
    									}
    									if(null != data.datas.p_index_list) {
    										vm.p_index_list = data.datas.p_index_list;
    									}
    									if(null != p_name) {
    										$("#city_name").text(p_name.substring(0, 3));
    										vm.p_name = p_name.substring(0, 3);
    									}
    									vm.entity = data.datas.entity;
    				   				}
    				   			},
    				   			error: function() {
    				   				mui.alert('好像出错了哦~');
    				   			}
    				   		});
    					}
    				});
    			}
            });
        }  
    },function(e) {
		localStorage.setItem('p_index_service_info', "340100");
		localStorage.setItem('p_name_service_info',"合肥");
		Common.getData({
   			route: 'm/MCityCenter.do?method=getAjaxData&p_index=' + p_index,
   			success: function(data) {
   				if(data.code == 1){
   					vm.datas = data.datas;
   					if(null != data.datas.manageUser) {
						vm.manageUser = data.datas.manageUser;
						vm.p_index = data.datas.manageUser.p_index;
					}
					if(null != data.datas.newsInfoList) {
						vm.newsInfoList = data.datas.newsInfoList;
					}
					if(null != data.datas.banner){
						vm.banner = data.datas.banner;
					}
					if(null != data.datas.p_index_list) {
						vm.p_index_list = data.datas.p_index_list;
					}
					if(null != p_name) {
						$("#city_name").text(p_name.substring(0, 3));
						vm.p_name = p_name.substring(0, 3);
					}
					vm.entity = data.datas.entity;
   				}
   			},
   			error: function() {
   				mui.alert('好像出错了哦~');
   			}
   		});
	});
}

function getPindexFromIp(ctx, type){
	$.post("CsAjax.do?method=getPindexFromIp",{},function(datas){
		if(datas.ret == "1"){
			var p_name = datas.p_name;
			var p_index = datas.p_index;
			$("#local_p_index").text(p_name);
			var url = ctx + "/ChangeCity.do?method=selectChangeCity&p_index=" + p_index;;
			if (type == "m") {
				url = ctx + "/m/MChangeCity.do?method=selectChangeCity&p_index=" + p_index;
				$("#local_p_index").click(function(){
					Common.loading();
					window.setTimeout(function () {
						location.href = url;
					}, 1000);
				});
			} else {
				$("#local_p_index").attr("href", url);
			}
			
		}
	});
}
//退换货原因
function showTuiHuoReasone(type){
	var txt="";
	if(type == 11630){
		txt="质量问题"
	}
	if(type == 11631){
		txt="实物与商品描述不符"
	}
	if(type == 15711){
		txt="假冒品牌"
	}
	if(type == 83950){
		txt="商品过期"
	}
	if(type == 83952){
		txt="其它"
	}
	if(type == 83958){
		txt="七天无理由退货"
	}
	if(type == 83959){
		txt="卖家发错货"
	}
     
	return document.write(txt);
}

//退换货原因
function showTuiHuoCause(type){
	var txt="";
	if(type == 1){
		txt="退货退款"
	}
	if(type == 2){
		txt="换货"
	}
	if(type == 3){
		txt="仅退款"
	}
	if(type == 4){
		txt="未发货退款"
	}
	return document.write(txt);
}

function showOrderState(order_state, pay_type, order_type){
	document.write(getOrderStateString(order_state, pay_type, order_type));
}

function getOrderStateString(order_state, pay_type, order_type){
	var txt1 = "",br = "",txt2 = "";
	if (order_type == 7 || order_type == 10) {//普通订单
		if (order_state == -20) {
			txt1 = "<span class=''>已退款</span>";
		}
		if (order_state == -10) {
			txt1 = "<span class=''>已取消</span>";
		}
		if (order_state == 0) {
			txt1 = "<span class=''>已预订(待付款)</span>";
		}
		if (order_state == 10) {
			txt1 = "<span class=''>待发货(已付款)</span>";
		}
		if (order_state == 15) {
			txt1 = "<span class=''>退款/换货申请中</span>";
		}
		if (order_state == 20) {
			txt1 = "<span class=''>已发货(待收货)</span>";
		}
		if (order_state == 30) {
			txt1 = "<span>已到货</span>";
		}
		if (order_state == 40) {
			txt1 = "<span class=''>已收货(已付款)</span>";
		}
		if (order_state == 50) {
			txt1 = "<div class='signet'></div>";
		}
		if (order_state == 90) {
			txt1 = "<span class=''>已关闭</span>";
		}
	}
	if (order_type == 20) {//付费升级订单
		if (order_state == 0) {
			txt1 = "<span class=''>已预订</span>";
		}
		if (order_state == 50) {
			txt1 = "<span class=''>交易成功</span>";
		}
	}
	if (order_state == -90) {
		txt1 = "<span class='tip-danger'>异常订单</span>";
		br = "";
		txt2 = "";
	}
	
	return (txt1 + br + txt2);
}


function orderInfoAddCart(id){
    $.ajax({
        type: "POST",
        url: app_path + "/CsAjax.do",
        async:false,
        data: "method=orderInfoAddCart&id=" + id,
        dataType: "json",
        error: function(request, settings) {}, 
        success: function(data) {
        	if(data.ret == -1){
        		mui.toast(data.msg);
        	}
            if (data.ret == 1) {
            	Common.hide();
            	if(data.order_type == 10){
            		$("#s-decision-wrapper").removeClass("show");
	                $("#s-decision-wrapper-cover").hide();
	                Common.confirm("加入购物车成功！",["去购物车","再逛逛"],function(){
	                    location.href=app_path + "/m/MMyCartInfo.do";
	                },function(){
	                });  
            	}else{
            		location.href= app_path + "/m/MMyNoCartInfo.do?comm_id=" + data.comm_id + "&comm_tczh_id=" + data.comm_tczh_id;
            	}
            }else{
            	
            }
        }
    });
}
function auditTuiKuanOrder(id,state){
    $.ajax({
        type: "POST",
        url: app_path + "/m/MAuditTuiKuan.do",
        async:false,
        data: "method=auditTuiKuanOrder&id=" + id+"&audit_state="+state,
        dataType: "json",
        error: function(request, settings) {}, 
        success: function(data) {
            if (data.ret == 1) {
            	mui.toast(data.msg);
            	setTimeout(function(){location.href=app_path + "/m/MAuditTuiKuan.do";}, 1000);
                 
            }else{
            	mui.toast(data.msg);
            }
        }
    });
}


function orderFh(id) { 
	var title = "订单发货";
	var url = app_path + "/m/MMyOrderEntp.do?method=orderFh&order_id=" + id;
	$.dialog({
		title:  title,
		zIndex:22,
	    content:"url:"+ encodeURI(url)
	}).max();
}

function orderConfirm(id) { 
	var title = "订单确认";
	var url = app_path + "/m/MMyOrderEntp.do?method=orderConfirm&order_id=" + id ;
	$.dialog({
		title:  title,
	    content:"url:"+ encodeURI(url)
	}).max();
}
function orderModifyPrice(id) { 
	var title = "订单修改价格";
	var url = app_path + "/m/MMyOrderModify.do?method=orderModifyPrice&order_id=" + id ;
	$.dialog({
		title:  title,
		content:"url:"+ encodeURI(url)
	}).max();
}

function lazyload(flag){
	$("img.lazy_"+ flag).lazyload({
	    effect : "fadeIn"
	});
}


function check_password_regx(checkid){
 var b = check_pwd1("" + checkid);
 switch (b) {
 case 0:
     return true;
 case 1:
     mui.toast("密码不能为空");
     break;
 case 2:
     mui.toast("密码应为6-20位字符");
     break;
 case 3:
     mui.toast("密码应为6-20位字符");
     break;
 case 4:
     mui.toast("密码中不允许有空格");
     break;
 case 5:
     mui.toast("密码不能全为数字");
     break;
 case 6:
     mui.toast("密码不能全为字母，请包含至少1个数字或符号 ");
     break;
 case 7:
     mui.toast("密码不能全为符号");
     break;
 case 8:
     mui.toast("密码不能全为相同字符或数字");
     break;
 default:
     mui.toast("6-20个大小写英文字母、符号或数字的组合")
 }
 return false;
}


function check_pwd1(f) {
var d = $("#" + f).val();
if (d == "") {
    return 1
}
if (d.length > 20) {
    return 2
}
if (d.length < 6) {
    return 3
}
var g = /\s+/;
if (g.test(d)) {
    return 4
}
var a = /^[0-9]+$/;
if (a.test(d)) {
    return 5
}
var b = /^[a-zA-Z]+$/;
if (b.test(d)) {
    return 6
}
var e = /^[^0-9A-Za-z]+$/;
if (e.test(d)) {
    return 7
}
if (isSameWord(d)) {
    return 8
}
var c = "^[\\da-zA-Z\\.\\,\\`\\~\\!\\@\\#\\$\\%\\\\^\\&\\*\\(\\)\\-\\_\\=\\+\\[\\{\\]\\}\\\\|\\;\\:\\'\\'\\\"\\\"\\<\\>\\/\\?]+$";
var h = new RegExp(c);
if (!h.test(d)) {
    return 10
}
return 0
}

function isSameWord(b) {
 var d;
 if (b != null && b != "") {
     d = b.charCodeAt(0);
     d = "\\" + d.toString(8);
     var c = "[" + d + "]{" + (b.length) + "}";
     var a = new RegExp(c);
     return a.test(b)
 }
 return true
}

