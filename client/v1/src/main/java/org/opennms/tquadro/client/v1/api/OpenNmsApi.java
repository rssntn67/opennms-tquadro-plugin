package org.opennms.tquadro.client.v1.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.GenericType;

import org.opennms.tquadro.client.v1.handler.ApiClient;
import org.opennms.tquadro.client.v1.handler.ApiException;
import org.opennms.tquadro.client.v1.handler.Pair;
import org.opennms.tquadro.client.v1.model.AssetOpenNMS;

public class OpenNmsApi {
  private final ApiClient apiClient;

  public OpenNmsApi(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  /**
   * 
   * 
   * @param ipAddress Identifica l&#x27;ip address dell&#x27;asset (required)
   * @return AssetOpenNMS
   * @throws ApiException if fails to make API call
   */
  public AssetOpenNMS apiOpenNMSAssetIpAddressGet(String ipAddress) throws ApiException {
    // verify the required parameter 'ipAddress' is set
    if (ipAddress == null) {
      throw new ApiException(400, "Missing the required parameter 'ipAddress' when calling apiOpenNMSAssetIpAddressGet");
    }
    // create path and map variables
    String localVarPath = "/api/OpenNMS/Asset/{ipAddress}"
      .replaceAll("\\{" + "ipAddress" + "\\}", apiClient.escapeString(ipAddress));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<>();
    Map<String, String> localVarHeaderParams = new HashMap<>();
    Map<String, Object> localVarFormParams = new HashMap<>();



    final String[] localVarAccepts = {
      "text/plain", "application/json", "text/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "Bearer" };

    GenericType<AssetOpenNMS> localVarReturnType = new GenericType<>() {
    };
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, null, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * 
   * 
   * @param body  (optional)
   * @return AssetOpenNMS
   * @throws ApiException if fails to make API call
   */
  public AssetOpenNMS apiOpenNMSAssetPost(AssetOpenNMS body) throws ApiException {
      // create path and map variables
    String localVarPath = "/api/OpenNMS/Asset";

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

    GenericType<AssetOpenNMS> localVarReturnType = new GenericType<>() {};
    return apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, body, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * 
   * 
   * @param assetId  (required)
   * @param body text (optional)
   * @throws ApiException if fails to make API call
   */
  public void apiOpenNMSDiscoveredDateAssetIdPut(Integer assetId, String body) throws ApiException {
    // verify the required parameter 'assetId' is set
    if (assetId == null) {
      throw new ApiException(400, "Missing the required parameter 'assetId' when calling apiOpenNMSDiscoveredDateAssetIdPut");
    }
    // create path and map variables
    String localVarPath = "/api/OpenNMS/DiscoveredDate/{assetId}"
      .replaceAll("\\{" + "assetId" + "\\}", apiClient.escapeString(assetId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<>();
    Map<String, String> localVarHeaderParams = new HashMap<>();
    Map<String, Object> localVarFormParams = new HashMap<>();



    final String[] localVarAccepts = {
      "text/plain", "application/json", "text/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "text/plain", "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "Bearer" };

    apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, body, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, null);
  }
}
