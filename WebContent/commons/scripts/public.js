
function goUrl(url, par_id){
	var parId_cookie = $.cookie("parId_cookie");
 	var parId;
 	if(parId_cookie){
 		var spl = parId_cookie.split(",");
		parId = spl[0];
 	}
 	if(null != par_id || "" != par_id){
		parId = par_id;
		var parId_cookie_new = parId + "," + url;
 		if ($.isFunction($.cookie)) $.cookie("parId_cookie", parId_cookie_new, { path: '/' });
		location.href = url;
	}
}
function goUrlForIndex(ctx, url, par_id){
		var parId_cookie_new = par_id + "," + url;
		if ($.isFunction($.cookie)) $.cookie("parId_cookie", parId_cookie_new, { path: '/' });
		location.href= ctx + "/manager/customer/index.shtml";
}
function goUrlForIndexManager(ctx, url,parParId,parId,modId){
	var parParId_parId_sonId_cookie = parParId +"," + parId +"," + modId + "," + url;
	if ($.isFunction($.cookie)) $.cookie("parParId_parId_sonId_cookie", parParId_parId_sonId_cookie, { path: '/' });
	location.href= ctx + "/manager/customer/index.shtml";
}

function addFavorite(sURL, sTitle) { 
    try { 
        window.external.addFavorite(sURL, sTitle); 
    } catch (e) { 
        try { 
            window.sidebar.addPanel(sTitle, sURL, ""); 
        } catch (e) { 
            alert("加入收藏失败，请使用Ctrl+D进行添加"); 
        } 
    } 
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

function setHome(url) {
	try {
		document.body.style.behavior = 'url(#default#homepage)';
		document.body.setHomePage(url);
	} catch (e) {
		if (window.netscape) {
			try {
				netscape.security.PrivilegeManager
						.enablePrivilege("UniversalXPConnect");
			} catch (e) {
				alert("此操作被浏览器拒绝！\n请在浏览器地址栏输入“about:config”并回车\n然后将[signed.applets.codebase_principal_support]设置为'true'");
			}
			var prefs = Components.classes['@mozilla.org/preferences-service;1']
					.getService(Components.interfaces.nsIPrefBranch);
			prefs.setCharPref('browser.startup.homepage', url);
		}
	}
}


function showOrderState(order_state, pay_type, order_type, sep){
	console.info(order_state)
	var sepa = "<br/>";
	if(undefined != sep){
		sepa = sep;
	}
	document.write(getOrderStateString(order_state, pay_type, order_type,sepa));
}
//退换货原因
function showTuiHuoCause(type){
	var txt="";
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
//退换货原因
function showOrderTuihuoState(state){
	var txt="";
	if(state == 0){
		txt="待审核";
	}
	if(state == 1){
		txt="<span style=\"color: green;\">平台审核通过</span>";
	}
	if(state == 2){
		txt="<span style=\"color: green;\">商家审核通过</span>";
	}
	if(state == 3){
		txt="平台待审核";
	}
	if(state == -1){
		txt="<span style=\"color: red;\">平台审核不通过</span>";
	}
	if(state == -2){
		txt="<span style=\"color: red;\">商家审核不通过</span>";
	}
	return document.write(txt);
}
function getOrderStateString(order_state, pay_type, order_type,sepa){
	var txt1 = "",br = "",txt2 = "";
	if (order_state == -20) {
		txt1 = "<span>已退款</span>";
	}
	if (order_state == -10) {
		txt1 = "已取消";
	}
	if (order_state == 0) {
		txt1 = "已预订";
		br = sepa;
		txt2 = "(<span class=\"tip-danger\">未支付</span>)";
	}
	if (order_state == 10) {
		txt1 = "待发货";
		br = sepa;
//		txt2 = "(<span class=\"tip-success\">已付款</span>)";
	}
	if (order_state == 15) {
		txt1 = "<span>退款/换货申请中</span>";
	}
	if (order_state == 20) {
		txt1 = "已发货";
		br = sepa;
		txt2 = "(<span class=\"tip-danger\">待收货</span>)";
	}
	if (order_state == 30) {
		txt1 = "已到货";
	}
	if (order_state == 40) {
		txt1 = "已收货";
	}
	if (order_state == 50) {
		txt1 = "已完成";
		br = sepa;
		txt2 = "(<span class=\"tip-success\">交易成功</span>)";
	}
	if (order_state == 90) {
		txt1 = "已关闭";
	}
	
	if (order_state == -90) {
		txt1 = "<span class='tip-danger'>异常订单</span>";
		br = sepa;
		txt2 = "(<span class=\"tip-danger\">核查中</span>)";
	}
	
	return (txt1 + br + txt2);
}

function showOrderStateForMobile(order_state,pay_type){
	var txt1 = "",br = "",txt2 = "";
	if (order_state == -10) {
		txt1 = "已取消";
	}
	if (order_state == 0) {
		txt1 = "已预订";
		txt2 = "(<span class=\"label label-danger\">未支付</span>)";
	}
	if (order_state == 10) {
		if (pay_type == 0) {
			txt1 = "等待发货";
			txt2 = "(<span>货到付款</span>)";
		}
		if (pay_type == 1 || pay_type == 2 ||pay_type == 3) {
			txt1 = "待发货";
			txt2 = "(<span class=\"tip-danger\">已付款</span>)";
		}
	}
	if (order_state == 20) {
		txt1 = "已发货";
		txt2 = "(<span class=\"label label-danger\">待收货</span>)";
	}
	if (order_state == 30) {
		txt1 = "已到货";
	}
	if (order_state == 40) {
		txt1 = "已收货";
		txt2 = "(<span class=\"label label-danger\">已付款</span>)";
	}
	if (order_state == 50) {
		txt1 = "已收货";
		txt2 = "(<span class=\"tip-danger\">交易成功</span>)";
	}
	if (order_state == 90) {
		txt1 = "已关闭";
	}
	if (order_state == -90) {
		txt1 = "<span class='tip-danger'>异常订单</span>";
		txt2 = "";
	}
	
	document.write(txt1 + txt2);
}

function getPindexFromIp(ctx){
	$.post("CsAjax.do?method=getPindexFromIp",{},function(datas){
		if(datas.ret == "1"){
			var p_name = datas.p_name;
			var p_index = datas.p_index;
			$("#local_p_index").text(p_name);
			var url = ctx + "/ChangeCity.do?method=selectChangeCity&p_index=" + p_index;;
			$("#local_p_index").attr("href", url);
			
		}
	});
}

function myConfirm(tip, submit){ 
	$.jBox.confirm(tip, "系统提示", submit, { buttons: { '确定': true, '取消': false} });
}
function selectHasAttr(cls_id,ids,comm_id){
	var url = "CommInfo.do?method=selectHasAttr&ids="+ ids +"&cls_id="+ cls_id +"&comm_id="+ comm_id +"&t=" + Math.random();
	$.dialog({
		title:  "选择已存在规格",
		width:  450,
		height: 400,
        lock:true ,
        zIndex:999,
		content:"url:"+url
	});
}
function parentGoUrl(goUrl){
	if(goUrl){
		location.href=goUrl;
	}else{
		window.location.reload();
	}
}

function check_password_regx(){
	 var b = check_pwd1("new_password");
    switch (b) {
    case 0:
        return true;
    case 1:
        alert("密码不能为空");
        break;
    case 2:
        alert("密码应为6-20位字符");
        break;
    case 3:
        alert("密码应为6-20位字符");
        break;
    case 4:
        alert("密码中不允许有空格");
        break;
    case 5:
        alert("密码不能全为数字");
        break;
    case 6:
        alert("密码不能全为字母，请包含至少1个数字或符号 ");
        break;
    case 7:
        alert("密码不能全为符号");
        break;
    case 8:
        alert("密码不能全为相同字符或数字");
        break;
    default:
        alert("6-20个大小写英文字母、符号或数字的组合")
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

