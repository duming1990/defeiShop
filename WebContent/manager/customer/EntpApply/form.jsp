<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body style="height:2100px;">
<div class="mainbox mine">
  <jsp:include page="../_nav.jsp" flush="true"/>
  <%@ include file="/commons/pages/messages.jsp" %>
  <html-el:form action="/customer/EntpApply.do" enctype="multipart/form-data">
    <html-el:hidden property="queryString" />
    <html-el:hidden property="method" value="save" />
    <html-el:hidden property="id" styleId="id"/>
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="par_id" />
    <html-el:hidden property="p_index" styleId="p_index" />
    <html-el:hidden property="p_index_pro" styleId="p_index_pro" />
    <html-el:hidden property="audit_state" styleId="audit_state" />
    <html-el:hidden property="audit_state_old" styleId="audit_state_old" value="${af.map.audit_state}"/>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
      <c:if test="${not empty af.map.id}">
        <tr>  
          <th colspan="4">审核信息 </th> 
        </tr>
        <tr>
          <td nowrap="nowrap" class="title_item">审核状态：</td>
          <td nowrap="nowrap" colspan="3">
            <c:choose>
              <c:when test="${af.map.audit_state eq -2}"><span class="label label-danger">审核不通过</span></c:when>
              <c:when test="${af.map.audit_state eq 0}"><span class="label label-default">待审核</span></c:when>
              <c:when test="${af.map.audit_state eq 2}"><span class="label label-success">审核通过</span></c:when>
            </c:choose>
          </td>
        </tr>
        <c:if test="${not empty af.map.audit_desc_two}">
          <tr>
            <td nowrap="nowrap" class="title_item">审核说明：</td>
            <td colspan="3"><c:out value="${af.map.audit_desc_two}" /></td>
          </tr>
        </c:if>
      </c:if>
      <tr>
        <th colspan="4">基本信息</th>
      </tr>
      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>所在地区：</td>
        <td colspan="3" id="city_div"><html-el:select property="province" styleId="province" styleClass="pi_prov" style="width:120px;">
            <html-el:option value="">请选择...</html-el:option>
          </html-el:select>
          &nbsp;
          <html-el:select property="city" styleId="city" styleClass="pi_city" style="width:120px;">
            <html-el:option value="">请选择...</html-el:option>
          </html-el:select>
          &nbsp;
          <html-el:select property="country" styleId="country" styleClass="pi_dist" style="width:120px;">
            <html-el:option value="">请选择...</html-el:option>
          </html-el:select></td>
      </tr>
      <tr>
        <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>店铺名称：</td>
        <td colspan="3"><html-el:text property="entp_name" maxlength="50" styleId="entp_name" style="width:300px"/></td>
      </tr>
      <c:if test="${not empty af.map.entp_no}">
        <tr>
          <td width="15%" nowrap="nowrap" class="title_item">店铺编号：</td>
          <td colspan="3">${af.map.entp_no}</td>
        </tr>
      </c:if>
      <tr>
        <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>门头照片：</td>
        <td colspan="3"><c:set var="img" value="${ctx}/commons/swfupload/style/images/no_image.jpg" />
          <c:if test="${not empty af.map.entp_logo}">
            <c:set var="img" value=" ${ctx}/${af.map.entp_logo}@s400x400" />
          </c:if>
          <img src="${img}" height="100" id="entp_logo_img" />
          <html-el:hidden property="entp_logo" styleId="entp_logo" />
          <div class="files-warp" id="entp_logo_warp">
            <div class="btn-files"> <span>添加附件</span>
              <input id="entp_logo_file" type="file" name="entp_logo_file"/>
            </div>
            <div class="progress"> <span class="bar"></span><span class="percent">0%</span > </div>
          </div>
          <span>说明：[图片比例]建议：1:1 [图片尺寸] 建议：[600px * 600px] ，图片大小不超过2M，图片格式支持：gif,jpg,jpeg,png,bmp,ico。</span></td>
      </tr>
      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>店铺简介：</td>
        <td colspan="3"><html-el:textarea property="entp_desc" styleId="entp_desc" rows="7" styleClass="webtextarea" style="width:620px" /></td>
      </tr>
      <tr>
        <td class="title_item">店铺详细信息：</td>
        <td colspan="3"><html-el:textarea property="entp_content" styleId="entp_content" style="width:650px;height:200px;visibility:hidden;" styleClass="webinput"></html-el:textarea>
          <div>点击【第一排】顺数【最后一个】按钮可实现全屏编辑</div></td>
      </tr>
      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>详细地址：</td>
        <td colspan="3"><html-el:text property="entp_addr" maxlength="100" styleId="entp_addr" style="width:400px" /></td>
      </tr>
      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>店铺地理位置：</td>
        <td colspan="3"><html-el:text property="entp_latlng" readonly="true" maxlength="128" style="width:250px;" styleId="entp_latlng" />
          &nbsp;
          <input type="button" value="维护坐标" onclick="getLatlng('entp_latlng')" class="bgButton" /></td>
      </tr>
      <tr>
        <td class="title_item" nowrap="nowrap"><span style="color: #F00;">*</span>有无营业执照：</td>
        <td nowrap="nowrap" colspan="3"><html-el:select property="is_has_yinye_no" styleId="is_has_yinye_no">
            <html-el:option value="1">有</html-el:option>
            <html-el:option value="0">没有</html-el:option>
          </html-el:select>
        </td>
      </tr>
      <tr id="hasYinYeNo">
        <td class="title_item" nowrap="nowrap"><span style="color: #F00;">*</span>商家法人营业执照：</td>
        <td colspan="3">营业执照编码：
          <html-el:text property="entp_licence" maxlength="50" styleId="entp_licence" style="width:150px" />
          </br>
          </br>
          营业执照扫描件：&nbsp;
          <c:set var="img" value="${ctx}/commons/swfupload/style/images/no_image.jpg" />
          <c:if test="${not empty af.map.entp_licence_img}">
            <c:set var="img" value=" ${ctx}/${fn:substringBefore(af.map.entp_licence_img, '.')}_400.${fn:substringAfter(af.map.entp_licence_img, '.')}" />
          </c:if>
          <img src="${img}" height="100" id="entp_licence_img_img" />
          <html-el:hidden property="entp_licence_img" styleId="entp_licence_img" />
          <div class="files-warp" id="entp_licence_img_warp">
            <div class="btn-files"> <span>添加附件</span>
              <input id="entp_licence_img_file" type="file" name="entp_licence_img_file"/>
            </div>
            <div class="progress"> <span class="bar"></span><span class="percent">0%</span > </div>
          </div>
          <span>说明：营业执照扫描件大小不能超过2M!图片格式支持：gif,jpg,jpeg,png,bmp,ico。</span></td>
      </tr>
      <tbody id="notHasYinYeNo" style="display:none;">
        <tr>
          <td class="title_item" nowrap="nowrap"><span style="color: #F00;">*</span>身份证正面/反面：</td>
          <td colspan="3"><div style="float:left;width:50%;">
              <c:set var="img" value="${ctx}/commons/swfupload/style/images/no_image.jpg" />
              <c:set var="img_max" value="${ctx}/commons/swfupload/style/images/no_image.jpg" />
              <c:if test="${not empty af.map.img_id_card_zm}">
                <c:set var="img" value="${ctx}/${af.map.img_id_card_zm}@s400x400" />
                <c:set var="img_max" value="${ctx}/${af.map.img_id_card_zm}" />
              </c:if>
              <a href="${img_max}" id="img_id_card_zm_a" target="_blank"><img src="${img}" height="50" id="img_id_card_zm_img" /></a>
              <html-el:hidden property="img_id_card_zm" styleId="img_id_card_zm" />
              <div class="files-warp" id="img_id_card_zm_warp"> <span>身份证正面扫描件:</span><br />
                <div class="btn-files"> <span>添加附件</span>
                  <input id="img_id_card_zm_file" type="file" name="img_id_card_zm_file"/>
                </div>
                <div class="progress"> <span class="bar"></span><span class="percent">0%</span > </div>
              </div>
            </div>
            <div style="float:left;width:50%;">
              <c:set var="img" value="${ctx}/commons/swfupload/style/images/no_image.jpg" />
              <c:set var="img_max" value="${ctx}/commons/swfupload/style/images/no_image.jpg" />
              <c:if test="${not empty af.map.img_id_card_fm}">
                <c:set var="img" value="${ctx}/${af.map.img_id_card_fm}@s400x400" />
                <c:set var="img_max" value="${ctx}/${af.map.img_id_card_fm}" />
              </c:if>
              <a href="${img_max}" id="img_id_card_fm_a" target="_blank"><img src="${img}" height="50" id="img_id_card_fm_img" /></a>
              <html-el:hidden property="img_id_card_fm" styleId="img_id_card_fm" />
              <div class="files-warp" id="img_id_card_fm_warp"> <span>身份证反面扫描件:</span><br />
                <div class="btn-files"> <span>添加附件</span>
                  <input id="img_id_card_fm_file" type="file" name="img_id_card_fm_file"/>
                </div>
                <div class="progress"> <span class="bar"></span><span class="percent">0%</span > </div>
              </div>
            </div>
            <span style="color: #F00;float:left">&nbsp;店铺所属人(法人)身份证正面图片，大小500K以内，文字须清晰可见。</span></td>
        </tr>
      </tbody>
      <tr id="hasYinYeNo">
        <td class="title_item" nowrap="nowrap"><span style="color: #F00;"></span>税务登记证<br/>
          /组织机构代码<br/>
          /生产许可证：</td>
        <td colspan="3"><div style="float:left;width:33%;">
            <c:set var="img" value="${ctx}/commons/swfupload/style/images/no_image.jpg" />
            <c:if test="${not empty af.map.tax_reg_certificate}">
              <c:set var="img" value=" ${ctx}/${fn:substringBefore(af.map.tax_reg_certificate, '.')}_400.${fn:substringAfter(af.map.tax_reg_certificate, '.')}" />
            </c:if>
            <img src="${img}" height="100" id="tax_reg_certificate_img" />
            <html-el:hidden property="tax_reg_certificate" styleId="tax_reg_certificate" />
            <div class="files-warp" id="tax_reg_certificate_warp"> <span>税务登记证扫描件:</span><br />
              <div class="btn-files" width="100"> <span>添加附件</span>
                <input id="tax_reg_certificate_file" type="file" name="tax_reg_certificate_file"/>
              </div>
              <div class="progress"> <span class="bar"></span><span class="percent">0%</span > </div>
            </div>
          </div>
          <div style="float:left;width:33%;">
            <c:set var="img" value="${ctx}/commons/swfupload/style/images/no_image.jpg" />
            <c:if test="${not empty af.map.org_code}">
              <c:set var="img" value=" ${ctx}/${fn:substringBefore(af.map.org_code, '.')}_400.${fn:substringAfter(af.map.org_code, '.')}" />
            </c:if>
            <img src="${img}" height="100" id="org_code_img" />
            <html-el:hidden property="org_code" styleId="org_code" />
            <div class="files-warp" id="org_code_warp"> <span>组织机构代码扫描件:</span><br />
              <div class="btn-files" width="100"> <span>添加附件</span>
                <input id="org_code_file" type="file" name="org_code_file"/>
              </div>
              <div class="progress"> <span class="bar"></span><span class="percent">0%</span > </div>
            </div>
          </div>
          <div style="float:left;width:33%;">
            <c:set var="img" value="${ctx}/commons/swfupload/style/images/no_image.jpg" />
            <c:if test="${not empty af.map.production_license}">
              <c:set var="img" value=" ${ctx}/${fn:substringBefore(af.map.production_license, '.')}_400.${fn:substringAfter(af.map.production_license, '.')}" />
            </c:if>
            <img src="${img}" height="100" id="production_license_img" />
            <html-el:hidden property="production_license" styleId="production_license" />
            <div class="files-warp" id="production_license_warp"> <span>生产许可证扫描件:</span><br/>
              <div class="btn-files" width="100"> <span>添加附件</span>
                <input id="production_license_file" type="file" name="production_license_file"/>
              </div>
              <div class="progress"> <span class="bar"></span><span class="percent">0%</span > </div>
            </div>
          </div>
          <span style="float:left">说明：扫描件大小不能超过2M!图片格式支持：gif,jpg,jpeg,png,bmp,ico。</span></td>
      </tr>
      <tr id="hasYinYeNo">
        <td class="title_item" nowrap="nowrap"><span style="color: #F00;"></span>食品流通特许经营证<br/>
          /代理授权证明<br/>
          /商标注册证：</td>
        <td colspan="3"><div style="float:left;width:33%;">
            <c:set var="img" value="${ctx}/commons/swfupload/style/images/no_image.jpg" />
            <c:if test="${not empty af.map.food_liutong_texu_license}">
              <c:set var="img" value=" ${ctx}/${fn:substringBefore(af.map.food_liutong_texu_license, '.')}_400.${fn:substringAfter(af.map.food_liutong_texu_license, '.')}" />
            </c:if>
            <img src="${img}" height="100" id="food_liutong_texu_license_img" />
            <html-el:hidden property="food_liutong_texu_license" styleId="food_liutong_texu_license" />
            <div class="files-warp" id="food_liutong_texu_license_warp"> <span>食品流通特许经营证扫描件:</span><br />
              <div class="btn-files" width="100"> <span>添加附件</span>
                <input id="food_liutong_texu_license_file" type="file" name="food_liutong_texu_license_file"/>
              </div>
              <div class="progress"> <span class="bar"></span><span class="percent">0%</span > </div>
            </div>
          </div>
          <div style="float:left;width:33%;">
            <c:set var="img" value="${ctx}/commons/swfupload/style/images/no_image.jpg" />
            <c:if test="${not empty af.map.proxy_author_certificate}">
              <c:set var="img" value=" ${ctx}/${fn:substringBefore(af.map.proxy_author_certificate, '.')}_400.${fn:substringAfter(af.map.proxy_author_certificate, '.')}" />
            </c:if>
            <img src="${img}" height="100" id="proxy_author_certificate_img" />
            <html-el:hidden property="proxy_author_certificate" styleId="proxy_author_certificate" />
            <div class="files-warp" id="proxy_author_certificate_warp"> <span>代理授权证明扫描件:</span><br />
              <div class="btn-files" width="100"> <span>添加附件</span>
                <input id="proxy_author_certificate_file" type="file" name="proxy_author_certificate_file"/>
              </div>
              <div class="progress"> <span class="bar"></span><span class="percent">0%</span > </div>
            </div>
          </div>
          <div style="float:left;width:33%;">
            <c:set var="img" value="${ctx}/commons/swfupload/style/images/no_image.jpg" />
            <c:if test="${not empty af.map.brand_reg_license}">
              <c:set var="img" value=" ${ctx}/${fn:substringBefore(af.map.brand_reg_license, '.')}_400.${fn:substringAfter(af.map.brand_reg_license, '.')}" />
            </c:if>
            <img src="${img}" height="100" id="brand_reg_license_img" />
            <html-el:hidden property="brand_reg_license" styleId="brand_reg_license" />
            <div class="files-warp" id="brand_reg_license_warp"> <span>商标注册证扫描件:</span><br/>
              <div class="btn-files" width="100"> <span>添加附件</span>
                <input id="brand_reg_license_file" type="file" name="brand_reg_license_file"/>
              </div>
              <div class="progress"> <span class="bar"></span><span class="percent">0%</span > </div>
            </div>
          </div>
          <span style="float:left">说明：扫描件大小不能超过2M!图片格式支持：gif,jpg,jpeg,png,bmp,ico。</span></td>
      </tr>
      <tr>
        <th colspan="4">联系方式</th>
      </tr>
      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>联系人姓名：</td>
        <td colspan="3"><html-el:text property="entp_linkman" maxlength="10" styleId="entp_linkman" style="width:200px" /></td>
      </tr>
      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>联系人手机：</td>
        <td colspan="3"><html-el:text property="entp_tel" maxlength="20" styleId="entp_tel" style="width:200px" /></td>
      </tr>
      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>联系QQ：</td>
        <td colspan="3"><html-el:text property="qq" maxlength="200" styleId="qq" style="width:200px" /></td>
      </tr>
      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>营业时间：</td>
        <td colspan="3"><div style="display: none;" id="jbox_yy_time">
            <div style="padding:10px;">
              <html-el:select property="pmam_begin" styleId="pmam_begin">
                <html-el:option value="上午">上午</html-el:option>
              </html-el:select>
              &nbsp;
              <html-el:select property="time_begin" styleClass="yy_time" styleId="time_begin">
                <html-el:option value="">请选择</html-el:option>
                <c:forEach begin="5" end="12" var="cur">
                  <html-el:option value="${cur}">${cur}:00</html-el:option>
                </c:forEach>
              </html-el:select>
              &nbsp;至
              <html-el:select property="pmam_end" styleClass="yy_time" styleId="pmam_end">
                <html-el:option value="下午">下午</html-el:option>
                <html-el:option value="晚上">晚上</html-el:option>
              </html-el:select>
              &nbsp;
              <html-el:select property="time_end" styleClass="yy_time" styleId="time_end">
                <html-el:option value="">请选择</html-el:option>
                <c:forEach begin="13" end="24" var="cur">
                  <html-el:option value="${cur}">${cur}:00</html-el:option>
                </c:forEach>
              </html-el:select>
            </div>
          </div>
          <html-el:text property="yy_sj_between" maxlength="100" styleId="yy_sj_between" style="width:150px" />
          &nbsp;<html-el:button property="" value="快速选择" styleClass="bgButton" styleId="btn_select_yy_time" /></td>
      </tr>
      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>客服微信名片二维码：</td>
        <td colspan="3"><c:set var="img" value="${ctx}/commons/swfupload/style/images/no_image.jpg" />
          <c:if test="${not empty af.map.kefu_qr_code}">
            <c:set var="img" value=" ${ctx}/${af.map.kefu_qr_code}@s400x400" />
          </c:if>
          <img src="${img}" height="100" id="kefu_qr_code_img" />
          <html-el:hidden property="kefu_qr_code" styleId="kefu_qr_code" />
          <div class="files-warp" id="kefu_qr_code_warp">
            <div class="btn-files"> <span>添加附件</span>
              <input id="kefu_qr_code_file" type="file" name="kefu_qr_code_file"/>
            </div>
            <div class="progress"> <span class="bar"></span><span class="percent">0%</span > </div>
          </div>
          <span>说明：[图片比例]建议：1:1 [图片尺寸] 建议：<span style="color: red;">[280px * 280px]</span> ，图片大小不超过2M，图片格式支持：gif,jpg,jpeg,png,bmp,ico。</span></td>
      </tr>
      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>客服联系电话：</td>
        <td colspan="3"><html-el:text property="kefu_tel" maxlength="20" styleId="kefu_tel" style="width:200px" /></td>
      </tr>
      <tr>
        <td colspan="4" style="text-align:center"><html-el:button property="" value="同意以下协议并申请" styleClass="bgButton" styleId="btn_submit" />
          &nbsp;
          <html-el:button property="" value="重 填" styleClass="bgButton" styleId="btn_reset" onclick="this.form.reset();" />
          &nbsp;
          <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
      </tr>
      <tr>
        <td colspan="4" style="text-align:center"><a class="f1" href="javascript:void(0);" onclick="getEntpApplyInfo();">《店铺入驻电子协议》</a> </td>
      </tr>
    </table>
  </html-el:form>
</div>
<c:if test="${not empty af.map.audit_state}">
  <div style="position:absolute;z-index:9999;top:300px;left:40%;"> <img src="${ctx}/images/audit_status_${af.map.audit_state}.png" width="200px" height="130px" /></div>
</c:if>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript" src="${ctx}/commons/kindeditor/kindeditor.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.form.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/area.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/citySelect.js"></script>
<script type="text/javascript">//<![CDATA[

var f = document.forms[0];

$(document).ready(function(){
	
	$("#city_div").citySelect({
        data:getAreaDic(),
        province:"${af.map.province}",
        city:"${af.map.city}",
        country:"${af.map.country}",
        province_required:true,
        city_required:true,
        country_required:true,
    });
	
	var editor = KindEditor.create("textarea[id='entp_content']");
	
// 	$(".yy_time").change(function(){
// 		var pmam_begin = $("#pmam_begin").find("option:selected").text(); 
// 		var time_begin = $("#time_begin").find("option:selected").text(); 
// 		var fenge = " - ";
// 		var pmam_end = $("#pmam_end").find("option:selected").text(); 
// 		var time_end = $("#time_end").find("option:selected").text();
// 		if($("#time_end").val() > 18){
// 			$("#pmam_end").val(晚上);
// 			pmam_end = $("#pmam_end").find("option:selected").text(); 
// 		}
// 		$("#yy_sj_between").val(pmam_begin + time_begin + fenge + pmam_end + time_end);
// 	});

	$("#btn_select_yy_time").click(function(){
		//var html = "<div style='padding:10px;'>输入姓名：<input type='text' id='yourname' name='yourname' /></div>";
		var html = $("#jbox_yy_time").clone().show().html();
		var submit = function (v, h, f) {
		    if (f.time_begin == '') {
		        $.jBox.tip("请选择开始时间", 'error', { focusId: "time_begin" }); // 关闭设置 yourname 为焦点
		        return false;
		    }
		    if (f.time_end == '') {
		        $.jBox.tip("请选择结束时间", 'error', { focusId: "time_end" }); // 关闭设置 yourname 为焦点
		        return false;
		    }

			var pmam_begin = f.pmam_begin; 
			var time_begin = f.time_begin + ":00"; 
			var fenge = " - ";
			var pmam_end = f.pmam_end; 
			var time_end = f.time_end + ":00";
			if(f.time_end > 18){
				pmam_end = "晚上"; 
			}
			$("#yy_sj_between").val(pmam_begin + time_begin + fenge + pmam_end + time_end);

		    return true;
		};

		$.jBox(html, { title: "请选择营业时间",  top: '1400px' ,submit: submit });
	});
	
	
	var btn_name = "上传门头照片";
	if ("" != "${af.map.entp_logo}") {
		btn_name = "重新上传门头照片";
	}
	upload("entp_logo", "image", btn_name, "${ctx}");
	
	var btn_name = "上传营业执照扫描件";
	if ("" != "${af.map.entp_licence_img}") {
		btn_name = "重新上传营业执照扫描件";
	}
	upload("entp_licence_img", "image", btn_name, "${ctx}");
	
	var btn_name = "上传身份证正面";
	if ("" != "${af.map.img_id_card_zm}") {
		btn_name = "重新上传身份证正面";
	}
	upload("img_id_card_zm", "image", btn_name, "${ctx}");
	
	var btn_name = "上传身份证背面";
	if ("" != "${af.map.img_id_card_fm}") {
		btn_name = "重新上传身份证背面";
	}
	upload("img_id_card_fm", "image", btn_name, "${ctx}");
	
	var btn_name = "上传税务登记证";
	if ("" != "${af.map.tax_reg_certificate}") {
		btn_name = "重新税务登记证";
	}
	upload("tax_reg_certificate", "image", btn_name, "${ctx}");
	
	var btn_name = "上传组织机构扫描件";
	if ("" != "${af.map.org_code}") {
		btn_name = "重新上传组织机构扫描件";
	}
	upload("org_code", "image", btn_name, "${ctx}");
	
	var btn_name = "上传生产许可证";
	if ("" != "${af.map.production_license}") {
		btn_name = "重新上传生产许可证";
	}
	upload("production_license", "image", btn_name, "${ctx}");
	
	var btn_name = "上传食品流通特许经营证";
	if ("" != "${af.map.food_liutong_texu_license}") {
		btn_name = "重新上传食品流通特许经营证";
	}
	upload("food_liutong_texu_license", "image", btn_name, "${ctx}");
	
	var btn_name = "上传代理授权证明";
	if ("" != "${af.map.proxy_author_certificate}") {
		btn_name = "重新上传代理授权证明";
	}
	upload("proxy_author_certificate", "image", btn_name, "${ctx}");
	
	var btn_name = "上传商标注册证";
	if ("" != "${af.map.brand_reg_license}") {
		btn_name = "重新上传商标注册证";
	}
	upload("brand_reg_license", "image", btn_name, "${ctx}");
	
	var btn_name = "上传客服微信名片二维码";
	if ("" != "${af.map.kefu_qr_code}") {
		btn_name = "重新上传微信名片二维码";
	}
	upload("kefu_qr_code", "image", btn_name, "${ctx}");
	

	var regMobile = /^((\(\d{2,3}\))|(\d{3}\-))?((1[3-9]\d{9}))$/;
	var regPhone = /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/; 

	$("#entp_logo").attr("dataType", "Filter" ).attr("msg", "请上传门头照片").attr("accept", "bmp, gif, jpeg, jpg, png");

	$("#entp_name").attr("datatype","Require").attr("msg","店铺名称必须填写");
	
	
	$("#entp_desc").attr("datatype","Limit").attr("min","1").attr("max","1500").attr("msg","店铺简介在1500个汉字之内");
	$("#main_pd_class_names").attr("datatype","Require").attr("msg","主营产品必须填写");
	$("#entp_addr").attr("datatype","Require").attr("msg","详细地址必须填写");
	$("#entp_linkman").attr("datatype","Require").attr("msg","店铺联系人必须填写");
	$("#entp_tel").attr("dataType", "Require").attr("msg", "联系电话必须填写或格式不正确");
	$("#yy_sj_between").attr("datatype","Require").attr("msg","营业时间必须填写");
	$("#order_value").attr("datatype","Number").attr("msg","排序值必须在0~9999之间的正整数");
	$("#fanxian_rule").attr("datatype","Require").attr("msg","请选择返现政策");
	$("#qq").attr("datatype","Number").attr("msg","联系qq必须填写，且必须为数字");

	//暂时去除，2018-9-25恢复
	//$("#entp_licence").attr("datatype","Require").attr("msg","营业执照编码必须填写");
	<c:if test="${empty entp_licence_img}">
	$("#entp_licence_img").attr("dataType", "Filter" ).attr("require", "false").attr("msg", "营业执照扫描件必须上传且格式必须是(bmp,gif,jpeg,jpg,png,png)").attr("accept", "bmp, gif, jpeg, jpg, png");
	</c:if>
	
	$("#kefu_qr_code").attr("dataType", "Filter" ).attr("msg", "请上传客服二维码").attr("accept", "bmp, gif, jpeg, jpg, png");
	$("#kefu_tel").attr("datatype","Require").attr("msg","客服电话必须填写");
	
	$("#order_value").focus(setOnlyNum);
	
	<c:if test="${af.map.is_has_yinye_no eq 1}">
	<c:if test="${empty (af.map.entp_licence_img)}">
		//暂时去除，2018-9-25恢复
		//$("#entp_licence_img").attr("require", "true");
	</c:if>	
	</c:if>
	<c:if test="${af.map.is_has_yinye_no eq 0}">
		$("#notHasYinYeNo").show();
		$("#hasYinYeNo").hide();
		$("#img_id_card_zm").attr("dataType", "Filter" ).attr("require", "false").attr("msg", "身份证正面扫描件必须上传且格式必须是(bmp,gif,jpeg,jpg,png,png)").attr("accept", "bmp, gif, jpeg, jpg, png");
		$("#img_id_card_fm").attr("dataType", "Filter" ).attr("require", "false").attr("msg", "身份证反面扫描件必须上传且格式必须是(bmp,gif,jpeg,jpg,png,png)").attr("accept", "bmp, gif, jpeg, jpg, png");
		$("#entp_licence").attr("require",false);
		$("#entp_licence_img").attr("require",false);
		<c:if test="${empty (af.map.img_id_card_zm)}">
			$("#img_id_card_zm").attr("require", "true");
		</c:if>	
		<c:if test="${empty (af.map.img_id_card_fm)}">
			$("#img_id_card_fm").attr("require", "true");
		</c:if>	
	</c:if>
	
	
	$("#is_has_yinye_no").change(function(){
		var thisValue = $(this).val();
		if(thisValue == 1){
			$("#hasYinYeNo").show();
			$("#notHasYinYeNo").hide();
			//暂时去除，2018-9-25恢复
			//$("#entp_licence").attr("datatype","Require").attr("msg","营业执照编码必须填写");
			//$("#entp_licence_img").attr("dataType", "Filter" ).attr("msg", "营业执照扫描件必须上传且格式必须是(bmp,gif,jpeg,jpg,png,png)").attr("accept", "bmp, gif, jpeg, jpg, png");
			$("#img_id_card_zm").attr("require",false);
			$("#img_id_card_fm").attr("require",false);
		}else{
			$("#notHasYinYeNo").show();
			$("#hasYinYeNo").hide();
			$("#img_id_card_zm").attr("dataType", "Filter" ).attr("msg", "身份证正面扫描件必须上传且格式必须是(bmp,gif,jpeg,jpg,png,png)").attr("accept", "bmp, gif, jpeg, jpg, png");
			$("#img_id_card_fm").attr("dataType", "Filter" ).attr("msg", "身份证反面扫描件必须上传且格式必须是(bmp,gif,jpeg,jpg,png,png)").attr("accept", "bmp, gif, jpeg, jpg, png");
			$("#entp_licence").attr("require",false);
			$("#entp_licence_img").attr("require",false);
		}
	});
	
	// 提交
	$("#btn_submit").click(function(){
		$("#entp_content").val(editor.html());
		if(Validator.Validate(f, 1)){
			
			if($("#audit_state_old").val() != 0){
				var submit2 = function(v, h, f) {
					if (v == "ok") {
						 $("#audit_state").val(0);
						 $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
						 $("#btn_reset").attr("disabled", "true");
						 $("#btn_back").attr("disabled", "true");
						 document.forms[0].submit();
					}
				};
				$.jBox.confirm("企业信息修改之后，需重新审核，你确定要执行该操作？", "确定提示", submit2);
			}else{
				 $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
				 $("#btn_reset").attr("disabled", "true");
				 $("#btn_back").attr("disabled", "true");
				 f.submit();
			}
		}
	});

});

//地市选择
$("#province").change(function(){
	if (this.value.length != 0) {
		this.form.p_index.value = this.value;
		this.form.p_index_pro.value = this.value;
		$("#city").val("");
		$("#country").val("");
	}
});
$("#city").change(function(){
	if (this.value.length != 0) {
		this.form.p_index.value = this.value;
		$("#country").val("");
	} else {
		this.form.p_index.value = this.form.province.value;
	}
});
$("#country").change(function(){
	var thisVal = $(this).val();
	//查询该地区是否有合伙人
	if(null != thisVal && '' != thisVal){
	if(thisVal.length != 0) {
		$("#p_index").val(thisVal);
	} else {
		$("#p_index").val($("#city").val());
	}
  }
});

function myConfirm(tip, submit){ 
	$.jBox.confirm(tip, "${app_name}", submit, { buttons: { '确定': true, '取消': false} });
}

function setOnlyNum() {
	$(this).css("ime-mode", "disabled");
	$(this).attr("t_value", "");
	$(this).attr("o_value", "");
	$(this).bind("dragenter",function(){
		return false;
	});
	$(this).keypress(function (){
		if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value;
	}).keyup(function (){
		if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value;
	}).blur(function (){
		if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value;}
		if(this.value.length == 0) this.value = 0;
	});
}

function getmain_pd_class_names() {
	var hy_cls_id = $("#hy_cls_id").val()
	if(null == hy_cls_id || hy_cls_id == ""){
		$.jBox.tip("请先选择所属行业", "info");
		return false;
	}
	var main_pd_class_ids  =$("#main_pd_class_ids").val();
	var main_pd_class_names  =$("#main_pd_class_names").val();
	var url = "${ctx}/CsAjax.do?method=listPdClass&main_pd_class_ids=" + main_pd_class_ids +"&main_pd_class_names=" + main_pd_class_names +"&hy_cls_id=" + hy_cls_id;
	$.dialog({
		title:  "选择主营产品",
		width:  770,
		height: 500,
		padding: 0,
		max: false,
        min: false,
        fixed: true,
        lock: true,
		content:"url:"+ encodeURI(url)
	});
}

function getLatlng(obj){
	var url = "${ctx}/CsAjax.do?method=getBMap&result_id=" + obj;
	$.dialog({
		title:  "选择坐标",
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
                                          
function getEntpApplyInfo(){
	var url = "${ctx}/manager/customer/EntpApply.do?method=getEntpApplyInfo";
	$.dialog({
		title:  "店铺入驻电子协议",
		width:  770,
		height: 550,
        lock:true ,
		content:"url:"+url
	});
}                                          
                                          
//]]></script>
</body>
</html>
