package ai.sahaj.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Date;
@Entity
public class User {

  @Id
  private Integer id;

  private String name;

  private Date createdAt;

  private Date updatedAt;
}
