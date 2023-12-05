package org.opennms.tquadro.client.v1.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * LoginAPIViewModel
 */

public class LoginAPIViewModel {
  @JsonProperty("userName")
  private String userName = null;

  @JsonProperty("password")
  private String password = null;

  public LoginAPIViewModel userName(String userName) {
    this.userName = userName;
    return this;
  }

   /**
   * Get userName
   * @return userName
  **/
  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public LoginAPIViewModel password(String password) {
    this.password = password;
    return this;
  }

   /**
   * Get password
   * @return password
  **/
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LoginAPIViewModel loginAPIViewModel = (LoginAPIViewModel) o;
    return Objects.equals(this.userName, loginAPIViewModel.userName) &&
        Objects.equals(this.password, loginAPIViewModel.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userName, password);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LoginAPIViewModel {\n");
    
    sb.append("    userName: ").append(toIndentedString(userName)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
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
