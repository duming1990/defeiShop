<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
</head>
<body>
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/admin/PoorManager" styleClass="searchForm">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="par_id" />
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="p_index" styleId="p_index" />
    <table width="100%" border="0" cellpadding="1" cellspacing="1" class="tableClassSearch">
      <tr>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">
            <tr>
              <td width="6%" nowrap="nowrap">贫困户名称：
                <html-el:text property="real_name_like" styleId="real_name_like" style="width:140px;" styleClass="webinput"/>
                 &nbsp;性别：
                <html-el:select property="sex" styleId="sex" styleClass="webinput" >
                  <html-el:option value="">全部</html-el:option>
                  <html-el:option value="0">男</html-el:option>
                  <html-el:option value="1">女</html-el:option>
                </html-el:select>
                 &nbsp;身份证：
                <html-el:text property="id_card_like" styleId="id_card_like" style="width:140px;" styleClass="webinput"/>
                <div id="city_div" style="margin-top:5px;">
                	所属村站：
	               <html-el:select property="province" styleId="province" styleClass="pi_prov">
		            <html-el:option value="">请选择...</html-el:option>
		          </html-el:select>
		          &nbsp;
		          <html-el:select property="city" styleId="city" styleClass="pi_city">
		            <html-el:option value="">请选择...</html-el:option>
		          </html-el:select>
		          &nbsp;
		          <html-el:select property="country" styleId="country" styleClass="pi_dist">
		            <html-el:option value="">请选择...</html-el:option>
		          </html-el:select>
		           &nbsp; 	
		          <html-el:select property="town" styleId="town" styleClass="pi_town">
		            <html-el:option value="">请选择...</html-el:option>
		          </html-el:select>
		           &nbsp;
		          <html-el:select property="village" styleId="village" styleClass="pi_village">
		            <html-el:option value="">请选择...</html-el:option>
		          </html-el:select>
                </div>
                <div style="margin-top:5px;">
                 	电话号码：
                <html-el:text property="mobile" styleId="mobile" styleClass="webinput"/>
                 &nbsp;添加时间：
	           	   从
	            <html-el:text property="st_add_date" styleClass="webinput"  styleId="st_add_date" size="10" maxlength="10" readonly="true" onclick="WdatePicker();"/>
	   			   至
	            <html-el:text property="en_add_date" styleClass="webinput"  styleId="en_add_date" size="10" maxlength="10" readonly="true" onclick="WdatePicker();"/>
	             &nbsp;
                 &nbsp;审核状态：
                <html-el:select property="audit_state" styleId="audit_state" styleClass="webinput">
                  <html-el:option value="">全部</html-el:option>
                  <html-el:option value="-1">审核不通过</html-el:option>
                  <html-el:option value="0">待审核</html-el:option>
                  <html-el:option value="1">审核通过</html-el:option>
                </html-el:select>
                 &nbsp;是否脱贫：
                <html-el:select property="is_tuo_pin" styleId="is_tuo_pin" styleClass="webinput">
                  <html-el:option value="">全部</html-el:option>
                  <html-el:option value="0">未脱贫</html-el:option>
                  <html-el:option value="1">脱贫</html-el:option>
                </html-el:select>
                 &nbsp;是否绑定银行卡：
                <html-el:select property="is_band_bank" styleId="is_band_bank" styleClass="webinput">
                  <html-el:option value="">全部</html-el:option>
                  <html-el:option value="0">未绑定</html-el:option>
                  <html-el:option value="1">已绑定</html-el:option>
                </html-el:select>
                &nbsp;&nbsp;
                <button class="bgButtonFontAwesome" type="submit"><i class="fa fa-search"></i>查 询</button>
                &nbsp;<input id="download" type="button" value="导出" class="bgButton" />
                       &nbsp;<input id="downloadQrcode" type="button" value="导出二维码" class="bgButton" />
                 </div>
                </td>
            </tr>
          </table></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="PoorManager.do?method=cancelPoors">
  <div style="padding:5px;">
  	<input type="button" id="cancelPoors" name="cancelPoors" class="bgButton" value="选中脱贫" 
		onclick="this.form.action += '&' + $('#bottomPageForm').serialize();cancelPoorsNew(this.form);" ></input>
    <logic-el:match name="popedom" value="+1+">
      <input id="upload" type="button" value="导入" class="bgButton" />
    </logic-el:match>
    </div>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
      	<th width="5%"><input name="chkAll" type="checkbox" id="chkAll" onclick="checkAll(this);" ></input></th>
        <th width="10%">真实姓名</th>
        <th width="8%">用户编码</th>
        <th width="5%">性别</th>
        <th width="5%">民族</th>
<!--         <th width="5%">家庭人口</th> -->
        <th width="15%">家庭住址</th>
        <th width="15%">所属村站</th>
        <th width="8%">电话</th>
        <th width="8%">添加时间</th>
        <th width="8%">是否审核</th>
        <th width="8%">是否脱贫</th>
        <th width="8%">用户二维码</th>
        <th width="10%" nowrap="nowrap">操作</th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr>
          <td align="center">
          <c:if test="${cur.is_tuo_pin eq 0}"><input type="checkbox" id="pks_${cur.id}" name="pks" value="${cur.id}"></input></c:if>
          <c:if test="${cur.is_tuo_pin eq 1}"><input type="checkbox" id="pks_${cur.id}" name="pks" value="${cur.id}" disabled="disabled"></input></c:if>
          </td>
          <td align="center"><a title="查看" href="PoorManager.do?method=step1&amp;poor_id=${cur.id}&mod_id=${af.map.mod_id}&par_id=${af.map.par_id}">${fn:escapeXml(cur.real_name)}</a></td>
          <c:if test="${not empty cur.user_name}">
          	<td align="center">${fn:escapeXml(cur.user_name)}<a class="butbase" href="javascript:void(0);"><span class="icon-lock" onclick="initPassword(${cur.map.user_id});">初始化密码</span></a></td>
          </c:if>
          <c:if test="${empty cur.user_name}">
          	<td align="center">---</td>
          </c:if>
          <td align="center">
          	<c:if test="${cur.sex eq 1}">女</c:if>
          	<c:if test="${cur.sex eq 0}">男</c:if>
		  </td>
          <td align="center">${fn:escapeXml(cur.nation)}</td>
<%--           <td align="center"><a title="查看" href="PoorManager.do?method=step2&poor_id=${cur.id}&mod_id=${af.map.mod_id}&par_id=${af.map.par_id}">${fn:escapeXml(cur.family_num)}</a></td> --%>
          <td align="center">${fn:escapeXml(cur.addr)}</td>
          <td align="center">${fn:escapeXml(cur.map.villageInfo.village_name)}</td>
          <td align="center">${fn:escapeXml(cur.mobile)}
           <c:if test="${cur.audit_state eq 1}">
          </br>
          <a class="butbase" onclick="edit_mobile(${cur.id });"><span class="icon-ok">绑定手机号</span></a>
          
          <c:if test="${not empty cur.map.userInfo.bank_name}">
           <a class="butbase" onclick="edit_bank_card(${cur.id});"><span class="icon-ok">绑定银行卡</span></a>
          </c:if>
          <c:if test="${empty cur.map.userInfo.bank_name}">
           <a class="butbase" onclick="edit_bank_card(${cur.id});"><span class="icon-remove">绑定银行卡</span></a>
          </c:if>
          
          </c:if>
          </td>
          <td align="center"><fmt:formatDate value="${cur.add_date}" pattern="yyyy-MM-dd" /></td>
          <td align="center"><c:choose>
              <c:when test="${cur.audit_state eq -1}"><span style=" color:#F00;">审核不通过</span></c:when>
              <c:when test="${cur.audit_state eq 0}"><span>待审核</span></c:when>
              <c:when test="${cur.audit_state eq 1}"><span style=" color:#060;">审核通过</span></c:when>
            </c:choose>
          </td>
          <td align="center"><c:choose>
              <c:when test="${cur.is_tuo_pin eq 0}"><span style=" color:red;">未脱贫</span></c:when>
              <c:when test="${cur.is_tuo_pin eq 1}"><span style=" color:#060;">已脱贫</span></c:when>
            </c:choose>
          </td>
          <td align="center">
          <c:if test="${not empty cur.poor_qrcode}">
          <img src="${ctx}/${cur.poor_qrcode}" width="100%"/>
          </c:if>
          </td>
          <td align="center" nowrap="nowrap">
            <logic-el:match name="popedom" value="+8+">
                <a class="butbase" onclick="doNeedMethod(null, 'PoorManager.do', 'step1','poor_id=${cur.id}&mod_id=${af.map.mod_id}&'+$('#bottomPageForm').serialize())"><span class="icon-ok">审核</span></a>
            </logic-el:match>
            <logic-el:match name="popedom" value="+8+">
                <c:if test="${cur.audit_state eq 1}">
				<!--已脱贫 -->
                <c:if test="${cur.is_tuo_pin eq 1}">
                <a class="butbase but-disabled"><span class="icon-cancel">取消贫困户</span></a>
                <a class="butbase but-disabled"><span class="icon-cancel">脱贫</span></a>
                </c:if>
                <!--未脱贫 -->
                <c:if test="${cur.is_tuo_pin eq 0}">
                <a class="butbase" onclick="doNeedMethod(null, 'PoorManager.do', 'cancelPoor','id=${cur.id}&mod_id=${af.map.mod_id}&'+$('#bottomPageForm').serialize())"><span class="icon-cancel">取消贫困户</span></a>
                <a class="butbase" onclick="doNeedMethod(null, 'PoorManager.do', 'cancelPoors','id=${cur.id}&mod_id=${af.map.mod_id}&'+$('#bottomPageForm').serialize())"><span class="icon-cancel">脱贫</span></a>
                </c:if>
                </c:if>
                <c:if test="${cur.audit_state ne 1}"><a class="butbase but-disabled"><span class="icon-cancel">取消贫困户</span></a> </c:if>
            </logic-el:match>
          </td>
        </tr>
      </c:forEach>
    </table>
  </form>
  <div class="pageClass">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="PoorManager.do">
      <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" >
        <tr>
          <td height="10" ><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
            pager.addHiddenInputs("method", "list");
            pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
            pager.addHiddenInputs("par_id", "${af.map.par_id}");
            pager.addHiddenInputs("real_name_like", "${fn:escapeXml(af.map.real_name_like)}");
            pager.addHiddenInputs("audit_state", "${fn:escapeXml(af.map.audit_state)}");
            pager.addHiddenInputs("province", "${af.map.province}");
            pager.addHiddenInputs("city", "${fn:escapeXml(af.map.city)}");
            pager.addHiddenInputs("country", "${fn:escapeXml(af.map.country)}");
            pager.addHiddenInputs("town", "${af.map.town}");
            pager.addHiddenInputs("village", "${fn:escapeXml(af.map.village)}");
            pager.addHiddenInputs("mobile", "${fn:escapeXml(af.map.mobile)}");
            pager.addHiddenInputs("st_add_date", "${af.map.st_add_date}");
            pager.addHiddenInputs("en_add_date", "${fn:escapeXml(af.map.en_add_date)}");
            pager.addHiddenInputs("is_tuo_pin", "${fn:escapeXml(af.map.is_tuo_pin)}");
            pager.addHiddenInputs("id_card_like", "${fn:escapeXml(af.map.id_card_like)}");
            pager.addHiddenInputs("is_band_bank", "${fn:escapeXml(af.map.is_band_bank)}");
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
<script type="text/javascript" src="${ctx}/scripts/tip/jquery.quicktip.min.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.cs.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/pager.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script> 
<script type="text/javascript">
var f = document.forms[0];
$(document).ready(function(){
	$("#province").attr({"subElement": "city", "defaultText": "请选择...", "defaultValue": "", "selectedValue": "${af.map.province}","datatype": "Require", "msg": "请选择省份"});
	$("#city").attr({"subElement": "country", "defaultText": "请选择...", "defaultValue": "", "selectedValue": "${af.map.city}","datatype": "Require", "msg": "请选择市"});
	$("#country").attr({"subElement": "town", "defaultText": "请选择...", "defaultValue": "", "selectedValue": "${af.map.country}","datatype": "Require", "msg": "请选择县"});
	$("#town").attr({"subElement": "village", "defaultText": "请选择...", "defaultValue": "", "selectedValue": "${af.map.town}","datatype": "Require", "msg": "请选择乡/镇"});
	$("#village" ).attr({"defaultText": "请选择...", "defaultValue": "", "selectedValue": "${af.map.village}","datatype": "Require", "msg": "请选择村"});
	$("#province").cs("${ctx}/BaseCsAjax.do?method=getBaseProvinceList", "p_index", "0", false);
	
	$("#but_submit").click(function(){
			f.submit();
	});
});

function edit_mobile(id){
	var url = "${ctx}/manager/admin/PoorManager.do?method=getPoorInfo&type=mobile&id=" + id;
	$.dialog({
		title:  "",
		width:  1000,
		height: 700,
		padding: 0,
		max: false,
        min: false,
        fixed: true,
        lock: true,
        zIndex:100,
		content:"url:"+ encodeURI(url)
	});
}
function edit_bank_card(id){
	var url = "${ctx}/manager/admin/PoorManager.do?method=getPoorInfo&id=" + id;
	$.dialog({
		title:  "",
		width:  1000,
		height: 700,
		padding: 0,
		max: false,
        min: false,
        fixed: true,
        lock: true,
        zIndex:100,
		content:"url:"+ encodeURI(url)
	});
}

//导入excel
$("#upload").click(function(){
	location.href = "${ctx}/manager/admin/PoorManager.do?method=importExcel";
});

$("#download").click(function(){
	
	var submit = function (v, h, f) {
	    if (v == true) {
	    	location.href = "${ctx}/manager/admin/PoorManager.do?method=toExcel&mod_id=${af.map.mod_id}&" + $('.searchForm').serialize();
	    } else {
	    	location.href = "${ctx}/manager/admin/PoorManager.do?method=toExcel&code=GBK&mod_id=${af.map.mod_id}&" + $('.searchForm').serialize();
	    }
	    return true;
	};
	var tip = "确认导出EXCEL格式数据？如果UTF-8编码格式乱码，请选择GBK编码格式下载！";
	$.jBox.confirm(tip, "系统提示", submit, { buttons: { '下载(UTF-8编码)': true, '下载(GBK编码)': false} });
});


$("#downloadQrcode").click(function(){
	var submit = function (v, h, f) {
	    if (v == true) {
	    	location.href = "${ctx}/manager/admin/PoorManager.do?method=downloadPoorInfoQrcode&" + $('.searchForm').serialize();
	    }
	    return true;
	};
	var tip = "确定导出二维码图片吗？";
	$.jBox.confirm(tip, "系统提示", submit, { buttons: { '确定': true, '取消': false} });
});
	function initPassword(id) {
		if (confirm("确认要初始化密码吗？")) {
			var password = prompt("请输入您的新密码,如果不输入,默认初始密码为“${init_pwd}”。","");
			if (null != password) {
				if (password.length == 0) {
					password = "${init_pwd}";
				}
				$.post("CsAjax.do?method=initPassword",{uid : id, password : password},function(data){
					if(null != data.result){alert(data.msg);} else {alert("初始化密码失败");}
				});
			}
		}
		return false;
	}
function cancelPoorsNew(form){
	//批量脱贫
	var checkedCount = 0;
	if(form.pks.length > 0) {
		for (var i = 0; i < form.pks.length; i++) {
			if (form.pks[i].checked == true) {
				checkedCount = 1;
				break;
			}
		}
	}
	
	if (checkedCount == 0) {
		alert("请至少选择一个脱贫项！");
	} else {
		if(confirm("确定所有选中的项要脱贫吗？")) {
			form.method.value = "cancelPoors";
			form.submit();
		}
	}
}



</script>
</body>
</html>
