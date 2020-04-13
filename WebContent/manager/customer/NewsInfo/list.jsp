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
  <html-el:form action="/customer/NewsInfo">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClassSearch">
      <tr>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">
            <tr>
              <td width="5%" nowrap="nowrap">&nbsp;标题：
                <html-el:text property="title_like" styleClass="webinput" />
                &nbsp;发布时间 从：
                <html-el:text property="st_pub_date" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker();" />
                至：
                <html-el:text property="en_pub_date" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker();" />
                &nbsp; 发布状态：
                <select name="pub_state" id="pub_state">
                </select>
                &nbsp;信息状态：
                <select name="info_state" id="info_state">
                </select>
                &nbsp;
                <html-el:button value="查 询" styleClass="bgButton" property="" styleId="btn_submit" /></td>
            </tr>
          </table></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="NewsInfo.do?method=delete">
    <div style="padding-bottom:5px;">
      <logic-el:match name="popedom" value="+4+">
        <input type="button" name="delete" id="delete" class="bgButton" value="删除所选" onclick="this.form.action += '&' + $('#bottomPageForm').serialize();confirmDeleteAll(this.form);" />
      </logic-el:match>
      <logic-el:match name="popedom" value="+1+">
        <input type="button" name="add" id="add" class="bgButton" value="添 加" onclick="location.href='NewsInfo.do?method=add&mod_id=${af.map.mod_id}';" />
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
        <th >标题</th>
        <th width="10%" >作者</th>
        <th width="18%" >信息来源</th>
        <th width="10%" >发布时间</th>
        <th width="10%" >信息状态</th>
        <th width="5%" >排序值</th>
        <c:if test="${fn:contains(popedom, '+2+') or fn:contains(popedom, '+4+') or fn:contains(popedom, '+8+')}" var="isContains">
          <th width="12%" nowrap="nowrap">操作</th>
        </c:if>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr align="center">
          <td><c:if test="${fn:contains(popedom, '+4+')}" var="isDelete">
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
          <td align="left" >
          <a style="color:${cur.title_color}; text-decoration:none; <c:if test='${cur.title_is_strong eq 1}'>font-weight:bold;</c:if>" href="NewsInfo.do?method=view&amp;id=${cur.id}&amp;mod_id=${cur.mod_id}">
            <c:out value="${fnx:abbreviate(cur.title, 50, '...')}" />
            </a></td>
          <td align="left">${fn:escapeXml(cur.author)}</td>
          <td align="left">${fn:escapeXml(cur.info_source)}</td>
          <td align="center"><fmt:formatDate value="${cur.pub_time}" pattern="yyyy-MM-dd" /></td>
          <td><c:choose>
              <c:when test="${cur.info_state eq 0}"> 默认（待审核） </c:when>
              <c:when test="${cur.info_state eq 3}"> <span style="color:#060;">已审核（已发布）</span> </c:when>
              <c:when test="${cur.info_state eq 1}"> <span style="color:#060;">已审核（已发布）</span> </c:when>
              <c:when test="${cur.info_state eq -1}"> <span style="color:#f00;">已审核（未发布）</span> </c:when>
              <c:when test="${cur.info_state eq -3}"> <span style="color:#f00;">已审核（未发布）</span> </c:when>
            </c:choose></td>
          <td align="center">${fn:escapeXml(cur.order_value)}</td>
          <c:if test="${isContains}" >
            <td nowrap="nowrap">
            <logic-el:match name="popedom" value="+8+">
            <a class="butbase" onclick="doNeedMethod(null, 'NewsInfo.do', 'audit','id=${cur.id}&mod_id=${cur.mod_id}&'+$('#bottomPageForm').serialize())">
            <span class="icon-ok">审核</span></a>
            </logic-el:match>
              <logic-el:match name="popedom" value="+2+">
                <c:if test="${cur.info_state ne 3}"> 
                <a class="butbase"  onclick="confirmUpdate(null, 'NewsInfo.do', 'id=${cur.id}&' + $('#bottomPageForm').serialize())">
                <span  class="icon-edit">修改</span></a>
                 </c:if>
                <c:if test="${cur.info_state eq 3}"> 
                <a class="butbase but-disabled"  onclick="javascript:void(0);"  title="已审核（已发布），不能再修改"><span class="icon-edit">修改</span></a> 
                </c:if>
              </logic-el:match>
              <logic-el:match name="popedom" value="+4+">
                <c:if test="${isDel}">
                <a class="butbase"   onclick="confirmDelete(null, 'NewsInfo.do', 'id=${cur.id}&' + $('#bottomPageForm').serialize())">
                <span  class="icon-remove">删除</span></a>
                </c:if>
                <c:if test="${not isDel}">
                <a class="butbase but-disabled"  onclick="javascript:void(0);"  title="已审核，不能删除"><span class="icon-remove">删除</span></a>
                </c:if>
              </logic-el:match></td>
          </c:if>
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
          <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
      </c:forEach>
    </table>
  </form>
  <div class="pageClass">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="NewsInfo.do">
      <table width="98%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td height="10"><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script> 
            <script type="text/javascript">
				var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
				pager.addHiddenInputs("method", "list");
				pager.addHiddenInputs("title_like", "${fn:escapeXml(af.map.title_like)}");
				pager.addHiddenInputs("st_pub_date", "${af.map.st_pub_date}");
				pager.addHiddenInputs("en_pub_date", "${af.map.en_pub_date}");
				pager.addHiddenInputs("info_state", "${af.map.info_state}");
				pager.addHiddenInputs("pub_state", "${af.map.pub_state}");
				pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
				pager.addHiddenInputs("order_value", "${af.map.order_value}");
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
$(document).ready(function(){
	var f = document.forms[0];
	var states = 
	[
		["P", "已发布", "1"],
		["P", "未发布", "0"],
		//[1, "直接发布", "1"],
		//[1, "已修改（已发布）", "2"],
		[1, "已审核（已发布）", "3"],
		[0, "默认（待审核）", "0"],
		//[0, "不发布", "-1"],
		//[0, "已修改（未发布）", "-2"],
		[0, "已审核（未发布）", "-3"]
	]

	f.pub_state.setAttribute("subElement", "info_state");
	
	f.pub_state.setAttribute("defaultText", "请选择...");
	f.pub_state.setAttribute("defaultValue", "");
	f.pub_state.setAttribute("selectedValue", "${af.map.pub_state}");
	
	f.info_state.setAttribute("defaultText", "请选择...");
	f.info_state.setAttribute("defaultValue", "");
	f.info_state.setAttribute("selectedValue", "${af.map.info_state}");

	new CascadeSelect(f.pub_state, "P", states);

	$("#btn_submit").click(function(){
		if (this.form.st_pub_date.value != "" && this.form.en_pub_date.value != "") {
			if (this.form.en_pub_date.value < this.form.st_pub_date.value) {
				alert("结束日期不得早于开始日期,请重新选择!")
				return false;
			}
		}
		this.form.submit();
	});
});
//]]></script>
</body>
</html>
