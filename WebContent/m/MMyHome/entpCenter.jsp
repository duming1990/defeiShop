<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>店铺中心-${app_name}</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Expires" content="-1">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Pragma" content="no-cache">
<jsp:include page="../_public_in_head.jsp" flush="true" />
<link href="${ctx}/m/styles/css/my/my-v1.css?v20160502" rel="stylesheet" type="text/css" />
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
    <section class="t"> <i class="i-mode"></i>店铺订单 </section>
    <div class="panel-content">
      <div class="grids-contant">
        <c:if test="${userInfo.is_entp eq 1}"> <a class="grids-grid" onClick="goUrl('${ctx}/m/MMyOrderEntp.do?method=list&order_type=11&mod_id=1300300100')">
          <div class="grids-grid-icon">
            <c:if test="${shiwu_num ne 0}"><span class="my_tip">${shiwu_num}</span></c:if>
            <img src="${ctx}/m/styles/img/dianpudingdan.png" alt=""> </div>
          <p class="grids-grid-label">实物订单</p>
          </a> <a class="grids-grid" onClick="goUrl('${ctx}/m/MMyOrderEntp.do?method=list&order_type=10&mod_id=1300300100')">
          <div class="grids-grid-icon">
            <c:if test="${xuni_num ne 0}"><span class="my_tip">${xuni_num}</span></c:if>
            <img src="${ctx}/m/styles/img/dianpudingdan.png" alt=""> </div>
          <p class="grids-grid-label">虚拟订单</p>
          </a> <a class="grids-grid" onClick="goUrl('${ctx}/m/MMyOrderEntp.do?method=list&order_type=20&mod_id=1300300100')">
          <div class="grids-grid-icon">
<%--             <c:if test="${saoma_num ne 0}"><span class="my_tip">${saoma_num}</span></c:if> --%>
            <img src="${ctx}/m/styles/img/dianpudingdan.png" alt=""> </div>
          <p class="grids-grid-label">扫码订单</p>
          </a> <a class="grids-grid" onClick="goUrl('${ctx}/m/MMyOrderEntp.do?method=list&order_type=40&mod_id=1300300100')">
          <div class="grids-grid-icon">
<%--             <c:if test="${fanxian_num ne 0}"><span class="my_tip">${fanxian_num}</span></c:if> --%>
            <img src="${ctx}/m/styles/img/dianpudingdan.png" alt=""> </div>
          <p class="grids-grid-label">线下订单</p>
          </a> <a style="border-right: 1px solid #e3e3e3;" class="grids-grid" onClick="goUrl('${ctx}/m/MMyOrderEntp.do?method=list&order_type=140&mod_id=1300300100')">
          <div class="grids-grid-icon">
            <c:if test="${tuangou_num ne 0}"><span class="my_tip">${tuangou_num}</span></c:if>
            <img src="${ctx}/m/styles/img/dianpudingdan.png" alt=""> </div>
          <p class="grids-grid-label">团购订单</p>
          <a class="grids-grid" onClick="goUrl('${ctx}/m/MAuditTuiKuan.do')">
          <c:if test="${tui_huan_num ne 0}"><span class="my_tip">${tui_huan_num}</span></c:if>
          <div class="grids-grid-icon"> <img src="${ctx}/m/styles/img/tuikuanshenhe.png" alt=""> </div>
          <p class="grids-grid-label">退换货审核</p>
          </a>
          </a> </c:if>
      </div>
    </div>
  </div>
  <div class="myls-frame">
    <section class="t">
      <c:url var="centername" value="会员中心" />
      <c:if test="${userInfo.is_entp eq 1}">
        <c:url var="centername" value="商家中心" />
      </c:if>
      <i class="i-mode"></i>${centername} </section>
    <div class="panel-content">
      <div class="grids-contant">
        <c:if test="${userInfo.is_entp eq 1}"> <a class="grids-grid" onClick="goUrl('${ctx}/m/MMyQianBao.do?method=huoKuanBi')">
          <div class="grids-grid-icon"> <img src="${ctx}/m/styles/img/huokuanbi.png" alt=""> </div>
          <p class="grids-grid-label">货款</p>
          </a> <a class="grids-grid" onClick="goUrl('${ctx}/m/MPayOffline.do?par_id=1300510000&mod_id=1300510140')">
          <div class="grids-grid-icon"> <img src="${ctx}/m/styles/img/paisong.png" alt=""> </div>
          <p class="grids-grid-label">增值券派送</p>
          </a> <a class="grids-grid" onClick="goUrl('${ctx}/m/MChongZhiEntpFxMoney.do?par_id=1300510000&mod_id=1300510130')">
          <div class="grids-grid-icon"> <img src="${ctx}/m/styles/img/chongzhi.png" alt=""> </div>
          <p class="grids-grid-label">增值券充值</p>
          </a> <a class="grids-grid" onClick="goUrl('${ctx}/m/MyFreight.do')">
          <div class="grids-grid-icon"> <img src="${ctx}/m/styles/img/wuliumuban.png" alt=""> </div>
          <p class="grids-grid-label">物流模板</p>
          </a> <a class="grids-grid" onClick="goUrl('${ctx}/m/MMyShopQrCode.do')">
          <div class="grids-grid-icon"> <img src="${ctx}/m/styles/img/shoukuan.png" alt=""> </div>
          <p class="grids-grid-label">我的收款码</p>
          </a> <a class="grids-grid" onClick="goUrl('${ctx}/m/MEntpInfo.do?method=index&entp_id=${userInfo.own_entp_id}')">
          <div class="grids-grid-icon"> <img src="${ctx}/m/styles/img/dianpu.png" alt=""> </div>
          <p class="grids-grid-label">我的店铺</p>
          </a>  <a class="grids-grid" onClick="goUrl('${ctx}/m/MMyComment.do?method=getCommentList&is_entp=true')">
          <div class="grids-grid-icon"> <img src="${ctx}/m/styles/img/comment.png" alt=""> </div>
          <p class="grids-grid-label">商品评论</p>
          </a>
          <!-- 							<a class="grids-grid"> -->
          <!-- 								<div class="grids-grid-icon"> -->
          <!-- 								</div> -->
          <!-- 								<p class="grids-grid-label">&nbsp;</p> -->
          <!-- 							</a> -->
          <!-- 							<a class="grids-grid"> -->
          <!-- 								<div class="grids-grid-icon"> -->
          <!-- 								</div> -->
          <!-- 								<p class="grids-grid-label">&nbsp;</p> -->
          <!-- 							</a> -->
          <!-- 							<a class="grids-grid"> -->
          <!-- 								<div class="grids-grid-icon"> -->
          <!-- 								</div> -->
          <!-- 								<p class="grids-grid-label">&nbsp;</p> -->
          <!-- 							</a> -->
          <!-- 							<a class="grids-grid"> -->
          <!-- 								<div class="grids-grid-icon"> -->
          <!-- 								</div> -->
          <!-- 								<p class="grids-grid-label">&nbsp;</p> -->
          <!-- 							</a>							 -->
        </c:if>
      </div>
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
			location.href = dataurl; 
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
