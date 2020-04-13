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
  <html-el:form action="/admin/MerchantCheckTwo" styleClass="searchForm">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="entp_type" />
    <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tableClassSearch">
      <tr>
        <td><div> 商家名：
            <html-el:text property="entp_name" maxlength="40" style="width:150px;" styleClass="webinput" />
          
          &nbsp;添加时间：
            从
            <html-el:text property="st_add_date" styleClass="webinput"  styleId="st_add_date" size="10" maxlength="10" readonly="true" onclick="WdatePicker();"/>
            至
            <html-el:text property="en_add_date" styleClass="webinput"  styleId="en_add_date" size="10" maxlength="10" readonly="true" onclick="WdatePicker();"/>
            &nbsp;审核状态：
            <html-el:select property="is_check" styleId="is_check">
           		<html-el:option value="">全部</html-el:option>
            	<html-el:option value="0">待对账</html-el:option>
	            <html-el:option value="1">对账成功</html-el:option>
	            <html-el:option value="-1">对账失败</html-el:option>
	            <html-el:option value="15">付款成功</html-el:option>
	            <html-el:option value="20">退款成功</html-el:option>
            </html-el:select>
             &nbsp;类型：
            <html-el:select property="order_type" styleId="order_type" >
              <html-el:option value="">全部</html-el:option>
              <html-el:option value="0">线上</html-el:option>
              <html-el:option value="90">线下</html-el:option>
            </html-el:select>
            &nbsp;<html-el:submit value="查 询" styleClass="bgButton"  />
            
            <input id="download" type="button" value="导出" class="bgButton" />
          </div></td>
      </tr>
    </table>
  </html-el:form>
  <form>
  <div style="padding-bottom:5px;">
      <input type="button" name="audit" id="audit" class="bgButton" value="批量审核" onclick="confirmAuditAll(this.form);">
        </input>
    </div>
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
	<tr>
		<th width="5%"> 
			<input name="chkAll" type="checkbox" id="chkAll" value="-1" onclick="checkAll(this);"/>
        </th>
        <th width="5%">结算类型</th>
        <th width="5%">订单金额</th>
        <th width="8%">商家货款</th>
        <th width="6%">结算金额</th>
        <th width="15%">企业名称</th>
        <th width="7%">开始时间</th>
        <th width="7%">结束时间</th>
        <th width="7%">添加时间</th>
        <th width="5%">审核状态</th>
        <th width="7%">审核时间</th>
        <th width="7%">审核意见</th>
        <th width="7%">付款备注</th>
        <th width="8%">操作</th>
      </tr>
     <c:forEach var="cur" items="${entityList}">
     	<tr>
     	<td width="5%" nowrap="nowrap" align="center">
	       <c:if test="${cur.is_check ne 0}">
				<input name="pks" type="checkbox" id="pks_${cur.map.id}" value="${cur.id}"  disabled="disabled"/>
			</c:if>
			<c:if test="${cur.is_check eq 0}">
 				<input name="pks" type="checkbox" id="pks_${cur.map.id}" value="${cur.id}"/>			
 			</c:if>
          </td>
          <td align="center">
     			<c:if test="${cur.order_type eq 0}"><span style="color :red;">线上</span></c:if>
     			<c:if test="${cur.order_type ne 0}"> <span style="color :green;">线下</span> </c:if>
     		</td>
          	<td align="center">${fn:escapeXml(cur.sum_order_money)}</td>
     		<td align="center">${fn:escapeXml(cur.sum_money)}<br/> <span align="center" style="color: red">手续费：${fn:escapeXml(cur.cash_rate)}元</span></td>
     		<td align="center">${fn:escapeXml(cur.cash_pay)}</td>
     		<td align="left">${cur.entp_name}</td>
     		<fmt:formatDate var="add_check_date" value="${cur.add_check_date}" pattern ="yyyy-MM-dd HH:mm:ss"/>
     		<fmt:formatDate var="end_check_date" value="${cur.end_check_date}" pattern ="yyyy-MM-dd HH:mm:ss"/>
     		<fmt:formatDate var="add_date" value="${cur.add_date}" pattern ="yyyy-MM-dd HH:mm:ss"/>
     		<fmt:formatDate var="confirm_date" value="${cur.confirm_date}" pattern ="yyyy-MM-dd HH:mm:ss"/>
     		<fmt:formatDate var="update_date" value="${cur.update_date}" pattern ="yyyy-MM-dd HH:mm:ss"/>
     		<td align="center">${fn:escapeXml(add_check_date)}</td>
     		<td align="center">${fn:escapeXml(end_check_date)}</td>
     		<td align="center">${fn:escapeXml(add_date)}</td>
     		<c:if test="${cur.is_check eq 0}">
     			<td align="center">待结算</td>
     		</c:if>
     		<c:if test="${cur.is_check eq -1}">
     			<td align="center" ><span style="color :red;">结算失败</span></td>
     		</c:if>
     		<c:if test="${cur.is_check eq 1}">
     			<td align="center"><span style="color :green;">结算成功</span></td>
     		</c:if>
            <c:if test="${cur.is_check eq 15}">
                <td align="center"><span style="color :green;">付款成功</span></td>
            </c:if>
            <c:if test="${cur.is_check eq 20}">
                <td align="center"><span style="color :green;">退款成功</span></td>
            </c:if>
     		<td align="center">${fn:escapeXml(confirm_date)}</td>
     		<td align="center">${fn:escapeXml(cur.confirm_desc)}</td>
     		<td align="center">${fn:escapeXml(cur.pay_remarks)}</td>
     		<td align="center">
     			<a href="${ctx}/manager/admin/MerchantCheckTwo.do?method=listDetails&mod_id=${af.map.mod_id}&id=${cur.id}" class="butbase beautybg" style="color: white;"><span class="icon-ok">查看详情</span></a>
     			<c:if test="${cur.is_check ne 0}">
					<a class="butbase but-disabled" title="已审核，不能再审核"><span class="icon-ok">审核</span></a>
				</c:if>
				<c:if test="${cur.is_check eq 0}">
     				<a class="butbase"><span onclick="doNeedMethod(null, 'MerchantCheckTwo.do', 'lastCheck','id=${cur.id}')" class="icon-ok">审核</span></a>
				</c:if>

                <c:if test="${cur.is_check eq 1}">
                    <a class="butbase" onclick="confirmAudit(${cur.id})"><span style="cursor: pointer;" class="icon-undo">付款</span></a>
                    <a class="butbase but-disabled"><span style="cursor: pointer;" class="icon-undo">退款</span></a>
                </c:if>

                <c:if test="${cur.is_check eq 15}">
                    <a class="butbase but-disabled"><span style="cursor: pointer;" class="icon-undo">付款</span></a>
                    <a class="butbase" onclick="confirmTk(${cur.id})"><span style="cursor: pointer;" class="icon-undo">退款</span></a>
                </c:if>

                <c:if test="${cur.is_check eq 20}">
                    <a class="butbase but-disabled"><span style="cursor: pointer;" class="icon-undo">付款</span></a>
                    <a class="butbase but-disabled"><span style="cursor: pointer;" class="icon-undo">退款</span></a>
                </c:if>
     		</td>
     	</tr>
     </c:forEach>
</table>
  </form>
 <div class="pageClass">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="MerchantCheckTwo.do">
      <table width="98%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
            pager.addHiddenInputs("method", "list");
           	pager.addHiddenInputs("entp_name", "${af.map.entp_name}");
			pager.addHiddenInputs("st_add_date", "${af.map.st_add_date}");
			pager.addHiddenInputs("en_add_date", "${af.map.en_add_date}");
			pager.addHiddenInputs("confirm_state", "${af.map.confirm_state}");
			pager.addHiddenInputs("is_check", "${af.map.is_check}");
			pager.addHiddenInputs("order_type", "${af.map.order_type}");
            document.write(pager.toString());
            </script></td>
        </tr>
      </table>
    </form>
  </div>
 </div>
 <script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/area.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/citySelect.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/scripts/colorbox/jquery.colorbox.min.js"></script>
<script type="text/javascript">
var f = document.forms[0];


$(document).ready(function(){
	
	$("a.beautybg").colorbox({width:"90%", height:"80%", iframe:true});
	
	$("#is_check").change(function(){
		f.submit();
	});
	$("#order_type").change(function(){
		f.submit();
	});
	
});



function confirmAuditAll(form){
	var checkedCount = 0;
	if (!form.pks) {
		return;
	}
	if(!form.pks.length) {
		if (form.pks.checked == true) {
			checkedCount = 1;
		}
	}
	for (var i = 0; i < form.pks.length; i++) {
		if (form.pks[i].checked == true) {
			checkedCount++;
		}
	}
	if (checkedCount == 0) {
		$.jBox.alert("请至少选择一个审核项！", '提示');
	} else {
			var html = "<div style='padding:10px;'>审核说明：<input type='text' id='audit_memo' name='audit_memo' /></div>";
			var submit = function (v, h, f) {
				//alert($(form).serialize())
				if (f.audit_memo == '') {
		        $.jBox.tip("请输入审核说明。", 'error', { focusId: "audit_memo" });
		        return false;
		    }
				var audit_memo = h.find("#audit_memo").val();
				var info_state = 1;
			    if (v == true) {
			    	info_state = 1;
			    } else {
			    	info_state = -1;
			    }
			   $.jBox.tip("执行中...", 'loading');
			     window.setTimeout(function () { 
			     $.post("MerchantCheckTwo.do?method=saveAll&info_state="+info_state + "&audit_memo=" + audit_memo + "&" + $(form).serialize() ,{},function(data){
			     $.jBox.tip(data.msg, "success");
				     window.setTimeout(function () { 
				     	location.reload();
				     }, 2000);
			     });
			     }, 1000);
			};
			myAuditConfirm(html,submit);
		 }
}
function myAuditConfirm(tip, submit){ 
	$.jBox.confirm(tip, "${app_name}", submit, { buttons: { '审核通过': true, '审核驳回': false} });
}

function myPayConfirm(tip, submit){ 
	$.jBox.confirm(tip, "${app_name}", submit, { buttons: { '确定': true, '取消': false} });
}
function confirmAudit(id){
	console.info(123);
	$.dialog({
		title:  "付款",
		width:  500,
		height: 124,
        lock:true ,
		content:"url:${ctx}/manager/admin/MerchantCheckTwo.do?method=payForm&id=" + id+ "&" + $(".searchForm").serialize()
	});
}
function confirmTk(id){
    var submit = function (v, h, f) {
        if (v == true) {
            var tuikuan_memo = h.find("#tuikuan_memo").val();
            $.jBox.tip("执行中...", 'loading');
            window.setTimeout(function () {
                location.href = "${ctx}/manager/admin/MerchantCheckTwo.do?method=confirmTk&id=" + id+"&is_check=20" + "&refundRemarks=" + tuikuan_memo;
            }, 1000);
        }
        return true;
    };
    var tip = "你是出现线下操作失败，所以才要进行退款操作？";
    tip += "<div style='padding:10px;'>退款说明：<input type='text' id='tuikuan_memo' name='tuikuan_memo' /></div>";
    $.jBox.confirm(tip, "系统提示", submit, { buttons: { '确定退款': true, '取消退款': false} });
}

$("#download").click(function(){
	
	var submit = function (v, h, f) {
	    if (v == true) {
	    	location.href = "${ctx}/manager/admin/MerchantCheckTwo.do?method=toExcel&mod_id=${af.map.mod_id}&" + $('.searchForm').serialize();
	    } else {
	    	location.href = "${ctx}/manager/admin/MerchantCheckTwo.do?method=toExcel&code=GBK&mod_id=${af.map.mod_id}&" + $('.searchForm').serialize();
	    }
	    return true;
	};
	var tip = "确认导出EXCEL格式数据？如果UTF-8编码格式乱码，请选择GBK编码格式下载！";
	$.jBox.confirm(tip, "系统提示", submit, { buttons: { '下载(UTF-8编码)': true, '下载(GBK编码)': false} });
});


</script>
</body>
</html>
