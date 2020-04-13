<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="../../commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="MSThemeCompatible" content="no" />
<meta name="MSSmartTagsPreventParsing" content="true" />
<meta name="Description" content="${app_name}" />
<meta name="Keywords" content="${app_name}" />
<title>查看购物车 - ${app_name}</title>
<jsp:include page="../../_public_header.jsp" flush="true" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/buy.css"  />
</head>
<body class="pg-buy pg-buy-process pg-buy pg-cart pg-buy-process" id="deal-buy">
  <c:url var="url" value="IndexShoppingCar.do" />
   <html-el:form action="${url}" styleClass="formAddr" method="post">
   <html-el:hidden property="method" value="saveAddr"/>
   <div class="address-field-list">
    <div class="address-field-list">
      <div class="address-field-list">
        <div class="form-field">
          <label for="address-province"><em>*</em>所在地区：</label>
          <span id="area-container">
          <select id="province" class="address-province dropdown--small" name="rel_province">
          </select>
          <select id="city" class="address-city dropdown--small" name="rel_city">
          </select>
          <select id="country" class="address-district dropdown--small" name="rel_region">
          </select>
          </span> </div>
        <div class="form-field">
          <label for="rel_addr"><em>*</em>街道地址：</label>
          <input type="text" maxlength="60" size="60" name="rel_addr" id="rel_addr" class="f-text address-detail" value="" />
        </div>
        <div class="form-field">
          <label for="rel_zip">邮政编码：</label>
          <input id="rel_zip" class="f-text address-zipcode" type="text" maxlength="20" size="10" name="rel_zip" value=""/>
        </div>
        <div class="form-field">
          <label for="address-name"><em>*</em>收货人姓名：</label>
          <input type="text" maxlength="15" size="15" name="rel_name" id="rel_name" class="f-text address-name" value=""/>
        </div>
        <div class="form-field">
          <label for="rel_phone"><em>*</em>电话号码：</label>
          <input id="rel_phone" class="f-text address-phone" type="text" maxlength="20" size="15" name="rel_phone" value="${af.map.rel_phone}" />
        </div>
        <div class="form-field">
          <div class="fl">
			<a class="btn-9" onclick="saveAddr(this)">
			<span id="saveConsigneeTitleDiv">保存收货人信息</span></a>
		</div>
        </div>
      </div>
    </div>
   </div>
  </html-el:form>
<script type="text/javascript" src="${ctx}/scripts/citySelect/citySelect.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/area.js"></script> 
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script> 
<script type="text/javascript">//<![CDATA[
var f = document.forms[0];                                         
 $(document).ready(function(){
	 $("#province").citySelect({
	        data:getAreaDic(),
	        province:"${af.map.rel_province}",
	        city:"${af.map.rel_city}",
	        country:"${af.map.rel_region}",
	        province_required : true,
	        city_required : true,
	        country_required : true,
	        callback:function(selectValue,selectText){
	        	if(null != selectValue && "" != selectValue){
	        		var p_indexs = selectValue.split(",");
	        		if(null != p_indexs && p_indexs.length > 0){
	        			$("#p_index").val(p_indexs[p_indexs.length - 1]);
	        		}
	        	}
	        }
	 });
	 
	 
	 $("#rel_name").attr("dataType", "Require").attr("msg", "请填写收货人姓名");
	 $("#rel_addr").attr("dataType", "Require").attr("msg", "请填写地址");
	 $("#rel_phone").attr("dataType", "Mobile").attr("msg", "手机号码格式不正确！");
	 $("#rel_zip").attr("datatype","Zip").attr("msg","邮政编码格式填写不正确！").attr("require", "false");
 });
 var api = frameElement.api, W = api.opener; 
function saveAddr(obj){
	if(Validator.Validate(f, 3)){
		$(obj).removeAttr("onclick");
		$.post("CsAjax.do?method=saveShippingAddress&" + $('.formAddr').serialize(),{},function(data){
			if (data.result) {
				W.showShippingAddress(data.result);
			}
		});
	}
}
 
//]]></script>
</body>
</html>
