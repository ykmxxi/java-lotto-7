package lotto.service.dto;

import java.math.BigDecimal;
import java.util.Map;

public record DrawWinningResponse(Map<WinningResult, Long> winningResultStatistics, BigDecimal returnOnInvestment) {
}
