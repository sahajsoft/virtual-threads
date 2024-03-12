package ai.sahaj.subscription.service;

import ai.sahaj.subscription.domain.UserPlanDO;
import ai.sahaj.subscription.entity.Plan;
import ai.sahaj.subscription.entity.UserPlan;
import ai.sahaj.subscription.exception.PlanNotFoundException;
import ai.sahaj.subscription.repository.PlanRepository;
import ai.sahaj.subscription.repository.UserPlanRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserPlanServiceTest {
  private UserPlanRepository userPlanRepository = mock(UserPlanRepository.class);
  private PlanRepository planRepository = mock(PlanRepository.class);
  private UserPlanService userPlanService = new UserPlanService(planRepository, userPlanRepository);

  @Test
  void shouldGetUserPlanDetailsForGivenUser() throws InterruptedException {
    Integer userId = 1;
    UserPlan userPlan = new UserPlan(1, 1, BigDecimal.valueOf(12), LocalDate.now());
    when(userPlanRepository.findByUserId(userId))
      .thenReturn(Optional.of(userPlan));
    Plan plan = new Plan(1, "Test", BigDecimal.valueOf(100), 100);
    when(planRepository.findById(1))
      .thenReturn(Optional.of(plan));

    UserPlanDO userPlanDO = userPlanService.getUserPlanDetails(userId);

    UserPlanDO expectedUserPlanDO = new UserPlanDO(1, 1, userPlan.getDataConsumed(), userPlan.getStartDate(), plan.getData(), plan.getName());
    assertEquals(expectedUserPlanDO, userPlanDO);
  }

  @Test
  void shouldThrowExceptionWhenPlanForUserNotFound() {
    Integer userId = 1;
    when(userPlanRepository.findByUserId(userId))
      .thenReturn(Optional.empty());

    assertThrows(PlanNotFoundException.class, () -> userPlanService.getUserPlanDetails(userId));
  }

  @Test
  void shouldThrowExceptionWhenPlanDetailsNotFound() {
    Integer userId = 1;
    UserPlan userPlan = new UserPlan(1, 1, BigDecimal.valueOf(12), LocalDate.now());
    when(userPlanRepository.findByUserId(userId))
      .thenReturn(Optional.of(userPlan));
    Plan plan = new Plan(1, "Test", BigDecimal.valueOf(100), 100);
    when(planRepository.findById(1))
      .thenReturn(Optional.empty());

    assertThrows(PlanNotFoundException.class, () -> userPlanService.getUserPlanDetails(userId));
  }
}