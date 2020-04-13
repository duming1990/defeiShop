<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>评论信息 - ${app_name}</title>
<jsp:include page="../../_public_header.jsp" flush="true" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/business-info.css"  />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/pages.css"  />
<link href="${ctx}/scripts/colorbox/style3/colorbox.css" rel="stylesheet" type="text/css" />
</head>
<body>
     <div class="rate-detail">
      <ul class="J-rate-filter rate-filter ratelist-title cf" id="rate-filter__itemUl">
        <li class="rate-filter__item">
        <c:url var="url" value="/IndexCommentInfo.do?link_id=${af.map.link_id}" />
            <a href="${url}" id="pjli" class="rate-filter__link J-filter-link rate-filter__link--active">全部</a>
        </li>
        <li class="rate-filter__item">
         <c:url var="url" value="/IndexCommentInfo.do?link_id=${af.map.link_id}&comm_level=1" />
            <a href="${url}" id="pjli1" class="rate-filter__link J-filter-link">好评</a>
        </li>
        <li class="rate-filter__item">
         <c:url var="url" value="/IndexCommentInfo.do?link_id=${af.map.link_id}&comm_level=2" />
            <a href="${url}" id="pjli2" class="rate-filter__link J-filter-link">中评</a>
        </li>
        <li class="rate-filter__item">
         <c:url var="url" value="/IndexCommentInfo.do?link_id=${af.map.link_id}&comm_level=3" />
            <a href="${url}" id="pjli3" class="rate-filter__link J-filter-link">差评</a>
        </li>
    </ul>
    <div class="ratelist-content cf">
     <c:if test="${not empty commentInfoList}">
        <ul class="J-rate-list">
       <c:forEach items="${commentInfoList}" var="cur">
        <li class="J-ratelist-item rate-list__item cf">
        <div class="user-info-block">
            <div class="avatar-wrapper">
               <c:set var="imageUrl" value="${ctx}/styles/imagesPublic/user_header.png" />
               <c:if test="${(not empty cur.map.userInfo) and (not empty cur.map.userInfo.user_logo)}">
                 <c:set var="imageUrl" value="${ctx}/${cur.map.userInfo.user_logo}" />
               </c:if>
              <c:if test="${fn:contains(cur.map.userInfo.user_logo, 'http://') or fn:contains(cur.map.userInfo.user_logo, 'https://')}">
		         <c:set var="imageUrl" value="${cur.map.userInfo.user_logo}"/>
		      </c:if>
               <img class="avatar" src="${imageUrl}" />
            </div>
            <p class="name-wrapper">
                <span class="name vip_level_high">${fn:escapeXml(cur.map.secretString)}</span>
<!--                 <span class="growth-info"> -->
<%--                 <i class="sp-growth-icons level-icon level-icon-${cur.map.userInfo.map.user_level}"></i></span> --%>
            </p>
        </div>
        <div class="review-content-wrapper">
            <div class="info cf">
                <div class="rate-status">
                    <span class="common-rating"><span class="rate-stars" style="width:${(cur.comm_score/5) * 100}%"></span></span>
                </div>
                <span class="time">
                <fmt:formatDate pattern="yyyy-MM-dd" value="${cur.comm_time}" /></span>
            </div>
            <div class="J-normal-view">
                <p class="content">
                   ${fn:escapeXml(cur.comm_experience)}
                </p>
                <div>
                   <c:forEach items="${cur.map.baseFilesList}" var="cur1">
                   <c:if test="${not empty cur1.save_path}">
			        <a href="${ctx}/${cur1.save_path}"  class="cb">
			        <img height="100" src="${ctx}/${cur1.save_path}@s400x400" /></a></c:if>
                   </c:forEach>
                </div>
                
                <c:if test="${not empty cur.map.commentInfoSonList}">
	                <c:forEach items="${cur.map.commentInfoSonList}" var="son">
	                <p style="display:inline-block;color:#FD8C1B;font-weight:400">
	                   <span style="color: #217dad">
	                   <c:if test="${son.is_entp eq 1}">${son.entp_name}</c:if>
         				<c:if test="${son.is_entp eq 0}">${son.add_user_name}</c:if>：</span>${fn:escapeXml(son.content)}
	                </p>
	                </c:forEach>
                </c:if>
          </div>
      </div>
    </li>
    </c:forEach>
    </ul>
   <div class="pages">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="/IndexCommentInfo.do">
    <ul id="pagination" class="pagination"></ul>
     <script type="text/javascript" src="${ctx}/commons/pager/pagination.home.js">;</script>
       <script type="text/javascript">
          $.fn.pagination.addHiddenInputs("method", "list");
          $.fn.pagination.addHiddenInputs("link_id", "${af.map.link_id}");
          $.fn.pagination.addHiddenInputs("comm_level", "${af.map.comm_level}");
      	  $("#pagination").pagination({
      		pageForm : document.bottomPageForm,
      		recordCount : parseFloat("${af.map.pager.recordCount}"),
      		pageSize : parseFloat("${af.map.pager.pageSize}"),
      		currentPage : parseFloat("${af.map.pager.currentPage - 1}")
      	 });
       </script>
    </form>
    </div>
    </c:if>
    <c:if test="${empty commentInfoList}">
    	<div style="padding:10px;">
    		暂无评论。
    	</div>
    </c:if>
   </div>
</div>
<script type="text/javascript" src="${ctx}/scripts/colorbox/jquery.colorbox.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	//$(".cb").colorbox({});
	$(".cb").colorbox({rel:'cb',is_show_in_top:true});
	
	 $("#rate-filter__itemUl li a").removeClass("rate-filter__link--active");
	 $("#pjli${af.map.comm_level}").addClass("rate-filter__link--active");
});  
	                                 

//]]>
</script>
</body>
</html>
