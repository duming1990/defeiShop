<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
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
  <html-el:form action="/admin/Link">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClassSearch">
      <tr>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">
            <tr>
              <td width="5%" nowrap="nowrap">&nbsp;标题：
                <html-el:text property="title_like" styleClass="webinput" />
                &nbsp;信息状态：
                <html-el:select property="info_state" styleId="info_state" >
		          <html-el:option value="0">默认（待审核）</html-el:option>
		          <html-el:option value="3">已审核（已发布）</html-el:option>
		          <html-el:option value="-3">已审核（未发布）</html-el:option>
		        </html-el:select>
                &nbsp;
                <html-el:submit value="查 询" styleClass="bgButton"  /></td>
            </tr>
          </table></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="Link.do?method=delete">
    <div style="padding-bottom:5px;">
      <logic-el:match name="popedom" value="+4+">
        <input type="button" name="delete" id="delete" class="bgButton" value="删除所选" onclick="this.form.action += '&' + $('#bottomPageForm').serialize();confirmDeleteAll(this.form);" />
      </logic-el:match>
      <logic-el:match name="popedom" value="+1+">
        <input type="button" name="add" id="add" class="bgButton" value="添 加" onclick="location.href='Link.do?method=add&mod_id=${af.map.mod_id}';" />
      </logic-el:match>
      <input type="hidden" name="method" id="method" value="delete" />
      <input type="hidden" name="mod_id" id="mod_id" value="${af.map.mod_id}" />
    </div>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th width="5%" nowrap="nowrap"> <c:if test="${fn:contains(popedom, '+4+')}" var="isDelete">
            <input name="chkAll" type="checkbox" id="chkAll" value="-1" onclick="checkAll(this);" />
          </c:if>
          <c:if test="${not isDelete}"> 序号 </c:if>
        </th>
        <th nowrap="nowrap">标题</th>
        <th width="20%" nowrap="nowrap">链接地址</th>
        <th width="10%" nowrap="nowrap">信息状态</th>
        <th width="5%" nowrap="nowrap">排序值</th>
        <c:if test="${fn:contains(popedom, '+2+') or fn:contains(popedom, '+4+') or fn:contains(popedom, '+8+')}" var="isContains">
          <th width="12%" nowrap="nowrap">操作</th>
        </c:if>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr align="center">
          <td nowrap="nowrap"><c:if test="${fn:contains(popedom, '+4+')}" var="isDelete">
           <c:set var="isDel" value="true"></c:set>
              <c:if test="${cur.info_state le 0}">
                <input name="pks" type="checkbox" id="pks_${cur.id}" value="${cur.id}" />
              </c:if>
              <c:if test="${cur.info_state gt 0}">
                <input name="pks" type="checkbox" id="pks_${cur.id}" value="${cur.id}" disabled="disabled" />
                <c:set var="isDel" value="false"></c:set>
              </c:if>
            </c:if>
            <c:if test="${not isDelete}"> ${vs.count } </c:if></td>
          <td align="left" ><a style="color:${cur.title_color}; text-decoration:none; <c:if test='${cur.title_is_strong eq 1}'>font-weight:bold;</c:if>" href="Link.do?method=view&amp;id=${cur.id}&amp;mod_id=${cur.mod_id}">
            <c:out value="${fnx:abbreviate(cur.title, 50, '...')}" />
            </a></td>
          <td align="left" nowrap="nowrap">${fn:escapeXml(cur.direct_uri)}</td>
          <td nowrap="nowrap"><c:choose>
              <c:when test="${cur.info_state eq 0}"> 默认（待审核） </c:when>
              <c:when test="${cur.info_state eq 3}"> <span style="color:#060;">已审核（已发布）</span> </c:when>
              <c:when test="${cur.info_state eq -3}"> <span style="color:#f00;">已审核（未发布）</span> </c:when>
            </c:choose></td>
          <td align="center" nowrap="nowrap">${fn:escapeXml(cur.order_value)}</td>
          <c:if test="${isContains}" >
            <td nowrap="nowrap"><logic-el:match name="popedom" value="+8+"><span style="cursor: pointer;" onclick="doNeedMethod(null, 'Link.do', 'audit','id=${cur.id}&mod_id=${cur.mod_id}&'+$('#bottomPageForm').serialize())"><img src="${ctx}/styles/images/shenhe.gif" width="55" height="18" /></span> </logic-el:match>
              <logic-el:match name="popedom" value="+2+">
                <c:if test="${cur.info_state ne 3}"> <span style="cursor: pointer;" onclick="confirmUpdate(null, 'Link.do', 'id=${cur.id}&' + $('#bottomPageForm').serialize())"><img src="${ctx}/styles/images/xiugai.gif" width="55" height="18" /></span> </c:if>
                <c:if test="${cur.info_state eq 3}"> <span style="cursor: pointer;" title="已审核（已发布），不能再修改"><img src="${ctx}/styles/images/xiugai_2.gif" width="55" height="18" /></span> </c:if>
              </logic-el:match>
              <logic-el:match name="popedom" value="+4+">
                <c:if test="${isDel}"><span style="cursor:pointer;" onclick="confirmDelete(null, 'Link.do', 'id=${cur.id}&' + $('#bottomPageForm').serialize())"><img src="${ctx}/styles/images/shanchu.gif" width="55" height="18" /></span></c:if>
                <c:if test="${not isDel}"><span style="color:#999;" title="已审核，不能删除"><img src="${ctx}/styles/images/shanchu_2.gif" width="55" height="18" /></span></c:if>
              </logic-el:match></td>
          </c:if>
        </tr>
        <c:if test="${vs.last eq true}">
          <c:set var="i" value="${vs.count}" />
        </c:if>
      </c:forEach>
      <c:forEach begin="${i}" end="${af.map.pager.pageSize - 1}">
        <tr align="center">
          <c:forEach begin="1" end="${isContains ? 6 :5}">
            <td>&nbsp;</td>
          </c:forEach>
        </tr>
      </c:forEach>
    </table>
  </form>
  <div class="pageClass">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="Link.do">
      <table width="98%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td height="10"><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script> 
            <script type="text/javascript">
				var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
				pager.addHiddenInputs("method", "list");
				pager.addHiddenInputs("title_like", "${af.map.title_like}");
				pager.addHiddenInputs("info_state", "${af.map.info_state}");
				pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
				document.write(pager.toString());
            	</script></td>
        </tr>
      </table>
    </form>
  </div>
</div>
 
<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script> 
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
});
//]]></script>
<jsp:include page="../public_page.jsp" flush="true"/>
</body>
</html>
