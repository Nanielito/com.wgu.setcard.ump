package com.wgu.setcard.ump.controller.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wgu.setcard.ump.domain.dto.AuthenticationResponse;
import com.wgu.setcard.ump.domain.dto.AuthenticationErrorResponse;
import com.wgu.setcard.ump.model.User;
import com.wgu.setcard.ump.service.spec.IUserAuthenticationService;
import com.wgu.setcard.ump.service.spec.IUserService;

/**
 * Defines the RESTful entry points for the <code>PublicUserRestController</code> class.
 *
 * @author danielramirez (https://github.com/nanielito)
 */
@RestController
@RequestMapping("/api/v1/public/users")
public class PublicUserRestController {

  /* DEFINITIONS **************************************************/

  private static final String AUTHENTICATION_FAILED_MESSAGE = "Invalid login and/or password";
  private static final String USER_PREVIOUSLY_REGISTERED = "User had been previously registered";

  /* MEMBERS DECLARATIONS *****************************************/

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  private IUserAuthenticationService userAuthenticationService;

  @Autowired
  private IUserService userService;

  /* METHODS IMPLEMENTATIONS **************************************/

  @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity login(
      @RequestParam("username") final String username,
      @RequestParam("password") final String password) {
    return userAuthenticationService.login(username, password)
        .map(token -> ResponseEntity.ok(new AuthenticationResponse(token)))
        .orElse(
            ResponseEntity
              .status(HttpStatus.UNAUTHORIZED)
              .body(new AuthenticationErrorResponse(AUTHENTICATION_FAILED_MESSAGE)));
  }

  @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity register(
      @RequestParam("username") final String username,
      @RequestParam("password") final String password) {
    User newUser = new User(username, bCryptPasswordEncoder.encode(password));

    return Optional.ofNullable(userService.createUser(newUser))
        .map(user -> login(user.getUsername(), password))
        .orElse(
            ResponseEntity
              .status(HttpStatus.CONFLICT)
              .body(new AuthenticationErrorResponse(USER_PREVIOUSLY_REGISTERED)));
  }
}
