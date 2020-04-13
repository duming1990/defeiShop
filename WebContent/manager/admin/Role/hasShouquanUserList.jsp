<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>查看已授权用户</title>
<base target="_self" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
</head>
<body>
<div class="divContent" style="margin:5px;"> 
  <html-el:form action="/admin/Role.do">
    <html-el:hidden property="method" value="getHasShouquanUserList" />
    <html-el:hidden property="role_id" />
    <table width="99%" border="0" cellpadding="0" cellspacing="0" class="tableClassSearch">
      <tr>
        <td>用户姓名:
          <html-el:text property="user_name_like" maxlength="20" styleId="nurse_name_like" style="width:80px;" styleClass="webinput" /> 
           &nbsp;手机号码:
          <html-el:text property="mobile_like" maxlength="20" styleId="mobile_like" style="width:80px;" styleClass="webinput" /> 
            &nbsp;
            <html-el:submit value="查 询" styleClass="bgButton" />
        </td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" >
    <table width="99%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th width="5%" nowrap="nowrap">序号</th>
        <th nowrap="nowrap">用户姓名</th>
        <th nowrap="nowrap">手机号码</th>
        <th width="12%" nowrap="nowrap">操作</th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr>
          <td align="center" nowrap="nowrap">${vs.count}</td>
          <td align="left" nowrap="nowrap"><span>${fn:escapeXml(cur.map.user_name)}</span></td>
          <td align="center" nowrap="nowrap"><span>${fn:escapeXml(cur.map.mobile)}</span></td>
          <td align="center">
          <a class="butbase" onclick="canaleRoleUser(${cur.id});">
          <span class="icon-ok">取消授权</span></a></td>
        </tr>
      </c:forEach>
    </table>
  </form>
  <div class="pageClass">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="Role.do">
      <table width="98%" border="0" cellpadding="0" cellspacing="0">
        <tr align="center">
          <td><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script> 
            <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
            pager.addHiddenInputs("method", "getHasShouquanUserList");
            pager.addHiddenInputs("user_name_like", "${fn:escapeXml(af.map.user_name_like)}");
			pager.addHiddenInputs("is_del", "${af.map.is_del}"); 
			pager.addHiddenInputs("mobile_like", "${af.map.mobile_like}"); 
			pager.addHiddenInputs("sex", "${af.map.sex}");
			pager.addHiddenInputs("user_type", "${af.map.user_type}");
			pager.addHiddenInputs("role_id", "${af.map.role_id}");
            document.write(pager.toString());
            </script></td>
        </tr>
      </table>
    </form>
  </div>
</div>
 
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript">//<![CDATA[
function canaleRoleUser(id) {
	  $.post("?method=canalRoleUser",{id:id},function(data){
		     if(data.ret == 1){
		    	 $.jBox.tip(data.msg, "success",2000);
		    	 window.setTimeout(function () { 
		    	 location.reload();
		    	 }, 1000);
		     }else{
		    	 $.jBox.tip(data.msg, "error",2000);
		     }
	  });
}

//]]></script>
</body>
</html>
