<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!-- top begin -->
<div id="user-info">
    <div class="user-info-wrap w1200">
        <div class="user-info-l" style="width: 80%;">
            <c:set var="user_logo" value="${ctx}/styles/imagesPublic/user_header.png" />
		    <c:if test="${not empty userInfo.user_logo}">
		      <c:set var="user_logo" value=" ${ctx}/${userInfo.user_logo}@s400x400" />
		    </c:if>
		    <c:if test="${fn:contains(userInfo.user_logo, 'http://')}">
		      <c:set var="user_logo" value="${userInfo.user_logo}"/>
		    </c:if>
		    <c:url var="url" value="/manager/customer/MyAccount.do?par_id=1100600000&mod_id=1100600200" />
            <a onclick="loadIframe('${url}',1100600000)" class="u-pic">
            <img src="${user_logo}" width="85" height="85"></a>
            <div class="info-m">
                <div class="u-name">
                <a onclick="loadIframe('${url}',1100600000)" class="name"><em>${date}</em>${userInfo.real_name}</a></div>
                <div class="u-growth">
                    <span class="tel">手机：${userInfo.mobile}</span>
                </div>
            </div>
        </div>
        <div class="user-info-r" style="width: 20%;">
            <div class="info-item info-fore4">
               <span onclick="location.href='../login.do?method=logout'" style="cursor: pointer;"><i class="fa fa-sign-out"></i><span class="text-muted">安全退出</span></span>
			</div>
        </div>
    </div>
</div>
<!-- top end -->
