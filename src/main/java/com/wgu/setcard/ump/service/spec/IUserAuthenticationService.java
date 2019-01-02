package com.wgu.setcard.ump.service.spec;

import java.util.Optional;

import com.wgu.setcard.ump.model.User;

/**
 * Defines the <code>IUserAuthenticationService</code> interface specification.
 *
 * @author danielramirez (https://github.com/nanielito)
 */
public interface IUserAuthenticationService {

  /* METHODS DECLARATIONS *****************************************/

  /**
   * Logs in a user.
   *
   * @param username A <code>String</code> which represents the username related to a user.
   * @param password A <code>String</code> which represents the password related to a user.
   *
   * @return The <code>JWT</code> session token related to the user who creates a session.
   */
  Optional<String> login(String username, String password);

  /**
   * Finds a user by a <code>JWT</code> session token.
   *
   * @param token A <code>String</code> which represents a <code>JWT</code> session token.
   *
   * @return The {@link User} related to the <code>JWT</code> session token.
   */
  Optional<User> findByToken(String token);

  /**
   * Logs out a user.
   *
   * @param user A {@link User} which represents the user to be logged out.
   */
  void logout(User user);
}
