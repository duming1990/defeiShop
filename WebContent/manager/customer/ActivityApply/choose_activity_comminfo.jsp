<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <base target="_self" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <jsp:include page="../../admin/_public_head_back.jsp" flush="true" />
    <jsp:include page="../../public_page_in_head.jsp" flush="true" />
    <title>选择产品类别</title>
    <style type="text/css">
        body {
            background:#F8F8F8;
            font-family:"微软雅黑", "宋体";
        }
        fieldset {
            border:1px solid #CCC/*A3C0E8*/;
            text-align:left;
            margin:0 auto;
            margin:5px;
            border-radius:2px;
            background:#FFF;
        }
        fieldset a {
            height:30px;
            line-height:30px;
            background:#009900;
            color:#FFF;
            margin:2px 5px;
            padding:2px 8px;
            border-radius: 5px;
        }
        fieldset a:hover {
            background:#00B800;
            text-decoration:none;
            color:#FFF;/*color:#FFF0AC;*/
        }
        fieldset a:visited {
            color:#FFF;
            text-decoration:none;
        }
        legend {
            font-weight: bold;
        }
        .dataContent {
            width:99%;
            overflow:hidden;/*border:1px solid #A3C0E8;*/
            margin:5px auto;
            overflow:auto;/*height:320px;*/
        }
        .ul_selpd {
            padding-left:30px;
        }
        .ul_selpd li {/*width:210px;*/
            width:20%;
            line-height:18px;
            overflow:hidden;
            float:left;
            padding:5px;
            margin:5px;
            text-align:center;
        }
        .ul_selpd li b {
            float:right;
            font-weight:normal;
            padding-left:5px;
            font-size:12px;
        }
        .dataContent input,label{
            cursor:pointer;
        }
        .cked {
            background:#FFF0AC;/*#F1F8FF;*/
            border-radius: 5px;
        }
        .cked label {
            color:#FF5809;
            text-decoration:underline;/*#F1F8FF;*/
        }
        .fdul li{float:left;}
    </style>
</head>
<body>
<div style="background:#FFFFFF;">&nbsp;</div>
<div align="center" style="height:650px;overflow:auto;">
    <fieldset>
        <legend>已选&nbsp;${_cs_user}</legend>
        <div id="chZj" style="height:auto;overflow:auto;">
            <ul id="fdul" class="fdul">
                <c:forEach items="${detailList}" var="cus">
	                    <li><a href="javascript:void(0);" id="a_${cus.comm_id}">${cus.comm_name}</a></li>
                </c:forEach>
            </ul>
        </div>
    </fieldset>
    <div>
        <html-el:form action="/customer/ActivityApply.do">
            <html-el:hidden property="method" value="chooseActivityCommInfo" />
            <html-el:hidden property="discount_comm_ids" styleId="discount_comm_ids"/>
            <html-el:hidden property="discount_comm_names" styleId="discount_comm_names"/>
            <html-el:hidden property="discount_comm_prices" styleId="discount_comm_prices"/>
            <html-el:hidden property="activity_id" styleId="activity_id"/>
            <html-el:hidden property="own_entp_id" styleId="own_entp_id"/>
<%--             <html-el:hidden property="pay_type" styleId="pay_type" value="${af.map.pay_type}"/> --%>
            <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" class="tableClass">
                <tr>
                    <td align="left" nowrap="nowrap"> 商品名称：
                        <html-el:text property="comm_name_like" styleClass="webinput" size="40" />
                        &nbsp;&nbsp; <a class="butbase" id="btn_search" style="cursor:pointer;"/> <span class="icon-find">查询</span>
                        
                        
                    <div style="    float: right;">
                        <a class="butbase" href="#" onclick="returnVal();" ><span class="icon-ok">确定</span></a>&nbsp;
    <a class="butbase" href="#" onclick="gb()"><span class="icon-remove">关闭</span></a>
                    </div>
                        
                        </td>
                </tr>
                <tr>
                    <td colspan="5" align="left"> 活动类型：
                        <html-el:select property="pay_type" styleId="pay_type" styleClass="webinput">
				              <html-el:option value="1">直接支付</html-el:option>
				             <html-el:option value="0">选商品</html-el:option>
			            </html-el:select></td>
                </tr>
            </table>
        </html-el:form>
    </div>
    <div id="choose_comm" style="display: none;">
    <form id="listForm" name="listForm" method="post" action="Coupons.do?method=delete">
        <div>
            <table width="100%" cellspacing="0" cellpadding="0" class="tableClass" align="left" id="tableForList">
                <tr class="tite2">
                    <th align="center">商品名称</th>
                    <th width="6%" align="center" nowrap="nowrap"><input name="_chkAll" type="checkbox" onclick="checkCtAll(this);" id="_chkAll" value="-1" /></th>
                    <th align="center">套餐名称</th>
                    <th align="center">价格</th>
                    <th align="center">库存</th>
                </tr>
                <c:forEach var="cur" items="${entityList}" varStatus="vs" >
	               <c:if test="${empty cur.map.choose}">
	                    <tr align="center">
	                        <td width="30%" align="left"><c:out value="${fnx:abbreviate(cur.comm_name, 60, '...')}" />
	                        </td>
	                        <td colspan="4" align="left">
	                        <table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableClass">
				              <c:forEach var="cur2" varStatus="vs1" items="${cur.map.ctpList}">
				                <tr id="forTrClick" style="cursor:pointer;" title="点击选择" >
				                	<td width="5%" align="center">
			                        	<input name="pks" type="checkbox" data-flag="0" id="pks_${cur2.id}" value="${cur2.id}+${cur.comm_name}-${cur2.tczh_name}+${cur2.comm_price}" class="cked"/>
			                        </td>
				                  <td width="27%" align="center">${cur2.tczh_name}</td>
				                  <td width="16%" align="center"><fmt:formatNumber value="${cur2.comm_price}" pattern="0.##" />元</td>
				                  <td width="16%" align="center">${cur2.inventory}</td>
				                </tr>
				              </c:forEach>
				            </table>
				            </td>
	                    </tr>
	               </c:if>
                    <c:if test="${vs.last eq true}">
                        <c:set var="i" value="${vs.count}" />
                    </c:if>
                </c:forEach>
                <c:forEach begin="${i}" end="${af.map.pager.pageSize - 1}">
                    <tr align="center">
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td colspan="3">&nbsp;</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </form>
    
    <div class="clear"></div>
<!--     <div class="pageClass"> -->
<!--         <form id="bottomPageForm" name="bottomPageForm" method="post" action="ActivityApply.do"> -->
<!--             <table width="98%" border="0" cellpadding="0" cellspacing="0"> -->
<!--                 <tr> -->
<%--                     <td><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script> --%>
<!--                         <script type="text/javascript"> -->
<%-- //                             var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage}); --%>
<!-- //                             pager.addHiddenInputs("method", "chooseActivityCommInfo"); -->
<%-- //                             pager.addHiddenInputs("comm_name_like", "${fn:escapeXml(af.map.comm_name_like)}"); --%>
<%-- //                             pager.addHiddenInputs("discount_comm_ids", "${fn:escapeXml(af.map.discount_comm_ids)}"); --%>
<%-- //                             pager.addHiddenInputs("discount_comm_names", "${fn:escapeXml(af.map.discount_comm_names)}"); --%>
<%-- //                             pager.addHiddenInputs("discount_comm_prices", "${fn:escapeXml(af.map.discount_comm_prices)}"); --%>
<%-- //                             pager.addHiddenInputs("own_entp_id", "${fn:escapeXml(af.map.own_entp_id)}"); --%>
<%-- //                             pager.addHiddenInputs("activity_id", "${fn:escapeXml(af.map.activity_id)}"); --%>
<%-- //                             pager.addHiddenInputs("pay_type", "${af.map.pay_type}"); --%>
<!-- //                             document.write(pager.toString()); -->
<!--                         </script></td> -->
<!--                 </tr> -->
<!--             </table> -->
<!--         </form> -->
<!--     </div> -->
    </div>
</div>
<div class="clear"></div>
<div style="height:30px;width:100%;text-align:right;padding-top:10px;background:#EEEEEE;border-top:1px solid #CCCCCC;">
    
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){

	var pay_type = $("#pay_type").val();
	if(pay_type == 1){
		$("#choose_comm").hide();
	}else{
		$("#choose_comm").show();
	}
	
    var f = document.forms[0];
    $("#btn_search").click(function(){
        f.submit();

    });
    
   
    $("#pay_type").change(function(){
		var thisVal = $(this).val();
		if(thisVal== 1){
			$("#choose_comm").hide();
		}else{
			$("#choose_comm").show();
		}
	});
    

    $(".Tab tr").mouseover(function(){
        $(this).addClass("over");
    }).mouseout(function(){
        $(this).removeClass("over");
    })
    $(".Tab tr:even").addClass("alteven");
    $(".Tab tr:odd").addClass("altodd");

    if ("" != "${af.map.discount_comm_ids}") {
        var discount_comm_ids = "${af.map.discount_comm_ids}";
        var custIdsArray = new Array();
        custIdsArray = discount_comm_ids.split(",");
        var cks = document.getElementsByName("pks");
        for ( var i = 0; i < cks.length; i++) {
            for ( var j = 0; j < custIdsArray.length; j++) {
                if (cks[i].value.split("+")[0] == custIdsArray[j]) {
                    cks[i].checked = true;
                    $(cks[i]).parent().parent().addClass("cked");
                    $(cks[i]).attr("data-flag","1");
                }
            }
        }
    }

});

$("#tableForList #forTrClick").each(function(){
    var obj = $(this).find("input[type='checkbox']");
    obj.checked = true;
    $(this).bind("click",function(){
        checkWk(obj);
    });
});

function gb(){
    var api = frameElement.api, W = api.opener;
    api.close();
}

//多选
function checkCtAll(e) {
    var idsArray = new Array();
    var namesArray = new Array();
    var pricesArray = new Array();
    
    var wkIdsArray = new Array();
    var wkNamesArray = new Array();
    var wkPricesArray = new Array();
    if (e.form.elements) {
        for (var i = e.form.elements.length - 1; i > 0; i--) {
            if (e.form.elements[i].type == "checkbox" && e.form.elements[i].disabled == false) {
                e.form.elements[i].checked = e.checked;
            }
        }

        var cks = document.getElementsByName("pks");
        if (e.checked) {
            $("a", "#fdul").each(function(){
                for ( var i = 0; i < cks.length; i++) {
                    if ($(this).attr("id").split("_")[1] == cks[i].value.split("+")[0]) {
                        $(this).parent().remove();
                    }
                }
            });

            $("a", "#fdul").each(function(){
                var id = $(this).attr("id").split("_")[1];
                var name = $(this).text();
                var price = $(this).attr("id").split("_")[2];
                idsArray.push(id);
                namesArray.push(name);
                pricesArray.push(price);
            });

            for ( var i = 0; i < cks.length; i++) {
                wkIdsArray.push(cks[i].value.split("+")[0]);
                wkNamesArray.push(cks[i].value.split("+")[1]);
                wkPricesArray.push(cks[i].value.split("+")[2]);
                $("#fdul").append('<li><a href="javascript:void(0);" id="a_' + cks[i].value.split("+")[0] + '">' + cks[i].value.split("+")[1] + '</a></li>');
                $(cks[i]).parent().parent().addClass("cked");//改变样式
                $(cks[i]).attr("data-flag","1");
            }

            for ( var x = 0; x < idsArray.length; x++) {
                wkIdsArray.push(idsArray[x]);
            }
            for ( var y = 0; y < namesArray.length; y++) {
                wkNamesArray.push(namesArray[y]);
            }
            for ( var y = 0; y < namesArray.length; y++) {
                wkPricesArray.push(pricesArray[y]);
            }
            var ids = wkIdsArray.join(",");
            var names = wkNamesArray.join(",");
            var prices = wkPricesArray.join(",");
            $("input[name='discount_comm_ids']").val(ids);
            $("input[name='discount_comm_names']").val(names);
            $("input[name='discount_comm_prices']").val(prices);
        } else {
            $("a", "#fdul").each(function(){
                for ( var i = 0; i < cks.length; i++) {
                    if ($(this).attr("id").split("_")[1] == cks[i].value.split("+")[0]) {
                        $(this).parent().remove();
                    }
                    $(cks[i]).parent().parent().removeClass("cked");//改变样式
                    $(cks[i]).attr("data-flag","0");
                }
            });
            $("a", "#fdul").each(function(){
                var id = $(this).attr("id").split("_")[1];
                var price = $(this).attr("id").split("_")[2];
                var name = $(this).text;
                idsArray.push(id);
                namesArray.push(name);
                pricesArray.push(price);
            });
            var ids = idsArray.join(",");
            var names = namesArray.join(",");
            var prices = pricesArray.join(",");
            $("input[name='discount_comm_ids']").val(ids);
            $("input[name='discount_comm_names']").val(names);
            $("input[name='discount_comm_prices']").val(prices);
        }
    }
}

//单选
function checkWk(e) {
    var wkIdsArray = new Array();
    var wkNamesArray = new Array();
    var wkPricesArray = new Array();
    var data_flag = $(e).attr("data-flag");
    if (e.checked && data_flag == 0) { //选中
        $(e).attr("checked","checked");
        e.checked = false;
        $(e).parent().parent().addClass("cked");//改变样式
        $(e).attr("data-flag","1");
        /********************ids**********************/
        if ("" != $("#discount_comm_ids").val()) {
            wkIdsArray = $("#discount_comm_ids").val().split(",");
        }
        wkIdsArray.push($(e).val().split("+")[0]);
        var ids = wkIdsArray.join(",");
        $("input[name='discount_comm_ids']").val(ids);
        /*********************names*********************/
        if ("" != $("#discount_comm_names").val()) {
            wkNamesArray = $("#discount_comm_names").val().split(",");
        }
        wkNamesArray.push($(e).val().split("+")[1]);
        var names = wkNamesArray.join(",");
        $("input[name='discount_comm_names']").val(names);
        /*********************prices*********************/
        if ("" != $("#discount_comm_prices").val()) {
            wkPricesArray = $("#discount_comm_prices").val().split(",");
        }
        wkPricesArray.push($(e).val().split("+")[2]);
        var prices = wkPricesArray.join(",");
        $("input[name='discount_comm_prices']").val(prices);
        /******************************************/
        $("#fdul").append('<li><a href="javascript:void(0);" id="a_' + $(e).val().split("+")[0] + '">' + $(e).val().split("+")[1] + '</a></li>');
    } else { //取消选中
        $(e).removeAttr("checked");
        e.checked = true;
        $(e).parent().parent().removeClass("cked");
        $(e).attr("data-flag","0");
        /******************************************/
        var twkArray = new Array();
        if ("" != $("#discount_comm_ids").val()) {
            wkIdsArray = $("#discount_comm_ids").val().split(",");
        }
        for ( var i = 0; i < wkIdsArray.length; i++) {
            if (wkIdsArray[i] != $(e).val().split("+")[0]) {
                twkArray.push(wkIdsArray[i]);
            }
        }
        var ids = twkArray.join(",");
        $("input[name='discount_comm_ids']").val(ids);
        /******************************************/
        var twknArray = new Array();
        if ("" != $("#discount_comm_names").val()) {
            wkNamesArray = $("#discount_comm_names").val().split(",");
        }
        for ( var i = 0; i < wkNamesArray.length; i++) {
            if (wkNamesArray[i] != $(e).val().split("+")[1]) {
                twknArray.push(wkNamesArray[i]);
            }
        }
        var names = twknArray.join(",");
        $("input[name='discount_comm_names']").val(names);
        /******************************************/
        var twknArray = new Array();
        if ("" != $("#discount_comm_names").val()) {
            wkPricesArray = $("#discount_comm_prices").val().split(",");
        }
        for ( var i = 0; i < wkNamesArray.length; i++) {
            if (wkPricesArray[i] != $(e).val().split("+")[2]) {
                twknArray.push(wkPricesArray[i]);
            }
        }
        var prices = twknArray.join(",");
        $("input[name='discount_comm_prices']").val(prices);
        
        /******************************************/
        $("a", "#fdul").each(function(){
            var aid = $(this).attr("id").split("_")[1];
            if ($(e).val().split("+")[0] == aid) {
                $(this).parent().remove();
            }
        });
    }
    var discount_comm_ids = $("input[name='discount_comm_ids']").val();
    var discount_comm_names = $("input[name='discount_comm_names']").val();
    var discount_comm_prices = $("input[name='discount_comm_prices']").val();
}

function returnVal(){
	var api = frameElement.api, W = api.opener;
	var comm_ids = $("input[name='discount_comm_ids']").val();
	var comm_names = $("input[name='discount_comm_names']").val();
	var pay_type = $("#pay_type").val();
	var activity_id = $("#activity_id").val();
	
	if(!comm_ids &&pay_type ==0){
		$.jBox.tip("请选择商品", "info");
		return;
	}
	$.ajax({
		type: "POST",
		url: "?method=saveActivityComminfo",
		data: {
			"comm_ids":comm_ids,
			"comm_names":comm_names,
			"pay_type":pay_type,
			"activity_id":activity_id
		},
		dataType: "json",
		error: function(request, settings) {},
		success: function(data) {
			if(data.code == "1"){
				$.jBox.tip(data.msg, "success",500);
					
					window.setTimeout(function () {
						if(pay_type==0){
							W.editActivityCommInfo(activity_id);
						}
						W.reloadJsp();
						api.close();
					}, 1000);
			} else {
				$.jBox.tip(data.msg, "info");
			}
		}
	});	
}

//]]></script>
</body>
</html>
