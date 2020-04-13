<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
<style type="text/css">
#tablePagination { 
  padding:5px; 
  height:30px;
}
</style>	
</head>
<body>
<div class="mainbox mine">
  <jsp:include page="../_nav.jsp" flush="true"/>
    <div style="padding:5px;">
      <button class="bgButtonFontAwesome" type="button" onclick="getMyOffline();"><i class="fa fa-file-text"></i>查看关系树</button>
    </div>
    <div style="padding:5px;" class="tip-success">
    我的上级：
    </div>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable" id="table-advanced">
      <tr>
        <th>用户名</th>
        <th>姓名</th>
        <th width="12%">积分</th>
        <th width="12%">会员星级</th>
        <th width="12%">身份</th>
        <th width="12%">添加时间</th>
      </tr>
        <tr>
          <td align="center">${fn:escapeXml(uipar.user_name)}</td>
          <td align="center">${fn:escapeXml(uipar.real_name)}</td>
          <fmt:formatNumber var="score" value="${uipar.cur_score}" pattern="0.########"/>
          <td align="center">${score}</td>
          <td align="center"><c:forEach items="${baseDataList}" var="keys"><c:if test="${uipar.user_level eq  keys.id}">${keys.type_name}</c:if></c:forEach></td>
          <c:set var="shenfen" value="会员" />
          <c:if test="${uipar.is_entp eq 1}">
          <c:set var="shenfen" value="商家" />
          </c:if>
          <c:if test="${uipar.is_fuwu eq 1}">
          <c:set var="shenfen" value="合伙人" />
          </c:if>
          <td align="center">${shenfen}</td>
          <td align="center"><fmt:formatDate value="${uipar.add_date}" pattern="yyyy-MM-dd" /></td>
          </td>
        </tr>
    </table>
    <div style="padding:5px;" class="tip-danger">
    我的下级：
    </div>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable" id="table-advanced">
      <tr>
        <th>用户名</th>
        <th>姓名</th>
        <th width="12%">积分</th>
        <th width="12%">会员推广</th>
        <th width="12%">会员星级</th>
        <th width="12%">身份</th>
        <th width="12%">添加时间</th>
        <th width="12%" nowrap="nowrap">操作</th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr>
          <td align="center"><a title="查看" href="MyLowerLevel.do?method=view&amp;id=${cur.id}&mod_id=${af.map.mod_id}&par_id=${af.map.par_id}">${fn:escapeXml(cur.map.userInfo.user_name)}</a></td>
          <td align="center">${fn:escapeXml(cur.map.userInfo.real_name)}</td>
          <fmt:formatNumber var="score" value="${cur.map.userInfo.cur_score}" pattern="0.########"/>
          <td align="center">${score}</td>
          <td align="center">${cur.user_par_levle}层会员</td>
          <td align="center"><c:forEach items="${baseDataList}" var="keys"><c:if test="${cur.map.userInfo.user_level eq  keys.id}">${keys.type_name}</c:if></c:forEach></td>
          <c:set var="shenfen" value="会员" />
          <c:if test="${cur.map.userInfo.is_entp eq 1}">
          <c:set var="shenfen" value="商家" />
          </c:if>
          <c:if test="${cur.map.userInfo.is_fuwu eq 1}">
          <c:set var="shenfen" value="合伙人" />
          </c:if>
          <td align="center">${shenfen}</td>
          <td align="center"><fmt:formatDate value="${cur.add_date}" pattern="yyyy-MM-dd" /></td>
          <td align="center" nowrap="nowrap"><a href="MyLowerLevel.do?method=view&amp;id=${cur.id}&mod_id=${af.map.mod_id}&par_id=${af.map.par_id}">查看</a>&nbsp;
          </td>
        </tr>
      </c:forEach>
    </table>
    <div class="black">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="${ctx}/manager/customer/MyLowerLevel.do">
      <table width="98%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td height="10"><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
				var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
		        pager.addHiddenInputs("method", "list");
		        pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
		        pager.addHiddenInputs("par_id", "${af.map.par_id}");
		        document.write(pager.toString());
            	</script></td>
        </tr>
      </table>
    </form>
  </div>
</div>
<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript">
$(document).ready(function(){
});

function getMyOffline(){
	var url = "MyLowerLevel.do?method=myOffline";
	$.dialog({
		title:  "查看用户关系树",
		width:  770,
		height: 550,
        lock:true ,
		content:"url:"+url
	});
}
</script>
</body>
</html>
