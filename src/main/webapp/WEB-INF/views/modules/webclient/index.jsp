<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@include file="/WEB-INF/includes/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/includes/common.jsp"%>
    <title>测试投票</title>
    <script>
        function reloadImg(id) {
            var img = $("#" + id + " img");
            img.attr("src", "getImage.html?id=" + id + "&r=" + Math.random());
        }
        $(function(){
            $.ajaxSetup({
                            timeout: 180000
                        });
            var cmtFun = function () {
                var myid = $(this).attr("myid");
                $("#" + myid + " p").text("提交中……");
                $.post("commit.json", {id: myid,
                    code: $(this).val()}, function (rs) {
                    var id = rs.id;
                    var label = $("#" + id + " p");
                    label.text(rs.msg);
                });
            };
            $("input[name='code']").on("change", cmtFun);
//                $(this).on("focusout", cmtFun);
        });
    </script>
</head>
<body>
<div class="container-fluid">
    <div class="page-header">
        <h1>投票</h1>
        <p class="lead">为${userId}投票.</p>
    </div>

    <h3></h3>

    <p></p>
    <c:forEach items="${idList}" var="id">
        <div class="row" id="${id}">
            <div class="col-md-3">
                <input type="text" name="code" myid="${id}" style="border:1px solid #8a8a8a;width:80px;height:25px">
                <img src="getImage.html?id=${id}" onclick="return reloadImg('${id}');">
            </div>
            <div class="col-md-6">
                <p />
            </div>
        </div>
    </c:forEach>

</div>
</body>
</html>
