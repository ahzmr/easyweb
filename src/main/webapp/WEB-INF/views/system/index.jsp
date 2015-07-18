<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@include file="/WEB-INF/includes/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <%@include file="/WEB-INF/includes/common.jsp"%>
    <script src="${commonUrl}/js/main.js"></script>
    <title>综合管理系统</title>
    <style>
        body { overflow: hidden;}
        #mainFrame {
            border-style: none;
            border-width: 0;
            width: 100%;
        }
    </style>
    <script>
        var sys_menus = (function() {
            var allMenus = ${menus};
            var menus = [];
            if(allMenus) {
                menuDict = {};
                for (var i = 0; i < allMenus.length; i++) {
                    var menu = allMenus[i];
                    menuDict[menu.id] = menu;
                }
                for (var i = 0; i < allMenus.length; i++) {
                    var menu = allMenus[i];
                    var parent = menuDict[menu.parentId];
                    if(!parent) {
                        menus.push(menu);
                    } else {
                        var children = parent.children;
                        if(!children) {
                            children = [];
                            parent.children = children;
                        }
                        children.push(menu);
                    }
                }
            }
            return {
                getMenu: function() {
                    return menus;
                }
            };
        })();

        var myMenu = angular.module("myMenu", []);
        myMenu.controller("initMenus", function($scope) {
            $scope.menus = sys_menus.getMenu();
        });
    </script>
</head>
<body>
<nav id="navtoolbar" class="navbar navbar-default" role="navigation">
    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse"
                data-target="#navbar-collapse">
            <span class="sr-only">切换导航</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="#" onclick="return false;">wenin819</a>
    </div>
    <div class="collapse navbar-collapse" id="navbar-collapse" ng-app="myMenu">
        <ul id="toolbar" class="nav navbar-nav" ng-controller="initMenus">
            <li class="dropdown" ng-repeat="menu1 in menus">
                <a id="{{menu1.id}}" href="{{menu1.href}}" class="dropdown-toggle" data-toggle="dropdown">
                    {{menu1.name}} &nbsp; <span class='{{menu1.children && "caret"}}'></span>
                </a>
                <ul class="dropdown-menu dropdown-menu-right">
                    <li ng-repeat="menu2 in menu1.children"><a id="{{menu2.id}}" href="{{menu2.href}}">{{menu2.name}}</a></li>
                </ul>
            </li>
            <%--<li><a id="m0_contacts" href="javascript:"--%>
                   <%--data-url="${baseUrl}/contacts/" >通讯录</a></li>--%>
            <%--<li><a id="m0_webclient" href="${baseUrl}/webclient/" >投票</a></li>--%>
        </ul>
        <shiro:authenticated>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a href="javascript:" class="dropdown-toggle" data-toggle="dropdown">
                        <shiro:principal property="name"/> &nbsp;<b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu dropdown-menu-right">
                        <li><a id="selfInfo" href="${baseUrl}/contacts/form.html?id=<shiro:principal property='no'/>">
                            修改个人信息</a></li>
                        <li><a id="selfPwd" href="${baseUrl}/sys/user/modifyPwd.html">
                            修改个人密码</a></li>
                    </ul>
                </li>
                <li><a href="${baseUrl}/logout.html">退出</a></li>
            </ul>
        </shiro:authenticated>
    </div>
</nav>
<div class="container-fluid">
    <iframe id="mainFrame" name="mainFrame" src="" frameborder="0" scrolling="auto" >
    </iframe>
</div>
</body>
</html>
