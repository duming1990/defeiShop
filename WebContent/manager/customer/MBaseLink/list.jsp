<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/admin/css/admin.css"  />
</head>
<body>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script> 
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/customer/MBaseLink">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="link_type" />
    <html-el:hidden property="type" />
    <html-el:hidden property="pre_number" />
    <html-el:hidden property="par_id" />
    <html-el:hidden property="par_son_type" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClassSearch">
      <tr>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">
            <tr>
              <td width="5%" nowrap="nowrap">&nbsp;标题：
                <html-el:text property="title_like" styleClass="webinput" />
                &nbsp; 是否删除：
                <html-el:select property="is_del" styleId="is_del">
                  <html-el:option value="">请选择...</html-el:option>
                  <html-el:option value="0">否</html-el:option>
                  <html-el:option value="1">是</html-el:option>
                </html-el:select>
                &nbsp;
                <html-el:button value="查 询" styleClass="bgButton" property="" styleId="btn_submit" /></td>
            </tr>
          </table></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="MBaseLink.do?method=delete">
    <div style="padding-bottom:5px;">
      <input type="button" name="delete" id="delete" class="bgButton" value="删除所选" onclick="this.form.action += '&' + $('#bottomPageForm').serialize();confirmDeleteAll(this.form);" />
      <input type="button" name="add" id="add" class="bgButton" value="添 加" onclick="location.href='MBaseLink.do?method=add&mod_id=${af.map.mod_id}&type=${af.map.type}&link_type=${af.map.link_type}&pre_number=${af.map.pre_number}&par_id=${af.map.par_id}&par_son_type=${af.map.par_son_type}';" />
      <input type="hidden" name="method" id="method" value="delete" />
      <input type="hidden" name="mod_id" id="mod_id" value="${af.map.mod_id}" />
    </div>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th width="5%" nowrap="nowrap">
           <input name="chkAll" type="checkbox" id="chkAll" value="-1" onclick="checkAll(this);" />
        </th>
        <th>标题</th>
<!--         <th width="8%">所属区域</th> -->
        <th width="15%">图片</th>
        <th width="12%">链接地址</th>
        <th width="8%">添加时间</th>
        <th width="8%">是否删除</th>
        <th width="5%">排序值</th>
        <th width="12%" nowrap="nowrap">操作</th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr align="center">
          <td>
	          <c:if test="${cur.is_del eq 1}">
	          	<input name="pks" type="checkbox" id="pks_${cur.id}" value="${cur.id}" disabled="disabled" />
	          </c:if>
	          <c:if test="${cur.is_del ne 1}">
	          	<input name="pks" type="checkbox" id="pks_${cur.id}" value="${cur.id}" />
	          </c:if>
          </td>
          <td align="left"><a href="MBaseLink.do?method=view&amp;id=${cur.id}&mod_id=${af.map.mod_id}">
            <c:out value="${fnx:abbreviate(cur.title, 50, '...')}" />
            </a></td>
<!--           <td align="center"> -->
<%-- 	         <c:if test="${not empty cur.p_name}">${fn:escapeXml(cur.p_name)}</c:if> --%>
<%-- 	         <c:if test="${empty cur.p_name}">--</c:if> --%>
<!--           </td> -->
          <td align="center">
          <c:if test="${not empty cur.image_path}">
          	<img src="${ctx}/${cur.image_path}" height="100"/>
          </c:if>
          <c:if test="${empty cur.image_path}">暂无图片</c:if>
          </td>
          <td align="center">${cur.link_url}</td>
          <td align="center"><fmt:formatDate value="${cur.add_time}" pattern="yyyy-MM-dd" /></td>
          <td><c:if test="${cur.is_del eq 1}">
              <c:out value="是"/>
            </c:if>
            <c:if test="${cur.is_del eq 0}">
              <c:out value="否"/>
            </c:if></td>
          <td align="center">${fn:escapeXml(cur.order_value)}</td>
          <td nowrap="nowrap">
            <a class="butbase" id="edit" onclick="confirmUpdate(null, 'MBaseLink.do', 'id=${cur.id}&type=${af.map.type}&' + $('#bottomPageForm').serialize())"><span class="icon-edit">修改</span></a>
          	<c:if test="${cur.is_del eq 0}">
          	    <a class="butbase" id="remove" onclick="confirmDelete(null, 'MBaseLink.do', 'id=${cur.id}&' + $('#bottomPageForm').serialize())"><span class="icon-remove">删除</span></a>
            </c:if>
          	<c:if test="${cur.is_del eq 1}"><a class="butbase but-disabled" id="remove" title="已删除，不能删除"><span class="icon-remove">删除</span></a></c:if>
          </td>
        </tr>
        <c:if test="${vs.last eq true}">
          <c:set var="i" value="${vs.count}" />
        </c:if>
      </c:forEach>
      <c:forEach begin="${i}" end="${af.map.pager.pageSize - 1}">
        <tr align="center">
          <c:forEach begin="1" end="8">
            <td>&nbsp;</td>
          </c:forEach>
        </tr>
      </c:forEach>
    </table>
  </form>
  <div class="pageClass">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="MBaseLink.do">
      <table width="98%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td height="10"><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script> 
            <script type="text/javascript">
				var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
				pager.addHiddenInputs("method", "list");
				pager.addHiddenInputs("title_like", "${fn:escapeXml(af.map.title_like)}");
				pager.addHiddenInputs("is_del", "${af.map.is_del}");
				pager.addHiddenInputs("link_type", "${af.map.link_type}");
				pager.addHiddenInputs("type", "${af.map.type}");
				pager.addHiddenInputs("pre_number", "${af.map.pre_number}");
				pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
				pager.addHiddenInputs("p_index", "${af.map.p_index}");
				pager.addHiddenInputs("par_id", "${af.map.par_id}");
				pager.addHiddenInputs("par_son_type", "${af.map.par_son_type}");
				document.write(pager.toString());
            	</script></td>
        </tr>
      </table>
    </form>
  </div>
</div>
 
<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	var f = document.forms[0];
	$("#btn_submit").click(function(){
		this.form.submit();
	});
});

function openChildForPindex(){
	var url = "${ctx}/BaseCsAjax.do?method=choosePIndex&dir=admin&num=0";
	$.dialog({
		title:  "选择开放区域",
		width:  770,
		height: 550,
        lock:true ,
        zIndex:9999,
		content:"url:"+url
	});
}
//]]></script>
</body>
</html>
