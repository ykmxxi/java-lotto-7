package lotto.service;

import java.util.ArrayList;
import java.util.List;

import lotto.domain.lotto.Lotto;
import lotto.domain.lotto.LottoTickets;
import lotto.service.dto.LottoPurchaseResponse;

public class LottoWinningService {

    public LottoPurchaseResponse purchaseLotto(final long purchaseQuantity) {
        LottoTickets lottoTickets = new LottoTickets();
        for (long quantity = 1L; quantity <= purchaseQuantity; quantity++) {
            NumbersCreator randomNumbersCreator = new RandomNumbersCreator();
            List<Integer> randomNumbers = randomNumbersCreator.create();
            lottoTickets.save(Lotto.issue(randomNumbers));
        }

        List<Lotto> tickets = lottoTickets.findAll();
        return toLottoPurchaseResponse(tickets);
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

}
