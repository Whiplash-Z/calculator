package hello.lv3;

import hello.lv3.error.ErrorMessage;
import hello.lv3.exception.CalculatorException;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

public class Calculator {
    private final Queue<Double> queue = new ArrayDeque<>();

    public Queue<Double> getResults() {
        return new ArrayDeque<>(queue); // 복사본을 반환해서 원본을 건들 수 없도록 캡슐화
    }

    public void isRemoveOldestData(Scanner scanner, String message) {
        System.out.print(message);
        String isRemove = scanner.next();
        if (isRemove.equalsIgnoreCase("y")) {
            if (!queue.isEmpty()) {
                queue.poll();
            } else {
                throw new CalculatorException(ErrorMessage.NOT_EXIST_DATA);
            }
        }
    }

    public double calculate(long firstInput, OperatorType operationSymbol, long secondInput) {
        double result = 0;

        switch (operationSymbol) {
            case PLUS -> result = firstInput + secondInput;
            case MINUS -> result = firstInput - secondInput;
            case MULTIPLY -> result = firstInput * secondInput;
            case DIVIDE -> {
                if (secondInput == 0) {
                    throw new CalculatorException(ErrorMessage.CAN_NOT_BE_ZERO);
                }
                result = (double) firstInput / secondInput;
            }
        }
        return result;
    }

    public long getPositiveNumber(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.next();

            exitCheck(input);

            try {
                long number = Long.parseLong(input);

                if (number < 0) {
                    throw new CalculatorException(ErrorMessage.CAN_ONLY_POSITIVE_INTEGER);
                }

                return number;
            } catch (RuntimeException e) {
                if (e instanceof NumberFormatException) {
                    System.out.println(ErrorMessage.CAN_ONLY_WRITE_NUMBER.getMessage());
                } else if (e instanceof CalculatorException exception) {
                    System.out.println(exception.getMessage());
                }
            }
        }
    }

    public OperatorType getOperatorInput(Scanner scanner, String message) {
        String operatorInput;
        char operatorType;
        while (true) {
            try {
                System.out.print(message);
                operatorInput = scanner.next();

                exitCheck(operatorInput);
                validInputLength(operatorInput);

                operatorType = operatorInput.charAt(0);

                return OperatorType.fromChar(operatorType);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
                continue;
            }
        }
    }

    public void isSave(Scanner scanner, double result, String message) {
        System.out.print(message);
        String isSave = scanner.next();
        if (isSave.equalsIgnoreCase("y")) {
            queue.offer(result);
        } else {
            System.out.println("값이 저장되지 않았습니다.");
        }
    }

    private static void validInputLength(String operatorInput) {
        if (operatorInput.length() != 1) {
            throw new CalculatorException(ErrorMessage.CAN_NOT_OVER_ONE_LENGTH);
        }
    }

    private void exitCheck(String input) {
        if (input.equals("exit")) {
            throw new CalculatorException(ErrorMessage.EXIT_REQUESTED);
        }
    }
}
