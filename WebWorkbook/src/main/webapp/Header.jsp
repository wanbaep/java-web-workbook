<%--
  Created by IntelliJ IDEA.
  User: wanbaep
  Date: 2021/06/16
  Time: 7:21 오전
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div style="background-color: #00008b; color: #ffffff; height: 20px;padding:5px;">
  Simple Project Management System
  <c:if test="${!empty sessionScope.member and
              !empty sessionScope.member.email}">
  <span style="float:right;">
    ${sessionScope.member.name}
    <a style="color:white;" href="<%=request.getContextPath()%>/auth/logout.do">로그아웃</a>
  </span>
  </c:if>
</div>