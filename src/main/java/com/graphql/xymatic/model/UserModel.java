package com.graphql.xymatic.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.graphql.xymatic.enums.RoleEnums;
import com.graphql.xymatic.enums.StatusEnums;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.*;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "xt_users")
public class UserModel implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  private Long id;

  @Column(name = "name")
  @EqualsAndHashCode.Include
  private final String name;

  @Column(name = "email")
  @EqualsAndHashCode.Include
  private final String email;

  @Column(name = "password")
  @EqualsAndHashCode.Include
  private final String password;

  @ElementCollection
  @CollectionTable(
    name = "xt_roles",
    joinColumns = @JoinColumn(name = "author")
  )
  @Column(name = "name")
  @Transient
  @Enumerated(EnumType.STRING)
  private Set<RoleEnums> roles;

  @JsonFormat(
    shape = JsonFormat.Shape.STRING,
    pattern = "yyyy-MM-dd HH:mm:ss.SSS"
  )
  @Column(name = "created_at", columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
  private LocalDateTime createdAt;

  @JsonFormat(
    shape = JsonFormat.Shape.STRING,
    pattern = "yyyy-MM-dd HH:mm:ss.SSS"
  )
  @Column(name = "updated_at", columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
  private LocalDateTime updatedAt;

  @Column(name = "status")
  @Transient
  @Enumerated(EnumType.STRING)
  private StatusEnums status;

  @Deprecated
  public UserModel() {
    this.name = null;
    this.password = null;
    this.email = null;
  }

  public UserModel(String email, String password, String name) {
    this.email = email;
    this.password = password;
    this.name = name;
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
    this.status = sEnums;
  }

  public StatusEnums getStatus() {
    return status;
  }

  public Set<RoleEnums> getRoles() {
    return roles;
  }

  /**
   * Setting Roles accordignly to the RoleEnums credentials
   * @param roles
   */
  public void setRoles(Set<RoleEnums> roles) {
    this.roles = roles;
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
