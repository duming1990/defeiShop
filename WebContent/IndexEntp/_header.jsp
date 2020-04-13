<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script>
<script type="text/javascript" src="${ctx}/scripts/autocomplete/autocompleteForSeach.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/styles/indexEntp/css/common.css" />
<link rel="stylesheet" href="${ctx}/styles/indexEntp/css/style.css">
<div class="header">
  <div class="header_n clearfix">
    <div class="logo fl">
      <a href="index.html"><img src="${ctx}/styles/indexEntp/images/logo.png" alt="logo" width="200" height="57"></a>
    </div>
    <ul class="fl clearfix">
      <c:forEach items="${baseLink10070List}" var="cur2">
        <li class="navbar__item-w ">
          <a href="${cur2.link_url}" class="navbar__item" style="cursor:pointer"><span class="nav-label">${cur2.title}</span></a>
        </li>
      </c:forEach>
    </ul>
    <div class="tel fr">
      <img src="${ctx}/styles/indexEntp/images/tel.png" alt="tel" width="33" height="33"> ${baseLink10071.pre_varchar}
    </div>
  </div>

  <span class="line"></span>
</div>