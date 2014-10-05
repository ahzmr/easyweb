function winResize() {  // 自动计算高度并设置
    $("#mainFrame").css(
        { 'height': $(document).height() - $("#navtoolbar").outerHeight(true) - 18
        });
}
$(window).resize(function(){
    winResize();
});

$(function(){
    winResize();

    $("#toolbar li > a").click(function() {
        $("#toolbar li").removeClass("active");
        $(this).parent().addClass("active");
//               $("#mainFrame").attr("src", $(this).attr("href"));
        toPage($(this).attr("href"));
        return false;
    });

    var p = $.hash().get("p");
    if(!p) {
        p = $("#myself").attr("href");
    }
    toPage(p);
});
