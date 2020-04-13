<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="mainbox mine">
  <jsp:include page="../_nav.jsp" flush="true"/>
  <html-el:form action="/customer/MyBiHuoKuan">
  <html-el:hidden property="method" value="list" />
  <html-el:hidden property="par_id" />
  <html-el:hidden property="mod_id" />
  <table width="100%" border="0" cellpadding="1" cellspacing="1" class="tableClassSearch">
    <tr>
      <td><table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">
          <tr>
            <td><!--             类型： -->
              <%--               <html-el:select property="score_type" styleId="score_type" styleClass="webinput"> --%>
              <%--                 <html-el:option value="">全部</html-el:option> --%>
              <%--                 <c:forEach items="${biGetTypes}" var="keys"> --%>
              <%--                   <html-el:option value="${keys.index}">${fn:escapeXml(keys.name)}</html-el:option> --%>
              <%--                 </c:forEach> --%>
              <%--               </html-el:select> --%>
              <!--               &nbsp; -->
              &nbsp; 时间：
               从&nbsp;
                <html-el:text property="begin_date" styleId="begin_date" size="10" maxlength="10" readonly="true" onclick="WdatePicker()" />
                至&nbsp;
                <html-el:text property="end_date" styleId="end_date" size="10" maxlength="10" readonly="true" onclick="WdatePicker()" />
              &nbsp;
              <button class="bgButtonFontAwesome" type="submit"><i class="fa fa-search"></i>查 询</button></td>
          </tr>
        </table></td>
    </tr>
  </table>
</html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
  <tr>
    <th width="25%">类型</th>
    <th>操作前金额</th>
    <th>本次金额</th>
    <th>操作后金额</th>
    <th>购买人</th>
    <th>订单号</th>
    <th>操作时间</th>
  </tr>
  <c:forEach var="cur" items="${userBiRecordlList}" varStatus="vs">
    <tr>
      <td align="center"><c:forEach items="${biGetTypes}" var="keys">
          <c:if test="${cur.bi_get_type eq  keys.index}">${keys.name}</c:if>
        </c:forEach>
      </td>
      <td align="center"><fmt:formatNumber var="bi" pattern="0.########" value="${cur.bi_no_before}" />
        ${bi}</td>
      <td align="center"><c:set var="pre" value="+" />
        <c:set var="class_" value="tip-success" />
        <c:if test="${cur.bi_chu_or_ru eq -1}">
          <c:set var="pre" value="-" />
          <c:set var="class_" value="tip-danger" />
        </c:if>
        <fmt:formatNumber var="bi" pattern="0.########" value="${cur.bi_no}" />
        <span class="${class_}">${pre}&nbsp;${bi}</span> </td>
      <td align="center"><fmt:formatNumber var="bi" pattern="0.########" value="${cur.bi_no_after}" />${bi}</td>
      <td align="center"><c:if test="${(cur.bi_get_type ne -60) and (cur.bi_get_type ne 112)}">${cur.map.buy_name}</c:if></td>
      <td align="center"><c:if test="${(cur.bi_get_type ne -60) and (cur.bi_get_type ne 112)}">${cur.map.trade_index}</c:if></td>
      <td align="center"><fmt:formatDate value="${cur.add_date}" pattern="yyyy-MM-dd HH:mm" /></td>
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
        </tr>
      </c:forEach>
  <tr>
    <td colspan="7" style="text-align:center"><html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
  </tr>
</table>
  <div class="black">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="MyBiHuoKuan.do">
      <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" >
        <tr>
          <td height="10" ><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
            pager.addHiddenInputs("method", "list");
            pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
            pager.addHiddenInputs("par_id", "${af.map.par_id}");
            pager.addHiddenInputs("bi_chu_or_ru", "${af.map.bi_chu_or_ru}");
            pager.addHiddenInputs("entp_name_like", "${fn:escapeXml(af.map.entp_name_like)}");
            pager.addHiddenInputs("begin_date", "${af.map.begin_date}");
            pager.addHiddenInputs("end_date", "${af.map.end_date}");
            document.write(pager.toString());
        </script></td>
        </tr>
      </table>
    </form>
  </div>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	
});
</script>
</body>
</html>
