package lotto.domain.winning;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class WinningTest {

    @DisplayName("당첨 번호와 보너스 번호 1개를 넣으면 당첨번호 추첨에 성공 한다.")
    @Test
    void 당첨번호와_보너스번호_추첨_성공() {
        List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6);
        int bonusNumber = 7;

        assertDoesNotThrow(() -> Winning.draw(winningNumbers, bonusNumber));
    }

    @DisplayName("당첨 번호로 로또 발행을 실패하면 예외가 발생한다.")
    @MethodSource("provideWrongWinningNumbers")
    @ParameterizedTest
    void 당첨번호_추첨_실패(List<Integer> winningNumbers) {
        int bonusNumber = 7;

        assertThatThrownBy(() -> Winning.draw(winningNumbers, bonusNumber))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("보너스 번호가 당첨 번호에 존재하면 예외가 발생한다.")
    @Test
    void 보너스_번호_추첨_실패() {
        List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6);
        int bonusNumber = 6;

        assertThatThrownBy(() -> Winning.draw(winningNumbers, bonusNumber))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    static Stream<Arguments> provideWrongWinningNumbers() {
        return Stream.of(
                Arguments.of(List.of(1, 2, 3, 4, 5, 6, 7)),
                Arguments.of(List.of(1, 2, 3, 4, 5, 5)),
                Arguments.of(List.of(1, 2, 3, 4, 5, 46))
        );
    }

}
