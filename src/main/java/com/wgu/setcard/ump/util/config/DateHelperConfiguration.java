package com.wgu.setcard.ump.util.config;

import org.joda.time.DateTimeZone;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.wgu.setcard.ump.util.DateHelper;

/**
 * Defines the configuration component for the {@link DateHelper} class.
 *
 * @author danielramirez (https://github.com/nanielito)
 */
@Configuration
public class DateHelperConfiguration {

  /**
   * Gets the default time zone.
   *
   * @return The <code>DateTimeZone</code> time zone configured as default.
   */
  @Bean
  DateTimeZone defaultTimeZone() {
    return DateTimeZone.UTC;
  }

  /**
   * Gets the {@link DateHelper} related to the default time zone.
   *
   * @return A {@link DateHelper} related to the default time zone.
   */
  @Bean
  DateHelper dateHelper() {
    return new DateHelper(defaultTimeZone());
  }
}
