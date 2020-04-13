<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="../../commons/pages/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-hans">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>首页 - ${app_name}</title>
</head>
<body>
<div style="display:none;">
<div id="video_path_div"></div></div>
<input type="button" class="player-play" value="Play" />  
<input type="button" class="player-stop" value="Stop" />  
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jwplayer/jwplayer.js"></script>
<script type="text/javascript">//<![CDATA[
var thePlayer; 
thePlayer =  jwplayer("video_path_div").setup({//通过js调用播放器并安装到指定容器（container）内
         flashplayer: "jwplayer/player.swf",//调用播放器
         file: "newOrder.mp3",//调用视频文件
         width: 0,//播放器宽
         height: 0,//播放器高
         autostart:false//自动播放
     });
autoPlayer();     
     
 function autoPlayer(){
	 if(thePlayer.getState() != 'PLAYING'){  
	       thePlayer.play(true);  
	       this.value = 'Pause';  
	   }else {  
	       thePlayer.play(false);  
	       this.value = 'Play';  
	   }  
 }    
     
//Stop  
$('.player-stop').click(function(){thePlayer.stop();});      
	   
//]]></script>
</body>
</html>