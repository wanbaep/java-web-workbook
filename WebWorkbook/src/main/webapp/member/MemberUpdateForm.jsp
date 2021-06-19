<%@ page import="wanbaep.workbook.vo.Member" %><%--
  Created by IntelliJ IDEA.
  User: wanbaep
  Date: 2021/06/17
  Time: 7:23 오전
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>회원정보</title>
</head>
<body>
<h1>회원정보</h1>
<%
    Member member = (Member) request.getAttribute("updateMember");
%>
<form action="update.do" method="post">
    번호: <input type="text" name="no" value="<%=member.getNo()%>" readonly><br>
    이름: <input type="text" name="name" value="<%=member.getName()%>"><br>
    이메일: <input type="text" name="email" + value="<%=member.getEmail()%>"><br>
    가입일: <%=member.getCreatedDate()%><br>
    <input type="submit" value="저장">
    <input type="button" value="삭제" onclick="location.href='delete.do?no=<%=member.getNo()%>';">
    <input type="button" value="취소" onclick="location.href='list.do'">
</form>
</body>
</html>
