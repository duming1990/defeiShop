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
  <html-el:form action="/MMyScore" styleClass="searchForm">
  <html-el:hidden property="method" value="list" />
  <html-el:hidden property="mod_id" />
    <div class="set-site">
      <ul class="formUl">
        <li class="select"><span>积分获取类型：</span>
           <html-el:select property="score_type" styleClass="webinput" style="    width: 70%;">
                <html-el:option value="">全部</html-el:option>
                  <c:forEach var="cur" items="${scoreTypes}">
                  <html-el:option value="${cur.index}">${cur.name}</html-el:option>
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
<div class="tipmsg">
   	<p>
   个人积分：<b><fmt:formatNumber pattern="#0.########" value="${userInfo.cur_score}" /></b>
    <span style="margin-left: 20%;">联盟积分：</span><b><fmt:formatNumber pattern="#0.########" value="${userInfo.user_score_union}"/></b></br>
   总积分：<b><fmt:formatNumber pattern="#0.########" value="${sum_score}" /></b>
   <b style="margin-left: 20%;">当前等级为
   <c:if test="${user_info.user_type eq 2}">
      <c:if test="${not empty user_info.user_level}">
          ${cur_level_name}
      </c:if>
      <c:if test="${empty user_info.user_level}">
                    普通会员
      </c:if>
    </c:if>
   <span class="gs-gqul"><span class="l${user_level} app_logout" style="margin-left: 0.1rem;"></span></span></b>
   </br>
    ${next_level_name}
    <c:if test="${not empty next_level_score}">
    最低积分为：<b><fmt:formatNumber pattern="#0.########" value="${next_level_score}" /></b>
    </c:if>，
    
    <c:if test="${is_upgrade eq 0}"><b onclick="upgradeRule();">不能升级</c:if>	
    <c:if test="${is_upgrade eq 1}"><b>可以升级</c:if>
    <c:if test="${is_upgrade eq 2}"><b>您的等级已最高</c:if></b>
    
    <c:if test="${is_upgrade ne 2}">
<!--     三级及其以下 -->
<%--     <c:if test="${cur_user_level le 2}"> --%>
<!--     </br>需满足以下条件其一可升级： -->
<!--     </br>个人积分升级还需：<b> -->
<%--     <c:if test="${not empty person_score_need}"><fmt:formatNumber pattern="#0.########" value="${person_score_need}" /></c:if> --%>
<%--     <c:if test="${is_upgrade eq 1}"><c:if test="${empty person_score_need}">已满足升级条件</c:if></c:if></b> --%>

<!--     </br>联盟积分升级还需：<b> -->
<%--     <c:if test="${not empty union_score_need}"><fmt:formatNumber pattern="#0.########" value="${union_score_need}" /></c:if> --%>
<%--     <c:if test="${is_upgrade eq 1}"><c:if test="${empty union_score_need}">已满足升级条件</c:if></c:if></b> --%>
<%--     </c:if> --%>
<!--     三级以上 -->
	<c:if test="${cur_user_level gt 2}">
		<c:if test="${empty upgrade_defeated}">
		    </br>直推联盟总积分升级还需：<b>
		    <c:if test="${not empty union_score_need}"><fmt:formatNumber pattern="#0.########" value="${union_score_need}"/></c:if>
		<!--     </br>（直推会员去掉一个最高分 -->
		<%--     ${top_user_name}，个人积分+直推会员总积分大于等于 --%>
		<%--     <fmt:formatNumber pattern="#0.########" value="${next_level_score}" />*${user_score_union_up_scale}即可升级） --%>
		    <c:if test="${empty union_score_need}">已满足升级条件</c:if></b>   
	    </c:if>
 	</c:if>   
    </c:if>
   	</p>
</div>
<div class="list-view">
  <ul class="list-ul" id="ul_data">
  <c:if test="${empty userScoreRecordList}">
	          <div id="no_data" style="background:#fff; padding:15px; margin-bottom:10px;">暂无数据~</div>
	 </c:if>
     <c:forEach var="cur" items="${userScoreRecordList}" varStatus="vs">
      	<li>
      		<div class="list-item">
	          <div class="info">
	            <div class="ordermain">
	              <p>积分类型：
	                <c:forEach items="${scoreTypes}" var="keys">
			          <c:if test="${cur.score_type eq keys.index}">${keys.name}</c:if>
			        </c:forEach>
	              </p>
	              <span class="gs-gqul" style="margin-right: 1rem;"><span class="l${user_level} app_logout"></span></span>
	            </div>
	            <div class="ordermain">
	              <p>操作前积分：<em>
	              <fmt:formatNumber var="jifen" value="${cur.hd_score_before}" pattern="0.########"/>
	              ${jifen}
	              </em></p>
	              <p>本次积分：<em>
	              
	              <fmt:formatNumber var="jifen" value="${cur.hd_score}" pattern="0.########"/>
	              ${jifen}
	              
<%-- 	              <c:if var="score_flag" test="${not empty cur.link_id and (cur.score_type eq 1 or cur.score_type eq 4)}"><a href="javascript:cardInfoView('${cur.link_id}');"> --%>
<%-- 	               <fmt:formatNumber var="jifen" value="${cur.hd_score}" pattern="0.########"/> --%>
<%-- 	              ${jifen} --%>
<%-- 	              </a></c:if> --%>
<%--       			<c:if test="${!score_flag}"> --%>
<%--       			</c:if> --%>
      			
	              </em></p>
	            </div>
	            <div class="ordermain">
	              <p>操作后积分：<em>
	              <fmt:formatNumber var="jifen" value="${cur.hd_score_after}" pattern="0.########"/>
	              ${jifen}
	              </em></p>
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
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
 var topBtnUrl = "${ctx}/m/MMyScore.do?method=add&mod_id=${af.map.mod_id}";
 setTopBtnUrl(topBtnUrl);
});

function upgradeRule() {
	
	$.jBox.alert("三星以下（包括三星）不做考核，总积分达到标准，系统考核后升级。</br>三星以上考核内容："
			+"</br>一、个人消费积分达到升级最低标准，系统考核后升级。"
			+"</br>二、个人积分不足以升级，满足以下条件："
			+"</br>1、总积分（个人积分+联盟积分）达到升级最低要求。"
			+"</br>2、 （个人积分  +  直推一级中去掉一个最高分之和  ）  ≥  星级标准*25% "
			+"</br>三、考核时间:2次/月，中旬（16号凌晨），月底(1号凌晨)。", '升级规则');
}

function appendMore() {
	getData();
}
function getData() {
	Common.loading();
	var page = $("#appendMore").attr('data-pages');
	page = Number(page);
	$.ajax({
		type: "POST",
		url: app_path + "/m/MMyScore.do?method=getListJson",
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
					html += '<p>积分类型：'+data.score_type_name+'</p>';
					html += '</div>';
					
					html += '<div class="ordermain">';
					html += '<p>操作前积分：<em>'+data.hd_score_before+'</em></p>';
					html += '<p>本次积分：<em>'+data.hd_score+'</em></p>';
					html += '</div>';
					
					html += '<div class="ordermain">';
					html += '<p>操作后积分：<em>'+data.hd_score_after+'</em></p>';
					html += '<p>操作时间：<em>'+data.add_date+'</em></p>';
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