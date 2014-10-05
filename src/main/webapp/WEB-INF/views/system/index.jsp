<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@include file="/WEB-INF/includes/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <%@include file="/WEB-INF/includes/common.jsp"%>
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
        function winResize() {  // 自动计算高度
            $("#mainFrame").css(
                    { 'height': $(document).height() - $("#navtoolbar").outerHeight(true) - 18
                                });
        }
        $(window).resize(function(){
            winResize();
        });

        $(function(){
           winResize();

           $("#toolbar li > a").click(function() {
               $("#toolbar li").removeClass("active");
               $(this).parent().addClass("active");
           });

           if($("#myself")) {
               $("#mainFrame").attr("src", $("#myself").attr("href"));
           };
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
        <a class="navbar-brand" href="#">wenin819</a>
    </div>
    <div class="collapse navbar-collapse" id="navbar-collapse">
        <ul id="toolbar" class="nav navbar-nav">
            <li><a href="${baseUrl}/contacts/" target="mainFrame">通讯录</a></li>
            <li><a href="${baseUrl}/webclient/" target="mainFrame">投票</a></li>
        </ul>
        <shiro:authenticated>
            <ul class="nav navbar-nav navbar-right">
                <li><a id="myself"
                        href="${baseUrl}/contacts/form.html?id=<shiro:principal property='id'/>" target="mainFrame">
                    <shiro:principal property="name"/></a></li>
                <li><a href="${baseUrl}/logout.html" >退出</a></li>
            </ul>
        </shiro:authenticated>
    </div>
</nav>
<div class="container-fluid">
    <iframe id="mainFrame" name="mainFrame" src="" >
    </iframe>
</div>
</body>
</html>
