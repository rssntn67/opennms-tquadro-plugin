package org.opennms.tquadro.client.v1;

import org.junit.Assert;
import org.junit.Test;
import org.opennms.tquadro.client.v1.api.OpenNmsApi;
import org.opennms.tquadro.client.v1.handler.ApiClient;
import org.opennms.tquadro.client.v1.handler.ApiException;
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
    public void opennmsApiTest() throws ApiException{
        OpenNmsApi openNmsApi = new OpenNmsApi(getApiClient());
        try {
            openNmsApi.apiOpenNMSAssetIpAddressGet("10.28.1.270");
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


}
