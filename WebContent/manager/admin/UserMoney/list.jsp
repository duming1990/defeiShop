<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
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
  <html-el:form action="/admin/UserMoney">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="id" value="${af.map.id}" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClassSearch">
      <tr>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">
            <tr>
              <td width="5%" nowrap="nowrap">币种：
                <html-el:select property="bi_type">
                <c:if test="${is_open_bi_dian eq true}">
                  <html-el:option value="100">余额</html-el:option>
                </c:if>
                  <html-el:option value="300">货款</html-el:option>
                  <html-el:option value="500">扶贫金</html-el:option>
<%--                   <html-el:option value="700">福利金</html-el:option> --%>
                </html-el:select>
                &nbsp;
                  <html-el:select property="bi_get_type" styleId="bi_get_type">
                    <html-el:option value="">请选择...</html-el:option>
                    <c:forEach items="${biGetTypes}" var="keys">
                    <html-el:option value="${keys.index}">${keys.name}</html-el:option>
                    </c:forEach>
                  </html-el:select>
                &nbsp;时间 从：
                <html-el:text property="begin_date" styleId="begin_date" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker()" />
                至：
                <html-el:text property="end_date" styleId="end_date" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker()" />
                &nbsp;
                <html-el:submit value="查 询" styleClass="bgButton" />
			  </td>
            </tr>
          </table></td>
      </tr>
    </table>
  </html-el:form>
  <div class="padding:5px;"></div>
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClassSearch">
    <tr>
      <th width="5%"><c:if test="${is_open_bi_dian eq true}">余额：
      <fmt:formatNumber var="dianzi_bi" value="${dianzibi}" pattern="0.########"/>${dianzi_bi}
      </c:if>
     	&nbsp;货款： 
      <fmt:formatNumber var="huokuan_bi" value="${huokuanbi}" pattern="0.########"/>${huokuan_bi}
        &nbsp;扶贫金： 
      <fmt:formatNumber var="fupin_bi" value="${af.map.bi_aid}" pattern="0.########"/>${fupin_bi}		
      </th>
    </tr>
  </table>
  <%@ include file="/commons/pages/messages.jsp" %>
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
  <tr>
  <th width="3%" nowrap="nowrap" align="center"><strong>编号</strong></th>
    <th>操作用户</th>
    <th>接受/发送用户</th>
    <th width="20%">类型</th>
    <th>操作前金额</th>
    <th>本次金额</th>
    <th>操作后金额</th>
    <th>订单信息</th>    
    <th>操作时间</th>
  </tr>
  <c:forEach var="cur" items="${userBiRecordList}" varStatus="vs">
    <tr>
      <td align="center">${cur.id}</td>
      <td align="center"><c:out value="${cur.map.userName}" /></td>
      <td align="center"><c:out value="${cur.dest_uname}" /></td>
      <td align="center">
        <c:forEach items="${biGetTypes}" var="keys">
          <c:if test="${cur.bi_get_type eq  keys.index}">${keys.name}</c:if>
        </c:forEach>
      </td>
      <c:if test="${(cur.bi_get_type ne 130 and cur.bi_get_type ne -10 and cur.bi_get_type ne -150)or cur.bi_type ne 100}">
      <td align="center">
      <fmt:formatNumber var="bi" pattern="0.########" value="${cur.bi_no_before}"/>
        ${bi}
      </td>
      <td align="center">
      	<c:set var="pre" value="+" />
        <c:set var="class" value="tip-success" />
        <c:if test="${cur.bi_chu_or_ru eq -1}">
          <c:set var="pre" value="-" />
          <c:set var="class" value="tip-danger" />
        </c:if>
        <fmt:formatNumber var="bi" pattern="0.########" value="${cur.bi_no}" />
        <fmt:formatNumber var="sub" pattern="0.########" value="${cur.map.orderInfo.order_money + cur.map.orderInfo.money_bi }" />
        
        <span class="${class}">${pre}&nbsp;${bi}</span>
        <c:if test="${bi ne sub}">
	        </br><span style="color: red;">异常金额 (${sub})</span>
		</c:if>
      </td>
      <td align="center"><fmt:formatNumber var="bi" pattern="0.########" value="${cur.bi_no_after}" />
        ${bi}</a></td>
      </c:if>
      <c:if test="${(cur.bi_get_type eq 130 or cur.bi_get_type eq -10 or cur.bi_get_type eq -150)and cur.bi_type eq 100}">
	      <td align="center">
		      <fmt:formatNumber var="bi" pattern="0.##" value="${cur.bi_no_before}"/>
		      <fmt:formatNumber var="bi_no" pattern="0.##" value="${cur.bi_no}"/>
		      <fmt:formatNumber var="bi_no_after" pattern="0.##" value="${cur.bi_no_after}"/>
		      
		      <fmt:formatNumber var="fuxiao" pattern="0.##" value="${cur.welfare_no_before}"/>
		      <fmt:formatNumber var="fuxiao_no" pattern="0.##" value="${cur.welfare_no}"/>
		      <fmt:formatNumber var="fuxiao_no_after" pattern="0.##" value="${cur.welfare_no_after}"/>
	          ${bi}
          </td>
	      <td align="center">
	      	<c:set var="pre" value="+" />
	        <c:set var="class" value="tip-success" />
	        <c:if test="${cur.bi_chu_or_ru eq -1}">
	          <c:set var="pre" value="-" />
	          <c:set var="class" value="tip-danger" />
	        </c:if>
	        <span class="${class}">${pre}&nbsp;${bi_no}</span>
      	  </td>
      	  
	      <td align="center">
	      	${bi_no_after}
          </td>
      </c:if>
       <td align="center">
         <c:if test="${cur.bi_type eq 300}">
      	订单号：<a href="OrderQuery.do?method=view&amp;mod_id=${af.map.mod_id}&amp;order_id=${cur.map.orderInfo.id}">${cur.map.orderInfo.trade_index}</a><br/>
      	订单金额：${cur.map.orderInfo.order_money}<br/>
      	运费金额：${cur.map.orderInfo.matflow_price}<br/>
      	下单人：${cur.map.orderInfo.add_user_name}<br/>
		
      	<c:if test="${cur.bi_type eq 500}">
      	订单号：${cur.map.orderInfo.trade_index}<br/>
      	订单金额：${cur.map.orderInfo.order_money}<br/>
      	运费金额：${cur.map.orderInfo.matflow_price}<br/>
      	下单人：${cur.map.orderInfo.add_user_name}<br/>
      	</c:if>
      	</c:if>
      	
      	<c:if test="${cur.map.orderInfo.money_bi gt 0}">
	          余额抵扣:<fmt:formatNumber value="${cur.map.orderInfo.money_bi-cur.map.orderInfo.welfare_pay_money}" pattern="0.##"/>
		          <c:if test="${cur.map.orderInfo.welfare_pay_money gt 0}">
		          	<br/>福利金抵扣:<fmt:formatNumber value="${cur.map.orderInfo.welfare_pay_money}" pattern="0.##"/>
	              </c:if> 
            </c:if> 
            <c:if test="${cur.map.orderInfo.card_pay_money gt 0}">
	          <br/>福利卡抵扣:<fmt:formatNumber value="${cur.map.orderInfo.card_pay_money}" pattern="0.##"/>
            </c:if> 
            
      </td>
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
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
      </c:forEach>
</table>
  <div class="pageClass">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="UserMoney.do">
      <table width="98%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td height="10"><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
            pager.addHiddenInputs("method", "list");
            pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
            pager.addHiddenInputs("user_name", "${af.map.user_name}");
            pager.addHiddenInputs("mobile", "${af.map.mobile}");
            pager.addHiddenInputs("user_no", "${af.map.user_no}");
            pager.addHiddenInputs("id", "${af.map.id}");
            pager.addHiddenInputs("bi_type", "${af.map.bi_type}");
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

<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<script type="text/javascript">//<![CDATA[
$("#id_role_id").focus(setOnlyNum).blur(function(){
	if(/[.+]+/.test(this.value)){
		alert("ID只能为整数！");
		this.value = this.value.replace(/[.+]/g,"");
		this.focus();
		return false;
	}	
});
function setOnlyNum() {
	$(this).css("ime-mode", "disabled").attr("t_value", "").attr("o_value", "").bind("dragenter",function(){
		return false;
	});
	$(this).keypress(function (){
		if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value;
	}).keyup(function (){
		if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value;
	}).blur(function (){
		if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value;}
		if(this.value.length == 0) this.value = "";
	});
	//this.text.selected;
}
//]]></script>
<jsp:include page="../public_page.jsp" flush="true" />
</body>
</html>
