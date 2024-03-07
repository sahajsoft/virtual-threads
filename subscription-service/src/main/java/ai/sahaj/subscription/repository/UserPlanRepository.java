package ai.sahaj.subscription.repository;

import ai.sahaj.subscription.entity.UserPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserPlanRepository extends JpaRepository<UserPlan, Integer> {

  Optional<UserPlan> findByUserId(Integer userId);
}
