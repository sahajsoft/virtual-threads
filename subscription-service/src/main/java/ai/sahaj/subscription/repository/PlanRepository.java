package ai.sahaj.subscription.repository;

import ai.sahaj.subscription.entity.Plan;
import org.springframework.data.repository.CrudRepository;

public interface PlanRepository extends CrudRepository<Plan, Integer> {

}
