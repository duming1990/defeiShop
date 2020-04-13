<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!--MOB SHARE BEGIN-->
<div class="-mob-share-ui -mob-share-ui-theme -mob-share-ui-theme-slide-bottom" style="display: none">
    <ul class="-mob-share-list">
        <li class="-mob-share-weixin"><p>微信</p></li>
        <li class="-mob-share-qzone"><p>QQ空间</p></li>
        <li class="-mob-share-qq"><p>QQ好友</p></li>
        <li class="-mob-share-weibo"><p>新浪微博</p></li>
    </ul>
    <div class="-mob-share-close">取消</div>
</div>
<div class="-mob-share-ui-bg"></div>
<!--MOB SHARE END-->
<script id="-mob-share" src="http://f1.webshare.mob.com/code/mob-share.js?appkey=155e971cadc8a"></script>
<script id="weixin_js_guangfang" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	getWeixinJsApi();
});

function getWeixinJsApi(){
	var targetUrl = window.location.href;
	$.post("${ctx}/CsAjax.do?method=ajaxGetJsApiTicketRetrunParam",{'targetUrl':targetUrl},function(datas){
		if (datas.code == 1) {
			wx.config({
			    debug: false,
			    appId: datas.appid,
			    timestamp: datas.timestamp,
			    nonceStr: datas.nonceStr,
			    signature: datas.signature,
			    jsApiList: [
			                'checkJsApi',
			                'onMenuShareTimeline',
			                'onMenuShareAppMessage',
			                'onMenuShareQQ'
			              ]
			});
			
			//document.write(unescape("%3Cspan %3E%3C/span%3E%3Cscript src='${ctx}/weixin/js/weixin.js%3F20160615' type='text/javascript'%3E%3C/script%3E"))
			//$("#weixin_js").attr("src","${ctx}/weixin/js/weixin.js?20160615");
			$.getScript("${ctx}/weixin/js/weixin.js?201606158");
			wx.error(function(res) {
				var msg  = res.errMsg;
				//alert(msg);
				//alert(msg.indexOf("signature") != -1);
				if(msg.indexOf("signature") != -1){
					//alert("刷新页面");
					//setTimeout(function(){window.location.reload();},100); 
					setTimeout(function(){getWeixinJsApi();},100); 
				}
				
			});
		}
	});
}

//]]></script>
<script id="weixin_js" type="text/javascript"></script>
