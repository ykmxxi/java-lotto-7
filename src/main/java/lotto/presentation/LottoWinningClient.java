package lotto.presentation;

import lotto.presentation.view.InputView;

public class LottoWinningClient {

    private static final long AMOUNT_UNIT = 1000L;
    private final InputView inputView;

    public LottoWinningClient(final InputView inputView) {
        this.inputView = inputView;
    }

    public void run() {
        long purchaseAmount = readPurchaseAmount();
    }

    private long readPurchaseAmount() {
        try {
            long purchaseAmount = Long.parseLong(inputView.readPurchaseAmount());
            validatePurchaseAmountUnit(purchaseAmount);
            return purchaseAmount;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return readPurchaseAmount();
        }
    }

    private void validatePurchaseAmountUnit(final long purchaseAmount) {
        if ((purchaseAmount % AMOUNT_UNIT) != 0) {
            throw new IllegalArgumentException("[ERROR] 구입 금액은 1,000원 단위여야 합니다.");
        }
    }

}
