<%--
  Created by IntelliJ IDEA.
  User: wanbaep
  Date: 2021/06/15
  Time: 7:26 오전
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html"; charset="UTF-8">
    <title>회원 목록</title>
</head>
<body>
<jsp:include page="/Header.jsp"/>
<h1>회원 목록</h1>
<p><a href='add.do'>신규 회원</a></p>
<c:forEach var="member" items="${members}">
    ${member.no},
    <a href="update.do?no=${member.no}">${member.name}</a>,
    ${member.email},
    ${member.createdDate}
    <a href="delete.do?no=${member.no}">[삭제]</a><br>
</c:forEach>

<jsp:include page="/Tail.jsp"/>
</body>
</html>
