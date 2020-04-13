<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html-el" %>
<%@ taglib uri="http://struts.apache.org/tags-logic-el" prefix="logic-el" %>
<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean-el"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新闻中心</title>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/indexEntp/css/common.css"/>
<link rel="stylesheet" href="${ctx}/styles/indexEntp/css/style.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/indexEntp/css/owl.carousel.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/styles/indexEntp/css/owl.theme.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/commons/bootstrap/default/bootstrap.min.css"/>
</head>
<body>

 	<div class="header">
		<jsp:include page="../_header.jsp"  flush="true"/>
		
	</div>

	<!--新闻中心开始-->
        <div class="wrap">
		<div class="col_banner about_banner bgSize" style="background-image:url(${ctx}/styles/indexEntp/images/news_banner.jpg);">
	      <div class="inner_index w1200">
	        <h4>新闻中心</h4>
	        <p class="en">NEWS CENTER</p>
	      </div>
	    </div>
        <div class="w1200 news">
          <!-- title -->
          <ul class="news_t">
          	<li class="${af.map.mod_id eq 1808004100? 'active':''}" id="news_t1"><a href="${ctx}/indexEntp/IndexNewsInfo.do?mod_id=1808004100">公司动态</a><span class="line"></span></li>
          	<li class="${af.map.mod_id eq 1808004200? 'active':''}" id="news_t2"><a href="${ctx}/indexEntp/IndexNewsInfo.do?mod_id=1808004200">行业新闻</a><span class="line"></span></li>
          </ul>
          <!-- content -->
         <c:if test="${empty newsInfoList}">
           	暂时没有最新动态
           </c:if>
          <c:forEach var="cur" items="${newsInfoList}" >
          
          <ul class="news_item clearfix">
            <li>
	            <a href="${ctx }/indexEntp/IndexNewsInfo.do?method=view&id=${cur.id}" >
	              <div class="fl date">
	              	<span><fmt:formatDate value="${cur.add_time }" pattern="MM-dd" />
	              	</span>
	              	<fmt:formatDate value="${cur.add_time }" pattern="yyyy" />
	              	
	              </div>
	              <div class="fr txt">
	                <h5 style="${cur.title_color}">${cur.title }</h5>
	                <p>${cur.summary}</p>
	              </div>
	            </a>
            </li>
          </ul>
          </c:forEach>
          <div class="row">
          <form id="bottomPageForm" name="bottomPageForm" action="IndexNewsInfo.do">
			     <div class="dataTables_paginate_row">
			       <div class="dataTables_paginate paging_bootstrap" style="text-align:center;">
			           <ul id="pagination" class="pagination"></ul>
			            <script type="text/javascript" src="${ctx}/commons/pager/pagination.home.source.js">;</script> 
			            <script type="text/javascript">
						   	$.fn.pagination.addHiddenInputs("mod_id", "${fn:escapeXml(af.map.mod_id)}");
							$("#pagination").pagination({
								pageForm : document.bottomPageForm,
								recordCount : parseFloat("${pager.recordCount}"),
								pageSize : parseFloat("${pager.pageSize}"),
								currentPage : parseFloat("${pager.requestPage - 1}")
						   });
			            </script>
			       </div>
			     </div>
			     </form>
		   </div>
      </div>
	</div>
   <!--新闻中心结束-->
      <jsp:include page="../_footer.jsp" flush="true"/>
	 <script type="text/javascript" src="${ctx}/commons/scripts/pager.js"></script> 
	 <script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
	<script src="${ctx}/styles/indexEntp/js/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="${ctx}/styles/indexEntp/js/jquery.min.js"></script>
	<script type="text/javascript" src="${ctx}/styles/indexEntp/js/jquery.mousewheel.min.js"></script>
	<script type="text/javascript" src="${ctx}/styles/indexEntp/js/fullpagecode.min.js"></script>
	<script type="text/javascript" src="${ctx}/styles/indexEntp/js/jquery.SuperSlide.2.1.1.js"></script>
 	<script type="text/javascript" src="${ctx}/styles/indexEntp/js/owl.carousel.min.js"></script> 
	
	<script type="text/javascript">
	 $(window).load(function(){
			$(".pagination li:first-child").addClass("prev")
			$(".pagination li:last-child").addClass("next")
		})
		
	</script>
</body>
</html>