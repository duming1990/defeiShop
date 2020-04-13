
/*
 * Extended API for Google Maps v3
 *
 * @author  Wu,Yang
 * @version 2010-09-06
 * 
 * 首先引入google map js需要先定义一些参数
 * var map;
 * var markerForGeocode;
 * var markersArrayForGeocode = [];
 * var infowindowLevelForGeocode = 0;
 * var geocoderForGeocode = new google.maps.Geocoder();
 * 页面：
 * <div> 
 * <span style="font-weight: bold">关键字:</span> 
 * <input type="text" size="60" id="query"/> 
 * <input type="button" value="查 询" onclick="submitQuery()" class="bgButton" /> 
 * </div> 
 * <div id="results"></div>
 * paras 可以传一些自定义的参数，完成自定义的需求
 */
window.openInfoWinFuns = null;
var QjresultId;

function submitQuery(paras) {
	var query;
	if(null != paras.result_id && '' != paras.result_id){
		QjresultId = paras.result_id;
	}
	//alert(QjresultId);
	
	query = $("#query").val();
	var options = {
			  onSearchComplete: function(results){
			    // 判断状态是否正确
			    if (local.getStatus() == BMAP_STATUS_SUCCESS){
			        var s = [];
			        s.push('<div style="font-family: arial,sans-serif; border: 1px solid rgb(153, 153, 153); font-size: 12px;">');
			        s.push('<div style="background: none repeat scroll 0% 0% rgb(255, 255, 255);">');
			        s.push('<ol style="list-style: none outside none; padding: 0pt; margin: 0pt;">');
			        openInfoWinFuns = [];
			        for (var i = 0; i < results.getCurrentNumPois(); i ++){
			            var marker = addMarker(results.getPoi(i).point,i);
			            var openInfoWinFun = addInfoWindow(marker,results.getPoi(i),i);
			            openInfoWinFuns.push(openInfoWinFun);
			            // 默认打开第一标注的信息窗口
			            var selected = "";
			            if(i == 0){
			                selected = "background-color:#f0f0f0;";
			                openInfoWinFun();
			            }
			            s.push('<li id="list' + i + '" style="margin: 2px 0pt; padding: 0pt 5px 0pt 3px; cursor: pointer; overflow: hidden; line-height: 17px;' + selected + '" onclick="openInfoWinFuns[' + i + ']()">');
			            s.push('<span style="width:1px;background:url('+ ctxpath +'/images/red_labels.gif) 0 ' + ( 2 - i*20 ) + 'px no-repeat;padding-left:10px;margin-right:3px"> </span>');
			            s.push('<span style="color:#00c;text-decoration:underline">' + results.getPoi(i).title.replace(new RegExp(results.keyword,"g"),'<b>' + results.keyword + '</b>') + '</span>');
			            s.push('<span style="color:#666;"> - ' + results.getPoi(i).address + '</span>');
			            s.push('</li>');
			            s.push('');
			        }
			        s.push('</ol></div></div>');
			        document.getElementById("results").innerHTML = s.join("");
			    }
			  }
			};
	var local = new BMap.LocalSearch(map, options);
	local.search(query);
	if (paras) {
		if (paras.showInfo) {
			$("#tishiForFactory").remove();
			if(null != vieType && "" != vieType){
				$("#results").after("<div id='tishiForFactory' style='color:#ff6000;' align='left'>提示：如果找到你需要的位置，直接点击即可！</div>");
			}else{
				$("#results").after("<div id='tishiForFactory' style='color:#ff6000;' align='left'>提示：如果找到你需要的位置，直接在<span style='color:red;font-weight:bold;'>红色圆点</span>位置附近单击鼠标左键即可！</div>");
			}
		}
	}
}



// 添加标注
function addMarker(point, index){
  var myIcon = new BMap.Icon(ctxpath +"/images/markers.png", new BMap.Size(23, 25), {
    offset: new BMap.Size(10, 25),
    imageOffset: new BMap.Size(0, 0 - index * 25)
  });
  var marker = new BMap.Marker(point, {icon: myIcon});
  map.addOverlay(marker);
  return marker;
}
// 添加信息窗口
function addInfoWindow(marker,poi,index){
    var maxLen = 10;
    var name = null;
    if(poi.type == BMAP_POI_TYPE_NORMAL){
        name = "地址：  "
    }else if(poi.type == BMAP_POI_TYPE_BUSSTOP){
        name = "公交：  "
    }else if(poi.type == BMAP_POI_TYPE_SUBSTOP){
        name = "地铁：  "
    }
    // infowindow的标题
    var infoWindowTitle = '<div style="font-weight:bold;color:#CE5521;font-size:14px">'+poi.title+'</div>';
    // infowindow的显示信息
    var infoWindowHtml = [];
    infoWindowHtml.push('<table cellspacing="0" style="table-layout:fixed;width:100%;font:12px arial,simsun,sans-serif"><tbody>');
    infoWindowHtml.push('<tr>');
    if(null != name){
    infoWindowHtml.push('<td style="vertical-align:top;line-height:16px;width:38px;white-space:nowrap;word-break:keep-all">' + name + '</td>');
    }
    infoWindowHtml.push('<td style="vertical-align:top;line-height:16px">' + poi.address + ' </td>');
    infoWindowHtml.push('</tr>');
    infoWindowHtml.push('</tbody></table>');
    var infoWindow = new BMap.InfoWindow(infoWindowHtml.join(""),{title:infoWindowTitle,width:200}); 
    var openInfoWinFun = function(){
        marker.openInfoWindow(infoWindow);
        for(var cnt = 0; cnt < maxLen; cnt++){
            if(!document.getElementById("list" + cnt)){continue;}
            if(cnt == index){
                document.getElementById("list" + cnt).style.backgroundColor = "#f0f0f0";
            }else{
                document.getElementById("list" + cnt).style.backgroundColor = "#fff";
            }
        }
        //清空最原始的marker
      //  markerInit.remove(); 
        
        map.setCenter(poi.point);
        markerInit.setPosition(poi.point);
		var newPoint = poi.point.lng + "," + poi.point.lat;
		if(null != W && "" != W){
			W.document.getElementById(QjresultId).value = newPoint;
		}
		$("#" + QjresultId).val(newPoint); 
	}
    //marker.addEventListener("click", openInfoWinFun);
    return openInfoWinFun;
}


