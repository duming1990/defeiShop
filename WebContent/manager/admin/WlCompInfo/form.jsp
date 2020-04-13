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
  <html-el:form action="/admin/WlCompInfo" enctype="multipart/form-data">
    <html-el:hidden property="queryString" styleId="queryString" />
    <html-el:hidden property="method" styleId="method" value="save" />
    <html-el:hidden property="mod_id" styleId="mod_id" />
    <html-el:hidden property="id" styleId="id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th colspan="3">快递公司基本信息</th>
      </tr>
<!--       <tr>-->
<!--	        <td width="15%" nowrap="nowrap" class="title_item" ><span style="color: #F00;">*</span>公司类型：</td>-->
<!--	        <td colspan="2" width="88%">-->
<!--	        <html-el:select property="comp_type" styleId="comp_type">-->
<!--                <html-el:option value="">请选择...</html-el:option>-->
<!--                <html-el:option value="1">物流公司</html-el:option>-->
<!--                <html-el:option value="0">快递公司</html-el:option>-->
<!--             </html-el:select>-->
<!--	       	</td>-->
<!--	  </tr>-->
      <tr>
        <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>快递公司名称：</td>
        <td colspan="2" width="88%"><html-el:text property="wl_comp_name" size="40" maxlength="128" styleId="wl_comp_name"  styleClass="webinput" /></td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>快递公司代码：</td>
        <td colspan="2" width="88%"><html-el:text property="wl_code" size="40" maxlength="128" styleId="wl_code"  styleClass="webinput" />
        <div style="color: #EC1212">快递公司代码必须填写，为了调用快递100API接口，快递公司代码必须按照以下规范：<a href="http://www.kuaidi100.com/download/api_kuaidi100_com(20140729).doc" target="_blank">所支持的快递公司及参数说明</a> 或者 <a href="http://www.kuaidi100.com/download/api_international(20140729).doc" target="_blank">支持的国际类快递及参数说明</a></div>
      </td>
      </tr>
      <tr>
      <td align="right" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>物流/快递公司URL：</td>
      <td colspan="2" width="88%"><html-el:text property="wl_comp_url" styleId="wl_comp_url" maxlength="100" style="width:200px" styleClass="webinput" />
      </td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item">详细地址：</td>
        <td colspan="2" width="88%"><html-el:text property="addr" maxlength="50" size="60" styleId="addr"  styleClass="webinput" /></td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item">联系人：</td>
        <td colspan="2" width="88%"><html-el:text property="link_man" maxlength="20" size="20" styleId="link_man"  styleClass="webinput" /></td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item">联系电话：</td>
        <td colspan="2" width="88%"><html-el:text property="tel" maxlength="20" size="20"  styleId="tel"  styleClass="webinput" /></td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item">传真：</td>
        <td colspan="2" width="88%"><html-el:text property="fax" maxlength="20" size="20"  styleId="fax"  styleClass="webinput" /></td>
      </tr>
      <tr>
        <td class="title_item">所在地区：</td>
        <td colspan="2" width="88%" id="city_div">
        <select name="province" id="province" class="pi_prov" style="width:120px;">
            <option value="">请选择...</option>
          </select>
          <select name="city" id="city" class="pi_city" style="width:120px;">
            <option value="">请选择...</option>
          </select>
          <select name="country" id="country" class="pi_dist" style="width:120px;">
            <option value="">请选择...</option>
          </select>
          </td>
      </tr>
      <tr>
        <td class="title_item">是否锁定：</td>
        <td colspan="2" width="88%"><html-el:select property="is_lock" styleId="is_lock">
              <html-el:option value="0">否</html-el:option>
              <html-el:option value="1">是</html-el:option>
            </html-el:select>（锁定后，数据将无法删除） </td>
      </tr>
      <tr>
        <td class="title_item">是否是合作物流/快递公司：</td>
        <td colspan="2" width="88%"><html-el:select property="is_collaborate" styleId="is_collaborate">
              <html-el:option value="0">否</html-el:option>
              <html-el:option value="1">是</html-el:option>
            </html-el:select></td>
      </tr>
      <tr>
        <td class="title_item">排序值：</td>
        <td colspan="2" width="88%"><html-el:text property="order_value"  maxlength="4" size="4" styleClass="webinput" styleId="order_value"  />
          值越大，显示越靠前，范围：0-9999 </td>
      </tr>
      <tr>
        <td colspan="3" align="center"><html-el:button property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
          &nbsp;
          <html-el:button property="" value="重 填" styleClass="bgButton" styleId="btn_reset" onclick="this.form.reset();" />
          &nbsp;
          <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
      </tr>
    </table>
  </html-el:form>
</div>
<div id="">

</div>

<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript" src="${ctx}/scripts/commons.plugin.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/area.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/citySelect.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	
	$("#city_div").citySelect({
        data:getAreaDic(),
        prov:"${af.map.province}",
        city:"${af.map.city}",
        dist:"${af.map.country}",
        prov_required:true,
        city_required:false,
        callback:function(selectValue,selectText){
        	if(null != selectValue && "" != selectValue){
        		var p_indexs = selectValue.split(",");
        		if(null != p_indexs && p_indexs.length > 0){
        			$("#p_index").val(p_indexs[p_indexs.length - 1]);
        		}
        	}
        	
        }
    });

	var f = document.forms[0];
	var regMobile = /^((\(\d{2,3}\))|(\d{3}\-))?((1[3-9]\d{9}))$/;
	var regPhone = /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/; 


	$("#wl_code").attr("datatype","Require").attr("msg","快递公司代码必须填写");
	$("#comp_type").attr("datatype", "Require").attr("msg", "请选择公司类型！");
	$("#wl_comp_name").attr("datatype","Require").attr("msg","企业名称必须填写");
	$("#order_value").attr("datatype","Number").attr("msg","排序值必须在0~9999之间的正整数");
	$("#wl_comp_url").attr("dataType", "Url2").attr("msg", "物流/快递公司URL必须填写").attr("require", "true");
	if('' != $.trim($("#tel").val()) && (regMobile.test($.trim($("#tel").val())) == false) && (regPhone.test($.trim($("#tel").val())) == false)){
		$("#tel").attr("datatype","Mobile").attr("msg","电话格式不正确");
	}

	if ("${af.map.comp_type}" == "0") {
		$("#wl_comp_en_name_tr").show();
		$("#wl_code").attr("datatype","Require").attr("msg","快递公司代码必须填写");
	}
//	$("#comp_type").change(function(){
//		var ts_val = $(this).val();
//		if (ts_val == 0) {
//			$("#wl_comp_en_name_tr").show();
//			$("#wl_code").attr("datatype","Require").attr("msg","快递公司代码必须填写");
//		} else {
//			$("#wl_comp_en_name_tr").hide();
//			$("#wl_code").removeAttr("datatype");
//		}
//	});
	$("#btn_submit").click(function(){
		if(Validator.Validate(f, 3)){
            $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
            $("#btn_reset").attr("disabled", "true");
            $("#btn_back").attr("disabled", "true");
			f.submit();
		}
	});
	
	});

//]]></script>
<jsp:include page="../public_page.jsp" flush="true"/>
</body>
</html>
