package lotto.service;

import static lotto.domain.lotto.LottoRule.LOTTO_NUMBERS_SIZE;
import static lotto.domain.lotto.LottoRule.LOTTO_NUMBER_END_INCLUSIVE;
import static lotto.domain.lotto.LottoRule.LOTTO_NUMBER_START_INCLUSIVE;

import java.util.List;

import camp.nextstep.edu.missionutils.Randoms;

public class RandomNumbersCreator implements NumbersCreator {

    @Override
    public List<Integer> create() {
        try {
            return Randoms.pickUniqueNumbersInRange(
                    LOTTO_NUMBER_START_INCLUSIVE.value(),
                    LOTTO_NUMBER_END_INCLUSIVE.value(),
                    LOTTO_NUMBERS_SIZE.value()
            );
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("[ERROR] 로또 번호 자동 생성에 문제가 생겨 프로그램을 종료합니다. 다시 실행해 주세요.");
        }
    }

}
