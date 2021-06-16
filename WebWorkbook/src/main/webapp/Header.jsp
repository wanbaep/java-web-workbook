<%--
  Created by IntelliJ IDEA.
  User: wanbaep
  Date: 2021/06/16
  Time: 7:21 오전
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="wanbaep.workbook.vo.Member" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  Member member = (Member)session.getAttribute("member");
%>
<div style="background-color: #00008b; color: #ffffff; height: 20px;padding:5px;">
  Simple Project Management System
  <span style="float:right;">
    <%=member.getName()%>
    <a style="color:white;" href="<%=request.getContextPath()%>/auth/logout">로그아웃</a>
  </span>
</div>