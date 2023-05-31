<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>방명록</title>

<style>
  .inline-form {
    display: inline;
  }
</style>

<script>
function showPopup(message) {
    alert(message);
}

function checkForm() {
    var name = document.getElementById("name").value;
    var email = document.getElementById("email").value;
    var title = document.getElementById("title").value;
    var password = document.getElementById("password").value;
    var content = document.getElementById("content").value;
    
    if (name.trim() === '' || email.trim() === '' || title.trim() === '' || password.trim() === '' || content.trim() === '') {
        showPopup("칸이 비어있습니다.");
        return false;
    }
    return true;
}

function clearFields() {
    document.getElementById("name").value = '';
    document.getElementById("email").value = '';
    document.getElementById("title").value = '';
    document.getElementById("password").value = '';
    document.getElementById("content").value = '';
}

</script>

</head>
<body>
	<h2>방명록 수정</h2>
    <div>
    	<form method="post" action="/jwbook/rooms?action=modifyRooms&aid=${rooms.aid}"  class="inline-form" onsubmit="return checkForm();">
	    	<table>
	    	<h2>${rooms.aid}</h2>
		    	<tr><td>작성자</td><td><input type="text" name="name" id="name" value="${rooms.name}"></td></tr>
		    	<tr><td>이메일</td><td><input type="text" name="email" id="email" value="${rooms.email}"></td></tr>
		    	<tr><td>제목</td><td><input type="text" name="title" id="title" value="${rooms.title}"></td></tr>
		    	<tr><td>비밀번호</td><td><input type="text" name="password" id="password" value="${rooms.password}"></td></tr>
	    	</table>
    		<textarea cols="50" rows="5" name="content" id="content">${rooms.content}</textarea>
    		<br>
    		<button type="submit" onclick="return checkForm();">수정</button>
    	</form>
    	<form class="inline-form">
    		<button type="button" onclick="clearFields();">삭제</button>
    	</form>
    	<form method="post" action="/jwbook/rooms?action=listRooms"  class="inline-form">
    		<button type="submit">목록</button>
    	</form>
    </div>
</body>
</html>