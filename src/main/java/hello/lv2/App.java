package hello.lv2;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                long firstInput = calculator.getPositiveNumber(scanner, "첫 번째 숫자를 입력하세요: ");
                char operationSymbol = calculator.getOperationInput(scanner, "사칙연산 기호를 입력하세요: ");
                long secondInput = calculator.getPositiveNumber(scanner, "두 번째 숫자를 입력하세요: ");

                double result = calculator.calculate(firstInput, operationSymbol, secondInput);

                calculator.isSave(scanner, result, "값을 저장하시겠습니까? (y / n): ");
                calculator.isRemoveOldestData(scanner, "가장 오래된 값을 삭제하시겠습니까? (y / n): ");

                System.out.println("계산 결과: " + result);
                System.out.println("저장 내역: " + calculator.getResults());

            } catch (RuntimeException e) {
                if (e.getMessage().equals("EXIT_REQUESTED")) {
                    System.out.println("계산기를 종료합니다.");
                    break;
                } else if (e.getMessage().equals("CAN_NOT_BE_ZERO")) {
                    System.out.println("나눗셈 연산에서 분모는 0이 될 수 없습니다. 다시 시작해주세요.");
                    continue;
                } else {
                    System.out.println(e.getMessage());
                    continue;
                }
            }
        }

        scanner.close();
        System.out.println("리소스를 정리합니다.");
    }
}
