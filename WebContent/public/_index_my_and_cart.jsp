<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<c:if test="${userInfo.user_type eq 1 || userInfo.user_type eq 9662  || userType eq 4}">
  <c:url var="m_url" value="/manager/admin/Frames.do" />
</c:if>
<c:if test="${userInfo.user_type eq 2}">
  <c:url var="m_url" value="/manager/customer/index.do" />
</c:if>
<c:if test="${userInfo.user_type eq 3}">
  <c:if test="${!app_is_dandian}">
    <c:url var="m_url" value="/manager/entp/index.do" />
  </c:if>
 <c:if test="${app_is_dandian}">
    <c:url var="m_url" value="/manager/entps/index.do" />
 </c:if>
</c:if>
  <div class="lright">
      <div id="my360buy-2013">
      <dl id="dl_my_sw">
        <dt class="ld" id="my_sw">
         <c:if test="${not empty userInfo}">
        <a href="${m_url}">我的${app_name_min}</a>
        </c:if>
        <c:if test="${empty userInfo}">
        <c:url var="url" value="/IndexLogin.do" />
        <a href="${url}" onclick="this.href += '?returnUrl=' + escape(location.href);return true;" title="我的${app_name_min}">我的${app_name_min}</a>
        </c:if>
        <b></b></dt>
        <dd id="show_my_sw" style="display: none;">
          <div class="uctitle">
          <span>您好${userInfo.user_name}[
          <c:if test="${empty userInfo}">
        <c:url var="url" value="/IndexLogin.do" />
        <a href="${url}" onclick="this.href += '?returnUrl=' + escape(location.href);return true;" title="登录">登录</a> </c:if>
      <c:if test="${not empty userInfo}">
        <c:url var="url" value="/IndexLogin.do?method=logout" />
        <a href="${url}" title="退出">退出</a> </c:if>
          ]</span></div>
           <div class="uclist">
            <ul class="fore1" style="float: left;">
              <li>
               <c:if test="${empty userInfo}">
       	       <c:url var="url" value="/IndexLogin.do" />
              <a href="${url}" onclick="this.href += '?returnUrl=' + escape(location.href);return true;" title="待处理订单">待处理订单</a>
              </c:if>
              <c:if test="${not empty userInfo}">
               <c:url var="url" value="/manager/customer/OrderInfo.do?order_state=0"/>
                <a href="${url}">待处理订单</a>
                </c:if>
              </li>
            </ul>
            <ul style="float: left;">
              <li>
               <c:if test="${empty userInfo}">
       	       <c:url var="url" value="/IndexLogin.do" />
              <a href="${url}" onclick="this.href += '?returnUrl=' + escape(location.href);return true;" title="已处理订单">已处理订单</a>
              </c:if>
                <c:if test="${not empty userInfo}">
                <c:url var="url" value="/manager/customer/OrderInfo.do?order_state=40"/>
                <a href="${url}">已处理订单</a>
                </c:if>
              </li>
            </ul>
          </div>
           <div class="viewlist">
            <div class="viewlist-t">
            <span style="float: left;">最近浏览的商品</span>
<!--             <span class="r"><a class="blue" href="#">查看浏览历史 ></a></span> -->
            </div>
            <div class="viewlist-img">
              <ul>
              <c:forEach items="${cookiesCommList}" varStatus="vs" var="cur">
	 			<c:if test="${vs.index lt 5}">
			  	<c:url var="url" value="/entp/IndexEntpInfo.do?method=getCommInfo&id=${cur.id}" />
                <li><a href="${url}" title="${cur.comm_name}" target="_blank"><img src="${ctx}/${cur.main_pic}" width="54" height="52" /></a></li>
                </c:if>
                </c:forEach>
              </ul>
            </div>
          </div>
        </dd>
      </dl>
    </div>
    <div id="settleup-2013">
      <dl id="dl_my_gwc">
        <c:if test="${not empty userInfo}">
        <dt class="ld" id="my_gwc">
       <c:url var="url" value="/IndexShoppingCar.do" />
        <a href="${url}">去购物车结算</a></c:if>
        <c:if test="${empty userInfo}">
        <dt class="ld" id="no_login_my_gwc">
        <c:url var="url" value="/IndexLogin.do" />
        <a href="${url}" onclick="this.href += '?returnUrl=' + escape(location.href);return true;" title="去购物车结算">去购物车结算</a></c:if>
        <b></b>
        <span class="shopping">
        <span id="gwcCount">0</span>
        </span>
        </dt>
        <dd id="loading_gwc" style="display: none;">
        <div class="prompt">
        <div class="loading-style1"><b></b>加载中，请稍候...</div>
        </div>
        </dd>
        <dd id="show_my_gwc" style="display: none;">
          <div id="settleup-content">
            <div class="smt">
              <h4>最新加入的商品</h4>
            </div>
            <div class="smc" id="showGwcHas">
            </div>
            <div class="smb" id="showGwcHasCount">
              </div>
          </div>
        </dd>
      </dl>
    </div>
  </div>
<script type="text/javascript">//<![CDATA[
                                          
<c:if test="${not empty userInfo}">
	getGwcCount();                                       
</c:if>	                                       
function getGwcCount(){                                          
$.post("${ctx}/CsAjax.do?method=getGwcCount",{user_id : '${userInfo.id}'},function(data){
	 $("#gwcCount").text(data.result);
});                                        
};                                      
    
    $("#my360buy-2013").mouseover(function(){
		$("#dl_my_sw").addClass("hover");
		$("#show_my_sw").show();
	}).mouseleave(function(){
		$("#dl_my_sw").removeClass();
		$("#show_my_sw").hide();
	});
    
    var timeConfig = null;
    $("#my_gwc").mouseover(function(){
    	timeConfig = setTimeout(function(){
		$.ajax({
			type: "POST",
			url: "${ctx}/CsAjax.do",
			data: "method=ViewGwc&user_id=${userInfo.id}",
			dataType: "json",
			error: function(request, settings) {},
			success: function(datas) {
				var html="<ul>";
				var html2="";
				if(datas.list != "" && null != datas.list && datas.list.length > 0){
					for(var x in datas.list){
						var cur = datas.list[x];
						html += "<li><div class='p-img l'>";
						html += "<a href='${ctx}/entp/IndexEntpInfo.do?method=getCommInfo&id="+cur.comm_id+"'><img src=${ctx}/" + cur.comm_pic + " width='50' height='50' /></a></div>";
						html += "<div class='p-name l'><a href='${ctx}/entp/IndexEntpInfo.do?method=getCommInfo&id="+cur.comm_id+"'>" + cur.comm_name + "</a></div>";
						html += "<div class='p-detail r'><span><strong class='red'>￥" + cur.comm_price + "</strong>×" + cur.comm_count + "</span>";
						html += "<p style='cursor:pointer;'><a onclick=deleteOrder(this," + cur.cart_id + ")>删除</a></p>";
					}
					  html2 += "共<b id='count_comm'>" + datas.pd_count + "</b>件商品";
		              html2 += "　共计<strong id='pd_price_count'>￥" + datas.pd_price_count + "</strong><br>";
		              html2 += "<a href='${ctx}/cart.shtml'>去购物车结算</a>";
		              $("#showGwcHasCount").show();
					
				}else{
					html += "<div class='nogoods'><b></b>购物车中还没有商品，赶紧选购吧！</div>";
					$("#showGwcHasCount").hide();
					$("#showGwcHas").css("border-bottom-width","1px");
				}
				html += "</ul>";
				$("#loading_gwc").hide();
				$("#showGwcHas").html(html);
				$("#showGwcHasCount").html(html2);
				$("#show_my_gwc").show();
			}
		});
    	}, 1000);
	}).mouseout(function() {
	    if (timeConfig) {
	        clearTimeout(timeConfig);
	    }
	});
    
	$("#settleup-2013").mouseover(function(){
		$("#dl_my_gwc").addClass("hover");
		<c:if test="${not empty userInfo}">
		$("#loading_gwc").show();
		</c:if>
		<c:if test="${empty userInfo}">
		$("#show_my_gwc").show();
		$("#showGwcHasCount").hide();
		$("#showGwcHas").css("border-bottom-width","1px");
		var html = "<div class='nogoods'><b></b>购物车中还没有商品，赶紧选购吧！</div>";
		$("#showGwcHas").html(html);
		</c:if>
	}).mouseleave(function(){
		$("#dl_my_gwc").removeClass();
		$("#show_my_gwc").hide();
		$("#loading_gwc").hide();
	});
	
function deleteOrder(thisobj,cart_id){
	 $.post("${ctx}/CsAjax.do?method=delCart",{cart_id : cart_id},function(data){
			if (data.result) {
				$(thisobj).parent().parent().parent().remove();
				var gwcCount= $("#gwcCount").text();
				var cartCount = data.cartCount;
				var pd_price_count = data.pd_price_count;
				if(isNaN(cartCount)){
					cartCount = 0;
				}
				if(isNaN(pd_price_count)){
					pd_price_count = 0;
				}
				$("#gwcCount").text(cartCount);
				$("#count_comm").text(cartCount);
				$("#pd_price_count").text(pd_price_count);
				var size = $("#showGwcHas").find("ul li").length;
				if(size ==0){
					$("#showGwcHasCount").hide();
					$("#showGwcHas").css("border-bottom-width","1px");
					var html = "<div class='nogoods'><b></b>购物车中还没有商品，赶紧选购吧！</div>";
					$("#showGwcHas").html(html);
				}
				
			}
		});
}
  //]]></script> 