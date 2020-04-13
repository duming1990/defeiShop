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
			.r12r1 div:nth-child(1) img { width: 49%; margin-top: 25px; float: left; } .r12r1 div:nth-child(2) img { width: 49%; margin-top: 25px; float: right; } .r12r1 div:nth-child(3) img { width: 49%; margin-top: 25px; float: right; } .r12r1 div:nth-child(4) img { width: 100%; margin-top: 25px; float: right; } .areasbg{ width:150px; height:40px; position: absolute; left: -160px; top: 300px; } .preview{ width:50px; height:40px; border-radius:20px; border-style:none; background:#ec6941; font-size:14px; color:rgba(249,248,248,1.00); position: absolute; left: -120px; top: 150px; } .beautybg1{ box-shadow: inset 0px 1px 0px 0px #a4e271; background: -webkit-gradient(linear, left top, left bottom, color-stop(0.05, #89c403), color-stop(1, #77a809)); background: -moz-linear-gradient(center top, #89c403 5%, #77a809 100%); filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#89c403',endColorstr='#77a809'); background-color: #89c403; -moz-border-radius: 5px; -webkit-border-radius: 5px; border-radius: 5px; border: 1px solid #74b807; display: inline-block; color: #ffffff; font-family: arial; font-size: 12px; font-weight: bold; padding: 4px 10px; text-decoration: none; text-shadow: 1px 1px 0px #528009; line-height: 15px; cursor: pointer; }
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
						<a href="${ctx}/manager/customer/ServiceBaseLink.do?mod_id=${af.map.mod_id}&link_type=10&link_id=${entity.id}" class="beautybg">编辑轮播图</a>
					</div>
					<div class="swiper-wrapper">
						<c:if test="${not empty baseLink10List }">
						<c:forEach var="entity" items="${baseLink10List}" varStatus="vs">
						<div class="swiper-slide"><img src="${ctx}/${entity.image_path}" /></div>
						</c:forEach>
						</c:if>
						<c:if test="${empty baseLink10List }">
						<div class="swiper-slide"><img src="${ctx}/styles/serviceIndex/img/banner.png" /></div>
						<div class="swiper-slide"><img src="${ctx}/styles/serviceIndex/img/banner.png" /></div>
						<div class="swiper-slide"><img src="${ctx}/styles/serviceIndex/img/banner.png" /></div>
						</c:if>
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
					<a onclick="editFloor(${entity.id});" class="beautybg1">编辑楼层</a>
				</div>
				<div class="areasbg">
					<a href="${ctx}/manager/customer/ServiceBaseLink.do?method=indexTsg&mod_id=${af.map.mod_id}&link_type=30&main_type=10&link_id=${entity.id}&p_index=${entity.p_index}&" class="beautybg">编辑背景图</a>
				</div>
				<div class="areas" style="left:400px;" title="刷新楼层"><span id="reload" onclick="location.reload()" style="cursor:pointer;" class="beautybg">刷新楼层</span></div>
				<button class="preview"><a href="${ctx}/IndexTsg.do?method=index&Link_id=${entity.id}" style="color: white;text-decoration:none;" target="_blank">预览</a></button>
				<c:forEach var="cur" items="${baseLink20List}" varStatus="vs">

					<c:if test="${cur.pre_number eq 1}">
						<div class="lanmuhe">
							<img src="${ctx}/${cur.image_path}" />
						</div>
						<div class="areas" style="position: absolute;left:5px">
							<a href="${ctx}/manager/customer/ServiceBaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}01&link_id=${cur.link_id}&pre_number=${cur.pre_number}&par_id=${cur.id}&par_son_type=1" class="beautybg">编辑</a>
						</div>
						<div class="r1r1">
						<c:if test="${not empty cur.map.baseLink101List}">
							<c:forEach var="product" items="${cur.map.baseLink101List}" varStatus="vs">
							<a><img src="${ctx}/${product.image_path}" /></a>
							</c:forEach>
						</c:if>
						<c:if test="${empty cur.map.baseLink101List}">
							<a><img src="${ctx}/styles/serviceIndex/img/product1.png" /></a>
							<a><img src="${ctx}/styles/serviceIndex/img/product2.png" /></a>
							<a><img src="${ctx}/styles/serviceIndex/img/product3.png" /></a>
						</c:if>
						</div>
					</c:if>

					<c:if test="${cur.pre_number eq 2}">
						<div class="lanmuhe">
							<img src="${ctx}/${cur.image_path}" />
						</div>
						<div class="r12r1" style="position: relative;">
							<div>
								<div class="areas" style="position: absolute;top:30px">
									<a href="${ctx}/manager/customer/ServiceBaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}01&link_id=${cur.link_id}&pre_number=${cur.pre_number}&par_id=${cur.id}&par_son_type=1" class="beautybg">编辑</a>
								</div>
								<c:if test="${ empty cur.map.baseLink201List}">
									<c:forEach var="product" items="${cur.map.baseLink201List }">
										<img src="${ctx}/${product.image_path}" />
									</c:forEach>
								</c:if>
								<c:if test="${ not empty cur.map.baseLink201List}">
								<img src="${ctx}/styles/serviceIndex/img/product4.png" />
								</c:if></div>

							<div>
								<div class="areas" style="position: absolute;right: 540px;top: 30px">
									<a href="${ctx}/manager/customer/ServiceBaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}02&link_id=${cur.link_id}&pre_number=${cur.pre_number}&par_id=${cur.id}&par_son_type=2" class="beautybg">编辑</a>
								</div>
								<c:if test="${ empty cur.map.baseLink202List}">
									<c:forEach var="product" items="${cur.map.baseLink202List }">
										<img src="${ctx}/${product.image_path}" />
									</c:forEach>
								</c:if>
								<c:if test="${not empty cur.map.baseLink202List}">
									<img src="${ctx}/styles/serviceIndex/img/product5.png" />
								</c:if></div>
							<div><img src="${ctx}/styles/serviceIndex/img/product6.png" /></div>
							<div>
								<div class="areas" style="position:absolute; top: 760px;left: 10px">
									<a href="${ctx}/manager/customer/ServiceBaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}03&link_id=${cur.link_id}&pre_number=${cur.pre_number}&par_id=${cur.id}&par_son_type=3" class="beautybg">编辑</a>
								</div>
								<c:if test="${not  empty cur.map.baseLink203List}">
									<c:forEach var="product" items="${cur.map.baseLink203List }">
										<img src="${ctx}/${product.image_path}" />
									</c:forEach>
								</c:if>
								<c:if test="${empty cur.map.baseLink203List}">
									<img src="${ctx}/styles/serviceIndex/img/product7.png" />
								</c:if></div>
						</div>
					</c:if>

					<c:if test="${cur.pre_number eq 3}">
						<div class="lanmuhe">
							<img src="${ctx}/${cur.image_path}" />
						</div>
						<div class="areas" style="position: absolute;left:5px">
							<a href="${ctx}/manager/customer/ServiceBaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}01&link_id=${cur.link_id}&pre_number=${cur.pre_number}&par_id=${cur.id}&par_son_type=1" class="beautybg">编辑</a>
						</div>
						<div class="r1r3">
						<c:if test="${not empty cur.map.baseLink301List }">
							<c:forEach var="product" items="${cur.map.baseLink301List}" varStatus="vs">
								<a><img src="${ctx}/${product.image_path}" /></a>
							</c:forEach>
						</c:if>
						<c:if test="${empty cur.map.baseLink301List }">
							<a><img src="${ctx}/styles/serviceIndex/img/product8.png" /></a>
							<a><img src="${ctx}/styles/serviceIndex/img/product9.png" /></a>
							<a><img src="${ctx}/styles/serviceIndex/img/product10.png" /></a>
						</c:if>
						</div>
					</c:if>

					<c:if test="${cur.pre_number eq 4}">
						<div class="lanmuhe">
							<img src="${ctx}/${cur.image_path}" />
						</div>
						<div class="areas" style="position: absolute;left:5px">
							<a href="${ctx}/manager/customer/ServiceBaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}01&link_id=${cur.link_id}&pre_number=${cur.pre_number}&par_id=${cur.id}&par_son_type=1" class="beautybg">编辑</a>
						</div>
						<div class="r1r2" style="position: relative;">
						<c:if test="${not empty cur.map.baseLink401List }">
							<c:forEach var="product" items="${cur.map.baseLink401List}" varStatus="vs">
								<a><img src="${ctx}/${product.image_path}" /></a>
							</c:forEach>
						</c:if>
						<c:if test="${empty cur.map.baseLink401List }">
							<a><img src="${ctx}/styles/serviceIndex/img/product11.png" /></a>
							<a><img src="${ctx}/styles/serviceIndex/img/product12.png" /></a>
							<a><img src="${ctx}/styles/serviceIndex/img/product13.png" /></a>
							<a><img src="${ctx}/styles/serviceIndex/img/product14.png" /></a>
						</c:if>
						</div>
					</c:if>

					<c:if test="${cur.pre_number eq 5}">
						<div class="lanmuhe">
							<img src="${ctx}/${cur.image_path}" />
						</div>
						<div class="areas" style="position: absolute;left:5px">
							<a href="${ctx}/manager/customer/ServiceBaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}01&link_id=${cur.link_id}&pre_number=${cur.pre_number}&par_id=${cur.id}&par_son_type=1" class="beautybg">编辑</a>
						</div>
						<div class="r1r4">
						<c:if test="${not empty cur.map.baseLink501List }">
							<c:forEach var="product" items="${cur.map.baseLink501List}" varStatus="vs">
								<a><img src="${ctx}/${product.image_path}" /></a>
							</c:forEach>
						</c:if>
						<c:if test="${empty cur.map.baseLink501List }">
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
						</c:if>
						</div>
					</c:if>

				</c:forEach>
				<div style="height: 1300px"></div>
			</div>
		</div>
		<script type="text/javascript" src="${ctx}/scripts/colorbox/jquery.colorbox.min.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
		<script type="text/javascript">
			//<![CDATA[
			$("a.beautybg").colorbox({
				width: "90%",
				height: "80%",
				iframe: true
			});

			function editFloor(id) {
				var url = "${ctx}/manager/customer/ServiceBaseLink.do?mod_id=${af.map.mod_id}&link_type=20&link_id=" + id;
				$.dialog({
					title: "编辑楼层",
					width: 1400,
					height: 700,
					padding: 0,
					max: false,
					min: false,
					fixed: true,
					lock: true,
					content: "url:" + encodeURI(url),
					close: function() {
						location.reload();
					}

				});
			}
			//]]>
		</script>
	</body>

</html>