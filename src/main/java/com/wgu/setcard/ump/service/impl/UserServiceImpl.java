package com.wgu.setcard.ump.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wgu.setcard.ump.model.User;
import com.wgu.setcard.ump.repository.spec.IUserRepository;
import com.wgu.setcard.ump.service.spec.IUserService;

/**
 * Defines the class implementation for {@link IUserService} interface.
 *
 * @author danielramirez (https://github.com/nanielito)
 */
@Service
public class UserServiceImpl implements IUserService {

  /* DEFINITIONS **************************************************/


  /* MEMBERS DECLARATIONS *****************************************/

  @Autowired
  private IUserRepository userRepository;

  /* CLASS CONSTRUCTORS *******************************************/


  /* METHODS IMPLEMENTATIONS **************************************/

  /**
   * {@inheritDoc}
   */
  @Override
  public User createUser(User user) {
    User newUser = null;

    if (userRepository.findByUsername(user.getUsername()).isPresent() == false) {
      newUser = userRepository.insert(user);
    }

    return newUser;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<User> getUserById(String id) {
    return userRepository.findById(id);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<User> getUserByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public User updateUser(User user) {
    User updatedUser = null;

    if (userRepository.findByUsername(user.getUsername()).isPresent() == true) {
      updatedUser = userRepository.save(user);
    }

    return updatedUser;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteUser(String id) {
    userRepository.deleteById(id);
  }
}
