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
<body >
<jsp:include page="../_header.jsp" flush="true" />
<div class="content">
<c:set var="cl_all" value="cur" />
  <c:set var="cl_1" value="" />
  <c:set var="cl_2" value="" />
  <c:if test="${empty af.map.sc_type}">
  	  <c:set var="cl_all" value="cur" />
	  <c:set var="cl_1" value="" />
	  <c:set var="cl_2" value="" />
  </c:if>
  <c:if test="${af.map.sc_type eq 1}">
  	  <c:set var="cl_all" value="" />
	  <c:set var="cl_1" value="cur" />
	  <c:set var="cl_2" value="" />
  </c:if>
  <c:if test="${af.map.sc_type eq 2}">
  	  <c:set var="cl_all" value="" />
	  <c:set var="cl_1" value="" />
	  <c:set var="cl_2" value="cur" />
  </c:if>
  <div class="myorder-ors">
  	<a href="${ctx}/m/MMyFav.do?mod_id=${af.map.mod_id}" id="ors_" class="${cl_all}">全部</a>
	<a href="${ctx}/m/MMyFav.do?mod_id=${af.map.mod_id}&sc_type=1" id="ors_1" class="${cl_1}">店铺</a>
	<a href="${ctx}/m/MMyFav.do?mod_id=${af.map.mod_id}&sc_type=2" id="ors_2" class="${cl_2}">商品</a>
  </div>
  <!--article-->
  <div class="list-view">
    <ul class="list-ul" id="ul_data">
      <!-- 未付款、已付款、待评价、物流单、退款单  start -->
      <c:if test="${empty entityList}">
          <div id="no_data" style="background:#fff; padding:15px; margin-bottom:10px;">暂无数据~</div>
      </c:if>
               
      <c:forEach items="${entityList}" var="cur">
             <c:if test="${ cur.sc_type eq 2}">
       	<c:set var="url" value="${ctx}/m/MEntpInfo.do?id=${cur.link_id}" />
       </c:if>
  		<c:if test="${ cur.sc_type eq 1}">
       		<c:set var="url" value="${ctx}/m/MEntpInfo.do?method=index&entp_id=${cur.link_id}" />
        </c:if>
        <li class="li_${cur.id}" data-id="${cur.id}" data-trade="${cur.id}">
          <div class="list-item">
            <div class="info">
              <div class="ordermain" onclick="location.href=${url}">
                 <a href="${url}"><p>店铺或商品名称：<em>
                  ${fn:escapeXml(cur.title_name)}
                </em></p></a>
              </div>
              <div class="ordermain">
                <p>收藏时间：<em>
                  <fmt:formatDate value="${cur.add_date}" pattern="yyyy-MM-dd" />
                  </em></p>
              </div>
              <div class="orderedit"> 
              <span></span>
	              <span> <a class="buy" onclick="delClick('${cur.id}');" >删除</a> </span> 
	          </div>
            </div>
          </div>
          </li>
      </c:forEach>
      <!-- 未付款、已付款、待评价、物流单、退款单  end -->
    </ul>
  </div>
  <!-- 延迟加载交互提示 -->
  <div class="list-other-more">
    <c:set var="display" value="none" />
    <c:if test="${appendMore eq 1}">
      <c:set var="display" value="block" />
    </c:if>
    <div class="load-more btnpage" style="display: ${display};" id="appendMore" onClick="appendMore()" data-pages="1">查看更多</div>
  </div>
  <form id="bottomPageForm" name="bottomPageForm" method="post" action="${ctx}/m/MMyFav.do">
    <input type="hidden" name="method" value="list" />
    <input type="hidden" name="order_type" value="${af.map.sc_type}" />
  </form>
  <!-- ——page -->
</div>
<jsp:include page="../_footer.jsp" flush="true" />
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/order/order.js"></script>
<script type="text/javascript">//<![CDATA[
function delClick(id){
	
	Common.confirm("是否确认删除?",["确定","取消"],function(){
		$.post("?method=delete&id="+id, 
				function(datas) {
				if (datas.ret != 0) {
					mui.toast('恭喜删除成功！',2000);
					setTimeout(function(){
						window.location.reload();
					},1000); 
				} else {
					mui.toast('对不起，删除失败！');
				}
		});
	},function(){
	});
}
                                          
var ctx = "${ctx}";

function appendMore() {
	getData();
}

function getData() {
	Common.loading();
	var page = $("#appendMore").attr('data-pages');
	page = Number(page);
	$.ajax({
		type: "POST",
		url: ctx + "/m/MMyFav.do?method=getScEntpCommListJson",
		data: 'startPage=' + page +'&' + $(".bottomPageForm").serialize(),
		dataType: "json",
		error: function(request, settings) {},
		success: function(datas) {
			var html = "";
			$("#appendMore").hide();
			Common.hide();
			if (datas.ret == 1) {
				var dataList = eval(datas.dataList);
				$.each(dataList, function(i,data){  
					var url = ctx + "/m/MMyFav.do?id=" + data.id;
					var order_logo = ctx + "/styles/imagesPublic/user_header.png";
					html += '<li class="li_"' + data.id +' data-id="'+ data.id +'" data-trade="'+ data.id +'">';
					html += '<div class="list-item list-room">';
// 					html += '<div class="pic"> <img src="'+ order_logo +'" original="'+ order_logo +'" alt="" style="display: inline;"> <i class="status"><b>'+ data.order_state_name +'</b></i> </div>';
					html += '<div class="info">';
					html += '<div class="ordermain">';
					html += '<p>主题：<em>'+data.msg_title+'</em></p>';
					html += '<p>是否已读：<em>';
					if(data.is_read == 1) {
						html += '<span class="label label-success">已读</span>';
					} else {
						html += '<span class="label label-danger">未读</span>';
					}
					html += '</em></p>';
					html += '</div>';
					html += '<div class="ordermain">';
					html += '<p>发送人：<em>'+data.user_name+'</em></p>';
					html += '<p>发送时间：<em>'+data.send_time+'</em></p>';
					html += '</div>';
					html += '<div class="orderedit"><span> <a href="'+ctx+'/m/MMyFav.do?method=view&id='+data.id+'" class="buy">查看详情</a> </span> &nbsp;&nbsp; <span> <a class="buy" onclick="delClick(\''+data.id+'\');" >删除</a> </span></div>';
					html += '</div>';
					html += '</div>';
					html += '<a href="" class="btn-combg"></a> ';
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
