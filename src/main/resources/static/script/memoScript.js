$(function() {
    // CSRF 토큰 설정
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $.ajaxSetup({
        beforeSend: function(xhr) {
            if (token && header) {
                xhr.setRequestHeader(header, token);
            }
        }
    });

    // DOM 요소 선택
    const $newMemoBtn = $('#newMemoBtn');
    const $memoInput = $('#memoInput');
    const $content = $('#content');
    const $saveMemoBtn = $('#saveMemoBtn');
    const $memoList = $('#memoList');
    const $layerBG = $('#layerBG');
    const $editMemoContent = $('#editMemoContent');
    const $closeMark = $('#closeMark');
    const $contentCharCount = $('#contentCharCount');
    const $editCharCount = $('#editCharCount');
    const $searchInput = $('#searchInput');
    const $searchBtn = $('#searchBtn');

    // 글자 수 제한 및 카운트 업데이트 함수
    function limitCharactersAndUpdateCount(element, maxLength, countElement) {
        const currentLength = $(element).val().length;
        if (currentLength > maxLength) {
            $(element).val($(element).val().substring(0, maxLength));
        }
        $(countElement).text(Math.min(currentLength, maxLength));
    }

    // 새 메모 버튼 클릭 이벤트 처리
    $newMemoBtn.on('click', function() {
        $memoInput.toggleClass('hidden');
        $content.focus();
    });

    // 메모 저장 버튼 클릭 이벤트 처리
    $saveMemoBtn.on('click', function(e) {
        e.preventDefault();
        const content = $content.val().trim();
        if (content) {
            $.ajax({
                url: '/memoWrite',
                method: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({ content: content }),
                success: function(response) {
                    if (response.status === "success") {
                        $content.val('');
                        $contentCharCount.text('0');
                        $memoInput.addClass('hidden');
                        location.reload();
                    } else {
                        alert("메모 저장에 실패했습니다.");
                    }
                },
                error: function(xhr, status, error) {
                    console.error("오류 상세:", xhr.responseText);
                    alert("서버 오류가 발생했습니다.");
                }
            });
        }
    });

    // 메모 내용 클릭 이벤트 처리 (전체 내용 보기 및 수정 준비)
    $(document).on('click', '.memo-content', function() {
        const $memoItem = $(this).closest('.memo-item');
        const memoId = $memoItem.data('id');
        const fullContent = $(this).data('full-content');
        console.log("클릭된 메모 - ID:", memoId, "내용:", fullContent);

        $editMemoContent.val(fullContent);
        $editCharCount.text(fullContent.length);
        $layerBG.data('memoId', memoId);
        $layerBG.show();
    });

    // 메모 수정 버튼 클릭 이벤트
    $('#updateMemoBtn').on('click', function() {
        const memoId = $layerBG.data('memoId');
        const updatedContent = $editMemoContent.val().trim();
        console.log("수정 - 메모 ID:", memoId, "내용:", updatedContent);
        if (!memoId) {
            alert("메모 ID를 찾을 수 없습니다.");
            return;
        }
        if (updatedContent) {
            if (updatedContent.length > 255) {
                alert("메모는 255자를 초과할 수 없습니다.");
                return;
            }
            $.ajax({
                url: '/memoUpdate',
                method: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({ id: memoId, content: updatedContent }),
                success: function(response) {
                    if (response.status === "success") {
                        alert("메모가 수정되었습니다.");
                        $layerBG.hide();
                        location.reload();
                    } else {
                        alert("메모 수정에 실패했습니다.");
                    }
                },
                error: function(xhr, status, error) {
                    console.error("오류 상세:", xhr.responseText);
                    alert("서버 오류가 발생했습니다.");
                }
            });
        }
    });

    // 메모 삭제 버튼 클릭 이벤트
    $('#deleteMemoBtn').on('click', function() {
        const memoId = $layerBG.data('memoId');
        console.log("삭제 - 메모 ID:", memoId);
        if (!memoId) {
            alert("삭제할 메모의 ID를 찾을 수 없습니다.");
            return;
        }
        if (confirm("정말로 이 메모를 삭제하시겠습니까?")) {
            $.ajax({
                url: '/memoDelete',
                method: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({ id: memoId }),
                success: function(response) {
                    if (response.status === "success") {
                        alert("메모가 삭제되었습니다.");
                        $layerBG.hide();
                        location.reload();
                    } else {
                        alert("메모 삭제에 실패했습니다.");
                    }
                },
                error: function(xhr, status, error) {
                    console.error("오류 상세:", xhr.responseText);
                    alert("서버 오류가 발생했습니다.");
                }
            });
        }
    });

    // 레이어 닫기
    $closeMark.click(function() {
        $layerBG.hide();
    });

    // 글자 수 제한 및 카운트 업데이트 (새 메모)
    $content.on('input', function() {
        limitCharactersAndUpdateCount(this, 255, $contentCharCount);
    });

    // 글자 수 제한 및 카운트 업데이트 (메모 수정)
    $editMemoContent.on('input', function() {
        limitCharactersAndUpdateCount(this, 255, $editCharCount);
    });

    // 검색 기능 추가
    $searchBtn.on('click', function() {
        const searchTerm = $searchInput.val().trim().toLowerCase();
        if (searchTerm) {
            $.ajax({
                url: '/memoSearch',
                method: 'GET',
                data: { searchTerm: searchTerm },
                success: function(response) {
                    updateMemoList(response);
                },
                error: function(xhr, status, error) {
                    console.error("검색 오류:", xhr.responseText);
                    alert("검색 중 오류가 발생했습니다.");
                }
            });
        } else {
            location.reload(); // 검색어가 없으면 전체 목록을 다시 로드
        }
    });

    // 메모 목록 업데이트 함수
    function updateMemoList(memoGroups) {
        $memoList.empty(); // 기존 메모 목록 제거

        memoGroups.forEach(function(group) {
            const dateGroup = $('<div>').addClass('date-group').attr('data-date', group.date);
            const dateHeader = $('<h2>').addClass('date-header').text(group.date.replace(/-/g, '.'));
            dateGroup.append(dateHeader);

            const memoContents = group.contents.split('||');
            const memoIds = group.ids.split('||');
            const memoTimes = group.times.split('||');

            memoContents.forEach(function(content, index) {
                const memoItem = $('<div>').addClass('memo-item').attr('data-id', memoIds[index]);
                const memoTime = $('<div>').addClass('memo-time').text(formatTime(memoTimes[index]));
                const memoContentWrapper = $('<div>').addClass('memo-content-wrapper');
                const memoContent = $('<div>').addClass('memo-content')
                    .attr('data-full-content', content)
                    .text(content.length > 80 ? content.substring(0, 80) + '...' : content);

                memoContentWrapper.append(memoContent);
                memoItem.append(memoTime, memoContentWrapper);
                dateGroup.append(memoItem);
            });

            $memoList.append(dateGroup);
        });
    }

    // 시간 포맷 함수
    function formatTime(time) {
        const [hours, minutes] = time.split(':');
        const hour = parseInt(hours);
        if (hour < 12) {
            return `오전 ${hour}시 ${minutes}분`;
        } else {
            return `오후 ${hour === 12 ? 12 : hour - 12}시 ${minutes}분`;
        }
    }
});