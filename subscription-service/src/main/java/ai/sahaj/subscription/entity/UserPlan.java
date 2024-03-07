package ai.sahaj.subscription.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "user_plan")
@Getter
@NoArgsConstructor
public class UserPlan {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer userPlanId;
  private Integer planId;
  private Integer userId;
  private BigDecimal dataConsumed;
  private LocalDate startDate;
  private LocalDate createdAt;
  private LocalDate updatedAt;

  public UserPlan(Integer planId, Integer userId, BigDecimal dataConsumed, LocalDate startDate) {
    this.planId = planId;
    this.userId = userId;
    this.dataConsumed = dataConsumed;
    this.startDate = startDate;
    this.createdAt = LocalDate.now();
    this.updatedAt = LocalDate.now();
  }
}
