<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/commons/styles/nav.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/scripts/colorbox/style3/colorbox.css" rel="stylesheet" type="text/css" />
</head>
<body>
<script type="text/javascript" src="${ctx}/commons/scripts/public.js"></script>
<div align="center" class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/customer/OrderQuery"  styleClass="searchForm">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="order_type" styleId="order_type"/>
    <html-el:hidden property="from_type" styleId="from_type"/>
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="entp_id" styleId="${af.map.entp_id}"/>
    <html-el:hidden property="activity_id" styleId="${af.map.activity_id}"/>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClassSearch">
      <tr>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">
            <tr>
              <td nowrap="nowrap"> 支付方式：
                <html-el:select property="pay_type" styleId="pay_type" styleClass="webinput">
                  <html-el:option value="">请选择...</html-el:option>
                  <c:forEach var="curPayType" items="${payTypeList}">
                    <html-el:option value="${curPayType.index}">${curPayType.name}</html-el:option>
                  </c:forEach>
                </html-el:select>
                &nbsp;订单状态：
                <html-el:select property="order_state" styleId="order_state" styleClass="webinput">
                  <html-el:option value="">请选择...</html-el:option>
                  <c:forEach var="curOrderState" items="${orderStateList}">
                    <html-el:option value="${curOrderState.index}">${curOrderState.name}</html-el:option>
                  </c:forEach>
                </html-el:select>
                &nbsp;下单时间 从：
                <html-el:text property="st_add_date" styleId="st_add_date" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker()" />
                至：
                <html-el:text property="en_add_date" styleId="en_add_date" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker()" />
                &nbsp;支付时间 从：
                <html-el:text property="st_pay_date" styleId="st_pay_date" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker()" />
                至：
                <html-el:text property="en_pay_date" styleId="en_pay_date" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker()" />
                &nbsp;直接搜索：
                <html-el:checkbox property="order_has_pay" styleId="order_has_pay" styleClass="webinput"  style="vertical-align: bottom;" value="1"/>
                <label for="order_has_pay">已支付(全部)</label>
                &nbsp;
                <div style="margin-top: 5px;"> 下单人：
                  <html-el:text property="add_user_name_like" maxlength="20" style="width:70px;" styleClass="webinput" />
                  &nbsp;下单人手机：
                  <html-el:text property="add_user_mobile_like" maxlength="20" style="width:70px;" styleClass="webinput" />
                  &nbsp;收货人：
                  <html-el:text property="rel_name_like" maxlength="20" style="width:70px;" styleClass="webinput" />
                  &nbsp;收货人电话：
                  <html-el:text property="rel_phone_like" maxlength="20" style="width:70px;" styleClass="webinput" />
                  &nbsp;企业名称：
                  <html-el:text property="entp_name_like" maxlength="20" style="width:100px;" styleClass="webinput" />
                 &nbsp;商品名称：
                  <html-el:text property="comm_name_like" styleClass="webinput" maxlength="50" style="width:120px;"/>
                </div>
                <div style="margin-top: 5px;"> ${app_name_min}订单号：
                  <html-el:text property="trade_index_like" maxlength="25" style="width:140px;" styleClass="webinput" />
                  &nbsp;${app_name_min}交易单号：
                  <html-el:text property="trade_merger_index_like" maxlength="25" style="width:140px;" styleClass="webinput" />
                  &nbsp;第三方订单号：
                  <html-el:text property="trade_no_like" maxlength="40" style="width:140px;" styleClass="webinput" />
                  &nbsp; 发票状态：
		          <html-el:select property="fp_state" styleId="fp_state" styleClass="webinput">
		            <html-el:option value="">请选择...</html-el:option>
		              <html-el:option value="0">不需要发票</html-el:option>
		              <html-el:option value="1">发票未寄送</html-el:option>
		              <html-el:option value="2">发票已寄送</html-el:option>
		          </html-el:select>
		          &nbsp; 是否测试数据：
                  <html-el:select property="is_test" styleId="is_test" styleClass="webinput">
		            <html-el:option value="">请选择...</html-el:option>
		              <html-el:option value="0">正式订单</html-el:option>
		              <html-el:option value="1">测试订单</html-el:option>
		          </html-el:select>
		          <!-- 线下补录订单需审核 -->
		          <c:set var="colNum" value="11"/>
		          <c:set var="isOffline" value="false"/>
		          <c:if test="${af.map.order_type eq 60}">
		          <c:set var="colNum" value="12"/>
		          <c:set var="isOffline" value="true"/>
		          &nbsp; 审核状态：
		          <html-el:select property="audit_state" styleId="audit_state" styleClass="webinput">
		            <html-el:option value="">请选择...</html-el:option>
		              <html-el:option value="0">待审核</html-el:option>
		              <html-el:option value="1">审核通过</html-el:option>
		              <html-el:option value="-1">审核不通过</html-el:option>
		          </html-el:select>
		          </c:if>
		          &nbsp; 是否修改过价格：
                  <html-el:select property="is_price_modify" styleId="is_price_modify" styleClass="webinput">
		            <html-el:option value="">请选择...</html-el:option>
		              <html-el:option value="0">否</html-el:option>
		              <html-el:option value="1">是</html-el:option>
		          </html-el:select>
                  &nbsp;
                  <input type="submit" class="bgButton" value="查 询" />
                  &nbsp;<input id="download" type="button" value="导出" class="bgButton" />
                  
                </div></td>
            </tr>
          </table></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="OrderQuery.do?method=delete">
    <div class="all">
      <ul class="nav nav-tabs" id="nav_ul_content">
        <c:forEach var="cur" items="${orderTypeList}" varStatus="vs">
          <c:set var="liClass" value="" />
          <c:if test="${vs.count eq 1}">
            <c:set var="liClass" value="active" />
          </c:if>
          <li data-type="${cur.index}" class="${liClass}"><a><span>${cur.name}</span></a></li>
        </c:forEach>
      </ul>
    </div>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th nowrap="nowrap" align="center"><strong>订单信息</strong></th>
        <th width="8%" align="center"><strong>收货人</strong></th>
        <th width="8%" align="center"><strong>收货人电话</strong></th>
        <th width="8%" align="center"><strong>下单人</strong></th>
        <th width="8%" align="center"><strong>下单人电话</strong></th>
        <th width="8%" align="center"><strong>下单时间</strong></th>
        <th width="8%" align="center"><strong>订单金额</strong></th>
        <th width="8%" align="center"><strong>支付方式</strong></th>
        <th width="8%" align="center"><strong>订单状态</strong></th>
        <th width="8%" align="center"><strong>是否修改过价格</strong></th>
        <c:if test="${isOffline eq true}">
        <th width="10%" align="center"><strong>审核状态</strong></th>
        </c:if>
        <th width="10%" align="center"><strong>操作</strong></th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr>
          <td colspan="${colNum}" style="background: #e6e6e6;">${vs.count}、
            &nbsp;订单编号：${cur.trade_index}&nbsp;&nbsp;下单时间：
            <fmt:formatDate value="${cur.order_date}" pattern="yyyy-MM-dd HH:mm:ss" />
            <c:if test="${not empty cur.trade_merger_index}">&nbsp;${app_name_min}交易单号：${cur.trade_merger_index}</c:if>
            <c:if test="${not empty cur.trade_no}">&nbsp;第三方订单号：${cur.trade_no}</c:if>
            <c:if test="${cur.order_type != 20}">
              &nbsp;<a><span class="tip-primary">商家：${cur.entp_name}</span></a>
            </c:if>
          </td>
        </tr>
        <tr>
          <td align="left"><table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
              <c:set var="isPd" value="false" />
              <c:forEach var="oidsList" varStatus="vs1" items="${cur.orderInfoDetailsList}">
              <c:set var="style" value="" />
                  <c:if test="${oidsList.is_tuihuo eq 1}">
                  	<c:set var="style" value="text-decoration:line-through" />
                  </c:if>
                <tr style="${style}">
                  <td>
                    <c:if test="${cur.order_type eq 10 or cur.order_type eq 30  or cur.order_type eq 40}">
                    <c:url var="url" value="/entp/IndexEntpInfo.do?method=getCommInfo&id=${oidsList.comm_id}" />
                    <a href="${url}" target="_blank">${oidsList.comm_name}</a>
                    </c:if>
                    <c:if test="${cur.order_type eq 50 }">${oidsList.comm_name}</c:if>
                    <c:if test="${cur.order_type eq 7 or cur.order_type eq 20}">${oidsList.comm_name}</c:if>
                    <c:if test="${not empty oidsList.comm_tczh_name}"> &nbsp;[${oidsList.comm_tczh_name}] </c:if>
                    <c:if test="${oidsList.fp_state eq 2}">&nbsp;<img src="${ctx}/styles/images/fapiao.png" title="发票已寄送" class="fapiao"/></c:if>
                    <c:if test="${oidsList.fp_state eq 1}">&nbsp;<img src="${ctx}/styles/images/no_fapiao.png" title="发票未寄送" class="fapiao"/></c:if>
                  </td>
                  <td width="20%" align="center"><c:if test="${not empty oidsList.good_price}">￥
                      <fmt:formatNumber value="${oidsList.good_price}" pattern="0.00" />
                    </c:if>
                    <c:if test="${empty oidsList.good_price}">-</c:if>
                  </td>
                  <td width="6%" align="center">${oidsList.good_count}</td>
                </tr>
              </c:forEach>
            </table></td>
          <td align="center">
          ${cur.rel_name}
          <c:if test="${empty cur.rel_name}">--</c:if>
            <c:if test="${cur.order_type ne 50 }">
             <a class="viewDetail" href="${ctx}/manager/customer/UserRelation.do?method=queryAllParList&user_id=${cur.add_user_id}&mod_id=1002005000">
		        <div><span class="qtip" title="真实姓名：${fn:escapeXml(cur.map.user_name)}">(${fn:escapeXml(cur.map.user_name)})</span></div>
		     </a>
            </c:if>
          </td>
          <td align="center">${cur.rel_phone}<c:if test="${empty cur.rel_phone}">--</c:if></td>
          <td align="center">${cur.add_user_name}<c:if test="${empty cur.add_user_name}">--</c:if></td>
          <td align="center">${cur.map.add_user_mobile}<c:if test="${empty cur.map.add_user_mobile}">--</c:if></td>
          <td align="center"><fmt:formatDate value="${cur.order_date}" pattern="yyyy-MM-dd" /></td>
          <td align="center" nowrap="nowrap">
        	  第三方支付：￥<fmt:formatNumber value="${cur.order_money}" pattern="0.00"/><br/>
          	应付：￥<fmt:formatNumber value="${cur.no_dis_money}" pattern="0.00"/><br/>
          	实付：￥<fmt:formatNumber value="${cur.order_money}" pattern="0.00"/>
            <br/>
            (运费:<fmt:formatNumber value="${cur.matflow_price}" pattern="0.00"/>) 
            <c:if test="${cur.money_bi gt 0}">
	          <br/>(余额抵扣:<fmt:formatNumber value="${cur.money_bi-cur.welfare_pay_money}" pattern="0.##"/>) 
		          <c:if test="${cur.welfare_pay_money gt 0}">
		          	<br/>(福利金抵扣:<fmt:formatNumber value="${cur.welfare_pay_money}" pattern="0.##"/>) 
	              </c:if> 
            </c:if> 
            <c:if test="${cur.card_pay_money gt 0}">
	          <br/>(福利卡抵扣:<fmt:formatNumber value="${cur.card_pay_money}" pattern="0.##"/>) 
            </c:if> 
            <c:if test="${not empty cur.yhq_tip_desc}">
            <br/>(${cur.yhq_tip_desc}) 
            </c:if>
	        <c:if test="${not empty cur.map.huanhuo}">
            	<br/>(换货订单)
            </c:if>
            <c:if test="${not empty cur.map.reMoney}">
          		<br/><span style="color:red">(退款:<fmt:formatNumber value="${cur.map.reMoney}" pattern="0.##"/>)</span>
         	</c:if>
          </td>
          <td align="center">
            <c:if test="${cur.order_state eq 0 or cur.order_state eq -10}">─</c:if>
            <c:if test="${cur.order_state ne 0 and cur.order_state ne -10}">
	            <c:forEach var="curPayType" items="${payTypeList}">
	              <c:if test="${curPayType.index eq cur.pay_type}">
	                ${curPayType.name}
	              </c:if>
	            </c:forEach>
            </c:if>
          </td>
          <td align="center" nowrap="nowrap"><script type="text/javascript">showOrderState(${cur.order_state},${cur.pay_type},${cur.order_type})</script>
              <div>
                <c:if test="${cur.is_ziti eq 1}" var="isziti"><span class="tip-success qtip" title="用户自提订单"><i class="fa fa-shopping-basket"></i>自提</span></c:if>
              </div>
              <div>
                <c:if test="${not empty cur.remark}"><span class="tip-success qtip" title="留言：${fn:escapeXml(cur.remark)}"><i class="fa fa-wechat"></i>留言</span></c:if>
              </div>
          </td>
          <td align="center">
          	<c:choose>
          		<c:when test="${empty cur.is_price_modify}"><span style=" color:#060;">否</span></c:when>
          		<c:when test="${cur.is_price_modify eq 1}"><span style=" color:#F00;">是（修改前：<fmt:formatNumber value="${cur.price_modify_old}" pattern="0.##"/>元）</span></c:when>
          	</c:choose>
          </td>
          <c:if test="${isOffline eq true}">
	          <td align="center">
	          	<c:choose>
	          		<c:when test="${cur.audit_state eq 1}"><span style=" color:#060;">审核通过</span></c:when>
	          		<c:when test="${cur.audit_state eq -1}"><span style=" color:#F00;">审核不通过</span></c:when>
	          		<c:otherwise>待审核</c:otherwise>
	          	</c:choose>
	          </td>
          </c:if>
          <td align="center">
<%--          <c:if test="${(cur.order_type eq 11 or cur.order_type eq 10) and (cur.order_state eq 10)}"> --%>
<%--           <a href="javascript:void(0);" onclick="updateStateFh('${cur.id}');">发货</a><br/> --%>
<%--          </c:if>  --%>
			<c:if test="${cur.order_type eq 60}">
				<a class="butbase" href="javascript:void(0);"><span class="icon-cancel" onclick="removeOrder(${cur.id});">删除线下订单</span></a><br/>
			</c:if>
			<c:if test="${isOffline eq true}">
				<a class="butbase" href="javascript:confirmView(null,'OrderQuery.do','mod_id=${af.map.mod_id}&order_id=${cur.id}&'+$('#bottomPageForm').serialize());"><span class="icon-ok">审核线下订单</span></a><br/>
			</c:if>
          <a href="?method=view&amp;mod_id=${af.map.mod_id}&amp;order_id=${cur.id}">订单详情</a><c:if test="${cur.is_test eq 1}"><br /><span style="color:red;">（测试订单）</span></c:if></td>
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
          <c:if test="${isOffline eq true}">
          <td>&nbsp;</td>
          </c:if>
        </tr>
      </c:forEach>
    </table>
  </form>
  <div class="pageClass">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="${ctx}/manager/customer/OrderQuery.do">
      <table width="98%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td height="10"><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
				var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
				pager.addHiddenInputs("method", "list");
				pager.addHiddenInputs("order_state", "${af.map.order_state}");
				pager.addHiddenInputs("fp_state", "${af.map.fp_state}");
				pager.addHiddenInputs("st_add_date", "${af.map.st_add_date}");
				pager.addHiddenInputs("en_add_date", "${af.map.en_add_date}");
				pager.addHiddenInputs("st_pay_date", "${af.map.st_pay_date}");
				pager.addHiddenInputs("en_pay_date", "${af.map.en_pay_date}");
				pager.addHiddenInputs("st_qrsh_date", "${af.map.st_qrsh_date}");
				pager.addHiddenInputs("en_qrsh_date", "${af.map.en_qrsh_date}");
				pager.addHiddenInputs("entp_name_like", "${af.map.entp_name_like}");
				pager.addHiddenInputs("rel_name_like", "${af.map.rel_name_like}");
				pager.addHiddenInputs("rel_phone_like", "${af.map.rel_phone_like}");
				pager.addHiddenInputs("add_user_name_like", "${af.map.add_user_name_like}");
				pager.addHiddenInputs("add_user_mobile_like", "${af.map.add_user_mobile_like}");
				pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
				pager.addHiddenInputs("trade_merger_index_like", "${af.map.trade_merger_index_like}");
				pager.addHiddenInputs("trade_index_like", "${af.map.trade_index_like}");
				pager.addHiddenInputs("pay_type", "${af.map.pay_type}");
				pager.addHiddenInputs("order_type", "${af.map.order_type}");
				pager.addHiddenInputs("trade_no_like", "${af.map.trade_no_like}");
				pager.addHiddenInputs("order_has_pay", "${af.map.order_has_pay}");
				pager.addHiddenInputs("order_has_pay_three", "${af.map.order_has_pay_three}");
				pager.addHiddenInputs("from_type", "${af.map.from_type}");
				pager.addHiddenInputs("comm_name_like", "${af.map.comm_name_like}");
				pager.addHiddenInputs("is_test", "${af.map.is_test}");
				pager.addHiddenInputs("audit_state", "${af.map.audit_state}");
				pager.addHiddenInputs("is_price_modify", "${af.map.is_price_modify}");
				pager.addHiddenInputs("activity_id", "${af.map.activity_id}");
				pager.addHiddenInputs("entp_id", "${af.map.entp_id}");
		        document.write(pager.toString());
            	</script></td>
        </tr>
      </table>
    </form>
  </div>
</div>
<div class="clear"></div>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/scripts/tip/jquery.quicktip.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/scripts/colorbox/jquery.colorbox.min.js"></script> 
<script type="text/javascript">//<![CDATA[
var f = document.forms[0];

$(document).ready(function(){
	
	$(".qtip").quicktip();
	
	$("a.viewDetail").colorbox({width:"100%", height:"100%", iframe:true});
	
	$("#download").click(function(){
		
		var submit = function (v, h, f) {
		    if (v == true) {
		    	location.href = "${ctx}/manager/customer/OrderQuery.do?method=toExcel&mod_id=${af.map.mod_id}&" + $('.searchForm').serialize();
		    } else {
		    	location.href = "${ctx}/manager/customer/OrderQuery.do?method=toExcel&code=GBK&mod_id=${af.map.mod_id}&" + $('.searchForm').serialize();
		    }
		    return true;
		};
		var tip = "确认导出EXCEL格式数据？如果UTF-8编码格式乱码，请选择GBK编码格式下载！";
		$.jBox.confirm(tip, "系统提示", submit, { buttons: { '下载(UTF-8编码)': true, '下载(GBK编码)': false} });
	});
	
	
	//导航回显
	$("#nav_ul_content li").each(function(){
		if($(this).attr("data-type") ==  $("#order_type").val()){
			$(this).addClass("active").siblings().removeClass("active");
			return false;
		}
	});
	//导航跳转
	$("#nav_ul_content a").click(function(){ 
		 var type = $(this).parent().attr("data-type");
		 //非线下订单，审核状态不作为查询条件
		 if(type != 60){
			 $('#bottomPageForm').find("input[id='audit_state']").val("");
		 }
		 this.href= "${ctx}/manager/customer/OrderQuery.do?method=list&order_type="+ type + "&" + $('#bottomPageForm').serialize();
	     this.target = "_self";
	});

	$("#but_submit").click(function(){
		if(Validator.Validate(f, 2)){
			f.submit();
		}
	});
});

function updateStateFh(id){
	$.dialog({
		title:  "发货",
		width:  500,
		height: 400,
        lock:true ,
		content:"url:${ctx}/manager/customer/OrderQuery.do?method=updateStateFh&order_id=" +id + "&" + $('#bottomPageForm').serialize()
	});
}
function removeOrder(id){
	if(id){
		var submit = function (v, h, f) {
		    if (v == true) {
		    	 $.post("?method=removeOrder",{order_id:id},function(data){
			    	 jBox.tip(data.msg, 'info');
			    	  if(data.ret == 1){
			    		location.reload();
			    	  }
				 });
		    } 
		    return true;
		};
		// 自定义按钮
		$.jBox.confirm("您确定要删除该订单吗?", "系统提示", submit, { buttons: { '确定': true, '取消': false} });
	}
}
function windowReload(){
	window.location.reload();
}

function confirmView(msg, page, queryString) {
	//msg  = msg  || "\u786e\u5b9a\u4fee\u6539\u8fd9\u6761\u4fe1\u606f\u5417\uff1f";
	page = page || "?";
	page = page.indexOf("?") != -1 ? page : (page + "?");
	location.href = page  + "method=view&" + queryString;
}
//]]>
</script>
</body>
</html>
