# 자바 컨벤션

- 작성일 : 22.10.18
- 참석자 : Jin

---

- 기본적인 컨벤션<br>
- 세부적인 내용은 추가 회의 필요<br>
- `파라메터로 사용하는 id, 그냥 단일 id` 어떻게 처리해야되나
  ```java
  private String id
  public void getUser(String id, String userId, String userID) // 어느게 맞는지
  ```
- 약어의 기준
  ```java
  private int userNumber
  private int userNum
  private boolean isCheck
  private boolean isChk
  ```
---

## 클래스, 인터페이스
- Upper Camel Case
- 명사로 시작
```java
public class ItemCategory   // (O)
public class Item_Category  // (X)
public class itemCategory   // (X)
  ```

## 메소드
- Lower Camel Case
- 동사/전치사로 시작
- 파라메터 개수 제한
```java
public void getCharacter()                                                      // (O)
public void toString()                                                          // (O)
public void charactorGet()                                                      // (X)
public void createCharacter(String id, String name, String skill, int level)    // (X)
```

## 변수
- Lower Camel Case
- 명사
- 약어 사용 금지 (ID 같은 대표적인 약어 제외)
- 상수형 변수는 Upper UnderScore 사용
```java
private int itemID                                      // (O)
private int item_id                                     // (X)
private int userString                                  // (O)
private int userStr                                     // (X)
private static final BASE_URL = "http://loatong.me"     // (O)
private static final base_url = "http://loatong.me"     // (X)
```
