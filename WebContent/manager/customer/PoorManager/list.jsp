<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/commons/styles/icons/icons.css" rel="stylesheet" type="text/css" />
</head>
<body style="height:1500px;">
<div class="mainbox mine">
  <jsp:include page="../_nav.jsp" flush="true"/>
  <html-el:form action="/customer/PoorManager">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="par_id" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellpadding="1" cellspacing="1" class="tableClassSearch">
      <tr>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">
            <tr>
              <td width="6%" nowrap="nowrap">
           		 &nbsp;贫困户名称：
                <html-el:text property="real_name_like" styleId="real_name_like" style="width:140px;" styleClass="webinput"/>
                 &nbsp;性别：
                <html-el:select property="sex" styleId="sex" styleClass="webinput" >
                  <html-el:option value="">全部</html-el:option>
                  <html-el:option value="0">男</html-el:option>
                  <html-el:option value="1">女</html-el:option>
                </html-el:select>
                &nbsp;身份证：
                <html-el:text property="id_card_like" styleId="id_card_like" style="width:140px;" styleClass="webinput"/>
            	 &nbsp;电话号码：
                <html-el:text property="mobile" styleId="mobile" styleClass="webinput"/>
                 &nbsp;添加时间：
	           	   从
	            <html-el:text property="st_add_date" styleClass="webinput"  styleId="st_add_date" size="10" maxlength="10" readonly="true" onclick="WdatePicker();"/>
	   			   至
	            <html-el:text property="en_add_date" styleClass="webinput"  styleId="en_add_date" size="10" maxlength="10" readonly="true" onclick="WdatePicker();"/>
	             <br/> &nbsp;上传所至 步骤：
                <html-el:select property="report_step" styleClass="webinput" >
                  <html-el:option value="">全部</html-el:option>
                  <html-el:option value="0"><span>步骤一(基本信息)</html-el:option>
                  <html-el:option value="1"><span>步骤二(家庭成员情况)</html-el:option>
                  <html-el:option value="2"><span>步骤三(帮扶措施)</span></html-el:option>
                  <html-el:option value="3"><span>步骤四(帮扶责任人)</span></html-el:option>
                  <html-el:option value="4"><span><span>已上报</span></html-el:option>
                </html-el:select>
	             &nbsp;
                 &nbsp;审核状态：
                <html-el:select property="audit_state" styleId="audit_state" styleClass="webinput" >
                  <html-el:option value="">全部</html-el:option>
                  <html-el:option value="-1">审核不通过</html-el:option>
                  <html-el:option value="0">待审核</html-el:option>
                  <html-el:option value="1">审核通过</html-el:option>
                </html-el:select>
                  &nbsp;是否脱贫：
                <html-el:select property="is_tuo_pin" styleId="is_tuo_pin" styleClass="webinput" >
                  <html-el:option value="">全部</html-el:option>
                  <html-el:option value="0">未脱贫</html-el:option>
                  <html-el:option value="1">脱贫</html-el:option>
                </html-el:select>
                &nbsp;&nbsp;
                <button class="bgButtonFontAwesome" type="submit"><i class="fa fa-search"></i>查 询</button></td>
            </tr>
          </table></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="PoorManager.do?method=delete">
    <div style="padding:5px;">
    <logic-el:match name="popedom" value="+1+">
      <button class="bgButtonFontAwesome" type="button" onclick="location.href='PoorManager.do?method=step1&par_id=${af.map.par_id}&mod_id=${af.map.mod_id}';" ><i class="fa fa-plus-square"></i>添加</button>
<!--       <input id="upload" type="button" value="导入" class="bgButton" /> -->
          &nbsp;<input id="downloadQrcode" type="button" value="导出二维码" class="bgButton" />
    </logic-el:match>
    </div>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
      <tr>
        <th width="10%">户主姓名</th>
        <th width="8%">用户编码</th>
        <th width="5%">民族</th>
        <th width="5%">性别</th>
        <th width="5%">家庭人口</th>
        <th width="15%">家庭住址</th>
        <th width="8%">电话</th>
        <th width="8%">添加时间</th>
        <th width="8%">上传所至步骤</th>
        <th width="5%">是否审核</th>
        <th width="5%">是否脱贫</th>
        <th width="8%">用户二维码</th>
        <th width="10%" nowrap="nowrap">操作</th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr>
          <td align="center"><a title="查看" href="PoorManager.do?method=step1&poor_id=${cur.id}&mod_id=${af.map.mod_id}&par_id=${af.map.par_id}">${fn:escapeXml(cur.real_name)}</a></td>
          <c:if test="${not empty cur.map.user_name}">
          	<td align="center">${fn:escapeXml(cur.map.user_name)}<a class="butbase" href="javascript:void(0);"><span class="icon-lock" onclick="initPassword(${cur.map.user_id});">初始化密码</span></a></td>
          </c:if>
          <c:if test="${empty cur.map.user_name}">
          	<td align="center">---</td>
          </c:if>
          <td align="center">${fn:escapeXml(cur.nation)}</td>
          <td align="center">
          	<c:if test="${cur.sex eq 1}">女</c:if>
          	<c:if test="${cur.sex eq 0}">男</c:if>
		  </td>
		  <td align="center"><a title="查看" href="PoorManager.do?method=step2&poor_id=${cur.id}&mod_id=${af.map.mod_id}&par_id=${af.map.par_id}">${fn:escapeXml(cur.family_num)}</a></td>
          <td align="center">${fn:escapeXml(cur.addr)}</td>
          <td align="center">${fn:escapeXml(cur.mobile)}</td>
          <td align="center"><fmt:formatDate value="${cur.add_date}" pattern="yyyy-MM-dd" /></td>
          <td align="center"><c:choose>
              <c:when test="${cur.report_step eq 0}"><span>步骤一</br>(基本信息)</span></c:when>
              <c:when test="${cur.report_step eq 1}"><span>步骤二</br>(家庭成员情况)</span></c:when>
              <c:when test="${cur.report_step eq 2}"><span>步骤三</br>(帮扶措施)</span></c:when>
              <c:when test="${cur.report_step eq 3}"><span>步骤四</br>(帮扶责任人)</span></c:when>
              <c:when test="${cur.report_step eq 4}"><span style="color: green;">已上报</span></c:when>
            </c:choose>
          </td>
          <td align="center"><c:choose>
              <c:when test="${cur.audit_state eq -1}"><span style=" color:#F00;" title="${cur.audit_desc}">审核不通过</span></c:when>
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
            <div style="padding-top: 2px;">
              <logic-el:match name="popedom" value="+2+">
			    <c:if test="${cur.audit_state ne 1}"><a class="butbase" onclick="doNeedMethod(null, 'PoorManager.do','step1', 'poor_id=${cur.id}&mod_id=${af.map.mod_id}&' + $('#bottomPageForm').serialize())"><span class="icon-edit">修改</span></a></c:if>
			    <c:if test="${cur.audit_state eq 1}"><a class="butbase" onclick="doNeedMethod(null, 'PoorManager.do','step1', 'poor_id=${cur.id}&mod_id=${af.map.mod_id}&' + $('#bottomPageForm').serialize())" title="已审核，不能修改"><span class="icon-edit">查看</span></a></c:if>
              </logic-el:match>
              <logic-el:notMatch name="popedom" value="+2+"><a class="butbase but-disabled"><span class="icon-edit">修改</span></a> </logic-el:notMatch>
              <logic-el:match name="popedom" value="+4+">
                <c:if test="${cur.audit_state ne 1}"><a class="butbase" onclick="confirmDelete(null, 'PoorManager.do', 'id=${cur.id}&mod_id=${af.map.mod_id}&' + $('#bottomPageForm').serialize())"><span class="icon-remove">删除</span></a> </c:if>
                <c:if test="${cur.audit_state eq 1}"><a class="butbase but-disabled" title="已审核，不能删除"><span class="icon-remove">删除</span></a> </c:if>
              </logic-el:match>
              <logic-el:notMatch name="popedom" value="+4+"><a class="butbase but-disabled"><span class="icon-remove">删除</span></a> </logic-el:notMatch>
            </div>
          </td>
        </tr>
      </c:forEach>
    </table>
  </form>
  <div class="black">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="PoorManager.do">
      <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" >
        <tr>
          <td height="10" ><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
            pager.addHiddenInputs("method", "list");
            pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
            pager.addHiddenInputs("par_id", "${af.map.par_id}");
            pager.addHiddenInputs("village_name_like", "${fn:escapeXml(af.map.village_name_like)}");
            pager.addHiddenInputs("audit_state", "${fn:escapeXml(af.map.audit_state)}");
            pager.addHiddenInputs("mobile", "${fn:escapeXml(af.map.mobile)}");
            pager.addHiddenInputs("st_add_date", "${af.map.st_add_date}");
            pager.addHiddenInputs("en_add_date", "${fn:escapeXml(af.map.en_add_date)}");
            pager.addHiddenInputs("is_tuo_pin", "${fn:escapeXml(af.map.is_tuo_pin)}");
            pager.addHiddenInputs("report_step", "${fn:escapeXml(af.map.report_step)}");
            pager.addHiddenInputs("id_card_like", "${fn:escapeXml(af.map.id_card_like)}");
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
<script type="text/javascript">
	//导入excel
	$("#upload").click(function(){
		location.href = "${ctx}/manager/customer/PoorManager.do?method=importExcel";
	});
	$("#downloadQrcode").click(function(){
		var submit = function (v, h, f) {
		    if (v == true) {
		    	location.href = "${ctx}/manager/customer/PoorManager.do?method=downloadPoorInfoQrcode&" + $('.searchForm').serialize();
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
</script>
</body>
</html>
