package com.wgu.setcard.ump.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wgu.setcard.ump.model.User;
import com.wgu.setcard.ump.service.spec.IUserAuthenticationService;

/**
 * Defines the RESTful entry points for the <code>SecuredUserRestController</code> class.
 *
 * @author danielramirez (https://github.com/nanielito)
 */
@RestController
@RequestMapping("/api/v1/users")
public class SecuredUserRestController {

  /* DEFINITIONS **************************************************/


  /* MEMBERS DECLARATIONS *****************************************/

  @Autowired
  private IUserAuthenticationService userAuthenticationService;

  /* METHODS IMPLEMENTATIONS **************************************/

  @GetMapping(value = "/current", produces = MediaType.APPLICATION_JSON_VALUE)
  public User getCurrentUser(@AuthenticationPrincipal final User user) {
    return user;
  }

  @GetMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
  public boolean logout(@AuthenticationPrincipal final User user) {
    userAuthenticationService.logout(user);
    return true;
  }
}
