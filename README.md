# health connect 연동 테스트

## 준비사항
### 안드로이드 스튜디오 설치
- https://developer.android.com/codelabs/basic-android-kotlin-compose-install-android-studio?hl=ko0
- 테스트용 안드로이드 에뮬레이터 설치 or 안드로이드 기기 연결

### ngrok
- 안드로이드와 API 연동 테스트 시 localhost로 테스트 불가능, https를 사용해야 함
- ngrok를 사용해서 임의이 https 주소를 얻을 수 있음
- 참고: https://ngrok.com/download

## 테스트 방법
### 1. API 실행
- intellij에서 API 프로젝트 open
- 프로젝트 실행 (디버그 모드로 실행 추천)

### 2. ngrok 실행
- ngrok http 8080
  - 위 명령어 입력
- session status 항목 중 Forwarding 복사 -> https로 시작하는 url

### 3. 안드로이드 앱 실행
- 안드로이드 스튜디오에서 health 프로젝트 open
- java/com/example/health/api/RetrofitInstance.kt 파일 12라인 BASE_URL 변수에 ngrok에서 복사한 url 입력
- 앱 실행

![image](https://github.com/hs413/health-connect-test/assets/160138990/009c178d-407b-4255-adc8-72369f7d5c47)

- insert 버튼을 누르면 임의의 데이터 입력
- read 버튼을 누르면 저장한 데이터 출력 및 api 서버로 데이터 전송
  - 데이터 출력

    ![image](https://github.com/hs413/health-connect-test/assets/160138990/225ba372-df0c-4a36-a764-b14833e1919d)

  - 데이터 전송(디버그 모드로 확인)
   
    ![image](https://github.com/hs413/health-connect-test/assets/160138990/6e0d7b42-b634-4114-b361-8fa8e7119b7f)



