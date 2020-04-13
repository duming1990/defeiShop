<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
</head>
<c:set var="type_name" value="商家"/>
<body>
<div style="width: 99%" class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/admin/MoneyUserQuery" styleClass="searchForm">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tableClassSearch">
      <tr>
        <td>申请人用户名：
          <html-el:text property="real_name_like" maxlength="40" style="width:150px;" styleClass="webinput" />
          &nbsp;手机号码：
          <html-el:text property="mobile_like" maxlength="40" style="width:150px;" styleClass="webinput" />
          &nbsp;审核状态：
          <html-el:select property="info_state" styleClass="webinput" >
            <html-el:option value="">全部</html-el:option>
            <html-el:option value="0">待审核</html-el:option>
            <html-el:option value="1">审核通过</html-el:option>
            <html-el:option value="-1">审核驳回</html-el:option>
            <html-el:option value="2">已付款</html-el:option>
            <html-el:option value="-2">已退款</html-el:option>
            
          </html-el:select>
          &nbsp;
          <div style="margin-top: 5px;">申请时间：
            从&nbsp;
            <html-el:text property="add_begin_date" styleId="add_begin_date" size="9" maxlength="9" readonly="true" onclick="WdatePicker();" />
            至&nbsp;
            <html-el:text property="add_end_date" styleId="add_end_date" size="9" maxlength="9" readonly="true" onclick="WdatePicker();" />
            &nbsp;&nbsp;
            审核时间：
            从&nbsp;
            <html-el:text property="audit_begin_date" styleId="audit_begin_date" size="9" maxlength="9" readonly="true" onclick="WdatePicker();" />
            至&nbsp;
            <html-el:text property="audit_end_date" styleId="audit_end_date" size="9" maxlength="9" readonly="true" onclick="WdatePicker();" />
            &nbsp;
            <html-el:submit value="查 询" styleClass="bgButton"  />
            <logic-el:match name="popedom" value="+16+">&nbsp;
              <input id="download" type="button" value="导出" class="bgButton" />
            </logic-el:match>
          </div></td>
      </tr>
    </table>
  </html-el:form>
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClassSearch">
    <tr>
      <th width="15%" ><span style="color: #F00;"></span>提现申请总金额： </th>
      <td style="text-align:left;" width="85%">${uma.cash_count}</td>
    </tr>
    <tr>
      <th width="15%" ><span style="color: #F00;"></span>实际付款总金额： </th>
      <td style="text-align:left;">${uma.cash_pay}</td>
    </tr>
  </table>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="MoneyUserQuery.do?method=delete">
    <input type="hidden" name="mod_id" value="${af.map.mod_id}" />
    <div style="padding-bottom:5px;">
      <logic-el:match name="popedom" value="+8+">
        <input type="button" name="audit" id="audit" class="bgButton" value="批量审核" onclick="confirmAuditAll(this.form);">
        </input>
      </logic-el:match>
      <logic-el:match name="popedom" value="+17+">
        <input type="button" name="pay" id="pay" class="bgButton" value="批量付款" onclick="confirmPayAll(this.form);">
        </input>
      </logic-el:match>
    </div>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th width="5%"> <input name="chkAll" type="checkbox" id="chkAll" value="-1" onclick="checkAll(this);"/>
        </th>
        <th>申请人信息</th>
        <th width="5%">提现前金额</th>
        <th width="5%">提现金额</th>
        <th width="8%">付款金额</th>
        <th width="5%">已提现金额</th>
        <th width="5%">会员类型</th>
        <th width="8%">申请说明</th>
        <th width="6%">申请时间</th>
        <th width="5%">审核状态</th>
        <th width="5%">审核时间</th>
        <c:if test="${fn:contains(popedom, '+8+') or fn:contains(popedom, '+17+')}" var="isContains">
          <th width="8%" nowrap="nowrap">操作</th>
        </c:if>
      </tr>
      <c:forEach var="cur" items="${userMoneyApplyList}" varStatus="vs">
        <tr>
          <td align="center"><c:set var="disabled" value=""/>
            <c:if test="${(cur.info_state eq -1) or (cur.info_state eq 2)}">
              <c:set var="disabled" value="disabled='disabled'"/>
            </c:if>
            <input name="pks" type="checkbox" ${disabled} id="pks" value="${cur.id}" /></td>
          <td align="center">用户名：${fn:escapeXml(cur.map.apply_user_name)}<br/>
            电话号码：${fn:escapeXml(cur.mobile)}<br/>
            开户银行：${cur.bank_name}<br/>
            开户账号：${fn:escapeXml(cur.bank_account)}<br/>
            开户名：${fn:escapeXml(cur.bank_account_name)} </td>
          <td align="center">${fn:escapeXml(cur.cash_count_before)}元</td>
          <td align="center">${fn:escapeXml(cur.cash_count)}元</td>
          <td align="center">${fn:escapeXml(cur.cash_pay)}元
        	<br/>
          <span align="center" style="color: red">手续费：${fn:escapeXml(cur.cash_rate)}元</span>
          </td>
          <td align="center">
          <fmt:formatNumber pattern="#0.00" value="${cur.map.has_tixian_money}" />元</td>
          <td align="center">${fn:escapeXml(cur.map.user_type_name)}</td>
          <td align="center">${fn:escapeXml(cur.add_memo)}</td>
          <td align="center"><fmt:formatDate value="${cur.add_date}" pattern="yyyy-MM-dd HH:mm" /></td>
          <td align="center"><c:choose>
              <c:when test="${cur.info_state eq 0}">待审核</c:when>
              <c:when test="${cur.info_state eq 1}"><span class="tip-primary">已审核(待付款)</span></c:when>
              <c:when test="${cur.info_state eq -1}"><span class="tip-danger">审核不通过(已退款)</span></c:when>
              <c:when test="${cur.info_state eq 2}"><span class="tip-success">已付款</span></c:when>
              <c:when test="${cur.info_state eq -2}"><span class="tip-danger">已退款</span></c:when>
              <c:otherwise>未知</c:otherwise>
            </c:choose>
            <c:if test="${not empty cur.audit_memo}">
              <div class="tip-primary qtip" title="${cur.audit_memo}">查看审核说明</div>
            </c:if>
            <c:if test="${not empty cur.tuikuan_memo}">
              <div class="tip-primary qtip" title="${cur.tuikuan_memo}">查看退款说明</div>
            </c:if>
          </td>
          <td align="center"><fmt:formatDate value="${cur.audit_date}" pattern="yyyy-MM-dd" /></td>
          <c:if test="${isContains}" >
            <td align="center"><c:if test="${cur.info_state eq 0}">
                <logic-el:match name="popedom" value="+8+"> <a class="butbase" onclick="doNeedMethod(null, 'MoneyUserQuery.do', 'audit','mod_id=${af.map.mod_id}&id=${cur.id}&'+$('#bottomPageForm').serialize())"><span class="icon-ok">审核</span></a> </logic-el:match>
                <logic-el:match name="popedom" value="+17+"> <a class="butbase but-disabled" title="未审核通过，不能付款"><span style="cursor: pointer;" class="icon-undo">付款</span></a> </logic-el:match>
              </c:if>
              <c:if test="${cur.info_state eq 1}">
                <logic-el:match name="popedom" value="+8+"> <a class="butbase but-disabled"><span class="icon-ok">已审核</span></a> </logic-el:match>
                <logic-el:match name="popedom" value="+17+"> <a class="butbase" onclick="confirmAudit(${cur.id})"><span style="cursor: pointer;" class="icon-undo">付款</span></a></logic-el:match>
                <logic-el:match name="popedom" value="+18+">
                 <a class="butbase" onclick="confirmTk(${cur.id})"><span style="cursor: pointer;" class="icon-undo">退&nbsp;&nbsp;&nbsp; 款</span></a>
                </logic-el:match>
              </c:if>

              <c:if test="${cur.info_state eq 2}">
                <logic-el:match name="popedom" value="+8+"> <a class="butbase but-disabled"><span class="icon-ok">已审核</span></a> </logic-el:match>
                <logic-el:match name="popedom" value="+17+"> <a class="butbase but-disabled"><span style="cursor: pointer;" class="icon-undo" title="${cur.remark}">已付款</span></a></logic-el:match>
                <logic-el:match name="popedom" value="+18+">
                 <a class="butbase" onclick="confirmTk(${cur.id})"><span style="cursor: pointer;" class="icon-undo">退&nbsp;&nbsp;&nbsp; 款</span></a>
                </logic-el:match>
              </c:if>

            </td>
          </c:if>
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
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
      </c:forEach>
    </table>
  </form>
  <div class="pageClass">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="MoneyUserQuery.do">
      <table width="98%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
            pager.addHiddenInputs("method", "list");
            pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
			pager.addHiddenInputs("send_time_like", "${af.map.send_time_like}");
			pager.addHiddenInputs("real_name_like", "${af.map.real_name_like}");
			pager.addHiddenInputs("mobile_like", "${af.map.mobile_like}");
			pager.addHiddenInputs("info_state", "${af.map.info_state}");
			pager.addHiddenInputs("add_begin_date", "${af.map.add_begin_date}");
			pager.addHiddenInputs("add_end_date", "${af.map.add_end_date}");
			pager.addHiddenInputs("audit_begin_date", "${af.map.audit_begin_date}");
			pager.addHiddenInputs("audit_end_date", "${af.map.audit_end_date}");
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
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/scripts/tip/jquery.quicktip.min.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	//下载excel
		$(".qtip").quicktip();
$("#download").click(function(){
		
		var submit = function (v, h, f) {
		    if (v == true) {
		    	location.href = "${ctx}/manager/admin/MoneyUserQuery.do?method=toExcel&mod_id=${af.map.mod_id}&" + $('.searchForm').serialize();
		    } else {
		    	location.href = "${ctx}/manager/admin/MoneyUserQuery.do?method=toExcel&code=GBK&mod_id=${af.map.mod_id}&" + $('.searchForm').serialize();
		    }
		    return true;
		};
		var tip = "确认导出EXCEL格式数据？如果UTF-8编码格式乱码，请选择GBK编码格式下载！";
		$.jBox.confirm(tip, "系统提示", submit, { buttons: { '下载(UTF-8编码)': true, '下载(GBK编码)': false} });
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
			     $.post("MoneyUserQuery.do?method=auditAll&info_state="+info_state + "&audit_memo=" + audit_memo + "&" + $(form).serialize() ,{},function(data){
			     //$.jBox.tip(data, "success");
			     location.reload();
			     });
			     }, 1000);
			};
			myAuditConfirm(html,submit);
		 }
}

function confirmPayAll(form){
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
		$.jBox.alert("请至少选择一个付款项！", '提示');
	} else {
		var submit = function (v, h, f) {
		    if (v == true) {
		     $.jBox.tip("执行中...", 'loading');
		     window.setTimeout(function () { 
		     $.post("MoneyUserQuery.do?method=payAll"+"&" + $(form).serialize(),function(data){
		     //$.jBox.tip(data, "success");
		     location.reload();
		     });
		     }, 1000);
		    } 
		    return true;
		};
		myPayConfirm("您将批量付款，你确定要执行该操作吗？",submit);
	}
}

function myAuditConfirm(tip, submit){ 
	$.jBox.confirm(tip, "${app_name}", submit, { buttons: { '审核通过': true, '审核驳回': false} });
}

function myPayConfirm(tip, submit){ 
	$.jBox.confirm(tip, "${app_name}", submit, { buttons: { '确定': true, '取消': false} });
}

function confirmAudit(id){
		$.dialog({
			title:  "付款",
			width:  500,
			height: 124,
	        lock:true ,
			content:"url:${ctx}/manager/admin/MoneyUserQuery.do?method=payForm&id=" +id + "&" + $('#bottomPageForm').serialize()
		});
}
function refreshPage(){
	window.location.reload();
}
function confirmTk(id){
	var submit = function (v, h, f) {
	    if (v == true) {
	    	 var tuikuan_memo = h.find("#tuikuan_memo").val();
	    	 $.jBox.tip("执行中...", 'loading');
		     window.setTimeout(function () { 
		    	 location.href = "${ctx}/manager/admin/MoneyUserQuery.do?method=confirmTk&id=" + id+"&mod_id=${af.map.mod_id}" + "&tuikuan_memo=" + tuikuan_memo;
		     }, 1000);
	    }
	    return true;
	};
	var tip = "你是出现线下操作失败，所以才要进行退款操作？";
	tip += "<div style='padding:10px;'>退款说明：<input type='text' id='tuikuan_memo' name='tuikuan_memo' /></div>";
	$.jBox.confirm(tip, "系统提示", submit, { buttons: { '确定退款': true, '取消退款': false} });
}
//]]>
</script>
<jsp:include page="../public_page.jsp" flush="true" />
</body>
</html>
