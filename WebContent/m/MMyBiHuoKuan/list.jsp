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
</head>
<body>
<jsp:include page="../_header.jsp" flush="true" />	
<div class="content"> 
<div class="list-view">
  <ul class="list-ul" id="ul_data">
  <c:if test="${empty userBiRecordlList}">
	          <div id="no_data" style="background:#fff; padding:15px; margin-bottom:10px;">暂无数据~</div>
	 </c:if>
     <c:forEach var="cur" items="${userBiRecordlList}" varStatus="vs">
      	<li>
      		<div class="list-item">
	          <div class="info">
	            <div class="ordermain">
	              <p>类型：
	                <c:forEach items="${biGetTypes}" var="keys">
			          <c:if test="${cur.bi_get_type eq keys.index}">${keys.name}</c:if>
			        </c:forEach>
	              </p>
	            </div>
	            <div class="ordermain">
	              <p>操作前金额：<em>
	              <fmt:formatNumber var="bi" pattern="0.########" value="${cur.bi_no_before}" />${bi}
	              </em></p>
	            </div>
	            <div class="ordermain">
	              <p>本次金额：<em>
	                <c:set var="pre" value="+" />
			        <c:set var="_class" value="tip-success" />
			        <c:if test="${cur.bi_chu_or_ru eq -1}">
			          <c:set var="pre" value="-" />
			          <c:set var="_class" value="tip-danger" />
			        </c:if>
			        <fmt:formatNumber var="bi" pattern="0.########" value="${cur.bi_no}" />
			        <span class="${_class}">${pre}${bi}</span> 
	              </em></p>
	              <p>操作后金额：<em><fmt:formatNumber var="bi" pattern="0.########" value="${cur.bi_no_after}" />${bi}</em></p>
	            </div>
	            <div class="ordermain">
	              <p>购买人：<em>
		          <c:if test="${(cur.bi_get_type ne -60) and (cur.bi_get_type ne 112)}">${cur.map.buy_name}</c:if>
	              </em></p>
	            </div>
	            <div class="ordermain">
	              <p>订单号：<em>
		          <c:if test="${(cur.bi_get_type ne -60) and (cur.bi_get_type ne 112)}">${cur.map.trade_index}</c:if>
	              </em></p>
	            </div>
	            <div class="ordermain">
	              <p>操作时间：<em>
		          <fmt:formatDate value="${cur.add_date}" pattern="yyyy-MM-dd HH:mm" />
	              </em></p>
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
    <div class="load-more btnpage" style="display:${display};" id="appendMore" onclick="appendMore()" data-pages="1">查看更多</div>
  </div>
   <div class="pop-shade hide" style="display:none;"></div>
</div>
<jsp:include page="../_footer.jsp" flush="true" />
<script type="text/javascript">//<![CDATA[

function appendMore() {
	getData();
}
function getData() {
	Common.loading();
	var page = $("#appendMore").attr('data-pages');
	page = Number(page);
	$.ajax({
		type: "POST",
		url: app_path + "/m/MMyBiHuoKuan.do?method=getListJson",
		data: 'startPage=' + page +'&' + $(".searchForm").serialize(),
		dataType: "json",
		error: function(request, settings) {},
		success: function(datas) {
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
					html += '<p>类型：'+data.desc_name+'</p>';
					html += '</div>';
					
					html += '<div class="ordermain">';
					html += '<p>操作前金额：<em>¥'+data.bi_no_before+'</em></p>';
					html += '</div>';
					
					html += '<div class="ordermain">';
					var pre= "+";
					var bi1= "";
					var classSpan= "tip-success";
					if(data.bi_chu_or_ru == -1){
						 pre= "-";
						 classSpan= "tip-danger";
					}
					html += '<p>本次金额：<em><span class="'+ classSpan +'">'+ pre +'&nbsp;'+ data.bi_no +'</span></em></p>';
					html += '<p>操作后金额：<em>'+data.bi_no_after+'</em></p>';
					html += '</div>';
					
					html += '<div class="ordermain">';
					html += '<p>购买人：<em>'+ data.buy_name +'</em></p>';
					html += '</div>';
					html += '<div class="ordermain">';
					html += '<p>订单号：<em>'+ data.trade_index +'</em></p>';
					html += '</div>';
					
					html += '<div class="ordermain">';
					html += '<p>操作时间：<em>'+ data.add_date +'</em></p>';
					html += '</div>';
					
					html += '</div>';
					html += '</div>';
					html += '</li>';
					
				});
				page += 1;
				$("#appendMore").attr('data-pages',page);
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
				html = "<li>"+datas.msg+"</li>";
			}
		}
	});	
}

//]]></script>
</body>
</html>