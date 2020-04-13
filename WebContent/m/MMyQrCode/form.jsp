<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="../../commons/pages/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta name="Description" content="${app_name}" />
<title>${app_name}</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<jsp:include page="../_public_in_head.jsp" flush="true" />
</head>
<body>
<jsp:include page="../_header.jsp" flush="true" />
<div class="content">
  <div class="pay-pw" style="margin-top: 0.5rem;">
     <div class="blank" style="text-align: center;padding: 5px;">我的专属二维码</div>
    <div class="mc">
      <ul class="com-ul" style="text-align: center">
        <li class="last"> <img alt="我的二维码" src="${ctx}/${fileTruePath}?t=${time}" width="250" />
          <div style="text-align:center;">
            <div style="padding: 10px;">${share_url}</div>
          </div>
        </li>
      </ul>
      <div style="padding: 8px;text-align:center;">
        <button id="share-button" class="com-btn btns-success"><i class="fa fa-share-alt"></i> 分享二维码</button>
      </div>
    </div>
  </div>
</div>
<jsp:include page="../_footer.jsp" flush="true" />
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script>
var wxData = {
        imgUrl : 'http://${app_domain}/${fileTruePath}',
        link : '${share_url}',
        desc: '快来【${app_name}】赚钱吧！点击赚钱：${share_url}',
        title : "wuyang的专属链接-${app_name}"
    };
    
  wx.config({
      debug: false,
      appId: '${appid}',
      timestamp: ${timestamp},
      nonceStr: '${nonceStr}',
      signature: '${signature}',
      jsApiList: [
                  'checkJsApi',
                  'onMenuShareTimeline',
                  'onMenuShareAppMessage',
                  'onMenuShareQQ'
                ]
  });
</script>
<script src="${ctx}/weixin/js/weixin.js?1234" type="text/javascript" ></script>
</body>
</html>
