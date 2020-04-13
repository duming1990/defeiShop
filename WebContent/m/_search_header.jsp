<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<header>
<div class="new-header" id="_swBox"><a href="javascript:pageBack();" class="new-a-back"><span>返回</span></a> 
<a href="javascript:void(0)" id="btnSwBox" class="new-srch-box2" style="overflow: hidden;">${af.map.search}</a> 
<a id="btnSwkey" onclick="$('#swkey').slideToggle();" class="new-a-jd"><span>${app_name_min}键</span></a> </div>

  <div class="new-header" id="_swSearch" style="display:none">
      <input name="htype" id="htype" value="0" type="hidden"/>
      <a href="javascript:void(0)" id="_cancelSearch" class="new-a-cancel">取消</a>
      <div class="new-srch-box new-srch-box-v1">
        <input type="text" class="new-srch-input" name="keyword" id="keyword" value="${af.map.search}">
        <a href="javascript:void(0);" target="_self" onClick="cancelHotWord()" class="new-s-close new-s-close-v1"></a>
        <a target="_self" class="new-s-srch" id="searchbtn"><span></span></a>
        <div class="new-srch-lst" id="shelper" style="display:none">
        </div>
      </div>
  </div>

<div class="new-jd-tab" id="swkey" style="display:none;">
  <div class="new-tbl-type">
    <input type="hidden" id="flag" value="${flag}" />
    <a class="new-tbl-cell" href="index.do"> 
    <span class="icon">首页</span><br/>
    <span style="color:#6e6e6e;">首页</span></a> 
    
    <a class="new-tbl-cell" href="MCategory.do"> 
    <span class="icon2">分类搜索</span><br/>
    <span style="color:#6e6e6e;">分类搜索</span></a> 
    
    <c:if test="${empty userInfo}">
    <c:url var="url" value="/m/MIndexLogin.do" />
    <a class="new-tbl-cell" onclick="toLogin('${url}');"> 
    <span class="icon3">购物车</span><br/>
    <span style="color:#6e6e6e;">购物车</span></a> 
    </c:if>
    <c:if test="${not empty userInfo}">
    <c:url var="url" value="/m/MMyCartInfo.do" />
    <a href="${url}" class="new-tbl-cell"> 
    <span class="icon3">购物车</span><br/>
    <span style="color:#6e6e6e;">购物车</span></a> 
    </c:if>
    
    <c:if test="${empty userInfo}">
    <c:url var="url" value="/m/MIndexLogin.do" />
    <a class="new-tbl-cell" onclick="toLogin('${url}');">
     <span class="icon4">我的${app_name_min}</span><br/>
    <span style="color:#6e6e6e;" class="on">我的${app_name_min}</span></a>
    </c:if>
    <c:if test="${not empty userInfo}">
    <a href="MMyHome.do" class="new-tbl-cell">
     <span class="icon4">我的${app_name_min}</span><br/>
    <span style="color:#6e6e6e;" class="on">我的${app_name_min}</span></a>
    </c:if>
  </div>
</div>
</header>
<script type="text/javascript">//<![CDATA[
$(function(){

		$("#searchbtn").click(
				function() {
					var htype = $("#htype").val();
					location.href = "${ctx}/m/MSearch.do?htype=" + htype
							+ "&keyword=" + $("input[name='keyword']").val();
				});
		$("#keyword").keyup(function(e) {
			var htype = $("input[name='htype']").val();
			var keyword = $("input[name='keyword']").val();
			if (e.keyCode == 13) {
				location.href = "${ctx}/m/MSearch.do?htype=" + htype
						+ "&keyword="
						+ keyword;
			}
			if(($(this).length) > 0){
				var url = "${ctx}/CsAjax.do?method=getCommInfoListForMobileSeach&jsoncallback=?"; 
					$.getJSON(url,
					{htype:htype,keyword:keyword},
					function(datas) {
						var html = "";
						if (datas[0].ret == 1) {
							var dataList = datas[0].dataLoadList;
							html += '<ul>';
							$.each(dataList, function(i,data){
								html += '<li><a href="MEntpInfo.do?id='+ data.comm_id +'">'+data.comm_name+'</a></li>';
							});
							html += '</ul>';
							html += '<div class="new-tbl-type"><a href="javascript:void(0)" onclick="closeTip()" class="new-tbl-cell">关闭</a></div>'; 
							$("#shelper").html(html);
			                $("#shelper").show()
						}
				})
			}
		});
	});

	function cancelHotWord() {
		$("#keyword").val("");
		$("#keyword").focus()
	}

	$("#_cancelSearch").click(function() {
		$("#_swSearch").hide();
		$("#_swBox").show()
	});

	$("#btnSwBox").click(function() {
		$("#_swBox").hide();
		$("#_swSearch").show();
		$("#keyword").focus()
	})
	
	function closeTip() {
    $("#shelper").html("");
    $("#shelper").hide()
	}
	//]]>
</script>
