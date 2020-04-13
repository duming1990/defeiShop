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
<link rel="stylesheet" type="text/css" href="${ctx}/m/styles/commInfo/css/list.css" />
<style type="text/css"></style>
</head>
<body id="body" style="background: #fff;">
<jsp:include page="../_header.jsp" flush="true" />
<c:url value="/m/MEntpInfo.do" var="url"/>
<form class="attrForm" method="get" action="${url}">
<input type="hidden" id="method" name="method" value="getCommList"/>
<input type="hidden" id="entp_id" name="entp_id" value="${af.map.entp_id}"/>
<input type="hidden" id="cls_id" name="cls_id" value="${af.map.cls_id}"/>
<input type="hidden" id="orderByParam" name="orderByParam"/>
<input type="hidden" name="comm_name_like" value="${af.map.comm_name_like}"/>
<input type="hidden" id="startRow" name="startRow" value="8"/>
<div class="row c-product-sort">
  <div class="small-4 left" data="1"> <a onclick="orderByParam('orderBySaleDesc');" class="tab"><i class="icon-volume"></i><span>销量</span></a> </div>
  <div class="small-4 left" data="2"> <a onclick="orderByParam('${fn:escapeXml(af.map.orderByParamForPrice)}');" class="tab"><i class="icon-price"></i><span>价格</span></a> </div>
  <div class="small-4 left" data="3"> <a onclick="orderByParam('orderByUpDateDesc');" class="tab"><i class="icon-new"></i><span>新品</span></a> </div>
</div>
</form>

<div class="row c-product-list">
  <div class="small-12">
  <c:if test="${empty commList}">
	          <div id="no_data" style="background:#fff; padding:15px; margin-bottom:10px;">暂无数据~</div>
	 </c:if>
  <c:if test="${not empty commList}">
    <ul class="small-block-grid-2">
     <c:forEach items="${commList}" var="cur">
        <c:url var="url" value="/m/MEntpInfo.do?id=${cur.id}"/>
      <li> 
      	<a href="${url}">
        	<div class="p-img"><img src="${ctx}/${cur.main_pic}" alt="" height="350" width="350"/></div>
        	<div class="p-info">${fn:escapeXml(cur.comm_name)}</div>
        	<div class="flag"><span class="price"><fmt:formatNumber value="${cur.sale_price}" pattern="￥ 0.00" /></span> </div>
        </a> 
      </li>
      </c:forEach>
    </ul>
    </c:if>
  </div>
</div>

	<div class="row base c-pagination" id="loadZt" style="display: none;">
		  <div class="small-12">
		   <div class="pagination">
		      <p style="display: none;" id="list-loading" class="list-loading"><span></span>加载中...</p>
		      <p style="display: none;" id="list-nomore" class="list-nomore"><span>已到尾页</span></p>
		   </div>
		 </div>
	</div>
	    
	<div class="row base c-pagination" id="page">
	  <div class="small-12">
	        <div class="pagination">
	            <a id="loadmore" href="javascript:void(0);" class="page-num">加载更多...
	            <i class="icon-down"></i>
	            </a>
	        </div>
	 </div>
	</div>
	
 <jsp:include page="../_footer.jsp" flush="true" />
<script type="text/javascript" src="${ctx}/m/scripts/mobiledialog/mobiledialog.js"></script> 
<script type="text/javascript" src="${ctx}/commons/scripts/jqueryMobile.cs.js"></script> 
<script type="text/javascript" src="${ctx}/m/scripts/owl-carousel/owl.carousel.min.js"></script> 
<script type="text/javascript">//<![CDATA[
var f = $(".attrForm").get(0);
$(document).ready(function(){
	
	$("#loadmore").click(function(){
		loadData();
	});
	
	var data_flag = '${data_flag}';
	
	$(".c-product-sort").find("div").each(function(index){
		var _index = index + 1;
		if(_index == data_flag){
		
			var orderByParamForPrice = "${af.map.orderByParamForPrice}";
			if("orderByPriceAsc" == orderByParamForPrice){
				$(this).find("a").removeClass("up");	
				$(this).find("a").addClass("down");	
			}else if("orderByPriceDesc" == orderByParamForPrice){
				$(this).find("a").removeClass("down");	
				$(this).find("a").addClass("up");	
			}
			
			$(this).find("a").addClass("active");
			$(this).siblings().find("a").removeClass("active");
		}
		
	});

});

function orderByParam(orderByParam) {
	$("#orderByParam").val(orderByParam);
	f.submit();
}

function loadData(){
	var level = $("#flag_comment").val();
	var startRow = parseInt($("#startRow").val());
	if(isNaN(startRow)){
		startRow = 0;
	}
	getData(level,startRow);
}

function setStartRow(){
	var startRow = parseInt($("#startRow").val());
	startRow = startRow + 8;
	$("#startRow").val(startRow);
}

function getData(level,startRow){
	$.ajax({
		type: "POST",
		url: "${ctx}/m/MEntpInfo.do",
		data: { "method":"getCommentInfo", "entp_id":"${af.map.entp_id}", "cls_id":"${af.map.cls_id}", "comm_name_like":"${af.map.comm_name_like}", "startRow":startRow},
		dataType: "json",
		sync: true,
		success: function(data) {
			if(null != data && '' != data){
				var list = data.list;
				if(null != list && list.length >0){
					var html="";
					$('#loadZt').show();
					$('#page').hide();
					$('.list-loading').show();
					for(x in list){
						html += '<li><a href="'+ app_path +'/m/MEntpInfo.do?id='+ list[x].comm_id +'">';
						html += '<div class="p-img"><img src="'+ app_path + '/'+ list[x].main_pic +'" alt="" height="350" width="350"></div>';
						html += '<div class="p-info">'+ list[x].comm_name +'</div>';
						html += '<div class="flag"><span class="price">￥ '+ list[x].min_price +'</span> </div>';
						html += ' </a></li>';
					}
					setStartRow();
					$(".small-block-grid-2").append(html);
					$('.list-loading').hide();
					$('#loadZt').hide();
					$('#page').show();
				}else{
					$('#list-loading').hide();
					$('#page').hide();
					$('#loadZt').show();
					$('#list-nomore').show();
				}
			}
		}
	});
}


//]]></script>
</body>
</html>