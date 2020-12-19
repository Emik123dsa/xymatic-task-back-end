package com.graphql.xymatic.model;

public class UserInput {

  private final String name;
  private final String email;
  private final String password;

  public UserInput() {
    this.name = null;
    this.email = null;
    this.password = null;
  }

  public UserInput(String name, String email, String password) {
    this.name = name;
    this.email = email;
    this.password = password;
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
}
