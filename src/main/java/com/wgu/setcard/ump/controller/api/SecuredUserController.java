package com.wgu.setcard.ump.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wgu.setcard.ump.model.User;
import com.wgu.setcard.ump.service.spec.IUserAuthenticationService;

@RestController
@RequestMapping("/api/v1/users")
public class SecuredUserController {

  /* DEFINITIONS **************************************************/


  /* MEMBERS DECLARATIONS *****************************************/

  @Autowired
  private IUserAuthenticationService userAuthenticationService;

  /* METHODS IMPLEMENTATIONS **************************************/

  @GetMapping("/current")
  public User getCurrentUser(@AuthenticationPrincipal final User user) {
    return user;
  }

  @GetMapping("/logout")
  public boolean logout(@AuthenticationPrincipal final User user) {
    userAuthenticationService.logout(user);
    return true;
  }
}
