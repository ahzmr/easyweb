<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@include file="/WEB-INF/includes/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta name="decorator" content="default"/>
    <title>通讯录列表</title>
    <script>
        $(function(){
            $("#viewType > label").click(function(){
                $(this).find('input:radio').attr("checked", "checked");
                $("#searchForm").submit();
            });
            $("[name='viewType'][value='${viewType}']:radio").parent().button('toggle');
            initForm("#searchForm");
        });
    </script>
</head>
<body>
<div class="container-fluid table-responsive">
    <tags:message />
    <form id="searchForm" class="form-horizontal searchForm"
          action="${baseUrl}/contacts/" method="post">
        <div class="row">
            <div class="form-group col-lg-6 col-sm-8">
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
            </div>
        </div>
        <div class="row">
            <div class="form-group col-lg-3 col-sm-6">
                <label class="col-sm-4 control-label">姓名：</label>
                <div class="col-sm-8">
                    <input type="text" name="name" data-defVal="${param.name}" class="form-control" />
                </div>
            </div>
            <div class="form-group col-lg-3 col-sm-6">
                <label class="col-sm-4 control-label">常住地点：</label>
                <div class="col-sm-8">
                    <input type="text" name="address" data-defVal="${param.address}" class="form-control" />
                </div>
            </div>
            <div class="form-group col-lg-3 col-sm-6">
                <label class="col-sm-4 control-label">工作地点：</label>
                <div class="col-sm-8">
                    <input type="text" name="workAddr" data-defVal="${param.workAddr}" class="form-control" />
                </div>
            </div>
            <div class="form-group col-lg-3 col-sm-6">
                <label class="col-sm-4 control-label">公司：</label>
                <div class="col-sm-8">
                    <input type="text" name="company" data-defVal="${param.company}" class="form-control" />
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
