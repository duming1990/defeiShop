<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<jsp:include page="../_public_head_back.jsp" flush="true" />
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/serviceIndex/css/public.css?20180605" />
		<link rel="stylesheet" type="text/css" href="${ctx}/scripts/swiper/swiper.min.css" />
		<link href="${ctx}/styles/public.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/scripts/colorbox/style3/colorbox.css" rel="stylesheet" type="text/css" />
		<style type="">
			.r12r1 div:nth-child(1) img { width: 49%; margin-top: 25px; float: left; } .r12r1 div:nth-child(2) img { width: 49%; margin-top: 25px; float: right; } .r12r1 div:nth-child(3) img { width: 49%; margin-top: 25px; float: right; } .r12r1 div:nth-child(4) img { width: 100%; margin-top: 25px; float: right; } .areasbg{ width:150px; height:40px; position: absolute; left: -160px; top: 300px; } .preview{ width:50px; height:40px; border-radius:20px; border-style:none; background:#ec6941; font-size:14px; color:rgba(249,248,248,1.00); position: absolute; left: -120px; top: 100px; }
		</style>
	</head>
	<script type="text/javascript" src="${ctx}/scripts/swiper/swiper.min.js"></script>
	<c:set var="bodyBackgroudImage" value="" />
	<c:if test="${not empty baseLinkBg}">
		<c:set var="bodyBackgroudImage" value="background-image: url(${ctx}/${baseLinkBg.image_path})" />
	</c:if>

	<body style="${bodyBackgroudImage}">
		<div class="bannermain">
			<div class="banner">
				<div class="swiper-container">
					<div class="areasSmall" style="left:400px;top:100px;">
						<a href="${ctx}/manager/customer/EntpBaseLink.do?mod_id=${af.map.mod_id}&link_type=10&link_id=${entpInfo.id}" class="beautybg">编辑轮播图</a>
					</div>
					<div class="swiper-wrapper">
						<div class="swiper-slide"><img src="${ctx}/styles/serviceIndex/img/banner.png" /></div>
						<div class="swiper-slide"><img src="${ctx}/styles/serviceIndex/img/banner.png" /></div>
						<div class="swiper-slide"><img src="${ctx}/styles/serviceIndex/img/banner.png" /></div>
					</div>
					<!-- 如果需要分页器 -->
					<div class="swiper-pagination"></div>
				</div>
			</div>
		</div>
		<script>
			var mySwiper = new Swiper('.swiper-container', {
				direction: 'horizontal',
				loop: true,
				autoplay: true,
				delay: 3000, //1秒切换一次
				// 如果需要分页器
				pagination: {
					el: '.swiper-pagination',
				}
			})
		</script>
		<div class="main">
			<div class="container">
				<!-- 				<div class="guan"> -->
				<!-- 					<div class="swiper-container swiperguan"> -->
				<!-- 						<div class="swiper-wrapper"> -->
				<%-- 							<div class="swiper-slide"><img src="${ctx}/styles/serviceIndex/img/gundong1.png" /></div> --%>
				<%-- 							<div class="swiper-slide"><img src="${ctx}/styles/serviceIndex/img/gundong2.png" /></div> --%>
				<%-- 							<div class="swiper-slide"><img src="${ctx}/styles/serviceIndex/img/gundong3.png" /></div> --%>
				<%-- 							<div class="swiper-slide"><img src="${ctx}/styles/serviceIndex/img/gundong4.png" /></div> --%>
				<%-- 							<div class="swiper-slide"><img src="${ctx}/styles/serviceIndex/img/gundong5.png" /></div> --%>
				<%-- 							<div class="swiper-slide"><img src="${ctx}/styles/serviceIndex/img/gundong1.png" /></div> --%>
				<%-- 							<div class="swiper-slide"><img src="${ctx}/styles/serviceIndex/img/gundong2.png" /></div> --%>
				<%-- 							<div class="swiper-slide"><img src="${ctx}/styles/serviceIndex/img/gundong3.png" /></div> --%>
				<%-- 							<div class="swiper-slide"><img src="${ctx}/styles/serviceIndex/img/gundong4.png" /></div> --%>
				<%-- 							<div class="swiper-slide"><img src="${ctx}/styles/serviceIndex/img/gundong5.png" /></div> --%>

				<!-- 						</div> -->
				<!-- 					</div> -->
				<!-- 				</div> -->
				<script>
					var swiper = new Swiper('.swiperguan', {
						slidesPerView: 5,
						spaceBetween: 10,
						freeMode: true,
						loop: true,
						autoplay: true,
						delay: 1000, //1秒切换一次
					});
				</script>

				<div class="areas">
					<a href="${ctx}/manager/customer/EntpBaseLink.do?mod_id=${af.map.mod_id}&link_type=20&link_id=${entpInfo.id}" class="beautybg">编辑楼层</a>
				</div>
				<div class="areasbg">
					<a href="${ctx}/manager/customer/EntpBaseLink.do?method=indexTsg&mod_id=${af.map.mod_id}&link_type=30&main_type=10&link_id=${entpInfo.id}&" class="beautybg">编辑背景图</a>
				</div>
				<div class="areasbg" style="top:200px;cursor: pointer" onclick="entpStyle(${entpInfo.id},${entpInfo.is_entpstyle});">
					<c:if test="${entpInfo.is_entpstyle eq 0}"><span class="beautybg">启用该模板</span></c:if>
					<c:if test="${entpInfo.is_entpstyle eq 1}"><span class="beautybg">关闭该模板</span></c:if>
				</div>
				<div class="areas" style="left:400px;" title="刷新楼层"><span id="reload" onclick="location.reload()" style="cursor:pointer;" class="beautybg">刷新楼层</span></div>
				<button class="preview"><a href="${ctx}/entp/IndexEntpInfo.do?method=entpIndex&entp_id=${entpInfo.id}" style="color: white;text-decoration:none;" target="_blank">预览</a></button>
				<c:forEach var="cur" items="${baseLink20List}" varStatus="vs">

					<c:if test="${cur.pre_number eq 1}">
						<div class="lanmuhe">
							<img src="${ctx}/${cur.image_path}" />
						</div>
						<div class="areas" style="position: absolute;left:5px">
							<a href="${ctx}/manager/customer/EntpBaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}01&link_id=${cur.link_id}&pre_number=${cur.pre_number}&par_id=${cur.id}&par_son_type=1" class="beautybg">编辑</a>
						</div>
						<div class="r1r1">
							<a><img src="${ctx}/styles/serviceIndex/img/product1.png" /></a>
							<a><img src="${ctx}/styles/serviceIndex/img/product2.png" /></a>
							<a><img src="${ctx}/styles/serviceIndex/img/product3.png" /></a>
						</div>
					</c:if>

					<c:if test="${cur.pre_number eq 2}">
						<div class="lanmuhe">
							<img src="${ctx}/${cur.image_path}" />
						</div>
						<div class="r12r1" style="position: relative;">
							<div>
								<div class="areas" style="position: absolute;top:30px">
									<a href="${ctx}/manager/customer/EntpBaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}01&link_id=${cur.link_id}&pre_number=${cur.pre_number}&par_id=${cur.id}&par_son_type=1" class="beautybg">编辑</a>
								</div>
								<img src="${ctx}/styles/serviceIndex/img/product4.png" /></div>

							<div>
								<div class="areas" style="position: absolute;right: 540px;top: 30px">
									<a href="${ctx}/manager/customer/EntpBaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}02&link_id=${cur.link_id}&pre_number=${cur.pre_number}&par_id=${cur.id}&par_son_type=2" class="beautybg">编辑</a>
								</div>
								<img src="${ctx}/styles/serviceIndex/img/product5.png" /></div>
							<div><img src="${ctx}/styles/serviceIndex/img/product6.png" /></div>
							<div>
								<div class="areas" style="position:absolute; top: 760px;left: 10px">
									<a href="${ctx}/manager/customer/EntpBaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}03&link_id=${cur.link_id}&pre_number=${cur.pre_number}&par_id=${cur.id}&par_son_type=3" class="beautybg">编辑</a>
								</div>
								<img src="${ctx}/styles/serviceIndex/img/product7.png" /></div>
						</div>
					</c:if>

					<c:if test="${cur.pre_number eq 3}">
						<div class="lanmuhe">
							<img src="${ctx}/${cur.image_path}" />
						</div>
						<div class="areas" style="position: absolute;left:5px">
							<a href="${ctx}/manager/customer/EntpBaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}01&link_id=${cur.link_id}&pre_number=${cur.pre_number}&par_id=${cur.id}&par_son_type=1" class="beautybg">编辑</a>
						</div>
						<div class="r1r3">
							<a><img src="${ctx}/styles/serviceIndex/img/product8.png" /></a>
							<a><img src="${ctx}/styles/serviceIndex/img/product9.png" /></a>
							<a><img src="${ctx}/styles/serviceIndex/img/product10.png" /></a>
						</div>
					</c:if>

					<c:if test="${cur.pre_number eq 4}">
						<div class="lanmuhe">
							<img src="${ctx}/${cur.image_path}" />
						</div>
						<div class="areas" style="position: absolute;left:5px">
							<a href="${ctx}/manager/customer/EntpBaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}01&link_id=${cur.link_id}&pre_number=${cur.pre_number}&par_id=${cur.id}&par_son_type=1" class="beautybg">编辑</a>
						</div>
						<div class="r1r2" style="position: relative;">
							<a><img src="${ctx}/styles/serviceIndex/img/product11.png" /></a>
							<a><img src="${ctx}/styles/serviceIndex/img/product12.png" /></a>
							<a><img src="${ctx}/styles/serviceIndex/img/product13.png" /></a>
							<a><img src="${ctx}/styles/serviceIndex/img/product14.png" /></a>
						</div>
					</c:if>

					<c:if test="${cur.pre_number eq 5}">
						<div class="lanmuhe">
							<img src="${ctx}/${cur.image_path}" />
						</div>
						<div class="areas" style="position: absolute;left:5px">
							<a href="${ctx}/manager/customer/EntpBaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}01&link_id=${cur.link_id}&pre_number=${cur.pre_number}&par_id=${cur.id}&par_son_type=1" class="beautybg">编辑</a>
						</div>
						<div class="r1r4">
							<a><img src="${ctx}/styles/serviceIndex/img/product15.png" /></a>
							<a><img src="${ctx}/styles/serviceIndex/img/product16.png" /></a>
							<a><img src="${ctx}/styles/serviceIndex/img/product17.png" /></a>
							<a><img src="${ctx}/styles/serviceIndex/img/product18.png" /></a>
							<a><img src="${ctx}/styles/serviceIndex/img/product15.png" /></a>
							<a><img src="${ctx}/styles/serviceIndex/img/product16.png" /></a>
							<a><img src="${ctx}/styles/serviceIndex/img/product17.png" /></a>
							<a><img src="${ctx}/styles/serviceIndex/img/product18.png" /></a>
							<a><img src="${ctx}/styles/serviceIndex/img/product15.png" /></a>
							<a><img src="${ctx}/styles/serviceIndex/img/product16.png" /></a>
							<a><img src="${ctx}/styles/serviceIndex/img/product17.png" /></a>
							<a><img src="${ctx}/styles/serviceIndex/img/product18.png" /></a>
						</div>
					</c:if>

				</c:forEach>
				<div style="height: 1300px"></div>
			</div>
		</div>
		<script type="text/javascript" src="${ctx}/scripts/colorbox/jquery.colorbox.min.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
		<script type="text/javascript" src="${ctx}/scripts/tip/jquery.quicktip.min.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
		<script type="text/javascript">
			//<![CDATA[
			var f = document.forms[0];

			function entpStyle(id, is_entpstyle) {
				if(is_entpstyle == 1) {
					var is_entpstyle = 0;
				} else {
					var is_entpstyle = 1;
				}
				var submit = function(v, h, f) {
					if(v == "ok") {
						$.jBox.tip("修改中！", 'loading');
						window.setTimeout(function() {
							$.ajax({
								type: "POST",
								url: "${ctx}/manager/customer/EntpBaseLinkIndex.do",
								data: "method=entpStyle&entp_id=" + id + "&is_entpstyle=" + is_entpstyle,
								dataType: "json",
								async: true,
								error: function(request, settings) {
									alert(" 数据加载请求失败！ ");
								},
								success: function(data) {
									if(data.code == 0) {
										$.jBox.tip(data.msg, 'error');
									} else {
										$.jBox.tip("修改成功！", 'success');
										window.setTimeout(function() {
											window.location.reload();
										}, 1000);
									}
								}
							});
						}, 1000);
					}
				};
				$.jBox.confirm("您确定要修改吗?", "确定提示", submit);
			}
			$("a.beautybg").colorbox({
				width: "90%",
				height: "80%",
				iframe: true
			});
			//]]>
		</script>
	</body>

</html>