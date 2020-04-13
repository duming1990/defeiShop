<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
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
  <html-el:form action="/admin/RfidBlackList">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClassSearch">
      <tr>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">
            <tr>                              
               <td width="6%" nowrap="nowrap">电子标签ID查询：
               <html-el:text property="rfid_id_like" styleId="rfid_id_like" style="width:120px;" styleClass="webinput"/>
                  &nbsp;&nbsp;关联商铺名称:
                  <html-el:text property="own_entp_name_like" styleId="own_entp_name_like" style="width:120px;" styleClass="webinput"/>
                  &nbsp;&nbsp;是否删除：
                 <html-el:select property="is_del">
                  <html-el:option value="">全部</html-el:option>
                  <html-el:option value="0">未删除</html-el:option>
                  <html-el:option value="1">已删除</html-el:option>
                </html-el:select>
                &nbsp;&nbsp;
                <html-el:submit value="查 询" styleClass="bgButton" />
                </td>
            </tr>
          </table></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="RfidBlackList.do?method=delete">
    <div style="text-align: left; padding: 5px;">
      <logic-el:match name="popedom" value="+4+">
        <input type="button" name="delete" id="delete" class="bgButton" value="删除所选" onclick="this.form.action += '&' + $('#bottomPageForm').serialize();confirmDeleteAll(this.form);" />
      </logic-el:match>
      <logic-el:match name="popedom" value="+1+">
        <input type="button" name="add" id="add" class="bgButton" value="添 加" onclick="location.href='RfidBlackList.do?method=add&mod_id=${af.map.mod_id}&type=${af.map.type}';" />
      </logic-el:match>
    </div>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th width="5%" nowrap="nowrap"> <c:if test="${fn:contains(popedom, '+4+')}" var="isDelete">
            <input name="chkAll" type="checkbox" id="chkAll" value="-1" onclick="checkAll(this);" />
          </c:if>
          <c:if test="${not isDelete}">序号 </c:if>
        </th>
        <th width="6%">电子标签ID</th>
        <th width="8%">添加时间</th>
        <th width="12%">关联商铺名称</th>
        <th width="6%">是否删除</th>
        <th width="12%" nowrap="nowrap">操作</th>
      </tr>
      <c:forEach var="cur" items="${RfidBlackList}" varStatus="vs">
        <tr>
          <td align="center" nowrap="nowrap"><c:if test="${fn:contains(popedom, '+4+')}" var="isDelete">
              <c:set var="isDel" value="${cur.is_del eq 1}"></c:set>
              <c:if test="${ isDel}">
                <input name="pks" type="checkbox" id="pks" value="${cur.id}" disabled="disabled"/>
              </c:if>
              <c:if test="${not isDel}">
                <input name="pks" type="checkbox" id="pks" value="${cur.id}" />
              </c:if>
            </c:if>
            <c:if test="${not isDelete}"> ${vs.count} </c:if></td>
          <td nowrap="nowrap" align="center">${cur.rfid_id}</td>
          <td align="center"><fmt:formatDate value="${cur.add_date}" pattern="yyyy-MM-dd"/></td>
          <td nowrap="nowrap" align="center">${cur.own_entp_name}</td>
          <td align="center"><c:choose>
              <c:when test="${cur.is_del eq 0}"><span style=" color:#060;">未删除</span></c:when>
              <c:when test="${cur.is_del eq 1}"><span style=" color:#F00;">已删除</span></c:when>
            </c:choose></td>
            <td align="center" nowrap="nowrap">
              <logic-el:match name="popedom" value="+2+">
              <c:if test="${isDel }">
                  <a class="butbase but-disabled"  onclick="javascript:void(0);"  title="已删除，不能再修改"><span class="icon-edit">修改</span></a>
              </c:if>
              <c:if test="${not isDel}">
              <a class="butbase" onclick="confirmUpdate(null, 'RfidBlackList.do', 'id=${cur.id}&' + $('#bottomPageForm').serialize())"><span class="icon-edit">修改</span></a>
              </c:if>
              </logic-el:match>
              <logic-el:match name="popedom" value="+4+">
                <c:if test="${isDel}">
                <a class="butbase but-disabled"  onclick="javascript:void(0);"  title="已删除，不能再删除"><span class="icon-remove">删除</span></a>
                </c:if>
                <c:if test="${not isDel}">
                 <a class="butbase" onclick="confirmDelete(null, 'RfidBlackList.do', 'id=${cur.id}&' + $('#bottomPageForm').serialize())"><span class="icon-remove">删除</span></a>
                </c:if>
              </logic-el:match>
             </td>
        </tr>
       <c:if test="${vs.last eq true}">
          <c:set var="i" value="${vs.count}" />
        </c:if>
       </c:forEach>
      <c:forEach begin="${i}" end="${af.map.pager.pageSize - 1}">
        <tr align="center">
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
      </c:forEach>
    </table>
  </form>
  <div class="pageClass">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="RfidBlackList.do">
      <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" >
        <tr>
          <td height="10" ><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
            pager.addHiddenInputs("method", "list");
            pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
			pager.addHiddenInputs("rfid_id_like", "${fn:escapeXml(af.map.rfid_id_like)}");
			pager.addHiddenInputs("is_del", "${af.map.is_del}");
			pager.addHiddenInputs("own_entp_name_like", "${fn:escapeXml(af.map.own_entp_name_like)}");
            document.write(pager.toString());
        </script></td>
        </tr>
      </table>
    </form>
  </div>
</div>
<script type="text/javascript">//<![CDATA[


//]]></script>
</body>
<jsp:include page="../public_page.jsp" flush="true" />
</html>
