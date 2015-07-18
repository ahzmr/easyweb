;initPage();

$(function(){
    initDoc();
});

function winResize() {  // 自动计算高度并设置
    $("#mainFrame").css(
        { 'height': $(document).height() - $("#navtoolbar").outerHeight(true) - 18
        });
    $("#mainFrame").ready(function() {
        $('a[href][target="_blank"]').click(function(e) {
            e.preventDefault();
            toPage($(this.href));
            return true;
        });
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

    $("#navbar-collapse ul.navbar-nav > li > a[id]").click(function(e) {
        if(!$(this).attr("href")) {
            return true;
        }
        e.preventDefault();
        return updateActive(this, $(this).attr("id")) || true;
    });
    $("#navbar-collapse ul.dropdown-menu > li > a[id]").click(function(e) {
        e.preventDefault();
        return updateActive(this) || true;
    });

    var p = $.hash().get("p");
    if(!p) {
        goIndex();
    } else {
        toPage(p);
    }

    // 监听iframe变化
    $("#mainFrame").on("load", function() {
        var p = subUrlFormat(this.contentWindow.location.href);
        var m0 = $("#navbar-collapse li.active > a").attr("id");
        top.$.hash().set({"m0": m0, "p": p}).location('?');
    });
}

function goIndex() {
    return updateActive($("#navbar-collapse li > a").first());
}

function updateActive(thiz) { // 更新工具栏选择状态
    $(thiz).parent().addClass("active");
    $("#navbar-collapse li").not($(thiz)).removeClass("active");
    toPage(thiz);
    return false;
}