<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
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
   <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
     <tr>
        <th colspan="4">基本信息</th>
      </tr>
      <tr>
         <td width="14%" nowrap="nowrap" class="title_item">合伙人类型：</td>
            <td colspan="3">
            <c:forEach var="curserviceLevel" items="${serviceLevelList}">
            <c:if test="${curserviceLevel.index eq af.map.servicecenter_level}">${curserviceLevel.name}</c:if>
            </c:forEach>
            </td>
       </tr>
      <tr>
         <td width="14%" nowrap="nowrap" class="title_item">用户：</td>
            <td colspan="3">${af.map.add_user_name}</td>
       </tr>
       <c:if test="${not empty af.map.servicecenter_no}">
       <tr>
         <td width="14%" nowrap="nowrap" class="title_item">服务编号：</td>
            <td colspan="3">${af.map.servicecenter_no}</td>
       </tr>
       </c:if>
       <tr>
         <td width="14%" nowrap="nowrap" class="title_item">申请区域：</td>
            <td colspan="3">
             <c:if test="${not empty af.map.map.full_name}">${fn:escapeXml(af.map.map.full_name)}</c:if>
            </td>
       </tr>
       <tr>
         <td width="14%" nowrap="nowrap" class="title_item">驿站数量限制：</td>
            <td colspan="3">
             ${fn:escapeXml(af.map.village_count_limit)}&nbsp;家
            </td>
       </tr>
       <tr>
         <td width="14%" nowrap="nowrap" class="title_item">合伙人名称：</td>
         <td>${fn:escapeXml(af.map.servicecenter_name)}</td>
         <td width="14%" nowrap="nowrap" class="title_item">注册地址：</td>
         <td colspan="3">${fn:escapeXml(af.map.servicecenter_addr)}</td>
       </tr>
       <tr>
	       <td width="14%" nowrap="nowrap" class="title_item">地理位置：</td>
	       <td colspan="3">${af.map.position_latlng}
	       &nbsp;
	       <a onclick="viewMap('${af.map.position_latlng}');">查看</a>
	       </td>
       </tr>
       <tr>
         <td width="14%" nowrap="nowrap" class="title_item">公司/商户法人：</td>
         <td>${fn:escapeXml(af.map.servicecenter_corporation)}</td>
         <td width="14%" nowrap="nowrap" class="title_item">公司性质：</td>
         <td>
            <c:forEach var="cur" items="${baseData3100List}" varStatus="vs">
             <c:if test="${cur.id eq af.map.servicecenter_type}">${cur.type_name}</c:if>
            </c:forEach>
         </td>
       </tr>
       <tr>
         <td width="14%" nowrap="nowrap" class="title_item">注册时间：</td>
         <td><fmt:formatDate value="${af.map.servicecenter_build_date}" pattern="yyyy-MM-dd" /></td>
         <td width="14%" nowrap="nowrap" class="title_item">注册资金：</td>
         <td>${fn:escapeXml(af.map.servicecenter_reg_money)}&nbsp;万元
         </td>
       </tr>
       <tr>
         <td width="14%" nowrap="nowrap" class="title_item">员工人数：</td>
         <td>${fn:escapeXml(af.map.servicecenter_persons)}</td>
         <td width="14%" nowrap="nowrap" class="title_item">经营面积：</td>
         <td>${fn:escapeXml(af.map.servicecenter_area)}&nbsp;平方
         </td>
       </tr>
       <tr>
         <td width="14%" nowrap="nowrap" class="title_item">经营状况：</td>
         <td colspan="3">${fn:escapeXml(af.map.servicecenter_jy_desc)}</td>
       </tr>
       <tr>
         <td width="14%" nowrap="nowrap" class="title_item">小程序二维码：</td>
         <td colspan="3"> <img src="${ctx}/${af.map.service_qrcode_path}" width="100px"/>   </td>
       </tr>
      <tr>
        <th colspan="4">个人信息</th>
      </tr>
      <tr>
        <td class="title_item">真实姓名：</td>
        <td>${fn:escapeXml(af.map.servicecenter_linkman)}</td>
        <td class="title_item">籍贯：</td>
        <td>${fn:escapeXml(af.map.servicecenter_linkman_jg)}</td>
      </tr>
      
      <tr>
         <td class="title_item">身份证号码：</td>
        <td colspan="4">${fn:escapeXml(af.map.id_card)}</td>
      </tr>
       <tr>
          <td class="title_item" nowrap="nowrap">身份证正面：</td>
          <td nowrap="nowrap" colspan="3"><c:if test="${not empty img_id_card_zm}"> <a href="${ctx}/${img_id_card_zm}" title="身份证正面" class="cb"> <img height="100" src="${ctx}/${img_id_card_zm}@s400x400" /></a> </c:if></td>
        </tr>
        <tr>
          <td class="title_item" nowrap="nowrap">身份证背面：</td>
          <td nowrap="nowrap" colspan="3"><c:if test="${not empty img_id_card_fm}"> <a href="${ctx}/${img_id_card_fm}" title="身份证背面" class="cb"> <img height="100" src="${ctx}/${img_id_card_fm}@s400x400" /></a> </c:if></td>
        </tr>
      
      
      <tr>
        <td class="title_item">联络地址：</td>
        <td>${fn:escapeXml(af.map.servicecenter_linkman_addr)}</td>
        <td class="title_item">联络手机：</td>
        <td>${fn:escapeXml(af.map.servicecenter_linkman_tel)}</td>
      </tr>
      <tr>
        <td class="title_item">微信号：</td>
        <td>${fn:escapeXml(af.map.servicecenter_linkman_wixin_nu)}</td>
        <td class="title_item">联系QQ：</td>
        <td>${fn:escapeXml(af.map.servicecenter_linkman_qq)}</td>
      </tr>
      <tr>
        <td class="title_item">紧急联系人：</td>
        <td>${fn:escapeXml(af.map.servicecenter_help_linkman)}</td>
        <td class="title_item">紧急联系电话：</td>
        <td>${fn:escapeXml(af.map.servicecenter_help_linkman_tel)}</td>
      </tr>
       
      <tr>
        <th colspan="4">对公账户信息</th>
      </tr>
      <tr>
        <td class="title_item">对公账户名称：</td>
        <td colspan="4">${fn:escapeXml(af.map.brought_account)}</td>
      </tr>
      <tr>
       	<td class="title_item">对公账号：</td>
        <td colspan="4">${fn:escapeXml(af.map.brought_account_no)}</td>
      </tr>
     <tr>
       	<td class="title_item">开户行名称：</td>
        <td colspan="4">${fn:escapeXml(af.map.bank_name)}</td>
      </tr>
     <tr>
        <th colspan="4">审核信息</th>
     </tr>
    
    <c:if test="${af.map.effect_state eq 1}">
      <tr>
        <td nowrap="nowrap" class="title_item">确认时间：</td>
        <td colspan="3"><fmt:formatDate value="${af.map.effect_date}" pattern="yyyy-MM-dd" /></td>
      </tr>
    </c:if>
    <tr>
      <td nowrap="nowrap" class="title_item">审核状态：</td>
      <td nowrap="nowrap" colspan="3">
      <c:choose>
          <c:when test="${af.map.audit_state eq -1}"><span style=" color:#F00;">审核不通过</span></c:when>
          <c:when test="${af.map.audit_state eq 0}"><span style=" color:#F00;">待审核</span></c:when>
          <c:when test="${af.map.audit_state eq 1}"><span style=" color:#F00;">审核通过</span></c:when>
        </c:choose></td>
    </tr>
    <c:if test="${af.map.audit_state ne 0}">
      <tr>
        <td nowrap="nowrap" class="title_item">审核说明：</td>
        <td colspan="3"><c:out value="${af.map.audit_desc}" /></td>
      </tr>
    </c:if>
    <tr>
      <td colspan="4" style="text-align:center">
      <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
    </tr>
  </table>
</div>
 
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript">//<![CDATA[
function kdcx(){
	$.ajax({
		type: "POST",
		url: "${ctx}/CsAjax.do?method=getDeliveryInfoByServiceId",
		data:"service_id=${af.map.id}",
		dataType: "json",
		error: function(request, settings) {alert("系统错误，请联系管理员！");},
		success: function(result) {
			if(result.ret == 1){
				$.dialog({
					title:  "物流信息查询",
					width:  560,
					height: 280,
			        left: '100%',
			        top: '100%',
			        drag: false,
			        resize: false,
			        max: false,
			        min: false,
					content:"url:" + result.msg
				});
			}else{
				$.jBox.alert(result.msg, '提示');
			}
			
		}
	});	
}

function viewMap(latlng){
	var url = "${ctx}/CsAjax.do?method=viewBMap&latlng=" + latlng;
	$.dialog({
		title:  "查看坐标",
		width:  900,
		height: 520,
		padding: 0,
		max: false,
        min: false,
        fixed: true,
        lock: true,
		content:"url:"+ encodeURI(url)
	});
}
                                          
//]]></script>
<jsp:include page="../public_page.jsp" flush="true" />
</body>
</html>
