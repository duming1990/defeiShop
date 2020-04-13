<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!----------页脚开始---------->
<div class=" footer site-info-w J-br" style="min-height:298px;">
<div class="site-outer">
        <div class="site-info">
            <div class="site-info__item">
                <h3>用户帮助</h3>
                <ul>
                    <li><a rel="nofollow">我要留言</a></li>
                    <li><a>常见问题</a></li>
                    <li><a rel="nofollow">联系我们</a></li>
                </ul>
            </div>
            <div class="site-info__item">
                <h3>项目信息</h3>
                <ul>
                    <li><a rel="nofollow">项目介绍</a></li>
                    <li><a rel="nofollow">项目优势</a></li>
                    <li><a rel="nofollow">服务网络</a></li>
                </ul>
            </div>
            <div class="site-info__item">
                <h3>商务合作</h3>
                <ul>
                    <li><a rel="nofollow">商家入驻</a></li>
                    <li><a rel="nofollow">地区代理</a></li>
                    <li><a rel="nofollow">全国招商</a></li>
                </ul>
            </div>
            <div class="site-info__item">
                <h3>公司信息</h3>
                <ul>
                    <li><a rel="nofollow" class="J-selfservice-tab">关于${app_name_min}</a></li>
                    <li><a rel="nofollow" class="J-selfservice-tab">媒体报道</a></li>
                    <li><a rel="nofollow">加入我们</a></li>
                </ul>
            </div>
            <div class="site-info__item site-info__item--service">
		<div class="site-info-service-content">
          <p class="contact-name"><img src="${ctx}/styles/imagesPublic/footer_qrcode_pic.gif" width="239" height="169"></p>
                    <p class="desc">&nbsp;</p>
	        </div>
            </div>
	    <div class="clear"></div> 
	</div>
    </div>
    <div class="copyright">
        <p>${app_name} ${app_domain}</p>
        <p>Copyright©2012-2015 工信部备案：${app_beian_no}</p>
        <p>${app_name}全国服务热线：${app_tel} 工作时间：周一至周六 8:30-18:00</p>
        <p><a>${app_name}</a>&nbsp;<a title="网站地图">SITEMAP</a></p>
    </div>

    </div>
<!----------页脚结束---------->
