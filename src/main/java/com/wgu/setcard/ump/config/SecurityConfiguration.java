package com.wgu.setcard.ump.config;

import static java.util.Objects.requireNonNull;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.wgu.setcard.ump.config.authentication.TokenAuthenticationFilter;
import com.wgu.setcard.ump.config.authentication.TokenAuthenticationProvider;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  /* DEFINITIONS **************************************************/

  private static final RequestMatcher PUBLIC_URLS = new OrRequestMatcher(
      new AntPathRequestMatcher("/api/v1/public/**"));

  private static final RequestMatcher PROTECTED_URLS = new NegatedRequestMatcher(PUBLIC_URLS);

  /* MEMBERS DECLARATIONS *****************************************/

  @Bean
  SimpleUrlAuthenticationSuccessHandler successHandler() {
    final SimpleUrlAuthenticationSuccessHandler successHandler = new SimpleUrlAuthenticationSuccessHandler();

    successHandler.setRedirectStrategy(new NoRedirectStrategy());

    return successHandler;
  }

  @Bean
  TokenAuthenticationFilter restAuthenticationFilter() throws Exception {
    final TokenAuthenticationFilter tokenAuthenticationFilter = new TokenAuthenticationFilter(PROTECTED_URLS);

    tokenAuthenticationFilter.setAuthenticationManager(authenticationManager());
    tokenAuthenticationFilter.setAuthenticationSuccessHandler(successHandler());

    return tokenAuthenticationFilter;
  }

  @Bean
  FilterRegistrationBean disableAutoRegistration(final TokenAuthenticationFilter tokenAuthenticationFilter) {
    final FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(tokenAuthenticationFilter);

    filterRegistrationBean.setEnabled(false);

    return filterRegistrationBean;
  }

  @Bean
  AuthenticationEntryPoint forbiddenEntryPoint() {
    return new HttpStatusEntryPoint(HttpStatus.FORBIDDEN);
  }

  private TokenAuthenticationProvider tokenAuthenticationProvider;

  /* CLASS CONSTRUCTORS *******************************************/

  SecurityConfiguration(final TokenAuthenticationProvider tokenAuthenticationProvider) {
    super();
    this.tokenAuthenticationProvider = requireNonNull(tokenAuthenticationProvider);
  }

  /* METHODS IMPLEMENTATIONS **************************************/

  @Override
  protected void configure(final AuthenticationManagerBuilder authenticationManagerBuilder) {
    authenticationManagerBuilder.authenticationProvider(tokenAuthenticationProvider);
  }

  @Override
  public void configure(WebSecurity webSecurity) throws Exception {
    webSecurity.ignoring().requestMatchers(PUBLIC_URLS);
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
      .sessionManagement()
      .sessionCreationPolicy(STATELESS)
      .and()
      .exceptionHandling()
      .defaultAuthenticationEntryPointFor(forbiddenEntryPoint(), PROTECTED_URLS)
      .and()
      .authenticationProvider(tokenAuthenticationProvider)
      .addFilterBefore(restAuthenticationFilter(), AnonymousAuthenticationFilter.class)
      .authorizeRequests()
      .anyRequest()
      .authenticated()
      .and()
      .csrf().disable()
      .formLogin().disable()
      .httpBasic().disable()
      .logout().disable();
  }
}
