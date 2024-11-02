package lotto.service.dto;

import java.util.Map;

public record DrawWinningResponse(Map<WinningResult, Long> winningResultStatistics, String rateOfReturn) {
}
