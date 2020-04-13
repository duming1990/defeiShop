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
<html-el:form action="/customer/MyScore">
  <html-el:hidden property="method" value="list" />
  <html-el:hidden property="par_id" />
  <html-el:hidden property="mod_id" />
  <table width="100%" border="0" cellpadding="1" cellspacing="1" class="tableClassSearch">
    <tr>
      <td><table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">
          <tr>
            <td width="6%" nowrap="nowrap">时间：
             	  从&nbsp;
                <html-el:text property="begin_date" styleId="begin_date" size="10" maxlength="10" readonly="true" onclick="WdatePicker()" />
                	至&nbsp;
                <html-el:text property="end_date" styleId="end_date" size="10" maxlength="10" readonly="true" onclick="WdatePicker()" />
              &nbsp; <button class="bgButtonFontAwesome" type="button" id="btn_submit"><i class="fa fa-search"></i>查 询</button></td>
          </tr>
        </table></td>
    </tr>
  </table>
</html-el:form>
<%@ include file="/commons/pages/messages.jsp" %>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
  <tr>
    <th width="20%">积分类型</th>
    <th>操作前积分</th>
    <th>本次积分</th>
    <th>操作后积分</th>
    <th>操作时间</th>
  </tr>
  <c:forEach var="cur" items="${userScoreRecordList}" varStatus="vs">
    <tr>
      <td align="center"><c:forEach items="${scoreTypes}" var="keys">
          <c:if test="${cur.score_type eq  keys.index}">${keys.name}</c:if>
        </c:forEach>
      </td>
      <td align="center"><fmt:formatNumber pattern="#0.########" value="${cur.hd_score_before}" /></td>
      <td align="center"><c:if var="score_flag" test="${not empty cur.link_id and (cur.score_type eq 1 or cur.score_type eq 4)}"><a><fmt:formatNumber pattern="#0.########" value="${cur.hd_score}" /></a></c:if>
      	<c:if test="${!score_flag}"><fmt:formatNumber pattern="#0.########" value="${cur.hd_score}" /></c:if>
      </td>
      <td align="center"><fmt:formatNumber pattern="#0.########" value="${cur.hd_score_after}" /></td>
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
        </tr>
   </c:forEach>
  <tr>
    <td colspan="6" style="text-align:center"><html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
  </tr>
 
</table>
<div class="black">
  <form id="bottomPageForm" name="bottomPageForm" method="post" action="MyScore.do">
    <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" >
      <tr>
        <td height="10" ><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
          <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
            pager.addHiddenInputs("method", "list");
            pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
            pager.addHiddenInputs("par_id", "${af.map.par_id}");
            pager.addHiddenInputs("score_type", "${af.map.score_type}");
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
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript">//<![CDATA[
  var f = document.forms[0];
  $("#btn_submit").click(function(){
  	f.submit();
  });
  
  function cardInfoView(card_id) {
		var title = "返现卡信息";
	
		var url = "${ctx}/manager/customer/MyJifen.do?method=view&card_id=" + card_id ;
		$.dialog({
			title:  title,
			width:  500,
			height: 300,
			max: false,
	        min: false,
	        fixed: true,
	        lock: true,
			content:"url:"+ encodeURI(url)
		});
	}
  </script>
</body>
</html>
