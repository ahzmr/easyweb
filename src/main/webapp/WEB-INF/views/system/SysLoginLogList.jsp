<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<%@include file="/WEB-INF/includes/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta name="decorator" content="default"/>
    <title>登录日志列表</title>
    <script>
        $(function() {
            initForm("#searchForm");
        });
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${baseUrl}/system/SysLoginLog/">登录日志列表</a></li>
</ul>
<form id="searchForm" class="form-horizontal search-form"
      action="${baseUrl}/system/SysLoginLog/" method="post">
    <div class="row">
        <div class="form-group col-lg-4 col-sm-6">
            <label class="col-sm-4 control-label">登录人：</label>
            <div class="col-sm-8">
                <input type="text" name="createBy" data-defVal="${param.createBy}" class="form-control" />
            </div>
        </div>
        <div class="form-group col-lg-4 col-sm-6">
            <label class="col-sm-4 control-label">登录开始时间：</label>
            <div class="col-sm-8">
                <input type="text" name="createDateStart" data-defVal="${param.createDateStart}" class="Wdate form-control"
                       value="<fmt:formatDate value="${entry.createDateStart}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" />
            </div>
        </div>
        <div class="form-group col-lg-4 col-sm-6">
            <label class="col-sm-4 control-label">登录结束时间：</label>
            <div class="col-sm-8">
                <input type="text" name="createDateEnd" data-defVal="${param.createDateEnd}" class="Wdate form-control"
                       value="<fmt:formatDate value="${entry.createDateEnd}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" />
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
<tags:message />
<div class="container-fluid table-responsive">
    <table class="table table-condensed table-hover table-striped table-bordered" >
        <thead>
        <tr>
            <th>编号</th>
            <th>登陆人</th>
            <th>登陆时间</th>
            <th>退出时间</th>
            <th>登录IP</th>
            <th>登录地点</th>
            <th>操作系统</th>
            <th>操作系统版本</th>
            <th>设备名称</th>
            <th>设备类型</th>
            <th>应用名称</th>
            <th>应用版本</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${page}" var="entity">
            <tr>
                    <td><a href="${baseUrl}/system/SysLoginLog/form?isView=1&id=${entity.id}">${entity.id}</a></td>
                    <td>${entity.createBy}</td>
                    <td><fmt:formatDate value="${entity.createDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                    <td><fmt:formatDate value="${entity.logoutDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                    <td>${entity.loginIp}</td>
                    <td>${entity.loginLocation}</td>
                    <td>${entity.osName}</td>
                    <td>${entity.osVersion}</td>
                    <td>${entity.deviceName}</td>
                    <td>${entity.deviceType}</td>
                    <td>${entity.appName}</td>
                    <td>${entity.appVersion}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <tags:pagination page="${page}" />
</div>
</body>
</html>
