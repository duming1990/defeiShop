<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>运营中心 - ${app_name}</title>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<jsp:include page="../../_public_header.jsp" flush="true" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/newscontent.css"  />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/pages.css"  />
<style>
a{ cursor:pointer;}
.citys-list{padding-bottom:20px;padding-top:0px;position:relative;z-index:2;}
.citys-list DL{border:#fff 1px solid;padding:10px 0;background:#fff;}
.citys-list DL.hover{background:#edf1f5;border:1px solid #e4e6ea;}
.citys-list DT{padding-left:25px;width:130px;font-family:Arial;float:left;color:#444;font-size:12px;}
.citys-list DD{border-left:#b2b2b2 1px dashed;margin:0px 0px 0px 140px;padding-left:15px;}
.citys-list DD a{padding-bottom:1px;margin:0px 15px 5px 0px;padding-left:5px;padding-right:5px;display:inline-block;color:#666;font-size:12px;text-decoration:none;padding-top:1px;}
.citys-list .bold{font-weight:bold;color:#c05600;}
.citys-list DD .itemsTag{position:absolute;background:#fbf8cf;border:1px solid #ddd;padding:10px;width:800px;z-index:8;display:none;font-size: 12px;}
.citys-list DD .itemsTag span,.citys-list DD .itemsTag b,.citys-list DD .itemsTag i{float:left;margin-right:10px;height:30px;line-height:30px;white-space:nowrap;font-style:normal;}
.citys-list DD .itemsTag span{margin:0;font-weight:bold;}
.citys-list DD .itemsTag b{color:#c05600;}
.citys-list DD .itemsTag u{text-decoration:none;position:absolute;background:#fbf8cf;border:1px solid #ddd;border-bottom:0;padding:5px 5px 2px;z-index:9;white-space:nowrap;}
.citys-list DD .itemsTag s{text-decoration:none;float:right;color:#D62323;cursor:pointer;}
</style>
<style type="">
#bd{
    width: 1200px;
    top:0px;
}
</style>
</head>
<body>
<jsp:include page="../../_header.jsp" flush="true" />
<div class="bdw">
  <div id="bd" class="cf">
    <!----内容开始---->
    <div>
      <!--------地区开始--------->
      <div class="content__body">
        <div class="citys-list">
          <c:forEach var="bp1" items="${bpList}">
            <dl onmouseover="$(this).addClass('hover')" onmouseout="$(this).removeClass('hover')" class="">
              <dt>
                <c:set var="class2" value="" />
                <c:if test="${bp1.is_fuwu eq 1}">
                  <c:set var="class2" value="bold" />
                </c:if>
                <c:set var="txt1" value="" />
                <c:if test="${bp1.fuwu_count gt 0}">
                  <c:set var="txt1" value="(${bp1.fuwu_count})" />
                </c:if>
                <span class="${class2}">${bp1.p_name}${txt1}</span></dt>
              <dd>
                <c:forEach var="bp2" items="${bp1.bp2List}">
                  <c:set var="class2" value="" />
                  <c:if test="${bp2.is_fuwu eq 1}">
                    <c:set var="class2" value="bold" />
                  </c:if>
                  <c:set var="txt2" value="" />
                  <c:if test="${bp2.fuwu_count gt 0}">
                    <c:set var="txt2" value="(${bp2.fuwu_count})" />
                  </c:if>
                  <a id="${bp2.p_index}" onclick="show(this)" class="${class2}">${bp2.p_name}${txt2}</a>
                  <div class="itemsTag" id="city_${bp2.p_index}"> <u class="${class2}">${bp2.p_name}${txt2}</u><span>市辖县区：</span>
                    <c:forEach var="bp3" items="${bp2.bp3List}">
                      <c:set var="class3" value="" />
                      <c:if test="${bp3.is_fuwu eq 1}">
                        <c:set var="class3" value="bold" />
                      </c:if>
                      <i class="${class3}">${bp3.p_name}</i> </c:forEach>
                    <div class="clear"></div>
                    <s onclick="$(this).parent().slideUp()">[关闭]</s></div>
                </c:forEach>
              </dd>
            </dl>
          </c:forEach>
        </div>
      </div>
      <!--------地区结束--------->
    </div>
  </div>
</div>
<jsp:include page="../../_footer.jsp" flush="true" />
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	
});
function show(ob){
	$('.itemsTag').slideUp();
	var pos = $(ob).position();
	$("#city_" + ob.id).find("u").css("left",pos.left-152).css("top","-26px");
	$("#city_" + ob.id).css({
		'left':150,
		'top':pos.top + 20
	}).slideDown();
}

//]]>
</script>
</body>
</html>
