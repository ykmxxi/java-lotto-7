package lotto;

import lotto.presentation.LottoWinningClient;
import lotto.presentation.view.InputView;

public class Application {

    public static void main(String[] args) {
        LottoWinningClient lottoWinningClient = new LottoWinningClient(new InputView());

        lottoWinningClient.run();
    }

}
