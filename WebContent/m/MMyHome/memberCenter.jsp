<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>会员中心-${app_name}</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
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
    <section class="t"> <i class="i-mode"></i>会员中心 </section>
    <div class="panel-content">
      <div class="grids-contant"> <a class="grids-grid"
						onclick="goUrl('${ctx}/m/MMyTianfan.do?par_id=1100410000')">
        <div class="grids-grid-icon"> <img src="${ctx}/m/styles/img/wodehongbao.png" alt=""> </div>
        <p class="grids-grid-label">消费券明细</p>
        </a> <a class="grids-grid"
						onclick="goUrl('${ctx}/m/MMyScore.do?method=list&mod_id=1100610300')">
        <div class="grids-grid-icon"> <img src="${ctx}/m/styles/img/jifendengji.png" alt=""> </div>
        <p class="grids-grid-label">积分等级</p>
        </a> <a class="grids-grid"
						onclick="goUrl('${ctx}/m/MMyQianBao.do?par_id=1100400000')">
        <div class="grids-grid-icon"> <img src="${ctx}/m/styles/img/qianbao.png" alt=""> </div>
        <p class="grids-grid-label">我的钱包</p>
        </a>
        <c:url var="url" value="/m/MMyQianBao.do?mod_id=1100400050" />
        <a class="grids-grid" onClick="goUrl('${url}')">
        <div class="grids-grid-icon"> <img src="${ctx}/m/styles/img/wodeyuee.png" alt=""> </div>
        <p class="grids-grid-label">我的余额</p>
        </a> <a class="grids-grid"
						onclick="goUrl('${ctx}/m/MMyQianBao.do?method=walletList')">
        <div class="grids-grid-icon"> <img src="${ctx}/m/styles/img/wodehongbao.png" alt=""> </div>
        <p class="grids-grid-label">我的红包</p>
        </a> <a class="grids-grid"
						onclick="goUrl('${ctx}/m/MMySecurityCenter.do?mod_id=1100620100')">
        <div class="grids-grid-icon"> <img src="${ctx}/m/styles/img/anquanzhongxin.png" alt=""> </div>
        <p class="grids-grid-label">安全中心</p>
        </a> </a> <a class="grids-grid"
							onclick="goUrl('${ctx}/m/MMyFav.do?mod_id=1100600400')">
        <div class="grids-grid-icon"> <img src="${ctx}/m/styles/img/shoucang.png" alt=""> </div>
        <p class="grids-grid-label">收藏</p>
        </a> <a class="grids-grid"
							onclick="goUrl('${ctx}/m/MMyShippingAddress.do?mod_id=1100600500')">
        <div class="grids-grid-icon"> <img src="${ctx}/m/styles/img/shouhuodizhi.png" alt=""> </div>
        <p class="grids-grid-label">收货地址</p>
        </a>
        <c:if test="${not empty cc_kefu_id}"> <a class="grids-grid" style="border-right: 1px solid #E3E3E3;"
							onclick="goUrl('https://kefu.qycn.com/vclient/chat/?m=m&websiteid=${cc_kefu_id}')">
          <div class="grids-grid-icon"> <img src="${ctx}/m/styles/img/wodekefu.png" alt=""> </div>
          <p class="grids-grid-label">我的客服</p>
          </a></c:if>
        <a style="border-right: 1px solid #E3E3E3;" class="grids-grid"
							onclick="goUrl('${ctx}/m/MMyComment.do?method=getCommentList&add_user_id=${userInfo.id}&tip=1')">
        <div class="grids-grid-icon"> <img src="${ctx}/m/styles/img/comment.png" alt=""> </div>
        <p class="grids-grid-label">我的评价</p>
        </a> </div>
    </div>
  </div>
  <c:if test="${not empty af.map.app_version}">
    <div class="app_version">V ${fn:escapeXml(af.map.app_version)}</div>
  </c:if>
  <!-- 		<div class="myls-frame"> -->
  <!-- 			<section class="t"><i class="i-asset"></i>我的资产</section> -->
  <!-- 			<ul class="box myls-asset"> -->
  <!-- 				<li class="app_ye"><div><b>0.00</b>元</div><span class="name">余额</span></li> -->
  <!-- 				<li class="app_dyq"><a href=""><b>0</b>张<span class="name">抵用券</span></a></li> -->
  <!-- 				<li class="app_dbb"><a href=""><b>0</b>元<span class="name">夺宝币</span></a></li> -->
  <!-- 		   </ul> -->
  <!-- 		</div> -->
  <!-- 		<div class="myls-frame"> -->
  <!-- 			<a href="" class="t sel lottery"><i class="i-lucky"></i>我的抽奖<span class="side">0</span></a> -->
  <!-- 		</div> -->
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
	var tianxiehaoma="${tianxiehaoma}" ;
	if(tianxiehaoma==0){
		Common.confirm("请绑定手机号",["前往绑定","取消"],function(){
			location.href="MMySecurityCenter.do?method=setMobile&isBind=true";
		},function(){
		})
	}
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
