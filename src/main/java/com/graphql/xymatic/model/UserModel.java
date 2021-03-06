package com.graphql.xymatic.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.graphql.xymatic.enums.RoleEnums;
import com.graphql.xymatic.enums.StatusEnums;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.*;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.Transient;

@Entity
@Table(name = "xt_users")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
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

  @Enumerated(EnumType.STRING)
  @ElementCollection
  @CollectionTable(
    name = "xt_roles",
    joinColumns = @JoinColumn(name = "author")
  )
  @Column(name = "name", nullable = false)
  private Set<RoleEnums> roles;

  @JsonFormat(
    shape = JsonFormat.Shape.STRING,
    pattern = "yyyy-MM-dd HH:mm:ss.SSS"
  )
  @CreationTimestamp
  @Column(
    name = "created_at",
    columnDefinition = "TIMESTAMP WITHOUT TIME ZONE",
    updatable = false
  )
  private LocalDateTime createdAt;

  @JsonFormat(
    shape = JsonFormat.Shape.STRING,
    pattern = "yyyy-MM-dd HH:mm:ss.SSS"
  )
  @UpdateTimestamp
  @Column(name = "updated_at", columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
  private LocalDateTime updatedAt;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
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
}
