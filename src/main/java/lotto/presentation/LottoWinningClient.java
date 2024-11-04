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
            LottoPurchaseResponse lottoPurchaseResponse = buyAutoLotto();
            resultView.printLottoPurchaseResult(lottoPurchaseResponse);

            saveWinningLotto();
            saveBonusNumber();

            WinningDrawResponse winningDrawResponse = lottoWinningService.startDrawWinning();
            resultView.printDrawWinningResult(winningDrawResponse);
        } finally {
            inputView.closeConsole();
        }
    }

    private LottoPurchaseResponse buyAutoLotto() {
        try {
            String purchaseAmountInput = inputView.readPurchaseAmount()
                    .replaceAll("[,|원]", "");
            long purchaseAmount = Long.parseLong(purchaseAmountInput);
            return lottoWinningService.buyAutoLotto(purchaseAmount);
        } catch (IllegalArgumentException e) {
            resultView.printUserExceptionMessage(e.getMessage());
            return buyAutoLotto();
        }
    }

    private void saveWinningLotto() {
        try {
            String winningNumbers = inputView.readWinningNumbers();
            lottoWinningService.saveWinningLotto(winningNumbers);
        } catch (IllegalArgumentException e) {
            resultView.printUserExceptionMessage(e.getMessage());
            saveWinningLotto();
        }
    }

    private void saveBonusNumber() {
        try {
            int bonusNumber = Integer.parseInt(inputView.readBonusNumber());
            lottoWinningService.saveBonusNumber(bonusNumber);
        } catch (IllegalArgumentException e) {
            resultView.printUserExceptionMessage(e.getMessage());
            saveBonusNumber();
        }
    }

}
