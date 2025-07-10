package hello.lv2;

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
                throw new RuntimeException("저장된 데이터가 없습니다.");
            }
        }
    }

    public double calculate(long firstInput, char operationSymbol, long secondInput) {
        double result;

        // 사칙연산 수행
        switch (operationSymbol) {
            case '+':
                result = firstInput + secondInput;
                break;
            case '-':
                result = firstInput - secondInput;
                break;
            case '*':
                result = firstInput * secondInput;
                break;
            case '/':
                if (secondInput == 0) {
                    throw new ArithmeticException("CAN_NOT_BE_ZERO");
                }
                result = (double) firstInput / secondInput;
                break;
            default:
                throw new RuntimeException("+ - * / 만 입력이 가능합니다. 다시 시작해주세요.");
        }
        return result;
    }

    public long getPositiveNumber(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.next();

            // exit 체크
            exitCheck(input);

            try {
                long number = Long.parseLong(input);

                // 음수 검증
                if (number < 0) {
                    System.out.println("양의 정수(0 포함)를 입력해주세요.");
                    continue;
                }

                return number;
            } catch (NumberFormatException e) {
                System.out.println("양의 정수를 입력해주세요.");
            }
        }
    }

    public char getOperationInput(Scanner scanner, String message) {
        String operationInput;
        char operation;
        while (true) {
            System.out.print(message);
            operationInput = scanner.next();

            // exit 체크
            exitCheck(operationInput);

            // 하나의 연산자만 입력되었는지 확인
            if (operationInput.length() != 1) {
                System.out.println("하나의 연산자만 입력해주세요");
                continue;
            }
            operation = operationInput.charAt(0);

            switch (operation) {
                case '+', '-', '*', '/':
                    break;
                default:
                    System.out.println("+ - * / 만 입력이 가능합니다. 다시 시작해주세요.");
                    continue;
            }

            break;
        }
        return operation;

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

    private static void exitCheck(String input) {
        if (input.equals("exit")) {
            throw new RuntimeException("EXIT_REQUESTED");
        }
    }
}
