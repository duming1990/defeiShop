<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <base target="_self" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <jsp:include page="../_public_head_back.jsp" flush="true" />
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
<div align="center" style="height:550px;overflow:auto;">
    <fieldset>
        <legend>已选&nbsp;${_cs_user}</legend>
        <div id="chZj" style="height:45px;overflow:auto;">
            <ul id="fdul" class="fdul">
                <c:forEach items="${ciList}" var="cus">
                    <li><a href="javascript:void(0);" id="a_${cus.id}">${cus.comm_name}</a></li>
                </c:forEach>
            </ul>
        </div>
    </fieldset>
    <div>
        <html-el:form action="/admin/ClickFarming.do">
            <html-el:hidden property="method" value="listCommInfo" />
            <html-el:hidden property="discount_comm_ids" styleId="discount_comm_ids"/>
            <html-el:hidden property="discount_comm_names" styleId="discount_comm_names"/>
            <html-el:hidden property="discount_comm_prices" styleId="discount_comm_prices"/>
            <html-el:hidden property="own_entp_id"/>
            <html-el:hidden property="user_id" styleId="user_id"/>
            <html-el:hidden property="comm_type" styleId="comm_type" value="${comm_type}"/>
            <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" class="tableClass">
                <tr>
                    <td align="left" nowrap="nowrap"> 商品名称：
                        <html-el:text property="comm_name_like" styleClass="webinput" size="40" />
                        &nbsp;&nbsp; <a class="butbase" id="btn_search" style="cursor:pointer;"/> <span class="icon-find">查询</span></td>
                </tr>
            </table>
        </html-el:form>
    </div>
    <form id="listForm" name="listForm" method="post" action="ClickFarming.do?method=delete">
        <div>
            <table width="100%" cellspacing="0" cellpadding="0" class="tableClass" align="left" id="tableForList">
                <tr class="tite2">
                    <th width="8%" align="center" nowrap="nowrap"><input name="_chkAll" type="checkbox" onclick="checkCtAll(this);" id="_chkAll" value="-1" /></th>
                    <th align="center">商品名称</th>
                    <th width="10%" align="center" nowrap="nowrap">参考价格</th>
                </tr>
                <c:forEach var="cur" items="${entityList}" varStatus="vs">
                    <tr align="center" id="forTrClick" style="cursor:pointer;" title="点击选择">
                        <td><input name="pks" type="checkbox" data-flag="0" id="pks_${cur.id}" value="${cur.id}+${cur.comm_name}+${cur.price_ref}" />
                        </td>
                        <td align="left"><c:out value="${fnx:abbreviate(cur.comm_name, 60, '...')}" />
                        </td>
                        <td><fmt:formatNumber pattern="￥#,##0.00" value="${cur.map.price}"/></td>
                    </tr>
                    <c:if test="${vs.last eq true}">
                        <c:set var="i" value="${vs.count}" />
                    </c:if>
                </c:forEach>
                <c:forEach begin="${i}" end="${af.map.pager.pageSize - 1}">
                    <tr align="center">
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </form>
    <div class="clear"></div>
    <div class="pageClass">
        <form id="bottomPageForm" name="bottomPageForm" method="post" action="ClickFarming.do">
            <table width="98%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
                        <script type="text/javascript">
                            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
                            pager.addHiddenInputs("method", "listCommInfo");
                            pager.addHiddenInputs("comm_name_like", "${fn:escapeXml(af.map.comm_name_like)}");
                            pager.addHiddenInputs("discount_comm_ids", "${fn:escapeXml(af.map.discount_comm_ids)}");
                            pager.addHiddenInputs("discount_comm_names", "${fn:escapeXml(af.map.discount_comm_names)}");
                            pager.addHiddenInputs("discount_comm_prices", "${fn:escapeXml(af.map.discount_comm_prices)}");
                            pager.addHiddenInputs("own_entp_id", "${fn:escapeXml(af.map.own_entp_id)}");
                            document.write(pager.toString());
                        </script></td>
                </tr>
            </table>
        </form>
    </div>
</div>
<div class="clear"></div>
<div style="height:30px;width:100%;text-align:right;padding-top:10px;background:#EEEEEE;border-top:1px solid #CCCCCC;">
    <a class="butbase" href="#" onclick="returnVal();" ><span class="icon-ok">确定</span></a>&nbsp;
    <a class="butbase" href="#" onclick="gb()"><span class="icon-remove">关闭</span></a>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){

    var f = document.forms[0];
    $("#btn_search").click(function(){
        f.submit();

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
                var id = $(this).attr("id").split("_")[2];
                idsArray.push(id);
                namesArray.push(name);
                namesArray.push(name);
            });

            for ( var i = 0; i < cks.length; i++) {
                wkIdsArray.push(cks[i].value.split("+")[0]);
                wkNamesArray.push(cks[i].value.split("+")[1]);
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
            var ids = wkIdsArray.join(",");
            var names = wkNamesArray.join(",");
            $("input[name='discount_comm_ids']").val(ids);
            $("input[name='discount_comm_names']").val(names);
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
                var name = $(this).text;
                idsArray.push(id);
                namesArray.push(name);
            });
            var ids = idsArray.join(",");
            var names = namesArray.join(",");
            $("input[name='discount_comm_ids']").val(ids);
            $("input[name='discount_comm_names']").val(names);
        }
    }
}

//单选
function checkWk(e) {
    var wkIdsArray = new Array();
    var wkNamesArray = new Array();
    var data_flag = $(e).attr("data-flag");
    if (e.checked && data_flag == 0) { //选中
        $(e).attr("checked","checked");
        e.checked = false;
        $(e).parent().parent().addClass("cked");//改变样式
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
        $("a", "#fdul").each(function(){
            var aid = $(this).attr("id").split("_")[1];
            if ($(e).val().split("+")[0] == aid) {
                $(this).parent().remove();
            }
        });
    }
    var discount_comm_ids = $("input[name='discount_comm_ids']").val();
    var discount_comm_names = $("input[name='discount_comm_names']").val();
}

function returnVal(){
    var api = frameElement.api, W = api.opener;
    var discount_comm_ids = $("input[name='discount_comm_ids']").val();
    var discount_comm_names = $("input[name='discount_comm_names']").val();
    var comm_type = $("input[name='comm_type']").val();
    var user_ids = $("#user_id").val();
    if(null == discount_comm_ids || "" == discount_comm_ids){
        alert("请选择商品！");
        return false;
    }else{
        W.document.getElementById("discount_comm_ids").value = $("input[name='discount_comm_ids']").val();
        W.test(discount_comm_ids,comm_type);
        api.close();
    }
}
//]]></script>
</body>
</html>
