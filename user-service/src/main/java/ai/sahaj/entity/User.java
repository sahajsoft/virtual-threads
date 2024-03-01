package ai.sahaj.entity;

import jakarta.persistence.Entity;

import java.util.Date;
@Entity
public class User {

  private Long id;

  private String name;

  private Date createdAt;

  private Date updatedAt;
}
