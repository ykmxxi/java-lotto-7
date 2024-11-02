package lotto.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lotto.domain.lotto.Lotto;
import lotto.domain.lotto.LottoTickets;
import lotto.domain.winning.LottoRank;
import lotto.domain.winning.Winning;
import lotto.domain.winning.WinningStatistics;
import lotto.service.dto.DrawWinningResponse;
import lotto.service.dto.LottoPurchaseResponse;
import lotto.service.dto.WinningResult;

public class LottoWinningService {

    private final LottoTickets lottoTickets = new LottoTickets();

    public LottoPurchaseResponse purchaseLotto(final long purchaseQuantity) {
        for (long quantity = 1L; quantity <= purchaseQuantity; quantity++) {
            NumbersCreator randomNumbersCreator = new RandomNumbersCreator();
            List<Integer> randomNumbers = randomNumbersCreator.create();
            lottoTickets.save(Lotto.issue(randomNumbers));
        }

        List<Lotto> tickets = lottoTickets.findAll();
        return toLottoPurchaseResponse(tickets);
    }

    public DrawWinningResponse drawWinning(final String winningNumbersInput, final int bonusNumber) {
        NumbersCreator inputSplitNumbersCreator = new InputSplitNumbersCreator(winningNumbersInput);
        Winning winning = Winning.draw(inputSplitNumbersCreator.create(), bonusNumber);

        WinningStatistics winningStatistics = WinningStatistics.create();
        List<Lotto> tickets = lottoTickets.findAll();
        for (Lotto lotto : tickets) {
            LottoRank rank = winning.compare(lotto);
            winningStatistics.save(rank);
        }
        return toDrawWinningResponse(winningStatistics, tickets.size() * 1000L);
    }

    private LottoPurchaseResponse toLottoPurchaseResponse(final List<Lotto> tickets) {
        List<List<String>> lottoNumbers = new ArrayList<>();
        for (Lotto ticket : tickets) {
            lottoNumbers.add(ticket.numbers().stream()
                    .map(String::valueOf)
                    .toList()
            );
        }
        return new LottoPurchaseResponse(tickets.size(), lottoNumbers);
    }

    private DrawWinningResponse toDrawWinningResponse(
            final WinningStatistics winningStatistics,
            final long totalAmount
    ) {
        Map<WinningResult, Long> winningResultStatistics = new LinkedHashMap<>();
        for (LottoRank lottoRank : LottoRank.values()) {
            if (lottoRank == LottoRank.NONE) {
                continue;
            }
            long winningCount = winningStatistics.findWinningCountByLottoRank(lottoRank);
            WinningResult winningResult = new WinningResult(lottoRank.matchingCount(), lottoRank.winningMoney());
            winningResultStatistics.put(winningResult, winningCount);
        }
        BigDecimal rateOfReturn = winningStatistics.calculateRateOfReturn(totalAmount);
        return new DrawWinningResponse(winningResultStatistics, rateOfReturn.toString());
    }

}
