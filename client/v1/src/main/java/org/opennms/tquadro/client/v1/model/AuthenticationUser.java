package org.opennms.tquadro.client.v1.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * AuthenticationUser
 */

public class AuthenticationUser {
  @JsonProperty("token")
  private String token = null;

  @JsonProperty("user")
  private UtenteREST user = null;

  public AuthenticationUser token(String token) {
    this.token = token;
    return this;
  }

   /**
   * Get token
   * @return token
  **/
  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public AuthenticationUser user(UtenteREST user) {
    this.user = user;
    return this;
  }

   /**
   * Get user
   * @return user
  **/
  public UtenteREST getUser() {
    return user;
  }

  public void setUser(UtenteREST user) {
    this.user = user;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AuthenticationUser authenticationUser = (AuthenticationUser) o;
    return Objects.equals(this.token, authenticationUser.token) &&
        Objects.equals(this.user, authenticationUser.user);
  }

  @Override
  public int hashCode() {
    return Objects.hash(token, user);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AuthenticationUser {\n");
    
    sb.append("    token: ").append(toIndentedString(token)).append("\n");
    sb.append("    user: ").append(toIndentedString(user)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
