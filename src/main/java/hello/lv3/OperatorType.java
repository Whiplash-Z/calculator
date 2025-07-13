package hello.lv3;

import hello.lv3.error.ErrorMessage;
import hello.lv3.exception.CalculatorException;

public enum OperatorType {
    PLUS('+'),
    MINUS('-'),
    MULTIPLY('*'),
    DIVIDE('/');

    private final char operatorType;

    OperatorType(char operatorType) {
        this.operatorType = operatorType;
    }

    public char getOperatorType() {
        return operatorType;
    }

    public static OperatorType fromChar(char c) {
        for (OperatorType type : OperatorType.values()) {
            if (c == type.operatorType) {
                return type;
            }
        }
        throw new CalculatorException(ErrorMessage.NOT_EXIST_OPERATOR);
    }
}
