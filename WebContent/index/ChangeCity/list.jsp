<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>切换区域 - ${app_name}</title>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<jsp:include page="../../_public_header.jsp" flush="true" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/change-city.css?v=20160122"  />
<style type="">
#bd{
    width: 1200px;
    top:0px;
}
</style>
</head>
<body>
<jsp:include page="../../_header.jsp" flush="true" />
<div id="bdw" class="bdw">
  <div id="bd" class="cf">
    <div class="choosecities">
      <div class="city_province">
        <html-el:form styleClass="changeCity" action="/ChangeCity.do" method="post">
         <html-el:hidden property="method" value="selectChangeCity" />
         <html-el:hidden property="p_index" styleId="p_index" />
         <h2 class="enter-city" style="border-right:none;">
          <span>进入:</span>
          <c:url var="url" value="/ChangeCity.do?method=selectChangeCity&p_index=${quanguo_p_index}" />
          <a href="${url}">${quanguo_p_name}</a>
          <span>您所在城市:</span>
          <a id="local_p_index">定位中...</a>
         </h2>
<!--           <span class="label">按省份选择：</span> <span class="province-city-select"> -->
<!--           <select name="province" class="ui-select-small" id="province"> -->
           
<!--           </select> -->
<!--           <select name="city" class="ui-select-small" id="city"> -->
<!--           </select> -->
<!--           </span> -->
<!--           <input class="btn btn-mini" type="submit" value="确定"/> -->
        </html-el:form>
      </div>
<!--       <div class="city_list"> -->
<!--         <h3>常用城市：</h3> -->
<!--         <p><a href="http://sh.meituan.com" gaevent="changecity/hot1">上海</a><a href="http://bj.meituan.com" gaevent="changecity/hot2">北京</a><a href="http://gz.meituan.com" gaevent="changecity/hot3">广州</a><a href="http://sz.meituan.com" gaevent="changecity/hot4">深圳</a><a href="http://wh.meituan.com" gaevent="changecity/hot5">武汉</a><a href="http://tj.meituan.com" gaevent="changecity/hot6">天津</a><a href="http://xa.meituan.com" gaevent="changecity/hot7">西安</a><a href="http://nj.meituan.com" gaevent="changecity/hot8">南京</a><a href="http://hz.meituan.com" gaevent="changecity/hot9">杭州</a><a href="http://cd.meituan.com" gaevent="changecity/hot10">成都</a><a href="http://cq.meituan.com" gaevent="changecity/hot11">重庆</a><a href="http://su.meituan.com" gaevent="changecity/hot12">苏州</a><a href="http://wx.meituan.com" gaevent="changecity/hot13">无锡</a><a href="http://kunshan.meituan.com" gaevent="changecity/hot14">昆山</a><a href="http://cz.meituan.com" gaevent="changecity/hot15">常州</a><a href="http://nb.meituan.com" gaevent="changecity/hot16">宁波</a><a href="http://hf.meituan.com" gaevent="changecity/hot17">合肥</a><a href="http://chs.meituan.com" gaevent="changecity/hot18">长沙</a><a href="http://jx.meituan.com" gaevent="changecity/hot19">嘉兴</a><a href="http://xm.meituan.com" gaevent="changecity/hot20">厦门</a></p> -->
<!--       </div> -->
      <div class="citieslist">
        <h2>按拼音首字母选择<span class="arrow">»</span></h2>
        <ol class="hasallcity">
        <c:forEach var="cur" items="${entityList}">
          <li>
            <p class="cf">
            <span class="label"><strong>${fn:escapeXml(cur.p_alpha)}</strong></span>
            <span>
            <c:forEach var="curSon" items="${cur.map.tempSonList}">
            <c:url var="url" value="/ChangeCity.do?method=selectChangeCity&p_index=${curSon.p_index}" />
            <c:if test="${not empty has_weihu}">
            <c:url var="url" value="/IndexTsg.do?method=index&p_index=${curSon.p_index}" />
            </c:if>
            <a class="isonline" href="${url}">${fn:replace(curSon.p_name, '市', '')}</a>
            </c:forEach>
            </span>
            </p>
          </li>
          </c:forEach>
        </ol>
      </div>
   
    </div>
  </div>
 
</div>
<jsp:include page="../../_footer.jsp" flush="true" />
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	getPindexFromIp("${ctx}");
	
	$(".hasallcity li").each(function(){
		$(this).mouseover(function(){
			$(this).addClass("hover");
		}).mouseout(function(){
			$(this).removeClass("hover");
		});
	});
});
//]]></script>
</body>
</html>
