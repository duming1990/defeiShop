<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${app_name}</title>
<link rel="stylesheet" href="${ctx}/styles/indexEntp/css/style.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/indexEntp/css/owl.carousel.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/indexEntp/css/owl.theme.css" />
<%-- <link rel="stylesheet" type="text/css" href="${ctx}/styles/indexEntp/css/jquery.fullPage.css"/> --%>
<link href="${ctx}/scripts/colorbox/style3/colorbox.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/category-list.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/customer.css?v=20161130" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/indexEntp/css/common.css" />
</head>
<body>
<!--导航条-->
<jsp:include page="../_header.jsp" flush="true" />
<!--导航条-->
<!--客户服务-->

<div class="wrap">
<c:if test="${not empty LINK_TYPE_10109}">
  <div class="col_banner about_banner bgSize" style="background-image:url('../${LINK_TYPE_10109.image_path}');">
    <div class="inner_index w1200">
      <h4>${LINK_TYPE_10109.title}</h4>
      <p class="en">${LINK_TYPE_10109.pre_varchar}</p>
    </div>
  </div>
  </c:if>
  <c:forEach items="${baseLink10050List}" var="cur">
  <div class="service_o" style="margin:auto;">
	<c:if test="${cur.pre_number eq 1}">
  <div class="service_o_o clearfix" style="width: 100%;height: 606px; background:url('../${cur.map.LINK_TYPE_CASE.image_path}') no-repeat center left;margin-top: 70px;">
    <div class="service_o_o_n fr" style="    margin-right: 27px;margin-top:-220px">
      <h3>${cur.map.LINK_TYPE_CASE.title}</h3>
      <p>${cur.map.LINK_TYPE_CASE.pre_varchar}</p>
      <span>${cur.map.LINK_TYPE_CASE.content}</span>
    </div>
  </div>
  </c:if>
  <c:if test="${cur.pre_number eq 2}">
  <div class="service_o_s clearfix" style="width: 100%;height: 606px; background:url('../${cur.map.LINK_TYPE_CASE.image_path}') no-repeat center right;margin-top: 70px;">
    <div class="service_o_o_n fr" style="    margin-right: 670px;margin-top:-220px">
      <h3>${cur.map.LINK_TYPE_CASE.title}</h3>
      <p>${cur.map.LINK_TYPE_CASE.pre_varchar}</p>
      <span>${cur.map.LINK_TYPE_CASE.content}</span>
    </div>
  </div>
  </c:if>
  </div>
  <%-- <c:if test="${cur.order_value eq 3}">
  <div class="service_o_s clearfix" style="width: 100%;height: 606px; background:url('../${cur.map.LINK_TYPE_10130.image_path}') no-repeat center left;margin-top: 70px;">
    <div class="service_o_o_n fr" style="    margin-right: 217px;">
      <h3>${cur.map.LINK_TYPE_10130.title}</h3>
      <p>${cur.map.LINK_TYPE_10130.pre_varchar}</p>
      <span>${cur.map.LINK_TYPE_10130.comm_name}</span>
    </div>
  </div>
  </c:if>
  
  <c:if test="${cur.order_value eq 4}">
  <div class="service_o_s clearfix" style="width: 100%;height: 606px; background:url('../${cur.map.LINK_TYPE_10140.image_path}') no-repeat center right;margin-top: 70px;">
    <div class="service_o_o_n fr" style="    margin-right: 670px;">
      <h3>${cur.map.LINK_TYPE_10140.title}</h3>
      <p>${cur.map.LINK_TYPE_10140.pre_varchar}</p>
      <span>${cur.map.LINK_TYPE_10140.comm_name}</span>
    </div>
  </div>
  </c:if> --%>
 </c:forEach>
</div>
<!--客户服务-->
<!--底部-->
<jsp:include page="../_footer.jsp" flush="true" />
<!--底部-->
<!-- js -->
<script type="text/javascript" src="${ctx}/styles/indexEntp/js/jquery.min.js"></script>

<script type="text/javascript" src="${ctx}/styles/indexEntp/js/jquery.mousewheel.min.js"></script>
<script type="text/javascript" src="${ctx}/styles/indexEntp/js/jquery.fullPage.js"></script>
<script type="text/javascript" src="${ctx}/styles/indexEntp/js/jquery.SuperSlide.2.1.1.js"></script>
<script type="text/javascript" src="${ctx}/styles/indexEntp/js/owl.carousel.min.js"></script>
<script type="text/javascript" src="${ctx}/styles/indexEntp/js/style2.js"></script>
<%-- 	<script type="text/javascript" src="${ctx}/scripts/colorbox/jquery.colorbox.min.js"></script>  --%>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript">
function editFloor(id, link_type, type, par_id) {
  var url = "${ctx}/manager/admin/BaseLinkNew.do?mod_id=" + id + "&link_type=" + link_type + "&type=" + type + "&par_id=" + par_id;
  $.dialog({
    title: "编辑",
    width: 1600,
    height: 700,
    padding: 0,
    max: false,
    min: false,
    fixed: true,
    lock: true,
    content: "url:" + encodeURI(url),
    close: function () {
      location.reload(true);
    }

  });
}
var Wheight = $(window).height();
$('.cuowu').height(Wheight);
</script>
</body>
</html>