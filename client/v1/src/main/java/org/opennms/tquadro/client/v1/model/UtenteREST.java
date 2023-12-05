package org.opennms.tquadro.client.v1.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * UtenteREST
 */

public class UtenteREST {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("fullName")
  private String fullName = null;

  @JsonProperty("email")
  private String email = null;

  @JsonProperty("username")
  private String username = null;

  public UtenteREST id(String id) {
    this.id = id;
    return this;
  }

   /**
   * Get id
   * @return id
  **/
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public UtenteREST fullName(String fullName) {
    this.fullName = fullName;
    return this;
  }

   /**
   * Get fullName
   * @return fullName
  **/
  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public UtenteREST email(String email) {
    this.email = email;
    return this;
  }

   /**
   * Get email
   * @return email
  **/
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public UtenteREST username(String username) {
    this.username = username;
    return this;
  }

   /**
   * Get username
   * @return username
  **/
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UtenteREST utenteREST = (UtenteREST) o;
    return Objects.equals(this.id, utenteREST.id) &&
        Objects.equals(this.fullName, utenteREST.fullName) &&
        Objects.equals(this.email, utenteREST.email) &&
        Objects.equals(this.username, utenteREST.username);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, fullName, email, username);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UtenteREST {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    fullName: ").append(toIndentedString(fullName)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    username: ").append(toIndentedString(username)).append("\n");
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
