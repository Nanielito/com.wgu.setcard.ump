package com.wgu.setcard.ump.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.RedirectStrategy;

/**
 * Defines the <code>NoRedirectStrategy</code> class.
 *
 * @author danielramirez (https://github.com/nanielito)
 */
public class NoRedirectStrategy implements RedirectStrategy {

  /**
   * {@inheritDoc}
   */
  @Override
  public void sendRedirect(final HttpServletRequest request, final HttpServletResponse response, final String url)
      throws IOException {

  }
}
