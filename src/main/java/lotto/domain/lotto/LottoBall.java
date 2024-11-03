package lotto.domain.lotto;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

public class LottoBall {

    private static final Map<Integer, LottoBall> LOTTO_BALLS = new HashMap<>();

    private final int number;

    static {
        IntStream.rangeClosed(
                LottoRule.LOTTO_NUMBER_START_INCLUSIVE.value(),
                LottoRule.LOTTO_NUMBER_END_INCLUSIVE.value()
        ).forEach(number -> LOTTO_BALLS.put(number, new LottoBall(number)));
    }

    private LottoBall(final int number) {
        this.number = number;
    }

    public static LottoBall draw(final int number) {
        validateLottoNumberRange(number);
        return LOTTO_BALLS.get(number);
    }

    private static void validateLottoNumberRange(final int number) {
        if (LottoRule.isRangeRuleViolation(number)) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 1~45 사이의 숫자여야 합니다.");
        }
    }

    public int number() {
        return number;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LottoBall lottoBall)) {
            return false;
        }
        return number == lottoBall.number;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(number);
    }

    @Override
    public String toString() {
        return "LottoBall{" +
                "number=" + number +
                '}';
    }

}
