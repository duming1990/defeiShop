<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<link href="${ctx}/commons/styles/nav.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/index/css/btns.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="mainbox mine">
   <div style="padding:5px;" class="tip-success">
    <h3>【${user_name_par}】的上级：</h3>
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
          <fmt:formatNumber var="score" value="${uipar.cur_score+uipar.user_score_union}" pattern="0.########"/>
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
  <div class="tip-danger">
    <h3>【${user_name_par}】的下三级用户:${userSonLevel3Count}个（全部下级：${userSonLevelCount}个）</h3>
  </div>
   <div class="all">
      <ul class="nav nav-tabs" id="nav_ul_content">
          <li class="active"><a href="${ctx}/manager/customer/MyTeam.do?method=list&user_id=${user_id}"><span>列表查看</span></a></li>
          <li><a href="${ctx}/manager/customer/MyTeam.do?method=lowerlist&user_id=${user_id}&par_level=1"><span>我的第一级(${recordCount1})</span></a></li>
          <li><a href="${ctx}/manager/customer/MyTeam.do?method=lowerlist&user_id=${user_id}&par_level=2"><span>我的第二级(${recordCount2})</span></a></li>
          <li><a href="${ctx}/manager/customer/MyTeam.do?method=lowerlist&user_id=${user_id}&par_level=3"><span>我的第三级(${recordCount3})</span></a></li>
          <li><a href="${ctx}/manager/customer/MyTeam.do?method=listChart&user_id=${user_id}"><span>图形查看</span></a></li>
          <li onclick="history.back();"><a class="tip-danger"><span>返回上页</span></a></li>
      </ul>
    </div>
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
    <tr>
      <th width="5%" nowrap="nowrap">序号</th>
      <th width="8%">是否商家</th>
      <th width="10%">是否合伙人</th>
      <th nowrap="nowrap">登录名</th>
      <th width="8%">真实姓名</th>
      <th width="8%">个人积分</th>
      <th width="12%">注册时间</th>
      <th width="9%">邀请会员</th>
    </tr>
   <c:if test="${empty userInfoList}">
      <tr>
        <td colspan="10" align="center"><div class="tip-danger"> 没有该级成员</div></td>
      </tr>
    </c:if>
    <c:forEach var="cur" items="${userInfoList}" varStatus="vs">
      <tr>
        <td align="center">${vs.count}</td>
        <td align="center"><c:choose>
            <c:when test="${cur.is_entp eq 0}"> <span style="color: #F00;">会员</span> </c:when>
            <c:when test="${cur.is_entp eq 1}"> <span style="color: #060;">商家</span> </c:when>
          </c:choose></td>
        <td align="center"><c:choose>
            <c:when test="${cur.is_fuwu eq 0}"> <span style="color: #F00;">非合伙人</span> </c:when>
            <c:when test="${cur.is_fuwu eq 1}"> <span style="color: #060;">合伙人</span> </c:when>
          </c:choose></td>
        <td align="center"><a title="查看" href="${ctx}/manager/admin/UserRelation.do?method=list&user_id=${cur.id}&mod_id=1002005000">${fn:escapeXml(cur.user_name)}</a> </td>
        <td align="center">${fn:escapeXml(cur.real_name)}</td>
        <fmt:formatNumber var="score" value="${cur.cur_score}" pattern="0.########"/>
        <td align="center">${score}</td>
        <fmt:formatNumber var="score_union" value="${cur.user_score_union}" pattern="0.########"/>
        <td align="center"><fmt:formatDate value="${cur.add_date}" pattern="yyyy-MM-dd HH:mm:ss" />
        </td>
        <td nowrap="nowrap" align="center">${cur.map.user_son_count}</td>
      </tr>
    </c:forEach>
  </table>
</div>
 <div class="black">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="MyTeam.do">
      <table width="98%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td height="10"><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script> 
            <script type="text/javascript">
				var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
				pager.addHiddenInputs("method", "lowerlist");
				pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
				pager.addHiddenInputs("user_id", "${af.map.user_id}");
				pager.addHiddenInputs("par_level", "${af.map.par_level}");
				document.write(pager.toString());
            	</script></td>
        </tr>
      </table>
    </form>
  </div>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script>
<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script>
<script type="text/javascript">//<![CDATA[
var par_level=${af.map.par_level};
if(par_level==1){
	$("#nav_ul_content li").removeClass("active");
	$("#nav_ul_content li:eq(1)").addClass("active");
}
if(par_level==2){
	$("#nav_ul_content li").removeClass("active");
	$("#nav_ul_content li:eq(2)").addClass("active");
}
if(par_level==3){
	$("#nav_ul_content li").removeClass("active");
	$("#nav_ul_content li:eq(3)").addClass("active");
}
//]]></script>
</body>
</html>
