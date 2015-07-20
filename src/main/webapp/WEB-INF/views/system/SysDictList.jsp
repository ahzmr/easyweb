<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<%@include file="/WEB-INF/includes/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta name="decorator" content="default"/>
    <title>字典列表</title>
    <script>
        $(function() {
            initForm("#searchForm");
        });
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${baseUrl}/system/SysDict/">字典列表</a></li>
    <shiro:hasPermission name="system:SysDict:edit"><li><a href="${baseUrl}/system/SysDict/form">字典添加</a></li></shiro:hasPermission>
</ul>
<form:form id="searchForm" class="form-horizontal search-form" modelAttribute="entry"
      action="${baseUrl}/system/SysDict/" method="post">
    <div class="row">
        <div class="form-group col-lg-4 col-sm-6">
            <label class="col-sm-4 control-label">字典类型：</label>
            <div class="col-sm-8">
                <input type="text" name="type" data-defVal="${param.type}" class="form-control" />
            </div>
        </div>
        <div class="form-group col-lg-4 col-sm-6">
            <label class="col-sm-4 control-label">标签名：</label>
            <div class="col-sm-8">
                <input type="text" name="label" data-defVal="${param.label}" class="form-control" />
            </div>
        </div>
        <div class="form-group col-lg-4 col-sm-6">
            <label class="col-sm-4 control-label">描述：</label>
            <div class="col-sm-8">
                <input type="text" name="description" data-defVal="${param.description}" class="form-control" />
            </div>
        </div>
    </div>

    <div class="form-group">
        <div class="col-sm-12 text-center">
            <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
            <input class="btn" type="reset" value="重置"/>
        </div>
    </div>
</form:form>
<tags:message />
<div class="container-fluid table-responsive">
    <table class="table table-condensed table-hover table-striped table-bordered" >
        <thead>
        <tr>
            <th>类型</th>
            <th>标签名</th>
            <th>数据值</th>
            <th>描述</th>
            <th>排序</th>
            <th>更新者</th>
            <th>更新时间</th>
        <shiro:hasPermission name="system:SysRole:edit">
            <th>操作</th>
        </shiro:hasPermission>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${page}" var="entity">
            <tr>
                    <td>${entity.type}</td>
                    <td><a href="${baseUrl}/system/SysDict/form?isView=1&id=${entity.label}">${entity.label}</a></td>
                    <td>${entity.value}</td>
                    <td>${entity.description}</td>
                    <td>${entity.sort}</td>
                    <td>${entity.updateBy}</td>
                    <td><fmt:formatDate value="${entity.updateDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                <shiro:hasPermission name="system:SysDict:edit"><td>
                    <a href="${baseUrl}/system/SysDict/form?id=${entity.id}">修改</a>
                    <a href="${baseUrl}/system/SysDict/form?type=${entity.type}&label=${entity.label}&value=${entity.value}&description=${entity.description}&sort=${entity.sort}">拷贝字典</a>
                    <a href="${baseUrl}/system/SysDict/delete?id=${entity.id}" onclick="return confirmx('确认要删除该字典吗？', this.href)">删除</a>
                </td></shiro:hasPermission>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <tags:pagination page="${page}" />
</div>
</body>
</html>
