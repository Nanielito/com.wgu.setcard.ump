package com.wgu.setcard.ump.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wgu.setcard.ump.model.User;
import com.wgu.setcard.ump.service.spec.IUserAuthenticationService;
import com.wgu.setcard.ump.service.spec.IUserService;

@RestController
@RequestMapping("/api/v1/public/users")
public class PublicUserController {

  /* DEFINITIONS **************************************************/

  private static final String AUTHENTICATION_FAILED_MESSAGE = "Invalid login and/or password";

  /* MEMBERS DECLARATIONS *****************************************/

  @Autowired
  private IUserAuthenticationService userAuthenticationService;

  @Autowired
  private IUserService userService;

  /* METHODS IMPLEMENTATIONS **************************************/

  @PostMapping("/login")
  public String login(
      @RequestParam("username") final String username,
      @RequestParam("password") final String password) {
    return
        userAuthenticationService
          .login(username, password)
          .orElseThrow(() -> new RuntimeException(AUTHENTICATION_FAILED_MESSAGE));
  }

  @PostMapping("/register")
  public String register(
      @RequestParam("username") final String username,
      @RequestParam("password") final String password) {
    User user = new User(username, password);

    userService.save(user);

    return login(username, password);
  }
}
