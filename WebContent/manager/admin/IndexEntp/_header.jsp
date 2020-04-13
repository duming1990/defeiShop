<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script>
<script type="text/javascript" src="${ctx}/scripts/autocomplete/autocompleteForSeach.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/styles/indexEntp/css/common.css"/>
<link rel="stylesheet" href="${ctx}/styles/indexEntp/css/style.css">
<%-- <c:forEach items="${baseLink10000List}" var="cur"> --%>
<%-- 	<c:if test="${cur.pre_number eq 0}"> --%>
<div class="header">
			<div class="header_n clearfix">
				<div class="logo fl"><a href="index.html"><img src="${ctx}/styles/indexEntp/images/logo.png" alt="logo" width="200" height="57"></a></div>
				<ul class="fl clearfix">
				<div class="areas"><a  style="cursor:pointer" class="beautybg" onclick="editFloor(${af.map.mod_id},10070,'txt',${cur.id});">编辑导航</a></div>
<%-- 						<c:forEach items="${cur.map.baseLink10070List}" var="cur2"> --%>
<%-- 			  				<li class="navbar__item-w "><a class="navbar__item"><span class="nav-label">${cur2.title}</span></a> </li> --%>
		             		
<%-- 		  				</c:forEach> --%>
					
          			<li class="navbar__item-w "><a class="navbar__item"><span class="nav-label">首页</span></a> </li>
          			<li class="navbar__item-w "><a href="${ctx}/manager/admin/About.do" class="navbar__item"><span class="nav-label">关于我们</span></a> </li>
          			<li class="navbar__item-w "><a class="navbar__item"><span class="nav-label">核心业务</span></a> </li>
          			<li class="navbar__item-w "><a href="${ctx}/manager/admin/BaseLinkNew.do?method=news" class="navbar__item"><span class="nav-label">新闻中心</span></a> </li>
          			<li class="navbar__item-w "><a class="navbar__item"><span class="nav-label">客户服务</span></a> </li>
          			<li class="navbar__item-w "><a class="navbar__item"><span class="nav-label">联系我们</span></a> </li>
          			<li class="navbar__item-w "><a class="navbar__item navbar__item-hots"><span class="nav-label">人才招聘</span></a> </li>
				</ul>
				<div class="tel fr">
				<div class="areas"><a  style="cursor:pointer" class="beautybg" onclick="editFloor(${af.map.mod_id},10071,'txt',${cur.id});">编辑电话号码</a></div>
					<img src="${ctx}/styles/indexEntp/images/tel.png" alt="tel" width="33" height="33">
					400-807-9909
				</div>
			</div>
			
			<span class="line"></span>
		</div>
<%--  </c:if> --%>
<%--  </c:forEach> --%>
 <script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>  
 <script type="text/javascript">
		function editFloor(id,link_type,type,par_id){
		var url = "${ctx}/manager/admin/BaseLinkNew.do?mod_id="+id+"&link_type="+link_type+"&type="+type+"&par_id="+par_id;
		
		$.dialog({
			title:  "编辑楼层",
			width:  1600,
			height:700,
			padding: 0,
			max: false,
	        min: false,
	        fixed: true,
	        lock: true,
			content:"url:"+ encodeURI(url),
	  		close:function(){  
	             location.reload(true);
	            }  

		});
	}
// 		$(function(){
// 		    $('#dowebok').fullpage({
// 				'navigation': true,
// 		    });
// 		});
		</script>