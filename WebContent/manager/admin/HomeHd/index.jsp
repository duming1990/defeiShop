<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/common.css"  />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/main_all.css"  />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/hd/css/common.css"  />
<link href="${ctx}/scripts/colorbox/style3/colorbox.css" rel="stylesheet" type="text/css" />
<title>${app_name}</title>
</head>
<body class="forIndex">
<!-- S 主体内容 -->
<jsp:include page="_header.jsp" flush="true" />
<div class="mod_container">
  <div class="seckill_container">
    <div class="timeline" id="timeline">
      <div class="grid_c1">
        <ul class="timeline_list">
          <li class="timeline_item timeline_item_selected"> 
           <a class="timeline_item_link" href="javascript:void(0)">
            <div class="timeline_item_link_skew"><i class="timeline_item_link_skew_time">10:00</i><i class="timeline_item_link_skew_processtips">进行中</i> </div>
            </a></li>
          <li class="timeline_item"> <a class="timeline_item_link" href="javascript:void(0)">
            <div class="timeline_item_link_skew"><i class="timeline_item_link_skew_time">12:00</i><i class="timeline_item_link_skew_processtips">即将开始</i> </div>
            </a></li>
          <li class="timeline_item"> <a class="timeline_item_link" href="javascript:void(0)">
            <div class="timeline_item_link_skew"><i class="timeline_item_link_skew_time">14:00</i><i class="timeline_item_link_skew_processtips">即将开始</i> </div>
            </a></li>
          <li class="timeline_item"> <a class="timeline_item_link" href="javascript:void(0)">
            <div class="timeline_item_link_skew"><i class="timeline_item_link_skew_time">16:00</i><i class="timeline_item_link_skew_processtips">即将开始</i> </div>
            </a></li>
          <li class="timeline_item"> <a class="timeline_item_link" href="javascript:void(0)">
            <div class="timeline_item_link_skew"><i class="timeline_item_link_skew_time">18:00</i><i class="timeline_item_link_skew_processtips">即将开始</i> </div>
            </a></li>
        </ul>
      </div>
    </div>
    <div class="skwrap">
      <div class="seckillbanner" id="seckillbanner">
        <div class="grid_c1">
          <div class="seckillbanner_slider">
          <div class="areas"><a href="${ctx}/manager/admin/BaseLink.do?mod_id=${af.map.mod_id}&link_type=2100" class="beautybg">编辑</a></div>
            <ul class="slider_ctn">
              <a><img src="${ctx}/styles/hd/images/58783a48Nf72f9e5f.jpg" /></a>
            </ul>
          </div>
        </div>
      </div>
      <div class="timecount" id="timecount">
        <div class="grid_c1">
          <div class="timecount_container">
            <div class="timecount_container_skew"> 
            <span id="J-seckilling" class="seckilling">抢购中</span> 
            <span id="J-timeContainer" class="time_container"> 
            <b id="J-text">先下单先得哦！</b>
            <b id="J-endDef">距结束</b> 
            <i class="hour">01</i>时 <i class="minutes">29</i>分 <i class="seconds">35</i>秒 </span> </div>
          </div>
        </div>
      </div>
      <div class="spsk" id="super_seckill">
        <div class="grid_c1">
          <ul class="seckill_mod_goodslist clearfix">
<%--            <div class="areas"><a href="${ctx}/manager/admin/BaseLink.do?mod_id=${af.map.mod_id}&link_type=2200" class="beautybg">编辑</a></div> --%>
            <li class="seckill_mod_goods"> 
              <a class="seckill_mod_goods_link"> 
              <img class="seckill_mod_goods_link_img" src="${ctx}/styles/hd/images/57306088N4310fced.jpg" />
              <h4 class="seckill_mod_goods_title">维达（Vinda) 抽纸 超韧系列 软抽3层130抽*24包(小规格) 整箱销售</h4>
              <span class="seckill_mod_goods_tag"><i class="seckill_mod_goods_tag_i">超值</i></span> 
              <span class="seckill_mod_goods_info"> 
              <span class="seckill_mod_goods_info_txt"> 
              <span class="seckill_mod_goods_price"> <i class="seckill_mod_goods_price_now"><em>¥</em>53<i class="seckill_mod_goods_price_now_small">.90</i></i> <span class="seckill_mod_goods_price_pre">¥<del>99</del></span> </span> 
              </span> 
              <span class="seckill_mod_goods_info_btn"> <i></i> </span> <i class="seckill_mod_goods_info_i">立即抢购</i> </span> </a> 
            </li>
            <li class="seckill_mod_goods"> 
              <a class="seckill_mod_goods_link"> 
              <img class="seckill_mod_goods_link_img" src="${ctx}/styles/hd/images/55ae159fN369e929a.jpg" />
              <h4 class="seckill_mod_goods_title">TP-LINK TL-WR890N 450M无线路由器（全金属机身）</h4>
              <span class="seckill_mod_goods_tag"><i class="seckill_mod_goods_tag_i">超值</i></span> 
              <span class="seckill_mod_goods_info"> 
              <span class="seckill_mod_goods_info_txt"> 
              <span class="seckill_mod_goods_price"> <i class="seckill_mod_goods_price_now"><em>¥</em>53<i class="seckill_mod_goods_price_now_small">.90</i></i> <span class="seckill_mod_goods_price_pre">¥<del>99</del></span> </span> 
              </span> 
              <span class="seckill_mod_goods_info_btn"> <i></i> </span> <i class="seckill_mod_goods_info_i">立即抢购</i> </span> </a> 
            </li>
            <li class="seckill_mod_goods"> 
              <a class="seckill_mod_goods_link"> 
              <img class="seckill_mod_goods_link_img" src="${ctx}/styles/hd/images/5512856aN2957c0b4.jpg" />
              <h4 class="seckill_mod_goods_title">雅马哈（Yamaha）光音系列 LSX-170 迷你音响 灯光蓝牙音箱 光音系统桌面书架式 钢琴黑</h4>
              <span class="seckill_mod_goods_tag"><i class="seckill_mod_goods_tag_i">超值</i></span> 
              <span class="seckill_mod_goods_info"> 
              <span class="seckill_mod_goods_info_txt"> 
              <span class="seckill_mod_goods_price"> <i class="seckill_mod_goods_price_now"><em>¥</em>53<i class="seckill_mod_goods_price_now_small">.90</i></i> <span class="seckill_mod_goods_price_pre">¥<del>99</del></span> </span> 
              </span> 
              <span class="seckill_mod_goods_info_btn"> <i></i> </span> <i class="seckill_mod_goods_info_i">立即抢购</i> </span> </a> 
            </li>
             <li class="seckill_mod_goods"> 
              <a class="seckill_mod_goods_link"> 
              <img class="seckill_mod_goods_link_img" src="${ctx}/styles/hd/images/58468844N96e00104.jpg" />
              <h4 class="seckill_mod_goods_title">百草味 茶几上的春晚1538g 坚果礼盒 年货干果零食大礼包 9袋装</h4>
              <span class="seckill_mod_goods_tag"><i class="seckill_mod_goods_tag_i">超值</i></span> 
              <span class="seckill_mod_goods_info"> 
              <span class="seckill_mod_goods_info_txt"> 
              <span class="seckill_mod_goods_price"> <i class="seckill_mod_goods_price_now"><em>¥</em>53<i class="seckill_mod_goods_price_now_small">.90</i></i> <span class="seckill_mod_goods_price_pre">¥<del>99</del></span> </span> 
              </span> 
              <span class="seckill_mod_goods_info_btn"> <i></i> </span> <i class="seckill_mod_goods_info_i">立即抢购</i> </span> </a> 
            </li>
            <li class="seckill_mod_goods"> 
              <a class="seckill_mod_goods_link"> 
              <img class="seckill_mod_goods_link_img" src="${ctx}/styles/hd/images/57eb7e1dN47558c9e.jpg" />
              <h4 class="seckill_mod_goods_title">格力（GREE）正1.5匹 变频 品圆 冷暖 壁挂式空调 KFR-35GW/(35592)FNhDa-A3</h4>
              <span class="seckill_mod_goods_tag"><i class="seckill_mod_goods_tag_i">超值</i></span> 
              <span class="seckill_mod_goods_info"> 
              <span class="seckill_mod_goods_info_txt"> 
              <span class="seckill_mod_goods_price"> <i class="seckill_mod_goods_price_now"><em>¥</em>53<i class="seckill_mod_goods_price_now_small">.90</i></i> <span class="seckill_mod_goods_price_pre">¥<del>99</del></span> </span> 
              </span> 
              <span class="seckill_mod_goods_info_btn"> <i></i> </span> <i class="seckill_mod_goods_info_i">立即抢购</i> </span> </a> 
            </li>
            <li class="seckill_mod_goods"> 
              <a class="seckill_mod_goods_link"> 
              <img class="seckill_mod_goods_link_img" src="${ctx}/styles/hd/images/58623cc9Nde8f286c.jpg" />
              <h4 class="seckill_mod_goods_title">澳大利亚 进口牛奶 德运 （Devondale ）全脂牛奶200ml*24整箱装（两种包装随机发货）</h4>
              <span class="seckill_mod_goods_tag"><i class="seckill_mod_goods_tag_i">超值</i></span> 
              <span class="seckill_mod_goods_info"> 
              <span class="seckill_mod_goods_info_txt"> 
              <span class="seckill_mod_goods_price"> <i class="seckill_mod_goods_price_now"><em>¥</em>53<i class="seckill_mod_goods_price_now_small">.90</i></i> <span class="seckill_mod_goods_price_pre">¥<del>99</del></span> </span> 
              </span> 
              <span class="seckill_mod_goods_info_btn"> <i></i> </span> <i class="seckill_mod_goods_info_i">立即抢购</i> </span> </a> 
            </li>
            <li class="seckill_mod_goods"> 
              <a class="seckill_mod_goods_link"> 
              <img class="seckill_mod_goods_link_img" src="${ctx}/styles/hd/images/5631e8f8N45043be1.jpg" />
              <h4 class="seckill_mod_goods_title">西麦 早餐谷物 无添加蔗糖 膳食纤维 即食 纯燕麦片1480g</h4>
              <span class="seckill_mod_goods_tag"><i class="seckill_mod_goods_tag_i">超值</i></span> 
              <span class="seckill_mod_goods_info"> 
              <span class="seckill_mod_goods_info_txt"> 
              <span class="seckill_mod_goods_price"> <i class="seckill_mod_goods_price_now"><em>¥</em>53<i class="seckill_mod_goods_price_now_small">.90</i></i> <span class="seckill_mod_goods_price_pre">¥<del>99</del></span> </span> 
              </span> 
              <span class="seckill_mod_goods_info_btn"> <i></i> </span> <i class="seckill_mod_goods_info_i">立即抢购</i> </span> </a> 
            </li>
            <li class="seckill_mod_goods"> 
              <a class="seckill_mod_goods_link"> 
              <img class="seckill_mod_goods_link_img" src="${ctx}/styles/hd/images/5811d953Nf72a33f9.jpg" />
              <h4 class="seckill_mod_goods_title">全球购 Apple iphone 6 Plus 海外版  移动联通4G手机(官换满保) 银色 16G</h4>
              <span class="seckill_mod_goods_tag"><i class="seckill_mod_goods_tag_i">超值</i></span> 
              <span class="seckill_mod_goods_info"> 
              <span class="seckill_mod_goods_info_txt"> 
              <span class="seckill_mod_goods_price"> <i class="seckill_mod_goods_price_now"><em>¥</em>53<i class="seckill_mod_goods_price_now_small">.90</i></i> <span class="seckill_mod_goods_price_pre">¥<del>99</del></span> </span> 
              </span> 
              <span class="seckill_mod_goods_info_btn"> <i></i> </span> <i class="seckill_mod_goods_info_i">立即抢购</i> </span> </a> 
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</div>
<jsp:include page="_footer.jsp" />
<script type="text/javascript" src="${ctx}/scripts/colorbox/jquery.colorbox.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript">//<![CDATA[
$("a.beautybg").colorbox({width:"90%", height:"80%", iframe:true});
//]]></script>
</body>
</html>
