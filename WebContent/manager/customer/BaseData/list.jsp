<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员中心 - ${app_name}</title>
<meta content="${app_name}会员中心关键字" name="keywords" />
<meta content="${app_name}会员中心介绍" name="description" />
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="mainbox mine">
   <c:set var="base_name" value="内容" />
  <c:if test="${af.map.type eq 18000}"><c:set var="base_name" value="供应商" /></c:if>
  <c:if test="${af.map.type eq 1123}"><c:set var="base_name" value="产品类型" /></c:if>
  <jsp:include page="../_nav.jsp" flush="true"/>
  <html-el:form action="/customer/BaseData" styleClass="searchForm">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="type" />
    <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tableClassSearch">
      <tr>
        <td>${base_name}：
              <html-el:text property="type_name_like" styleId="type_name_like" style="width:80px;" styleClass="webinput"/>
               &nbsp;&nbsp;是否锁定：
                <html-el:select property="is_lock">
                  <html-el:option value="">全部</html-el:option>
                  <html-el:option value="0">未锁定</html-el:option>
                  <html-el:option value="1">已锁定</html-el:option>
                </html-el:select>
                &nbsp;&nbsp;是否删除：
                <html-el:select property="is_del">
                  <html-el:option value="">全部</html-el:option>
                  <html-el:option value="0">未删除</html-el:option>
                  <html-el:option value="1">已删除</html-el:option>
                </html-el:select>
                &nbsp;&nbsp;
            &nbsp;&nbsp;
            <button class="bgButtonFontAwesome" type="submit"><i class="fa fa-search"></i>查 询</button>
          </div></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="BaseData.do?method=delete">
    <div style="text-align: left; padding: 5px;">
        <input type="button" name="delete" id="delete" class="bgButton" value="删除所选" onclick="this.form.action += '&' + $('#bottomPageForm').serialize();confirmDeleteAll(this.form);" />
        <input type="button" name="add" id="add" class="bgButton" value="添 加" onclick="location.href='BaseData.do?method=add&mod_id=${af.map.mod_id}&type=${af.map.type}';" />
    </div>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
      <tr>
        <th width="5%" nowrap="nowrap"> <c:if test="${fn:contains(popedom, '+4+')}" var="isDelete">
            <input name="chkAll" type="checkbox" id="chkAll" value="-1" onclick="checkAll(this);" />
          </c:if>
          <c:if test="${not isDelete}">序号 </c:if>
        </th>
        <th width="6%">编码</th>
        <th nowrap="nowrap" width="12%">${base_name}</th>
        <th>详细说明</th>
        <th width="8%">添加时间</th>
        <th width="6%">是否锁定</th>
        <th width="6%">是否删除</th>
        <th width="12%" nowrap="nowrap">操作</th>
      </tr>
      <c:forEach var="cur" items="${baseDataList}" varStatus="vs">
        <tr>
          <td align="center" nowrap="nowrap">
              <c:set var="isLock" value="${cur.is_lock eq 1}"></c:set>
              <c:set var="isDel" value="${cur.is_del eq 1}"></c:set>
              <c:if test="${isLock or isDel}">
                <input name="pks" type="checkbox" id="pks" value="${cur.id}" disabled="disabled"/>
              </c:if>
              <c:if test="${not isLock and not isDel}">
                <input name="pks" type="checkbox" id="pks" value="${cur.id}" />
              </c:if>
          </td>
          <td align="center">${cur.id}</td>
          <td nowrap="nowrap" align="center">${fn:escapeXml(cur.type_name)}</td>
          <td align="center"><c:out value="${cur.remark}"></c:out></td>
          <td align="center"><fmt:formatDate value="${cur.add_date}" pattern="yyyy-MM-dd"/></td>
          <td align="center"><c:choose>
              <c:when test="${cur.is_lock eq 0}"><span style=" color:#060;">未锁定</span></c:when>
              <c:when test="${cur.is_lock eq 1}"><span style=" color:#ccc;">已锁定</span></c:when>
            </c:choose></td>
          <td align="center"><c:choose>
              <c:when test="${cur.is_del eq 0}"><span style=" color:#060;">未删除</span></c:when>
              <c:when test="${cur.is_del eq 1}"><span style=" color:#F00;">已删除</span></c:when>
            </c:choose></td>
            <td align="center" nowrap="nowrap">
              <a class="butbase" onclick="confirmUpdate(null, 'BaseData.do', 'id=${cur.id}&' + $('#bottomPageForm').serialize())"><span class="icon-edit">修改</span></a>
                <c:if test="${isLock or isDel}">&nbsp;
                <a class="butbase but-disabled"  onclick="javascript:void(0);"  title="已锁定或删除，不能删除"><span class="icon-remove">删除</span></a>
                </c:if>
                <c:if test="${not isLock and not isDel}">
                 <a class="butbase" onclick="confirmDelete(null, 'BaseData.do', 'id=${cur.id}&' + $('#bottomPageForm').serialize())"><span class="icon-remove">删除</span></a>
                </c:if>
             </td>
        </tr>
      </c:forEach>
    </table>
  </form>
  <div class="black">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="BaseData.do">
      <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
					var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
					pager.addHiddenInputs("method", "list");
		            pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
		            pager.addHiddenInputs("type_name_like", "${fn:escapeXml(af.map.type_name_like)}");
					pager.addHiddenInputs("is_del", "${af.map.is_del}");
					pager.addHiddenInputs("is_lock", "${af.map.is_lock}");
					pager.addHiddenInputs("type", "${af.map.type}");
					document.write(pager.toString());
	            	</script></td>
        </tr>
      </table>
    </form>
  </div>
  <div class="clear"></div>
</div>
<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
});


//]]></script>
</body>
</html>
