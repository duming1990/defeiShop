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
<script type="text/javascript" src="${ctx}/m/scripts/jquery.SuperSlide.js?v=20170616"></script>
		<style type="text/css">
		/* css 重置 */
		*{margin:0; padding:0; list-style:none; }
		body{ background:#fff; font:normal 12px/22px 宋体;    height: 100%;  }
		img{ border:0;  }
		a{ text-decoration:none; color:#333;  }
		a:hover{ color:#1974A1;  }


		/* 本例子css */
		.slideTxtBox{ width:450px; border:1px solid #ddd; text-align:left;     height: 100%; }
		.slideTxtBox .hd{ height:40px; line-height:40px; background:#f4f4f4; padding:0 20px; border-bottom:1px solid #ddd;  position:relative;     top: 10px; }
		.slideTxtBox .hd ul{ float:left; position:absolute; left:20px; top:-1px; height:32px;   }
		.slideTxtBox .hd ul li{ float:left; padding:0 15px; cursor:pointer;  }
		.slideTxtBox .hd ul li.on{ height:40px;  background:#fff; border:1px solid #ddd; border-bottom:2px solid #fff; }
		.slideTxtBox .bd ul{ padding:15px;  zoom:1;  }
		.slideTxtBox .bd li{ height:24px; line-height:24px;   }
		.slideTxtBox .bd li .date{ float:right; color:#999;  }

		</style>

</head>
<body>
<jsp:include page="../_header.jsp" flush="true" />

	<div class="slideTxtBox">
		<div class="hd">
			<ul>
				<li>100:100收款码</li>
				<li>200:100收款码</li>
			</ul>
		</div>
		<div class="bd">
			<ul class="com-ul" style="text-align: center; margin-top: 20px;">
				<li class="last"><img alt="我的收款码100:100"
					src="${ctx}/${fileTruePath}" width="300" height="300"
					style="margin-top: 25%" />
					<div style="text-align: center;">
						<div>
							<div style="padding-bottom: 10px; font-size: 16px;">
								<span class="label label-warning"><i
									class="fa fa-arrow-up"></i>我的100:100收款码</span>
							</div>
						</div>
					</div></li>
			</ul>
			<ul class="com-ul" style="text-align: center; margin-top: 20px;">
				<li class="last"><img alt="我的收款码200:100"
					src="${ctx}/${fileTruePath200}" width="300" height="300"
					style="margin-top: 25%" />
					<div style="text-align: center;">
						<div>
							<div style="padding-bottom: 10px; font-size: 16px;">
								<span class="label label-warning"><i
									class="fa fa-arrow-up"></i>我的200:100收款码</span>
							</div>
						</div>
					</div></li>
			</ul>
		</div>
	</div>
	<script id="jsID" type="text/javascript">
		 var ary = location.href.split("&");
		jQuery(".slideTxtBox").slide( { effect:ary[1],autoPlay:ary[2],trigger:ary[3],easing:ary[4],delayTime:ary[5] });
		</script>



<jsp:include page="../_footer.jsp" flush="true" />
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
});

//]]></script>
</body>
</html>