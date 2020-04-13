<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<c:if test="${not empty cc_kefu_id}">
<script>
function loadkefu(){
	var c=document.createElement("script");
	s=document.getElementsByTagName("script")[0];
	c.src="http://kefu.qycn.com/vclient/state.php?webid=${cc_kefu_id}";
	s.parentNode.insertBefore(c,s);
}
$(function (){
        setTimeout('loadkefu()', 3000); //延迟3秒加载,提高速度
    })
	</script>
</c:if>