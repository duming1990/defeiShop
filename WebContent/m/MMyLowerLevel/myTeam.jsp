<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${app_name}</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Expires" content="-1">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Pragma" content="no-cache">
<jsp:include page="../_public_in_head.jsp" flush="true" />
<link href="${ctx}/m/styles/css/my/my-v1.css?v20170105" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx}/styles/index/css/xiaoguo.css" />
<style type="text/css">
.myls-frame {
	border: none;
}
</style>
</head>
<body>
	<jsp:include page="../_header.jsp" flush="true" />
	<div class="content">
		<!--article-->
		<div class="myls-frame">
			<section class="t">
				<i class="i-asset"></i>我的团队(${recordCountAll})
			</section>
			<div class="panel-content">
				<div class="grids-contant">
					<a class="grids-grid33" style="border-right: 1px solid #e3e3e3;"
						onclick="goUrl('${ctx}/m/MMyLowerLevel.do?par_id=1100700000&par_level=1&mod_id=1100700100')">
						<div class="grids-grid-icon">${recordCount1}</div>
						<p class="grids-grid-label">我的第一级</p>
					</a> 
<!-- 					<a class="grids-grid33" -->
<%-- 						onclick="goUrl('${ctx}/m/MMyLowerLevel.do?par_id=1100700000&par_level=2&mod_id=1100700100')"> --%>
<%-- 						<div class="grids-grid-icon">${recordCount2}</div> --%>
<!-- 						<p class="grids-grid-label">我的第二级</p> -->
<!-- 					</a>  -->
<%-- 					<a class="grids-grid33" onclick="goUrl('${ctx}/m/MMyLowerLevel.do?par_id=1100700000&par_level=3&mod_id=1100700100')"> --%>
<%-- 						<div class="grids-grid-icon">${recordCount3}</div> --%>
<!-- 						<p class="grids-grid-label">我的第三级</p> -->
<!-- 					</a> -->
				</div>
			</div>
		</div>
	<jsp:include page="../_footer.jsp" flush="true" />
	<script type="text/javascript">
$(document).ready(function(){
	
}); 
</script>
</body>
</html>
