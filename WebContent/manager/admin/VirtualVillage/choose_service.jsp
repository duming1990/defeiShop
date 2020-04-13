<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
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
  <html-el:form action="/admin/VirtualServiceInfo">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tableClassSearch">
      <tr>
        <td>
        	<div> 
                                合伙人名称：
            <html-el:text property="servicecenter_name_like" maxlength="40" style="width:150px;" styleClass="webinput" />
            &nbsp;用户名：
            <html-el:text property="add_user_name_like" maxlength="40" style="width:100px;" styleClass="webinput" />
            &nbsp;服务编号：
            <html-el:text property="servicecenter_no" maxlength="40" style="width:100px;" styleClass="webinput" />
            &nbsp;<html-el:submit value="查 询" styleClass="bgButton"/>
          </div>
         </td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="VirtualServiceInfo.do?method=delete">
  <div style="padding-bottom:5px;">
    </div>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th width="5%">序号</th>
        <th>合伙人名称</th>
        <th width="8%">服务编号</th>
        <th width="10%">联系电话</th>
        <th width="12%">所属地区</th>
        <th width="7%">添加时间</th>
        <th width="6%">更新时间</th>
        <th width="6%">审核时间</th>
        <th width="6%">是否审核</th>
        <th width="8%">是否生效</th>
        <th width="8%" nowrap="nowrap">操作</th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr>
          <td align="center">${vs.count}</td>
          <td align="left">${fn:escapeXml(cur.servicecenter_name)}</td>
          <td align="center">
            <c:if test="${not empty cur.servicecenter_no}"> 
            ${fn:escapeXml(cur.servicecenter_no)}
            <a class="butbase" href="javascript:void(0);"></a> 
            </c:if>
            <c:if test="${empty cur.servicecenter_no}">---</c:if>
          </td>
          <td align="center">
            <div>
            <span class="tip-info qtip" title="合伙人联系电话：${fn:escapeXml(cur.servicecenter_linkman_tel)}">${fn:escapeXml(cur.servicecenter_linkman_tel)}</span>
            </div>
          </td>
          <td align="left">
          ${fn:escapeXml(cur.map.full_name)}
           <div>
              <c:choose>
                <c:when test="${cur.servicecenter_level eq 1}"><span class="tip-danger">[普通合伙人]</span></c:when>
                <c:when test="${cur.servicecenter_level eq 2}"><span class="tip-primary">[股份合伙人]</span></c:when>
              </c:choose>
            </div>
          </td>
          <td align="center"><fmt:formatDate value="${cur.add_date}" pattern="yyyy-MM-dd" /></td>
          <td align="center"><fmt:formatDate value="${cur.update_date}" pattern="yyyy-MM-dd" /></td>
          <td align="center"><fmt:formatDate value="${cur.audit_date}" pattern="yyyy-MM-dd" /></td>
           <td align="center">
            <c:choose>
              <c:when test="${cur.audit_state eq -1}"><span class="label label-danger">审核不通过</span></c:when>
              <c:when test="${cur.audit_state eq 0}"><span class="label label-default">待审核</span></c:when>
              <c:when test="${cur.audit_state eq 1}"><span class="label label-success">审核通过</span></c:when>
            </c:choose>
          </td>
          <td align="center"><c:choose>
              <c:when test="${cur.effect_state eq 0}"><span class="tip-danger">未生效</span></c:when>
              <c:when test="${cur.effect_state eq 1}"><span class="tip-success">已生效</span>
                <fmt:formatDate value="${cur.effect_date}" pattern="yyyy-MM-dd" var="date_effect_date"/>
                <fmt:formatDate value="${cur.effect_date}" pattern="yyyy-MM-dd HH:mm:ss" var="date_effect_date_detail"/>
                <fmt:formatDate value="${cur.effect_end_date}" pattern="yyyy-MM-dd" var="date_effect_end_date"/>
                <fmt:formatDate value="${cur.effect_end_date}" pattern="yyyy-MM-dd HH:mm:ss" var="date_effect_end_date_detail"/>
                <c:if test="${not empty date_effect_date}">
                 <div style="padding: 2px;" class="tip-success qtip" title="生效时间：${date_effect_date_detail}"><i class="fa fa-clock-o"></i> ${date_effect_date}</div>
                 <div class="tip-danger qtip" title="失效时间：${date_effect_end_date_detail}"><i class="fa fa-clock-o"></i> ${date_effect_end_date}</div>
                </c:if>
              </c:when>
            </c:choose>
          </td>
          <td align="center" nowrap="nowrap">
                <a class="butbase" onclick="choose('${cur.p_index}','${cur.servicecenter_name}');"><span class="icon-ok">选择</span></a>
          </td>
        </tr>
         <c:if test="${vs.last eq true}">
          <c:set var="i" value="${vs.count}" />
        </c:if>
  </c:forEach>
  <c:forEach begin="${i}" end="${af.map.pager.pageSize-1}">
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
        </tr>
      </c:forEach>
    </table>
  </form>
  <div class="pageClass">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="VirtualServiceInfo.do">
      <table width="98%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
            pager.addHiddenInputs("method", "list");
            pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
            pager.addHiddenInputs("servicecenter_name_like", "${fn:escapeXml(af.map.servicecenter_name_like)}");
            pager.addHiddenInputs("add_user_name_like", "${fn:escapeXml(af.map.add_user_name_like)}");
			pager.addHiddenInputs("audit_state", "${af.map.audit_state}");
			pager.addHiddenInputs("pay_success", "${af.map.pay_success}");
			pager.addHiddenInputs("effect_state", "${af.map.effect_state}");
			pager.addHiddenInputs("today_date", "${af.map.today_date}");
			pager.addHiddenInputs("province", "${fn:escapeXml(af.map.province)}");
			pager.addHiddenInputs("city", "${fn:escapeXml(af.map.city)}");
			pager.addHiddenInputs("country", "${fn:escapeXml(af.map.country)}");
			pager.addHiddenInputs("pay_type", "${fn:escapeXml(af.map.pay_type)}");
			pager.addHiddenInputs("update_date_eq", "${af.map.update_date_eq}");
			pager.addHiddenInputs("servicecenter_no", "${af.map.servicecenter_no}");
			pager.addHiddenInputs("servicecenter_level", "${af.map.servicecenter_level}");
			pager.addHiddenInputs("orderByInfo", "${af.map.orderByInfo}");
			
			pager.addHiddenInputs("add_date_st", "${af.map.add_date_st}");
			pager.addHiddenInputs("add_date_en", "${af.map.add_date_en}");
            pager.addHiddenInputs("update_date_st", "${af.map.update_date_st}");
			pager.addHiddenInputs("update_date_en", "${af.map.update_date_en}");
            pager.addHiddenInputs("audit_date_st", "${af.map.audit_date_st}");
			pager.addHiddenInputs("audit_date_en", "${af.map.audit_date_en}");
            pager.addHiddenInputs("pay_date_st", "${af.map.pay_date_st}");
			pager.addHiddenInputs("pay_date_en", "${af.map.pay_date_en}");
			
			pager.addHiddenInputs("wl_order_state", "${af.map.wl_order_state}");
			pager.addHiddenInputs("is_del", "${af.map.is_del}");
			
            document.write(pager.toString());
            </script></td>
        </tr>
      </table>
    </form>
  </div>
</div>

<script type="text/javascript" src="${ctx}/commons/scripts/jquery.cs.js"></script>
<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/scripts/tip/jquery.quicktip.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/colorbox/jquery.colorbox.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/area.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/citySelect.js"></script>
<script type="text/javascript" src="${ctx}/scripts/colorbox/jquery.colorbox.min.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	$("#city_div").citySelect({
        data:getAreaDic(),
        province:"${af.map.province}",
        city:"${af.map.city}",
        country:"${af.map.country}",
        required:false
    });
	
	$(".qtip").quicktip();
});

$("a.beautybg").colorbox({width:"90%", height:"80%", iframe:true});

function choose(p_index,service_name){
	var api = frameElement.api, W = api.opener;
	 W.document.getElementById("service_name").value = service_name;
	 W.document.getElementById("p_index_service").value = p_index;
	 api.close();
}

function setPayInfo(id){
	var url = "VirtualServiceInfo.do?method=setPayInfo&id=" + id;
	$.dialog({
		title:  "设置支付信息",
		width:  900,
		height: 520,
		content:"url:"+ encodeURI(url)
	});
}
function add_Brougth_Account(id){
	var url = "VirtualServiceInfo.do?method=add_Brougth_Account&id=" + id;
	$.dialog({
		title:  "增加对公账户",
		width:  900,
		height: 520,
		content:"url:"+ encodeURI(url)
	});
}

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

function confirmServiceCenter(id){
	var submit2 = function(v, h, f) {
		if (v == "ok") {
			 $.jBox.tip("操作中...", 'loading');
			location.href="VirtualServiceInfo.do?method=confirmServiceInfo&id="+ id +"&mod_id=${af.map.mod_id}&" + $('#bottomPageForm').serialize();
		}
	};
	$.jBox.confirm("确认之后将会生成服务编号，可以使用服务编号进行登录等操作！", "确定提示", submit2);
}
function cancelServiceCenter(id){
	var submit2 = function(v, h, f) {
		if (v == "ok") {
			$.jBox.tip("操作中...", 'loading');
			location.href="VirtualServiceInfo.do?method=cancelServiceInfo&id="+ id +"&mod_id=${af.map.mod_id}&" + $('#bottomPageForm').serialize();
		}
	};
	$.jBox.confirm("确认通过后将系统将取消合伙人权限！你确定要执行该操作吗?", "确定提示", submit2);
}

function refreshPage(){
	window.location.reload();
}

//]]></script>
<jsp:include page="../public_page.jsp" flush="true" />
</body>
</html>
