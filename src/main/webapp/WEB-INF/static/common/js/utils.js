function goHistory(cur) {   // 历史跳转
    history.go(cur);
}

function subUrlFormat(url) {    // 子网网站格式化
    if(!url) {
        return url;
    }
    return url.replace(/.*(\/easyweb\/)/i, "");
}

function toPage(path, m0) { // 跳转页面
    if(!path) {
        return;
    }
    var p = path;
    if(typeof path != "string") {
        p = $(path).attr("href");
    }
    p = subUrlFormat(p);
    var $mainFrame = top.$("#mainFrame");
    if($mainFrame && $mainFrame.length) {
        $mainFrame.attr("src", p);
    } else {
        location.href = '/easyweb/#?' + (m0?'m0='+m0+'&':'') + 'p=' + p;
    }
    return false;   // 成功处理，不继续处理
}

function page(pageNum) {   // 分页跳转
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
