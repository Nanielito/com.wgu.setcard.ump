package com.wgu.setcard.ump.service.impl;

import java.util.Optional;

import com.google.common.collect.ImmutableMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.wgu.setcard.ump.model.User;
import com.wgu.setcard.ump.repository.spec.IUserRepository;
import com.wgu.setcard.ump.service.spec.ITokenService;
import com.wgu.setcard.ump.service.spec.IUserAuthenticationService;

/**
 * Defines the class implementation for {@link IUserAuthenticationService} interface.
 *
 * @author danielramirez (https://github.com/nanielito)
 */
@Service
public class UserAuthenticationServiceImpl implements IUserAuthenticationService {

  /* DEFINITIONS **************************************************/

  private static final String USERNAME_KEY = "username";

  /* MEMBERS DECLARATIONS *****************************************/

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  private ITokenService tokenService;

  @Autowired
  private IUserRepository userRepository;

  /* CLASS CONSTRUCTORS *******************************************/


  /* METHODS IMPLEMENTATIONS **************************************/

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<String> login(final String username, final String password) {
    return userRepository.findByUsername(username)
        .filter(user -> bCryptPasswordEncoder.matches(password, user.getPassword()))
        .map(user -> tokenService.expiring(ImmutableMap.of(USERNAME_KEY, username)));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<User> findByToken(final String token) {
    return Optional.of(tokenService.verify(token))
        .map(attributes -> attributes.get(USERNAME_KEY))
        .flatMap(userRepository::findByUsername);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void logout(final User user) {

  }
}
