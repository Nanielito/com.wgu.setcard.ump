package com.wgu.setcard.ump.config.authentication;

import static java.util.Optional.ofNullable;

import static org.apache.commons.lang3.StringUtils.removeStart;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * Defines the <code>TokenAuthenticationFilter</code> class.
 *
 * @author danielramirez (https://github.com/nanielito)
 */
public class TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

  /* DEFINITIONS **************************************************/

  private static final String BEARER = "Bearer";
  private static final String MISSING_AUTHENTICATION_TOKEN_MESSAGE = "Missing authentication token";

  /* MEMBERS DECLARATIONS *****************************************/


  /* CLASS CONSTRUCTORS *******************************************/

  public TokenAuthenticationFilter(final RequestMatcher requestMatcher) {
    super(requestMatcher);
  }

  /* METHODS IMPLEMENTATIONS **************************************/

  /**
   * {@inheritDoc}
   */
  @Override
  public Authentication attemptAuthentication(
      final HttpServletRequest request,
      final HttpServletResponse response) {
    final String parameter =
      ofNullable(request.getHeader(AUTHORIZATION))
        .orElse(request.getParameter("t"));
    final String token =
      ofNullable(parameter).map(value -> removeStart(value, BEARER))
        .map(String::trim)
        .orElseThrow(() -> new BadCredentialsException(MISSING_AUTHENTICATION_TOKEN_MESSAGE));
    final Authentication authentication = new UsernamePasswordAuthenticationToken(token, token);

    return getAuthenticationManager().authenticate(authentication);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void successfulAuthentication(
      final HttpServletRequest request,
      final HttpServletResponse response,
      final FilterChain chain,
      final Authentication authentication) throws IOException, ServletException {
    super.successfulAuthentication(request, response, chain, authentication);
    chain.doFilter(request, response);
  }
}
