<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>

<div class="component-order-nav mt-component--booted">
  <div class="side-nav J-order-nav">
    <dl class="side-nav__list">
      <dd class="header-item" id="header-item">
        <ul>
          <li class="header-item__current">
            <c:url var="url" value="/manager/customer/IndexCustomer.do?method=welcome" />
            <a onclick="loadIframe('${url}',0);"><strong><i class="fa fa-home"></i> 我的主页</strong></a></li>
        </ul>
      </dd>
      <dd class="last">
        <ul class="item-list">
          <c:url var="url" value="/manager/customer/MyAccount.do?par_id=1100600000&mod_id=1100600200" />
          <li id="son_1100600200"><a onclick="loadIframe('${url}',1100600200);"><i class="fa fa-user"></i> 我的账号</a></li>
          <c:url var="url" value="/manager/customer/MyOrder.do?par_id=1100500000&mod_id=1100500100" />
          <li id="son_1100500100"><a onclick="loadIframe('${url}',1100500100);"><i class="fa fa-reorder"></i> 我的订单</a></li>
          <c:url var="url" value="/manager/customer/MySecurityCenter.do?par_id=1100620000&mod_id=1100620100" />
          <li id="son_1100620100"><a onclick="loadIframe('${url}',1100620100);"><i class="fa fa-lock"></i> 安全中心</a></li>
          <c:url var="url" value="/manager/customer/MyMsg.do?par_id=1100630000&mod_id=1100630100" />
          <li id="son_1100630100"><a onclick="loadIframe('${url}',1100630100);"><i class="fa fa-envelope"></i> 消息中心</a></li>
        </ul>
      </dd>
    </dl>
  </div>
</div>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	$(".nav_dt").click(function(){
		var $this = $(this);
        var $next_dd = $this.next();
		if($next_dd.css('display') != 'none') {//显示
			$this.find("i").removeClass().addClass("fa fa-plus-square");
			$this.attr("title","展开节点");
		}else{
			$this.find("i").removeClass().addClass("fa fa-minus-square");
			$this.attr("title","收起节点");
		}
	  
	    $next_dd.slideToggle('slow');
	});
	
});
function getUserXyInfo(){
	var url = "${ctx}/Register.do?method=getUserXyInfo";
	$.dialog({
		title:  "用户注册协议",
		width:  770,
		height: 550,
        lock:true ,
		content:"url:"+url
	});
}
//]]></script>
<!-- left end -->
