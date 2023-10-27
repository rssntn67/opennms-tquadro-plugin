package org.opennms.tquadro.client.v1;

import java.util.Objects;

import org.opennms.tquadro.client.v1.model.UtenteREST;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * AuthenticationToken
 */

public class AuthenticationToken {
  @JsonProperty("token")
  private String token = null;

  @JsonProperty("user")
  private UtenteREST user = null;

  @Schema()
  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public AuthenticationToken token(String token) {
    this.token = token;
    return this;
  }

   /**
   * Get user
   * @return UtenteREST
  **/
  @Schema()
  public UtenteREST getUser() {
    return user;
  }

  public void setUser(UtenteREST user) {
    this.user = user;
  }

  public AuthenticationToken user(UtenteREST user) {
    this.user = user;
    return this;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AuthenticationToken token = (AuthenticationToken) o;
    return Objects.equals(this.token, token.token) &&
        Objects.equals(this.user, token.user);
  }

  @Override
  public int hashCode() {
    return Objects.hash(token, user);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AuthenticationToken {\n");
    
    sb.append("    token: ").append(toIndentedString(token)).append("\n");
    sb.append("    user: ").append(user).append("\n");
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
