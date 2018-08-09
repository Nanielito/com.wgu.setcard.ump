package com.wgu.setcard.ump.config;

import org.springframework.security.web.RedirectStrategy;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NoRedirectStrategy implements RedirectStrategy {

  @Override
  public void sendRedirect(final HttpServletRequest request, final HttpServletResponse response, final String url)
      throws IOException {

  }
}
