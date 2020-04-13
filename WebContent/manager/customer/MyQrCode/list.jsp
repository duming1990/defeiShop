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
<div class="mainbox mine" style="min-height: 500px">
  <jsp:include page="../_nav.jsp" flush="true"/>
  <div class="pay-pw">
    <div class="blank"></div>
    <div class="mc">
      <ul class="com-ul" style="text-align: center">
        <li class="last"> <img alt="商城分享二维码" src="${ctx}/${fileTruePath}?t=${time}" width="200" height="200"/>
          <div style="text-align:center;">
            <c:url var="url" value="Register.do?user_id=${userInfo.id}" />
            <div>
              <div style="padding: 10px;">${share_url}&nbsp;
                <button id="copy-button" class="btn_base" data-clipboard-text="${share_url}">复制</button>
              </div>
              <div class="bdsharebuttonbox" style="padding-left:45%;"><a href="#" class="bds_more" data-cmd="more"></a> <a href="#" class="bds_weixin" data-cmd="weixin" title="分享到微信"></a> <a href="#" class="bds_sqq" data-cmd="sqq" title="分享到QQ好友"></a> <a href="#" class="bds_tsina" data-cmd="tsina" title="分享到新浪微博"></a> <a href="#" class="bds_qzone" data-cmd="qzone" title="分享到QQ空间"></a> <a href="#" class="bds_tqq" data-cmd="tqq" title="分享到腾讯微博"></a></div>
            </div>
          </div>
        </li>
      </ul>
    </div>
  </div>
</div>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script src="${ctx}/scripts/ZeroClipboard/ZeroClipboard.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
  var clip = new ZeroClipboard($('#copy-button'));
  
  clip.on('ready', function(){
  
    this.on('aftercopy', function(event){
    	$.dialog.tips("已经复制到剪贴板",2, "success.gif");
    });
  });
  
  clip.on('error', function(event){
	$.dialog.tips('error[name="' + event.name + '"]: ' + event.message,2, "tips.gif");
    ZeroClipboard.destroy();
  });

	var user_logo = "${userInfo.user_logo}";
	if(user_logo.indexOf("http://") == -1){
	   user_logo = '${ctx}/${user_logo}';
	}
	window._bd_share_config = {
		common : {
			bdText : '${ShareQrCodeDesc}',	
			bdDesc : '${app_name}',	
			bdUrl : '${share_url}', 	
			bdPic : user_logo
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
