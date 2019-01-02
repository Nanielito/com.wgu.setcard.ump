package com.wgu.setcard.ump.domain.dto;

/**
 * Defines the <code>AuthenticationErrorResponse</code> class.
 *
 * @author danielramirez (https://github.com/nanielito)
 */
public class AuthenticationErrorResponse extends AuthenticationResponse {

  /* DEFINITIONS **************************************************/


  /* MEMBERS DECLARATIONS *****************************************/

  private String message;

  /* CLASS CONSTRUCTORS *******************************************/

  /**
   * Initializes an instance of the class.
   *
   * @param message A <code>String</code> which represents the message.
   */
  public AuthenticationErrorResponse(final String message) {
    super();
    this.message = message;
  }

  /**
   * Gets the <code>message</code> property value.
   *
   * @return The <code>message</code> property value.
   */
  public String getMessage() {
    return message;
  }

  /**
   * Sets the <code>message</code> property value.
   *
   * @param message A <code>String</code> which represents the message.
   */
  public void setMessage(String message) {
    this.message = message;
  }
}
