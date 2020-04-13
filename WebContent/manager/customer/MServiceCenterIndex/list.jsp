<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<jsp:include page="../_public_head_back.jsp" flush="true" />
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/mserviceIndex/css/public.css" />
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/mserviceIndex/css/index.css?20180615" />
		<link rel="stylesheet" type="text/css" href="${ctx}/scripts/swiper/swiper.min.css" />
		<link href="${ctx}/styles/public.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/scripts/colorbox/style3/colorbox.css" rel="stylesheet" type="text/css" />
		<style type="">
			.beautybg1{ box-shadow: inset 0px 1px 0px 0px #a4e271; background: -webkit-gradient(linear, left top, left bottom, color-stop(0.05, #89c403), color-stop(1, #77a809)); background: -moz-linear-gradient(center top, #89c403 5%, #77a809 100%); filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#89c403',endColorstr='#77a809'); background-color: #89c403; -moz-border-radius: 5px; -webkit-border-radius: 5px; border-radius: 5px; border: 1px solid #74b807; display: inline-block; color: #ffffff; font-family: arial; font-size: 12px; font-weight: bold; padding: 4px 10px; text-decoration: none; text-shadow: 1px 1px 0px #528009; line-height: 15px; cursor: pointer; } .preview{ width:50px; height:40px; border-radius:20px; border-style:none; background:#ec6941; font-size:14px; color:rgba(249,248,248,1.00); position: absolute; left: -120px; top: 150px; } .areasbg{ width:150px; height:40px; position: absolute; left: -160px; top: 300px; }
		</style>

		<body>
			<div class="container index" style="width: 50% ;position: relative;float: left;left: 25%">
				<div class="swiper-container">
					<div class="areasSmall">
						<a href="${ctx}/manager/customer/MServiceBaseLink.do?mod_id=${af.map.mod_id}&link_type=10&link_id=${entity.id}" class="beautybg">编辑轮播图</a>
					</div>
					<div class="swiper-wrapper">
						<div class="swiper-slide">
							<img src="${ctx}/styles/mserviceIndex/img/img1.jpg" class="guanStyle" />
						</div>
						<div class="swiper-slide">
							<img src="${ctx}/styles/mserviceIndex/img/img1.jpg" class="guanStyle" />
						</div>
						<div class="swiper-slide">
							<img src="${ctx}/styles/mserviceIndex/img/img1.jpg" class="guanStyle" />
						</div>
					</div>
					<!-- 如果需要分页器 -->
					<div class="swiper-pagination"></div>
					<!-- 如果需要导航按钮 -->
					<div class="swiper-button-prev swiper-button-white"></div>
					<div class="swiper-button-next swiper-button-white"></div>
				</div>
				<div class="areas">
					<a onclick="editFloor(${entity.id});" class="beautybg1">编辑楼层</a>
				</div>
				<div class="areasbg">
					<a href="${ctx}/manager/customer/MServiceBaseLink.do?method=indexTsg&mod_id=${af.map.mod_id}&link_type=30&main_type=10&link_id=${entity.id}&p_index=${entity.p_index}&" class="beautybg">编辑背景图</a>
				</div>
				<div class="areas" style="left:400px;" title="刷新楼层"><span id="reload" onclick="location.reload()" style="cursor:pointer;" class="beautybg">刷新楼层</span></div>
				<button class="preview"><a href="${ctx}/m/IndexMTsg.do?method=index&link_id=${entity.id}" style="color: white;text-decoration:none;" target="_blank">预览</a></button>
				<c:forEach var="cur" items="${baseLink20List}" varStatus="vs">
					<c:if test="${cur.pre_number eq 1}">
						<div class="div2">
							<img src="${ctx}/styles/mserviceIndex/img/img7.jpg" />
							<div class="areas" style="position: absolute;">
								<a href="${ctx}/manager/customer/MServiceBaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}01&link_id=${cur.link_id}&main_type=${cur.main_type}&pre_number=${cur.pre_number}&par_id=${cur.id}&par_son_type=1" class="beautybg">编辑</a>
							</div>
							<img class="mr1" src="${ctx}/styles/mserviceIndex/img/img8.jpg" />
							<img class="mr1" src="${ctx}/styles/mserviceIndex/img/img9.jpg" />
							<img class="mr1" src="${ctx}/styles/mserviceIndex/img/img10.jpg" />
						</div>
					</c:if>
					<c:if test="${cur.pre_number eq 2}">
						<div class="div2">
							<img class="mr1" src="${ctx}/styles/mserviceIndex/img/img7.jpg" />
						</div>
						<div class="div3">
							<div class="left">
								<div class="areas" style="position: absolute;">
									<a href="${ctx}/manager/customer/MServiceBaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}01&link_id=${cur.link_id}&main_type=${cur.main_type}&pre_number=${cur.pre_number}&par_id=${cur.id}&par_son_type=1" class="beautybg">编辑</a>
								</div>
								<img src="${ctx}/styles/mserviceIndex/img/img12.jpg" />
							</div>
							<div class="right">
								<div class="areas" style="position: absolute;">
									<a href="${ctx}/manager/customer/MServiceBaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}02&link_id=${cur.link_id}&main_type=${cur.main_type}&pre_number=${cur.pre_number}&par_id=${cur.id}&par_son_type=2" class="beautybg">编辑</a>
								</div>
								<img src="${ctx}/styles/mserviceIndex/img/img11.jpg" />
								<img src="${ctx}/styles/mserviceIndex/img/img13.jpg" />
							</div>
						</div>
						<div class="div2">
							<div class="areas" style="position: absolute; left: 9px">
								<a href="${ctx}/manager/customer/MServiceBaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}03&link_id=${cur.link_id}&main_type=${cur.main_type}&pre_number=${cur.pre_number}&par_id=${cur.id}&par_son_type=3" class="beautybg">编辑</a>
							</div>
							<img class="mr2" src="${ctx}/styles/mserviceIndex/img/img14.jpg" />
						</div>
					</c:if>
					<c:if test="${cur.pre_number eq 3}">
						<div class="div2">
							<img class="mr1" src="${ctx}/styles/mserviceIndex/img/img7.jpg" />
						</div>
						<div class="div4">
							<div class="areas" style="position: absolute;">
								<a href="${ctx}/manager/customer/MServiceBaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}01&link_id=${cur.link_id}&main_type=${cur.main_type}&pre_number=${cur.pre_number}&par_id=${cur.id}&par_son_type=1" class="beautybg">编辑</a>
							</div>
							<img src="${ctx}/styles/mserviceIndex/img/img15.jpg" class="style" />
							<img src="${ctx}/styles/mserviceIndex/img/img16.jpg" class="style" />
							<img src="${ctx}/styles/mserviceIndex/img/img17.jpg" class="style" />
						</div>
					</c:if>
					<c:if test="${cur.pre_number eq 4}">
						<div class="div2">
							<img src="${ctx}/styles/mserviceIndex/img/img7.jpg" />
						</div>
						<div class="div5">
							<div class="areas" style="position: absolute;">
								<a href="${ctx}/manager/customer/MServiceBaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}01&link_id=${cur.link_id}&main_type=${cur.main_type}&pre_number=${cur.pre_number}&par_id=${cur.id}&par_son_type=1" class="beautybg">编辑</a>
							</div>
							<img src="${ctx}/styles/mserviceIndex/img/img18.jpg" class="style" />
							<img src="${ctx}/styles/mserviceIndex/img/img19.jpg" class="style" />
							<img src="${ctx}/styles/mserviceIndex/img/img21.jpg" class="style" />
							<img src="${ctx}/styles/mserviceIndex/img/img20.jpg" class="style" />
						</div>
					</c:if>
					<c:if test="${cur.pre_number eq 5}">
						<div class="div2">
							<img src="${ctx}/styles/mserviceIndex/img/img22.jpg" />
						</div>
						<div class="div5">
							<div class="areas" style="position: absolute;">
								<a href="${ctx}/manager/customer/MServiceBaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}01&link_id=${cur.link_id}&main_type=${cur.main_type}&pre_number=${cur.pre_number}&par_id=${cur.id}&par_son_type=1" class="beautybg">编辑</a>
							</div>
							<img src="${ctx}/styles/mserviceIndex/img/img23.jpg" class="style" />
							<img src="${ctx}/styles/mserviceIndex/img/img23.jpg" class="style" />
							<img src="${ctx}/styles/mserviceIndex/img/img23.jpg" class="style" />
							<img src="${ctx}/styles/mserviceIndex/img/img23.jpg" class="style" />
						</div>
					</c:if>
				</c:forEach>
			</div>
			<script type="text/javascript " src="${ctx}/commons/scripts/jquery.js "></script>
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
					var url = "${ctx}/manager/customer/MServiceBaseLink.do?mod_id=${af.map.mod_id}&link_type=20&main_type=10&link_id=" + id;
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