package hello.lv1;

import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            double result = 0.0;

            while (true) {
                // 첫 번째 숫자 입력
                long firstInput = getPositiveNumber(scanner, "첫 번째 숫자를 입력하세요: ");

                // 연산자 입력
                char operationSymbol = getOperationInput(scanner, "사칙연산 기호를 입력하세요: ");

                // 두 번째 숫자 입력
                long secondInput = getPositiveNumber(scanner, "두 번째 숫자를 입력하세요: ");


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
                            System.out.println("나눗셈 연산에서 분모는 0이 될 수 없습니다. 다시 시작해주세요.");
                            continue;
                        }
                        result = (double) firstInput / secondInput;
                        break;
                    default:
                        System.out.println("+ - * / 만 입력이 가능합니다. 다시 시작해주세요.");
                        continue;
                }

                // 계산 결과 출력
                System.out.println("결과: " + result);
                System.out.println();
            }

        } catch (RuntimeException e) {
            // exit 요청 예외와 다른 RuntimeException 구분 처리
            if (e.getMessage().equals("EXIT_REQUESTED")) {
                System.out.println("계산기를 종료합니다.");
            }
        } finally {
            // 프로그램 종료 전 리소스 정리
            scanner.close();
            System.out.println("리소스를 정리합니다.");
        }
    }

    private static char getOperationInput(Scanner scanner, String message) {
        String operationInput;
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

            if (Character.isLetter(operationInput.charAt(0))) {
                System.out.println("연산자만 입력이 가능합니다.");
                continue;
            }
            break;
        }
        return operationInput.charAt(0);

    }


    /**
     * 양의 정수를 입력받는 메서드
     *
     * @param scanner
     * @param message
     * @return
     */
    private static long getPositiveNumber(Scanner scanner, String message) {
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

    /**
     * exit이 입력되면 계산기를 종료시키기 위한 체크 메서드
     *
     * @param input
     */
    private static void exitCheck(String input) {
        if (input.equals("exit")) {
            throw new RuntimeException("EXIT_REQUESTED");
        }
    }
}
