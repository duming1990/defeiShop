<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<div class="admincp-container-left" id="admincp-container-left">
    <div class="top-border"><span class="nav-side"></span><span class="sub-side"></span></div>
    <c:forEach var="cur" items="${sysModuleParList}" varStatus="vs">
     <c:set var="divCss" value="display: none;" />
     <c:if test="${vs.count eq 1}">
     <c:set var="divCss" value="display: block;" />
     </c:if>
    <div id="parParSys_${cur.mod_id}" class="nav-tabs" style="${divCss}" data-modid="${cur.mod_id}">
    <c:forEach var="curSon" items="${cur.map.sysModuleSonList}" varStatus="vs2">
      <c:set var="dlClass" value="" />
      <c:if test="${vs2.count eq 1}">
      <c:set var="dlClass" value="active" />
      </c:if>
      <dl class="${dlClass}" data-modid="${curSon.mod_id}" id="parSys_${curSon.mod_id}">
        <dt onclick="setCookieAndOpenUrl(null,this,2,true);">
          <a href="javascript:void(0);"><span><i class="fa ${curSon.font_awesome}"></i></span>
          <h3>${fn:escapeXml(curSon.mod_name)}</h3>
          </a>
        </dt>
        <c:set var="ddTop" value="${70*(vs2.count-1)}"/>
        <dd class="sub-menu" style="top:-${ddTop}px;" data-top="${ddTop}">
          <ul>
        <c:forEach var="curSonSon" items="${curSon.map.sysModuleSonSonList}" varStatus="vs3">
           <c:set var="liClass2" value="" />
	       <c:if test="${vs3.count eq 1}">
	       <c:set var="liClass2" value="active" />
	       </c:if>
           <li class="${liClass2}" id="sonSys_${curSonSon.mod_id}" data-url="${ctx}${curSonSon.mod_url}" data-modid="${curSonSon.mod_id}" onclick="setCookieAndOpenUrl('${ctx}${curSonSon.mod_url}',this,3,true)"><a href="javascript:void(0);">${fn:escapeXml(curSonSon.mod_name)}</a></li>
          </c:forEach>
          </ul>
        </dd>
      </dl>
      </c:forEach>
    </div>
    </c:forEach>
  </div>
