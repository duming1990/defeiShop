<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!----------页脚开始---------->
<div class=" footer site-info-w J-br" style="padding-bottom:0px;padding-top: 15px;">
<!-- <div class="container"> -->
<!--  <div class="div7" style="width: 100%;"> -->
<!--      <div class="item"><span class="span1"></span>会员消费制</div> -->
<!--      <div class="item"><span class="span2"></span>海量源头产品</div> -->
<!--      <div class="item"><span class="span3"></span>源头直供</div> -->
<!--      <div class="item"><span class="span4"></span>精准扶贫</div> -->
<!--   </div> -->
<!-- </div> -->
<!--   <div class="site-outer"> -->
<!--     <div class="site-info"> -->
<%--     <c:forEach var="cur" items="${baseLinkList}" varStatus="vs"> --%>
<!--       <div class="site-info__item"> -->
<%--         <h3>${cur.title}</h3> --%>
<!--         <ul> -->
<%--         <c:forEach var="curSon" items="${cur.map.helpModuleChiList}"> --%>
<%--           <c:if test="${curSon.mod_url eq 'HelpInfo.do?method=single'}"> --%>
<%-- 		    <c:url var="url" value="/IndexHelpInfo.do?method=view&h_mod_id=${curSon.h_mod_id}" /> --%>
<%-- 		  </c:if> --%>
<%-- 		  <c:if test="${curSon.mod_url eq 'HelpInfo.do?method=list'}"> --%>
<%-- 		    <c:url var="url" value="/IndexHelpInfo.do?method=list&h_mod_id=${curSon.h_mod_id}" /> --%>
<%-- 		  </c:if> --%>
<%-- 		  <li><a title="${fn:escapeXml(curSon.mod_name)}"  href="${url}" target="_blank"> ${fn:escapeXml(curSon.mod_name)}</a> </li> --%>
<%--          </c:forEach> --%>
<!--         </ul> -->
<!--       </div> -->
<%--       </c:forEach> --%>
      
<!--       <div class="site-info__item site-info__item--service"> -->
<!--         <div class="site-info-service-content"> -->
<%--           <p class="contact-name"><img src="${ctx }/styles/imagesPublic/footer_qrcode_pic.gif" width="239" height="169"></p> --%>
<!--         </div> -->
<!--         <div style="margin: 0 2px 2px 100px"></div> -->
<!--       </div> -->
<!--       <div class="clear"></div> -->
<!--     </div> -->
<!--   </div> -->

  <div class="copyright" style="border-top: 1px solid #ddd;padding-bottom: 20px;">
    <p><a href="http://www.beian.gov.cn/portal/registerSystemInfo?domainname=defeibg.com" target="_blank" title="D&M">工信部备案：${app_beian_no} &nbsp;|&nbsp;${app_gongan_no}</a>&nbsp;|&nbsp;Copyright©2012-2015 ${app_name}&nbsp;${app_domain_no_www}</p>
    <p>${app_addr}&nbsp;|&nbsp;全国服务热线：${app_tel}&nbsp;|&nbsp;</p>
    <p>技术支持：<a href="http://www.defeibg.com" target="_blank" title="D&M">D&M</a></p>
    
  <p>
	  <a href="http://www.beian.gov.cn/portal/registerSystemInfo?domainname=defeibg.com" target="_blank">
	  	<img src="${ctx}/images/static.png" style="width:50px;margin:27px;"></img>
	  </a>
  </p>
	 </div>
  <!-- <div class="Box" style="margin: 15px 2px 2px 0">
	  <ul>
	      <li>
	       <script type="text/javascript" src="http://112.30.63.109:8887/resource/scripts/controller/common/businessLicense.js?id=348ec77d208540d7825372d3a8ab2b19"></script>
	      </li>
	  </ul>
	</div> -->
</div>
<!----------页脚结束---------->
<!---------右侧工具条开始----------->
<div class="component-rightbottom-sticky mt-component--booted" style="bottom: 20px; right: 10px;">
  <div class="new-index-triffle-w" style="bottom: 20px; right: 10px;">
    <div id="fixbar-item-top" style="bottom: 20px; right: 10px;"> <a class="J-go-top lift-nav new-index-triffle" href="#"> <i></i><span>回到顶部</span> </a> </div>
  </div>
</div>
<script>
$(function (){
})
</script>
	
<!-- footer end-->
<script type="text/javascript">
function loadAnaylytics(){
	var c=document.createElement("script");
	s=document.getElementsByTagName("script")[0];
	c.src="https://s95.cnzz.com/z_stat.php?id=1260412460&web_id=1260412460";
	s.parentNode.insertBefore(c,s);
}
$(function (){
        setTimeout('loadAnaylytics()', 3000); //延迟3秒加载,提高速度
    })
    
</script>
