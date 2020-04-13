$(".navlist").mouseover(function() {
    var ref = $(this);
    ref.find("h3").addClass("hover");
    ref.find(".navmore").show();
    $(this).css("z-index", 2);
}).mouseleave(function() {
    var ref = $(this);
    ref.find("h3").removeClass("hover");
    ref.find(".navmore").hide();
    $(this).css("z-index", 0);
});