package com.wgu.setcard.ump.service.spec;

import java.util.Optional;

import com.wgu.setcard.ump.model.User;

public interface IUserService {

  /* METHODS DECLARATIONS *****************************************/

  User save(User user);

  Optional<User> findById(String id);

  Optional<User> findByUsername(String username);
}
