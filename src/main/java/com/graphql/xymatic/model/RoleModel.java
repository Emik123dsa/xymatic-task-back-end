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

  @Enumerated(EnumType.STRING)
  @Column(name = "name")
  private RoleEnums name;

  public RoleModel() {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public RoleEnums getName() {
    return name;
  }

  public void setName(RoleEnums name) {
    this.name = name;
  }

  public UserModel getAuthor() {
    return author;
  }

  public void setAuthor(UserModel userModel) {
    this.author = userModel;
  }
}
