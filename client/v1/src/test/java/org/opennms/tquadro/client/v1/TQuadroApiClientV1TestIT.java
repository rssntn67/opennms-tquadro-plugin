package org.opennms.tquadro.client.v1;

import org.junit.Test;
import org.opennms.tquadro.client.api.ApiClientCredentials;

public class TQuadroApiClientV1TestIT {

    @Test
    public void validateTest() {
        V1ApiClientProvider provider = new V1ApiClientProvider();
        provider.validate(getCredential());
    }

    private ApiClientCredentials getCredential() {
        return ApiClientCredentials.builder()
                .withTQuadroUrl("https://tquadro.arsinfo.it:9443")
                .withUsername(System.getenv("TQ_USER"))
                .withPassword(System.getenv("TQ_PASS"))
                .withIgnoreSslCertificateValidation(true)
                .build();
    }

}
