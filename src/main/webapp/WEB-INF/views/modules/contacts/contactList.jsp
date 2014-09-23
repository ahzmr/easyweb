<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@include file="/WEB-INF/includes/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/includes/common.jsp"%>
    <title>通讯录列表</title>
    <script>
        $(function(){
            $("[name='viewType']:input").click(function(){
                $("#searchForm").submit();
            });
            $("[name='viewType'][value='${empty param.viewType?2:param.viewType}']:input")
                    .attr("checked", "checked");
        });
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${baseUrl}/contacts/list.html">通讯录列表</a></li>
    <li><a href="${baseUrl}/contacts/form.html">通讯录添加</a></li>
</ul><br/>

<div class="container-fluid">
    <form id="searchForm" class="form-horizontal"
          action="${baseUrl}/contacts/list.html" method="post">
        <div class="form-group">
            <div class="col-sm-offset-1 col-sm-11">
                <div class="radio">
                    <label class="radio-inline">
                        <input type="radio" name="viewType" value="2" />查看全部
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="viewType" value="1" />查看已填写
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="viewType" value="0" />查看未填写
                    </label>
                </div>
            </div>
        </div>
    </form>
</div>

<div class="container-fluid table-responsive">
    <tags:message content="${empty message?param.message:message}"/>
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
            <c:forEach items="${list}" var="entry" varStatus="idx">
            <tr>
                <td>${idx.index + 1}</td>
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
</div>
</body>
</html>
