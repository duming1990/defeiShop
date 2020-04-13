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
  <html-el:form action="/MMyJifen" styleClass="searchForm">
  <html-el:hidden property="method" value="list" />
  <html-el:hidden property="mod_id" />
    <div class="set-site">
      <ul class="formUl">
        <li class="select"><span>类型：</span>
           <html-el:select property="jifen_type" styleClass="webinput" style="width:85%;">
                <html-el:option value="">全部</html-el:option>
                  <c:forEach var="cur" items="${jifenTypes}">
                  <html-el:option value="${cur.index}">${cur.showName}</html-el:option>
                  </c:forEach>
           </html-el:select>
        </li><span>卡号：</span>
        <html-el:text property="card_no" styleId="card_no" styleClass="webinput"/>
        <li>
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
<fmt:formatNumber var="bi_jifen_sum" pattern="0.########" value="${bi_jifen}" />
 <fmt:formatNumber var="bi_dianzibi_sum" pattern="0.########" value="${bi_dianzi}" />
   	<p>
   	累计金额 <b>${bi_dianzibi_sum} </b>元	</p>
  </div>
  <div class="list-view">
    <ul class="list-ul" id="ul_data">
      <c:forEach var="cur" items="${userJifenRecordList}" varStatus="vs">
        <li>
          <div class="list-item">
            <div class="info">
              <div class="ordermain">
                <p>类型：
                  <c:forEach var="jt" items="${jifenTypes}">
                    <c:if test="${jt.index eq  cur.jifen_type}">${jt.showName}</c:if>
                  </c:forEach>
                </p>
                <p>返现卡:<em> ${cur.card_no} </em></p>
              </div>
              <div class="ordermain">
                <p>本次积分：<em>
                  <fmt:formatNumber pattern="0" value="${cur.opt_c_score}" />
                  </em></p>
                <p>本次金额：<em>
                  <fmt:formatNumber pattern="0.########" value="${cur.opt_c_dianzibi}" />
                  </em></p>
              </div>
              <div class="ordermain">
                <p>操作时间：<em>
                  <fmt:formatDate value="${cur.add_date}" pattern="yyyy-MM-dd HH:mm" />
                  </em></p>
                <p>持卡人：<em> ${fn:escapeXml(cur.map.card_user_name)} </em></p>
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
    <div class="load-more btnpage" style="display:${display};" id="appendMore" onClick="appendMore()" data-pages="1">查看更多</div>
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
		url: app_path + "/m/MMyJifen.do?method=getListJson",
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
					html += '<p>类型：'+data.jifen_type+'</p>';
					html += '<p>返现卡:<em>'+data.card_no+'</em></p>';
					html += '</div>';
					
					html += '<div class="ordermain">';
					html += '<p>本次积分：<em>'+data.opt_c_score+'</em></p>';
					html += '<p>本次金额：<em>'+data.opt_c_dianzibi+'</em></p>';
					html += '</div>';
					
					html += '<div class="ordermain">';
					html += '<p>操作时间：<em>'+data.add_date+'</em></p>';
					html += '<p>持卡人：<em>'+data.card_user_name+'</em></p>';
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
