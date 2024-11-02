package lotto;

import lotto.presentation.LottoWinningClient;
import lotto.presentation.view.InputView;
import lotto.presentation.view.ResultView;
import lotto.service.LottoWinningService;

public class Application {

    public static void main(String[] args) {
        LottoWinningClient lottoWinningClient = new LottoWinningClient(
                new InputView(),
                new ResultView(),
                new LottoWinningService()
        );
        
        lottoWinningClient.run();
    }

}
