package com.graphql.xymatic.model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "xt_users")
public class UserModel implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String email;

  private String password;

  public UserModel() {}

  public UserModel(Long id) {
    this.id = id;
  }

  public UserModel(String name, String email, String password) {
    this.name = name;
    this.email = email;
    this.password = password;
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

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPassword() {
    return password;
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
