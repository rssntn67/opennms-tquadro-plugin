package org.opennms.tquadro.client.v1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.GenericType;

import org.opennms.tquadro.client.v1.api.AccountApi;
import org.opennms.tquadro.client.v1.handler.ApiClient;
import org.opennms.tquadro.client.v1.handler.ApiException;
import org.opennms.tquadro.client.v1.handler.Pair;
import org.opennms.tquadro.client.v1.model.LoginAPIViewModel;

public class AccountApiExtension extends AccountApi {
    public AccountApiExtension(ApiClient apiClient) {
        super(apiClient);
    }

    public AuthenticationToken getAuthorizationToken(LoginAPIViewModel body) throws ApiException {
        Object localVarPostBody = body;
        // create path and map variables
        String localVarPath = "/api/Account/Login";

        // query params
        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();



        final String[] localVarAccepts = {

        };
        final String localVarAccept = getApiClient().selectHeaderAccept(localVarAccepts);

        final String[] localVarContentTypes = {
                "application/json", "text/json", "application/_*+json"
        };
        final String localVarContentType = getApiClient().selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "Bearer" };

        GenericType<AuthenticationToken> localVarReturnType = new GenericType<AuthenticationToken>() {};
        return getApiClient().invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

}
