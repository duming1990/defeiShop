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
<link rel="stylesheet" type="text/css" href="${ctx}/commons/font-awesome/css/font-awesome.min.css" />
<link href="${ctx}/m/js/date/app1/css/date.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx}/m/scripts/dropload/css/dropload.css" />
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
	<div class="cate-nav" id="searchContent" style="display:none;">
  <div class="sortdrop-wrapper search-wrapper">
  <html-el:form action="/MMyService" styleClass="searchForm">
  <html-el:hidden property="method" value="list" />
  <html-el:hidden property="mod_id" />
  <html-el:hidden property="tip" />
  <html-el:hidden property="is_entp" />
    <div class="set-site">
      <ul class="formUl">
<!--         <li><span>用户名：</span> -->
<%--         <html-el:text property="user_name_like" styleClass="webinput" style="width: 70%;"></html-el:text> --%>
<!--         </li> -->
        <li class="select"><span>开始时间：</span>
          <fmt:formatDate value="${sereach_servecenter_st_date}" pattern="yyyy-MM-dd" var="_sereach_servecenter_st_date" />
          <input name="sereach_servecenter_st_date" type="text" id=sereach_servecenter_st_date readonly="readonly" autocomplete="off" maxlength="38" value="${_sereach_servecenter_st_date}" placeholder="开始时间" class="buy_input">
        </li>
        <li class="select"><span>结束时间：</span>
          <fmt:formatDate value="${sereach_servecenter_en_date}" pattern="yyyy-MM-dd" var="_sereach_servecenter_en_date" />
          <input name="sereach_servecenter_en_date" type="text" id=sereach_servecenter_en_date readonly="readonly" autocomplete="off" maxlength="38" value="${_sereach_servecenter_en_date}" placeholder="结束时间" class="buy_input">
        </li>
      </ul>
    </div>
    <div class="box submit-btn">
    <input type="submit" class="com-btn" id="search_submit" value="查询" /></div>
  </html-el:form>
 </div>
</div>

	<div class="content">
			<c:if test="${af.map.tip eq 1}">
			<div style="padding: .16rem .24rem .12rem;" class="tip-success">
				<i class="fa fa-user"></i> 区域销售额：${serviceSaleMoney}
			</div>
			</c:if>
			<c:if test="${af.map.tip ne 1}">
			<div style="padding: .16rem .24rem .12rem;" class="tip-success">
				<i class="fa fa-user"></i> 用户：${userInfo.user_name}
			</div>
			<div class="list-view">
				<ul class="list-ul">
					<li style="min-height: 0px;">
						<div class="list-item">
							<div class="info">
								<div class="ordermain">
									<p>
										累计数量：<span class="tip-success">
										<c:if test="${af.map.tip eq 2}">${userInfoCountEntp }</c:if>
										<c:if test="${af.map.tip eq 3}">${userInfoCountVip }</c:if>
										</span>
									</p>
									<p>
										今日数量：<span class="tip-success">
										<c:if test="${af.map.tip eq 2}">${userInfoCountEntpToday }</c:if>
										<c:if test="${af.map.tip eq 3}">${userInfoCountVipToday }</c:if>
										</span>
									</p>
								</div>
							</div>
						</div>
					</li>
				</ul>
			</div>
			<div class="list-view">
				<ul class="list-ul" id="ul_data" data-page="1">
					<c:if test="${(empty entityList) and (af.map.tip ne 1)}">
						<div id="no_data" style="background: #fff; padding: 15px; margin-bottom: 10px;">暂无数据~</div>
					</c:if>
					<c:forEach items="${entityList}" var="cur">
						<li style="min-height: 0px;">
							<div class="list-item">
								<div class="info">
									<div class="ordermain">
										<p>
										<div class="title">登陆名称：</div>
										<div class="title-value">
											<em>${fn:escapeXml(cur.user_name)}</em>
										</div>
										</p>
										<p>
										<div class="title1">邀请人：</div>
										<div class="title-value1">
											<em>${fn:escapeXml(cur.ymid)}</em>
										</div>
										</p>
									</div>
									<div class="ordermain">
										<p>
										<div class="title">认证时间：</div>
										<div class="title-value">
											<em><fmt:formatDate value="${cur.map.audit_date}"pattern="yyyy-MM-dd" /></em>
										</div>
										</p>
										<p>
										<div class="title1">添加时间：</div>
										<div class="title-value1">
											<em> <fmt:formatDate value="${cur.add_date}"pattern="yyyy-MM-dd" />
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
			</c:if>
			<div class="pop-shade hide" style="display: none;"></div>
	</div>

<%-- 	</c:if> --%>
<jsp:include page="../_footer.jsp" flush="true" />
<script type="text/javascript" src="${ctx}/m/js/date/app1/js/date.js"></script>
<script type="text/javascript" src="${ctx}/scripts/iscroll/iscroll-5.1.2.min.js"></script>
<script type="text/javascript" src="${ctx}/m/scripts/dropload/js/dropload.min.js?v20160725"></script>
<script type="text/javascript" src="${ctx}/scripts/lazyload/min.js"></script>
<script type="text/javascript">//  
var ctx = "${ctx}";
$(document).ready(function() {
	 var d_year = ${d_year};
	 $('#sereach_servecenter_st_date').datePicker({
	     beginyear: 1920,
	     endyear: d_year,
	     theme: 'date',
	 });
	 $('#sereach_servecenter_en_date').datePicker({
	     beginyear: 1920,
	     endyear: d_year,
	     theme: 'date',
	 });
	 
	 $("img.lazy").lazyload({
		    effect : "fadeIn"
		});
		<c:if test="${af.map.tip ne 1}">
		getData();
		</c:if>
});

function getData() {
	var ajax_url = ctx + "/m/MMyService.do?method=getServiceCenterInfoSaleMoneyJson&" + $(".searchForm").serialize();
	$('.content').dropload({
        scrollArea : window,
        autoLoad : true,     
        loadDownFn : function(me){
        	var page = $("#ul_data").attr('data-page');
        	page = Number(page);
            $.ajax({
                type: 'GET',
                url: ajax_url,
                data: "startPage=" + page,
                dataType: 'json',
                success: function(data){
                	var html = "";
                	if(null != data.datas.dataList){
                		 var dataList = eval(data.datas.dataList);
         				$.each(dataList, function(i,data){
         					html += '<li style="min-height: 0px;"><div class="list-item"><div class="info">';
         					html += '<div class="ordermain">'
         					html += '<p><div class="title">登陆名称：</div><div class="title-value"><em>'+data.user_name+'</em></div></p>'
         					html += '<p><div class="title1">邀请人：</div><div class="title-value1"><em>'+data.ymid+'</em></div></p>'
         					html += '</div>'
         					html += '<div class="ordermain">'
         					html += '<p><div class="title">认证时间：</div><div class="title-value"><em>'+data.audit_date+'</em></div></p>'					
         					html += '<p><div class="title1">添加时间：</div><div class="title-value1"><em>'+data.add_date+'</em></div></p>'
         					html += '</div>'
         					html += '</div></div></li>'
                         });
                	}
                   
    				setTimeout(function(){
    					$('#ul_data').append(html);
    					
    					lazyload(page - 1);
    					
                        me.resetload();
                    },500); 
                    page += 1;
			        $("#ul_data").attr('data-page',page);
                   	if (data.code == 2) {
                         me.lock();// 锁定
                         me.noData(); // 无数据
   					} 
                },
            });
        },
        domUp : {// 上方DOM                                                       
            domClass   : 'dropload-up',
            domRefresh : '<div class="dropload-refresh"><i class="fa fa-long-arrow-down"></i>&nbsp;下拉刷新</div>',
            domUpdate  : '<div class="dropload-update"><i class="fa fa-long-arrow-up"></i>&nbsp;释放更新</div>',
            domLoad    : '<div class="dropload-load"><span class="loading"></span>刷新中...</div>'
        },
        loadUpFn : function(me){
            // 为了测试，延迟1秒加载
            setTimeout(function(){
                me.resetload();
                me.unlock();
                me.noData(false);
                Common.loading();
 	       		window.setTimeout(function () {
 	       			window.location.reload();
 	       		}, 1000);
            },500);
         },
        threshold : 50
    });
}

		

//</script>
</body>
</html>