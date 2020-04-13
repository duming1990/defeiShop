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
<div class="cate-nav" id="searchContent" style="display:none;">
  <div class="sortdrop-wrapper search-wrapper">
  <html-el:form action="/MMyBiDianZi" styleClass="searchForm">
  <html-el:hidden property="method" value="list" />
  <html-el:hidden property="mod_id" />
    <div class="set-site">
      <ul class="formUl">
        <li class="select"><span>转入或支出：</span>
           <html-el:select property="bi_chu_or_ru" styleClass="webinput" style="width:70%;">
                <html-el:option value="">全部</html-el:option>
                <html-el:option value="1">入</html-el:option>
                <html-el:option value="-1">出</html-el:option>
           </html-el:select>
        </li>
        <li class="select"><span>类型：</span>
           <html-el:select property="bi_get_type" styleClass="webinput" style="width:70%;">
                <html-el:option value="">全部</html-el:option>
                <c:forEach items="${biGetTypes}" var="keys">
                <html-el:option value="${keys.index}">${keys.name}</html-el:option>
                </c:forEach>
           </html-el:select>
        </li>
      </ul>
    </div>
    <div class="box submit-btn">
    <input type="submit" class="com-btn" id="search_submit" value="查询" /></div>
  </html-el:form>
 </div>
</div>
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
			        <c:if test="${(cur.bi_get_type eq 200) or cur.bi_get_type eq -90}">
			         <c:if test="${cur.bi_chu_or_ru eq 1}"><div>${cur.dest_uname}转给${cur.add_uname}</div></c:if>
			         <c:if test="${cur.bi_chu_or_ru eq -1}"><div>${cur.add_uname}转给${cur.dest_uname}</div></c:if>
			        </c:if>
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
			         <c:set var="bi1" value=""  />
			        <c:if test="${((cur.bi_get_type eq 200) or (cur.bi_get_type eq -90))}">
			        <c:if test="${cur.bi_chu_or_ru eq -1}">
			         <fmt:formatNumber var="bi_rate" pattern="0.########" value="${cur.bi_rate}" />
			         <fmt:formatNumber var="bisrc" pattern="0.########" value="${bi - cur.bi_rate}" />
			         <c:set var="bi1" value="<span title='手续费：${bi_rate}'>(${bisrc} + ${bi_rate})<span>"  />
			         </c:if>
			        </c:if>
			        <span class="${_class}">${pre}${bi}</span> 
	              </em></p>
	              <p>操作后金额：<em><fmt:formatNumber var="bi" pattern="0.########" value="${cur.bi_no_after}" />${bi}</em></p>
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
$(document).ready(function(){
	
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
		url: app_path + "/m/MMyBiDianZi.do?method=getListJson",
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
						 if(data.bi_get_type == 200 || data.bi_get_type == -90){
							 var bi_rate = data.bi_rate;
							 var bisrc = bi - cur.bi_rate;
							 bi1 = "<span title='手续费："+ data.bi_rate +"'>("+ data.bisrc +" + "+ data.bi_rate +")<span>";
						 }
					}
					html += '<p>本次金额：<em><span class="'+ classSpan +'">'+ pre +'&nbsp;'+ data.bi_no +'</span></em></p>';
					html += '<p>操作后金额：<em>'+data.bi_no_after+'</em></p>';
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