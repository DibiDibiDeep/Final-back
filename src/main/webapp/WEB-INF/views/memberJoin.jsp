<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>회원가입</title>
    <link rel="stylesheet" href="/style/memberStyle.css">
</head>
<body>
    <div id="memberJoinwrap">
        <h1>회원가입</h1>
        <hr>
      <form action="/memberJoin" method="post">
        <div class="memberJoin">
            <span>이메일:</span>
            <input type="text" name="USER_EMAIL" required>
            <span>@</span>
            <select name="USER_EMAIL">
                  <option>naver.com</option>
                  <option>gmail.com</option>
                  <option>daum.net</option>
            </select>
        </div>
        <div class="memberJoin">
            <span>비밀번호:</span>
            <input type="password" id="USER_PW" name="USER_PW" required>
        </div>
        <div class="memberJoin">
            <span>닉네임:</span>
            <input type="text" id="USER_NICK" name="USER_NICK" required>
            <button type="button">중복확인</button>
        </div>
        <div class="memberJoin">
            <span>연락처:</span>
            <input type="text" id="USER_PHONE" name="USER_PHONE" required>
        </div>
        <button type="submit">가입</button>
      </form>

    </div>
    <!-- div#memberJoinwrap -->
</body>
</html>
