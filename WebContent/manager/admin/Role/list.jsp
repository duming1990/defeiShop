<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
</head>
<body>
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/admin/Role">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClassSearch">
      <tr>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">
            <tr>
              <td width="19%" nowrap="nowrap">&nbsp;角色名称：
                <html-el:text property="role_name_like" style="width:80px;" styleClass="webinput"/>
                &nbsp;是否删除：
                <html-el:select property="is_del">
                  <html-el:option value="">全部</html-el:option>
                  <html-el:option value="0">未删除</html-el:option>
                  <html-el:option value="1">已删除</html-el:option>
                </html-el:select>
                &nbsp;
                <html-el:submit value="查 询" styleClass="bgButton" /></td>
            </tr>
          </table></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="Role.do?method=delete">
    <div style="text-align: left; padding: 5px;">
      <input type="button" name="delete" id="delete" class="bgButton" value="删除所选" onclick="this.form.action += '&' + $('#bottomPageForm').serialize();confirmDeleteAll(this.form);" />
      <input type="button" name="add" id="add" class="bgButton" value="添 加" onclick="location.href='Role.do?method=add&mod_id=${af.map.mod_id}';" />
    </div>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th width="7%" align="center" nowrap="nowrap"><input name="chkAll" type="checkbox" id="chkAll" value="-1" onclick="checkAll(this);" /></th>
         <th width="8%">编码</th>
        <th align="center" nowrap="nowrap">角色名称</th>
        <th width="10%" align="center" nowrap="nowrap">添加时间</th>
        <th width="8%" align="center" nowrap="nowrap">排序值</th>
        <th width="8%" align="center" nowrap="nowrap">是否删除</th>
        <th width="8%" align="center" nowrap="nowrap">授权</th>
        <th width="15%" align="center" nowrap="nowrap">操作</th>
      </tr>
      <c:forEach var="cur" items="${roleList}">
        <tr>
          <td align="center"><c:set var="isLock" value="${cur.is_lock eq 1}"></c:set>
            <c:set var="isDel" value="${cur.is_del eq 1}"></c:set>
            <c:if test="${isLock or isDel}">
              <input name="pks" type="checkbox" id="pks" value="${cur.id}" disabled="disabled"/>
            </c:if>
            <c:if test="${not isLock and not isDel}">
              <input name="pks" type="checkbox" id="pks" value="${cur.id}" />
            </c:if></td>
             <td align="center">${cur.id}</td>
          <td align="left">
            <a title="查看" href="Role.do?method=view&amp;mod_id=${af.map.mod_id}&amp;id=${cur.id}">${fn:escapeXml(cur.role_name)}</a>
          </td>
          <td align="center"><fmt:formatDate value="${cur.add_date}" pattern="yyyy-MM-dd" /></td>
          <td align="center"><c:out value="${cur.order_value }"></c:out></td>
          <td align="center"><c:choose>
              <c:when test="${cur.is_del eq 0}"> <span style="color: #060;">未删除</span> </c:when>
              <c:when test="${cur.is_del eq 1}"> <span style="color: #F00;">已删除</span> </c:when>
            </c:choose></td>
		  <c:if test="${userInfo.user_type eq 1}">
	          <td align="center">
	          <a class="butbase" onclick="doNeedMethod(null, 'ModPopedom.do', 'edit', 'mod_id=${af.map.mod_id}&amp;role_id=${cur.id}&amp;url=Role');" href="javascript:void(0);">
	          <span class="icon-configure">授权</span></a>
	          </td>
	          <td align="center" nowrap="nowrap">
	           <a class="butbase"  onclick="confirmUpdate('null', 'Role.do', 'id=${cur.id}&' + $('#bottomPageForm').serialize())"><span  class="icon-edit">修改</span></a>
	            <c:if test="${isLock or isDel}">
	            <a class="butbase but-disabled"  onclick="javascript:void(0);"  title="已锁定或已删除，不能删除"><span class="icon-remove">删除</span></a> 
	            </c:if>
	            <c:if test="${not isLock and not isDel}">
	            <a class="butbase"  onclick="confirmDelete(null, 'Role.do', 'id=${cur.id}&' + $('#bottomPageForm').serialize())"><span class="icon-remove">删除</span></a>
	            </c:if>
	          </td>
          </c:if>
		  <c:if test="${userInfo.user_type ne 1}">
	          <td align="center"><img src="${ctx}/styles/images/shouquan_2.gif" width="55" height="18" /></td>
	          <td align="center" nowrap="nowrap"><img src="${ctx}/styles/images/xiugai_2.gif" width="55" height="18" /><img src="${ctx}/styles/images/shanchu_2.gif" width="55" height="18" /></td>
          </c:if>
        </tr>
     </c:forEach>
    </table>
  </form>
<div class="pageClass">
  <form id="bottomPageForm" name="bottomPageForm" method="post" action="Role.do">
    <table width="98%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td height="10"><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
          <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
            pager.addHiddenInputs("method", "list");
            pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
            pager.addHiddenInputs("id", "${af.map.id}");
			pager.addHiddenInputs("role_name", "${af.map.role_name}");
            pager.addHiddenInputs("is_del", "${af.map.is_del}");
            pager.addHiddenInputs("role_name_like", "${fn:escapeXml(af.map.role_name_like)}");
            document.write(pager.toString());
            </script></td>
      </tr>
    </table>
  </form>
</div>
</div>

<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script>
<script type="text/javascript">//<![CDATA[
$("#id_role_id").focus(setOnlyNum).blur(function(){
	if(/[.+]+/.test(this.value)){
		alert("ID只能为整数！");
		this.value = this.value.replace(/[.+]/g,"");
		this.focus();
		return false;
	}	
});
function setOnlyNum() {
	$(this).css("ime-mode", "disabled").attr("t_value", "").attr("o_value", "").bind("dragenter",function(){
		return false;
	});
	$(this).keypress(function (){
		if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value;
	}).keyup(function (){
		if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value;
	}).blur(function (){
		if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value;}
		if(this.value.length == 0) this.value = "";
	});
	//this.text.selected;
}
//]]></script>
<jsp:include page="../public_page.jsp" flush="true" />
</body>
</html>
