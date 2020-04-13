<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!----------页脚开始---------->
<div class=" footer site-info-w J-br" style="min-height:298px;">
<div class="container">
 <div class="div7">
     <div class="item"><span class="span1"></span>会员消费制</div>
     <div class="item"><span class="span2"></span>海量源头产品</div>
     <div class="item"><span class="span3"></span>源头直供</div>
     <div class="item"><span class="span4"></span>农户直供</div>
  </div>
</div>
<div class="site-outer">
        <div class="site-info">
            <div class="site-info__item">
               <div class="areas"><a href="${ctx}/manager/admin/BaseLink.do?mod_id=${af.map.mod_id}&link_type=1100" class="beautybg">编辑</a></div>
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
<!---------右侧工具条开始----------->
<div class="component-rightbottom-sticky mt-component--booted" style="bottom: 20px; right: 10px;">
  <div class="new-index-triffle-w" style="bottom: 20px; right: 10px;">
    <div id="fixbar-item-top" style="bottom: 20px; right: 10px;">
        <a class="J-go-top lift-nav new-index-triffle">
            <i></i><span>回到顶部</span>
        </a>
    </div>
    <div id="fixbar-item-vote" style="bottom: 20px; right: 10px;">
        <a class="new-index-triffle lift-nav lift-nav--vote"><i></i><span>问卷调查</span></a>
    </div>
    <div id="fixbar-item-help"> 
        <a class="J-lift-help new-index-triffle lift-nav lift-nav--help" href="javascript:;">
            <i></i><span>帮助中心</span>
        </a>
    </div>
    <div id="fixbar-item-feedback" style="bottom: 20px; right: 10px;"><a class="new-index-triffle lift-nav lift-nav--feedback"><i></i><span>意见反馈</span></a></div>
</div></div>
<!---------右侧工具条结束----------->