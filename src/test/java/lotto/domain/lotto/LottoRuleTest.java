package lotto.domain.lotto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class LottoRuleTest {

    @DisplayName("로또 규칙에서 번호들의 크기가 6이 아니면 규칙을 위반한다.")
    @ValueSource(ints = {5, 7})
    @ParameterizedTest
    void 크기가_6이_아니면_로또_규칙_위반(int size) {
        assertThat(LottoRule.isSizeRuleViolation(size)).isTrue();
    }

    @DisplayName("로또 규칙에서 번호들의 크기가 6이면 규칙을 준수한다.")
    @Test
    void 크기가_6이면_로또_규칙_준수() {
        assertThat(LottoRule.isSizeRuleViolation(6)).isFalse();
    }

    @DisplayName("로또 규칙에서 1 미만 45 초과의 숫자는 규칙을 위반한다.")
    @ValueSource(ints = {0, 46})
    @ParameterizedTest
    void 숫자가_범위를_벗어나면_규칙_위반(int number) {
        assertThat(LottoRule.isRangeRuleViolation(number)).isTrue();
    }

    @DisplayName("로또 규칙에서 1 이상 45 이하의 숫자는 규칙을 준수한다.")
    @ValueSource(ints = {1, 45})
    @ParameterizedTest
    void 숫자가_범위를_만족하면_규칙_준수(int number) {
        assertThat(LottoRule.isRangeRuleViolation(number)).isFalse();
    }

}
