package ai.sahaj.subscription.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "user_plan")
public class UserPlan {

  @Id
  private Integer userPlanId;
  private Integer planId;
  private Long userId;
  private Double dataConsumed;

  private Date startDate;

  private Date createdAt;

  private Date updatedAt;
}
