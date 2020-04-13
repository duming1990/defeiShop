<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!-- 会员头像、姓名、等级 -->

<c:set var="user_logo" value="${ctx}/m/styles/img/my/myls_avatar_v2.png" />
<c:if test="${not empty userInfo}">
  <c:if test="${not empty userInfo.user_logo}">
    <c:set var="user_logo" value=" ${ctx}/${userInfo.user_logo}@s400x400" />
  </c:if>
  <c:if test="${fn:contains(userInfo.user_logo, 'http://')}">
    <c:set var="user_logo" value="${userInfo.user_logo}" />
  </c:if>
</c:if>
<div class="myls-infowarp${userInfo.is_entp}">
  <header class="glue2 mhome">
    <div class="top mhome"> 
      <a href="${ctx}/m/MMyAccount.do?mod_id=1100400200">
       <div class="big" style="margin-left: 1px;"> <span><img src="${user_logo}"> </span> <c:out value="${fnx:abbreviate(userInfo.real_name,10, '..')}" escapeXml="true" /></div>
      </a> 
      <span class="gs-gqul" style="position: absolute;margin-left: 0.3rem;"> 
      <span class="l${user_level} app_logout" onclick="goUrl('${ctx}/m/MMyScore.do?method=list&mod_id=1100610300')"></span>
      <c:set var="account_logo" value="${ctx}/styles/index/images/shang_gray.png" />
      <c:url var="url" value="/m/MEntpEnter.do" />
      <c:if test="${userInfo.is_entp eq 1}">
        <c:set var="account_tip" value="恭喜您已成为商家" />
        <c:set var="account_logo" value="${ctx}/styles/index/images/shang.png" />
        <c:url var="url" value="" />
      </c:if>
      <a onclick="goUrl('${url}')"> <img title="${account_tip}" class="user_shenfen" src="${account_logo}"/> </a> 
      <!--服运营中心-->
      <c:set var="account_tip" value="请到PC端申请成为运营中心" />
      <c:set var="account_logo" value="${ctx}/styles/index/images/yun_gray.png" />
      <c:if test="${userInfo.is_fuwu eq 1}">
        <c:set var="account_tip" value="恭喜您已成为运营中心" />
        <c:set var="account_logo" value="${ctx}/styles/index/images/yun.png" />
      </c:if>
      <img title="${account_tip}" class="user_shenfen" src="${account_logo}" /> 
      </span> 
       <a href="${ctx}/m/MMyAccount.do?mod_id=1100400200" class="right_a"><img src="${ctx}/m/styles/img/set.png" width="32" /></a>
      <c:if test="${not empty titleSideName}">
        <%-- 				<a href="${ctx}/m/MIndexLogin.do?method=logout" class="right_b"><img --%>
        <%-- 					src="${ctx}/m/styles/img/logout.png"  width="32" /></a> --%>
      </c:if>
    </div>
    <div class="cont mhome">
      <div class="cont_list" style="height: 0rem;top: 0.01rem;"> 
        <!-- 余额 -->
        <fmt:formatNumber var="bi_src" value="${userInfo.bi_dianzi}" pattern="0.########" />
        <fmt:formatNumber var="bi" value="${userInfo.bi_dianzi}" pattern="0.##" />
        <fmt:formatNumber var="bi1" value="${userInfo.bi_dianzi}" pattern="0" />
        <c:if test="${bi1 gt 10000}">
          <fmt:formatNumber var="bi" value="${bi/10000}" pattern="#.##万" />
        </c:if>
        <c:url var="url" value="/m/MMyQianBao.do?mod_id=1100400050" />
        <c:if test="${empty userInfo}">
          <c:url var="url" value="/m/MIndexLogin.do" />
          <a onclick="goUrl('${url}')" class="my-btnlogin app_login">点击登录</a> </c:if>
        <c:if test="${not empty userInfo}"> <span class="tttext size-16" style="display:none;">总金额</span> 
        <span class="ttprice size-16" onclick="goUrl('${url}');">
          <a class="app_mynote" title="您共有：${bi_src}余额，点击查看余额明细"><span style="display:none;">${bi}</span></a> </span> </c:if>
      </div>
    </div>
    <div class="butt mhome height${userInfo.is_entp}" id="mhome_shadow">
      <ul class="butt_list${userInfo.is_entp}">
        <li>
          <c:if test="${not empty userInfo}"> <span class="tttext size-14">总金额</span> 
          <span class="ttprice size-16" onclick="goUrl('${url}');" >
           <a class="app_mynote" title="您共有：${bi_src}余额，点击查看余额明细"> <span>${bi}</span></a> </span> </c:if>
        </li>
        <li>
          <c:if test="${not empty userInfo}"> 
            <!-- 消费券 -->
            <fmt:formatNumber var="bi_src" value="${userInfo.bi_dianzi_lock}" pattern="0.########" />
            <fmt:formatNumber var="bi" value="${userInfo.bi_dianzi_lock}" pattern="0.##" />
            <fmt:formatNumber var="bi1" value="${userInfo.bi_dianzi_lock}" pattern="0" />
            <c:if test="${bi1 gt 10000}">
              <fmt:formatNumber var="bi" value="${bi/10000}" pattern="#.##万" />
            </c:if>
            <c:url var="url"
							value="/m/MMyTianfan.do?par_id=1100410000" />
            <span class="tttext size-14" title="您共有：${bi_src}消费券，点击查看消费消费券明细">消费券</span> <span class="ttprice size-16" onclick="goUrl('${url}');">${bi}</span> </a> </span> </c:if>
        </li>
        <c:if test="${userInfo.is_entp eq 1}">
          <li> 
            <!-- 商家增值券 -->
            <fmt:formatNumber var="bi_src" value="${userInfo.leiji_money_entp/rmb_to_fanxianbi_rate}" pattern="0.########" />
            <fmt:formatNumber var="bi" value="${userInfo.leiji_money_entp/rmb_to_fanxianbi_rate}" pattern="0.##" />
            <fmt:formatNumber var="bi1" value="${userInfo.leiji_money_entp/rmb_to_fanxianbi_rate}" pattern="0" />
            <c:if test="${bi1 gt 10000}">
              <fmt:formatNumber var="bi" value="${bi/10000}" pattern="#.##万" />
            </c:if>
            <c:url var="url" value="/m/MPayOffline.do?par_id=1300510000&mod_id=1300510140" />
            <span class="tttext size-14">增值券</span></a> 
            <span class="ttprice  size-16" title="您共有：${bi_src}商家增值券，点击查看商家增值券明细" onclick="goUrl('${url}');">${bi}</span> </li>
        </c:if>
        <li> 
          <!-- 货款 -->
          <c:if test="${userInfo.is_entp eq 1}">
            <fmt:formatNumber var="bi_src" value="${userInfo.bi_huokuan}" pattern="0.########" />
            <fmt:formatNumber var="bi" value="${userInfo.bi_huokuan}" pattern="0.##" />
            <fmt:formatNumber var="bi1" value="${userInfo.bi_huokuan}" pattern="0" />
            <c:if test="${bi1 gt 10000}">
              <fmt:formatNumber var="bi" value="${bi/10000}" pattern="#.##万" />
            </c:if>
            <c:url var="url" value="/m/MTiXianHuoKuanBi.do?method=add&&par_id=1300100000&mod_id=1300300400" />
            <span class="tttext size-14">货款</span> 
            <span class="ttprice size-16" title="您共有：${bi_src}货款，点击查看货款明细" onclick="goUrl('${url}');">${bi}</span> </a> </c:if>
        </li>
        <li style="display:none;">
          <c:if test="${not empty userInfo}"> 
            <!-- 个人积分 -->
            <c:url var="url" value="/m/MMyScore.do?method=list&mod_id=1100610300" />
            <fmt:formatNumber var="score" value="${userInfo.cur_score + userInfo.user_score_union}" pattern="0.##" />
            <fmt:formatNumber var="score1" value="${userInfo.cur_score + userInfo.user_score_union}" pattern="0" />
            <c:if test="${score1 gt 10000}">
              <fmt:formatNumber var="score" value="${score1/10000}" pattern="#.##万" />
            </c:if>
            <span class="tttext size-14">总积分</span> <span class="ttprice  size-16" onclick="goUrl('${url}');">${score}</span> </c:if>
        </li>
      </ul>
    </div>
  </header>
</div>
<script type="text/javascript">
<c:if test="${empty userInfo}">
$("#mhome_shadow").css({"top":"1rem","position":"absolute"});
$(".cont_list").css({"z-index":"9999"});
$(".myls-infowarp").css("height","2.0rem");
</c:if>
</script>