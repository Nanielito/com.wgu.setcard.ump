package com.wgu.setcard.ump.service.spec;

import java.util.Optional;

import com.wgu.setcard.ump.model.User;

public interface IUserAuthenticationService {

  /* METHODS DECLARATIONS *****************************************/

  Optional<String> login(String username, String password);

  Optional<User> findByToken(String token);

  void logout(User user);
}
