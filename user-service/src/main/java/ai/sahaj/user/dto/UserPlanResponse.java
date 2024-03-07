package ai.sahaj.user.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UserPlanResponse(Integer userId, Integer planId, String planName, BigDecimal dataBalance,
                               LocalDate planStartedOn) {
}
