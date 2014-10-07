<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@include file="/WEB-INF/includes/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <%@include file="/WEB-INF/includes/common.jsp"%>
    <title>通讯录列表</title>
    <script>
        $(function(){
            $("#viewType > label").click(function(){
                $(this).find('input:radio').attr("checked", "checked");
                $("#searchForm").submit();
            });
            $("[name='viewType'][value='${viewType}']:radio").parent().button('toggle');
        });
    </script>
</head>
<body>
<div class="container-fluid table-responsive">
    <tags:message />
    <form id="searchForm" class="form-horizontal"
          action="${baseUrl}/contacts/list.html" method="post">
        <div id="viewType" class="btn-group" data-toggle="buttons">
            <label class="btn btn-default">
                <input type="radio" name="viewType" value="2" />查看全部
            </label>
            <label class="btn btn-default">
                <input type="radio" name="viewType" value="1" />查看已填写
            </label>
            <label class="btn btn-default">
                <input type="radio" name="viewType" value="0" />查看未填写
            </label>
        </div>
    </form>
    <table class="table table-condensed table-hover table-striped table-bordered" >
        <thead>
        <tr>
            <th>序号</th>
            <th>姓名</th>
            <th>常住地点</th>
            <th>工作地点</th>
            <th>职业</th>
            <th>公司</th>
            <th>互助信息</th>
        </tr>
        </thead>
        <tbody>
            <c:forEach items="${page}" var="entry" varStatus="idx">
            <tr>
                <td>${fn:substringAfter(entry.id, '07070110')}</td>
                <td><a href="${baseUrl}/contacts/form.html?id=${entry.id}">${entry.name}</a></td>
                <td>${entry.address}</td>
                <td>${entry.workAddr}</td>
                <td>${entry.job}</td>
                <td>${entry.company}</td>
                <td>${entry.myMsg}</td>
            </tr>
            </c:forEach>
        </tbody>
    </table>
    <tags:pagination page="${page}" />
</div>
</body>
</html>
