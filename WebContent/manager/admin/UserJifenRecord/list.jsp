<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
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
  <html-el:form action="/admin/UserJifenRecord" styleClass="searchForm">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="type" />
    <html-el:hidden property="type" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClassSearch">
      <tr>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">
            <tr>
              <td width="6%" nowrap="nowrap">
                	类型：
                <html-el:select property="biGetType">
                  <html-el:option value="">全部</html-el:option>
                  <c:forEach var="cur" items="${biGetTypes}">
                  <html-el:option value="${cur.index}">${cur.name}</html-el:option>
                  </c:forEach>
                </html-el:select>
                &nbsp;订单编号：<html-el:text property="trade_index" styleId="trade_index" maxlength="40" style="width:120px;" styleClass="webinput"/>
                                      下单用户：<html-el:text property="add_user_name" styleId="add_user_name" style="width:80px;" styleClass="webinput"/>
              	&nbsp;奖励用户：<html-el:text property="add_uname" styleId="add_uname" maxlength="40" style="width:80px;" styleClass="webinput"/>
              	&nbsp;操作时间 从：
                <html-el:text property="st_add_date" styleId="st_add_date" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker()" />
               至：
                <html-el:text property="en_add_date" styleId="en_add_date" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker()" />
                &nbsp;&nbsp; <html-el:submit value="查 询" styleClass="bgButton" />
                 &nbsp;&nbsp;
                <input id="download" type="button" value="导出" class="bgButton" />
                </td>
            </tr>
          </table></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="UserJifenRecord.do?method=delete">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th width="5%"> 序号</th>
        <th width="7%">类型</th>
        <th width="8%">订单编号</th>
        <th width="5%">下单用户</th>
        <th width="5%">奖励用户</th>
        <th width="5%">操作前余额</th>
        <th width="5%">本次余额</th>
        <th width="5%">操作后余额</th>
        <th width="6%">操作时间</th>
      </tr>
      <c:forEach var="cur" items="${userbiRecordList}" varStatus="vs">
        <tr>
          <td align="center"> ${vs.count }</td>
          <td align="center">${(cur.map.bi_get_memo)}</td>
          <td align="center">${cur.map.trade_index}</td>
          <td align="center">${cur.map.add_user_name}</td>
          <td align="center">${(cur.map.add_uname)}</td>
          <td align="center"> <fmt:formatNumber pattern="0.########" value="${cur.map.bi_no_before}" /></td>
          <td align="center"> <fmt:formatNumber pattern="0.########" value="${cur.map.bi_no}" /></td>
          <td align="center"> <fmt:formatNumber pattern="0.########" value="${cur.map.bi_no_after}" /></td>
          <td align="center">
            <fmt:formatDate value="${cur.map.add_date}" pattern="yyyy-MM-dd HH:mm:ss" />
          </td>
        </tr>
      </c:forEach>
    </table>
  </form>
  <div class="pageClass">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="UserJifenRecord.do">
      <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" >
        <tr>
          <td height="10" ><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
            pager.addHiddenInputs("method", "list");
            pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
            pager.addHiddenInputs("trade_index", "${fn:escapeXml(af.map.trade_index)}");
            pager.addHiddenInputs("biGetType", "${af.map.biGetType}");
            pager.addHiddenInputs("add_user_name", "${fn:escapeXml(af.map.add_user_name)}");
            pager.addHiddenInputs("add_uname", "${fn:escapeXml(af.map.user_name)}");
            pager.addHiddenInputs("st_add_date", "${fn:escapeXml(af.map.st_add_date)}");
            pager.addHiddenInputs("en_add_date", "${fn:escapeXml(af.map.en_add_date)}");
            document.write(pager.toString());
        </script></td>
        </tr>
      </table>
    </form>
  </div>
</div>

<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	
   $("#download").click(function(){
		
		var submit = function (v, h, f) {
		    if (v == true) {
		    	location.href = "${ctx}/manager/admin/UserJifenRecord.do?method=toExcel&mod_id=${af.map.mod_id}&" + $('.searchForm').serialize();
		    } else {
		    	location.href = "${ctx}/manager/admin/UserJifenRecord.do?method=toExcel&code=GBK&mod_id=${af.map.mod_id}&" + $('.searchForm').serialize();
		    }
		    return true;
		};
		var tip = "确认导出EXCEL格式数据？如果UTF-8编码格式乱码，请选择GBK编码格式下载！";
		$.jBox.confirm(tip, "系统提示", submit, { buttons: { '下载(UTF-8编码)': true, '下载(GBK编码)': false} });
	});
	
});

function openUserInfo(id){
	var url = "${ctx}/manager/admin/UserInfo.do?method=view&mod_id=${af.map.mod_id}&id="+id;
	$.dialog({
		title:  "会员信息",
		width:  770,
		height: 550,
        lock:true ,
		content:"url:"+url
	});
}
//]]></script>
</body>
<jsp:include page="../public_page.jsp" flush="true" />
</html>
