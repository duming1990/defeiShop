<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的收藏- ${app_name}</title>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="mainbox mine">
  <jsp:include page="../_nav.jsp" flush="true"/>
  <html-el:form action="/customer/MyFav">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="mod_id"/>
    <html-el:hidden property="par_id"/>
    <table width="100%" border="0" cellpadding="1" cellspacing="1" class="tableClassSearch">
      <tr>
        <td nowrap="nowrap" align="left">店铺或商品名称：
          <html-el:text property="title_name_like" style="width:120px" styleClass="webinput" maxlength="50"/>
          &nbsp;收藏类型：
          <html-el:select property="sc_type" styleClass="webinput" styleId="sc_type">
            <html-el:option value="">请选择...</html-el:option>
            <html-el:option value="1">店铺</html-el:option>
            <html-el:option value="2">商品</html-el:option>
          </html-el:select>
          &nbsp;
          <button class="bgButtonFontAwesome" type="submit"><i class="fa fa-search"></i>查 询</button></td>
      </tr>
    </table>
  </html-el:form>
    <div class="clear"></div>
  <div class="admin_r">
    <div class="admin_two">
      <%@ include file="/commons/pages/messages.jsp" %>
      <form id="listForm" name="listForm" method="post" action="MyFav.do?method=delete">
        <div class="all">
        <button class="bgButtonFontAwesome" type="button" onclick="this.form.action += '&amp;' + $('#bottomPageForm').serialize();confirmDeleteAll(this.form);"><i class="fa fa-minus-square"></i>删除所选</button></td>
        </div>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable" align="left">
          <tr>
            <th width="5%" align="center"><input name="chkAll" type="checkbox" id="chkAll" value="-1" onclick="checkAll(this);" /></th>
            <th align="center">店铺或商品名称</th>
            <th width="10%" align="center">收藏类型</th>
            <th width="12%" align="center">添加时间</th>
            <th width="10%" align="center">操作</th>
          </tr>
          <c:forEach var="cur" items="${entityList}" varStatus="vs">
            <tr>
              <td align="center"><input name="pks" type="checkbox" id="pks" value="${cur.id}" /></td>
              <td><c:if test="${cur.sc_type eq 1}">
                  <c:url var="url" value="/entp/IndexEntpInfo.do?entp_id=${cur.link_id}" />
                  <c:if test="${not empty cur.map.custom_url}">
                    <c:url var="entp_url" value="/entp/IndexEntpInfo.do?method=subdomain&custom_url=${cur.map.custom_url}" />
                    <c:if test="${not empty server_min_domain}">
                      <c:url var="entp_url" value="http://${cur.map.custom_url}.${server_min_domain}" />
                    </c:if>
                  </c:if>
                </c:if>
                <a title="查看" href="MyFav.do?method=view&amp;id=${cur.id}">${fn:escapeXml(cur.title_name)}</a></td>
              <td align="center"><c:if test="${cur.sc_type eq 1}">店铺</c:if>
                <c:if test="${cur.sc_type eq 2}">商品</c:if>
              </td>
              <td align="center"><fmt:formatDate value="${cur.add_date}" pattern="yyyy-MM-dd" /></td>
              <td align="center"><span style="cursor: pointer;" onclick="location.href='MyFav.do?method=view&amp;id=${cur.id}'" title="查看">查看</span> <span style="cursor: pointer;margin-left:7px;" onclick="confirmDelete(null, 'MyFav.do', 'id=${cur.id}&' + $('#bottomPageForm').serialize())">删除</span> </td>
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
            </tr>
          </c:forEach>
        </table>
      </form>
    </div>
    <div class="clear"></div>
    <div class="black">
      <form id="bottomPageForm" name="bottomPageForm" method="post" action="MyFav.do">
        <table width="98%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td height="10"><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
              <script type="text/javascript">
				var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
		        pager.addHiddenInputs("method", "list");
		        pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
		        pager.addHiddenInputs("par_id", "${af.map.par_id}");
				pager.addHiddenInputs("title_name_like", "${af.map.title_name_like}");
				pager.addHiddenInputs("sc_type", "${af.map.sc_type}");
		        document.write(pager.toString());
            	</script></td>
          </tr>
        </table>
      </form>
    </div>
    <div class="clear"></div>
  </div>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/cs.js"></script>
<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	
});

//]]></script>
</body>
</html>
