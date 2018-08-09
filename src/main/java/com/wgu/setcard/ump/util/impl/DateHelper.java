package com.wgu.setcard.ump.util.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;;

import com.wgu.setcard.ump.util.spec.IDateHelper;

public final class DateHelper implements IDateHelper {

  /* DEFINITIONS **************************************************/


  /* MEMBERS DECLARATIONS *****************************************/

  private final DateTimeZone timeZone;

  /* CLASS CONSTRUCTORS *******************************************/

  public DateHelper(final DateTimeZone timeZone) {
    this.timeZone = checkNotNull(timeZone);

    System.setProperty("user.timezone", timeZone.getID());
    TimeZone.setDefault(timeZone.toTimeZone());
    DateTimeZone.setDefault(timeZone);
  }

  /* METHODS IMPLEMENTATIONS **************************************/

  @Override
  public DateTime now() {
    return DateTime.now(timeZone);
  }
}
