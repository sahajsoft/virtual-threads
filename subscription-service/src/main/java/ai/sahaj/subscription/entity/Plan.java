package ai.sahaj.subscription.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "plan")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Plan {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  private String name;
  private BigDecimal data;
  private Integer durationInDays;

  public Plan(String name, BigDecimal data, Integer durationInDays) {
    this.name = name;
    this.data = data;
    this.durationInDays = durationInDays;
  }
}
