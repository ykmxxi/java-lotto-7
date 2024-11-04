package lotto.domain.winning;

import lotto.domain.lotto.Lotto;
import lotto.domain.lotto.LottoBall;

public class Winning {

    private final Lotto winningLotto;
    private final LottoBall bonusNumber;

    private Winning(final Lotto winningLotto, final LottoBall bonusNumber) {
        validateBonusNumberDuplication(winningLotto, bonusNumber);
        this.winningLotto = winningLotto;
        this.bonusNumber = bonusNumber;
    }

    public static Winning draw(final Lotto winningLotto, final int bonusNumber) {
        return new Winning(winningLotto, LottoBall.draw(bonusNumber));
    }

    public LottoRank compare(final Lotto lotto) {
        int matchingCount = winningLotto.calculateMatchingCount(lotto);
        boolean hasBonusNumber = lotto.has(bonusNumber);

        return LottoRank.find(matchingCount, hasBonusNumber);
    }

    private void validateBonusNumberDuplication(final Lotto winningLotto, final LottoBall bonusNumber) {
        if (winningLotto.has(bonusNumber)) {
            throw new IllegalArgumentException("보너스 번호가 당첨 번호와 중복되면 안됩니다.");
        }
    }

}
