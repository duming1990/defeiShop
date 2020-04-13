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
      <c:forEach items="${entityList}" var="cur">
       <c:url var="url" value="/m/MTiXianDianZiBi.do?method=view&id=${cur.id}&mod_id=${af.map.mod_id}"/>
      	<li onclick="goUrl('${url}')">
      		<div class="list-item list-room">
	          <div class="info">
	            <div class="ordermain">
	              <p>申请时间：<em><fmt:formatDate value="${cur.add_date}" pattern="yyyy-MM-dd HH:mm:ss" /></em></p>
	            </div>
	            <div class="ordermain">
	              <p>提现金额：<em>¥${fn:escapeXml(cur.cash_count)}</em></p>
	            </div>
	            <div class="ordermain">
	              <p>手续费：<em><fmt:formatNumber var="bi" value="${fn:escapeXml(cur.cash_rate)}" pattern="0.00" />${bi}元</em></p>
	              <p>实际金额：<em>${fn:escapeXml(cur.cash_pay)}元</em></p>
	            </div>
	            <div class="ordermain">
	              <p>审核状态：<em>
		            <c:choose>
		              <c:when test="${cur.info_state eq 0}"><span class="label label-default">未审核</span></c:when>
		              <c:when test="${cur.info_state eq 1}"><span class="label label-info">已审核(待付款)</span></c:when>
		              <c:when test="${cur.info_state eq -1}"><span class="label label-danger">审核不通过(已退款)</span></c:when>
		              <c:when test="${cur.info_state eq 2}"><span class="label label-success">已付款</span></c:when>
		              <c:when test="${cur.info_state eq -2}"><span class="label label-danger">已退款</span></c:when>
		              <c:otherwise>未知</c:otherwise>
		            </c:choose>
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
    <div class="load-more btnpage" style="display: ${display};" id="appendMore" onclick="appendMore()" data-pages="1">查看更多</div>
  </div>
  
</div>
<jsp:include page="../_footer.jsp" flush="true" />
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
 var cash_type="${af.map.cash_type}";
 if(cash_type = 40){
	 var topBtnUrl = "${ctx}/m/MTiXianDianZiBi.do?method=addWelfare&mod_id=${af.map.mod_id}&cash_type="+cash_type;
 }else{
	 var topBtnUrl = "${ctx}/m/MTiXianDianZiBi.do?method=add&mod_id=${af.map.mod_id}&cash_type="+cash_type;
 }
 setTopBtnUrl(topBtnUrl);
});

function appendMore() {
	getData();
}
function getData() {
	var cash_type="${af.map.cash_type}";
	Common.loading();
	var page = $("#appendMore").attr('data-pages');
	page = Number(page);
	$.ajax({
		type: "POST",
		url: app_path + "/m/MTiXianDianZiBi.do?method=getListJson",
		data: 'startPage=' + page +'&cash_type='+cash_type+'&' + $(".bottomPageForm").serialize(),
		dataType: "json",
		error: function(request, settings) {},
		success: function(datas) {
			var html = "";
			$("#appendMore").hide();
			Common.hide();
			if (datas.ret == 1) {
				var dataList = eval(datas.dataList);
				$.each(dataList, function(i,data){  
					var url = app_path + "/m/MTiXianDianZiBi.do?method=view&id="+ data.id +"&mod_id=${af.map.mod_id}";
					html += '<li onclick="goUrl('+ url +')">';
					html += '<div class="list-item list-room">';
					html += '<div class="info">';
					html += '<div class="ordermain">';
					html += '<p>申请时间：<em>'+data.add_date+'</em></p>';
					html += '</div>';
					
					html += '<div class="ordermain">';
					html += '<p>提现金额：<em>¥'+data.cash_count+'</em></p>';
					html += '</div>';
					
					html += '<div class="ordermain">';
					html += '<p>手续费：<em>'+data.bi+'%</em></p>';
					html += '<p>实际金额：<em>'+data.cash_pay+'</em></p>';
					html += '</div>';
					
					html += '<div class="ordermain">';
					html += '<p>审核状态：<em>';
					var info_state = data.info_state;
					if(info_state == 0){html += '<span class="label label-default">未审核</span>';}
					if(info_state == 1){html += '<span class="label label-info">已审核(待付款)</span>';}
					if(info_state == -1){html += '<span class="label label-danger">审核不通过</span>';}
					if(info_state == 2){html += '<span class="label label-success">已付款</span>';}
					html += '</em></p>';
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