<%--
  Created by IntelliJ IDEA.
  User: wanbaep
  Date: 2021/06/17
  Time: 7:12 오전
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>회원추가</title>
</head>
<body>
<h1>회원 등록</h1>
<form action="add.do" method="post">
  이름: <input type="text" name="name"><br>
  이메일: <input type="text" name="email"><br>
  암호: <input type="password" name="password"><br>
  <input type="submit" value="추가">
  <input type="reset" value="취소">
</form>
</body>
</html>
