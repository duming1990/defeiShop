<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/scripts/colorbox/style3/colorbox.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div style="width: 99%" class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/admin/MerchantCheck" styleClass="searchForm">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="entp_type" />
    <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tableClassSearch">
      <tr>
        <td><div> 商家名：
            <html-el:text property="entp_name" maxlength="40" style="width:150px;" styleClass="webinput" />
          
          &nbsp;<span style="color:red">*</span>结算区间：
            从
            <html-el:text property="st_finish_date" styleClass="webinput" styleId="st_finish_date" size="10" maxlength="10" readonly="true" onclick="WdatePicker();"/>
            至
            <html-el:text property="en_finish_date" styleClass="webinput" styleId="en_finish_date" size="10" maxlength="10" readonly="true" onclick="WdatePicker();"/>
            &nbsp;<html-el:submit value="查 询" styleClass="bgButton" styleId="bgButton" />
            &nbsp;&nbsp;&nbsp;&nbsp;<c:if test="${empty show}"><span style="color:red">请选择要结算的时间区间</span></c:if>
          </div></td>
      </tr>
    </table>
 </html-el:form>
<c:if test="${not empty show}">
<form id="listForm" name="listForm" method="post" action="MerchantCheck.do?method=batchCheck">

  <div style="padding-bottom:5px;">
      <input type="button" name="add" id="add" class="bgButton" value="批量结算" onclick="batchCheck(this.form)" />
    </div>
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
	<tr>
		<th width="5%" nowrap="nowrap">
           <input name="chkAll" type="checkbox" id="chkAll" value="-1" onclick="checkAll(this);" />
        </th>
        <th width="20%">商家名</th>
        <th width="5%">结算类型</th>
        <th width="10%">订单总数量</th>
        <th width="10%">产品总数量</th>
        <th width="10%">订单总金额</th>
        <th width="10%">本次结算金额</th>
        <th width="10%">商家货款币</th>
        <th width="15%">结算</th>
      </tr>
      
     <c:forEach var="cur" items="${entityList}">
     	<tr>
     	<td width="5%" nowrap="nowrap" align="center">
	        <input name="pks" type="checkbox" id="pks_${cur.map.entp_id}" value="${cur.map.entp_id}" />
          </td>
     		<td align="center">${fn:escapeXml(cur.map.entp_name)}</td>
     		<td align="center">
     			<c:if test="${cur.order_type eq 0}"><span style="color :red;">线上</span></c:if>
     			<c:if test="${cur.order_type ne 0}"> <span style="color :green;">线下</span> </c:if>
     		</td>
     		<td align="center">${fn:escapeXml(cur.map.count_entp_id)}</td>
     		<td align="center">${fn:escapeXml(cur.map.sum_order_num)}</td>
     		<td align="center"><fmt:formatNumber pattern="#0.00" value="${cur.map.sum_order_money}" /></td>
     		<td align="center"><fmt:formatNumber pattern="#0.00" value="${cur.map.sum_huokuan}" /></td>
     		<td align="center"><fmt:formatNumber pattern="#0.00" value="${cur.map.bi_huoKuang}"/></td>
     		
     		<td align="center">
     			<a href="${ctx}/manager/admin/MerchantCheck.do?method=listDetails&mod_id=${af.map.mod_id}&entp_name=${cur.map.entp_name}&entp_id=${cur.map.entp_id}&st_finish_date=${af.map.st_finish_date}&en_finish_date=${af.map.en_finish_date}" class="butbase beautybg" style="color: white;"><span class="icon-ok">查看</span></a>
     			<a class="butbase"><span onclick="doNeedMethod(null, 'MerchantCheck.do', 'startCheck','entp_id=${cur.map.entp_id}&'+ $('.searchForm').serialize())" class="icon-ok">结算</span></a>
     		</td>
     	</tr>
     </c:forEach>
     
</table>
</form>

<div>
 <div class="pageClass">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="MerchantCheck.do">
      <table width="98%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
            pager.addHiddenInputs("method", "list");
           	pager.addHiddenInputs("entp_name", "${af.map.entp_name}");
			pager.addHiddenInputs("st_finish_date", "${af.map.st_finish_date}");
			pager.addHiddenInputs("en_finish_date", "${af.map.en_finish_date}");
            document.write(pager.toString());
            </script></td>
        </tr>
      </table>
    </form>
  </div>
</div>
</c:if>
 </div>
 <script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/area.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/citySelect.js"></script>
<script type="text/javascript" src="${ctx}/scripts/colorbox/jquery.colorbox.min.js"></script>
<script type="text/javascript"> //<![CDATA[
	
$("a.beautybg").colorbox({width:"90%", height:"80%", iframe:true});
	
function batchCheck(form){
	//批量对账
	var checkedCount = 0;
	if (!form.pks) {
		
	}else if(!form.pks.length) {
		if (form.pks.checked == true) {
			checkedCount = 1;
		}
	}else if(form.pks.length > 0) {
		for (var i = 0; i < form.pks.length; i++) {
			if (form.pks[i].checked == true) {
				checkedCount = 1;
				break;
			}
		}
	}
	var st_finish_date="${af.map.st_finish_date}";
	var en_finish_date="${af.map.en_finish_date}";
	
	if (checkedCount == 0) {
		alert("请至少选择一个结算项！");
	} else {
		if(confirm("确定所有选中的项要结算吗？")) {
			form.method.value = "batchCheck";
			$.ajax({
				type: "POST" , 
				url: "MerchantCheck.do", 
				data:"method=batchCheck&st_finish_date="+st_finish_date+"&en_finish_date="+en_finish_date+"&"+$('#listForm').serialize(),
				dataType: "json", 
		        error: function (request, settings) {}, 
		        success: function (data) {
					if (data.code == 1) {
						alert(data.msg);
						$.jBox.tip(data.msg, "success",{timeout:1000});
					} else {
						alert("操作失败，请联系管理员");
					}
					location.reload(true);
		        }
			});
		}
	}
}

	
$("#bgButton").click(function(){
	var st=$("#st_finish_date").val();
	var en=$("#en_finish_date").val();
	if(!st||!en){
		alert("结算区间必须填写");
		return false;
	}
});
//]]></script>
</body>
</html>
