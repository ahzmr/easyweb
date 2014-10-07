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

    $("#navbar-collapse li > a[id]").click(function() {
        $("#navbar-collapse li").removeClass("active");
        $(this).parent().addClass("active");
        toPage($(this).attr("href"));
        return false;
    });

    var p = $.hash().get("p");
    var m0 = $.hash().get("m0");
    if(m0) {
        $("#" + m0).parent().addClass("active");
    }
    if(!p) {
        p = $("#myself").attr("href");
    }
    toPage(p);

    // 监听iframe变化
    $("#mainFrame").on("load", function() {
        var p = this.contentWindow.location.href.replace(/.+(\/easyweb\/)/i, "");
        var m0 = $("#navbar-collapse li.active > a").attr("id");
        m0 = m0 || "";
        top.$.hash().set({"m0": m0, "p": p}).location('?');
    });
}
