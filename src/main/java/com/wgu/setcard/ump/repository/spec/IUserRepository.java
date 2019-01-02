package com.wgu.setcard.ump.repository.spec;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.wgu.setcard.ump.model.User;

/**
 * Defines the <code>IUserRepository</code> interface specification.
 *
 * @author danielramirez (https://github.com/nanielito)
 */
public interface IUserRepository extends MongoRepository<User, String> {

  /* METHODS DECLARATIONS *****************************************/

  /**
   * Inserts a new user into database.
   *
   * @param user A {@link User} which represents the user to be inserted.
   *
   * @return The {@link User} which represents the inserted user.
   */
  User insert(User user);

  /**
   * Finds a user from database by id.
   *
   * @param id A <code>String</code> which represents the id related to the user to be searched.
   *
   * @return The {@link User} which represents the searched user.
   */
  Optional<User> findById(String id);

  /**
   * Finds a user from database by username.
   *
   * @param username A <code>String</code> which represents the username related to the user to be searched.
   *
   * @return The {@link User} which represents the searched user.
   */
  Optional<User> findByUsername(String username);

  /**
   * Saves a user into database.
   *
   * @param user A {@link User} which represents the user to be updated.
   *
   * @return The {@link User} which represents the updated user.
   */
  User save(User user);

  /**
   * Deletes a user from database by id.
   *
   * @param id A <code>String</code> which represents the id related to the user to be deleted.
   */
  void deleteById(String id);
}
