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
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/admin/QaInfo.do?method=list">
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="q_type" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClassSearch">
      <tr>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">
            <tr>
              <td>&nbsp;标题：
                <html-el:text property="q_title_like" styleClass="webinput" maxlength="256"/>
                &nbsp;状态：
                <html-el:select property="qa_state" style="width:80px;">
                  <html-el:option value="">请选择...</html-el:option>
                  <html-el:option value="1">已发布</html-el:option>
                  <html-el:option value="0">未发布</html-el:option>
                </html-el:select>
                &nbsp;投诉时间 从：
                <html-el:text property="st_q_date" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker();" />
                至：
                <html-el:text property="en_q_date" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker();" />
                &nbsp;回答时间 从：
                <html-el:text property="st_a_date" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker();" />
                至：
                <html-el:text property="en_a_date" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker();" />
                &nbsp;
                <html-el:button property="" value="快速搜索" styleClass="bgButton" styleId="btn_submit" /></td>
            </tr>
          </table></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="QaInfo.do?method=delete">
    <div style="padding-bottom:5px;">
      <input type="button" name="delete" id="delete" class="bgButton" value="删除所选" onclick="this.form.action += '&' + $('#bottomPageForm').serialize();confirmDeleteAll(this.form);" />
      <c:if test="${af.map.q_type eq 1}">
      <input type="button" name="add" id="add" class="bgButton" value="添 加" onclick="location.href='QaInfo.do?method=add&mod_id=${af.map.mod_id}&q_type=${af.map.q_type}';"/>
      </c:if>
      <c:if test="${af.map.q_type eq 2}">
      <input type="button" name="add" id="add" class="bgButton" value="添 加" onclick="location.href='QaInfo.do?method=add&mod_id=${af.map.mod_id}&q_type=${af.map.q_type}&is_nx=${af.map.is_nx}';"/>
      </c:if>
      <input type="hidden" name="mod_id" id="mod_id" value="${af.map.mod_id}" />
    </div>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th width="5%" nowrap="nowrap"><input name="chkAll" type="checkbox" id="chkAll" value="-1" onclick="checkAll(this);" /></th>
        <th width="25%" nowrap="nowrap">问题标题</th>
        <th width="12%">投诉时间</th>
        <th width="12%">回答时间</th>
        <th width="7%">回答人</th>
        <th width="7%">状态</th>
        <th width="5%">排序值</th>
        <th width="10%" nowrap="nowrap">操作</th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr>
          <td align="center" nowrap="nowrap"><input name="pks" type="checkbox" id="pks" value="${cur.id}" /></td>
          <td align="left" nowrap="nowrap"><a style="text-decoration:none;" href="QaInfo.do?method=view&amp;id=${cur.id}&amp;mod_id=${af.map.mod_id}">
            <c:out value="${fnx:abbreviate(cur.q_title, 50, '...')}" />
            </a></td>
          <td align="center"><fmt:formatDate value="${cur.q_date}" pattern="yyyy-MM-dd" /></td>
          <td align="center"><fmt:formatDate value="${cur.a_date}" pattern="yyyy-MM-dd" /></td>
          <td align="left"><c:out value="${cur.a_uname}" /></td>
          <td align="center"><c:choose>
              <c:when test="${cur.qa_state eq 1}"> <span style="color:green">已发布</span> </c:when>
              <c:when test="${cur.qa_state eq 0}"> <span style="color:red">未发布</span> </c:when>
              <c:when test="${cur.qa_state eq -1}"> 已删除 </c:when>
              <c:otherwise> - </c:otherwise>
            </c:choose></td>
          <td align="center"><c:out value="${cur.order_value}" /></td>
          <td align="center" nowrap="nowrap"><span style="cursor: pointer; margin-right:7px;" onclick="confirmUpdate(null, 'QaInfo.do', 'id=${cur.id}&' + $('#bottomPageForm').serialize())">回复</span>|<span style="cursor: pointer; margin-left:7px;" onclick="confirmDelete(null, 'QaInfo.do', 'id=${cur.id}&' + $('#bottomPageForm').serialize())">删除</span></td>
        </tr>
        <c:if test="${vs.last eq true}">
          <c:set var="i" value="${vs.count}" />
        </c:if>
      </c:forEach>
      <c:forEach begin="${i}" end="${af.map.pager.pageSize - 1}">
        <tr align="center">
          <td>&nbsp;</td>
          <td nowrap="nowrap">&nbsp;</td>
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
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="QaInfo.do">
      <table width="98%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td height="10"><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script> 
            <script type="text/javascript">
				var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
		        pager.addHiddenInputs("method", "list");
				pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
		        pager.addHiddenInputs("q_title_like", "${fn:escapeXml(af.map.q_title_like)}");
		        pager.addHiddenInputs("q_type", "${af.map.q_type}");
		        pager.addHiddenInputs("qa_state", "${af.map.qa_state}");
				pager.addHiddenInputs("st_q_date", "${af.map.st_q_date}");
				pager.addHiddenInputs("en_q_date", "${af.map.en_q_date}");
				pager.addHiddenInputs("st_a_date", "${af.map.st_a_date}");
				pager.addHiddenInputs("en_a_date", "${af.map.en_a_date}");

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
	$("#btn_submit").click(function(){
		if (this.form.st_q_date.value != "" && this.form.en_q_date.value != "") {
			if (this.form.en_q_date.value < this.form.st_q_date.value) {
				alert("结束日期不得早于开始日期,请重新选择!");
				return false;
			}
		}
		if(this.form.st_a_date.value != "" && this.form.en_a_date.value != "") {
			if (this.form.en_a_date.value < this.form.st_a_date.value) {
				alert("结束日期不得早于开始日期,请重新选择!");
				return false;
			}
		}
		this.form.submit();
	});
});
//]]></script>
<jsp:include page="../public_page.jsp" flush="true"/>
</body>
</html>
