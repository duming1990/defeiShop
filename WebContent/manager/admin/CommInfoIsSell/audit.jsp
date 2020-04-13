<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${app_name}</title>
<meta content="${app_name}会员中心关键字" name="keywords" />
<meta content="${app_name}会员中心介绍" name="description" />
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
</head>
<body>
<div class="divContent">
 <div class="subtitle">
      <h3>${naviString}</h3>
  </div>
  <html-el:form action="/admin/CommInfoIsSell.do"  enctype="multipart/form-data">
        <html-el:hidden property="queryString" styleId="queryString" />
        <html-el:hidden property="method" styleId="method" value="saveSell" />
        <html-el:hidden property="mod_id" styleId="mod_id" />
        <html-el:hidden property="opt_type" styleId="opt_type" value="${af.map.opt_type}"/>
        <html-el:hidden property="id" styleId="id" />
       <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
          <tr>
          <td width="14%" nowrap="nowrap" class="title_item">商品名称：</td>
          <td colspan="2">${fn:escapeXml(af.map.comm_name)}</td>
        </tr>
         <tr>
          <td nowrap="nowrap" class="title_item">副标题：</td>
          <td colspan="2">${fn:escapeXml(af.map.sub_title)}</td>
        </tr> 
        <tr>
          <td nowrap="nowrap" class="title_item">产品类别：</td>
          <td colspan="2">${fn:escapeXml(af.map.cls_name)}</td>
        </tr>
        <tr>
          <td nowrap="nowrap" class="title_item">商品编号：</td>
          <td colspan="2">${fn:escapeXml(af.map.comm_no)}</td>
        </tr>
        <tr>
          <td nowrap="nowrap" class="title_item">所属企业：</td>
          <td colspan="2">${fn:escapeXml(af.map.map.entp_name)}</td>
        </tr>
        <tr>
          <td nowrap="nowrap" class="title_item">所属区域：</td>
          <td colspan="2">${fn:escapeXml(af.map.map.full_name)}</td>
        </tr>
       
        <tr>
          <td nowrap="nowrap" class="title_item">主图地址：</td>
          <td colspan="2"><c:if test="${not empty fn:substringBefore(af.map.main_pic, '.')}"> <img src="${ctx}/${af.map.main_pic}@s400x400" height="100" /> </c:if>
            <c:if test="${empty fn:substringBefore(af.map.main_pic, '.')}"> <img src="${ctx}/images/no_img.gif" /> </c:if></td>
        </tr>
        <tr id="trFile">
          <td class="title_item">${btnName}轮播图片：</td>
          <td colspan="2"><c:forEach var="cur" items="${af.map.commImgsList}" varStatus="vs"> <span style="float:left;"><img src="${ctx}/${cur.file_path}@s400x400" height="50" width="50"/>
              <c:if test="${vs.count%4 eq 0}"><br/>
              </c:if>
              <c:if test="${vs.count%4 ne 0}">&nbsp;&nbsp;</c:if>
              </span></c:forEach></td>
        </tr>
        <tr>
          <td nowrap="nowrap" class="title_item">销售价格：</td>
          <td colspan="2">${fn:escapeXml(af.map.sale_price)}&nbsp;<span>元</span></td>
        </tr>
        <tr>
        <td class="title_item">折扣规则：</td>
        <td colspan="2">
            <c:forEach var="cur" items="${baseData700List}">
              <c:if test="${cur.id eq af.map.fanxian_rule}">${cur.type_name}</c:if>
            </c:forEach>
        </td>
      </tr>
        <c:if test="${af.map.is_sell eq 1}">
          <tr>
            <td nowrap="nowrap" class="title_item">上架时间：</td>
            <td  colspan="2" height="24"><fmt:formatDate value="${af.map.up_date}" pattern="yyyy-MM-dd" var="_up_date" />
              <c:out value="${_up_date}"/></td>
          </tr>
          <tr>
            <td nowrap="nowrap" class="title_item">下架时间：</td>
            <td  colspan="2" height="24"><fmt:formatDate value="${af.map.down_date}" pattern="yyyy-MM-dd" var="_down_date" />
              <c:out value="${_down_date}"/></td>
          </tr>
        </c:if>
          <tr>
            <td nowrap="nowrap" class="title_item">商品净重：</td>
            <td  colspan="2" height="24">${af.map.comm_weight}（单位：千克）</td>
          </tr>
         <tr>
          <td nowrap="nowrap" class="title_item">物流模版：</td>
          <td colspan="2">
          <c:if test="${not empty af.map.map.freight.fre_title}">
          ${af.map.map.freight.fre_title }
          </c:if>
          <c:if test="${ empty af.map.map.freight.fre_title}">
         	 暂无
          </c:if>
          </td>
        </tr>
        <tr>
          <td nowrap="nowrap" class="title_item">商品详细内容：</td>
          <td colspan="2">${af.map.comm_content}</td>
        </tr>
        <tr>
          <td nowrap="nowrap" class="title_item">排序值：</td>
          <td colspan="2">${fn:escapeXml(af.map.order_value)}</td>
        </tr>
      <tr>
        <th colspan="3">套餐信息</th>
      </tr>
        <tr>
        <td nowrap="nowrap" class="title_item">套餐信息：</td>
          <td colspan="2" width="88%"><table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass" >
            <tr>
              <td align="center" width="34%">套餐组合属性</td>
              <td align="center" width="33%">套餐价格</td>
              <td align="center" width="33%">套餐库存</td>
            </tr>
            <tbody id="xzShow">
                <c:forEach var="cur" items="${list_CommTczhPrice}" varStatus="vs" >
                  <tr>
                  <td align="center" id="edit_son" width="34%">
                  <c:forEach items="${cur.map.list_CommTczhAttribute}"  var="tcattr">
                    <c:out value="${tcattr.attr_name}"></c:out>&nbsp;
                    </c:forEach>
                    </td>
                    <td align="center" width="33%">${cur.comm_price}</td>
                    <td align="center" width="33%">${cur.inventory}</td>
                  </tr>
                </c:forEach>
            </tbody>
          </table></td>
        </tr>
      <tr>
        <th colspan="3">审核信息</th>
      </tr>
         <tr>
        <td width="12%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>审核状态：</td>
        <td colspan="2" width="88%"><html-el:select property="audit_state" styleId="audit_state">
            <html-el:option value="">请选择...</html-el:option>
            <html-el:option value="0">待审核</html-el:option>
            <html-el:option value="1">审核通过</html-el:option>
            <html-el:option value="-1">审核不通过</html-el:option>
          </html-el:select></td>
      </tr>
      <tr>
      	<td width="12%" nowrap="nowrap" class="title_item">审核说明：</td>
      	<td colspan="2">
      		<html-el:textarea property="audit_desc" styleClass="webinput" styleId="audit_desc"  style="width:500px; height:80px;" ></html-el:textarea>
      	</td>
      </tr>
       <tr>
        <td colspan="3" align="center"><html-el:button property="" value="审 核" styleClass="bgButton" styleId="btn_submit" />
            &nbsp;
            <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
      </tr>
      </table>
      </html-el:form>
    <div class="clear"></div>
</div>

<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
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
//]]></script>
<jsp:include page="../../../_public_page.jsp" flush="true" />
</body>
</html>