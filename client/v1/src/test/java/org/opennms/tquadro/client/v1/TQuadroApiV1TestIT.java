package org.opennms.tquadro.client.v1;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.opennms.tquadro.client.v1.api.AccountApi;
import org.opennms.tquadro.client.v1.api.OpenNmsApi;
import org.opennms.tquadro.client.v1.handler.ApiClientExtension;
import org.opennms.tquadro.client.v1.handler.ApiException;
import org.opennms.tquadro.client.v1.model.AssetOpenNMS;
import org.opennms.tquadro.client.v1.model.AuthenticationUser;
import org.opennms.tquadro.client.v1.model.LoginAPIViewModel;

public class TQuadroApiV1TestIT {

    private ApiClientExtension getApiClient() throws ApiException {
        ApiClientExtension apiClient = new ApiClientExtension();
        apiClient.setBasePath("https://tquadro.arsinfo.it:9443");
        apiClient.setDebugging(true);
        apiClient.setIgnoreSslCertificateValidation(true);
        AccountApi accountApi = new AccountApi(apiClient);
        LoginAPIViewModel login = new LoginAPIViewModel();
        login.setUserName(System.getenv("TQ_USER"));
        login.setPassword(System.getenv("TQ_PASS"));
        AuthenticationUser authUser = accountApi.apiAccountLoginPost(login);
        System.out.println(authUser.getToken());
        System.out.println(authUser.getUser());
        apiClient.setApiKeyPrefix("Bearer");
        apiClient.setApiKey(authUser.getToken());

        return apiClient;

    }

    @Test
    public void testEnvVariable() {
        Assert.assertNotNull(System.getenv("TQ_USER"));
        Assert.assertNotNull(System.getenv("TQ_PASS"));
    }

    @Test
    public void opennmsApiGetNotExistingAssetByIpAddressTest() throws ApiException{
        OpenNmsApi openNmsApi = new OpenNmsApi(getApiClient());
        try {
            openNmsApi.apiOpenNMSAssetIpAddressGet("192.168.151.2");
        } catch (ApiException e) {
            Assert.assertEquals(404, e.getCode());
            //Code management
            //  400 Bad Request: Invalid IP address format
            //  401 Not Authorized
            //  404 Asset Not Found
            //  405 Method Not Allowed
            //  500 Internal Server Error

            Assert.assertEquals(404, e.getCode());
            Assert.assertEquals("\"Asset Not Found\"", e.getResponseBody());
            Assert.assertEquals("\"Asset Not Found\"", e.getMessage());
        }
    }

    @Test
    public void opennmsApiGetIpAddressInvalidIpTest() throws ApiException{
        OpenNmsApi openNmsApi = new OpenNmsApi(getApiClient());
        try {
           openNmsApi.apiOpenNMSAssetIpAddressGet("192.168.1.270");
        } catch (ApiException e) {
            //Code management
            //  400 Bad Request: Invalid IP address format
            //  401 Not Authorized
            //  404 Asset Not Found
            //  405 Method Not Allowed
            //  500 Internal Server Error

            Assert.assertEquals(400, e.getCode());
            Assert.assertEquals("\"Invalid IP address format\"", e.getResponseBody());
            Assert.assertEquals("\"Invalid IP address format\"", e.getMessage());

        }
    }

    @Test
    public void opennmsApiAssetIdPutAssetIdNotExistsTest() throws ApiException{
        OpenNmsApi openNmsApi = new OpenNmsApi(getApiClient());
        try {
            openNmsApi.apiOpenNMSDiscoveredDateAssetIdPut(7777777, "");
            //  204 Updated discovered date
        } catch (ApiException e) {
            Assert.assertEquals(400, e.getCode());
            //Code management
            //  400 Bad Request: Asset not found
            //  401 Not Authorized
            //  405 Method Not Allowed
            //  500 Internal Server Error
            Assert.assertEquals("\"Asset not found (id 7777777)\"", e.getResponseBody());
            Assert.assertNotNull(e.getResponseHeaders());
            Assert.assertTrue(e.getResponseHeaders().containsKey("Transfer-Encoding"));
            Assert.assertTrue(e.getResponseHeaders().containsKey("Strict-Transport-Security"));
            Assert.assertEquals("\"Asset not found (id 7777777)\"", e.getMessage());
        }
    }

    @Test
    @Ignore
    public void opennmsApiAssetIdPutAssetIdExistsTest() throws ApiException{
        OpenNmsApi openNmsApi = new OpenNmsApi(getApiClient());
        try {
            openNmsApi.apiOpenNMSDiscoveredDateAssetIdPut(17059, "");
        } catch (ApiException e) {
            Assert.fail();
        }
    }

    @Test
    public void opennmsApiPostTest() throws ApiException{
        OpenNmsApi openNmsApi = new OpenNmsApi(getApiClient());
        AssetOpenNMS asset = new AssetOpenNMS();
        asset.setAssetId(0);
        asset.setHostname("prova-15");
        asset.setIpAddress("10.10.10.15");
        asset.setSysObjectIdSNMP(".1.2.3.4.5.6");
        asset.setNameSNMP("prova-15-snmp");
        asset.setLocationSNMP("casa");
        asset.setContactSNMP("antonio@opennms.it");
        asset.setDescriptionSNMP("Prova per verificare che scrive correttamente");

        try {
            AssetOpenNMS response = openNmsApi.apiOpenNMSAssetPost(asset);
            Assert.assertEquals(response.getHostname(), asset.getHostname());
            Assert.assertEquals(response.getIpAddress(), asset.getIpAddress());
            AssetOpenNMS open = openNmsApi.apiOpenNMSAssetIpAddressGet(asset.getIpAddress());
            Assert.assertEquals(open.getAssetId(), response.getAssetId());
            Assert.assertEquals(open.getHostname(), response.getHostname());
            Assert.assertEquals(open.getIpAddress(), response.getIpAddress());

        } catch (ApiException e) {
            Assert.fail();
            //Code management
            //  400 Bad Request: Invalid IP address format
            //  401 Not Authorized
            //  405 Method Not Allowed
            //  200 Asset
            //  500 Internal Server Error
        }
    }


    @Test
    public void opennmsApiTest() throws ApiException{
        OpenNmsApi openNmsApi = new OpenNmsApi(getApiClient());
        AssetOpenNMS asset = new AssetOpenNMS();
        asset.setAssetId(0);
        asset.setHostname("gira-11.148");
        asset.setIpAddress("10.10.11.148");
        asset.setSysObjectIdSNMP(".1.2.3.4.6.6");
        asset.setNameSNMP("prova-11-snmp");
        asset.setLocationSNMP("casa");
        asset.setContactSNMP("antonio@opennms.it");
        asset.setDescriptionSNMP("Prova per verificare che scrive correttamente");
        try {
            AssetOpenNMS response = openNmsApi.apiOpenNMSAssetPost(asset);
            Assert.assertEquals(response.getHostname(), asset.getHostname());
            Assert.assertEquals(response.getIpAddress(), asset.getIpAddress());
            openNmsApi.apiOpenNMSDiscoveredDateAssetIdPut(response.getAssetId(), "");
        } catch (ApiException e) {
            System.out.println(e.getMessage());
            Assert.fail();
        }
    }

}
