package org.opennms.tquadro.client.v1;

import org.junit.Assert;
import org.junit.Test;
import org.opennms.tquadro.client.v1.api.OpenNmsApi;
import org.opennms.tquadro.client.v1.handler.ApiException;
import org.opennms.tquadro.client.v1.model.AssetOpenNMS;
import org.opennms.tquadro.client.v1.model.LoginAPIViewModel;

public class TQuadroApiV1TestIT {

    private ApiClientExtension getApiClient() throws ApiException {
        ApiClientExtension apiClient = new ApiClientExtension();
        apiClient.setBasePath("https://tquadro.arsinfo.it:9443");
        apiClient.setDebugging(true);
        apiClient.setIgnoreSslCertificateValidation(true);
        AccountApiExtension accountApi = new AccountApiExtension(apiClient);
        LoginAPIViewModel login = new LoginAPIViewModel();
        login.setUserName(System.getenv("TQ_USER"));
        login.setPassword(System.getenv("TQ_PASS"));
        AuthenticationToken token = accountApi.getAuthorizationToken(login);
        System.out.println(token.getToken());
        System.out.println(token.getUser());
        apiClient.setApiKeyPrefix("Bearer");
        apiClient.setApiKey(token.getToken());

        return apiClient;

    }

    @Test
    public void testEnvVariable() {
        Assert.assertNotNull(System.getenv("TQ_USER"));
        Assert.assertNotNull(System.getenv("TQ_PASS"));
    }

    @Test
    public void opennmsApiGetIpAddressTest() throws ApiException{
        OpenNmsApi openNmsApi = new OpenNmsApi(getApiClient());
        try {
            AssetOpenNMS asset = openNmsApi.apiOpenNMSAssetIpAddressGet("10.28.1.270");
            //  200 Asset
            System.out.println(asset.getIpAddress());
            System.out.println(asset.getAssetId());
            System.out.println(asset.getHostname());
        } catch (ApiException e) {
            //Code management
            //  400 Bad Request: Invalid IP address format
            //  401 Not Authorized
            //  404 Asset Not Found
            //  405 Method Not Allowed
            //  500 Internal Server Error
                System.out.println(e.getCode());
                System.out.println(e.getResponseHeaders());
                System.out.println(e.getResponseBody());
                System.out.println(e.getMessage());
        }
    }

    @Test
    public void opennmsApiAssetIdPutTest() throws ApiException{
        OpenNmsApi openNmsApi = new OpenNmsApi(getApiClient());
        Integer assetId = 1;
        try {
            openNmsApi.apiOpenNMSDiscoveredDateAssetIdPut(assetId);
        } catch (ApiException e) {
            //Code management
            //  400 Bad Request: Invalid IP address format
            //  401 Not Authorized
            //  404 Asset Not Found
            //  405 Method Not Allowed
            //  200 Asset
            //  500 Internal Server Error
            System.out.println(e.getCode());
            System.out.println(e.getResponseHeaders());
            System.out.println(e.getResponseBody());
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void opennmsApiPostTest() throws ApiException{
        OpenNmsApi openNmsApi = new OpenNmsApi(getApiClient());
        AssetOpenNMS asset = new AssetOpenNMS();
        asset.setAssetId(0);
        asset.setHostname("prova");
        asset.setIpAddress("32.1.1.1");
        try {
            AssetOpenNMS saved = openNmsApi.apiOpenNMSAssetPost(asset);
            System.out.println(saved.getIpAddress());
            System.out.println(saved.getAssetId());
            System.out.println(saved.getHostname());

        } catch (ApiException e) {
            //Code management
            //  400 Bad Request: Invalid IP address format
            //  401 Not Authorized
            //  405 Method Not Allowed
            //  200 Asset
            //  500 Internal Server Error
            System.out.println(e.getCode());
            System.out.println(e.getResponseHeaders());
            System.out.println(e.getResponseBody());
            System.out.println(e.getMessage());
        }
    }


}
