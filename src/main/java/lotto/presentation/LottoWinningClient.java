package lotto.presentation;

import camp.nextstep.edu.missionutils.Console;
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
        try {
            long purchaseAmount = readPurchaseAmount();
            LottoPurchaseResponse lottoPurchaseResponse = lottoWinningService.purchaseLotto(purchaseAmount);

            resultView.printLottoPurchaseResult(lottoPurchaseResponse);

            DrawWinningResponse drawWinningResponse = drawWinning();

            resultView.printDrawWinningResult(drawWinningResponse);
        } finally {
            Console.close();
        }
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


    private DrawWinningResponse drawWinning() {
        drawWinningLotto();
        return drawWinningResponse();
    }

    private DrawWinningResponse drawWinningResponse() {
        try {
            drawBonusNumber();
            return lottoWinningService.drawWinning();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return drawWinningResponse();
        }
    }

    private void drawWinningLotto() {
        try {
            String winningNumbers = readWinningNumbers();
            lottoWinningService.drawWinningLotto(winningNumbers);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            drawWinningLotto();
        }
    }

    private void drawBonusNumber() {
        try {
            String bonusNumber = readBonusNumber();
            lottoWinningService.drawBonusNumber(bonusNumber);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            drawBonusNumber();
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

    private void validatePurchaseAmountUnit(final long purchaseAmount) {
        if ((purchaseAmount % AMOUNT_UNIT) != 0) {
            throw new IllegalArgumentException("[ERROR] 구입 금액은 1,000원 단위여야 합니다.");
        }
    }

}
