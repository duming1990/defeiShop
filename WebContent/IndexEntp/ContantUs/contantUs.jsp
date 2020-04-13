<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>联系我们-${app_name}</title>
<link rel="stylesheet" href="${ctx}/styles/indexEntp/css/style.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/styles/indexEntp/css/owl.carousel.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/styles/indexEntp/css/owl.theme.css" />
<%-- <link rel="stylesheet" type="text/css" href="${ctx}/styles/indexEntp/css/jquery.fullPage.css"/> --%>
<link href="${ctx}/scripts/colorbox/style3/colorbox.css"
	rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/styles/index/css/category-list.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/styles/index/css/customer.css?v=20161130" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/styles/indexEntp/css/common.css" />
<style>
body {
	color: #333;
	-webkit-font-smoothing: antialiased;
	-moz-osx-font-smoothing: grayscale;
	background: #fff;
	font-size: 18px;
	font-family: "微软雅黑", Microsoft YaHei;
	min-width: 1080px;
}
</style>
</head>
<body>
	<!--导航条-->
	<jsp:include page="../_header.jsp" flush="true" />
	<!--导航条-->
	<!--客户服务-->

	<div class="wrap showWrap">
		<div class="col_banner about_banner bgSize"
			style="background-image:url(${ctx}/styles/indexEntp/images/news_banner.jpg);">
			<div class="inner_index w1200">
				<h4>联系我们</h4>
				<p class="en">Contant Us</p>
			</div>
		</div>
		<div class="lxwm_n w1200">
			<div class="lxwm_bt">
				<p>CANTACT</p>
				<span>联系我们</span>
			</div>
			<div class="lxwm_o clearfix">
				<ul class="fl">
					<li class="li1"><img
						src="${ctx}/styles/indexEntp/images/lxwm01.png" alt="全国免费热线"
						width="42" height="42"></li>
					<li class="li2">全国免费热线</li>
					<li class="li3">${index_entp_tel.content }</li>
				</ul>
				<ul class="fl" style="width: 455px;">
					<li class="li1"><img
						src="${ctx}/styles/indexEntp/images/lxwm03.png" alt="总部地址"
						width="42" height="42"></li>
					<li class="li2">总部地址</li>
					<li class="li3">${index_entp_addr.content }</li>
				</ul>
				<ul class="fl" style="border-right: none;">
					<li class="li1"><img
						src="${ctx}/styles/indexEntp/images/lxwm02.png" alt="市场合作邮箱"
						width="42" height="42"></li>
					<li class="li2">市场合作邮箱</li>
					<li class="li3">${index_entp_mail.content }</li>
				</ul>
			</div>
			<div class="lxwm_t">
				<div id="container"
					style="width: 1120px; height: 333px; margin: auto;"></div>
			</div>
		</div>

		<div class="lxwm_s w1200">
			<h4>分公司信息</h4>
			<div class="lxwm_s_x">
				<ul class="fl clearfix">

					<c:forEach items="${comList}" var="cur">
						<li class="lShadow fl"><strong>${cur.title}</strong><span>${cur.title_short}</br>${cur.title_sub}
						</span>
						
						</li>
						
					</c:forEach>

				</ul>
				<div style="clear: both;"></div>
			</div>
		</div>
	</div>



	<!--客户服务-->
	<!--底部-->
	<jsp:include page="../_footer.jsp" flush="true" />
	<!--底部-->
	<!-- js -->
	<script type="text/javascript"
		src="${ctx}/styles/indexEntp/js/jquery.min.js"></script>

	<script type="text/javascript"
		src="${ctx}/styles/indexEntp/js/jquery.mousewheel.min.js"></script>
	<script type="text/javascript"
		src="${ctx}/styles/indexEntp/js/jquery.fullPage.js"></script>
	<script type="text/javascript"
		src="${ctx}/styles/indexEntp/js/jquery.SuperSlide.2.1.1.js"></script>
	<script type="text/javascript"
		src="${ctx}/styles/indexEntp/js/owl.carousel.min.js"></script>
	<script type="text/javascript"
		src="${ctx}/styles/indexEntp/js/style2.js"></script>
	<%-- 	<script type="text/javascript" src="${ctx}/scripts/colorbox/jquery.colorbox.min.js"></script>  --%>
	<script type="text/javascript"
		src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
	<script type="text/javascript"
		src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
	<script type="text/javascript"
		src="http://api.map.baidu.com/api?v=2.0&ak=Oe78HxaRf8Or0gWMOYMDOna6XXLf6zMA"></script>

	<script type="text/javascript">
		//联系我们（地图）
		// 初始化地图
		var map = new BMap.Map("container");
		var point = new BMap.Point(117.1577175335,31.8700904603);
		map.enableScrollWheelZoom();
		map.centerAndZoom(point, 15);
		map.addControl(new BMap.NavigationControl());
		map.addControl(new BMap.ScaleControl());
		map.addControl(new BMap.OverviewMapControl());
		map.addControl(new BMap.MapTypeControl());

		// 创建小蓝点
		var marker = new BMap.Marker(point); // 创建标注
		map.addOverlay(marker); // 将标注添加到地图中
		marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画

		var opts2 = {
			position : point, // 指定文本标注所在的地理位置
			offset : new BMap.Size(-50, -80)
		//设置文本偏移量
		}
		var label2 = new BMap.Label("集团总部", opts2); // 创建文本标注对象
		label2.setStyle({
			color : "black",
			fontSize : "16px",
			paddingLeft : "10px",
			paddingRight : "10px",
			lineHeight : "30px",
			fontFamily : "微软雅黑",
			textAlign : "center"
		});
		map.addOverlay(label2);
	</script>
</body>
</html>