package lotto.domain.winning;

import lotto.domain.lotto.Lotto;
import lotto.domain.lotto.LottoRule;

public class Winning {

    private final Lotto winningLotto;
    private final int bonusNumber;

    private Winning(final Lotto winningLotto, final int bonusNumber) {
        validateBonusNumber(bonusNumber);
        validateBonusNumberDuplication(winningLotto, bonusNumber);
        this.winningLotto = winningLotto;
        this.bonusNumber = bonusNumber;
    }

    public static Winning draw(final Lotto winningLotto, final int bonusNumber) {
        return new Winning(winningLotto, bonusNumber);
    }

    public LottoRank compare(final Lotto lotto) {
        int matchingCount = winningLotto.calculateMatchingCount(lotto);
        boolean hasBonusNumber = lotto.has(bonusNumber);

        return LottoRank.find(matchingCount, hasBonusNumber);
    }

    private void validateBonusNumber(final int bonusNumber) {
        if (LottoRule.isRangeRuleViolation(bonusNumber)) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 1부터 45 사이의 숫자여야 합니다.");
        }
    }

    private void validateBonusNumberDuplication(final Lotto winningLotto, final int bonusNumber) {
        if (winningLotto.has(bonusNumber)) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호가 당첨 번호와 중복되면 안됩니다.");
        }
    }

}
