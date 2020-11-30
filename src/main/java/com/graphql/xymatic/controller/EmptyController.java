package com.graphql.xymatic.controller;

import com.graphql.xymatic.model.UserModel;
import com.graphql.xymatic.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class EmptyController {

  private final UserService userService;

  @RequestMapping(value = "/users", method = RequestMethod.GET)
  public ResponseEntity<List<UserModel>> listUsers() {
    return ResponseEntity.ok(userService.findAll());
  }

  @Autowired
  public EmptyController(UserService userService) {
    this.userService = userService;
  }
}
