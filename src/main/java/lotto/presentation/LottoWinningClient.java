package lotto.presentation;

import camp.nextstep.edu.missionutils.Console;
import lotto.presentation.view.InputView;
import lotto.presentation.view.ResultView;
import lotto.service.LottoWinningService;
import lotto.service.dto.DrawWinningResponse;
import lotto.service.dto.LottoPurchaseResponse;

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

            DrawWinningResponse drawWinningResponse = drawWinning();

            resultView.printDrawWinningResult(drawWinningResponse);
        } finally {
            Console.close();
        }
    }

    private LottoPurchaseResponse buyLotto() {
        try {
            long purchaseAmountInput = Long.parseLong(inputView.readPurchaseAmount());
            return lottoWinningService.purchaseLotto(purchaseAmountInput);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return buyLotto();
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

}
