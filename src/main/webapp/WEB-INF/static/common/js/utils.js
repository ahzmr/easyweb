var global = global || {};

function goHistory(cur) {
    history.go(cur);
}

function toPage(path) {
    if(null) {
        return;
    }
    var p = path;
    if(typeof path != "string") {
        p = $(path).attr("href");
    }
    global.prevPage = $.hash().get("p");
    top.$.hash().set("p", p).location('?');
    var $mainFrame = $("#mainFrame");
    if($mainFrame && $mainFrame.length) {
        $mainFrame.attr("src", p);
    } else {
        location.href = location.href.replace(/\/easyweb\/[^#]+/i, "/easyweb/");
    }
    return false;   // 成功处理，不继续处理
}

$(function() {
   if(top == window && "/easyweb/" != location.pathname) {
       toPage(location.pathname);
   }
});
