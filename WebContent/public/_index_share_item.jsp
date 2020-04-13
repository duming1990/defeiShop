<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<a class="small-btn share-tip J-component-share-tip-dialog log-mod-viewed" onclick="$('#J-share-link-list').slideToggle();"><i class="F-glob F-glob-share"></i>分享到</a>
<c:set var="seo_title" value="${fn:escapeXml(commInfo.comm_name)}" />
<c:url var="url" value="/entp/IndexEntpInfo.do?method=getCommInfo&id=${commInfo.id}" />
<c:set var="url_item" value="http://${app_domain}${url}"/>
<c:set var="url_img"  value="http://${app_domain}/${commInfo.main_pic}@s400x400"/>
<div class="share-pop" id="J-share-link-list"  style="display:none;">
  <div class="share-border"></div>
  <div class="bdsharebuttonbox share-popcon" data-tag="share_1"> <a class="bds_weixin weixin" data-cmd="weixin" title="分享到微信"><i></i>微信</a> <a class="bds_tsina sina" data-cmd="tsina" title="分享到新浪微博"><i></i>新浪微博</a> <a class="bds_qzone qqzone" data-cmd="qzone" title="分享到QQ空间"><i></i>QQ空间</a> <a class="bds_tqq qqweibo" data-cmd="tqq" title="分享到腾讯微博"><i></i>腾讯微博</a> <a class="bds_douban" data-cmd="douban" title="分享到豆瓣"><i></i>豆瓣</a> <a class="bds_renren renren" data-cmd="renren" title="分享到人人网"><i></i>人人网</a> </div>
  <a class="collect-close" href="javascript:void(0);" onclick="$('#J-share-link-list').slideToggle();">×</a> </div>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	window._bd_share_config = {
		common : {
			//此处放置通用设置data='{"url":"${url_item}","text":"#${app_name}#【${seo_title}】${url_item}","pic":"${url_img}"}'
			bdText : '#${app_name}#【${seo_title}】${url_item}',	
			bdUrl : '${url_item}', 	
			bdPic : '${url_img}'
		},
		share :  [{
			"tag" : "share_1",
			"bdSize" : 16
		}]
	}	
	with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?cdnversion='+~(-new Date()/36e5)];
			
});





//]]></script>
