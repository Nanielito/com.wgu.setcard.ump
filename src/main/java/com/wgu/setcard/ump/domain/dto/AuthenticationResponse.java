package com.wgu.setcard.ump.domain.dto;

/**
 * Defines the <code>AuthenticationResponse</code> class.
 *
 * @author danielramirez (https://github.com/nanielito)
 */
public class AuthenticationResponse {

  /* DEFINITIONS **************************************************/


  /* MEMBERS DECLARATIONS *****************************************/

  private String token;

  /* CLASS CONSTRUCTORS *******************************************/

  /**
   * Initializes a default instance of the class.
   */
  public AuthenticationResponse() {
    this.token = null;
  }

  /**
   * Initializes an instance of the class.
   *
   * @param token A <code>String</code> which represents a <code>JWT</code> session token.
   */
  public AuthenticationResponse(final String token) {
    this.token = token;
  }

  /* METHODS IMPLEMENTATIONS **************************************/

  /**
   * Gets the <code>token</code> property value.
   *
   * @return The <code>token</code> property value.
   */
  public String getToken() {
    return token;
  }

  /**
   * Sets the <code>token</code> property value.
   *
   * @param token A <code>String</code> which represents a <code>JWT</code> session token.
   */
  public void setToken(final String token) {
    this.token = token;
  }
}
