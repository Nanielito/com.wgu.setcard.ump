package com.wgu.setcard.ump.service.spec;

import java.util.Optional;

import com.wgu.setcard.ump.model.User;

/**
 * Defines the <code>ITokenService</code> interface specification.
 *
 * @author danielramirez (https://github.com/nanielito)
 */
public interface IUserService {

  /* METHODS DECLARATIONS *****************************************/

  /**
   * Creates a new user.
   *
   * @param user A {@link User} which represents the new user to be created.
   *
   * @return The {@link User} which represents the created user.
   */
  User createUser(User user);

  /**
   * Gets a user by id.
   *
   * @param id A <code>String</code> which represents the id related to the user to be searched.
   *
   * @return The {@link User} which represents the searched user.
   */
  Optional<User> getUserById(String id);

  /**
   * Gets a user by username.
   *
   * @param username A <code>String</code> which represents the username related to the user to be searched.
   *
   * @return The {@link User} which represents the searched user.
   */
  Optional<User> getUserByUsername(String username);

  /**
   * Updates a user.
   *
   * @param user A {@link User} which represents the user to be updated.
   *
   * @return The {@link User} which represents the updated user.
   */
  User updateUser(User user);

  /**
   * Deletes a user.
   *
   * @param id A <code>String</code> which represents the id related to the user to be deleted.
   */
  void deleteUser(String id);
}
