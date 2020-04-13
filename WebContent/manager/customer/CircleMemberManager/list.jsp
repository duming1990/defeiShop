<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/commons/styles/icons/icons.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="mainbox mine" style="height: 1400px">
  <jsp:include page="../_nav.jsp" flush="true"/>
  <html-el:form action="/customer/CircleMemberManager">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="par_id" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellpadding="1" cellspacing="1" class="tableClassSearch">
      <tr>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">
            <tr>
              <td width="6%" nowrap="nowrap">用户名称：
                <html-el:text property="user_name_like" styleId="user_name_like" style="width:140px;" styleClass="webinput"/>
                &nbsp;审核状态：
                <html-el:select property="audit_state" styleClass="webinput" >
                  <html-el:option value="">全部</html-el:option>
                  <html-el:option value="-1">审核不通过</html-el:option>
                  <html-el:option value="0">待审核</html-el:option>
                  <html-el:option value="1">审核通过</html-el:option>
                </html-el:select>
                &nbsp;&nbsp;
                <button class="bgButtonFontAwesome" type="submit"><i class="fa fa-search"></i>查 询</button>
              </td>
            </tr>
          </table></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="CircleMemberManager.do?method=delete">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
      <tr>
        <th width="5%">序号</th>
        <th width="10%">用户名</th>
        <th width="10%">真实姓名</th>
        <th width="8%">手机</th>
        <th width="8%">添加时间</th>
        <th width="8%">审核状态</th>
        <th width="10%">审核说明</th>
        <th width="10%" nowrap="nowrap">操作</th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr>
          <td align="center">${vs.count}</td>
          <td align="center">${fn:escapeXml(cur.map.userInfo.user_name)}</td>
          <td align="center">${fn:escapeXml(cur.map.userInfo.real_name)}</td>
          <td align="center">${fn:escapeXml(cur.mobile)}</td>
          <td align="center"><fmt:formatDate value="${cur.add_date}" pattern="yyyy-MM-dd" /></td>
          <td align="center"><c:choose>
              <c:when test="${cur.audit_state eq -1}"><span style=" color:#F00;">审核不通过</span></c:when>
              <c:when test="${cur.audit_state eq 0}"><span>待审核</span></c:when>
              <c:when test="${cur.audit_state eq 1}"><span style=" color:#060;">审核通过</span></c:when>
            </c:choose>
          </td>
          <td align="center">${fn:escapeXml(cur.audit_desc)}</td>
          <td align="center" nowrap="nowrap">
            <logic-el:match name="popedom" value="+8+">
               <a class="butbase" onclick="doNeedMethod(null, 'CircleMemberManager.do', 'audit','id=${cur.id}&mod_id=${af.map.mod_id}&'+$('#bottomPageForm').serialize())"><span class="icon-ok">审核</span></a> 
            </logic-el:match>
            <logic-el:match name="popedom" value="+4+">
               <c:if test="${cur.audit_state ne 1}"><a class="butbase" onclick="confirmDelete(null, 'CircleMemberManager.do', 'id=${cur.id}&mod_id=${af.map.mod_id}&' + $('#bottomPageForm').serialize())"><span class="icon-remove">删除</span></a> </c:if>
               <c:if test="${cur.audit_state eq 1}"><a class="butbase but-disabled" title="已审核，不能删除"><span class="icon-remove">删除</span></a> </c:if>
            </logic-el:match>
          </td>
        </tr>
      </c:forEach>
    </table>
  </form>
  <div class="black">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="CircleMemberManager.do">
      <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" >
        <tr>
          <td height="10" ><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
            pager.addHiddenInputs("method", "list");
            pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
            pager.addHiddenInputs("par_id", "${af.map.par_id}");
            pager.addHiddenInputs("village_name_like", "${fn:escapeXml(af.map.village_name_like)}");
            pager.addHiddenInputs("audit_state", "${fn:escapeXml(af.map.audit_state)}");
            document.write(pager.toString());
        </script></td>
        </tr>
      </table>
    </form>
  </div>
</div>
<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript" src="${ctx}/scripts/tip/jquery.quicktip.min.js"></script>
<script type="text/javascript">
	function initPassword(id) {
		if (confirm("确认要初始化密码吗？")) {
			var password = prompt("请输入您的新密码,如果不输入,默认初始密码为“${init_pwd}”。","");
			if (null != password) {
				if (password.length == 0) {
					password = "${init_pwd}";
				}
				$.post("CsAjax.do?method=initPassword",{uid : id, password : password},function(data){
					if(null != data.result){alert(data.msg);} else {alert("初始化密码失败");}
				});
			}
		}
		return false;
	}
</script>
</body>
</html>
