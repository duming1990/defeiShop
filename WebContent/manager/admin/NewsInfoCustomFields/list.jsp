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
  <html-el:form action="/admin/NewsInfoCustomFields">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClassSearch">
      <tr>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">
            <tr>
              <td width="5%" nowrap="nowrap">&nbsp;字段名称：
                <html-el:text property="title_name_like" styleId="title_name_like" maxlength="125"  styleClass="webinput" />
                &nbsp;字段类型：
                <html-el:select property="type" styleId="type">
                  <html-el:option value="">请选择...</html-el:option>
                  <html-el:option value="1">简单文本框</html-el:option>
                  <html-el:option value="2">可编辑文本框</html-el:option>
                  <html-el:option value="3">单选</html-el:option>
                  <html-el:option value="4">多选</html-el:option>
                  <html-el:option value="5">下拉框选择</html-el:option>
                </html-el:select>
                <!--
                        &nbsp;是否删除：
                        <html-el:select property="is_del" styleId="is_del">
                          <html-el:option value="">请选择...</html-el:option>
                          <html-el:option value="1">是</html-el:option>
                          <html-el:option value="0">否</html-el:option>
                        </html-el:select>
                     -->
                &nbsp;
                <html-el:submit value="查 询" styleClass="bgButton"  /></td>
            </tr>
          </table></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="NewsInfoCustomFields.do?method=delete">
    <div style="text-align: left; padding: 5px;">
      <input type="button" name="delete" id="delete" class="bgButton" value="删除所选" onclick="checkDelAll(this.form)" />
      <input type="button" name="add" id="add" class="bgButton" value="添 加" onclick="location.href='NewsInfoCustomFields.do?method=add&mod_id=${af.map.mod_id}';" />
      <input type="hidden" name="method" id="method" value="delete" />
      <input type="hidden" name="mod_id" id="mod_id" value="${af.map.mod_id}" />
    </div>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th width="5%" nowrap="nowrap"><input name="chkAll" type="checkbox" id="chkAll" value="-1" onclick="checkAll(this);" /></th>
        <th  nowrap="nowrap">字段名称</th>
        <th width="15%" nowrap="nowrap">字段类型</th>
        <th width="12%" nowrap="nowrap">添加时间</th>
        <th width="5%" nowrap="nowrap">排序值</th>
        <th width="10%" nowrap="nowrap">操作</th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr align="center">
          <td nowrap="nowrap"><c:if test="${cur.is_del eq 0}">
              <input name="pks" type="checkbox" id="pks_${cur.id}" value="${cur.id}" />
            </c:if>
            <c:if test="${cur.is_del eq 1}">
              <input name="pks" type="checkbox" id="pks_${cur.id}" value="${cur.id}" disabled="disabled" />
            </c:if></td>
          <td align="left" nowrap="nowrap"><a href="NewsInfoCustomFields.do?method=view&id=${cur.id}&mod_id=${af.map.mod_id}" >${fn:escapeXml(cur.title_name)}</a></td>
          <td align="center" nowrap="nowrap"><c:if test="${cur.type eq 1}">简单文本框</c:if>
            <c:if test="${cur.type eq 2}">可编辑文本框</c:if>
            <c:if test="${cur.type eq 3}">单选</c:if>
            <c:if test="${cur.type eq 4}">多选</c:if>
            <c:if test="${cur.type eq 5}">下拉框选择</c:if></td>
          <td align="center" nowrap="nowrap"><fmt:formatDate value="${cur.add_time}" pattern="yyyy-MM-dd" /></td>
          <td align="center" nowrap="nowrap">${fn:escapeXml(cur.order_value)}</td>
          <td nowrap="nowrap"><span style="cursor: pointer;" onclick="confirmUpdate(null, 'NewsInfoCustomFields.do', 'id=${cur.id}&' + $('#bottomPageForm').serialize())"><img src="${ctx}/styles/images/xiugai.gif" width="55" height="18" /></span>
            <c:if test="${cur.is_del eq 0}"><span style="cursor:pointer;" onclick="checkDel('${cur.id}')"><img src="${ctx}/styles/images/shanchu.gif" width="55" height="18" /></span></c:if>
            <c:if test="${cur.is_del eq 1}"><span style="color:#999;""><img src="${ctx}/styles/images/shanchu_2.gif" width="55" height="18" /></span></c:if></td>
        </tr>
      </c:forEach>
    </table>
  </form>
  <div class="pageClass">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="NewsInfoCustomFields.do">
      <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td height="10"><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
				var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
				pager.addHiddenInputs("method", "list");
				pager.addHiddenInputs("title_name_like", "${fn:escapeXml(af.map.title_name_like)}");
				pager.addHiddenInputs("is_del", "${af.map.is_del}");
				pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
				document.write(pager.toString());
            	</script></td>
        </tr>
      </table>
    </form>
  </div>
</div>

<script type="text/javascript" src="${ctx}/commons/scripts/cs.js"></script>
<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script>
<script type="text/javascript">//<![CDATA[
function checkDel(id){
	$.ajax({
		type: "POST",
		url: "NewsInfoCustomFields.do",
		data: "method=checkDelete&id="+ id,
		dataType: "json",
		error: function(request, settings) {alert("数据加载请求失败！");},
		success: function(data) {
			if(data == 1){
				alert("该自定义字段正在使用中，请勿删除！");
				return false;
			}
			confirmDelete(null, 'NewsInfoCustomFields.do', 'id=' + id + '&' + $('#bottomPageForm').serialize());
		}
	});	
}

function checkDelAll(form){
	var pks = [];
	$("input[type='checkbox'][name='pks']:checked").each(function(){
			pks.push($(this).val());
	});
	if(pks.length < 1){
		alert("至少选择一项删除！");
		return false;
	}
	$.ajax({
		type: "POST",
		url: "NewsInfoCustomFields.do",
		data: "method=checkDeleteAll&ids=" + pks.join(","),
		dataType: "json",
		error: function(request, settings) {alert("数据加载请求失败！");},
		success: function(data) {
			if(data.length > 1){
				alert("自定义字段["+data[0].name+"]正在使用中，请勿删除！");
				return false;
			}
			form.action += '&' + $('#bottomPageForm').serialize();
			confirmDeleteAll(form);
		}
	});	
		
}
//]]></script>
<jsp:include page="../public_page.jsp" flush="true"/>
</body>
</html>
