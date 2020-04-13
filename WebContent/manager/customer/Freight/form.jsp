<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>企业会员中心 - ${app_name}</title>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/wuliu/css/wuliu1.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/wuliu/css/wuliu2.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/commons/styles/icons/icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
<style >
.areas1 {
	margin: 5px;
	position: relative;
	z-index: 9000;
	display: inline-block;
}
.btns1 {
	z-index:88888;
}
</style>
</head>
<body>
<!-- main start -->
<c:set var="btnName" value="模版" />

<div class="mainbox mine" style="height:4000px;">
  <jsp:include page="../_nav.jsp" flush="true"/>
  <html-el:form action="/customer/Freight.do"  >
        <html-el:hidden property="queryString" styleId="queryString" />
        <html-el:hidden property="method" styleId="method" value="save" />
        <html-el:hidden property="mod_id" styleId="mod_id" />
        <html-el:hidden property="pd_id" styleId="pd_id" />
        <html-el:hidden property="p_index" styleId="p_index" />
        <html-el:hidden property="freight_id" styleId="freight_id"  value="${freight_id }"/>
        <html-el:hidden property="cls_id_old" styleId="cls_id_old" value="${af.map.cls_id}"/>
        <html-el:hidden property="par_cls_id" styleId="par_cls_id"/>
        <html-el:hidden property="pd_type" />
        <html-el:hidden property="own_entp_id" styleId="own_entp_id" value="${userInfo.own_entp_id}"/>  
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable" align="left">
         <tr><th colspan="2">模版信息</th></tr>
          <tr>
            <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>${btnName}名称：</td>
            <td colspan="2" width="88%"><html-el:text property="pd_name" styleId="pd_name" maxlength="30" style="width:280px" styleClass="webinput" /></td>
          </tr>
<!--           <tr> -->
<!-- 	        <td width="12%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>所属企业名称：</td> -->
<!-- 	        <td colspan="2" width="88%"> -->
<%-- 	        <html-el:text property="entp_name" styleId="entp_name" maxlength="125" style="width:280px" styleClass="webinput" readonly="true" onclick="openEntpChild()"/> --%>
<!-- 	         &nbsp; -->
<!-- 	         <a class="butbase" onclick="openEntpChild()" ><span class="icon-search">选择</span></a> -->
<!-- 	       </td> -->
<!-- 	      </tr> -->
          <tr>
            <td nowrap="nowrap" class="title_item" ><span style="color: #F00;">*</span>商品地址：</td>
            <html-el:hidden property="cls_id" styleId="cls_id"/>
            <td colspan="2" width="88%">
              <html-el:select property="province" styleId="province" style="width:120px;" disabled="disabled">
                <html-el:option value="">请选择...</html-el:option>
              </html-el:select>
              &nbsp;
              <html-el:select property="city" styleId="city" style="width:120px;">
                <html-el:option value="">请选择...</html-el:option>
              </html-el:select>
              &nbsp;
              <html-el:select property="country" styleId="country" style="width:120px;">
                <html-el:option value="">请选择...</html-el:option>
              </html-el:select></td>
          </tr>
          <tr>
            <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>发货时间：</td>
            <td colspan="2" width="88%">
             <html-el:select property="delivery_time" styleId="delivery_time">
                <html-el:option value="">请选择...</html-el:option>
                <html-el:option value="4">4小时</html-el:option>
                <html-el:option value="8">8小时</html-el:option>
                <html-el:option value="12">12小时</html-el:option>
                <html-el:option value="16">16小时</html-el:option>
                <html-el:option value="20">20小时</html-el:option>
                <html-el:option value="24">1天内</html-el:option>
                <html-el:option value="48">2天内</html-el:option>
                <html-el:option value="72">3天内</html-el:option>
                <html-el:option value="120">5天内</html-el:option>
                <html-el:option value="168">7天内</html-el:option>
                <html-el:option value="192">8天内</html-el:option>
                <html-el:option value="240">10天内</html-el:option>
                <html-el:option value="288">12天内</html-el:option>
                <html-el:option value="360">15天内</html-el:option>
                <html-el:option value="408">17天内</html-el:option>
                <html-el:option value="480">20天内</html-el:option>
                <html-el:option value="600">25天内</html-el:option>
                <html-el:option value="720">30天内</html-el:option>
                <html-el:option value="840">35天内</html-el:option>
                <html-el:option value="960">40天内</html-el:option>
              </html-el:select></td>
          </tr>
          <tr>
            <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>运费承担：</td>
            <td colspan="2" width="88%"><html-el:radio property="is_freeShipping" styleId="is_freeShipping" value="0">卖家承担运费</html-el:radio>
              &nbsp;&nbsp;&nbsp;&nbsp;
              <html-el:radio property="is_freeShipping" styleId="is_freeShipping" value="1">买家承担运费</html-el:radio>
              </td>
          </tr>
          <tr id="is_open_freeShipping_money_tr" style="display:none;">
            <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>是否开启满多包邮：</td>
            <td colspan="2" width="88%">
              <html-el:select property="is_open_freeShipping_money" styleId="is_open_freeShipping_money">
                <html-el:option value="0">不开启</html-el:option>
                <html-el:option value="1">开启</html-el:option>
              </html-el:select>
              <span id="open_money_freeShipping_span" style="display:none;">
              &nbsp;<html-el:text property="open_money_freeShipping" styleId="open_money_freeShipping" maxlength="10" style="width:120px" styleClass="webinput" />&nbsp;元
              </span>
           </tr>
          <tr>
            <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>计价方式： </td>
            <td colspan="2" width="88%"><html-el:radio property="valuation" styleId="valuation" value="1">按件数</html-el:radio>
              &nbsp;&nbsp;&nbsp;&nbsp;
              <html-el:radio property="valuation" styleId="valuation" value="2">按重量</html-el:radio>
             </td>
          </tr>
          <tr>
            <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>区域限售：</td>
            <td colspan="2" width="88%"><html-el:radio property="area_limit" styleId="area_limit" value="0">不支持</html-el:radio>
              &nbsp;&nbsp;&nbsp;&nbsp;
              <html-el:radio property="area_limit" styleId="area_limit" value="1">支持</html-el:radio>
              &nbsp;&nbsp;&nbsp;&nbsp; <span  style="color:#bbb"> 如果支持区域限售，商品只能在设置了运费的指定地区城市销售 </span></td>
          </tr>
          
          <!-- 循环,分别给不同的运送方式添加,并设定参数 -->
          <c:forEach var="index" begin="1" end="1" step="1">
            <tr>
              <td nowrap="nowrap" class="title_item"><span style="color: #F00;"></span>
                <html-el:checkbox property="delivery_way_${index}">
                  <c:choose>
                    <c:when test="${index eq 1}"> 快&nbsp;递： </c:when>
                  </c:choose>
                </html-el:checkbox></td>
              <td colspan="2" width="88%">
              <table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable" align="left" id="dwtable_${index}">
                  <tr>
                    <td colspan ="6"> 默认费用
                      <html-el:text property="dw${index}_first_weight" styleId="dw${index}_first_weight" maxlength="9" style="width:80px" styleClass="webinput" />
                      <span id="unit">件</span>内,
                      <html-el:text property="dw${index}_first_price" styleId="dw${index}_first_price" maxlength="9" style="width:80px" styleClass="webinput" />
                      元,每增加
                      <html-el:text property="dw${index}_sed_weight" styleId="dw${index}_sed_weight" maxlength="9" style="width:80px" styleClass="webinput" />
                      <span id="unit">件</span>,增加运费
                      <html-el:text property="dw${index}_sed_price" styleId="dw${index}_sed_price" maxlength="9" style="width:80px" styleClass="webinput" />
                      元 
                  </tr>
                  <tr>
                    <td align="center" width="30%">运送到</td>
                    <td align="center" width="12%">首<span id="unit">件</span></td>
                    <td align="center" width="12%">首费</td>
                    <td align="center" width="12%">续<span id="unit">件</span></td>
                    <td align="center" width="12%">续费</td>
                    <td align="center" width="12%"><div id="divFile"> <img src="../../images/+.gif" style="vertical-align:middle; cursor: pointer;" id="dw${index}AddTr" title="再添加一个" /></div></td>
                  </tr>
                  <tr id="dw${index}Hidden" style="display: none;">
                    <td  width="30%" align="center"><input type="hidden"   parentSign="${index }"   name="area_pindex_${index}" id="area_pindex_${index}" class="isHaveSon" />
                      <input  readonly name="area_name_${index}" id="area_name_${index}" type="text" class="webinput" maxlength="1900"   style="width:260px" onclick="showArea(this,event)" /></td>
                    <td  width="12%" align="center"><input name="first_weight_${index}" value="0" id="first_weight_${index}" type="text"  style="width:90px" class="webinput" maxlength="9"/></td>
                    <td  width="12%" align="center"><input name="first_price_${index}" id="first_price_${index}"  value="0" type="text" class="webinput" maxlength="9"   style="width:90px"/></td>
                    <td  width="12%" align="center"><input name="sed_weight_${index}" id="sed_weight_${index}"  value="0" type="text" class="webinput" maxlength="9"   style="width:90px"/></td>
                    <td  width="12%" align="center"><input name="sed_price_${index}" id="sed_price_${index}"  value="0" type="text" class="webinput" maxlength="9"   style="width:90px"/></td>
                    <td  width="15%" align="center"><img src="../../images/x.gif" style="vertical-align:middle; cursor: pointer;" id="dw${index}DelTr2" title="删除"/></td>
                  </tr>
                  
                  <!-- 作为从后台加载数据 -->
                  <tbody id="dw${index}Show">
                  </tbody>
                  <c:choose>
                    <c:when test="${index eq 1}">
                      <c:if test="${not empty delivery_way_1_list}">
                        <c:forEach var="area" items="${delivery_way_1_list}" varStatus="avs">
                          <tbody id="_dw${index}Show">
                            <tr >
                              <td   width="30%" align="center"><input parentSign="${index }"  type="hidden" name="area_pindex_${index}" id="area_pindex_${index}" value ="${area.area_pindex }" class="isHaveSon" />
                                <input  readonly    name="area_name_${index}" id="area_name_${index}" type="text" class="webinput" maxlength="1900"  value="${area.area_name }"  onclick="showArea(this,event)" style="width:260px" /></td>
                              <td  width="12%" align="center"><input name="first_weight_${index}"  id="first_weight_${index}" type="text" value="${area.first_weight }" style="width:90px" class="webinput" maxlength="9"/></td>
                              <td  width="12%" align="center"><input name="first_price_${index}" id="first_price_${index}"  type="text" class="webinput" maxlength="9" value="${area.first_price }"   style="width:90px"/></td>
                              <td  width="12%" align="center"><input name="sed_weight_${index}" id="sed_weight_${index}" type="text" class="webinput" maxlength="9"  value="${area.sed_weight }" style="width:90px"/></td>
                              <td  width="12%" align="center"><input name="sed_price_${index}" id="sed_price_${index}" type="text" class="webinput" maxlength="9"  value="${area.sed_price }"  style="width:90px"/></td>
                              <td  width="15%" align="center"><img src="../../images/x.gif" style="vertical-align:middle; cursor: pointer;" id="_dw${index}DelTr2" title="删除"/></td>
                            </tr>
                          </tbody>
                        </c:forEach>
                      </c:if>
                    </c:when>
                  </c:choose>
                </table></td>
            </tr>
            <script type="text/javascript">
          
          $(document).ready(function(){
        	  $("tr", "#dw${index}Show").each(function(){
             		var lastTR = $(this);
             	
             		//------------------------
             		$("td:last", $(this)).click(function (){
             			$(this).parent().remove();
             		}).css("cursor", "pointer");
             	});// end_edit
             	
                 $("#dw${index}AddTr").click(function (){
                 	$("#dw${index}Hidden").clone().appendTo($("#dw${index}Show")).show();
                 	var lastTR = $("tr:last", "#dw${index}Show");
                 	
                 	
                 	$("#valuation").live("click",function(){
                		var v =$(this).val();
                		if(v ==1){
                			
                			setInitControl2();
                		}else if( v ==2){
                			setInitControl();
                		}else{
                			setInitControl();
                		}
                	});
                 	
                 	$("#dw${index}DelTr2", lastTR).click(function (){
                 		$(this).parent().parent().remove();
                 	});
                 });
        	  
                 $("tr", "tbody[id='_dw${index}Show']").each(function(){
              		var lastTR = $(this);
              		//console.info(lastTR);
              		//------------------------
              		$("td:last", $(this)).click(function (){
              			$(this).parent().remove();
              		}).css("cursor", "pointer");
              	});// end_edit
        	  
          });
          </script> 
          </c:forEach>
          <tr>
            <td colspan="3" align="center">
            <html-el:button property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
          &nbsp;
          <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" />
            </td>
          </tr>
        </table>
      </html-el:form>
      <div id="areaTopdiv" class="ks-dialog ks-overlay ks-ext-position dialog-areas ks-dialog-shown ks-overlay-shown ks-dialog-focused ks-overlay-focused" style="visibility: visible; left: 142px; top: 363px; display:none;">
  <input type="hidden" name ="textId" id ="textId" />
  <a tabindex="0" href="javascript:void(" 关闭")" style="z-index: 9" class="ks-ext-close"><span class="ks-ext-close-x"></span></a>
  <div class="ks-contentbox">
    <div class="ks-stdmod-header" id="ks-dialog-header1213">
      <div class="title">选择送达方区域</div>
    </div>
    <div class="ks-stdmod-body"> 
      <!-- 存储最终选择的区域的值 -->
      <input id ="areaAllValue" name ="areaAllValue" type="hidden"/>
      <input id ="areaAllName" name ="areaAllName" type ="hidden"/>
      <ul id="J_CityList">
        <li>
          <div class=" dcity clearfix"> 
            <!-- showCityPop -->
            <div class="province-list" id="allareadiv">
              <c:forEach var="area" items="${areaList}" varStatus="avs">
                <div class="ecity" id="prodiv_${area.p_index }">
                  <input type="hidden" id="checkValue" name ="checkValue"/>
                  <input type="hidden" id="checkName" name ="checkName"/>
                  <span class="gareas">
                  <input type="checkbox" sign="${area.p_index}" value="${area.p_index }|ALL" data="${area.s_name}" id="J_Province" name ="J_Province" class="J_Province" />
                  <label for="J_Province_lable">${area.s_name }</label>
                  <span class="check_num" id="check_num" data=0 ></span> <img class="trigger" src="${ctx}/styles/wuliu/images/wuliu.gif" id="showCityImg" /> </span>
                  <div class="citys">
                    <c:forEach var ="city" items="${area[area.p_index] }" varStatus="cvs"> <span class="areas1">
                      <input type="checkbox" sign="${city.p_index}" value="${area.p_index }|${city.p_index }" data="${area.s_name }.${city.s_name}" id="J_City" name ="J_City"class="J_City" />
                      <label >${city.s_name }</label>
                      </span> </c:forEach>
                    <p style="text-align: right;">
                      <input type="button" value="关闭" class="close_button" data="${area.p_index }" id="closeCitybtn"/>
                    </p>
                  </div>
                </div>
              </c:forEach>
            </div>
          </div>
        </li>
      </ul>
      <div class="btns btns1">
      	<input type="checkbox" id="all_check" name="all_check" onclick="allCheck();" />全选
        <button type="button" class="J_Submit">确定</button>
        <button type="button" class="J_Cancel">取消</button>
      </div>
    </div>
    <div class="ks-stdmod-footer"></div>
  </div>
  <div tabindex="0" style="position: absolute;"></div>
</div>
</div>

<!-- <div class="admin divContent" style="margin-bottom:300px"> -->
      
<!--     </div> -->
 

<!-- main end -->

<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script> 
<script type="text/javascript" src="${ctx}/commons/kindeditor/kindeditor.min.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/citySelect/citySelect.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/area.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/commons.plugin.js"></script> 
 
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript">//<![CDATA[
var isCheckAll = false;
function allCheck(){
	
	if (isCheckAll) {  
        $("input[name='J_Province']").each(function() {  
            this.checked = false;  
        });  
         isCheckAll = false;  
    } else {  
         $("input[name='J_Province']").each(function() {  
             this.checked = true;  
         });  
         isCheckAll = true;  
    }  
}
 //为所有的扩展行的区域选择域添加选择方法             
 
 function showArea(obj,e){
	//清除所有的隐藏存储域,取消所选择的checkbox
	 var areaTopdiv =$("#areaTopdiv");
	 $("input[id='areaAllValue']").val("");
	 $("input[id='areaAllName']").val("");
	 $("input[id='checkValue']").val("");
	 $("input[id='checkName']").val("");
	 $(areaTopdiv).find("input[type='checkbox']").attr("checked",false);
	 
	//删除所有的记录数的span的html,以及设置data 为0
	var check_num =$(areaTopdiv).find("#check_num");
	$(check_num).html("");
	$(check_num).attr("data","0");
	//将城市的打开样式修改,查找所有的,将class样式修改为 ecity,既是 关闭状态
	var areadiv =$("#allareadiv").find("div[id^='prodiv']");
	$(areadiv).attr("class","ecity");

	 //得到top和left
	 var top =$(obj).offset().top;
	 var left=$(obj).offset().left;
	
	 //将区域显示框出现在输入框的下方
	 $(areaTopdiv).css({"posotion":"absolute","left":left,"top":top+30});
	 var sign=new Date().getTime();
	//仅仅只能保证一个最开始有sign的值,因此需要每次清理掉sign属性,在重新赋值
	//为了保证可以从sign的唯一标识中反向取得获取的数据
	  $("input[name^='area_pindex'][sign]").removeAttr("sign");
	  $("input[name^='area_name'][sign]").removeAttr("sign");
	 
	 
	 //给自身添加一个sign属性
	 $(obj).attr("sign",sign);
	 //给存储id的隐藏域也添加一个sign属性
	 $(obj).parent().find("input[id^='area_pindex']").attr("sign",sign);
	 //将获取的值设置到 textId 中
	 $("#textId").val(sign);
	 //反向,从text的框中渠道值,并加载到区域框中
		var hasValue =$("input[name^='area_pindex'][sign]").val();
	 	var valuearr =hasValue.split(",");
	 	//取得同为 同一个方式下的 数据 ,
	 	//取得焦点的对象
	 	var  thisObj =$("input[name^='area_pindex'][sign]");
	 	//得到其 table 的父元素 
	 	var parentSign= $(thisObj).attr("parentSign");
	 	var dwtable =$("#dwtable_"+parentSign);
	 	//给所有的 thisfalg标志 设置默认值
	 	$(dwtable).find("input[id='area_pindex_"+parentSign+"']").attr("thisflag","0");
		//添加一个标志 thisflag ="1"
	 	$(thisObj).attr("thisflag","1");
	 	var otherAreaValue ="";
	 		//在同一个区域框中,查找除自己外的所有的checkbox的隐藏域的值,
	 		$(dwtable).find("input[id='area_pindex_"+parentSign+"'][thisflag='0']").each(function(){
	 			var v =$(this).val();
	 			if(v!=""){
	 				otherAreaValue=otherAreaValue+$(this).val()+",";
	 			}
	 	});
	 		otherAreaValue =otherAreaValue.substring(0,otherAreaValue.length-1);
	 		var otherAreaValuearr =otherAreaValue.split(",");
	 		//遍历区域框的checkbox,将值相等的,设置为disabled
	 		 for(var i =0;i<otherAreaValuearr.length;i++){
		 		$(areaTopdiv).find("input[type='checkbox']").each(function(){
			 		////console.info($(this).val());
			 		//$(this).attr("disabled",false);
			 		if($(this).val() ==otherAreaValuearr[i] ){
			 			//如果hasValue ="*|ALL" 类型的值
			 			
			 			//console.info(otherAreaValuearr[i]);
			 			if($(this).val().indexOf("ALL") >= 0){
			 				//将自己disabled
				 			$(this).attr("disabled","disabled");
				 			//将自己的子节点也disabled
				 			$(this).parent().parent().find("input[type='checkbox']").attr("disabled","disabled");
				 		
			 				
			 			}else{
			 				
			 				//将自己disabled
				 			$(this).attr("disabled","disabled");
			 			}
			 			
			 		}
			 		
			 	});
		 	} 
	 		
	 	//遍历区域框的checkbox,将值相等的,设置为checked 为true
	 	for(var i =0;i<valuearr.length;i++){
	 		$(areaTopdiv).find("input[type='checkbox']").each(function(){
		 		
		 		if($(this).val() ==valuearr[i] ){
		 			//如果hasValue ="*|ALL" 类型的值
		 			
		 			////console.info(valuearr[i]);
		 			if($(this).val().indexOf("ALL") >= 0){
		 				//将自己勾选
			 			$(this).attr("checked",true);
		 				//
		 				$(this).removeAttr("disabled");
			 			//将自己的子节点也勾选
			 			$(this).parent().parent().find("input[type='checkbox']").attr("checked",true);
			 			$(this).parent().parent().find("input[type='checkbox']").removeAttr("disabled");
		 				
		 			}else{
		 				
		 				//将自己勾选
			 			$(this).attr("checked",true);
		 				$(this).removeAttr("disabled");
		 			}
		 		}
		 	});
	 	}

	 	areaTopdiv.find("input[type=checkbox]").each(function(){
			var check_num =$(this).parent().find("#check_num");
			//将城市的全选或者取消
			var ecitydiv =$(this).parent().parent(); 
			 	//取得省份下面的未被 disabled 的 城市的复选框
			var citycheck =$(ecitydiv).find("input[id='J_City']:checked");
			if($(citycheck).length > 0){
				$(check_num).html("<font color='red'>("+$(citycheck).length+")</font>");
			}
		}); 
		 
	 //区域框显示
	 $(areaTopdiv).show();
 }
 
 //对回写的数据进行过滤
 function filterValue(str){
	 	var allvarr = str.split(",");
		var key = "";
		var a = "";
		var re = /.*ALL/gi;
		for (var i = 0; i < allvarr.length; i++) {
			if(	re.test(allvarr[i])){
				key = key + allvarr[i].split("\|")[0] + ",";
			}
		}
		key = key.substring(0, key.length - 1);
	
		var keyarr = key.split(",");
		for (var r = 0; r < keyarr.length; r++) {
			a = a + keyarr[r] + "|ALL,";
		}
		for (var j = 0; j < allvarr.length; j++) {

			if (key.indexOf(allvarr[j].split("\|")[0]) < 0) {
				a = a + allvarr[j] + ",";
			}
		}
		a = a.substring(0, a.length - 1);
	 	return a;
 }
    
//对回写的数据进行过滤
 function filterName(str){
	 var allvarr = str.split(",");
		var key = "";
		var a = "";
		for (var i = 0; i < allvarr.length; i++) {
			if (allvarr[i].indexOf(".") < 0) {
				key = key + allvarr[i] + ",";
			}
		}
		a=key;
		for (var j = 0; j < allvarr.length; j++) {
			if (key.indexOf(allvarr[j].split("\.")[0]) < 0) {
				a = a + allvarr[j] + ",";
			}
		}
		a = a.substring(0, a.length - 1);
	 	return a;
 }
		
//输入小数
	function setOnlyNumFloat() {  
	   	$(this).css("ime-mode", "disabled");  
	    $(this).attr("t_value", "");  
	    $(this).attr("o_value", "");  
	    $(this).bind("dragenter",function(){  
	        return false;  
	    })  ;
	    $(this).keypress(function (){  
	        if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value  ;
	    }).keyup(function (){  
	        if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value  ;
	    }).blur(function (){  
	        if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value;}  
	        if(this.value.length == 0) this.value = "0";  
	    })  ; 
	    
	  
	} 
	function  unbindEvent(){
		//取出所有的事件
		$("#dw1_first_weight").unbind();
		$("#dw1_first_price").unbind();
		$("#dw1_sed_weight").unbind();
		$("#dw1_sed_price").unbind();
		$("#dw2_first_weight").unbind();
		$("#dw2_first_price").unbind();
		$("#dw2_sed_weight").unbind();
		$("#dw2_sed_price").unbind();
		$("#dw3_first_weight").unbind();
		$("#dw3_first_price").unbind();
		$("#dw3_sed_weight").unbind();
		$("#dw3_sed_price").unbind();
		
		
		//动态移除
		$("input[id^='first_weight']").unbind();
		$("input[id^='first_price']").unbind();
		$("input[id^='sed_weight']").unbind();
		$("input[id^='sed_price']").unbind();
		
		$("input[id^='first_weight']").die();
		$("input[id^='first_price']").die();
		$("input[id^='sed_weight']").die();
		$("input[id^='sed_price']").die();
		
	}
	
	//输入正整数 
	function setOnlyNumInt() {
		 $(this).css("ime-mode", "disabled");
		$(this).attr("t_value", "");
		$(this).attr("o_value", "");
		$(this).bind("dragenter",function(){
			return false;
		});
		$(this).keypress(function (){
			if(!this.value.match(/^\d*?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+]?\d+(?:\.\d+)?)?$/))this.o_value=this.value;
		}).keyup(function (){
			if(!this.value.match(/^\d*?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+]?\d+(?:\.\d+)?)?$/))this.o_value=this.value;
		}).blur(function (){
			if(!this.value.match(/^(?:[\+]?\d+(?:\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=this.value;this.o_value=this.value;};
		}); 
		
		
	
		//this.text.selected;
	}
	//页面加载完毕,初始化一些参数
	function setInitControl(){
		//var unitv =$("#valuation").val();
		unbindEvent();
	}
	
	//页面加载完毕,初始化一些参数
	function setInitControl2(){
		//var unitv =$("#valuation").val();
		unbindEvent();
	}


$(document).ready(function(){
	
	$("#is_freeShipping").live("click",function(){
		var v =$(this).val();
		if(v ==0){
			$("#is_open_freeShipping_money_tr").hide();
		}else if( v ==1){
			$("#is_open_freeShipping_money_tr").show();
		}
	});
	$("#is_open_freeShipping_money").live("change",function(){
		var v =$(this).val();
		if(v ==0){
			$("#open_money_freeShipping_span").hide();
			$("#open_money_freeShipping").removeAttr("dataType");
		}else if( v ==1){
			$("#open_money_freeShipping_span").show();
			$("#open_money_freeShipping").attr("dataType", "Number").attr("msg", "请输入开启满包邮价格");
		}
	});
	
	<c:if test="${af.map.is_freeShipping eq 1}">
	  $("#is_open_freeShipping_money_tr").show();
	</c:if>
	<c:if test="${af.map.is_open_freeShipping_money eq 1}">
	  $("#open_money_freeShipping_span").show();
	  $("#open_money_freeShipping").attr("dataType", "Number").attr("msg", "请输入开启满包邮价格");
	</c:if>
	
	/**
	*区域框 JS begin
	*
	*
	*/
	//给所有的扩展的行绑定事件
	//展示区域   开始
	//点击图片显示
	$('#showCityImg').live('click',function(){ 
		$(this).toggle(
				  function () {
					  var prodiv =$(this).parent().parent();
					  //关闭所有的
					  $(prodiv).parent().find("div[id^='prodiv_']").attr("class","ecity");
					  //打开
					  
					  
						$(prodiv).attr("class","ecity showCityPop");
				  },
				  function () {
					  
					  var prodiv =$(this).parent().parent();
						$(prodiv).attr("class","ecity");
				  }
		);

        $(this).trigger('click'); 

    }); 
	//选择省份 ,默认选中下面所有的市区
	 $("#J_Province").live("click",function(){
		var ischeck =$(this).is(":checked");
		 //将城市的全选或者取消
		 var ecitydiv =$(this).parent().parent(); 
		 	//取得省份下面的未被 disabled 的 城市的复选框
		 	var citycheck =$(ecitydiv).find("input[id='J_City'][disabled!='disabled']");
		 	//取得省份下面所有的城市复选框
		 	var citychecktotle =$(ecitydiv).find("input[id='J_City']");
		 	
		 	//添加一个标志,判断是否是全部选择,[内部可能有其他不可选的框,则数量就不会相等,那么就不是全选状态]
		 	var istruecheck=false;
		 	//判断 可选的框的数量是否等于总共的数量
		 	if(citychecktotle.length ==citycheck.length){

		 		istruecheck=true;
		 	}
		 	
		 	//选取city的index的值
		 	var citycheckvalue ="";
		 	//选取city的name的值
		 	var citycheckname ="";
		 	//查找未被禁用的框,获取name的值
		 	$(ecitydiv).find("input[id='J_City'][disabled!='disabled']").each(function(){
		 		if($(this).val()!=""){
		 			citycheckname=citycheckname+$(this).attr("data")+",";
		 		}
		 	
		 	});
		 	
		 	citycheckname=citycheckname.substring(0,citycheckname.length-1);
		 	
		 	//查找未被禁用的框,获取index的值
		 	$(ecitydiv).find("input[id='J_City'][disabled!='disabled']").each(function(){
		 		if($(this).val()!=""){
		 		citycheckvalue=citycheckvalue+$(this).val()+",";
		 		}
		 	
		 	});
		 	
		 	citycheckvalue=citycheckvalue.substring(0,citycheckvalue.length-1);
		 	
		 	//console.info("选择的="+citycheckvalue);
		 	//console.info("选择的="+citycheckname);
		 	//勾选省份的时候,将省份的check状态赋值给 city,在没有禁用的框存在的情况下,是实现全选和全部取消的功能
    		$(citycheck).attr("checked",ischeck);
		 	//保存选择的city的个数的存储域
    		var check_num =$(this).parent().find("#check_num");
    		//将选中的值存入隐藏域,并且去掉下面的城市的值,只保留省份
    		var ecityvalue =$(ecitydiv).find("#checkValue");
    		var ecityname = $(ecitydiv).find("#checkName");
    		//如果省份被选中
    		if(ischeck){
    			
    			//将数量的span中添加数量提示
    			$(check_num).html("<font color='red'>("+$(citycheck).length+")</font>");
    			//给check_num的data 赋值
    			$(check_num).attr("data",$(citycheck).length);
    			//给中间隐藏域 赋值
    			$(ecityvalue).val(citycheckvalue);
    			$(ecityname).val(citycheckname);
    			//去掉省份的选中状态
    			$(this).attr("checked",false);
    			//console.info(istruecheck);
    			//如果全部选择
    			if(istruecheck){
    				//console.info($(this).attr("checked"));
    				//将省份的index和name存入隐藏域
        			$(ecityvalue).val($(this).val());
        			$(ecityname).val($(this).attr("data"));
        			//勾选省份的选中状态
        			$(this).attr("checked",true);
    			}
    			
    			//将下面的城市也要存入进去
    		}else{
    			$(check_num).html("");
    			$(check_num).attr("data","0");
    			
    			//将省份的index 存入隐藏域,
    			$(ecityvalue).val("");
    			$(ecityname).val("");
    		}
	});
	//城市的复选框点击操作
	$("#J_City").live("click",function(){
		var ecitydiv =$(this).parent().parent().parent();
		var checknumspan =$(this).parent().parent().parent().find("#check_num") ;
		//获得span中的值,然后动态获取数据(废弃,会有问题)
		//var checknum =parseInt($(checknumspan).attr("data"));
		/* if($(this).is(":checked")){
			checknum =checknum+1;
		}else{
			checknum=checknum-1;
		} */
		//通过父节点获得所有被选中的子节点的个数
		var checknum =$(this).parent().parent().find("input[type='checkbox']:checked").length;
		//修改选中的个数
		$(checknumspan).html("<font color='red'>("+checknum+")</font>");
		$(checknumspan).attr("data",checknum);
		//保存选中的值到隐藏域
		//检查所有被选中的checkbox,将其value和data属性获取并存入对应的隐藏域中
		var proindex ="";
		var proname ="";
		var cityarr_check= $(ecitydiv).find("input[name ='J_City']:checked");
		var cityarr_all =$(ecitydiv).find("input[name ='J_City']");
		//如果城市的选中的个数等于全部的个数,则 直接默认选中省份
		var province =$(ecitydiv).find("#J_Province");
		if($(cityarr_check).length ==$(cityarr_all).length ){
			////console.info($(cityarr_check).length);
			
			//设置省份的状态为选中状态
			$(province).attr("checked",true);
			//给隐藏域赋值
			$(ecitydiv).find("#checkValue").val($(province).val());
			$(ecitydiv).find("#checkName").val($(province).attr("data"));	
			
		}else{
			//给省份的选中状态取消
			$(province).attr("checked",false);
			//将选中的城市的值存入隐藏域
			$(cityarr_check).each(function(){
				proindex=proindex+$(this).val()+",";
				proname =proname +$(this).attr("data")+",";
			});
			proindex=proindex.substring(0,proindex.length-1);
			proname =proname.substring(0,proname.length-1);
			//////console.info(proindex);
			//////console.info(proname);
			//给隐藏域赋值
			$(ecitydiv).find("#checkValue").val(proindex);
			$(ecitydiv).find("#checkName").val(proname);	
			
		}
	});
	
	//确定按钮操作
	$(".J_Submit").live("click",function(event){
		//确定是那个text框,唯一标识
		var sign =$("#textId").val();
		var areaTopdiv =$("#areaTopdiv");
		//检索全部所有选中的的checkbox的值
		
		
			var allv="";
			var alln="";
			$(areaTopdiv).find("input[type='checkbox']:checked").each(function(){
				allv=allv+$(this).val()+",";
				alln=alln+$(this).attr("data")+",";
			});
			allv=allv.substring(0,allv.length-1);
			alln=alln.substring(0,alln.length-1);
			//过滤allv 和alln的值,如果发现有a|ALL,则去掉a开头的数据
			
			//console.info("值="+allv);
			//console.info("名="+alln);
			
			allv =filterValue(allv);
			alln=filterName(alln);
			//console.info("值1="+allv);
			//console.info("名1="+alln);
			
			
			$("#areaAllValue").val(allv);
			$("#areaAllName").val(alln);
			$("input[name^='area_pindex'][sign='"+sign+"']").val(allv);
			$("input[name^='area_name'][sign='"+sign+"']").val(alln);
		//将主框体隐藏
		//获得最顶级的div		
			$(areaTopdiv).hide();
	});
	//取消按钮
	$(".J_Cancel").live("click",function(){
		//获得最顶级的div		
		var areaTopdiv =$("#areaTopdiv");
		//清除所有选中的省份和城市,
		//var pdiv=$(this).parent().parent();
		$(areaTopdiv).find("input[type='checkbox']").attr("checked",false);
		//删除所有的记录数的span的html,以及设置data 为0
		var check_num =$(areaTopdiv).find("#check_num");
		$(check_num).html("");
		$(check_num).attr("data","0");
		//清楚所有的隐藏区域的值
		var checkValue =$(areaTopdiv).find("#checkValue");
		var checkName =$(areaTopdiv).find("#checkName");
		
		$(checkValue).val("");
		$(checkName).val("");
		//将主框体隐藏
		$(areaTopdiv).hide();
	});
	
	
	
	//城市框关闭按钮,展示区域   结束
	$("#closeCitybtn").live("click",function(){
		var p_index =$(this).attr("data");
		var areadiv =$("#allareadiv").find("#prodiv_"+p_index);
		$(areadiv).attr("class","ecity");
		
	});
	
	
	/**
	*区域框 js 结束
	*
	**/
	
	
	//添加控制
	//初始化默认给定的控制类型
	 setInitControl2();
	
	
	<c:if test="${valuation_ eq 1}">
	$("span[id='unit']").each(function(){
		$(this).html("件");
	});	
	</c:if>
	<c:if test="${valuation_ eq 2}">
	$("span[id='unit']").each(function(){
		$(this).html("kg");
	});
	</c:if>
	<c:if test="${valuation_ eq 3}">
	$("span[id='unit']").each(function(){
		$(this).html("m³");	
	});
	</c:if>
	

	
//单位
$("#valuation").live("click",function(){
		var v =$(this).val();
		if(v ==1){
			$("span[id='unit']").each(function(){
				$(this).html("件");
			});	
			
			
			setInitControl2();
			
		
		}else if( v ==2){
			$("span[id='unit']").each(function(){
				$(this).html("kg");
			});
			
			setInitControl();
		}else{
			$("span[id='unit']").each(function(){
				$(this).html("m³");	
			});
			
			setInitControl();
		}
	});
	//地市选择
	$("#province").citySelect({
	        data:getAreaDic(),
	        province:"${af.map.province}",
	        city:"${af.map.city}",
	        country:"${af.map.country}",
	        province_required : true,
	        city_required : true,
	        country_required : false,
	        callback:function(selectValue,selectText){
	        	if(null != selectValue && "" != selectValue){
	        		var p_indexs = selectValue.split(",");
	        		if(null != p_indexs && p_indexs.length > 0){
	        			$("#p_index").val(p_indexs[p_indexs.length - 1]);
	        		}
	        	}
	        }
	 });
	
	
	$("#province").change(function(){
		if (this.value.length != 0) {
			this.form.p_index.value = this.value;
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
		if (this.value.length != 0) {
			this.form.p_index.value=this.value;
		} else {
			this.form.p_index.value = this.form.city.value;
		}
	});
	
	var f = document.forms[0];
	$("#price_vip").attr("dataType", "Currency").attr("require", "false").attr("msg", "请输入正确的价格");
	//模版名称
	$("#pd_name").attr("dataType", "Require").attr("msg", "请输入${btnName}名称");
	//发货时间
	
	$("#delivery_time").attr("dataType", "Require").attr("msg", "请选择发货时间");
	//是否包邮
	
	$("#is_freeShipping").attr("dataType", "Group").attr("msg", "请选择是否包邮");
	//计价方式
	
	$("#valuation").attr("dataType", "Group").attr("msg", "请选择计价方式");
	//区域限售
	
	$("#area_limit").attr("dataType", "Group").attr("msg", "请选择区域限售");
	
	//运送方式
	
	
	
	

	$("#btn_submit").click(function(){
		//$("#pd_content").val(editor.html());
		
		
		// var unitv =$("#valuation").val();
	  var  way1= $("input[name='delivery_way_1']").is(":checked");
		if(Validator.Validate(f, 1)){
			if(!(way1)){
		    	alert("运送方式必须最少选择一种!");
		    	return false;
		    }
            $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
            $("#btn_reset").attr("disabled", "true");
            $("#btn_back").attr("disabled", "true");
			f.submit();
		}
		return false;
	});
	
// 	$("input[name='J_Province']").each(function() {
// 		console.info("this.checked:"+this.checked)
// 		if(!this.checked){
// 			isCheckAll = false;
// 		}
// 	});
// 	console.info("isCheckAll:"+isCheckAll)
// 	if(isCheckAll){
// 		$("input[name='all_check']").each(function() {  
// 	        this.checked = true;  
// 	        console.info("改为true")
// 	    });  
// 	}

});

function openEntpChild(){
	
	var url = "${ctx}/BaseCsAjax.do?method=chooseEntpInfo&dir=admin";
	$.dialog({
		title:  "选择企业",
		width:  770,
		height: 550,
        lock:true ,
		content:"url:"+url
	});
}


//]]></script>

</body>
</html>