package lotto.presentation;

import lotto.presentation.view.InputView;
import lotto.presentation.view.ResultView;
import lotto.service.LottoWinningService;
import lotto.service.dto.LottoPurchaseResponse;
import lotto.service.dto.WinningDrawResponse;

public class LottoWinningClient {

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
            LottoPurchaseResponse lottoPurchaseResponse = buyLotto();

            resultView.printLottoPurchaseResult(lottoPurchaseResponse);

            WinningDrawResponse winningDrawResponse = drawWinning();

            resultView.printDrawWinningResult(winningDrawResponse);
        } finally {
            inputView.closeConsole();
        }
    }

    private LottoPurchaseResponse buyLotto() {
        try {
            String purchaseAmountInput = inputView.readPurchaseAmount()
                    .replaceAll("[,|원]", "");
            long purchaseAmount = Long.parseLong(purchaseAmountInput);
            return lottoWinningService.purchaseLotto(purchaseAmount);
        } catch (IllegalArgumentException e) {
            resultView.printUserExceptionMessage(e.getMessage());
            return buyLotto();
        }
    }

    private WinningDrawResponse drawWinning() {
        drawWinningLotto();
        return drawWinningResponse();
    }

    private WinningDrawResponse drawWinningResponse() {
        try {
            drawBonusNumber();
            return lottoWinningService.drawWinning();
        } catch (IllegalArgumentException e) {
            resultView.printUserExceptionMessage(e.getMessage());
            return drawWinningResponse();
        }
    }

    private void drawWinningLotto() {
        try {
            String winningNumbers = readWinningNumbers();
            lottoWinningService.drawWinningLotto(winningNumbers);
        } catch (IllegalArgumentException e) {
            resultView.printUserExceptionMessage(e.getMessage());
            drawWinningLotto();
        }
    }

    private void drawBonusNumber() {
        try {
            String bonusNumber = readBonusNumber();
            lottoWinningService.drawBonusNumber(bonusNumber);
        } catch (IllegalArgumentException e) {
            resultView.printUserExceptionMessage(e.getMessage());
            drawBonusNumber();
        }
    }

    private String readWinningNumbers() {
        try {
            return inputView.readWinningNumbers();
        } catch (IllegalArgumentException e) {
            resultView.printUserExceptionMessage(e.getMessage());
            return readWinningNumbers();
        }
    }

    private String readBonusNumber() {
        try {
            return inputView.readBonusNumber();
        } catch (IllegalArgumentException e) {
            resultView.printUserExceptionMessage(e.getMessage());
            return readBonusNumber();
        }
    }

}
