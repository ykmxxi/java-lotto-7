package lotto.domain.winning;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

public class WinningStatistics {

    private final Map<LottoRank, Long> statistics;

    private WinningStatistics(final Map<LottoRank, Long> statistics) {
        this.statistics = statistics;
    }

    public static WinningStatistics create() {
        Map<LottoRank, Long> emptyStatistics = new EnumMap<>(LottoRank.class);
        initStatistics(emptyStatistics);
        return new WinningStatistics(emptyStatistics);
    }

    private static void initStatistics(final Map<LottoRank, Long> emptyStatistics) {
        Arrays.stream(LottoRank.values())
                .forEach(lottoRank -> emptyStatistics.put(lottoRank, 0L));
    }

    public void save(final LottoRank lottoRank) {
        statistics.computeIfPresent(lottoRank, (key, value) -> value + 1L);
    }

    public long findWinningCountByLottoRank(final LottoRank lottoRank) {
        return statistics.get(lottoRank);
    }

}
