<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>회원목록</title>
    <link rel="stylesheet" href="/style/memberStyle.css">
</head>

<body>
<div id="memberListwrap">
    <h1>회원목록</h1>
    <hr>
    <table id="Listtable">
        <tbody id="prnArea">
        <tr id="colArea">
            <th>회원코드</th>
            <th>플랫폼유형</th>
            <th>이메일</th>
            <th>비밀번호</th>
            <th>닉네임</th>
            <th>연락처</th>
            <th>가입일</th>
            <th>최근접속일</th>
            <th>권한</th>
            <th>탈퇴일자</th>
            <th>리프레시토큰</th>
            <th>강제탈퇴</th>
        </tr>
        <c:forEach var="memberList" items="${mtdMemberList}">
            <tr class="rowArea dFlex">
                <td>${memberList.USER_CODE}</td>
                <td>${memberList.USER_PLATFORM}</td>
                <td>${memberList.USER_EMAIL}</td>
                <td>${memberList.USER_PW}</td>
                <td>${memberList.USER_NICK}</td>
                <td>${memberList.USER_PHONE}</td>
                <td>${memberList.USER_SIGNUP}</td>
                <td>${memberList.USER_LATEST}</td>
                <td>${memberList.USER_AUTH}</td>
                <td>${memberList.USER_LEAVE}</td>
                <td>${memberList.USER_TOKEN}</td>
                <td class="delIcon">
                    &times;
                    <input type="hidden" value="${memberList.USER_CODE}">
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="/script/memberScript.js"></script>
</body>
</html>