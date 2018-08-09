package com.wgu.setcard.ump.service.impl;

import java.util.Objects;
import java.util.Optional;

import com.google.common.collect.ImmutableMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wgu.setcard.ump.model.User;
import com.wgu.setcard.ump.repository.spec.IUserRepository;
import com.wgu.setcard.ump.service.spec.ITokenService;
import com.wgu.setcard.ump.service.spec.IUserAuthenticationService;

@Service
public class UserAuthenticationServiceImpl implements IUserAuthenticationService {

  /* DEFINITIONS **************************************************/

  private static final String USERNAME_KEY = "username";

  /* MEMBERS DECLARATIONS *****************************************/

  @Autowired
  private ITokenService tokenService;

  @Autowired
  private IUserRepository userRepository;

  /* CLASS CONSTRUCTORS *******************************************/


  /* METHODS IMPLEMENTATIONS **************************************/

  @Override
  public Optional<String> login(final String username, final String password) {
    return
        userRepository
          .findByUsername(username)
          .filter(user -> Objects.equals(password, user.getPassword()))
          .map(user -> tokenService.expiring(ImmutableMap.of(USERNAME_KEY, username)));
  }

  @Override
  public Optional<User> findByToken(final String token) {
    return
        Optional.of(tokenService.verify(token))
          .map(map -> map.get(USERNAME_KEY))
          .flatMap(userRepository::findByUsername);
  }

  @Override
  public void logout(final User user) {

  }
}
