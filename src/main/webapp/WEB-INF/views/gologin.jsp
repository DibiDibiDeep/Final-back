<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Google Login Example</title>
</head>
<body>
<h1>Google Login Example</h1>
<button id="loginBtn">Google 로그인</button>
<div id="result"></div>

<script>
    document.getElementById('loginBtn').addEventListener('click', function() {
        // 서버에서 Google 로그인 URL을 가져옵니다.
        fetch('/api/v1/oauth2/google', {
            method: 'POST'
        })
            .then(response => response.text())
            .then(url => {
                // Google 로그인 페이지로 리다이렉트합니다.
                window.location.href = url;
            });
    });

    // URL에서 code 파라미터를 확인합니다.
    const urlParams = new URLSearchParams(window.location.search);
    const code = urlParams.get('code');

    if (code) {
        // code가 있다면 서버에 로그인 요청을 보냅니다.
        fetch(`/api/v1/oauth2/google?code=${code}`)
            .then(response => response.json())
            .then(data => {
                if (data.message === "Login successful") {
                    document.getElementById('result').innerHTML = `로그인 성공! 이메일: ${data.email}`;
                } else {
                    document.getElementById('result').innerHTML = '로그인 실패';
                }
            })
            .catch(error => {
                console.error('Error:', error);
                document.getElementById('result').innerHTML = '로그인 중 오류 발생';
            });
    }
</script>
</body>
</html>
