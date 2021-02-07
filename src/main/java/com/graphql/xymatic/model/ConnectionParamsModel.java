package com.graphql.xymatic.model;

public class ConnectionParamsModel {

  private final String authToken;

  /**
   *  Connection Params Instance
   * @param authToken
   */
  public ConnectionParamsModel(String authToken) {
    this.authToken = authToken;
  }

  public String getAuthToken() {
    return authToken;
  }
}
