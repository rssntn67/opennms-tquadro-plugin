package org.opennms.tquadro.client.v1;

import org.opennms.tquadro.client.api.ApiClientCredentials;
import org.opennms.tquadro.client.api.ApiClientProvider;
import org.opennms.tquadro.client.api.ApiClientService;

public class V1ApiClientProvider implements ApiClientProvider {

    @Override
    public ApiClientService client(ApiClientCredentials credentials) {
        return new V1ApiClientService();
    }
    @Override
    public boolean validate(ApiClientCredentials credentials)  {
        return true;
    }
}
