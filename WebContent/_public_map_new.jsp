<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<link href="${ctx}/styles/map/index.css" rel="stylesheet" type="text/css" />
<style type="">
#query{
border: 1px solid #EBEBEB;
height: 20px;
}
</style>
</head>
<body onload="initialize_f('${af.map.result_id}')">
<div id="page">
        <!-- 地图容器 -->
        <div id="container" class="map" tabindex="0"></div>
        <!-- 搜索框-->
        <div id="searchBox">
            <input id="tipinput" type="input" placeholder="请输入关键字搜索" />
            <div id="clearSearchBtn">
                <div class="del">&#10005;</div>
            </div>
        </div>
        <!-- 结果面板 -->
        <div id="panel" class="hidden">
            <!-- 隐藏按钮 -->
            <a id="showHideBtn"></a>
            <div id="emptyTip">没有内容！</div>
            <!--搜索结果列表 -->
            <div id="poiList">
            </div>
        </div>
        <!-- loading -->
        <div id="loader"></div>
    </div>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script> 
<script type="text/javascript" src='http://webapi.amap.com/maps?v=1.3&test=true&plugin=AMap.ToolBar&key=5bf4f0b21d2baf43ba9bd033d555457f'></script>
    <!--缩放按钮 -->
    <script type="text/javascript" src="http://cache.amap.com/lbs/static/addToolbar.js"></script>
    <!--Just Zepto -->
    <script type='text/javascript' src='http://g.alicdn.com/sj/lib/zepto/zepto.min.js'></script>
    <script type="text/javascript">
    //创建地图
    var map = new AMap.Map('container', {
        zoom: 9,
        center: [116.868549, 34.918187]
    });

    //加载PlaceSearch和Autocomplete插件
    AMap.service(["AMap.PlaceSearch", "AMap.Autocomplete"], function() {

        try {
            ready();

        } catch (e) {
            console.error(e);
        }
    });

    function ready() {

        //搜索框支持自动完成提示
        var auto = new AMap.Autocomplete({
            input: "tipinput"
        });

        //构造地点查询类
        var placeSearch = new AMap.PlaceSearch({
            pageSize: 5,
            pageIndex: 1,
            map: map,
            panel: "poiList"
        });

        //监听搜索框的提示选中事件
        AMap.event.addListener(auto, "select", function(e) {

            //设置搜索的城市
            placeSearch.setCity(e.poi.adcode);
            //开始搜索对应的poi名称
            placeSearch.search(e.poi.name, function(status, results) {

                if (results.pois && results.pois.length > 0) {
                    $('#panel').toggleClass('empty');
                }

                //显示结果列表
                $('#panel').removeClass('hidden');

                //隐藏loading状态
                $(document.body).removeClass('searching');
            });

            //显示loading状态
            $(document.body).addClass('searching');
        });

        //检查结果列表是否为空， 为空时显示必要的提示，即#emptyTip
        function checkPoiList() {
            $('#panel').toggleClass('empty', !($.trim($('#poiList').html())));
        }

        checkPoiList();

        //监听搜索列表的渲染完成事件
        AMap.event.addListener(placeSearch, 'renderComplete', function() {
            checkPoiList();
        });

        //监听marker/列表的选中事件
        AMap.event.addListener(placeSearch, 'selectChanged', function(results) {
            //获取当前选中的结果数据
            console.log(results.selected.data);
        });

        $('#showHideBtn').click(function() {
            $('#panel').toggleClass('hidden');
        });

        $('#clearSearchBtn').click(function() {

            //清除搜索框内容
            $('#tipinput').val('');

            //清除结果列表
            placeSearch.clear();
            $('#panel').addClass('hidden');
            checkPoiList();
        });
    }
    </script>
<script type="text/javascript" src="${ctx}/scripts/baidu.map.geocoder.js?v20160114"></script>
</body>
</html>