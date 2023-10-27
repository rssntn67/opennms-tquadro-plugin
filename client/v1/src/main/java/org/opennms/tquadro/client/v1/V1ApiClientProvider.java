package org.opennms.tquadro.client.v1;

import org.opennms.tquadro.client.api.ApiClientCredentials;
import org.opennms.tquadro.client.api.ApiClientProvider;
import org.opennms.tquadro.client.api.ApiClientService;
import org.opennms.tquadro.client.api.TQuadroApiException;
import org.opennms.tquadro.client.v1.handler.ApiException;
import org.opennms.tquadro.client.v1.model.LoginAPIViewModel;

public class V1ApiClientProvider implements ApiClientProvider {

    private ApiClientExtension getClient(ApiClientCredentials credentials) throws TQuadroApiException {
        ApiClientExtension apiClient = new ApiClientExtension();
        apiClient.setBasePath(credentials.tquadroUrl);
        AccountApiExtension accountApi = new AccountApiExtension(apiClient);
        LoginAPIViewModel login = new LoginAPIViewModel();
        login.setUserName(credentials.username);
        login.setPassword(credentials.password);
        apiClient.setIgnoreSslCertificateValidation(credentials.ignoreSslCertificateValidation);
        try {
            AuthenticationToken token = accountApi.getAuthorizationToken(login);
            apiClient.setApiKeyPrefix("Bearer");
            apiClient.setApiKey(token.getToken());
        } catch (ApiException e) {
            throw new TQuadroApiException(e.getMessage(),e, e.getCode(),e.getResponseHeaders(), e.getResponseBody() );
        }
        return apiClient;
    }

    @Override
    public ApiClientService client(ApiClientCredentials credentials) {
        try {
            return new V1ApiClientService(getClient(credentials));
        } catch (TQuadroApiException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public boolean validate(ApiClientCredentials credentials)  {
        try {
            getClient(credentials);
        } catch (TQuadroApiException e) {
            return false;
        }
        return true;
    }
}
