# 🧮 계산기 구현 과제

## 📌 구현 단계 및 기능

### ✔️ Lv.1 클래스 없이 기본 연산을 수행하는 계산기 만들기
- `Scanner`를 사용한 양의 정수 입력 (입력 검증 포함)
- 사칙연산 기호 (`+`, `-`, `*`, `/`) 입력 및 처리
- 잘못된 입력에 대한 오류 메시지 출력
- `exit` 입력 시 프로그램 종료
- 반복 입력 및 연산 처리 (`while` 문 기반)

💡 **주요 구현 포인트**
- 숫자 및 연산자 입력값 검증 후 연산 처리
- 예외 상황 처리: 0으로 나눌 수 없음, 지원하지 않는 연산자 등
- `if`, `switch` 조건문을 통한 연산 분기 처리

---

### ✔️ Lv.2 클래스를 적용한 계산기 만들기(Lv1 구현 후 클래스 적용, 캡슐화)
- 연산 로직 분리 및 캡슐화 (`Calculator` 클래스)
- 결과값 저장 기능 (`Queue` 컬렉션 사용)
- 컬렉션 캡슐화를 위한 복사 객체 반환
- 저장된 결과 삭제 기능 (`poll`)
- `App` 클래스는 `Calculator` 인스턴스만 사용 (내부 필드 직접 접근 차단 → Getter/Setter 사용)
- 예외 상황을 `RuntimeException`으로 처리

💡 **주요 구현 포인트**
- `App` 클래스는 `Calculator`에만 의존하도록 구조 설계
- 계산 결과는 내부 컬렉션에 저장
- 저장된 결과 중 가장 오래된 값부터 제거 가능 (FIFO 구조)

---

### ✔️ Lv.3 Enum, 제네릭, 람다, 스트림 적용한 계산기 만들기
- `ArithmeticCalculator<T extends Number>` 제네릭 클래스 구현
- 다양한 타입 연산 지원 (`Integer`, `Long`, `Float`, `Double`)
- 연산 타입 분리를 위한 `Enum OperatorType` 설계
- 사용자 정의 예외 처리 (`CalculatorException`, `ErrorMessage`)
- 저장된 결과 중 평균 / 최댓값 출력 기능
- 특정 기준 이상의 결과만 필터링 출력 (`람다 & Stream` 활용)
- 내부 필드 캡슐화 및 타입 안전성 고려

💡 **주요 구현 포인트**
- `calculate()` 메서드가 제네릭 기반으로 확장되어 다양한 수 타입 처리
- `람다 표현식`으로 조건 필터링 및 결과 출력 (`List.stream().filter().collect()` 사용)
- `Enum` 기반 연산자로 코드 가독성 및 유지보수성 향상
