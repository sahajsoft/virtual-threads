package ai.sahaj.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class User {

  @Id
  private Integer id;

  private String name;

  private LocalDate createdAt;

  private LocalDate updatedAt;

  public User(final Integer userId, final String userName) {
    this.id = userId;
    this.name = userName;
    this.createdAt = LocalDate.now();
    this.updatedAt = LocalDate.now();
  }
}
