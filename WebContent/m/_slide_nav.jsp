<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>

<div class="cate-nav">
  <div class="mall-cate-box">
    <ul class="mall-cate">
      <li class=""><a href="javascript:void(0);" data-type="category" id="category">${cls_name}<em></em></a></li>
      <li class=""><a href="javascript:void(0);" data-type="district" id="district">全部区域<em></em></a></li>
      <li class=""><a href="javascript:void(0);" data-type="sort" id="sort">默认排序<em></em></a></li>
      <li class=""><a href="javascript:void(0);" data-type="commType" id="commType">类型<em></em></a></li>
    </ul>
  </div>
  <!--弹出分类-->
<%--   <c:if test="${empty baseClassSonSonList}"></c:if> --%>
  <div class="sortdrop-wrapper hide" data-type="category" style="display: none;">
    <div class="sort-wrapper">
      <div class="sort-view">
        <div>
          <c:forEach items="${slideNavList}" var="cur" varStatus="vs">
            <section class="${select_class}" id="cate_par_${cur.content}"> <a data-main="category_${cur.id}"> <span class="${cur.title_color}">${fn:escapeXml(cur.title)}</span>
              </a> </section>
          </c:forEach>
        </div>
        <i class="m-down hide" style="display: none;"></i> </div>
      <c:forEach items="${slideNavList}" var="cur">
        <div class="sort-sider hide" style="display:none;" id="cate_son_${cur.content}" data-main="category_${cur.id}">
          <div>
            <c:forEach items="${cur.baseClassList}" var="curson">
              <section> <a class="submenu_handle" onclick="clickedCls(${(curson.par_id)},${(curson.cls_id)})"> <span>${fn:escapeXml(curson.cls_name)}</span> <em class="more"></em></a> </section>
            </c:forEach>
          </div>
          <i class="m-down hide"></i></div>
      </c:forEach>
      <div class="sort-sider hide" data-main="category_emptyMenu">
        <div></div>
      </div>
    </div>
  </div>
  <div class="sortdrop-wrapper hide" data-type="district" style="display: none;">
    <div class="sort-wrapper">
      <div class="sort-view">
        <div>
          <section class="select"> <a onclick="clickedAear('')"><span>全部区域</span> <em class="more"></em> </a> </section>
          <c:forEach var="cur" items="${sonBaseProList}" varStatus="vs">
            <section class="${select_class}" id="p_index_${cur.p_index}"> <a onclick="clickedAear('${cur.p_index}')"> <span>${cur.p_name}</span> <em class="more"></em> </a> </section>
          </c:forEach>
        </div>
        <i class="m-down hide"></i> </div>
    </div>
  </div>
  <div class="sortdrop-wrapper hide" data-type="sort" style="display: none;">
    <div class="sort-wrapper">
      <div class="sort-view">
        <div>
          <section class="select"> <a onclick="orderByParam('');"> <span>默认排序</span> </a> </section>
          <section id="orderby_orderByAddDateDesc"> <a onclick="orderByParam('orderByAddDateDesc');"> <span>最新发布</span> </a> </section>
          <section id="orderby_orderBySaleDesc"> <a onclick="orderByParam('orderBySaleDesc');"> <span>销量最高</span> </a> </section>
          <section id="orderby_orderByPriceAsc"> <a onclick="orderByParam('orderByPriceAsc');"> <span>价格最低</span> </a> </section>
          <section id="orderby_orderByPriceDesc"> <a onclick="orderByParam('orderByPriceDesc');"> <span>价格最高</span> </a> </section>
          <section id="orderby_orderByAidScaleDesc"> <a onclick="orderByParam('orderByAidScaleDesc');"> <span>扶贫比例最高</span> </a> </section>
          <section id="orderby_orderByRebateScaleDesc"> <a onclick="orderByParam('orderByRebateScaleDesc');"> <span>返现比例最高</span> </a> </section>
        </div>
      </div>
    </div>
  </div>
  <div class="sortdrop-wrapper hide" data-type="commType" style="display: none;">
    <div class="sort-wrapper">
      <div class="sort-view">
        <div>
          <section class="select"><a onclick="clickZyType('');"> <span>默认排序</span> </a> </section>
          <c:forEach var="cur" items="${commZyTypeList}" varStatus="vs">
            <section id="zingyin_${cur.index}"><a onclick="clickZyType('${cur.index}');"><span>${cur.name}</span> </a></section>
          </c:forEach>
        </div>
      </div>
    </div>
  </div>
</div>
<script type="text/javascript" src="${ctx}/scripts/iscroll/iscroll-5.1.2.min.js"></script>
<script type="text/javascript" src="${ctx}/m/js/slideNav.js"></script>
<script type="text/javascript">//<![CDATA[
var f_in_nav = $(".attrForm").get(0);                                         
$(document).ready(function(){
	if("" != "${af.map.par_cls_id}"){
	    $('[data-type="category"]').find("section").siblings('.select').removeClass('select');
		$("#cate_par_${af.map.par_cls_id}").addClass("select");
		$("#cate_son_${af.map.par_cls_id}").show();
	}
	if("" != "${af.map.root_cls_id}"){
	    $('[data-type="category"]').find("section").siblings('.select').removeClass('select');
		$("#cate_par_${af.map.root_cls_id}").addClass("select");
		$("#cate_son_${af.map.root_cls_id}").show();
	}
	
	if("" != "${af.map.p_index}"){
	    $('[data-type="district"]').find("section").siblings('.select').removeClass('select');
		var px = $("#p_index_${af.map.p_index}");
		px.addClass("select");
		$("#district").html(px.text() + '<em></em>');
	}
	if("" != "${af.map.orderByParam}"){
	    $('[data-type="sort"]').find("section").siblings('.select').removeClass('select');
	    var ob = $("#orderby_${af.map.orderByParam}");
	    ob.addClass("select");
		$("#sort").html(ob.text() + '<em></em>');
	}
	
	if("" != "${af.map.is_zingying}"){
	    $('[data-type="commType"]').find("section").siblings('.select').removeClass('select');
	    var ob = $("#zingyin_${af.map.is_zingying}");
	    ob.addClass("select");
		$("#commType").html(ob.text() + '<em></em>');
	}
});

function clickedAear(area_p_index){
	$("#p_index").val(area_p_index);
	submitForm(f_in_nav);
}
function clickedCls(root_cls_id,par_cls_id){
	if(root_cls_id){
		$("#root_cls_id").val(root_cls_id);
	}
	if(par_cls_id){
		$("#par_cls_id").val(par_cls_id);
	}
	submitForm(f_in_nav);
}
function orderByParam(orderByParam) {
	$("#orderByParam").val(orderByParam);
	submitForm(f_in_nav);
}
function clickZyType(is_zingying) {
	$("#is_zingying").val(is_zingying);
	submitForm(f_in_nav);
}

//]]></script>
