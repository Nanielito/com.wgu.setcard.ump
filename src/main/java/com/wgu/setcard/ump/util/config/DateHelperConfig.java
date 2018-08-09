package com.wgu.setcard.ump.util.config;

import org.joda.time.DateTimeZone;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.wgu.setcard.ump.util.impl.DateHelper;

@Configuration
public class DateHelperConfig {

  @Bean
  DateTimeZone defaultTimeZone() {
    return DateTimeZone.UTC;
  }

  @Bean
  DateHelper dateHelper() {
    return new DateHelper(defaultTimeZone());
  }
}
