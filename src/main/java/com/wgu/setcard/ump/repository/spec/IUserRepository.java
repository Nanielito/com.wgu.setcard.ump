package com.wgu.setcard.ump.repository.spec;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.wgu.setcard.ump.model.User;

public interface IUserRepository extends MongoRepository<User, String> {

  /* METHODS DECLARATIONS *****************************************/

  User save(User user);

  Optional<User> findById(String id);

  Optional<User> findByUsername(String username);
}
