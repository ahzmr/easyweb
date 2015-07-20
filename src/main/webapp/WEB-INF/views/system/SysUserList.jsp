<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<%@include file="/WEB-INF/includes/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta name="decorator" content="default"/>
    <title>用户列表</title>
    <script>
        $(function() {
            initForm("#searchForm");
        });
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${baseUrl}/system/SysUser/">用户列表</a></li>
    <shiro:hasPermission name="system:SysUser:edit"><li><a href="${baseUrl}/system/SysUser/form">用户添加</a></li></shiro:hasPermission>
</ul>
    <tags:message />
    <form id="searchForm" class="form-horizontal search-form"
          action="${baseUrl}/system/SysUser/" method="post">
        <div class="row">
            <div class="form-group col-lg-4 col-sm-6">
                <label class="col-sm-4 control-label">登录名：</label>
                <div class="col-sm-8">
                    <input type="text" name="loginName" data-defVal="${param.loginName}" class="form-control" />
                </div>
            </div>
            <div class="form-group col-lg-4 col-sm-6">
                <label class="col-sm-4 control-label">姓名：</label>
                <div class="col-sm-8">
                    <input type="text" name="name" data-defVal="${param.name}" class="form-control" />
                </div>
            </div>
            <div class="form-group col-lg-4 col-sm-6">
                <label class="col-sm-4 control-label">手机：</label>
                <div class="col-sm-8">
                    <input type="text" name="mobile" data-defVal="${param.mobile}" class="form-control" />
                </div>
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-12 text-center">
                <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
                <input class="btn" type="reset" value="重置"/>
            </div>
        </div>
    </form>
<div class="container-fluid table-responsive">
    <table class="table table-condensed table-hover table-striped table-bordered" >
        <thead>
        <tr>
            <th>登录名</th>
            <th>工号</th>
            <th>姓名</th>
            <th>邮箱</th>
            <th>手机</th>
            <th>最后登陆IP</th>
            <th>最后登陆时间</th>
            <th>更新者</th>
            <th>更新时间</th>
            <th>备注信息</th>
            <shiro:hasPermission name="system:SysUser:edit">
                <th>操作</th>
            </shiro:hasPermission>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${page}" var="entity">
            <tr>
                    <td><a href="${baseUrl}/system/SysUser/form?isView=1&id=${entity.loginName}">${entity.loginName}</a></td>
                    <td>${entity.no}</td>
                    <td>${entity.name}</td>
                    <td>${entity.email}</td>
                    <td>${entity.mobile}</td>
                    <td>${entity.loginIp}</td>
                    <td><fmt:formatDate value="${entity.loginDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                    <td>${entity.updateBy}</td>
                    <td><fmt:formatDate value="${entity.updateDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                    <td>${entity.remarks}</td>
                <shiro:hasPermission name="system:SysUser:edit"><td>
                    <a href="${baseUrl}/system/SysUser/form?id=${entity.id}">修改</a>
                    <a href="${baseUrl}/system/SysUser/delete?id=${entity.id}" onclick="return confirmx('确认要删除该用户吗？', this.href)">删除</a>
                </td></shiro:hasPermission>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <tags:pagination page="${page}" />
</div>
</body>
</html>
