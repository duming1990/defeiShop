<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${app_name}</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Expires" content="-1">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Pragma" content="no-cache">
<jsp:include page="../_public_in_head.jsp" flush="true" />
<link href="${ctx}/m/styles/css/my/my-v1.css?v20170105" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/commons/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" href="${ctx}/m/scripts/dropload/css/dropload.css" />
<link rel="stylesheet" href="${ctx}/styles/index/css/xiaoguo.css" />
<style type="text/css">
.user_shenfen {
	width: 35px
}
.box-content{
    width: 100%;
    text-align: center;
}
.box-title{
      color: black;
    position: absolute;
    top: 2vh;
    width: 100%;
    }
.content_color{
    color: red;}
.del_padding{
    padding: inherit;}
.content_0{
    position: absolute;
    width: 100%;
    top: 6vh;
}
.top_vh{
    top: 8vh;}
</style>
</head>
<body>
	<jsp:include page="../_header.jsp" flush="true" />
	<div class="content">
		<!--article-->

		<div class="myls-frame">
			<section class="t">
				<i class="i-asset"></i>区域管理
			</section>
			<div class="panel-content">
				<div class="grids-contant">
					<a class="grids-grid33 del_padding" style="padding: inherit;" onclick="goUrl('${ctx}/m/MMyService.do?method=list&tip=1')">
						<div class="content_0 top_vh"><span class="content_color">${serviceSaleMoney}元</span></div>
						<div class="box-title">区域销售额</div>
					</a>
					<a class="grids-grid33 del_padding" style="padding: inherit;" onclick="goUrl('${ctx}/m/MMyService.do?method=list&is_entp=1&tip=2')">
						<div class="content_0">
							<div class="box-content">累计数量:<span class="content_color">${userInfoCountEntp}</span></div>
							<div class="box-content">今日新增:<span class="content_color">${userInfoCountEntpToday}</span></div>
						</div>
						<div class="box-title">认证商家</div>
					</a>
					<a class="grids-grid33 del_padding" style="padding: inherit;" onclick="goUrl('${ctx}/m/MMyService.do?method=list&tip=3')">
						<div class="content_0">
							<div class="box-content">累计数量:<span class="content_color">${userInfoCountVip}</span></div>
							<div class="box-content">今日新增:<span class="content_color">${userInfoCountVipToday}</span></div>
						</div>
						<div class="box-title">会员</div>
					</a>
				</div>
			</div>
		</div>

		<jsp:include page="../_footer.jsp" flush="true" />
		<script type="text/javascript"
			src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
		<script type="text/javascript"
			src="${ctx}/m/scripts/dropload/js/dropload.min.js?v20160725"></script>
		<c:url var="url_my_pc" value="/manager/customer/Index.do" />
		<script type="text/javascript">
$(document).ready(function(){
	var topBtnUrl = "${url_my_pc}";
	setTopBtnUrl(topBtnUrl); 
	
	$(".user_shenfen").click(function(){
		var
						title=$(this).attr(
						"title");
		if(title){
			mui.toast(title);
			return
						false;
		}
		var dataurl=$(this).attr(
						"data-url");
		if(dataurl){
			location.href=dataurl; return
						false;
		}
	});
	
	<c:if test="${not empty userInfo}">
	canRefresh();
	</c:if>
	
// 	提示绑定手机
						var tianxiehaoma="${tianxiehaoma}" ;
	
	if(tianxiehaoma==0){
						// 		Common.confirm("请绑定手机号",["前往绑定",""],null,null,function(){
// 			location.href="MMySecurityCenter.do?method=setMobile&isBind=true"
						;
// 		});
		Common.confirm("请绑定手机号",["前往绑定","取消"],function(){
			location.href="MMySecurityCenter.do?method=setMobile&isBind=true";
		})
	}
// var city_manager_hide=$.cookie("city_manager_hide");
// if(city_manager_hide==
						null){
// 	<c:if test="${userInfo.is_city_manager eq 0 and userInfo.user_level eq 204}">
	
// 	 Common.confirm("恭喜您已经升级为3星会员，如果要升级为4星会员需要升级成为服务专员!",["前往升级","不再提示"],function(){
// 		 setCityManager();
// 	 },function(){
// 		 mui.toast("7天内不再显示此提示！");
// 		 $.cookie("city_manager_hide", "hide", {expires: 7, path: '/' });
// 	 });
//  </c:if>
// }
	
}); 

function setCityManager(){
	Common.loading();
    window.setTimeout(function () { 
    $.post("${ctx}/CsAjax.do?method=updateToCityManager",{user_id:"${userInfo.id}"},function(data){
      Common.hide();
   	 window.setTimeout(function () { 
   	   window.location.reload();
   	 }, 1000);
    });
    }, 1000);
}

                                          
</script>
</body>
</html>
