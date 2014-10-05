;initPage();

$(function(){
    initDoc();
});

function winResize() {  // 自动计算高度并设置
    $("#mainFrame").css(
        { 'height': $(document).height() - $("#navtoolbar").outerHeight(true) - 18
        });
}

function initPage() {   // 初始化页面
    if(top != window) {
        top.location.href = location.href;
    }

    $(window).resize(function(){
        winResize();
    });
}

function initDoc() {
    winResize();

    $("#toolbar li > a").click(function() {
        $("#toolbar li").removeClass("active");
        $(this).parent().addClass("active");
        toPage($(this).attr("href"));
        return false;
    });

    var p = $.hash().get("p");
    if(!p) {
        p = $("#myself").attr("href");
    }
    toPage(p);

    // 监听iframe变化
    $("#mainFrame").on("load", function() {
        var p = this.contentWindow.location.href.replace(/.+(\/easyweb\/)/i, "$1");
        toPage(p, true);
        top.$.hash().set("p", p).location('?');
    });
}
