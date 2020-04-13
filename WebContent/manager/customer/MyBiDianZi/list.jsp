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
<html-el:form action="/customer/MyBiDianZi">
  <html-el:hidden property="method" value="list" />
  <html-el:hidden property="par_id" />
  <html-el:hidden property="mod_id" />
  <table width="100%" border="0" cellpadding="1" cellspacing="1" class="tableClassSearch">
    <tr>
      <td><table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">
          <tr>
            <td>
                        类型：
          <html-el:select property="bi_get_type" styleId="bi_get_type" styleClass="webinput">
            <html-el:option value="">全部</html-el:option>
            <c:forEach items="${biGetTypes}" var="keys">
              <html-el:option value="${keys.index}">${fn:escapeXml(keys.name)}</html-el:option>
            </c:forEach>
          </html-el:select>
          &nbsp;
                     转入或支出：
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
              <button class="bgButtonFontAwesome" type="submit"><i class="fa fa-search"></i>查 询</button>
              <button class="bgButtonFontAwesome" type="button" id="download"><i class="fa fa-download"></i>导 出</button>
              </td>
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
    <th>操作时间</th>
  </tr>
  <c:forEach var="cur" items="${userBiRecordlList}" varStatus="vs">
    <tr>
      <%--       <td align="center">${fn:escapeXml(cur.bi_get_memo)}</a></td> --%>
      <td align="center"><c:forEach items="${biGetTypes}" var="keys">
          <c:if test="${cur.bi_get_type eq keys.index}">${keys.name}</c:if>
        </c:forEach>
        <c:if test="${(cur.bi_get_type eq 200) or cur.bi_get_type eq -90}">
        <c:if test="${cur.bi_chu_or_ru eq 1}"><div>${cur.dest_uname}转给${cur.add_uname}</div></c:if>
        <c:if test="${cur.bi_chu_or_ru eq -1}"><div>${cur.add_uname}转给${cur.dest_uname}</div></c:if>
        </c:if>
      </td>
      <td align="center"><fmt:formatNumber var="bi" pattern="0.##" value="${cur.bi_no_before}" />
        <c:if test="${((cur.bi_get_type eq 130) or (cur.bi_get_type eq -10))}">
        <fmt:formatNumber var="bi" pattern="0.##" value="${cur.bi_no_before-cur.fuxiao_no_before}" />
        </c:if>
        ${bi}
        <c:if test="${((cur.bi_get_type eq 130) or (cur.bi_get_type eq -10))}">
         <fmt:formatNumber var="bi_fuxiao_before" pattern="0.##" value="${cur.fuxiao_no_before}" />
        (复销券：${bi_fuxiao_before})
        </c:if>
        </td>
      <td align="center">
        <c:set var="pre" value="+" />
        <c:set var="class_t" value="tip-success" />
        
        <c:if test="${cur.bi_chu_or_ru eq -1}">
          <c:set var="pre" value="-" />
          <c:set var="class_t" value="tip-danger" />
        </c:if>
        
        <fmt:formatNumber var="bi" pattern="0.##" value="${cur.bi_no}" />
         <c:if test="${((cur.bi_get_type eq 130) or (cur.bi_get_type eq -10))}">
         <fmt:formatNumber var="bi" pattern="0.##" value="${cur.bi_no-cur.fuxiao_no}" />
         </c:if>
         
        <c:set var="bi1" value=""  />
        
        <c:if test="${((cur.bi_get_type eq 200) or (cur.bi_get_type eq -90))}">
        <c:if test="${cur.bi_chu_or_ru eq -1}">
         <fmt:formatNumber var="bi_rate" pattern="0.##" value="${cur.bi_rate}" />
         <fmt:formatNumber var="bisrc" pattern="0.##" value="${bi - cur.bi_rate}" />
         <c:set var="bi1" value="<div title='手续费：${bi_rate}'>(${bisrc} + ${bi_rate})<span>"  />
        </c:if>
        </c:if>
        
        <c:if test="${((cur.bi_get_type eq 130) or (cur.bi_get_type eq -10))}">
         <fmt:formatNumber var="bi_fuxiao" pattern="0.##" value="${cur.fuxiao_no}" />
         <c:set var="bi1" value="(复销券：${bi_fuxiao})"/>
        </c:if>
        
        <span class="${class_t}">${pre}&nbsp;${bi}${bi1}</span> 
        </td>
      <td align="center"><fmt:formatNumber var="bi" pattern="0.##" value="${cur.bi_no_after}" />
       <c:if test="${((cur.bi_get_type eq 130) or (cur.bi_get_type eq -10))}">
        <fmt:formatNumber var="bi" pattern="0.##" value="${cur.bi_no_after-cur.fuxiao_no_after}" />
        </c:if>
        ${bi}
         <c:if test="${((cur.bi_get_type eq 130) or (cur.bi_get_type eq -10))}">
         <fmt:formatNumber var="bi_fuxiao_after" pattern="0.##" value="${cur.fuxiao_no_after}" />
        (复销券：${bi_fuxiao_after})
        </c:if>
        </td>
      <td align="center"><fmt:formatDate value="${cur.add_date}" pattern="yyyy-MM-dd HH:mm" /></td>
    </tr>
  </c:forEach>
  <tr>
    <td colspan="6" style="text-align:center"><html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
  </tr>
</table>
<div class="black">
  <form id="bottomPageForm" name="bottomPageForm" method="post" action="MyBiDianZi.do">
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
            pager.addHiddenInputs("bi_get_type", "${af.map.bi_get_type}");
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
		    	location.href = "${ctx}/manager/customer/MyBiDianZi.do?method=toExcel&mod_id=${af.map.mod_id}&" + $('.searchForm').serialize();
		    } else {
		    	location.href = "${ctx}/manager/customer/MyBiDianZi.do?method=toExcel&code=GBK&mod_id=${af.map.mod_id}&" + $('.searchForm').serialize();
		    }
		    return true;
		};
		var tip = "确认导出EXCEL格式数据？如果UTF-8编码格式乱码，请选择GBK编码格式下载！";
		$.jBox.confirm(tip, "系统提示", submit, { buttons: { '下载(UTF-8编码)': true, '下载(GBK编码)': false} });
	});
  
</script>
</body>
</html>
