<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<%@include file="/WEB-INF/includes/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta name="decorator" content="tree"/>
    <title>菜单列表</title>
    <script>
        $(function() {
            $("#treeTable").treetable({expandable: true});
        });
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${baseUrl}/system/SysMenu/">菜单列表</a></li>
    <shiro:hasPermission name="system:SysMenu:edit"><li><a href="${baseUrl}/system/SysMenu/form">菜单添加</a></li></shiro:hasPermission>
</ul>
<div class="container-fluid table-responsive">
    <tags:message />
    <table id="treeTable" class="table table-condensed table-hover table-striped table-bordered treetable" >
        <thead>
        <tr>
            <th>名称</th>
            <th>链接</th>
            <th>是否显示</th>
            <th>权限标识</th>
            <th>备注信息</th>
        <shiro:hasPermission name="system:SysRole:edit">
            <th>操作</th>
        </shiro:hasPermission>
        </tr >
        </thead>
        <tbody>
        <c:forEach items="${page}" var="entity">
            <tr data-tt-id="${entity.id}" data-tt-parent-id="${entity.parentId}">
                    <td><a href="${baseUrl}/system/SysMenu/form?isView=1&id=${entity.id}">${entity.name}</a></td>
                    <td>${entity.href}</td>
                    <td>${entity.isShow}</td>
                    <td>${entity.permission}</td>
                    <td>${entity.remarks}</td>
                <shiro:hasPermission name="system:SysMenu:edit"><td>
                    <a href="${baseUrl}/system/SysMenu/form?id=${entity.id}">修改</a>
                    <a href="${baseUrl}/system/SysMenu/delete?id=${entity.id}" onclick="return confirmx('确认要删除该菜单吗？', this.href)">删除</a>
                </td></shiro:hasPermission>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <tags:pagination page="${page}" />
</div>
</body>
</html>
