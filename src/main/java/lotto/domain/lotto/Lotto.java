package lotto.domain.lotto;

import java.util.HashSet;
import java.util.List;

public class Lotto {

    private final List<LottoBall> lottoBalls;

    private Lotto(List<LottoBall> lottoBalls) {
        validate(lottoBalls);
        this.lottoBalls = lottoBalls;
    }

    public static Lotto issue(final List<Integer> numbers) {
        return new Lotto(numbers.stream()
                .map(LottoBall::draw)
                .toList()
        );
    }

    public int calculateMatchingCount(final Lotto lotto) {
        int matchingCount = 0;
        for (LottoBall lottoBall : lottoBalls) {
            if (lotto.lottoBalls.contains(lottoBall)) {
                matchingCount++;
            }
        }
        return matchingCount;
    }

    public boolean has(final LottoBall lottoBall) {
        return lottoBalls.contains(lottoBall);
    }

    private void validate(List<LottoBall> lottoBalls) {
        validateLottoBallsSize(lottoBalls);
        validateDuplication(lottoBalls);
    }

    private void validateLottoBallsSize(final List<LottoBall> lottoBalls) {
        if (LottoRule.isSizeRuleViolation(lottoBalls.size())) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 6개여야 합니다.");
        }
    }

    private void validateDuplication(final List<LottoBall> lottoBalls) {
        if (LottoRule.isSizeRuleViolation(new HashSet<>(lottoBalls).size())) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 중복되지 않아야 합니다.");
        }
    }

    public List<Integer> numbers() {
        return lottoBalls.stream()
                .map(LottoBall::number)
                .sorted()
                .toList();
    }

    @Override
    public String toString() {
        return "Lotto" + lottoBalls;
    }

}
