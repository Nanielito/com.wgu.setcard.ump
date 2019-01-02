package com.wgu.setcard.ump.service.spec;

import java.util.Map;

/**
 * Defines the <code>ITokenService</code> interface specification.
 *
 * @author danielramirez (https://github.com/nanielito)
 */
public interface ITokenService {

  /* METHODS DECLARATIONS *****************************************/

  /**
   * Generates a permanent <code>JWT</code> session token.
   *
   * @param attributes A <code>Map</code> which contains the information related to a user.
   *
   * @return The <code>String</code> which represent the <code>JWT</code> session token.
   */
  String permanent(Map<String, String> attributes);

  /**
   * Generates an expiring <code>JWT</code> session token.
   *
   * @param attributes A <code>Map</code> which contains the information related to a user.
   *
   * @return The <code>String</code> which represent the <code>JWT</code> session token.
   */
  String expiring(Map<String, String> attributes);

  /**
   * Gets the user information related to a <code>JWT</code> session token which had been put as untrusted.
   *
   * @param token A <code>String</code> which represents a <code>JWT</code> sesion token.
   *
   * @return The <code>Map</code> which contains the user information.
   */
  Map<String, String> untrusted(String token);

  /**
   * Gets the user information related to a <code>JWT</code> session token which need to be verified.
   *
   * @param token A <code>String</code> which represents a <code>JWT</code> session token.
   *
   * @return The <code>Map</code> which contains the user information.
   */
  Map<String, String> verify(String token);
}
