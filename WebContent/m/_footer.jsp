<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<style>
#cartNum{
    color: #fff;
    background-color: #dd524d;
    font-size: 12px;
    line-height: 1;
    display: inline-block;
    padding: 3px 6px;
    border-radius: 100px;
    position: absolute;
    top: 0.05rem;
    right: 23%;
    }
</style>
<footer class="aui-footer-default aui-footer-fixed" id="footer" style="z-index:888;"> 
	<c:url var="url" value="/m/Index.do" />
	<a class="aui-footer-item" data-url="${url}">
		<span class="aui-footer-item-icon aui-icon aui-footer-icon-home"></span>
		<span class="aui-footer-item-text">首页</span>
	</a>
	<c:url var="url" value="/m/MCategory.do" />
	<a class="aui-footer-item" data-url="${url}">
		<span class="aui-footer-item-icon aui-icon aui-footer-icon-class"></span>
		<span class="aui-footer-item-text">分类</span>
	</a>
	<c:url var="url" value="/m/MCityCenter.do" /> 
	<a class="aui-footer-item" data-url="${url}">
		<span class="aui-footer-item-icon aui-icon aui-footer-icon-find"></span>
		<span class="aui-footer-item-text">社区</span>
	</a>
	<c:url var="url" value="/m/MMyCartInfo.do" />
	<c:if test="${isApp}">
	 <c:url var="url" value="/m/MMyCartInfo.do?isApp=true" />
	</c:if>
	<a class="aui-footer-item" data-url="${url}">
		<span class="aui-footer-item-icon aui-icon aui-footer-icon-car"></span>
		<span class="aui-footer-item-text">购物车<span id="cartNum">0</span></span>
	</a>
	<c:url var="url" value="/m/MMyHome.do" />
	<a class="aui-footer-item" data-url="${url}">
		<span class="aui-footer-item-icon aui-icon aui-footer-icon-me"></span>
		<span class="aui-footer-item-text">我的</span>
	</a>
</footer>
<script>
$(document).ready(function(){
	$.ajax({
		type: "POST" , 
		url: "${ctx}/CsAjax.do", 
		data:"method=getCartNum",
		dataType: "json", 
	    async: true, 
	    error: function (request, settings) {alert(" 数据加载请求失败！ ");}, 
	    success: function (data) {
			if (data.ret == 1) {
				$("#cartNum").text(data.sumPdCount);
			} 
	    }
	});
});
var foot_num = localStorage.getItem('foot_num');

if(foot_num!=null){
	var foot=$("#footer").find("a:eq("+foot_num+")");
	foot.addClass("aui-footer-active");
}else{
	var foot=$("#footer").find("a:eq(0)");
	foot.addClass("aui-footer-active");
}

$("#footer a").each(function(i){
	 $(this).click(function(){
		 var url = $(this).attr("data-url");
		 localStorage.setItem('foot_num',i);
		 location.href=url;
	 })
});
</script>

