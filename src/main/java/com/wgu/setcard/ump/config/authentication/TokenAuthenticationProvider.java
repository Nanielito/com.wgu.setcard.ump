package com.wgu.setcard.ump.config.authentication;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.wgu.setcard.ump.service.spec.IUserAuthenticationService;

/**
 * Defines the <code>TokenAuthenticationProvider</code> class.
 *
 * @author danielramirez (https://github.com/nanielito)
 */
@Component
public class TokenAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

  /* DEFINITIONS **************************************************/

  private static final String USER_NOT_FOUND_MESSAGE = "Cannot find user with authentication token=%s";

  /* MEMBERS DECLARATIONS *****************************************/

  @Autowired
  private IUserAuthenticationService userAuthenticationService;

  /* CLASS CONSTRUCTORS *******************************************/


  /* METHODS IMPLEMENTATIONS **************************************/

  /**
   * {@inheritDoc}
   */
  @Override
  protected void additionalAuthenticationChecks(
      final UserDetails userDetails,
      final UsernamePasswordAuthenticationToken authenticationToken) {

  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UserDetails retrieveUser(
      final String username,
      final UsernamePasswordAuthenticationToken authenticationToken) {
    final Object token = authenticationToken.getCredentials();

    return Optional.ofNullable(token)
        .map(String::valueOf)
        .flatMap(userAuthenticationService::findByToken)
        .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, token)));
  }
}
