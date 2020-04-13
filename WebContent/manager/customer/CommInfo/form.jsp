<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员中心 - ${app_name}</title>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/commons/styles/icons/icons.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx}/commons/swfupload/style/default.css" type="text/css" />
<style type="text/css">
.file_hidden_class {
	height:24px;
	border-top:1px #a8a8a8 solid;
	line-height:24px;
	padding-left:6px;
	border-left:1px #a8a8a8 solid;
	border-right:1px #e6e6e6 solid;
	border-bottom:1px #e6e6e6 solid;
}
</style>
</head>
<body  style="height:4000px;">
<div class="mainbox mine">
  <jsp:include page="../_nav.jsp" flush="true"/>
  <html-el:form action="/customer/CommInfo.do"  enctype="multipart/form-data">
    <html-el:hidden property="queryString" styleId="queryString" />
    <html-el:hidden property="method" styleId="method" value="save" />
    <html-el:hidden property="mod_id" styleId="mod_id" />
    <html-el:hidden property="par_id" styleId="par_id" />
    <html-el:hidden property="p_index" styleId="p_index" />
    <html-el:hidden property="id" styleId="id" />
    <html-el:hidden property="pd_id" styleId="pd_id" />
    <html-el:hidden property="is_edit_freight" styleId="is_edit_freight"  value="N"/>
    <html-el:hidden property="brand_id" styleId="brand_id" />
    <html-el:hidden property="comm_type" styleId="comm_type" />
    <html-el:hidden property="audit_state_old" styleId="audit_state_old" value="${af.map.audit_state}"/>
    <html-el:hidden property="own_entp_id" styleId="own_entp_id" />
    <html-el:hidden property="entp_name" styleId="entp_name" />
    <html-el:hidden property="old_comm_id" styleId="old_comm_id" value="${af.map.comm_id}"/>
    <html-el:hidden property="tczh_names" styleId="tczh_names" />
    <html-el:hidden property="tczh_prices" styleId="tczh_prices" />
    <html-el:hidden property="inventorys" styleId="inventorys" />
    <html-el:hidden property="orig_prices" styleId="orig_prices" />
    <html-el:hidden property="comm_weights" styleId="comm_weights" />
    <html-el:hidden property="buyer_limit_nums" styleId="buyer_limit_nums" />
    <html-el:hidden property="tag_ids" styleId="tag_ids" />
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable" align="left">
      <c:if test="${(not empty af.map.audit_state)}">
        <tr>
          <td nowrap="nowrap" class="title_item">审核状态：</td>
          <td colspan="2">
          <c:choose>
              <c:when test="${af.map.audit_state eq -1}"><span class="tip-danger">审核不通过</span></c:when>
              <c:when test="${af.map.audit_state eq -2}"><span class="tip-danger">合伙人审核不通过</span></c:when>
              <c:when test="${af.map.audit_state eq 0}"><span class="tip-default">待审核</span></c:when>
              <c:when test="${af.map.audit_state eq 2}"><span class="tip-success">合伙人审核通过</span></c:when>
              <c:when test="${af.map.audit_state eq 1}"><span class="tip-success">审核通过</span></c:when>
            </c:choose>
          </td>
        </tr>
      </c:if>
        <c:if test="${(not empty af.map.audit_desc)}">
          <tr>
            <td nowrap="nowrap" class="title_item">审核说明：</td>
            <td colspan="2">${fn:escapeXml(af.map.audit_desc)}</td>
          </tr>
        </c:if>
        <c:if test="${(not empty af.map.audit_service_desc)}">
          <tr>
            <td nowrap="nowrap" class="title_item">合伙人审核说明：</td>
            <td colspan="2">${fn:escapeXml(af.map.audit_service_desc)}</td>
          </tr>
        </c:if>
      <c:if test="${(af.map.comm_type eq 2) || (af.map.comm_type eq 10)}">
      <tr>
        <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>商品名称：</td>
        <td colspan="2" width="88%">
          <html-el:text property="comm_name" styleId="comm_name" maxlength="128" style="width:280px" styleClass="webinput"/>
          &nbsp;
          <button class="bgButton" type="button" onclick="getCommInfo()"><i class="fa fa-search"></i>选择现有商品</button></td>
      </tr>
      </c:if>
      <c:if test="${af.map.comm_type eq 10}">
	      <tr>
	        <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>预售时间：</td>
	        <td  colspan="2" height="24"><fmt:formatDate value="${af.map.presell_date}" pattern="yyyy-MM-dd" var="_presell_date" />
	          <html-el:text property="presell_date" styleId="presell_date" size="12" maxlength="20" styleClass="webinput" readonly="true" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:DayFunc})" style="cursor:pointer;" value="${_presell_date}" /></td>
	      </tr>
      </c:if>
      <tr>
        <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>分类：</td>
        <td colspan="2" width="88%"><html-el:hidden property="cls_id" styleId="cls_id" />
          <html-el:text property="cls_name" styleId="cls_name" readonly="true" onclick="getParClsInfo();" style="cursor:pointer;" styleClass="webinput"/>
          &nbsp;
          <button class="bgButton" type="button" onclick="getParClsInfo()"><i class="fa fa-search"></i> 选择</button></td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item">商品标签分类：</td>
        <td colspan="2" width="88%"><html-el:select property="entp_comm_class_id" styleId="entp_comm_class_id">
          <html-el:option value="" disabled="disabled">请选择</html-el:option>
           <c:forEach items="${commClasslist}" var="cur">
        	<html-el:option value="${cur.id}">${cur.class_name}</html-el:option>
       	   </c:forEach>
          </html-el:select></td>
      </tr>
     <c:if test="${af.map.comm_type ne 2}">
      <tr>
        <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>商品名称：</td>
        <td colspan="2" width="88%">
          <html-el:text property="comm_name" styleId="comm_name" maxlength="128" style="width:280px" styleClass="webinput"/>
       </td>
      </tr>
     </c:if> 
      <tr>
        <td width="14%" nowrap="nowrap" class="title_item">关键字：</td>
        <td colspan="2" width="88%"><html-el:text property="key_word" styleId="key_word" maxlength="125" style="width:280px" styleClass="webinput" />&nbsp; <span class="tip-danger">前台不显示，主要用于搜索功能</span></td>
      </tr>
      <tr>
        <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>商品编号：</td>
        <td colspan="2" width="88%"><html-el:text property="comm_no" styleId="comm_no" maxlength="125" style="width:280px" styleClass="webinput" readonly="true"/></td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item"><span style="color: #F00" id="span_main_pic">*</span>商品主图：</td>
        <td colspan="2"><c:set var="img" value="${ctx}/styles/imagesPublic/user_header.png" />
          <c:if test="${not empty af.map.main_pic}">
            <c:set var="img" value=" ${ctx}/${af.map.main_pic}@s400x400" />
          </c:if>
          <img src="${img}" height="80" id="main_pic_img" />
          <html-el:hidden property="main_pic" styleId="main_pic" />
          <div class="files-warp" id="main_pic_warp">
            <div class="btn-files"> <span>上传主图</span>
              <input id="main_pic_file" type="file" name="main_pic_file" />
            </div>
            <div class="progress"> <span class="bar"></span><span class="percent">0%</span></div>
          </div>
          <c:url var="urlps" value="/IndexPs.do" />
          <span>说明：[图片比例]建议：1:1 [图片尺寸] 建议：[800px * 800px] ，图片大小不超过2M。<a href="${urlps}" class="label label-danger" target="_blank">[在线编辑图片]</a></span></td>
      </tr>
      <tr id="trFile">
        <td class="title_item"><span style="color: #F00;">*</span>商品轮播图片：</td>
        <td colspan="2" width="88%"><div class="multimage-info">
            <div class="buttonDiv"> <span id="spanButtonPlaceHolder"></span> <span id="btnCancel" style="display:none;" onclick="swfu.cancelQueue();"><img title="取消上传" src="${ctx}/commons/swfupload/style/images/cancel.png" /></span>
              <div> <span> <span class="" id="fsUploadProgress"></span> </span> </div>
            </div>
            <div class="info-wrapper">
              <div style="color:#F12A22;margin-bottom: 8px;line-height: 20px;">说明：[图片比例]建议：1:1 [图片尺寸] 建议：[800px * 800px] ，图片大小不超过2M。<a href="${urlps}" class="label label-danger" target="_blank">[在线编辑图片]</a></div>
              <div class="multimage-gallery">
                <ul id="picUl" style="padding: 0px;">
                  <c:forEach var="cur" items="${CommImgsList}" varStatus="vs">
                    <c:set var= "flag" value="" />
                    <c:set var= "src" value="${ctx}/commons/swfupload/style/images/no_image.jpg" />
                    <c:set var= "data_flag_img" value="" />
                    <c:if test="${not empty cur.file_path}">
                      <c:set var= "flag" value="${vs.count}" />
                      <c:set var= "data_flag_img" value="data_flag_img='1'" />
                      <c:set var= "src" value="${ctx}/${cur.file_path}@s400x400" />
                    </c:if>
                    <li id="primaryli${vs.count}" data-status="${flag}">
                      <div class="preview"> <img src="${src}"  width="90" height="90" ${data_flag_img} /> </div>
                        <div class="operate" style="display: block;"> 
                        <i class="toleft" onclick="toleft('primaryli${vs.count}')">左移</i> <i class="toright" onclick="toright('primaryli${vs.count}')">右移</i> <i class="del" onclick="deleteFileForNew('${cur.file_path}', 'primaryli${vs.count}', 0)">删除</i>
                          <input value="${cur.file_path}" type="text" style="display:none;" name="file_path" id="file_path"/>
                        </div>
                    </li>
                  </c:forEach>
                </ul>
              </div>
            </div>
          </div></td>
      </tr>
      <tr id="pindan_option_count">
      	<td nowrap="nowrap" class="title_item">
      		<span style="color: #F00;">*</span>拼团人数：
      	</td>
      	<td colspan="2" width="88%">
      	<html-el:text property="group_count" styleId="group_count" maxlength="4" style="width:50px" styleClass="webinput" />
      	</td>
      	
      	
      </tr>
      <tr id="pindan_option_type">
      	<td nowrap="nowrap" class="title_item">
      		<span style="color: #F00;">*</span>拼团类型：
      	</td>
      	<td colspan="2" width="88%">
      	<html-el:select property="group_type" styleId="group_type">
      		<html-el:option value="">----请选择----</html-el:option>
      		<html-el:option value="1">普通</html-el:option>
      		<html-el:option value="2">老带新</html-el:option>
      	</html-el:select>
      	</td>
      </tr>
      <tr id="pindan_option_time">
      	<td nowrap="nowrap" class="title_item">
      		<span style="color: #F00;">*</span>拼团时间：
      	</td>
      	<td colspan="2" width="88%">
      	<html-el:text property="group_time" styleId="group_time" maxlength="4" style="width:50px" styleClass="webinput" />按小时计算
      	</td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>是否上架：</td>
        <td colspan="2" width="88%"><html-el:select property="is_sell" styleId="is_sell">
            <html-el:option value="">请选择...</html-el:option>
            <html-el:option value="0">否</html-el:option>
            <html-el:option value="1">是</html-el:option>
          </html-el:select></td>
      </tr>
      <tr id="up_date_tr">
        <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>上架时间：</td>
        <td  colspan="2" height="24"><fmt:formatDate value="${af.map.up_date}" pattern="yyyy-MM-dd" var="_up_date" />
          <html-el:text property="up_date" styleId="up_date" size="12" maxlength="20" styleClass="webinput" readonly="true" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:DayFunc})" style="cursor:pointer;" value="${_up_date}" /></td>
      </tr>
      <tr id="down_date_tr">
        <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>下架时间：</td>
        <td  colspan="2" height="24"><fmt:formatDate value="${af.map.down_date}" pattern="yyyy-MM-dd" var="_down_date" />
          <html-el:text property="down_date" styleId="down_date" size="12" maxlength="20" styleClass="webinput" readonly="true" onclick="WdatePicker()" style="cursor:pointer;" value="${_down_date}" /></td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>是否返利：</td>
        <td colspan="2" width="88%"><html-el:select property="is_rebate" styleId="is_rebate">
            <html-el:option value="">请选择...</html-el:option>
            <html-el:option value="0">否</html-el:option>
            <html-el:option value="1">是</html-el:option>
          </html-el:select></td>
      </tr>
      <tr id="rebate_scale_tr">
        <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>返利比例：</td>
        <td colspan="2" width="88%"><html-el:text property="rebate_scale" styleId="rebate_scale" maxlength="8" style="width:80px" styleClass="webinput" /><b>&nbsp;%</b>
        <span style="color:rgb(241, 42, 34)">（注：比例范围0~100，最多两位小数，并且返利比例+扶贫比例不能超过100）</span></td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>是否扶贫：</td>
        <td colspan="2" width="88%"><html-el:select property="is_aid" styleId="is_aid">
            <html-el:option value="">请选择...</html-el:option>
            <html-el:option value="0">否</html-el:option>
            <html-el:option value="1">是</html-el:option>
          </html-el:select></td>
      </tr>
      <tr id="aid_scale_tr">
        <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>扶贫比例：</td>
        <td colspan="2" width="88%"><html-el:text property="aid_scale" styleId="aid_scale" maxlength="8" style="width:80px" styleClass="webinput" /><b>&nbsp;%</b>
        <span style="color:rgb(241, 42, 34)">（注：比例范围0~100，最多两位小数，并且返利比例+扶贫比例不能超过100）</span></td>
      </tr>
      <tr id="poor_tr">
        <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>扶贫对象：</td>
        <td colspan="2" width="88%">
       	  <html-el:hidden property="temp_poor_ids" styleId="temp_poor_ids" value="${temp_poor_ids}" />
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable" align="left" id="poor_table">
                <tr>
                  <th nowrap="nowrap" colspan="3"><b><a class="butbase" onclick="choosePoorInfo();"><span class="icon-search">选择</span></a></b></th>
                </tr>
                <tr id="poor_list_tr">
                	<th nowrap="nowrap" width="40%">贫困户姓名</th>
                	<th nowrap="nowrap" width="50%">行政区划</th>
                	<th nowrap="nowrap" width="10%">操作</th>
                </tr>
                <c:forEach items="${commInfoPoorsList}" var="cur">
                	<tr>
    			 <input type="hidden" name="poor_ids" value="${cur.poor_id}" />
    			 <td align="center">${cur.map.real_name}</td>
    			 <td align="center">${cur.map.p_name}</td>
    			 <td align="center"><b><a class="butbase" onclick="delPoorInfo(this,${cur.poor_id});"><span class="icon-configure">删除</span></a></b></td>
    			 </tr>
                </c:forEach>
          </table>
        </td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item">商品详细信息：</td>
        <td colspan="2"><html-el:hidden property="comm_content" styleId="comm_content" />
          <script id="comm_content_ue" name="comm_content_ue" type="text/plain" style="width:100%;height:200px;">${af.map.comm_content}</script>
          <div style="color:rgb(241, 42, 34)">图片宽度建议：800(px)，高度根据图片对应比例设定，如果宽度大于800(px)请用图片处理工具处理后再上传，图片大小不超过2M。</div></td>
      </tr>
      <tr id="freight_show">
            <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>物流模板：</td>
            <td colspan="2" width="88%">
	          <html-el:hidden property="freight_id" styleId="freight_id" />
	          <html-el:text property="fre_title" styleId="fre_title" readonly="true" onclick="getFreightInfoList();" style="cursor:pointer;" styleClass="webinput"/>
	          &nbsp;
	          <button class="bgButton" type="button" onclick="getFreightInfoList()"><i class="fa fa-search"></i> 选择</button>
            </td>
       </tr>
<!--        <tr id="weight_show"> -->
<!--         <td nowrap="nowrap" class="title_item" ><span style="color: #F00;">*</span>商品净重：</td> -->
<%--         <td colspan="2" width="88%"><html-el:text property="comm_weight" styleId="comm_weight" styleClass="webinput" maxlength="10"/> --%>
<!--             （单位：kg）</td> -->
<!--       </tr> -->
      <tr>
        <td nowrap="nowrap" class="title_item">频道选择：</td>
        <td colspan="2" id="tagDiv"></td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>商品套餐：</td>
        <td colspan="2">
        	  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable" align="left">
                <tr>
                  <th nowrap="nowrap" colspan="6"><b><a class="butbase" onclick="clone();"><span class="icon-configure">添加套餐</span></a></b></th>
                </tr>
                <tr>
                  <th nowrap="nowrap" width="50%">套餐名称</th>
                  <th nowrap="nowrap">
                  	套餐价格 
                  <div class="batch">
                       <i class="fa fa-edit icon" title="批量修改" onclick="updateAll(this);"></i>
	                     <div class="batch-input" style="display:none;">
		                    <h6>批量设置价格：</h6>
		                    <a class="close" onclick="closeUpdateAll(this);">X</a>
		                    <input name="updateAll" type="text" class="text price" style="width:55px;" size="8" maxlength="8"/>
		                    <a class="ncbtn-mini" onclick="setUpdateVal('tczh_price',this);">设置</a>
	                    </div>
	                  </div>
                  </th>
                   <c:if test="${af.map.comm_type eq 20 }">
                  <th nowrap="nowrap" id="group_price_title">
                  	拼团价格 
                  <div class="batch">
                       <i class="fa fa-edit icon" title="批量修改" onclick="updateAll(this);"></i>
	                     <div class="batch-input" style="display:none;">
		                    <h6>批量设置价格：</h6>
		                    <a class="close" onclick="closeUpdateAll(this);">X</a>
		                    <input name="updateAll" type="text" class="text price" style="width:55px;" size="8" maxlength="8"/>
		                    <a class="ncbtn-mini" onclick="setUpdateVal('tczh_price',this);">设置</a>
	                    </div>
	                  </div>
                  </th>
                  </c:if>
                   <th nowrap="nowrap">
                  	套餐重量 
                  <div class="batch">
                       <i class="fa fa-edit icon" title="批量修改" onclick="updateAll(this);"></i>
	                     <div class="batch-input" style="display:none;">
		                    <h6>批量设置价格：</h6>
		                    <a class="close" onclick="closeUpdateAll(this);">X</a>
		                    <input name="updateAll" type="text" class="text price" style="width:55px;" size="8" maxlength="8"/>
		                    <a class="ncbtn-mini" onclick="setUpdateVal('comm_weight',this);">设置</a>
	                    </div>
	                  </div>
                  </th>
                  <th nowrap="nowrap">套餐库存 
                  <div class="batch">
                       <i class="fa fa-edit icon" title="批量修改" onclick="updateAll(this);"></i>
	                     <div class="batch-input" style="display:none;">
		                    <h6>批量设置库存：</h6>
		                    <a class="close" onclick="closeUpdateAll(this);">X</a>
		                    <input name="updateAll" type="text" class="text price" style="width:55px;" size="8" maxlength="8"/>
		                    <a class="ncbtn-mini" onclick="setUpdateVal('tczh_inventory',this);">设置</a>
	                    </div>
	               </div>
                  </th>
                  <th nowrap="nowrap">操作</th>
                  </tr>
                <tbody id="tczh_tbody">
                 <c:if test="${empty list_CommTczhPrice}">
                   <tr class="clone_tr">
                      <td align="center" nowrap="nowrap"><html-el:text property="tczh_name" style="width:300px;" styleId="tczh_name" styleClass="webinput"/></td>
                      <td align="center">
                    	<html-el:text property="tczh_price" styleId="tczh_price" styleClass="webinput"  size="10" maxlength="10"  onfocus='javascript:setOnlyNum3(this);'/>&nbsp;元
                      </td>
                        <c:if test="${af.map.comm_type eq 20 }">
                      <td align="center" id="group_price_text">
                    	<html-el:text property="group_price" styleId="group_price" styleClass="webinput"  size="10" maxlength="10"  onfocus='javascript:setOnlyNum3(this);'/>&nbsp;元
                      </td>
                      </c:if>
                      <td align="center">
                    	<html-el:text property="comm_weight" styleId="comm_weight"  styleClass="webinput"  size="10" maxlength="10" value="${cur.comm_weight}" onfocus='javascript:setOnlyNum(this);'/>&nbsp;kg
                     </td>
                      <td align="center">
                    	<html-el:text property="tczh_inventory" styleId="tczh_inventory" styleClass="webinput"  size="8" maxlength="8"  onfocus='javascript:setOnlyNum3(this);'/>&nbsp;件
                      </td>
                      <td align="center"><b><a id="icon-del-flag" class="butbase but-disabled"><span class="icon-configure">删除</span></a></b></td>
                    </tr>
                    </c:if>
                  <c:if test="${not empty list_CommTczhPrice}">
                  <c:forEach var="cur" items="${list_CommTczhPrice}" varStatus="vs" >
                    <tr class="clone_tr">
                      <td align="center" nowrap="nowrap"><html-el:text property="tczh_name" style="width:300px;" styleId="tczh_name" styleClass="webinput" value="${cur.tczh_name}" /></td>
                      <td align="center">
                    	<html-el:text property="tczh_price" styleId="tczh_price" styleClass="webinput"  size="10" maxlength="10" value="${cur.comm_price}" onfocus='javascript:setOnlyNum3(this);'/>&nbsp;元
                      </td>
                      
                      <c:if test="${af.map.comm_type eq 20 }">
                      <td align="center">
                    	<html-el:text property="group_price" styleId="group_price" styleClass="webinput"  size="10" maxlength="10" value="${cur.group_price}" onfocus='javascript:setOnlyNum3(this);'/>&nbsp;元
                      </td>
                      </c:if>
                      
                       <td align="center">
                    	<html-el:text property="comm_weight" styleId="comm_weight"  styleClass="webinput"  size="10" maxlength="10" value="${cur.comm_weight}" onfocus='javascript:setOnlyNum(this);'/>&nbsp;kg
                     </td>
                      <td align="center">
                    	<html-el:text property="tczh_inventory" styleId="tczh_inventory" styleClass="webinput"  size="8" maxlength="8" value="${cur.inventory}" onfocus='javascript:setOnlyNum3(this);'/>&nbsp;件
                      </td>
                      <c:if test="${vs.count eq 1}">
                      	<td align="center"><b><a id="icon-del-flag" class="butbase but-disabled" title="默认规格，不能删除"><span class="icon-configure">删除</span></a></b></td>
                      </c:if>
                      <c:if test="${vs.count gt 1}">
                      	<td align="center"><b><a class="butbase" onclick="delClone(this);"><span class="icon-configure">删除</span></a></b></td>
                      </c:if>
                    </tr>
                  </c:forEach>
                  </c:if>
                </tbody>
              </table>
              <span style="color:red;">点击<i class="fa fa-edit icon"></i>可批量修改所在列的值。</span>
          </td>
      </tr>
      
      <tr>
        <td colspan="3" style="text-align:center"><html-el:button property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
          &nbsp;
          <html-el:button property="" value="重 填" styleClass="bgButton" styleId="btn_reset" onclick="this.form.reset();" />
          &nbsp;
          <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
      </tr>
    </table>
  </html-el:form>
  <div class="clear"></div>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/commons/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/commons/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="${ctx}/commons/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" src="${ctx}/scripts/commons.plugin.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/commons/swfupload/swfupload.min.js"></script>
<script type="text/javascript" src="${ctx}/commons/swfupload/handlers.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.form.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/citySelect.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/area.js"></script>
<script type="text/javascript">//<![CDATA[
var del_num = 0;
function clone(){
	var newtr = $(".clone_tr").last().clone();
	newtr.find("#icon-del-flag").removeClass("but-disabled");
	newtr.find("#tczh_name").val("").removeAttr("disabled");
	newtr.find("#tczh_price").val("").removeAttr("disabled");
	newtr.find("#tczh_inventory").val("").removeAttr("disabled");
	newtr.find("#icon-del-flag").attr("onclick","delClone(this);");
	$(".clone_tr").last().after(newtr);
}
function delClone(obj){
	 $(obj).parent().parent().parent().remove();
}

//输入小数
function setOnlyNum() {  
    $(this).css("ime-mode", "disabled");  
    $(this).attr("t_value", "");  
    $(this).attr("o_value", "");  
    $(this).bind("dragenter",function(){  
        return false;  
    })  
    $(this).keypress(function (){  
        if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value  
    }).keyup(function (){  
        if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value  
    }).blur(function (){  
        if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}  
        if(this.value.length == 0) this.value = "0";  
    })  
}

//获取频道列表
function getTags(p_index){
	$.post("${ctx}/BaseCsAjax.do?method=ajaxGetTags" , {
		p_index : p_index
	}, function(d){
		if(d && d.ret == 1){
			var html = "<div class='choose p-choose-wrap'>";
			html += "<div class='choose-version attr-radio li' id='choose-type'>"; 
			html += "<div class='dd'>"; 
			html += "<ul>"; 
			var tag_ids_str = "${tag_ids_str}"; 
			$.each(d.dataList, function (key, item) {
				var selected = "";
				if(tag_ids_str.indexOf(","+item['tag_id']+",")!=-1){
					selected = "selected";
				}
				html += "<li class='item "+selected+"' data-sonId='"+item['tag_id']+"' onclick='selectTag(this);'><b></b> "; 
				html += "<a href='javascript:;' class='noimg'>"+item['tag_name']+"</a> ";
				html += "</li>";    
		    });
			html += "</ul>"
			html += "</div>"
			html += "</div>"
			html += "</div>"
			$("#tagDiv").append(html);
			
		}
	});
}

var tag_ids =[];//已选择频道
//选择频道
function selectTag(obj){
	if($(obj).hasClass("selected")){
		$(obj).removeClass("selected");
		//$(obj).siblings().removeClass("selected");//其他同级对象
		removeByValue(tag_ids,$(obj).attr("data-sonid"));
	}else {
		$(obj).addClass("selected");
		tag_ids.push($(obj).attr("data-sonid"));
	}
	$("#tag_ids").val(tag_ids);
}
//移除数组内指定内容
function removeByValue(arr, val) {
	for(var i=0; i<arr.length; i++) {
	  if(arr[i] == val) {
	    arr.splice(i, 1);
	    break;
	  }
	}
}

$(document).ready(function(){
	
	var p_index = $("#p_index").val();
	if(null!=p_index && p_index!=''){
		getTags(p_index);
	}
	
		
	var btn_name = "上传主图";
	if ("" != "${af.map.user_logo}") {
		btn_name = "重新上传";
	}
	upload("main_pic", "image", btn_name, "${ctx}");
	
	
// 	$("input[id='comm_weight']").focus(setOnlyNum);
	
	var editor = UE.getEditor('comm_content_ue', {
	    toolbars: UEDITOR_CONFIG.toolbarsmin
	});
	editor.ready(function() {editor.on('showmessage', function(type, m){if (m['content'] == '本地保存成功') {return true;}});});
	//地市选择
	$("#province").citySelect({
	        data:getAreaDic(),
	        province:"${af.map.province}",
	        city:"${af.map.city}",
	        country:"${af.map.country}",
	        province_required:true,
	        city_required:true,
	        country_required:true,
	        callback:function(selectValue,selectText){
	        	if(null != selectValue && "" != selectValue){
	        		var p_indexs = selectValue.split(",");
	        		if(null != p_indexs && p_indexs.length > 0){
	        			$("#p_index").val(p_indexs[p_indexs.length - 1]);
	        		}
	        	}
	        }
	 });

	<c:if test="${(af.map.comm_type eq 2) or (not empty af.map.needFre)}">
	$("#freight_show").show();
	$("#weight_show").show();
// 	$("#comm_weight").attr("datatype", "Require").attr("msg", "请填写商品净重量！");
	$("#freight_id").attr("datatype","Require").attr("msg","请选择物流模板");
	</c:if>

	$("#pd_name").attr("datatype", "Require").attr("msg", "请选择产品！");
	$("#comm_no").attr("datatype", "Require").attr("msg", "请填写商品编号！");
	$("#entp_name").attr("datatype", "Require").attr("msg", "请选择企业！");	
	$("#comm_name").attr("datatype", "Require").attr("msg", "请填写商品名称！");
	$("#sub_title").attr("datatype", "Require").attr("msg", "请填写商品副标题！");
	$("#is_sell").attr("dataType", "Require").attr("msg", "请选择是否上架！");
	$("#up_date").attr("dataType", "Require").attr("msg", "请选择上架时间！");
	$("#down_date").attr("dataType", "Require").attr("msg", "请选择下架时间！");
// 	$("#comm_weight").attr("datatype", "Require").attr("msg", "请填写商品净重量！");
	$("#is_rebate").attr("dataType", "Require").attr("msg", "请选择是否返利！");
	$("#is_aid").attr("dataType", "Require").attr("msg", "请选择是否扶贫！");
	$("#presell_date").attr("dataType", "Require").attr("msg", "请选择预售时间！");
	//$("#group_count").attr("dataType", "Require").attr("msg", "请填写拼团人数！");
	//$("#group_type").attr("dataType", "Require").attr("msg", "请选择拼团类型！");
	//$("#group_time").attr("dataType", "Require").attr("msg", "请填写拼团有效时间！");
	
	var f = document.forms[0];
	//通过判断是否显示拼单需要填写的信息
	var comm_type = $("#comm_type").val();
	if(comm_type != "20"){//不是拼团商品，不显示拼团相关属性
		$("#pindan_option_count").hide();
		$("#pindan_option_type").hide();
		$("#pindan_option_time").hide();
		$("#group_price_text").hide();
		$("#group_price_title").hide();
	}else{
		$("#pindan_option").show();
		$("#pindan_option_type").show();
		$("#pindan_option_time").show();
		$("#group_price_text").show();
		$("#group_price_title").show();
	}

	// 通过判断是否上架控制上架时间和下架时间的显示
	if("1" != $("#is_sell").val()){
		$("#up_date_tr").hide();
        $("#down_date_tr").hide();
	}
	$("#is_sell").change(function(){
         if("1" == $(this).val()){
             $("#up_date_tr").show();
             $("#down_date_tr").show();
         }else{
             $("#up_date").val("");
             $("#down_date").val("");  
             $("#up_date_tr").hide();
             $("#down_date_tr").hide();
         }
	});
	
	// 通过判断是否返利控制返利比例的显示
	if("1" != $("#is_rebate").val()){
		$("#rebate_scale_tr").hide();
	}else {
		$("#rebate_scale").attr("dataType", "Double").attr("min", "0").attr("max", "100").attr("require", "true").attr("msg", "请填写正确的返现比例！");
	}
	$("#is_rebate").change(function(){
		if("1" == $(this).val()){
			$("#rebate_scale").attr("dataType", "Double").attr("min", "0").attr("max", "100").attr("require", "true").attr("msg", "请填写正确的返现比例！");
			$("#rebate_scale_tr").show();
		}else {
			$("#rebate_scale").removeAttr("dataType");
			$("#rebate_scale").val(0);//返现比例清0
			$("#rebate_scale_tr").hide();
		}
	});
	// 通过判断是否扶贫控制扶贫比例的显示
	if("1" != $("#is_aid").val()){
		$("#aid_scale_tr").hide();
		$("#poor_tr").hide();
	}else {
		$("#aid_scale").attr("dataType", "Double").attr("min", "0").attr("max", "100").attr("require", "true").attr("msg", "请填写正确的扶贫比例！");
		$("#pet_name").attr("dataType", "Require").attr("msg", "请选择扶贫对象！");
	}
	$("#is_aid").change(function(){
		if("1" == $(this).val()){
			$("#aid_scale").attr("dataType", "Double").attr("min", "0").attr("max", "100").attr("require", "true").attr("msg", "请填写正确的扶贫比例！");
			$("#aid_scale_tr").show();
			$("#poor_tr").show();
		}else {
			$("#aid_scale").removeAttr("dataType");
			$("#aid_scale").val(0);//扶贫比例清0
			$("#aid_scale_tr").hide();
			$("#pet_name").val("");//扶贫对象清空
			$("#poor_tr").hide();
		}
	});
	
	//返利比例与扶贫比例的限制判断
	$("#rebate_scale").blur(function(){
		var sum_scale = (parseFloat($("#rebate_scale").val()||0) + parseFloat($("#aid_scale").val()||0)).toFixed(2);
		if(sum_scale>=100){
			alert("返利比例与扶贫比例之和不能超过100，请重新填写");
			$(this).val(0);
			$(this).focus();
		}
	});
	$("#aid_scale").blur(function(){
		var sum_scale = (parseFloat($("#rebate_scale").val()||0) + parseFloat($("#aid_scale").val()||0)).toFixed(2);
		if(sum_scale>=100){
			alert("返利比例与扶贫比例之和不能超过100，请重新填写");
			$(this).val(0);
			$(this).focus();
		}
	});
	
	$("#order_value").focus(setOnlyNum2);

	$("#main_pic").attr("dataType", "Filter" ).attr("msg", "请上传格式为（bmp, gif, jpeg, jpg, png）的主图地址！").attr("require", "true").attr("accept", "bmp, gif, jpeg, jpg, png");


	
	// 主图
	$("#main_pic").change(function(){
		var img_div = $("#img_div");
		img_div.empty();
		if (this.value != '') {
			$("<img />").attr("src",this.value).appendTo(img_div).parent().show();
		}
	});
	<c:if test="${not empty af.map.main_pic}">
		$("<img />").attr("src",'${af.map.main_pic}').appendTo($("#img_div")).parent().show();
		$("#main_pic").removeAttr("dataType");
	</c:if>


	$("#btn_submit").click(function(){
		if(comm_type == "20"){
			if(!Validator.Number.test($("#group_count").val())){
				alert("拼团人数必须是数字");
				return false;
			}
			if($("#group_type").val() == ''){
				alert("请选择拼团类型");
				return false;
			}
			if(!Validator.Number.test($("#group_time").val())){
				alert("拼团有效时间必须是数字");
				return false;
			}
		}
		if("1" == $("#is_aid").val()){
			if($("input[name='poor_ids']").length==0){
				alert("请选择扶贫对象");
				return false;
			}
		}
		
		var tczh_name = [];
		var tczh_name_flag = false;
		$("input[name='tczh_name']").each(function (){
			var this_tczh_name = $(this).val();
			if(null == this_tczh_name || this_tczh_name == ''){
				//alert("请填写商品套餐名称");
				tczh_name_flag = true;
				return false;
			}
			tczh_name.push(this_tczh_name); 
		});
		if(tczh_name_flag){
			alert("请填写商品套餐名称");
			return false;
		}
		$("#tczh_names").val(tczh_name);
		
		var comm_weight = [];
		$("input[name='comm_weight']").each(function (){
			var this_comm_weight = $(this).val();
			if(null == this_comm_weight || this_comm_weight == ''){
				this_comm_weight=0;
			}
			comm_weight.push(this_comm_weight); 
		});
		$("#comm_weights").val(comm_weight);
		
		var tczh_price = [];
		var tczh_price_flag = false;
		$("input[name='tczh_price']").each(function (){
			var this_tczh_price = $(this).val();
			if(null == this_tczh_price || this_tczh_price == ''){
				tczh_price_flag = true;
				return false;
			}
			tczh_price.push(this_tczh_price); 
		});
		if(tczh_price_flag){
			alert("请填写商品套餐价格");
			return false;
		}
		$("#tczh_prices").val(tczh_price);
		
		var tczh_inventory = [];
		var tczh_inventory_flag = false;
		$("input[name='tczh_inventory']").each(function (){
			var this_inventory = $(this).val();
			if(null == this_inventory || this_inventory == ''){
				//alert("请填写商品套餐库存");
				tczh_inventory_flag = true;
				return false;
			}
			tczh_inventory.push(this_inventory); 
		});
		if(tczh_inventory_flag){
			alert("请填写商品套餐库存");
			return false;
		}
		$("#inventorys").val(tczh_inventory);
		
		if(Validator.Validate(f, 1)){
			if("1" == $("#is_sell").val()){
			 if($("#up_date").val()>=$("#down_date").val()){
				alert("下架时间必须大于上架时间");
				return false;
				}
			}
			if("1" == $("#is_aid").val()&&$("input[name^='poor_ids']").length>${baseData1901.pre_number}){
				alert("所选扶贫对象数量大于系统限制，请重新选择！");
				return false;
			}
			$("#comm_content").val(editor.getContent()); 
			if($("#audit_state_old").val() == 1 || $("#audit_state_old").val() == 2){
				var submit2 = function(v, h, f) {
					if (v == "ok") {
						$("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
				        $("#btn_reset").attr("disabled", "true");
				        $("#btn_back").attr("disabled", "true");
						document.forms[0].submit();
					}
				};
				$.jBox.confirm("确定修改！", "确定提示", submit2);
			}else{
				 $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
		         $("#btn_reset").attr("disabled", "true");
		         $("#btn_back").attr("disabled", "true");
				f.submit();
			}
		}
		return false;
	});

    $("#imgAddTr").click(function (){
		$("#divFileHidden").clone().find("#file_hidden").attr("name", "file_" + new Date().getTime()).attr("datatype","Filter").attr("msg","请上传格式为（bmp, gif, jpeg, jpg, png）的主图地址").attr("require","true").attr("accept","bmp, gif, jpeg, jpg, png").end().appendTo($("#divFile")).show();
	    $("img[id='imgDelTr']").each(function(){
			$(this).click(function (){
				$(this).parent().remove();
			});
		});
	});

    $("a[id^='del_']").click(function(){
		var obj = $(this);
		$.post("CommInfo.do?method=deleteFile" , {
			id : $(obj).get(0).id.substring(4)
		}, function(d){
			if(d == "success"){
				del_num = del_num + 1;
				$(obj).parent().remove();
			}
		});
	})
});
function updateAll(obj){
	var $this = $(obj);
	if($this.next().is(":hidden")){
		$this.next().show();
	}else{
		$this.next().hide();
	}
}
function closeUpdateAll(obj){
	$(obj).parent().hide();
}

function setUpdateVal(data_flag,obj){
	var setVal = $(obj).prev().val();
	var curr = /^\d+(\.\d+)?$/;
	if(null != setVal && "" != setVal && curr.test(setVal)){
		$("#tczh_tbody").find("input[name='"+ data_flag +"']").val(setVal);
		$(obj).parent().hide();
	}else{
		alert("数据有误，请重新填写");
	}
}

$("#is_has_tc").change(function() {
	if ($(this).val() == 1) {
		$("#tczh_tr").show();
	} else {
		$("#tczh_tr").hide();
	}
});
if ("1" == $("#is_has_tc").val()) {
	$("#tczh_tr").show();
}

function DayFunc(){
	var c = $dp.cal;
	var todate = new Date(c.getP('y'),c.getP('M')-1,c.getP('d'));
	todate.setDate(todate.getDate()+365);
	$("#down_date").val(todate.format("yyyy-MM-dd")); 
}

function getBrandInfo() {
	var url = "${ctx}/CsAjax.do?method=getBrandInfoList&t=" + Math.random();
	$.dialog({
		title:  "选择品牌",
		width:  800,
		height: 600,
        lock:true ,
		content:"url:"+url
	});
};

function setOnlyNum() {
	$(this).css("ime-mode", "disabled");
	$(this).attr("t_value", "");
	$(this).attr("o_value", "");
	$(this).bind("dragenter",function(){
		return false;
	})
	$(this).keypress(function (){
		if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value
	}).keyup(function (){
		if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value
	}).blur(function (){
		if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}
		if(this.value.length == 0) this.value = 0;
	})
}

function setOnlyNum2() {
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
		if(!this.value.match(/^(?:[\+]?\d+(?:\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^$/))this.value=0;this.o_value=this.value};
		if(this.value.length == 0) this.value = "0";
	});
	//this.text.selected;
}

function setOnlyNum3(obj) {
	$(obj).css("ime-mode", "disabled");
	$(obj).attr("t_value", "");
	$(obj).attr("o_value", "");
	$(obj).bind("dragenter",function(){
		return false;
	})
	$(obj).keypress(function (){
		if(!obj.value.match(/^[\+\-]?\d*?\.?\d*?$/))obj.value=obj.t_value;else obj.t_value=obj.value;if(obj.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))obj.o_value=obj.value
	}).keyup(function (){
		if(!obj.value.match(/^[\+\-]?\d*?\.?\d*?$/))obj.value=obj.t_value;else obj.t_value=obj.value;if(obj.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))obj.o_value=obj.value
	}).blur(function (){
		if(!obj.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))obj.value=obj.o_value;else{if(obj.value.match(/^\.\d+$/))obj.value=0+obj.value;if(obj.value.match(/^\.$/))obj.value=0;obj.o_value=obj.value}
		if(obj.value.length == 0) obj.value = 0;
	})
}


function delete_confirm(){
	 var k = window.confirm("您确定要删除该附件吗?");
	 if (k) {
	 	event.returnValue=true;
	 } else {
	    event.returnValue=false;
	 }
}

function getParClsInfo() {
	var url = "${ctx}/CsAjax.do?method=getClsId&s_type=comm&noSelectFar=true&t=" + Math.random();
	$.dialog({
		title:  "选择类别",
		width:  450,
		height: 600,
        lock:true ,
        zIndex: 9999,
		content:"url:"+url
	});
};

function setCommInfoToForm(comm_id){
	var mod_id = $("#mod_id").val();
	var par_id = $("#par_id").val();
	var comm_type = $("#comm_type").val();
	location.href='${ctx}/manager/customer/CommInfo.do?method=selectCommAddForType4&&comm_id=' + comm_id +"&mod_id="+ mod_id + "&par_id=" + par_id+"&comm_type="+comm_type;
}

function getCommInfo() {
	var url = "${ctx}/BaseCsAjax.do?method=chooseCommInfo&dir=customer&ajax=selectCommType4&own_entp_id=${af.map.own_entp_id}&noNeedState=true&t=" + new Date().getTime();
	$.dialog({
		title:  "选择商品",
		width:  800,
		height: 600,
        lock:true ,
        zIndex:"10000",
		content:"url:"+url
	});
};

function choosePoorInfo(){
	var p_index = $("#p_index").val();
	var province = '';
	var city = '';
	var country = '';
	if('' != p_index && p_index.length == 6){
		province = p_index.substring(0,2) + '0000';
		city = p_index.substring(0,4) + '00';
		country = p_index;
	}
	var url = "${ctx}/BaseCsAjax.do?method=choosePoorInfo&dir=admin&type=multiple&province="+province+"&city="+city+"&country="+country;
	$.dialog({
		title:  "选择贫困户",
		width:  850,
		height: 550,
        lock:true ,
		content:"url:"+url
	});
}

function returnPoorInfo(ids,names,p_names){
	 var temp_poor_ids = $("#temp_poor_ids").val() || "";
     if(null!=ids&&''!=ids){
    	 var id_array = ids.split(',');
    	 var name_array = names.split(',');
    	 var pname_array = p_names.split(',');
    	 for(var i=0;i<id_array.length;i++){
    		 if(temp_poor_ids.indexOf(','+id_array[i]+',')==-1){
    			 var html = "";	
    			 html = '<tr>';
    			 html += '<input type="hidden" name="poor_ids" value="'+id_array[i]+'" />';
    			 html += '<td align="center">'+name_array[i]+'</td>';
    			 html += '<td align="center">'+pname_array[i]+'</td>';
    			 html += '<td align="center"><b><a class="butbase" onclick="delPoorInfo(this,'+id_array[i]+');"><span class="icon-configure">删除</span></a></b></td>';
    			 html += '</tr>';
    			 $("#poor_list_tr").last().after(html);
    			 temp_poor_ids = temp_poor_ids+","+id_array[i]+',';
    			 $("#temp_poor_ids").val(temp_poor_ids);
    		 }
    	 }
     }
}
function delPoorInfo(obj,id){
	 $(obj).parent().parent().parent().remove();
	 var str = $("#temp_poor_ids").val().replace(','+id+',',',');
	 $("#temp_poor_ids").val(str);
}


function validate(value){
	if ("" != value) {
			$.ajax({
				type: "POST" , 
				url: "${ctx}/CsAjax.do" , 
				data:"method=validate&type=comm_name&value="+value+ "&id=${af.map.id}&t=" + new Date(),
				dataType: "json", 
		        async: true, 
		        error: function (request, settings) {alert(" 数据加载请求失败！ ");	$("#btn_submit").attr("disabled", "true");}, 
		        success: function (data) {
					if (data.ret == 0) {
						alert('参数丢失！', '提示');
						$("#btn_submit").attr("disabled", "true");
						return false;
					} else if (data.ret == 1) {
						$("#btn_submit").removeAttr("disabled");
					} else if (data.ret == -1) {
						alert(data.msg, '提示');
						$("#btn_submit").attr("disabled", "true");
						return false;
					}
		        }
			});
	}
}
function getFreightInfoList() {
	var url = "${ctx}/CsAjax.do?method=getFreightInfoList&canAdd=true&own_entp_id=${af.map.own_entp_id}&t=" + Math.random();
	$.dialog({
		title:  "选择物流模板",
		width:  800,
		height: 600,
        lock:true ,
        zIndex: 9999,
		content:"url:"+url
	});
}
var swfu;
var settings = {
	ctx : "${ctx}",// 路径
	entity_id : "${af.map.id}",// 当前数据的id
	delete_url : "${ctx}/CsAjax.do",// ajax删除文件的url
	delete_method : "deleteFileForCosSwfupload",// ajax删除文件的method
	upload_single_file : false,// 是否上传单个文件,默认上传多个文件
	flash_url : "${ctx}/commons/swfupload/swfupload.swf",
	upload_url : "${ctx}/CosUploader.do?jsessionid=${sessionId}",
	can_upload:${5 - (CommImgsListCount)},
	no_image : "${ctx}/commons/swfupload/style/images/no_image.jpg",
	post_params : {
		"uploadTimer" : new Date()
	},
	file_size_limit : "2 MB",
	file_types : "*.jpg;*.jpeg;*.gif;*.png;*.bmp",
	file_types_description : "All Files",
	file_upload_limit : 5,//上次文件个数限制
	file_queue_limit : 5,
	custom_settings : {
		progressTarget : "fsUploadProgress",
		cancelButtonId : "btnCancel",
		upload_successful : false
	},
	// Button settings
	button_image_url : "${ctx}/commons/swfupload/style/images/upload.png",
	button_placeholder_id : "spanButtonPlaceHolder",
	button_text : '',
	button_cursor : -2,
	button_text_style: ".theFont { font-size: 12; color: #FFFFFF;}",
	button_width: 64,
	button_height : 22,
	<c:if test="${CommImgsListCount gt 5}">
	button_disabled : true,
	button_image_url : "${ctx}/commons/swfupload/style/images/upload_disabled.png",
	</c:if>
	// The event handler functions are defined in handlers.js
	file_queued_handler : fileQueued,
	file_queue_error_handler : fileQueueError,
	file_dialog_complete_handler : fileDialogComplete,
	upload_start_handler : uploadStart,
	upload_progress_handler : uploadProgress,
	upload_error_handler : uploadError,
	upload_success_handler : uploadSuccess,
	upload_complete_handler : uploadComplete,
	queue_complete_handler : queueComplete
// Queue plugin event
};
swfu = new SWFUpload(settings);

//]]></script>
</body>
</html>
