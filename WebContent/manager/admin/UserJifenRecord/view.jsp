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
  <c:set var="base_name" value="内容" />
  <c:if test="${af.map.type eq 10}"><c:set var="base_name" value="用户类型" /></c:if>
  <c:if test="${af.map.type eq 30}"><c:set var="base_name" value="热门搜索" /></c:if>
  <c:if test="${af.map.type eq 200}"><c:set var="base_name" value="会员等级" /></c:if>
  <c:if test="${af.map.type eq 300}"><c:set var="base_name" value="返现卡面值" /></c:if>
  <c:if test="${af.map.type eq 400}"><c:set var="base_name" value="卡库存预警提醒阈值" /></c:if>
  <c:if test="${af.map.type eq 500}"><c:set var="base_name" value="积分获取规则" /></c:if>
  <c:if test="${af.map.type eq 600}"><c:set var="base_name" value="手续费管理" /></c:if>
  <c:if test="${af.map.type eq 700}"><c:set var="base_name" value="返现卡返现政策" /></c:if>
  <c:if test="${af.map.type eq 750}"><c:set var="base_name" value="返现卡返现通道" /></c:if>
  <c:if test="${af.map.type eq 800}"><c:set var="base_name" value="消费币获取规则" /></c:if>
  <c:if test="${af.map.type eq 900}"><c:set var="base_name" value="币种兑换规则" /></c:if>
  <c:if test="${af.map.type eq 1000}"><c:set var="base_name" value="余额获取规则" /></c:if>
  <c:if test="${af.map.type eq 1100}"><c:set var="base_name" value="时间管理" /></c:if>
  <c:if test="${af.map.type eq 1200}"><c:set var="base_name" value="汇赚每天购买单数限制" /></c:if>
  <c:if test="${af.map.type eq 1300}"><c:set var="base_name" value="汇赚通道" /></c:if>
  <c:if test="${af.map.type eq 1400}"><c:set var="base_name" value="汇赚推广奖励" /></c:if>
  <c:if test="${af.map.type eq 2000}"><c:set var="base_name" value="内部商城配置" /></c:if>
  <c:if test="${af.map.type eq 1500}"><c:set var="base_name" value="汇赚公司收益" /></c:if>
  <c:if test="${af.map.type eq 1800}"><c:set var="base_name" value="合伙人缴费金额" /></c:if>
  <c:if test="${af.map.type eq 5000}"><c:set var="base_name" value="包邮价格" /></c:if>
  <c:if test="${af.map.type eq 3100}"><c:set var="base_name" value="公司性质" /></c:if>
  <c:if test="${af.map.type eq 3200}"><c:set var="base_name" value="安全问题" /></c:if>
  <c:if test="${af.map.type eq 9000}"><c:set var="base_name" value="敏感词库" /></c:if>
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
    <tr>
      <th colspan="2">${base_name}</th>
    </tr>
       <tr>
      <td width="15%" class="title_item">${base_name}：</td>
      <td width="85%">${fn:escapeXml(af.map.type_name)}</td>
    </tr>
    <c:if test="${af.map.type eq 5000}">
    <tr>
      <td class="title_item">包邮价格：</td>
      <td>${fn:escapeXml(af.map.pre_number)}&nbsp;（元）</td>
    </tr>
    </c:if>
    <c:if test="${af.map.type eq 6000}">
    <tr>
      <td class="title_item">每一元所获积分数：</td>
      <td>${fn:escapeXml(af.map.pre_number)}&nbsp;（积分）</td>
    </tr>
    </c:if>
     <c:if test="${af.map.type eq 6500}">
      <tr>
        <td class="title_item">每一百个积分兑换元：</td>
        <td>${fn:escapeXml(af.map.pre_number)}&nbsp;（元）</td>
      </tr>
      </c:if>
     <c:if test="${af.map.type eq 6610}">
      <tr>
        <td class="title_item">注册积分数值：</td>
        <td>${fn:escapeXml(af.map.pre_number)}&nbsp;（个）</td>
      </tr>
      </c:if>
     <c:if test="${af.map.type eq 6620}">
      <tr>
        <td class="title_item">签到积分数值：</td>
        <td>${fn:escapeXml(af.map.pre_number)}&nbsp;（个）</td>
      </tr>
      </c:if>
     <c:if test="${af.map.type eq 6630}">
      <tr>
        <td class="title_item">评价积分数值：</td>
        <td>${fn:escapeXml(af.map.pre_number)}&nbsp;（个）</td>
      </tr>
      </c:if>
     <c:if test="${af.map.type eq 15000}">
      <tr>
        <td class="title_item">优惠券结算比例数值：</td>
        <td>${fn:escapeXml(af.map.pre_number)}%</td>
      </tr>
      </c:if>
     <c:if test="${af.map.type eq 16000}">
      <tr>
        <td class="title_item">团购结算比例数值：</td>
        <td>${fn:escapeXml(af.map.pre_number)}%</td>
      </tr>
      </c:if>
    <c:if test="${af.map.type ne 5000}">
    <tr>
      <td class="title_item">备注：</td>
      <td>${fn:escapeXml(af.map.remark)}</td>
    </tr>
    </c:if>
    <c:if test="${af.map.type eq 200}">
    <tr>
      <td class="title_item">达到等级所需积分数：</td>
      <td>${fn:escapeXml(af.map.pre_number)}</td>
    </tr>
    </c:if>
    <tr>
      <td class="title_item">排序值：</td>
      <td>${af.map.order_value }</td>
    </tr>
    <tr>
      <td colspan="2" style="text-align:center"><input type="button" class="bgButton" value=" 返回 " onclick="history.back();" /></td>
    </tr>
  </table>
</div>

<jsp:include page="../public_page.jsp" flush="true" />
</body>
</html>