;if(top != window) {
    top.location.href = location.href;
}

$(function(){
    initDoc();
});

function winResize() {  // 自动计算高度并设置
    $("#mainFrame").css(
        { 'height': $(document).height() - $("#navtoolbar").outerHeight(true) - 18
        });
}

function initDoc() {
    goIndex();

    winResize();

    $(window).resize(function(){
        winResize();
    });
}

function goIndex() {
    return toPage($("#navbar-collapse li a[href*='/']").first());
}