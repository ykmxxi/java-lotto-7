package lotto.presentation.view;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import lotto.service.dto.DrawWinningResponse;
import lotto.service.dto.LottoPurchaseResponse;
import lotto.service.dto.WinningResult;

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
        System.out.println();
    }

    public void printDrawWinningResult(final DrawWinningResponse drawWinningResponse) {
        System.out.println();
        System.out.println("당첨 통계");
        System.out.println("---");
        Map<WinningResult, Long> winningResultStatistics = drawWinningResponse.winningResultStatistics();
        for (WinningResult winningResult : winningResultStatistics.keySet()) {
            NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.KOREA);
            String prefix;
            if (winningResult.prizeMoney() == 30_000_000L) {
                prefix = String.format("%d개 일치, 보너스 볼 일치 (%s원)", winningResult.matchingCount(),
                        numberFormat.format(winningResult.prizeMoney()));
            } else {
                prefix = String.format("%d개 일치 (%s원)", winningResult.matchingCount(),
                        numberFormat.format(winningResult.prizeMoney()));
            }
            String suffix = String.join("", Long.toString(winningResultStatistics.get(winningResult)), "개");
            System.out.println(String.join(" - ", prefix, suffix));
        }
        String rateOfReturn = String.join("", drawWinningResponse.rateOfReturn(), "%");
        System.out.printf("총 수익률은 %s입니다.", rateOfReturn);

    }

}
