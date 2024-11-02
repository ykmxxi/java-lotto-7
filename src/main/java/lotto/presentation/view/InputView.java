package lotto.presentation.view;

import java.util.NoSuchElementException;
import java.util.regex.Pattern;

import camp.nextstep.edu.missionutils.Console;

public class InputView {

    private static final Pattern POSITIVE_NUMBER = Pattern.compile("\\d+");
    private static final Pattern WINNING_NUMBERS = Pattern.compile("(\\d+,){5}\\d+");

    public String readPurchaseAmount() {
        System.out.println("구입금액을 입력해 주세요.");

        String purchaseAmountInput = readInputLine();
        validatePositiveNumber(purchaseAmountInput);
        return purchaseAmountInput;
    }

    public String readWinningNumbers() {
        System.out.println("당첨 번호를 입력해 주세요.");

        String winningNumbersInput = readInputLine();
        validateWinningNumbersPattern(winningNumbersInput);
        return winningNumbersInput;
    }

    private String readInputLine() {
        try {
            String inputLine = Console.readLine();
            validateNull(inputLine);
            validateBlank(inputLine);
            return inputLine;
        } catch (NoSuchElementException | IllegalStateException e) {
            throw new IllegalStateException("[ERROR] 콘솔에 문제가 생겨 프로그램을 종료합니다. 다시 실행해 주세요.");
        }
    }

    private void validateNull(final String input) {
        if (input == null) {
            throw new IllegalStateException("[ERROR] 콘솔에 문제가 생겨 프로그램을 종료합니다. 다시 실행해 주세요.");
        }
    }

    private void validateBlank(final String input) {
        if (input.isBlank()) {
            throw new IllegalArgumentException("[ERROR] 값을 입력해 주세요.");
        }
    }

    private void validatePositiveNumber(final String input) {
        if (!POSITIVE_NUMBER.matcher(input)
                .matches()
        ) {
            throw new IllegalArgumentException("[ERROR] 양수를 입력해 주세요.");
        }
    }

    private void validateWinningNumbersPattern(final String input) {
        if (!WINNING_NUMBERS.matcher(input)
                .matches()
        ) {
            throw new IllegalArgumentException("[ERROR] 당첨 번호는 6개의 숫자를 쉼표(,)로 구분해 입력해 주세요.");
        }
    }

}
