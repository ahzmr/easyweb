<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<%@include file="/WEB-INF/includes/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta name="decorator" content="default"/>
    <title>角色列表</title>
    <script>
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${baseUrl}/system/SysRole/">角色列表</a></li>
    <shiro:hasPermission name="system:SysRole:edit"><li><a href="${baseUrl}/system/SysRole/form">角色添加</a></li></shiro:hasPermission>
</ul>
<div class="container-fluid table-responsive">
    <tags:message />
    <form id="searchForm" class="form-horizontal"
          action="${baseUrl}/system/SysRole/" method="post">

        <div class="form-group">
            <div class="col-sm-12 text-center">
                <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
                <input class="btn" type="reset" value="重置"/>
            </div>
        </div>
    </form>
    <table class="table table-condensed table-hover table-striped table-bordered" >
        <thead>
        <tr>
            <th>编号</th>
            <th>名称</th>
            <th>编码</th>
            <th>创建者</th>
            <th>创建时间</th>
            <th>更新者</th>
            <th>更新时间</th>
            <th>备注信息</th>
            <shiro:hasPermission name="system:SysRole:edit">
                <th>操作</th>
            </shiro:hasPermission>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${page}" var="entity">
            <tr>
                    <td>${entity.id}</td>
                    <td><a href="${baseUrl}/system/SysRole/form?isView=1&id=${entity.id}">${entity.name}</a></td>
                    <td>${entity.code}</td>
                    <td>${entity.createBy}</td>
                    <td><fmt:formatDate value="${entity.createDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                    <td>${entity.updateBy}</td>
                    <td><fmt:formatDate value="${entity.updateDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                    <td>${entity.remarks}</td>
                <shiro:hasPermission name="system:SysRole:edit"><td>
                    <a href="${baseUrl}/system/SysRole/form?id=${entity.id}">修改</a>
                    <a href="${baseUrl}/system/SysRole/delete?id=${entity.id}" onclick="return confirmx('确认要删除该角色吗？', this.href)">删除</a>
                </td></shiro:hasPermission>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <tags:pagination page="${page}" />
</div>
</body>
</html>
