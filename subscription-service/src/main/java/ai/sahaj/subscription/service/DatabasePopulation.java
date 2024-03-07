package ai.sahaj.subscription.service;

import ai.sahaj.subscription.entity.Plan;
import ai.sahaj.subscription.entity.UserPlan;
import ai.sahaj.subscription.repository.PlanRepository;
import ai.sahaj.subscription.repository.UserPlanRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Component
public class DatabasePopulation {
  private final PlanRepository planRepository;
  private final UserPlanRepository userPlanRepository;

  public DatabasePopulation(PlanRepository planRepository, UserPlanRepository userPlanRepository) {
    this.planRepository = planRepository;
    this.userPlanRepository = userPlanRepository;
  }

  @PostConstruct
  public void populateUserPlan() {
    Random random = new Random();
    Plan hundredGbPlan = planRepository.save(new Plan("100GB Plan", BigDecimal.valueOf(100), 100));
    Plan fiftyGbPlan = planRepository.save(new Plan("50GB Plan", BigDecimal.valueOf(50), 100));
    List<UserPlan> userPlans = Stream
      .iterate(1, userId -> userId <= 400, userId -> userId + 1)
      .map(userId -> {
        if (userId % 2 == 0) {
          return new UserPlan(hundredGbPlan.getId(), userId, BigDecimal.valueOf(random.nextInt(100)), LocalDate.now());
        }
        return new UserPlan(fiftyGbPlan.getId(), userId, BigDecimal.valueOf(random.nextInt(50)), LocalDate.now());
      })
      .toList();
    userPlanRepository.saveAll(userPlans);
  }
}
