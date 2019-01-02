package com.wgu.setcard.ump.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Defines the entity class related to the <code>User</code> model.
 *
 * @author danielramirez (https://github.com/nanielito)
 */
@Document(collection = "users")
public class User implements UserDetails {

  private static final long serialVersionUID = -5162974095093193826L;

  /* DEFINITIONS **************************************************/


  /* MEMBERS DECLARATIONS *****************************************/

  @Id
  private String id;

  @Indexed(unique = true)
  private String username;

  private String password;

  /* CLASS CONSTRUCTORS *******************************************/

  /**
   * Initializes an instance of the class.
   *
   * @param username A <code>String</code> which represents the username related to a user.
   * @param password A <code>String</code> which represents the password related to a user.
   */
  public User(final String username, final String password) {
    super();
    this.username = requireNonNull(username);
    this.password = requireNonNull(password);
  }

  /* METHODS IMPLEMENTATIONS **************************************/

  /**
   * Gets the <code>id</code> property value.
   *
   * @return The <code>id</code> property value.
   */
  public String getId() {
    return id;
  }

  /**
   * Gets the <code>username</code> property value.
   *
   * @return The <code>username</code> property value.
   */
  @Override
  public String getUsername() {
    return username;
  }

  /**
   * Gets the <code>password</code> property value.
   *
   * @return The <code>password</code> property value.
   */
  @Override
  public String getPassword() {
    return password;
  }

  /**
   * Sets the <code>password</code> property value.
   *
   * @param password A <code>String</code> which represents the password related to a user.
   */
  public void setPassword(final String password) {
    this.password = password;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<GrantedAuthority> getAuthorities() {
    return new ArrayList<>();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isEnabled() {
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    ObjectMapper mapper = new ObjectMapper();
    String jsonString = "";

    try {
      mapper.enable(SerializationFeature.INDENT_OUTPUT);
      jsonString = mapper.writeValueAsString(this);
    }
    catch (JsonProcessingException e) {
      e.printStackTrace();
    }

    return jsonString;
  }
}
