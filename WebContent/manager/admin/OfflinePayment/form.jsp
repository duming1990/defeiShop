<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>${naviString}</title>
    <jsp:include page="../_public_head_back.jsp" flush="true" />
    <script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>

</head>
<body>
<div class="divContent">
    <div class="subtitle">
        <h3>${naviString}</h3>
    </div>
    <html-el:form action="/admin/OfflinePayment" >
        <html-el:hidden property="id" styleId="id" />
        <html-el:hidden property="method" value="save" />
        <html-el:hidden property="mod_id" styleId="mod_id" />
        <html-el:hidden property="queryString" styleId="queryString" />
        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
            <tr>
                <th colspan="3">线下订单录入</th>
            </tr>
            <tr>
                <td width="12%" nowrap="nowrap" class="title_item">选择用户：</td>
                <td colspan="2" width="88%">
                    <html-el:hidden property="user_id" styleId="user_id"/>
                    <html-el:text property="user_name" styleId="user_name" maxlength="125" style="width:90%" readonly="true" onclick="openUser()" styleClass="webinput" />
                </td>
            </tr>
            <tr>
                <td width="12%" nowrap="nowrap" class="title_item">选择企业：</td>
                <td colspan="2" width="88%">
                    <html-el:hidden property="own_entp_id" styleId="own_entp_id"/>
                    <html-el:text property="entp_name" styleId="entp_name" maxlength="125" style="width:90%" styleClass="webinput" value="${entp_name}" onclick="openEntpChild()" readonly="true"/>
                </td>
            </tr>
            <tr>
                <td width="12%" nowrap="nowrap" class="title_item">下单时间：</td>
                <td colspan="2" width="88%">
<%--                     <fmt:formatDate value="${beginDate}" pattern="yyyy-MM-dd" var="_beginDate" /> --%>
                    <html-el:text property="beginDate" styleId="beginDate" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
                </td>
            </tr>
<!--             <tr> -->
<!--                 <td width="12%" nowrap="nowrap" class="title_item">结束时间：</td> -->
<!--                 <td colspan="2" width="88%"> -->
<%--                     <fmt:formatDate value="${endDate}" pattern="yyyy-MM-dd" var="_endDate" /> --%>
<%--                     <html-el:text property="endDate" size="10" maxlength="10" readonly="true" onclick="WdatePicker()" value="${_endDate}"/> --%>
<!--                 </td> -->
<!--             </tr> -->
            <tr>
                <td width="12%" nowrap="nowrap" class="title_item">选择商品：</td>
                <td colspan="2" width="88%">
                    <html-el:hidden property="discount_comm_ids" styleId="discount_comm_ids"/>
                    <a class="butbase" style="cursor:pointer; margin-bottom: 5px;" onclick="getdiscount_comm_names();" id="comm_name"><span class="icon-search">选择商品</span></a>
                </td>
            </tr>
            <tr>
                <td width="12%" nowrap="nowrap" class="title_item">是否发放扶贫金：</td>
                <td colspan="2" width="88%">
                    <html-el:select property="is_send_fp" styleId="is_send_fp">
				        <html-el:option value="1">发放</html-el:option>
	                    <html-el:option value="0">不发放</html-el:option>
			        </html-el:select>
                </td>
            </tr>
            <tr>
                <td id="newtable" colspan="2"></td>
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
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>

<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){

    var f = document.forms[0];

    $("#user_name").attr("dataType", "Require").attr("msg", "请选择用户");
    $("#entp_name").attr("dataType", "Require").attr("msg", "请选择企业");
    $("#beginDate").attr("dataType", "Require").attr("msg", "请选择开始时间");
//     $("#endDate").attr("dataType", "Require").attr("msg", "请选择结束时间");
    $("#shippingAddress_name").attr("dataType", "Require").attr("msg", "请选择收货地址");

    $("#btn_submit").click(function(){
        if(Validator.Validate(f, 3)){
            $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
            $("#btn_reset").attr("disabled", "true");
            $("#btn_back").attr("disabled", "true");
            f.submit();
        }
    });
});

function openUser(){
    var user_ids = $("#user_id").val();
    var user_names = $("#user_name").val();
    var url = "OfflinePayment.do?method=listUser&user_ids="+user_ids+"&user_names="+user_names;
    $.dialog({
        title:  "选择下单用户",
        width:  1200,
        height: 600,
        padding: 0,
        max: false,
        min: false,
        fixed: true,
        lock: true,
        zIndex:222,
        //close: function(){location.reload();},
        content:"url:"+ encodeURI(url)
    });
}

function openEntpChild(){
    var own_entp_id_ = $("#own_entp_id").val();
    var url = "${ctx}/BaseCsAjax.do?method=chooseEntpInfo&dir=admin&own_entp_id="+own_entp_id_;
    $.dialog({
        title:  "选择企业",
        width:  1200,
        height: 600,
        lock:true ,
        content:"url:"+url
    });
}

function getdiscount_comm_names(){
    var own_entp_id = $("#own_entp_id").val();
    var discount_comm_ids = $("#discount_comm_ids").val();
    var user_id = $("#user_id").val();
    if(null == own_entp_id || "" == own_entp_id){
        alert("请先选择企业！");
        return false;
    }else{
        var url = "OfflinePayment.do?method=listCommInfo&own_entp_id="+own_entp_id+"&discount_comm_ids="+discount_comm_ids;
        $.dialog({
            title:  "选择商品",
            width:  1200,
            height:700,
            padding: 0,
            max: false,
            min: false,
            fixed: true,
            lock: true,
            zIndex:228,
            content:"url:"+ encodeURI(url)
        });
    }
}

function openShippingAddressChild(){
    var shippingAddress_id_ = $("#shippingAddress_id").val();
    alert("33:"+shippingAddress_id_);
    var user_ids = $("#user_id").val();
    if(null == user_ids || "" == user_ids){
        alert("请先选择下单用户！");
        return false;
    }else{
        var url = "${ctx}/BaseCsAjax.do?method=chooseShippingAddressInfo&dir=admin&shippingAddress_id="+shippingAddress_id_+"&user_ids="+user_ids;
        $.dialog({
            title:  "选择收货地址",
            width:  770,
            height: 550,
            lock:true ,
            content:"url:"+url
        });
    }
}

function test(discount_comm_ids,comm_type){
    $.ajax({
        type: "POST",
        url: "OfflinePayment.do?method=commInfo&discount_comm_ids="+discount_comm_ids+"&comm_type="+comm_type,
        success: function(data){
            var entityList = data.entityList;
            var html='<table width="100%" border="0" cellpadding="1" cellspacing="1" class="tableClass">';
            html+='<tr class="tite2">';
            html+='<th align="center">商品名称</th>';
            html+='<th align="center">商品数量</th>';
            html+='<th width="10%" align="center" nowrap="nowrap">参考价格</th>';
            html+='</tr>';
            html+='<tbody id="dataTbody">';
            for (var i = 0; i < entityList.length; i++) {
                var comm_name = entityList[i].comm_name;
                var price_ref = entityList[i].map.price;
                html+='<tr align="center" data-id="'+entityList[i].id+'">';
                html+='<input type="hidden" name="this_comm_id" value="'+entityList[i].id+'">';
                html+='<td align="left">'+comm_name;
                html+='</td>';
                html+='<td><input name="this_comm_count" id="count_'+entityList[i].id+'" maxlength="5" style="width:100px" styleClass="webinput" value="'+entityList[i].map.count+'" onchange="getSum()" datatype="Number" msg="请填写商品数量！"/></td>';
                html+='<td><input style="border-left:0px;border-top:0px;border-right:0px;border-bottom:1px;background-color:#FFFFFF;text-align:center;color:#333;font-size:12px;" name="this_comm_price" id="priceTd_'+entityList[i].id+'" disabled="disabled" value="￥'+price_ref+'"/></td>';
                html+='</tr>';
            }
            html+='</tbody>';
            html+='<tr align="center">';
            html+='<td align="left" colspan="2"><c:out value="合计："/>';
            html+='<td><span id="sum">'+data.sum+'</span></td></tr>';
            html+='<tr align="center">';
            html+='<td align="left"><c:out value="下单数量："/>';
            html+='<td colspan="2"><input name="count" id="count" maxlength="10" style="width:100px" styleClass="webinput" value="1" onchange="getSum()" datatype="Number" msg="请填写下单数量！"/></td></tr>';
            html+='<tr align="center">';
            html+='<td align="left" colspan="2"><c:out value="总计："/>';
            html+='<td><span id="sum2">'+data.sum2+'</span></td></tr>';
            html+='</table>';
            $("#newtable").empty().append(html);
        }
    });
}

function getSum(){
    var Sum = 0;
    $("#dataTbody tr").each(function(){
        var data_id = $(this).attr("data-id");
        var count = $("#count_"+data_id).val();
        var priceTd = $("#priceTd_"+data_id).val();
        if(null!=priceTd && "" != priceTd){
            var priceTd_ = priceTd.substring(1, priceTd.length);
        }
        Sum += count*priceTd_;
    });
    var sum = "￥"+Sum.toFixed(2);
    var count = $("#count").val();
    var Sum2 = Sum*count;
    var sum2 = "￥"+Sum2.toFixed(2);
    document.getElementById("sum").innerHTML=sum;
    document.getElementById("sum2").innerHTML=sum2;
}
//]]></script>
</body>
</html>
