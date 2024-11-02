package lotto.domain.lotto;

public enum LottoRule {

    LOTTO_NUMBER_START_INCLUSIVE(1),
    LOTTO_NUMBER_END_INCLUSIVE(45),
    LOTTO_NUMBERS_SIZE(6);

    private final int value;

    LottoRule(final int value) {
        this.value = value;
    }

    public static boolean isSizeRuleViolation(final int size) {
        return LOTTO_NUMBERS_SIZE.value != size;
    }

    public static boolean isRangeRuleViolation(final int number) {
        return number < LOTTO_NUMBER_START_INCLUSIVE.value ||
                number > LOTTO_NUMBER_END_INCLUSIVE.value;
    }

}
