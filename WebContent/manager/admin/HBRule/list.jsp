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
  <html-el:form action="/admin/HBRule">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClassSearch">
      <tr>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">
            <tr>
              <td width="19%" nowrap="nowrap">&nbsp;标题：
                <html-el:text property="title_like" style="width:80px;" styleClass="webinput"/>
                &nbsp;是否删除：
                <html-el:select property="is_del">
                  <html-el:option value="">全部</html-el:option>
                  <html-el:option value="0">未删除</html-el:option>
                  <html-el:option value="1">已删除</html-el:option>
                </html-el:select>
                &nbsp;
                &nbsp;是否关闭：
                <html-el:select property="is_closed">
                  <html-el:option value="">全部</html-el:option>
                  <html-el:option value="0">未关闭</html-el:option>
                  <html-el:option value="1">已关闭</html-el:option>
                </html-el:select>
                &nbsp;
                &nbsp;红包种类：
                <html-el:select property="hb_class">
                  <html-el:option value="">全部</html-el:option>
                  <c:forEach items="${hbClass}" var="keys">
                    <html-el:option value="${keys.index}">${keys.name}</html-el:option>
                  </c:forEach>
                </html-el:select>
                &nbsp;
                &nbsp;红包类型：
                <html-el:select property="hb_type">
                  <html-el:option value="">全部</html-el:option>
                  <c:forEach items="${hbTypes}" var="keys">
                    <html-el:option value="${keys.index}">${keys.name}</html-el:option>
                  </c:forEach>
                </html-el:select>
                &nbsp;
                <html-el:submit value="查 询" styleClass="bgButton" /></td>
            </tr>
          </table></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="HBRule.do?method=delete">
    <div style="text-align: left; padding: 5px;">
      <input type="button" name="delete" id="delete" class="bgButton" value="删除所选" onclick="this.form.action += '&' + $('#bottomPageForm').serialize();confirmDeleteAll(this.form);" />
      <input type="button" name="add" id="add" class="bgButton" value="添 加" onclick="location.href='HBRule.do?method=add&mod_id=${af.map.mod_id}';" />
    </div>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th width="7%" align="center" nowrap="nowrap"><input name="chkAll" type="checkbox" id="chkAll" value="-1" onclick="checkAll(this);" /></th>
        <th width="8%">标题</th>
        <th align="center" nowrap="nowrap">红包大类</th>
        <th width="8%" align="center" nowrap="nowrap">红包类型</th>
        <th width="8%" align="center" nowrap="nowrap">分享人获得额度</th>
        <th width="8%" align="center" nowrap="nowrap">定额红包额度</th>
        <th width="8%" align="center" nowrap="nowrap">随机红包额度范围</th>
        <th width="8%" align="center" nowrap="nowrap">红包总量</th>
        <th width="8%" align="center" nowrap="nowrap">有效期周期</th>
        <th width="8%" align="center" nowrap="nowrap">是否关闭</th>
        <th width="8%" align="center" nowrap="nowrap">是否删除</th>
        <th width="10%" align="center" nowrap="nowrap">操作</th>
      </tr>
      <c:forEach var="cur" items="${rwHbRuleList}">
        <tr>
          <td align="center"><c:set var="isDel" value="${cur.is_del eq 1}"></c:set>
            <c:if test="${isDel}">
              <input name="pks" type="checkbox" id="pks" value="${cur.id}" disabled="disabled"/>
            </c:if>
            <c:if test="${not isDel}">
              <input name="pks" type="checkbox" id="pks" value="${cur.id}" />
            </c:if></td>
          <td align="left"><a title="查看" href="HBRule.do?method=view&amp;mod_id=${af.map.mod_id}&amp;id=${cur.id}">${fn:escapeXml(cur.title)}</a> </td>
          <td align="center"><c:forEach items="${hbClass}" var="keys">
              <c:if test="${cur.hb_class eq  keys.index}">${keys.name}</c:if>
            </c:forEach>
          </td>
          <td align="center"><c:forEach items="${hbTypes}" var="keys">
              <c:if test="${cur.hb_type eq  keys.index}">${keys.name}</c:if>
            </c:forEach>
          </td>
          <td align="center"><c:out value="${cur.share_user_money}"></c:out>
            元</td>
          <td align="center"><c:if test="${cur.hb_type eq 10}">
              <c:out value="${cur.hb_money}"></c:out>
              元 </c:if></td>
          <td align="center"><c:if test="${cur.hb_type eq 20}">
              <c:out value="${cur.min_money}"></c:out>
              元-
              <c:out value="${cur.max_money}"></c:out>
              元 </c:if></td>
          <td align="center"><c:if test="${cur.hb_type eq 20}">
              <c:out value="${cur.hb_max_count}"></c:out>
              个 </c:if></td>
          <td align="center"><c:out value="${cur.effect_count}"></c:out>
            天</td>
          <td align="center"><c:choose>
              <c:when test="${cur.is_closed eq 0}"> <span style="color: #060;">未关闭</span> </c:when>
              <c:when test="${cur.is_closed eq 1}"> <span style="color: #F00;">已关闭</span> </c:when>
            </c:choose></td>
          <td align="center"><c:choose>
              <c:when test="${cur.is_del eq 0}"> <span style="color: #060;">未删除</span> </c:when>
              <c:when test="${cur.is_del eq 1}"> <span style="color: #F00;">已删除</span> </c:when>
            </c:choose></td>
          <td align="center" nowrap="nowrap">
          <a class="butbase" onclick="confirmUpdate(null, 'HBRule.do', 'id=${cur.id}&' + $('#bottomPageForm').serialize())"><span class="icon-edit">修改</span></a>
            <c:if test="${cur.is_del eq 1}"><img src="${ctx}/styles/images/shanchu_2.gif" width="55" height="18" /> </c:if>
            <c:if test="${cur.is_del eq 0}">
            <a class="butbase" onclick="confirmDelete(null, 'HBRule.do', 'id=${cur.id}&' + $('#bottomPageForm').serialize())"><span class="icon-remove">删除</span></a>
              </c:if>
          </td>
        </tr>
      </c:forEach>
    </table>
  </form>
  <div class="pageClass">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="HBRule.do">
      <table width="98%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td height="10"><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
            pager.addHiddenInputs("method", "list");
            pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
            pager.addHiddenInputs("id", "${af.map.id}");
            pager.addHiddenInputs("is_del", "${af.map.is_del}");
            pager.addHiddenInputs("is_closed", "${af.map.is_closed}");
            pager.addHiddenInputs("hb_class", "${af.map.hb_class}");
            pager.addHiddenInputs("hb_type", "${af.map.hb_type}");
            pager.addHiddenInputs("title_like", "${fn:escapeXml(af.map.title_like)}");
            document.write(pager.toString());
            </script></td>
        </tr>
      </table>
    </form>
  </div>
</div>
<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script>
<script type="text/javascript">//<![CDATA[
                                          
//]]></script>
<jsp:include page="../public_page.jsp" flush="true" />
</body>
</html>
