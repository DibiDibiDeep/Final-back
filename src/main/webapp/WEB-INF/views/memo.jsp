<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>메모 애플리케이션</title>
  <meta name="_csrf" content="${_csrf.token}" />
  <meta name="_csrf_header" content="${_csrf.headerName}" />
  <link rel="stylesheet" href="/style/memoStyle.css">
</head>
<body>
<div class="container">
  <header>
    <h1>Memo</h1>
    <button id="newMemoBtn" class="icon-button">➕</button>
  </header>

  <!-- 검색 기능 추가 -->
  <div id="searchBox">
    <input type="text" id="searchInput" placeholder="메모 검색...">
    <button id="searchBtn">검색</button>
  </div>

  <div id="memoInput" class="hidden">
    <div class="textarea-wrapper">
      <textarea id="content" placeholder="메모를 입력하세요..." maxlength="255"></textarea>
      <div class="char-count"><span id="contentCharCount">0</span>/255</div>
    </div>
    <button id="saveMemoBtn">저장</button>
  </div>

  <div id="memoList">
    <c:forEach var="group" items="${memoGroups}">
      <div class="date-group" data-date="${group.date}">
        <h2 class="date-header">${fn:replace(group.date, '-', '.')}</h2>
        <c:forEach var="memo" items="${fn:split(group.contents, '||')}" varStatus="status">
          <div class="memo-item" data-id="${fn:split(group.ids, '||')[status.index]}">
            <div class="memo-time">
              <c:set var="time" value="${fn:split(group.times, '||')[status.index]}" />
              <c:set var="hour" value="${fn:substring(time, 0, 2)}" />
              <c:set var="minute" value="${fn:substring(time, 3, 5)}" />
              <c:choose>
                <c:when test="${hour < 12}">오전 ${hour}시 ${minute}분</c:when>
                <c:otherwise>
                  오후
                  <c:choose>
                    <c:when test="${hour == 12}">
                      12
                    </c:when>
                    <c:otherwise>
                      ${hour - 12}
                    </c:otherwise>
                  </c:choose>
                  시 ${minute}분
                </c:otherwise>
              </c:choose>
            </div>
            <div class="memo-content-wrapper">
              <div class="memo-content" data-full-content="${memo}">
                  ${fn:substring(memo, 0, 80)}${fn:length(memo) > 80 ? '...' : ''}
              </div>
            </div>
          </div>
        </c:forEach>
      </div>
    </c:forEach>
  </div>
</div>

<div id="layerBG">
  <span id="closeMark">&times;</span>
  <div class="textarea-wrapper">
    <textarea id="editMemoContent" maxlength="255"></textarea>
    <div class="char-count"><span id="editCharCount">0</span>/255</div>
  </div>
  <div id="memoActions">
    <button id="updateMemoBtn">수정</button>
    <button id="deleteMemoBtn">삭제</button>
  </div>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="/script/memoScript.js"></script>
</body>
</html>