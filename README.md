## 구현 기능 목록

### 1단계

- [x] 예외 처리
    - [x] 시간 생성 시 시작 시간에 유효하지 않은 값이 입력되었을 때
    - [x] 예약 생성 시 예약자명, 날짜, 시간에 유효하지 않은 값이 입력 되었을 때
    - [x] 특정 시간에 대한 예약이 존재하는데, 그 시간을 삭제하려 할 때
    - [x] 사용자로부터 잘못된 요청이 오면 BAD_REQUEST 를 응답한다
- [x] 추가된 서비스 정책
    - [x] 중복 시간은 불가능하다
    - [x] 중복 예약은 불가능하다
    - [x] 지나간 날짜와 시간에 대한 예약은 생성 불가능하다

### 2단계

- [x] 테마 테이블 추가
- [x] 기존 코드에 테마 추가
- [x] 테마 관리 페이지 추가
- [ ] API 구현
    - [ ] 테마 조회 API
    - [ ] 테마 추가 API
    - [ ] 테마 삭제 API
