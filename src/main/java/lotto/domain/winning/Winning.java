package lotto.domain.winning;

import java.util.List;

import lotto.domain.lotto.Lotto;

public class Winning {

    private final Lotto winningLotto;
    private final int bonusNumber;

    private Winning(final List<Integer> winningNumbers, final int bonusNumber) {
        validateBonusNumberDuplication(winningNumbers, bonusNumber);
        this.winningLotto = Lotto.issue(winningNumbers);
        this.bonusNumber = bonusNumber;
    }

    public static Winning draw(final List<Integer> winningNumbers, final int bonusNumber) {
        return new Winning(winningNumbers, bonusNumber);
    }

    private void validateBonusNumberDuplication(final List<Integer> winningNumbers, final int bonusNumber) {
        if (winningNumbers.contains(bonusNumber)) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호가 당첨 번호와 중복되면 안됩니다.");
        }
    }

}
