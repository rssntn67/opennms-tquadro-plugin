package org.opennms.tquadro.client.v1;

import java.util.HashSet;
import java.util.Set;

import org.opennms.tquadro.client.api.ApiClientCredentials;
import org.opennms.tquadro.client.api.ApiClientProvider;
import org.opennms.tquadro.client.api.ApiClientService;
import org.opennms.tquadro.client.v1.api.AccountApi;
import org.opennms.tquadro.client.v1.handler.ApiException;
import org.opennms.tquadro.client.v1.model.AuthenticationUser;
import org.opennms.tquadro.client.v1.model.LoginAPIViewModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class V1ApiClientProvider implements ApiClientProvider {

    private static final Logger LOG = LoggerFactory.getLogger(V1ApiClientProvider.class);
    Set<Integer> credentialAuthenticatedHashes = new HashSet<>();
    private ApiClientExtension getClient(ApiClientCredentials credentials) {
        ApiClientExtension apiClient = new ApiClientExtension();
        apiClient.setBasePath(credentials.tquadroUrl);
        apiClient.setIgnoreSslCertificateValidation(credentials.ignoreSslCertificateValidation);
        apiClient.setApiKeyPrefix("Bearer");
        apiClient.setApiKey(authenticate(apiClient,credentials));
        return apiClient;
    }

    private String authenticate(ApiClientExtension apiClient, ApiClientCredentials credentials) {
        AccountApi accountApi = new AccountApi(apiClient);
        LoginAPIViewModel login = new LoginAPIViewModel();
        login.setUserName(credentials.username);
        login.setPassword(credentials.password);
        try {
            AuthenticationUser token = accountApi.apiAccountLoginPost(login);
            credentialAuthenticatedHashes.add(credentials.hashCode());
            LOG.info("authenticate success for user: {}, on {}", apiClient.getBasePath(), login.getUserName());
            return token.getToken();
        } catch (ApiException e) {
            LOG.info("authenticate failed for user: {}, on {} -> {}", apiClient.getBasePath(), login.getUserName(), e.getMessage(), e);
        }
        return "invalid-token";
    }

    @Override
    public ApiClientService client(ApiClientCredentials credentials) {
            return new V1ApiClientService(getClient(credentials));
    }
    @Override
    public boolean validate(ApiClientCredentials credentials)  {
            getClient(credentials);
        return credentialAuthenticatedHashes.contains(credentials.hashCode());
    }
}
