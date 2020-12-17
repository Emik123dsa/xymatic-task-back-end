package com.graphql.xymatic.model;

import com.graphql.xymatic.enums.RoleEnums;
import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "xt_roles")
public class RoleModel implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn(name = "author", nullable = false, updatable = false)
  private UserModel author;

  @Column(name = "role")
  private String role;

  public RoleModel() {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public UserModel getAuthor() {
    return author;
  }

  public void setAuthor(UserModel author) {
    this.author = author;
  }

  public String getRole() {
    return role;
  }

  public void setRole(RoleEnums rEnums) {
    this.role = rEnums.getRole();
  }
}
