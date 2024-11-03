package lotto.domain.lotto;

import java.util.HashSet;
import java.util.List;

public class Lotto {

    private final List<LottoBall> numbers;

    private Lotto(List<LottoBall> numbers) {
        validate(numbers);
        this.numbers = numbers;
    }

    public static Lotto issue(final List<Integer> numbers) {
        return new Lotto(numbers.stream().map(LottoBall::draw).toList());
    }

    public int calculateMatchingCount(final Lotto lotto) {
        int matchingCount = 0;
        for (LottoBall lottoBall : numbers) {
            if (lotto.numbers.contains(lottoBall)) {
                matchingCount++;
            }
        }
        return matchingCount;
    }

    public boolean has(final LottoBall number) {
        return numbers.contains(number);
    }

    private void validate(List<LottoBall> numbers) {
        validateNumbersSize(numbers);
        validateDuplication(numbers);
    }

    private void validateNumbersSize(final List<LottoBall> numbers) {
        if (LottoRule.isSizeRuleViolation(numbers.size())) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 6개여야 합니다.");
        }
    }

    private void validateDuplication(final List<LottoBall> numbers) {
        if (LottoRule.isSizeRuleViolation(new HashSet<>(numbers).size())) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 중복되지 않아야 합니다.");
        }
    }

    public List<Integer> numbers() {
        return numbers.stream()
                .map(LottoBall::number)
                .sorted()
                .toList();
    }

    @Override
    public String toString() {
        return "Lotto" + numbers;
    }

}
