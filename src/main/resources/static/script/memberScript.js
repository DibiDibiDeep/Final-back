$(function () {

    $(".delIcon").click(function () {
        let chk = confirm('해당 회원을 강제탈퇴 시키겠습니까?');
        if(chk) {
            let USER_CODE = $(this).children("input[type=hidden]").val();
            location.href = '/memberDelete?USER_CODE=' + USER_CODE;
        } else {
            alert('사용자가 취소하였습니다.')
        }
    })
});

// $(function () {
//     // 기존 기능: 회원 탈퇴 버튼 클릭 시
//     $(".delIcon").click(function () {
//         let chk = confirm('해당 회원을 강제탈퇴 시키겠습니까?');
//         if(chk) {
//             let USER_CODE = $(this).children("input[type=hidden]").val();
//             location.href = '/memberDelete?USER_CODE=' + USER_CODE;
//         } else {
//             alert('사용자가 취소하였습니다.')
//         }
//     });
//
//     // Google 로그인 버튼 클릭 시
//     $("#googleLoginBtn").click(function () {
//         const url = 'https://accounts.google.com/o/oauth2/v2/auth?' +
//             'client_id=' + encodeURIComponent('656959082482-gk9k618jti7gv10htl6rhvs8p6lmfcb0.apps.googleusercontent.com') +
//             '&redirect_uri=' + encodeURIComponent('http://localhost:8080/login/oauth2/code/google') +
//             '&response_type=code' +
//             '&scope=email profile';
//
//         // Google 로그인 페이지로 리디렉션
//         window.location.href = url;
//     });
// });
