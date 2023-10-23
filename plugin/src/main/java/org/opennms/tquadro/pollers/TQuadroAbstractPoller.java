
package org.opennms.tquadro.pollers;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import org.opennms.integration.api.v1.pollers.PollerRequest;
import org.opennms.integration.api.v1.pollers.PollerResult;
import org.opennms.integration.api.v1.pollers.ServicePoller;
import org.opennms.integration.api.v1.pollers.ServicePollerFactory;
import org.opennms.integration.api.v1.pollers.Status;
import org.opennms.integration.api.v1.pollers.immutables.ImmutablePollerResult;
import org.opennms.tquadro.client.api.ApiClientCredentials;
import org.opennms.tquadro.client.api.ApiClientService;
import org.opennms.tquadro.client.api.TQuadroApiException;
import org.opennms.tquadro.clients.ClientManager;
import org.opennms.tquadro.connections.ConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableMap;

public abstract class TQuadroAbstractPoller implements ServicePoller {
    private static final Logger LOG = LoggerFactory.getLogger(TQuadroAbstractPoller.class);
    private final ClientManager clientManager;

    protected TQuadroAbstractPoller(final ClientManager client) {
        this.clientManager = Objects.requireNonNull(client);
    }

    protected abstract CompletableFuture<PollerResult> poll(final Context context) throws TQuadroApiException;

    @Override
    public final CompletableFuture<PollerResult> poll(final PollerRequest pollerRequest) {
        try {
            LOG.debug("poll: {} {}", pollerRequest.getAddress().getHostAddress(), pollerRequest.getServiceName());
            return this.poll(new Context(pollerRequest));
        } catch (final TQuadroApiException e) {
            LOG.warn("TQuadro communication failed", e);
            return CompletableFuture.completedFuture(ImmutablePollerResult.newBuilder()
                                                                          .setStatus(Status.Down)
                                                                          .setReason(e.getMessage())
                                                                          .build());
        }
    }

    public static abstract class Factory<T extends TQuadroAbstractPoller> implements ServicePollerFactory<T> {

        private final ClientManager clientManager;

        private final ConnectionManager connectionManager;

        private final Class<T> clazz;

        protected Factory(final ClientManager client,
                          final ConnectionManager connectionManager,
                          final Class<T> clazz) {
            this.clientManager = Objects.requireNonNull(client);
            this.connectionManager = Objects.requireNonNull(connectionManager);

            this.clazz = Objects.requireNonNull(clazz);
        }

        protected abstract T createPoller(ClientManager clientManager);

        @Override
        public final T createPoller() {
            LOG.debug("Factory::createPoller -> class {}", getPollerClassName());
            return this.createPoller(this.clientManager);
        }

        @Override
        public final String getPollerClassName() {
            return this.clazz.getCanonicalName();
        }



        @Override
        public final Map<String, String> getRuntimeAttributes(final PollerRequest pollerRequest) {
            final var alias = Objects.requireNonNull(pollerRequest.getPollerAttributes().get(ConnectionManager.ALIAS_KEY), "Missing property: " + ConnectionManager.ALIAS_KEY);
            final var connection = this.connectionManager.getConnection(alias)
                                                         .orElseThrow(() -> new NullPointerException("Connection not found for alias: " + alias));
            LOG.debug("Factory::getRuntimeAttributes -> connection: {}, class {}", connection, getPollerClassName());

            final var attrs = ImmutableMap.<String,String>builder();
            attrs.put(ConnectionManager.PRISM_URL_KEY, connection.getTQuadroUrl());
            attrs.put(ConnectionManager.USERNAME_KEY, connection.getUsername());
            attrs.put(ConnectionManager.PASSWORD_KEY, connection.getPassword());
            attrs.put(ConnectionManager.IGNORE_SSL_CERTIFICATE_VALIDATION_KEY, String.valueOf(connection.isIgnoreSslCertificateValidation()));
            attrs.put(ConnectionManager.LENGTH_KEY, String.valueOf(connection.getLength()));
            return attrs.build();
        }
    }

    public class Context {
        public final PollerRequest request;

        public Context(final PollerRequest request) {
            this.request = Objects.requireNonNull(request);
        }

        public ApiClientCredentials getClientCredentials() {
            final var prismUrl = Objects.requireNonNull(this.request.getPollerAttributes().get(ConnectionManager.PRISM_URL_KEY),
                                                               "Missing attribute: " + ConnectionManager.PRISM_URL_KEY);
            final var username = Objects.requireNonNull(this.request.getPollerAttributes().get(ConnectionManager.USERNAME_KEY),
                                                      "Missing attribute: " + ConnectionManager.USERNAME_KEY);

            final var password = Objects.requireNonNull(this.request.getPollerAttributes().get(ConnectionManager.PASSWORD_KEY),
                    "Missing attribute: " + ConnectionManager.PASSWORD_KEY);

            final var ignoreSslCertificateValidation = Objects.requireNonNull(this.request.getPollerAttributes().get(ConnectionManager.IGNORE_SSL_CERTIFICATE_VALIDATION_KEY),
                    "Missing attribute: " + ConnectionManager.IGNORE_SSL_CERTIFICATE_VALIDATION_KEY);


            ApiClientCredentials credentials = ApiClientCredentials.builder()
                    .withTQuadroUrl(prismUrl)
                    .withUsername(username)
                    .withPassword(password)
                    .withIgnoreSslCertificateValidation(Boolean.parseBoolean(ignoreSslCertificateValidation))
                    .build();

            LOG.debug("Context::getClientCredentials -> {}", credentials);

            return credentials;
        }

        public ApiClientService client() throws TQuadroApiException {
            return TQuadroAbstractPoller.this.clientManager.getClient(this.getClientCredentials());
        }
    }
}
