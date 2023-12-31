
package org.opennms.tquadro.clients;

import java.util.Objects;
import java.util.Optional;

import org.opennms.tquadro.client.api.ApiClientCredentials;
import org.opennms.tquadro.client.api.ApiClientProvider;
import org.opennms.tquadro.client.api.ApiClientService;
import org.opennms.tquadro.connections.ConnectionValidationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientManager {
    private static final Logger LOG = LoggerFactory.getLogger(ClientManager.class);

    private final ApiClientProvider clientProvider;

    public ClientManager(ApiClientProvider providerA) {
        clientProvider = Objects.requireNonNull(providerA);
    }
    public Optional<ConnectionValidationError> validate(final ApiClientCredentials credentials) {
        boolean validated = clientProvider.validate(credentials);
        LOG.info("validate: {}, - {}", credentials,validated);
        if (validated) {
            return Optional.empty();
        }
        return Optional.of(new ConnectionValidationError("Credentials could not be validated"));
    }


    public ApiClientService getClient(ApiClientCredentials credentials) {
        return clientProvider.client(credentials);
    }
}
