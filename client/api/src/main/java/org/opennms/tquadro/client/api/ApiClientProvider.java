package org.opennms.tquadro.client.api;

public interface ApiClientProvider {
    /**
     * Create a client for a TQuadro account.
     *
     * @param credentials the credentials to use for the client.
     * @return a ApiClient client
     */
    ApiClientService client(final ApiClientCredentials credentials);

    boolean validate(final ApiClientCredentials credentials);

}
