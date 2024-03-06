package ai.sahaj.subscription.dto;

import ai.sahaj.subscription.domain.UserPlanDO;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UserPlanResponse(Long userId, Integer planId, String planName, BigDecimal dataBalance,
                               LocalDate planStartedOn) {
  public static UserPlanResponse from(UserPlanDO userPlanDO) {
    return new UserPlanResponse(userPlanDO.userId(), userPlanDO.planId(), userPlanDO.planName(), userPlanDO.getDataBalance(),
      userPlanDO.planStartDate());
  }
}
