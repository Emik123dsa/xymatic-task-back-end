package com.graphql.xymatic.service;

import com.graphql.xymatic.model.UserModel;
import com.graphql.xymatic.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;

  public List<UserModel> findAll() {
    return userRepository.findAll();
  }

  public void saveUser(UserModel user) {
    userRepository.save(user);
  }

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }
}
