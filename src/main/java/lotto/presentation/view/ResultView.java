package lotto.presentation.view;

import java.util.stream.Collectors;

import lotto.service.dto.LottoPurchaseResponse;

public class ResultView {

    public void printLottoPurchaseResult(LottoPurchaseResponse lottoPurchaseResponse) {
        System.out.println();
        System.out.printf("%d개를 구매했습니다.", lottoPurchaseResponse.lottoTicketsCount());
        System.out.println();

        lottoPurchaseResponse.lottoNumbers()
                .forEach((lottoNumber) -> {
                    System.out.println(lottoNumber.stream()
                            .collect(Collectors.joining(", ", "[", "]"))
                    );
                });
    }

}
