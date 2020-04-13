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
<div id="wrap">
  <header class="index app_hide">
    <div class="c-hd s-hd">
      <section class="city" id="changeSearchType">
        <a class="arrowleft atop" id="typeShowName">商品</a>
      </section>
        
      <section class="hd-search">
        <c:url var="url" value="MSearch.do" />
        <html-el:form action="${url}" styleClass="box">
          <html-el:hidden property="params" value="${params}"/>
          <html-el:hidden property="par_cls_id"/>
          <html-el:hidden property="son_cls_id"/>
          <html-el:hidden property="htype" styleId="htype"/>
          <html-el:hidden property="is_aid" styleId="is_aid"/>
          <html-el:hidden property="orderByParam" styleId="orderByParam"/>
          <div class="box-flex c-form-search"> <em style="display:none;"></em>
            <input type="text" name="keyword" class="search-form c-ipunt-txt" placeholder="请输入商品名..." autocomplete="off" id="search_input" value="${af.map.search}" />
          </div>
          <input type="submit" class="searchbtn" onClick="putInHistoryKeywords()" name="dosearch" value="搜索" />
        </html-el:form>
      </section>
      
      <div class="s-input-tab-nav off" id="showSearchType">
         <ul>
          <li class="all"  data-flag="0">商品</li>
          <li class="shop" data-flag="1">店铺</li>
          <li class="shop" data-flag="2">县域</li>
          <li class="shop" data-flag="3">村子</li>
         </ul>
      </div>
      
    </div>
  </header>
  <div id="wrap">
    <div class="search-suggest" style="" id="search_hot">
      <ul class="search-hots">
        <c:forEach items="${baseData30List}" var="cur">
          <li>
            <c:url var="url" value="/m/MSearch.do?htype=0&keyword=${fn:escapeXml(cur.type_name)}" />
            <a onClick="putInHistoryKeywords('${fn:escapeXml(cur.type_name)}')" href="${url}">${fn:escapeXml(cur.type_name)}</a> </li>
        </c:forEach>
      </ul>
    </div>
    <div class="search-asslist">
      <c:if test="${not empty history_keywords}">
        <ul id="search_history_ul" class="search-meat search-history">
          <c:forEach items="${history_keywords}" var="cur">
            <c:url var="url" value="/m/MSearch.do?htype=0&keyword=${fn:escapeXml(cur)}" />
            <li><a onClick="putInHistoryKeywords('${fn:escapeXml(cur)}')" href="${url}">
              <div class="title"><i></i>${fn:escapeXml(cur)}</div>
              </a></li>
          </c:forEach>
        </ul>
        <div class="search-calcel" id="del_history">
          <input onClick="delSearchHistory()" type="button" value="清除搜索记录" class="btn-search-calcel">
        </div>
      </c:if>
    </div>
    <jsp:include page="../_footer.jsp" flush="true" />
  </div>
</div>
<script type="text/javascript" src="${ctx}/m/js/search.m.js?v=20160913"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
});
//]]></script>
</body>
</html>
