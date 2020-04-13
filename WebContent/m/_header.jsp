<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<header class="index app_hide">
    <div class="c-hd">
      <section class="back" id="back"> 
       <a class="mui-action-back"><i></i></a> 
      </section>
      <section class="hd-title" style="width:80%;">${header_title}
      <c:if test="${not empty msgCount }">(${msgCount})</c:if>
      </section>
      <section class="side"> 
      <c:if test="${not empty titleSideName}">
        <a id="titleSideName">${titleSideName}</a>   
      </c:if>  
      <c:if test="${not empty canSearch}">
       <a onclick="canSearch();" title="筛选">筛选</a>
      </c:if>
      
      <c:if test="${not empty Can_Search}">
       <a onclick="canSearch();" title="筛选">${Can_Search}</a>
      </c:if>
      </section>
      
      <c:if test="${not empty hasGoHome}">
      <section class="hd-nav">
        <div class="hd-home">
        <c:url var="loginUrl" value="/m/MIndexLogin.do"/>
        <c:if test="${not empty userInfo}">
         <c:url var="loginUrl" value="/m/MMyHome.do"/>
        </c:if> 
         <a onclick="goUrl('${loginUrl}')" class="my"><i></i></a>
         <c:url var="url" value="/m/Index.do" />
         <a onclick="goUrl('${url}')" class="home"><i></i></a>
        </div>
      </section>
      </c:if>
    </div>
  </header>
<c:url var="urlOut" value="/m/MIndexLogin.do?method=logout"/>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	<c:if test="${not empty af.map.noBack}">
	   $("#back").css("visibility","hidden");
	</c:if>
});
//]]></script>