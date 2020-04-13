<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
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
  <html-el:form action="/admin/ServiceEntpMoney" styleClass="searchForm">
    <html-el:hidden property="method" value="form" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClassSearch">
      <tr>
        <td>&nbsp;用户名称：
          <html-el:text property="user_name_like" styleClass="webinput" maxlength="50" style="width:200px;"/>
          &nbsp;
          <html-el:submit value="查 询" styleClass="bgButton" />
        </td>
      </tr>
    </table>
  </html-el:form>
  <html-el:form action="/admin/ServiceEntpMoney" styleClass="searchForm">
    <html-el:hidden property="method" value="listAll" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClassSearch">
      <tr>
        <td>
         &nbsp; 时间：
                从&nbsp;
                <html-el:text property="begin_date" styleId="begin_date" size="10" maxlength="10" readonly="true" onclick="WdatePicker()" />
                至&nbsp;
                <html-el:text property="end_date" styleId="end_date" size="10" maxlength="10" readonly="true" onclick="WdatePicker()" />
          &nbsp;
          <html-el:submit value="全 用 户 查 询" styleClass="bgButton" />
        </td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="ServiceEntpMoney.do?method=delete">
    <div style="text-align: left;padding: 5px;"> <span class="tip-danger"><i class="fa fa-info-circle"></i> 100增值券=${rmb_to_fanxianbi_rate*100}元</span></div>
    <input type="hidden" name="method" id="method" value="delete" />
    <input type="hidden" name="mod_id" id="mod_id" value="${af.map.mod_id}" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr class="tite2">
        <th nowrap="nowrap">用户名称</th>
        <th nowrap="nowrap">所属企业名</th>
        <th nowrap="nowrap">电话</th>
        <th nowrap="nowrap">添加时间</th>
        <th nowrap="nowrap">增值券余额</th>
        <th nowrap="nowrap">操作</th>
      </tr>
      <c:forEach var="cur" items="${userinfos}" varStatus="vs">
        <tr align="center">
          <td>${fn:escapeXml(cur.user_name)}</td>
          <td>${fn:escapeXml(cur.own_entp_name)}</td>
          <td>${cur.mobile}</td>
          <td><fmt:formatDate value="${cur.add_date}" pattern="yyyy-MM-dd" /></td>
          <fmt:formatNumber var="bi_src" value="${cur.leiji_money_entp/rmb_to_fanxianbi_rate}" pattern="0.########"/>
          <td> ${bi_src}</td>
          <td><c:url var="url" value="/manager/admin/ServiceEntpMoney.do?method=list&user_id=${cur.id}"/>
            <a class="butbase" onclick="location.href='${url}'"> <span class="icon-ok">查询</span></a> </td>
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
          <td width="8%" nowrap="nowrap">&nbsp;</td>
        </tr>
      </c:forEach>
    </table>
  </form>
  <div class="pageClass">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="ServiceEntpMoney.do">
      <table width="98%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
					var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
					pager.addHiddenInputs("method", "form");
					pager.addHiddenInputs("user_name_like", "${fn:escapeXml(af.map.user_name_like)}");
					pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
					document.write(pager.toString());
	            	</script></td>
        </tr>
      </table>
    </form>
  </div>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/scripts/tip/jquery.quicktip.min.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<script type="text/javascript">//<![CDATA[
var f = document.forms[0];
// 提交
$("#btn_submit").click(function(){
	if(Validator.Validate(this.form, 3)){
       $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
//        $("#btn_reset").attr("disabled", "true");
//        $("#btn_back").attr("disabled", "true");
		this.form.submit();
	}
});


</script>
<jsp:include page="../public_page.jsp" flush="true" />
</body>
</html>
