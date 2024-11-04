package lotto.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lotto.domain.lotto.Lotto;
import lotto.domain.lotto.LottoTickets;
import lotto.domain.lotto.PurchaseAmount;
import lotto.domain.winning.LottoRank;
import lotto.domain.winning.Winning;
import lotto.domain.winning.WinningStatistics;
import lotto.service.dto.DrawWinningResponse;
import lotto.service.dto.LottoPurchaseResponse;
import lotto.service.dto.WinningResult;

public class LottoWinningService {

    private final LottoTickets lottoTickets = new LottoTickets();
    private Lotto winningLotto;
    private String bonusNumber;

    public LottoPurchaseResponse purchaseLotto(final long purchaseAmountInput) {
        PurchaseAmount purchaseAmount = new PurchaseAmount(purchaseAmountInput);
        for (long count = 1L; count <= purchaseAmount.getCountOfTicket(); count++) {
            NumbersCreator randomNumbersCreator = new RandomNumbersCreator();
            List<Integer> randomNumbers = randomNumbersCreator.create();
            lottoTickets.save(Lotto.issue(randomNumbers));
        }

        List<Lotto> tickets = lottoTickets.findAll();
        return toLottoPurchaseResponse(tickets);
    }

    public void drawWinningLotto(final String winningNumbers) {
        winningLotto = createWinningLotto(winningNumbers);
    }

    public void drawBonusNumber(final String bonusNumber) {
        this.bonusNumber = bonusNumber;
    }

    public DrawWinningResponse drawWinning() {
        return drawWinningWithBonusNumber();
    }

    private Lotto createWinningLotto(final String winningNumbersInput) {
        NumbersCreator inputSplitNumbersCreator = new InputSplitNumbersCreator(winningNumbersInput);
        List<Integer> winningNumbers = inputSplitNumbersCreator.create();
        return Lotto.issue(winningNumbers);
    }

    private DrawWinningResponse drawWinningWithBonusNumber() {
        Winning winning = Winning.draw(winningLotto, Integer.parseInt(bonusNumber));
        return calculateWinningStatistics(winning);
    }

    private DrawWinningResponse calculateWinningStatistics(final Winning winning) {
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
        Map<WinningResult, Long> winningResultStatistics = createWinningResultStatistics(winningStatistics);
        BigDecimal rateOfReturn = winningStatistics.calculateRateOfReturn(totalAmount);
        return new DrawWinningResponse(winningResultStatistics, rateOfReturn.toString());
    }

    private Map<WinningResult, Long> createWinningResultStatistics(final WinningStatistics winningStatistics) {
        LinkedHashMap<WinningResult, Long> winningResultStatistics = new LinkedHashMap<>();
        for (LottoRank lottoRank : LottoRank.values()) {
            if (lottoRank == LottoRank.NONE) {
                continue;
            }
            long winningCount = winningStatistics.findWinningCountByLottoRank(lottoRank);
            WinningResult winningResult = createWinningResult(lottoRank);
            winningResultStatistics.put(winningResult, winningCount);
        }
        return winningResultStatistics;
    }

    private WinningResult createWinningResult(final LottoRank lottoRank) {
        return new WinningResult(
                lottoRank.matchingCount(),
                lottoRank.winningMoney(),
                lottoRank.hasBonusNumber()
        );
    }

}
