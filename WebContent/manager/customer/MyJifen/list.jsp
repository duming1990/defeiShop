<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css?v20150103" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="mainbox mine">
<jsp:include page="../_nav.jsp" flush="true"/>
 <fmt:formatNumber var="bi_jifen_sum" pattern="0.########" value="${bi_jifen}" />
 <fmt:formatNumber var="bi_dianzibi_sum" pattern="0.########" value="${bi_dianzi}" />
<div class="tipmsg"><p>累计金额： <b>${bi_dianzibi_sum} 元</b></p></div>
<html-el:form action="/customer/MyJifen">
  <html-el:hidden property="method" value="list" />
  <html-el:hidden property="par_id" />
  <html-el:hidden property="mod_id" />
  <table width="100%" border="0" cellpadding="1" cellspacing="1" class="tableClassSearch">
    <tr>
      <td><table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">
          <tr>
            <td width="6%" nowrap="nowrap"> 类型：
                <html-el:select property="jifen_type">
                  <html-el:option value="">全部</html-el:option>
                  <c:forEach var="cur" items="${jifenTypes}">
                  <html-el:option value="${cur.index}">${cur.showName}</html-el:option>
                  </c:forEach>
                </html-el:select>
                &nbsp;卡号：<html-el:text property="card_no" styleId="card_no" maxlength="40" style="width:120px;" styleClass="webinput"/>
                时间：
               从&nbsp;
                <html-el:text property="begin_date" styleId="begin_date" size="10" maxlength="10" readonly="true" onclick="WdatePicker()" />
                至&nbsp;
                <html-el:text property="end_date" styleId="end_date" size="10" maxlength="10" readonly="true" onclick="WdatePicker()" />&nbsp;&nbsp;
              &nbsp;
              <button class="bgButtonFontAwesome" type="button" id="btn_submit"><i class="fa fa-search"></i>查 询</button></td>
          </tr>
        </table></td>
    </tr>
  </table>
</html-el:form>
<%@ include file="/commons/pages/messages.jsp" %>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
      <tr>
        <th width="7%"> 序号</th>
        <th nowrap="nowrap">类型</th>
        <th width="15%">返现卡</th>
        <th width="10%">本次积分</th>
        <th width="10%">本次金额</th>
        <th width="20%">操作时间</th>
        <th width="10%">持卡人</th>
        <th width="15%">备注</th>
      </tr>
      <c:forEach var="cur" items="${userJifenRecordList}" varStatus="vs">
        <tr>
          <td align="center" nowrap="nowrap"> ${vs.count }</td>
          <td align="center">
           <c:forEach var="jt" items="${jifenTypes}">
              <c:if test="${jt.index eq  cur.jifen_type}">${jt.showName}</c:if>
           </c:forEach>
          </td>
          <td align="center" nowrap="nowrap"><a href="javascript:cardInfoView('${cur.card_id}');">${cur.card_no}</a></td>
          <td align="right" nowrap="nowrap"><fmt:formatNumber pattern="0" value="${cur.opt_c_score}" /></td>
          <td align="right" nowrap="nowrap"> <fmt:formatNumber pattern="0.########" value="${cur.opt_c_dianzibi}" /></td>
          <td align="center">
            <fmt:formatDate value="${cur.add_date}" pattern="yyyy-MM-dd HH:mm:ss" />
          </td>
          <td align="center" nowrap="nowrap">${fn:escapeXml(cur.map.card_user_name)}</td>
          <td align="center" nowrap="nowrap">${fn:escapeXml(cur.remark)}</td>
        </tr>
      </c:forEach>
    </table>
</form>
<div class="black">
  <form id="bottomPageForm" name="bottomPageForm" method="post" action="MyJifen.do">
    <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" >
      <tr>
        <td height="10" ><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
          <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
            pager.addHiddenInputs("method", "list");
            pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
            pager.addHiddenInputs("par_id", "${af.map.par_id}");
    		pager.addHiddenInputs("jifen_type", "${af.map.jifen_type}");
			pager.addHiddenInputs("begin_date", "${af.map.begin_date}");
			pager.addHiddenInputs("end_date", "${af.map.end_date}");
            document.write(pager.toString());
        </script></td>
      </tr>
    </table>
  </form>
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
			id: "cardInfoView",
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
