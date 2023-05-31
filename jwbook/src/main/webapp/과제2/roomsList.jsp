<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>방명록</title>
</head>
<body>
<h2>방명록 목록</h2>
<hr>
<table border="1">
<tr><th>번호</th><th>작성자</th><th>이메일</th><th>작성일</th><th>제목</th></tr>
<c:forEach var="r" items="${roomslist}" varStatus="status">
	<tr><td>${r.aid}</td><td>${r.name}</td><td>${r.email}</td><td>${r.date}</td><td onclick="location.href='rooms?action=getRooms&aid=${r.aid}'">${r.title}</td></tr>
</c:forEach>
</table>
<hr> 
<form method="post" action="/jwbook/rooms?action=moveView">
	<button type="submit">등록</button>
</form>
</body>
</html>