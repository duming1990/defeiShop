<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${app_name}</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Expires" content="-1">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Pragma" content="no-cache">
<jsp:include page="../_public_in_head.jsp" flush="true" />
<link rel="stylesheet" type="text/css" href="${ctx}/commons/font-awesome/css/font-awesome.min.css" />
<style>
.info .ordermain {
    padding: 0 0 .2rem;
    color: #878684;
    height: 20px;
}
.title {
	position: absolute;
	left: 10px;
	width: 100px;
	color: #000;
	overflow: hidden;
	line-height: 25px;
	font-size: 12px;
	height: 25px;
	/* margin-left: 15px; */
	padding-left: 15px;
}

.title-value {
	position: absolute;
	left: 100px;
	width: 100px;
	color: #000;
	overflow: hidden;
	line-height: 25px;
	font-size: 12px;
	height: 25px;
	/* margin-left: 15px; */
	padding-left: 15px;
}

.title1 {
	position: absolute;
	left: 210px;
	width: 100px;
	color: #000;
	overflow: hidden;
	line-height: 25px;
	font-size: 12px;
	height: 25px;
	/* margin-left: 15px; */
	padding-left: 15px;
}

.title-value1{
	position: absolute;
	left: 280px;
	width: 100px;
	color: #000;
	overflow: hidden;
	line-height: 25px;
	font-size: 12px;
	height: 25px;
	/* margin-left: 15px; */
	padding-left: 15px;
}
</style>
</head>
<body>
	<jsp:include page="../_header.jsp" flush="true" />

	<div class="cate-nav" id="searchContent" style="display: none;">
		<div class="sortdrop-wrapper search-wrapper">
			<html-el:form action="/MMyLowerLevel" styleClass="searchForm">
				<html-el:hidden property="method" value="list" />
				<html-el:hidden property="mod_id" />
			</html-el:form>
		</div>
	</div>
	<div class="content">
		
		<c:if test="${not empty entityList}">
			<div style="padding: .16rem .24rem .12rem;" class="tip-danger">
				<i class="fa fa-users"></i> 下级会员：
			</div>
			<div class="list-view">
				<ul class="list-ul" id="ul_data">
					<c:if test="${empty entityList}">
						<div id="no_data"
							style="background: #fff; padding: 15px; margin-bottom: 10px;">暂无数据~</div>
					</c:if>
					<c:forEach items="${entityList}" var="cur">
						<li>
							<div class="list-item">
								<div class="info">
									<div class="ordermain">
										<p>
										<div class="title">登陆名称：</div>
										<div class="title-value">
											<em>${fn:escapeXml(cur.map.userInfo.user_name)}</em>
										</div>
										</p>
										<fmt:formatNumber var="score"
											value="${cur.map.userInfo.cur_score}" pattern="0.########" />
										<p>
										<div class="title1">积分：</div>
										<div class="title-value1">
											<em>${score}</em>
										</div>
										</p>
									</div>
									<div class="ordermain">
										<p>
										<div class="title">用户等级：</div>
										<div class="title-value">
											<em> <c:forEach items="${baseDataList}" var="keys">
													<c:if test="${cur.map.userInfo.user_level eq  keys.id}">${keys.type_name}</c:if>
												</c:forEach>
											</em>
										</div>
										</p>
										<p>
										<div class="title1">身份：</div>
										<div class="title-value1">
											<em> <c:set var="shenfen" value="会员" /> <c:if
													test="${cur.map.userInfo.is_entp eq 1}">
													<c:set var="shenfen" value="商家" />
												</c:if> <c:if test="${cur.map.userInfo.is_fuwu eq 1}">
													<c:set var="shenfen" value="运营中心" />
												</c:if>
												${shenfen}
											</em>
										</div>
										</p>
									</div>
									<div class="ordermain">
										<p>
										<div class="title">推荐会员数：</div>
										<div class="title-value">
											<em>${cur.map.team_count}</em>
										</div>
										</p>
										<p>
										<div class="title1">添加时间：</div>
										<div class="title-value1">
											<em> 
											<fmt:formatDate value="${cur.add_date}"
													pattern="yyyy-MM-dd" />
											</em>
										</div>
										</p>
									</div>
								</div>
							</div>
						</li>
					</c:forEach>
				</ul>
			</div>

			<div class="list-other-more">
				<c:set var="display" value="none" />
				<c:if test="${appendMore eq 1}">
					<c:set var="display" value="block" />
				</c:if>
				<div class="load-more btnpage" style="display: ${display};"
					id="appendMore" onclick="appendMore()" data-pages="1">查看更多</div>
			</div>
			<div class="pop-shade hide" style="display: none;"></div>
	</div>

	</c:if>
	<jsp:include page="../_footer.jsp" flush="true" />
	<script type="text/javascript">

		function appendMore() {
			getData();
		}
		function getData() {
			Common.loading();
			var page = $("#appendMore").attr('data-pages');
			page = Number(page);
			$.ajax({
						type : "POST",
						url : app_path+ "/m/MMyLowerLevel.do?method=getListJson",
						data : 'startPage=' + page + '&'+ $(".searchForm").serialize(),
						dataType : "json",
						error : function(request, settings) {},
						success : function(datas) {
							var html = "";
							$("#appendMore").hide();
							Common.hide();
							if (datas.ret == 1) {
								var dataList = eval(datas.dataList);
								$.each(dataList, function(i,data){ 
											html += '<li>';
											html += '<div class="list-item">';
											html += '<div class="info">';
											
											html += '<div class="ordermain">';
											html += '<p><div class="title">登陆名称：</div><div class="title-value"><em>' + data.user_name + '</em></div></p>';
											html += '<p><div class="title1">积分：</div><div class="title-value1"><em>' + data.cur_score + '</em></div></p>';
											html += '</div>';
											
											html += '<div class="ordermain">';
											html += '<p><div class="title">用户等级：</div><div class="title-value"><em>' + data.user_level + '</em></div></p>';
											html += '<p><div class="title1">身份：</div><div class="title-value1"><em>' + data.shenfen + '</em></div></p>';
											html += '</div>';
											
											html += '<div class="ordermain">';
											html += '<p><div class="title">推荐会员数：</div><div class="title-value"><em>'
													+ data.team_count
													+ '</em></div></p>';
													
											html += '<p><div class="title1">添加时间：</div><div class="title-value1"><em>'+ data.add_date+ '</em></div></p>';
											html += '</div>';
											
											html += '</div>';
											html += '</div>';
											html += '</li>';

										});
								page += 1;
								$("#appendMore").attr('data-pages', page);
								if (datas.appendMore == 1) {
									$("#appendMore").show();
								} else {
									mui.toast("全部加载完成");
								}
								$("#ul_data").append(html);
							} else {
								mui.toast(datas.msg);
							}
							if (datas.ret == 2) {
								html = "<li>" + datas.msg + "</li>";
							}

						}
					});
		}
//</script>
</body>
</html>