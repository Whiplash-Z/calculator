package hello.lv3.error;

public enum ErrorMessage {
    EXIT_REQUESTED("계산기를 종료합니다."),
    CAN_NOT_BE_ZERO("나눗셈 연산에서 분모는 0이 될 수 없습니다. 다시 시작해주세요."),
    CAN_NOT_OVER_ONE_LENGTH("하나의 연산자만 입력해주세요"),
    CAN_ONLY_POSITIVE_NUMBER("양수만(0 포함) 입력해주세요."),
    CAN_ONLY_WRITE_NUMBER("숫자만 입력 가능합니다."),
    NOT_EXIST_DATA("저장된 데이터가 없습니다."),
    NOT_EXIST_OPERATOR("+ - * / 만 입력이 가능합니다. 다시 시작해주세요."),
    NOT_SUPPORT_TYPE("지원하지 않는 타입");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
