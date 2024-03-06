package ai.sahaj.subscription.service;

import ai.sahaj.subscription.domain.UserPlanDO;
import ai.sahaj.subscription.entity.Plan;
import ai.sahaj.subscription.entity.UserPlan;
import ai.sahaj.subscription.exception.PlanNotFoundException;
import ai.sahaj.subscription.repository.PlanRepository;
import ai.sahaj.subscription.repository.UserPlanRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserPlanService {

  private final PlanRepository planRepository;
  private final UserPlanRepository userPlanRepository;

  public UserPlanService(PlanRepository planRepository, UserPlanRepository userPlanRepository) {
    this.planRepository = planRepository;
    this.userPlanRepository = userPlanRepository;
  }

  public UserPlanDO getUserPlanDetails(Long userId) {
    UserPlan userPlan = userPlanRepository.findByUserId(userId)
      .orElseThrow(() -> new PlanNotFoundException("Plan for user " + userId + " not found"));

    Plan plan = planRepository.findById(userPlan.getPlanId())
      .orElseThrow(() -> new PlanNotFoundException(("Plan with id " + userPlan.getPlanId() + " not found")));

    return UserPlanDO.from(userPlan, plan);
  }


}
