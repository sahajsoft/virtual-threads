package ai.sahaj.subscription.domain;

import ai.sahaj.subscription.entity.Plan;
import ai.sahaj.subscription.entity.UserPlan;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UserPlanDO(Integer userId, Integer planId, BigDecimal dataConsumed, LocalDate planStartDate,
                         BigDecimal dataAlloted, String planName) {

  public static UserPlanDO from(UserPlan userPlan, Plan plan) {
    return new UserPlanDO(userPlan.getUserId(), plan.getId(), userPlan.getDataConsumed(), userPlan.getStartDate(),
      plan.getData(), plan.getName());
  }

  public BigDecimal getDataBalance() {
    return dataAlloted.subtract(dataConsumed);
  }
}
