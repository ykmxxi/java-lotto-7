package lotto.presentation;

import lotto.presentation.view.InputView;
import lotto.presentation.view.ResultView;
import lotto.service.LottoWinningService;
import lotto.service.dto.DrawWinningResponse;
import lotto.service.dto.LottoPurchaseResponse;

public class LottoWinningClient {

    private static final long AMOUNT_UNIT = 1000L;
    private final InputView inputView;
    private final ResultView resultView;
    private final LottoWinningService lottoWinningService;

    public LottoWinningClient(
            final InputView inputView,
            final ResultView resultView,
            final LottoWinningService lottoWinningService
    ) {
        this.inputView = inputView;
        this.resultView = resultView;
        this.lottoWinningService = lottoWinningService;
    }

    public void run() {
        long purchaseAmount = readPurchaseAmount();
        LottoPurchaseResponse lottoPurchaseResponse = lottoWinningService.purchaseLotto(purchaseAmount);

        resultView.printLottoPurchaseResult(lottoPurchaseResponse);

        DrawWinningResponse drawWinningResponse = drawWinning();
    }

    private long readPurchaseAmount() {
        try {
            long purchaseAmount = Long.parseLong(inputView.readPurchaseAmount());
            validatePurchaseAmountUnit(purchaseAmount);
            return purchaseAmount / AMOUNT_UNIT;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return readPurchaseAmount();
        }
    }

    private String readWinningNumbers() {
        try {
            return inputView.readWinningNumbers();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return readWinningNumbers();
        }
    }

    private String readBonusNumber() {
        try {
            return inputView.readBonusNumber();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return readBonusNumber();
        }
    }

    private DrawWinningResponse drawWinning() {
        try {
            String winningNumbers = readWinningNumbers();
            String bonusNumber = readBonusNumber();
            return lottoWinningService.drawWinning(
                    winningNumbers,
                    Integer.parseInt(bonusNumber)
            );
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return drawWinning();
        }
    }

    private void validatePurchaseAmountUnit(final long purchaseAmount) {
        if ((purchaseAmount % AMOUNT_UNIT) != 0) {
            throw new IllegalArgumentException("[ERROR] 구입 금액은 1,000원 단위여야 합니다.");
        }
    }

}
