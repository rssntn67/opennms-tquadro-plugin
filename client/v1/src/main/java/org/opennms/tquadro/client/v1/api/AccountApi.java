package org.opennms.tquadro.client.v1.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.GenericType;

import org.opennms.tquadro.client.v1.handler.ApiClient;
import org.opennms.tquadro.client.v1.handler.ApiException;
import org.opennms.tquadro.client.v1.handler.Pair;
import org.opennms.tquadro.client.v1.model.AuthenticationUser;
import org.opennms.tquadro.client.v1.model.LoginAPIViewModel;

public class AccountApi {
  private final ApiClient apiClient;

  public AccountApi(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  /**
   * Metodo che autentica un utente con username e passsword
   * 
   * @param body TQuadro.Models.AccountViewModels.LoginAPIViewModel username e password (optional)
   * @return AuthenticationUser
   * @throws ApiException if fails to make API call
   */
  public AuthenticationUser apiAccountLoginPost(LoginAPIViewModel body) throws ApiException {
      // create path and map variables
    String localVarPath = "/api/Account/Login";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<>();
    Map<String, String> localVarHeaderParams = new HashMap<>();
    Map<String, Object> localVarFormParams = new HashMap<>();



    final String[] localVarAccepts = {
      "text/plain", "application/json", "text/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json", "text/json", "application/_*+json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "Bearer" };

    GenericType<AuthenticationUser> localVarReturnType = new GenericType<>() {};
    return apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, body, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
}
