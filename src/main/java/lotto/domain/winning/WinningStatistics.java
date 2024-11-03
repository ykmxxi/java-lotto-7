package lotto.domain.winning;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

public class WinningStatistics {

    private static final BigDecimal PERCENT_MULTIPLIER = BigDecimal.valueOf(100L);

    private final Map<LottoRank, Long> statistics;

    private WinningStatistics(final Map<LottoRank, Long> statistics) {
        this.statistics = statistics;
    }

    public static WinningStatistics create() {
        Map<LottoRank, Long> emptyStatistics = new EnumMap<>(LottoRank.class);
        initStatistics(emptyStatistics);
        return new WinningStatistics(emptyStatistics);
    }

    public void save(final LottoRank lottoRank) {
        statistics.computeIfPresent(lottoRank, (key, value) -> value + 1L);
    }

    public long findWinningCountByLottoRank(final LottoRank lottoRank) {
        return statistics.get(lottoRank);
    }

    public BigDecimal calculateRateOfReturn(long totalAmount) {
        BigDecimal winningMoneyTotal = calculateWinningMoneyTotal();

        return winningMoneyTotal.multiply(PERCENT_MULTIPLIER)
                .divide(BigDecimal.valueOf(totalAmount), 1, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateWinningMoneyTotal() {
        BigDecimal winningMoneyTotal = BigDecimal.ZERO;
        for (LottoRank lottoRank : statistics.keySet()) {
            long winningCount = statistics.get(lottoRank);
            winningMoneyTotal = winningMoneyTotal.add(
                    BigDecimal.valueOf(lottoRank.calculateWinningMoneyTotal(winningCount))
            );
        }
        return winningMoneyTotal;
    }

    private static void initStatistics(final Map<LottoRank, Long> emptyStatistics) {
        Arrays.stream(LottoRank.values())
                .forEach(lottoRank -> emptyStatistics.put(lottoRank, 0L));
    }

}
