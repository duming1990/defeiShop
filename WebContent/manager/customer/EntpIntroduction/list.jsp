<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/public.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/scripts/colorbox/style3/colorbox.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx}/styles/mui/css/mui.min.css">
<style type="">
.beautybg1{
    box-shadow: inset 0px 1px 0px 0px #a4e271;
    background: -webkit-gradient(linear, left top, left bottom, color-stop(0.05, #89c403), color-stop(1, #77a809));
    background: -moz-linear-gradient(center top, #89c403 5%, #77a809 100%);
    filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#89c403',endColorstr='#77a809');
    background-color: #89c403;
    -moz-border-radius: 5px;
    -webkit-border-radius: 5px;
    border-radius: 5px;
    border: 1px solid #74b807;
    display: inline-block;
    color: #ffffff;
    font-family: arial;
    font-size: 12px;
    font-weight: bold;
    padding: 4px 10px;
    text-decoration: none;
    text-shadow: 1px 1px 0px #528009;
    line-height: 15px;
    cursor: pointer;

}
</style>
</head>
<script type="text/javascript" src="${ctx}/scripts/swiper/swiper.min.js"></script>
<body style="height: 3000px">
	<div style="width: 500px;position: absolute;left: 30%;">
	  <div  class ="areasbg"><a href="${ctx}/manager/customer/EntpIntroBaseLink.do?method=indexTsg&mod_id=${af.map.mod_id}&link_type=50&main_type=10&link_id=${entpInfo.id}&" class="beautybg">编辑图片</a></div>
	  <img src="${ctx}/styles/serviceIndex/img/entpIntro.jpg" width="500px"/>
		<div class="areas"><a onclick="editFloor(${entpInfo.id});" class="beautybg1">编辑楼层</a></div>
<!-- 		 <div class="areas"  title="刷新楼层"><span id="reload" onclick="location.reload()" style="cursor:pointer;" class="beautybg">刷新楼层</span></div> -->
		 <c:forEach var="cur" items="${baseLink60List}" varStatus="vs">
		   <div class="mui-content" style="background-color:#fff">
				<div class="mui-card">
					<ul class="mui-table-view">
						<li class="mui-table-view-cell mui-collapse">
							<a class="mui-navigate-right" href="">${cur.title}</a>
							<div class="mui-collapse-content">
							<div class="areas" style="position: absolute;"><a href="${ctx}/manager/customer/EntpIntroBaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}01&link_id=${cur.link_id}&par_id=${cur.id}" class="beautybg">编辑</a></div>
								<img src="${ctx}/styles/serviceIndex/img/entpIntro.jpg" width="500px"/>
							</div>
						</li>
					</ul>
				</div>
		  </div>
	    </c:forEach>
	</div>
<script type="text/javascript" src="${ctx}/scripts/colorbox/jquery.colorbox.min.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script> 
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript" src="${ctx}/styles/mui/mui.min.js"></script>
<script type="text/javascript">//<![CDATA[
	var f = document.forms[0];
$("a.beautybg").colorbox({width:"90%", height:"80%", iframe:true});

	function editFloor(id){
			var url = "${ctx}/manager/customer/EntpIntroBaseLink.do?mod_id=${af.map.mod_id}&link_type=60&link_id="+id;
			$.dialog({
				title:  "编辑楼层",
				width:  1400,
				height:700,
				padding: 0,
				max: false,
		        min: false,
		        fixed: true,
		        lock: true,
				content:"url:"+ encodeURI(url),
		  		close:function(){  
		             location.reload();
		            }  

			});
		}
		//]]></script>
</body>
</html>
