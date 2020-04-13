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
<link href="${ctx}/m/js/date/app1/css/date.css" rel="stylesheet" type="text/css" />
</head>
<body>
<jsp:include page="../_header.jsp" flush="true" />	
<div class="cate-nav" id="searchContent" style="display:none;">
  <div class="sortdrop-wrapper search-wrapper">
  <html-el:form action="/MMyQianBao" styleClass="searchForm">
  <html-el:hidden property="method" value="walletList" />
  <html-el:hidden property="mod_id" />
    <div class="set-site">
      <ul class="formUl">
        <li class="select"><span>红包类型：</span>
           <html-el:select property="bi_get_type" styleClass="webinput" style="width: 70%;">
                <html-el:option value="">全部</html-el:option>
                  <c:forEach var="cur" items="${biGetTypes}">
                  <html-el:option value="${cur.index}">${cur.name}</html-el:option>
                  </c:forEach>
           </html-el:select>
        </li>
        <li class="select"><span>时间：</span>
          <fmt:formatDate value="${begin_date}" pattern="yyyy-MM-dd" var="_add_birthday" />
          <input name="begin_date" type="text" id="begin_date" readonly="readonly" autocomplete="off" maxlength="38" value="${_add_birthday}" placeholder="起始时间" class="buy_input">
        </li>
      </ul>
    </div>
    <div class="box submit-btn">
    <input type="submit" class="com-btn" id="search_submit" value="查询" /></div>
  </html-el:form>
 </div>
</div>
<div class="content"> 
<div class="tipmsg">
<!--    	<p> -->
<%--    总积分：<b><fmt:formatNumber pattern="#0.########" value="${sum_score}" /></b><br/> --%>
<%--    	个人积分：<b><fmt:formatNumber pattern="#0.########" value="${userInfo.cur_score}" /></b><br/> --%>
<%--    		联盟积分：<b><fmt:formatNumber pattern="#0.########" value="${userInfo.user_score_union}" /></b> --%>
<!--    	</p> -->
</div>
<div class="list-view">
  <ul class="list-ul" id="ul_data">
     <c:if test="${empty UserBiRecordList}">
	          <div id="no_data" style="background:#fff; padding:15px; margin-bottom:10px;">亲！还未收到红包哦~~</div>
	 </c:if>
     <c:forEach var="cur" items="${UserBiRecordList}" varStatus="vs">
      	<li>
      		<div class="list-item">
      		<c:url var="url" value="/m/Index.do?method=HbIndex&user_bi_record=${cur.id}" />
	          <div class="info" onclick="goUrl('${url}')">
	            <div class="ordermain">
	              <p >红包类型：
	                <c:forEach items="${biGetTypes}" var="keys">
			          <c:if test="${cur.bi_get_type eq keys.index}">${keys.name}</c:if>
			        </c:forEach>
	              </p>
	              <p>来源：${cur.map.laiyuan}</p>
	            </div>
	            <div class="ordermain">
	            <c:if test="${cur.bi_get_type eq 130}">
	              <p style="width: 50%;">余额：<em>
	               <fmt:formatNumber value="${cur.bi_no}" pattern="#0.00" type="number"/>
	              </em>元</p>
	             </c:if>
	             <c:if test="${cur.bi_get_type ne 130}">
	              <p style="width: 50%;">金额：<em>
	              <fmt:formatNumber value="${cur.bi_no}" pattern="#0.00" type="number"/>
	              </em>元</p>
	              <p>获得时间：<em>
		          <fmt:formatDate value="${cur.add_date}" pattern="yyyy-MM-dd HH:mm" />
	              </em></p>
	             </c:if>
	            </div>
	            <c:if test="${cur.bi_get_type eq 130}">
	            <div class="ordermain">
	              <p style="width: 50%;">复销券：<em>
	              <fmt:formatNumber value="${cur.fuxiao_no}" pattern="#0.00" type="number"/>
	              </em>元</p>
	              <p>获得时间：<em>
		          <fmt:formatDate value="${cur.add_date}" pattern="yyyy-MM-dd HH:mm" />
	              </em></p>
	            </div>
	            </c:if>
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
<script type="text/javascript" src="${ctx}/m/js/date/app1/js/date.js?v20160406"></script>
<script type="text/javascript" src="${ctx}/scripts/iscroll/iscroll-5.1.2.min.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
 var topBtnUrl = "${ctx}/m/MMyScore.do?method=add&mod_id=${af.map.mod_id}";
 setTopBtnUrl(topBtnUrl);
 
 var d_year = ${d_year};
 $('#begin_date').datePicker({
     beginyear: 1920,
     endyear: d_year,
     theme: 'date',
 });
});

function appendMore() {
	getData();
}
function getData() {
	Common.loading();
	var page = $("#appendMore").attr('data-pages');
	page = Number(page);
	$.ajax({
		type: "POST",
		url: app_path + "/m/MMyQianBao.do?method=getListJson",
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
						var url = "${ctx}/m/Index.do?method=HbIndex&user_bi_record=" + data.user_bi_record+"&begin_date="+begin_date;  
					html += '<li>';
					html += '<div class="list-item">';
					html += '<div class="info" onclick="goUrl(\''+url+'\')">';
					
					html += '<div class="ordermain">';
					html += '<p>红包类型：'+data.bi_get_type_name+'</p>';
					html += '<p>来源：'+data.laiyuan+'</p>';
					html += '</div>';
					
					html += '<div class="ordermain">';
					html += '<p style="width: 50%;">金额：<em>'+data.wallet_money+'</em>元</p>';
					html += '<p>获得时间：<em>'+data.add_date+'</em></p>';
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