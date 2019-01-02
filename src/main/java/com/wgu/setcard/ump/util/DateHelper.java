package com.wgu.setcard.ump.util;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 * Defines the <code>DateHelper</code> class.
 *
 * @author danielramirez (https://github.com/nanielito)
 */
public final class DateHelper {

  /* DEFINITIONS **************************************************/


  /* MEMBERS DECLARATIONS *****************************************/

  private final DateTimeZone timeZone;

  /* CLASS CONSTRUCTORS *******************************************/

  /**
   * Initializes an instance of the class.
   *
   * @param timeZone The <code>DateTimeZone</code> time zone to be used to instantiate the class.
   */
  public DateHelper(final DateTimeZone timeZone) {
    this.timeZone = checkNotNull(timeZone);

    System.setProperty("user.timezone", timeZone.getID());
    TimeZone.setDefault(timeZone.toTimeZone());
    DateTimeZone.setDefault(timeZone);
  }

  /* METHODS IMPLEMENTATIONS **************************************/

  /**
   * Gets the current date time.
   *
   * @return The <code>DateTime</code> which represents the current date time.
   */
  public DateTime now() {
    return DateTime.now(timeZone);
  }
}
