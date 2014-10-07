function goHistory(cur) {   // 历史跳转
    history.go(cur - 1);
}

function toPage(path) { // 跳转页面
    if(null) {
        return;
    }
    var p = path;
    if(typeof path != "string") {
        p = $(path).attr("href");
    }
    var $mainFrame = $("#mainFrame");
    if($mainFrame && $mainFrame.length) {
        $mainFrame.attr("src", p);
    } else {
        top.$.hash().set("p", p).location('?');
        location.href = location.href.replace(/(\/easyweb\/)[^#]+/i, "");
    }
    return false;   // 成功处理，不继续处理
}

function page(pageNum) {   // 分布跳转
    var searchForm = $("#searchForm");
    if(!searchForm) {
        searchForm = $("form:first");
    }
    var $pageNum = searchForm.find('[name="pageNum"]:input');
    if(!$pageNum.length) {
        $pageNum=$('<input type="hidden" name="pageNum" />');
        searchForm.append($pageNum);
    }
    $pageNum.val(pageNum);
    searchForm.submit();
    return false;
}

$(function() {
   if(top == window && "/easyweb/" != location.pathname) {
       toPage(location.pathname);
   }
});
