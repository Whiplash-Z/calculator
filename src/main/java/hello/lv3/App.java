package hello.lv3;

import hello.lv3.error.ErrorMessage;
import hello.lv3.exception.CalculatorException;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                long firstInput = calculator.getPositiveNumber(scanner, "첫 번째 숫자를 입력하세요: ");
                OperatorType operationSymbol = calculator.getOperatorInput(scanner, "사칙연산 기호를 입력하세요: ");
                long secondInput = calculator.getPositiveNumber(scanner, "두 번째 숫자를 입력하세요: ");

                double result = calculator.calculate(firstInput, operationSymbol, secondInput);

                calculator.isSave(scanner, result, "값을 저장하시겠습니까? (y / n): ");
                calculator.isRemoveOldestData(scanner, "가장 오래된 값을 삭제하시겠습니까? (y / n): ");

                System.out.println("계산 결과: " + result);
                System.out.println("저장 내역: " + calculator.getResults());

            } catch (RuntimeException e) {
                System.out.println(e.getMessage());

                if (e instanceof CalculatorException exception &&
                        exception.getErrorMessage() == ErrorMessage.EXIT_REQUESTED) {
                    break;
                }
            }
        }

        scanner.close();
        System.out.println("리소스를 정리합니다.");
    }

}

