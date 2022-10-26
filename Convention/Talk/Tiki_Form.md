

# 2022.10.26

# 주제 : 티키 양식 관련 얘기, Spring Batch test code 및 meta table 약간 설명

# 참석자 : Jin, Hunter, Leo, Log

---

## 티키 양식 관련 얘기
- Tiki 노션에 Tiki의 정제성과 작성 가이드 추가됨. 작성 전 참고 필수!
- Tiki 양식 중 History에 관련하여 변경 상태 및 작성자 선택하는 방법으로 추가 및 변경
  - 변경 상태는 Sub 생성 상태를 제거하기로 함
    - Leo 제안으로 Sub 내에 Content 생성 부분이 존재해서 Sub 생성의 작성자 및 생성 시간의 의미가 중복되어 결정된 사안
  - Content 생성, 수정 두가지 상태 운용으로 결정
- 목차(Index)페이지 생성
  - 검색 기능이 불편한 노션 구조상 임시 해결책
  - 폴더 형식으로 생성되어 있는 페이지들을 목차 페이지에 구조화한 Ctrl+F 를 통한 페이지명 검색방식
  - 각 페이지 링크를 추가하여 접근성 강화
  - Log가 목차 양식 작성
---

## Spring Batch test code 및 meta table 약간 설명
- 노션에 정리된 Spring Batch 테스트 코드 작성 과정 소개
- Batch Meta Table AWS RDS에 테이블 생성