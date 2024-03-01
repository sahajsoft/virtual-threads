package ai.sahaj.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class UserPlan {

  private Long planId;
  private Long userId;
  private Double dataConsumed;

  private Date startDate;

  private Date createdAt;

  private Date updatedAt;
}
