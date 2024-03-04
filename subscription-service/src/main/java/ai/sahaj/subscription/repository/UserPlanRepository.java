package ai.sahaj.subscription.repository;

import ai.sahaj.subscription.entity.UserPlan;
import org.springframework.data.repository.CrudRepository;

public interface UserPlanRepository extends CrudRepository<UserPlan, Integer> {
}
