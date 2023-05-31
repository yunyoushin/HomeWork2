<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>방명록 입력</title>
<script>
	function cancel(){
		const value = ["name","email","title","password","content"]
		for(let i=0; i<value.length; i++){
			var input = document.getElementById(value[i]);
			input.value = null;
		}
	}
	function validateForm() {
		const value = ["name", "email", "title", "password", "content"];
		  for (let i = 0; i < value.length; i++) {
		    var input = document.getElementById(value[i]);
		    if (input.value == "") {
		      alert("입력란을 채우세요");
		      return false;
		    }
		  }
	}
</script>
<style>
    body {
        display: flex;
        flex-direction: column;
        align-items: center;
        margin: 0;
    }
	form {
        display: inline;
        margin-top: 20px;
        text-align: center;
    }
    form button {
    	background-color : #38A3EB;
    	color : white;
        margin: 20px;
        padding: 10px 24px;
        font-size: 10px;
        border : none;
        border-radius: 4px;
    }
	span { 
		color : #19AEBB;
	}
    .input {       
        margin-top: 20px;
    }

    .input table {
        width: 100%; 
        border-collapse: collapse;
    }

    .input th,
    .input td {
        padding: 3px 20px 3px 20px;
        border: 1px solid #ccc;
        border-width: 2px;
        width: 50%; 
    }
    .input input{
        width: 100%;
        padding: 7px;
        border: 1px solid #ccc;
        border-radius: 4px;
        box-sizing: border-box;
    }

    textarea {
    	border: none;
        margin: 4px;
        resize: none;
    }
    textarea:focus{
    	outline: none;
    }
    .textdiv {
    	margin-top: 10px;
    	background-color : #ccc;
    }
</style>
</head>
<body>
	<h2>방명록 입력</h2>
	<div>
		<form method="post" action="/jwbook/visit.nhn?action=addVisit" onsubmit="return validateForm()">
		 	<div class="input">
		        <table>
		            <tr>
		                <th><span>작성자</span></th>
		                <td><input id="name" type="text" name="name"></td>
		            </tr>
		            <tr>
		                <th><span>이메일</span></th>
		                <td><input id="email" type="email" name="email"></td>
		            </tr>
		            <tr>
		                <th><span>제목</span></th>
		                <td><input id="title" type="text" name="title"></td>
		            </tr>
		            <tr>
		                <th><span>비밀번호</span></th>
		                <td><input id="password" type="password" name="password"></td>
		            </tr>
		        </table>
    		</div>
    		<div class = textdiv>
    			<textarea id="content" cols="43" rows="14" name="content"></textarea>
    		</div>
			<button type="submit">입력</button>
		</form>
		<form>
			<button type="button" onClick="cancel()">취소</button>
		</form>
		<form method="post" action="/jwbook/visit.nhn?action=listVisit">
			<button type="submit">목록</button>
		</form>
	</div>
</body>
</html>