$(function(){
    //大事记
     $(".event-line-warp").slide({
        mainCell: ".event-list-main ul",
        vis: 4,
        scroll: 1,
        easing: "swing",
        effect: "left",
        autoPage: !0,
        pnLoop: !1,
        startFun: function(t, e) {
            // $(".event-line").append('<div class="full-time"></div>'); 
            0 == t ? $(".event-p-n.prev").hide() : $(".event-p-n.prev").show();
            t == e - 1 ? $(".event-p-n.next").hide() : $(".event-p-n.next").show();
            $(".event-txt").hide(); 
        },
        endFun: function() {
            $(".event-time").remove();
        }
    });
    var i = $(".event-list-main li"),
        s = $(".event-txt"),
        o = i.first(),
        a = o.offset().left-25 ,
        n = o.attr("data-content"),
        l = o.attr("data-src");
    s.find(".p").text(n).end().show().animate({
        left: a
    }, 300).find(".img").empty().append('<img src="' + l + '">');
    i.first().addClass("active");
    var e1=$(".event-list-main li").first().offset().left-25,
        e2=$(".event-list-main li").eq(3).offset().left-25;
    i.mouseenter(function() {
        var t = $(this),
            e = t.offset().left-25;    
        if(e<=e1){
          e=e1;
        } 
        if(e>=e2){
          e=e2;
        }  
        $(".event-list-main li").removeClass("active");
        t.addClass("active"); 
        n = t.attr("data-content");
        l = t.attr("data-src");
        s.find(".img").empty();
        s.stop(true,true).animate({
            left: e
        }, 300);
         "" != l && null != l && void 0 != l && s.find(".img").append('<img src="' + l + '">');
         "" != n && null != n && void 0 != n ? s.find(".p").text(n).end().find("img").attr("src", l).end().show() : s.hide();
        
        
    })
})
    