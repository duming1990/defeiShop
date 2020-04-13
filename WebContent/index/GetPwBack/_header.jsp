<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<header class="header--mini">
    <div class="wrapper cf">
        <c:url var="url" value="/IndexLogin.do" />
   		<a href="${url}" title="${app_name}" style="vertical-align:bottom;line-height: 58px;">
        <img  src="${ctx}/styles/imagesPublic/logo_m.png" /></a>
        <div class="login-block">
            <span class="tip">已有${app_name_min}账号？</span>
             <c:url var="url" value="/IndexLogin.do" />
            <a class="btn btn-small login" href="${url}">登录</a>
        </div>
    </div>
</header>
