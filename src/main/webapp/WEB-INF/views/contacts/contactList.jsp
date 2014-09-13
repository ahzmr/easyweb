<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@include file="/WEB-INF/includes/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/includes/common.jsp"%>
    <title>通讯录列表</title>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${baseUrl}/contacts/list.html">通讯录列表</a></li>
    <li><a href="${baseUrl}/contacts/form.html?id=${entry.id}">通讯录${fn:length(entry.id) gt 8 ?'修改':'添加'}</a></li>
</ul><br/>

<div class="container-fluid">
    <table class="table table-condensed table-hover" >
        <thead>
        <tr>
            <th>姓名</th>
            <th>常住地点</th>
            <th>工作地点</th>
            <th>职业</th>
            <th>公司</th>
            <th>互助信息</th>
        </tr>
        </thead>
        <tbody>
            <c:forEach items="${list}" var="entry">
                <td>${entry.name}</td>
                <td>${entry.address}</td>
                <td>${entry.workAddr}</td>
                <td>${entry.job}</td>
                <td>${entry.company}</td>
                <td>${entry.myMsg}</td>
            </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
