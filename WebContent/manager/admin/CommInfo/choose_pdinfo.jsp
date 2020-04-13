<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base target="_self" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${ctx}/commons/styles/icons/icons.css" rel="stylesheet" type="text/css" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<title>选择产品</title>
<style type="text/css">
.ul_selpd {
}
.ul_selpd li {
	width: 130px;
	height: 18px;
	overflow: hidden;
	float: left;
	padding: 10px;
}
.ul_selpd li b {
	float: right;
	font-weight: normal;
	padding-left: 5px;
}
.inputText {
	border:1px solid #ccc;
	height:16px;
	line-height:16px;
	text-align:center;
}
table.TabTitle {
	border:1px solid #ccc;
	border-bottom:none;
	border-left:none;
}
table.TabTitle td {
	border-bottom:1px solid #ccc;
	border-left:1px solid #ccc;
	border-collapse:collapse;
	background:#F1F7FC;
	padding:6px 6px;
}
table.Tab {
	border:none;
}
table.Tab td {
	border-collapse:collapse;
	padding:5px 6px;
}
table.Tab td.next {
	background:#DBE9F7;
}
table.Tab td.page {
	text-align:right;
	background:#fff;
	padding-right:20px;
}
tr.alteven td {
	background: #ecf6fc;
}
tr.altodd td {
	background: #DBE9F7;
}
tr.over td {
	background: #BDD3E8;
}
table {
	table-layout: fixed;
}
</style>
</head>
<body>
<div align="center">
  <html-el:form action="/admin/CommInfo.do">
    <html-el:hidden property="method" value="choosePdInfo" />
    <table width="750" border="0" align="center" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th align="left" nowrap="nowrap">产品类别：
         <html-el:hidden property="cls_id" styleId="cls_id"/>
         <html-el:text property="cls_name" styleId="cls_name" readonly="true" onclick="getParClsInfo();" styleClass="webinput" maxlength="50"  style="width:258px;"/>
          &nbsp;
          产品名称：
          <html-el:text property="pd_name_like" styleClass="webinput" maxlength="50"/>
          &nbsp;
          <input name="submit" type="submit" style="cursor:pointer;" class="bgButton" value="查 询" /></th>
      </tr>
    </table>
  </html-el:form>
  <div>
    <ul class="ul_selpd">
      <c:forEach var="cur" items="${entityList}" varStatus="vs1">
        <li title="${cur.pd_name}" > <b><a class="butbase" href="#" onclick="returnPdInfo('${cur.pd_id}','${cur.pd_name}','${cur.cls_id}','${cur.cls_name}','${cur.par_cls_id}','${cur.brand_id}','${cur.own_entp_id}','${cur.map.own_entp_name}');" ><span class="icon-ok">选择</span></a></b> ${fn:escapeXml(cur.pd_name)} </li>
        <c:if test="${vs1.count mod 3 eq 0}">
          <c:out value="</tr>" escapeXml="false" />
          <c:out value="<tr>" escapeXml="false" />
        </c:if>
      </c:forEach>
    </ul>
  </div>
  <form id="bottomPageForm" name="bottomPageForm" method="post" action="CommInfo.do">
    <table width="750" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td height="40" align="center"><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script> 
          <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
            pager.addHiddenInputs("method", "choosePdInfo");
            pager.addHiddenInputs("pd_name_like", "${fn:escapeXml(af.map.pd_name_like)}");
            pager.addHiddenInputs("cls_name", "${fn:escapeXml(af.map.cls_name)}");
            document.write(pager.toString());
            </script></td>
      </tr>
    </table>
  </form>
</div>

<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script> 
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	$(".Tab tr").mouseover(function(){  
		$(this).addClass("over");
	}).mouseout(function(){
		$(this).removeClass("over");
	})
	$(".Tab tr:even").addClass("alteven");
	$(".Tab tr:odd").addClass("altodd");
});

function getParClsInfo() {
	var url = "BasePdClass.do?method=getParClsInfo&isPd=true&azaz=" + Math.random();
	$.dialog({
		title:  "选择产品类别",
		width:  450,
		height: 400,
        lock:true ,
		content:"url:"+url
	});
}


function returnPdInfo(val0,val1,val2,val3,val4,val5,val6,val7){
	var api = frameElement.api, W = api.opener;
	
	W.document.getElementById("pd_id").value = val0;
	W.document.getElementById("pd_name").value = val1;
	W.document.getElementById("cls_id").value = val2;
	W.document.getElementById("cls_name").value = val3;
	W.document.getElementById("par_cls_id").value = val4;
	W.document.getElementById("brand_id").value = val5;	
	
	W.document.getElementById("own_entp_id").value = val6;
	W.document.getElementById("entp_name").value = val7;	
	
	$.ajax({
		type: "POST",
		url: "CommInfo.do",
		data: "method=getComm_no&cls_id=" + val2,
		dataType: "json",
		error: function(request, settings) {},
		success: function(data) {
			if(null != data){
				W.document.getElementById("comm_no").value = data.comm_no;	
				api.close();
			}		
		}
	});
	//$(W.document.getElementById("view_a")).attr("href","${ctx}/manager/all/CommInfo.do?method=view&pd_id=" + val0).show();
}
//]]></script>
<jsp:include page="../../../_public_page.jsp" flush="true"/>
</body>
</html>