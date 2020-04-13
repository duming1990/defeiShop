// YOU MUST IMPORT JQUERY LIB AT FIRST
$(document).ready(function(){
	$(".tableClass tr").mouseover(function(){  
		$(this).addClass("over");
	}).mouseout(function(){
		$(this).removeClass("over");
	})
	$(".tableClass tr:even").addClass("alt");
})