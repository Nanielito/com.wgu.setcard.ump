package com.wgu.setcard.ump.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wgu.setcard.ump.model.User;
import com.wgu.setcard.ump.repository.spec.IUserRepository;
import com.wgu.setcard.ump.service.spec.IUserService;

@Service
public class UserServiceImpl implements IUserService {

  /* DEFINITIONS **************************************************/


  /* MEMBERS DECLARATIONS *****************************************/

  @Autowired
  private IUserRepository userRepository;

  @Override
  public User save(User user) {
    return userRepository.save(user);
  }

  /* CLASS CONSTRUCTORS *******************************************/


  /* METHODS IMPLEMENTATIONS **************************************/

  @Override
  public Optional<User> findById(String id) {
    return userRepository.findById(id);
  }

  @Override
  public Optional<User> findByUsername(String username) {
    return userRepository.findByUsername(username);
  }
}
