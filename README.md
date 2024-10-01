## Redis start

#### WSL 설치:

WSL이 설치되어 있지 않다면, PowerShell을 관리자 모드로 열고 다음 명령을 실행하세요:
wsl --install

#### Ubuntu 설치:
Microsoft Store에서 "Ubuntu"를 검색하여 Ubuntu를 설치합니다.

#### Redis 설치:

WSL(Ubuntu) 터미널을 열고 아래 명령을 실행하여 Redis를 설치합니다.
sudo apt update
sudo apt install redis-server

#### Redis 서버 실행:

Redis 설치 후, Redis 서버를 실행합니다.
sudo service redis-server start
#### Redis 확인:

Redis가 제대로 작동하는지 확인하려면 아래 명령어를 입력하세요:
redis-cli ping

결과가 PONG 이면 설치 완료

### macOS에 Redis 설치
macOS에서는 Homebrew를 통해 간단하게 Redis를 설치할 수 있습니다.
Homebrew 설치 (Homebrew가 설치되어 있지 않은 경우):
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"

#### Redis 설치:
brew install redis

#### Redis 서버 실행:
brew services start redis

#### Redis 확인:
redis-cli ping

결과가 PONG이면 설치 완료
