package hello.lv3.exception;

import hello.lv3.error.ErrorMessage;

public class CalculatorException extends RuntimeException {
    private final ErrorMessage errorMessage;

    public CalculatorException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }
}
