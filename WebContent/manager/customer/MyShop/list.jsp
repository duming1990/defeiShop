<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="mainbox mine">
  <jsp:include page="../_nav.jsp" flush="true"/>
  <div class="pay-pw">
    <div class="blank"></div>
    <div class="mc">
      <ul class="com-ul" style="text-align: center">
        <li class="last"> <img alt="商城分享二维码" src="${ctx}/${fileTruePath}" width="200" height="200"/>
          <div style="text-align:center;">
            <div>
              <div style="padding-bottom: 20px;font-size: 16px;"><span class="label label-warning"><i class="fa fa-arrow-up"></i> 我的收款二维码</span></div>
            </div>
          </div>
        </li>
      </ul>
    </div>
  </div>
</div>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript">
$(document).ready(function(){
	
	window._bd_share_config = {
		common : {
			bdText : '${app_name},体验“零”消费，把花出去的钱排回来，免费注册会员中！',	
			bdDesc : '${app_name}',	
			bdUrl : '${share_url}', 	
			bdPic : '${share_img}'
		},
		share : [{
			"bdSize" : 16
		}],
		image : [{
			viewType : 'list',
			viewPos : 'top',
			viewColor : 'black',
			viewSize : '16',
			viewList : ['weixin','sqq','tsina','qzone','tqq']
		}]
	}
	with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?cdnversion='+~(-new Date()/36e5)];

});
</script>
</body>
</html>
