

	function loadAnaylytics(){
		var c=document.createElement("script");
		s=document.getElementsByTagName("script")[0];
		c.src="https://s95.cnzz.com/z_stat.php?id=1260412460&web_id=1260412460";
		s.parentNode.insertBefore(c,s);
	}
	$(function (){
	        setTimeout('loadAnaylytics()', 3000); //延迟3秒加载,提高速度
	    })
	    