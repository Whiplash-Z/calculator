package hello.lv3;

import hello.lv3.error.ErrorMessage;
import hello.lv3.exception.CalculatorException;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

public class ArithmeticCalculator<T extends Number> {
    private final Queue<T> queue = new ArrayDeque<>();
    private final Class<T> type;

    public ArithmeticCalculator(Class<T> type) {
        this.type = type;
    }

    public Queue<T> getResults() {
        return new ArrayDeque<>(queue); // 복사본을 반환해서 원본을 건들 수 없도록 캡슐화
    }

    public void printResultsGreaterThan(Scanner scanner) {
        System.out.print("기준값을 입력하세요: ");
        T referenceValue = getPositiveNumber(scanner, "");
        System.out.println("===== 기준값보다 큰 값===== ");
        queue.stream()
                .filter(result -> result.doubleValue() > referenceValue.doubleValue())
                .forEach(System.out::println);
    }

    public void printAverage() {
        double average = queue.stream()
                .mapToDouble(Number::doubleValue)
                .average()
                .orElse(0.0);
        System.out.println("평균값: " + average);
    }

    public void printMaxValue() {
        queue.stream()
                .mapToDouble(Number::doubleValue)
                .max()
                .ifPresentOrElse(
                        max -> System.out.println("최대값: " + max),
                        () -> System.out.println("저장된 값이 없습니다.")
                );
    }

    public void askToRemoveOldestData(Scanner scanner, String message) {
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

    @SuppressWarnings("unchecked")
    public T calculate(T firstInput, OperatorType operationSymbol, T secondInput) {
        Number result = 0;
        double first = firstInput.doubleValue();
        double second = secondInput.doubleValue();

        switch (operationSymbol) {
            case PLUS -> result = first + second;
            case MINUS -> result = first - second;
            case MULTIPLY -> result = first * second;
            case DIVIDE -> {
                if (second == 0) {
                    throw new CalculatorException(ErrorMessage.CAN_NOT_BE_ZERO);
                }
                result = first / second;
            }
        }
        return getResult(result);

    }


    /**
     * type 필드로 런타임에 타입이 보장된다.
     * 같은 타입으로 캐스팅이 되므로 안전한 캐스팅이다.
     */
    @SuppressWarnings("unchecked")
    private T getResult(Number result) {
        if (type == Integer.class) {
            return (T) Integer.valueOf(result.intValue());
        } else if (type == Long.class) {
            return (T) Long.valueOf(result.longValue());
        } else if (type == Double.class) {
            return (T) Double.valueOf(result.doubleValue());
        } else if (type == Float.class) {
            return (T) Float.valueOf(result.floatValue());
        }
        throw new CalculatorException(ErrorMessage.NOT_SUPPORT_TYPE);
    }

    public T getPositiveNumber(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.next();

            exitCheck(input);

            try {
                Number number = getNumber(input);
                return castToType(number);
            } catch (RuntimeException e) {
                if (e instanceof NumberFormatException) {
                    System.out.println(ErrorMessage.CAN_ONLY_WRITE_NUMBER.getMessage());
                } else if (e instanceof CalculatorException exception) {
                    System.out.println(exception.getMessage());
                } else {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    /**
     * getNumber()에서 type에 맞는 Number 객체를 생성했으므로
     * T 타입으로의 캐스팅이 안전함이 보장됨
     */
    @SuppressWarnings("unchecked")
    private T castToType(Number number) {
        return (T) number;
    }


    private Number getNumber(String input) {
        Number number;

        if (type == Integer.class) {
            number = Integer.parseInt(input);
        } else if (type == Long.class) {
            number = Long.parseLong(input);
        } else if (type == Float.class) {
            number = Float.parseFloat(input);
        } else if (type == Double.class) {
            number = Double.parseDouble(input);
        } else {
            throw new CalculatorException(ErrorMessage.NOT_SUPPORT_TYPE);
        }

        if (number.doubleValue() < 0) {
            throw new CalculatorException(ErrorMessage.CAN_ONLY_POSITIVE_NUMBER);
        }
        return number;
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
                if (e instanceof CalculatorException exception &&
                        exception.getErrorMessage() == ErrorMessage.EXIT_REQUESTED) {
                    throw new CalculatorException(ErrorMessage.EXIT_REQUESTED);
                }
                System.out.println(e.getMessage());
            }
        }
    }

    public void askToSave(Scanner scanner, T result, String message) {
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
