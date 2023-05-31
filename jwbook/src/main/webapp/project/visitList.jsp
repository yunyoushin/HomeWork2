<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>방명록 목록</title>
<style>
    body {
        display: flex;
        flex-direction: column;
        align-items: center;
        margin: 0;
    }   
    .list {
        text-align: center;
        margin-top: 20px;
    }
	#formcs {
		margin: 50px;
        text-align: center;
        background-color : #38A3EB;
    	color : white;
        padding: 8px 24px;
        font-size: 15px;
        border : none;
        border-radius: 4px;
	}
    table {
        border-collapse: collapse;
        border-color: #CCCCCC;
    }
    td {
        padding: 5px 30px;
    }
    td button{
    	border:none;
    	background-color: #1ABC9C;
    }
    #toptable {
    	background-color: #EAEAEA;
    }
    #bottomtable {
    	background-color: #1ABC9C; 
    }
</style>
</head>
<body>
	<h2>방명록 목록</h2>
	<div class ="list">
		<table border="1">
		<tr id="toptable"><td>번호</td><td>작성자</td><td>이메일</td><td>작성일</td><td>제목</td></tr>
		<c:forEach items = "${visitlist}" var = "s">
			<tr id=bottomtable>
				<td>${s.aid}</td>
				<td>${s.name}</td>
				<td>${s.email}</td>
				<td>${s.date}</td>
				<td>
				<form method="post" action="/jwbook/visit.nhn?action=getVisit&aid=${s.aid}">
					<button type="submit">${s.title}</button>
				</form>
				</td>
			</tr>
		</c:forEach>
		</table>
	</div>
	<form method="post" action="project/addView.jsp">
		<button id = "formcs" type="submit" >등록</button>
	</form>
	<script>
        var table = document.querySelector("table");
        var rows = Array.from(table.getElementsByTagName("tr"));
        rows.shift(); 
        rows.sort(function (a, b) {
            var aidA = parseInt(a.getElementsByTagName("td")[0].textContent);
            var aidB = parseInt(b.getElementsByTagName("td")[0].textContent);
            return aidB - aidA; 
        });
        rows.forEach(function (row) {
            table.appendChild(row);
        });
    </script>
</body>
</html>