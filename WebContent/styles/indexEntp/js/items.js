var lm=[],zx=[],num=0,num2=0;
$(function(){
    //搜索按钮
    $('.btn_search').on('click',function(){
        var that=$(this), $search=$('input[name=search]');
        if($search.val()==''){
          return false;  
        }else{
           that.attr("disabled", "true"); 
           $.ajax({
                    cache: false,
                    "type": "POST",
                    "url":"http://g.sunaomei.com/core/search",
                    "data":$('#itemsSearch').serialize(),
                    "dataType": "jsonp",
                      jsonpCallback:"callback",
                    "error": function(XMLHttpRequest, textStatus, errorThrown){
                           alert('提交失败');
                           that.removeAttr("disabled");
                    },
                    "success": function(data) {
                    	console.log(data)
                        that.removeAttr("disabled");
                        $('.items .typeWrap').hide();
                        $('.search_data').show();
                        $('.items_close').fadeIn(300);
                        $(".data_yw .data").html('');
                        $(".data_zx .data").html('');
                        lm=[],zx=[],num=0,num2=0;
                         $('.yw_more').show().html('<img src="images/icon_more.png" alt="" /><span>更多业务详情</span>');
                         $('.zx_more').show().html('<img src="images/icon_more.png" alt="" /><span>更多资讯详情</span>');
                        lm=data["栏目"];
                        zx=data["文章"];
                        companyInfor.init();
                        companyInfor2.init();
                    }
            }); 
            
        }
        return false;
    });
    //关闭按钮
    $('.items_close').on('click',function(){
        $('.items .typeWrap').show();
        $(this).fadeOut(300);
        $('.search_data').hide();

    });
    //热门操作
    var h=$('.items .hot .list').height(),$drop=$('.items .hot .drop');
    if(h>34){
      $drop.show();
    }else{
      $drop.hide();  
    }
    $drop.on('click',function(){
        if($(this).hasClass('active')){
          $(this).removeClass('active');
          $('.items .hot').stop().animate({'height':34},200);  
        }else{
         $(this).addClass('active');
         $('.items .hot').stop().animate({'height':h},200);  
        }
    });
    
    $('.yw_more').on('click',function(){
       if(num>lm.length){
         return false;
       }
       companyInfor.loadMore();
    });
     $('.zx_more').on('click',function(){
       if(num2>zx.length){
         return false;
       }
       companyInfor2.loadMore();
    });
    

})
var companyInfor = {
    _default:2, //默认显示个数
    _loading:4,  //每次点击按钮后加载的个数
    init:function(){

        var len=lm.length;
        if(len==0){
           $(".data_yw .data").html('<li style="border:0;">暂时没有业务介绍相关信息</li>');
           $('.yw_more').hide();
           return false;
        }else if(len>0&&len<=companyInfor._default){
            $('.yw_more').hide();
            
        }
           for(var n=0;n<companyInfor._default;n++){
                if(n>=lm.length){ 
                    break;
                }
                var str='';
                str+='<li><a target="_blank" href="hxywjs.html">';
                str+='<div class="img fl"><img src="http://g.sunaomei.com/'+lm[n]['bigpic']+'" alt=""></div>' ; 
                str+='<div class="txt fr">';
                str+='<h4>'+lm[n]['name']+'</h4>';
                str+='<p>'+limitSize(lm[n]['intro'],32)+'</p>';
                str+='</div></a></li>';
                $(".data_yw .data").append(str);
            }
        num=companyInfor._default;
    },
    loadMore:function(){ 
       
        for(var i =num;i<companyInfor._loading+num;i++){
			if(i==lm.length-1){
                $('.yw_more').html("<p>！ 没有更多了</p>");
            }
            if(i>=lm.length){
                $('.yw_more').html("<p>！ 没有更多了</p>");
                break;
            }
            var str='';
            str+='<li><a target="_blank" href="hxywjs.html">';
            str+='<div class="img fl"><img src="http://g.sunaomei.com/'+lm[i]['bigpic']+'" alt=""></div>' ; 
            str+='<div class="txt fr">';
            str+='<h4>'+lm[i]['name']+'</h4>';
            str+='<p>'+limitSize(lm[i]['intro'],32)+'</p>';
            str+='</div></a></li>';
            $(".data_yw .data").append(str);
        }
        num+=companyInfor._loading;
    }
}

var companyInfor2 = {
    _default:3, //默认显示个数
    _loading:3,  //每次点击按钮后加载的个数
    init:function(){
        var len=zx.length;
     
        if(len==0){
           $(".data_zx .data").html('<li style="border:0;">暂时没有资讯相关信息</li>');
           $('.zx_more').hide();
           return false;
        }else if(len>0&&len<=companyInfor2._default){
            $('.zx_more').hide();
      
        }
           for(var n=0;n<companyInfor2._default;n++){
              if(n>=zx.length){ 
                break;
              }
                var str='';
                str+='<li><a target="_blank" href="show.html">';
                str+='<h4>'+zx[n]['title']+'<span class="date fr">'+zx[n]['date']+'</span></h4>';
                str+='<p>'+limitSize(zx[n]['intro'],60)+'</p>';
                str+='</a></li>';
                $(".data_zx .data").append(str);
            }
            num2=companyInfor2._default;
      
    },
    loadMore:function(){ 
        for(var i =num2;i<companyInfor2._loading+num2;i++){
			if(i==zx.length-1){
                $('.zx_more').html("<p>！ 没有更多了</p>");
            }
            if(i>=zx.length){
                $('.zx_more').html("<p>！ 没有更多了</p>");
                break;
            }
            var str='';
            str+='<li><a target="_blank" href="show.html">';
            str+='<h4>'+zx[i]['title']+'<span class="date fr">'+zx[i]['date']+'</span></h4>';
            str+='<p>'+limitSize(zx[i]['intro'],60)+'</p>';
            str+='</a></li>';
            $(".data_zx .data").append(str);
        }
         num2+=companyInfor2._loading;
    }
}
//跨域
function callback (result){
    var data = JSON.stringify(result); //json对象转成字符串
 }
//字数控制
function limitSize(obj,nums){
    if(obj=='' || obj==null){
      return obj;
      return false;
    }
    var len = obj.length;
    if(len > nums){
       return obj.substring(0,nums)+'...';
    }
    return obj;
 }