package ai.sahaj.repository;

import ai.sahaj.entity.UserPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPlanRepository extends JpaRepository<UserPlan, Long> {
}
