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
  <jsp:include page="../_public_head_back.jsp" flush="true" />
  <table width="100%" border="0" cellpadding="1" cellspacing="1" class="tableClassSearch">
    <tr>
      <td width="14%" nowrap="nowrap" class="title_item">商家增值券余额：
        <fmt:formatNumber var="fxb"  pattern="#0.########" value="${userInfoEntp.leiji_money_entp/rmb_to_fanxianbi_rate}" />
      <fmt:formatNumber var="fxb_to_rmb"  pattern="#0.########" value="${userInfoEntp.leiji_money_entp}" />
      ${fxb}增值券 = ${fxb_to_rmb}元
        </td>
    </tr>
  </table>
  <div style="text-align: left;padding: 5px;"> <span class="tip-danger"><i class="fa fa-info-circle"></i> 100增值券=${rmb_to_fanxianbi_rate*100}元</span></div>
  <html-el:form action="/admin/ServiceEntpMoney" styleClass="searchForm">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="par_id" />
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="bi_get_types" />
    <html-el:hidden property="user_name" />
    <html-el:hidden property="user_id" />
    <table width="100%" border="0" cellpadding="1" cellspacing="1" class="tableClassSearch">
      <tr>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">
            <tr>
              <td> 转入或支出：
                <html-el:select property="bi_chu_or_ru" styleClass="webinput" >
                  <html-el:option value="">全部</html-el:option>
                  <html-el:option value="1">入</html-el:option>
                  <html-el:option value="-1">出</html-el:option>
                </html-el:select>
                &nbsp; 时间：
                从&nbsp;
                <html-el:text property="begin_date" styleId="begin_date" size="10" maxlength="10" readonly="true" onclick="WdatePicker()" />
                至&nbsp;
                <html-el:text property="end_date" styleId="end_date" size="10" maxlength="10" readonly="true" onclick="WdatePicker()" />
                &nbsp;
                <button class="bgButtonFontAwesome" type="submit"><i class="fa fa-search"></i>查 询</button> &nbsp; &nbsp;<input id="download" type="button" value="导出" class="bgButton" /></td>
                
            </tr>
          </table></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
    <tr>
      <th width="25%">类型</th>
      <th>操作前金额</th>
      <th>本次金额</th>
      <th>操作后金额</th>
      <th>派发消费者金额</th>
      <th>操作时间</th>
    </tr>
    <c:forEach var="cur" items="${userBiRecordlList}" varStatus="vs">
      <tr>
        <%--       <td align="center">${fn:escapeXml(cur.bi_get_memo)}</a></td> --%>
        <td align="center"><c:forEach items="${biGetTypes}" var="keys">
            <c:if test="${cur.bi_get_type eq keys.index}">${keys.name}</c:if>
          </c:forEach>
          <c:if test="${(cur.bi_get_type eq 200) or cur.bi_get_type eq -90}">
            <c:if test="${cur.bi_chu_or_ru eq 1}">
              <div>${cur.dest_uname}转给${cur.add_uname}</div>
            </c:if>
            <c:if test="${cur.bi_chu_or_ru eq -1}">
              <div>${cur.add_uname}转给${cur.dest_uname}</div>
            </c:if>
          </c:if>
        </td>
        <td align="center"><fmt:formatNumber var="bi" pattern="0.########" value="${cur.bi_no_before/rmb_to_fanxianbi_rate}" />
          ${bi}</td>
        <td align="center"><c:set var="pre" value="+" />
          <c:set var="class" value="tip-success" />
          <c:if test="${cur.bi_chu_or_ru eq -1}">
            <c:set var="pre" value="-" />
            <c:set var="class" value="tip-danger" />
          </c:if>
          <fmt:formatNumber var="bi" pattern="0.########" value="${cur.bi_no/rmb_to_fanxianbi_rate}" />
          <span class="${class}">${pre}&nbsp;${bi}</span> </td>
        <td align="center"><fmt:formatNumber var="bi" pattern="0.########" value="${cur.bi_no_after/rmb_to_fanxianbi_rate}" />
          ${bi}</td>
        <c:if test="${-130 eq cur.bi_get_type}">
          <td align="center">${cur.map.Xiadan_user_balance}</td>
        </c:if>
        <c:if test="${-130 ne cur.bi_get_type}">
          <td align="center">——</td>
        </c:if>
        <td align="center"><fmt:formatDate value="${cur.add_date}" pattern="yyyy-MM-dd HH:mm" /></td>
      </tr>
    </c:forEach>
    <tr>
      <td colspan="6" style="text-align:center"><html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
    </tr>
  </table>
  <div class="black">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="ServiceEntpMoney.do">
      <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" >
        <tr>
          <td height="10" ><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
            pager.addHiddenInputs("method", "list");
            pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
            pager.addHiddenInputs("par_id", "${af.map.par_id}");
            pager.addHiddenInputs("bi_chu_or_ru", "${af.map.bi_chu_or_ru}");
            pager.addHiddenInputs("begin_date", "${af.map.begin_date}");
            pager.addHiddenInputs("end_date", "${af.map.end_date}");
            pager.addHiddenInputs("bi_get_types", "${af.map.bi_get_types}");
            pager.addHiddenInputs("bi_type", "${af.map.bi_type}");
            pager.addHiddenInputs("user_name", "${af.map.user_name}");
            document.write(pager.toString());
        </script></td>
        </tr>
      </table>
    </form>
  </div>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript">//<![CDATA[
  var f = document.forms[0];
  $("#btn_submit").click(function(){
  	f.submit();
  });
  $("#download").click(function(){
		
		var submit = function (v, h, f) {
		    if (v == true) {
		    	location.href = "${ctx}/manager/admin/ServiceEntpMoney.do?method=toExcelOneUser&mod_id=${af.map.mod_id}&" + $('.searchForm').serialize();
		    } else {
		    	location.href = "${ctx}/manager/admin/ServiceEntpMoney.do?method=toExcelOneUser&code=GBK&mod_id=${af.map.mod_id}&" + $('.searchForm').serialize();
		    }
		    return true;
		};
		var tip = "确认导出EXCEL格式数据？如果UTF-8编码格式乱码，请选择GBK编码格式下载！";
		$.jBox.confirm(tip, "系统提示", submit, { buttons: { '下载(UTF-8编码)': true, '下载(GBK编码)': false} });
	});
  </script>
</body>
</html>
