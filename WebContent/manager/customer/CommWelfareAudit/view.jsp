<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>审核列表详情</title>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body id="order-detail">
<div class="mainbox mine">
  <jsp:include page="../_nav.jsp" flush="true"/>
  <div id="content" style="width:100%;">
    <div class="mainbox mine">
      <h2>审核商品详情<span class="op-area"><a href="javascript:void(0);" onclick="history.back();">返回审核列表</a></span></h2>
      <dl class="info-section primary-info J-primary-info">
        <dt class="bunch-section__label">申请者信息</dt>
        <dd class="bunch-section__content">
        <c:if test="${type eq 1}">
        <ul class="flow-list">
            <li  style="width: 1171px">商家名称：${entity.entp_name}</li>
            <li  style="width: 1171px">商家地址：${entity.entp_addr}</li>
            <li  style="width: 1171px">联系方式：${entity.entp_tel}</li>
          </ul>
        </c:if>
        <c:if test="${type eq 2}">
        <ul class="flow-list">
            <li>代理名称：${entity.servicecenter_name}</li>
            <li>
          		代理等级：
				 <c:choose>
		          <c:when test="${entity.servicecenter_level eq 1}">省级代理</c:when>
		          <c:when test="${entity.servicecenter_level eq 2}">市级代理</c:when>
		          <c:when test="${entity.servicecenter_level eq 3}">县级代理</c:when>
	          </c:choose>
            </li>
            <li>联系方式：${entity.servicecenter_linkman_tel}</li>
          </ul>
        </c:if>
        </dd>
        
        <dt class="bunch-section__label">商品信息</dt>
        <dd class="bunch-section__content">
          <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
      <tr class="tite2">
        <th nowrap="nowrap">商品名称</th>
        <th width="8%">商品主图</th>
        <th width="6%">产品类别</th>
        <th width="8%">商品编号</th>
        <th width="6%">销售价格</th>
        <th width="6%">上下架时间</th>
        <th width="8%">商品库存</th>
        <th width="10%">商品二维码</th>
        <th width="8%">是否提供发票</th>
        <c:if test="${empty is_jd}"><th width="8%">能否自提</th></c:if>
        <th width="8%">审核状态</th>
      </tr>
      <c:forEach var="cur" items="${commList}" varStatus="vs">
        <tr align="center">
          <td align="left">
               <i class="fa fa-globe preview" onclick="getCommInfo(${cur.id});"></i>
        		<a onclick="getCommInfo(${cur.id});"> ${fnx:abbreviate(cur.comm_name, 60, '...')} </a>
            </td>
          <td><img src="${ctx}/${cur.main_pic}" width="100%" /></td>
          <td>${fn:escapeXml(cur.cls_name)}</td>
          <td>${fn:escapeXml(cur.comm_no)}</td>
          <td><fmt:formatNumber pattern="#,##0.00" value="${cur.sale_price}"/></td>
          <td><c:if test="${cur.is_sell eq 1}">
              <fmt:formatDate value="${cur.up_date}" pattern="yyyy-MM-dd" />
              <br/> 至<br/>
              <fmt:formatDate value="${cur.down_date}" pattern="yyyy-MM-dd" />
            </c:if>
            <c:if test="${cur.is_sell eq 0}"><span style="color:#F00;">暂不上架</span></c:if>
          </td>
          <td><c:if test="${not empty cur.map.count_tczh_price_inventory}">
              <c:if test="${cur.map.count_tczh_price_inventory le 0}"> <span>库存不足，请及时修改库存</span> </c:if>
              <c:if test="${cur.map.count_tczh_price_inventory gt 0}"> <span style="color:#060;">库存充足</span> </c:if>
            </c:if>
            <c:if test="${empty cur.map.count_tczh_price_inventory}"> <span style="color:#F00;">没有维护套餐，没有库存</span> </c:if>
          </td>
           <td>
            <c:if test="${ not empty cur.comm_qrcode_path}">
           <img src="${ctx}/${cur.comm_qrcode_path}" width="120"/>
            </c:if>
          </td>
          <td>
          	<c:if test="${cur.is_fapiao eq 0}"><span style="color:red;">不提供</span> </c:if>
            <c:if test="${cur.is_fapiao eq 1}"> <span style="color:#060;">提供</span> </c:if>
          </td>
          <c:if test="${empty is_jd}">
          <td>
          	<c:if test="${cur.is_ziti eq 0}"><span style="color:red;">不能自提</span> </c:if>
            <c:if test="${cur.is_ziti eq 1}"> <span style="color:#060;">能自提</span> </c:if>
          </td>
          </c:if>
          <td>
          	<c:choose>
              <c:when test="${cur.audit_state eq -1}">
              <span class="label label-danger label-block">审核不通过</span>
              <c:if test="${(not empty cur.audit_desc)}">
	            <a title="${fn:escapeXml(cur.audit_desc)}" class="label label-warning label-block" onclick="viewAuditDesc('${cur.audit_desc}');">
	            <i class="fa fa-info-circle"></i>查看原因</a> 
	          </c:if>
              </c:when>
              <c:when test="${cur.audit_state eq 0}"><span class="label label-default">待审核</span></c:when>
              <c:when test="${cur.audit_state eq 1}"><span class="label label-success">审核通过</span></c:when>
            </c:choose>
          </td>
        </tr>
        <c:if test="${vs.last eq true}">
          <c:set var="i" value="${vs.count}" />
        </c:if>
      </c:forEach>
    </table>
        </dd>
      </dl>
      <form id="af" method="post" action="${ctx}/manager/customer/CommWelfareAudit.do?method=audit&comm_welfare_id=${comm_welfare_id}" enctype="multipart/form-data">
	  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th colspan="3">审核信息</th>
      </tr>
        <tr>
          <td nowrap="nowrap" class="title_item">当前审核状态：</td>
          <td nowrap="nowrap" colspan="2">
          <c:choose>
		          <c:when test="${audit_state eq 0}"><span class="tip-default" style="color:#5bc0de ">未审核</span></c:when>
		          <c:when test="${audit_state eq 1}"><span class="tip-default" style="color:#5cb85c ">审核通过</span></c:when>
		          <c:when test="${audit_state eq -1}"><span class="tip-default" style="color:red ">审核不通过</span></c:when>
	          </c:choose>
          </td>
        </tr>
        <tr>
        <td width="12%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>审核状态：</td>
        <td colspan="2" width="88%">
	        <select name="audit_state" id="audit_state">
		        <option value="">请选择...</option>
		        <option value="0" selected="selected">待审核</option>
		        <option value="1">审核通过</option>
		        <option value="-1">审核不通过</option>
	        </select>
	     </td>
      </tr>
      <tr>
      	<td width="12%" nowrap="nowrap" class="title_item">审核说明：</td>
	    <td colspan="2">
	      <textarea name="audit_desc" id="audit_desc" style="width:500px; height:80px;" class="webinput"></textarea>
	    </td>
      </tr>
       <tr>
	        <td colspan="3" align="center"><input type="button" name="" value="审  核" id="btn_submit" class="bgButton" />
	            &nbsp;
	            <input type="button" name="" value="返  回" onclick="history.back();" id="btn_back" class="bgButton" />
	        </td>
      </tr>
      </table>
      </form>
      <div class="clear"></div>
    </div>
  </div>
</div>

<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script> 
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.cookie.js"></script> 
<script type="text/javascript" src="${ctx}/commons/scripts/public.js?v=20180503"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>

<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	
	$("#audit_state").attr("dataType", "Require").attr("msg", "请选择审核状态！");
	$("#audit_desc").attr("datatype","Limit").attr("min","0").attr("max","125").attr("msg","审核说明在125个汉字之内");
	
	var f = document.forms[0];
	$("#btn_submit").click(function(){
		if(Validator.Validate(f, 3)){
            $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
            $("#btn_reset").attr("disabled", "true");
            $("#btn_back").attr("disabled", "true");
			f.submit();
		}else{
			return false;
		}
	});
	
});

function getCommInfo(id) {
	var url = "${ctx}/manager/customer/CommInfo.do?method=view&id="+id;
	$.dialog({
		title:  "商品详情",
		width:  1000,
		height: 650,
        lock:true ,
        zIndex:"10000",
		content:"url:"+url
	});
};

function viewAuditDesc(audit_desc){
	$.dialog({
		title:  "审核不通过原因",
		width:  250,
		height: 100,
        lock:true ,
		content:audit_desc
	});
}


//]]></script>
</body>
</html>
