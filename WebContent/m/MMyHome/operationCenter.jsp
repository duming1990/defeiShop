<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>店铺中心-${app_name}</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Expires" content="-1">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Pragma" content="no-cache">
<jsp:include page="../_public_in_head.jsp" flush="true" />
<link href="${ctx}/m/styles/css/my/my-v1.css?v20170105" rel="stylesheet" type="text/css" />
<link href="${ctx}/m/styles/css/my/my-index.css?v20170505" rel="stylesheet"type="text/css" />
<link rel="stylesheet" type="text/css" href="${ctx}/commons/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" href="${ctx}/m/scripts/dropload/css/dropload.css" />
<link rel="stylesheet" href="${ctx}/styles/index/css/xiaoguo.css" />
</head>
<body>
<%-- 	<jsp:include page="../_header.jsp" flush="true" /> --%>
	<jsp:include page="../_userInfo.jsp" flush="true" />
	<div class="content">
			<!--article-->

			<div class="myls-frame">
				<section class="t">
					<i class="i-mode"></i>运营中心
				</section>
				<div class="panel-content">
					<div class="grids-contant">
						<c:if test="${userInfo.is_fuwu eq 1}">

							<a class="grids-grid" onclick="goUrl('${ctx}/m/MAuditCommInfo.do')">
								<div class="grids-grid-icon">
									<img src="${ctx}/m/styles/img/dianpudingdan.png" alt="">
								</div>
								<p class="grids-grid-label">商品审核</p>
							</a>
							<a class="grids-grid" onclick="goUrl('${ctx}/m/MAuditEntpInfo.do')">
								<div class="grids-grid-icon">
									<img src="${ctx}/m/styles/img/dianpudingdan.png" alt="">
								</div>
								<p class="grids-grid-label">商家审核</p>
							</a>
							<a class="grids-grid" onclick="goUrl('${ctx}/m/MMyService.do')">
								<div class="grids-grid-icon">
									<img src="${ctx}/m/styles/img/dianpudingdan.png" alt="">
								</div>
								<p class="grids-grid-label">区域管理</p>
							</a>
							<a class="grids-grid">
								<div class="grids-grid-icon">
								</div>
								<p class="grids-grid-label">&nbsp;</p>
							</a>
						</c:if>
					</div>
				</div>
			</div>
			<c:if test="${not empty af.map.app_version}">
				<div class="app_version">V ${fn:escapeXml(af.map.app_version)}</div>
			</c:if>
		</div>

	<jsp:include page="../_footer.jsp" flush="true" />
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/m/scripts/dropload/js/dropload.min.js?v20160725"></script>
<c:url var="url_my_pc" value="/manager/customer/Index.do" />
<script type="text/javascript">
$(document).ready(function(){
	var topBtnUrl = "${url_my_pc}";
	setTopBtnUrl(topBtnUrl); 
	
	$(".user_shenfen").click(function(){
		var title=$(this).attr("title");
		if(title){
			mui.toast(title);
			return false;
		}
		var dataurl=$(this).attr("data-url");
		if(dataurl){
			location.href=dataurl;
			return false;
		}
	});
	
	<c:if test="${not empty userInfo}">
	  canRefresh(".content");
	</c:if>
	
// 	提示绑定手机
	var tianxiehaoma="${tianxiehaoma}";
	
	if(tianxiehaoma==0){
	// 		Common.confirm("请绑定手机号",["前往绑定",""],null,null,function(){
	// 			location.href="MMySecurityCenter.do?method=setMobile&isBind=true"
						;
// 		});
		Common.confirm("请绑定手机号",["前往绑定","取消"],function(){
			location.href="MMySecurityCenter.do?method=setMobile&isBind=true";
		},function(){
		})
	}
// var city_manager_hide=$.cookie("city_manager_hide");
// if(city_manager_hide==null){
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
