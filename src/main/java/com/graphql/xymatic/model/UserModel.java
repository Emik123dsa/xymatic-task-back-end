package com.graphql.xymatic.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.graphql.xymatic.enums.StatusEnums;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "xt_users")
public class UserModel implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name")
  private final String name;

  @Column(name = "email")
  private final String email;

  @Column(name = "password")
  private final String password;

  @JsonFormat(
    shape = JsonFormat.Shape.STRING,
    pattern = "yyyy-MM-dd HH:mm:ss.SSS"
  )
  //@Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created_at", columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
  private LocalDateTime createdAt;

  @JsonFormat(
    shape = JsonFormat.Shape.STRING,
    pattern = "yyyy-MM-dd HH:mm:ss.SSS"
  )
  //@Temporal(TemporalType.TIMESTAMP)
  @Column(name = "updated_at", columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
  private LocalDateTime updatedAt;

  @Column(name = "status")
  private String status;

  @Deprecated
  public UserModel() {
    this.name = null;
    this.password = null;
    this.email = null;
  }

  public UserModel(String name, String password, String email) {
    this.name = name;
    this.password = password;
    this.email = email;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime setUpdatedAt() {
    return updatedAt;
  }

  public void setStatus(StatusEnums sEnums) {
    String aStatus = sEnums.getStatus();
    this.status = aStatus;
  }

  public String getStatus() {
    return status;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;

    if (object == null || getClass() != object.getClass()) return false;

    UserModel userModel = (UserModel) object;

    return id.equals(userModel.id);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }
}
