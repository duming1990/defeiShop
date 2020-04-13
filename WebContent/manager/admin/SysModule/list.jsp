<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link rel="stylesheet" href="${ctx}/commons/treetable/stylesheets/jquery.treetable.css" />
<link rel="stylesheet" href="${ctx}/commons/treetable/stylesheets/jquery.treetable.theme.default.css" />
</head>
<body>
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/admin/SysModule">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="type" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClassSearch">
      <tr>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">
            <tr>
              <td nowrap="nowrap"> 节点名称：
                <html-el:text property="mod_name_like" styleId="mod_name_like" style="width:80px;" styleClass="webinput"/>
                &nbsp;节点URL：
                <html-el:text property="mod_url_like" styleId="mod_url_like" style="width:80px;" styleClass="webinput"/>
                &nbsp;节点描述：
                <html-el:text property="mod_desc_like" styleId="mod_desc_like" style="width:80px;" styleClass="webinput"/>
                &nbsp;节点组：
                <html-el:select property="mod_group">
                  <html-el:option value="">全部</html-el:option>
                  <c:forEach items="${modGroups}" var="cur">
                    <html-el:option value="${cur.index}">${cur.name}</html-el:option>
                  </c:forEach>
                </html-el:select>
                &nbsp;是否锁定：
                <html-el:select property="is_lock">
                  <html-el:option value="">全部</html-el:option>
                  <html-el:option value="0">未锁定</html-el:option>
                  <html-el:option value="1">已锁定</html-el:option>
                </html-el:select>
                &nbsp;是否删除：
                <html-el:select property="is_del">
                  <html-el:option value="">全部</html-el:option>
                  <html-el:option value="0">未删除</html-el:option>
                  <html-el:option value="1">已删除</html-el:option>
                </html-el:select>
                &nbsp;&nbsp;
                <html-el:submit value="查 询" styleClass="bgButton" /></td>
            </tr>
          </table></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="SysModule.do?method=delete">
<!--     <div style="text-align: left; padding: 5px;"> -->
<%--       <logic-el:match name="popedom" value="+4+"> --%>
<!--         <input type="button" name="delete" id="delete" class="bgButton" value="删除所选" onclick="this.form.action += '&' + $('#bottomPageForm').serialize();confirmDeleteAll(this.form);" /> -->
<%--       </logic-el:match> --%>
<%--       <logic-el:match name="popedom" value="+1+"> --%>
<%--         <input type="button" name="add" id="add" class="bgButton" value="添 加" onclick="location.href='SysModule.do?method=add&mod_id=${af.map.mod_id}&type=${af.map.type}';" /> --%>
<%--       </logic-el:match> --%>
<!--     </div> -->
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass" id="table-advanced">
      <thead>
        <tr>
          <th nowrap="nowrap" width="15%">节点名称</th>
          <th width="8%">编码</th>
          <th width="8%">父编码</th>
          <th width="5%">层级</th>
          <th>节点URL</th>
          <th width="8%">排序值</th>
          <th width="6%">是否锁定</th>
          <th width="6%">是否删除</th>
          <c:if test="${fn:contains(popedom, '+2+') or fn:contains(popedom, '+4+')}" var="isContains">
            <th width="15%" nowrap="nowrap">操作</th>
          </c:if>
        </tr>
      <thead>
      <tbody>
        <c:forEach var="cur" items="${sysModule1List}" varStatus="vs">
          <c:set var="par_id" value="" />
          <c:if test="${cur.mod_level ne 1}">
            <c:set var="par_id" value="data-tt-parent-id='${cur.par_id}'" />
          </c:if>
          <c:set var="isLock" value="${cur.is_lock eq 1}"></c:set>
          <c:set var="isDel" value="${cur.is_del eq 1}"></c:set>
          <tr data-tt-id="${cur.mod_id}" ${par_id}>
            <td align="left">${fn:escapeXml(cur.mod_name)}</td>
            <td align="center">${cur.mod_id}</td>
            <td align="center">${cur.par_id}</td>
            <td align="center">${cur.mod_level}</td>
            <td align="left">${cur.mod_url}</td>
            <td align="center"><c:out value="${cur.order_value }"></c:out></td>
            <td align="center"><c:choose>
                <c:when test="${cur.is_lock eq 0}"><span style=" color:#060;">未锁定</span></c:when>
                <c:when test="${cur.is_lock eq 1}"><span style=" color:#ccc;">已锁定</span></c:when>
              </c:choose></td>
            <td align="center"><c:choose>
                <c:when test="${cur.is_del eq 0}"><span style=" color:#060;">未删除</span></c:when>
                <c:when test="${cur.is_del eq 1}"><span style=" color:#F00;">已删除</span></c:when>
              </c:choose></td>
            <c:if test="${isContains}" >
              <td align="center" nowrap="nowrap"><logic-el:match name="popedom" value="+2+"> <a class="butbase" href="javascript:void(0);"><span class="icon-edit" onclick="confirmUpdate(null, 'SysModule.do', 'id=${cur.mod_id}&' + $('#bottomPageForm').serialize())">修改</span></a> </logic-el:match>
                <logic-el:match name="popedom" value="+4+">
                  <c:if test="${isLock or isDel}"><a class="butbase but-disabled" href="javascript:void(0);"><span class="icon-remove">删除</span></a></c:if>
                  <c:if test="${not isLock and not isDel}"> <a class="butbase" href="javascript:void(0);"><span class="icon-remove" onclick="confirmDelete(null, 'SysModule.do', 'id=${cur.mod_id}&' + $('#bottomPageForm').serialize())">删除</span></a> </c:if>
                </logic-el:match>
                <logic-el:match name="popedom" value="+1+">
                <a class="butbase" onclick="location.href='SysModule.do?method=add&par_id=${cur.mod_id}&mod_id=${af.map.mod_id}';" ><span class="icon-add">添加下级</span></a>
                </logic-el:match>
                </td>
            </c:if>
          </tr>
          <c:forEach var="cur2" items="${cur.sysModuleList}">
            <c:set var="isLock" value="${cur2.is_lock eq 1}"></c:set>
            <c:set var="isDel" value="${cur2.is_del eq 1}"></c:set>
            <tr data-tt-id="${cur2.mod_id}" data-tt-parent-id="${cur2.par_id}">
              <td align="left"><i class="${cur2.font_awesome}"></i> ${fn:escapeXml(cur2.mod_name)}</td>
              <td align="center">${cur2.mod_id}</td>
              <td align="center">${cur2.par_id}</td>
              <td align="center">${cur2.mod_level}</td>
              <td align="left">${cur2.mod_url}</td>
              <td align="center"><c:out value="${cur2.order_value }"></c:out></td>
              <td align="center"><c:choose>
                  <c:when test="${cur2.is_lock eq 0}"><span style=" color:#060;">未锁定</span></c:when>
                  <c:when test="${cur2.is_lock eq 1}"><span style=" color:#ccc;">已锁定</span></c:when>
                </c:choose></td>
              <td align="center"><c:choose>
                  <c:when test="${cur2.is_del eq 0}"><span style=" color:#060;">未删除</span></c:when>
                  <c:when test="${cur2.is_del eq 1}"><span style=" color:#F00;">已删除</span></c:when>
                </c:choose></td>
              <c:if test="${isContains}" >
                <td align="center" nowrap="nowrap"><logic-el:match name="popedom" value="+2+"> <a class="butbase" href="javascript:void(0);"><span class="icon-edit" onclick="confirmUpdate(null, 'SysModule.do', 'id=${cur2.mod_id}&' + $('#bottomPageForm').serialize())">修改</span></a> </logic-el:match>
                  <logic-el:match name="popedom" value="+4+">
                    <c:if test="${isLock or isDel}"><a class="butbase but-disabled" href="javascript:void(0);"><span class="icon-remove">删除</span></a></c:if>
                    <c:if test="${not isLock and not isDel}"> <a class="butbase" href="javascript:void(0);"><span class="icon-remove" onclick="confirmDelete(null, 'SysModule.do', 'id=${cur2.mod_id}&' + $('#bottomPageForm').serialize())">删除</span></a> </c:if>
                  </logic-el:match>
<%--                   <c:if test="${cur2.mod_group eq 1}" var="is_admin_mod_group"> --%>
<%-- 	              <logic-el:match name="popedom" value="+1+"> --%>
	                <a class="butbase" onclick="location.href='SysModule.do?method=add&par_id=${cur2.mod_id}&mod_id=${af.map.mod_id}';" ><span class="icon-add">添加下级</span></a>
<%-- 	              </logic-el:match> --%>
<%-- 	              </c:if> --%>
<%-- 	              <c:if test="${not is_admin_mod_group}"> --%>
<!-- 	              	<a class="butbase but-disabled"><span class="icon-add">添加下级</span></a> -->
<%-- 	              </c:if> --%>
	              <a class="butbase" onclick="location.href='SysModule.do?method=copy&this_mod_id=${cur2.mod_id}&mod_id=${af.map.mod_id}';" ><span class="icon-add">复制</span></a>
                  </td>
              </c:if>
            </tr>
            <c:forEach var="cur3" items="${cur2.sysModuleList}">
              <c:set var="isLock" value="${cur3.is_lock eq 1}"></c:set>
              <c:set var="isDel" value="${cur3.is_del eq 1}"></c:set>
              <tr data-tt-id="${cur3.mod_id}" data-tt-parent-id="${cur3.par_id}">
                <td align="left">${fn:escapeXml(cur3.mod_name)}</td>
                <td align="center">${cur3.mod_id}</td>
                <td align="center">${cur3.par_id}</td>
                <td align="center">${cur3.mod_level}</td>
                <td align="left">${cur3.mod_url}</td>
                <td align="center"><c:out value="${cur3.order_value }"></c:out></td>
                <td align="center"><c:choose>
                    <c:when test="${cur3.is_lock eq 0}"><span style=" color:#060;">未锁定</span></c:when>
                    <c:when test="${cur3.is_lock eq 1}"><span style=" color:#ccc;">已锁定</span></c:when>
                  </c:choose></td>
                <td align="center"><c:choose>
                    <c:when test="${cur3.is_del eq 0}"><span style=" color:#060;">未删除</span></c:when>
                    <c:when test="${cur3.is_del eq 1}"><span style=" color:#F00;">已删除</span></c:when>
                  </c:choose></td>
                <c:if test="${isContains}" >
                  <td align="center" nowrap="nowrap">
                  <logic-el:match name="popedom" value="+2+">
                  	<a class="butbase" href="javascript:void(0);">
                  		<span class="icon-edit" onclick="confirmUpdate(null, 'SysModule.do', 'id=${cur3.mod_id}&' + $('#bottomPageForm').serialize())">修改</span></a>
                  </logic-el:match>
                    <logic-el:match name="popedom" value="+4+">
                      <c:if test="${isLock or isDel}"><a class="butbase but-disabled" href="javascript:void(0);"><span class="icon-remove">删除</span></a></c:if>
                      <c:if test="${not isLock and not isDel}"> <a class="butbase" href="javascript:void(0);"><span class="icon-remove" onclick="confirmDelete(null, 'SysModule.do', 'id=${cur3.mod_id}&' + $('#bottomPageForm').serialize())">删除</span></a> </c:if>
                    </logic-el:match>
	                <logic-el:match name="popedom" value="+1+">
	                	<a class="butbase but-disabled"><span class="icon-add">添加下级</span></a>
	                </logic-el:match>
	                <a class="butbase" onclick="location.href='SysModule.do?method=copy&this_mod_id=${cur3.mod_id}&mod_id=${af.map.mod_id}';" ><span class="icon-add">复制</span></a>
                    </td>
                </c:if>
              </tr>
            </c:forEach>
          </c:forEach>
        </c:forEach>
      </tbody>
    </table>
  </form>
</div>
  <div class="pageClass">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="SysModule.do">
      <script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
      <input type="hidden" name="mod_id" value="${af.map.mod_id}" />
    </form>
  </div>
<script src="${ctx}/commons/treetable/javascripts/src/jquery.treetable.js"></script>
<script type="text/javascript">//<![CDATA[
                                          
$(document).ready(function(){ 
	$("#table-advanced").treetable({ expandable: true }); 
	$('#table-advanced').treetable('expandAll'); //return false;
});  

//]]></script>
</body>
<jsp:include page="../public_page.jsp" flush="true" />
</html>
