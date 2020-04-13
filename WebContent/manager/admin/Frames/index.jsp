<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${app_name}后台管理系统</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link rel="stylesheet" type="text/css" href="${ctx}/commons/styles/manager/css/index.css?v=20161129"  />
<script type="text/javascript" src="${ctx}/commons/scripts/public_admin.js?v=20161128"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.bgColorSelector.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jquery-ui/ui/jquery-ui.custom.js"></script>
<link rel="stylesheet" href="${ctx}/commons/jerichotab/css/jquery.jerichotab.css" />
<script type="text/javascript" src="${ctx}/commons/jerichotab/js/jquery.jerichotab.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
</head>
<body style="background-color:#468E33;">
<div class="admincp-header">
  <div class="bgSelector"></div>
  <div id="foldSidebar"><i class="fa fa-outdent" title="展开/收起侧边导航"></i></div>
  <div class="admincp-name">
    <h1>${app_name}</h1>
    <h2>电商平台系统管理中心</h2>
  </div>
  <div class="nc-module-menu">
    <ul class="nc-row" id="nc-row">
     <c:forEach var="cur" items="${sysModuleParList}" varStatus="vs">
      <c:set var="liClass" value="" />
      <c:if test="${vs.count eq 1}">
      <c:set var="liClass" value="active" />
      </c:if>
      <li class="${liClass}" id="parParSysTop_${cur.mod_id}" data-id="${cur.mod_id}" onclick="setCookieAndOpenUrl(null,this,1,true);">
      <a href="javascript:void(0);">${fn:escapeXml(cur.mod_name)}</a></li>
     </c:forEach> 
    </ul>
  </div>
  <div class="admincp-header-r">
    <div class="manager">
      <dl>
        <dt class="name">${fn:escapeXml(userInfo.user_name)}</dt>
        <dd class="group">${fn:escapeXml(userInfo.real_name)}</dd>
      </dl>
      <span class="avatar">
      <img src="${ctx}/commons/styles/manager/images/admin.png" /></span>
      <i class="arrow" id="admin-manager-btn"></i>
      <div class="manager-menu" id="updatePass">
        <div class="title">
          <h4>最后登录</h4>
          <a href="javascript:void(0);" onclick="updatePassDia();" class="edit-password tip tip-danger">修改密码</a>
          </div>
          
          <div class="login-date">
          <fmt:formatDate value="${userInfo.last_login_time}" pattern="yyyy-MM-dd HH:mm:SS" /><br/>
          IP:${userInfo.last_login_ip}</div>
          
          <div class="title">
          	<a href="javascript:void(0);" onclick="updateBank();" class="edit-password tip tip-danger">修改银行卡信息</a>
          </div>
      </div>
    </div>
    <ul class="operate nc-row">
      <li><a class="style-color show-option" id="trace_show" title="给管理中心换个颜色" href="javascript:void(0);">&nbsp;</a></li>
      <li>
      <c:url var="url" value="/index.do"/>
      <a class="homepage show-option" target="_blank" href="${url}" title="新窗口打开商城首页">&nbsp;</a></li>
      <li>
      <a class="login-out show-option" onclick="loginOut();" href="javascript:void(0);" title="安全退出管理中心">&nbsp;</a></li>
    </ul>
  </div>
  <div class="clear"></div>
</div>
<div class="admincp-container unfold" id="admincp-container">
  <jsp:include page="left.jsp" flush="true"/>
  <div class="admincp-container-right" id="right">
    <div class="top-border"></div>
    <iframe src="" id="workspace" name="workspace" style="overflow:visible;" frameborder="0" width="100%" height="94%" scrolling="yes">
    </iframe>
  </div>
</div>
<script type="text/javascript">

</script>
</body>
</html>
