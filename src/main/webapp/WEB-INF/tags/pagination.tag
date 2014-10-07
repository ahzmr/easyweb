<%@ tag import="com.wenin819.easyweb.core.db.Page" %>
<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ include file="/WEB-INF/includes/taglib.jsp" %>
<%@ attribute name="page" type="com.wenin819.easyweb.core.db.Page"
              required="true" description="分页信息" %>
<ul class="pagination" style="margin-top: 0">
    <%
        Page<?> thisPage = (Page<?>) page;
        System.out.println(thisPage);
        final int paginationSize = 7;
        final int tep = paginationSize / 2;
        final int pages = thisPage.getPages();
        final int pageNum = thisPage.getPageNum();
        int first = pageNum - tep;
        if(1 > first) {
            first = 1;
        }
        int end = pageNum + tep;
        if(pages < end) {
            end = pages;
        }
    %>
    <li <%=pageNum==1?"class='disabled'":""%>><a href="javascript:" onclick="return page(1)">首页</a></li>
    <li <%=pageNum==1?"class='disabled'":""%>><a href="javascript:" onclick="return page(<%=pageNum-1%>)">上一页</a></li>
<% for (int i = first; i <= end; i++) {%>
    <li <%=pageNum==i?"class='active'":""%>><a href="javascript:" onclick="return page(<%=i%>)"><%=i%></a></li>
<%}%>
    <li <%=pageNum==pages?"class='disabled'":""%>><a href="javascript:" onclick="return page(<%=pageNum+1%>)">下一页</a></li>
    <li <%=pageNum==pages?"class='disabled'":""%>><a href="javascript:" onclick="return page(<%=pages%>)">末页</a></li>
</ul>
