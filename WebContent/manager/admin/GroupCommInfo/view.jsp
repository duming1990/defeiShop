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
<link href="${ctx}/styles/entp/css/css.css" rel="stylesheet" type="text/css" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
</head>
<body>
<div class="divContent">
 <div class="subtitle">
      <h3>${naviString}</h3>
  </div>
       <table width="100%" border="0" align="left" cellpadding="0" cellspacing="0" class="tableClass">
        <tr>
          <td width="14%" nowrap="nowrap" class="title_item">商品名称：</td>
          <td colspan="2">${fn:escapeXml(af.map.comm_name)}</td>
        </tr>
          <tr>
          <td nowrap="nowrap" class="title_item">副标题：</td>
          <td colspan="2">${fn:escapeXml(af.map.sub_title)}</td>
        </tr> 
        <c:forEach var="cur" items="${commInfoCustomFieldContentList}">
        <tr>
          <td nowrap="nowrap" class="title_item">${cur.custom_field_name}：</td>
          <td>
              <c:set var="fieldContent" value=",${cur.custom_field_content}," />
              <c:forEach items="${cur.baseClsAttributeSonList}" var="cur_son">
                <c:set var="fieldsSonId" value=",${cur_son.id}," />
                <c:if test="${fn:contains(fieldContent, fieldsSonId)}" var="isContains"> ${cur_son.attr_name}&nbsp; </c:if>
              </c:forEach>
            </td>
        </tr>
      </c:forEach>
        <tr>
          <td nowrap="nowrap" class="title_item">用户名：</td>
          <td colspan="2">${fn:escapeXml(userInfo.user_name)}</td>
        </tr> 
         <tr>
          <td nowrap="nowrap" class="title_item">姓名：</td>
          <td colspan="2">${fn:escapeXml(userInfo.real_name)}</td>
        </tr> 
         <tr>
          <td nowrap="nowrap" class="title_item">联系电话：</td>
          <td colspan="2">${fn:escapeXml(userInfo.mobile)}</td>
        </tr> 
        <tr>
          <td nowrap="nowrap" class="title_item">所属品牌：</td>
          <td colspan="2">${brand_name }</td>
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
          <td colspan="2"><c:forEach var="cur" items="${af.map.commImgsList}" varStatus="vs"> <span style="float:left;"><img src="${ctx}/${cur.file_path}@s400x400"  height="50" width="50"/>
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
          <td nowrap="nowrap" class="title_item">是否上架：</td>
          <td colspan="2"><c:if test="${af.map.is_sell eq 0}">
              <c:out value="否"/>
            </c:if>
            <c:if test="${af.map.is_sell eq 1}">
              <c:out value="是"/>
            </c:if></td>
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
	   <c:if test="${af.map.comm_type eq 2}" >
	    <tr>
	      <td nowrap="nowrap" class="title_item">商品净重：</td>
	      <td  colspan="2" height="24">${af.map.comm_weight}（单位：千克）</td>
	    </tr>
	    <tr>
	      <td nowrap="nowrap" class="title_item">商品体积：</td>
	      <td  colspan="2" height="24">${af.map.comm_volume}（单位：立方米）</td>
	    </tr>
	    <tr>
	      <td nowrap="nowrap" class="title_item">物流模板：</td>
	      <td colspan="2"><c:forEach items="${wlList}" var="cur">
	          <c:if test="${cur.id eq af.map.freight_id}">${cur.fre_title}</c:if>
	        </c:forEach>
	      </td>
	    </tr>
	   </c:if>
        <tr>
          <td nowrap="nowrap" class="title_item">商品详细内容：</td>
          <td colspan="2">${af.map.comm_content}</td>
        </tr>
        <tr>
          <td nowrap="nowrap" class="title_item">购买须知：</td>
          <td colspan="2">${af.map.comm_asrc_content}</td>
        </tr>
        <tr>
          <td nowrap="nowrap" class="title_item">商品规格参数：</td>
          <td colspan="2">${af.map.comm_paramenter}</td>
        </tr>
        <tr>
          <td nowrap="nowrap" class="title_item">排序值：</td>
          <td colspan="2">${fn:escapeXml(af.map.order_value)}</td>
        </tr>
        <tr>
      <td nowrap="nowrap" class="title_item">审核状态：</td>
      <td colspan="2">
          <c:if test="${af.map.audit_state eq -1}"> <span style="color:#F00;">
              <c:out value="审核不通过"/>
              </span> </c:if>
            <c:if test="${af.map.audit_state eq 0}">
              <c:out value="待审核"/>
            </c:if>
            <c:if test="${af.map.audit_state eq 1}"> <span style="color:#060;">
              <c:out value="审核通过"/>
              </span> </c:if>
      </td>
    </tr>
        <c:if test="${(not empty af.map.audit_desc)}">
          <tr>
            <td nowrap="nowrap" class="title_item">审核说明：</td>
            <td colspan="2">${fn:escapeXml(af.map.audit_desc)}</td>
          </tr>
        </c:if>
        <tr>
      <td colspan="3" style="text-align:center">
      <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
    </tr>
      </table>
    <div class="clear"></div>
</div>
<!-- main end -->
<jsp:include page="../../../_public_page.jsp" flush="true" />
</body>
</html>