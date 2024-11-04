package lotto.presentation.view;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import lotto.service.dto.DrawWinningResponse;
import lotto.service.dto.LottoPurchaseResponse;
import lotto.service.dto.WinningResult;

public class ResultView {

    private static final String NEW_LINE = System.lineSeparator();

    public void printLottoPurchaseResult(final LottoPurchaseResponse lottoPurchaseResponse) {
        StringBuilder builder = new StringBuilder();

        appendPurchaseCountMessage(builder, formatPurchaseCountMessage(lottoPurchaseResponse.lottoTicketsCount()));
        appendLottoNumberMessage(builder, lottoPurchaseResponse.lottoNumbers());

        System.out.println(builder);
    }

    public void printDrawWinningResult(final DrawWinningResponse drawWinningResponse) {
        StringBuilder builder = new StringBuilder();

        appendDrawWinningResultGuide(builder);
        appendWinningResultStatistics(builder, drawWinningResponse);
        appendRateOfReturn(builder, drawWinningResponse.rateOfReturn());

        System.out.println(builder);
    }

    public void printUserExceptionMessage(final String exceptionMessage) {
        System.out.println(String.join(" ", "[ERROR]", exceptionMessage));
    }

    private void appendPurchaseCountMessage(final StringBuilder builder, final String purchaseCountMessage) {
        builder.append(NEW_LINE)
                .append(purchaseCountMessage)
                .append(NEW_LINE);
    }

    private void appendLottoNumberMessage(final StringBuilder builder, final List<List<String>> lottoNumbers) {
        lottoNumbers.forEach((lottoNumber) -> {
            builder.append(formatLottoNumberMessage(lottoNumber))
                    .append(NEW_LINE);
        });
    }

    private void appendDrawWinningResultGuide(final StringBuilder builder) {
        builder.append(NEW_LINE)
                .append("당첨 통계")
                .append(NEW_LINE)
                .append("---")
                .append(NEW_LINE);
    }

    private void appendWinningResultStatistics(
            final StringBuilder builder,
            final DrawWinningResponse drawWinningResponse
    ) {
        Map<WinningResult, Long> winningResultStatistics = drawWinningResponse.winningResultStatistics();
        for (WinningResult winningResult : winningResultStatistics.keySet()) {
            String prefix = formatPrefix(winningResult);

            long winningCount = winningResultStatistics.get(winningResult);
            String suffix = formatSuffix(Long.toString(winningCount));
            builder.append(String.join(" - ", prefix, suffix))
                    .append(NEW_LINE);
        }
    }

    private void appendRateOfReturn(final StringBuilder builder, final BigDecimal rateOfReturn) {
        DecimalFormat rateOfReturnFormat = new DecimalFormat("#,##0.0");
        BigDecimal rateOfReturnPercentageValue = rateOfReturn.multiply(BigDecimal.valueOf(100L))
                .setScale(1, RoundingMode.HALF_UP);

        String rateOfReturnMessage = formatRateOfReturnMessage(rateOfReturnFormat.format(rateOfReturnPercentageValue));
        builder.append(rateOfReturnMessage);
    }

    private String formatPurchaseCountMessage(final int lottoTicketsCount) {
        return String.join("", lottoTicketsCount + "개를 구매했습니다.");
    }

    private String formatLottoNumberMessage(final List<String> lottoNumber) {
        return lottoNumber.stream()
                .collect(Collectors.joining(", ", "[", "]"));
    }

    private String formatPrefix(final WinningResult winningResult) {
        NumberFormat moneyFormat = NumberFormat.getNumberInstance(Locale.KOREA);
        if (winningResult.hasBonusNumber()) {
            return String.format("%d개 일치, 보너스 볼 일치 (%s원)", winningResult.matchingCount(),
                    moneyFormat.format(winningResult.prizeMoney()));
        }
        return String.format("%d개 일치 (%s원)", winningResult.matchingCount(),
                moneyFormat.format(winningResult.prizeMoney()));
    }

    private String formatSuffix(final String winningResultStatistics) {
        return String.join("", winningResultStatistics, "개");
    }

    private String formatRateOfReturnMessage(final String rateOfReturn) {
        String rateOfReturnPercentage = String.join("", rateOfReturn, "%");
        return String.format("총 수익률은 %s입니다.", rateOfReturnPercentage);
    }

}
