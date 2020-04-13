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
<style type="">
.preview{ width:50px; height:40px; border-radius:20px; border-style:none; background:#ec6941; font-size:14px; color:rgba(249,248,248,1.00); position: absolute;  top: 50px; }
</style>
</head>
<body>
<!--导航条-->
<jsp:include page="/IndexEntp/_header.jsp" flush="true" />
<!--导航条-->
<!--客户服务-->

<div class="wrap">

	<div class="areasSmall" style="padding-left:20px;padding-top:10px;"><button class="preview"><a href="${ctx}/indexEntp/Case.do" style="color: white;text-decoration:none;" target="_blank">预览</a></button></div>
	
  <div class="areasSmall" style="padding-left:120px;padding-top:211px;z-index:999;">
    <a style="cursor:pointer" class="beautybg" onclick="editFloor('${af.map.mod_id}',10109,'pic','');">编辑</a>
  </div>

<div class="areasSmall" style="padding-left:420px;padding-top:111px;z-index:999;">
    <a style="cursor:pointer" class="beautybg" onclick="editFloor('${af.map.mod_id}',10050,'txt','');">编辑楼层</a>
  </div>
		  <div class="col_banner about_banner bgSize" style="background-image:url(${ctx}/styles/indexEntp/images/khfw_banner.jpg);">
		    <div class="inner_index w1200">
		      <h4>客户服务</h4>
		      <p class="en">service</p>
		    </div>
		  </div>

  <c:forEach items="${baseLink10050List}" var="cur">
	<c:if test="${cur.pre_number eq 1}">
  <div class="areasSmall" style="padding-left:120px;padding-top:111px;z-index:999;">
    <a style="cursor:pointer" class="beautybg" onclick="editFloor('${af.map.mod_id}',10110,'pic',${cur.id});">编辑${cur.title}</a>
  </div>
  <div class="service_o_o clearfix" style="width: 100%;height: 606px; background:url(${ctx}/styles/indexEntp/images/kufw01.jpg) no-repeat center left;margin-top: 70px;">
    <div class="service_o_o_n fr">
      <h3>品质保障</h3>
      <p>中财华商集团16年资深行业经验，服务客户100000+。专业即是名片，创新助力企业发展。</p>
      <span>集团秉持“一切以客户为中心”的宗旨，专业、诚信、规范经营。海量优质资源，高效便捷，为您解决创业及公司发展中各个阶段的难题，专业扫清前路障碍，助力开辟事业坦途。</span>
    </div>
  </div>
 </c:if>
  <c:if test="${cur.pre_number eq 2}">
  <div class="areasSmall" style="padding-left:120px;padding-top:111px;z-index:999;">
    <a style="cursor:pointer" class="beautybg" onclick="editFloor('${af.map.mod_id}',10120,'pic',${cur.id});">编辑${cur.title}</a>
  </div>
  <div class="clearfix service_o_t">
    <div class="service_o_o_n fl">
      <h3>专业团队</h3>
      <p>中财华商集团打造行业领军品牌，旗下拥有持证精英代理人近500，一对一定制服务，随时乐享星级体验。</p>
      <span>集团拥有强大的金融、财务、法律服务团队，时刻洞察行业信息，与时俱进。不断深化“以人为本”，明确人才是企业可持续发展、不断创新的源动力，在现有团队基础上打造“黑马营”、“云豹子商学院”等培训项目，结合集团发展战略，不断输出人才资源。</span>
    </div>
  </div>
   </c:if>
   
 </c:forEach>
</div>

	 
<!--客户服务-->
<!--底部-->
<jsp:include page="/IndexEntp/_footer.jsp" flush="true" />
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
    // 	  		close:function(){  
    // 	             location.reload(true);
    // 	            }  

  });
}
var Wheight = $(window).height();
$('.cuowu').height(Wheight);
</script>
</body>
</html>