<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${app_name}</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Expires" content="-1">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Pragma" content="no-cache">
<link href="${ctx}/m/styles/css/cp_style_v15.11.min.css?20180201" rel="stylesheet" type="text/css" />
<link href="${ctx}/scripts/lightbox/css/lightbox.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.comment_content{overflow-y: hidden;height: inherit;color: black;font-size: 0.6rem;}
</style>
</head>
<body id="body">
<input type="hidden" id="startRow" name="startRow" value="${queryCount}"/>
<div class="content comment">
  <div class="d3">
    <div style="background: #fff;padding: .82rem 1.2rem;margin-bottom: .4rem;">
      <div class="box"><span>总体评价：</span>
        <ul class="gs_starBg" style="position:relative; right:0; top:-.12rem;">
          <li style="z-index:0; display: list-item; width:${(commentScore/5)*100}%"></li>
        </ul>
        <span class="f60">${commentScore}分</span> </div>
    </div>
    <div class="section-detailbox">
      <section class="title goods">
       <c:if test="${not empty commentInfoList}">
        <dl class="goods-txt" id="_assessItem">
        <c:forEach var="cur" items="${commentInfoList}">
          <dd>
            <div class="gs-gqul comment_title_div"> 
            <span class="comment_name">${cur.map.secretString}</span>
            <ul class="gs_starBg" style="top: 4vh;left: 2vh;position: static;">
               <li style="position: static;z-index:0; display: list-item; width:${(cur.comm_score/5)*100}%"></li>
             </ul>
            <span class="comm_title_div_date"><fmt:formatDate pattern="yyyy-MM-dd" value="${cur.comm_time}" /></span>
            </div>
            <div class="comment_comm_tczh_name">
              ${cur.comm_name}<c:if test="${not empty cur.comm_tczh_name}">[${cur.comm_tczh_name}]</c:if>
              </div>
            <div class="txt">
              <p class="comment_content" style="overflow-y: hidden;height: inherit;color: black;font-size: 0.9rem;">${fn:escapeXml(cur.comm_experience)}</p>
              <p class="img">
               <c:forEach items="${cur.map.baseFilesList}" var="cur1" varStatus="vs1">
                <c:if test="${not empty cur1.save_path}">
                 <a href="${ctx}/${cur1.save_path}" data-lightbox="image-${vs1.count}">
                  <img src="${ctx}/${cur1.save_path}@s400x400"></a>
                </c:if>
               </c:forEach>
               </p>
              </div>
              <!-- 回复 -->
              <div class="commentSon">
              	<ul>
              	<c:forEach items="${cur.map.commentInfoSonList}" var="cur">
              		<li><span class="commentSon_title">${cur.add_user_name}:<span class="to_user">@${cur.to_user_name}</span></span>${cur.content}</li>
              	</c:forEach>
              	</ul>
              	</div>
              </dd>
          </c:forEach>
        </dl>
       </c:if>
       <c:if test="${empty commentInfoList}">
       	<div style="text-align:center;">暂无评价</div>
       </c:if>
      </section>
    </div>
    <div class="eval-txtips" id="loadmore">加载更多...</div>
    <!--  -->
  </div>
</div>
<script type="text/javascript" src="${ctx}/scripts/lightbox/js/lightbox.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
    lightbox.option({
      'showImageNumberLabel': false, 
      'alwaysShowNavOnTouchDevices': true
    });
	
});
   
function setStartRow(){
	var startRow = parseInt($("#startRow").val());
	startRow = startRow + 15;
	$("#startRow").val(startRow);
}

$("#loadmore").click(function(){
	loadData();
});


function loadData(){
	var startRow = parseInt($("#startRow").val());
	if(isNaN(startRow)){
		startRow = 0;
	}
	getData(startRow);
}

function getData(startRow){
	$.ajax({
		type: "POST",
		url: "${ctx}/CsAjax.do",
		data: { "method":"getCommentInfo","comm_id":"${af.map.comm_id}", "startRow":startRow},
		dataType: "json",
		sync: true,
		success: function(data) {
			Common.loading();
			if(null != data && '' != data){
				var list = data.list;
				if(null != list && list.length >0){
					var html="";
					for(x in list){
						var star_width = 100 - (5-list[x].comm_score)*20; 
						html += '<dd>';
						html += '<div class="gs-gqul comment_title_div">';
						html += '<span class="comment_name">'+list[x].secretString +'</span>'
						html += '<ul class="gs_starBg" style="top: 4vh;left: 2vh;position: static;">';
						html += '<li style="position: static;z-index:0; display: list-item; width:'+ star_width +'%"></li>';
						html += '</ul>';
						html += '<span class="comm_title_div_date">'+ list[x].comm_time +'</span>'
						html += '</div>';
						html += '<div class="comment_comm_tczh_name">'+ list[x].comm_name;
						if(null != list[x].comm_tczh_name){
						    html += '[' + list[x].comm_tczh_name + ']';
						}
						html += '</div>';

						html += '<div class="txt">';
						html += '<p class="comment_content" style="overflow-y: hidden;height: inherit;color: black;font-size: 0.3rem;">'+ list[x].comm_experience + '</p>'
						html += '<p class="img">';
						var listBaseFile = list[x].baseFilesList;
						if("" != listBaseFile){
						var z = 1;
						for(y in listBaseFile){
						html += '<a href="${ctx}/'+ listBaseFile[y].save_path +'" data-lightbox="image-'+ z +'">';
						html += '<img src="${ctx}/'+ listBaseFile[y].save_path +'"></a>';	
						z++;
						}
						}
						html += '</p></div>';
						<!-- 回复 -->
						html += '<div class="commentSon">';
						html += '<ul>';
						var commentInfoSonList = list[x].commentInfoSonList;
						if("" != commentInfoSonList ){
						    for( y in commentInfoSonList ){
								html += '<li><span class="commentSon_title">'+commentInfoSonList[y].add_user_name+':<span class="to_user">@'+commentInfoSonList[y].to_user_name+'</span></span>'+commentInfoSonList[y].content+'</li>';
							}
						}
						              
						html +='</ul></div>';
						html += '</dd>';

					}
					window.setTimeout(function () { 
					setStartRow();
					Common.hide();
					$("#_assessItem").append(html);
					}, 1500);
				}else{
					window.setTimeout(function () { 
					Common.hide();
					$("#loadmore").text("已显示全部");
					}, 1500);
				}
			}
		}
	});
}

//]]></script>
</body>
</html>
